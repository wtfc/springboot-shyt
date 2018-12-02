package com.jc.system.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.security.dao.IUserRoleDao;
import com.jc.system.security.domain.UserRole;
import com.jc.system.security.service.IUserRoleService;

/**
 * 
 * @title GOA V2.0
 * @description 用户角色service
 * @version  2014年5月23日
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements
		IUserRoleService {
	@Autowired
	public UserRoleServiceImpl(IUserRoleDao userRoleDao) {
		super(userRoleDao);
		this.userRoleDao= userRoleDao;
	}
	public UserRoleServiceImpl(){
		
	}
	
	private IUserRoleDao userRoleDao;

}
