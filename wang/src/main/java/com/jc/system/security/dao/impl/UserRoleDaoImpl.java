package com.jc.system.security.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.security.dao.IUserRoleDao;
import com.jc.system.security.domain.UserRole;

@Repository
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole> implements
		IUserRoleDao {

}
