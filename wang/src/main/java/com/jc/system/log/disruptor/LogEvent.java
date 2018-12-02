package com.jc.system.log.disruptor;

import java.util.List;

import com.jc.system.log.domain.LogBean;
import com.lmax.disruptor.EventFactory;

/**
 * @description: disruptor中事件类
 */
public final class LogEvent {
	private LogBean log;
	private List<LogBean> logList;

	public LogBean getLog() {
		return log;
	}

	public void setLog(LogBean log) {
		this.log = log;
	}

	public void setLogList(List<LogBean> logList) {
		this.logList = logList;
	}

	public List<LogBean> getLogList() {
		return logList;
	}

	public final static EventFactory<LogEvent> EVENT_FACTORY = new EventFactory<LogEvent>() {
		@Override
		public LogEvent newInstance() {
			return new LogEvent();
		}
	};
}
