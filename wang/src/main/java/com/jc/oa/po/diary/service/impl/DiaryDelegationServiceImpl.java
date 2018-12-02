package com.jc.oa.po.diary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.oa.po.diary.dao.IDiaryDelegationDao;
import com.jc.oa.po.diary.domain.DiaryDelegation;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.po.diary.service.IDiaryDelegationService;

/**
 * @title 个人办公
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 金峰
 * @version  2014-04-28
 */
@Service
public class DiaryDelegationServiceImpl extends BaseServiceImpl<DiaryDelegation> implements IDiaryDelegationService{

	private IDiaryDelegationDao diaryDelegationDao;
	
	public DiaryDelegationServiceImpl(){}
	
	@Autowired
	public DiaryDelegationServiceImpl(IDiaryDelegationDao diaryDelegationDao){
		super(diaryDelegationDao);
		this.diaryDelegationDao = diaryDelegationDao;
	}

}