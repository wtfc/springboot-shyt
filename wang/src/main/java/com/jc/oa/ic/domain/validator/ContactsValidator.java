package com.jc.oa.ic.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.oa.ic.domain.Contacts;

/**
 * @title GOAIC
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-05-04
 */

public class ContactsValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-05-04
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Contacts.class.equals(arg0);
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
		Contacts v =  (Contacts)arg0;
		if (v.getUserName() == null || v.getUserName().equals("")) {
			arg1.reject("userName", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getUserName()!=null&&v.getUserName().length()>10){
			arg1.reject("userName", MessageUtils.getMessage("JC_SYS_011", new Object[]{"10"}));
		}
		if(v.getMail()!=null&&!"".equals(v.getMail())){
			String rex  ="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
			if(!v.getMail().matches(rex))
			{
				arg1.reject("mail",  MessageUtils.getMessage("JC_SYS_017"));
			}
		}
		if (v.getMail() == null || v.getMail().equals("")) {
			arg1.reject("mail", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getMail()!=null&&v.getMail().length()>30){
			arg1.reject("mail", MessageUtils.getMessage("JC_SYS_011", new Object[]{"30"}));
		}
		if(v.getContactsType()!=null&&v.getContactsType().length()>32){
			arg1.reject("contactsType", MessageUtils.getMessage("JC_SYS_011", new Object[]{"30"}));
		}
		if(v.getSimpleName()!=null&&v.getSimpleName().length()>10){
			arg1.reject("simpleName", MessageUtils.getMessage("JC_SYS_011", new Object[]{"10"}));
		}
		
		if(v.getPhone()!=null&&!"".equals(v.getPhone())){
			String rex  =("[0-9]*");
			if(!v.getPhone().matches(rex) && v.getPhone().length()>11)
			{
				arg1.reject("phone",  MessageUtils.getMessage("JC_SYS_017"));
			}
		}
		
		if (v.getPhone() == null || v.getPhone().equals("")) {
			arg1.reject("phone", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getPhone()!=null&&v.getPhone().length()>11){
			arg1.reject("phone", MessageUtils.getMessage("JC_SYS_011", new Object[]{"11"}));
		}
	}
}
