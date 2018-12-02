package com.jc.oa.archive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.oa.archive.dao.IRecommendDao;
import com.jc.oa.archive.domain.Recommend;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.archive.service.IRecommendService;

/**
 * @title  GOA2.0源代码
 * @description  业务服务类
 * @author 
 * @version  2014-06-05
 */
@Service
public class RecommendServiceImpl extends BaseServiceImpl<Recommend> implements IRecommendService{

	private IRecommendDao recommendDao;
	
	public RecommendServiceImpl(){}
	
	@Autowired
	public RecommendServiceImpl(IRecommendDao recommendDao){
		super(recommendDao);
		this.recommendDao = recommendDao;
	}

}