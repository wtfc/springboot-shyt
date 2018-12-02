package com.jc.system.specialData.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.MessageUtils;
import com.jc.system.specialData.domain.SpecialData;

/**
 * @title 172.16.3.68
 * @description  检验类
 * @author 
 * @version  2014-12-02
 */

public class SpecialDataValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-12-02
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return SpecialData.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-12-02
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		SpecialData v =  (SpecialData)arg0;
			if(v.getInfoName()!=null&&v.getInfoName().length()>50){
				arg1.reject("infoName", MessageUtils.getMessage("JC_SYS_011", new Object[]{"50"}));
			}
			if(v.getInfoType()!=null&&v.getInfoType().length()>20){
				arg1.reject("infoType", MessageUtils.getMessage("JC_SYS_011", new Object[]{"20"}));
			}
			if(v.getUserSex()!=null&&v.getUserSex().length()>20){
				arg1.reject("userSex", MessageUtils.getMessage("JC_SYS_011", new Object[]{"20"}));
			}
			if(v.getShowType()!=null&&v.getShowType().length()>20){
				arg1.reject("showType", MessageUtils.getMessage("JC_SYS_011", new Object[]{"20"}));
			}
			if(v.getInfoCirculate()!=null&&v.getInfoCirculate().length()>20){
				arg1.reject("infoCirculate", MessageUtils.getMessage("JC_SYS_011", new Object[]{"20"}));
			}
			if(v.getOpenLevel()!=null&&v.getOpenLevel().length()>20){
				arg1.reject("openLevel", MessageUtils.getMessage("JC_SYS_011", new Object[]{"20"}));
			}
			if(v.getSolarorlunar()!=null&&v.getSolarorlunar().length()>20){
				arg1.reject("solarorlunar", MessageUtils.getMessage("JC_SYS_011", new Object[]{"20"}));
			}
			if(v.getSummaryContent()!=null&&v.getSummaryContent().length()>65535){
				arg1.reject("summaryContent", MessageUtils.getMessage("JC_SYS_011", new Object[]{"65,535"}));
			}
			if(v.getSendmailStatus()!=null&&v.getSendmailStatus().length()>20){
				arg1.reject("sendmailStatus", MessageUtils.getMessage("JC_SYS_011", new Object[]{"20"}));
			}
			if(v.getSendpictureStatus()!=null&&v.getSendpictureStatus().length()>20){
				arg1.reject("sendpictureStatus", MessageUtils.getMessage("JC_SYS_011", new Object[]{"20"}));
			}
			if(v.getExtStr1()!=null&&v.getExtStr1().length()>200){
				arg1.reject("extStr1", MessageUtils.getMessage("JC_SYS_011", new Object[]{"200"}));
			}
			if(v.getExtStr2()!=null&&v.getExtStr2().length()>200){
				arg1.reject("extStr2", MessageUtils.getMessage("JC_SYS_011", new Object[]{"200"}));
			}
			if(v.getExtStr3()!=null&&v.getExtStr3().length()>200){
				arg1.reject("extStr3", MessageUtils.getMessage("JC_SYS_011", new Object[]{"200"}));
			}
			if(v.getExtStr4()!=null&&v.getExtStr4().length()>200){
				arg1.reject("extStr4", MessageUtils.getMessage("JC_SYS_011", new Object[]{"200"}));
			}
			if(v.getExtStr5()!=null&&v.getExtStr5().length()>200){
				arg1.reject("extStr5", MessageUtils.getMessage("JC_SYS_011", new Object[]{"200"}));
			}
	}
}
