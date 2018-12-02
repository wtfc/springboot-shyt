package com.jc.system.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.CustomException;
import com.jc.system.DBException;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.dao.IRoleDao;
import com.jc.system.security.domain.AdminSide;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.Role;
import com.jc.system.security.domain.RoleBlocks;
import com.jc.system.security.domain.RoleExts;
import com.jc.system.security.domain.RoleMenus;
import com.jc.system.security.domain.SysUserRole;
import com.jc.system.security.domain.User;
import com.jc.system.security.exception.SystemException;
import com.jc.system.security.service.IAdminSideService;
import com.jc.system.security.service.IDepartmentService;
import com.jc.system.security.service.IRoleBlocksService;
import com.jc.system.security.service.IRoleExtsService;
import com.jc.system.security.service.IRoleMenusService;
import com.jc.system.security.service.IRoleService;
import com.jc.system.security.service.ISysUserRoleService;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {
	@Autowired
	public RoleServiceImpl(IRoleDao dao) {
		super(dao);
		this.roleDao = dao;
		// TODO Auto-generated constructor stub
	}

	public RoleServiceImpl(){
		
	}
	
	private IRoleDao roleDao;

	@Autowired
	private IDepartmentService departmentService;
	
	@Autowired
	private IRoleMenusService roleMenuService;
	
	@Autowired
	private IRoleBlocksService roleBlocksService;
	
	@Autowired
	private IRoleExtsService roleExtService;
	
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	
	@Autowired
	private IAdminSideService adminSideService;
	
	/**
	 * @description 分页查询角色
	 * @param Role
	 *            role 实体类
	 * @return PageManager 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-05-09
	 */
	@Override
	public PageManager query(Role role, PageManager page) {
		return super.query(role, page);
	}
	

	/**
	  * @description  保存角色-菜单关联数据
	  * @param roleMap
	  * @param result
	  * @param request
	  * @return
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月13日 下午2:51:29
	 * @throws CustomException 
	*/
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void saveRoleMenu(Role role) throws CustomException {
		RoleMenus rMenu = new RoleMenus();
		RoleBlocks rBlock = new RoleBlocks();
		SysUserRole rUser = new SysUserRole();
		RoleExts rExt = new RoleExts();
		if(role.getPrimaryKeys()!=null && role.getPrimaryKeys().length > 0 ){
			rMenu.setPrimaryKeys(role.getPrimaryKeys());
			rBlock.setPrimaryKeys(role.getPrimaryKeys());
			rUser.setRoleIds(role.getPrimaryKeys());
			rExt.setPrimaryKeys(role.getPrimaryKeys());
		}else{
			rMenu.setPrimaryKeys(role.getId().toString().split("&"));
			rBlock.setPrimaryKeys(role.getId().toString().split("&"));
			rUser.setRoleIds(role.getId().toString().split("&"));
			rExt.setPrimaryKeys(role.getId().toString().split("&"));
		}
		try{
			roleMenuService.delete(rMenu, false);
			roleBlocksService.delete(rBlock,false);
			sysUserRoleService.delete(rUser, false);
			roleExtService.delete(rExt,false);
			List<RoleMenus> roleMenus = role.getRoleMenus();
			List<RoleBlocks> roleBlocks = role.getRoleBlocks();
			List<SysUserRole> roleUsers = role.getSysUserRoles();
			List<RoleExts> roleExts = role.getRoleExts();
			roleMenuService.saveList(roleMenus);
			roleBlocksService.saveList(roleBlocks);
			sysUserRoleService.saveList(roleUsers);
			roleExtService.saveList(roleExts);
		}catch(Exception e){
			SystemException ce = new SystemException(e);
			throw ce;
		}
	}

	/**
	  * @description 根据角色获得已选中菜单
	  * @param roleMenus
	  * @return
	  * @author 孙鹏
	  * @version 1.0 2014年5月13日 上午11:12:03
	 * @throws CustomException 
	*/
	public List<RoleMenus> getMenusByRole(RoleMenus roleMenus) throws CustomException  {
		return roleMenuService.queryAll(roleMenus);
	}
	
	/**
	  * @description 根据角色获得已选中权限信息
	  * @param roleMenus
	  * @return
	  * @author 孙鹏
	  * @version 1.0 2014年5月13日 上午11:12:03
	 * @throws CustomException 
	*/
	public List<RoleExts> getExtsByRole(RoleExts roleExts) throws CustomException  {
		return roleExtService.queryAll(roleExts);
	}
	
	/**
	  * @description 根据角色获得已选中部门
	  * @param roleMenus
	  * @return
	  * @author 孙鹏
	  * @version 1.0 2014年5月13日 上午11:12:03
	 * @throws CustomException 
	*/
	public List<RoleBlocks> getBlocksByRole(RoleBlocks roleBlcoks) throws CustomException {
		return roleBlocksService.queryAll(roleBlcoks);
	}
	
	/**
	  * @description 删除角色及相关关联表信息
	  * @param role
	  * @return 删除的记录数
	  * @author 孙鹏
	  * @version 1.0 2014年5月13日 上午11:12:03
	 * @throws CustomException 
	*/
	public Integer delete(Role role) throws CustomException{
		propertyService.fillProperties(role,false);
		try {
			if(role.getPrimaryKeys()!=null && role.getPrimaryKeys().length > 0 ){
				RoleMenus rMenu = new RoleMenus();
				RoleBlocks rBlock = new RoleBlocks();
				SysUserRole rUser = new SysUserRole();
				RoleExts rExts = new RoleExts();
				rMenu.setPrimaryKeys(role.getPrimaryKeys());
				rBlock.setPrimaryKeys(role.getPrimaryKeys());
				rUser.setRoleIds(role.getPrimaryKeys());
				rExts.setPrimaryKeys(role.getPrimaryKeys());
				roleMenuService.delete(rMenu, false);
				roleBlocksService.delete(rBlock,false);
				sysUserRoleService.delete(rUser, false);
				roleExtService.delete(rExts,false);
			}
			return roleDao.delete(role);
		} catch (DBException e) {
			SystemException ce = new SystemException(e);
			throw ce;
		}
	}

	/**
	  * @description 获得部门及部门下所有角色集合
	  * @param response
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月23日 下午1:50:32
	*/
	public JSONArray getAllDeptAndRole() throws Exception {
		List<Department> newList = new ArrayList<Department>();
		Department dept = new Department();
		dept.setDeleteFlag(0);
		List<Department> list = departmentService.getDepartmentByUserAndRole();
		String deptIds = "";
		
		List<AdminSide> asList = new ArrayList<AdminSide>();
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			//超级管理员
			if(userInfo.getIsSystemAdmin()){
				
			}
			//管理员
			else if(userInfo.getIsAdmin() == 1){
				AdminSide adminSide = new AdminSide();
				adminSide.setUserId(userInfo.getId());
				asList = adminSideService.queryManagerDeptRree(adminSide);
			}
		}
		for(AdminSide adminSide : asList){
			if(adminSide.getDeptType() == 1 && "1".equals(adminSide.getIsChecked())){
				List<Department> deptsList = departmentService.getDeptAndUserByDeptId(adminSide.getId());
				for(Department depart : deptsList){
					deptIds += depart.getId() + ",";
				}
			}
		}
		deptIds = deptIds.length() > 0 ? deptIds.substring(0, deptIds.length() - 1) : null;
		Role role = new Role();
		role.setDeleteFlag(0);
		role.setDeptIds(deptIds);
		List<Role> uList = roleDao.queryAll(role);
		if(list != null && list.size() > 0){
			if(uList != null && uList.size() >0){
				for(int i=0;i<list.size();i++){
					Department deptTmp = list.get(i);
					for(int j=0;j<uList.size();j++){
						Role uTmp = uList.get(j);
						if(deptTmp.getId().equals(uTmp.getDeptId())){
							deptTmp.addRole(uTmp);
						}
					}
					newList.add(deptTmp);
				}
			}
		}
		JSONArray array = new JSONArray();
		for(Department department : newList){
			if(department.getParentId().equals(new Long(0))){
				JSONObject obj = new JSONObject();
				obj.put("id", department.getId());
				obj.put("name",department.getName());
				obj.put("deptType",department.getDeptType());
				obj.put("parentId",department.getParentId());
				obj.put("role",department.getRoles());
				recur(newList, department.getId(), obj);
				array.add(obj);
			}
		}
		return array;
	}
	
	
	/**
	  * @description 递归部门层次关系
	  * @param departMentList
	  * @param id
	  * @param obj
	  * @return
	  * @author 孙鹏
	  * @version 1.0 2014年5月23日 下午1:51:40
	*/
	private static JSONObject recur(List<Department> departMentList, Long id, JSONObject obj){
		JSONArray array = new JSONArray();
		for(int i = 0 ; i < departMentList.size(); i++){
			if(departMentList.get(i).getParentId().equals(id)){
				JSONObject sonobj = new JSONObject();
				sonobj.put("id", departMentList.get(i).getId());
				sonobj.put("name", departMentList.get(i).getName());
				sonobj.put("deptType", departMentList.get(i).getDeptType());
				sonobj.put("parentId", departMentList.get(i).getParentId());
				sonobj.put("role", departMentList.get(i).getRoles());
				recur(departMentList, departMentList.get(i).getId(), sonobj);
				array.add(sonobj);
			}
		}
		if(!array.isEmpty()){
			obj.put("subDept", array);
		}
		return obj;
	}

	/**
	  * @description 根据角色获得配置部门集合
	  * @param response
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月23日 下午1:50:32
	 * @throws CustomException 
	*/
	public List<RoleBlocks> getAllDeptWithRoles(Long[] roles) throws CustomException {
		List<RoleBlocks> deptList = new ArrayList<RoleBlocks>();
		for(Long roleId : roles){
			RoleExts rExts = new RoleExts();
			rExts.setRoleId(roleId);
			List<RoleExts> extsList = roleExtService.queryAll(rExts);
			for(RoleExts ext : extsList){
				switch(ext.getPermissionType()){
					case 1:{
						deptList.addAll(this.getDeptRelation(SystemSecurityUtils.getUser().getOrgId()));
						break;
					}
					case 2:{
						deptList.addAll(this.getDeptRelation(SystemSecurityUtils.getUser().getDeptId()));
						break;
					}
					case 3:{
						deptList.addAll(this.getDeptRelation(SystemSecurityUtils.getUser().getOrgId()));
						break;
					}
					case 4:{
						RoleBlocks rBlock = new RoleBlocks();
						rBlock.setRoleId(roleId);
						List<RoleBlocks> roleBlockList = roleBlocksService.queryAll(rBlock);
						deptList.addAll(roleBlockList);
						break;
					}
				}
			}
		}
		return removalDept(deptList);
	}
	
	
	/**
	  * @description 根据角色获得配置部门集合(数据权限使用)
	  * @param response
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月23日 下午1:50:32
	 * @throws CustomException 
	*/
	public List<RoleBlocks> getAllDeptWithRolesPermission(Long[] roles) throws CustomException {
		List<RoleBlocks> deptList = new ArrayList<RoleBlocks>();
		for(Long roleId : roles){
			RoleExts rExts = new RoleExts();
			rExts.setRoleId(roleId);
			List<RoleExts> extsList = roleExtService.queryAll(rExts);
			for(RoleExts ext : extsList){
				switch(ext.getPermissionType()){
					case 1:{
						RoleBlocks rBlock = new RoleBlocks();
						rBlock.setDeptId(SystemSecurityUtils.getUser().getId());
						rBlock.setFlag(false);
						rBlock.setIsChecked("0");
						deptList.add(rBlock);
						break;
					}
					case 2:{
						RoleBlocks rBlock = new RoleBlocks();
						rBlock.setDeptId(SystemSecurityUtils.getUser().getDeptId());
						rBlock.setIsChecked("1");
						deptList.add(rBlock);
						break;
					}
					case 3:{
						RoleBlocks rBlock = new RoleBlocks();
						rBlock.setDeptId(SystemSecurityUtils.getUser().getOrgId());
						rBlock.setIsChecked("1");
						deptList.add(rBlock);
						break;
					}
					case 4:{
						RoleBlocks rBlock = new RoleBlocks();
						rBlock.setRoleId(roleId);
						rBlock.setIsChecked("1");
						List<RoleBlocks> roleBlockList = roleBlocksService.queryAll(rBlock);
						deptList.addAll(roleBlockList);
						break;
					}
				}
			}
		}
		return removalDept(deptList);
	}

	/**
	  * @description 去掉部门集合中的重复信息
	  * @param response
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月23日 下午1:50:32
	 * @throws CustomException 
	*/
	public List<RoleBlocks> removalDept(List<RoleBlocks> deptList){
		List<RoleBlocks> blocksList = new ArrayList<RoleBlocks>();
		for(RoleBlocks block : deptList){
			if(!blocksList.contains(block)){
				blocksList.add(block);
			}else{
				for(RoleBlocks changeBlock : blocksList){
					if(changeBlock.getDeptId().equals(block.getDeptId())&&changeBlock.getIsChecked().equals("0")&&block.getIsChecked().equals("1")){
						changeBlock.setIsChecked("1");
					}
				}
			}
		}
		return blocksList;
	}
	
	
	/**
	  * @description 根据部门id获得与之相关的集合
	  * @param response
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月23日 下午1:50:32
	 * @throws CustomException 
	*/
	
	Map<Long,Long> departMap = new HashMap<Long,Long>();
	
	public List<RoleBlocks> getDeptRelation(Long id) throws CustomException{
		departMap = new HashMap<Long,Long>();
		List<RoleBlocks> rBlocksList = new ArrayList<RoleBlocks>();
		List<Department> departList = departmentService.queryAll(new Department());
		for(Department depart : departList){
			departMap.put(depart.getId(),depart.getParentId());
		}
		RoleBlocks rBlock = new RoleBlocks();
		rBlock.setIsChecked("1");
		rBlock.setDeptId(id);
		rBlocksList.add(rBlock);
		this.recurDeptRelation(id,rBlocksList);
		return rBlocksList;
	}
	public void recurDeptRelation(Long id,List<RoleBlocks> rBlocksList){
		Long pId = departMap.get(id);
		if(pId == 0)return;
		RoleBlocks rBlock = new RoleBlocks();
		rBlock.setIsChecked("0");
		rBlock.setDeptId(pId);
		rBlocksList.add(rBlock);
		this.recurDeptRelation(pId, rBlocksList);
	}
	
	
	
	
	/**
	  * @description 根据部门id获得与之相关的集合(数据权限使用)
	  * @param response
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月23日 下午1:50:32
	 * @throws CustomException 
	*/
	
	Map<Long,String> departMapForPermission = new HashMap<Long,String>();
	
	public List<RoleBlocks> getDeptRelationForPermisssion(Long id,String deptType) throws CustomException{
		departMapForPermission = new HashMap<Long,String>();
		List<RoleBlocks> rBlocksList = new ArrayList<RoleBlocks>();
		List<Department> departList = departmentService.queryAll(new Department());
		for(Department depart : departList){
			departMapForPermission.put(depart.getId(),depart.getParentId()+"&&"+depart.getDeptType());
			
		}
		RoleBlocks rBlock = new RoleBlocks();
		rBlock.setIsChecked("1");
		rBlock.setDeptId(id);
		rBlocksList.add(rBlock);
		this.recurDeptRelationForPermisssion(id,rBlocksList,deptType);
		return rBlocksList;
	}
	public void recurDeptRelationForPermisssion(Long id,List<RoleBlocks> rBlocksList,String deptType){
		String pIdAndDeptType = departMapForPermission.get(id);
		Long pId = Long.parseLong(pIdAndDeptType.split("&&")[0]);
		String deptTypeT = pIdAndDeptType.split("&&")[1];
		if(pId == 0 || ("1".equals(deptTypeT) && "1".equals(deptType)) )return;
		RoleBlocks rBlock = new RoleBlocks();
		rBlock.setIsChecked("0");
		rBlock.setDeptId(pId);
		rBlocksList.add(rBlock);
		this.recurDeptRelationForPermisssion(pId, rBlocksList,deptTypeT);
	}
	
	/* (non-Javadoc)
	 * @see com.jc.system.security.service.IRoleService#getAllDeptWithRolesSelect(java.lang.Long[])
	 */
	@Override
	public List<RoleBlocks> getAllDeptWithRolesSelect(Long[] roles) throws CustomException {
		List<RoleBlocks> deptList = new ArrayList<RoleBlocks>();
		for(Long roleId : roles){
			RoleExts rExts = new RoleExts();
			rExts.setRoleId(roleId);
			List<RoleExts> extsList = roleExtService.queryAll(rExts);
			for(RoleExts ext : extsList){
				switch(ext.getPermissionType()){
					case 1:{
						break;
					}
					case 2:{
						break;
					}
					case 3:{
						break;
					}
					case 4:{
						RoleBlocks rBlock = new RoleBlocks();
						rBlock.setRoleId(roleId);
						rBlock.setIsChecked("1");
						List<RoleBlocks> roleBlockList = roleBlocksService.queryAll(rBlock);
						deptList.addAll(roleBlockList);
						break;
					}
				}
			}
		}
		return removalDept(deptList);
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
	public List<Role> getRolesForUser(Role role) throws CustomException {
		return roleDao.getRolesForUser(role);
	}

	
}