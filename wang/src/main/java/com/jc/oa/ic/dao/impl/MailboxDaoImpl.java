package com.jc.oa.ic.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.ic.dao.IMailboxDao;
import com.jc.oa.ic.domain.Mail;
import com.jc.oa.ic.domain.Mailbox;

/**
 * @title GOA V2.0 互动交流
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */
@Repository
public class MailboxDaoImpl extends BaseDaoImpl<Mailbox> implements IMailboxDao{
	
	/**
	 * 方法描述：查询内部签名
	 * @return
	 * @author weny
	 * @version  2014年9月24日
	 * @see
	 */
	@Override
	public Mailbox querySign(Mailbox mailbox){
		return template.selectOne(getNameSpace(mailbox)+".querySign", mailbox);
	}

	@Override
	public List<Mailbox> queryAllSign(Mailbox mailbox) {
		return template.selectList(getNameSpace(mailbox)+".queryAllMailBox", mailbox);
	}
}