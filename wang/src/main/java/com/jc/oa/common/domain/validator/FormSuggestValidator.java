package com.jc.oa.common.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.StringUtil;
import com.jc.oa.common.domain.FormSuggest;

/**
 * @title  GOA2.0源代码
 * @description  检验类
 * @author 
 * @version  2014-04-18
 */

public class FormSuggestValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-04-18
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return FormSuggest.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-04-18
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		FormSuggest v =  (FormSuggest)arg0;
		if(v.getPiId()!=null&&v.getPiId().length()>64){
			arg1.reject("piId", "PiId is too long");
		}
		if(v.getNodeName()!=null&&v.getNodeName().length()>255){
			arg1.reject("nodeName", "NodeName is too long");
		}
		if(v.getTaskId()!=null&&v.getTaskId().length()>64){
			arg1.reject("taskId", "TaskId is too long");
		}
		if(v.getMessage()!=null&&v.getMessage().length()>1000){
			arg1.reject("message", "Message is too long");
		}
		if(v.getUserName()!=null&&v.getUserName().length()>255){
			arg1.reject("userName", "UserName is too long");
		}
		if(v.getSuggestId()!=null&&v.getSuggestId().length()>64){
			arg1.reject("suggestId", "SuggestId is too long");
		}
		if(v.getSignContainerId()!=null&&v.getSignContainerId().length()>64){
			arg1.reject("signContainerId", "SignContainerId is too long");
		}
		if(v.getCreateUserDuty()!=null&&v.getCreateUserDuty().length()>255){
			arg1.reject("createUserDuty", "CreateUserDuty is too long");
		}
		if(v.getCreateUserLevel()!=null&&v.getCreateUserLevel().length()>255){
			arg1.reject("createUserLevel", "CreateUserLevel is too long");
		}
		if(v.getSignInfo()!=null&&v.getSignInfo().length()>65535){
			arg1.reject("signInfo", "SignInfo is too long");
		}
		if(v.getExtStr5()!=null&&v.getExtStr5().length()>200){
			arg1.reject("extStr5", "ExtStr5 is too long");
		}
		if(v.getExtStr3()!=null&&v.getExtStr3().length()>200){
			arg1.reject("extStr3", "ExtStr3 is too long");
		}
		if(v.getExtStr4()!=null&&v.getExtStr4().length()>200){
			arg1.reject("extStr4", "ExtStr4 is too long");
		}
		if(v.getExtStr1()!=null&&v.getExtStr1().length()>200){
			arg1.reject("extStr1", "ExtStr1 is too long");
		}
		if(v.getExtStr2()!=null&&v.getExtStr2().length()>200){
			arg1.reject("extStr2", "ExtStr2 is too long");
		}
	}
}
