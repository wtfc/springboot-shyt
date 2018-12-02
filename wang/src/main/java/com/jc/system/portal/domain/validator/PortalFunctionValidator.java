package com.jc.system.portal.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.MessageUtils;
import com.jc.system.portal.domain.PortalFunction;

/**
 * @title GOA2.0
 * @description  检验类
 * @author 
 * @version  2014-06-11
 */

public class PortalFunctionValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-06-11
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return PortalFunction.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-06-11
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		PortalFunction v =  (PortalFunction)arg0;
			if(v.getFunName()!=null&&v.getFunName().length()>50){
				arg1.reject("funName", MessageUtils.getMessage("JC_SYS_011", new Object[]{"50"}));
			}
			if(v.getFunCode()!=null&&v.getFunCode().length()>100){
				arg1.reject("funCode", MessageUtils.getMessage("JC_SYS_011", new Object[]{"100"}));
			}
			if(v.getFunUrl()!=null&&v.getFunUrl().length()>200){
				arg1.reject("funUrl", MessageUtils.getMessage("JC_SYS_011", new Object[]{"200"}));
			}
			if(v.getLineType()!=null&&v.getLineType().length()>30){
				arg1.reject("lineType", MessageUtils.getMessage("JC_SYS_011", new Object[]{"30"}));
			}
			if(v.getViewType()!=null&&v.getViewType().length()>30){
				arg1.reject("viewType", MessageUtils.getMessage("JC_SYS_011", new Object[]{"30"}));
			}
			if(v.getFunColumnShowName()!=null&&v.getFunColumnShowName().length()>1000){
				arg1.reject("funColumnShowName", MessageUtils.getMessage("JC_SYS_011", new Object[]{"1,000"}));
			}
			if(v.getFunColumnName()!=null&&v.getFunColumnName().length()>1000){
				arg1.reject("funColumnName", MessageUtils.getMessage("JC_SYS_011", new Object[]{"1,000"}));
			}
			if(v.getFunUrlparameter()==null){
				arg1.reject("funUrlparameter", MessageUtils.getMessage("JC_SYS_010"));
			}
			if(v.getFunUrlparameter()!=null&&v.getFunUrlparameter().length()>1000){
				arg1.reject("funUrlparameter", MessageUtils.getMessage("JC_SYS_011", new Object[]{"1,000"}));
			}
			if(v.getFunUrlmore()!=null&&v.getFunUrlmore().length()>1000){
				arg1.reject("funUrlmore", MessageUtils.getMessage("JC_SYS_011", new Object[]{"1,000"}));
			}
			if(v.getFunParametertype()!=null&&v.getFunParametertype().length()>100){
				arg1.reject("funParametertype", MessageUtils.getMessage("JC_SYS_011", new Object[]{"100"}));
			}
			if(v.getFunUrlParametername()!=null&&v.getFunUrlParametername().length()>200){
				arg1.reject("funUrlParametername", MessageUtils.getMessage("JC_SYS_011", new Object[]{"200"}));
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
