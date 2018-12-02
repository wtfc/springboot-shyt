package com.jc.system.log.creator.impl;

import java.util.Date;

import com.jc.system.log.activemq.ActivemqUtil;
import com.jc.system.log.creator.LogCreator;
import com.jc.system.log.domain.LogBean;

/**
 * @description: 管理员日志生产者
 *            Rights Reserved.
 */
public class ManagerLogCreator extends LogCreator {

	private final String DESTINATION_STR = "managerLog";

	@Override
	public void log(LogBean logBean) {
		if (logBean.getCreateDate() == null) {
			logBean.setCreateDate(new Date());
		}
		ActivemqUtil.sendMessage(logBean, DESTINATION_STR);
	}

}
