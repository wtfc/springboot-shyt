package com.jc.system.security.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.CustomException;
import com.jc.system.common.service.ICommonService;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.SystemAuthorizingRealm.Principal;
import com.jc.system.security.domain.Role;
import com.jc.system.security.domain.User;
import com.jc.system.security.domain.validator.UserValidator;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.ActionLog;
import com.jc.system.common.util.BeanUtil;

/**
 * @title GOA2.0
 * @author 高研
 * @version 2014-03-18
 * 
 */
@Controller
@RequestMapping(value = "/sys/user")
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	ICommonService commonService;
	
	@org.springframework.web.bind.annotation.InitBinder("user")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new UserValidator());
	}

	public UserController() {
		
	}
	
	private UserValidator uv = new UserValidator();
	
	
	/**
	 * 分页查询方法
	 * @param User
	 *            user 实体类
	 * @param PageManager
	 *            page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequiresPermissions("user:select")
	@RequestMapping(value = "manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="用户设置",operateFuncNm="manageList",operateDescribe="对用户设置进行查询")
	public PageManager manageList(User user,final PageManager page,
			HttpServletRequest request) {
		
		//默认排序
		if(StringUtils.isEmpty(user.getOrderBy())) {
			user.addOrderByField("t.ORDER_NO");
		}
		
		if(user.getStatus() != null && user.getStatus().equals(GlobalContext.USER_STATUS_3)){
			user.setDeleteFlag(1);
		} else {
			user.setDeleteFlag(null);
		}
		return userService.query(user, page);
	}

	
	@RequestMapping(value = "userAllList.action")
	@ResponseBody
	public PageManager userAllList(User user, final PageManager page, HttpServletRequest request) {
		user.addOrderByField("t.ORDER_NO");
		user.setDeleteFlag(0);
		return userService.queryAllUsersForOrder(user, page);
	}
	
	/**
	 * 跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "manageAll.action")
	public String manageAll(Model model, HttpServletRequest request) throws Exception {
		return "sys/user/userAll";
	}
	
	/**
	 * 跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequiresPermissions("user:select")
	@RequestMapping(value = "manage.action")
	public String manage(Model model, HttpServletRequest request) throws Exception {
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			if(userInfo.getIsAdmin() == 1){
				return "sys/user/user";				
			} else {
				return "error/unauthorized";
			}
		}
		return "error/unauthorized";
	}

	
	@RequestMapping(value = "userEdit.action")
	public String userEdit(Model model, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		List<Role> roleList = commonService.getRolesForUser();
		map.put(GlobalContext.SESSION_TOKEN, token);
		map.put("roleList", roleList);
		model.addAttribute("data", map);
		return "sys/user/userEdit";
	}


	/**
	 * 删除方法
	 * @param User
	 *            user 实体类
	 * @param String
	 *            ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequiresPermissions("user:delete")
	@RequestMapping(value = "deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="用户设置",operateFuncNm="deleteByIds",operateDescribe="对用户设置进行删除")
	public Map<String, Object> deleteByIds(User user, String ids, HttpServletRequest request) throws Exception {
		Map<String, Object> map = SystemSecurityUtils.loginState(ids);
		String onLine = (String) map.get("onLine");
		String deleteIds = (String) map.get("noOnLine");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(deleteIds.length() > 0){
			user.setPrimaryKeys(deleteIds.split(","));
			if(userService.delete(user) == 1){
				if(onLine.length() > 0){
					resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_078") + onLine + MessageUtils.getMessage("JC_SYS_079"));
				} else {
					resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
					resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
				}
				return resultMap;
			}
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_078") +onLine+ MessageUtils.getMessage("JC_SYS_080"));
			return resultMap;
		}
		return null;
	}

	/**
	 * 保存方法
	 * @param User
	 *            user 实体类
	 * @param BindingResult
	 *            result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequiresPermissions("user:add")
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="用户设置",operateFuncNm="save",operateDescribe="对用户设置进行添加") 
	public Map<String, Object> save(@RequestBody Map<String, Object> userMap, BindingResult result,
			HttpServletRequest request) throws Exception {
		
		User user = BeanUtil.map2Object(userMap, User.class);
		uv.validate(user, result);
		
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request, (String)getMapValue(userMap, GlobalContext.SESSION_TOKEN));
		if (resultMap.size() > 0) {
			return resultMap;
		}

		// 保存用户
		if (userService.save(user) == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}
		return resultMap;
	}
	
	/**
	 * 初始化密码
	 * @param User
	 *            user 实体类
	 * @return Integer 更新的数目
	 * @author
	 * @version 2014-03-18
	 */
	@RequiresPermissions("user:initPwd")
	@RequestMapping(value = "initPassword.action")
	@ResponseBody
	@ActionLog(operateModelNm="用户设置",operateFuncNm="initPassword",operateDescribe="对用户设置进行密码初始化") 
	public Map<String, Object> initPassword(User user, HttpServletRequest request) throws Exception {
		if(userService.initPassword(user) == 1){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_081"));
			return resultMap;
		}
		return null;
	}
	
	/**
	 * 验证用户名是否存在
	 * @param User user 实体类
	 * @return String true不存在	false存在
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "checkLoginName.action")
	@ResponseBody
	public String checkLoginName(User user, String loginNameOld) throws Exception {
		if(StringUtils.isEmpty(loginNameOld)){
			user.setDeleteFlag(null);
			if(userService.get(user) == null){
				return "true";
			} else {
				return "false";
			}
		} else {
			return "true";
		}
	}
	
	/**
	 * 验证手机号是否存在
	 * @param User user 实体类
	 * @return String true不存在	false存在
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "checkMobile.action")
	@ResponseBody
	public String checkMobile(User user, String mobileOld) throws Exception {
		if(user.getId() == 0){
			user.setId(null);
		}
		if(userService.checkMobile(user) == null){
			return "true";
		} else {
			return "false";
		}
	}

	/**
	 * 修改方法
	 * @param User
	 *            user 实体类
	 * @return Integer 更新的数目
	 * @author
	 * @version 2014-03-18
	 */
	@RequiresPermissions("user:update")
	@RequestMapping(value = "update.action")
	@ResponseBody
	@ActionLog(operateModelNm="用户设置",operateFuncNm="update",operateDescribe="对用户设置进行修改")
	public Map<String, Object> update(@RequestBody Map<String, Object> userMap, BindingResult result,
			HttpServletRequest request) throws Exception {
		
		User user = BeanUtil.map2Object(userMap, User.class);
		uv.validate(user, result);
		
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		//如果修改用户状态那么清空密码错误次数
		String statusOld = (String) getMapValue(userMap, "statusOld");
		if(!statusOld.equals(user.getStatus())){
			user.setWrongCount(0);
		}
		Integer flag = userService.updateUser(user);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param User
	 *            user 实体类
	 * @return User 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequiresPermissions("user:update")
	@RequestMapping(value = "get.action")
	@ResponseBody
	public User get(User user, HttpServletRequest request) throws Exception {
		user.setDeleteFlag(null);
		user = userService.getUser(user);
		return user;
	}
	
	@RequestMapping(value = "userInfo.action")
	public String userInfo(Model model) throws Exception {
		return "sys/user/userInfo";
	}
	
	/**
	 * 个人信息获取信息方法
	 * @param User
	 *            user 实体类
	 * @return User 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "getUserInfo.action")
	@ResponseBody
	public Map<String, Object> getUserInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(GlobalContext.SESSION_TOKEN, getToken(request));
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			User user = new User();
			user.setId(userInfo.getId());
			user.setDeleteFlag(0);
			user = userService.get(user);
			map.put("user", user);
		}
		return map;
	}
	
	/**
	 * 修改个人信息
	 * @param User
	 *            user 实体类
	 * @return User 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "userInfoUpdate.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人信息",operateFuncNm="userInfoUpdate",operateDescribe="对个人信息进行修改")
	public Map<String,Object> userInfoUpdate(User user,BindingResult result,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		// 验证token
		resultMap = validateToken(request);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		int state = userService.updateUserInfo(user);
		if(state == 1){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
			User u = new User();
			u.setId(user.getId());
			u = userService.get(u);
			resultMap.put("modifyDate", DateUtils.formatDate(u.getModifyDate(), "yyyy-MM-dd HH:mm:ss"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_032"));
		}
		resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		return resultMap;
	}
	
	@RequestMapping(value = "userPwd.action")
	public String userPwd(Model model ,HttpServletRequest request) throws Exception {
		model.addAttribute(GlobalContext.SESSION_TOKEN, getToken(request));
		return "sys/user/userPwd";
	}
	
	@RequestMapping(value = "checkUserPwd.action")
	@ResponseBody
	public Boolean checkUserPwd(User user, HttpServletRequest request) throws Exception{
		User userInfo = SystemSecurityUtils.getUser();
		User u = new User();
		u.setId(userInfo.getId());
		u = userService.get(u);
		if(SystemSecurityUtils.validatePassword(user.getPassword(), u.getPassword())){
			return true;
		} else {
			return false;
		}
	}
	
	@RequestMapping(value = "userPwdModify.action")
	@ResponseBody
	@ActionLog(operateModelNm="用户密码",operateFuncNm="userPwdModify",operateDescribe="对用户密码进行修改")
	public Map<String,Object> userPwdModify(User user,BindingResult result,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			// 验证token
			resultMap = validateToken(request);
			if (resultMap.size() > 0) {
				return resultMap;
			}
			User u = new User();
			u.setId(userInfo.getId());
			u = userService.get(u);
			if(SystemSecurityUtils.validatePassword(user.getPassword(), u.getPassword())){
				u = null;
				u = new User();
				u.setId(userInfo.getId());
				u.setPassword(SystemSecurityUtils.entryptPassword(user.getNewPassword()));
				u.setModifyPwdFlag(1);
				if(userService.update2(u) == 1){
					resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
					resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_042"));
					SystemSecurityUtils.setFirstLoginState();
				}
			} else {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_082"));
			}
		}
		resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		return resultMap;
	}
	
	@RequestMapping(value = "forgetPwd.action")
	@ResponseBody
	public Map<String,Object> forgetPwd(User user,BindingResult result,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		User u = new User(); 
		u.setLoginName(user.getLoginName());
		u = userService.get(u);
		if(u == null){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_083"));
		} else {
			if(StringUtils.isNotEmpty(u.getQuestion()) && StringUtils.isNotEmpty(u.getAnswer())){
				if(u.getQuestion().trim().equals(user.getQuestion().trim()) && u.getAnswer().trim().equals(user.getAnswer().trim())){
					User newUser = new User();
					newUser.setPassword(SystemSecurityUtils.entryptPassword(GlobalContext.PASSWORD_DEFAULT_VALUE));
					newUser.setId(u.getId());
					if(userService.update2(newUser) == 1){
						resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
						resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_084")+GlobalContext.PASSWORD_DEFAULT_VALUE+MessageUtils.getMessage("JC_SYS_085"));
					} else {
						resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
						resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_086"));
					}
				} else {
					resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_087"));
				}
			} else {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_088"));
			}
		}
		
		return resultMap;
	}
	
	@RequestMapping(value = "duressPassword.action")
	public String duressPassword(Model model, HttpServletRequest request) throws Exception {
		model.addAttribute(GlobalContext.SESSION_TOKEN, getToken(request));
		return "sys/user/duressPassword";
	}
	
	@RequestMapping(value = "isFirstLogin.action")
	@ResponseBody
	public boolean isFirstLogin() throws Exception {
		return SystemSecurityUtils.isFirstLogin();
	}
	
	@RequestMapping(value = "skin.action")
	@ResponseBody
	public Map<String, Object> skin(String theme, HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(theme)){
			Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
			User userInfo = SystemSecurityUtils.getUser();
			User user = new User();
			user.setId(userInfo.getId());
			user.setTheme(theme);
			if(userService.update2(user) == 1){
				principal.setTheme(theme);
				map.put("theme", theme);
				map.put(GlobalContext.RESULT_SUCCESS, "true");
			}
		}
		return map;
	}
	
	/**
	 * 修改方法
	 * @param User
	 *            user 实体类
	 * @return Integer 更新的数目
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "updateUserOrder.action")
	@ResponseBody
	public Integer updateUserOrder(User user, HttpServletRequest request) throws Exception {
		
		String userId = request.getParameter("userId");
		String orderNo = request.getParameter("orderNo");
		String oUserId = request.getParameter("oUserId");
		String oOrderNo = request.getParameter("oOrderNo");
		
		if(StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(orderNo) 
				&& StringUtils.isNotEmpty(oUserId) && StringUtils.isNotEmpty(oOrderNo)){
			
			return userService.updateUserOrder(userId, orderNo, oUserId, oOrderNo);
		}
		else if(StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(orderNo)){
			user.setId(Long.parseLong(userId));
			user.setOrderNo(Integer.parseInt(orderNo));
			return userService.update2(user);
		}
		return null;
	}
}