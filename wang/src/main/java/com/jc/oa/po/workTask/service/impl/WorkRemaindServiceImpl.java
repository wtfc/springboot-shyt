package com.jc.oa.po.workTask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.oa.po.workTask.dao.IWorkRemaindDao;
import com.jc.oa.po.workTask.domain.WorkRemaind;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.po.workTask.service.IWorkRemaindService;

/**
 * @title 个人办公
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-23
 */
@Service
public class WorkRemaindServiceImpl extends BaseServiceImpl<WorkRemaind> implements IWorkRemaindService{

	private IWorkRemaindDao remaindDao;
	
	public WorkRemaindServiceImpl(){}
	
	@Autowired
	public WorkRemaindServiceImpl(IWorkRemaindDao remaindDao){
		super(remaindDao);
		this.remaindDao = remaindDao;
	}

}