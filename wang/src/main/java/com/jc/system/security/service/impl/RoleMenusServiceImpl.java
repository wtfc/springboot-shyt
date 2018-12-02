package com.jc.system.security.service.impl;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.security.dao.IRoleMenusDao;
import com.jc.system.security.domain.RoleMenus;
import com.jc.system.security.service.IRoleMenusService;

@Service
public class RoleMenusServiceImpl extends BaseServiceImpl<RoleMenus> implements
		IRoleMenusService {
	@Autowired
	public RoleMenusServiceImpl(IRoleMenusDao roleMenusDao) {
		super(roleMenusDao);
		// TODO Auto-generated constructor stub
		this.roleMenusDao= roleMenusDao;
	}

	public RoleMenusServiceImpl(){
		
	}

	private IRoleMenusDao roleMenusDao;

	public List<RoleMenus> query(RoleMenus roleMenus) {
		return roleMenusDao.queryAll(roleMenus);
	}

}
