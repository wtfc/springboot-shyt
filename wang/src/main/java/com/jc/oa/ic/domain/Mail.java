package com.jc.oa.ic.domain;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.content.domain.Attach;


/**
 * @title 互动交流
 * @description 邮件表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-17
 */

public class Mail extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long contactsId;   /*邮件记录表主键*/
	private Long mailboxId;   /*邮件记录表主键*/
	private String mailTitle;   /*邮件标题*/
	private String mailContent;   /*邮件内容*/
	private Long senderUserId;   /*发件人ID：内部发件必填*/
	private String senderMail;   /*发件人邮箱：外部邮箱地址，外部发件必填*/
	private String sendType;/*发送方式0-富文本编辑器1-WebOffice*/
	private Integer isfile;   /*是否有附件：0否 1是*/
	private Integer starMail;   /*星标邮件：0否 1是*/
	private Integer smsAlert;   /*短信提醒：0否 1是*/
	private Integer signature;   /*签名：0否 1是*/
	private Integer pressing;   /*紧急：0否 1是*/
	private Integer encryption;   /*加密：0否 1是*/
	private String encryptionPass;   /*加密密码：加密（ENCRYPTION）值为1时，该项必填*/
	private Integer replyTexting;   /*回复邮件提醒：0否 1是*/
	private Integer replyTextingTime;   /*回复邮件提醒时间：回复邮件提醒（REPLY_TEXTING）值为1时,该项必填 值为:1小时，2小时，3小时，4小时......*/
	private Integer mailStatus;   /*外部邮件接收状态：0未接收 1已接收 */ 
	private Date senderTime;   /*邮件发送时间*/
	private Date receiveTime2;   /*邮件接收时间（接收人查看时间）*/
    private String messageId;  /*邮件唯一标识*/
	private Long mailFolderId;/*文件夹Id，已发送1、草稿箱2、发件箱3、废件箱4,做为初始数据，不可删除*/
	
	private List<MailRecord> receivers;/*收件人列表，用于代码调用，不做数据库映射*/
	private List<Mailbox> mailbox;/*邮箱列表，用于代码调用，不做数据库映射*/
	private String fileids;//附件Id列表，用于代码调用，不做数据库映射*/
	private List<Attach> attachs;/*附件对象列表，用于代码调用，不做数据库映射*/
	private Integer forward;/*导航方向，>0,取下一封，<=0取上一封*/
	private String innerTo;/*用于界面，不做数据库映射*/
	private String innerCc;/*用于界面，不做数据库映射*/
	private String innerBcc;/*用于界面，不做数据库映射*/
	private String innerSs;/*用于界面，不做数据库映射*/
	private String toValid;/*外部收件校验，用于界面，不做数据库映射*/
	private String to;/*收件人，用于界面，不做数据库映射*/
	private String cc;/*抄送人，用于界面，不做数据库映射*/
	private String bcc;/*密送人，用于界面，不做数据库映射*/
	private String showSingle;/*群发单显，用于界面，不做数据库映射*/
	private String showSingleValid;/*群发单显，外部收件校验，用于界面，不做数据库映射*/
	private Integer reply;/*回复操作选项，0为回复发件人，1为回复全部,用于界面，不做数据库映射*/
	private MailRecord receiver;/*用于查询条件的接收人信息，不做数据库映射*/
	private Date searchReceiveTimeBegin;/*搜索条件：开始接收时间*/
	private Date searchReceiveTimeEnd;/*搜索条件：结束接收时间*/
	private String senderUserName;/*回显内部用户名：发件人*/
	private boolean wrongPassword;/*查看邮件密码错误*/
	private String returnURL;/*写邮件、查看邮件后的回调地址*/
	private int index;/*查询方法用到的位置索引*/
	private boolean showPre=true;/*是否显示上一封*/
	private boolean showNext=true;/*是否显示下一封*/
	private String  consigneeStatus;/*查看收信人状态*/
	private String receiveMail;//接收人邮箱地址（用于外网邮件查询）
	private String excludeFolderId;
	private String mrid;/*在表主键id*/
	private String isOnline;/*用户是否在线 0代表不在线 1代表在线*/ 
	private String isHaveReceiveUser;/*是否有收件人 0没有 1有*/
	private String isRead;/*是否已读0未读1已读*/
	private String smsReceiver;/*短信提醒接收人*/
	private String delattachIds;//删除附件Id
	private Long mailReplyId;/*回复邮件id，用于代码调用，不做数据库映射*/
	private String isAllMail;/*是否是全部邮件*/
	private String isHaveAttach;/*用于草稿编辑查看附件是否存在，来改变mail表isfile状态，改变列表显示，0代表没有附件，1代表有附件*/
	private String receiveUsers;/*用户查询发件箱中收件人*/
	private String toSs;/*是否是全部邮件*/
	private String newTo;/*带有状态的收件人，用于界面，不做数据库映射*/
	private String newCc;/*带有状态的抄送人，用于界面，不做数据库映射*/
	private String newBcc;/*带有状态的密送人，用于界面，不做数据库映射*/
	private String newSs;/*带有状态的群发单显，用于界面，不做数据库映射*/
	private String mailEasyTitle;/*此条件用于查询收件人和标题*/
	public String getToSs() {
		return toSs;
	}

	public void setToSs(String toSs) {
		this.toSs = toSs;
	}

	public String getReceiveMail() {
		return receiveMail;
	}

	public void setReceiveMail(String receiveMail) {
		this.receiveMail = receiveMail;
	}

	public String getSenderUserName() {
		return senderUserName;
	}

	public boolean isShowPre() {
		return showPre;
	}

	public void setShowPre(boolean showPre) {
		this.showPre = showPre;
	}

	public boolean isShowNext() {
		return showNext;
	}

	public void setShowNext(boolean showNext) {
		this.showNext = showNext;
	}

	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
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

	public String getShowSingle() {
		return showSingle;
	}

	public void setShowSingle(String showSingle) {
		this.showSingle = showSingle;
	}

	public Long getContactsId(){
		return contactsId;
	}
	
	public void setContactsId(Long contactsId){
		this.contactsId = contactsId;
	}
	public Long getMailboxId(){
		return mailboxId;
	}
	
	public void setMailboxId(Long mailboxId){
		this.mailboxId = mailboxId;
	}
	public String getMailTitle(){
		return mailTitle;
	}
	
	public void setMailTitle(String mailTitle){
		this.mailTitle = mailTitle;
	}
	public String getMailContent(){
		return mailContent;
	}
	
	public void setMailContent(String mailContent){
		this.mailContent = mailContent;
	}
	public Long getSenderUserId(){
		return senderUserId;
	}
	
	public void setSenderUserId(Long senderUserId){
		this.senderUserId = senderUserId;
	}
	public String getSenderMail(){
		return senderMail;
	}
	
	public void setSenderMail(String senderMail){
		this.senderMail = senderMail;
	}
	public Integer getIsfile(){
		return isfile;
	}
	
	public void setIsfile(Integer isfile){
		this.isfile = isfile;
	}
	public Integer getStarMail(){
		return starMail;
	}
	
	public void setStarMail(Integer starMail){
		this.starMail = starMail;
	}
	public Integer getSmsAlert(){
		return smsAlert;
	}
	
	public void setSmsAlert(Integer smsAlert){
		this.smsAlert = smsAlert;
	}
	public Integer getSignature(){
		return signature;
	}
	
	public void setSignature(Integer signature){
		this.signature = signature;
	}
	public Integer getPressing(){
		return pressing;
	}
	
	public void setPressing(Integer pressing){
		this.pressing = pressing;
	}
	public Integer getEncryption(){
		return encryption;
	}
	
	public void setEncryption(Integer encryption){
		this.encryption = encryption;
	}
	public String getEncryptionPass(){
		return encryptionPass;
	}
	
	public void setEncryptionPass(String encryptionPass){
		this.encryptionPass = encryptionPass;
	}
	public Integer getReplyTexting(){
		return replyTexting;
	}
	
	public void setReplyTexting(Integer replyTexting){
		this.replyTexting = replyTexting;
	}
	
	public Integer getReplyTextingTime() {
		return replyTextingTime;
	}

	public void setReplyTextingTime(Integer replyTextingTime) {
		this.replyTextingTime = replyTextingTime;
	}

	public Integer getMailStatus(){
		return mailStatus;
	}
	
	public void setMailStatus(Integer mailStatus){
		this.mailStatus = mailStatus;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getSenderTime(){
		return senderTime;
	}
	
	public void setSenderTime(Date senderTime){
		this.senderTime = senderTime;
	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getReceiveTime2(){
		return receiveTime2;
	}
	
	public void setReceiveTime2(Date receiveTime2){
		this.receiveTime2 = receiveTime2;
	}

	public List<MailRecord> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<MailRecord> receivers) {
		this.receivers = receivers;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Long getMailFolderId() {
		return mailFolderId;
	}

	public void setMailFolderId(Long mailFolderId) {
		this.mailFolderId = mailFolderId;
	}

	public Integer getForward() {
		return forward;
	}

	public void setForward(Integer forward) {
		this.forward = forward;
	}

	public Integer getReply() {
		return reply;
	}

	public void setReply(Integer reply) {
		this.reply = reply;
	}

	public MailRecord getReceiver() {
		return receiver;
	}

	public void setReceiver(MailRecord receiver) {
		this.receiver = receiver;
	}

	public String getInnerTo() {
		return innerTo;
	}

	public void setInnerTo(String innerTo) {
		this.innerTo = innerTo;
	}

	public String getInnerCc() {
		return innerCc;
	}

	public void setInnerCc(String innerCc) {
		this.innerCc = innerCc;
	}

	public String getInnerBcc() {
		return innerBcc;
	}

	public void setInnerBcc(String innerBcc) {
		this.innerBcc = innerBcc;
	}

	public String getInnerSs() {
		return innerSs;
	}

	public void setInnerSs(String innerSs) {
		this.innerSs = innerSs;
	}

	public Date getSearchReceiveTimeBegin() {
		return searchReceiveTimeBegin;
	}

	public void setSearchReceiveTimeBegin(Date searchReceiveTimeBegin) {
		this.searchReceiveTimeBegin = searchReceiveTimeBegin;
	}

	public Date getSearchReceiveTimeEnd() {
		return searchReceiveTimeEnd;
	}

	public void setSearchReceiveTimeEnd(Date searchReceiveTimeEnd) {
		this.searchReceiveTimeEnd = searchReceiveTimeEnd;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public boolean isWrongPassword() {
		return wrongPassword;
	}

	public void setWrongPassword(boolean wrongPassword) {
		this.wrongPassword = wrongPassword;
	}

	public String getReturnURL() {
		return returnURL;
	}

	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}


	public String getFileids() {
		return fileids;
	}

	public void setFileids(String fileids) {
		this.fileids = fileids;
	}

	public List<Attach> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<Attach> attachs) {
		this.attachs = attachs;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getConsigneeStatus() {
		return consigneeStatus;
	}

	public void setConsigneeStatus(String consigneeStatus) {
		this.consigneeStatus = consigneeStatus;
	}

	public List<Mailbox> getMailbox() {
		return mailbox;
	}

	public void setMailbox(List<Mailbox> mailbox) {
		this.mailbox = mailbox;
	}

	public String getExcludeFolderId() {
		return excludeFolderId;
	}

	public void setExcludeFolderId(String excludeFolderId) {
		this.excludeFolderId = excludeFolderId;
	}

	public String getMrid() {
		return mrid;
	}

	public void setMrid(String mrid) {
		this.mrid = mrid;
	}

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	public String getIsHaveReceiveUser() {
		return isHaveReceiveUser;
	}

	public void setIsHaveReceiveUser(String isHaveReceiveUser) {
		this.isHaveReceiveUser = isHaveReceiveUser;
	}

	public String getSmsReceiver() {
		return smsReceiver;
	}

	public void setSmsReceiver(String smsReceiver) {
		this.smsReceiver = smsReceiver;
	}

	public String getToValid() {
		return toValid;
	}

	public void setToValid(String toValid) {
		this.toValid = toValid;
	}

	public String getShowSingleValid() {
		return showSingleValid;
	}

	public void setShowSingleValid(String showSingleValid) {
		this.showSingleValid = showSingleValid;
	}

	public String getDelattachIds() {
		return delattachIds;
	}

	public void setDelattachIds(String delattachIds) {
		this.delattachIds = delattachIds;
	}

	public Long getMailReplyId() {
		return mailReplyId;
	}

	public void setMailReplyId(Long mailReplyId) {
		this.mailReplyId = mailReplyId;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getIsAllMail() {
		return isAllMail;
	}

	public void setIsAllMail(String isAllMail) {
		this.isAllMail = isAllMail;
	}

	public String getIsHaveAttach() {
		return isHaveAttach;
	}

	public void setIsHaveAttach(String isHaveAttach) {
		this.isHaveAttach = isHaveAttach;
	}

	public String getReceiveUsers() {
		return receiveUsers;
	}

	public void setReceiveUsers(String receiveUsers) {
		this.receiveUsers = receiveUsers;
	}

	public String getNewTo() {
		return newTo;
	}

	public void setNewTo(String newTo) {
		this.newTo = newTo;
	}

	public String getNewCc() {
		return newCc;
	}

	public void setNewCc(String newCc) {
		this.newCc = newCc;
	}

	public String getNewBcc() {
		return newBcc;
	}

	public void setNewBcc(String newBcc) {
		this.newBcc = newBcc;
	}

	public String getNewSs() {
		return newSs;
	}

	public void setNewSs(String newSs) {
		this.newSs = newSs;
	}

	public String getMailEasyTitle() {
		return mailEasyTitle;
	}

	public void setMailEasyTitle(String mailEasyTitle) {
		this.mailEasyTitle = mailEasyTitle;
	}	
	
}