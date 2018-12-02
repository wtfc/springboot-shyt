package com.jc.oa.common.job;

import org.quartz.JobExecutionContext;

import com.jc.oa.po.PoException;
import com.jc.oa.po.workTask.service.IWorkTaskService;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.job.CustomJob;

public class POTaskRemindJob extends CustomJob {

	private IWorkTaskService workTaskService;
	public POTaskRemindJob(){
		this.workTaskService=SpringContextHolder.getBean(IWorkTaskService.class);
	}
	@Override
	public void run(JobExecutionContext jobExecutionContext) {
		try {
			workTaskService.callRemind();//超期提醒
		} catch (PoException e) {
			e.printStackTrace();
		}
	}

}
