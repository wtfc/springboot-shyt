package com.jc.system.security.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.foundation.domain.PageManager;
import com.jc.system.security.dao.IUserDao;
import com.jc.system.security.domain.User;
import com.jc.android.oa.system.domain.User4M;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-18
 * 
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

	/**
	  * 初始化密码
	  * @param User
	  * @return Integer
	  * @author 高研
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	@Override
	public Integer initPassword(User user) {
		return template.update(getNameSpace(user)+".initPassword", user);
	}
	/**
	  * 根据部门ID查询用户
	  * @param User
	  * @return List<User>
	  * @author 高研
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	@Override
	public List<User> queryUserByDeptId(User user) {
		return template.selectList(getNameSpace(user) + ".queryUserByDeptId", user);
	}
	/**
	  * 根据用户ID查询下级用户
	  * @param User
	  * @return List<User>
	  * @author 高研
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	@Override
	public List<User> queryUserByLeader(User user) {
		return template.selectList(getNameSpace(user) + ".queryUserByLeader", user);
	}
	/**
	  * 根据IDS查询用户
	  * @param User
	  * @return List<User>
	  * @author 高研
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	@Override
	public List<User> queryUserByIds(User user) {
		return template.selectList(getNameSpace(user) + ".queryAllUsers", user);
	}
	@Override
	public Integer update2(User user) {
		return template.update(getNameSpace(user) + ".update2", user);
	}
	@Override
	public User checkMobile(User user) {
		return template.selectOne(getNameSpace(user) + ".checkMobile", user);
	}
	@Override
	public Integer updateUserInfo(User user) {
		return template.update(getNameSpace(user) + ".updateUserInfo", user);
	}
	@Override
	public List<User4M> queryAll4M(User4M user4m) {
		return template.selectList("com.jc.system.security.domain.User.queryAllFor4M", user4m);
	}
}