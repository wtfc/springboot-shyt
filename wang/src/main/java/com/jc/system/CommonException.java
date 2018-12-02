package com.jc.system;

/**
 * @description:系统通用异常类
 * @created: 2014年3月4日 上午8:42:20
 */
public class CommonException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 构造函数
	 */
	public CommonException() {
	}
	
	public CommonException(Throwable e) {
		super(e);
	}

	public CommonException(String msg) {
		super(msg);
	}

	public CommonException(String msg, Throwable e) {
		super(msg, e);
	}
}
