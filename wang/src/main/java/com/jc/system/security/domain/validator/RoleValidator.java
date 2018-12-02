package com.jc.system.security.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.security.domain.Role;

/**
 * @title GOA2.0
 * @description InnoDB free: 41984 kB 检验类 Copyright (c) 2014 Jiacheng.com Inc.
 *              All Rights Reserved Company 长春嘉诚网络工程有限公司
 * @author
 * @version 2014-03-18
 * 
 */
public class RoleValidator implements Validator {

	/**
	 * @description 检验类能够校验的类
	 * @param arg0
	 *            校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version 2014-03-18
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Role.class.equals(arg0);
	}

	/**
	 * @description 检验具体实现方法
	 * @param arg0
	 *            当前的实体类
	 * @param arg1
	 *            错误的信息
	 * @author
	 * @version 2014-03-18
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		Role v = (Role) arg0;
		
		if (v.getName() == null || v.getName().equals("")) {
			arg1.reject("name", "角色名称不能为空");
		}
		if (v.getDescription() == null || v.getDescription().equals("")) {
			arg1.reject("description", "角色描述不能为空");
		}
		
	}
}
