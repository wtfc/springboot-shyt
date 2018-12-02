package com.jc.oa.po.workTask.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title 个人办公
 * @description 公共_个人办公_督办协办_任务关联表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-07-15
 */

public class WorkTaskRelevance extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long   taskId;          /*任务ID*/
	private Long   taskPersonId;    /*任务操作人ID*/
	private String isParent;        /*是否为操作人的父任务[0-否;1-是]*/
	private String taskType;        /*任务关系类型[0-发起人;1-负责人]*/

	public Long getTaskId(){
		return taskId;
	}
	
	public void setTaskId(Long taskId){
		this.taskId = taskId;
	}
	
	public Long getTaskPersonId(){
		return taskPersonId;
	}
	
	public void setTaskPersonId(Long taskPersonId){
		this.taskPersonId = taskPersonId;
	}
	
	public String getIsParent(){
		return isParent;
	}
	
	public void setIsParent(String isParent){
		this.isParent = isParent;
	}
	
	public String getTaskType(){
		return taskType;
	}
	
	public void setTaskType(String taskType){
		this.taskType = taskType;
	}
	
}