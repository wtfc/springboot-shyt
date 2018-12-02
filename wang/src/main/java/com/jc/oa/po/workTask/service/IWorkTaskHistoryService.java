package com.jc.oa.po.workTask.service;

import java.util.List;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.oa.po.PoException;
import com.jc.oa.po.workTask.domain.WorkTaskHistory;

/**
 * @title 个人办公
 * @description  业务接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-23
 */

public interface IWorkTaskHistoryService extends IBaseService<WorkTaskHistory>{
	/**
	 * @description 任务查询中对于任务事件操作而对应保存任务信息
	 * @param WorkTaskHistory taskHistory 实体类
	 * @return Integer
	 * @throws Exception
	 * @author	李洪宇
	 * @version  2014-05-07
	 */
	public Integer saveWorkTask(WorkTaskHistory taskHistory)  throws PoException;
	/**
	 * @description 查询任务事件总数
	 * @param WorkTaskHistory taskHistory 实体类
	 * @return Integer
	 * @throws Exception
	 * @author	李洪宇
	 * @version  2014-05-09
	 */
	public Integer getWorkTaskHisTotal(WorkTaskHistory taskHistory);
	/**
	 * @description 查询任务事件ID
	 * @param WorkTaskHistory taskHistory 实体类
	 * @return List<WorkTaskHistory>
	 * @throws Exception
	 * @author	李洪宇
	 * @version  2014-05-20
	 */
	public List<WorkTaskHistory> getWorkTaskHisTaskId(WorkTaskHistory taskHistory);
	/**
	 * @description 根据任务ID及任务事件类型查询任务事件
	 * @param WorkTaskHistory taskHistory,PageManager page
	 * @return PageManager
	 * @throws Exception
	 * @author	李洪宇
	 * @version  2014-05-13
	 */
	public PageManager getTaskHistListByTaskId(WorkTaskHistory taskHistory,final PageManager page);
}