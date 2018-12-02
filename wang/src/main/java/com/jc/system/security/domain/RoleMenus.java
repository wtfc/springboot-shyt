package com.jc.system.security.domain;

import com.jc.foundation.domain.BaseBean;

public class RoleMenus extends BaseBean{
	private static final long serialVersionUID = 1L;

	private Long roleId;  /* 角色ID */
	private Long menuId;	/* 菜单ID */
	
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	
	

}
