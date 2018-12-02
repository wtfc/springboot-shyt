package com.jc.system.group.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.group.dao.IGroupUserDao;
import com.jc.system.group.domain.GroupUser;
import com.jc.system.group.service.IGroupUserService;

/**
 * @title GOA2.0使用的个人组别和公共组别应用
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-07-24
 */
@Service
public class GroupUserServiceImpl extends BaseServiceImpl<GroupUser> implements IGroupUserService{

	private IGroupUserDao groupUserDao;
	
	public GroupUserServiceImpl(){}
	
	@Autowired
	public GroupUserServiceImpl(IGroupUserDao groupUserDao){
		super(groupUserDao);
		this.groupUserDao = groupUserDao;
	}

}