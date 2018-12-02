package com.jc.oa.archive.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.archive.ArchiveException;
import com.jc.oa.archive.dao.IArchiveFolderDao;
import com.jc.oa.archive.domain.Document;
import com.jc.oa.archive.domain.Folder;
import com.jc.oa.archive.domain.Permission;
import com.jc.oa.archive.domain.Recycle;
import com.jc.oa.archive.domain.Relate;
import com.jc.oa.archive.domain.Version;
import com.jc.oa.archive.service.IArchiveFolderService;
import com.jc.oa.archive.service.IAudithisService;
import com.jc.oa.archive.service.IDocumentService;
import com.jc.oa.archive.service.IPermissionService;
import com.jc.oa.archive.service.IRelateService;
import com.jc.oa.archive.service.IVersionService;
import com.jc.oa.archive.util.PermissionUtil;
import com.jc.system.CustomException;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.FileUtil;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.content.domain.Attach;
import com.jc.system.content.service.IAttachBusinessService;
import com.jc.system.content.service.IAttachService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.AdminSide;
import com.jc.system.security.service.IAdminSideService;
import com.jc.system.security.util.SettingUtils;
import com.jc.system.security.util.UserUtils;

/**
 * @title GOA2.0源代码
D * @author
 * @version 2014-06-05
 */
@Service
public class ArchiveFolderServiceImpl extends BaseServiceImpl<Folder> implements
		IArchiveFolderService {

	private IArchiveFolderDao folderDao;

	private IDocumentService docService;

	@Autowired
	private IPermissionService permissionService;

	@Autowired
	private IAudithisService audithisService;

	@Autowired
	private IVersionService versionService;

	@Autowired
	private IRelateService relateService;
	
	@Autowired
	private IAdminSideService adminSideService;
	
	private IAttachBusinessService attachBusinessService;
	private IAttachService attachService;

	public ArchiveFolderServiceImpl() {
	}

	@Autowired
	public ArchiveFolderServiceImpl(IArchiveFolderDao folderDao,
			IDocumentService docService,
			IAttachBusinessService attachBusinessService,
			IAttachService attachService, IPermissionService permissionService) {
		super(folderDao);
		this.folderDao = folderDao;
		this.docService = docService;
		this.attachBusinessService = attachBusinessService;
		this.attachService = attachService;
		this.permissionService = permissionService;
	}

	private PageManager getDataByPermission(Folder folder) {
		PageManager p = new PageManager();
		p.setPageRows(999999999);
		return queryForPermission(folder, p, "queryCount", "query");
		/*
		 * try { } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); return null; }
		 */}

	/**
	 * 方法描述：获取指定目录下列表信息
	 * 
	 * @param folder
	 * @return
	 * @author zhanglg
	 * @version 2014年6月6日下午2:34:02
	 * @throws ArchiveException
	 * @see
	 */
	@Override
	public Folder getDirDocs(Folder folder) throws ArchiveException {
		folder.setCreateUser(null);
		// 如果参数中没有文件夹ID，既为查看根目录，应设置上级目录ID为0，做为查询条件
		if (folder.getId() == null) {
			folder.setParentFolderId(0L);
			folder.setFolderName("根目录");
			folder.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());   
		}

		try {
			folder = this.get(folder);
			if (folder == null) {
				return null;
			}

			// 查询直接子目录
			Folder subdir = new Folder();
			subdir.setParentFolderId(folder.getId());
			subdir.setFolderType(folder.getFolderType());
			subdir.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
			subdir.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
			subdir.setDeleteFlag(0);
			subdir.addOrderByFieldDesc("t.MODIFY_DATE");
			/*
			 * if(Constants.ARC_FOLDER_TYPE_PUB_DOC.equals(folder.getFolderType()
			 * )) { PageManager p = getDataByPermission(subdir); if(p != null) {
			 * folder.setSubdirs((List<Folder>)p.getData()); } } else
			 * if(Constants
			 * .ARC_FOLDER_TYPE_MY_DOC.equals(folder.getFolderType())) { }
			 */
			folder.setSubdirs(folderDao.queryAll(subdir));

			// 查询当前目录下文件
			Document doc = new Document();
			doc.setFolderId(folder.getId());
			doc.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
			doc.setDeleteFlag(0);
			if ("0".equals(folder.getFolderType())) {
				doc.setFileType(folder.getFolderType());
			}
			doc.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
			doc.addOrderByFieldDesc("t.MODIFY_DATE");
			/*
			 * if(Constants.ARC_FOLDER_TYPE_PUB_DOC.equals(folder.getFolderType()
			 * )) { doc.setFileType(Constants.ARC_FOLDER_TYPE_PUB_DOC);
			 * PageManager pm = docService.getDataByPermission(doc); if(pm !=
			 * null) { folder.setDocuments((List<Document>)pm.getData()); } }
			 * else
			 * if(Constants.ARC_FOLDER_TYPE_MY_DOC.equals(folder.getFolderType
			 * ())) { }
			 */
			folder.setDocuments(docService.queryAll(doc));
			/**/
			// 查询当前目录权限
			Permission permission = new Permission();
			permission.setFolderId(folder.getId());
			permission.setUserId(SystemSecurityUtils.getUser().getId());
			permission.setDeptId(UserUtils.getUser(
					SystemSecurityUtils.getUser().getId()).getDeptId());
			permission.setOrgId(UserUtils.getUser(
					SystemSecurityUtils.getUser().getId()).getOrgId());
			List<Permission> list = permissionService
					.queryPermission(permission);
			Folder permFolder = PermissionUtil.permissionValue(list);

			folder.setPermView(permFolder.isPermView());
			folder.setPermNewUpDown(permFolder.isPermNewUpDown());
			folder.setPermEdit(permFolder.isPermEdit());
			folder.setPermDelete(permFolder.isPermDelete());
			folder.setPermCopyPaste(permFolder.isPermCopyPaste());
			folder.setPermRename(permFolder.isPermRename());
			folder.setPermCollect(permFolder.isPermCollect());
			folder.setPermVersion(permFolder.isPermVersion());
			folder.setPermHistory(permFolder.isPermHistory());
			folder.setPermRelate(permFolder.isPermRelate());

		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		} catch (Exception e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}
		return folder;
	}

	/**
	 * 方法描述：归档目录下列表信息
	 * 
	 * @param folder
	 * @return
	 * @author weny
	 * @version 2014-07-09
	 * @throws ArchiveException
	 * @see
	 */
	@Override
	public Folder getFolderPermissionQuery(Folder folder)
			throws ArchiveException {
		folder.setCreateUser(null);
		// 如果参数中没有文件夹ID，既为查看根目录，应设置上级目录ID为0，做为查询条件
		if (folder.getId() == null) {
			folder.setParentFolderId(0L);
			folder.setFolderName("根目录");
			if(Constants.ARC_FOLDER_TYPE_FILE_DOC.equals(folder.getFolderType())) {
				folder.setCreateUserOrg(UserUtils.getUser(
						SystemSecurityUtils.getUser().getId()).getOrgId());
			}
		}

		try {
			folder = this.get(folder);
			if (folder == null) {
				return null;
			}

			// 查询直接子目录
			Folder subdir = new Folder();
			subdir.setParentFolderId(folder.getId());
			// subdir.setFolderType(Constants.ARC_FOLDER_TYPE_PUB_DOC);
			subdir.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
			subdir.setDeleteFlag(0);
			subdir.setOrderBy("a.MODIFY_DATE DESC");
			subdir.setCreateUser(SystemSecurityUtils.getUser().getId());
			subdir.setCreateUserDept(UserUtils.getUser(
					SystemSecurityUtils.getUser().getId()).getDeptId());
			subdir.setCreateUserOrg(UserUtils.getUser(
					SystemSecurityUtils.getUser().getId()).getOrgId());
			List<Folder> list = folderDao.getFolderPermission(subdir);
			// if(list != null && list.size()>0){
			// for(int i=0;i<list.size();i++){
			// Folder f = list.get(i);
			// f.setOwner(UserUtils.getUser(SystemSecurityUtils.getUser().getId()).getDisplayName());
			// }
			// }
			folder.setSubdirs(list);

			// 查询当前目录下文件
			Document doc = new Document();
			doc.setFolderId(folder.getId());
			doc.setFileType(Constants.ARC_FOLDER_TYPE_FILE_DOC);
			doc.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
			doc.setDeleteFlag(0);
			doc.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
			doc.setOrderBy("t.MODIFY_DATE DESC");
			folder.setDocuments(docService.queryAll(doc));

		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		} catch (Exception e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}
		return folder;
	}

	/**
	 * 方法描述：上传文档保存方法。通过上传组件统一上传，出于统一管理的目的，并且与点聚WebOffice组件工作模式兼容，
	 * 所有文件要统一移动到Constants.DJ_UPLOAD_DIR目录下。Attach表的记录不再需要，予以删除。
	 * 
	 * @param folder
	 * @throws ArchiveException
	 * @author 张立刚
	 * @version 2014年6月17日下午7:11:53
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public List<Document> uploadDocs(Folder folder, HttpServletRequest request)
			throws ArchiveException {
		List<Document> documents = new ArrayList<Document>();
		if (folder.getFileids() != null) {
			String fileIds[] = folder.getFileids().split(",");
			Folder queryFolder = new Folder();
			queryFolder.setId(folder.getId());
			Folder parentfolder;
			try {
				parentfolder = this.get(queryFolder);
			} catch (CustomException e1) {
				e1.printStackTrace();
				ArchiveException ae = new ArchiveException();
				ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
				throw ae;
			}
			// 保存文档
			if (fileIds != null && fileIds.length > 0) {
				for (String fileid : fileIds) {
					if (StringUtil.trimIsEmpty(fileid)) {
						continue;
					}

					try {
						// 读取上传的附件记录
						Attach attach = new Attach();
						attach.setId(new Long(fileid));
						attach = attachService.get(attach);

						// 创建文档记录
						Document doc = new Document();
						doc.setFolderId(folder.getId());
						doc.setModel(folder.getModel());
						doc.setFileType(Constants.ARC_DOC_FILETYPE_DOC);
						String fileName = docService.getNewFileName(
								folder.getId(),
								attach.getFileName().substring(0,
										attach.getFileName().lastIndexOf(".")),
								attach.getExt());
						doc.setDmName(fileName);
						doc.setDmAlias(attach.getResourcesName().substring(
								attach.getResourcesName().lastIndexOf("/") + 1));
						doc.setDmLockStatus(Constants.ARC_DOC_LOCKSTATUS_UNLOCK);
						doc.setPhysicalPath(Constants.DJ_UPLOAD_DIR
								+ File.separator + doc.getDmAlias());
						BigDecimal size = new BigDecimal(attach.getFileSize());
						if (attach.getFileSize() / 1024 < 1024) {

							doc.setDmSize(size.divide(new BigDecimal(1024))
									.setScale(2, BigDecimal.ROUND_HALF_UP)
									.toString()
									+ "K");
						} else {
							doc.setDmSize(size
									.divide(new BigDecimal(1024 * 1024))
									.setScale(2, BigDecimal.ROUND_HALF_UP)
									.toString()
									+ "M");
						}

						doc.setDmSuffix(attach.getExt());
						// 根据文件扩展名决定ContentType
						if ("doc".equalsIgnoreCase(doc.getDmSuffix())
								|| "dot".equalsIgnoreCase(doc.getDmSuffix())
								|| "docx".equalsIgnoreCase(doc.getDmSuffix())) {
							doc.setContentType(Constants.ARC_DOC_CONTENTTYPE_WORD);
						} else if ("xls".equalsIgnoreCase(doc.getDmSuffix())
								|| "xla".equalsIgnoreCase(doc.getDmSuffix())
								|| "xlsx".equalsIgnoreCase(doc.getDmSuffix())) {
							doc.setContentType(Constants.ARC_DOC_CONTENTTYPE_EXCEL);
						} else if ("ppt".equalsIgnoreCase(doc.getDmSuffix())
								|| "pot".equalsIgnoreCase(doc.getDmSuffix())
								|| "pps".equalsIgnoreCase(doc.getDmSuffix())
								|| "pptx".equalsIgnoreCase(doc.getDmSuffix())) {
							doc.setContentType(Constants.ARC_DOC_CONTENTTYPE_POWERPOINT);
						} else {
							doc.setContentType(Constants.ARC_DOC_CONTENTTYPE_UNKNOWN);
						}
						doc.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
						doc.setDmDir(parentfolder.getFolderPath());
						Document dt = new Document();
						dt.setModel(folder.getModel());
						doc.setSeq(docService.getSeq(dt));
						// 增加版本号
						Version v = versionService.createVersion(doc);
						doc.setCurrentVersion(v.getCurrentVersion());

						docService.save(doc);
						v.setBackUpId(doc.getId());
						v.setVersionDesc("上传文档(" + doc.getDmName() + ")版本号："
								+ v.getCurrentVersion());
						v.setIsCurrentUsed(1);
						versionService.save(v);

						documents.add(doc);
						File parentFile = new File(
								this.getAbsoluteParentPath(request));
						if (parentFile.exists() == false) {
							if (parentFile.mkdirs() == false) {
								// 创建目录失败
								ArchiveException ae = new ArchiveException();
								ae.setLogMsg(MessageUtils
										.getMessage("JC_SYS_002"));
								throw ae;
							}
						}
						// 读取上传的附件文件
						File sourceFile = new File(
								this.getAbsoluteContextPath(request)
										+ (String) SettingUtils
												.getSetting(SettingUtils.FILE_PATH)
										+ File.separator
										+ attach.getResourcesName());
						// 移动文件到DJ_UPLOAD_DIR
						File targetFile = new File(
								this.getAbsoluteParentPath(request)
										+ doc.getDmAlias());
						if (sourceFile.exists()) {
							// 用rename方式移动
							if (sourceFile.renameTo(targetFile) == false) {
								// 如果rename方式移动不成功，调用JC封装方法通过Copy/Delete方式实现移动
								FileUtil.moveFile(sourceFile.getAbsolutePath(),
										targetFile.getAbsolutePath());
							}
						}
						// 删除Attach记录
						attach.setPrimaryKeys(new String[] { String
								.valueOf(attach.getId()) });
						attachService.delete(attach, false);
					} catch (CustomException e) {
						e.printStackTrace();
						ArchiveException ae = new ArchiveException();
						ae.setLogMsg(MessageUtils.getMessage("JC_SYS_002"));
						throw ae;
					}
				}
			}
		}

		return documents;
	}

	/**
	 * 方法描述：删除选中的文件夹和文档
	 * 
	 * @param ids
	 *            Id数组字符串格式，如：#dir_1,#dir_2,#doc_1
	 * @return 返回删除的对象
	 * @author zhanglg
	 * @version 2014年6月19日上午10:13:28
	 * @throws ArchiveException
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Folder deleteDirDocs(String ids, HttpServletRequest request)
			throws ArchiveException {
		String[] idsArray = ids.split(",");
		ArrayList<String> folderIds = new ArrayList<String>();
		ArrayList<String> docIds = new ArrayList<String>();
		Folder returnFolder = new Folder();
		returnFolder.setSubdirs(new ArrayList<Folder>());
		returnFolder.setDocuments(new ArrayList<Document>());
		// 分类要删除的对象Id
		for (String id : idsArray) {
			String[] item = id.split("_");
			if (item.length == 2 && "#dir".equals(item[0])) {
				folderIds.add(item[1]);

			} else if (item.length == 2 && "#doc".equals(item[0])) {
				docIds.add(item[1]);
			}
		}
		// 删除文件夹
		if (folderIds.size() > 0) {
			Folder folder = new Folder();
			String[] pks = new String[folderIds.size()];
			for (int i = 0; i < folderIds.size(); i++) {
				String pk = folderIds.get(i);
				pks[i] = pk;
			}
			folder.setPrimaryKeys(pks);
			folder.setModifyUser(SystemSecurityUtils.getUser().getId());
			folder.setModifyDate(new Date());
			folderDao.deleteDirToRecycle(folder);
			returnFolder.getSubdirs().add(folder);
		}
		// 删除文档
		if (docIds.size() > 0) {
			Document doc = new Document();
			String[] pks = new String[docIds.size()];
			for (int i = 0; i < docIds.size(); i++) {
				String pk = docIds.get(i);
				pks[i] = pk;

			}
			doc.setPrimaryKeys(pks);
			doc.setModifyDate(new Date());
			docService.deleteDocToRecycle(doc);
			try {
				Relate relate = new Relate();
				relate.setPrimaryKeys(pks);
				relateService.deleteRelateDM(relate);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
				e.printStackTrace();
			}
			returnFolder.getDocuments().add(doc);
		}
		return returnFolder;
	}

	/**
	 * 方法描述：回收站数据查询
	 * 
	 * @param folder
	 * @throws ArchiveException
	 * @author weny
	 * @version 2014年6月18日
	 * @see
	 */
	public List<Recycle> selectRecycle() throws ArchiveException {
		List<Recycle> recycle = new ArrayList<Recycle>();
		Document document = new Document();
		document.setDmInRecycle(1);
		document.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
		document.setOrderBy("t.MODIFY_DATE DESC");
		Folder folder = new Folder();
		folder.setDmInRecycle(1);
		folder.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
		folder.setOrderBy("t.MODIFY_DATE DESC");
		List<Folder> folerRecycle = null;
		List<Document> documentRecycle = null;
		try {
			folerRecycle = this.queryAll(folder);
			documentRecycle = docService.queryAll(document);
			if (folerRecycle != null) {
				for (int i = 0; i < folerRecycle.size(); i++) {
					Recycle recycleFolder = new Recycle();
					Folder der = folerRecycle.get(i);
					recycleFolder.setId(der.getId());
					recycleFolder.setDmName(der.getFolderName());
					recycleFolder.setContentType("0");
					recycleFolder.setType("0");
					recycleFolder.setDmSize("0");
					recycleFolder.setDmSuffix("文件夹");
					recycleFolder.setModifyDate(der.getModifyDate());
					recycle.add(recycleFolder);
				}
			}
			if (documentRecycle != null) {
				for (int i = 0; i < documentRecycle.size(); i++) {
					Recycle recycleFolder = new Recycle();
					Document der = documentRecycle.get(i);
					recycleFolder.setId(der.getId());
					recycleFolder.setDmName(der.getDmName());
					recycleFolder.setContentType(der.getContentType());
					recycleFolder.setType("1");
					recycleFolder.setDmSize(der.getDmSize());
					recycleFolder.setModifyDate(der.getModifyDate());
					recycleFolder.setDmSuffix(der.getDmSuffix());
					recycle.add(recycleFolder);
				}
			}
		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}
		return recycle;
	}

	/**
	 * 方法描述：回收站数据查询
	 * 
	 * @param folder
	 * @throws ArchiveException
	 * @author weny
	 * @version 2014年6月18日
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer batchDelete(String id, String type) throws ArchiveException {
		Integer count = 0;
		try {
			if ("0".equals(type)) {
				Folder folder = new Folder();
				folder.setPrimaryKeys(id.split(","));
				count = this.delete(folder);
				String[] permissionId = id.split(",");
				for (int i = 0; i < permissionId.length; i++) {
					Permission permission = new Permission();
					permission.setFolderId(Long.valueOf(permissionId[i]));
					List<Permission> list = permissionService
							.queryAll(permission);
					for (int j = 0; j < list.size(); j++) {
						permissionService.delectPermission(list.get(j));
					}
				}
			} else {
				Document document = new Document();
				document.setPrimaryKeys(id.split(","));
				count = docService.delete(document);
			}
		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}
		return count;
	}

	/**
	 * 方法描述：回收站数据还原
	 * 
	 * @param folder
	 * @throws ArchiveException
	 * @author weny
	 * @version 2014年6月26日
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Map<String, Object> batchRecycle(Long id, String type)
			throws ArchiveException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// 文件夹还原
			if ("0".equals(type)) {
				Folder folder = new Folder();
				folder.setId(id);
				folder.setDeleteFlag(0);
				folder.setDmInRecycle(1);
				folder = this.get(folder);
				if(folder == null) {
					resultMap.put("success", "false");
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
							MessageUtils.getMessage("JC_OA_ARCHIVE_023"));
					return resultMap;
				}
				// check文件所在文件夹是否被删除
				if (checkDelectFolder(folder.getParentFolderId()) == 0) {
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
							MessageUtils.getMessage("JC_OA_ARCHIVE_010"));
					return resultMap;
				}
				// 文件夹还原重命名check
				// getNewFolderName();
				String dmName = getNewFolderName(folder.getParentFolderId(),
						folder.getFolderName());
				if (dmName.length() > 64) {
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
							MessageUtils.getMessage("JC_OA_ARCHIVE_026"));
					return resultMap;
				}
				folder.setFolderName(dmName);
				folder.setDmInRecycle(0);
				folder.setModifyDateNew(new Date());
				folderDao.update(folder);
				resultMap.put("success", "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils
						.getMessage("JC_OA_ARCHIVE_012",
								new String[] { dmName }));
				// 文档还原
			} else {
				Document document = new Document();
				document.setId(id);
				document.setDeleteFlag(0);
				document.setDmInRecycle(1);
				document.setFileType(Constants.ARC_DOC_FILETYPE_DOC);
				document = docService.get(document);
				if(document == null) {
					resultMap.put("success", "false");
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
							MessageUtils.getMessage("JC_OA_ARCHIVE_023"));
					return resultMap;
				}
				// check文件所在文件夹是否被删除
				if (checkDelectFolder(document.getFolderId()) == 0) {
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
							MessageUtils.getMessage("JC_OA_ARCHIVE_010"));
					return resultMap;
				}
				Permission permission = new Permission();
				permission.setFolderId(document.getFolderId());
				permission.setUserId(SystemSecurityUtils.getUser().getId());
				permission.setDeptId(SystemSecurityUtils.getUser().getDeptId());
				permission.setOrgId(SystemSecurityUtils.getUser().getOrgId());
				List<Permission> list = permissionService
						.queryPermission(permission);
				Folder folderPermission = PermissionUtil.permissionValue(list);

				// 文档还原重命名check
				String dmName = docService.getNewFileName(
						document.getFolderId(), document.getDmName(),
						document.getDmSuffix(), Constants.ARC_DOC_FILETYPE_DOC);
				if (dmName.length() > 64) {
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
							MessageUtils.getMessage("JC_OA_ARCHIVE_026"));
					return resultMap;
				}
				if (document.getModel().intValue() == 1
						|| folderPermission.isPermNewUpDown()
						|| document.getCreateUser().longValue() == SystemSecurityUtils
								.getUser().getId().longValue()) {
					document.setDmName(dmName);
					document.setDmInRecycle(0);
					document.setModifyDateNew(new Date());
					docService.update(document);
					resultMap.put("success", "true");
					resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
							MessageUtils.getMessage("JC_OA_ARCHIVE_012",
									new String[] { dmName }));
				} else {
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
							MessageUtils.getMessage("JC_OA_ARCHIVE_011"));
				}
			}
		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}
		return resultMap;
	}

	// 查询文件夹是否被删除
	private Integer checkDelectFolder(Long folderId) throws CustomException {
		Integer num = 0;
		Folder folderParent = new Folder();
		folderParent.setId(folderId);
		folderParent.setDeleteFlag(0);
		folderParent.setDmInRecycle(0);
		folderParent = this.get(folderParent);
		if (folderParent == null) {
			num = 0;
		} else if (folderParent.getParentFolderId().longValue() == 0) {
			num = 1;
		} else {
			return checkDelectFolder(folderParent.getParentFolderId());
		}
		return num;
	}

	// 文件夹还原重命名判断
	private String folderPermission(String folderName, Long parentFolderId,
			String fileType) throws CustomException {
		Folder folder = new Folder();
		folder.setFolderName(folderName);
		folder.setParentFolderId(parentFolderId);
		folder.setDmInRecycle(0);
		folder.setDeleteFlag(0);
		folder.setFolderType(fileType);
		List<Folder> permission = this.queryAll(folder);
		if (permission != null && permission.size() > 0) {
			return folderPermission(folderName + "(1)", parentFolderId,
					fileType);
		}
		return folderName;
	}

	// 文档还原重命名判断
	private String documentPermission(String dmName, Long folderId,
			String fileType, String dmSuffix) throws CustomException {
		Document document = new Document();
		document.setDmName(dmName);
		document.setFolderId(folderId);
		document.setDmInRecycle(0);
		document.setDeleteFlag(0);
		document.setFileType(fileType);
		document.setDmSuffix(dmSuffix);
		List<Document> permission = docService.queryAll(document);
		if (permission != null && permission.size() > 0) {
			return documentPermission(dmName + "(1)", folderId, fileType,
					dmSuffix);
		}
		return dmName;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public synchronized Document copyDocTo(Document document, Folder folder,
			HttpServletRequest request) throws ArchiveException {
		Document newDoc = new Document();
		try {
			// 查询原文档
			// Document oldDoc = new Document();
			// oldDoc.setId(document.getId());
			// oldDoc = docService.get(oldDoc);
			// 生成文件名
			Calendar calendar = Calendar.getInstance();
			String fileName = String.valueOf(calendar.getTimeInMillis()) + "-"
					+ String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + "-"
					+ String.valueOf(calendar.get(Calendar.MINUTE)) + "-"
					+ String.valueOf(calendar.get(Calendar.SECOND)) + "."
					+ document.getDmSuffix();
			// 设置新文档
			newDoc.setFileType(document.getFileType());
			newDoc.setContentType(document.getContentType());
			newDoc.setDmName(document.getDmName());
			newDoc.setDmAlias(fileName);
			newDoc.setDmLockStatus(document.getDmLockStatus());
			newDoc.setDmSize(document.getDmSize());
			newDoc.setDmSuffix(document.getDmSuffix());
			newDoc.setDmInRecycle(document.getDmInRecycle());
			newDoc.setModel(document.getModel());
			// 查询目标目录
			// Folder folder = new Folder();
			// folder.setId(document.getFolderId());
			// folder = this.get(folder);

			// 生成新文件名
			newDoc.setDmName(docService.getNewFileName(folder.getId(),
					document.getDmName(), document.getDmSuffix()));
			newDoc.setId(null);
			Document dt = new Document();
			dt.setModel(document.getModel());
			newDoc.setSeq(docService.getSeq(dt));
			// 增加版本号
			Version v = versionService.createVersion(newDoc);
			newDoc.setCurrentVersion(v.getCurrentVersion());
			v.setVersionDesc("复制文档(" + newDoc.getDmName() + ")版本号："
					+ newDoc.getCurrentVersion());

			newDoc.setFolderId(folder.getId());
			newDoc.setDmDir(folder.getFolderPath());
			newDoc.setPhysicalPath(Constants.DJ_UPLOAD_DIR + File.separator
					+ fileName);
			// 复制File
			FileUtil.copyFile(
					this.getAbsoluteContextPath(request)
							+ document.getPhysicalPath(),
					this.getAbsoluteParentPath(request) + fileName);

			// 保存文件记录

			docService.save(newDoc);
			v.setBackUpId(newDoc.getId());
			v.setIsCurrentUsed(1);
			versionService.save(v);

		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		} catch (Exception e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
		}

		return newDoc;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Document cutDocTo(Document document) throws ArchiveException {

		// 查询原文档
		Document oldDoc = new Document();
		try {
			oldDoc.setId(document.getId());
			oldDoc = docService.get(oldDoc);

			// 查询目标目录
			Folder folder = new Folder();
			folder.setId(document.getFolderId());
			folder = this.get(folder);
			// 生成新文件名
			oldDoc.setDmName(docService.getNewFileName(document.getFolderId(),
					oldDoc.getDmName(), oldDoc.getDmSuffix()));

			oldDoc.setFolderId(document.getFolderId());
			oldDoc.setPhysicalPath(Constants.DJ_UPLOAD_DIR + File.separator
					+ oldDoc.getDmAlias());
			// 保存文件记录
			docService.update(oldDoc);
			// 更新附件表记录中的文件名信息
			Attach attach = new Attach();
			attach.setBusinessIdArray(new String[] { oldDoc.getId().toString() });
			List<Attach> attachs = attachService
					.queryAttachByBusinessIds(attach);
			if (attachs != null && attachs.size() > 0) {
				attach = attachs.get(0);
				attach.setFileName(oldDoc.getDmName());
				attachService.update(attach);
			}

		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		} catch (Exception e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
		}
		return oldDoc;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public synchronized Folder copyDirTo(Folder sourceFolder,
			Folder targetFolder, HttpServletRequest request)
			throws ArchiveException {

		Folder newFolder = new Folder();
		try {
			// // 查询源目录信息
			// Folder sourceFolder = new Folder();
			// sourceFolder.setId(folder.getId());
			// sourceFolder = this.get(sourceFolder);
			// // 查询目标目录
			// Folder targetFolder = new Folder();
			// targetFolder.setId(folder.getParentFolderId());
			// targetFolder = this.get(targetFolder);

			newFolder = sourceFolder.clone();
			newFolder.setId(null);
			newFolder.setParentFolderId(targetFolder.getId());
			// 生成新目录名
			newFolder.setFolderName(this.getNewFolderName(targetFolder.getId(),
					sourceFolder.getFolderName()));
			// 保存新目录
			newFolder.setFolderPath(targetFolder.getFolderPath() + "/"
					+ newFolder.getFolderName());
			this.save(newFolder);

			// 递归生成新目录树结构、并逐级保存
			this.recursionCopyFolder(sourceFolder, newFolder, request);
		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		} catch (Exception e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
		}
		return newFolder;
	}

	/**
	 * 方法描述：递归复制目录 用于复制下级目录到一个新目录，不做重名的校验
	 * 
	 * @author zhangligang
	 * @version 2014年6月26日上午10:30:46
	 * @throws CustomException
	 * @throws CloneNotSupportedException
	 * @see
	 */
	private void recursionCopyFolder(Folder source, Folder target,
			HttpServletRequest request) throws CustomException,
			CloneNotSupportedException {
		// 复制目录下文档
		Document doc = new Document();
		doc.setFolderId(source.getId());
		List<Document> documents = docService.queryAll(doc);
		for (Document document : documents) {
			document.setFolderId(target.getId());
			this.copyDocTo(document, target, request);
		}
		// 复制目录下目录
		Folder folder = new Folder();
		folder.setParentFolderId(source.getId());
		List<Folder> folders = this.queryAll(folder);
		for (Folder subFolder : folders) {
			Folder newFolder = subFolder.clone();
			newFolder.setParentFolderId(target.getId());
			newFolder.setFolderPath(target.getFolderPath() + "/"
					+ newFolder.getFolderName());
			this.save(newFolder);
			this.recursionCopyFolder(subFolder, newFolder, request);
		}
		return;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public synchronized Folder cutDirTo(Folder sourcefolder, Folder targetfolder)
			throws ArchiveException {
		// 查询源目录信息
		Folder oldFolder = sourcefolder;
		try {
			// oldFolder.setId(folder.getId());
			// oldFolder = this.get(oldFolder);

			// Folder parentFolder = new Folder();
			// parentFolder.setId(folder.getParentFolderId());
			// parentFolder = this.get(parentFolder);
			oldFolder.setParentFolderId(targetfolder.getId());
			// 生成新目录名
			oldFolder.setFolderName(this.getNewFolderName(targetfolder.getId(),
					oldFolder.getFolderName()));
			oldFolder.setOldFolderPath(oldFolder.getFolderPath()+"/");
			oldFolder.setFolderPath(targetfolder.getFolderPath() + "/"
					+ oldFolder.getFolderName());
			// 更新目录
			this.update(oldFolder);

		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		} catch (Exception e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
		}
		return oldFolder;
	}

	@Override
	public Document getDocument(Document doc) throws ArchiveException {
		Document document = null;
		try {
			document = docService.get(doc);
		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}
		return document;
	}

	@Override
	public String getNewFolderName(Long parentFolderId, String name)
			throws ArchiveException {
		String newName = name;
		Folder targetFolder = new Folder();
		targetFolder.setParentFolderId(parentFolderId);
		targetFolder.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
		targetFolder.setDeleteFlag(0);
		List<Folder> folders;
		try {
			// 查询父文件夹下所有文件
			folders = this.queryAll(targetFolder);

			if (folders != null) {
				Map<String, Folder> map = new HashMap<String, Folder>();
				for (Folder f : folders) {
					map.put(f.getFolderName(), f);
				}
				// 文件夹名称如果重复，生成新名称
				for (int i = 0; map.get(newName) != null; i++) {
					newName = name + "(" + (i + 1) + ")";
					// newName += "(1)";
				}
			}
		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}
		return newName;
	}

	/**
	 * 方法描述：取得项目下文档模块（兼容点聚）绝对路径（不包含文件名）
	 * 
	 * @param request
	 * @return
	 * @author zhangligang
	 * @version 2014年7月2日下午2:48:41
	 * @see
	 */
	@Override
	public String getAbsoluteParentPath(HttpServletRequest request) {
		return request.getRealPath(File.separator) + File.separator
				+ Constants.DJ_UPLOAD_DIR + File.separator;
	}

	/**
	 * 方法描述：取得项目上下文绝对路径
	 * 
	 * @param request
	 * @return
	 * @author zhangligang
	 * @version 2014年7月2日下午3:02:50
	 * @see
	 */
	@Override
	public String getAbsoluteContextPath(HttpServletRequest request) {
		return request.getRealPath(File.separator);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer update(Folder folder) throws CustomException {
		
		String path = folder.getFolderPath().substring(0,
				folder.getFolderPath().lastIndexOf("/") + 1);
		folder.setFolderPath(path + folder.getFolderName());
		Integer i = super.update(folder);
		if (i > 0){
			folder.setFolderPath(folder.getFolderPath()+"/");
			folderDao.updateAllChildPath(folder);
		}
		return i;
	}

	/**
	 * @description 权限过滤文件夹
	 * @param Folder
	 *            folder 实体类
	 * @return List<Folder>
	 * @throws Exception
	 * @author weny
	 * @version 2014-07-09
	 */
	@Override
	public List<Folder> getFolderPermission(Folder folder) {
		return folderDao.getFolderPermission(folder);
	}

	@Override
	public String getNewFileName(Long folderId, String dmName, String ext)
			throws ArchiveException {

		return docService.getNewFileName(folderId, dmName, ext);
	}

	/**
	 * 方法描述：回收站清空
	 * 
	 * @param folder
	 * @author weny
	 * @version 2014年6月18日
	 * @throws CustomException
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int clearRecycl() throws CustomException {
		Document document = new Document();
		document.setDmInRecycle(1);
		document.setDeleteFlag(0);
		List<Document> doc = docService.queryAll(document);
		int count = 0;
		for (int i = 0; i < doc.size(); i++) {
			Document ment = doc.get(i);
			ment.setDeleteFlag(1);
			count = docService.update(ment);
		}
		Folder folder = new Folder();
		folder.setDmInRecycle(1);
		folder.setDeleteFlag(0);
		List<Folder> fol = this.queryAll(folder);
		for (int i = 0; i < fol.size(); i++) {
			Folder der = fol.get(i);
			der.setDeleteFlag(1);
			count = folderDao.update(der);
		}
		return count;
	}
	/**
	 * 方法描述：批量删除我的文档文件夹和文档
	 * 
	 * @param folder
	 * @throws ArchiveException
	 * @author 
	 * @version 2014年6月18日
	 * @see
	 */
	@Override
	public Integer bulkDelete(String idsdir,String idsdoc) throws ArchiveException  {
		// TODO Auto-generated method stub
		Integer count = 0;
		try {
			if(idsdir!=null && !idsdir.equals("")){
				Folder folder = new Folder();
				folder.setPrimaryKeys(idsdir.split(","));
				count = count+ this.delete(folder);
				String[] permissionId = idsdir.split(",");
				for (int i = 0; i < permissionId.length; i++) {
					Permission permission = new Permission();
					permission.setFolderId(Long.valueOf(permissionId[i]));
					List<Permission> list = permissionService
							.queryAll(permission);
					for (int j = 0; j < list.size(); j++) {
						permissionService.delectPermission(list.get(j));
					}
				}
			}
		 if(idsdoc!=null && !idsdoc.equals("")){
				Document document = new Document();
				document.setPrimaryKeys(idsdoc.split(","));
				//Relate relate = new Relate();
				//relate.setPrimaryKeys(idsdoc.split(","));
				//relateService.deleteRelateDM(relate);
				count = count+docService.delete(document);
		}
		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}
		return count;
	}
	/**
	 * 方法描述：获取归档目录下是否是机构管理员列表信息
	 * 
	 * @param folder
	 * @return
	 * @author 
	 * @version
	 * @throws ArchiveException
	 * @see
	 */
	@Override
	public Folder getFileDirDocsQuery(Folder folder) throws ArchiveException {
		folder.setCreateUser(null);
		// 如果参数中没有文件夹ID，既为查看根目录，应设置上级目录ID为0，做为查询条件
		if (folder.getId() == null) {
			folder.setParentFolderId(0L);
			folder.setFolderName("根目录");
			folder.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
		}
		try {
			folder = this.get(folder);
			if (folder == null) {
				return null;
			}
			AdminSide adminside = new AdminSide();
			adminside.setUserId(SystemSecurityUtils.getUser().getId());
			adminside.setIsChecked("1");
			adminside.setDeptId(folder.getCreateUserOrg());
			List<AdminSide> as = adminSideService.queryAll(adminside);
			if (as != null && as.size() > 0) {
				// 查询直接子目录
				Folder subdir = new Folder();
				subdir.setParentFolderId(folder.getId());
				subdir.setFolderType(folder.getFolderType());
				subdir.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
				subdir.setCreateUserOrg(SystemSecurityUtils.getUser()
						.getOrgId());
				subdir.setDeleteFlag(0);
				subdir.addOrderByFieldDesc("t.MODIFY_DATE");
				folder.setSubdirs(folderDao.queryAll(subdir));

				// 查询当前目录下文件
				Document doc = new Document();
				doc.setFolderId(folder.getId());
				doc.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
				doc.setDeleteFlag(0);
				if ("0".equals(folder.getFolderType())) {
					doc.setFileType(folder.getFolderType());
				}
				doc.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
				doc.addOrderByFieldDesc("t.MODIFY_DATE");
				folder.setDocuments(docService.queryAll(doc));
			} else {
				return null;
			}
		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		} catch (Exception e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}
		return folder;
	}
}