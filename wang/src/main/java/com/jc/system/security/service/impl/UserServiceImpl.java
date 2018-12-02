package com.jc.system.security.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.android.oa.system.domain.User4M;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.CustomException;
import com.jc.system.common.util.CacheUtils;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.dao.IUserDao;
import com.jc.system.security.domain.AdminSide;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.SysOtherDepts;
import com.jc.system.security.domain.SysUser;
import com.jc.system.security.domain.SysUserRole;
import com.jc.system.security.domain.User;
import com.jc.system.security.exception.SystemException;
import com.jc.system.security.service.IAdminSideService;
import com.jc.system.security.service.IDepartmentService;
import com.jc.system.security.service.ISysOtherDeptsService;
import com.jc.system.security.service.ISysUserRoleService;
import com.jc.system.security.service.ISysUserService;
import com.jc.system.security.service.ISystemService;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.UserUtils;

/**
 * @title GOA2.0
 * @version 2014-03-18
 * 
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
	
	@Autowired
	public UserServiceImpl(IUserDao dao) {
		super(dao);
		this.userDao =dao;
	}
	public UserServiceImpl(){
		
	}
	
	private IUserDao userDao;
	
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysOtherDeptsService sysOtherDeptsService;
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	@Autowired
	private IAdminSideService adminSideService;
	@Autowired
	private IDepartmentService departmentService;
	@Autowired
	private ISystemService systemService;
	

	public static final String SQL_QUERYALLUSERCOUNT= "queryAllUsersCount";
	
	public static final String SQL_QUERYALLUSER= "queryAllUsers";
	
	public static final String SQL_QUERYALLUSERCOUNTFORORDER= "queryAllUsersCountForOrder";
	
	public static final String SQL_QUERYALLUSERFORORDER= "queryAllUsersForOrder";
	
	public static final String CACHE_USER_INFO = "user_info_";
	
	public static final String CACHE_DEPT_INFO = "dept_info_";

	/**
	 * 保存方法
	 * @param User user 实体类
	 * @return Integer 增加的记录数
	 * @author
	 * @version 2014-03-18
	 * @throws CustomException 
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer save(User user) throws CustomException {
		try {
			// 判断登录名是否存在
			User u = new User();
			u.setLoginName(user.getLoginName());
			u.setDeleteFlag(null);
			if (userDao.get(u) != null) {
				throw new CustomException(MessageUtils.getMessage("JC_SYS_089"));
			}
			//判断手机号是否存在
			if(StringUtils.isNotEmpty(user.getMobile())){
				u = null;
				u = new User();
				u.setDeleteFlag(null);
				u.setMobile(user.getMobile());
				if (userDao.get(u) != null) {
					throw new CustomException(MessageUtils.getMessage("JC_SYS_090"));
				}
			}
			
			user.setPassword(SystemSecurityUtils.entryptPassword(GlobalContext.PASSWORD_DEFAULT_VALUE));
			user.setTheme("1");
			SysUser sysUser = new SysUser();
			UserUtils.user2SysUser(user, sysUser);
			sysUser.setSource(GlobalContext.USER_SOURCE_OA);
			//保存到通用用户表
			sysUserService.save(sysUser);
			user.setId(sysUser.getId());
			//保存到用户表
			propertyService.fillProperties(user,false);
			userDao.save(user);
			//保存其他部门
			if(user.getOtherDepts() != null && user.getOtherDepts().size() > 0){
				for(SysOtherDepts dept : user.getOtherDepts()){
					dept.setUserId(sysUser.getId());
				}
				sysOtherDeptsService.saveList(user.getOtherDepts());
			}
			//保存角色
			if(user.getSysUserRole() != null && user.getSysUserRole().size() > 0){
				for(SysUserRole role : user.getSysUserRole()){
					role.setUserId(sysUser.getId());
				}
				sysUserRoleService.saveList(user.getSysUserRole());
			}
			//保存管理员机构树
			if(user.getIsAdmin() == 1 && user.getAdminSide() != null && user.getAdminSide().size() > 0){
				for(AdminSide adminSide : user.getAdminSide()){
					adminSide.setUserId(sysUser.getId());
				}
				adminSideService.saveList(user.getAdminSide());
			}
			departmentService.clearDeptAndUserCache();
		} catch (CustomException e){
			throw e;
		} catch (Exception e) {
			SystemException se = new SystemException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_SYS_002"));
			throw se;
		}
		return 1;
	}

	/**
	 * 修改方法
	 * @param User user 实体类
	 * @return Integer 修改的记录数量
	 * @author
	 * @version 2014-03-18
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer updateUser(User user) throws CustomException {
		//清空用户缓存
		CacheUtils.remove(CACHE_USER_INFO + user.getId());
		//清空部门缓存
//		User oldUser = UserUtils.getUser(user.getId());
//		if(!user.getDeptId().equals(oldUser.getDeptId())){
//			CacheUtils.remove(CACHE_DEPT_INFO + oldUser.getDeptId());
//		}
		try {
			//判断手机号是否存在
			if(StringUtils.isNotEmpty(user.getMobile())){
				User u = new User();
				u.setMobile(user.getMobile());
				if (userDao.checkMobile(user) != null) {
					throw new CustomException(MessageUtils.getMessage("JC_SYS_090"));
				}
			}
			
			SysUser sysUser = new SysUser();
			sysUser.setId(user.getId());
			UserUtils.user2SysUser(user, sysUser);
			sysUser.setSource(GlobalContext.USER_SOURCE_OA);
			sysUserService.update(sysUser);
			user.setModifyDateNew(DateUtils.getSysDate());
			propertyService.fillProperties(user,true);
			if(user.getStatus().equals(GlobalContext.USER_STATUS_2)){
				user.setLockType(1);
			}
			userDao.update(user);
			
			//先删除用户下其他部门数据
			sysOtherDeptsService.deleteOtherDept(user.getId());
			//保存其他部门
			if(user.getOtherDepts() != null && user.getOtherDepts().size() > 0){
				for(SysOtherDepts dept : user.getOtherDepts()){
					dept.setUserId(sysUser.getId());
				}
				sysOtherDeptsService.saveList(user.getOtherDepts());
			}
			//先删除用户管理角色数据
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setUserId(user.getId());
			sysUserRoleService.deleteSysUserRole(sysUserRole);
			//保存角色
			if(user.getSysUserRole() != null && user.getSysUserRole().size() > 0){
				for(SysUserRole role : user.getSysUserRole()){
					role.setUserId(sysUser.getId());
				}
				sysUserRoleService.saveList(user.getSysUserRole());
			}
			//保存管理员机构树
			AdminSide as = new AdminSide();
			as.setUserId(user.getId());
			adminSideService.deleteAdminSide(as);
			if(user.getIsAdmin() == 1 && user.getAdminSide() != null && user.getAdminSide().size() > 0){
				for(AdminSide adminSide : user.getAdminSide()){
					adminSide.setUserId(sysUser.getId());
				}
				adminSideService.saveList(user.getAdminSide());
			}
			departmentService.clearDeptAndUserCache();
		} catch (CustomException e){
			throw e;
		} catch (Exception e) {
			SystemException ce = new SystemException(e);
			ce.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
			throw ce;
		}
		return 1;
	}


	/**
	 * 初始化用户密码
	 * @param User user 实体类
	 * @return Integer 操作结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@Override
	public Integer initPassword(User user) throws CustomException{
		try{
			user.setPassword(SystemSecurityUtils.entryptPassword(GlobalContext.PASSWORD_DEFAULT_VALUE));
			return userDao.initPassword(user);
		} catch (Exception e) {
			SystemException ce = new SystemException(e);
			ce.setLogMsg(MessageUtils.getMessage("JC_SYS_091"));
			throw ce;
		}
	}

	/**
	 * 查询单条记录方法
	 * @param User user 实体类
	 * @return User 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@Override
	public User getUser(User user) throws CustomException {
		try{
			user = userDao.get(user);
			if(user != null){
				SysOtherDepts sysOtherDepts = new SysOtherDepts();
				sysOtherDepts.setUserId(user.getId());
				List<SysOtherDepts> deptList = sysOtherDeptsService.queryAll(sysOtherDepts);
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setUserId(user.getId());
				List<SysUserRole> roleList = sysUserRoleService.queryAll(sysUserRole);
				AdminSide adminSide = new AdminSide();
				adminSide.setUserId(user.getId());
				List<AdminSide> adminSideList = adminSideService.queryAll(adminSide);
				
				user.setOtherDepts(deptList);
				user.setSysUserRole(roleList);
				user.setAdminSide(adminSideList);
				return user;
			} else {
				return null;
			}
			
		}  catch (Exception e) {
			SystemException ce = new SystemException(e);
			ce.setLogMsg(MessageUtils.getMessage("JC_SYS_092"));
			throw ce;
		}
	}

	/**
	 * 删除用户
	 * @param User user 实体类
	 * @return Integer 操作结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer delete(User user) throws SystemException {
		//清空用户缓存
		CacheUtils.remove(CACHE_USER_INFO + user.getId());
		try{
			SysUser sysUser = new SysUser();
			sysUser.setPrimaryKeys(user.getPrimaryKeys());
			propertyService.fillProperties(sysUser,true);
			sysUserService.updateDeleteFlagByIds(sysUser);
			
			user.setStatus(GlobalContext.USER_STATUS_3);
			propertyService.fillProperties(user,true);
			userDao.delete(user);
			
			SysOtherDepts sysOtherDepts = new SysOtherDepts();
			sysOtherDepts.setPrimaryKeys(user.getPrimaryKeys());
			propertyService.fillProperties(sysOtherDepts,true);
			sysOtherDeptsService.updateDeleteFlagByIds(sysOtherDepts);
			
			SysUserRole role = new SysUserRole();
			role.setPrimaryKeys(user.getPrimaryKeys());
			propertyService.fillProperties(role,true);
			sysUserRoleService.delete(role, true);
			
			AdminSide adminSide = new AdminSide();
			adminSide.setPrimaryKeys(user.getPrimaryKeys());
			propertyService.fillProperties(adminSide,true);
			adminSideService.delete(adminSide, true);
			
			departmentService.clearDeptAndUserCache();
		} catch (Exception e) {
			SystemException ce = new SystemException(e);
			ce.setLogMsg(MessageUtils.getMessage("JC_SYS_006"));
			throw ce;
		}
		return 1;
	}
	
	/**
	 * 根据部门ID查询用户
	 * @param User user 实体类
	 * @return List<User> 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@Override
	public List<User> queryUserByDeptId(User user) {
		return userDao.queryUserByDeptId(user);
	}
	
	/**
	 * 分页查询用户不关联部门表
	 * @param User user 实体类
	 * @param PageManager page 分页实体类
	 * @return PageManager 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@Override
	public PageManager queryAllUsers(User user, PageManager page) {
		return userDao.queryByPage(user, page, SQL_QUERYALLUSERCOUNT, SQL_QUERYALLUSER);
	}
	
	/**
	 * 查询下级用户(下级的下级)不包括自己
	 * @param User user 实体类
	 * @return List<User> 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@Override
	public List<User> queryUserByLeader(User user) {
		try {
			List<User> resultList = new ArrayList<User>();
			addChildrenUser(user, resultList, 0);
			return listSorting(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 递归添加下级用户
	 * @param User user 实体类
	 * @param List<User> resultList 返回的结果集
	 * @param int forcount 最多循环的层级
	 * @return List<User> 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	private List<User> addChildrenUser(User user, List<User> resultList, int forcount){
		//领导设置错误返回空值 最多循环20层
		if(forcount >= 20){
			return new ArrayList<User>();
		} else {
			forcount ++;
		}
		User u = new User();
		u.setLeaderId(user.getId());
		List<User> userList = userDao.queryAll(u);
		for(User user_ : userList){
			resultList.add(user_);
			addChildrenUser(user_, resultList, forcount);
		}
		return resultList;
	}
	
	/**
	 * list排序 根据user.orderno
	 * @param List<User> list 排序list
	 * @return List<User> 排序后的结果集
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	private List<User> listSorting(List<User> list) throws Exception {
		Set<User> Hset = new HashSet<User>();
		for(int i=0;i<list.size();i++) {
			Hset.add(list.get(i));
		}
		
        List<User> userList = new ArrayList<User>();
        userList.addAll(Hset);
        
        Collections.sort(userList,new Comparator<User>(){
            public int compare(User arg0, User arg1) {
            	int v = arg0.getOrderNo().compareTo(arg1.getOrderNo());
            	if(v == 0){
            		return arg0.getId().compareTo(arg1.getId());
            	}
            	return v;
            }
        });
        return userList;
	}
	
	/**
	 * 根据ids查询用户
	 * @param String ids 根据逗号分隔的id
	 * @return List<User> 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@Override
	public List<User> queryUserByIds(String ids) {
		User user = new User();
		user.setPrimaryKeys(ids.split(","));
		return userDao.queryUserByIds(user);
	}
	
	/**
	 * 方法描述：判断目标用户是否是源用户的上级（或上级的上级....）
	 * @param sourceUser  源用户
	 * @param targetUser  目标用户
	 * @return  true-是  false-否
	 * @author 王世元
	 * @version  2014年6月25日上午8:37:56
	 * @see
	 */
	public boolean isLeader(User sourceUser,User targetUser){
		boolean isLeader = false;
		List<User> underList = queryUserByLeader(targetUser);

		for(User user :underList){
			if (user.getId().longValue() == sourceUser.getId().longValue()){
				isLeader = true;
				return isLeader;
			}
			
		}

		return isLeader;
	}
	
	/**
	 * 方法描述：判断目标用户是否是源用户的上级（或上级的上级....）
	 * @param sourceUserId 源用户ID
	 * @param targetUserId  目标用户ID
	 * @return true-是  false-否
	 * @author 王世元
	 * @version  2014年7月24日下午3:16:01
	 * @see
	 */
	public boolean isLeader(Long sourceUserId,Long targetUserId){
		boolean isLeader = false;
		User targetUser = new User();
		targetUser.setId(targetUserId);
		List<User> underList = queryUserByLeader(targetUser);

		for(User user :underList){
			if (user.getId().longValue() == sourceUserId.longValue()){
				isLeader = true;
				return isLeader;
			}
		}

		return isLeader;
	}
	
	/**
	 * 缓存查询单个用户
	 * @param User user 实体类
	 * @return User 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	public User getUserById(User user){
		User u = (User) CacheUtils.get(CACHE_USER_INFO + user.getId());
		if(u == null){
			u = userDao.get(user);
//			if(u != null){
//				Department department = new Department();
//				department.setId(u.getDeptId());
//				department = systemService.queryOrgIdAndName(department);
//				u.setOrgId(department.getOrgId());
//				u.setOrgName(department.getOrgName());
//			}
			CacheUtils.put(CACHE_USER_INFO + user.getId(), u);
		}
		return u;
	}
	
	/**
	 * 根据用户ID查询用户信息(包括部门、机构信息)
	 * @param Long id 用户ID
	 * @return User 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	public User getUser(Long id){
		User user = new User();
		user.setId(id);
		user = userDao.get(user);
		if(user != null){
			Department department = new Department();
			department.setId(user.getDeptId());
			department = systemService.queryOrgIdAndName(department);
			user.setOrgId(department.getOrgId());
			user.setOrgName(department.getOrgName());
		}
		
		return user;
	}
	
	/**
	 * 缓存查询所有用户
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@Override
	public void getAllUsers() {
		List<User> userList = userDao.queryAll(new User());
		for(User user : userList){
//			Department department = new Department();
//			department.setId(user.getDeptId());
//			department = systemService.queryOrgIdAndName(department);
//			user.setOrgId(department.getOrgId());
//			user.setOrgName(department.getOrgName());
			
			CacheUtils.put(CACHE_USER_INFO + user.getId(), user);
		}
	}
	
	/**
	 * 修改用户信息(修改条件不包括modifyDate)
	 * @return Integer 操作结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@Override
	public Integer update2(User user) {
		return userDao.update2(user);
	}
	
	/**
	 * 检查用户手机号码
	 * @param User user 用户实体
	 * @return User 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@Override
	public User checkMobile(User user) {
		return userDao.checkMobile(user);
	}
	
	/**
	 * 根据用户IDS查询用户机构树(结果排序)
	 * @param String ids 根据逗号分隔的用户ID
	 * @return List<Department> 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	public List<Department> queryUserTreeByIds(String ids) {
		List<Department> tree = new ArrayList<Department>();
		try{
			String idArray [] = ids.split(",");
			for(String userId : idArray){
				User user = new User();
				user.setId(Long.parseLong(userId));
				user = userDao.get(user);
				if(user == null){
					continue;
				}
				//组装人员节点
				Department d = new Department();
				d.setName(user.getDisplayName());
				d.setParentId(user.getDeptId());
				d.setId(user.getId());
				//人员设置type为0
				d.setType("0");
				d.setQueue(user.getOrderNo());
				tree.add(d);
				//组装树节点
				Department dept = new Department();
				dept.setId(user.getDeptId());
				dept = departmentService.get(dept);
				if(!isExist(dept, tree)){
					//部门设置type为1
					dept.setType("1");
					tree.add(dept);
					//递归添加父节点
					addParentDept(tree, dept);
				}
			}
			return listSorting2(tree);
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 递归添加上级部门信息
	 * @param List<Department> tree 返回的结果集
	 * @param Department dept 部门实体
	 * @return List<Department> 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	private List<Department> addParentDept(List<Department> tree, Department dept){
		try {
			if(dept.getParentId() != 0){
				Department d = new Department();
				d.setId(dept.getParentId());
				d = departmentService.get(d);
				if(!isExist(d, tree)){
					//部门设置type为1
					d.setType("1");
					tree.add(d);
					addParentDept(tree, d);
				}
			}
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return tree;
	}
	
	/**
	 * 判断集合里是否存在此部门
	 * @param Department d 要判断的部门
	 * @param List<Department> list 部门集合
	 * @return boolean true包含	false不包含
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	private boolean isExist(Department d, List<Department> list){
		for(int i=0;i<list.size();i++){
			Department department = (Department) list.get(i);
			if(department.getId().equals(d.getId())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 集合排序
	 * @param Department d 要判断的部门
	 * @param List<Department> list 部门集合
	 * @return List<Department> 排序后的结果集
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	private List<Department> listSorting2(List<Department> list) throws Exception {
		Set<Department> Hset = new HashSet<Department>();
		for(int i=0;i<list.size();i++) {
			Hset.add(list.get(i));
		}
        List<Department> userList = new ArrayList<Department>();
        userList.addAll(Hset);
        
        Collections.sort(userList,new Comparator<Department>(){
        	public int compare(Department arg0, Department arg1) {
        		int v = arg0.getQueue().compareTo(arg1.getQueue());
        		if(v == 0){
        			return arg0.getId().compareTo(arg1.getId());
        		}
        		return v;
            }
        });
        return userList;
	}
	
	/**
	 * 根据用户ID查询机构下级(下下级)的下属
	 * @param User user 必须参数deptid、id
	 * @return List<User> 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@Override
	public List<User> queryUserByLeaderAndDeptId(User user) {
		try {
			String deptIds = "";
			List<Department> deptList = departmentService.getDeptAndUserByDeptId(user.getDeptId());
			for(int i=0;i<deptList.size();i++){
				Department dept = deptList.get(i);
				deptIds += dept.getId();
				if(i != deptList.size()-1){
					deptIds += ",";
				}
			}
			if(deptIds.length() > 0){
				User u = new User();
				u.setLeaderId(user.getId());
				u.setDeptIds(deptIds);
				return userDao.queryAll(u);
			}
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 修改用户信息
	 * @param User user
	 * @return Integer 操作结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@Override
	public Integer updateUserInfo(User user) throws CustomException {
		//清空用户缓存
		CacheUtils.remove(CACHE_USER_INFO + user.getId());
		user.setModifyDateNew(DateUtils.getSysDate());
		return userDao.updateUserInfo(user);
	}
	
	/**
	 * 移动端获取用户信息
	 * @param Long id 用户ID
	 * @return User4M 用户对象
	 * @throws Exception
	 * @author
	 * @version 2014-09-10
	 */
	@Override
	public User4M getUser4M(Long id) {
		User user = new User();
		user.setId(id);
		user = userDao.get(user);
		if(user != null){
			Department department = new Department();
			department.setId(user.getDeptId());
			department = systemService.queryOrgIdAndName(department);
			user.setOrgId(department.getOrgId());
			user.setOrgName(department.getOrgName());
		}
		User4M user4m = new User4M();
		UserUtils.user2User4M(user, user4m);
		return user4m;
	}
	
	/**
	 * 移动端获取所有用户
	 * @param Long id 用户ID
	 * @return User4M 用户对象
	 * @throws Exception
	 * @author
	 * @version 2014-09-10
	 */
	@Override
	public List<User4M> queryAll4M(User4M user4m) {
		return userDao.queryAll4M(user4m);
	}
	
	@Override
	public PageManager queryAllUsersForOrder(User user, PageManager page) {
		return userDao.queryByPage(user, page, SQL_QUERYALLUSERCOUNTFORORDER, SQL_QUERYALLUSERFORORDER);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer updateUserOrder(String userId, String orderNo,
			String oUserId, String oOrderNo) {
		try{
			User user = new User();
			user.setId(Long.parseLong(userId));
			user.setOrderNo(Integer.parseInt(oOrderNo));
			userDao.update2(user);
			
			user.setId(Long.parseLong(oUserId));
			user.setOrderNo(Integer.parseInt(orderNo));
			userDao.update2(user);
		} catch (Exception e){
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
}