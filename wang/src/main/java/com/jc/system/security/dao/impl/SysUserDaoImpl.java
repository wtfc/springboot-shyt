package com.jc.system.security.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.security.dao.ISysUserDao;
import com.jc.system.security.domain.SysUser;

 /**
 * @title GOA2.0
 * @description 用户表（公共） dao实现类
 * @author 
 * @version  2014-04-10
 *
 */
@Repository
public class SysUserDaoImpl extends BaseDaoImpl<SysUser> implements ISysUserDao{
	/**
	  * 修改删除状态
	  * @param SysUser
	  * @return Integer
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	@Override
	public Integer updateDeleteFlagByIds(SysUser sysUser) {
		return template.update(getNameSpace(sysUser) + ".updateDeleteFlagByIds" , sysUser);
	}

}