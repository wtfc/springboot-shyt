package com.jc.system.security.service;

import java.util.List;
import java.util.Map;

import com.jc.foundation.service.IBaseService;
import com.jc.system.security.domain.Menu;

/**
 * @title GOA2.0
 * @description  业务接口类
 * @author 
 * @version  2014-04-17
 */

public interface IMenuService extends IBaseService<Menu>{
	public Map<String,Object> desktopMenuForQuery(Menu menuVo); 
	
	public Menu getAddInfo(Long id);
	
	public Menu getEditInfo(Long id);
	
	public String groupMenuId(Long id);
	
	public Map<String,Object> getNavigationMenu(Long id);
	
	public Map<String,Object> getNavigationMenu(String url);
	
	public List<Menu> getResourceInfo(Menu menu,String seltype);
	
	public int getRolesByMenu(Long id);
	
	/**
	  * @description  用户角色使用菜单
	  * @param menu
	  * @return
	  * @author 孙鹏
	  * @version 1.0 2014年5月15日 下午1:54:09
	*/
	public List<Menu> queryWithRole(Menu menu);
	
	public Menu queryUserMenu(Long id,Long userId);
}