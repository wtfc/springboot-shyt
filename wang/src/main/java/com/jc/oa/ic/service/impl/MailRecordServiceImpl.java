package com.jc.oa.ic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.dao.IMailRecordDao;
import com.jc.oa.ic.domain.MailRecord;
import com.jc.oa.ic.service.IMailRecordService;
import com.jc.system.DBException;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.MessageUtils;

/**
 * @title 互动交流
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-17
 */
@Service
public class MailRecordServiceImpl extends BaseServiceImpl<MailRecord> implements IMailRecordService{

	private IMailRecordDao mailRecordDao;
	
	@Autowired
	public MailRecordServiceImpl(IMailRecordDao mailRecordDao){
		super(mailRecordDao);
		this.mailRecordDao = mailRecordDao;
	}
	public MailRecordServiceImpl(){
		
	}
	/**
	 * 方法描述：根据邮箱ID删除
	 * @param t
	 * @return
	 * @author 张立刚
	 * @version 2014年4月17日上午9:57:34
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public Integer deleteByMailId(MailRecord t) throws IcException {
		return mailRecordDao.deleteByMailId(t);
	}
	/**
	 * 方法描述：设置提醒状态
	 * @param ids
	 * @return
	 * @author 曹杨
	 * @version  2014年7月2日上午8:58:12
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public Integer setRemindFlag(List<MailRecord> list) throws IcException {
		int result = -1;
		try {
			String[] primaryKeys = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				primaryKeys[i] = list.get(i).getId().toString();
			}
			MailRecord record = new MailRecord();
			//设置提醒标记
			record.setRemindFlag(Constants.IC_MAIL_REMIND_FLAG);
			record.setPrimaryKeys(primaryKeys);
			result = mailRecordDao.setRemindFlag(record);

		} catch (Exception e) {
			IcException ce = new IcException();
			ce.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
			throw ce;
		}
		return result;
	}
	/**
	 * 方法描述：设置回复状态
	 * @param ids
	 * @return
	 * @author 曹杨
	 * @version  2014年7月16日上午16:12:12
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public Integer setReplyFlag(MailRecord t) throws IcException {
		int result=-1;
		try {
			result= mailRecordDao.setReplyFlag(t);
		} catch (Exception e) {
			e.printStackTrace();
			IcException ce = new IcException();
			ce.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
			throw ce;
		}
		return result;
	}
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public Integer deleteByMailIdsLogic(MailRecord record) throws IcException {
		int result=-1;
		try {
			result= mailRecordDao.delete(record, true);
		} catch (DBException e) {
			e.printStackTrace();
			IcException ce = new IcException();
			ce.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
			throw ce;
		}
		return result;
	}

}