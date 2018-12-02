package com.jc.oa.po.workTask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.po.workTask.dao.IWorkTaskRelevanceDao;
import com.jc.oa.po.workTask.domain.WorkTaskRelevance;
import com.jc.oa.po.workTask.service.IWorkTaskRelevanceService;

/**
 * @title 个人办公
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-07-15
 */
@Service
public class WorkTaskRelevanceServiceImpl extends BaseServiceImpl<WorkTaskRelevance> implements IWorkTaskRelevanceService{

	private IWorkTaskRelevanceDao workTaskRelevanceDao;
	
	public WorkTaskRelevanceServiceImpl(){}
	
	@Autowired
	public WorkTaskRelevanceServiceImpl(IWorkTaskRelevanceDao workTaskRelevanceDao){
		super(workTaskRelevanceDao);
		this.workTaskRelevanceDao = workTaskRelevanceDao;
	}

}