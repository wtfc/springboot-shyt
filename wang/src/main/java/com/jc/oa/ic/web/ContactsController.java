package com.jc.oa.ic.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.constants.OrderType;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.ic.domain.Contacts;
import com.jc.oa.ic.domain.ContactsGroup;
import com.jc.oa.ic.domain.Suggest;
import com.jc.oa.ic.domain.validator.ContactsValidator;
import com.jc.oa.ic.service.IConGroupRService;
import com.jc.oa.ic.service.IContactsGroupService;
import com.jc.oa.ic.service.IContactsService;
import com.jc.oa.po.worklog.domain.Worklog;
import com.jc.oa.po.worklog.domain.WorklogContent;
import com.jc.system.common.util.ExcuteExcelUtil;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IDepartmentService;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.ActionLog;


/**
 * @title 邮件联系人
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 曹杨
 * @version  2014-05-04
 */
 
@Controller
@RequestMapping(value="/ic/contacts")
public class ContactsController extends BaseController{
	
	@Autowired
	private IContactsService contactsService;
	@Autowired
	private IConGroupRService conGroupRService;
	@Autowired
	private IContactsGroupService contactsGroupService;
	@Autowired
	private IUserService userService;	
	@Autowired
	private IDepartmentService deptService;
	
	@org.springframework.web.bind.annotation.InitBinder("contacts")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new ContactsValidator()); 
    }
	
	public ContactsController() {
	}

	/**
	 * @description 保存方法
	 * @param Contacts contacts 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 曹杨
	 * @version  2014-05-04
	 */
	@ActionLog(operateModelNm="邮件联系人",operateFuncNm="save",operateDescribe="对邮件联系人进行保存")
	@RequiresPermissions("contacts:add")
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid Contacts contacts,BindingResult result,HttpServletRequest request) throws Exception{
		
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
		// 保存联系人
		Integer resultInteger = contactsService.save(contacts);
		if (resultInteger >= 1) {
			resultMap.put("saveOverId",resultInteger );
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param Contacts contacts 实体类
	* @return Integer 更新的数目
	* @author 曹杨
	* @version  2014-05-04 
	*/
	@ActionLog(operateModelNm="邮件联系人",operateFuncNm="update",operateDescribe="对邮件联系人进行修改")
	@RequiresPermissions("contacts:update")
	@RequestMapping(value="update.action")
	@ResponseBody
	public Map<String, Object> update(Contacts contacts,BindingResult result,HttpServletRequest request) throws Exception{
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Contacts oldContacts =  new Contacts();
		oldContacts.setId(contacts.getId());
		oldContacts = contactsService.get(oldContacts);
		if(oldContacts != null){
			contacts.setModifyDate(oldContacts.getModifyDate());
		}
		Integer flag = contactsService.updateContacts(contacts);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
			resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));
		}
		return resultMap;
	}

	/**
	 * @description 获取单条记录方法
	 * @param Contacts contacts 实体类
	 * @return Contacts 查询结果
	 * @throws Exception
	 * @author 曹杨
	 * @version  2014-05-04
	 */
	@ActionLog(operateModelNm="邮件联系人",operateFuncNm="get",operateDescribe="对邮件联系人进行单条记录查询")
	@RequestMapping(value="get.action")
	@ResponseBody
	public Contacts get(Contacts contacts,HttpServletRequest request) throws Exception{
		return contactsService.get(contacts);
	}

	/**
	 * @description 分页查询方法
	 * @param Contacts contacts 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author 曹杨
	 * @version  2014-05-04 
	 */
	@ActionLog(operateModelNm="邮件联系人",operateFuncNm="manageList",operateDescribe="对邮件联系人进行分页记录查询")
	@RequestMapping(value="manageList.action")
	@ResponseBody
	public PageManager manageList(Contacts contacts,PageManager page,HttpServletRequest request){
		// 默认排序
		if (StringUtils.isEmpty(contacts.getOrderBy())) {
			contacts.addOrderByFieldDesc("t.CREATE_DATE");
		}
		request.getParameterMap();
		contacts.setDeleteFlag(0);
		//contacts.setCreateUserOrg(SystemSecurityUtils.getUser().getCreateUserOrg());
		contacts.setCreateUser(SystemSecurityUtils.getUser().getId());
		//contacts.setCreateUserDept(SystemSecurityUtils.getUser().getCreateUserDept());
		return contactsService.query(contacts, page);
	}

	
	/**
	 * 方法描述：内部联系人导出Excel
	 * @param User user
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014年11月17日上午9:42:17
	 * @see
	 */
	@RequestMapping(value="exportExcelForInternal.action")
	@SuppressWarnings({ "unchecked", "deprecation" })
	@ResponseBody
	@ActionLog(operateModelNm="互动交流_邮件联系人",operateFuncNm="exportExcelForInternal",operateDescribe="内部联系人导出数据")
	public void exportExcelForInternal(User user,PageManager page,HttpServletRequest request,HttpServletResponse response) throws Exception{
		// 默认排序
		if (StringUtils.isEmpty(user.getOrderBy())) {
			user.addOrderByField("t.ORDER_NO",OrderType.ASC);
		}
		//获取当前用户的组织的下的所有部门
		String deptIds = deptService.getDeptIdsByOrgId();
		if(user.getDeptIds() == null || "".equals(user.getDeptIds())){
			user.setDeptIds(deptIds);
		}
		user.setDeleteFlag(0);
		if("".equals(user.getDisplayName()))
			user.setDisplayName(null);
		if("".equals(user.getSex()))
			user.setSex(null);
		if("".equals(user.getOfficeTel()))
			user.setOfficeTel(null);
		if("".equals(user.getMobile()))
			user.setMobile(null);
		if("".equals(user.getGroupTel()))
			user.setGroupTel(null);
		if("".equals(user.getEmail()))
			user.setEmail(null);
		if(user.getEmail() != null){	
			user.setEmail(StringUtil.escapeSQLWildcard(user.getEmail()));
		}
		page.setPageRows(-1);//导出所有数据不分页
		PageManager page_ = userService.queryAllUsers(user, page);
		List<User> userList = (List<User>)page_.getAaData();
		List<User> list = new ArrayList<User>();
		//循环列表将性别一栏的值替换成男、女和未知
		if(userList.size() > 0 ){
			for (int i = 0; i < userList.size(); i++) {
				User u = (User)userList.get(i);
				if("sex_0".equals(u.getSex())){
					u.setSex("未知的性别");
				}else if("sex_1".equals(u.getSex())){
					u.setSex("男");
				}else if("sex_2".equals(u.getSex())){
					u.setSex("女");
				}else{
					u.setSex(null);
				}
				list.add(u);
			}
		}
		List<String> head = new ArrayList<String>();
		head.add("姓名");
		head.add("性别");
		head.add("部门");
		head.add("办公电话");
		head.add("移动电话");
		head.add("集团号码");
		head.add("邮箱地址");
		List<String> column = new ArrayList<String>();
		column.add("displayName");
		column.add("sex");
		column.add("deptName");
		column.add("officeTel");
		column.add("mobile");
		column.add("groupTel");
		column.add("email");
		ExcuteExcelUtil.setDateFormat("yyyy-MM-dd");
		ExcuteExcelUtil.exportExcel(head,column,list,response);
	}
	/**
	 * 方法描述：外部联系人导出Excel
	 * @param Contacts contacts
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014年11月17日上午9:42:17
	 * @see
	 */
	@RequestMapping(value="exportExcelForExternal.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="互动交流_邮件联系人",operateFuncNm="exportExcelForExternal",operateDescribe="外部联系人导出数据")
	public void exportExcelForExternal(Contacts contacts,PageManager page,HttpServletRequest request,HttpServletResponse response) throws Exception{
		// 默认排序
		if (StringUtils.isEmpty(contacts.getOrderBy())) {
			contacts.addOrderByFieldDesc("d.GROUP_NAME");
		}
		if("".equals(contacts.getUserName()))
			contacts.setUserName(null);
		if("".equals(contacts.getSex()))
			contacts.setSex(null);
		if("".equals(contacts.getSimpleName()))
			contacts.setSimpleName(null);
		if("".equals(contacts.getPhone()))
			contacts.setPhone(null);
		if("".equals(contacts.getGroupId()))
			contacts.setGroupId(null);
		if("".equals(contacts.getMail()))
			contacts.setMail(null);
		if(contacts.getMail() != null){
			contacts.setMail(StringUtil.escapeSQLWildcard(contacts.getMail()));
		}
		contacts.setCreateUser(SystemSecurityUtils.getUser().getId());
		page.setPageRows(-1);//导出所有数据不分页
		PageManager page_ = contactsService.queryContacts(contacts, page);
		List<Contacts> contactsList = (List<Contacts>)page_.getAaData();
		List<Contacts> list = new ArrayList<Contacts>();
		//循环列表将性别一栏的值替换成男、女和未知
		if(contactsList.size() > 0 ){
			for (int i = 0; i < contactsList.size(); i++) {
				Contacts c = (Contacts)contactsList.get(i);
				if("sex_0".equals(c.getSex())){
					c.setSex("未知的性别");
				}else if("sex_1".equals(c.getSex())){
					c.setSex("男");
				}else if("sex_2".equals(c.getSex())){
					c.setSex("女");
				}else{
					c.setSex(null);
				}
				list.add(c);
			}
		}
		List<String> head = new ArrayList<String>();
		head.add("组别");
		head.add("姓名");
		head.add("简称");
		head.add("性别");
		head.add("移动电话");
		head.add("邮箱地址");
		List<String> column = new ArrayList<String>();
		column.add("groupName");
		column.add("userName");
		column.add("simpleName");
		column.add("sex");
		column.add("phone");
		column.add("mail");
		ExcuteExcelUtil.setDateFormat("yyyy-MM-dd");
		ExcuteExcelUtil.exportExcel(head,column,list,response);
	}
	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author 曹杨
	* @version  2014-05-04 
	*/
	@ActionLog(operateModelNm="邮件联系人",operateFuncNm="manage",operateDescribe="跳转邮件联系人页面")
	@RequestMapping(value="manage.action")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		// 默认排序
		ContactsGroup contactsGroup = new ContactsGroup();
		if (StringUtils.isEmpty(contactsGroup.getOrderBy())) {
			contactsGroup.addOrderByFieldDesc("t.CREATE_DATE");
		}
		contactsGroup.setDeleteFlag(0);
		contactsGroup.setCreateUser(SystemSecurityUtils.getUser().getId());
		List<ContactsGroup> groupList = contactsGroupService.queryAll(contactsGroup);
		model.addAttribute("groupList",groupList);
		model.addAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));
		return "ic/contacts/contactsInteract"; 
	}

/**
	 * @description 删除方法
	 * @param Contacts contacts 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 曹杨
	 * @version  2014-05-04
	 */
	@ActionLog(operateModelNm="邮件联系人",operateFuncNm="delete",operateDescribe="对邮件联系人进行删除")
	@RequiresPermissions("contacts:delete")
	@RequestMapping(value="delete.action")
	@ResponseBody
	public Map<String, Object> delete(Contacts contacts,String ids,HttpServletRequest request) throws Exception{
		contacts.setPrimaryKeys(ids.split(","));
		if(contactsService.deleteContacts(contacts) == contacts.getPrimaryKeys().length){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
			return resultMap;
		}
		return null;
	}
	/**
	 * 关联联系人分组表分页查询
	 * @param contacts
	 * @param page
	 * @return
	 * @throws Exception
	 * @author 曹杨
	 */
	@ActionLog(operateModelNm="邮件联系人",operateFuncNm="getContactsList",operateDescribe="对邮件联系人及分组进行关联查询")
	@RequestMapping(value = "getContactsList.action")
	@ResponseBody
	public PageManager getContactsList(Contacts contacts,PageManager page,HttpServletRequest request)  {
		// 默认排序
		if (StringUtils.isEmpty(contacts.getOrderBy())) {
			contacts.addOrderByFieldDesc("d.GROUP_NAME");
		}
		if(contacts.getMail() != null){
			contacts.setMail(StringUtil.escapeSQLWildcard(contacts.getMail()));
		}
		//contacts.setCreateUserOrg(SystemSecurityUtils.getUser().getCreateUserOrg());
		contacts.setCreateUser(SystemSecurityUtils.getUser().getId());
		//contacts.setCreateUserDept(SystemSecurityUtils.getUser().getCreateUserDept());
		PageManager page_ = contactsService.queryContacts(contacts, page);
		return page_; 
	}
	
   /** @description 验证联系人是否存在
	 * @param Contacts contacts 实体类
	 * @return String true不存在	false存在
	 * @throws Exception
	 * @author 曹杨
	 * @version 2014-05-04
	 */
	@ActionLog(operateModelNm="邮件联系人",operateFuncNm="checkUserName",operateDescribe="对邮件联系人姓名检查是否重复")
	@RequestMapping(value = "checkUserName.action")
	@ResponseBody
	public String checkUserName(Contacts contacts, String userNameOld,HttpServletRequest request) throws Exception {
		if(StringUtils.isEmpty(userNameOld)){
			contacts.setDeleteFlag(0);
			contacts.setCreateUser(SystemSecurityUtils.getUser().getId());
			if(contactsService.get(contacts) == null){
				return "true";
			} else {
				return "false";
			}
		} else {
			return "true";
		}
	}
	
	/**
	 * 方法描述：验证联系人手机号是否存在
	 * @param contacts
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年8月6日上午10:05:16
	 * @see
	 */
	
	@ActionLog(operateModelNm="联系人",operateFuncNm="checkMobile",operateDescribe="对联系人手机号检查是否重复")
	@RequestMapping(value = "checkMobile.action")
	@ResponseBody
	public String checkMobile(Contacts contacts,HttpServletRequest request) throws Exception {
		if(StringUtils.isEmpty(contacts.getPhoneOld())){
			if(contactsService.getMobile(contacts) == null){
				return "true";
			} else {
	 			return "false";
			}
		} else {
			if(contacts.getPhoneOld().equals(contacts.getPhone())){
				return "true";
			}else{
				if(contactsService.getMobile(contacts) == null){
					return "true";
				} else {
		 			return "false";
				}
			}
		}
	}
	
	/**
	 * 方法描述：获取人员信息
	 * @param user
	 * @param page
	 * @return
	 * @throws Exception
	 * @author 曹杨
	 * @version  2014年5月7日上午8:45:46
	 * @see
	 */
	@ActionLog(operateModelNm="邮件联系人",operateFuncNm="getUserList",operateDescribe="获取内部联系人员信息")
	@RequestMapping(value = "getUserList.action")
	@ResponseBody
	public PageManager getUserList(User user,PageManager page,HttpServletRequest request) throws Exception {
		// 默认排序
		if (StringUtils.isEmpty(user.getOrderBy())) {
			user.addOrderByField("t.ORDER_NO",OrderType.ASC);
		}
		//获取当前用户的组织的下的所有部门
		String deptIds = deptService.getDeptIdsByOrgId();
		if(user.getDeptIds() == null || "".equals(user.getDeptIds())){
			user.setDeptIds(deptIds);
		}
		user.setDeleteFlag(0);
		if(user.getEmail() != null){	
			user.setEmail(StringUtil.escapeSQLWildcard(user.getEmail()));
		}
		PageManager page_ = userService.queryAllUsers(user, page);
		return page_; 
	}
	
	/**
	 * @description 获取联系人及分组单条记录
	 * @param Contacts contacts 实体类
	 * @return Contacts 查询结果
	 * @throws Exception
	 * @author 曹杨
	 * @version  2014-05-04
	 */
	@ActionLog(operateModelNm="邮件联系人",operateFuncNm="getContacts",operateDescribe="获取联系人及分组单条记录信息")
	@RequestMapping(value="getContacts.action")
	@ResponseBody
	public Contacts getContacts(Contacts contacts,HttpServletRequest request) throws Exception{
		// 默认排序
		if (StringUtils.isEmpty(contacts.getOrderBy())) {
			contacts.addOrderByFieldDesc("t.CREATE_DATE");
		}
		contacts.setDeleteFlag(0);
		//contacts.setCreateUserOrg(SystemSecurityUtils.getUser().getCreateUserOrg());
		contacts.setCreateUser(SystemSecurityUtils.getUser().getId());
		//contacts.setCreateUserDept(SystemSecurityUtils.getUser().getCreateUserDept());
		Contacts c = contactsService.getContacts(contacts);
		return c;
	}
	/**
	 * 方法描述：根据条件查询邮件联系人
	 * @param contacts
	 * @return
	 * @throws Exception
	 * @author 曹杨
	 * @version  2014年5月27日
	 * @see
	 */
	@ActionLog(operateModelNm="邮件联系人",operateFuncNm="searchContactsList",operateDescribe="获取联系人记录信息")
	@RequestMapping(value="searchContactsList.action")
	@ResponseBody
	public List<Contacts> searchContactsList(Contacts contacts,HttpServletRequest request) throws Exception{
		contacts.setDeleteFlag(0);
		return contactsService.queryAll(contacts);
	}
	
	
	@ActionLog(operateModelNm="邮件联系人",operateFuncNm="getContacts",operateDescribe="外部联系人组件")
	@RequestMapping(value="queryOutSideUser.action")
	@ResponseBody
	public Contacts queryOutSideUser(Contacts contacts,HttpServletRequest request) throws Exception{
		contacts.setDeleteFlag(0);
	//	contacts.setCreateUserOrg(SystemSecurityUtils.getUser().getCreateUserOrg());
		contacts.setCreateUser(SystemSecurityUtils.getUser().getId());
		//contacts.setCreateUserDept(SystemSecurityUtils.getUser().getCreateUserDept());
		Contacts c = contactsService.queryOutSideUser(contacts);
		return c;
	}
	@ActionLog(operateModelNm="邮件联系人",operateFuncNm="getContacts",operateDescribe="获得外部联系人树")
	@RequestMapping(value="getOutSideUserTree.action")
	@ResponseBody
	public List<Contacts> getOutSideUserTree(Contacts contacts,HttpServletRequest request) throws Exception{
		//contacts.setCreateUserOrg(SystemSecurityUtils.getUser().getCreateUserOrg());
		contacts.setCreateUser(SystemSecurityUtils.getUser().getId());
		//contacts.setCreateUserDept(SystemSecurityUtils.getUser().getCreateUserDept());
		List<Contacts> list = contactsService.getOutSideUserTree(contacts);
		return list;
	}

	/**
	 * @description 验证邮箱是否存在
	 * @param Contacts
	 *            contacts 实体类
	 * @return String true不存在 false存在
	 * @throws Exception
	 * @author 曹杨
	 * @version 2014-07-09
	 */
	@ActionLog(operateModelNm = "邮件联系人", operateFuncNm = "checkMail", operateDescribe = "对邮件检查是否重复")
	@RequestMapping(value = "checkMail.action")
	@ResponseBody
	public String checkMail(Contacts contacts, String mailOld,
			HttpServletRequest request) throws Exception {
		if (StringUtils.isEmpty(mailOld)) {
			contacts.setDeleteFlag(0);
			contacts.setCreateUser(SystemSecurityUtils.getUser().getId());
			if (contactsService.get(contacts) == null) {
				return "true";
			} else {
				return "false";
			}
		} else {
			if (mailOld.equals(contacts.getMail())) {
				return "true";
			} else {
				if (contactsService.get(contacts) == null) {
					return "true";
				} else {
					return "false";
				}
			}
		}
	}
	/**
	 * 方法描述：div跳转方法
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 曹杨
	 * @version  2014年10月8日下午4:01:42
	 * @see
	 */
	@ActionLog(operateModelNm="邮件联系人",operateFuncNm="contactsDiv",operateDescribe="跳转邮件联系人页面弹出层")
	@RequestMapping(value = "contactsDiv.action")
	public String contactsDiv(Model model,HttpServletRequest request) throws Exception {
		return "ic/contacts/contactsInteractDiv";
	}
	/**
	 * 方法描述：联系人组Div跳转方法
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 曹杨
	 * @version  2014年10月8日下午4:30:05
	 * @see
	 */
	@ActionLog(operateModelNm="邮件联系人",operateFuncNm="groupDiv",operateDescribe="跳转邮件联系人组页面弹出层")
	@RequestMapping(value = "groupDiv.action")
	public String groupDiv(HttpServletRequest request) throws Exception {
		return "ic/contacts/contactsGroupInteractDiv";
	}
}