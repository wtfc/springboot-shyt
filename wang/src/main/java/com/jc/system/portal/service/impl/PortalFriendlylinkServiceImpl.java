package com.jc.system.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.DBException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.portal.dao.IPortalFriendlylinkDao;
import com.jc.system.portal.domain.PortalFriendlylink;
import com.jc.system.portal.service.IPortalFriendlylinkService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;

/**
 * @title 172.16.3.68
 * @description  业务服务类
 * @author 
 * @version  2014-11-18
 */
@Service
public class PortalFriendlylinkServiceImpl extends BaseServiceImpl<PortalFriendlylink> implements IPortalFriendlylinkService{

	private IPortalFriendlylinkDao portalFriendlylinkDao;
	
	public PortalFriendlylinkServiceImpl(){}
	
	@Autowired
	public PortalFriendlylinkServiceImpl(IPortalFriendlylinkDao portalFriendlylinkDao){
		super(portalFriendlylinkDao);
		this.portalFriendlylinkDao = portalFriendlylinkDao;
	}

	public Map<String, Object> instertFriendlyLinks(String linkName,
			String linkUrl, String sequence, String portletid,Long portalid) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int flag = 0;
		User userInfo = SystemSecurityUtils.getUser();
		if(linkName != null && !linkName.equals("")){
			String[] linkNames = linkName.substring(1).split("-");
			String[] linkUrls = linkUrl.substring(1).split("-");
			String[] sequences = sequence.substring(1).split("-");
			String[] portletId = portletid.split("_");
			List<PortalFriendlylink> friendlyLinks = new ArrayList<PortalFriendlylink>();
			for(int i=0;i<linkNames.length;i++){
				PortalFriendlylink friendlylinksVo = new PortalFriendlylink();
				friendlylinksVo.setLinkName(linkNames[i]);
				friendlylinksVo.setLinkUrl(linkUrls[i]);
				friendlylinksVo.setSequence(Integer.parseInt(sequences[i]));
				friendlylinksVo.setPortletId(Long.valueOf(portletId[0]));
				friendlylinksVo.setPortalId(portalid);
				friendlylinksVo.setCreateUserOrg(userInfo.getOrgId());
				friendlylinksVo.setCreateUser(userInfo.getId());
				friendlyLinks.add(friendlylinksVo);
			}
			try {
				PortalFriendlylink delLinksVo = new PortalFriendlylink();
				delLinksVo.setPortletId(Long.valueOf(portletId[0]));
				delLinksVo.setCreateUser(userInfo.getId());
				flag = portalFriendlylinkDao.delFriendlyLinksForPortletId(delLinksVo);
				flag = portalFriendlylinkDao.save(friendlyLinks);
			} catch (DBException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		if (flag > 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		return resultMap;
	}

	public Integer delFriendlyLinksForPortalIds(PortalFriendlylink portalFriendlylink) {
		return portalFriendlylinkDao.delFriendlyLinksForPortalIds(portalFriendlylink);
	}

	public Integer delFriendlyLinksForPortletIds(PortalFriendlylink portalFriendlylink) {
		return portalFriendlylinkDao.delFriendlyLinksForPortletIds(portalFriendlylink);
	}

	public PageManager queryFriendlyLinkAndPortal(PortalFriendlylink portalFriendlylink,PageManager page) {
		return portalFriendlylinkDao.queryFriendlyLinkAndPortal(portalFriendlylink,page);
	}
	
	public Map<String, Object> valNameEcho(PortalFriendlylink portalFriendlylink) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long portalFriendlylinkid = portalFriendlylink.getId();
		int result = portalFriendlylinkDao.valNameEcho(portalFriendlylink);
		if(portalFriendlylinkid == null || portalFriendlylinkid == 0){
			if(result == 0){
				resultMap.put("success", "true");
			} else {
				resultMap.put("success", "false");
			}
		} else {
			PortalFriendlylink tempVo = new PortalFriendlylink();
			tempVo.setId(portalFriendlylinkid);
			tempVo = portalFriendlylinkDao.get(tempVo);
			if(tempVo.getLinkName().equals(portalFriendlylink.getLinkName())){
				if(result <= 1){
					resultMap.put("success", "true");
				} else {
					resultMap.put("success", "false");
				}
			} else {
				if(result == 0){
					resultMap.put("success", "true");
				} else {
					resultMap.put("success", "false");
				}
			}
		}
		
		return resultMap;
	}

}