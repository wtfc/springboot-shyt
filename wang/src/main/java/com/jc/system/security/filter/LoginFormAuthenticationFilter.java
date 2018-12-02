package com.jc.system.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * 
 * @title GOA V2.0
 * @description 
 * @version  2014年6月18日
 */
public class LoginFormAuthenticationFilter extends FormAuthenticationFilter{

   @Override
   protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
           throws Exception {
       HttpServletRequest httpServletRequest = (HttpServletRequest) request;
       HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        
       //subject.getSession().setAttribute(SubjectUtils.CURRENT_USER, subject.getPrincipal());    //设置用户身份进session属性
        
       String url = this.getSuccessUrl();
       httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + url);    //页面跳转
       return false;
   }
}
