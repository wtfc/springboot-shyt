package com.jc.oa.ic.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA V2.0 互动交流
 * @description 建议接收人表，根据建议类别确定接收人范围 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */

public class SugRecipient extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long suggestId;   /*建议ID*/
	private Long recipientId;   /*接收人ID*/
	
	private String recipientNames;/*回显使用的用户名称*/
	
	

	public String getRecipientNames() {
		return recipientNames;
	}

	public void setRecipientNames(String recipientNames) {
		this.recipientNames = recipientNames;
	}

	public Long getSuggestId(){
		return suggestId;
	}
	
	public void setSuggestId(Long suggestId){
		this.suggestId = suggestId;
	}
	public Long getRecipientId(){
		return recipientId;
	}
	
	public void setRecipientId(Long recipientId){
		this.recipientId = recipientId;
	}
}