package com.jc.system.security.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.system.security.domain.SysUserRole;

/**
 * @title GOA2.0
 * @description  dao接口类
 * @author 
 * @version  2014-04-15
 *
 */
public interface ISysUserRoleDao extends IBaseDao<SysUserRole>{

	/**
	  * 删除方法
	  * @param adminSide
	  * @return int
	  * @author 高研
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	public int deleteSysUserRole(SysUserRole sysUserRole);
}
