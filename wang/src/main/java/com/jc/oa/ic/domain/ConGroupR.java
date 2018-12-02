package com.jc.oa.ic.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title 互动交流
 * @description 联系人与分组中间表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 曹杨
 * @version  2014-05-08
 */

public class ConGroupR extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long contactsId;   /*联系人主键ID*/
	private Long contactsGroupId;   /*联系人分组ID*/
	
	public Long getContactsId() {
		return contactsId;
	}
	public void setContactsId(Long contactsId) {
		this.contactsId = contactsId;
	}
	public Long getContactsGroupId() {
		return contactsGroupId;
	}
	public void setContactsGroupId(Long contactsGroupId) {
		this.contactsGroupId = contactsGroupId;
	}

	
}