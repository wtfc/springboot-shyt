package com.jc.oa.archive.facade.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.domain.PageManager;
import com.jc.oa.archive.ArchiveException;
import com.jc.oa.archive.domain.Document;
import com.jc.oa.archive.domain.Filing;
import com.jc.oa.archive.domain.Folder;
import com.jc.oa.archive.domain.Permission;
import com.jc.oa.archive.facade.IArchiveFacadeService;
import com.jc.oa.archive.service.IArchiveFolderService;
import com.jc.oa.archive.service.IAudithisService;
import com.jc.oa.archive.service.IDocumentService;
import com.jc.oa.archive.service.IFilingService;
import com.jc.oa.archive.service.IPermissionService;
import com.jc.oa.archive.service.impl.ArchiveFolderServiceImpl;
import com.jc.oa.archive.service.impl.DocumentServiceImpl;
import com.jc.oa.archive.util.PermissionUtil;
import com.jc.oa.common.domain.Archive;
import com.jc.system.CustomException;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.FileUtil;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.content.domain.Attach;
import com.jc.system.content.service.IAttachService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.SettingUtils;

@Service
public class ArchiveFacadeService implements IArchiveFacadeService {
	@Autowired
	private IDocumentService docService;
	@Autowired
	private IArchiveFolderService folderService;
	@Autowired
	private IAudithisService audithisService;
	@Autowired
	private IFilingService filingService;
	
	@Autowired
	private IAttachService attachService;
	
	@Autowired
	private IPermissionService permissionService;
	
	protected transient final Logger log = Logger.getLogger(this.getClass());

	/**
	 * 检测父节点是否存在
	 * @param id
	 * @return
	 */
	private boolean checkFolder(Long id) {
		log.info("选择的归档文件夹id："+id);
		Folder folder = new Folder();
		folder.setFolderType(Constants.ARC_FOLDER_TYPE_FILE_DOC);
		boolean ok = true;
		try {
			folder.setDeleteFlag(null);
			List<Folder> list = folderService.queryAll(folder);
			Long parentFolderId = id;
			if(list == null) {
				ok = false;
			} else {
				while(true) {
					if(0 == parentFolderId || !ok) {
						//ok = false;
						break;
					}
					for(Folder f : list) {
						if(f.getId().longValue() == parentFolderId.longValue()) {
							if(f.getDmInRecycle().intValue() == 1 || f.getDeleteFlag().intValue() == 1) {
								ok = false;
								break;
							}
							parentFolderId = f.getParentFolderId();
						}
					}
				}
			}
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return ok;
		
	}
	@Override
	public Map<String, Object> archiveFile(Archive archive,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 检查参数
		if (archive.getFolderId() == null
				|| StringUtil.isEmpty(archive.getArchiveName())
				|| StringUtil.isEmpty(archive.getPiId())) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_022"));
			return resultMap;
		}
		// 查询归档文件夹
		Folder folder = new Folder();
		folder.setId(archive.getFolderId());
		folder.setFolderType(Constants.ARC_FOLDER_TYPE_FILE_DOC);
		if(!checkFolder(archive.getFolderId())) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_034"));
			return resultMap;
		}
		// 验证当前人有没有权限访问选中的文件夹
		Permission permission = new Permission();
		permission.setFolderId(archive.getFolderId());
		permission.setUserId(SystemSecurityUtils.getUser().getId());
		permission.setDeptId(SystemSecurityUtils.getUser().getDeptId());
		permission.setOrgId(SystemSecurityUtils.getUser().getOrgId());
		List<Permission> listPermission = null;
		try {
			listPermission = permissionService
					.queryPermission(permission);
			
			if(listPermission != null && listPermission.size()>0) {
				Folder folderPermission = PermissionUtil
						.permissionValue(listPermission);
				if(folderPermission == null || !folderPermission.isPermNewUpDown()) {
					resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
							MessageUtils.getMessage("JC_OA_ARCHIVE_027"));
					return resultMap;
				}
			}
			else{
				resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
						MessageUtils.getMessage("JC_OA_ARCHIVE_027"));
				return resultMap;
			}
		} catch (ArchiveException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			folder.setDmInRecycle(0);
			folder = folderService.get(folder);
			if(folder == null) {
				resultMap.put("success", "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
						MessageUtils.getMessage("JC_OA_ARCHIVE_023"));
				return resultMap;
			}
		} catch (CustomException e) {
			e.printStackTrace();
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_019"));
			return resultMap;
		}
		try {
			Attach attach = new Attach();
			attach.setBusinessId(Long.valueOf(archive.getPiId()));
			attach.setBusinessTable(archive.getTableName());
			attach.setIsPaged("1");
		//	attach.setCategory(category);
			List<Attach> attachList = null;//(List<Attach>) page_.getData();
			if(archive.getAttachList() != null && archive.getAttachList().size() > 0) {
				attachList = archive.getAttachList();
			} else {
				PageManager page = new PageManager();
				page.setPageRows(999999);
				PageManager page_ = attachService.query(attach, page);
				attachList = (List<Attach>) page_.getData();
			}
			if(attachList != null) {
				Map<String,String> filingMap = new HashMap<String, String>();
				for(Attach a : attachList) {
					filingMap.put(a.getFileName(), SettingUtils.getSetting("filePath").toString() + File.separatorChar + a.getResourcesName());
				}
				archive.setFilingMap(filingMap);
			}
			
			// 检查同一归档对象在同一文件夹下只允许一个实例
			Document checkDoc = new Document();
			checkDoc.setPiId(archive.getPiId());
			checkDoc.setFolderId(folder.getId());
			checkDoc.setFileType(Constants.ARC_DOC_FILETYPE_FILING);
			checkDoc.setTableName(archive.getTableName());//归档表名
			checkDoc = docService.get(checkDoc);
			Document doc=null;
			if (checkDoc == null) {
				// 如果归档对象为空，是新建归档
				// 创建归档对象
				doc = new Document();
				doc.setFolderId(folder.getId());
				doc.setPiId(archive.getPiId());
				doc.setFileType(Constants.ARC_DOC_FILETYPE_FILING);
				doc.setContentType(Constants.ARC_DOC_CONTENTTYPE_UNKNOWN);
				// doc.setDmLink(paramMap.get(Constants.ARC_FACADE_PARAM_DMLINK));
				doc.setDmName(archive.getArchiveName());
				doc.setDmLockStatus(Constants.ARC_DOC_LOCKSTATUS_UNLOCK);
				doc.setDmDir(folder.getFolderPath());
				doc.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
				doc.setDeleteFlag(0);
				// doc.setSeq(docService.getSeq());
				doc.setTableName(archive.getTableName());//归档表名
				Integer count = docService.save(doc);
				

				if (count > 0) {
					audithisService.audithis(request, doc.getId(),
							doc.getDmName(),
							Constants.ARC_AUDITHIS_DATATYPE_DOC,
							Constants.ARC_AUDITHIS_NEWUPDOWN, "新建归档 ");
					resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
					resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
							MessageUtils.getMessage("JC_OA_ARCHIVE_020"));
				}

			} else {
				// 如果归档对象不为空，更新归档
				doc=checkDoc;
				
				doc.setContentType(Constants.ARC_DOC_CONTENTTYPE_UNKNOWN);
				doc.setDmName(archive.getArchiveName());
				doc.setDmLockStatus(Constants.ARC_DOC_LOCKSTATUS_UNLOCK);
				doc.setDmDir(folder.getFolderPath());
				doc.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
				doc.setDeleteFlag(0);
				doc.setModifyDateNew(new Date());
				doc.setTableName(archive.getTableName());//归档表名
				Integer count=docService.update(doc);
				if (count > 0) {
					//先清空归档附件表
					Filing filing=new Filing();
					filing.setDocumentId(doc.getId());
					List<Filing> filings=filingService.queryAll(filing);
					if(filings.size() > 0) {
						String[] ids=new String[filings.size()];
						for (int i = 0; i < filings.size(); i++) {
							ids[i] = filings.get(i).getId().toString();
						}
						
						filing.setPrimaryKeys(ids);
						filingService.delete(filing, false);
					}
					
					audithisService.audithis(request, doc.getId(),
							doc.getDmName(),
							Constants.ARC_AUDITHIS_DATATYPE_DOC,
							Constants.ARC_AUDITHIS_NEWUPDOWN, "新建归档 ");
					resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
					resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
							MessageUtils.getMessage("JC_OA_ARCHIVE_020"));
				}
			}
			
			// 处理归档表单
			// 创建附件记录
			if (StringUtils.isEmpty(archive.getFormContent()) == false) {
				Filing filing = new Filing();
				filing.setFileName(archive.getArchiveName());

				filing.setDocumentId(doc.getId());

				filing.setFileType(Constants.ARC_ARCHIVE_FILETYPE_FORM);
				filing.setFormContent(archive.getFormContent());
				filingService.save(filing);
			}
			// 处理归档正文
			Map<String, String> bodyMap = archive.getBodyMap();
			if (bodyMap != null) {
				Calendar calendar = Calendar.getInstance();
				String newFileName = null;

				Iterator<String> keyIte = bodyMap.keySet().iterator();
				String fileName = null;
				String filePath = null;
				while (keyIte.hasNext()) {
					fileName = keyIte.next();
					filePath = bodyMap.get(fileName);
					calendar.setTimeInMillis(System.currentTimeMillis());
					// 复制附件文件
					newFileName = String
							.valueOf(calendar.getTimeInMillis())
							+ "-"
							+ String.valueOf(calendar
									.get(Calendar.HOUR_OF_DAY))
							+ "-"
							+ String.valueOf(calendar.get(Calendar.MINUTE))
							+ "-"
							+ String.valueOf(calendar.get(Calendar.SECOND))
							+ "." + FileUtil.getFileExt(filePath);
					FileUtil.copyFile(
							folderService.getAbsoluteContextPath(request)
									+ filePath,
							folderService.getAbsoluteParentPath(request)
									+ newFileName);
					File file = new File(
							folderService.getAbsoluteParentPath(request)
									+ newFileName);
					if (file.exists()) {
						// 创建附件记录
						Filing filing = new Filing();
						filing.setFileName(fileName);
						filing.setFilePath(Constants.DJ_UPLOAD_DIR
								+ File.separator + newFileName);
						filing.setDocumentId(doc.getId());
					//	BigDecimal size = new BigDecimal(file.length());
						filing.setFizeSize(getFileSize(file.length()));
						filing.setFileType(Constants.ARC_ARCHIVE_FILETYPE_BODY);
						filingService.save(filing);
					}
				}
			}
			// 处理归档附件
			Map<String, String> filingMap = archive.getFilingMap();
			if (filingMap != null) {
				Calendar calendar = Calendar.getInstance();
				String newFileName = null;

				Iterator<String> keyIte = filingMap.keySet().iterator();
				String fileName = null;
				String filePath = null;
				while (keyIte.hasNext()) {
					fileName = keyIte.next();
					filePath = filingMap.get(fileName);
					calendar.setTimeInMillis(System.currentTimeMillis());
					// 复制附件文件
					newFileName = String
							.valueOf(calendar.getTimeInMillis())
							+ "-"
							+ String.valueOf(calendar
									.get(Calendar.HOUR_OF_DAY))
							+ "-"
							+ String.valueOf(calendar.get(Calendar.MINUTE))
							+ "-"
							+ String.valueOf(calendar.get(Calendar.SECOND))
							+ "." + FileUtil.getFileExt(filePath);
					//判断文件是否存在 不存在进行文件夹创建
					File fileCreate = new File(folderService.getAbsoluteParentPath(request));
					if (!fileCreate.exists()||!fileCreate.isDirectory()) {
						fileCreate.mkdirs();
					}					
					FileUtil.copyFile(
							folderService.getAbsoluteContextPath(request)
									+ filePath,
							folderService.getAbsoluteParentPath(request)
									+ newFileName);
					File file = new File(
							folderService.getAbsoluteParentPath(request)
									+ newFileName);
					if (file.exists()) {
						// 创建附件记录
						Filing filing = new Filing();
						filing.setFileName(fileName);
						filing.setFilePath(Constants.DJ_UPLOAD_DIR
								+ File.separator + newFileName);
						filing.setDocumentId(doc.getId());
						//BigDecimal size = new BigDecimal(file.length());
						filing.setFizeSize(getFileSize(file.length()));
						filing.setFileType(Constants.ARC_ARCHIVE_FILETYPE_ATTACH);
						filingService.save(filing);
					}
				}
			}
		} catch (CustomException e) {
			e.printStackTrace();
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_021"));
			return resultMap;
		}
		return resultMap;
	}
	
	private String getFileSize(Long sizeValue) {
		BigDecimal size = new BigDecimal(sizeValue);
		if(sizeValue / 1024 < 1024) {
			
			return (size.divide(new BigDecimal(1024))
					.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toString()
					+ "KB");
		} else {
			return (size.divide(new BigDecimal(1024 * 1024))
					.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toString()
					+ "MB");
		}
	}
}
