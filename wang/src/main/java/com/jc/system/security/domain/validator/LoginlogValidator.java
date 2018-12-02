package com.jc.system.security.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.security.domain.Loginlog;

/**
 * @title GOA2.0
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-05-04
 */

public class LoginlogValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-05-04
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Loginlog.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-05-04
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		Loginlog v =  (Loginlog)arg0;
		if(v.getLoginName()!=null&&v.getLoginName().length()>50){
			arg1.reject("loginName", "LoginName is too long");
		}
		if(v.getDisplayName()!=null&&v.getDisplayName().length()>50){
			arg1.reject("displayName", "DisplayName is too long");
		}
		if(v.getIp()!=null&&v.getIp().length()>20){
			arg1.reject("ip", "Ip is too long");
		}
	}
}
