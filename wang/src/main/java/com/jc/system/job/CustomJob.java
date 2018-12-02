package com.jc.system.job;

import java.util.Date;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;

import com.jc.system.CustomException;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.job.domain.RunLog;
import com.jc.system.job.domain.TimerTask;
import com.jc.system.job.service.IRunLogService;
import com.jc.system.job.service.ITimerTaskMonitor;
import com.jc.system.job.service.ITimerTaskService;
import com.jc.system.job.util.TimerTaskUtils;

/**
 * @title 系统任务
 * @description 系统任务抽象类
 * @version  2014-05-08 17:08
 */
public abstract class CustomJob implements Job {
	
	public CustomJob() {
		this.runLogService = SpringContextHolder.getBean(IRunLogService.class);
		this.timerTaskMonitor = SpringContextHolder.getBean(ITimerTaskMonitor.class);
		this.timerTaskService = SpringContextHolder.getBean(ITimerTaskService.class);
	}
	
	private ITimerTaskService timerTaskService;
	private ITimerTaskMonitor timerTaskMonitor;

	public ITimerTaskMonitor getTimerTaskMonitor() {
		return timerTaskMonitor;
	}

	public void setTimerTaskMonitor(ITimerTaskMonitor timerTaskMonitor) {
		this.timerTaskMonitor = timerTaskMonitor;
	}

	private IRunLogService runLogService;

	public IRunLogService getRunLogService() {
		return runLogService;
	}

	public void setRunLogService(IRunLogService runLogService) {
		this.runLogService = runLogService;
	}

	public abstract void run(JobExecutionContext jobExecutionContext);

	@Override
	public void execute(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		
		//加入log信息
		RunLog runLog = new RunLog();
		Trigger trigger = jobExecutionContext.getTrigger();
		
		//设置triggerKey(与数据库主键id相同)
		runLog.setTimerTaskId(Integer.parseInt(trigger.getKey().getName()));
		
		//设置组名(默认为default)
		runLog.setGroupName(trigger.getKey().getGroup());
		
		//开始时间
		runLog.setStartAt(new Date());
		try {
			run(jobExecutionContext);
			
			//定义定时任务对象
			TimerTask tt = new TimerTask();
			tt.setId(Long.parseLong(jobExecutionContext.getTrigger().getKey().toString().replaceAll("DEFAULT.", "")));
			
			//如果任务不再执行,则将系统任务中的状态置为暂停
			if(null == jobExecutionContext.getNextFireTime()){
				tt.setState(CycleType.SYS_JOB_STATE_PAUSE);
				timerTaskMonitor.pause(tt);
				
			//否则的话记录执行次数
			}else{
				tt = timerTaskService.get(tt);
				
				//解析循环详情
				Map<String, String> hashmap = TimerTaskUtils.jsonToHashMap(tt.getCycleDetail());
				
				//将对应的数据保存到bean中
				tt = TimerTaskUtils.hashMapToBean(tt, hashmap);
				
				//如果不是次数型循环则不更新执行次数
				if(!"".equals(tt.getTaskCounts()) && null != tt.getTaskCounts() && "1".equals(tt.getCycleType())){
					int repeatCount = Integer.parseInt(tt.getEXT_STR1());
					repeatCount++;
					tt.setEXT_STR1(String.valueOf(repeatCount));
					timerTaskService.update(tt);
				}
			}
			
			runLog.setJobData(this.getClass().getName()+":" + "执行成功");
		} catch (Exception e) {
		    runLog.setJobData(this.getClass().getName()+":" + "执行失败" 
		            + "(ErrorMessage: " + e.getMessage() + ")");
		} finally {
            //结尾时间
			runLog.setEndAt(new Date());
			try {
                runLogService.save(runLog);
            } catch (CustomException e) {
                e.printStackTrace();
            }
		}
	}

}
