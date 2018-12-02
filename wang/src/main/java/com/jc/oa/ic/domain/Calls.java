package com.jc.oa.ic.domain;

import java.util.Date;

import com.jc.foundation.domain.BaseBean;


/**
 * @title HR
 * @description 中间件接到电话之后会将其存入该表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-29
 */

public class Calls extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Date callDate;   /*电话呼入时间*/
	private String gatewayId;   /*通道标识，用于标识是哪个通道收到的短信*/
	private Integer callerId;   /*对方号码*/
	
	public Date getCallDate(){
		return callDate;
	}
	
	public void setCallDate(Date callDate){
		this.callDate = callDate;
	}
	public String getGatewayId(){
		return gatewayId;
	}
	
	public void setGatewayId(String gatewayId){
		this.gatewayId = gatewayId;
	}
	public Integer getCallerId(){
		return callerId;
	}
	
	public void setCallerId(Integer callerId){
		this.callerId = callerId;
	}
}