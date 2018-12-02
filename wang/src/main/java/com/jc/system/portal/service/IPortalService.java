package com.jc.system.portal.service;

import java.util.List;
import java.util.Map;

import com.jc.foundation.service.IBaseService;
import com.jc.system.portal.domain.Portal;

/**
 * @title GOA2.0
 * @description  业务接口类
 * @author 
 * @version  2014-06-13
 */

public interface IPortalService extends IBaseService<Portal>{
	/**
	 * 根据登录用户信息查询关联门户
	 * @param portal
	 * @return
	 */
	public List<Portal> queryRolePortal(Portal portal);
	
	/**
	 * 根据门户类型拼装查询关联门户对象
	 * @param portal
	 * @return
	 */
	public Portal spellToPortal(Portal portal, String portalType);
	
	/**
	 * 根据门户类型返回关联门户组件列表
	 * @param portal
	 * @return
	 */
	public Map<String, Object> portletListForPortal(String portalType,Long portalId);
	
	/**
	 * 根据门户id关联删除门户业务组件
	 * @param String ids
	 * @return
	 */
	public Integer deletePortalAndPortletByIds(String ids);
	
	/**
	 * 根据功能组件名称验证重复
	 * @param PortalFunction portalFunction
	 * @return Map
	 */
	public Map<String, Object> valNameEcho(Portal portal);
	
}