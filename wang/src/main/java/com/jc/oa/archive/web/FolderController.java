package com.jc.oa.archive.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.archive.ArchiveException;
import com.jc.oa.archive.domain.Document;
import com.jc.oa.archive.domain.Folder;
import com.jc.oa.archive.domain.Permission;
import com.jc.oa.archive.domain.Recycle;
import com.jc.oa.archive.domain.validator.FolderValidator;
import com.jc.oa.archive.service.IArchiveFolderService;
import com.jc.oa.archive.service.IAudithisService;
import com.jc.oa.archive.service.IPermissionService;
import com.jc.system.CustomException;
import com.jc.system.common.util.CharConventUtils;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;
import com.jc.system.security.util.UserUtils;

/**
 * @title GOA2.0源代码
 * @author
 * @version 2014-06-05
 */

@Controller
@RequestMapping(value = "/archive/folder")
public class FolderController extends BaseController {

	@Autowired
	private IArchiveFolderService folderService;

	@Autowired
	private IAudithisService audithisService;

	@Autowired
	private IPermissionService permissionService;

	@org.springframework.web.bind.annotation.InitBinder("folder")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new FolderValidator());
	}

	public FolderController() {
	}

	/**
	 * 分页查询方法
	 * 
	 * @param Folder
	 *            folder 实体类
	 * @param PageManager
	 *            page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息", operateFuncNm = "manageList", operateDescribe = "对OA_文档管理/知识管理_文件夹信息进行查询操作")
	public PageManager manageList(Folder folder, PageManager page,
			HttpServletRequest request) {
		PageManager page_ = folderService.query(folder, page);
		return page_;
	}

	/**
	 * 跳转方法
	 * 
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 * @throws ArchiveException
	 */
	@RequestMapping(value = "manage.action")
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息", operateFuncNm = "manage", operateDescribe = "对OA_文档管理/知识管理_公共文档进行跳转操作")
	public String manage(Model model,HttpServletRequest request)
			throws ArchiveException {
		// 公共文档根目录为Root目录
		Folder folder = new Folder();
		folder.setFolderType(Constants.ARC_FOLDER_TYPE_PUB_DOC);
		folder.setCreateUser(null);
		folder.setFolderName("根目录");
		folder.setParentFolderId(0L);
		folder.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());   
		try {
			folder = this.get(folder, request);
			// 若无公共文档根目录，创建之！
			if (folder == null) {
				folder = new Folder();
				folder.setFolderType(Constants.ARC_FOLDER_TYPE_PUB_DOC);
				folder.setParentFolderId(0L);
				folder.setKmAppFlag("0");
				folder.setFolderName("根目录");
				folder.setFolderPath("/根目录");
				folder.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());   
				folder.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
				folderService.save(folder);
			}

		} catch (Exception e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}
		model.addAttribute("folder", folder);
		//String token = super.getToken(request);
        //model.addAttribute(GlobalContext.SESSION_TOKEN, token);
		return "archive/document/pub_doc";
	}

	/**
	 * 跳转方法
	 * 
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 * @throws ArchiveException
	 */
	@RequestMapping(value = "manageFile.action")
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_归档信息", operateFuncNm = "manageFile", operateDescribe = "对OA_文档管理/知识管理_归档文档进行跳转操作")
	public String manageFile(Model model,HttpServletRequest request)
			throws ArchiveException {
		// 公共文档根目录为Root目录
		Folder folder = new Folder();
		folder.setFolderType(Constants.ARC_FOLDER_TYPE_FILE_DOC);
		folder.setCreateUser(null);
		folder.setFolderName("根目录");
		folder.setCreateUserOrg(UserUtils.getUser(
				SystemSecurityUtils.getUser().getId()).getOrgId());
		folder.setParentFolderId(0L);

		try {
			folder = this.get(folder, request);
			// 若无公共文档根目录，创建之！
			if (folder == null) {
				folder = new Folder();
				folder.setFolderType(Constants.ARC_FOLDER_TYPE_FILE_DOC);
				folder.setParentFolderId(0L);
				folder.setKmAppFlag("0");
				folder.setFolderName("根目录");
				folder.setFolderPath("/根目录");
				folder.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
				folder.setCreateUserOrg(UserUtils.getUser(
						SystemSecurityUtils.getUser().getId()).getOrgId());
				folderService.save(folder);
			}

		} catch (Exception e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}
		model.addAttribute("folder", folder);
		String token = super.getToken(request);
        model.addAttribute(GlobalContext.SESSION_TOKEN, token);
		return "archive/document/file_doc";
	}

	/**
	 * 跳转方法
	 * 
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "manageMydoc.action")
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息", operateFuncNm = "manageMydoc", operateDescribe = "对OA_文档管理/知识管理_我的文档进行跳转操作")
	public String manageMydoc(Model model,HttpServletRequest request)
			throws Exception {
		// 我的文档根目录为 "用户名_Root"目录
		Folder folder = new Folder();
		folder.setFolderType(Constants.ARC_FOLDER_TYPE_MY_DOC);
		folder.setCreateUser(SystemSecurityUtils.getUser().getId());
		folder.setParentFolderId(0L);
		try {
			folder = this.get(folder, request);
			// 若无文档根目录，创建之！
			if (folder == null) {
				folder = new Folder();
				folder.setFolderType(Constants.ARC_FOLDER_TYPE_MY_DOC);
				folder.setParentFolderId(0L);
				folder.setKmAppFlag("0");
				folder.setFolderName("根目录");
				folder.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
				folder.setFolderPath("/" + folder.getFolderName());
				folderService.save(folder);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}
		model.addAttribute("folder", folder);
		//String token = super.getToken(request);
        //model.addAttribute(GlobalContext.SESSION_TOKEN, token);
		return "archive/document/my_doc";
	}

	/**
	 * 跳转方法
	 * 
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "newDocument.action")
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息", operateFuncNm = "newDocument", operateDescribe = "对OA_文档管理/知识管理_文件夹信息进行跳转操作")
	public String newDocument(String documentType, String documentPath,
			HttpServletRequest request) throws Exception {
		request.setAttribute("editable", true);
		request.setAttribute("documentType", documentType);
		request.setAttribute("physicalPath", documentPath.replace('\\', '/'));
		if (request.getParameter("folderId") != null
				&& !"null".equals(request.getParameter("folderId"))) {
			request.setAttribute("folderId", request.getParameter("folderId"));
		}
		if (request.getParameter("currentDocumentId") != null
				&& !"null".equals(request.getParameter("currentDocumentId"))) {
			request.setAttribute("currentDocumentId",
					request.getParameter("currentDocumentId"));
		}
		if (request.getParameter("model") != null
				&& !"null".equals(request.getParameter("model"))) {
			request.setAttribute("model", request.getParameter("model"));
		}
		return "archive/document/newDocument";
	}

	/**
	 * 方法描述：公共文档功能-获取数据集
	 * 
	 * @return
	 * @author zhanglg
	 * @version 2014年6月6日上午10:47:12
	 * @throws ArchiveException
	 * @see
	 */
	@RequestMapping(value = "getPubDirDocs.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息", operateFuncNm = "getPubDirDocs", operateDescribe = "对OA_文档管理/知识管理_获取公共文档数据")
	public Map<String, Object> getPubDirDocs(Folder folder,
			HttpServletRequest request) throws ArchiveException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		folder.setFolderType(Constants.ARC_FOLDER_TYPE_PUB_DOC);
		folder.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
		folder.setDeleteFlag(0);
		folder = folderService.getDirDocs(folder);
		if (folder == null) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
		} else {
			folder.setCurrentUserId(SystemSecurityUtils.getUser().getId());
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put("folder", folder);
		}
		String token = super.getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	 * 方法描述：公共文档功能-获取数据集
	 * 
	 * @return
	 * @author zhanglg
	 * @version 2014年6月6日上午10:47:12
	 * @throws ArchiveException
	 * @see
	 */
	@RequestMapping(value = "getMyDirDocs.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息", operateFuncNm = "getMyDirDocs", operateDescribe = "对OA_文档管理/知识管理_获取我的文档数据")
	public Map<String, Object> getMyDirDocs(Folder folder,
			HttpServletRequest request) throws ArchiveException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		folder.setFolderType(Constants.ARC_FOLDER_TYPE_MY_DOC);
		folder = folderService.getDirDocs(folder);
		if (folder == null) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put("folder", folder);
		}
		String token = super.getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	 * 方法描述：归档获取数据集
	 * 
	 * @return
	 * @author weny
	 * @version 2014年7月09日
	 * @throws ArchiveException
	 * @see
	 */
	@RequestMapping(value = "getFileDirDocs.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_归档信息", operateFuncNm = "getFileDirDocs", operateDescribe = "对OA_文档管理/知识管理_获取归档文档数据")
	public Map<String, Object> getFileDirDocs(Folder folder,
			HttpServletRequest request) throws ArchiveException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		folder.setFolderType(Constants.ARC_FOLDER_TYPE_FILE_DOC);
		Folder f = folderService.getFileDirDocsQuery(folder);
		if (f != null) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put("folder", f);
		} else {
			if (folder.getId() == null) {
				folder.setParentFolderId(0L);
				folder.setFolderName("根目录");
				if (Constants.ARC_FOLDER_TYPE_FILE_DOC.equals(folder
						.getFolderType())) {
					folder.setCreateUserOrg(UserUtils.getUser(
							SystemSecurityUtils.getUser().getId()).getOrgId());
				}
				try {
					folder = folderService.get(folder);
					Permission permission = new Permission();
					permission.setFolderId(folder.getId());
					User u = SystemSecurityUtils.getUser();
					permission.setUserId(u.getId());
					permission.setDeptId(u.getDeptId());
					permission.setOrgId(u.getOrgId());
					Long count = permissionService
							.getFolderPermissionCount(permission);
					if (count == null || count < 1) {
						resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
						resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
								"没有访问权限");
						return resultMap;
					}

				} catch (CustomException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			folder = folderService.getFolderPermissionQuery(folder);
			if (folder == null) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			} else {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put("folder", folder);
			}
		}
		return resultMap;
	}

	/**
	 * 方法描述：归档获取数据集
	 * 
	 * @return
	 * @author weny
	 * @version 2014年7月09日
	 * @throws Exception
	 * @see
	 */
	@RequestMapping(value = "getFileReturn.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_归档信息", operateFuncNm = "getFileReturn", operateDescribe = "对OA_文档管理/知识管理_获取归档文档数据")
	public Map<String, Object> getFileReturn(Folder folder,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		folder = folderService.get(folder);
		Folder fol = new Folder();
		fol.setId(folder.getParentFolderId());
		fol.setFolderType(Constants.ARC_FOLDER_TYPE_FILE_DOC);
		fol = folderService.getFolderPermissionQuery(fol);
		if (fol == null) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put("folder", fol);
		}
		return resultMap;
	}

	/**
	 * 方法描述：公共文档功能-获取数据集
	 * 
	 * @return
	 * @author zhanglg
	 * @version 2014年6月6日上午10:47:12
	 * @throws ArchiveException
	 * @see
	 */
	@RequestMapping(value = "getParentPubDirDocs.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息", operateFuncNm = "getParentPubDirDocs", operateDescribe = "对OA_文档管理/知识管理_获取公共文档数据")
	public Map<String, Object> getParentPubDirDocs(Folder folder,
			HttpServletRequest request) throws ArchiveException {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			folder = folderService.get(folder);

			Folder parentFolder = new Folder();
			parentFolder.setId(folder.getParentFolderId());

			folder = folderService.getDirDocs(parentFolder);

			if (folder == null) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			} else {
				folder.setCurrentUserId(SystemSecurityUtils.getUser().getId());
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put("folder", folder);
			}
		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}

		return resultMap;
	}

	/**
	 * 删除方法
	 * 
	 * @param Folder
	 *            folder 实体类
	 * @param String
	 *            ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息", operateFuncNm = "deleteByIds", operateDescribe = "对OA_文档管理/知识管理_文件夹信息进行删除")
	public Integer deleteByIds(Folder folder, String ids,
			HttpServletRequest request) throws Exception {
		folder.setPrimaryKeys(ids.split(","));
		Integer count = folderService.delete(folder);
		if (count > 0) {
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				Folder doc = new Folder();
				doc.setId(Long.valueOf(id));
				Folder fol = folderService.get(doc);
				audithisService.audithis(request, Long.valueOf(id),
						fol.getFolderName(),
						Constants.ARC_AUDITHIS_DATATYPE_DIR,
						Constants.ARC_AUDITHIS_DELETE, "删除文件夹 " + getModelValue(fol.getFolderType()) + id);
			}
		}
		return count;
	}
	
	
	private String getModelValue(String model) {
		String type = "";
		if("0".equals(model)) {
			type = "(公共文档)";
		} else if("1".equals(model)){
			type = "(我的文档)";
		}else if("4".equals(model)){
			type = "(归档目录)";
		}
		return type;
	}

	/**
	 * 保存方法
	 * 
	 * @param Folder
	 *            folder 实体类
	 * @param BindingResult
	 *            result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息", operateFuncNm = "save", operateDescribe = "对OA_文档管理/知识管理_文件夹信息进行新增操作")
	public Map<String, Object> save(@Valid Folder folder, BindingResult result,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}

		// 验证token
		resultMap = validateToken(request);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		if("根目录".equals(folder.getFolderName())){
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils
					.getMessage("JC_OA_ARCHIVE_031",
							new String[] { folder.getFolderName() }));
			return resultMap;
		}
		Folder checkFolder = new Folder();
		checkFolder.setFolderName(folder.getFolderName());
		checkFolder.setParentFolderId(folder.getParentFolderId());
		checkFolder.setDeleteFlag(0);
		checkFolder.setDmInRecycle(0);
		if (this.checkFolderNameExist(checkFolder,request)) {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils
					.getMessage("JC_OA_ARCHIVE_008",
							new String[] { folder.getFolderName() }));
			resultMap.put("token", super.getToken(request));
			return resultMap;
		}
		if (!"false".equals(resultMap.get("success"))) {
			Folder parent = new Folder();
			parent.setId(folder.getParentFolderId());
			parent = folderService.get(parent);
			folder.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
			folder.setFolderPath(parent.getFolderPath() + "/"
					+ folder.getFolderName());
			folderService.save(folder);

			// 子文件夹权限的继承
			permissionService.copyPermission(folder.getParentFolderId(),
					folder.getId());

			audithisService.audithis(request, folder.getId(),
					folder.getFolderName(),
					Constants.ARC_AUDITHIS_DATATYPE_DIR,
					Constants.ARC_AUDITHIS_NEWUPDOWN,
					"新建目录" + folder.getFolderName()+getModelValue(folder.getFolderType()));
			folder.setOwner(SystemSecurityUtils.getUser().getDisplayName());
			resultMap.put("folder", folder);
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_SYS_001"));
		}
		resultMap.put("token", super.getToken(request));
		return resultMap;
	}

	/**
	 * 修改方法
	 * 
	 * @param Folder
	 *            folder 实体类
	 * @return Integer 更新的数目
	 * @author
	 * @version 2014-06-05
	 * @throws ArchiveException
	 */
	@RequestMapping(value = "update.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息", operateFuncNm = "update", operateDescribe = "对OA_文档管理/知识管理_文件夹信息进行更新操作")
	public Map<String, Object> update(Folder folder, BindingResult result,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Map<String, Object> map = this.selectDir(folder, request);
		if (map.get("success").equals("error")) {
			resultMap.putAll(map);
			return resultMap;
		} else {
			Folder oldFolder = (Folder)map.get("Folder");
			if ("根目录".equals(folder.getFolderName())) {
				resultMap.put("success", "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils
						.getMessage("JC_OA_ARCHIVE_031",
								new String[] { folder.getFolderName() }));
				return resultMap;
			}
			try {
				// 更新时检查名称唯一性，应该在搜索结果中排除自己
				Folder checkFolder = new Folder();
				checkFolder.setFolderName(folder.getFolderName());
				checkFolder.setParentFolderId(folder.getParentFolderId());
				checkFolder.setDeleteFlag(0);
				checkFolder.setDmInRecycle(0);
				checkFolder.setFolderPath(folder.getFolderPath());
				List<Folder> folders = folderService.queryAll(checkFolder);
				if (folders != null && folders.size() > 0) {
					for (Folder f : folders) {
						if (f.getId().equals(folder.getId())) {
							folders.remove(f);
							break;
						}
					}
					if (folders.size() > 0) {
						resultMap.put("success", "false");
						resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
								MessageUtils
										.getMessage("JC_OA_ARCHIVE_008",
												new String[] { folder
														.getFolderName() }));
						return resultMap;
					}
				}

				if(folder.getParentFolderId() != null) {
					if(oldFolder.getParentFolderId().longValue() != folder.getParentFolderId()) {
						resultMap.put("success", "false");
						resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
								MessageUtils
										.getMessage("JC_OA_ARCHIVE_023"));
						return resultMap;
					}
				}
				folder.setModifyDate(oldFolder.getModifyDate());
				folder.setFolderPath(oldFolder.getFolderPath());
				folder.setOldFolderPath(oldFolder.getFolderPath() + "/");
				// folder.setModifyDateNew(new Date());
				Integer flag = folderService.update(folder);

				if (flag == 1) {
					audithisService.audithis(request, folder.getId(),
							folder.getFolderName(),
							Constants.ARC_AUDITHIS_DATATYPE_DIR,
							Constants.ARC_AUDITHIS_EDIT,
							"编辑目录" + folder.getFolderName()
									+ getModelValue(folder.getFolderType()));
					resultMap.put("success", "true");
					resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
							MessageUtils.getMessage("JC_SYS_001"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				ArchiveException ae = new ArchiveException();
				ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
				throw ae;
			}
			resultMap.put("token", super.getToken(request));
			return resultMap;
		}
	}

	/**
	 * 获取单条记录方法
	 * 
	 * @param Folder
	 *            folder 实体类
	 * @return Folder 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "get.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息", operateFuncNm = "get", operateDescribe = "对OA_文档管理/知识管理_文件夹信息进行单条查询操作")
	public Folder get(Folder folder, HttpServletRequest request)
			throws Exception {
		return folderService.get(folder);
	}

	/**
	 * 方法描述：
	 * 
	 * @param Folder
	 * @return
	 * @throws ArchiveException
	 * @author zhanglg
	 * @version 2014年6月17日下午6:32:52
	 * @see
	 */
	@RequestMapping(value = "uploadDocs.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_上传文档", operateFuncNm = "uploadDocs", operateDescribe = "对OA_文档管理/知识管理_上传文档数据")
	public Map<String, Object> uploadDocs(Folder folder,
			HttpServletRequest request) throws ArchiveException {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<Document> documents = folderService.uploadDocs(folder, request);
		for (Document document : documents) {
			audithisService.audithis(request, document.getId(),
					document.getDmName(), Constants.ARC_AUDITHIS_DATATYPE_DOC,
					Constants.ARC_AUDITHIS_NEWUPDOWN,
					"上传文件" +getModelValue(String.valueOf(document.getModel()))+ document.getDmName());
		}

		resultMap.put("success", "true");
		resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
				MessageUtils.getMessage("JC_SYS_045"));
		resultMap.put("documents", documents);
		return resultMap;
	}

	/**
	 * 方法描述：
	 * 
	 * @param Folder
	 * @return
	 * @author zhanglg
	 * @version 2014年6月17日下午6:32:52
	 * @throws Exception
	 * @see
	 */
	@RequestMapping(value = "delDirDocs.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_删除文件夹和文档数据", operateFuncNm = "delDirDocs", operateDescribe = "对OA_文档管理/知识管理_删除文件夹和文档数据")
	public Map<String, Object> delDirDocs(String ids, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (StringUtil.isEmpty(ids)) {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_SYS_055"));
			return resultMap;
		}
		String[] idArray = ids.split(",");
		Document oldDoc = new Document();
		for (String id : idArray) {
			if (id.startsWith("#doc_")) {
				oldDoc.setId(Long.valueOf(id.replace("#doc_", "")));
				oldDoc = folderService.getDocument(oldDoc);
				// 如果文档已被锁定，不能操作
				if (oldDoc.getDmLockStatus().equals(
						Constants.ARC_DOC_LOCKSTATUS_LOCKED)) {
					resultMap.put("success", "false");
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
							MessageUtils.getMessage("JC_OA_ARCHIVE_015"));
					return resultMap;
				}
			}
		}
		folderService.deleteDirDocs(ids, request);

		for (String id : idArray) {
			if (id.startsWith("#dir_")) {
				Folder folder = new Folder();
				folder.setId(Long.valueOf(id.replace("#dir_", "")));
				Folder fol = folderService.get(folder);
				audithisService.audithis(request,
						Long.valueOf(id.replace("#dir_", "")),
						fol.getFolderName(),
						Constants.ARC_AUDITHIS_DATATYPE_DIR,
						Constants.ARC_AUDITHIS_DELETE, "删除文件夹" + getModelValue(fol.getFolderType()));
			} else {
				Document doc = new Document();
				doc.setId(Long.valueOf(id.replace("#doc_", "")));
				Document ment = folderService.getDocument(doc);
				audithisService.audithis(request,
						Long.valueOf(id.replace("#doc_", "")),
						ment.getDmName(), Constants.ARC_AUDITHIS_DATATYPE_DOC,
						Constants.ARC_AUDITHIS_DELETE, "删除文件" + getModelValue(String.valueOf(doc.getModel())));
			}
		}
		resultMap.put("success", "true");
		resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
				MessageUtils.getMessage("JC_SYS_054"));
		return resultMap;
	}

	/**
	 * 方法描述：
	 * 
	 * @author zhangligang
	 * @version 2014年6月20日上午10:35:02
	 * @throws ArchiveException
	 * @see
	 */
	@RequestMapping(value = "showDocContent.action")
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_显示文档内容", operateFuncNm = "showDocContent", operateDescribe = "对OA_文档管理/知识管理_显示文档内容")
	public String showDocContent(Document doc,HttpServletResponse response,HttpServletRequest request) throws ArchiveException {
		try {
			doc = folderService.getDocument(doc);
			// TODO 公共文档要做权限验证

			// 如果文件为WebOffice类型，跳转WebOffice显示。否则，跳转显示页面
			if (!Constants.ARC_DOC_CONTENTTYPE_UNKNOWN.equals(doc
					.getContentType())) {
				request.setAttribute(
						"physicalPath",
						"http://" + request.getServerName() + ":"
								+ request.getServerPort()
								+ request.getContextPath() + "/"
								+ doc.getPhysicalPath().replace('\\', '/'));
				request.setAttribute("documentType", doc.getDmSuffix());
				request.setAttribute("editable", false);
				audithisService.audithis(request, doc.getId(), doc.getDmName(),
						Constants.ARC_AUDITHIS_DATATYPE_DOC,
						Constants.ARC_AUDITHIS_VIEW, "浏览" + doc.getDmName() + getModelValue(String.valueOf(doc.getModel())));
				return "archive/document/newDocument";
			}

			File file = new File(folderService.getAbsoluteContextPath(request)
					+ doc.getPhysicalPath());
			if (file.exists()) {

				response.setCharacterEncoding("utf-8");
				response.setHeader("Content-Disposition", "inline;fileName=\""
						+ doc.getDmName() + "." + doc.getDmSuffix() + "\"");
				String extName = file.getName().substring(
						file.getName().lastIndexOf(".") + 1);
				if ("txt".equalsIgnoreCase(extName)) {
					response.setContentType("text/plain");
				} else if ("css".equalsIgnoreCase(extName)) {
					response.setContentType("text/css");
				} else if ("htm".equalsIgnoreCase(extName)
						|| "html".contentEquals(extName)
						|| "stm".contentEquals(extName)) {
					response.setContentType("text/html");
				} else if ("jpe".equalsIgnoreCase(extName)
						|| "jpg".contentEquals(extName)
						|| "jpeg".contentEquals(extName)) {
					response.setContentType("image/jpeg	");
				} else if ("bmp".equalsIgnoreCase(extName)) {
					response.setContentType("image/bmp	");
				} else if ("doc".equalsIgnoreCase(extName)
						|| "dot".contentEquals(extName)
						|| "docx".contentEquals(extName)) {
					response.setContentType("application/msword");
				} else if ("xls".equalsIgnoreCase(extName)
						|| "xlc".contentEquals(extName)
						|| "xla".contentEquals(extName)
						|| "xlm".equalsIgnoreCase(extName)
						|| "xlt".contentEquals(extName)
						|| "xlw".contentEquals(extName)) {
					response.setContentType("application/vnd.ms-excel");
				} else if ("pot".equalsIgnoreCase(extName)
						|| "pps".contentEquals(extName)
						|| "ppt".contentEquals(extName)) {
					response.setContentType("application/ms-powerpoint");
				} else if ("js".equalsIgnoreCase(extName)) {
					response.setContentType("application/x-javascript");
				} else if ("gif".equalsIgnoreCase(extName)) {
					response.setContentType("image/gif");
				} else if ("tif".equalsIgnoreCase(extName)
						|| "tiff".contentEquals(extName)) {
					response.setContentType("image/tiff");
				} else if ("ico".equalsIgnoreCase(extName)) {
					response.setContentType("image/x-icon");
				} else if ("pdf".equalsIgnoreCase(extName)) {
					response.setContentType("application/pdf");
				} else {
					// 其它不支持的文件类型，返回“不支持在线查看内容”
					response.setCharacterEncoding("utf-8");
					response.setContentType("multipart/form-data");
					response.setHeader("Content-Disposition",
							"attachment;fileName=\"" + doc.getDmName() + "."
									+ doc.getDmSuffix() + "\"");

				}

				InputStream inputStream;
				inputStream = new FileInputStream(file);
				OutputStream os = response.getOutputStream();
				byte[] b = new byte[Integer.parseInt(GlobalContext
						.getProperty("STREAM_SLICE"))];
				int length;
				while ((length = inputStream.read(b)) > 0) {
					os.write(b, 0, length);
				}
				os.close();
				inputStream.close();
			} else {

			}
			audithisService.audithis(request, doc.getId(), doc.getDmName(),
					Constants.ARC_AUDITHIS_DATATYPE_DOC,
					Constants.ARC_AUDITHIS_VIEW, "浏览文件" + doc.getDmName() + getModelValue(String.valueOf(doc.getModel())));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 方法描述：公共文档功能-获取子目录
	 * 
	 * @return
	 * @author zhanglg
	 * @version 2014年6月6日上午10:47:12
	 * @throws ArchiveException
	 * @see
	 */
	@RequestMapping(value = "getSubDir.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息", operateFuncNm = "getSubDir", operateDescribe = "对OA_文档管理/知识管理_获取子目录数据")
	public Map<String, Object> getSubDir(Folder folder,
			HttpServletRequest request) throws ArchiveException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		folder.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
		try {
			if (folder.getParentFolderId() == null) {
				folder.setParentFolderId(0L);
			}
			// 如果不传文件夹类型，默认查询公共文档目录
			if (folder.getFolderType() == null
					|| Constants.ARC_FOLDER_TYPE_PUB_DOC.equals(folder
							.getFolderType())) {
				folder.setFolderType(Constants.ARC_FOLDER_TYPE_PUB_DOC);
			} else {
				// 查询“我的文档”目录时需要用创建人ID做条件
				folder.setCreateUser(SystemSecurityUtils.getUser().getId());
			}
			List<Folder> folders = folderService.queryAll(folder);

			// 如果当前不是Root目录，需要取得上一级的上一级目录放在结果集第一个，以便客户端生成“返回上一级”按钮
			if (folder.getParentFolderId().longValue() != 0) {
				Folder parentFolder = new Folder();
				parentFolder.setId(folder.getParentFolderId());
				parentFolder = folderService.get(parentFolder);
				Long id = parentFolder.getParentFolderId();
				if (id.equals(0L)) {
					parentFolder = new Folder();
					parentFolder.setId(0L);
				} else {
					parentFolder = new Folder();
					parentFolder.setId(id);
				}
				parentFolder.setFolderName("返回上一级");
				folders.add(0, parentFolder);
			}
			resultMap.put("folders", folders);
		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		}

		resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		return resultMap;
	}

	/**
	 * 跳转方法
	 * 
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-06-05
	 * @throws ArchiveException
	 */
	@RequestMapping(value = "manageRecycle.action")
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_回收站信息", operateFuncNm = "manageRecycle", operateDescribe = "对OA_文档管理/知识管理_回收站进行跳转操作")
	public String manageRecycle(HttpServletRequest request) throws ArchiveException {
		return "archive/document/recycle";
	}

	/**
	 * 回收站详细信息取得
	 * 
	 * @return String 回收站详细信息
	 * @throws Exception
	 * @author weny
	 * @version 2014-06-26
	 */
	@RequestMapping(value = "selectRecycle.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_回收站详细信息取得", operateFuncNm = "selectRecycle", operateDescribe = "对OA_文档管理/知识管理_回收站详细信息取得操作")
	public Map<String, Object> selectRecycle(HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Recycle> lecycleRecycle = folderService.selectRecycle();
		resultMap.put("success", "true");
		resultMap.put("lecycleRecycle", lecycleRecycle);
		return resultMap;
	}
	
	/**
	 * 批量删除方法
	 * 
	 * @param Folder
	 *            folder 实体类
	 * @param String
	 *            ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version 2014-06-26
	 */
	@RequestMapping(value = "bulkDelete.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_我的文档信息", operateFuncNm = "bulkDelete", operateDescribe = "对OA_文档管理/知识管理_我的文档进行批量删除")
	public Map<String, Object> blukDelete(String id,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
        if(id!=null&&id!=""){
        	String dwtype = "";
    		String name = "";
    		String ids="";
    		String type="";
    		String idsdir="";
    		String idsdoc="";
        	String[] idstype=id.split(";");
        	for(int i=0;i<idstype.length;i++){
        		String[] idtype=idstype[i].toString().split(",");
            	ids=idtype[0].toString().substring(5);
            	type=idtype[1].toString();
        		if ("0".equals(type)) {
        			dwtype = "文件夹的删除";
        			Folder folder = new Folder();
        			folder.setId(Long.valueOf(ids));
        			Folder fol = folderService.get(folder);
        			name = fol.getFolderName();
        			if(idsdir==null||idsdir=="")
        			{
        				idsdir=ids;
        			}else {
        				idsdir=idsdir+","+ids;
					}
        		} else {
        			dwtype = "文档的删除";
        			Document doc = new Document();
        			doc.setId(Long.valueOf(ids));
        			Document ment = folderService.getDocument(doc);
        			name = ment.getDmName();
        			if(idsdoc==null||idsdoc=="")
        			{
        				idsdoc=ids;
        			}else {
            				idsdoc=idsdoc+","+ids;
    				}
        		}
        		}
        	Integer count = folderService.bulkDelete(idsdir, idsdoc);
    		if (count > 0) {
    			resultMap.put("success", "true");
    			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
    					MessageUtils.getMessage("JC_SYS_005"));
        		audithisService.audithis(request, Long.valueOf(ids), name,
        				Integer.valueOf(type), Constants.ARC_AUDITHIS_DELETE, dwtype);
        	}	
        	
        }	
		return resultMap;
	}
	
	/**
	 * 我的文档删除方法
	 * 
	 * @param Folder
	 *            folder 实体类
	 * @param String
	 *            ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version 2014-06-26
	 */
	@RequestMapping(value = "batchDelete.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息", operateFuncNm = "batchDelete", operateDescribe = "对OA_文档管理/知识管理_文件夹信息进行删除")
	public Map<String, Object> batchDelete(String id, String type,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String dwtype = "";
		String name = "";
		if ("0".equals(type)) {
			dwtype = "文件夹回收站的删除";
			Folder folder = new Folder();
			folder.setId(Long.valueOf(id));
			Folder fol = folderService.get(folder);
			name = fol.getFolderName();
		} else {
			dwtype = "文档回收站的删除";
			Document doc = new Document();
			doc.setId(Long.valueOf(id));
			Document ment = folderService.getDocument(doc);
			name = ment.getDmName();
		}

		Integer count = folderService.batchDelete(id, type);
		if (count > 0) {
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_SYS_005"));
		}
		audithisService.audithis(request, Long.valueOf(id), name,
				Integer.valueOf(type), Constants.ARC_AUDITHIS_RECYCLE, dwtype);
		return resultMap;
	}

	/**
	 * 回收站删除方法
	 * 
	 * @param Folder
	 *            folder 实体类
	 * @param String
	 *            ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version 2014-06-26
	 */
	@RequestMapping(value = "batchRecycleDelete.action")
	@RequiresPermissions("recycle:delect")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息", operateFuncNm = "batchRecycleDelete", operateDescribe = "对OA_文档管理/知识管理_文件夹信息进行删除")
	public Map<String, Object> batchRecycleDelete(String id, String type,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String dwtype = "";
		String name = "";
		if ("0".equals(type)) {
			dwtype = "文件夹回收站的删除";
			Folder folder = new Folder();
			folder.setId(Long.valueOf(id));
			Folder fol = folderService.get(folder);
			//如果文件夹不在回收站
			if(null == fol || Constants.ARC_DM_IN_RECYCLE_NO==fol.getDmInRecycle()){
				resultMap.put("success", "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
						MessageUtils.getMessage("JC_SYS_032"));
				return resultMap;
			}
			name = fol.getFolderName();
		} else {
			dwtype = "文档回收站的删除";
			Document doc = new Document();
			doc.setId(Long.valueOf(id));
			Document ment = folderService.getDocument(doc);
			//如果文档不在回收站
			if(null == ment || Constants.ARC_DM_IN_RECYCLE_NO==ment.getDmInRecycle()){
				resultMap.put("success", "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
						MessageUtils.getMessage("JC_SYS_032"));
				return resultMap;
			}
			name = ment.getDmName();
		}

		Integer count = folderService.batchDelete(id, type);
		if (count > 0) {
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_SYS_005"));
		}
		audithisService.audithis(request, Long.valueOf(id), name,
				Integer.valueOf(type), Constants.ARC_AUDITHIS_RECYCLE, dwtype);
		return resultMap;
	}
	
	/**
	 * 回收站还原方法
	 * 
	 * @param Folder
	 *            folder 实体类
	 * @throws Exception
	 * @author weny
	 * @version 2014-06-26
	 */
	@RequestMapping(value = "batchRecycle.action")
	@RequiresPermissions("recycle:update")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_回收站还原", operateFuncNm = "batchRecycle", operateDescribe = "对OA_文档管理/知识管理_回收站还原进行删除")
	public Map<String, Object> batchRecycle(Long id, String type,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = folderService.batchRecycle(id, type);
		if("false".equals(resultMap.get("success"))) {
			return resultMap;
		}
		String dwtype = "";
		String name = "";
		if ("0".equals(type)) {
			dwtype = "还原公共文件夹";
			Folder folder = new Folder();
			folder.setId(Long.valueOf(id));
			Folder fol = folderService.get(folder);
			name = fol.getFolderName();
		} else {
			dwtype = "还原公共文档";
			Document doc = new Document();
			doc.setId(Long.valueOf(id));
			Document ment = folderService.getDocument(doc);
			name = ment.getDmName();
		}
		audithisService.audithis(request, Long.valueOf(id), name,
				Integer.valueOf(type), Constants.ARC_AUDITHIS_RECYCLE, dwtype);
		return resultMap;
	}

	/**
	 * 方法描述：复制文件夹功能
	 * 
	 * @param folder
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 * @author zhangligang
	 * @version 2014年6月24日下午3:29:41
	 * @see
	 */
	@RequestMapping(value = "copyDirTo")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息表", operateFuncNm = "copyDirTo", operateDescribe = "对OA_文档管理/知识管理_文件夹信息表复制")
	public Map<String, Object> copyDirTo(Folder folder, BindingResult result,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Map<String, Object> map=this.selectDir(folder, request);
		if(map.get("success").equals("error")){
			resultMap.putAll(map);
			return resultMap;
		}else{
		Long time=System.currentTimeMillis();
		log.info(time+"    Start copyDirTo : "+folder.getId()+" ------> "+folder.getParentFolderId());
		// 查询源目录信息
		Folder oldFolder = new Folder();
		oldFolder=(Folder) map.get("Folder");
		//oldFolder.setId(folder.getId());
		//oldFolder = folderService.get(oldFolder);
		// 目标目录与源目录相同
		if (oldFolder.getParentFolderId().equals(folder.getParentFolderId())) {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_002"));
			return resultMap;
		}
		// 查询目标目录
		Folder parent = new Folder();
		parent.setId(folder.getParentFolderId());
		parent.setDeleteFlag(0);
		parent.setDmInRecycle(0);
		parent = folderService.get(parent);
		if(parent == null){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,MessageUtils.getMessage("JC_SYS_034"));
			return resultMap;
		}
		if (oldFolder.getId().equals(parent.getId())
				|| parent.getFolderPath().startsWith(
						oldFolder.getFolderPath() + "/")) {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_003"));
			return resultMap;
		}
		if(folder.getModifyDate() != null && folder.getModifyDate().getTime() != oldFolder.getModifyDate().getTime()) {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_023"));
			return resultMap;
		}
		// 检查新生成的文件夹名称是否会超长
		String newName = folderService.getNewFolderName(parent.getId(),
				oldFolder.getFolderName());
		if (newName.length() > 64) {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_026"));
			return resultMap;
		}

		// 复制文档信息到指定目录
		Folder newFolder = folderService.copyDirTo(oldFolder, parent, request);
		if (newFolder != null) {
			audithisService.audithis(request, oldFolder.getId(),
					oldFolder.getFolderName(),
					Constants.ARC_AUDITHIS_DATATYPE_DIR,
					Constants.ARC_AUDITHIS_COPYCUT,
					"复制文件夹" + oldFolder.getFolderName() + getModelValue(oldFolder.getFolderType()));
			audithisService.audithis(request, newFolder.getId(),
					newFolder.getFolderName(),
					Constants.ARC_AUDITHIS_DATATYPE_DIR,
					Constants.ARC_AUDITHIS_NEWUPDOWN,
					"通过复制创建文件夹" + newFolder.getFolderName() + getModelValue(newFolder.getFolderType()));
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils
					.getMessage("JC_OA_ARCHIVE_004",
							new Object[] { newFolder.getFolderName() }));
		}
		log.info(time+"    Finish copyDirTo : "+folder.getId()+" ------> "+folder.getParentFolderId());
		}
		return resultMap;
	}

	/**
	 * 方法描述：剪切文件夹信息功能
	 * 
	 * @param document
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 * @author zhangligang
	 * @version 2014年6月24日下午3:29:41
	 * @see
	 */
	@RequestMapping(value = "cutDirTo")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息表", operateFuncNm = "cutDirTo", operateDescribe = "对OA_文档管理/知识管理_文件夹信息表剪切")
	public Map<String, Object> cutDirTo(Folder folder, BindingResult result,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = validateBean(result);
		Folder temp = new Folder();
		temp.setId(folder.getId());
		if(Constants.ARC_FOLDER_TYPE_PUB_DOC.equals(folder.getFolderType())) {
			temp.setDmInRecycle(0);
			temp.setDeleteFlag(0);
		} else {
			temp.setDeleteFlag(0);
		}
		temp = this.folderService.get(temp);
		if(temp == null) {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_034"));
			return resultMap;
		}
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Map<String, Object> map=this.selectDir(folder, request);
		if(map.get("success").equals("error")){
			resultMap.putAll(map);
			return resultMap;
		}else{
		Long time=System.currentTimeMillis();
		log.info(time+"    Start cutDirTo : "+folder.getId()+" ------> "+folder.getParentFolderId());
		// 查询源目录信息
		Folder oldFolder = new Folder();
		oldFolder=(Folder) map.get("Folder");
		//oldFolder.setId(folder.getId());
		//oldFolder = folderService.get(oldFolder);
		if (oldFolder.getParentFolderId().equals(folder.getParentFolderId())) {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_002"));
			return resultMap;
		}
		if(folder.getModifyDate() != null && folder.getModifyDate().getTime() != oldFolder.getModifyDate().getTime()) {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_023"));
			return resultMap;
		}
		// 查询目标目录
		Folder parent = new Folder();
		parent.setId(folder.getParentFolderId());
		parent.setDeleteFlag(0);
		parent.setDmInRecycle(0);
		parent = folderService.get(parent);
		if(parent == null){
			resultMap.putAll(map);
			return resultMap;
		}
		if (oldFolder.getId().equals(parent.getId())
				|| parent.getFolderPath().startsWith(
						oldFolder.getFolderPath() + "/")) {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_003"));
			return resultMap;
		}
		// 检查新生成的文件夹名称是否会超长
		String newName = folderService.getNewFolderName(parent.getId(),
				oldFolder.getFolderName());
		if (newName.length() > 64) {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_026"));
			return resultMap;
		}
		// 复制文档信息到指定目录
		Folder newFolder = folderService.cutDirTo(oldFolder, parent);
		Integer flag = 1;
		if (flag == 1) {
			audithisService.audithis(request, oldFolder.getId(),
					oldFolder.getFolderName(),
					Constants.ARC_AUDITHIS_DATATYPE_DIR,
					Constants.ARC_AUDITHIS_COPYCUT,
					"剪切文件夹" + oldFolder.getFolderName() + getModelValue(oldFolder.getFolderType()));
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils
					.getMessage("JC_OA_ARCHIVE_005",
							new Object[] { newFolder.getFolderName() }));
		}
		log.info(time+"    Finish cutDirTo : "+folder.getId()+" ------> "+folder.getParentFolderId());
		}
		return resultMap;
	}

	/**
	 * 方法描述：复制文档信息功能
	 * 
	 * @param document
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 * @author zhangligang
	 * @version 2014年6月24日下午3:29:41
	 * @see
	 */
	@RequestMapping(value = "copyDocTo")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文档信息表", operateFuncNm = "copyDocTo", operateDescribe = "对OA_文档管理/知识管理_文档信息表复制")
	public Map<String, Object> copyDocTo(Document document,
			BindingResult result, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Document oldDoc = new Document();
		oldDoc.setDeleteFlag(0);
		oldDoc.setDmInRecycle(0);
		oldDoc.setId(document.getId());
		oldDoc = folderService.getDocument(oldDoc);
		if(oldDoc == null){
				resultMap.put(GlobalContext.RESULT_SUCCESS, "error");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, 
						MessageUtils.getMessage("JC_OA_ARCHIVE_034"));
				return resultMap;
		}
		else {
			if (oldDoc.getDmLockStatus().equals(
					Constants.ARC_DOC_LOCKSTATUS_LOCKED)) {
				resultMap.put("success", "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
						MessageUtils.getMessage("JC_OA_ARCHIVE_015"));
				return resultMap;
			}
			if(document.getModifyDate() != null && document.getModifyDate().getTime() != oldDoc.getModifyDate().getTime()) {
				resultMap.put("success", "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
						MessageUtils.getMessage("JC_OA_ARCHIVE_023"));
				return resultMap;
			}
			Folder folder = new Folder();
			folder.setId(document.getFolderId());
			folder = folderService.get(folder);
			// 检查新生成的文件名称是否会超长
			String newName = folderService.getNewFileName(
					document.getFolderId(), oldDoc.getDmName(),
					oldDoc.getDmSuffix());
			if (newName.length() > 64) {
				resultMap.put("success", "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
						MessageUtils.getMessage("JC_OA_ARCHIVE_026"));
				return resultMap;
			}
			// 复制记录，如果当前目录下已有同名文件，需要重命名
			Document newDoc = folderService.copyDocTo(oldDoc, folder, request);

			if (newDoc != null) {
				audithisService.audithis(request, newDoc.getId(),
						newDoc.getDmName(),
						Constants.ARC_AUDITHIS_DATATYPE_DOC,
						Constants.ARC_AUDITHIS_COPYCUT, "复制文件"
								+ getModelValue(folder.getFolderType()));
				audithisService.audithis(
						request,
						newDoc.getId(),
						newDoc.getDmName(),
						Constants.ARC_AUDITHIS_DATATYPE_DOC,
						Constants.ARC_AUDITHIS_NEWUPDOWN,
						"通过复制创建文件" + newDoc.getDmName()
								+ getModelValue(folder.getFolderType()));
				resultMap.put("success", "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils
						.getMessage("JC_OA_ARCHIVE_006",
								new Object[] { newDoc.getDmName() }));
			}
		} 
		return resultMap;
	}

	/**
	 * 方法描述：剪切文档信息功能
	 * 
	 * @param document
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 * @author zhangligang
	 * @version 2014年6月24日下午3:29:41
	 * @see
	 */
	@RequestMapping(value = "cutDocTo")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文档信息表", operateFuncNm = "cutDocTo", operateDescribe = "对OA_文档管理/知识管理_文档信息表剪切")
	public Map<String, Object> cutDocTo(Document document,
			BindingResult result, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Document oldDoc = new Document();
		oldDoc.setDeleteFlag(0);
		oldDoc.setDmInRecycle(0);
		oldDoc.setId(document.getId());
		oldDoc = folderService.getDocument(oldDoc);
		if (oldDoc != null) {
			if(document.getModifyDate() != null && document.getModifyDate().getTime() != oldDoc.getModifyDate().getTime()) {
				resultMap.put("success", "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
						MessageUtils.getMessage("JC_OA_ARCHIVE_023"));
				return resultMap;
			}
			if (oldDoc.getDmLockStatus()
					.equals(Constants.ARC_DOC_LOCKSTATUS_LOCKED)) {
				resultMap.put("success", "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
						MessageUtils.getMessage("JC_OA_ARCHIVE_015"));
				return resultMap;
			}
			if (oldDoc.getFolderId().equals(document.getFolderId())) {
				resultMap.put("success", "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
						MessageUtils.getMessage("JC_OA_ARCHIVE_002"));
				return resultMap;
			}
			// 检查新生成的文件名称是否会超长
			String newName = folderService.getNewFileName(document.getFolderId(),
					oldDoc.getDmName(), oldDoc.getDmSuffix());
			if (newName.length() > 64) {
				resultMap.put("success", "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
						MessageUtils.getMessage("JC_OA_ARCHIVE_026"));
				return resultMap;
			}
			// 剪切文档信息到指定目录
			Document newDoc = folderService.cutDocTo(document);
	
			if (newDoc != null) {
				audithisService
						.audithis(request, newDoc.getId(), newDoc.getDmName(),
								Constants.ARC_AUDITHIS_DATATYPE_DOC,
								Constants.ARC_AUDITHIS_COPYCUT,
								"剪切文件" + newDoc.getDmName() + getModelValue(String.valueOf(newDoc.getModel())));
				resultMap.put("success", "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils
						.getMessage("JC_OA_ARCHIVE_007",
								new Object[] { newDoc.getDmName() }));
			}
		}else{
			resultMap.put(GlobalContext.RESULT_SUCCESS, "error");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_OA_ARCHIVE_035"));
		}
		return resultMap;
	}

	/**
	 * 验文件夹名称是否存在
	 * 
	 * @param Folder
	 *            folder 实体类
	 * @return String true存在 false不存在
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "checkFolderName.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息表", operateFuncNm = "checkFolderName", operateDescribe = "对OA_文档管理/知识管理_检查文件夹名称是否存在")
	public boolean checkFolderNameExist(Folder folder,HttpServletRequest request) throws Exception {
		folder.setDeleteFlag(0);
		folder.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
		if (folderService.get(folder) == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 验文档名称是否存在
	 * 
	 * @param Folder
	 *            folder 实体类
	 * @return String true存在 false不存在
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "checkDmName.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文档信息表", operateFuncNm = "checkDmName", operateDescribe = "对OA_文档管理/知识管理_检查文档名称是否存在")
	public boolean checkDmNameExist(Document doc,HttpServletRequest request) throws Exception {
		doc.setDeleteFlag(0);
		doc.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
		if (folderService.getDocument(doc) == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 方法描述：
	 * 
	 * @author zhangligang
	 * @version 2014年6月20日上午10:35:02
	 * @throws ArchiveException
	 * @see
	 */
	@RequestMapping(value = "download.action")
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_下载文档", operateFuncNm = "download", operateDescribe = "对OA_文档管理/知识管理_下载文档")
	public void download(Document doc,HttpServletResponse response,HttpServletRequest request) throws ArchiveException {
		try {
			doc = folderService.getDocument(doc);
			File file = new File(folderService.getAbsoluteContextPath(request)
					+ doc.getPhysicalPath());
			if (file.exists()) {
				String filename = "";
				filename = CharConventUtils.encodingFileName(doc.getDmName() + "."
						+ doc.getDmSuffix());
				response.setCharacterEncoding("utf-8");
				response.setContentType("multipart/form-data");
				response.setHeader(
						"Content-Disposition",
						"attachment;fileName=\"" + filename + "\"");

				InputStream inputStream;
				inputStream = new FileInputStream(file);
				OutputStream os = response.getOutputStream();
				byte[] b = new byte[Integer.parseInt(GlobalContext
						.getProperty("STREAM_SLICE"))];
				int length;
				while ((length = inputStream.read(b)) > 0) {
					os.write(b, 0, length);
				}
				os.close();
				inputStream.close();
			} else {
				// 文件不存在
			}
			audithisService.audithis(request, doc.getId(), doc.getDmName(),
					Constants.ARC_AUDITHIS_DATATYPE_DOC,
					Constants.ARC_AUDITHIS_NEWUPDOWN, "下载文件" + doc.getDmName() + getModelValue(String.valueOf(doc.getModel())));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 方法描述：公共文档功能-获取子目录
	 * 
	 * @return
	 * @author zhanglg
	 * @version 2014年6月6日上午10:47:12
	 * @throws ArchiveException
	 * @see
	 */
	@RequestMapping(value = "getSubDirTree.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文件夹信息", operateFuncNm = "getSubDir", operateDescribe = "对OA_文档管理/知识管理_获取子目录数据")
	public Map<String, Object> getSubDirTree(Folder folder,
			HttpServletRequest request) throws ArchiveException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		folder.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
		try {
			// 文件夹类型不能为空
			if (folder.getFolderType() == null) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
						MessageUtils.getMessage("JC_OA_ARCHIVE_017"));
				return resultMap;
			}
			if (Constants.ARC_FOLDER_TYPE_MY_DOC.equals(folder.getFolderType())) {
				// 查询“我的文档”目录时需要用创建人ID做条件
				folder.setCreateUser(SystemSecurityUtils.getUser().getId());
			}
			folder.setDmInRecycle(Constants.ARC_DM_IN_RECYCLE_NO);
			folder.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
			folder.setDeleteFlag(0);
			folder.addOrderByFieldDesc("t.MODIFY_DATE");
			List<Folder> folders = folderService.queryAll(folder);

			//公共文档 （或归档目录 update by zhanglg 20140729 )
			if("0".equals(folder.getFolderType()) || "4".equals(folder.getFolderType())) {
				/*Folder root = new Folder();
				root.setFolderName("根目录");
				root.setFolderType(folder.getFolderType());
				root.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
				root = folderService.get(root);
				//根目录创建人和当前登录人不在一个机构时 增加根节点到前台
				if(SystemSecurityUtils.getUser().getOrgId() != root.getCreateUserOrg()) {
					//folders.add(root);
				}*/
			}
			// 剔除没有父节点的文件夹，防止因中间节点被删除，而把下级节点当成根节点的情况
			boolean keep = true;
			while (keep) {
				// 假设不需要循环下去了，如果发现存在没有父节点的子节点，才继续检查还有没有
				keep = false;
				// 反向循环，从列表最后一个节点开始查找它的父节点
				int i = folders.size() - 1;
				for (; i >= 0; i--) {
					Folder f = folders.get(i);
//					if (f.getParentFolderId().longValue() == 0
//							&& f.getFolderName().indexOf("root") > -1) {
//						f.setFolderName(f.getFolderName()
//								.replace("root", "根目录"));
//					}

					boolean foundParent = false;
					for (int j = 0; j < folders.size(); j++) {
						if (folders.get(j).getId().equals(f.getParentFolderId())) {
							foundParent = true;
							break;
						}
					}
					// 如果当前节点没有父节点，则删除它，同时为了防止还有其它节点没有父节点，应该继续循环
					if(f.getParentFolderId() != null && !"".equals(f.getParentFolderId())){
						if (foundParent == false
								&& f.getParentFolderId().longValue() != 0) {
							folders.remove(i);
							keep = true;
						}
					}
				}
			}
			resultMap.put("folders", folders);
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_OA_ARCHIVE_032"));
		}

		
		return resultMap;
	}

	/**
	 * 验文档文件是否存在
	 * 
	 * @param Folder
	 *            folder 实体类
	 * @return String true存在 false不存在
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 * @param request
	 */
	@RequestMapping(value = "checkDocFileExist.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_文档信息表", operateFuncNm = "checkDocFileExist", operateDescribe = "对OA_文档管理/知识管理_检查文档文件是否存在")
	public boolean checkDocFileExist(Document doc, HttpServletRequest request)
			throws Exception {
		doc.setDeleteFlag(0);
		doc.setDmInRecycle(0);
		doc = folderService.getDocument(doc);
		if(doc == null) {
			return false;
		}
		File file = new File(folderService.getAbsoluteContextPath(request)
				+ doc.getPhysicalPath());
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 回收站清空
	 * 
	 * @return String 回收站清空信息
	 * @throws Exception
	 * @author weny
	 * @version 2014-06-26
	 */
	@RequestMapping(value = "clearRecycl.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/知识管理_回收站清空", operateFuncNm = "clearRecycl", operateDescribe = "对OA_文档管理/知识管理_回收站清空操作")
	public Map<String, Object> clearRecycl(HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int count = folderService.clearRecycl();
		if(count==0){
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_SYS_032"));
			return resultMap;
		}
		resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
				MessageUtils.getMessage("JC_SYS_054"));
		return resultMap;
	}
	
	/**
	 * 销毁归档文件
	 * 
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author weny
	 * @version 2014-06-05
	 */
	@RequestMapping(value = "deleteFolderFiling.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/销毁归档文件夹", operateFuncNm = "deleteFolderFiling", operateDescribe = "销毁归档文件夹")
	public Map<String, Object> deleteFolderFiling(Folder folder,String id,HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		folder.setPrimaryKeys(id.split(","));
		Folder doc = new Folder();
		doc.setId(Long.valueOf(id));
		Folder fol = folderService.get(doc);
		folderService.delete(folder);
		resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
				MessageUtils.getMessage("JC_SYS_005"));
		audithisService.audithis(request, Long.valueOf(id),
				fol.getFolderName(),
				Constants.ARC_AUDITHIS_DATATYPE_DIR,
				Constants.ARC_AUDITHIS_DELETE, "删除文件夹 " + getModelValue(fol.getFolderType()));
		return resultMap;
	}
	/**
	 * 查询文件夹数据
	 * 
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 
	 * @version 2014-09-28
	 */
	@RequestMapping(value = "selectDir.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_文档管理/查询文件夹", operateFuncNm = "selectDir", operateDescribe = "查询文件夹")
	public Map<String, Object> selectDir(Folder folder,HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Folder oldFolder = new Folder();
		oldFolder.setId(folder.getId());
		oldFolder.setDeleteFlag(0);
		oldFolder.setDmInRecycle(0);
		Folder folders = folderService.get(oldFolder);
        if(folders != null){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		 }else{
			resultMap.put(GlobalContext.RESULT_SUCCESS, "error");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,MessageUtils.getMessage("JC_OA_ARCHIVE_034"));
		 }
        resultMap.put("Folder", folders);
		return resultMap;
	}
}