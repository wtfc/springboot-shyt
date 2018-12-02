package com.jc.oa.ic.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;


/**
 * @title HR
 * @description 中间件收到短信以及状态报告之后，会将其存入该表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-29
 */

public class In extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Integer process;   /*预留备用*/
	private String originator;   /*发件人*/
	private String type;   /*短信类型："I"普通短信, "S"短信状态报告*/
	private String encoding;   /*编码格式："7" for 7bit, "8" for 8bit and "U" for Unicode/UCS2*/
	private Date messageDate;   /*短信发送日期时间*/
	private Date receiveDate;   /*接收日期时间*/
	private String text;   /*正文*/
	private String originalRefNo;   /*用于短信状态报告: 此号码对应于外发的短信序号*/
	private Date originalReceiveDate;   /*用于短信状态报告: 对方收到短信的日期时间*/
	private String gatewayId;   /*通道标识，用于标识是哪个通道收到的短信*/
	private Integer state;   /*状态*/
	
	//查询条件
	private Date inDateStart;   /*时间开始*/
	private Date inDateEnd;   /*时间结束*/
	private String userId;   /*发信人Id*/
	private String originatorOut;
	private String currentUserId;
	public Integer getProcess(){
		return process;
	}
	
	public void setProcess(Integer process){
		this.process = process;
	}
	public String getOriginator(){
		return originator;
	}
	
	public void setOriginator(String originator){
		this.originator = originator;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEncoding(){
		return encoding;
	}
	
	public void setEncoding(String encoding){
		this.encoding = encoding;
	}
	public Date getMessageDate(){
		return messageDate;
	}
	
	public void setMessageDate(Date messageDate){
		this.messageDate = messageDate;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getReceiveDate(){
		return receiveDate;
	}
	
	public void setReceiveDate(Date receiveDate){
		this.receiveDate = receiveDate;
	}
	public String getText(){
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	public String getOriginalRefNo(){
		return originalRefNo;
	}
	
	public void setOriginalRefNo(String originalRefNo){
		this.originalRefNo = originalRefNo;
	}
	public Date getOriginalReceiveDate(){
		return originalReceiveDate;
	}
	
	public void setOriginalReceiveDate(Date originalReceiveDate){
		this.originalReceiveDate = originalReceiveDate;
	}
	public String getGatewayId(){
		return gatewayId;
	}
	
	public void setGatewayId(String gatewayId){
		this.gatewayId = gatewayId;
	}
	public Integer getState(){
		return state;
	}
	
	public void setState(Integer state){
		this.state = state;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getInDateStart() {
		return inDateStart;
	}

	public void setInDateStart(Date inDateStart) {
		this.inDateStart = inDateStart;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getInDateEnd() {
		return inDateEnd;
	}

	public void setInDateEnd(Date inDateEnd) {
		this.inDateEnd = inDateEnd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOriginatorOut() {
		return originatorOut;
	}

	public void setOriginatorOut(String originatorOut) {
		this.originatorOut = originatorOut;
	}

	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}
	
	
}