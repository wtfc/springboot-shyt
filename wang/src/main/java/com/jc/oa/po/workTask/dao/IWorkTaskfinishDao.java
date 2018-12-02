package com.jc.oa.po.workTask.dao;

import java.util.List;

import com.jc.oa.po.workTask.domain.WorkTask;
import com.jc.oa.po.workTask.domain.WorkTaskfinish;
import com.jc.foundation.dao.IBaseDao;


/**
 * @title 个人办公
 * @description  dao接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-24
 */
 
public interface IWorkTaskfinishDao extends IBaseDao<WorkTaskfinish>{

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
	 * @description 查询任务进度
	 * @param WorkTask task
	 * @return WorkTask
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-19
	 */
	public List<WorkTask> queryTaskProc(WorkTaskfinish workTaskfinish);
}
