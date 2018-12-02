package com.jc.oa.po.workTask.service;

import java.util.List;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.oa.po.workTask.domain.WorkTask;
import com.jc.oa.po.workTask.domain.WorkTaskfinish;

/**
 * @title 个人办公
 * @description  业务接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-24
 */

public interface IWorkTaskfinishService extends IBaseService<WorkTaskfinish>{
	 /**
	 * @description 查询完成任务ID
	 * @param WorkTaskfinish workTaskfinish 实体类
	 * @return List<WorkTaskfinish>
	 * @throws Exception
	 * @author	李洪宇
	 * @version  2014-05-22
	 */
	public List<WorkTaskfinish> getFinWorkTaskTaskId(WorkTaskfinish workTaskfinish);
	/**
	 * @description 分页查询方法(任务统计:已完成任务)
	 * @param WorkTaskfinish taskfinish 实体类
	 * @param PageManager page
	 * @return PageManager
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-05-22
	 */
	public PageManager getFinWorkTaskList(WorkTaskfinish workTaskfinish, PageManager page);
	/**
	 * @description 查询任务进度
	 * @param WorkTask task
	 * @return WorkTask
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-19
	 */
	public List<WorkTask> queryTaskProc(WorkTaskfinish workTaskfinish);
}