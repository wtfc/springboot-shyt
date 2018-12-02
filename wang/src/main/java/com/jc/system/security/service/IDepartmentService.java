package com.jc.system.security.service;

import java.util.List;
import java.util.Map;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.User;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-18
 * 
 */
public interface IDepartmentService extends IBaseService<Department> {
	
	/**
	 * 分页查询方法
	 * @param Department
	 *            department 实体类
	 * @param PageManager
	 *            page 分页实体类
	 * @return PagingBean 查询结果
	 * @author
	 * @version 2014-03-18
	 */
	public PageManager query(Department department, PageManager page) ;

	/**
	 * 查询方法
	 * @param Department
	 *            department 实体类
	 * @return PagingBean 查询结果
	 * @author
	 * @version 2014-03-18
	 */
	public List<Department> query(Department department) throws CustomException;

	/**
	 * 根据主键删除多条记录方法
	 * @param Department
	 *            department 实体类
	 * @return Integer 处理结果
	 * @author
	 * @version 2014-03-18
	 */
	public Integer deleteByIds(Department department) throws CustomException;

	/**
	 * 保存方法
	 * @param Department
	 *            department 实体类
	 * @return Integer 增加的记录数
	 * @author
	 * @version 2014-03-18
	 */
	public Integer save(Department department) throws CustomException;

	/**
	 * 修改方法
	 * @param Department
	 *            department 实体类
	 * @return Integer 修改的记录数量
	 * @author
	 * @version 2014-03-18
	 */
	public Integer update(Department department) throws CustomException;

	/**
	 * 分页查询方法
	 * @param Department
	 *            department 实体类
	 * @param PageManager
	 *            page 分页实体类
	 * @return PagingBean 查询结果
	 * @author
	 * @version 2014-03-18
	 */
	public PageManager searchQuery(Department department, PageManager page) throws CustomException;

	/**
	 * 查询部门树(带部门负责人)
	 * @param Department
	 * @author
	 * @version 2014-04-15
	 */
	public List<Department> queryTree(Department department) throws CustomException;
	
	/**
	 * 查询机构树
	 * @param Department
	 * @author 高研
	 * @version 2014-06-20
	 */
	public List<Department> queryOrgTree(Department department) throws CustomException;
	
	/**
	 * 查询管理员机构部门树
	 * @param Department
	 * @author 高研
	 * @version 2014-06-20
	 */
	public List<Department> queryManagerDeptTree(Long userId) throws CustomException;

	/**
	 * 查询部门信息(带上级部门)
	 * @param Department
	 * @author
	 * @version 2014-04-15
	 */
	public Department queryOne(Department department) throws CustomException;

	/**
	 * 根据部门ID查询所有节点
	 * @param department
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年4月22日 上午11:53:38
	 */
	public List<Department> queryAllByDeptId(Department department) ;

	/**
	 * 根据部门Id集合删除
	 * @param department
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年4月22日 下午2:00:10
	 */
	public Integer updateByDeptIds(Department department) throws CustomException;

	/**
	 * 根据部门ID获取本部门及所有子部门加人员信息
	 * @param id
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年5月22日 下午4:12:39
	 */
	public List<Department> getDeptAndUserByDeptId(Long id) throws CustomException;

	/**
	 * 逻辑删除部门-[删除关联关系adminSide]
	 * @param department
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年5月23日 下午1:57:59
	 */
	public Map<String, Object> logicDeleteById(Department department) throws CustomException;
	
	/**
	 * 分页查询方法[用户查询]
	 * @param User  user 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	public PageManager userManageList(User user, final PageManager page) throws CustomException;
	
	/**
	 * 用户查询
	 * @return List<User>
	 * @author 张继伟
	 * @version 1.0 2014年4月28日 下午4:41:45
	 */
	public List<User> searchUserList() throws CustomException ;
	
	/**
	 * 获取全部部门及人员
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年5月15日 上午8:27:24
	 */
	public String getAllDeptAndUser() throws Exception ;
	
	/**
	 * 获取在线用户信息
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年6月16日 上午10:06:55
	 */
	public String getDeptAndUserByOnLine() throws Exception ;
	
	/**
	 * 获取职务人员
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年6月17日 上午11:19:10
	 */
	public String getPostAndUser() throws Exception ;
	
	/**
	 * 获取个人组别
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年7月29日 下午6:37:18
	 */
	public String getPersonGroupAndUser() throws Exception ;
	
	/**
	 * 获取公共组别
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年7月30日 上午9:22:10
	 */
	public String getPublicGroupAndUser() throws Exception ;

	/**
	 * 清除部门人员的缓存信息
	 * @author 张继伟
	 * @version 1.0 2014年6月23日 上午10:28:24
	 */
	public void clearDeptAndUserCache() ;
	
	/**
	 * 根据登录用户与角色获取组织
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年6月26日 下午8:20:51
	 */
	public List<Department> getDepartmentByUserAndRole() throws Exception;
	
	/**
	 * 根据部门ID查找所在
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年8月7日 上午10:54:51
	 */
	public String getDeptIdsByOrgId() throws Exception;
	
	/**
	 * 根据部门ID获取本部门及所有子部门
	 * @param id
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年5月22日 下午4:12:39
	 */
	public List<Department> getDeptByDeptId(Long id) throws CustomException;
	
	/**
	 * 获取所有组织并存入缓存
	 * @author 张继伟
	 * @version 1.0 2014年10月18日 下午12:03:48
	 */
	public void getAllDepts();
	
	/**
	 * 缓存查询单个组织
	 * @param dept
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年10月18日 下午2:27:41
	 */
	public Department getDeptById(Department dept);
	
	/**
	 * 根据登录人所在机构查询组织树
	 * @return
	 * @throws CustomException
	 * @author 张继伟
	 * @version 1.0 2014年11月6日 下午3:12:05
	 */
	public List<Department> getOrgTree() throws CustomException;
	
	/**
	 * 查询整个机构组织树不包含部门
	 * @return
	 * @throws CustomException
	 * @author 张继伟
	 * @version 1.0 2014年12月3日 下午12:01:36
	 */
	public List<Department> getAllOrgNoDeptTree() throws CustomException ;
	
	/**
	 * 根据登录人所在机构查询组织树与人员
	 * @return
	 * @throws CustomException
	 * @author 张继伟
	 * @version 1.0 2014年11月6日 下午3:55:15
	 */
	public List<Department> getOrgAndPersonTree() throws CustomException;
	
	/**
	 * 根据登录人员获取组织用户--手机端
	 * @param userInfo
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年11月11日 下午3:25:38
	 */
	public Map<String, Object> queryDeptAndUserFor4M(User userInfo)	throws CustomException;
	
}