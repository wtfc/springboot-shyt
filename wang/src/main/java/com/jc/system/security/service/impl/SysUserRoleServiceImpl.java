package com.jc.system.security.service.impl;

import java.util.Map;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.system.CustomException;
import com.jc.system.security.dao.ISysRoleDao;
import com.jc.system.security.dao.ISysUserRoleDao;
import com.jc.system.security.service.ISysUserRoleService;
import com.jc.system.security.domain.SysUserRole;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;

/**
 * @title GOA2.0
 * @description  业务实现类
 * @author 
 * @version  2014-04-15
 *
 */
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole> implements ISysUserRoleService{

	@Resource
	private ISysUserRoleDao sysUserRoleDao;
	
	@Autowired
	public SysUserRoleServiceImpl(ISysUserRoleDao dao) {
		super(dao);
		this.sysUserRoleDao = dao;
	}

	public SysUserRoleServiceImpl(){
		
	}

	/**
	* 删除方法
	* @param SysUserRole 实体类
	* @return int 删除的记录数
	* @author
	* @version  2014-04-15 
	*/
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteSysUserRole(SysUserRole sysUserRole)
			throws CustomException {
		return sysUserRoleDao.deleteSysUserRole(sysUserRole);
	}

}