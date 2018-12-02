package com.jc.system.security.dao;

import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.Menu;
import com.jc.system.security.domain.User;

public interface ISystemDao extends IBaseDao<User> {

	/**
	  * 查询登录用户
	  * @param String loginName
	  * @return User
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	public User get(String loginName);
	/**
	  * 查询菜单
	  * @param String userId
	  * @return List<Menu>
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	public List<Menu> queryMenu(String userId);
	/**
	  * 查询用户机构
	  * @param Department
	  * @return Long
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	public Department queryOrgIdAndName(Department department);
}