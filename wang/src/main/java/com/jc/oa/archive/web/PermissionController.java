package com.jc.oa.archive.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.archive.domain.Folder;
import com.jc.oa.archive.domain.Permission;
import com.jc.oa.archive.domain.SubPermission;
import com.jc.oa.archive.domain.validator.PermissionValidator;
import com.jc.oa.archive.service.IArchiveFolderService;
import com.jc.oa.archive.service.IAudithisService;
import com.jc.oa.archive.service.IPermissionService;
import com.jc.oa.archive.util.PermissionUtil;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;


/**
 * @title  GOA2.0源代码
 * @description  controller类
 * @author 闻瑜
 * @version  2014-06-05
 */
 
@Controller
@RequestMapping(value="/archive/permission")
public class PermissionController extends BaseController{
	
	@Autowired
	private IPermissionService permissionService;

	@Autowired
	private IArchiveFolderService archiveFolderService;

	@Autowired
	private IAudithisService audithisService;
	
	@org.springframework.web.bind.annotation.InitBinder("permission")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new PermissionValidator()); 
    }
	
	public PermissionController() {
	}

	/**
	 * 分页查询方法
	 * @param Permission permission 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05 
	 * @throws Exception 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="该表执行物理删除更新时删除原有记录，添加新记录",operateFuncNm="manageList",operateDescribe="对该表执行物理删除更新时删除原有记录，添加新记录进行查询操作")
	public PageManager manageList(Permission permission,PageManager page,Long folderId,HttpServletRequest request) throws Exception{
		permission.setOrderBy("t.CREATE_DATE DESC");
		permission.setFolderId(folderId);
		permission.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
		PageManager page_ = permissionService.manageListPermission(permission, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-06-05 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="该表执行物理删除更新时删除原有记录，添加新记录",operateFuncNm="manage",operateDescribe="对该表执行物理删除更新时删除原有记录，添加新记录进行跳转操作")
	public String manage(Model model,String folderId,HttpServletRequest request) throws Exception{
		model.addAttribute("folderId",folderId);
		return "archive/permission/permission"; 
	}

/**
	 * 删除方法
	 * @param Permission permission 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="该表执行物理删除更新时删除原有记录，添加新记录",operateFuncNm="deleteByIds",operateDescribe="对该表执行物理删除更新时删除原有记录，添加新记录进行删除")
	public Integer deleteByIds(Permission permission,String ids,HttpServletRequest request) throws Exception{
		permission.setPrimaryKeys(ids.split(","));
		return permissionService.delete(permission);
	}

	/**
	 * 保存方法
	 * @param Permission permission 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="该表执行物理删除更新时删除原有记录，添加新记录",operateFuncNm="save",operateDescribe="对该表执行物理删除更新时删除原有记录，添加新记录进行新增操作")
	public Map<String,Object> save(@Valid Permission permission,BindingResult result,
			HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		if(!"false".equals(resultMap.get("success"))){
			permissionService.save(permission);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param Permission permission 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-06-05 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="该表执行物理删除更新时删除原有记录，添加新记录",operateFuncNm="update",operateDescribe="对该表执行物理删除更新时删除原有记录，添加新记录进行更新操作")
	public Map<String, Object> update(Permission permission, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = permissionService.update(permission);
		if (flag == 1) {
			resultMap.put("success", "true");
		} else {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_SYS_002"));
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param Permission permission 实体类
	 * @return Permission 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="该表执行物理删除更新时删除原有记录，添加新记录",operateFuncNm="get",operateDescribe="对该表执行物理删除更新时删除原有记录，添加新记录进行单条查询操作")
	public Permission get(Permission permission,HttpServletRequest request) throws Exception{
		return permissionService.get(permission);
	}

	/**
	 * 删除方法
	 * @param Permission permission 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="batchDelete.action")
	@ResponseBody
	@ActionLog(operateModelNm="该表执行物理删除更新时删除原有记录，添加新记录",operateFuncNm="batchDelete",operateDescribe="对该表执行物理删除更新时删除原有记录，添加新记录进行删除")
	public Map<String, Object> batchDelete(Permission permission, String id,
			Long forderId, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Folder oldFolder = new Folder();
		oldFolder.setId(Long.valueOf(forderId));
		oldFolder.setDeleteFlag(0);
		oldFolder.setDmInRecycle(0);
		Folder folders = archiveFolderService.get(oldFolder);
		if (folders == null) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "error");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_034"));
			return resultMap;
		} else {
			permission.setId(Long.valueOf(id));
			Integer count = permissionService.delectPermission(permission);
			if (count == 1) {
				resultMap.put("success", "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
						MessageUtils.getMessage("JC_SYS_005"));
			}
			/*
			 * Folder folder = new Folder();
			 * folder.setId(Long.valueOf(forderId)); Folder fol =
			 * archiveFolderService.get(folder);
			 */
			audithisService.audithis(request, forderId,
					folders.getFolderName(),
					Constants.ARC_AUDITHIS_DATATYPE_DIR,
					Constants.ARC_AUDITHIS_AUTH, "文件夹权限的删除");
			return resultMap;
		}
	}

	/**
	 * 删除方法
	 * @param Permission permission 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="batchUpdate.action")
	@ResponseBody
	@ActionLog(operateModelNm="修改权限表",operateFuncNm="batchUpdate",operateDescribe="对权限表进行修改")
	public Map<String, Object> batchUpdate(String permissionValue,String id,String num,Long forderId,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Folder oldFolder = new Folder();
		oldFolder.setId(forderId);
		oldFolder.setDeleteFlag(0);
		oldFolder.setDmInRecycle(0);
		Folder folders = archiveFolderService.get(oldFolder);
		if (folders != null) {
			Permission permission = new Permission();
			permission.setId(Long.valueOf(id));
			permission = permissionService.get(permission);
			if (permission == null) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "error");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
						MessageUtils.getMessage("JC_OA_ARCHIVE_036"));
				return resultMap;
			}
			permission.setPermissionValue(PermissionUtil.permission(
					permission.getPermissionValue(), permissionValue, num));
			if (num.equals("1")) {
				if (permission.getPermissionValue().subSequence(0, 1)
						.equals("0")) {
					permission.setPermissionValue("0000000000");
				} else if (permission.getPermissionValue().subSequence(0, 1)
						.equals("1")
						&& !permission.getPermissionValue().subSequence(0, 1)
								.equals(permissionValue.substring(0, 1))) {
					permission.setPermissionValue("1111000000");
				}
			}
			Integer count = permissionService.update(permission);
			if (count == 1) {
				resultMap.put("success", "true");
			}
			/*
			 * Folder folder = new Folder();
			 * folder.setId(Long.valueOf(folderId)); Folder fol =
			 * archiveFolderService.get(folder);
			 */
			audithisService.audithis(request, forderId,
					folders.getFolderName(),
					Constants.ARC_AUDITHIS_DATATYPE_DIR,
					Constants.ARC_AUDITHIS_AUTH,
					"文件夹" + PermissionUtil.permissionType(num) + "权限的修改");
			return resultMap;
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "error");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_034"));
			return resultMap;
		}
	}
	
	/**
	 * 添加组织人员权限方法
	 * @param Permission permission 实体类
	 * @throws Exception
	 * @author 闻瑜
	 * @version  2014-06-20
	 */
	@RequestMapping(value="batchInsert.action")
	@ResponseBody
	@ActionLog(operateModelNm="修改权限表",operateFuncNm="batchInsert",operateDescribe="对组织人员权限添加")
	public Map<String, Object> batchInsert(Long folderId, String id, String text, String type,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Folder oldFolder = new Folder();
		oldFolder.setId(Long.valueOf(folderId));
		oldFolder.setDeleteFlag(0);
		oldFolder.setDmInRecycle(0);
		Folder folders = archiveFolderService.get(oldFolder);
        if(folders != null){
		Integer count = permissionService.batchSave(folderId, id, text, type);
		if (count == 1) {
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
		}
		audithisService.audithis(request, folderId,folders.getFolderName(), Constants.ARC_AUDITHIS_DATATYPE_DIR, Constants.ARC_AUDITHIS_AUTH, "文件夹权限的新增");
		return resultMap;
	 }else{
		 resultMap.put(GlobalContext.RESULT_SUCCESS, "error");
 		 resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,MessageUtils.getMessage("JC_OA_ARCHIVE_034"));
		return resultMap;
	 }
	}

	/**
	 * 修改组织人员权限方法
	 * @param Permission permission 实体类
	 * @throws Exception
	 * @author 闻瑜
	 * @version  2014-06-20
	 */
	@RequestMapping(value="updatePermission.action")
	@ResponseBody
	@ActionLog(operateModelNm="修改权限表",operateFuncNm="batchInsert",operateDescribe="对组织人员权限修改")
	public Map<String, Object> updatePermission(Long id,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Permission pm = new Permission();
		pm.setId(id);
		pm = permissionService.get(pm);
		if (pm == null) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "error");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_036"));
			return resultMap;
		} else {
			Folder oldFolder = new Folder();
			oldFolder.setId(pm.getFolderId());
			oldFolder.setDeleteFlag(0);
			oldFolder.setDmInRecycle(0);
			Folder folders = archiveFolderService.get(oldFolder);
			if (folders == null) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "error");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
						MessageUtils.getMessage("JC_OA_ARCHIVE_034"));
				return resultMap;
			} else {
				List<SubPermission> subPermission = permissionService
						.updatePermission(id);
				resultMap.put("success", "true");
				resultMap.put("subPermission", subPermission);
				return resultMap;
			}
		}
	}
	/**
	 * 添加组织人员权限方法
	 * @param Permission permission 实体类
	 * @throws Exception
	 * @author 闻瑜
	 * @version  2014-06-20
	 */
	@RequestMapping(value="batchPermission.action")
	@ResponseBody
	@ActionLog(operateModelNm="修改权限表",operateFuncNm="batchPermission",operateDescribe="对组织人员权限添加")
	public Map<String, Object> batchPermission(Long permissionId, String id, String text, String type,Long forderId,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Permission pm = new Permission();
		pm.setId(permissionId);
		pm = permissionService.get(pm);
		if (pm == null) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "error");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_OA_ARCHIVE_036"));
			return resultMap;
		} else {
			Integer count = permissionService.updateBatchPermission(
					permissionId, id, text, type);
			if (count > 0) {
				resultMap.put("success", "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
						MessageUtils.getMessage("JC_SYS_001"));
			}
			Folder folder = new Folder();
			folder.setId(Long.valueOf(forderId));
			Folder fol = archiveFolderService.get(folder);
			audithisService.audithis(request, forderId, fol.getFolderName(),
					Constants.ARC_AUDITHIS_DATATYPE_DIR,
					Constants.ARC_AUDITHIS_AUTH, "文件夹权限的修改");
			return resultMap;
		}
	}

	/**
	 * 权限查询
	 * @param Permission permission 实体类
	 * @throws Exception
	 * @author 闻瑜
	 * @version  2014-06-23
	 */
	@RequestMapping(value="queryPermission.action")
	@ResponseBody
	@ActionLog(operateModelNm="权限查询",operateFuncNm="queryPermission",operateDescribe="对权限查询")
	public Map<String, Object> queryPermission(Long folderId,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Permission permission = new Permission();
		permission.setFolderId(folderId);
		permission.setUserId(SystemSecurityUtils.getUser().getId());
		permission.setDeptId(SystemSecurityUtils.getUser().getDeptId());
		List<Permission> list = permissionService.queryPermission(permission);
		Folder folder = PermissionUtil.permissionValue(list);
		resultMap.put("folder", folder);
		return resultMap;
	}
	/**
	 * 权限查询
	 * @param Permission permission 实体类
	 * @throws Exception
	 * @author 盖旭
	 * @version  2014-06-23
	 */
	@RequestMapping(value="queryPermissionByFolder.action")
	@ResponseBody
	@ActionLog(operateModelNm="权限查询",operateFuncNm="queryPermission",operateDescribe="对权限查询")
	public boolean queryPermissionByFolder(Long folderId,HttpServletRequest request) throws Exception{
		Permission permission = new Permission();
		permission.setFolderId(folderId);
		permission.setUserId(SystemSecurityUtils.getUser().getId());
		permission.setDeptId(SystemSecurityUtils.getUser().getDeptId());
		permission.setOrgId(SystemSecurityUtils.getUser().getOrgId());
		List<Permission> list = permissionService.queryPermission(permission);
		return list == null || list.size() < 1;
	}
}