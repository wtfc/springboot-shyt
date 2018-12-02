package com.jc.system.security.domain;

import com.jc.foundation.domain.BaseBean;

/**
 * @title GOA2.0
 * @description 实体类
 * @version  2014-04-15
 *
 */
public class SysUserRole extends BaseBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private Long userId;   /*用户ID*/
	private Long roleId;   /*角色ID*/
	private Long deptId;   /*用户所在部门*/

	private String[] roleIds;
	
	public Long getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	public Long getRoleId(){
		return roleId;
	}
	
	public void setRoleId(Long roleId){
		this.roleId = roleId;
	}
	public Long getDeptId(){
		return deptId;
	}
	
	public void setDeptId(Long deptId){
		this.deptId = deptId;
	}

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	
	
}