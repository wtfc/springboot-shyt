package com.jc.system.security.domain;

import com.jc.foundation.domain.BaseBean;

/**
 * @title GOA2.0
 * @description 用户表（公共）实体类
 * @version  2014-04-10
 *
 */
public class SysUser extends BaseBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String code;   /*用户编号*/
	private String displayName;   /*用户显示名*/
	private String loginName;   /*用户登录名*/
	private String password;   /*密码*/
	private String sex;   /*用户性别*/
	private String source;   /*用户来源*/

	public String getCode(){
		return code;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	public String getDisplayName(){
		return displayName;
	}
	
	public void setDisplayName(String displayName){
		this.displayName = displayName;
	}
	public String getLoginName(){
		return loginName;
	}
	
	public void setLoginName(String loginName){
		this.loginName = loginName;
	}
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	public String getSex(){
		return sex;
	}
	
	public void setSex(String sex){
		this.sex = sex;
	}
	public String getSource(){
		return source;
	}
	
	public void setSource(String source){
		this.source = source;
	}
}