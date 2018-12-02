package com.jc.oa.archive.domain;

import java.util.Date;
import java.math.BigDecimal;
import com.jc.system.common.util.DateUtils;
import com.jc.foundation.domain.BaseBean;


/**
 * @title  GOA2.0源代码
 * @description  实体类
 * @author 
 * @version  2014-06-19
 */

public class SubPermission extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long permissionId;   /**/
	private Integer dataType;   /*0 用户，  1 部门*/
	private Long controlId;   /*如果选择的是用户，存用户id，如果选择的是部门存部门id*/
	private String controlName;   /*如果选择的是用户，存用户名称，如果选择的是部门存部门名称*/

	public Long getPermissionId(){
		return permissionId;
	}
	
	public void setPermissionId(Long permissionId){
		this.permissionId = permissionId;
	}
	
	public Integer getDataType(){
		return dataType;
	}
	
	public void setDataType(Integer dataType){
		this.dataType = dataType;
	}
	
	public Long getControlId(){
		return controlId;
	}
	
	public void setControlId(Long controlId){
		this.controlId = controlId;
	}
	
	public String getControlName(){
		return controlName;
	}
	
	public void setControlName(String controlName){
		this.controlName = controlName;
	}
	
}