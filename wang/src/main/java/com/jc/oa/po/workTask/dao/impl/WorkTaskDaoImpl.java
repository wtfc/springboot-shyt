package com.jc.oa.po.workTask.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.po.workTask.dao.IWorkTaskDao;
import com.jc.oa.po.workTask.domain.WorkTask;
import com.jc.system.common.util.Constants;

/**
 * @title 个人办公
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-23
 */
@Repository
public class WorkTaskDaoImpl extends BaseDaoImpl<WorkTask> implements IWorkTaskDao{

	public WorkTaskDaoImpl(){}
	
	/**
	 * @description 逻辑删除
	 * @param WorkTask  workTask 实体类
	 * @return Integer 
	 * @throws Exception
	 * @author 李洪宇
	 * @version 2014-04-30
	 */
	public Integer updateDeleteFlagByIds(WorkTask workTask) {
		return template.update(getNameSpace(workTask)+"."+Constants.DELETE_FLAG_BY_ID, workTask);
	}
	/**
	 * @description 对外抛出接口，用于查询所有任务(非分页)
	 * @param WorkTask  workTask 实体类
	 * @return List<WorkTask> 
	 * @throws Exception
	 * @author 李洪宇
	 * @version 2014-05-12
	 */
	public List<WorkTask> queryWorkTaskList(WorkTask workTask){
		List<WorkTask> list = template.selectList(getNameSpace(workTask) + "."+Constants.QUERY_WORK_TASK_LIST_OUT,workTask);
		return list;
	}
	/**
	 * @description 查询任务进度
	 * @param WorkTask task
	 * @return WorkTask
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-13
	 */
	public List<WorkTask> queryTaskProc(WorkTask task){
		return template.selectList(getNameSpace(task) + "."+Constants.QUERY_TASK_PROC,task);
	}
	/**
	 * @description 根据任务ID查询其对应的所有上级父任务ID
	 * @param Long taskId
	 * @return List<WorkTask>
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-27
	 */
	public List<WorkTask> getParentTask(Long taskId){
		WorkTask workTask=new WorkTask();
		workTask.setTaskId(taskId);
		List<WorkTask> list = template.selectList(getNameSpace(workTask) + "."+Constants.QUERY_PARENT_TASK,workTask);
		return list;
	}
	/**
	 * @description 查询所有任务
	 * @param WorkTask workTask
	 * @return List<WorkTask>
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-28
	 */
	public List<WorkTask> queryAllForUnion(WorkTask workTask){
		List<WorkTask> list = template.selectList(getNameSpace(workTask) + "."+Constants.QUERY_UNION, workTask);
		return list;
	}
	/**
	 * @description 取消任务模板
	 * @param WorkTask task
	 * @return Integer
	 * @author 李洪宇
	 * @version  2014-07-02
	 */
	public Integer cancalTemplate(WorkTask workTask) {
		return template.update(getNameSpace(workTask)+"."+Constants.CANCAL_TEMPLATE, workTask);
	}
	/**
	 * @description 对外抛出接口，用于查询待办任务(非分页)
	 * @param WorkTask  workTask 实体类
	 * @return List<WorkTask> 
	 * @throws Exception
	 * @author 李洪宇
	 * @version 2014-10-20
	 */
	public List<WorkTask> queryAbeyanceTaskList(WorkTask workTask){
		List<WorkTask> list = template.selectList(getNameSpace(workTask) + "."+Constants.QUERY_ABEYANCE_TASK_LIST_OUT,workTask);
		return list;
	}
	
	/**
	 * @description 查询portal中的待办任务数量
	 * @param WorkTask  workTask 实体类
	 * @return Long 
	 * @throws Exception
	 * @author 徐伟平
	 * @version 2014-12-04
	 */
	@Override
	public Long getWorkTaskCount(WorkTask workTask) {
		Object rowsCountObject = template.selectOne(getNameSpace(workTask) + "."+Constants.QUERY_WORKTASK_COUNT, workTask);
		if(rowsCountObject instanceof Long){
			return (Long) rowsCountObject;
		}
		else {
			return ((Integer) rowsCountObject).longValue();
		}
	}
}