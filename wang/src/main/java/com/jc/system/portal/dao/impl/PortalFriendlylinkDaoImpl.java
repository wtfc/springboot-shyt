package com.jc.system.portal.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.foundation.domain.PageManager;
import com.jc.system.portal.dao.IPortalFriendlylinkDao;
import com.jc.system.portal.domain.PortalFriendlylink;

/**
 * @title 172.16.3.68
 * @description  dao实现类
 * @author 
 * @version  2014-11-18
 */
@Repository
public class PortalFriendlylinkDaoImpl extends BaseDaoImpl<PortalFriendlylink> implements IPortalFriendlylinkDao{

	public PortalFriendlylinkDaoImpl(){}

	public Integer delFriendlyLinksForPortletId(PortalFriendlylink portalFriendlylink) {
		return template.delete(getNameSpace(portalFriendlylink)+".deleteByPortletId",portalFriendlylink);
	}

	public Integer delFriendlyLinksForPortalIds(PortalFriendlylink portalFriendlylink) {
		return template.delete(getNameSpace(portalFriendlylink)+".deleteByPortalIds",portalFriendlylink);
	}

	public Integer delFriendlyLinksForPortletIds(PortalFriendlylink portalFriendlylink) {
		return template.delete(getNameSpace(portalFriendlylink)+".deleteByPortletIds",portalFriendlylink);
	}

	public PageManager queryFriendlyLinkAndPortal(PortalFriendlylink portalFriendlylink,PageManager page) {
		return queryByPage(portalFriendlylink,page,SQL_QUERY_COUNT,"queryAll");
	}

	public List<PortalFriendlylink> queryFriendlyLink(PortalFriendlylink portalFriendlylink) {
		return template.selectList(getNameSpace(portalFriendlylink)+".queryFriendlyLink",portalFriendlylink);
	}
	
	public Integer valNameEcho(PortalFriendlylink portalFriendlylink) {
		return template.selectOne(getNameSpace(portalFriendlylink)+".queryForLinkName", portalFriendlylink);
	}
}