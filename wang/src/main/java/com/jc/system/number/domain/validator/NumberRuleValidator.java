package com.jc.system.number.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.StringUtil;
import com.jc.system.number.domain.NumberRule;

/**
 * @title GOA2.0
 * @description  检验类
 * @author 
 * @version  2014-05-04
 */

public class NumberRuleValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-05-04
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return NumberRule.class.equals(arg0);
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
		NumberRule v =  (NumberRule)arg0;
		if(v.getRuleName()!=null&&v.getRuleName().length()>32){
			arg1.reject("ruleName", "RuleName is too long");
		}
		if(v.getDateFormat()!=null&&v.getDateFormat().length()>32){
			arg1.reject("dateFormat", "DateFormat is too long");
		}
		if(v.getDateSplit()!=null&&v.getDateSplit().length()>64){
			arg1.reject("dateSplit", "DateSplit is too long");
		}
		if(v.getNumberStart()!=null&&v.getNumberStart().length()>16){
			arg1.reject("numberStart", "NumberStart is too long");
		}
		if(v.getNumberCeiling()!=null&&v.getNumberCeiling().length()>16){
			arg1.reject("numberCeiling", "NumberCeiling is too long");
		}
		if(v.getNumberResetState()!=null&&v.getNumberResetState().length()>16){
			arg1.reject("numberResetState", "NumberResetState is too long");
		}
		if(v.getParameter()!=null&&v.getParameter().length()>64){
			arg1.reject("parameter", "Parameter is too long");
		}
		if(v.getNumberValue()!=null&&v.getNumberValue().length()>16){
			arg1.reject("numberValue", "NumberValue is too long");
		}
		if(v.getNumberPlaceholder()!=null&&v.getNumberPlaceholder().length()>16){
			arg1.reject("numberPlaceholder", "NumberPlaceholder is too long");
		}
	}
}
