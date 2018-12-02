package com.jc.oa.ic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.oa.ic.dao.ISugRepDao;
import com.jc.oa.ic.domain.SugRecipient;
import com.jc.oa.ic.domain.SugRep;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.service.ISugRepService;

/**
 * @title 互动交流
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-17
 */
@Service
public class SugRepServiceImpl extends BaseServiceImpl<SugRep> implements ISugRepService{

	private ISugRepDao sugRepDao;
	
	@Autowired
	public SugRepServiceImpl(ISugRepDao sugRepDao){
		super(sugRepDao);
		this.sugRepDao = sugRepDao;
	}
	public SugRepServiceImpl(){
	}
	/**
	* @description 修改删除标记
	* @param SugRecipient sugRecipient 实体类
	* @return Integer 操作结果
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-10 
	*/
	@Override
	public Integer updateDeleteFlagByIds(SugRep sugRep) {
		return sugRepDao.updateDeleteFlagByIds(sugRep);
	}
	
}