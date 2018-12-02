package com.jc.system.log.executor.impl;

import java.util.ArrayList;
import java.util.List;

import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.log.dao.ILogBeanDao;
import com.jc.system.log.dao.impl.LogBeanDaoImpl;
import com.jc.system.log.disruptor.DisruptorContext;
import com.jc.system.log.disruptor.DisruptorLogCreate;
import com.jc.system.log.domain.LogBean;

/**
 * @description: 多线程插入数据库
 * @author：孙圣然
 * @created: 2013-10-29 下午4:02:46
 * @version：$Id: InsertLogThread.java 40809 2014-04-03 18:12:35Z sunjf $
 */
public class InsertLogThread implements Runnable {
	private ILogBeanDao dao = (ILogBeanDao) SpringContextHolder
			.getBean(LogBeanDaoImpl.class);
	List<LogBean> list = new ArrayList<LogBean>();

	public void setList(List<LogBean> list) {
		this.list = list;
	}

	@Override
	public void run() {
		if (GlobalContext.USER_DSIRUPTOR == 1) {
			DisruptorLogCreate creator = new DisruptorLogCreate(
					DisruptorContext.getRingBuffer());
			creator.log(list);
		} else {
			dao.save(list);
		}
	}

}
