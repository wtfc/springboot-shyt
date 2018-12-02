package com.jc.oa.ic.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title 互动交流
 * @description 联系人分组表，默认常用联系人，最近联系人 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 曹杨
 * @version  2014-05-08
 */

public class ContactsGroup extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String groupName; /* 分组名称 */
	private String groupNameOld;/*修改前联系人组名称不作数据库映射*/
	private String description; /* 描述 */

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGroupNameOld() {
		return groupNameOld;
	}

	public void setGroupNameOld(String groupNameOld) {
		this.groupNameOld = groupNameOld;
	}
	
}