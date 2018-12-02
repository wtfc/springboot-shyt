package com.jc.oa.finance.job;


import org.quartz.JobExecutionContext;

import com.jc.oa.finance.service.IToaFinanceIncomeService;
import com.jc.system.CustomException;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.job.CustomJob;


/**
 * 
 * @title 收入定时任务
 * @author zhanglg
 * @version 2014年5月23日 下午3:12:55
 */
public class FinanceIncomeJob extends CustomJob {
	
	//收入底表
	IToaFinanceIncomeService toaFinanceIncomeService;
	
	public FinanceIncomeJob(){
		this.toaFinanceIncomeService = SpringContextHolder.getBean(IToaFinanceIncomeService.class);
	}
	

	@Override
	public void run(JobExecutionContext jobExecutionContext) {
		try {
			toaFinanceIncomeService.updateFinanceIncome();
		} catch (CustomException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}