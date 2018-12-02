package com.jc.system.log.receiver;

import org.apache.log4j.Logger;

import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.log.dao.ILogBeanDao;
import com.jc.system.log.dao.impl.LogBeanDaoImpl;
import com.jc.system.log.domain.LogBean;

/**
 * @description: 管理者日志本地日志接受者
 * @created: 2013-10-29 上午10:42:36
 * @version：$Id: ManagerLocalLogReciver.java 40809 2014-04-03 18:12:35Z sunjf $
 */
public class ManagerLocalLogReciver implements Runnable {

	Logger log = Logger.getLogger(ManagerLocalLogReciver.class);

	LogBean logBean;

	ILogBeanDao dao = (ILogBeanDao) SpringContextHolder
			.getBean(LogBeanDaoImpl.class);

	public ManagerLocalLogReciver(LogBean logBean) {
		this.logBean = logBean;
	}

	@Override
	public void run() {
		try {
			dao.save(logBean);
		} catch (Exception e) {
			log.error("插入管理员日志失败", e);
		}
	}

}
