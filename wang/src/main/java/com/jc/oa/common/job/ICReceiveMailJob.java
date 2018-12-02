package com.jc.oa.common.job;

import org.quartz.JobExecutionContext;

import com.jc.oa.common.service.IRemindService;
import com.jc.oa.ic.service.IMailService;
import com.jc.system.CustomException;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.job.CustomJob;

/**
 * 
 * @title GOA V2.0
 * @author zhanglg
 * @version 2014年5月23日 下午3:12:55
 */
public class ICReceiveMailJob extends CustomJob {

	IMailService mailService;

	public ICReceiveMailJob() {
		this.mailService = SpringContextHolder.getBean(IMailService.class);
	}

	@Override
	public void run(JobExecutionContext jobExecutionContext) {
		try {
			mailService.autoReceiveAllExtMail();
		} catch (CustomException e) {
			e.printStackTrace();
		}
	}

}
