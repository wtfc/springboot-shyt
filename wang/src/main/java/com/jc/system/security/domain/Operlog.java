package com.jc.system.security.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;


/**
 * @title GOA2.0
 * @description 操作日志表 实体类
 * @author 
 * @version  2014-05-04
 */

public class Operlog extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long userId;   /*登录用户ID*/
	private String loginName;   /*登录名*/
	private String displayName;   /*显示名*/
	private String ip;   /*IP地址*/
	private String isAdmin;   /*是否管理员操作 0-否 1-是*/
	private String operDesc;   /*操作描述*/
	private Date operTime;   /*操作时间*/
	private String funName;   /*方法名：中文描述*/
	private String operType;	/*操作类型 ： 添加 修改 删除 查询*/
	
	//2014.5.4 chz 增加临时字段----start ---解决列表字段显示及查询问题
	private String deptName; /*部门名称*/
	private Date operStartTime; /*操作开始时间*/
	private Date operEndTime; /*操作结束时间*/
	//2014.5.4 chz 增加临时字段----end ---

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
	public String getIsAdmin(){
		return isAdmin;
	}
	
	public void setIsAdmin(String isAdmin){
		this.isAdmin = isAdmin;
	}
	public String getOperDesc(){
		return operDesc;
	}
	
	public void setOperDesc(String operDesc){
		this.operDesc = operDesc;
	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getOperTime(){
		return operTime;
	}
	
	public void setOperTime(Date operTime){
		this.operTime = operTime;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getOperStartTime() {
		return operStartTime;
	}

	public void setOperStartTime(Date operStartTime) {
		this.operStartTime = operStartTime;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getOperEndTime() {
		return operEndTime;
	}

	public void setOperEndTime(Date operEndTime) {
		this.operEndTime = operEndTime;
	}
	
	public String getFunName(){
		return funName;
	}
	
	public void setFunName(String funName){
		this.funName = funName;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}
	
}