package com.jc.system.portal.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.portal.dao.IPortalFunctionDao;
import com.jc.system.portal.domain.PortalFunction;

/**
 * @title GOA2.0
 * @description  dao实现类
 * @author 
 * @version  2014-06-11
 */
@Repository
public class PortalFunctionDaoImpl extends BaseDaoImpl<PortalFunction> implements IPortalFunctionDao{

	public PortalFunctionDaoImpl(){}

	public Integer valNameEcho(PortalFunction portalFunction) {
		return template.selectOne(getNameSpace(portalFunction)+".queryForFunName", portalFunction);
	}

}