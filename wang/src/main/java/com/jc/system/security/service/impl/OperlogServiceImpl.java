package com.jc.system.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.security.dao.IOperlogDao;
import com.jc.system.security.domain.Operlog;
import com.jc.system.security.service.IOperlogService;

/**
 * @title GOA2.0
 * @description  业务服务类
 * @author 
 * @version  2014-05-04
 */
@Service
public class OperlogServiceImpl extends BaseServiceImpl<Operlog> implements IOperlogService{

	private IOperlogDao operlogDao;
	
	public OperlogServiceImpl(){}
	
	@Autowired
	public OperlogServiceImpl(IOperlogDao operlogDao){
		super(operlogDao);
		this.operlogDao = operlogDao;
	}

}