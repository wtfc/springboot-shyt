/**
 * 
 */
package com.jc.system.security.domain;

import com.jc.foundation.domain.BaseBean;

/**
 * @title GOA V2.0
 * @description 
 * @author 孙鹏
 * @version 1.0 2014年5月14日
 */
public class RoleBlocks extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6113055312108186455L;
	private Long roleId; /* 角色Id */
	private Long deptId; /* 部门Id */
	private String isChecked; /* 是否真正点选 */
	public String[] roleIds;
	public String[] deptIds;
	
	
	public Boolean flag = true; /* 部门还是人员(部门为true)*/
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	
	
	public String[] getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(String[] deptIds) {
		this.deptIds = deptIds;
	}
	@Override 
	public boolean equals(Object obj) { 
		if (this == obj) { 
			return true; 
		}
		if (obj == null) { 
			return false; 
		}
		if (getClass() != obj.getClass()) { 
			return false; 
		}
		RoleBlocks other = (RoleBlocks) obj; 
		if (deptId == null) { 
			if (other.deptId != null) { 
				return false; 
			} 
		} else if (!deptId.equals(other.deptId)) { 
			return false;  
		} else if(!flag){
			return false;
		} else if(!other.flag){
			return false;
		}
		return true; 
	}
}
