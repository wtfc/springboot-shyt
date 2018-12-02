package com.jc.system.portal.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.portal.dao.IPortalDao;
import com.jc.system.portal.domain.Portal;

/**
 * @title GOA2.0
 * @description  dao实现类
 * @author 
 * @version  2014-06-13
 */
@Repository
public class PortalDaoImpl extends BaseDaoImpl<Portal> implements IPortalDao{

	public PortalDaoImpl(){}

	public List<Portal> queryRolePortal(Portal portal) {
		
		return template
				.selectList(getNameSpace(portal)+".queryRolePortal", portal);
	}
	
	public Integer valNameEcho(Portal portal) {
		return template.selectOne(getNameSpace(portal)+".queryForFunName", portal);
	}

}