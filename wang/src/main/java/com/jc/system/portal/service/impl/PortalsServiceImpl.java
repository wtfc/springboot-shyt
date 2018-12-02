package com.jc.system.portal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jc.system.portal.dao.IPortalDao;
import com.jc.system.portal.domain.Portal;
import com.jc.system.portal.service.IPortalsService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;

/**
 * @title GOA2.0
 * @description  业务服务类
 * @author 
 * @version  2014-06-13
 */
@Service
public class PortalsServiceImpl implements IPortalsService{

	@Resource
	private IPortalDao portalDao;
	
	public PortalsServiceImpl(){}
	
	public List<Portal> queryRolePortal(Portal portal) {
		List<Portal> portals = portalDao.queryAll(portal);
		return portals;
	}

}