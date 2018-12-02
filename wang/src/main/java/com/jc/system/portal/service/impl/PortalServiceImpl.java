package com.jc.system.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.CustomException;
import com.jc.system.portal.dao.IPortalDao;
import com.jc.system.portal.domain.Portal;
import com.jc.system.portal.domain.PortalFriendlylink;
import com.jc.system.portal.domain.Portlet;
import com.jc.system.portal.domain.PortletRelation;
import com.jc.system.portal.domain.RolePortal;
import com.jc.system.portal.service.IPortalFriendlylinkService;
import com.jc.system.portal.service.IPortalService;
import com.jc.system.portal.service.IPortletRelationService;
import com.jc.system.portal.service.IPortletsService;
import com.jc.system.portal.service.IRolePortalService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.SysUserRole;
import com.jc.system.security.domain.User;

/**
 * @title GOA2.0
 * @description  业务服务类
 * @author 
 * @version  2014-06-13
 */
@Service
public class PortalServiceImpl extends BaseServiceImpl<Portal> implements IPortalService{

	private IPortalDao portalDao;
	
	@Autowired
	private IPortletsService portletsService;
	
	@Autowired
	private IRolePortalService rolePortalService;
	
	@Autowired
	private IPortalFriendlylinkService portalFriendlylinkService;
	
	@Autowired
	private IPortletRelationService portletRelationService;
	
	public PortalServiceImpl(){}
	
	@Autowired
	public PortalServiceImpl(IPortalDao portalDao){
		super(portalDao);
		this.portalDao = portalDao;
	}

	public List<Portal> queryRolePortal(Portal portal) {
		return portalDao.queryRolePortal(portal);
	}

	public Portal spellToPortal(Portal portal, String portalType) {
		//获取用户登录信息
		User user = SystemSecurityUtils.getUser();
		if(portal.getPortalType() != null && !portal.getPortalType().equals("")) {
			/*门户类型 ptype_org 机构 ptype_dept 部门  ptype_user 个人*/		
			if(portal.getPortalType().equals("ptype_org")){
				portal.setOrganId(user.getOrgId());
			} else if(portal.getPortalType().equals("ptype_dept")){
				portal.setDeptId(user.getDeptId());
			} else if(portal.getPortalType().equals("ptype_user")){
				List<SysUserRole> userRoles = user.getSysUserRole();
				String roleStr = "";
				if(userRoles != null){
					for(int i=0;i<userRoles.size();i++){
						if(roleStr.equals(""))
							roleStr = userRoles.get(i).getRoleId().toString();
						else
							roleStr = roleStr + "," + userRoles.get(i).getRoleId().toString();
						
					}
				}
				portal.setOrganId(user.getOrgId());
				portal.setDeptId(user.getDeptId());
				portal.setRoleIds(roleStr);
				portal.setUserId(user.getId());
			}
		} else {
			if(portalType == null)//
				portal.setCreateUser(user.getId());
		}
		return portal;
	}
	
	public RolePortal spellToRolePortal(RolePortal rolePortal){
		//获取用户登录信息
		User user = SystemSecurityUtils.getUser();
		List<SysUserRole> userRoles = user.getSysUserRole();
		String roleStr = "";
		if(userRoles != null){
			for(int i=0;i<userRoles.size();i++){
				if(roleStr.equals(""))
					roleStr = userRoles.get(i).getRoleId().toString();
				else
					roleStr = roleStr + "," + userRoles.get(i).getRoleId().toString();
				
			}
		}
		rolePortal.setOrganId(user.getOrgId());
		rolePortal.setDeptId(user.getDeptId());
		rolePortal.setRoleIds(roleStr);
		rolePortal.setUserId(user.getId());
		return rolePortal;
	}

	public Map<String, Object> portletListForPortal(String portalType,Long portalId) {
		Map<String,Object> portalMap = new HashMap<String,Object>();
		Portal portal = new Portal();
		if(portalType != null && portalType.equals("ptype_user_only")){
			portal.setPortalType("ptype_user");
			portal = spellToPortal(portal,"ptype_user");
			portal.setPortalStatus("portalstatus_1");
		}
		else {
			portal = spellToPortal(portal,portalType);
		}
			
		List<Portal> portals = portalDao.queryAll(portal);
		List<Portlet> rePortletList = new ArrayList<Portlet>();
		if(portals != null && portals.size() > 0){
			String portalIds = "";
			for(int i=0;i<portals.size();i++){
				if(portalIds.equals(""))
					portalIds = portals.get(i).getId().toString();
				else
					portalIds = portalIds +","+ portals.get(i).getId();
			}
			Portlet portlet = new Portlet();
			if(portalId != null)
				portlet.setPortalId(portalId);
			portlet.setPortalIds(portalIds);
			List<Portlet> portlets = portletsService.getPortletListForPortalIds(portlet);
			//循环匹配个人门户下图片组件 文本域组件 信息
			if(portlets != null && portlets.size()>0){
				try {
					/*User user = SystemSecurityUtils.getUser();
					PortletRelation portletRelation = new PortletRelation();
					List<PortletRelation> templist = portletRelationService.queryAll(portletRelation);
					if(templist != null && templist.size() > 0){
						for(int i=0;i<portlets.size();i++){
							Portlet tempPortlet = portlets.get(i);
							if(tempPortlet.getViewType().equals("textareaEdit")
									|| tempPortlet.getViewType().equals("printEdit")){
								for(int j=0;j<templist.size();j++){
									PortletRelation tempPRelation = templist.get(j);
									if(tempPRelation.getPortalId().equals(tempPortlet.getPortalId()) 
											&& tempPRelation.getPortletId().equals(tempPortlet.getId())){
										if(tempPRelation.getUserId() != null && tempPRelation.getUserId() == user.getId()){
											tempPortlet.setLetFile(tempPRelation.getLetFile());
											tempPortlet.setLetTextarea(tempPRelation.getLetTextarea());
										} else if(tempPRelation.getUserId() == null){
											tempPortlet.setLetFile(tempPRelation.getLetFile());
											tempPortlet.setLetTextarea(tempPRelation.getLetTextarea());
										} else {
											tempPortlet.setLetFile("");
											tempPortlet.setLetTextarea("");
										}
									}
								}
							}
						}
					}*/
					for(int i=0;i<portlets.size();i++){
						Portlet tempPortlet = portlets.get(i);
						if(tempPortlet.getViewType().equals("textareaEdit")){
							User user = SystemSecurityUtils.getUser();
							PortletRelation portletRelation = new PortletRelation();
							portletRelation.setPortalId(tempPortlet.getPortalId());
							portletRelation.setPortletId(tempPortlet.getId());
							portletRelation.setUserId(user.getId());
							portletRelation.addOrderByFieldDesc("id");
							List<PortletRelation> templist = portletRelationService.queryAll(portletRelation);
							if(templist.size()>0){
								tempPortlet.setLetTextarea(templist.get(0).getLetTextarea());
							} else {
								tempPortlet.setLetTextarea("");
							}
						} else if(tempPortlet.getViewType().equals("printEdit")){
							User user = SystemSecurityUtils.getUser();
							PortletRelation portletRelation = new PortletRelation();
							portletRelation.setPortalId(tempPortlet.getPortalId());
							portletRelation.setPortletId(tempPortlet.getId());
							portletRelation.setUserId(user.getId());
							portletRelation.addOrderByFieldDesc("id");
							List<PortletRelation> templist = portletRelationService.queryAll(portletRelation);
							if(templist.size()>0){
								tempPortlet.setLetFile(templist.get(0).getLetFile());
							} else {
								tempPortlet.setLetFile("");
							}
						}
					}
				} catch (CustomException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			//
			rePortletList.addAll(portlets);
		}
		portalMap.put("portletLists", rePortletList);
		
		
		RolePortal rolePortal = new RolePortal();
		rolePortal = spellToRolePortal(rolePortal);
		List<RolePortal> rolePortalList = null;
		try {
			rolePortalList = rolePortalService.queryPortaletPower(rolePortal);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		String rolePortalStr = "";
		if(rolePortalList != null && rolePortalList.size() > 0){
			for(int i=0;i<rolePortalList.size();i++){
				RolePortal temprolePortal = rolePortalList.get(i);
				if(rolePortalStr.equals(""))
					rolePortalStr = temprolePortal.getPortaletId().toString();
				else
					rolePortalStr = rolePortalStr + "," + temprolePortal.getPortaletId().toString();
			}
		}
		portalMap.put("rolePortalStr", ","+rolePortalStr+",");
		
		return portalMap;
	}

	public Integer deletePortalAndPortletByIds(String ids) {
		Portal portal = new Portal();
		Portlet portlet = new Portlet();
		RolePortal rolePortal = new RolePortal();
		PortalFriendlylink portalFriendlylink = new PortalFriendlylink();
		portal.setPrimaryKeys(ids.split(","));
		portlet.setPrimaryKeys(ids.split(","));
		portalFriendlylink.setPrimaryKeys(ids.split(","));
		rolePortal.setPortalIds(ids);
		int returnvalue = 0;
		try {
			returnvalue = rolePortalService.deleteForPortalsOrPortalets(rolePortal);
			returnvalue = portalFriendlylinkService.delFriendlyLinksForPortalIds(portalFriendlylink);
			returnvalue = portletsService.deleteByPortalId(portlet);
			returnvalue = portalDao.delete(portal);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		return returnvalue;
	}

	public Map<String, Object> valNameEcho(Portal portal) {
		//获取用户登录信息
		User user = SystemSecurityUtils.getUser();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long portalid = portal.getId();
		portal.setCreateUserOrg(user.getOrgId());
		int result = portalDao.valNameEcho(portal);
		if(portalid == null){
			if(result == 0){
				resultMap.put("success", "true");
			} else {
				resultMap.put("success", "false");
			}
		} else {
			Portal tempVo = new Portal();
			tempVo.setId(portalid);
			tempVo = portalDao.get(tempVo);
			if(tempVo.getPortalName().equals(portal.getPortalName())){
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