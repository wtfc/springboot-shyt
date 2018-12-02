package com.jc.system.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.system.CustomException;
import com.jc.system.job.domain.TimerTask;
import com.jc.system.job.service.ITimerTaskMonitor;
import com.jc.system.job.service.ITimerTaskService;
import com.jc.system.job.util.TimerTaskUtils;

/**
 * @title 系统任务
 * @description 业务实现类 
 * @version 2014-05-08 17:08
 */
@Service
public class TimerTaskMonitorImpl implements ITimerTaskMonitor {
	private static Logger logger = Logger.getLogger(TimerTaskMonitorImpl.class);
	@Resource
	private Scheduler scheduler;
	
	@Autowired
	private ITimerTaskService timerTaskService;

	public TimerTaskMonitorImpl() {
	}

	/**
	 * @description 恢复系统任务的运行
	 * @param TimerTask timerTask 实体bean
	 * @return Integer 失败返回0,成功返回1
	 * @throws CustomException 
	 * @author 都业广
	 * @version 2014-05-12 15:50
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer resume(TimerTask timerTask) throws CustomException {
		Integer flag = 0;
		try {
			//更新成功提示
			flag = timerTaskService.update(timerTask);
			
			//更新成功时再更新系统任务状态
			if(flag == 1){
				scheduler.resumeTrigger(TimerTaskUtils.getTriggerKey(timerTask.getId()));
			}
			
			return flag;
		} catch (SchedulerException e) {
			logger.error("resume exception", e);
			return flag;
		}

	}

	/**
	 * @description 暂停系统任务的运行
	 * @param TimerTask timerTask 实体bean
	 * @return Integer 失败返回0,成功返回1
	 * @throws CustomException 
	 * @author 都业广
	 * @version 2014-05-12 15:50
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer pause(TimerTask timerTask) throws CustomException {
		Integer flag = 0;
		try {
			//停止成功提示
			flag = timerTaskService.update(timerTask);
			
			//更新成功时,再更新系统状态
			if(flag == 1){
				scheduler.pauseTrigger(TimerTaskUtils.getTriggerKey(timerTask.getId()));
			}
			
			return flag;
		} catch (SchedulerException e) {
			logger.error("pause exception", e);
			return flag;
		}
	}
	
	/**
	 * @description 删除系统任务
	 * @param TimerTask timerTask 实体bean
	 * @return boolean 失败返回false,成功返回true
	 * @throws CustomException 
	 * @author 都业广
	 * @version 2014-05-12 15:50
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean remove(TimerTask timerTask) throws CustomException {
		
		Integer flag = 0;
		
		try {
			//物理删除（第二个参数传入false）
			flag = timerTaskService.delete(timerTask,false);
			
			//数据库成功删除数据时
			if(flag != 0){
				//取得需要删除数据的主键串(格式:1,2,3,4)
				String ids[] = timerTask.getPrimaryKeys();
				
				//循环产生任务需要的triggerkey
				List<TriggerKey> triggerList = new ArrayList<TriggerKey>();
				for(int i=0; i<ids.length; i++){
					triggerList.add(TimerTaskUtils.getTriggerKey(Long.parseLong(ids[i])));
				}
				
				//删除掉进行中的任务
				scheduler.unscheduleJobs(triggerList);
			}
			
			return true;
		} catch (SchedulerException e) {
			logger.error("remove exception", e);
			return false;
		}
	}

	/**
	 * @description 追加系统任务
	 * @param TimerTask timerTask 实体bean
	 * @return boolean 失败返回false,成功返回true
	 * @author 都业广
	 * @version 2014-05-12 15:50
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer add(TimerTask timerTask) {
		
		Integer flag = 0;
		
		try {
			//追加系统任务信息
		    flag = timerTaskService.save(timerTask);
			
			//数据库成功追加系统任务时
			if(flag == 1){
				scheduler.scheduleJob(TimerTaskUtils.getJobDetail(timerTask),
						TimerTaskUtils.getTrigger(timerTask));
			}
			
			return flag;
		} catch (Exception e) {
			logger.error("add exception", e);
			return flag;
		}
	}

	/**
	 * @description 更新系统任务
	 * @param TimerTask timerTask 实体bean
	 * @return Integer 失败返回0,成功返回1
	 * @author 都业广
	 * @version 2014-05-12 15:50
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer update(TimerTask timerTask) {
		
		Integer flag = 0;
		
		try {
			flag = timerTaskService.update(timerTask);

			//更新数据库成功时,再更新系统任务
			if(flag == 1){
				//先删除再追加
				scheduler.unscheduleJob(TimerTaskUtils.getTriggerKey(timerTask));
				scheduler.scheduleJob(TimerTaskUtils.getJobDetail(timerTask),
						TimerTaskUtils.getTrigger(timerTask));
			}
			return flag;
		} catch (Exception e) {
			logger.error("update exception", e);
			return flag;
		}
	}

}
