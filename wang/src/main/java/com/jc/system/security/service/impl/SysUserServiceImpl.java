package com.jc.system.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.system.security.dao.ISysUserDao;
import com.jc.system.security.domain.SysUser;
import com.jc.system.security.service.ISysUserService;
import com.jc.foundation.service.impl.BaseServiceImpl;

/**
 * @title GOA2.0
 * @description 用户表（公共） 业务实现类
 * @author 
 * @version  2014-04-10
 *
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements ISysUserService{
	
	@Autowired
	public SysUserServiceImpl(ISysUserDao dao) {
		super(dao);
		this.sysUserDao = dao;
	}
	public SysUserServiceImpl(){
		
	}
	
	private ISysUserDao sysUserDao;

	@Override
	public Integer updateDeleteFlagByIds(SysUser sysUser) {
		return sysUserDao.updateDeleteFlagByIds(sysUser);
	}

}