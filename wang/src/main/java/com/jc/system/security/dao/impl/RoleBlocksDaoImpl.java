package com.jc.system.security.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.security.dao.IRoleBlocksDao;
import com.jc.system.security.domain.RoleBlocks;
import com.jc.system.security.domain.RoleMenus;

@Repository
public class RoleBlocksDaoImpl extends BaseDaoImpl<RoleBlocks> implements
		IRoleBlocksDao {

	public Integer save(RoleBlocks roleBlocks) {
		return template.insert(getNameSpace(roleBlocks)+".insert",
				roleBlocks);
	}

	public Integer deleteByRoleId(RoleBlocks roleBlocks) {
		return template.update(getNameSpace(roleBlocks)+".deleteByIds",
				roleBlocks);
	}
}
