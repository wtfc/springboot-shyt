package com.jc.system.security.exception;

import com.jc.system.DBException;

/**
 * 
 * @title GOA V2.0
 * @description 
 * @version  2014年5月20日
 */
public class ConcurrentException extends DBException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConcurrentException() {
		super.setLogMsg("数据已被修改，请刷新后重新操作");
	}

	public ConcurrentException(String msg) {
		super(msg);
		super.setLogMsg("数据已被修改，请刷新后重新操作");
	}

	public ConcurrentException(Throwable e) {
		super(e);
		super.setLogMsg("数据已被修改，请刷新后重新操作");
	}

	public ConcurrentException(String msg, Throwable e) {
		super(msg, e);
		super.setLogMsg("数据已被修改，请刷新后重新操作");
	}
}
