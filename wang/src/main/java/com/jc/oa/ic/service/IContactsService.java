package com.jc.oa.ic.service;

import java.util.List;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.domain.Contacts;
import com.jc.system.CustomException;

/**
 * @title GOAIC
 * @description  业务接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 曹杨
 * @version  2014-05-04
 */

public interface IContactsService extends IBaseService<Contacts>{
    /**
     * 联合联系人分组表分页查询
     * @param contacts
     * @param page
     * @return 
     * @throws CustomException
     * @author 曹杨
     */
	public PageManager queryContacts(Contacts contacts, PageManager page);
	/**
	 * 方法描述：获取邮件联系人与分组信息
	 * @param contacts
	 * @return
	 * @throws CustomException
	 * @author 曹杨
	 * @version  2014年5月12日下午1:05:37
	 */
	public Contacts getContacts(Contacts contacts) throws IcException;
	
	/**
	 * 方法描述：修改联系人与联系人分组
	 * @param contacts
	 * @return
	 * @throws IcException
	 * @author 曹杨
	 * @version  2014年5月20日下午1:05:30
	 */
	public Integer updateContacts(Contacts contacts) throws IcException;
	/**
	 * 方法描述：级联删除联系人及联系人关系
	 * @param contacts
	 * @return
	 * @throws IcException
	 * @author 曹杨
	 * @version 2014年5月20日下午4:58:30
	 */
	public Integer deleteContacts(Contacts contacts) throws IcException;
	/**
	 * 方法描述：获取邮件联系人与分组信息（外部联系人组件使用）
	 * @param contacts
	 * @return
	 * @throws CustomException
	 * @author 孙鹏
	 * @version  2014年5月12日下午1:05:37
	 */
	public Contacts queryOutSideUser(Contacts contacts) throws IcException;
	/**
	 * 方法描述：获得外部联系人树（外部联系组件使用）
	 * @param contacts
	 * @return
	 * @throws CustomException
	 * @author 孙鹏
	 * @version  2014年5月12日下午1:05:37
	 */
	public List<Contacts> getOutSideUserTree(Contacts contacts) throws IcException;
	
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