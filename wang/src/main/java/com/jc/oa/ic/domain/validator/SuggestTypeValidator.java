package com.jc.oa.ic.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.oa.ic.domain.SuggestType;

/**
 * @title 互动交流
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-17
 */

public class SuggestTypeValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-04-17
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return SuggestType.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-04-17
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		SuggestType v =  (SuggestType)arg0;
		if (v.getTypeName() == null || v.getTypeName().equals("")) {
			arg1.reject("typeName", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getTypeName()!=null&&v.getTypeName().length()>32){
			arg1.reject("typeName",  MessageUtils.getMessage("JC_SYS_011", new Object[]{"32"}));
		}
	}
}
