/**
 * 
 */
package com.jc.system.security.domain;

import com.jc.foundation.domain.BaseBean;

/**
 * @title GOA V2.0
 * @description 
 * @version 1.0 2014年6月24日
 */
public class RoleExts extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -364141066998872285L;
	private Long roleId;  /* 角色ID */
	private Integer permissionType;	/* 访问权限类型 */
	private Integer weight;	/* 安全系数 */
	private Integer weightRule;	/* 安全系数规则 */
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Integer getPermissionType() {
		return permissionType;
	}
	public void setPermissionType(Integer permissionType) {
		this.permissionType = permissionType;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getWeightRule() {
		return weightRule;
	}
	public void setWeightRule(Integer weightRule) {
		this.weightRule = weightRule;
	}
	
	
}
