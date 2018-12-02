package com.jc.oa.po.readingstatus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.po.readingstatus.dao.IReadingStatusDao;
import com.jc.oa.po.readingstatus.domain.ReadingStatus;
import com.jc.oa.po.readingstatus.service.IReadingStatusService;

/**
 * @title 个人办公
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-05-22
 */
@Service
public class ReadingStatusServiceImpl extends BaseServiceImpl<ReadingStatus> implements IReadingStatusService{

	private IReadingStatusDao readingStatusDao;
	
	public ReadingStatusServiceImpl(){}
	
	@Autowired
	public ReadingStatusServiceImpl(IReadingStatusDao readingStatusDao){
		super(readingStatusDao);
		this.readingStatusDao = readingStatusDao;
	}

}