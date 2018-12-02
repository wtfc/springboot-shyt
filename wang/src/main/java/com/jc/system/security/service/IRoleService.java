package com.jc.system.security.service;

import java.util.List;

import net.sf.json.JSONArray;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.system.security.domain.Role;
import com.jc.system.security.domain.RoleBlocks;
import com.jc.system.security.domain.RoleExts;
import com.jc.system.security.domain.RoleMenus;

public interface IRoleService extends IBaseService<Role>{

	/**
	  * @description  保存角色-菜单关联数据
	  * @param role
	  * @return
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月13日 下午2:51:29
	*/
	public void saveRoleMenu(Role role) throws CustomException;

	/**
	  * @description 根据角色获得已选中菜单
	  * @param roleMenus
	  * @return
	  * @author 孙鹏
	  * @version 1.0 2014年5月13日 上午11:12:03
	*/
	public List<RoleMenus> getMenusByRole(RoleMenus roleMenus) throws CustomException;
	
	/**
	  * @description 根据角色获得权限信息
	  * @param roleMenus
	  * @return
	  * @author 孙鹏
	  * @version 1.0 2014年5月13日 上午11:12:03
	*/
	public List<RoleExts> getExtsByRole(RoleExts roleExts) throws CustomException;
	
	/**
	  * @description 根据角色获得已选中部门
	  * @param roleMenus
	  * @return
	  * @author 孙鹏
	  * @version 1.0 2014年5月13日 上午11:12:03
	*/
	public List<RoleBlocks> getBlocksByRole(RoleBlocks roleBlocks) throws CustomException;
	
	/**
	  * @description 获得部门及部门下所有角色集合
	  * @param response
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月23日 下午1:50:32
	*/
	public JSONArray getAllDeptAndRole() throws Exception;
	
	/**
	  * @description 根据角色获得配置部门集合
	  * @param response
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月23日 下午1:50:32
	*/
	public List<RoleBlocks> getAllDeptWithRoles(Long roles[]) throws CustomException;
	
	
	
	/**
	  * @description 根据角色获得配置部门集合(数据权限)
	  * @param response
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月23日 下午1:50:32
	*/
	public List<RoleBlocks> getAllDeptWithRolesPermission(Long roles[]) throws CustomException;
	/**
	  * @description 根据部门id获得与之相关的集合
	  * @param response
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月23日 下午1:50:32
	 * @throws CustomException 
	*/
	public List<RoleBlocks> getDeptRelation(Long id) throws CustomException;
	
	/**
	  * @description 用户管理角色列表
	  * @param response
	  * @throws Exception
	  * @author 高研
	  * @version 1.0 2014年5月23日 下午1:50:32
	 * @throws CustomException 
	*/
	public List<Role> getRolesForUser(Role role) throws CustomException;
	/**
	  * @description 根据角色获得配置部门集合(人员选择树使用)
	  * @param response
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月23日 下午1:50:32
	*/
	public List<RoleBlocks> getAllDeptWithRolesSelect(Long[] roles) throws CustomException;
}