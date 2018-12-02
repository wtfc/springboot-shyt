package com.jc.oa.archive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.oa.archive.dao.IHistoryDao;
import com.jc.oa.archive.domain.History;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.archive.service.IHistoryService;

/**
 * @title  GOA2.0源代码
 * @description  业务服务类
 * @author 
 * @version  2014-06-05
 */
@Service
public class HistoryServiceImpl extends BaseServiceImpl<History> implements IHistoryService{

	private IHistoryDao historyDao;
	
	public HistoryServiceImpl(){}
	
	@Autowired
	public HistoryServiceImpl(IHistoryDao historyDao){
		super(historyDao);
		this.historyDao = historyDao;
	}

}