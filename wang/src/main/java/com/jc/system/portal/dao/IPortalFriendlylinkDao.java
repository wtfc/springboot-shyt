package com.jc.system.portal.dao;

import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.foundation.domain.PageManager;
import com.jc.system.portal.domain.PortalFriendlylink;


/**
 * @title 172.16.3.68
 * @description  dao接口类
 * @author 
 * @version  2014-11-18
 */
 
public interface IPortalFriendlylinkDao extends IBaseDao<PortalFriendlylink>{

	/**
	 * 根据业务组件id删除友情链接列表信息
	 * @param Long portletid
	 * @return integer
	 */
	public Integer delFriendlyLinksForPortletId(PortalFriendlylink portalFriendlylink);
	
	/**
	 * 根据业务组件ids删除友情链接列表信息
	 * @param Long portletid
	 * @return integer
	 */
	public Integer delFriendlyLinksForPortalIds(PortalFriendlylink portalFriendlylink);
	
	/**
	 * 根据业务组件ids删除友情链接列表信息
	 * @param Long portletid
	 * @return integer
	 */
	public Integer delFriendlyLinksForPortletIds(PortalFriendlylink portalFriendlylink);
	
	/**
	 * 查询所有友情链接信息
	 * @param PortalFriendlylink portalFriendlylink
	 * @return PageManager
	 */
	public PageManager queryFriendlyLinkAndPortal(PortalFriendlylink portalFriendlylink,PageManager page);
	
	/**
	 * 查询所有友情链接信息
	 * @param PortalFriendlylink portalFriendlylink
	 * @return list
	 */
	public List<PortalFriendlylink> queryFriendlyLink(PortalFriendlylink portalFriendlylink);

	/**
	 * 根据功能组件名称验证重复
	 * @param String funName
	 * @return Map
	 */
	public Integer valNameEcho(PortalFriendlylink portalFriendlylink);
}
