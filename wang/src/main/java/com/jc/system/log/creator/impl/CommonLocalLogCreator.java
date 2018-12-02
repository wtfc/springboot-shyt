package com.jc.system.log.creator.impl;

import java.util.Date;

import com.jc.system.log.creator.LogCreator;
import com.jc.system.log.domain.LogBean;
import com.jc.system.log.queue.CommonLogQueue;

/**
 * @description: 普通日志生产者
 *            Rights Reserved.
 */
public class CommonLocalLogCreator extends LogCreator {

	@Override
	public void log(LogBean logBean) {
		if (logBean.getCreateDate() == null) {
			logBean.setCreateDate(new Date());
		}
		CommonLogQueue.add(logBean);
	}
}
