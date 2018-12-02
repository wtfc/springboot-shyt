package com.jc.oa.ic;

import com.jc.system.CustomException;

/**
 * 
 * @title GOA V2.0
 * @description  互动交流模块异常类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author zhanglg
 * @version  2014年5月8日 上午9:17:50
 */
public class IcException extends CustomException {

	public IcException() {
	}
	public IcException(Throwable e) {
		super(e);
	}
	public IcException(String msg) {
		super(msg);
	}

	public IcException(String msg, Throwable e) {
		super(msg, e);
	}
}
