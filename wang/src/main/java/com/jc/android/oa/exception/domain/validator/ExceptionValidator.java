package com.jc.android.oa.exception.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.android.oa.exception.domain.Exception4M;
import com.jc.system.common.util.MessageUtils;

/**
 * @title goa2.0 
 * @version  2014-12-10
 */

public class ExceptionValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-12-10
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Exception.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-12-10
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		Exception4M v =  (Exception4M)arg0;
			if(v.getExceDetail()==null){
				arg1.reject("exceDetail", MessageUtils.getMessage("JC_SYS_010"));
			}
			if(v.getExceDetail()!=null&&v.getExceDetail().length()>4000){
				arg1.reject("exceDetail", MessageUtils.getMessage("JC_SYS_011", new Object[]{"4,000"}));
			}
			if(v.getUserId()==null){
				arg1.reject("userId", MessageUtils.getMessage("JC_SYS_010"));
			}
	}
}
