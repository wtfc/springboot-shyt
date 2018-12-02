package com.jc.oa.ic.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.oa.ic.domain.Mail;
import com.jc.system.common.util.MessageUtils;

/**
 * @title 互动交流
 * @description 检验类 Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 *              Company 长春嘉诚网络工程有限公司
 * @author
 * @version 2014-05-19
 */

public class MailValidator implements Validator {

	/**
	 * @description 检验类能够校验的类
	 * @param arg0
	 *            校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version 2014-05-19
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Mail.class.equals(arg0);
	}

	/**
	 * @description 检验具体实现方法
	 * @param arg0
	 *            当前的实体类
	 * @param arg1
	 *            错误的信息
	 * @author
	 * @version 2014-05-19
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		Mail v = (Mail) arg0;
		if(v.getMailboxId()!=null){
		if(v.getMailboxId()==1){
			if("".equals(v.getInnerTo())  && "".equals(v.getInnerSs())){
				arg1.reject("mailTo",MessageUtils.getMessage("JC_SYS_010"));
			}
		}else{
			if("".equals(v.getTo())  &&"".equals(v.getShowSingle())){
				arg1.reject("mailToOut",MessageUtils.getMessage("JC_SYS_010"));
			}
		}
		}
		if(v.getMailTitle()!=null&&v.getMailTitle().length()>80){
			arg1.reject("mailTitle", MessageUtils.getMessage("JC_SYS_011", new Object[]{"80"}));
		}
	
		// if(v.getMailTitle()==null){
		// arg1.reject("mailTitle", MessageUtils.getMessage("JC_SYS_010"));
		// }
		// if(v.getMailTitle()!=null&&v.getMailTitle().length()>200){
		// arg1.reject("mailTitle", MessageUtils.getMessage("JC_SYS_011", new
		// Object[]{"200"}));
		// }
		// if(v.getMailContent()!=null&&v.getMailContent().length()>65535){
		// arg1.reject("mailContent", MessageUtils.getMessage("JC_SYS_011", new
		// Object[]{"65,535"}));
		// }
		// if(v.getSenderMail()!=null&&v.getSenderMail().length()>128){
		// arg1.reject("senderMail", MessageUtils.getMessage("JC_SYS_011", new
		// Object[]{"128"}));
		// }
		// if(v.getIsfile()==null){
		// arg1.reject("isfile", MessageUtils.getMessage("JC_SYS_010"));
		// }
		// if(v.getEncryptionPass()!=null&&v.getEncryptionPass().length()>8){
		// arg1.reject("encryptionPass", MessageUtils.getMessage("JC_SYS_011",
		// new Object[]{"8"}));
		// }
		// if(v.getMailStatus()==null){
		// arg1.reject("mailStatus", MessageUtils.getMessage("JC_SYS_010"));
		// }
		// if(v.getSenderTime()==null){
		// arg1.reject("senderTime", MessageUtils.getMessage("JC_SYS_010"));
		// }
		// if(v.getMessageId()!=null&&v.getMessageId().length()>200){
		// arg1.reject("messageId", MessageUtils.getMessage("JC_SYS_011", new
		// Object[]{"200"}));
		// }
		// if(v.getExtStr1()!=null&&v.getExtStr1().length()>200){
		// arg1.reject("extStr1", MessageUtils.getMessage("JC_SYS_011", new
		// Object[]{"200"}));
		// }
		// if(v.getExtStr2()!=null&&v.getExtStr2().length()>200){
		// arg1.reject("extStr2", MessageUtils.getMessage("JC_SYS_011", new
		// Object[]{"200"}));
		// }
		// if(v.getExtStr3()!=null&&v.getExtStr3().length()>200){
		// arg1.reject("extStr3", MessageUtils.getMessage("JC_SYS_011", new
		// Object[]{"200"}));
		// }
		// if(v.getExtStr4()!=null&&v.getExtStr4().length()>200){
		// arg1.reject("extStr4", MessageUtils.getMessage("JC_SYS_011", new
		// Object[]{"200"}));
		// }
		// if(v.getExtStr5()!=null&&v.getExtStr5().length()>200){
		// arg1.reject("extStr5", MessageUtils.getMessage("JC_SYS_011", new
		// Object[]{"200"}));
		// }
	}
}
