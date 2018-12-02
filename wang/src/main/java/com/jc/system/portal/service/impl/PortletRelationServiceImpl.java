package com.jc.system.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.portal.dao.IPortletRelationDao;
import com.jc.system.portal.domain.PortletRelation;
import com.jc.system.portal.service.IPortletRelationService;

/**
 * @title GOA2.0
 * @description  业务服务类
 * @author 
 * @version  2014-06-16
 */
@Service
public class PortletRelationServiceImpl extends BaseServiceImpl<PortletRelation> implements IPortletRelationService{

	private IPortletRelationDao portletRelationDao;
	
	public PortletRelationServiceImpl(){}
	
	@Autowired
	public PortletRelationServiceImpl(IPortletRelationDao portletRelationDao){
		super(portletRelationDao);
		this.portletRelationDao = portletRelationDao;
	}

}