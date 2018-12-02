package com.jc.oa.common.job;

import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;

import com.jc.oa.common.domain.Remind;
import com.jc.oa.common.service.IRemindService;
import com.jc.system.CustomException;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.job.CustomJob;

import flex.messaging.log.Log;

/**
 * @author Administrator
 * 
 */

public class OARemindJob extends CustomJob {

	/**
	 * 
	 */
	private IRemindService remindService;
	public OARemindJob() {
		// TODO Auto-generated constructor stub
		this.remindService = SpringContextHolder.getBean(IRemindService.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jc.system.job.domain.CustomJob#run(org.quartz.JobExecutionContext)
	 */
	@Override
	public void run(JobExecutionContext jobExecutionContext) {
		// TODO Auto-generated method stub
		// jobExecutionContext
		try {
			List<Remind> list = remindService.getNextReminds();
			if(list != null && list.size() > 0) {
				remindService.remindByMode(list);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
