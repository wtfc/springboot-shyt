package com.jc.system.security.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA2.0
 * @description 管理员管理机构范围表 实体类
 * @version  2014-04-16
 */

public class AdminSide extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long userId;   /*用户id*/
	private Long deptId;   /*部门id*/
	private Long parentId;   /*父节点id*/
	private String isChecked;   /*是否选中*/
	
	//机构属性
	private String name;
	private Integer deptType;

	public Long getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	public Long getDeptId(){
		return deptId;
	}
	
	public void setDeptId(Long deptId){
		this.deptId = deptId;
	}
	public Long getParentId(){
		return parentId;
	}
	
	public void setParentId(Long parentId){
		this.parentId = parentId;
	}
	public String getIsChecked(){
		return isChecked;
	}
	
	public void setIsChecked(String isChecked){
		this.isChecked = isChecked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDeptType() {
		return deptType;
	}

	public void setDeptType(Integer deptType) {
		this.deptType = deptType;
	}
	
}