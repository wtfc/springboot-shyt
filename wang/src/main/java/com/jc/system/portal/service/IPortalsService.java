package com.jc.system.portal.service;

import java.util.List;

import com.jc.system.portal.domain.Portal;

/**
 * @title GOA2.0
 * @description  业务接口类
 * @author 
 * @version  2014-11-25
 */

public interface IPortalsService{
	/**
	 * 根据登录用户信息查询关联门户
	 * @param portal
	 * @return
	 */
	public List<Portal> queryRolePortal(Portal portal);	
}