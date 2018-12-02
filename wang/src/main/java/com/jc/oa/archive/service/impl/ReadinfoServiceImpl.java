package com.jc.oa.archive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.oa.archive.dao.IReadinfoDao;
import com.jc.oa.archive.domain.Readinfo;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.archive.service.IReadinfoService;

/**
 * @title  GOA2.0源代码
 * @description  业务服务类
 * @author 
 * @version  2014-06-05
 */
@Service
public class ReadinfoServiceImpl extends BaseServiceImpl<Readinfo> implements IReadinfoService{

	private IReadinfoDao readinfoDao;
	
	public ReadinfoServiceImpl(){}
	
	@Autowired
	public ReadinfoServiceImpl(IReadinfoDao readinfoDao){
		super(readinfoDao);
		this.readinfoDao = readinfoDao;
	}

}