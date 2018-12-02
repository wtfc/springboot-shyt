package com.jc.android.oa.ic.domain;






/**
 * @title 互动交流
 * @description 邮件表 实体类
 * @author 
 * @version  2014-04-17
 */

public class Mail4M{
	private Long id;  //邮件ID
	private String senderUserName;//发件人姓名
	private String mailTitle;  //邮件标题
	private String senderTime;   //邮件发出时间
	private String mailContent;  //邮件内容
	private String to;/*收件人，用于界面，不做数据库映射*/
	private String cc;/*抄送人，用于界面，不做数据库映射*/
	private String bcc;/*密送人，用于界面，不做数据库映射*/
	private String mrid;/*子表主键id*/
	private String isRead;/*是否已读0未读1已读*/
	//发件人照片URL
	
	public String getSenderUserName() {
		return senderUserName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}
	public String getMailTitle() {
		return mailTitle;
	}
	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}
	
	public String getSenderTime() {
		return senderTime;
	}
	public void setSenderTime(String senderTime) {
		this.senderTime = senderTime;
	}
	public String getMailContent() {
		return mailContent;
	}
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getMrid() {
		return mrid;
	}
	public void setMrid(String mrid) {
		this.mrid = mrid;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
}