package com.jc.oa.po.workTask.dao;
import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.oa.po.workTask.domain.WorkTask;

/**
 * @title 个人办公
 * @description  dao接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-23
 */
 
public interface IWorkTaskDao extends IBaseDao<WorkTask>{
	/**
	 * @description 逻辑删除
	 * @param WorkTask  workTask 实体类
	 * @return Integer 
	 * @throws Exception
	 * @author 李洪宇
	 * @version 2014-04-30
	 */
	public Integer updateDeleteFlagByIds(WorkTask workTask);
	/**
	 * @description 对外抛出接口，用于查询所有任务
	 * @param WorkTask  workTask 实体类
	 * @return List<WorkTask> 
	 * @throws Exception
	 * @author 李洪宇
	 * @version 2014-05-12
	 */
	public List<WorkTask> queryWorkTaskList(WorkTask workTask);
	/**
	 * @description 查询任务进度
	 * @param WorkTask task
	 * @return WorkTask
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-13
	 */
	public List<WorkTask> queryTaskProc(WorkTask task);
	/**
	 * @description 根据任务ID查询其对应的所有上级父任务ID
	 * @param Long taskId
	 * @return List<WorkTask>
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-27
	 */
	public List<WorkTask> getParentTask(Long taskId);
	/**
	 * @description 查询所有任务
	 * @param WorkTask workTask
	 * @return List<WorkTask>
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-28
	 */
	public List<WorkTask> queryAllForUnion(WorkTask workTask);
	/**
	 * @description 取消任务模板
	 * @param WorkTask task
	 * @return Integer
	 * @author 李洪宇
	 * @version  2014-07-02
	 */
	public Integer cancalTemplate(WorkTask workTask);
	/**
	 * @description 对外抛出接口，用于查询待办任务(非分页)
	 * @param WorkTask  workTask 实体类
	 * @return List<WorkTask> 
	 * @throws Exception
	 * @author 李洪宇
	 * @version 2014-10-20
	 */
	public List<WorkTask> queryAbeyanceTaskList(WorkTask workTask);
	
	/**
	 * 方法描述：查询待办任务（未接收）数量
	 * @param workTask
	 * @return Long
	 * @author 徐伟平
	 * @version  2014年12月04日
	 * @see
	 */
	public Long getWorkTaskCount(WorkTask workTask);
}
