package com.jc.system.security.exception;

import com.jc.system.CustomException;

public class SystemException  extends CustomException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SystemException() {
		super.setLogMsg("系统管理异常");
	}

	public SystemException(String msg) {
		super(msg);
		super.setLogMsg("系统管理异常");
	}

	public SystemException(Throwable e) {
		super(e);
		super.setLogMsg("系统管理异常");
	}

	public SystemException(String msg, Throwable e) {
		super(msg, e);
		super.setLogMsg("系统管理异常");
	}

}
