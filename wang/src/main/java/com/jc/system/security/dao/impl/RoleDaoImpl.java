package com.jc.system.security.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.CustomException;
import com.jc.system.security.dao.IRoleDao;
import com.jc.system.security.domain.Role;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements IRoleDao {

	/**
	  * @description 用户管理角色列表
	  * @param response
	  * @throws Exception
	  * @version 1.0 2014年5月23日 下午1:50:32
	 * @throws CustomException 
	*/
	@Override
	public List<Role> getRolesForUser(Role role) throws CustomException {
		return template.selectList(getNameSpace(role)+".getRolesForUser", role);
	}

}