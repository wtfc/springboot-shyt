package com.jc.system.security.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.security.dao.IRoleExtsDao;
import com.jc.system.security.domain.RoleExts;

@Repository
public class RoleExtsDaoImpl extends BaseDaoImpl<RoleExts> implements
		IRoleExtsDao {

	public Integer save(RoleExts roleExts) {
		return template.insert(getNameSpace(roleExts)+".insert",
				roleExts);
	}

	public Integer deleteByRoleId(RoleExts roleExts) {
		return template.update(getNameSpace(roleExts)+".deleteByIds",
				roleExts);
	}
}
