package com.jc.oa.ic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.dao.ICallsDao;
import com.jc.oa.ic.domain.Calls;
import com.jc.oa.ic.service.ICallsService;

/**
 * @title HR
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-29
 */
@Service
public class CallsServiceImpl extends BaseServiceImpl<Calls> implements ICallsService{

	private ICallsDao callsDao;
	
	public CallsServiceImpl(){}
	
	@Autowired
	public CallsServiceImpl(ICallsDao callsDao){
		super(callsDao);
		this.callsDao = callsDao;
	}

}