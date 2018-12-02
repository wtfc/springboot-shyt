package com.jc.oa.po.workTask.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jc.oa.po.workTask.dao.IWorkTaskfinishDao;
import com.jc.oa.po.workTask.domain.WorkTask;
import com.jc.oa.po.workTask.domain.WorkTaskfinish;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.po.workTask.service.IWorkTaskfinishService;

/**
 * @title 个人办公
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-24
 */
@Service
public class WorkTaskfinishServiceImpl extends BaseServiceImpl<WorkTaskfinish> implements IWorkTaskfinishService{

	private IWorkTaskfinishDao taskfinishDao;
	
	public WorkTaskfinishServiceImpl(){}
	
	@Autowired
	public WorkTaskfinishServiceImpl(IWorkTaskfinishDao taskfinishDao){
		super(taskfinishDao);
		this.taskfinishDao = taskfinishDao;
	}
	 /**
	 * @description 查询完成任务ID
	 * @param WorkTaskfinish workTaskfinish 实体类
	 * @return List<WorkTaskfinish>
	 * @throws Exception
	 * @author	李洪宇
	 * @version  2014-05-22
	 */
	public List<WorkTaskfinish> getFinWorkTaskTaskId(WorkTaskfinish workTaskfinish){
		return taskfinishDao.getFinWorkTaskTaskId(workTaskfinish);
	}
	/**
	 * @description 分页查询方法(任务统计:已完成任务)
	 * @param WorkTaskfinish taskfinish 实体类
	 * @param PageManager page
	 * @return PageManager
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-05-22
	 */
	public PageManager getFinWorkTaskList(WorkTaskfinish workTaskfinish, PageManager page) {
		PageManager pageManager=null;
		workTaskfinish.setDeleteFlag(0);
		pageManager=dao.queryByPage(workTaskfinish, page);
		WorkTaskfinish workfinish=null;
		WorkTask workTask=null;
		List list=null;
		List finishTaskList=pageManager.getData();
		if (null!=finishTaskList && finishTaskList.size()>0) {//将完成任务原型转换成任务，以便于页面显示
			list=new ArrayList();
			for (int i = 0,j=finishTaskList.size(); i < j; i++) {
				workTask=new WorkTask();
				workfinish=(WorkTaskfinish)finishTaskList.get(i);
				BeanUtils.copyProperties(workfinish,workTask);
				workTask.setId(workfinish.getTaskId());
				list.add(workTask);
			}
			pageManager.setData(list);
		}
		return pageManager;
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
		return taskfinishDao.queryTaskProc(workTaskfinish);
	}
}