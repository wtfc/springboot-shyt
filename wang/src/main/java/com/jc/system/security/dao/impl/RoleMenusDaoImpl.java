package com.jc.system.security.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.security.dao.IRoleMenusDao;
import com.jc.system.security.domain.RoleMenus;

@Repository
public class RoleMenusDaoImpl extends BaseDaoImpl<RoleMenus> implements
		IRoleMenusDao {

	public Integer save(RoleMenus roleMenus) {
		return template.insert(getNameSpace(roleMenus)+".insert",
				roleMenus);
	}

	public Integer deleteByRoleId(RoleMenus roleMenus) {
		return template.update(getNameSpace(roleMenus)+".deleteByIds",
				roleMenus);
	}
}
