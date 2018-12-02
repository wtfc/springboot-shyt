package com.jc.oa.po.diary.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.oa.po.diary.domain.DiaryDelegation;

/**
 * @title 个人办公
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-05-13
 */

public class DiaryDelegationValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-05-13
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return DiaryDelegation.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-05-13
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		DiaryDelegation v =  (DiaryDelegation)arg0;
	}
}
