package com.jc.system.portal.dao;

import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.system.portal.domain.Portal;

/**
 * @title GOA2.0
 * @description  dao接口类
 * @author 
 * @version  2014-06-13
 */
 
public interface IPortalDao extends IBaseDao<Portal>{

	/**
	 * 根据登录用户信息查询关联门户
	 * @param portal
	 * @return
	 */
	public List<Portal> queryRolePortal(Portal portal);
	
	/**
	 * 根据功能组件名称验证重复
	 * @param String funName
	 * @return Map
	 */
	public Integer valNameEcho(Portal portal);
}
