package com.jc.oa.ic.service;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.domain.SmsStatistic;
import com.jc.system.security.domain.User;

/**
 * @title 互动交流
 * @description  业务接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-06-05
 */

public interface ISmsStatisticService extends IBaseService<SmsStatistic>{
	public Integer save(SmsStatistic smsStatistic ) throws IcException;
	
	public void updateSmsStatistic(SmsStatistic smsStatistic,User user)throws IcException;
}