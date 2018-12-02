package com.jc.system.security.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;


/**
 * @title GOA2.0
 * @description 登录日志表 实体类
 * @author 
 * @version  2014-05-04
 */

public class Loginlog extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long userId;   /*登录用户ID*/
	private String loginName;   /*登录名*/
	private String displayName;   /*显示名*/
	private String ip;   /*IP地址*/
	private Date loginTime;   /*登录时间*/
	private Date logoutTime;   /*退出时间*/
	private Date loginTimeBegin; /*登录时间--查询开始时间*/
	private Date loginTimeEnd; /*登录时间--查询结束时间*/
	private Date logoutTimeBegin; /*退出时间--查询开始时间*/
	private Date logoutTimeEnd; /*退出时间--查询结束时间*/
	private int loginstatus; /*登录状态--0登录 1退出 2全部*/
	private int loginDevice;/*登录设备--1PC机 2移动设备*/

	public Long getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	public String getLoginName(){
		return loginName;
	}
	
	public void setLoginName(String loginName){
		this.loginName = loginName;
	}
	public String getDisplayName(){
		return displayName;
	}
	
	public void setDisplayName(String displayName){
		this.displayName = displayName;
	}
	public String getIp(){
		return ip;
	}
	
	public void setIp(String ip){
		this.ip = ip;
	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getLoginTime(){
		return loginTime;
	}
	
	public void setLoginTime(Date loginTime){
		this.loginTime = loginTime;
	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getLogoutTime(){
		return logoutTime;
	}
	
	public void setLogoutTime(Date logoutTime){
		this.logoutTime = logoutTime;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getLoginTimeBegin() {
		return loginTimeBegin;
	}

	public void setLoginTimeBegin(Date loginTimeBegin) {
		this.loginTimeBegin = loginTimeBegin;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getLoginTimeEnd() {
		return loginTimeEnd;
	}

	public void setLoginTimeEnd(Date loginTimeEnd) {
		this.loginTimeEnd = loginTimeEnd;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getLogoutTimeBegin() {
		return logoutTimeBegin;
	}

	public void setLogoutTimeBegin(Date logoutTimeBegin) {
		this.logoutTimeBegin = logoutTimeBegin;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getLogoutTimeEnd() {
		return logoutTimeEnd;
	}

	public void setLogoutTimeEnd(Date logoutTimeEnd) {
		this.logoutTimeEnd = logoutTimeEnd;
	}

	public int getLoginstatus() {
		return loginstatus;
	}

	public void setLoginstatus(int loginstatus) {
		this.loginstatus = loginstatus;
	}

	public int getLoginDevice() {
		return loginDevice;
	}

	public void setLoginDevice(int loginDevice) {
		this.loginDevice = loginDevice;
	}
	
}