package com.jc.system.log.impl;

import org.apache.log4j.Logger;

import com.jc.system.common.util.GlobalContext;
import com.jc.system.log.ILogQuery;
import com.jc.system.log.IOperateLog;
import com.jc.system.log.creator.LogCreator;
import com.jc.system.log.creator.factory.CommonCreatorFactory;
import com.jc.system.log.creator.factory.CommonLocalCreatorFactory;
import com.jc.system.log.creator.factory.CommonLocalDisruptorCreatorFactory;
import com.jc.system.log.creator.factory.ILogCreatorFactory;
import com.jc.system.log.creator.factory.ManagerCreatorFactory;
import com.jc.system.log.creator.factory.ManagerLocalCreatorFactory;
import com.jc.system.log.domain.LogBean;

/**
 * @description: 操作日志实现类
 * @created: 2013-10-25 下午1:51:19
 */
public class OperateLogImpl implements IOperateLog {
	private static Logger logger = Logger.getLogger(OperateLogImpl.class);

	@Override
	public void log(LogBean bean) {
		LogCreator creator = null;
		ILogCreatorFactory factory = null;
		if (GlobalContext.USER_ACTIVE == 0) {
			if (bean.getManagerFlag() == LogBean.MANAGE_FLAG_TRUE) {
				factory = new ManagerLocalCreatorFactory();
			} else {
				if (GlobalContext.USER_DSIRUPTOR == 1) {
					factory = new CommonLocalDisruptorCreatorFactory();
				} else {
					factory = new CommonLocalCreatorFactory();
				}
			}
		} else {
			if (bean.getManagerFlag() == LogBean.MANAGE_FLAG_TRUE) {
				factory = new ManagerCreatorFactory();
			} else {
				factory = new CommonCreatorFactory();
			}
		}
		try {
			creator = factory.createLogCreator();
			creator.log(bean);
		} catch (Exception e) {
			logger.error("发送日志失败", e);
		}
	}

	@Override
	public ILogQuery createLogQuery() {
		return new LogQueryImpl();
	}

}
