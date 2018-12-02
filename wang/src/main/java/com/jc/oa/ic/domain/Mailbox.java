package com.jc.oa.ic.domain;

import java.util.Date;

import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA V2.0 互动交流
 * @description 邮箱设置表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */

public class Mailbox extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String address;   /*邮箱地址*/
	private String receiveService;   /*接收服务器（POP3）*/
	private Integer receivePort;   /*接收端口*/
	private String senderService;   /*发送服务器（SMTP）*/
	private Integer senderPort;   /*发送端口*/
	private String username;   /*登录账号*/
	private String mailPassword;   /*登录密码*/
	private String isDefault;   /*做为内部邮件外发默认邮箱（必须设置账户密码）。0否 1是*/
	private String senderSSL;   /*发送服务器安全连接SSL 默认0不选择 1为选中*/
	private String receiveSSL;   /*接收服务器安全连接SSL 默认0不选择 1为选中*/
	private Long signId;   /*新建签名ID*/
	private Long replySignId;   /*回复转发签名ID*/
    private Date lastReceived; /*邮件最后接收日期*/
    
    private MailSign sign;/*新建邮件签名*/
    private MailSign replySign;/*回复转发签名*/
    private String updateFlag;/*确认是从我的Internet邮箱那的编辑，还是签名设置那的设置默认签名*/
    
    
	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getSenderSSL() {
		return senderSSL;
	}

	public void setSenderSSL(String senderSSL) {
		this.senderSSL = senderSSL;
	}

	public String getReceiveSSL() {
		return receiveSSL;
	}

	public void setReceiveSSL(String receiveSSL) {
		this.receiveSSL = receiveSSL;
	}

	public String getAddress(){
		return address;
	}
	
	public MailSign getSign() {
		return sign;
	}

	public void setSign(MailSign sign) {
		this.sign = sign;
	}

	public MailSign getReplySign() {
		return replySign;
	}

	public void setReplySign(MailSign replySign) {
		this.replySign = replySign;
	}

	public void setAddress(String address){
		this.address = address;
	}
	public String getReceiveService(){
		return receiveService;
	}
	
	public void setReceiveService(String receiveService){
		this.receiveService = receiveService;
	}
	public Integer getReceivePort(){
		return receivePort;
	}
	
	public void setReceivePort(Integer receivePort){
		this.receivePort = receivePort;
	}
	public String getSenderService(){
		return senderService;
	}
	
	public void setSenderService(String senderService){
		this.senderService = senderService;
	}
	public Integer getSenderPort(){
		return senderPort;
	}
	
	public void setSenderPort(Integer senderPort){
		this.senderPort = senderPort;
	}
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	public String getMailPassword(){
		return mailPassword;
	}
	
	public void setMailPassword(String mailPassword){
		this.mailPassword = mailPassword;
	}
	public String getIsDefault(){
		return isDefault;
	}
	
	public void setIsDefault(String isDefault){
		this.isDefault = isDefault;
	}
	public Long getSignId(){
		return signId;
	}
	
	public void setSignId(Long signId){
		this.signId = signId;
	}
	public Long getReplySignId(){
		return replySignId;
	}
	
	public void setReplySignId(Long replySignId){
		this.replySignId = replySignId;
	}

	public Date getLastReceived() {
		return lastReceived;
	}

	public void setLastReceived(Date lastReceived) {
		this.lastReceived = lastReceived;
	}
	
}