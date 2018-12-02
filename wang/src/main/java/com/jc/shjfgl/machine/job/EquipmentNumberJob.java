package com.jc.shjfgl.machine.job;


import org.quartz.JobExecutionContext;

import com.jc.shjfgl.machine.service.IEquipmentService;
import com.jc.system.CustomException;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.job.CustomJob;


/**
 * 
 * @title 收入定时任务
 * @author zhanglg
 * @version 2014年5月23日 下午3:12:55
 */
public class EquipmentNumberJob extends CustomJob {
	
	//收入底表
	IEquipmentService equipmentService;
	
	public EquipmentNumberJob(){
		this.equipmentService = SpringContextHolder.getBean(IEquipmentService.class);
	}
	

	@Override
	public void run(JobExecutionContext jobExecutionContext) {
		try {
			equipmentService.updateEquipmentNumber();
		} catch (CustomException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}