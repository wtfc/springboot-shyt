package com.jc.system.security.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.system.security.domain.RoleExts;
import com.jc.system.security.domain.RoleMenus;

public interface IRoleExtsDao extends IBaseDao<RoleExts> {

	public Integer save(RoleExts roleExts);

	public Integer deleteByRoleId(RoleExts roleExts);
}
