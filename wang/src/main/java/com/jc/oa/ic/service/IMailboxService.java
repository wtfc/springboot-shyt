package com.jc.oa.ic.service;

import java.util.List;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.ic.domain.Mailbox;

/**
 * @title GOA V2.0 互动交流
 * @description  业务接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */

public interface IMailboxService extends IBaseService<Mailbox>{

	List<Mailbox> queryAllSign(Mailbox mailbox);
}