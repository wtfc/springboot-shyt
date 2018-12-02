package com.jc.system.portal.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.MessageUtils;
import com.jc.system.portal.domain.Portlet;

/**
 * @title GOA2.0
 * @description  检验类
 * @author 
 * @version  2014-06-16
 */

public class PortletValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-06-16
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Portlet.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-06-16
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		Portlet v =  (Portlet)arg0;
			if(v.getLetTitle()!=null&&v.getLetTitle().length()>100){
				arg1.reject("letTitle", MessageUtils.getMessage("JC_SYS_011", new Object[]{"100"}));
			}
			if(v.getLetTextarea()!=null&&v.getLetTextarea().length()>65535){
				arg1.reject("letTextarea", MessageUtils.getMessage("JC_SYS_011", new Object[]{"65,535"}));
			}
			if(v.getLetFile()!=null&&v.getLetFile().length()>255){
				arg1.reject("letFile", MessageUtils.getMessage("JC_SYS_011", new Object[]{"255"}));
			}
			if(v.getFunctionId()!=null&&v.getFunctionId().length()>512){
				arg1.reject("functionId", MessageUtils.getMessage("JC_SYS_011", new Object[]{"512"}));
			}
			if(v.getFunctionName()!=null&&v.getFunctionName().length()>512){
				arg1.reject("functionName", MessageUtils.getMessage("JC_SYS_011", new Object[]{"512"}));
			}
			if(v.getViewType()!=null&&v.getViewType().length()>30){
				arg1.reject("viewType", MessageUtils.getMessage("JC_SYS_011", new Object[]{"30"}));
			}
			if(v.getColumnShowName()!=null&&v.getColumnShowName().length()>1000){
				arg1.reject("columnShowName", MessageUtils.getMessage("JC_SYS_011", new Object[]{"1,000"}));
			}
			if(v.getColumnName()!=null&&v.getColumnName().length()>1000){
				arg1.reject("columnName", MessageUtils.getMessage("JC_SYS_011", new Object[]{"1,000"}));
			}
			if(v.getLetStyle()!=null&&v.getLetStyle().length()>30){
				arg1.reject("letStyle", MessageUtils.getMessage("JC_SYS_011", new Object[]{"30"}));
			}
			if(v.getLetIcon()!=null&&v.getLetIcon().length()>30){
				arg1.reject("letIcon", MessageUtils.getMessage("JC_SYS_011", new Object[]{"30"}));
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
