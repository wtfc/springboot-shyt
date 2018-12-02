package com.jc.oa.ic.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.ic.domain.ContactsGroup;
import com.jc.oa.ic.domain.validator.ContactsGroupValidator;
import com.jc.oa.ic.service.IContactsGroupService;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;


/**
 * @title 互动交流
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 曹杨
 * @version  2014-05-08
 */
 
@Controller
@RequestMapping(value="/ic/contactsGroup")
public class ContactsGroupController extends BaseController{
	
	@Autowired
	private IContactsGroupService contactsGroupService;
	
	@org.springframework.web.bind.annotation.InitBinder("contactsGroup")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new ContactsGroupValidator()); 
    }
	
	public ContactsGroupController() {
	}

	/**
	 * @description 分页查询方法
	 * @param ContactsGroup contactsGroup 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author 曹杨
	 * @version  2014-05-08 
	 */
	@ActionLog(operateModelNm="邮件联系组",operateFuncNm="manageList",operateDescribe="对邮件联系组进行查询")
	@RequestMapping(value="manageList.action")
	@ResponseBody
	public PageManager manageList(ContactsGroup contactsGroup,PageManager page,HttpServletRequest request){
		// 默认排序
		if (StringUtils.isEmpty(contactsGroup.getOrderBy())) {
			contactsGroup.addOrderByFieldDesc("t.CREATE_DATE");
		}
		request.getParameterMap();
		contactsGroup.setDeleteFlag(0);
		//contactsGroup.setCreateUserOrg(SystemSecurityUtils.getUser().getCreateUserOrg());
		contactsGroup.setCreateUser(SystemSecurityUtils.getUser().getId());
		//contactsGroup.setCreateUserDept(SystemSecurityUtils.getUser().getCreateUserDept());
		return contactsGroupService.query(contactsGroup, page);
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author 曹杨
	* @version  2014-05-08 
	*/
	@RequestMapping(value="manage.action")
	public String manage(HttpServletRequest request) throws Exception{
		return "ic/contactsGroup/contactsGroupInteract"; 
	}

/**
	 * @description 删除方法
	 * @param ContactsGroup contactsGroup 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 曹杨
	 * @version  2014-05-08
	 */
	@ActionLog(operateModelNm="邮件联系人组",operateFuncNm="delete",operateDescribe="对邮件联系人组进行删除")
	@RequiresPermissions("groups:delete")
	@RequestMapping(value="delete.action")
	@ResponseBody
	public Map<String, Object> delete(ContactsGroup contactsGroup,String ids,HttpServletRequest request) throws Exception{
		contactsGroup.setPrimaryKeys(ids.split(","));
		if(contactsGroupService.deleteGroup(contactsGroup) == contactsGroup.getPrimaryKeys().length){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
			return resultMap;
		}
		return null;
	}

	/**
	 * @description 保存方法
	 * @param ContactsGroup contactsGroup 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 曹杨
	 * @version  2014-05-08
	 */
	@ActionLog(operateModelNm="邮件联系人组",operateFuncNm="save",operateDescribe="对邮件联系人组进行保存")
	@RequiresPermissions("groups:add")
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid ContactsGroup contactsGroup,BindingResult result,HttpServletRequest request) throws Exception{
		
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 保存邮箱
		if (contactsGroupService.save(contactsGroup) == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param ContactsGroup contactsGroup 实体类
	* @return Integer 更新的数目
	* @author 曹杨
	* @version  2014-05-08 
	*/
	@ActionLog(operateModelNm="邮件联系人组",operateFuncNm="update",operateDescribe="对邮件联系人组进行修改")
	@RequiresPermissions("groups:update")
	@RequestMapping(value="update.action")
	@ResponseBody
	public Map<String, Object> update(ContactsGroup contactsGroup,BindingResult result,HttpServletRequest request) throws Exception{
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		ContactsGroup oldGroup =  new ContactsGroup();
		oldGroup.setId(contactsGroup.getId());
		oldGroup = contactsGroupService.get(oldGroup);
		if(oldGroup != null){
		contactsGroup.setModifyDate(oldGroup.getModifyDate());
		}
		Integer flag = contactsGroupService.updateContactsGroup(contactsGroup);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
			resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));
		}
		return resultMap;
	}
	
	 /** @description 验证联系人组名是否存在
		 * @param ContactsGroup contactsGroup 实体类
		 * @return String true不存在	false存在
		 * @throws Exception
		 * @author 曹杨
		 * @version 2014-05-04
		 */
	    @ActionLog(operateModelNm="邮件联系人组",operateFuncNm="checkGroupName",operateDescribe="对邮件联系人组进行组名重复检查")
		@RequestMapping(value = "checkGroupName.action")
		@ResponseBody
		public String checkGroupName(ContactsGroup contactsGroup, String groupNameOld,HttpServletRequest request) throws Exception {
			if(StringUtils.isEmpty(groupNameOld)){
				contactsGroup.setDeleteFlag(0);
				contactsGroup.setCreateUser(SystemSecurityUtils.getUser().getId());
				if(contactsGroupService.get(contactsGroup) == null){
					return "true";
				} else {
					return "false";
				}
			} else {
				return "true";
			}
		}
	/**
	 * @description 获取单条记录方法
	 * @param ContactsGroup contactsGroup 实体类
	 * @return ContactsGroup 查询结果
	 * @throws Exception
	 * @author 曹杨
	 * @version  2014-05-08
	 */
	@ActionLog(operateModelNm="邮件联系人组",operateFuncNm="get",operateDescribe="对邮件联系人组别进行单条查询")    
	@RequestMapping(value="get.action")
	@ResponseBody
	public ContactsGroup get(ContactsGroup contactsGroup,HttpServletRequest request) throws Exception{
		return contactsGroupService.get(contactsGroup);
	}
	
	/**
	 * 方法描述：获取联系人组别
	 * @param contactsGroup
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 曹杨
	 * @version  2014年5月8日
	 */
	@ActionLog(operateModelNm="邮件联系人组",operateFuncNm="groupList",operateDescribe="对邮件联系人组别进行查询")
	@RequestMapping(value="groupList.action")
	@ResponseBody
	public List<ContactsGroup> groupList(ContactsGroup contactsGroup,final PageManager page,
			HttpServletRequest request) throws Exception{
		// 默认排序
		if (StringUtils.isEmpty(contactsGroup.getOrderBy())) {
			contactsGroup.addOrderByFieldDesc("t.CREATE_DATE");
		}
		contactsGroup.setDeleteFlag(0);
		//contactsGroup.setCreateUserOrg(SystemSecurityUtils.getUser().getCreateUserOrg());
		contactsGroup.setCreateUser(SystemSecurityUtils.getUser().getId());
		//contactsGroup.setCreateUserDept(SystemSecurityUtils.getUser().getCreateUserDept());
		return contactsGroupService.queryAll(contactsGroup);
	}
}