package com.jc.system.specialData.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.specialData.domain.SpecialdataShare;

/**
 * @title 172.16.3.68
 * @description  检验类
 * @author 
 * @version  2014-12-02
 */

public class SpecialdataShareValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-12-02
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return SpecialdataShare.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-12-02
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		SpecialdataShare v =  (SpecialdataShare)arg0;
	}
}
