package com.jc.system.portal.service;

import java.util.Map;

/**
 * @title 172.16.3.68
 * @description  业务接口类
 * @author 
 * @version  2014-11-18
 */

public interface IPortalFriendlylinksService{
	/**
	 * 查询友情链接数据组
	 * @param PortalFunction portalFunction
	 * @return Map
	 */
	public Map<String,Object> queryFriendlyLinks();
}