package com.jc.oa.po.workTask.service.impl;

import com.jc.oa.po.workTask.dao.IWorkTaskDao;
import com.jc.oa.po.workTask.domain.WorkTask;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.remind.service.IRemindService;
import com.jc.system.security.SystemSecurityUtils;

/**
 * 
 * @title GOA V2.3 待办任务Portal 数量统计提醒
 * @description  
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author xuweiping
 * @version  2014年12月04日 
 */
public class WorkTaskRemindImpl implements IRemindService {

	
	private IWorkTaskDao workTaskDao=SpringContextHolder.getBean(IWorkTaskDao.class);

	@Override
	public String getRemindCount() {
		try {
			WorkTask workTask = new WorkTask();
			workTask.setDirectorId(SystemSecurityUtils.getUser().getId());
			workTask.setStatus("0");
			workTask.setDeleteFlag(0);
			
			Long count = workTaskDao.getWorkTaskCount(workTask);
			return count.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
