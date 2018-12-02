package com.jc.system.security.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.system.security.domain.RoleMenus;

public interface IRoleMenusDao extends IBaseDao<RoleMenus> {

	public Integer save(RoleMenus roleMenus);

	public Integer deleteByRoleId(RoleMenus roleMenus);
}
