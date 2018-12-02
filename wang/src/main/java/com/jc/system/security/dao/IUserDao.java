package com.jc.system.security.dao;

import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.foundation.domain.PageManager;
import com.jc.system.security.domain.User;
import com.jc.android.oa.system.domain.User4M;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-18
 * 
 */
public interface IUserDao extends IBaseDao<User> {

	/**
	  * 初始化密码
	  * @param User
	  * @return Integer
	  * @author 高研
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	public Integer initPassword(User user);
	/**
	  * 根据部门ID查询用户
	  * @param User
	  * @return List<User>
	  * @author 高研
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	public List<User> queryUserByDeptId(User user);
	/**
	  * 根据用户ID查询下级用户
	  * @param User
	  * @return List<User>
	  * @author 高研
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	public List<User> queryUserByLeader(User user);
	/**
	  * 根据IDS查询用户
	  * @param User
	  * @return List<User>
	  * @author 高研
	  * @version 1.0 2014年4月17日 上午10:01:38
	*/
	public List<User> queryUserByIds(User usre);
	
	public Integer update2(User user);
	
	public User checkMobile(User user);
	
	public Integer updateUserInfo(User user);
	
	public List<User4M> queryAll4M(User4M user4m);
}
