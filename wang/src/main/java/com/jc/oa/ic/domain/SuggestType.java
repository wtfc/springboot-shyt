package com.jc.oa.ic.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA V2.0 互动交流
 * @description 建议类别表：通过设置建议的类别，确定接收人范围 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */

public class SuggestType extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String typeName;   /*类别名称*/
	private String isFixed;	   /*是否固定接收人 默认1为固定，0为不固定*/
	private String recipientIds;/*接收人Id串*/
	
	private String userName;//显示接收人名称的临时字段，不存在建议类别表中
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRecipientIds() {
		return recipientIds;
	}

	public void setRecipientIds(String recipientIds) {
		this.recipientIds = recipientIds;
	}

	public String getIsFixed() {
		return isFixed;
	}

	public void setIsFixed(String isFixed) {
		this.isFixed = isFixed;
	}

	public String getTypeName(){
		return typeName;
	}
	
	public void setTypeName(String typeName){
		this.typeName = typeName;
	}
}