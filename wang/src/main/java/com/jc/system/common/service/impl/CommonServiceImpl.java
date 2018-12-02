package com.jc.system.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.CustomException;
import com.jc.system.common.dao.ICommonDao;
import com.jc.system.common.service.ICommonService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.beans.UserBean;
import com.jc.system.security.domain.AdminSide;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.Role;
import com.jc.system.security.domain.RoleBlocks;
import com.jc.system.security.domain.SysUserRole;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IAdminSideService;
import com.jc.system.security.service.IDepartmentService;
import com.jc.system.security.service.IRoleService;
import com.jc.system.security.service.ISystemService;
import com.jc.system.security.service.IUserService;

/**
 * @title GOA V2.0
 * @description 
 * @author 高研
 * @version  2014年4月17日
 */
@Service
public class CommonServiceImpl extends BaseServiceImpl<BaseBean> implements ICommonService {
	
	@Autowired
	private IDepartmentService departmentService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IAdminSideService adminSideService;
	
	@Autowired
	private ISystemService systemService;
	
	@Autowired
	private IRoleService roleService;
	
	public CommonServiceImpl() {
	}
	
	@Resource
	private ICommonDao commonDao;

	@Override
	public String getDBSysdate() throws CustomException {
		return commonDao.getDBSysdate();
	}
	
	/**
	  * @description 获得部门树和人员数据
	  * @author 孙圣然
	  * @version 1.0 下午3:37:25
	 * @throws Exception 
	*/
	@SuppressWarnings("unchecked")
	public List<Department> getDeptsAndUser() throws Exception{
		List<Department> depts = queryManagerDeptRree();
		
		//放入到map中，以便于人员对应
		Map<Long,Department> map = new HashMap<Long, Department>();
		for(Department dept:depts){
			map.put(dept.getId(), dept);
		}

		User userQuery = new User();
		userQuery.setDeleteFlag(0);
		List<User> users = userService.queryAll(userQuery);

		for(User user:users){
			Department dept = map.get(user.getDeptId());
			if(dept!=null){
				dept.addUser(user);
			}
		}
		List<Department> result = new ArrayList<Department>();
		result.addAll(map.values());
		return result;
	}
	
	/**
	  * @description 用户管理角色列表
	  * @param response
	  * @throws Exception
	  * @author 高研
	  * @version 1.0 2014年5月23日 下午1:50:32
	 * @throws CustomException 
	*/
	@Override
	public List<Role> getRolesForUser() throws CustomException {
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			//超级管理员
			if(userInfo.getIsSystemAdmin()){
				return roleService.getRolesForUser(new Role());
			} 
			//管理员
			else if(userInfo.getIsAdmin() == 1){
				try {
					AdminSide adminSide = new AdminSide();
					adminSide.setUserId(userInfo.getId());
					List<AdminSide> asList = adminSideService.queryAll(adminSide);
					List<Department> deptList = new ArrayList<Department>();
					String roleIds = "";
					for(AdminSide as : asList){
						if(as.getIsChecked().equals("1")){
							roleIds = roleIds + as.getDeptId() + ",";
							getDeptsForRole(as.getDeptId(), deptList);
						}
					}
					for(int i=0;i<deptList.size();i++){
						Department d = deptList.get(i);
						roleIds = roleIds + d.getId();
						if(i != deptList.size()-1)
							roleIds = roleIds + ",";
					}
					if(roleIds.length() >0){
						if(roleIds.endsWith(",")){
							roleIds = roleIds.substring(0,roleIds.length()-1);
						}
						Role role = new Role();
						role.setDeptIds(roleIds);
						return roleService.getRolesForUser(role);
					}
					
				} catch (CustomException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public List<Department> getDeptsForRole(Long id, List<Department> deptList){
		try {
			//查询所有部门
			Department dept_ = new Department();
			dept_.setDeleteFlag(0);
			List<Department> allDeptList = departmentService.queryAll(dept_);
			
			Department dept = new Department();
			dept.setDeleteFlag(0);
			dept.setParentId(id);
			List<Department> list = departmentService.queryAll(dept);
			for(Department d : list){
				if(!isExist(d, deptList)){
					d.setIsChecked("1");
					deptList.add(d);
					getDepts(d.getId(), deptList, 4, allDeptList);
				}
			}
			
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return deptList;
	}

	/**
	  * @description 查询管理员机构树
	  * @author 高研
	  * @version 1.0 下午3:37:25
	*/
	@SuppressWarnings("rawtypes")
	@Override
	public List queryManagerDeptRree() {
		try {
			User userInfo = SystemSecurityUtils.getUser();
			if(userInfo != null){
				//超级管理员
				if(userInfo.getIsSystemAdmin()){
					Department dept = new Department();
					dept.setDeleteFlag(0);
					return departmentService.queryAll(dept);
				}
				//管理员
				else if(userInfo.getIsAdmin() == 1){
					//查询所有部门
					Department dept_ = new Department();
					dept_.setDeleteFlag(0);
					List<Department> allDeptList = departmentService.queryAll(dept_);
					//查询在管理员机构树中选择的节点
					AdminSide adminSide = new AdminSide();
					adminSide.setUserId(userInfo.getId());
					List<AdminSide> asList = adminSideService.queryAll(adminSide);
					//返回结果LIST
					List<Department> deptList = new ArrayList<Department>();
					//循环添加机构
					for(AdminSide as : asList){
						//添加自己
						Department d = new Department();
						d.setId(as.getDeptId());
						if(!isExist(d, deptList)){
							d = departmentService.get(d);
							d.setIsChecked(as.getIsChecked());
							d.setUserType(2);
							deptList.add(d);
							//递归添加部门	判断有权限的
							if(as.getIsChecked().equals("1")){
								getDepts(as.getDeptId(), deptList, 2, allDeptList);
							}
						}
					}
					return deptList;
				}
				//普通用户
				else{
					Long roleId [] = new Long[userInfo.getSysUserRole().size()];
					for(int i=0;i<userInfo.getSysUserRole().size();i++){
						SysUserRole userRole = userInfo.getSysUserRole().get(i);
						roleId[i] = userRole.getRoleId();
					}
					if(roleId.length > 0){
						//查询角色关联的机构
						List<RoleBlocks> orgList = roleService.getAllDeptWithRoles(roleId);
						//角色没有关联机构 默认显示本部门
						if(orgList == null || orgList.size() == 0){
							orgList = roleService.getDeptRelation(SystemSecurityUtils.getUser().getDeptId());
						}
						//查询所有部门
						Department dept_ = new Department();
						dept_.setDeleteFlag(0);
						List<Department> allDeptList = departmentService.queryAll(dept_);
						
						//返回结果LIST
						List<Department> deptList = new ArrayList<Department>();
						for(RoleBlocks roleBlock : orgList){
							//添加自己
							Department d = new Department();
							d.setId(roleBlock.getDeptId());
							d = departmentService.get(d);
							if(!isExist(d, deptList)){
								d.setIsChecked(roleBlock.getIsChecked());
								d.setUserType(3);
								deptList.add(d);
								//判断选择的机构
								if(roleBlock.getIsChecked().equals("1")){
									//递归添加部门
									getDepts(roleBlock.getDeptId(), deptList, 3, allDeptList);
								} 
							}
						}
						return deptList;
					}
				}
			}
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	  * @description 递归查询管理员机构树
	  * @author 高研
	  * @version 1.0 下午3:37:25
	*/
	public List<Department> getDepts(Long id, List<Department> deptList, int userType, List<Department> allDeptList){
		Department dept = new Department();
		//dept.setDeleteFlag(0);
		dept.setParentId(id);
		dept.setDeptType(0);
		List<Department> list = queryDept(dept, allDeptList);
		//List<Department> list = departmentService.queryAll(dept);
		for(Department d : list){
			if(!isExist(d, deptList)){
				d.setIsChecked("1");
				d.setUserType(userType);
				deptList.add(d);
				getDepts(d.getId(), deptList, userType, allDeptList);
			}
		}
		return deptList;
	}
	
	//在部门列表中查找符合条件的部门
	public List<Department> queryDept(Department dept, List<Department> allDeptList){
		List<Department> result = new ArrayList<Department>();
		for(Department d : allDeptList){
			if(d.getParentId().equals(dept.getParentId()) && d.getDeptType() == dept.getDeptType()){
				result.add(d);
			}
		}
		return result;
	}
	
	//判断部门是否在部门列表中存在
	public boolean isExist(Department d, List<Department> list){
		for(Department department : list){
			if(department.getId().equals(d.getId())){
				return true;
			}
		}
		return false;
	}
	
	/**
	  * @description 查询机构树(用户、机构、流程在用)
	  * @author 高研
	  * @version 1.0 下午3:37:25
	*/
	@SuppressWarnings("rawtypes")
	public List queryManagerOrgRree(){
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			//超级管理员
			if(userInfo.getIsSystemAdmin()){
				try {
					Department department = new Department();
					department.setDeleteFlag(0);
					department.setDeptType(1);
					return departmentService.queryOrgTree(department);
				} catch (CustomException e) {
					e.printStackTrace();
				}
			}
			//管理员
			else if(userInfo.getIsAdmin() == 1){
				AdminSide adminSide = new AdminSide();
				adminSide.setUserId(userInfo.getId());
				return adminSideService.queryManagerDeptRree(adminSide);
			}
		}
		return null;
	}
	
	/**
	  * @description 根据部门ID获取本部门及所有子部门加人员信息
	  * @param id
	  * @return	
	  * @throws Exception
	  * @author 张继伟
	  * @version 1.0 2014年5月23日 上午11:30:58
	*/
	public List<Department> getDeptAndUserByDeptId(Long id) throws Exception {
		List<Department> newList = new ArrayList<Department>();
		List<Department> list = departmentService.getDeptAndUserByDeptId(id);
		User user = new User();
		user.setDeleteFlag(0);
		List<User> uList = userService.queryAll(user);
		if(list != null && list.size() > 0){
			if(uList != null && uList.size() >0){
				for(int i=0;i<list.size();i++){
					Department deptTmp = list.get(i);
					for(int j=0;j<uList.size();j++){
						User uTmp = uList.get(j);
						if(deptTmp.getId().equals(uTmp.getDeptId())){
							deptTmp.addUser(uTmp);
						}
					}
					newList.add(deptTmp);
				}
			}
		}
		return newList;
	}
	
	/**
	 * 根据机构ID获取所有领导信息
	 * @param user
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年8月18日 上午9:33:01
	 */
	public List<User> getLeaderUserByDeptId(User user) throws Exception {
		List<User> leaderList = new ArrayList<User>();
		List<Department> list = departmentService.getDeptAndUserByDeptId(user.getOrgId());
		List<Long> tmpDeptId = new ArrayList<Long>();
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Department deptTmp = list.get(i);
				if(deptTmp.getDeptType() == 1 && deptTmp.getParentId() != 0){
					driverByOrgIdRecursive(deptTmp.getId(), list, tmpDeptId);
					if(!user.getOrgId().equals(deptTmp.getId()))
						tmpDeptId.add(deptTmp.getId());
				}
			}
			deleteRepeat(tmpDeptId);
			if(tmpDeptId != null && tmpDeptId.size() > 0){
				for(int j=0;j<tmpDeptId.size();j++){
					Long id = tmpDeptId.get(j);
					for(int i=list.size()-1;i>=0;i--){
						Department deptTmp = list.get(i);
						if(id.equals(deptTmp.getParentId())){
							list.remove(i);
						}
					}
				}
			}
		}else{
			return null;
		}
		User tmp = new User();
		user.setDeleteFlag(0);
		List<User> uList = userService.queryAll(tmp);
		if(uList != null && uList.size() >0){
			for(int i=0;i<list.size();i++){
				Department deptTmp = list.get(i);
				for(int j=0;j<uList.size();j++){
					User uTmp = uList.get(j);
					if(deptTmp.getId().equals(uTmp.getDeptId())){
						if(uTmp.getIsLeader().equals(1)){
							if(uTmp.getOpenCale().equals("1")){
								leaderList.add(uTmp);
							}
						}
						
					}
				}
			}
		}
		return leaderList;
	}
	
	/**
	 * 根据部门ID获取本部门及所有子部门司机信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年7月23日 上午11:41:53
	 */
	public List<User> getDriverByDeptId(Long id) throws Exception {
		List<Department> newList = new ArrayList<Department>();
		List<User> userList = new ArrayList<User>();
		List<Department> list = departmentService.getDeptAndUserByDeptId(id);
		User user = new User();
		user.setDeleteFlag(0);
		user.setIsDriver(1);
		List<User> uList = userService.queryAll(user);
		if(list != null && list.size() > 0){
			if(uList != null && uList.size() >0){
				for(int i=0;i<list.size();i++){
					Department deptTmp = list.get(i);
					for(int j=0;j<uList.size();j++){
						User uTmp = uList.get(j);
						if(deptTmp.getId().equals(uTmp.getDeptId())){
							deptTmp.addUser(uTmp);
						}
					}
					newList.add(deptTmp);
				}
			}
		}
		for(Department d : newList){
			if(d.getUsers() != null && d.getUsers().size() > 0){
				for(User u : d.getUsers())
					userList.add(u);
			}
		}
		return userList;
	}
	
	/**
	 * 根据登录人所以在机构ID获取本机构所有子部门司机信息
	 * @param orgId
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年8月26日 上午8:58:14
	 */
	public List<User> getDriverByOrgId(Long orgId) throws Exception {
		List<Department> deptList = departmentService.getDeptByDeptId(orgId);
		if(deptList != null && deptList.size() > 0){
			List<Department> newList = new ArrayList<Department>();
			List<User> userList = new ArrayList<User>();
			User user = new User();
			user.setDeleteFlag(0);
			user.setIsDriver(1);
			List<User> uList = userService.queryAll(user);
			if(uList != null && uList.size() >0){
				for(int i=0;i<deptList.size();i++){
					Department deptTmp = deptList.get(i);
					for(int j=0;j<uList.size();j++){
						User uTmp = uList.get(j);
						if(deptTmp.getId().equals(uTmp.getDeptId())){
							deptTmp.addUser(uTmp);
						}
					}
					newList.add(deptTmp);
				}
			}else{
				return null;
			}
			for(Department d : newList){
				if(d.getUsers() != null && d.getUsers().size() > 0){
					for(User u : d.getUsers())
						userList.add(u);
				}
			}
			return userList;
		}else{
			return null;
		}
	}
	
	/**
	  * @description 获取在线用户登录名
	  * @return	
	  * @author 张继伟
	  * @version 1.0 2014年6月13日 上午08:30:58
	*/
	public List<UserBean> getOnlineUserBean() {
		return SystemSecurityUtils.getOnlineUserBean();
	}
	
	/**
	 * 根据用户ID获取用户信息
	 * @param id
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年6月24日 下午1:55:07
	 */
	public User getUserById(Long id){
		return userService.getUser(id);
	}

	@Override
	public int getOnlineUserCount() {
		return SystemSecurityUtils.getOnlineCount();
	}
	
	/**
	 * 递归本机构所有子部门(不包括子机构及以下部门)
	 * @param id	组织ID
	 * @param list	所有组织
	 * @param tmpDeptId	保存组织ID
	 * @author 张继伟
	 * @version 1.0 2014年9月10日 下午4:28:16
	 */
	private void driverByOrgIdRecursive(Long id, List<Department> list, List<Long> tmpDeptId){
		for(int i=0;i<list.size();i++){
			Department department = list.get(i);
			if(id==department.getParentId()){
				Long deptId = department.getId();
				tmpDeptId.add(deptId);
				driverByOrgIdRecursive(deptId, list, tmpDeptId);
			}
		}
	}
	
	/**
	 * 去掉List中的重复数据
	 * @param list
	 * @author 张继伟
	 * @version 1.0 2014年9月11日 上午11:46:20
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	private void deleteRepeat(List list) { 
		HashSet h = new HashSet(list); 
	    list.clear(); 
	    list.addAll(h); 
	}
	
	/**
	 * 根据登录人所以在机构ID获取本机构所有子部门信息
	 * @param deptId
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年9月29日 上午11:37:02
	 */
	public List<Department> getDeptByDeptId(Long deptId) throws Exception {
		List<Department> deptList = departmentService.getDeptByDeptId(deptId);
		return deptList != null && deptList.size() > 0 ? deptList : null;
	}
	
	/**
	 * 根据机构ID获取本机构所有子部门人员信息
	 * @param orgId
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年8月26日 上午8:58:14
	 */
	public List<User> getUsersByOrgId(Long orgId) throws Exception {
		Department dp = new Department();
		dp.setId(orgId);
		List<Department> deptList = departmentService.queryAllByDeptId(dp);
	//	List<Department> deptList = departmentService.getDeptByDeptId(orgId);
		if(deptList != null && deptList.size() > 0){
			List<Department> newList = new ArrayList<Department>();
			List<User> userList = new ArrayList<User>();
			User user = new User();
			user.setDeleteFlag(0);
			List<User> uList = userService.queryAll(user);
			if(uList != null && uList.size() >0){
				for(int i=0;i<deptList.size();i++){
					Department deptTmp = deptList.get(i);
					for(int j=0;j<uList.size();j++){
						User uTmp = uList.get(j);
						if(deptTmp.getId().equals(uTmp.getDeptId())){
							deptTmp.addUser(uTmp);
						}
					}
					newList.add(deptTmp);
				}
			}else{
				return null;
			}
			for(Department d : newList){
				if(d.getUsers() != null && d.getUsers().size() > 0){
					for(User u : d.getUsers())
						userList.add(u);
				}
			}
			return userList;
		}else{
			return null;
		}
	}
	
}
