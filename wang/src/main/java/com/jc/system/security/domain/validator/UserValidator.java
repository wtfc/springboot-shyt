package com.jc.system.security.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.domain.User;

/**
 * @title GOA2.0
 * @description InnoDB free: 41984 kB 检验类 Copyright (c) 2014 Jiacheng.com Inc.
 *              All Rights Reserved Company 长春嘉诚网络工程有限公司
 * @author
 * @version 2014-03-18
 * 
 */
public class UserValidator implements Validator {

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
		return User.class.equals(arg0);
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
		User v = (User) arg0;
		
		if (v.getDisplayName() == null || v.getDisplayName().equals("")) {
			arg1.reject("displayName", MessageUtils.getMessage("JC_SYS_010"));
		}
		if (v.getDisplayName() != null && v.getDisplayName().length() > 20) {
			arg1.reject("displayName", MessageUtils.getMessage("JC_SYS_011", new Object[]{"20"}));
		}
		if (v.getLoginName() == null || v.getLoginName().equals("")) {
			arg1.reject("loginName", MessageUtils.getMessage("JC_SYS_010"));
		}
		if (v.getLoginName() != null && v.getLoginName().length() > 20) {
			arg1.reject("loginName", MessageUtils.getMessage("JC_SYS_011", new Object[]{"20"}));
		}
		if(v.getDeptId() == null || v.getDeptId().toString().length()==0) {
			arg1.reject("deptId", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getOrderNo() == null || v.getOrderNo() <= 0){
			arg1.reject("orderNo", MessageUtils.getMessage("JC_SYS_010"));
		}
	}
}
