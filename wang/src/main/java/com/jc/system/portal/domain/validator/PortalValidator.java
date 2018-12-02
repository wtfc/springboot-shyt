package com.jc.system.portal.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.MessageUtils;
import com.jc.system.portal.domain.Portal;

/**
 * @title GOA2.0
 * @description  检验类
 * @author 
 * @version  2014-06-13
 */

public class PortalValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-06-13
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Portal.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-06-13
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		Portal v =  (Portal)arg0;
			if(v.getPortalName()!=null&&v.getPortalName().length()>50){
				arg1.reject("portalName", MessageUtils.getMessage("JC_SYS_011", new Object[]{"50"}));
			}
			if(v.getPortalStatus()!=null&&v.getPortalStatus().length()>20){
				arg1.reject("portalStatus", MessageUtils.getMessage("JC_SYS_011", new Object[]{"20"}));
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
