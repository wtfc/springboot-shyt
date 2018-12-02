package com.jc.oa.po.workTask.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.oa.po.workTask.domain.WorkTask;
import com.jc.oa.po.workTask.domain.WorkTaskHistory;
import com.jc.oa.po.workTask.domain.WorkTaskfinish;
import com.jc.oa.po.workTask.dao.IWorkTaskfinishDao;
import com.jc.system.common.util.Constants;
import com.jc.foundation.dao.impl.BaseDaoImpl;

/**
 * @title 个人办公
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-24
 */
@Repository
public class WorkTaskfinishDaoImpl extends BaseDaoImpl<WorkTaskfinish> implements IWorkTaskfinishDao{

	public WorkTaskfinishDaoImpl(){}

	 /**
	 * @description 查询完成任务ID
	 * @param WorkTaskfinish workTaskfinish 实体类
	 * @return List<WorkTaskfinish>
	 * @throws Exception
	 * @author	李洪宇
	 * @version  2014-05-22
	 */
	public List<WorkTaskfinish> getFinWorkTaskTaskId(WorkTaskfinish workTaskfinish){
		 List<WorkTaskfinish> list=null;
		 list = template.selectList(getNameSpace(workTaskfinish) + ".queryFinWorkTask", workTaskfinish);
		 return list;
	}
	/**
	 * @description 查询任务进度
	 * @param WorkTask task
	 * @return WorkTask
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-19
	 */
	public List<WorkTask> queryTaskProc(WorkTaskfinish workTaskfinish){
		return template.selectList(getNameSpace(workTaskfinish) + "."+Constants.QUERY_TASK_PROC,workTaskfinish);
	}
}