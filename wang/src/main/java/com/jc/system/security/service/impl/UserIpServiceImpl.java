package com.jc.system.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.security.dao.IUserIpDao;
import com.jc.system.security.domain.UserIp;
import com.jc.system.security.service.IUserIpService;

/**
 * @title GOA2.0
 * @description  业务服务类
 * @version  2014-04-30
 */
@Service
public class UserIpServiceImpl extends BaseServiceImpl<UserIp> implements IUserIpService{

	private IUserIpDao userIpDao;
	
	public UserIpServiceImpl(){}
	
	@Autowired
	public UserIpServiceImpl(IUserIpDao userIpDao){
		super(userIpDao);
		this.userIpDao = userIpDao;
	}

}