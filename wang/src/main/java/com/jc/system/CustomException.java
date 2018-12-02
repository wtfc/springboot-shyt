package com.jc.system;

import com.jc.foundation.domain.BaseBean;

/**
 * @description:自定义异常基类
 * @created: 2014年3月4日 上午8:42:20
 */
public class CustomException extends Exception {

	private static final long serialVersionUID = 5489251737337134504L;

	/**
	 * 构造函数
	 */
	public CustomException() {
	}

	public CustomException(String msg) {
		super(msg);
		setLogMsg(msg);
	}

	public CustomException(Throwable e) {
		super(e);
		if(e instanceof CustomException){
			setLogMsg(((CustomException) e).getLogMsg());
		}
	}

	public CustomException(String msg, Throwable e) {
		super(msg, e);
		setLogMsg(msg);
	}

	// 包含的基础类
	private BaseBean bean;

	public void setBean(BaseBean bean) {
		this.bean = bean;
	}

	// 记录到日志的信息
	private String logMsg = super.getMessage();
	
	//消息
	private String messageStr ="";
	
	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
		this.messageStr = logMsg;
	}
	
	public String getMessageStr() {
		return messageStr;
	}
	
	/**
	 * @description 获得记录到日志的信息字符串
	 * @return 结果字符串
	 * @version 1.0 上午10:12:28
	 */
	public String getLogMsg() {
		StringBuffer buffer = new StringBuffer();
		if (logMsg != null) {
			buffer.append(logMsg);
		}
		if (bean != null) {
			buffer.append("\r\n" + "data:" + bean.toLogMsg());
		}
		return buffer.toString();
	}
}
