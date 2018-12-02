package com.jc.oa.ic.service;

import java.util.List;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.domain.MailRecord;

/**
 * @title 互动交流
 * @description  业务接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-17
 */

public interface IMailRecordService extends IBaseService<MailRecord>{
	/**
	 * 方法描述：根据邮箱ID删除
	 * @param t
	 * @return
	 * @author 张立刚
	 * @version 2014年4月17日上午9:57:34
	 * @see
	 */
	public Integer deleteByMailId(MailRecord t) throws IcException;
	/**
	 * 方法描述：设置提醒状态
	 * @param ids
	 * @return
	 * @author 曹杨
	 * @version  2014年7月2日上午8:58:12
	 * @see
	 */
	public Integer setRemindFlag(List<MailRecord> list) throws IcException;
	/**
	 * 方法描述：设置回复状态
	 * @param ids
	 * @return
	 * @author 曹杨
	 * @version  2014年7月16日上午16:12:12
	 * @see
	 */
	public Integer setReplyFlag(MailRecord t) throws IcException;
	/**
	 * 方法描述：彻底删除邮件
	 * @param record primaryKeys为邮件Id，receiveUserId或receiveMail不等于空
	 * @return
	 * @author zhangligang
	 * @version  2014年7月16日下午2:43:27
	 * @see
	 */
	public Integer deleteByMailIdsLogic(MailRecord record) throws IcException;
	
}