package com.jc.oa.ic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.dao.IMailboxDao;
import com.jc.oa.ic.domain.Mailbox;
import com.jc.oa.ic.service.IMailboxService;
import com.jc.oa.ic.util.CryptUtil;
import com.jc.oa.ic.util.MailReceiver;
import com.jc.oa.ic.util.MailSender;
import com.jc.system.CustomException;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.MessageUtils;

/**
 * @title GOA V2.0 互动交流
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */
@Service
public class MailboxServiceImpl extends BaseServiceImpl<Mailbox> implements IMailboxService{

	private IMailboxDao mailboxDao;
	@Autowired
	public MailboxServiceImpl(IMailboxDao mailboxDao){
		super(mailboxDao);
		this.mailboxDao = mailboxDao;
	}
	public MailboxServiceImpl(){
		
	}
	/**
	 * @description 保存方法
	 * @param Mailbox mailbox 实体类
	 * @return Integer 增加的记录数
	 * @author 徐伟平
	 * @version 2014-05-12
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer save(Mailbox mailbox) throws IcException {
		try {
			if(!"1".equals(mailbox.getUpdateFlag())){
				// 判断帐户名是否存在
				Mailbox m = new Mailbox();
				m.setUsername(mailbox.getUsername());
				m.setDeleteFlag(0);
				if (mailboxDao.get(m) != null) {
					CustomException ce = new CustomException();
					ce.setLogMsg(MessageUtils.getMessage("JC_SYS_036",new String[]{"账户名称"}));
					throw ce;
				}
				//验证账户名称和邮件地址是否一致
				if (mailbox.getAddress() != null) {
					if (mailbox.getUsername().indexOf("@") != -1) {
						if (!(mailbox.getAddress()).equals(mailbox.getUsername())) {
							IcException ce = new IcException();
							ce.setLogMsg(MessageUtils.getMessage("JC_OA_IC_082"));
							throw ce;
						}
					} else {
						String[] address = mailbox.getAddress().split("@");
						if (!address[0].equals(mailbox.getUsername())) {
							IcException ce = new IcException();
							ce.setLogMsg(MessageUtils.getMessage("JC_OA_IC_082"));
							throw ce;
						}
					}
				}
				//验证邮箱是否能发送邮件和接收邮件
				MailSender mailSender = new MailSender();
				if(!mailSender.identitySmtp(mailbox)){
					IcException ce = new IcException();
					ce.setLogMsg(MessageUtils.getMessage("JC_OA_IC_021"));
					throw ce;
				}
				MailReceiver mailReceiver = new MailReceiver();
				if(!mailReceiver.identityImap(mailbox)){
					IcException ce = new IcException();
					ce.setLogMsg(MessageUtils.getMessage("JC_OA_IC_022"));
					throw ce;
				}
				//对密码进行加密转换
				CryptUtil cryptUtil = new CryptUtil();
				mailbox.setMailPassword(cryptUtil.encrypt(mailbox.getMailPassword()));
			}
			
			//保存通用字段
			propertyService.fillProperties(mailbox,false);
			// 保存意见
			mailboxDao.save(mailbox);
		} catch (CustomException e) {
			IcException se = new IcException(e);
			se.setLogMsg(e.getLogMsg());
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	/**
	 * @description 修改方法
	 * @param Mailbox mailbox 实体类
	 * @return Integer 增加的记录数
	 * @author 徐伟平
	 * @version 2014-05-12
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer update(Mailbox mailbox) throws IcException {
		try {
			if(!"1".equals(mailbox.getUpdateFlag())){
				
				//验证账户名称和邮件地址是否一致
				if(mailbox.getAddress() !=null){
					String[] address = mailbox.getAddress().split("@");
					String[] username = mailbox.getUsername().split("@");
					if(!address[0].equals(username[0])){
						IcException ce = new IcException();
						ce.setLogMsg(MessageUtils.getMessage("JC_OA_IC_082"));
						throw ce;
					}
				 }
				//验证邮箱是否能发送邮件和接收邮件
				MailSender mailSender = new MailSender();
				if(!mailSender.identitySmtp(mailbox)){
					IcException ce = new IcException();
					ce.setLogMsg(MessageUtils.getMessage("JC_OA_IC_021"));
					throw ce;
				}
				MailReceiver mailReceiver = new MailReceiver();
				if(!mailReceiver.identityImap(mailbox)){
					IcException ce = new IcException();
					ce.setLogMsg(MessageUtils.getMessage("JC_OA_IC_022"));
					throw ce;
				}
				if(!"".equals(mailbox.getMailPassword()) && mailbox.getMailPassword() != null){
					//对密码进行加密转换
					CryptUtil cryptUtil = new CryptUtil();
					mailbox.setMailPassword(cryptUtil.encrypt(mailbox.getMailPassword()));
				}
				if("".equals(mailbox.getSenderSSL()) || mailbox.getSenderSSL() == null){
					mailbox.setSenderSSL("0");
				}
				if("".equals(mailbox.getReceiveSSL()) || mailbox.getReceiveSSL() == null){
					mailbox.setReceiveSSL("0");
				}
			}
			mailbox.setModifyDateNew(DateUtils.getSysDate());
			// 保存意见
			mailboxDao.update(mailbox);
		} catch (CustomException e) {
			IcException se = new IcException(e);
			se.setLogMsg(e.getLogMsg());
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	@Override
	public List<Mailbox> queryAllSign(Mailbox mailbox) {
		return mailboxDao.queryAllSign(mailbox);
	}
}