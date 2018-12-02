package com.jc.system.job.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.job.dao.IRunLogDao;
import com.jc.system.job.domain.RunLog;
import com.jc.system.job.service.IRunLogService;

/**
 * @title 系统任务
 * @description 业务实现类 
 * @version 2014-03-20 17:08
 * 
 */
@Service
public class RunLogServiceImpl extends BaseServiceImpl<RunLog> implements IRunLogService {

	private IRunLogDao runLogDao;
	
	@Autowired
	public RunLogServiceImpl(IRunLogDao runLogDao){
		super(runLogDao);
		this.runLogDao= runLogDao;
	}
	
	public RunLogServiceImpl(){
		
	}
}