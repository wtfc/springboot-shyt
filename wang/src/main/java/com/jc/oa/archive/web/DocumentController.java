package com.jc.oa.archive.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.archive.ArchiveException;
import com.jc.oa.archive.domain.Document;
import com.jc.oa.archive.domain.Filing;
import com.jc.oa.archive.domain.Folder;
import com.jc.oa.archive.domain.Permission;
import com.jc.oa.archive.domain.Relate;
import com.jc.oa.archive.domain.Version;
import com.jc.oa.archive.domain.validator.DocumentValidator;
import com.jc.oa.archive.service.IArchiveFolderService;
import com.jc.oa.archive.service.IAudithisService;
import com.jc.oa.archive.service.IDocumentService;
import com.jc.oa.archive.service.IFilingService;
import com.jc.oa.archive.service.IPermissionService;
import com.jc.oa.archive.service.IRelateService;
import com.jc.oa.archive.service.IVersionService;
import com.jc.oa.archive.util.PermissionUtil;
import com.jc.system.CustomException;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;
import com.jc.system.security.util.UserUtils;

/**
 * @title GOA2.0源代码
 * @author 闻瑜
 * @version 2014-06-05
 */

@Controller
@RequestMapping(value = "/archive/document")
public class DocumentController extends BaseController {

	@Autowired
	private IDocumentService documentService;

	@Autowired
	private IAudithisService audithisService;

	@Autowired
	private IArchiveFolderService archiveFolderService;

	@Autowired
	private IPermissionService permissionService;

	@Autowired
	private IVersionService versionService;
	
	@Autowired
	private IRelateService relateService;

	@Autowired
	private IFilingService filingService;
	
	@org.springframework.web.bind.annotation.InitBinder("document")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new DocumentValidator());
	}

	public DocumentController() {
	}

	/**
	 * 分页查询方法
	 * 
	 * @param Document
	 *            document 实体类
	 * @param PageManager
	 *            page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文档信息表", operateFuncNm = "manageList", operateDescribe = "对OA_文档管理/知识管理_文档信息表进行查询操作")
	public PageManager manageList(Document document, PageManager page,HttpServletRequest request) {
		PageManager page_ = documentService.query(document, page);
		return page_;
	}

	/**
	 * 跳转方法
	 * 
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "manage.action")
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文档信息表", operateFuncNm = "manage", operateDescribe = "对OA_文档管理/知识管理_公共文档进行跳转操作")
	public String manage(HttpServletRequest request) throws Exception {
		return "archive/document/document";
	}

	/**
	 * 删除方法
	 * @param Document
	 *            document 实体类
	 * @param String
	 *            ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文档信息表", operateFuncNm = "deleteByIds", operateDescribe = "对OA_文档管理/知识管理_文档信息表进行删除")
	public Map<String, Object> deleteByIds(Document document, String ids,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		document.setPrimaryKeys(ids.split(","));
		int num = documentService.delete(document);
		try {
			Relate relate = new Relate();
			relate.setPrimaryKeys(ids.split(","));
			relateService.deleteRelateDM(relate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
		if (num > 0) {
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				Document doc = new Document();
				doc.setId(Long.valueOf(id));
				Document ment = documentService.get(doc);
				audithisService.audithis(request, Long.valueOf(id),
						ment.getDmName(), Constants.ARC_AUDITHIS_DATATYPE_DOC,
						Constants.ARC_AUDITHIS_DELETE, "删除文件" + getModelValue(ment.getModel()) + id);
			}
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_SYS_005"));
		}
		return resultMap;
	}

	private String getModelValue(Integer model) {
		String type = "";
		if(0 == model) {
			type = "(公共文档)";
		} else if(1 == model){
			type = "(我的文档)";
		}else if(4 == model){
			type = "(归档目录)";
		}
		return type;
	}
	/**
	 * 保存方法
	 * 
	 * @param Document
	 *            document 实体类
	 * @param BindingResult
	 *            result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文档信息表", operateFuncNm = "save", operateDescribe = "对OA_文档管理/知识管理_文档信息表进行新增操作")
	public Map<String, Object> save(Document document,
			BindingResult result, HttpServletRequest request) throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();//validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request);
		if (resultMap.size() > 0) {
			// return resultMap;
		}
		Document checkDoc = new Document();
		Long dId = document.getId();
		checkDoc.setDmName(document.getDmName());
		checkDoc.setFolderId(document.getFolderId());
		checkDoc.setDmSuffix(document.getDmSuffix());
		checkDoc.setDeleteFlag(0);
		checkDoc.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
		checkDoc.setFileType(Constants.ARC_DOC_FILETYPE_DOC);
		checkDoc.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
		try {
			Document d = documentService.get(checkDoc);
			if (d != null) {
				// 如果文档已被锁定，不能操作
				if (d.getDmLockStatus().equals(
						Constants.ARC_DOC_LOCKSTATUS_LOCKED)) {
					resultMap.put("success", "false");
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
							MessageUtils.getMessage("JC_OA_ARCHIVE_033"));
					return resultMap;
				}
				if (dId == null || d.getId().longValue() != dId.longValue()) {
					resultMap.put("success", "false");
					resultMap.put(GlobalContext.RESULT_LABELERRORMESSAGE,
							MessageUtils.getMessage("JC_OA_ARCHIVE_009",
									new String[] { document.getDmName() + "."
											+ document.getDmSuffix() }));
					return resultMap;
				}
			}
			Folder queryFolder = new Folder();
			queryFolder.setId(document.getFolderId());
			Folder parentfolder = archiveFolderService.get(queryFolder);
			if (parentfolder != null) {
				document.setDmDir(parentfolder.getFolderPath());
			}
			if (dId == null) {
				document.setSeq(documentService.getSeq(document));
				// 增加版本号
				Version v = versionService.createVersion(document);
				document.setDmInRecycle(0);
				document.setFileType("0");
				document.setCurrentVersion(v.getCurrentVersion());
				documentService.save(document);
				v.setBackUpId(document.getId());
				v.setVersionDesc("新建文档" + document.getDmName() + " 版本号："
						+ v.getCurrentVersion());
				versionService.save(v);
				this.switchVersion(v,request);
				audithisService.audithis(request, document.getId(),
						document.getDmName(),
						Constants.ARC_AUDITHIS_DATATYPE_DOC,
						Constants.ARC_AUDITHIS_NEWUPDOWN, "新建文档 " + getModelValue(document.getModel()));
				resultMap.put("success", "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
						MessageUtils.getMessage("JC_SYS_001"));
			} else {
				// 修改
				// document.setSeq(d.getSeq());
				// 根据前台选择确定是否增加版本
				/*
				 * if(生成新版本) {
				 * 
				 * }
				 */
				Version v = versionService.createVersion(document);
				v.setBackUpId(document.getId());
				v.setVersionDesc("更新文档" + document.getDmName() + " 版本号："
						+ v.getCurrentVersion());
				document.setFileType("0");
				document.setDmInRecycle(0);
				document.setCurrentVersion(v.getCurrentVersion());
				document.setModifyDate(new Date());
				versionService.save(v);
				this.switchVersion(v,request);
				audithisService.audithis(request, document.getId(),
						document.getDmName(),
						Constants.ARC_AUDITHIS_DATATYPE_DOC,
						Constants.ARC_AUDITHIS_EDIT, "更新文档 " + getModelValue(document.getModel()));
				documentService.update(document);
				resultMap.put("success", "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
						MessageUtils.getMessage("JC_SYS_003"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	 * 修改方法
	 * @param Document
	 *            document 实体类
	 * @return Integer 更新的数目
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "update.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文档信息表", operateFuncNm = "update", operateDescribe = "对OA_文档管理/知识管理_文档信息表进行更新操作")
	public Map<String, Object> update(Document document, BindingResult result,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		try {
			Map<String, Object> map = this.selectDoc(document, request);
			if (map.get("success").equals("error")) {
				resultMap.putAll(map);
				return resultMap;
			} else {
				Document oldDoc = new Document();
				oldDoc = (Document) map.get("document");
				// 如果文档已被锁定，不能操作
				if (oldDoc.getDmLockStatus().equals(
						Constants.ARC_DOC_LOCKSTATUS_LOCKED)) {
					resultMap.put("success", "false");
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
							MessageUtils.getMessage("JC_OA_ARCHIVE_015"));
					return resultMap;
				}
				Document checkDoc = new Document();
				checkDoc.setDmName(document.getDmName());
				checkDoc.setFolderId(document.getFolderId());
				checkDoc.setDeleteFlag(0);
				checkDoc.setDmInRecycle(0);
				checkDoc.setFileType(document.getFileType());
				List<Document> documents = documentService.queryAll(checkDoc);
				if (documents != null) {
					for (Document doc : documents) {
						if (doc.getId().equals(document.getId())) {
							documents.remove(doc);
							break;
						}
					}
					if (documents.size() > 0) {
						resultMap.put("success", "false");
						resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
								MessageUtils.getMessage("JC_OA_ARCHIVE_009",
										new String[] { document.getDmName() }));
						return resultMap;
					}
				}
				Integer flag = documentService.update(document);
				if (flag == 1) {
					audithisService.audithis(request, document.getId(),
							document.getDmName(),
							Constants.ARC_AUDITHIS_DATATYPE_DOC,
							Constants.ARC_AUDITHIS_EDIT, "编辑或重命名"
									+ getModelValue(document.getModel()));
					resultMap.put("success", "true");
					resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
							MessageUtils.getMessage("JC_SYS_001"));
				} else {
					resultMap.put("success", "false");
					resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
							MessageUtils.getMessage("JC_OA_COMMON_014"));
				}
			}
			String token = getToken(request);
			resultMap.put(GlobalContext.SESSION_TOKEN, token);
			return resultMap;
		} catch (Exception ex) {
			ex.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}
	}

	/**
	 * 获取单条记录方法
	 * @param Document
	 *            document 实体类
	 * @return Document 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "get.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文档信息表", operateFuncNm = "get", operateDescribe = "对OA_文档管理/知识管理_文档信息表进行单条查询操作")
	public Document get(Document document,HttpServletRequest request) throws Exception {
		document = documentService.get(document);
		Folder folder = new Folder();
		folder.setId(document.getFolderId());
		folder = archiveFolderService.get(folder);
		document.setDmDir(folder.getFolderPath());
		document.setMaxVersion(versionService.getMaxVersion(document.getId()));
		return document;
	}

	/**
	 * 跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author weny
	 * @version 2014-06-16
	 */
	@RequestMapping(value = "manageMyCollectionFolder.action")
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_收藏夹信息", operateFuncNm = "manageMyCollectionFolder", operateDescribe = "对OA_文档管理/知识管理_收藏夹信息进行跳转操作")
	public String manageMyCollectionFolder(HttpServletRequest request) throws Exception {
		return "archive/document/myCollectionFolder";
	}

	/**
	 * 跳转方法
	 * @return String 收藏夹信息初始化
	 * @throws Exception
	 * @author weny
	 * @version 2014-06-16
	 */
	@RequestMapping(value = "intoMyCollectionFolder.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_收藏夹信息", operateFuncNm = "intoMyCollectionFolder", operateDescribe = "对OA_文档管理/知识管理_收藏夹信息进行初始化操作")
	public Map<String, Object> intoMyCollectionFolder(Document ment,
			PageManager page,HttpServletRequest request) throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 默认排序
		if (StringUtils.isEmpty(ment.getOrderBy())) {
			ment.addOrderByFieldDesc("t.CREATE_DATE");
		}
		ment.setCollectId(SystemSecurityUtils.getUser().getId());
		ment.setDmInRecycle(0);
		ment.setFileType(Constants.ARC_DOC_FILETYPE_FAVOR);
		List<Document> list = documentService.queryAll(ment);
		for (int i = 0; i < list.size(); i++) {
			Document document = list.get(i);
			document.setCollectName(UserUtils.getUser(document.getCreateUser())
					.getDisplayName());
		}
		resultMap.put("success", "true");
		resultMap.put("list", list);
		return resultMap;
	}

	/**
	 * 查询收藏表
	 * 
	 * @return String 收藏表信息是否存在
	 * @throws Exception
	 * @author weny
	 * @version 2014-06-16
	 */
	@RequestMapping(value = "selectCollection.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_收藏表信息", operateFuncNm = "selectCollection", operateDescribe = "对OA_文档管理/知识管理_收藏表信息查询操作")
	public Map<String, Object> selectCollection(Document ment, String id,HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Document oldment = new Document();
		oldment.setId(Long.valueOf(id));
		Document oldDoc = documentService.get(oldment);
		Map<String, Object> map = this.selectDoc(oldDoc, request);
		if (map.get("success").equals("error")) {
			resultMap.putAll(map);
			return resultMap;
		} else {
			Document documentCheck = documentService.get(ment);
			ment.setBackUpId(Long.valueOf(id));
			ment.setFileType(Constants.ARC_DOC_FILETYPE_FAVOR);
			ment.setCollectId(SystemSecurityUtils.getUser().getId());
			ment.setDmInRecycle(0);
			ment.setCurrentVersion(documentCheck.getCurrentVersion());
			ment.setId(null);
			Document document = documentService.get(ment);
			if (document != null) {
				resultMap.put("success", "true");
			}
			return resultMap;
		}
	}

	/**
	 * 收藏信息变更
	 * 
	 * @return String 收藏表信息变更
	 * @throws Exception
	 * @author weny
	 * @version 2014-06-17
	 */
	@RequestMapping(value = "updateCollection.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_收藏表信息变更", operateFuncNm = "updateCollection", operateDescribe = "对OA_文档管理/知识管理_收藏表信息变更操作")
	public Map<String, Object> updateCollection(Document ment, String id,int currentVersion,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ment.setCurrentVersion(currentVersion);
		int count = documentService.updateCollection(ment, id);
		if (count > 0) {
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_SYS_054"));
		}
		Document doc = new Document();
		doc.setId(Long.valueOf(id));
		Document document = documentService.get(doc);
		audithisService.audithis(request, Long.valueOf(id),
				document.getDmName(), Constants.ARC_AUDITHIS_DATATYPE_DOC,
				Constants.ARC_AUDITHIS_COLLECT, "编辑收藏文档" + getModelValue(document.getModel()));
		return resultMap;
	}

	/**
	 * 收藏信息新增
	 * 
	 * @return String 收藏表信息新增
	 * @throws Exception
	 * @author weny
	 * @version 2014-06-17
	 */
	@RequestMapping(value = "insertCollection.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_收藏表信息新增", operateFuncNm = "insertCollection", operateDescribe = "对OA_文档管理/知识管理_收藏表信息新增操作")
	public Map<String, Object> insertCollection(Document ment, String id,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int count = documentService.insertCollection(ment, id);
		if (count > 0) {
			Document doc = new Document();
			doc.setId(Long.valueOf(id));
			Document document = documentService.get(doc);
			audithisService.audithis(request, ment.getId(),
					document.getDmName(), Constants.ARC_AUDITHIS_DATATYPE_DOC,
					Constants.ARC_AUDITHIS_COLLECT, "收藏文件"
							+ getModelValue(document.getModel()));
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_SYS_054"));
		}
		return resultMap;
	}
	

	/**
	 * 删除方法
	 * 
	 * @param Document
	 *            document 实体类
	 * @param String
	 *            ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "deleteCollection.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文档信息表", operateFuncNm = "deleteCollection", operateDescribe = "对OA_文档管理/知识管理_文档信息表进行删除")
	public Map<String, Object> deleteCollection(Document document, String ids,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String[] id = ids.split(",");
		int num = 0;
		for (int i = 0; i < id.length; i++) {
			document.setId(Long.valueOf(id[i]));
			document.setDeleteFlag(1);
			num = documentService.update(document);
		}
		if (num > 0) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_SYS_005"));
		}
		for (int i = 0; i < id.length; i++) {
			Document doc = new Document();
			doc.setId(Long.valueOf(id[i]));
			doc.setDeleteFlag(1);
			Document ment = documentService.get(doc);
			audithisService.audithis(request, Long.valueOf(id[i]),
					ment.getDmName(), Constants.ARC_AUDITHIS_DATATYPE_DOC,
					Constants.ARC_AUDITHIS_DELETE, "删除收藏文档");
		}
		return resultMap;
	}

	/**
	 * 修改方法
	 * 
	 * @param Document
	 *            document 实体类
	 * @return Integer 更新的数目
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "lock.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文档信息表", operateFuncNm = "lock", operateDescribe = "对OA_文档管理/知识管理_文档信息表进行锁定、解锁操作")
	public Map<String, Object> lock(Document document, BindingResult result,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		try {

			Integer flag = documentService.update(document);
			if (flag == 1) {
				document=documentService.get(document);
				audithisService.audithis(request, document.getId(),
						document.getDmName(),
						Constants.ARC_AUDITHIS_DATATYPE_DOC,
						Constants.ARC_AUDITHIS_EDIT, "1".equals(document.getDmLockStatus()) ? "锁定文件" : "解除锁定文件");
				resultMap.put("success", "true");
				resultMap.put("modifyDate", DateFormatUtils.format(document.getModifyDate(),"yyyy-MM-dd HH:mm:ss"));
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
						MessageUtils.getMessage("JC_SYS_001"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}
		return resultMap;
	}

	/**
	 * 分页查询方法
	 * 
	 * @param Document
	 *            document 实体类
	 * @param PageManager
	 *            page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "manageVersionList.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文档版本信息表", operateFuncNm = "manageVersionList", operateDescribe = "对OA_文档管理/知识管理_文档版本信息表进行查询操作")
	public PageManager manageVersionList(Version version, PageManager page,HttpServletRequest request) {
		PageManager page_ = versionService.query(version, page);
		return page_;
	}

	/**
	 * 分页查询方法
	 * 
	 * @param Document
	 *            document 实体类
	 * @param PageManager
	 *            page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-07-02
	 */
	@RequestMapping(value = "manageByRelateList.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文档信息表", operateFuncNm = "manageByRelateList", operateDescribe = "根据文档id获取未关联文档的集合进行查询操作")
	public PageManager manageByRelateList(Document document, PageManager page,HttpServletRequest request) {
		PageManager page_ = null;
		try {
			if(document.getCreateDateEnd() != null){
				document.setCreateDateEnd(DateUtils.fillTime(document.getCreateDateEnd()));
			}
			document.setFileType("0");
			document.setDmInRecycle(0);
			document.setDeleteFlag(0);
			document.setModel(0);
			//document.setCreateUserDept(UserUtils.getUser(SystemSecurityUtils.getUser().getId()).getDeptId());
			document.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
			if(document.getOrderBy() == null || "".equals(document.getOrderBy())) {
				document.setOrderBy("t.CREATE_DATE desc");
			} else {
				document.setOrderBy("t." + document.getOrderBy());
				if(document.getOrderBy().indexOf("createDate") > -1) {
					document.setOrderBy(document.getOrderBy().replaceAll("createDate", "CREATE_DATE"));
				}  
			}
			page_ = documentService.queryByRelate(document, page);
		} catch (Exception e) {
			System.out.print(e);
		}
		return page_;
	}

	/**
	 * 判断关联文档有没有浏览权限或已被删除
	 * @param Document
	 *            document 实体类
	 * @return Integer 更新的数目
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "check.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_关联文档判断", operateFuncNm = "check", operateDescribe = "对OA_文档管理/知识管理_关联文档进行判断操作")
	public Map<String, Object> check(Document document,HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		document.setDmInRecycle(0);
		Document ment = documentService.get(document);
		if (ment == null) {
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_SYS_033"));
			return resultMap;
		}
		Folder folder = new Folder();
		folder.setId(ment.getFolderId());
		folder.setDmInRecycle(0);
		Folder list = archiveFolderService.get(folder);
		if (list == null || checkDelectFolder(ment.getFolderId()) == 0) {
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_SYS_033"));
			return resultMap;
		}
		Permission permission = new Permission();
		permission.setFolderId(list.getId());
		permission.setUserId(SystemSecurityUtils.getUser().getId());
		permission.setDeptId(SystemSecurityUtils.getUser().getDeptId());
		List<Permission> listPermission = permissionService
				.queryPermission(permission);
		Folder folderPermission = PermissionUtil
				.permissionValue(listPermission);
		if (folderPermission.isPermView()
				|| ment.getCreateUser().longValue() == SystemSecurityUtils
						.getUser().getId().longValue()) {
			resultMap.put("success", "true");
		} else {
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_016"));
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
		folderParent = archiveFolderService.get(folderParent);
		if (folderParent == null) {
			num = 0;
		} else if (folderParent.getParentFolderId().longValue() == 0) {
			num = 1;
		} else {
			return checkDelectFolder(folderParent.getParentFolderId());
		}
		return num;
	}

	/**
	 * 判断关联文档有没有浏览权限或已被删除
	 * 
	 * @param Document
	 *            document 实体类
	 * @return Integer 更新的数目
	 * @author zhangligang
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "switchVersion.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_切换文档版本", operateFuncNm = "switchVersion", operateDescribe = "对OA_文档管理/知识管理_切换文档版本")
	public Map<String, Object> switchVersion(Version version,HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long verId = version.getId();
		// 查询选中的版本
		version = versionService.get(version);
		Document doc = new Document();
		// 查询当前文档信息
		doc.setId(version.getBackUpId());
		doc = documentService.get(doc);
		// 更新文档信息
		doc.setCurrentVersion(version.getCurrentVersion());
		doc.setPhysicalPath(version.getPhysicalPath());
		doc.setDmName(version.getDmName());
		doc.setDmAlias(version.getDmAlias());
		doc.setModifyDateNew(new Date());

		doc.setFolderId(version.getFolderId()); /* 文件夹ID */
		doc.setDocState(version.getDocState()); /* 0 暂存 1 已发布 2 审核中 */
		doc.setFileType(version.getFileType()); /*
												 * 文件存放类型：0文档 1 知识 2 链接 3 收藏
												 */
		doc.setContentType(version.getContentType()); /*
													 * 文档内容类型 0 未知 1 word 2
													 * excel 3 ppt
													 */
		doc.setModel(version.getModel()); /* 0 公共文档 1 我的文档 */
		doc.setDmLink(version.getDmLink()); /* 链接路径:存放归档信息的URL */
		doc.setDmName(version.getDmName()); /* 文档管理_文档名称 */
		doc.setDmAlias(version.getDmAlias()); /* 文档在服务器中的名称已当前时间的long值名称 */
		doc.setDmLockStatus(version.getDmLockStatus()); /*
														 * 文档管理_文档锁定状态(0-
														 * 未锁定;1-锁定)
														 */
		doc.setPhysicalPath(version.getPhysicalPath()); /* 文件物理地址 */
		doc.setDmDir(version.getDmDir()); /* 文档所在目录位置 */
		doc.setDmType(version.getDmType()); /* 文档管理_文档类型(字典项) */
		doc.setDmSuffix(version.getDmSuffix()); /* 文档管理_文档历史后缀名 */
		doc.setWeight(version.getWeight()); /**/
		doc.setKmTitle(version.getKmTitle()); /* 知识标题 */
		doc.setDmInRecycle(version.getDmInRecycle()); /*
													 * 文档管理_回收站状态(0-否 ;
													 * 1-是级联;2-不是级联)
													 */
		doc.setKeyWords(version.getKeyWords()); /* 关键字(文档/知识) */
		doc.setPermissionValue(version.getPermissionValue()); /*
															 * 针对知识管理共三位 111 复制
															 * 打印 下载
															 */
		doc.setAuthor(version.getAuthor()); /* 作者 */
		doc.setDmSize(version.getDmSize()); /* 文档大小 */
		doc.setSeq(version.getSeq()); /* 编号 */
		doc.setIsValid(version.getIsValid()); /* 是否过期 0未过期 1 过期 */
		doc.setPiId(version.getPiId()); /* 流程ID */
		if (version.getOpenDate() != null) {
			doc.setOpenDate(version.getOpenDate()); /* 知识发布时间 */
		}
		if (version.getOpenDateBegin() != null) {
			doc.setOpenDateBegin(version.getOpenDateBegin()); /* 知识发布时间开始 */
		}
		if (version.getOpenDateEnd() != null) {
			doc.setOpenDateEnd(version.getOpenDateEnd()); /* 知识发布时间结束 */
		}
		doc.setKmEndTime(version.getKmEndTime()); /* 知识管理_结束日期 */
		doc.setKmKeepTime(version.getKmKeepTime()); /* 知识管理_知识时限天数(字典) */
		doc.setKmRemind(version.getKmRemind()); /*
												 * 知识管理_通知提醒(0-不提醒;1-邮件;2 -短信)
												 */
		doc.setKmContent(version.getKmContent()); /* 知识管理_知识内容 */
		doc.setKmClickNum(version.getKmClickNum()); /* 知识管理_点击次数 */

		documentService.update(doc);

		// 更新版本信息：一、此文档全部版本设置未启用，二、此文档当前版本设置启用
		Version allVersion = new Version();
		allVersion.setBackUpId(doc.getId());
		List<Version> versions = versionService.queryAll(allVersion);
		for (Version v : versions) {
			v.setIsCurrentUsed(0);
			versionService.update(v);
		}

		version = new Version();
		version.setId(verId);
		version = versionService.get(version);
		version.setIsCurrentUsed(1);
//		version.setModifyDateNew(new Date());
		versionService.update(version);

		resultMap.put("success", "true");
		resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
				MessageUtils.getMessage("JC_SYS_054"));
		return resultMap;
	}

	/**
	 * 分页查询方法
	 * 
	 * @param Document
	 *            document 实体类
	 * @param PageManager
	 *            page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 * @throws Exception
	 */
	@RequestMapping(value = "filingList.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_附件信息表", operateFuncNm = "filingList", operateDescribe = "对OA_文档管理/知识管理_附件信息表进行查询操作")
	public Map<String, Object> filingList(Filing filing, PageManager page,
			Long id,HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		filing.setDocumentId(id);
		filing.setId(null);
		filing.setFormType("1,2");
		Document d = new Document();
		d.setId(id);
		d = documentService.get(d);
		if(d == null) {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_ARCHIVE_023"));
			return resultMap;
		}
		Long folderId = d.getFolderId();
		Permission permission = new Permission();
		permission.setFolderId(folderId);
		permission.setUserId(SystemSecurityUtils.getUser().getId());
		permission.setDeptId(SystemSecurityUtils.getUser().getDeptId());
		permission.setOrgId(SystemSecurityUtils.getUser().getOrgId());
		List<Permission> listPermission = permissionService
				.queryPermission(permission);
		if(listPermission == null || listPermission.size() < 1) {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_ARCHIVE_016"));
		} else {
			List<Filing> listFiling = documentService.queryFiling(filing);
			Document doc = new Document();
			doc.setId(id);
			doc = documentService.get(doc);
			Folder folder = new Folder();
			folder.setId(folderId);
			folder = archiveFolderService.get(folder);
			User u = UserUtils.getUser(doc.getCreateUser());
			System.out.println(u);
			doc.setDmDir(folder.getFolderPath());
			User user = UserUtils.getUser(doc.getCreateUser());
			if(user != null) {
				doc.setOwner(user.getDisplayName());
			} else {
				doc.setOwner("管理员");
			}
			resultMap.put("listFiling", listFiling);
			resultMap.put("success", "true");
			resultMap.put("doc", doc);
		}
		audithisService.audithis(request, id,
				d.getDmName(),
				Constants.ARC_AUDITHIS_DATATYPE_DOC,
				Constants.ARC_AUDITHIS_VIEW, "浏览文档");
		return resultMap;
	}

	/**
	 * 方法描述：附件下载check
	 * 
	 * @author weny
	 * @version 2014年7月10日
	 * @throws ArchiveException
	 * @see
	 */
	@RequestMapping(value = "filingDownloadCheck.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_附件文档", operateFuncNm = "filingDownloadCheck", operateDescribe = "对OA_文档管理/知识管理_附件文档")
	public boolean filingDownloadCheck(Long id,HttpServletResponse response, HttpServletRequest request) throws Exception {
		return documentService.downLoadCheck(id, request);
		
	}
	/**
	 * 方法描述：附件下载
	 * @author weny
	 * @version 2014年7月10日
	 * @throws ArchiveException
	 * @see
	 */
	@RequestMapping(value = "filingDownload.action")
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_附件文档", operateFuncNm = "filingDownload", operateDescribe = "对OA_文档管理/知识管理_附件文档")
	public void filingDownload(Long id,HttpServletResponse response,HttpServletRequest request) throws ArchiveException {
		try {
			documentService.downLoad(id, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 方法描述：查看归档表单
	 * @param filing
	 * @return
	 * @throws ArchiveException
	 * @author zhangligang
	 * @version  2014年7月10日下午7:06:19
	 * @see
	 */
	@RequestMapping(value = "getFormContent.action")
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_附件文档", operateFuncNm = "getFormContent", operateDescribe = "对OA_文档管理/知识管理_查看归档表单")
	public String getFormContent(Filing filing,Model model,HttpServletRequest request) throws ArchiveException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Document d = new Document();
		d.setId(filing.getDocumentId());
		try {
			d = documentService.get(d);
			if(d == null) {
				//has bean delete 表示归档的文档已被删除,执行error回调函数
				return "has bean delete";
			}
		} catch (CustomException e1) {
			// TODO Auto-generated catch block
			log.error(e1.getMessage());
		}
		filing.setFileType(Constants.ARC_ARCHIVE_FILETYPE_FORM);
		List<Filing> listFiling;
		try {
			listFiling = documentService.queryFiling(filing);
			if (listFiling != null && listFiling.size() > 0) {
				model.addAttribute("formContent", listFiling.get(0).getFormContent());
				resultMap.put("filing", listFiling.get(0));
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			}
			
		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}
//		return resultMap;
		return "archive/document/docFormView";
	}
	
	
	/**
	 * 查看归档正文
	 * 
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "viewContent.action")
	@ActionLog(operateModelNm = "OA_文档管理/归档信息", operateFuncNm = "viewContent", operateDescribe = "查看归档的正文")
	public ModelAndView viewContent(String id , HttpServletRequest request) throws Exception {
		ModelAndView v = new ModelAndView("archive/document/viewContent");
		return v;
	}
	/**
	 * 查看归档正文
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "getContent.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/归档信息", operateFuncNm = "getContent", operateDescribe = "查看归档的正文")
	public Filing getContent(Filing filing , HttpServletRequest request) throws Exception {
		Filing f = new Filing();
		try {
			f = filingService.get(filing);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
	}
	
	/**
	 * 销毁归档文件
	 * 
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author weny
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "deleteFiling.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/销毁归档文件", operateFuncNm = "deleteFiling", operateDescribe = "销毁归档文件")
	public Map<String, Object> deleteFiling(Long id , HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Document doc = new Document();
		doc.setId(id);
		Document ment = documentService.get(doc);
		if(ment == null) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_023"));
		} else {
			documentService.deleteFiling(id, request);
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_SYS_005"));
			audithisService.audithis(request,id,
					ment.getDmName(), Constants.ARC_AUDITHIS_DATATYPE_DOC,
					Constants.ARC_AUDITHIS_DELETE, "删除文件" + getModelValue(Integer.parseInt(ment.getFileType())));
		}
		return resultMap;
	}
	/**
	* @description 文档默认跳转方法
	* @param HttpServletRequest request
	* @return /WebRoot/WEB-INF/web/archive/document/document.jsp
	* @throws Exception
	* @author 吕桢卓
	* @version  2014-09-22 
	*/
	@RequestMapping(value="documentjump.action")
	@ActionLog(operateModelNm="文档管理",operateFuncNm="documentjump",operateDescribe="文档管理默认跳转")
	public String personalOfficeSkip(HttpServletRequest request) throws Exception{
		return "archive/document/document"; 
	}
	
	/**
	 * 查询文件数据
	 * 
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-09-28
	 */
	@RequestMapping(value = "selectDoc.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/查询文件", operateFuncNm = "selectDoc", operateDescribe = "查询文件")
	public Map<String, Object> selectDoc(Document document,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Document oldDoc = new Document();
		oldDoc.setId(document.getId());
		oldDoc.setDeleteFlag(0);
		oldDoc.setDmInRecycle(0);
		oldDoc = documentService.get(oldDoc);
		if (oldDoc != null) {
			oldDoc.setMaxVersion(versionService.getMaxVersion(oldDoc.getId()));
			if (oldDoc.getFolderId() != null) {
				Folder folder = new Folder();
				folder.setId(oldDoc.getFolderId());
				folder.setDeleteFlag(0);
				folder.setDmInRecycle(0);
				folder = archiveFolderService.get(folder);
				if (folder == null) {
					resultMap.put(GlobalContext.RESULT_SUCCESS, "error");
					resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
							MessageUtils.getMessage("JC_OA_ARCHIVE_034"));
					return resultMap;
				}
			}
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put("document", oldDoc);
			return resultMap;
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "error");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_035"));
			return resultMap;
		}
	}
	/**
	 * 查询文件数据
	 * 
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author lvzz
	 * @version 2014-10-15
	 */
	@RequestMapping(value = "checkLock.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/查询文档是否被锁定", operateFuncNm = "checkLock", operateDescribe = "查询文档是否被锁定")
	public boolean checkLock(Long id, HttpServletRequest request)
			throws Exception {
		Document oldDoc = new Document();
		oldDoc.setId(id);
		//oldDoc.setDeleteFlag(0);
		//oldDoc.setDmInRecycle(0);
		oldDoc.setDmLockStatus("0");
		oldDoc = documentService.get(oldDoc);
		if (oldDoc == null) {
			return false;
		}
		return true;
	}
	/**
	 * 查询文件数据
	 * 
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author lvzz
	 * @version 2014-10-15
	 */
	@RequestMapping(value = "checkDocExist.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/查询文档是否被锁定", operateFuncNm = "checkDocExist", operateDescribe = "查看文档是否被删除")
	public boolean checkDocExist(Long id, HttpServletRequest request)
			throws Exception {
		Document oldDoc = new Document();
		oldDoc.setId(id);
		oldDoc.setDeleteFlag(0);
		oldDoc.setDmInRecycle(0);
		//oldDoc.setDmInRecycle(0);
		oldDoc = documentService.get(oldDoc);
		if (oldDoc == null) {
			return true;
		}
		return false;
	}
}