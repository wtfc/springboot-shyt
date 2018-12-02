package com.jc.oa.ic.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.oa.ic.domain.Mailbox;
import com.jc.system.common.util.MessageUtils;

/**
 * @title 互动交流
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-17
 */

public class MailboxValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-04-17
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Mailbox.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-04-17
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		Mailbox v =  (Mailbox)arg0;
		if(v.getAddress()!=null&&!"".equals(v.getAddress())){
			String rex  ="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
			if(!v.getAddress().matches(rex))
			{
				arg1.reject("address",  MessageUtils.getMessage("JC_SYS_017"));
			}
		}
		if (v.getAddress() == null || v.getAddress().equals("")) {
			arg1.reject("address", MessageUtils.getMessage("JC_SYS_010"));
		}	
		if(v.getAddress()!=null&&v.getAddress().length()>40){
			arg1.reject("address", MessageUtils.getMessage("JC_SYS_011", new Object[]{"40"}));
		}
		if (v.getReceiveService() == null || v.getReceiveService().equals("")) {
			arg1.reject("receiveService", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getReceiveService()!=null&&v.getReceiveService().length()>100){
			arg1.reject("receiveService", MessageUtils.getMessage("JC_SYS_011", new Object[]{"100"}));
		}
		if (v.getSenderService() == null || v.getSenderService().equals("")) {
			arg1.reject("senderService", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getSenderService()!=null&&v.getSenderService().length()>100){
			arg1.reject("senderService", MessageUtils.getMessage("JC_SYS_011", new Object[]{"100"}));
		}
		if (v.getUsername() == null || v.getUsername().equals("")) {
			arg1.reject("username", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getUsername()!=null&&v.getUsername().length()>40){
			arg1.reject("username", MessageUtils.getMessage("JC_SYS_011", new Object[]{"40"}));
		}
		if (v.getMailPassword() == null || v.getMailPassword().equals("")) {
			arg1.reject("mailPassword", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getMailPassword()!=null&&v.getMailPassword().length()>20){
			arg1.reject("mailPassword",  MessageUtils.getMessage("JC_SYS_010"));
		}
	}
}
