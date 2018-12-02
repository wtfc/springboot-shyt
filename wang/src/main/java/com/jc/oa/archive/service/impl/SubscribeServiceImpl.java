package com.jc.oa.archive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.oa.archive.dao.ISubscribeDao;
import com.jc.oa.archive.domain.Subscribe;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.archive.service.ISubscribeService;

/**
 * @title  GOA2.0源代码
 * @description  业务服务类
 * @author 
 * @version  2014-06-05
 */
@Service
public class SubscribeServiceImpl extends BaseServiceImpl<Subscribe> implements ISubscribeService{

	private ISubscribeDao subscribeDao;
	
	public SubscribeServiceImpl(){}
	
	@Autowired
	public SubscribeServiceImpl(ISubscribeDao subscribeDao){
		super(subscribeDao);
		this.subscribeDao = subscribeDao;
	}

}