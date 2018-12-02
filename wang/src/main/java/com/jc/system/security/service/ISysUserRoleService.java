package com.jc.system.security.service;

import com.jc.system.CustomException;
import com.jc.system.security.domain.SysUserRole;
import com.jc.foundation.service.IBaseService;

/**
 * @title GOA2.0
 * @description  业务接口类
 * @author 
 * @version  2014-04-15
 *
 */
public interface ISysUserRoleService extends IBaseService<SysUserRole>{

	/**
	* @description 根据主键删除多条记录方法
	* @param SysUserRole sysUserRole 实体类
	* @return Integer 处理结果
	* @author
	* @version  2014-04-15 
	*/
	public int deleteSysUserRole(SysUserRole sysUserRole) throws CustomException;
	
}