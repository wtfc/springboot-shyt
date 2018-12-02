package com.jc.system.portal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jc.system.portal.dao.IPortletDao;
import com.jc.system.portal.domain.Portlet;
import com.jc.system.portal.service.IPortletsService;

/**
 * @title GOA2.0
 * @description  业务服务类
 * @author 
 * @version  2014-06-16
 */
@Service
public class PortletsServiceImpl implements IPortletsService{

	@Resource
	private IPortletDao portletDao;
	
	public PortletsServiceImpl(){}
	
	public List<Portlet> getPortletList(Portlet portlet) {
		return portletDao.queryAll(portlet);
	}

	public List<Portlet> getPortletListForPortalIds(Portlet portlet) {
		return portletDao.queryAllForPortalIds(portlet);
	}

	public Integer deleteByPortalId(Portlet portlet) {
		return portletDao.deleteByPortalId(portlet);
	}

	

}