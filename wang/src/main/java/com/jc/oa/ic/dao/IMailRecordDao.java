package com.jc.oa.ic.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.oa.ic.domain.MailRecord;


/**
 * @title 互动交流
 * @description  dao接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 曹杨
 * @version  2014-04-17
 */
 
public interface IMailRecordDao extends IBaseDao<MailRecord>{
	/**
	 * 方法描述：根据邮箱ID删除
	 * @param t
	 * @return
	 * @author 张立刚
	 * @version 2014年4月17日上午9:57:34
	 * @see
	 */
	public Integer deleteByMailId(MailRecord t);
	/**
	 * 方法描述：设置提醒状态
	 * @param t
	 * @return
	 * @author 曹杨
	 * @version  2014年7月2日上午8:58:12
	 * @see
	 */
	public Integer setRemindFlag(MailRecord t);
	/** 
	 * 方法描述：设置回复状态
	 * @param t
	 * @return
	 * @author 曹杨
	 * @version  2014年7月16日下午4:08:44
	 * @see
	 */
	public Integer setReplyFlag(MailRecord t);
}
