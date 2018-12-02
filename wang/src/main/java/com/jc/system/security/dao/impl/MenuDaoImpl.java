package com.jc.system.security.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.system.security.domain.Menu;
import com.jc.system.security.dao.IMenuDao;
import com.jc.foundation.dao.impl.BaseDaoImpl;

/**
 * @title GOA2.0
 * @description  dao实现类
 * @author 
 * @version  2014-04-17
 */
@Repository
public class MenuDaoImpl extends BaseDaoImpl<Menu> implements IMenuDao{

	public List<Menu> queryMenu(Menu menu) {
		return template
				.selectList(getNameSpace(menu)+".queryMenus", menu);
	}
	
	/**
	  * @description  用户角色使用菜单
	  * @param menu
	  * @return
	  * @author 孙鹏
	  * @version 1.0 2014年5月15日 下午1:54:09
	*/
	public List<Menu> queryWithRole(Menu menu){
		return template
				.selectList(getNameSpace(menu)+".queryWithRole", menu);
	}

	/**
	  * @description  根据菜单id及用户id判断当前用户是否有此菜单
	  * @param menu
	  * @return
	  * @author chz
	  * @version 1.0 2014年6月16日 
	*/
	public List<Menu> queryUserMenu(Menu menu) {
		return template.selectList(getNameSpace(menu)+".queryUserMenu", menu);
	}

}