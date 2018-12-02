package com.jc.system.job;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.jc.system.job.domain.TimerTask;
import com.jc.system.job.service.ITimerTaskService;
import com.jc.system.job.util.TimerTaskUtils;

/**
 * @title 系统任务
 * @description 自动运行任务类
 * @version  2014-05-08 17:08
 */
public class DataBaseSchedulerFactoryBean extends SchedulerFactoryBean {
	@Autowired
	private ITimerTaskService service;
	
	public DataBaseSchedulerFactoryBean(){
	}
	
	/**
	 * @description 读取配置文件后自动运行的方法
	 * @return void
	 * @throws Exception
	 * @author 都业广
	 * @version 2014-03-20
	 */
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		TimerTask timerTask4Query = new TimerTask();

		System.out.println("########DataBaseSchedulerFactoryBean");
		//查询state值为1的数据(运行状态的数据)
		timerTask4Query.setState("1");
		List<TimerTask> list = service.queryAll(timerTask4Query);
		
		//遍历数据
		for (int i = 0; i < list.size(); i++) {

			System.out.println("########DataBaseSchedulerFactoryBean:"+list.size());
			TimerTask timerTask = list.get(i);
			try {
				
				//解析循环详情
				Map<String, String> hashmap = TimerTaskUtils.jsonToHashMap(timerTask.getCycleDetail());
				
				//将对应的数据保存到bean中
				timerTask = TimerTaskUtils.hashMapToBean(timerTask, hashmap);
				
				//取得任务的时间
				Date taskStartTime = timerTask.getStartAt();
				Date newDate = new Date();
				
				//判断任务的剩余执行次数
				String taskCount = timerTask.getTaskCounts();
				
				//有循环次数的情况
				if("1".equals(timerTask.getCycleType()) && taskCount != null && 
						!"".equals(taskCount) && timerTask.getEXT_STR1() != null && 
						!"".equals(timerTask.getEXT_STR1()) && taskStartTime.before(newDate)){
					taskCount = String.valueOf(Integer.parseInt(taskCount) - Integer.parseInt(timerTask.getEXT_STR1()));
					timerTask.setTaskCounts(taskCount);
					timerTask.setStartAt(new Date());
				}
				
				//加载系统任务
				this.getScheduler().scheduleJob(
						TimerTaskUtils.getJobDetail(timerTask),
						TimerTaskUtils.getTrigger(timerTask));
			} catch (Exception e) {
				logger.error("调度任务异常",e);
			}
			
		}

	}
}