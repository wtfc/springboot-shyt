package com.jc.system.job.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.job.domain.TimerTask;

/**
 * @title 系统任务
 * @description 业务接口类 
 * @version 2014-05-08 17:08
 */
public interface ITimerTaskService extends IBaseService<TimerTask>{
	
	public TimerTask queryByGroupName(TimerTask timerTask);
}