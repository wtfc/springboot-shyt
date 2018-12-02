package com.jc.system.exception;

import com.jc.system.CustomException;

/**
 * @title GOA V2.0
 * @description 流程异常
 * @version  2014年5月20日
 */
public class DataNotFoundException extends CustomException{

	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 */
	public DataNotFoundException() {
	}
	
	public DataNotFoundException(Throwable e) {
		super(e);
	}

	public DataNotFoundException(String msg) {
		super(msg);
	}
}
