package com.jc.system.security.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.security.domain.Department;

/**
 * @title GOA2.0
 * @description InnoDB free: 41984 kB 检验类 Copyright (c) 2014 Jiacheng.com Inc.
 *              All Rights Reserved Company 长春嘉诚网络工程有限公司
 * @author
 * @version 2014-03-18
 * 
 */
public class DepartmentValidator implements Validator {

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
		return Department.class.equals(arg0);
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
		Department v = (Department) arg0;
		if (v.getName() != null && v.getName().length() > 765) {
			arg1.reject("message", "Name is too long");
		}
		/*if (v.getCode() != null && v.getCode().length() > 192) {
			arg1.reject("message", "Code is too long");
		}
		if (v.getDeptDesc() != null && v.getDeptDesc().length() > 765) {
			arg1.reject("message", "DeptDesc is too long");
		}
		if (v.getRemark1() != null && v.getRemark1().length() > 765) {
			arg1.reject("message", "Remark1 is too long");
		}
		if (v.getRemark2() != null && v.getRemark2().length() > 65535) {
			arg1.reject("message", "Remark2 is too long");
		}*/
	}
}
