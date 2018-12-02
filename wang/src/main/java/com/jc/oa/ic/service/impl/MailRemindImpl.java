package com.jc.oa.ic.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.jc.oa.ic.dao.IMailDao;
import com.jc.oa.ic.domain.Mail;
import com.jc.oa.ic.domain.MailRecord;
import com.jc.oa.ic.domain.Mailbox;
import com.jc.oa.ic.service.IMailboxService;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.remind.service.IRemindService;
import com.jc.system.security.SystemSecurityUtils;

/**
 * 
 * @title GOA V2.0 邮件Portal 消息提醒
 * @description  
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author zhangligang
 * @version  2014年7月25日 下午2:10:15
 */
public class MailRemindImpl implements IRemindService {

	private IMailboxService mailboxService=SpringContextHolder.getBean(IMailboxService.class);
	
	private IMailDao mailDao=SpringContextHolder.getBean(IMailDao.class);

	@Override
	public String getRemindCount() {
		Mail mail = new Mail();
		//查询内部邮箱
		List<Mailbox> mailboxlist=new ArrayList<Mailbox>();
		Mailbox mbox = new Mailbox();
		mbox.setId(1L);
		mailboxlist.add(mbox);
		mail.setMailbox(mailboxlist);
		
		//查询内部邮箱需要设置收件人为当前用户
		mail.setReceivers(new ArrayList<MailRecord>());
		MailRecord receive;
		receive=new MailRecord();
		receive.setReceiveUserId(SystemSecurityUtils.getUser().getId());
		mail.getReceivers().add(receive);
		//取得所有邮箱Id  备份取全部邮箱使用
//		if (mail.getMailboxId() == null) {
//			Mailbox mailbox = new Mailbox();
//			mailbox.setCreateUser(SystemSecurityUtils.getUser().getId());
////			List<Mailbox> mailboxlist;
//			try {
//				mailbox.setCreateUser(SystemSecurityUtils.getUser().getId());
//				mailboxlist= mailboxService.queryAll(mailbox);
//				
//				MailRecord receive;
//				for (Mailbox mailbox2 : mailboxlist) {
//				receive=new MailRecord();
//				receive.setReceiveMail(mailbox2.getAddress());
//				mail.getReceivers().add(receive);
//				}
//				
//				
////				Mailbox mbox = new Mailbox();
//				mbox.setId(1L);
//				mailboxlist.add(mbox);
//				mail.setMailbox(mailboxlist);
//				receive=new MailRecord();
//				receive.setReceiveUserId(SystemSecurityUtils.getUser().getId());
//				mail.getReceivers().add(receive);
//			} catch (CustomException e) {
//				e.printStackTrace();
//			}
//		}
		Long count = mailDao.getUnreadCount(mail);
		return count.toString();
	}

}
