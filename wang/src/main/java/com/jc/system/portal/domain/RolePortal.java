package com.jc.system.portal.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA2.0
 * @description  实体类
 * @author 
 * @version  2014-06-16
 */

public class RolePortal extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long portalId;   /*门户ID*/
	private Long portaletId;   /*门户组件ID*/
	private Long roleId;   /*角色ID*/
	private Long deptId;   /*部门ID*/
	private Long userId;   /**/
	private Long organId;   /**/
	private String roleIds; /*角色IDS*/
	private String portalIds; /*门户IDS*/

	public Long getPortalId(){
		return portalId;
	}
	
	public void setPortalId(Long portalId){
		this.portalId = portalId;
	}
	
	public Long getPortaletId(){
		return portaletId;
	}
	
	public void setPortaletId(Long portaletId){
		this.portaletId = portaletId;
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
	
	public Long getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	public Long getOrganId(){
		return organId;
	}
	
	public void setOrganId(Long organId){
		this.organId = organId;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getPortalIds() {
		return portalIds;
	}

	public void setPortalIds(String portalIds) {
		this.portalIds = portalIds;
	}
	
}