package com.jc.system.security.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.system.security.domain.SysUserRole;
import com.jc.system.security.dao.ISysUserRoleDao;
import com.jc.foundation.dao.impl.BaseDaoImpl;

 /**
 * @title GOA2.0
 * @description  dao实现类
 * @author 
 * @version  2014-04-15
 *
 */
@Repository
public class SysUserRoleDaoImpl extends BaseDaoImpl<SysUserRole> implements ISysUserRoleDao{

	/**
	  * 删除方法
	  * @param SysUserRole
	  * @return int
	  * @author 高研
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	@Override
	public int deleteSysUserRole(SysUserRole sysUserRole) {
		return template.update(getNameSpace(sysUserRole)+".deleteSysUserRole", sysUserRole);
	}


}