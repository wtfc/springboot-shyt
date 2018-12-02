package com.jc.system.security.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.security.domain.SysRole;

/**
 * @title GOA2.0
 * @description 角色信息基本表 检验类
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-15
 *
 */
public class SysRoleValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-04-15
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return SysRole.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-04-15
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		SysRole v =  (SysRole)arg0;
		if(v.getName()!=null&&v.getName().length()>64){
			arg1.reject("message", "Name is too long");
		}
		if(v.getDescription()!=null&&v.getDescription().length()>255){
			arg1.reject("message", "Description is too long");
		}
	}
}
