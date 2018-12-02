package com.jc.system.portal.dao;

import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.system.DBException;
import com.jc.system.portal.domain.RolePortal;


/**
 * @title GOA2.0
 * @description  dao接口类
 * @author 
 * @version  2014-06-16
 */
 
public interface IRolePortalDao extends IBaseDao<RolePortal>{

	public Integer deleteRolePortalItem(RolePortal rolePortal)  throws DBException;
	
	public Integer deleteForPortalsOrPortalets(RolePortal rolePortal)  throws DBException;
	
	public List<RolePortal> queryPortaletPower(RolePortal rolePortal) throws DBException;
}
