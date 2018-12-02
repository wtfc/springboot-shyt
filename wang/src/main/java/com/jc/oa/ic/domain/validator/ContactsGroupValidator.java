package com.jc.oa.ic.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.oa.ic.domain.ContactsGroup;
import com.jc.system.common.util.MessageUtils;

/**
 * @title 互动交流
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-05-08
 */

public class ContactsGroupValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-05-08
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return ContactsGroup.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-05-08
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		ContactsGroup v =  (ContactsGroup)arg0;
		if (v.getGroupName() == null || v.getGroupName().equals("")) {
			arg1.reject("groupName", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getGroupName()!=null&&v.getGroupName().length()>20){
			arg1.reject("groupName", MessageUtils.getMessage("JC_SYS_011", new Object[]{"20"}));
		}
		if(v.getDescription()!=null&&v.getDescription().length()>20){
			arg1.reject("description", MessageUtils.getMessage("JC_SYS_011", new Object[]{"20"}));
		}
	}
}
