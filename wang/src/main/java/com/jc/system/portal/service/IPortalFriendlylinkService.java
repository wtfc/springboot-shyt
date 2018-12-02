package com.jc.system.portal.service;

import java.util.Map;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.system.portal.domain.PortalFriendlylink;

/**
 * @title 172.16.3.68
 * @description  业务接口类
 * @author 
 * @version  2014-11-18
 */

public interface IPortalFriendlylinkService extends IBaseService<PortalFriendlylink>{
	/**
	 * 批量插入友情链接数据组
	 * @param PortalFunction portalFunction
	 * @return Map
	 */
	public Map<String,Object> instertFriendlyLinks(String linkName,String linkUrl,String sequence,String portletid,Long portalid);

	/**
	 * 根据门户ids删除友情链接列表信息
	 * @param Long portalids
	 * @return integer
	 */
	public Integer delFriendlyLinksForPortalIds(PortalFriendlylink portalFriendlylink);
	
	/**
	 * 查询所有友情链接信息
	 * @param PortalFriendlylink portalFriendlylink
	 * @return PageManager
	 */
	public PageManager queryFriendlyLinkAndPortal(PortalFriendlylink portalFriendlylink,PageManager page);
	
	/**
	 * 根据业务组件ids删除友情链接列表信息
	 * @param Long portletid
	 * @return integer
	 */
	public Integer delFriendlyLinksForPortletIds(PortalFriendlylink portalFriendlylink);

	/**
	 * 根据功能组件名称验证重复
	 * @param PortalFriendlylink portalFriendlylink
	 * @return Map
	 */
	public Map<String, Object> valNameEcho(PortalFriendlylink portalFriendlylink);
}