package com.jc.oa.finance.job;
import org.quartz.JobExecutionContext;

import com.jc.oa.finance.service.IToaFinanceBillService;
import com.jc.system.CustomException;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.job.CustomJob;

/**
 * 
 * @title 账单任务
 * @author 
 * @version 
 */
public class FinanceBillJob extends CustomJob {
	//账单表
	private IToaFinanceBillService toaFinanceBillService;
	
	public FinanceBillJob(){
		this.toaFinanceBillService=SpringContextHolder.getBean(IToaFinanceBillService.class);
	}

	@Override
	public void run(JobExecutionContext jobExecutionContext) {
		try {
			toaFinanceBillService.invoicesBill();
		} catch (CustomException e) {
			e.printStackTrace();
		}
	}
}
