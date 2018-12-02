package com.jc.oa.po.workTask.dao;

import java.util.List;

import com.jc.oa.po.workTask.domain.WorkTaskHistory;
import com.jc.foundation.dao.IBaseDao;
import com.jc.foundation.domain.PageManager;


/**
 * @title 个人办公
 * @description  dao接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-23
 */
 
public interface IWorkTaskHistoryDao extends IBaseDao<WorkTaskHistory>{

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
}
