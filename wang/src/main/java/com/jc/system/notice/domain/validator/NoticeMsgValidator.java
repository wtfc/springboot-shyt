package com.jc.system.notice.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.MessageUtils;
import com.jc.system.notice.domain.NoticeMsg;

/**
 * @title GOA系统管理
 * @description  检验类
 * @author 
 * @version  2014-06-05
 */

public class NoticeMsgValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-06-05
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return NoticeMsg.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-06-05
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		NoticeMsg v =  (NoticeMsg)arg0;
			if(v.getSendUser()==null){
				arg1.reject("sendUser", MessageUtils.getMessage("JC_SYS_010"));
			}
			if(v.getTitle()==null){
				arg1.reject("title", MessageUtils.getMessage("JC_SYS_010"));
			}
			if(v.getTitle()!=null&&v.getTitle().length()>200){
				arg1.reject("title", MessageUtils.getMessage("JC_SYS_011", new Object[]{"200"}));
			}
			if(v.getContent()!=null&&v.getContent().length()>1000){
				arg1.reject("content", MessageUtils.getMessage("JC_SYS_011", new Object[]{"1,000"}));
			}
			if(v.getUrl()==null){
				arg1.reject("url", MessageUtils.getMessage("JC_SYS_010"));
			}
			if(v.getUrl()!=null&&v.getUrl().length()>200){
				arg1.reject("url", MessageUtils.getMessage("JC_SYS_011", new Object[]{"200"}));
			}
			if(v.getBusinessId()==null){
				arg1.reject("businessId", MessageUtils.getMessage("JC_SYS_010"));
			}
			if(v.getBusinessFlag()==null){
				arg1.reject("businessFlag", MessageUtils.getMessage("JC_SYS_010"));
			}
			if(v.getBusinessFlag()!=null&&v.getBusinessFlag().length()>30){
				arg1.reject("businessFlag", MessageUtils.getMessage("JC_SYS_011", new Object[]{"30"}));
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
