package com.jc.oa.ic.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;


/**
 * @title GOAIC
 * @description 邮件联系人表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 曹杨
 * @version  2014-05-04
 */

public class Contacts extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String userName;   /*联系人姓名，方便查询*/
	private String userNameOld;/*修改前联系人名称不作数据库映射*/
	private String mail;   /*类别（TYPE）为2 时该项值必填 为外部邮箱。*/
	private String mailOld;/*修改前邮箱名称*/
	private String contactsType;   /*区分联系人 1内部 2外部*/
	private String simpleName;   /*自定义简称，方便查询*/
	private String phone;   /*手机号码，方便查询*/
	private String phoneOld;/*修改前手机号码*/
	private String sex;   /*性别*/
    
	/*邮件联系人分组属性*/
	private Long groupId;/*分组主键ID*/
	private String groupName;   /*分组名称*/
	private String description;   /*描述*/
	private Long groupRId;/*联系人分组关系主键ID*/
   
	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	public String getMail(){
		return mail;
	}
	
	public void setMail(String mail){
		this.mail = mail;
	}
	public String getContactsType(){
		return contactsType;
	}
	
	public void setContactsType(String contactsType){
		this.contactsType = contactsType;
	}
	public String getSimpleName(){
		return simpleName;
	}
	
	public void setSimpleName(String simpleName){
		this.simpleName = simpleName;
	}
	public String getPhone(){
		return phone;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	public String getSex(){
		return sex;
	}
	public String getSexValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("sex", this.getSex()) == null ? "" : dicManager.getDic("sex", this.getSex()).getValue();
	}
	public void setSex(String sex){
		this.sex = sex;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getGroupRId() {
		return groupRId;
	}

	public void setGroupRId(Long groupRId) {
		this.groupRId = groupRId;
	}

	public String getUserNameOld() {
		return userNameOld;
	}

	public void setUserNameOld(String userNameOld) {
		this.userNameOld = userNameOld;
	}

	public String getMailOld() {
		return mailOld;
	}

	public void setMailOld(String mailOld) {
		this.mailOld = mailOld;
	}

	public String getPhoneOld() {
		return phoneOld;
	}

	public void setPhoneOld(String phoneOld) {
		this.phoneOld = phoneOld;
	}
	
	
}