package com.jc.oa.ic.dao;

import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.foundation.domain.PageManager;
import com.jc.oa.ic.domain.Contacts;
import com.jc.system.CustomException;


/**
 * @title GOAIC
 * @description  dao接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 曹杨
 * @version  2014-05-04
 */
 
public interface IContactsDao extends IBaseDao<Contacts>{
	/**
	 * 方法描述：联合用户分组表查询
	 * @param contacts
	 * @param pageManager
	 * @return
	 * @throws CustomException
	 * @author 曹杨
	 * @version  2014年5月7日上午8:44:26
	 */
	public PageManager queryContacts(Contacts contacts, PageManager pageManager);
	/**
	 * 方法描述：联合联系组表获取联系人信息
	 * @param contacts
	 * @return
	 * @throws CustomException
	 * @author 曹杨
	 * @version  2014年5月13日下午1:12:03
	 * @see
	 */
	public Contacts getContacts (Contacts contacts) throws CustomException;
	/**
	 * 方法描述：外部联系人组件查询方法
	 * @param contacts
	 * @return
	 * @throws CustomException
	 * @author 孙鹏
	 * @version  2014年5月13日下午1:12:03
	 * @see
	 */
	public Contacts queryOutSideUser (Contacts contacts) throws CustomException;
	/**
	 * 方法描述：获得外部联系人树（外部联系组件使用）
	 * @param contacts
	 * @return
	 * @throws CustomException
	 * @author 孙鹏
	 * @version  2014年5月12日下午1:05:37
	 */
	public List<Contacts> getOutSideUserTree(Contacts contacts) throws CustomException;
	
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
	public Contacts getMobile(Contacts contacts);
}
