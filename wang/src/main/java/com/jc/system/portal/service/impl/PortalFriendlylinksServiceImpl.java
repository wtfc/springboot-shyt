package com.jc.system.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.system.portal.dao.IPortalFriendlylinkDao;
import com.jc.system.portal.domain.Portal;
import com.jc.system.portal.domain.Portlet;
import com.jc.system.portal.service.IPortalFriendlylinksService;
import com.jc.system.portal.service.IPortalsService;
import com.jc.system.portal.service.IPortletsService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;

/**
 * @title 172.16.3.68
 * @description  业务服务类
 * @author 
 * @version  2014-11-18
 */
@Service
public class PortalFriendlylinksServiceImpl implements IPortalFriendlylinksService{

	@Resource
	private IPortalFriendlylinkDao portalFriendlylinkDao;
	
	@Autowired
	private IPortletsService portletsService;
	
	@Autowired
	private IPortalsService portalsService;
	
	public PortalFriendlylinksServiceImpl(){}

	public Map<String, Object> queryFriendlyLinks() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//获取用户登录信息
		User user = SystemSecurityUtils.getUser();
		
		Portal portal = new Portal();
		//portal.setCreateUser(user.getId());
		portal.setCreateUserOrg(user.getOrgId());
		List<Portal> portallist = portalsService.queryRolePortal(portal);
		resultMap.put("portallist", portallist);
		
		
		List<Portlet> rePortletList = new ArrayList<Portlet>();
		if(portallist != null && portallist.size() > 0){
			String portalIds = "";
			for(int i=0;i<portallist.size();i++){
				if(portalIds.equals(""))
					portalIds = portallist.get(i).getId().toString();
				else
					portalIds = portalIds +","+ portallist.get(i).getId();
			}
			Portlet portlet = new Portlet();
			portlet.setPortalIds(portalIds);
			portlet.setViewType("friendlyLink");
			List<Portlet> portlets = portletsService.getPortletListForPortalIds(portlet);
			rePortletList.addAll(portlets);
		}
		resultMap.put("portletlist", rePortletList);
		
		return resultMap;
	}

}