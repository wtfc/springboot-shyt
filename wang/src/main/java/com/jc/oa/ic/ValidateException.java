package com.jc.oa.ic;

import com.jc.system.CustomException;

/**
 * 
 * @title GOA V2.0
 * @description 互动交流模块验证异常类
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 宋海涛
 * @version  2014年5月30日下午3:14:54
 */
public class ValidateException extends CustomException {

	public ValidateException() {
	}
	public ValidateException(Throwable e) {
		super(e);
	}
	public ValidateException(String msg) {
		super(msg);
	}

	public ValidateException(String msg, Throwable e) {
		super(msg, e);
	}
}
