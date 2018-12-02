package com.jc.oa.doc;

import com.jc.system.CustomException;

/**
 * @description:文档管理和知识管理异常子类
 * @author：王世元
 * @created: 2014年4月24日 上午8:42:20
 * @version：$Id: CustomException.java 40809 2014-04-03 18:12:35Z sunjf $
 * @copyright ©1995-2014 Changchun Jiacheng Project of the Network Co.,ltd.All
 *            Rights Reserved.
 */
public class DocException extends CustomException {
	private static final long serialVersionUID = 8887403654020426556L;

	/**
	 * 构造函数
	 */
	public DocException() {
	}

	public DocException(String msg) {
		super(msg);
	}
	
	public DocException(Throwable e) {
		super(e);
	}
}
