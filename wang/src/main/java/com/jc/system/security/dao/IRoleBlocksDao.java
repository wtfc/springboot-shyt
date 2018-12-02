package com.jc.system.security.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.system.security.domain.RoleBlocks;

public interface IRoleBlocksDao extends IBaseDao<RoleBlocks> {

	public Integer save(RoleBlocks roleBlocks);

	public Integer deleteByRoleId(RoleBlocks roleBlocks);
}
