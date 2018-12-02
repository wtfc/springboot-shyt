package com.jc.system.security.domain;

import com.jc.foundation.domain.BaseBean;
/**
 * @title GOA2.0
 * @description 角色信息基本表实体类
 * @version  2014-04-15
 *
 */
public class SysRole extends BaseBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String name;   /*名称*/
	private String description;   /*描述*/
	private Integer deptId;   /*角色所在部门id*/
	private Integer queue;   /*排序*/

	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	public Integer getDeptId(){
		return deptId;
	}
	
	public void setDeptId(Integer deptId){
		this.deptId = deptId;
	}
	public Integer getDeleteFlag(){
		return deleteFlag;
	}
	
	public void setDeleteFlag(Integer deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	
	public Integer getQueue(){
		return queue;
	}
	
	public void setQueue(Integer queue){
		this.queue = queue;
	}
}