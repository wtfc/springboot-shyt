package com.jc.system.job.service;

import com.jc.system.CustomException;
import com.jc.system.job.domain.TimerTask;

/**
 * @title 系统任务
 * @description 业务接口类 
 * @version 2014-05-08 17:08
 */
public interface ITimerTaskMonitor {
	
	//系统任务恢复运行
	public Integer resume(TimerTask timerTask) throws CustomException;

	//系统任务暂停
	public Integer pause(TimerTask timerTask) throws CustomException;

	//删除系统任务
	public boolean remove(TimerTask timerTask) throws CustomException;

	//追加系统任务
	public Integer add(TimerTask timerTask);

	//更新系统任务
	public Integer update(TimerTask timerTask);
}
