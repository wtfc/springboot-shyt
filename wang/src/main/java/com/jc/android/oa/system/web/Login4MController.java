package com.jc.android.oa.system.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.android.oa.common.service.IUsermapService;
import com.jc.android.oa.system.domain.User4M;
import com.jc.foundation.web.BaseController;
import com.jc.system.CustomException;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.exception.UserIpException;
import com.jc.system.security.exception.UserLockedException;
import com.jc.system.security.exception.UserPasswordException;
import com.jc.system.security.service.ISystemService;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.UserUtils;
import com.jc.system.userPhone.domain.UserPhone;
import com.jc.system.userPhone.service.IUserPhoneService;

/**
 * 
 * @title 移动端action处理类
 * @description 
 * @version  2014年9月11日
 */
@Controller
@RequestMapping(value = "/android/system/")
public class Login4MController extends BaseController{

	@Autowired
	private ISystemService systemService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IUsermapService usermapService;
	@Autowired
	private IUserPhoneService userPhoneService;
	
	/**
	 * 手机用户登录
	 * @param loginName	用户名
	 * @param password 密码
	 * @param client 手机串号
	 * @param request request
	 * @return Map<String, Object> 登录结果
	 * @throws Exception
	 */
	@RequestMapping(value = "login4M.action")
	@ResponseBody
	public Map<String, Object> login4M(String loginName, String password, String deviceTag,String imeiNo,String deviceModel,HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(loginName) && StringUtils.isNotEmpty(password)){
			Subject currentUser = SecurityUtils.getSubject();
	        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password,request.getRemoteAddr());
	        token.setRememberMe(true);  
	       
	        try {
	            currentUser.login(token);
	            if(currentUser.isAuthenticated()){
	            	
	              try {
	    	        	UserPhone userPhone = new UserPhone();
	    	        	User valiUser = new User();
	    	 	        valiUser.setLoginName(loginName);
	    				valiUser = userService.get(valiUser);
	    				if(valiUser != null){
	    					userPhone.setUserId(valiUser.getId());
	    			        userPhone.setImeiNo(imeiNo);
	    			        userPhone.setDeviceModel(deviceModel);
//	    			        resultMap = userPhoneService.valiAndResetUserPhone(userPhone, resultMap,valiUser);
//	    		        	if(resultMap.get("errormsg") != null){
//	    		        		resultMap.put("state", "failure");
//	    		        		currentUser.logout();
//	    		        		return resultMap;
//	    		        	}
	    				}
	    			} catch (CustomException e1) {
	    				e1.printStackTrace();
	    			}
	            	
	            	
	            	User user = SystemSecurityUtils.getUser();
					try {
						//登录回调
						systemService.loginCallBack4M(user, request);
						//保存手机串号
						usermapService.saveUsermapClient(user.getId(), user.getLoginName(), deviceTag);
						//获取菜单列表
						/*
						Menu menuVo = new Menu();
						if(user.getIsSystemAdmin())
							menuVo.setUserId(Long.valueOf(-99));
						else
							menuVo.setUserId(user.getId());
						resultMap = menuService.desktopMenuForQuery(menuVo);//key : menuList
						*/						
					} catch (Exception e) {
						e.printStackTrace();
					}
					User4M user4m = new User4M();
					UserUtils.user2User4M(user, user4m);
					resultMap.put("state", "success");
					resultMap.put("sessionId", request.getSession().getId());
					resultMap.put("data", user4m);
					return resultMap;
		        }
	        }
	        catch (UnknownAccountException e) {
				resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_103"));
	        }
	        catch (UserLockedException e) {
				resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_099"));
	        }
	        catch (UserIpException e) {
				resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_100"));
	        }
	        catch (UserPasswordException e) {
				resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_103"));
	        }
	        catch (AuthenticationException e) {
	            e.printStackTrace(); 
				resultMap.put("errormsg", "登录失败");
	        }
	        
	        resultMap.put("state", "failure");
	        return resultMap;
	        
		} else {
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_103"));
			return resultMap;
		} 
	}
	
	/**
	 * 桌面用户登录
	 * @param loginName	用户名
	 * @param password 密码
	 * @param client 手机串号
	 * @param request request
	 * @return Map<String, Object> 登录结果
	 * @throws Exception
	 */
	@RequestMapping(value = "login4D.action")
	@ResponseBody
	public Map<String, Object> login4D(String loginName, String password, String deviceTag,String imeiNo,String deviceModel,HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(loginName) && StringUtils.isNotEmpty(password)){
			Subject currentUser = SecurityUtils.getSubject();
	        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password,request.getRemoteAddr());
	        token.setRememberMe(true);  
	       
	        try {
	            currentUser.login(token);
	            if(currentUser.isAuthenticated()){
	            	User user = SystemSecurityUtils.getUser();
					try {
						//登录回调
						systemService.loginCallBack4M(user, request);
					} catch (Exception e) {
						e.printStackTrace();
					}
					User4M user4m = new User4M();
					UserUtils.user2User4M(user, user4m);
					resultMap.put("state", "success");
					resultMap.put("sessionId", request.getSession().getId());
					resultMap.put("data", user4m);
					return resultMap;
		        }
	        }
	        catch (UnknownAccountException e) {
				resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_103"));
	        }
	        catch (UserLockedException e) {
				resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_099"));
	        }
	        catch (UserIpException e) {
				resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_100"));
	        }
	        catch (UserPasswordException e) {
				resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_103"));
	        }
	        catch (AuthenticationException e) {
	            e.printStackTrace(); 
				resultMap.put("errormsg", "登录失败");
	        }
	        
	        resultMap.put("state", "failure");
	        return resultMap;
	        
		} else {
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_103"));
			return resultMap;
		} 
	}
	
	
	/**
	 * 用户登出
	 * @param request request
	 * @return Map<String, Object> 登录结果
	 * @throws Exception
	 */
	@RequestMapping(value = "logout4M.action")
	@ResponseBody
	public Map<String, Object> logout4M(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Subject subject = SecurityUtils.getSubject();
//			if (subject.isAuthenticated()) {
				systemService.logoutCallBack4M(request);
				subject.logout();
//			}
			resultMap.put("state", "success");
		} catch (CustomException e) {
			e.printStackTrace();
			resultMap.put("state", "failure");
		}
		return resultMap;
	}
}
