package com.jc.system.portal.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.portal.domain.RolePortal;

/**
 * @title GOA2.0
 * @description  检验类
 * @author 
 * @version  2014-06-16
 */

public class RolePortalValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-06-16
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return RolePortal.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-06-16
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		RolePortal v =  (RolePortal)arg0;
	}
}
