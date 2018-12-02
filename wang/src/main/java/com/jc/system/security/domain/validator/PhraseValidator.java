package com.jc.system.security.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.security.domain.Phrase;

/**
 * @title GOA2.0
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-28
 */

public class PhraseValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-04-28
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Phrase.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-04-28
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		Phrase v =  (Phrase)arg0;
		if(v.getContent()!=null&&v.getContent().length()>255){
			arg1.reject("content", "Content is too long");
		}
		if(v.getPhraseType()!=null&&v.getPhraseType().length()>1){
			arg1.reject("phraseType", "PhraseType is too long");
		}
	}
}
