package com.jc.oa.ic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.oa.ic.dao.IConGroupRDao;
import com.jc.oa.ic.domain.ConGroupR;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.service.IConGroupRService;

/**
 * @title 互动交流
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-05-08
 */
@Service
public class ConGroupRServiceImpl extends BaseServiceImpl<ConGroupR> implements IConGroupRService{

	private IConGroupRDao conGroupRDao;
	
	public ConGroupRServiceImpl(){}
	
	@Autowired
	public ConGroupRServiceImpl(IConGroupRDao conGroupRDao){
		super(conGroupRDao);
		this.conGroupRDao = conGroupRDao;
	}

}