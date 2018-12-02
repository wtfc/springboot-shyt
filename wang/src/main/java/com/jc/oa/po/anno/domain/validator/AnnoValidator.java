package com.jc.oa.po.anno.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.StringUtil;
import com.jc.oa.po.anno.domain.Anno;

/**
 * @title 个人办公
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-29
 */

public class AnnoValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-04-29
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Anno.class.equals(arg0);
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
		Anno v =  (Anno)arg0;
		if(v.getAnnoType()!=null&&v.getAnnoType().length()>1){
			arg1.reject("annoType", "AnnoType is too long");
		}
		if(v.getAnnoName()!=null&&v.getAnnoName().length()>255){
			arg1.reject("annoName", "AnnoName is too long");
		}
		if(v.getContent()!=null&&v.getContent().length()>2000){
			arg1.reject("content", "Content is too long");
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
