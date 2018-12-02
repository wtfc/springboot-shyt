package com.jc.oa.ic.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.oa.ic.domain.In;

/**
 * @title HR
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-29
 */

public class InValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-04-29
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return In.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-04-29
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		In v =  (In)arg0;
		if(v.getOriginator()!=null&&v.getOriginator().length()>16){
			arg1.reject("originator", "Originator is too long");
		}
		if(v.getType()!=null&&v.getType().length()>1){
			arg1.reject("type", "type is too long");
		}
		if(v.getEncoding()!=null&&v.getEncoding().length()>1){
			arg1.reject("encoding", "Encoding is too long");
		}
		if(v.getText()!=null&&v.getText().length()>1000){
			arg1.reject("text", "Text is too long");
		}
		if(v.getOriginalRefNo()!=null&&v.getOriginalRefNo().length()>64){
			arg1.reject("originalRefNo", "OriginalRefNo is too long");
		}
		if(v.getGatewayId()!=null&&v.getGatewayId().length()>64){
			arg1.reject("gatewayId", "GatewayId is too long");
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
