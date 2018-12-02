package com.jc.oa.po;

import com.jc.system.CustomException;

/**
 * @description:个人办公异常子类
 * @created: 2014年4月24日 上午8:42:20
 * @version：$Id: CustomException.java 40809 2014-04-03 18:12:35Z sunjf $
 *            Rights Reserved.
 */
public class PoException extends CustomException {
	private static final long serialVersionUID = 6056833367187703061L;
	
	/**
	 * 构造函数
	 */
	public PoException() {
	}
	

	public PoException(Throwable e) {
		super(e);
	}

	public PoException(String msg) {
		super(msg);
	}
}
