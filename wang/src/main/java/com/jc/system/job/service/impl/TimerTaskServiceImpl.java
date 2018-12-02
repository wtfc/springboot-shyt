package com.jc.system.job.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.job.dao.ITimerTaskDao;
import com.jc.system.job.domain.TimerTask;
import com.jc.system.job.service.ITimerTaskService;

/**
 * @title 系统任务
 * @description 业务实现类 
 * @version 2014-05-08 17:08
 */
@Service
public class TimerTaskServiceImpl extends BaseServiceImpl<TimerTask> implements
		ITimerTaskService {

	
	private ITimerTaskDao timerTaskDao;

	
	public ITimerTaskDao getTimerTaskDao() {
		return timerTaskDao;
	}


	public void setTimerTaskDao(ITimerTaskDao timerTaskDao) {
		this.timerTaskDao = timerTaskDao;
	}


	@Autowired
	public TimerTaskServiceImpl(ITimerTaskDao timerTaskDao){
		super(timerTaskDao);
		this.timerTaskDao= timerTaskDao;
	}
	public TimerTaskServiceImpl(){
		
	}


	@Override
	public TimerTask queryByGroupName(TimerTask timerTask) {
		return timerTaskDao.queryByGroupName(timerTask);
	}
}