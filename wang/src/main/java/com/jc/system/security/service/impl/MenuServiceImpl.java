package com.jc.system.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.CustomException;
import com.jc.system.portal.domain.Portal;
import com.jc.system.portal.service.IPortalService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.dao.IMenuDao;
import com.jc.system.security.domain.Menu;
import com.jc.system.security.domain.RoleMenus;
import com.jc.system.security.domain.SysUserRole;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IMenuService;
import com.jc.system.security.service.IRoleMenusService;

/**
 * @title GOA2.0
 * @description  业务服务类
 * @author 
 * @version  2014-04-17
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements IMenuService{

	private IMenuDao menuDao;
	
	@Autowired
	private IPortalService portalService;
	
	@Autowired
	public MenuServiceImpl(IMenuDao menuDao){
		super(menuDao);
		this.menuDao = menuDao;
	}
	public MenuServiceImpl(){
		
	}
	
	@Autowired
	private IRoleMenusService roleMenuService;
	
	/**
	 * @description 桌面菜单查询方法
	 * @param Menu menuVo 实体类
	 * @return Map 查询结果集
	 * @author chz
	 * @version 2014-04-18
	 */
	public Map<String,Object> desktopMenuForQuery(Menu menuVo) {
		Map<String,Object> menuMap = new HashMap<String,Object>();
		List<Menu> menuList = menuDao.queryMenu(menuVo);
		if(menuList != null && menuList.size()>0){
			List<Menu> leftmenuList = new ArrayList<Menu>();
			for(int k=0;k<menuList.size();k++){
				Menu tempMenu = menuList.get(k);
				if(tempMenu.getId() == 1){
					menuMap.put("homemenu", tempMenu.getName());
					tempMenu.setUserId(menuVo.getUserId());
					leftmenuList = getResourceInfo(tempMenu,"power");
					for(int i=0;i<leftmenuList.size();i++){
						Menu portalMenu = leftmenuList.get(i);
						if(portalMenu.getId()==0 && portalMenu.getQueue() != null && portalMenu.getQueue() == 1){
							menuMap.put("portalurl", portalMenu.getActionName());
							tempMenu.setActionName(portalMenu.getActionName());
							break;
						} else {
							menuMap.put("portalurl", "");
						}
					}
					break;
				}
			}
			menuMap.put("leftmenuList", leftmenuList);
		}
		//menuMap.put("menuList", menuList);
		
		menuMap.put("menuList", gatherMenus(menuList,menuVo.getUserId()));//一次取出所有菜单
		
		return menuMap;
	}
	
	/**
	 * @description 资源添加查询方法
	 * @param  int id 菜单id
	 * @return Menu 查询结果
	 * @author chz
	 * @version 2014-04-21
	 */
	public Menu getAddInfo(Long id) {
		Menu menuVo = new Menu();
		if(id == -1){
			menuVo.setParentId(-1L);
			menuVo.setParentname("全部");
			menuVo.setRootNode("-1");
		} else {
			Menu menutemp = new Menu();
			menutemp.setId(id);
			menutemp = menuDao.get(menutemp);
			menuVo.setParentId(id);
			menuVo.setParentname(menutemp.getName());
			menuVo.setRootNode(menutemp.getParentId().toString());
		}
		return menuVo;
	}
	
	
	/**
	 * @description 资源维护查询方法
	 * @param  int id 菜单id
	 * @return Menu 查询结果
	 * @author chz
	 * @version 2014-04-21
	 */
	public Menu getEditInfo(Long id) {
		Menu menuVo = new Menu();
		menuVo.setId(id);
		menuVo = menuDao.get(menuVo);
		
		if(menuVo.getParentId() != -1L){
			Menu menutemp = new Menu();
			menutemp.setId(menuVo.getParentId());
			menutemp = menuDao.get(menutemp);
			menuVo.setParentname(menutemp.getName());
			menuVo.setRootNode(String.valueOf(menuVo.getParentId()));
		}else {
		    menuVo.setParentname("全部");
		}
			
		return menuVo;
	}
	
	/**
	 * @description 组装当前菜单及其子菜单id方法
	 * @param  int id 菜单id
	 * @return ids 查询结果
	 * @author chz
	 * @version 2014-04-21
	 */
	public String groupMenuId(Long id) {
		String ids = String.valueOf(id);
		ids = recursionmenuid(id,ids);//递归
		return ids;
	}
	
	private String recursionmenuid(Long id,String ids) {
		Menu menuVo = new Menu();
		menuVo.setParentId(id);
		List<Menu> menulist = menuDao.queryAll(menuVo);
		for(int i=0;menulist != null && i<menulist.size();i++){
			Long temp = menulist.get(i).getId();
			ids = ids+","+temp;
			ids = recursionmenuid(temp,ids);
		}
		
		return ids;
	}
	
	/**
	 * @description 组装当前菜单及其子菜单列表
	 * @param  list 菜单列表
	 * @return list 菜单列表
	 * @author chz
	 * @version 2014-07-17
	 */
	public List<Menu> gatherMenus(List<Menu> leftmenuList,Long userid){
		List<Menu> returnlist = new ArrayList<Menu>();
		if(leftmenuList != null && leftmenuList.size() > 0){
			Map<String,Menu> menuMap = new HashMap<String,Menu>();
			for(int i=0;i<leftmenuList.size();i++){
				Menu remenuVo = leftmenuList.get(i);
				if(remenuVo.getMenuType() == 0){
					String key = remenuVo.getParentId()+"-"+remenuVo.getId();
					menuMap.put(key, remenuVo);
				}
			}
			
			for(int i=0;i<leftmenuList.size();i++){
				Menu remenuVo = leftmenuList.get(i);
				if(remenuVo.getParentId() != -1)continue;
				List<Menu> childmenus = new ArrayList<Menu>();
				for(int k=0;k<leftmenuList.size();k++){
					String getkey = remenuVo.getId()+"-"+leftmenuList.get(k).getId();
					if(menuMap.containsKey(getkey))
						childmenus.add(menuMap.get(getkey));
				}
				gatherchildMenus(leftmenuList,childmenus,menuMap);
				if(remenuVo.getId() == 1){
					setportalToMenu(childmenus,remenuVo,"power","");
				}
				remenuVo.setChildmenus(childmenus);
				remenuVo.setChildmenussize(childmenus.size());
				returnlist.add(remenuVo);
			}
		}
		return returnlist;
	}
	
	/**
	 * @description 组装当前菜单及其子菜单列表
	 * @param  list leftmenuList 全菜单列表,list childmenuList 子菜单列表,Map menuMap菜单列表
	 * @return list 菜单列表
	 * @author chz
	 * @version 2014-08-27
	 */
	public List<Menu> gatherchildMenus(List<Menu> leftmenuList,List<Menu> childmenuList,Map<String,Menu> menuMap){
		for(int i=0;i<childmenuList.size();i++){
			Menu remenuVo = childmenuList.get(i);
			if(remenuVo.getId() == -1)
				continue;
			List<Menu> childmenus = new ArrayList<Menu>();
			for(int k=0;k<leftmenuList.size();k++){
				String getkey = remenuVo.getId()+"-"+leftmenuList.get(k).getId();
				if(menuMap.containsKey(getkey))
					childmenus.add(menuMap.get(getkey));
			}
			gatherchildMenus(leftmenuList,childmenus,menuMap);
			if(remenuVo.getId() == 1){
				setportalToMenu(childmenus,remenuVo,"power","");
			}
			remenuVo.setChildmenus(childmenus);
			remenuVo.setChildmenussize(childmenus.size());
		}
		
		return childmenuList;
	}
	
	/**
	 * @description 根据id导航栏查询方法
	 * @param  int id 菜单id
	 * @return Map 查询结果
	 * @author chz
	 * @version 2014-04-23
	 */
	public Map<String,Object> getNavigationMenu(Long id) {
		Map<String,Object> menuMap = new HashMap<String,Object>();
		List<Menu> returnlist = new ArrayList<Menu>();
		List<Menu> templist = new ArrayList<Menu>();
		templist = recursionNavigation(id,templist);
		if(templist != null){
			for(int i=templist.size()-1;i>=0;i--){
				returnlist.add(templist.get(i));
			}
			menuMap.put("navigationname", templist.get(0).getName());
			menuMap.put("navigationMenuList", returnlist);
		}
		return menuMap;
	}
	
	/**
	 * @description 根据url导航栏查询方法
	 * @param  int id 菜单id
	 * @return Map 查询结果
	 * @author chz
	 * @version 2014-07-23
	 */
	public Map<String,Object> getNavigationMenu(String url) {
		Map<String,Object> menuMap = new HashMap<String,Object>();
		List<Menu> returnlist = new ArrayList<Menu>();
		List<Menu> templist = new ArrayList<Menu>();
		Menu tempvo = new Menu();
		tempvo.setActionName(url);
		List<Menu> queryMenus= menuDao.queryAll(tempvo);
		if(queryMenus != null && queryMenus.size() > 0)
			templist = recursionNavigation(queryMenus.get(0).getId(),templist);
		
		if(templist != null && templist.size() > 0){
			for(int i=templist.size()-1;i>=0;i--){
				returnlist.add(templist.get(i));
			}
			menuMap.put("navigationname", templist.get(0).getName());
			menuMap.put("navigationMenuList", returnlist);
		}
		
		return menuMap;
	}
	
	private List<Menu> recursionNavigation(Long id,List<Menu> menulist){
		
		Menu menuVo = new Menu();
		menuVo.setId(id);
		menuVo = menuDao.get(menuVo);
		menulist.add(menuVo);
		if(menuVo.getParentId() != -1)
			menulist = recursionNavigation(menuVo.getParentId(),menulist);
		return menulist;
	}
	
	
	/**
	 * @description 资源树列表查询方法
	 * @param  Menu menu 菜单对象  String seltype 查询方式  all查询所有 power依据权限查询
	 * @return list 查询结果
	 * @author chz
	 * @version 2014-04-23
	 */
	public List<Menu> getResourceInfo(Menu menu,String seltype) {
		
		Menu menuVo = new Menu();
		menuVo.setParentId(menu.getId());
		List<Menu> menuList = null;
		if(seltype.equals("power")){
			menuVo.setUserId(menu.getUserId());
			menuList = menuDao.queryMenu(menuVo);
		}else 
			menuList = menuDao.queryAll(menuVo);
		
		String rootnode = "";
		if(menu.getId() != -1) {
			menuVo = new Menu();
			menuVo.setId(menu.getId());
			menuVo = menuDao.get(menuVo);
			rootnode = String.valueOf(menuVo.getParentId());
		}else {
			rootnode = "-1";
		}
		for(int i=0;menuList != null && i < menuList.size();i++){
			Menu edvo = menuList.get(i);
			Menu tempvo = new Menu();
			tempvo.setParentId(edvo.getId());
			tempvo.setIsShow(menu.getIsShow());
			edvo.setIsNextNode(String.valueOf(menuDao.getCount(tempvo)));
			edvo.setRootNode(rootnode);
		}
		
		setportalToMenu(menuList,menu,seltype,rootnode);
		
		return menuList;
	}
	
	/**
	 * @description 与用户相关门户添加到菜单树中
	 * @param  String seltype 查询方式  all查询所有 power依据权限查询, list menulist 菜单列表, Menu menu
	 * @return list 查询结果
	 * @author chz
	 * @version 2014-04-23
	 */
	public List<Menu> setportalToMenu(List<Menu> menuList, Menu menu, String seltype, String rootnode){
		if(seltype.equals("power")){//加载左侧菜单门户项
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
			Portal portal = new Portal();
			portal.setPortalmenuId(menu.getId());//参数后期不全 缺少登录用户信息
			portal.setRoleIds(roleStr);
			portal.setDeptId(user.getDeptId());
			portal.setUserId(user.getId());
			portal.setOrganId(user.getOrgId());
			portal.setPortalStatus("portalstatus_1");//门户状态portalstatus_2-禁用portalstatus_1-启用
			List<Portal> portalList = portalService.queryRolePortal(portal);
			if(portalList != null && portalList.size() > 0){
				for(int i=0;i<portalList.size();i++){
					Portal tempportal = portalList.get(i);
					Menu portalmenu = new Menu();
					portalmenu.setName(tempportal.getPortalName());
					portalmenu.setActionName("/sys/portal/manageView.action?portalId="+tempportal.getId()+"&portalType="+tempportal.getPortalType());//链接
					portalmenu.setRootNode(rootnode);
					portalmenu.setIsNextNode("0");
					portalmenu.setParentId(menu.getId());
					if(tempportal.getPortalType().equals("ptype_org")) /*门户类型 ptype_org 机构 ptype_dept 部门  ptype_user 个人*/		
						portalmenu.setIcon("fa fa-office icon");//样式
					else if(tempportal.getPortalType().equals("ptype_dept")) 		
						portalmenu.setIcon("fa fa-flag icon");//样式
					else if(tempportal.getPortalType().equals("ptype_user")) 		
						portalmenu.setIcon("fa fa-user2 icon");//样式
					portalmenu.setQueue(tempportal.getSequence());
					portalmenu.setId(Long.valueOf(0));
					
					menuList.add(portalmenu);
				}
			}
		}
		return menuList;
	}
	
	/**
	  * @description 根据菜单id判断该菜单是否被选中
	  * @param roleMenus
	  * @return 等于0不存在 大于0存在
	  * @author 孙鹏
	  * @version 1.0 2014年5月13日 上午11:12:03
	 * @throws CustomException 
	*/
	public int getRolesByMenu(Long id) {
		int issize = 0;
		try {
			RoleMenus roleMenus = new RoleMenus();
			roleMenus.setMenuId(id);
			List<RoleMenus> templist = roleMenuService.queryAll(roleMenus);
			if(templist != null)
				issize = templist.size();
		} catch (CustomException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return issize;
	}
	
	/**
	  * @description  用户角色使用菜单
	  * @param menu
	  * @return
	  * @author 孙鹏
	  * @version 1.0 2014年5月15日 下午1:54:09
	*/
	public List<Menu> queryWithRole(Menu menu) {
		return menuDao.queryWithRole(menu);
	}
	
	/**
	  * @description  根据菜单id及用户id判断当前用户是否有此菜单
	  * @param menu
	  * @return
	  * @author chz
	  * @version 1.0 2014年6月16日 
	*/
	public Menu queryUserMenu(Long id,Long userId) {
		Menu menuVo = new Menu();
		menuVo.setId(id);
		menuVo.setUserId(userId);
		List<Menu> menuList = menuDao.queryUserMenu(menuVo);
		if(menuList != null && menuList.size() != 0){
			menuVo = menuList.get(0);
		}else {
			menuVo = null;
		}
		return menuVo;
	}

}