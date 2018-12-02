package com.jc.system.security.dao;

import java.util.List;

import com.jc.system.security.domain.Menu;
import com.jc.foundation.dao.IBaseDao;


/**
 * @title GOA2.0
 * @description  dao接口类
 * @author 
 * @version  2014-04-17
 */
 
public interface IMenuDao extends IBaseDao<Menu>{

	public List<Menu> queryMenu(Menu menu);
	
	/**
	  * @description  用户角色使用菜单
	  * @param menu
	  * @return
	  * @author 孙鹏
	  * @version 1.0 2014年5月15日 下午1:54:09
	*/
	public List<Menu> queryWithRole(Menu menu);
	
	/**
	  * @description  根据菜单id及用户id判断当前用户是否有此菜单
	  * @param menu
	  * @return
	  * @author chz
	  * @version 1.0 2014年6月16日 
	*/
	public List<Menu> queryUserMenu(Menu menu);
}
