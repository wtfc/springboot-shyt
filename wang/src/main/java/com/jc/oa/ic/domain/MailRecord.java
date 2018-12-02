package com.jc.oa.ic.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;


/**
 * @title 互动交流
 * @description 邮件记录表，记录每条收、发的邮件基本信息 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-17
 */

public class MailRecord extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long mailId;   /*邮件记录表主键*/
	private Long receiveUserId;   /*邮件的接收人，包括正常收件人、抄送接收人、密送接收人*/
	private String receiveMail;   /*收件人(内部用户ID，外部邮箱地址)*/
	private Integer receiveType;   /*接收人类别:0 正常收件人 1 抄送接收人 2 密送接收人 4群发单显 5内部邮件发件人*/
    private Integer readFlag;      /*阅读状态：0未读1已读*/
    private Long folderId;/*文件夹Id，已发送1、草稿箱2、发件箱3、废件箱4,做为初始数据，不可删除*/
    private Integer starMail; /*星标邮件0否1是*/
    private Date readDate;/*邮件阅读时间*/
    private Integer remindFlag;/*提醒状态*/
    private Integer replyFlag;/*回复标记 0-未回复 1-已回复*/ 
    private Integer recoverFlag;/*追回标志0-未追回 1-追回*/
    private Date recoverDate;/*追回时间*/
    private Long receiveUserIdSearch;   /*邮件的接收人，包括正常收件人、抄送接收人、密送接收人*/
	private String receiveMailSearch;   /*收件人(内部用户ID，外部邮箱地址)*/
    
    private String receiveUserName;/*内部邮件：用户名，用于显示，关联用户表*/
	
	public Long getMailId() {
		return mailId;
	}

	public void setMailId(Long mailId) {
		this.mailId = mailId;
	}

	public Long getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(Long receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public String getReceiveMail(){
		return receiveMail;
	}
	
	public void setReceiveMail(String receiveMail){
		this.receiveMail = receiveMail;
	}
	public Integer getReceiveType(){
		return receiveType;
	}
	
	public void setReceiveType(Integer receiveType){
		this.receiveType = receiveType;
	}

	public Integer getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(Integer readFlag) {
		this.readFlag = readFlag;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public Integer getStarMail() {
		return starMail;
	}

	public void setStarMail(Integer starMail) {
		this.starMail = starMail;
	}

	public String getReceiveUserName() {
		return receiveUserName;
	}

	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public Integer getRemindFlag() {
		return remindFlag;
	}

	public void setRemindFlag(Integer remindFlag) {
		this.remindFlag = remindFlag;
	}

	public Integer getReplyFlag() {
		return replyFlag;
	}

	public void setReplyFlag(Integer replyFlag) {
		this.replyFlag = replyFlag;
	}

	public Integer getRecoverFlag() {
		return recoverFlag;
	}

	public void setRecoverFlag(Integer recoverFlag) {
		this.recoverFlag = recoverFlag;
	}

	public Date getRecoverDate() {
		return recoverDate;
	}

	public void setRecoverDate(Date recoverDate) {
		this.recoverDate = recoverDate;
	}

	public Long getReceiveUserIdSearch() {
		return receiveUserIdSearch;
	}

	public void setReceiveUserIdSearch(Long receiveUserIdSearch) {
		this.receiveUserIdSearch = receiveUserIdSearch;
	}

	public String getReceiveMailSearch() {
		return receiveMailSearch;
	}

	public void setReceiveMailSearch(String receiveMailSearch) {
		this.receiveMailSearch = receiveMailSearch;
	}
	
	
}