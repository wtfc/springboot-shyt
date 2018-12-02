package com.jc.system.security.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.system.security.domain.Role;
import com.jc.system.security.domain.User;
import com.jc.system.security.facade.ISecurityFacadeService;
import com.jc.system.security.service.IRoleService;
import com.jc.system.security.service.IUserService;

/**
 * @title GOA V2.0
 * @description 用户门面服务
 * @version  2014年5月14日下午2:51:03
 */
@Service
public class SecurityFacadeServiceImpl implements ISecurityFacadeService {
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	
	
	public SecurityFacadeServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<User> getDeptUsers(User user) {
		// TODO Auto-generated method stub
		return userService.queryUserByDeptId(user);
	}

	@Override
	public List<Role> getUserRoles(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
