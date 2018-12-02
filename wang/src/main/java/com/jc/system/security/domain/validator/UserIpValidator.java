package com.jc.system.security.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.security.domain.UserIp;

/**
 * @title GOA2.0
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-30
 */

public class UserIpValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-04-30
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return UserIp.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-04-30
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		UserIp v =  (UserIp)arg0;
		if(v.getUserStartIp()!=null&&v.getUserStartIp().length()>64){
			arg1.reject("userStartIp", "UserStartIp is too long");
		}
		if(v.getUserEndIp()!=null&&v.getUserEndIp().length()>64){
			arg1.reject("userEndIp", "UserEndIp is too long");
		}
		if(v.getUserIpDesc()!=null&&v.getUserIpDesc().length()>255){
			arg1.reject("userIpDesc", "UserIpDesc is too long");
		}
	}
}
