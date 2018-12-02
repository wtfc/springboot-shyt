package com.jc.system.security.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/*import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;*/
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.CustomException;
import com.jc.system.common.util.CacheUtils;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.service.IDicService;
import com.jc.system.group.domain.Group;
import com.jc.system.group.service.IGroupService;
import com.jc.system.security.SystemAuthorizingRealm.Principal;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.beans.User4MBean;
import com.jc.system.security.beans.UserBean;
import com.jc.system.security.dao.IDepartmentDao;
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
import com.jc.system.security.util.UserUtils;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-18
 * 
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements IDepartmentService {

	private IDepartmentDao departmentDao;
	
	@Autowired
	private IUserService userService;				//人员服务
	@Autowired
	private IAdminSideService adminSideService;		//关联表服务
	@Autowired
	private IDicService dicService;					//字典服务
	@Autowired
	private IRoleService roleService;				//角色服务
	@Autowired
	private IGroupService groupService;				//组别服务
	@Autowired
	private ISystemService systemService;
	
	
	public final String CACHE_DEPT_INFO = "dept_info_";
	
	@Autowired
	public DepartmentServiceImpl(IDepartmentDao departmentDao) {
		super(departmentDao);
		this.departmentDao=departmentDao;
	}

	public DepartmentServiceImpl(){
		
	}

	/**
	 * 分页查询方法
	 * @param Department department 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @author
	 * @version 2014-03-18
	 */
	public PageManager query(Department department, PageManager page) {
		return departmentDao.queryByPage(department, page);
	}

	/**
	 * 根据主键删除多条记录方法
	 * @param Department department 实体类
	 * @return Integer 处理结果
	 * @author
	 * @version 2014-03-18
	 */
	@Transactional(rollbackFor = Exception.class)
	//@TriggersRemove(cacheName = "departmentCache", when = When.AFTER_METHOD_INVOCATION, removeAll = true) 
	public Integer deleteByIds(Department department) throws CustomException {
		Integer result = -1;
		try {
			result = departmentDao.delete(department);
			if(result != -1){
				//清空组织缓存
				CacheUtils.remove(CACHE_DEPT_INFO + department.getId());
			}
		} catch (Exception e) {
			CustomException ce = new CustomException(e);
			ce.setBean(department);
			throw ce;
		}
		return result;
	}

	/**
	 * 保存方法
	 * @param Department department 实体类
	 * @return Integer 增加的记录数
	 * @author
	 * @version 2014-03-18
	 */
	@Transactional(rollbackFor = Exception.class)
	//@TriggersRemove(cacheName = "departmentCache", when = When.AFTER_METHOD_INVOCATION, removeAll = true) 
	public Integer save(Department department) throws CustomException {
		Integer result = -1;
		try {
			result = departmentDao.save(department);
		} catch (Exception e) {
			CustomException ce = new CustomException(e);
			ce.setBean(department);
			throw ce;
		}
		return result;
	}

	/**
	 * 修改方法
	 * @param Department department 实体类
	 * @return Integer 修改的记录数量
	 * @author
	 * @version 2014-03-18
	 */
	@Transactional(rollbackFor = Exception.class)
	//@TriggersRemove(cacheName = "departmentCache", when = When.AFTER_METHOD_INVOCATION, removeAll = true) 
	public Integer update(Department department) throws CustomException {
		Integer result = -1;
		try {
			result = departmentDao.update(department);
			if(result != -1){
				//清空组织缓存
				CacheUtils.remove(CACHE_DEPT_INFO + department.getId());
			}
		} catch (Exception e) {
			CustomException ce = new CustomException(e);
			ce.setBean(department);
			throw ce;
		}
		return result;
	}

	/**
	 * 查询单条记录方法
	 * @param Department department 实体类
	 * @return Integer 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	public Department get(Department department) throws CustomException {
		return departmentDao.get(department);
	}

	/**
	 * 查询方法
	 * @param Department department 实体类
	 * @return PagingBean 查询结果
	 * @author
	 * @version 2014-03-18
	 */
	public List<Department> query(Department department) {
		return departmentDao.queryAll(department);
	}

	/**
	 * 查询分页查询方法
	 * @param Department department 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @author
	 * @version 2014-03-18
	 */
	public PageManager searchQuery(Department department, PageManager page) {
		return departmentDao.searchQuery(department, page);
	}

	/**
	 * 查询部门树(带部门负责人)
	 * @param Department
	 * @author
	 * @version 2014-04-15
	 */
	public List<Department> queryTree(Department department) {
		return departmentDao.queryTree(department);
	}
	
	/**
	 * 查询部门信息(带上级部门)
	 * @param Department
	 * @author
	 * @version 2014-04-15
	 */
	public Department queryOne(Department department) {
		return departmentDao.queryOne(department);
	}

	/**
	 * 根据部门ID查询所有节点
	 * @param department
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年4月22日 上午11:53:38
	 */
	public List<Department> queryAllByDeptId(Department department) {
		return departmentDao.queryAllByDeptId(department);
	}

	/**
	 * 根据部门Id集合删除
	 * @param department
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年4月22日 下午2:00:10
	 */
	@Transactional(rollbackFor = Exception.class)
	//@TriggersRemove(cacheName = "departmentCache", when = When.AFTER_METHOD_INVOCATION, removeAll = true) 
	public Integer updateByDeptIds(Department department) {
		return departmentDao.updateByDeptIds(department);
	}

	/**
	 * 根据部门ID获取本部门及所有子部门加人员信息
	 * @param id
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年5月22日 下午4:12:39
	 */
	public List<Department> getDeptAndUserByDeptId(Long id) throws CustomException {
		return departmentDao.getDeptAndUserByDeptId(id);
	}

	/**
	 * 逻辑删除部门-[删除关联关系adminSide]
	 * @param department
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年5月23日 下午1:57:59
	 */
	//@TriggersRemove(cacheName = "departmentCache", when = When.AFTER_METHOD_INVOCATION, removeAll = true) 
	public Map<String, Object> logicDeleteById(Department department) throws CustomException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//判断是否有人员存在
		User userInfo = new User();
		userInfo.setDeptId(department.getId());
		List<User> userList =  userService.queryUserByDeptId(userInfo);
		if(userList != null && userList.size() > 0){
			resultMap.put("labelErrorMessage", MessageUtils.getMessage("JC_SYS_115"));
			return resultMap;
		}
		List<Department> dList = getDeptAndUserByDeptId(department.getId());
		//判断是否有角色存在
		String deptIds = "";
		if(dList != null && dList.size() > 0){
			for(int i=0;i<dList.size();i++){
				deptIds += dList.get(i).getId() + ",";
			}
			deptIds = deptIds.substring(0, deptIds.length()-1);
		}
		if(deptIds.length() > 0){
			Role role = new Role();
			role.setDeptIds(deptIds);
			List<Role> roleList = roleService.queryAll(role);
			if(roleList != null && roleList.size() > 0){
				resultMap.put("labelErrorMessage", MessageUtils.getMessage("JC_SYS_114"));
				return resultMap;
			}
		}
		//判断是否有机构管理员关系存在
		String[] depts = null;
		if(dList != null && dList.size() > 0){
			depts = new String[dList.size()];
			for(int i=0;i<dList.size();i++){
				depts[i] = dList.get(i).getId().toString();
			}
		}
		if(depts != null){
			if(adminSideService.queryAdminSideIdByDeptIds(depts)){
				resultMap.put("labelErrorMessage", MessageUtils.getMessage("JC_SYS_113"));
				return resultMap;
			}
		}
		
		List<Department> deptList = departmentDao.queryAllByDeptId(department);
		if(deptList != null && deptList.size() > 0){
			String[] primaryKeys = new String[deptList.size()];
			for(int i=0;i<deptList.size();i++){
				primaryKeys[i] = deptList.get(i).getId().toString();
			}
			department.setModifyUser(SystemSecurityUtils.getUser().getModifyUser());
			department.setModifyDate(DateUtils.getSysDate());
			department.setDeleteFlag(1);
			department.setPrimaryKeys(primaryKeys);
		} else {
			//已经被删除
			resultMap.put("success", "true");
			return resultMap;
		}
		
		if(departmentDao.updateByDeptIds(department) >= 1){
			//删除关联关系
			/*AdminSide aSide = new AdminSide();
			aSide.setDeptId(department.getId());
			List<AdminSide> adminSideList = adminSideService.queryAdminSideIdByDeptId(aSide);
			if(adminSideList != null && adminSideList.size() > 0){
				String[] primaryKeys = new String[adminSideList.size()];
				for(int i=0;i<adminSideList.size();i++){
					primaryKeys[i] = adminSideList.get(i).getId().toString();
				}
				aSide = new AdminSide();
				aSide.setPrimaryKeys(primaryKeys);
				adminSideService.delete(aSide);
			}*/
			resultMap.put("success", "true");
		}else{
			resultMap.put("success", "false");
		}
		return resultMap;
	}

	/**
	 * 分页查询方法[用户查询]
	 * @param User  user 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	public PageManager userManageList(User user, PageManager page) throws CustomException {
		if(user.getDeptIds() == null)
			user.setDeptId(1L);
		//return userService.queryForPermission(user, page);
		return userService.query(user, page);
	}

	/**
	 * 用户查询
	 * @return List<User>
	 * @author 张继伟
	 * @version 1.0 2014年4月28日 下午4:41:45
	 */
	public List<User> searchUserList() throws CustomException {
		List<User> users = new ArrayList<User>();
		try {
			List<Department> list = getDepartmentByUserAndRole();
			User user = new User();
			user.addOrderByField("t.ORDER_NO");
			List<User> uList = userService.queryAll(user);
			if (list != null && list.size() > 0) {
				if (uList != null && uList.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						Department deptTmp = list.get(i);
						for (int j = 0; j < uList.size(); j++) {
							User uTmp = uList.get(j);
							if (uTmp.getDeptId().equals(deptTmp.getId())) {
								users.add(uTmp);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	/**
	 * 获取全部部门及人员--selectControlNew组织人员使用
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年5月15日 上午8:27:24
	 */
	//@Cacheable(cacheName="departmentCache") 
	public String getAllDeptAndUser() throws Exception {
		List<Department> newList = new ArrayList<Department>();
		List<Department> list = this.getDeptByUserAndRoleForSelect();//getDepartmentByUserAndRole();
		User user = new User();
		user.setDeleteFlag(0);
		user.addOrderByField("t.ORDER_NO");
		List<User> uList = userService.queryAll(user);
		if (list != null && list.size() > 0) {
			if (uList != null && uList.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Department deptTmp = list.get(i);
					for (int j = 0; j < uList.size(); j++) {
						User uTmp = uList.get(j);
						if (deptTmp.getId().equals(uTmp.getDeptId())) {
							UserBean uBean = new UserBean();
							uBean.setId(uTmp.getId());
							uBean.setDeptId(uTmp.getDeptId());
							uBean.setDisplayName(uTmp.getDisplayName());
							uBean.setOrderNo(uTmp.getOrderNo());
							deptTmp.addUserBean(uBean);
							//deptTmp.addUser(uTmp);
						}
					}
					newList.add(deptTmp);
				}
			}
		}
		//User userInfo = SystemSecurityUtils.getUser();
		JSONArray array = new JSONArray();
		Department d = this.getDeptRoot();
		for (Department department : newList) {
			//if(userInfo.getIsSystemAdmin() || userInfo.getIsAdmin() == 1){
				if (department.getParentId().equals(d.getParentId())) {
					JSONObject obj = new JSONObject();
					obj.put("id", department.getId());
					obj.put("name", department.getName());
					obj.put("deptType", department.getDeptType());
					obj.put("parentId", department.getParentId());
					obj.put("user", department.getUserBeanList());
					//obj.put("user", department.getUsers());
					recur(newList, department.getId(), obj);
					array.add(obj);
				}
			/*}else{
				JSONObject obj = new JSONObject();
				obj.put("id", department.getId());
				obj.put("name", department.getName());
				obj.put("deptType", department.getDeptType());
				obj.put("parentId", department.getParentId());
				obj.put("user", department.getUsers());
				recur(newList, department.getId(), obj);
				array.add(obj);
			}*/
		}
		return array.toString();
	}
	
	/**
	 * 获取在线用户信息
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年6月16日 上午9:54:01
	 */
	public String getDeptAndUserByOnLine() throws Exception { 
		List<Department> list = getDepartmentByOrg();//getDepartmentByUserAndRole();
		List<Principal> uList = SystemSecurityUtils.getOnlineUsers();
		List<User> userList = new ArrayList<User>();
		if (uList != null && uList.size() > 0) {
			if (list != null && list.size() > 0) {
				for (int j = 0; j < uList.size(); j++) {
					User uTmp = UserUtils.getUser(uList.get(j).getId());
					for (int i = 0; i < list.size(); i++) {
						Department deptTmp = list.get(i);
						if (deptTmp.getId().equals(uTmp.getDeptId())) {
							if(!isExistUser(uTmp, userList)){
								//deptTmp.addUser(uTmp);
								UserBean uBean = new UserBean();
								uBean.setId(uTmp.getId());
								uBean.setDeptId(uTmp.getDeptId());
								uBean.setDisplayName(uTmp.getDisplayName());
								uBean.setOrderNo(uTmp.getOrderNo());
								deptTmp.addUserBean(uBean);
								userList.add(uTmp);
							}
						}
					}
				}
			}
		}
		//User userInfo = SystemSecurityUtils.getUser();
		JSONArray array = new JSONArray();
		Department d = this.getDeptRoot();
		for (Department department : list) {
			//if(userInfo.getIsSystemAdmin() || userInfo.getIsAdmin() == 1){
				//修改 在线人员中不显示当前登录人
				//if (department.getParentId().equals(d.getParentId())) {
				if (department.getId().equals(d.getId())) {
					JSONObject obj = new JSONObject();
					obj.put("id", department.getId());
					obj.put("name", department.getName());
					obj.put("deptType", department.getDeptType());
					obj.put("parentId", department.getParentId());
					obj.put("user", department.getUserBeanList());
					//obj.put("user", department.getUsers());
					recur(list, department.getId(), obj);
					array.add(obj);
				}
			/*}else{
				JSONObject obj = new JSONObject();
				obj.put("id", department.getId());
				obj.put("name", department.getName());
				obj.put("deptType", department.getDeptType());
				obj.put("parentId", department.getParentId());
				obj.put("user", department.getUserBeanList());
				//obj.put("user", department.getUsers());
				recur(list, department.getId(), obj);
				array.add(obj);
			}*/
		}
		return array.toString();
	}
	
	/**
	 * 私有方法-递归添加下级菜单
	 * @param departMentList 部门集合
	 * @param id 本身ID
	 * @param obj json对象
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年5月19日 上午10:27:22
	 */
	private JSONObject recur(List<Department> departMentList, Long id, JSONObject obj) {
		JSONArray array = new JSONArray();
		for (int i = 0; i < departMentList.size(); i++) {
			if (departMentList.get(i).getParentId().equals(id)) {
				JSONObject sonobj = new JSONObject();
				sonobj.put("id", departMentList.get(i).getId());
				sonobj.put("name", departMentList.get(i).getName());
				sonobj.put("deptType", departMentList.get(i).getDeptType());
				sonobj.put("parentId", departMentList.get(i).getParentId());
				sonobj.put("user", departMentList.get(i).getUserBeanList());
				//sonobj.put("user", departMentList.get(i).getUsers());
				recur(departMentList, departMentList.get(i).getId(), sonobj);
				array.add(sonobj);
			}
		}
		if (!array.isEmpty()) {
			obj.put("subDept", array);
		}
		return obj;
	}

	/**
	 * 获取职务人员
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年6月17日 上午11:22:10
	 */
	public String getPostAndUser() throws Exception {
		List<Department> newList = new ArrayList<Department>();
		List<Department> dList = new ArrayList<Department>();
		Dic dic = new Dic();
		dic.setParentId("dutyId");
		dic.setUseFlag(1);
		List<Dic> dicList = dicService.getDicByDuty(dic);
		User user = new User();
		user.addOrderByField("t.ORDER_NO");
		List<User> uList = userService.queryAll(user);
		
		//根据部门获取人员
		List<Department> deptList = getDepartmentByUserAndRole();
		List<User> deptUser = new ArrayList<User>();
		if (deptList != null && deptList.size() > 0) {
			if (uList != null && uList.size() > 0) {
				for (int i = 0; i < deptList.size(); i++) {
					Department deptTmp = deptList.get(i);
					for (int j = 0; j < uList.size(); j++) {
						User uTmp = uList.get(j);
						if (deptTmp.getId().equals(uTmp.getDeptId())) {
							deptUser.add(uTmp);
						}
					}
				}
			}
		}
		for(Dic d : dicList){
			Department dept = new Department();
			dept.setId(d.getId());
			dept.setCode(d.getCode());
			dept.setName(d.getValue());
			dept.setDeptType(1);
			dept.setParentId(new Long(49));
			dList.add(dept);
		}
		if (dList != null && dList.size() > 0) {
			if (deptUser != null && deptUser.size() > 0) {
				for (int i = 0; i < dList.size(); i++) {
					Department deptTmp = dList.get(i);
					for (int j = 0; j < deptUser.size(); j++) {
						User uTmp = deptUser.get(j);
						if (deptTmp.getCode().equals(uTmp.getDutyId())) {
							deptTmp.addUser(uTmp);
						}
					}
					newList.add(deptTmp);
				}
			}
		}
		JSONArray array = new JSONArray();
		for (Department department : newList) {
			if(department.getUsers() != null && department.getUsers().size() > 0){
				JSONObject obj = new JSONObject();
				obj.put("id", department.getId());
				obj.put("name", department.getName());
				obj.put("deptType", department.getDeptType());
				obj.put("parentId", department.getParentId());
				obj.put("user", department.getUsers());
				array.add(obj);
			}
		}
		return array.toString();
	}

	/**
	 * 获取个人组别
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年7月29日 上午11:22:10
	 */
	public String getPersonGroupAndUser() throws Exception {
		List<Department> dList = new ArrayList<Department>();
		Group group = new Group();
		group.setGroupType("1");
		group.setCreateUser(SystemSecurityUtils.getUser().getId());
		List<Group> groupList= groupService.getAllGroupUsers(group);
		if(groupList != null && groupList.size() > 0){
			for(Group g : groupList){
				Department dept = new Department();
				dept.setId(g.getId());
				dept.setCode(g.getName());
				dept.setName(g.getName());
				dept.setDeptType(1);
				dept.setParentId(new Long(49));
				dept.setGroupUsers(g.getLstGroupUser());
				dList.add(dept);
			}
		}
		JSONArray array = new JSONArray();
		for (Department department : dList) {//newList
			if(department.getGroupUsers() != null && department.getGroupUsers().size() > 0){
				JSONObject obj = new JSONObject();
				obj.put("id", department.getId());
				obj.put("name", department.getName());
				obj.put("deptType", department.getDeptType());
				obj.put("parentId", department.getParentId());
				obj.put("group", department.getGroupUsers());
				array.add(obj);
			}
		}
		return array.toString();
	}
	
	/**
	 * 获取公共组别
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年7月30日 上午9:22:10
	 */
	public String getPublicGroupAndUser() throws Exception {
		List<Department> dList = new ArrayList<Department>();
		Group group = new Group();
		group.setGroupType("2");
		group.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
		List<Group> groupList= groupService.getAllGroupUsers(group);
		if(groupList != null && groupList.size() > 0){
			for(Group g : groupList){
				Department dept = new Department();
				dept.setId(g.getId());
				dept.setCode(g.getName());
				dept.setName(g.getName());
				dept.setDeptType(1);
				dept.setParentId(new Long(49));
				dept.setGroupUsers(g.getLstGroupUser());
				dList.add(dept);
			}
		}
		JSONArray array = new JSONArray();
		for (Department department : dList) {//newList
			if(department.getGroupUsers() != null && department.getGroupUsers().size() > 0){
				JSONObject obj = new JSONObject();
				obj.put("id", department.getId());
				obj.put("name", department.getName());
				obj.put("deptType", department.getDeptType());
				obj.put("parentId", department.getParentId());
				obj.put("group", department.getGroupUsers());
				array.add(obj);
			}
		}
		return array.toString();
	}
	
	/**
	 * 查询机构树
	 * @param Department
	 * @author 高研
	 * @version 2014-06-20
	 */
	@Override
	public List<Department> queryOrgTree(Department department) throws CustomException {
		return departmentDao.queryTree(department);
	}

	@Override
	public List<Department> queryManagerDeptTree(Long userId) throws CustomException {
		return departmentDao.queryManagerDeptTree(userId);
	}
	
	/**
	 * 清除部门人员的缓存信息
	 * @author 张继伟
	 * @version 1.0 2014年6月23日 上午10:28:24
	 */
	//@TriggersRemove(cacheName="departmentCache", when= When.AFTER_METHOD_INVOCATION, removeAll=true)
	public void clearDeptAndUserCache() {
		System.out.println("部门人员缓存已清除......");
	}
	
	/**
	 * 根据登录用户与角色获取组织
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年6月26日 下午8:20:51
	 */
	public List<Department> getDepartmentByUserAndRole() throws Exception {
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			//超级管理员
			if(userInfo.getIsSystemAdmin()){
				Department dept = new Department();
				dept.setDeleteFlag(0);
				return departmentDao.queryAll(dept);
			}
			//管理员
			else if(userInfo.getIsAdmin() == 1){
				//查询所有部门
				Department dept_ = new Department();
				dept_.setDeleteFlag(0);
				List<Department> allDeptList = departmentDao.queryAll(dept_);
				
				AdminSide adminSide = new AdminSide();
				adminSide.setUserId(userInfo.getId());
				List<AdminSide> asList = adminSideService.queryAll(adminSide);
				List<Department> deptList = new ArrayList<Department>();
				//循环添加机构
				for(AdminSide as : asList){
					//添加自己	
					Department d = new Department();
					d.setId(as.getDeptId());
					if(!isExist(d, deptList)){
						d = departmentDao.get(d);
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
					List<Department> allDeptList = departmentDao.queryAll(dept_);
					//返回结果LIST
					List<Department> deptList = new ArrayList<Department>();
					for(RoleBlocks roleBlock : orgList){
						//添加自己
						Department d = new Department();
						d.setId(roleBlock.getDeptId());
						d = departmentDao.get(d);
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
		return null;
	}
	
	/**
	 * 根据登录用户与角色获取组织--selectControlNew组织人员使用
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年10月29日 下午4:45:56
	 */
	public List<Department> getDeptByUserAndRoleForSelect() throws CustomException {
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			//超级管理员
			if(userInfo.getIsSystemAdmin()){
				Department dept = new Department();
				dept.setDeleteFlag(0);
				return departmentDao.queryAll(dept);
			}
			//管理员
			else if(userInfo.getIsAdmin() == 1){
				//查询所有部门
				Department dept_ = new Department();
				dept_.setDeleteFlag(0);
				List<Department> allDeptList = departmentDao.queryAll(dept_);
				
				AdminSide adminSide = new AdminSide();
				adminSide.setUserId(userInfo.getId());
				List<AdminSide> asList = adminSideService.queryAll(adminSide);
				List<Department> deptList = new ArrayList<Department>();
				//循环添加机构
				for(AdminSide as : asList){
					//添加自己	
					Department d = new Department();
					d.setId(as.getDeptId());
					if(!isExist(d, deptList)){
						d = departmentDao.get(d);
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
				List<Department> defaultDeptList = new ArrayList<Department>();
				Long orgId = userInfo.getOrgId();
				
				//defaultDeptList = departmentDao.getDeptAndUserByDeptId(orgId);//机构下全的
				defaultDeptList = departmentDao.getDeptByOrgId(orgId);//机构下只显示部门的
				defaultDeptList.add(this.getDeptRoot());
				
				Long roleId [] = new Long[userInfo.getSysUserRole().size()];
				for(int i=0;i<userInfo.getSysUserRole().size();i++){
					SysUserRole userRole = userInfo.getSysUserRole().get(i);
					roleId[i] = userRole.getRoleId();
				}
				if(roleId.length > 0){
					//查询角色关联的机构
					List<RoleBlocks> orgList = roleService.getAllDeptWithRolesSelect(roleId);
					if(orgList != null && orgList.size() > 0){
						//查询所有部门
						Department dept_ = new Department();
						dept_.setDeleteFlag(0);
						//List<Department> allDeptList = departmentDao.queryAll(dept_);
						List<Department> roleRelationDeptList = new ArrayList<Department>();
						//返回结果LIST
						for(RoleBlocks roleBlock : orgList){
							//添加自己
							//Department d = new Department();
							//d.setId(roleBlock.getDeptId());
							//d = DeptCacheUtil.getDeptById(roleBlock.getDeptId());
							//d = departmentDao.get(d);
							
							roleRelationDeptList = getAllParentDeptByDeptId(roleBlock.getDeptId());
							
							//Department d = DeptCacheUtil.getDeptById(roleBlock.getDeptId());
							
							/*if(!isExist(d, roleRelationDeptList)){
								d.setIsChecked(roleBlock.getIsChecked());
								d.setUserType(3);
								roleRelationDeptList.add(d);
								//判断选择的机构
								if(roleBlock.getIsChecked().equals("1")){
									//递归添加部门
									getDepts(d.getId(), roleRelationDeptList, 3, allDeptList);
								} 
							}*/
							defaultDeptList.removeAll(roleRelationDeptList);
							defaultDeptList.addAll(roleRelationDeptList);
						}
						//defaultDeptList.removeAll(roleRelationDeptList);
						//defaultDeptList.addAll(roleRelationDeptList);
					}
					removeDuplicateWithOrder(defaultDeptList);
					return defaultDeptList;
				}
				return defaultDeptList;
			}
		}
		return null;
	}
	
	/**
	 * 递归获取机构
	 * @param deptId
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年11月5日 下午4:04:34
	 */
	/*private String isOrg(Long deptId) throws CustomException {
		Department d = DeptCacheUtil.getDeptById(deptId);
		if(d != null){
			String deptParentIds = d.getParentId().toString()+"@";
			String returnId = isOrg(d.getParentId());
			return deptParentIds + returnId ;
		}else {
			return "";
		}
	}*/
	
	/**
	 * 根据组织ID获取除根以外的父类
	 * @param deptId
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年11月10日 下午1:40:21
	 */
	private List<Department> getAllParentDeptByDeptId(Long deptId) {
		return departmentDao.getQueryDeptPidByOrgId(deptId);
	}
	
	/**
	 * 递归查询管理员机构树
	 * @param id
	 * @param deptList
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年6月26日 下午8:15:23
	 */
	private List<Department> getDepts(Long id, List<Department> deptList, int userType, List<Department> allDeptList){
		try {
			Department dept = new Department();
			dept.setParentId(id);
			dept.setDeptType(0);
			List<Department> list = queryDept(dept, allDeptList);
			for(Department d : list){
				if(!isExist(d, deptList)){
					d.setIsChecked("1");
					d.setUserType(userType);
					deptList.add(d);
					getDepts(d.getId(), deptList, userType, allDeptList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	
	private boolean isExist(Department d, List<Department> list){
		for(Department department : list){
			if(department.getId().equals(d.getId())){
				return true;
			}
		}
		return false;
	}
	
	private boolean isExistUser(User user, List<User> list){
		for(User u : list){
			if(u.getLoginName().equals(user.getLoginName())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据部门ID查找所在
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年8月7日 上午10:54:51
	 */
	public String getDeptIdsByOrgId() throws Exception	{
		String deptString = "";
		List<Department> list = getDeptByUserAndRoleForSelect();//getDepartmentByUserAndRole();
		for(Department d : list){
			if(d.getDeptType() != 1){
				deptString += d.getId() + ",";
			}
		}
		return deptString != ""?deptString.substring(0, deptString.length() - 1):"";
	}
	
	/**
	 * 根据部门ID获取本部门及所有子部门
	 * @param id
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年9月29日 下午14:42:39
	 */
	public List<Department> getDeptByDeptId(Long id) throws CustomException {
		return departmentDao.getDeptByDeptId(id);
	}
	
	
	/**
	 * 根据登录用户获取所在组织
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年6月26日 下午8:20:51
	 */
	public List<Department> getDepartmentByOrg() throws Exception {
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			//超级管理员
			if(userInfo.getIsSystemAdmin()){
				Department dept = new Department();
				dept.setDeleteFlag(0);
				return departmentDao.queryAll(dept);
			}
			//管理员
			/*else if(userInfo.getIsAdmin() == 1){//2015-1-27只显示本机构或所勾选机构，所以去掉管理员判断
				//查询所有部门
				Department dept_ = new Department();
				dept_.setDeleteFlag(0);
				List<Department> allDeptList = departmentDao.queryAll(dept_);
				
				AdminSide adminSide = new AdminSide();
				adminSide.setUserId(userInfo.getId());
				List<AdminSide> asList = adminSideService.queryAll(adminSide);
				List<Department> deptList = new ArrayList<Department>();
				//循环添加机构
				for(AdminSide as : asList){
					//添加自己	
					Department d = new Department();
					d.setId(as.getDeptId());
					if(!isExist(d, deptList)){
						d = departmentDao.get(d);
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
			}*/
			//普通用户
			else{
				List<Department> defaultDeptList = new ArrayList<Department>();
				Long orgId = userInfo.getOrgId();
				defaultDeptList = departmentDao.getDeptByOrgId(orgId);//机构下只显示部门的
				//获取上级组织
				List<Department> deptParent = departmentDao.getQueryDeptPidByOrgId(orgId);
				if(deptParent != null && deptParent.size() > 0){
					for(Department d : deptParent){
						defaultDeptList.add(d);
					}
				}
				//获取上级组织
				defaultDeptList.add(this.getDeptRoot());
				Long roleId [] = new Long[userInfo.getSysUserRole().size()];
				for(int i=0;i<userInfo.getSysUserRole().size();i++){
					SysUserRole userRole = userInfo.getSysUserRole().get(i);
					roleId[i] = userRole.getRoleId();
				}
				if(roleId.length > 0){
					//查询角色关联的机构
					List<RoleBlocks> orgList = roleService.getAllDeptWithRolesSelect(roleId);
					if(orgList != null && orgList.size() > 0){
						//查询所有部门
						List<Department> roleRelationDeptList = new ArrayList<Department>();
						//返回结果LIST
						for(RoleBlocks roleBlock : orgList){
							roleRelationDeptList = getAllParentDeptByDeptId(roleBlock.getDeptId());
							defaultDeptList.removeAll(roleRelationDeptList);
							defaultDeptList.addAll(roleRelationDeptList);
						}
					}
					removeDuplicateWithOrder(defaultDeptList);
					return defaultDeptList;
				}
				return defaultDeptList;
				
				/*Long orgId = userInfo.getOrgId();
				//departmentDao.getDeptAndUserByDeptId(orgId);//机构下全部的
				//List<Department> dList = departmentDao.getDeptByOrgId(orgId);//机构下全部门的
				
				List<Department> dList = departmentDao.getQueryDeptPidByOrgId(orgId);//机构下全部门的
				
				dList.add(this.getDeptRoot());
				return dList;*/
			}
		}
		return null;
	}
	
	
	/**
	 * 获取所有组织并存入缓存
	 * @author 张继伟
	 * @version 1.0 2014年10月18日 下午12:03:48
	 */
	public void getAllDepts(){
		Department dept = new Department();
		dept.setDeleteFlag(0);
		List<Department> deptList = departmentDao.queryAll(dept);
		if(deptList != null && deptList.size() > 0){
			for(Department d : deptList){
				Department department = new Department();
				department.setId(d.getId());
				department = systemService.queryOrgIdAndName(department);
				d.setOrgId(department.getOrgId());
				d.setOrgName(department.getOrgName());
				CacheUtils.put(CACHE_DEPT_INFO + d.getId(), d);
			}
		}
	}
	
	/**
	 * 缓存查询单个组织
	 * @param dept
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年10月18日 下午2:27:41
	 */
	public Department getDeptById(Department dept){
		Department d = (Department) CacheUtils.get(CACHE_DEPT_INFO + dept.getId());
		if(d == null){
			dept.setDeleteFlag(0);
			d = departmentDao.get(dept);
			if(d != null){
				Department department = new Department();
				department.setId(d.getId());
				department = systemService.queryOrgIdAndName(department);
				d.setOrgId(department.getOrgId());
				d.setOrgName(department.getOrgName());
				CacheUtils.put(CACHE_DEPT_INFO + dept.getId(), d);
			}
		}
		return d;
	}
	
	/**
	 * 获取根节点数据
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年10月30日 上午11:39:17
	 */
	private Department getDeptRoot(){
		Department d = new Department();
		d.setParentId(new Long(0));
		return departmentDao.get(d);
	}
	
	/**
	 * 去除集合中的重复数据
	 * @param list
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年10月30日 上午11:39:20
	 */
	private void removeDuplicateWithOrder(List<Department> list) {
		Set<Long> set = new HashSet<Long>();
        List<Department> newList = new ArrayList<Department>();
        for (Iterator<Department> iter = list.iterator(); iter.hasNext();) {
        	Department element = iter.next();
            if (set.add(element.getId())){
            	newList.add(element);
            }
        } 
        list.clear();
        list.addAll(newList);
   }
	
	/**
	 * 根据登录人所在机构查询组织树
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年11月6日 下午3:17:51
	 */
	public List<Department> getOrgTree() throws CustomException {
		return this.getDeptByUserAndRoleForSelect();
	}
	
	/**
	 * 查询整个机构组织树不包含部门
	 * @return
	 * @throws CustomException
	 * @author 张继伟
	 * @version 1.0 2014年12月3日 下午12:01:36
	 */
	public List<Department> getAllOrgNoDeptTree() throws CustomException {
		return departmentDao.getAllOrgNoDeptTree();
	}
	
	/**
	 * 根据登录人所在机构查询组织树与人员
	 * @return
	 * @throws CustomException
	 * @author 张继伟
	 * @version 1.0 2014年11月6日 下午3:55:15
	 */
	public List<Department> getOrgAndPersonTree() throws CustomException {
		List<Department> newList = new ArrayList<Department>();
		List<Department> list = this.getDeptByUserAndRoleForSelect();
		User user = new User();
		user.setDeleteFlag(0);
		user.addOrderByField("t.ORDER_NO");
		List<User> uList = userService.queryAll(user);
		if (list != null && list.size() > 0) {
			if (uList != null && uList.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Department deptTmp = list.get(i);
					for (int j = 0; j < uList.size(); j++) {
						User uTmp = uList.get(j);
						if (deptTmp.getId().equals(uTmp.getDeptId())) {
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
	 * 根据登录人员获取组织用户--手机端
	 * @param userInfo
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年11月11日 下午3:25:38
	 */
	public Map<String, Object> queryDeptAndUserFor4M(User userInfo)	throws CustomException  {
		List<User4MBean> userList = new ArrayList<User4MBean>();
		List<Department> list = getDeptByUserAndRoleForSelect4M(userInfo);
		User user = new User();
		user.setDeleteFlag(0);
		user.addOrderByField("t.ORDER_NO");
		List<User> uList = userService.queryAll(user);
		if (list != null && list.size() > 0) {
			if (uList != null && uList.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Department deptTmp = list.get(i);
					for (int j = 0; j < uList.size(); j++) {
						User uTmp = uList.get(j);
						if (deptTmp.getId().equals(uTmp.getDeptId())) {
							User4MBean uBean = new User4MBean();
							uBean.setId(uTmp.getId());
							uBean.setDisplayName(uTmp.getDisplayName());
							uBean.setPhoto(uTmp.getPhoto());
							uBean.setMobile(uTmp.getMobile());
							userList.add(uBean);
						}
					}
				}
			}
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(userList != null && userList.size() > 0){
			resultMap.put("state", "success");
			resultMap.put("data", userList);
			
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", "通讯录加载失败!");
			resultMap.put("data", null);
		}
		
		return resultMap;
	}
	
	/**
	 * 根据登录人员获取组织--手机端
	 * @param userInfo
	 * @return
	 * @throws CustomException
	 * @author 张继伟
	 * @version 1.0 2014年11月11日 下午3:25:30
	 */
	private List<Department> getDeptByUserAndRoleForSelect4M(User userInfo) throws CustomException  {
		if(userInfo.getIsSystemAdmin()){//超级管理员
			Department dept = new Department();
			dept.setDeleteFlag(0);
			return departmentDao.queryAll(dept);
		}else if(userInfo.getIsAdmin() == 1){//管理员
			//查询所有部门
			Department dept_ = new Department();
			dept_.setDeleteFlag(0);
			List<Department> allDeptList = departmentDao.queryAll(dept_);
			AdminSide adminSide = new AdminSide();
			adminSide.setUserId(userInfo.getId());
			List<AdminSide> asList = adminSideService.queryAll(adminSide);
			List<Department> deptList = new ArrayList<Department>();
			//循环添加机构
			for(AdminSide as : asList){
				//添加自己	
				Department d = new Department();
				d.setId(as.getDeptId());
				if(!isExist(d, deptList)){
					d = departmentDao.get(d);
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
		}else{//普通用户
			List<Department> defaultDeptList = new ArrayList<Department>();
			Long orgId = userInfo.getOrgId();
			defaultDeptList = departmentDao.getDeptByOrgId(orgId);//机构下只显示部门的
			defaultDeptList.add(this.getDeptRoot());
			if(userInfo.getSysUserRole() != null && userInfo.getSysUserRole().size() > 0){
				Long roleId [] = new Long[userInfo.getSysUserRole().size()];
				for(int i=0;i<userInfo.getSysUserRole().size();i++){
					SysUserRole userRole = userInfo.getSysUserRole().get(i);
					roleId[i] = userRole.getRoleId();
				}
				if(roleId.length > 0){
					//查询角色关联的机构
					List<RoleBlocks> orgList = roleService.getAllDeptWithRolesSelect(roleId);
					if(orgList != null && orgList.size() > 0){
						//查询所有部门
						Department dept_ = new Department();
						dept_.setDeleteFlag(0);
						List<Department> roleRelationDeptList = new ArrayList<Department>();
						//返回结果LIST
						for(RoleBlocks roleBlock : orgList){
							roleRelationDeptList = getAllParentDeptByDeptId(roleBlock.getDeptId());
							defaultDeptList.removeAll(roleRelationDeptList);
							defaultDeptList.addAll(roleRelationDeptList);
						}
					}
					removeDuplicateWithOrder(defaultDeptList);
					return defaultDeptList;
				}
			}
			return defaultDeptList;
		}
	}
}