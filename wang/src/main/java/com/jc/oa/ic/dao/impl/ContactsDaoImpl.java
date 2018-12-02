package com.jc.oa.ic.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.foundation.domain.PageManager;
import com.jc.oa.ic.dao.IContactsDao;
import com.jc.oa.ic.domain.Contacts;
import com.jc.system.CustomException;

import java.util.List;

/**
 * @title GOAIC
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 曹杨
 * @version  2014-05-04
 */
@Repository
public class ContactsDaoImpl extends BaseDaoImpl<Contacts> implements IContactsDao{

	public ContactsDaoImpl(){}
	/**
	 * 方法描述：联合用户分组表查询
	 * @param contacts
	 * @param pageManager
	 * @return
	 * @throws CustomException
	 * @author 曹杨
	 * @version  2014年5月7日上午8:43:47
	 * @see
	 */
	@Override
	public PageManager queryContacts(Contacts contacts, PageManager pageManager){
		return super.queryByPage(contacts, pageManager, "queryContactsCount", "queryContacts");
	}
	/**
	 * 方法描述：联合联系组表获取联系人信息
	 * @param contacts
	 * @return
	 * @throws CustomException
	 * @author 曹杨
	 * @version  2014年5月13日下午1:12:03
	 * @see
	 */
	@Override
	public Contacts getContacts(Contacts contacts) throws CustomException {
		return template.selectOne(getNameSpace(contacts) + ".queryContacts",contacts);
	}

	/**
	 * 方法描述：外部联系人组件查询方法
	 * @param contacts
	 * @return
	 * @throws CustomException
	 * @author 孙鹏
	 * @version  2014年5月13日下午1:12:03
	 * @see
	 */
	public Contacts queryOutSideUser(Contacts contacts) throws CustomException {
		return template.selectOne(getNameSpace(contacts) + ".queryOutSideUser",contacts);
	}
	/**
	 * 方法描述：获得外部联系人树（外部联系组件使用）
	 * @param contacts
	 * @return
	 * @throws CustomException
	 * @author 孙鹏
	 * @version  2014年5月12日下午1:05:37
	 */
	public List<Contacts> getOutSideUserTree(Contacts contacts)
			throws CustomException {
		return template.selectList(getNameSpace(contacts) + ".getOutSideUserTree",contacts);
	}
	
	/**
	 * 方法描述：验证联系人手机号是否存在
	 * @param contacts
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年8月6日上午10:05:16
	 * @see
	 */
	@Override
	public Contacts getMobile(Contacts contacts) {
		return template.selectOne(getNameSpace(contacts) + ".selectMobile",contacts);
	}
}