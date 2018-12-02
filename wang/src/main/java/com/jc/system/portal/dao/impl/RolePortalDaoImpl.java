package com.jc.system.portal.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.DBException;
import com.jc.system.portal.dao.IRolePortalDao;
import com.jc.system.portal.domain.RolePortal;

/**
 * @title GOA2.0
 * @description  dao实现类
 * @author 
 * @version  2014-06-16
 */
@Repository
public class RolePortalDaoImpl extends BaseDaoImpl<RolePortal> implements IRolePortalDao{

	public RolePortalDaoImpl(){}
	@Override
	public Integer deleteRolePortalItem(RolePortal rolePortal) throws DBException{
		Integer result = null;
		try {
				 result = template.delete(getNameSpace(rolePortal) +".deleteRolePortalItem", rolePortal);
			
		} catch (Exception e) {
			DBException exception = new DBException(e);
			exception.setLogMsg("数据库删除数据数量发生错误");
			throw exception;
		}
		return result;
	}
	@Override
	public List<RolePortal> queryPortaletPower(RolePortal rolePortal)
			throws DBException {
		// TODO 自动生成的方法存根
		return template.selectList(getNameSpace(rolePortal)+".queryPortaletPower", rolePortal);
	}
	
	@Override
	public Integer deleteForPortalsOrPortalets(RolePortal rolePortal)
			throws DBException {
		Integer result = null;
		try {
				 result = template.delete(getNameSpace(rolePortal) +".deleteForPortalsOrPortalets", rolePortal);
			
		} catch (Exception e) {
			DBException exception = new DBException(e);
			exception.setLogMsg("数据库删除数据数量发生错误");
			throw exception;
		}
		return result;
	}

}