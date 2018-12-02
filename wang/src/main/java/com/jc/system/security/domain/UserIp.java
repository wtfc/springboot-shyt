package com.jc.system.security.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA2.0
 * @description 用户Ip绑定 实体类
 * @version  2014-04-30
 */

public class UserIp extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long userId;   /*用户ID*/
	private Integer bindType;   /*绑定类型*/
	private String userStartIp;   /*用户要绑定的开始IP*/
	private String userEndIp;   /*用户要绑定的结束IP*/
	private String userIpDesc;   /*描述*/
	
	//2014.4.30 chz 增加用户中文名 、 部门中文名称 临时字段 解决列表显示 查询问题---start---
	private String userName;
	private String deptName;
	//2014.4.30 chz 增加用户中文名 、 部门中文名称 临时字段 解决列表显示 查询问题---end---

	public Long getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	public Integer getBindType(){
		return bindType;
	}
	
	public void setBindType(Integer bindType){
		this.bindType = bindType;
	}
	public String getUserStartIp(){
		return userStartIp;
	}
	
	public void setUserStartIp(String userStartIp){
		this.userStartIp = userStartIp;
	}
	public String getUserEndIp(){
		return userEndIp;
	}
	
	public void setUserEndIp(String userEndIp){
		this.userEndIp = userEndIp;
	}
	public String getUserIpDesc(){
		return userIpDesc;
	}
	
	public void setUserIpDesc(String userIpDesc){
		this.userIpDesc = userIpDesc;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	
}