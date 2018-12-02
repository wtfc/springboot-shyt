package com.jc.system.security.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.security.dao.ISystemDao;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.Menu;
import com.jc.system.security.domain.User;

@Repository
public class SystemDaoImpl extends BaseDaoImpl<User> implements ISystemDao {

	/**
	  * 查询登录用户
	  * @param String loginName
	  * @return User
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	public User get(String loginName) {
		return template.selectOne("com.jc.system.systemMap.query", loginName);
	}
	/**
	  * 查询菜单
	  * @param String userId
	  * @return List<Menu>
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	public List<Menu> queryMenu(String userId) {
		return template.selectList("com.jc.system.systemMap.queryMenus", userId);
	}
	/**
	  * 查询用户机构
	  * @param Department
	  * @return Long
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	@Override
	public Department queryOrgIdAndName(Department department) {
		template.selectOne("com.jc.system.systemMap.queryUserOrgId", department);
		return department;
	}
}