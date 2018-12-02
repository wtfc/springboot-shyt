package com.jc.oa.ic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.oa.ic.dao.ISugRecipientDao;
import com.jc.oa.ic.domain.SugRecipient;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.service.ISugRecipientService;

/**
 * @title GOA V2.0 互动交流
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */
@Service
public class SugRecipientServiceImpl extends BaseServiceImpl<SugRecipient> implements ISugRecipientService{

	private ISugRecipientDao sugRecipientDao;
	
	@Autowired
	public SugRecipientServiceImpl(ISugRecipientDao sugRecipientDao){
		super(sugRecipientDao);
		this.sugRecipientDao = sugRecipientDao;
	}
	public SugRecipientServiceImpl(){
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
	public Integer updateDeleteFlagByIds(SugRecipient sugRecipient) {
		return sugRecipientDao.updateDeleteFlagByIds(sugRecipient);
	}

}