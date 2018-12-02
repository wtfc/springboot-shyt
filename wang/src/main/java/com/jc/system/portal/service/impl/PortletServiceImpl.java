package com.jc.system.portal.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.portal.dao.IPortletDao;
import com.jc.system.portal.domain.Portal;
import com.jc.system.portal.domain.PortalFriendlylink;
import com.jc.system.portal.domain.PortalFunction;
import com.jc.system.portal.domain.PortaletLayout;
import com.jc.system.portal.domain.Portlet;
import com.jc.system.portal.domain.PortletRelation;
import com.jc.system.portal.service.IPortalFriendlylinkService;
import com.jc.system.portal.service.IPortalFunctionService;
import com.jc.system.portal.service.IPortalService;
import com.jc.system.portal.service.IPortaletLayoutService;
import com.jc.system.portal.service.IPortletRelationService;
import com.jc.system.portal.service.IPortletService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;

/**
 * @title GOA2.0
 * @description  业务服务类
 * @author 
 * @version  2014-06-16
 */
@Service
public class PortletServiceImpl extends BaseServiceImpl<Portlet> implements IPortletService{

	private IPortletDao portletDao;
	
	@Autowired
	private IPortalService portalService;
	
	@Autowired
	private IPortalFunctionService portalFunctionService;
	
	@Autowired
	private IPortaletLayoutService portaletLayoutService;
	
	@Autowired
	private IPortalFriendlylinkService portalFriendlylinkService;
	
	@Autowired
	private IPortletRelationService portletRelationService;
	
	public PortletServiceImpl(){}
	
	@Autowired
	public PortletServiceImpl(IPortletDao portletDao){
		super(portletDao);
		this.portletDao = portletDao;
	}

	/**
	  * @description  获取门户及功能组件列表
	  * @param Long portalId
	  * @return
	  * @author chz
	  * @version 1.0 2014年6月20日 
	*/
	public Map<String, Object> getPortalAndFouction() {
		Map<String,Object> portalMap = new HashMap<String,Object>();
		try {
			//获取用户登录信息
			User user = SystemSecurityUtils.getUser();
			Portal tempPortal = new Portal();
			tempPortal.setCreateUser(user.getId());
			List<Portal> portalList = portalService.queryAll(tempPortal);
			List<PortalFunction> functionList = portalFunctionService.queryAll(new PortalFunction());
			portalMap.put("portalList", portalList);
			portalMap.put("functionList", functionList);
		} catch (CustomException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		return portalMap;
	}

	/**
	  * @description  根据门户id获取门户组件列表
	  * @param Long portalId
	  * @return
	  * @author chz
	  * @version 1.0 2014年6月20日 
	*/
	public Map<String, Object> getPortletForPortalId(Long portalId,String portalType) {
		Map<String,Object> portalMap = new HashMap<String,Object>();
		Portlet portletVo = new Portlet();
		portletVo.setPortalId(portalId);
		List<Portlet> portletList = portletDao.queryAll(portletVo);
		List<Portlet> reportletList = new ArrayList<Portlet>();
		List<PortaletLayout> layoutList = null;
		try {
			PortaletLayout portaletLayoutVo = new  PortaletLayout();
			portaletLayoutVo.setPortalId(portalId);
			if(portalType != null && (portalType.equals("ptype_user") || portalType.equals("ptype_user_only"))){
				//获取用户登录信息
				User user = SystemSecurityUtils.getUser();
				portaletLayoutVo.setUserId(user.getId());
			}
			layoutList = portaletLayoutService.queryAll(portaletLayoutVo);
			
			Map<String,Portlet> portletMap = new HashMap<String,Portlet>();
			for(int i=0;i<portletList.size();i++){
				Portlet tempPortlet = portletList.get(i);
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
				reportletList.add(tempPortlet);
				portletMap.put(tempPortlet.getId().toString(), tempPortlet);
			}
			
			List<Portlet> reList = new ArrayList<Portlet>();
			if(layoutList != null && layoutList.size() > 0){
				PortaletLayout filerLayoutVo = layoutList.get(0);
				portalMap.put("layoutId", filerLayoutVo.getId());
				portalMap.put("layoutType", filerLayoutVo.getLayoutType());
				String[] layouts = filerLayoutVo.getLayoutSite().split(",");
				if(layouts.length >= 1 && layouts[0] != null && !layouts[0].equals("")){
					String[] layoutsite = layouts[0].split("&");
					for(int i=0;i<layoutsite.length;i++){
						String[] site = layoutsite[i].split("#");
						String[] layout = site[0].split("\\$");
						String[] XY = site[1].split("\\$");
						if(layout[0].equals("-99")){
							Portlet tempPortlet = new Portlet();
							tempPortlet.setId(-99l);
							tempPortlet.setSiteX(XY[0]);
							tempPortlet.setSiteY(XY[1]);
							tempPortlet.setLayoutPackId(layout[1]);
							reList.add(tempPortlet);
						}else {
							Portlet tempPortlet = portletMap.get(layout[0]);
							if(tempPortlet != null){
								tempPortlet.setSiteX(XY[0]);
								tempPortlet.setSiteY(XY[1]);
								tempPortlet.setLayoutPackId(layout[1]);
								reList.add(tempPortlet);
							}
						}
					}
				}
				if(layouts.length > 1 && layouts[1] != null && !layouts[1].equals("")){
					String[] shortcutlay = layouts[1].split("&");
					for(int i=0;i<shortcutlay.length;i++){
						Portlet tempPortlet = portletMap.get(shortcutlay[i]);
						if(tempPortlet != null){
							tempPortlet.setSiteX("4");
							tempPortlet.setSiteY("4");
							tempPortlet.setLayoutPackId("4");
							reList.add(tempPortlet);
						}
					}
				}
			}else {
				portalMap.put("layoutId", "0");
				portalMap.put("layoutType", "1");	
			}
			
			Collections.sort(reList, new Comparator<Portlet>(){  
		          public int compare(Portlet a, Portlet b) {  
		        	 //第一次比较行标
		        	 int i = a.getLayoutPackId().compareTo(b.getLayoutPackId());
		        	 //如果行标相同则进行第二次比较
		        	 if(i==0){
		        		 //第二次比较列标
		        	    int j=a.getSiteY().compareTo(b.getSiteY());
		        	    if(j==0){
			        		 //第二次比较列标
			        	    int x=a.getSiteX().compareTo(b.getSiteX());
			        	    return x;
			        	 }
		        	     return j;
		        	 }
		        	 return i;
		        }
		    }); 
			
			portalMap.put("reList", reList);
			portalMap.put("reListSize", reList.size());
			portalMap.put("portletList", reportletList);
		} catch (CustomException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		return portalMap;
	}

	public Map<String,Object> queryByuseFuncionids(String funtionids) {
		Map<String,Object> portalMap = new HashMap<String,Object>();
		Portlet portletVo = new Portlet();
		String ids = portletDao.queryByuseFuncionids(portletVo);
		String[] vals = funtionids.split(",");
		for(int i=0;i<vals.length;i++){
			if(ids.indexOf(vals[i]) != -1){
				portalMap.put(GlobalContext.RESULT_SUCCESS, "false");
				break;
			} else {
				portalMap.put(GlobalContext.RESULT_SUCCESS, "true");
			}
		}
		return portalMap;
	}

	public Integer deleteByPortalId(Portlet portlet) {
		return portletDao.deleteByPortalId(portlet);
	}

	public Map<String, Object> valNameEcho(Portlet portlet) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long portalid = portlet.getId();
		int result = portletDao.valNameEcho(portlet);
		if(portalid == null){
			if(result == 0){
				resultMap.put("success", "true");
			} else {
				resultMap.put("success", "false");
			}
		} else {
			Portlet tempVo = new Portlet();
			tempVo.setId(portalid);
			tempVo = portletDao.get(tempVo);
			if(tempVo.getLetTitle().equals(portlet.getLetTitle())){
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

	public Integer reLayout(Portlet portlet) {
		int flag = 0;
		List<PortaletLayout> layoutList = null;
		PortaletLayout portaletLayoutVo = new PortaletLayout();
		portaletLayoutVo.setPortalId(portlet.getPortalId());
		try {
			if(portlet.getViewType() != null){
				layoutList = portaletLayoutService.queryAll(portaletLayoutVo);
				if(layoutList != null && layoutList.size() > 0){
					for(int i=0;i<layoutList.size();i++){
						PortaletLayout filerLayoutVo = layoutList.get(i);
						String[] layouts = filerLayoutVo.getLayoutSite().split(",");
						if(portlet.getViewType().equals("shortcut")){
							if(layouts.length > 1 && layouts[0] != null && !layouts[0].equals("")){
								String temp = "&"+layouts[0];
								if(temp.indexOf("&"+portlet.getId()) > -1){
									temp = temp.replace("&"+portlet.getId(), "&-99").substring(1);
									if(layouts[1] != null && !layouts[1].equals(""))
										filerLayoutVo.setLayoutSite(temp+","+layouts[1]);
									else
										filerLayoutVo.setLayoutSite(temp+",");
									portaletLayoutService.update(filerLayoutVo);
								}
							} else if(layouts.length == 1 && layouts[0] != null && !layouts[0].equals("")){
								String temp = "&"+layouts[0];
								if(temp.indexOf("&"+portlet.getId()) > -1){
									temp = temp.replace("&"+portlet.getId(), "&-99").substring(1);
									filerLayoutVo.setLayoutSite(temp+",");
									portaletLayoutService.update(filerLayoutVo);
								}
							} 
						}else {
							if(layouts.length > 1 && layouts[1] != null && !layouts[1].equals("") && layouts[1].indexOf(portlet.getId().toString())>-1){
								String temp = "&"+layouts[1];
								temp = temp.replace("&"+portlet.getId(), "").substring(1);
								filerLayoutVo.setLayoutSite(layouts[0]+","+temp);
								portaletLayoutService.update(filerLayoutVo);
							}
						}
					}
				}
			} else {
				//获取用户登录信息
				Portal tempPortal = new Portal();
				tempPortal.setId(portlet.getPortalId());
				tempPortal = portalService.get(tempPortal);
				if(tempPortal != null){
					User user = SystemSecurityUtils.getUser(); 
					PortletRelation portletRelation = new PortletRelation();
					portletRelation.setPortalId(portlet.getPortalId());
					portletRelation.setPortletId(portlet.getId());
					portletRelation.setUserId(user.getId());
					List<PortletRelation> templist = portletRelationService.queryAll(portletRelation);
					if(templist != null && templist.size() > 0){
						Long relationid = templist.get(0).getId();
						portletRelation.setId(relationid);
						portletRelation.setLetFile(portlet.getLetFile());
						portletRelation.setLetTextarea(portlet.getLetTextarea());
						portletRelationService.update(portletRelation);
					}else {
						portletRelation.setLetFile(portlet.getLetFile());
						portletRelation.setLetTextarea(portlet.getLetTextarea());
						portletRelationService.save(portletRelation);
					}
				}
			}
			flag = portletDao.update(portlet);
		} catch (CustomException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return flag;
	}

	public Integer reLayoutFordel(Portlet portlet, String ids) {
		String[] portletid = ids.split(",");
		int flag = 0;
		try {
			for(int i=0;portletid.length > 0 && i<portletid.length;i++){
				Portlet portletVo = new Portlet();
				portletVo.setId(Long.valueOf(portletid[i]));
				portletVo = portletDao.get(portletVo);
				List<PortaletLayout> layoutList = null;
				PortaletLayout portaletLayoutVo = new PortaletLayout();
				portaletLayoutVo.setPortalId(portletVo.getPortalId());
				layoutList = portaletLayoutService.queryAll(portaletLayoutVo);
				if(layoutList != null && layoutList.size() > 0){
					for(int j=0;j<layoutList.size();j++){
						PortaletLayout filerLayoutVo = layoutList.get(j);
						String[] layouts = filerLayoutVo.getLayoutSite().split(",");
						String tempone = "";
						String temptwo = "";
						if(layouts.length >= 1 && layouts[0] != null && !layouts[0].equals("")){
							tempone = "&"+layouts[0];
							if(tempone.indexOf("&"+portletVo.getId()) > -1){
								tempone = tempone.replace("&"+portletVo.getId(), "&-99").substring(1);
							} else {
								tempone = layouts[0];
							}
						}
						if(layouts.length > 1 && layouts[1] != null && !layouts[1].equals("")){
							temptwo = "&"+layouts[1];
							if(temptwo.indexOf(portletVo.getId().toString())>-1){
								temptwo = temptwo.replace("&"+portletVo.getId(), "");
								if(temptwo.length() > 0)
									temptwo = temptwo.substring(1);
							}else {
								temptwo = layouts[1];
							}
							
						}
						filerLayoutVo.setLayoutSite(tempone+","+temptwo);
						portaletLayoutService.update(filerLayoutVo);
					}
				}
			}
			
			PortalFriendlylink portalFriendlylink = new PortalFriendlylink();
			portalFriendlylink.setPrimaryKeys(portletid);
			portalFriendlylinkService.delFriendlyLinksForPortletIds(portalFriendlylink);
			
			portlet.setPrimaryKeys(portletid);
			flag = portletDao.delete(portlet, true);
		} catch (CustomException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return flag;
	}

	public Integer updateForRelation(Long portletId, String url) {
		int flag = 0;
		Portlet portlet = new Portlet();
		portlet.setId(portletId);
		portlet = portletDao.get(portlet);
		//获取用户登录信息
		Portal tempPortal = new Portal();
		tempPortal.setId(portlet.getPortalId());
		try {
			tempPortal = portalService.get(tempPortal);
			if(tempPortal != null){
				User user = SystemSecurityUtils.getUser();
				PortletRelation portletRelation = new PortletRelation();
				portletRelation.setPortalId(portlet.getPortalId());
				portletRelation.setPortletId(portlet.getId());
				portletRelation.setLetFile(url);
				portletRelation.setLetTextarea(portlet.getLetTextarea());
				portletRelation.setUserId(user.getId());
				List<PortletRelation> templist = portletRelationService.queryAll(portletRelation);
				if(templist != null && templist.size() > 0){
					flag = portletRelationService.update(portletRelation);
				}else {
					flag = portletRelationService.save(portletRelation);
				}
			} else {
				portlet.setLetFile(url);
				portletDao.update(portlet);
			}
		} catch (CustomException e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	public String queryForPortalType(Long portletId) {
		String returntype = "";
		Portlet portlet = new Portlet();
		portlet.setId(portletId);
		portlet = portletDao.get(portlet);
		//获取用户登录信息
		Portal tempPortal = new Portal();
		tempPortal.setId(portlet.getPortalId());
		try {
			tempPortal = portalService.get(tempPortal);
			returntype = tempPortal.getPortalType();
		} catch (CustomException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return returntype;
	}

}