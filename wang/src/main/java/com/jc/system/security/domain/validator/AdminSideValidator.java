package com.jc.system.security.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.security.domain.AdminSide;

/**
 * @title GOA2.0
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-16
 */

public class AdminSideValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-04-16
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return AdminSide.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-04-16
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		AdminSide v =  (AdminSide)arg0;
		if(v.getIsChecked()!=null&&v.getIsChecked().length()>1){
			arg1.reject("isChecked", "IsChecked is too long");
		}
	}
}
