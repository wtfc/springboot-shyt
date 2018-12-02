package com.jc.system.log.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jc.foundation.domain.IPageManager;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.log.ILogQuery;
import com.jc.system.log.dao.ILogBeanDao;
import com.jc.system.log.dao.impl.LogBeanDaoImpl;
import com.jc.system.log.domain.LogBean;

/**
 * @description: 查询实现类
 * @created: 2013-10-30 上午9:25:56
 */
public class LogQueryImpl implements ILogQuery {

	private LogBean logBean = new LogBean();
	private ILogBeanDao dao = (ILogBeanDao) SpringContextHolder
			.getBean(LogBeanDaoImpl.class);

	public LogQueryImpl() {
		logBean.setManagerFlag(LogBean.MANAGE_FLAG_FALSE);
	}

	@Override
	public ILogQuery setUsesId(String userId) {
		logBean.setUserId(userId);
		return this;
	}

	@Override
	public ILogQuery setStartDate(Date startDate) {
		logBean.setStartDate(startDate);
		return this;
	}

	@Override
	public ILogQuery setEndDate(Date endDate) {
		logBean.setEndDate(endDate);
		return this;
	}

	@Override
	public ILogQuery setOperType(String operType) {
		logBean.setOperType(operType);
		return this;
	}

	@Override
	public ILogQuery isManager(Boolean managerFlag) {
		if (managerFlag == true) {
			logBean.setManagerFlag(LogBean.MANAGE_FLAG_TRUE);
		} else {
			logBean.setManagerFlag(LogBean.MANAGE_FLAG_FALSE);
		}
		return this;
	}

	@Override
	public List<LogBean> list() {
		return dao.queryAll(logBean);
	}

	@Override
	public Map<String, Object> listByPage(IPageManager page) {
		return dao.queryByPage(logBean, page);
	}

}
