package com.jc.system.log.executor;

import org.apache.log4j.Logger;

/**
 * @description: 日志执行接口
 * @created: 2013-10-25 下午4:07:13
 */
public abstract class LogExecutor implements Runnable {
	protected Logger log = Logger.getLogger(this.getClass());

	public abstract void run();
}
