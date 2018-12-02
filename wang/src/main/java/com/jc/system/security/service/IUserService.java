package com.jc.system.security.service;

import java.util.List;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.User;
import com.jc.android.oa.system.domain.User4M;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-18
 * 
 */
public interface IUserService extends IBaseService<User>{
	
	public PageManager query(User user, PageManager page);
	
	public PageManager queryAllUsers(User user, PageManager page);
	
	public List<User> queryUserByLeader(User user);
	
	public List<User> queryUserByLeaderAndDeptId(User user);
	
	public List<User> queryUserByIds(String ids);
	
	public User getUser(User user) throws CustomException;
	
	public Integer initPassword(User user) throws CustomException;
	
	public List<User> queryUserByDeptId(User user);
	
	public User getUserById(User user);
	
	public void getAllUsers();
	
	public User getUser(Long id);
	
	public User4M getUser4M(Long id);
	
	public Integer updateUser(User user) throws CustomException;
	
	public Integer update2(User user);
	
	public User checkMobile(User user);
	
	public List<Department> queryUserTreeByIds(String ids);
	
	public boolean isLeader(User sourceUser,User targetUser);

	public boolean isLeader(Long sourceUser, Long targetUser);
	
	public Integer updateUserInfo(User user) throws CustomException;
	
	public List<User4M> queryAll4M(User4M user4m);
	
	public PageManager queryAllUsersForOrder(User user, PageManager page);
	
	public Integer updateUserOrder(String userId, String orderNo, String oUserId, String oOrderNo);
	
}