package com.jc.system.security.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA2.0
 * @description 用户其他部门任职表实体类
 * @version  2014-04-14
 *
 */
public class SysOtherDepts extends BaseBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private Long userId;   /*用户ID*/
	private Long deptId;   /*用户所在部门*/
	private String dutyId;   /*用户职务*/
	private Integer queue;   /*排序*/
	
	private String deptName;

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
	public String getDutyId(){
		return dutyId;
	}
	
	public void setDutyId(String dutyId){
		this.dutyId = dutyId;
	}
	public Integer getQueue(){
		return queue;
	}
	
	public void setQueue(Integer queue){
		this.queue = queue;
	}
	public Integer getDeleteFlag(){
		return deleteFlag;
	}
	
	public void setDeleteFlag(Integer deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}