package com.jc.system.security.domain;

import java.util.List;

import com.jc.foundation.domain.BaseBean;

public class Role extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String name; /* 名称 */
	private String description; /* 描述 */
	private Long deptId;  /* 部门id */
	private String deptName; /* 部门名称 */
	private String personNum; /* 人员数量 */
	
	private List<RoleMenus> roleMenus;
	private List<RoleBlocks> roleBlocks;
	private List<RoleExts> roleExts;
	private List<SysUserRole> sysUserRoles;
	private String menuIds;
	private String nameOld;
	
	
	
	// 部门查询条件
	private String deptIds;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}


	public List<RoleMenus> getRoleMenus() {
		return roleMenus;
	}

	public void setRoleMenus(List<RoleMenus> roleMenus) {
		this.roleMenus = roleMenus;
	}

	public List<RoleBlocks> getRoleBlocks() {
		return roleBlocks;
	}

	public void setRoleBlocks(List<RoleBlocks> roleBlocks) {
		this.roleBlocks = roleBlocks;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getPersonNum() {
		return personNum;
	}

	public void setPersonNum(String personNum) {
		this.personNum = personNum;
	}

	public List<SysUserRole> getSysUserRoles() {
		return sysUserRoles;
	}

	public void setSysUserRoles(List<SysUserRole> sysUserRoles) {
		this.sysUserRoles = sysUserRoles;
	}

	public List<RoleExts> getRoleExts() {
		return roleExts;
	}

	public void setRoleExts(List<RoleExts> roleExts) {
		this.roleExts = roleExts;
	}

	public String getNameOld() {
		return nameOld;
	}

	public void setNameOld(String nameOld) {
		this.nameOld = nameOld;
	}
	
	
	
}