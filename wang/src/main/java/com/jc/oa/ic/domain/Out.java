package com.jc.oa.ic.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;
import com.jc.system.security.util.UserUtils;
/**
 * 
 * @title GOA V2.0
 * @description 表用于存放待发送的短信以及短信的发送结果,将需要发送的短信插入到该表，中间件会自动发送，并将发送结果记录到 实体类
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 宋海涛
 * @version  2014年5月6日下午7:10:17
 */
public class Out extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String recipient;   /*收件人手机号码，前面不要加 "+" 或者0*/
	private String type;   /*短信类型. "O"普通短信, "W" wappush 收件人手机号码，前面不要加"+" 或者0*/
	private String text;   /*正文*/
	private String wapUrl;   /*Wap短信URL地址*/
	private Date wapExpiryDate;   /*Wap短信有效期，默7天*/
	private String wapSignal;   /*"N" NONE, "L" LOW, "M" MEDIUM, "H" HIGH, "D" DELETE*/
	private String originator;   /*发件人*/
	private String encoding;   /*编码格式："7" for 7bit, "8" for 8bit and "U" for Unicode/UCS2*/
	private Integer statusReport;   /*状态报告：设为 1需要短信状态报告，默认为0*/
	private Integer flashSms;   /*NOT NULL, Default value: 0*/
	private Integer srcPort;   /*源端口 (对彩铃有效)*/
	private Integer dstPort;   /*目的端口 (对彩铃有效)*/
	private Date sentDate;   /*发送时间， Jdsmsserver发送时会更新这个时间*/
	private String refNo;   /*短信对应ID*/
	private Integer priority;   /*优先级： 低：负数 普通：0 高：正数*/
	private String status;   /*短信状态："U" :未发送, "Q" : 排队中, "S" : 已发送, "F" : 失败*/
	private Integer errors;   /*重发次数*/
	private String gatewayId;   /*通道标识，用于标识是哪个通道收到的短信*/
	private Integer remainingQty;   /*剩余短信条数*/
	private Integer sendedQty;   /*已发送条数*/
	private Date createDate2;   /*记录插入数据库的时间*/
	
	//字典转换
	private IDicManager dicManager = new DicManagerImpl();
	//短信扩展属性
	private String userId;   /*收件人id*/
	private String outLinkman;/*外部联系人*/
	private String sendType; /*短信类别：如会议、流程等，存储字典值*/
	private String addName;   /*短信内容中是否添加签名*/
	private String smmscheduler;/*是否定时发送*/
	private Date outDateStart;   /*时间开始*/
	private Date outDateEnd;   /*时间结束*/
	private Integer num;/*发送数量*/
	private Integer residue;/*剩余数量*/
	private String sendUser;/*发送人*/
	private String receiveUser;/*接收人*/
	private Date statisticSendDate;/*统计查询时间*/
	private String recipientOut;/*用户外部联系人查询*/
	private String currentUserId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getSendType() {
		return sendType;
	}
	public String getSendTypeValue() {
		String sendTypeValue="";
		if(this.getSendType()!=null&&!"".equals(this.getSendType())&&dicManager.getDic("sendType", this.getSendType())!=null){
			sendTypeValue = dicManager.getDic("sendType", this.getSendType()).getValue();
		}
		return sendTypeValue;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getAddName() {
		return addName;
	}

	public void setAddName(String addName) {
		this.addName = addName;
	}

	public String getRecipient(){
		return recipient;
	}
	
	public void setRecipient(String recipient){
		this.recipient = recipient;
	}
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	public String getText(){
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	public String getWapUrl(){
		return wapUrl;
	}
	
	public void setWapUrl(String wapUrl){
		this.wapUrl = wapUrl;
	}
	public Date getWapExpiryDate(){
		return wapExpiryDate;
	}
	
	public void setWapExpiryDate(Date wapExpiryDate){
		this.wapExpiryDate = wapExpiryDate;
	}
	public String getWapSignal(){
		return wapSignal;
	}
	
	public void setWapSignal(String wapSignal){
		this.wapSignal = wapSignal;
	}
	public String getOriginator(){
		return originator;
	}
	
	public void setOriginator(String originator){
		this.originator = originator;
	}
	public String getEncoding(){
		return encoding;
	}
	
	public void setEncoding(String encoding){
		this.encoding = encoding;
	}
	public Integer getStatusReport(){
		return statusReport;
	}
	
	public void setStatusReport(Integer statusReport){
		this.statusReport = statusReport;
	}
	public Integer getFlashSms(){
		return flashSms;
	}
	
	public void setFlashSms(Integer flashSms){
		this.flashSms = flashSms;
	}
	public Integer getSrcPort(){
		return srcPort;
	}
	
	public void setSrcPort(Integer srcPort){
		this.srcPort = srcPort;
	}
	public Integer getDstPort(){
		return dstPort;
	}
	
	public void setDstPort(Integer dstPort){
		this.dstPort = dstPort;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getSentDate(){
		return sentDate;
	}
	
	public void setSentDate(Date sentDate){
		this.sentDate = sentDate;
	}
	public String getRefNo(){
		return refNo;
	}
	
	public void setRefNo(String refNo){
		this.refNo = refNo;
	}
	public Integer getPriority(){
		return priority;
	}
	
	public void setPriority(Integer priority){
		this.priority = priority;
	}
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	public Integer getErrors(){
		return errors;
	}
	
	public void setErrors(Integer errors){
		this.errors = errors;
	}
	public String getGatewayId(){
		return gatewayId;
	}
	
	public void setGatewayId(String gatewayId){
		this.gatewayId = gatewayId;
	}
	public Integer getRemainingQty(){
		return remainingQty;
	}
	
	public void setRemainingQty(Integer remainingQty){
		this.remainingQty = remainingQty;
	}
	public Integer getSendedQty(){
		return sendedQty;
	}
	
	public void setSendedQty(Integer sendedQty){
		this.sendedQty = sendedQty;
	}
	public Date getCreateDate2(){
		return createDate2;
	}
	
	public void setCreateDate2(Date createDate2){
		this.createDate2 = createDate2;
	}


	public String getOutLinkman() {
		return outLinkman;
	}

	public void setOutLinkman(String outLinkman) {
		this.outLinkman = outLinkman;
	}
	
	public String getSmmscheduler() {
		return smmscheduler;
	}
	public void setSmmscheduler(String smmscheduler) {
		this.smmscheduler = smmscheduler;
	}
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getOutDateStart() {
		return outDateStart;
	}
	public void setOutDateStart(Date outDateStart) {
		this.outDateStart = outDateStart;
	}
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getOutDateEnd() {
		return outDateEnd;
	}
	public void setOutDateEnd(Date outDateEnd) {
		this.outDateEnd = outDateEnd;
	}
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	
	public Integer getResidue() {
		return residue;
	}
	public void setResidue(Integer residue) {
		this.residue = residue;
	}
	
	public String getSendUser() {
		String sendUser = "";
		if(super.getCreateUser()>0&&UserUtils.getUser(super.getCreateUser())!=null){
			sendUser = UserUtils.getUser(super.getCreateUser()).getDisplayName();
		}
		return sendUser;
	}
	
	public String getReceiveUser() {
		return receiveUser;
	}
	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}
	
	public Date getStatisticSendDate() {
		return statisticSendDate;
	}
	public void setStatisticSendDate(Date statisticSendDate) {
		this.statisticSendDate = statisticSendDate;
	}
	public String getRecipientOut() {
		return recipientOut;
	}
	public void setRecipientOut(String recipientOut) {
		this.recipientOut = recipientOut;
	}
	public String getCurrentUserId() {
		return currentUserId;
	}
	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}
	
	
	
}