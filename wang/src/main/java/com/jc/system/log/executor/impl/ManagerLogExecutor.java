package com.jc.system.log.executor.impl;

import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.log.dao.ILogBeanDao;
import com.jc.system.log.dao.impl.LogBeanDaoImpl;
import com.jc.system.log.domain.LogBean;
import com.jc.system.log.executor.LogExecutor;
import com.jc.system.log.receiver.ManagerLogReciver;

/**
 * @description: 管理日志线程执行类
 * @author：孙圣然
 * @created: 2013-10-25 下午4:06:54
 * @version：$Id: ManagerLogExecutor.java 40809 2014-04-03 18:12:35Z sunjf $
 */
public class ManagerLogExecutor extends LogExecutor {

	private ILogBeanDao dao = (ILogBeanDao) SpringContextHolder
			.getBean(LogBeanDaoImpl.class);

	public void run() {
		log.info("开始activemq管理日志监听");
		while (true) {
			ManagerLogReciver receive = new ManagerLogReciver();
			LogBean bean = receive.reciveObject();
			dao.save(bean);
		}
	}

}
