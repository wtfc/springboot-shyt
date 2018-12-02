package com.jc.system.common.service;

import java.util.List;

import com.jc.system.CustomException;
import com.jc.system.security.beans.UserBean;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.Role;
import com.jc.system.security.domain.User;

public interface ICommonService {

	public String getDBSysdate() throws CustomException;

	/**
	 * @description 获得部门树和人员数据
	 * @version 1.0 下午3:37:25
	 * @throws Exception
	 */
	public List<Department> getDeptsAndUser() throws Exception;

	/**
	 * @description 查询管理员机构树(包括部门)
	 * @version 1.0 下午3:37:25
	 */
	@SuppressWarnings("rawtypes")
	public List queryManagerDeptRree();
	
	/**
	 * @description 查询管理员机构树(不包括部门)
	 * @author 高研
	 * @version 1.0 下午3:37:25
	 */
	@SuppressWarnings("rawtypes")
	public List queryManagerOrgRree();

	/**
	 * @description 根据部门ID获取本部门及所有子部门及人员信息
	 * @param id
	 * @return
	 * @version 1.0 2014年5月23日 上午11:47:51
	 */
	public List<Department> getDeptAndUserByDeptId(Long id) throws Exception ;
	
	/**
	 * 获取在线用户登录名
	 * @return
	 * @version 1.0 2014年6月13日 上午8:29:14
	 */
	public List<UserBean> getOnlineUserBean();
	
	/**
	 * 获取在线用户数
	 * @return
	 * @version 1.0 2014年6月13日 上午8:29:14
	 */
	public int getOnlineUserCount();
	
	/**
	 * 根据用户ID获取用户信息
	 * @param id
	 * @return
	 * @version 1.0 2014年6月24日 下午1:55:07
	 */
	public User getUserById(Long id);
	
	/**
	  * @description 用户管理角色列表
	  * @param response
	  * @throws Exception
	  * @version 1.0 2014年5月23日 下午1:50:32
	 * @throws CustomException 
	*/
	public List<Role> getRolesForUser() throws CustomException;
	
	/**
	 * 根据部门ID获取本部门及所有子部门司机信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @version 1.0 2014年7月23日 上午11:41:53
	 */
	public List<User> getDriverByDeptId(Long id) throws Exception ;
	
	/**
	 * 根据机构ID获取所有领导信息
	 * @param user
	 * @return
	 * @throws Exception
	 * @version 1.0 2014年8月18日 上午9:33:01
	 */
	public List<User> getLeaderUserByDeptId(User user) throws Exception ;
	
	/**
	 * 根据登录人所以在机构ID获取本机构所有子部门司机信息
	 * @param orgId
	 * @return
	 * @throws Exception
	 * @version 1.0 2014年8月26日 上午8:58:14
	 */
	public List<User> getDriverByOrgId(Long orgId) throws Exception ;
	
	/**
	 * 根据登录人所以在机构ID获取本机构所有子部门信息
	 * @param deptId
	 * @return
	 * @throws Exception
	 * @version 1.0 2014年9月29日 上午11:37:02
	 */
	public List<Department> getDeptByDeptId(Long deptId) throws Exception ; 
	
	/**
	 * 根据机构ID获取本机构所有子部门人员信息
	 * @param orgId
	 * @return
	 * @throws Exception
	 * @version 1.0 2014年8月26日 上午8:58:14
	 */
	public List<User> getUsersByOrgId(Long orgId) throws Exception ;
}
