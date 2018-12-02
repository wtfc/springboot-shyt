package com.jc.system.job.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.job.dao.ITimerTaskDao;
import com.jc.system.job.domain.TimerTask;

/**
 * @title 系统任务
 * @description dao实现类 
 * @version 2014-03-20 17:08
 * 
 */
@Repository
public class TimerTaskDaoImpl extends BaseDaoImpl<TimerTask> implements ITimerTaskDao {

	@Override
	public TimerTask queryByGroupName(TimerTask timerTask) {
		return template.selectOne(getNameSpace(timerTask) + ".queryByGroupName", timerTask);
	}

}