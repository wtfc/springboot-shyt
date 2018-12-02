package com.jc.system.security.dao;

import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.system.CustomException;
import com.jc.system.security.domain.Role;

public interface IRoleDao extends IBaseDao<Role> {

	/**
	  * @description 用户管理角色列表
	  * @param response
	  * @throws Exception
	  * @author 高研
	  * @version 1.0 2014年5月23日 下午1:50:32
	 * @throws CustomException 
	*/
	public List<Role> getRolesForUser(Role role) throws CustomException;
}