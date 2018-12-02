package com.jc.system;

/**
 * @description:自定义异常基类
 * @created: 2014年3月4日 上午8:42:20
 */
public class DBException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 构造函数
	 */
	public DBException() {
	}
	
	public DBException(Throwable e) {
		super(e);
	}

	public DBException(String msg) {
		super(msg);
	}

	public DBException(String msg, Throwable e) {
		super(msg, e);
	}
}
