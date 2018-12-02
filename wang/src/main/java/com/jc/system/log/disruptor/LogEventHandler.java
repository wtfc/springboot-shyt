package com.jc.system.log.disruptor;

import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.log.dao.ILogBeanDao;
import com.jc.system.log.dao.impl.LogBeanDaoImpl;
import com.lmax.disruptor.EventHandler;

/**
 * @description: disruptor事件处理类
 */
public class LogEventHandler implements EventHandler<LogEvent> {

	@Override
	public void onEvent(LogEvent event, long sequence, boolean endOfBatch)
			throws Exception {
		ILogBeanDao dao = (ILogBeanDao) SpringContextHolder
				.getBean(LogBeanDaoImpl.class);
		dao.save(event.getLogList());
	}

}
