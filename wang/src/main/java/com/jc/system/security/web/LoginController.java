package com.jc.system.security.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.web.BaseController;
import com.jc.system.CustomException;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.SystemAuthorizingRealm.Principal;
import com.jc.system.security.domain.User;
import com.jc.system.security.exception.OnlineCountException;
import com.jc.system.security.exception.UserDisabledException;
import com.jc.system.security.exception.UserIpException;
import com.jc.system.security.exception.UserLockedException;
import com.jc.system.security.exception.UserPasswordException;
import com.jc.system.security.service.IDepartmentService;
import com.jc.system.security.service.ISystemService;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.SettingUtils;

/**
 * 
 * @title GOA V2.0
 * @description 登录Controller
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * @version  2014年5月12日
 */
@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private ISystemService systemService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IDepartmentService departmentService;
	
	
	/**
	 * 桌面精灵登录
	 * @param request response
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "loginForDesktop", method = RequestMethod.GET)
	public String loginForDesktop(HttpServletRequest request, HttpServletResponse response, Model model) throws CustomException{
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) { 
			systemService.logoutCallBack(request);
			System.out.println(subject.getSession().getId());
			subject.logout();
		}
		//departmentService.clearDeptAndUserCache();
		return "sys/loginForDesktop";
	}
	

	/**
	 * 管理登录
	 * @param request response
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		String kickout = request.getParameter("kickout");
		String timeout = request.getParameter("timeout");
		if(StringUtils.isNotEmpty(kickout)){
			request.getSession().setAttribute("kickoutMes", true);
		} else {
			if(request.getSession().getAttribute("kickoutMes") == null){
				if(StringUtils.isNotEmpty(timeout))
					model.addAttribute("timeout", true);
			}
		}
		return "sys/login";
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 * @param request Model
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(Model model, HttpServletRequest request) {
		String username = request.getParameter("username");
		String exceptionClassName = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if(UserDisabledException.class.getName().equals(exceptionClassName)){
        	model.addAttribute(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_098"));
        } 
		else if(UserLockedException.class.getName().equals(exceptionClassName)){
        	model.addAttribute(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_099"));
        }
		else if(UserIpException.class.getName().equals(exceptionClassName)){
        	model.addAttribute(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_100"));
        }
		else if(UserPasswordException.class.getName().equals(exceptionClassName)){
			if(username != null){
				try {
					User user = new User();
					user.setLoginName(username);
					user = userService.get(user);
					if(user.getStatus().equals(GlobalContext.USER_STATUS_2)){
						model.addAttribute(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_099"));
					} else {
						int count = Integer.parseInt(SettingUtils.getSetting("maxErrorCount").toString()) - user.getWrongCount();
						model.addAttribute(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_101")+ count + MessageUtils.getMessage("JC_SYS_102"));
					}
				} catch (CustomException e) {
					e.printStackTrace();
				}
			}
        }
		else if(UnknownAccountException.class.getName().equals(exceptionClassName)){
			model.addAttribute(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_103"));
		} else if(OnlineCountException.class.getName().equals(exceptionClassName)){
			model.addAttribute(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_124"));
		}
		else {
			User userInfo = SystemSecurityUtils.getUser();
			if(userInfo != null){
				return "redirect:"+"/";
			}
        }
		model.addAttribute("username", username);
		return "sys/login";
	}

	/**
	 * 登录成功，进入管理首页
	 * @param request response
	 * @author
	 * @version 2014-03-18
	 */
	@RequiresUser
	@RequestMapping(value = "")
	public void index(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
		if(principal != null){
			if(principal.getLoginState() == 0){
				systemService.loginCallBack(request);
				principal.setLoginState(1);
			}
			if(StringUtils.isEmpty(principal.getTheme())){
				request.getSession().setAttribute("theme", 0);
			} else {
				request.getSession().setAttribute("theme", principal.getTheme());
			}
		} else {
			request.getSession().setAttribute("theme", 0);
		}
		
		request.getRequestDispatcher("/sys/menu/manage.action").forward(request, response);
	}

	/**
	 * 退出登录
	 * @param request
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) throws CustomException {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) { 
			systemService.logoutCallBack(request);
			System.out.println(subject.getSession().getId());
			subject.logout();
		}
		//departmentService.clearDeptAndUserCache();
		return "redirect:" + "/sys/login";
	}
}
