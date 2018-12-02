package com.jc.system.log.creator;

import com.jc.system.log.domain.LogBean;

/**
 * @description: 日志生产类
 * @created: 2013-10-25 下午1:52:26
 *            Rights Reserved.
 */
public abstract class LogCreator {
	public abstract void log(LogBean logBean);
}
