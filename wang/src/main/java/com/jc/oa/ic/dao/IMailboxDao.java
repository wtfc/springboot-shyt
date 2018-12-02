package com.jc.oa.ic.dao;

import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.oa.ic.domain.Mailbox;


/**
 * @title GOA V2.0 互动交流
 * @description  dao接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */
 
public interface IMailboxDao extends IBaseDao<Mailbox>{
	/**
	 * 方法描述：查询内部签名
	 * @return
	 * @author weny
	 * @version  2014年9月24日
	 * @see
	 */
	public Mailbox querySign(Mailbox mailbox);

	public List<Mailbox> queryAllSign(Mailbox mailbox);
}
