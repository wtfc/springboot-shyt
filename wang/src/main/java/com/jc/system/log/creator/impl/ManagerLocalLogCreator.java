package com.jc.system.log.creator.impl;

import java.util.Date;

import com.jc.system.log.creator.LogCreator;
import com.jc.system.log.domain.LogBean;
import com.jc.system.log.receiver.ManagerLocalLogReciver;

/**
 * @description: 管理员本地日志生产者
 *            Rights Reserved.
 */
public class ManagerLocalLogCreator extends LogCreator {

	@Override
	public void log(LogBean logBean) {
		if (logBean.getCreateDate() == null) {
			logBean.setCreateDate(new Date());
		}
		ManagerLocalLogReciver receive = new ManagerLocalLogReciver(logBean);
		receive.run();
	}
}
