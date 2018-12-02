package com.jc.system.security.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.security.domain.SysUser;

/**
 * @title GOA2.0
 * @description 用户表（公共） 业务接口类
 * @author 
 * @version  2014-04-10
 *
 */
public interface ISysUserService extends IBaseService<SysUser>{

	/**
	* @description 修改删除标记
	* @param SysUser sysUser 实体类
	* @return Integer 操作结果
	* @throws Exception
	* @author
	* @version  2014-04-10 
	*/
	public Integer updateDeleteFlagByIds(SysUser sysUser) ;

	
}