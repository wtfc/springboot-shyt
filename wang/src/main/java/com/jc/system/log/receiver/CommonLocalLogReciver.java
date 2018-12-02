package com.jc.system.log.receiver;

import com.jc.system.log.domain.LogBean;
import com.jc.system.log.queue.CommonLogQueue;

/**
 * @description: 普通日志记录
 * @created: 2013-10-28 上午9:30:10
 * @version：$Id: CommonLocalLogReciver.java 40809 2014-04-03 18:12:35Z sunjf $
 */
public class CommonLocalLogReciver {
	public LogBean reciveObject() {
		LogBean bean = CommonLogQueue.poll();
		return bean;
	}
}
