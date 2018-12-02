package com.jc.oa.ic.service;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.domain.ContactsGroup;

/**
 * @title 互动交流
 * @description  业务接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-05-08
 */

public interface IContactsGroupService extends IBaseService<ContactsGroup>{

	/**
	 * 方法描述：级联删除联系人组及联系人关系
	 * @param contactsGroup
	 * @return
	 * @throws IcException
	 * @author 曹杨
	 * @version 2014年5月20日下午4:56:27
	 */
	public Integer deleteGroup(ContactsGroup contactsGroup) throws IcException;
	
	/**
	 * 方法描述：修改联系人分组
	 * @param contacts
	 * @return
	 * @throws IcException
	 * @author 曹杨
	 * @version  2014年5月20日下午1:05:30
	 */
	public Integer updateContactsGroup(ContactsGroup contactsGroup) throws IcException;
}