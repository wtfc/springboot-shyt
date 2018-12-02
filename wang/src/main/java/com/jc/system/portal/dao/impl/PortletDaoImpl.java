package com.jc.system.portal.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.portal.dao.IPortletDao;
import com.jc.system.portal.domain.Portlet;

/**
 * @title GOA2.0
 * @description  dao实现类
 * @author 
 * @version  2014-06-16
 */
@Repository
public class PortletDaoImpl extends BaseDaoImpl<Portlet> implements IPortletDao{

	public PortletDaoImpl(){}

	public List<Portlet> queryAllForPortalIds(Portlet portlet) {
		return template.selectList(getNameSpace(portlet)+".queryForPortalIds", portlet);
	}

	public String queryByuseFuncionids(Portlet portlet) {
		return template.selectOne(getNameSpace(portlet)+".queryByuseFuncionids");
	}

	public Integer deleteByPortalId(Portlet portlet) {
		return template.delete(getNameSpace(portlet)+".deleteByPortalId",portlet);
	}
	
	public Integer valNameEcho(Portlet portlet) {
		return template.selectOne(getNameSpace(portlet)+".queryForFunName", portlet);
	}

}