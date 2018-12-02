package com.jc.system.security.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.domain.Setting;

/**
 * @title GOA2.0
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-28
 */

public class SettingValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-04-28
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Setting.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-04-28
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		Setting v =  (Setting)arg0;
		if(v.getIsMsgService()!=null&&v.getIsMsgService().length()>1){
			arg1.reject("isMsgService", "IsMsgService is too long");
		}
		if(v.getMsgPrefix()!=null&&v.getMsgPrefix().length()>255){
			arg1.reject("msgPrefix", "MsgPrefix is too long");
		}
		if(v.getSuggestionType()!=null&&v.getSuggestionType().length()>1){
			arg1.reject("suggestionType", "SuggestionType is too long");
		}
		if(v.getShowIdentifyingCode()!=null&&v.getShowIdentifyingCode().length()>1){
			arg1.reject("showIdentifyingCode", "ShowIdentifyingCode is too long");
		}
		if(v.getUseIpBanding()!=null&&v.getUseIpBanding().length()>1){
			arg1.reject("useIpBanding", "UseIpBanding is too long");
		}
		if(v.getLoginType()!=null&&v.getLoginType().length()>1){
			arg1.reject("loginType", "LoginType is too long");
		}
		if(v.getNetKey()!=null&&v.getNetKey().length()>1){
			arg1.reject("netKey", "NetKey is too long");
		}
		if(v.getFilePath()!=null&&v.getFilePath().length()>200){
			arg1.reject("filePath", "FilePath is too long");
		}
		if(v.getPhotoPatn()!=null&&v.getPhotoPatn().length()>200){
			arg1.reject("photoPatn", "PhotoPatn is too long");
		}
		if(v.getControlPrint()!=null&&v.getControlPrint().length()>1){
			arg1.reject("controlPrint", MessageUtils.getMessage("JC_SYS_011", new Object[]{"1"}));
		}
		if(v.getSignType()!=null&&v.getSignType().length()>1){
			arg1.reject("signType", MessageUtils.getMessage("JC_SYS_011", new Object[]{"1"}));
		}
		if(v.getTaskParentToSub()!=null&&v.getTaskParentToSub().length()>1){
			arg1.reject("taskParentToSub", MessageUtils.getMessage("JC_SYS_011", new Object[]{"1"}));
		}
		if(v.getExtStr1()!=null&&v.getExtStr1().length()>200){
			arg1.reject("extStr1", "ExtStr1 is too long");
		}
		if(v.getExtStr2()!=null&&v.getExtStr2().length()>200){
			arg1.reject("extStr2", "ExtStr2 is too long");
		}
		if(v.getExtStr3()!=null&&v.getExtStr3().length()>200){
			arg1.reject("extStr3", "ExtStr3 is too long");
		}
		if(v.getExtStr4()!=null&&v.getExtStr4().length()>200){
			arg1.reject("extStr4", "ExtStr4 is too long");
		}
		if(v.getExtStr5()!=null&&v.getExtStr5().length()>200){
			arg1.reject("extStr5", "ExtStr5 is too long");
		}
	}
}
