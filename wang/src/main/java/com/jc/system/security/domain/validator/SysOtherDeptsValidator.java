package com.jc.system.security.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.security.domain.SysOtherDepts;


/**
 * @title GOA2.0
 * @description 用户其他部门任职表 检验类
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-14
 *
 */
public class SysOtherDeptsValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-04-14
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return SysOtherDepts.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-04-14
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		SysOtherDepts v =  (SysOtherDepts)arg0;
		if(v.getExtStr1()!=null&&v.getExtStr1().length()>200){
			arg1.reject("message", "ExtStr1 is too long");
		}
		if(v.getExtStr2()!=null&&v.getExtStr2().length()>200){
			arg1.reject("message", "ExtStr2 is too long");
		}
		if(v.getExtStr3()!=null&&v.getExtStr3().length()>200){
			arg1.reject("message", "ExtStr3 is too long");
		}
		if(v.getExtStr4()!=null&&v.getExtStr4().length()>200){
			arg1.reject("message", "ExtStr4 is too long");
		}
		if(v.getExtStr5()!=null&&v.getExtStr5().length()>200){
			arg1.reject("message", "ExtStr5 is too long");
		}
	}
}
