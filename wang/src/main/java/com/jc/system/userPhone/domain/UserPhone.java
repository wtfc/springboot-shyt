package com.jc.system.userPhone.domain;

import java.util.ArrayList;
import java.util.List;

import com.jc.foundation.domain.BaseBean;
import com.jc.system.security.domain.User;


/**
 * @title GOA2.0使用的个人组别和公共组别应用
 * @description OA移动用户绑定表 实体类
 * @author 
 * @version  2014-11-21
 */

public class UserPhone extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long userId;   /*用户ID*/
	private String status;   /*绑定状态
            ’0‘--启用 
            ’1‘--锁定*/
	private String imeiNo;   /*手机的IMEI码.长度为15或者17位(此处为加密存储，加密后为32或64位字符)*/
	private String deviceModel;
	
	private String displayName;
	private String sex;
	private String deptName;
	private String loginName;
	
	private List<User> userList;
	
	
	public Long getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public String getImeiNo(){
		return imeiNo;
	}
	
	public void setImeiNo(String imeiNo){
		this.imeiNo = imeiNo;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
}