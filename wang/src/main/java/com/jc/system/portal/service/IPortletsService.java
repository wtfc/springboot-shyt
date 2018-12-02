package com.jc.system.portal.service;

import java.util.List;

import com.jc.system.portal.domain.Portlet;

/**
 * @title GOA2.0
 * @description  业务接口类
 * @author 
 * @version  2014-07-3
 */

public interface IPortletsService{
	
	public List<Portlet> getPortletList(Portlet portlet);
	
	public List<Portlet> getPortletListForPortalIds(Portlet portlet);
	
	public Integer deleteByPortalId(Portlet portlet);
	
}