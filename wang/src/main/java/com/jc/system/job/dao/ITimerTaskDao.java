package com.jc.system.job.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.system.job.domain.TimerTask;

/**
 * @title 系统任务
 * @description dao接口类 
 * @version 2014-03-20 17:08
 * 
 */
public interface ITimerTaskDao extends IBaseDao<TimerTask> {

	public TimerTask queryByGroupName(TimerTask timerTask);
}
