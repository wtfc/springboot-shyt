package com.jc.system.security.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.system.security.domain.SysUser;

/**
 * @title GOA2.0
 * @description 用户表（公共） dao接口类
 * @author 
 * @version  2014-04-10
 *
 */
public interface ISysUserDao extends IBaseDao<SysUser>{

	/**
	  * 修改删除状态
	  * @param SysUser
	  * @return Integer
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	public Integer updateDeleteFlagByIds(SysUser sysUser);
}
