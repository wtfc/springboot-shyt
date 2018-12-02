package com.jc.system.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.portal.dao.IPortaletLayoutDao;
import com.jc.system.portal.domain.PortaletLayout;
import com.jc.system.portal.service.IPortaletLayoutService;

/**
 * @title GOA2.0
 * @description  业务服务类
 * @author 
 * @version  2014-07-01
 */
@Service
public class PortaletLayoutServiceImpl extends BaseServiceImpl<PortaletLayout> implements IPortaletLayoutService{

	private IPortaletLayoutDao portaletLayoutDao;
	
	public PortaletLayoutServiceImpl(){}
	
	@Autowired
	public PortaletLayoutServiceImpl(IPortaletLayoutDao portaletLayoutDao){
		super(portaletLayoutDao);
		this.portaletLayoutDao = portaletLayoutDao;
	}

}