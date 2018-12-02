package com.jc.oa.ic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.dao.IMailSignDao;
import com.jc.oa.ic.domain.MailSign;
import com.jc.oa.ic.domain.Mailbox;
import com.jc.oa.ic.service.IMailSignService;
import com.jc.oa.ic.service.IMailboxService;
import com.jc.system.CustomException;
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
public class MailSignServiceImpl extends BaseServiceImpl<MailSign> implements IMailSignService{

	private IMailSignDao mailSignDao;
	@Autowired
	private IMailboxService mailboxService;
	
	@Autowired
	public MailSignServiceImpl(IMailSignDao mailSignDao){
		super(mailSignDao);
		this.mailSignDao = mailSignDao;
	}
	public MailSignServiceImpl(){
	}
	
	/**
	 * @description 删除方法
	 * @param MailSign mailSign 实体类
	 * @param String id 被删除数据主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer delete(MailSign mailSign,String id) throws IcException{
		try {
			//判断该签名是否已经有邮箱使用
//			Mailbox mailboxInfo = new Mailbox();
//			mailboxInfo.setSignId(new Long(id));
//			mailboxInfo.setReplySignId(new Long(id));
//			if(mailboxService.queryAll(mailboxInfo).size() != 0){
//				IcException ce = new IcException();
//				ce.setLogMsg(MessageUtils.getMessage("JC_OA_IC_041"));
//				throw ce;
//			}
			mailSign.setPrimaryKeys(id.split(","));
			mailSignDao.delete(mailSign,true);
			Mailbox mailbox =new Mailbox();
			List<Mailbox> list = mailboxList(mailbox);
			for (Mailbox mailbox2 : list) {
				if(mailSign.getId().equals(mailbox2.getSignId())&&!mailSign.getId().equals(mailbox2.getReplySignId())){
					mailbox2.setSignId((long) 0);
					mailbox2.setUpdateFlag("1");
					mailboxService.update(mailbox2);
				}else if(!mailSign.getId().equals(mailbox2.getSignId())&&mailSign.getId().equals(mailbox2.getReplySignId())){
					mailbox2.setReplySignId((long) 0);
					mailbox2.setUpdateFlag("1");
					mailboxService.update(mailbox2);
				}else if(mailSign.getId().equals(mailbox2.getSignId())&&mailSign.getId().equals(mailbox2.getReplySignId())){
					mailbox2.setSignId((long) 0);
					mailbox2.setReplySignId((long) 0);
					mailbox2.setUpdateFlag("1");
					mailboxService.update(mailbox2);
				}
			}
			
		} catch (CustomException e) {
			IcException se = new IcException(e);
			se.setLogMsg(e.getLogMsg());
			throw se;
		}
		return 1;
	}
	
	/**
	* @description 设置邮箱默认签名时的下拉数据
	* @return List<Mailbox>
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-21 
	*/
	public List<Mailbox> mailboxList(Mailbox mailbox) throws IcException{
		List<Mailbox> list = null;
		try {
			list = mailboxService.queryAllSign(mailbox);
		} catch (Exception e) {
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_OA_IC_042"));
			throw se;
		}
		return list;
	}
	/**
	 * @description 保存邮箱默认签名方法
	 * @param Mailbox mailbox 实体类
	 * @return Integer 更新的数目
	 * @author 徐伟平
	 * @version 2014-03-18
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer updateMailbox(Mailbox mailbox) throws IcException{
		try {
			 mailboxService.update(mailbox);
		} catch (Exception e) {
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
			throw se;
		}
		return 1;
	}
	
	/**
	 * 方法描述：保存内部邮箱默认签名方法
	 * @param mailbox
	 * @param result
	 * @param request
	 * @return
	 * @throws CustomException
	 * @author 宋海涛
	 * @version  2014年9月24日上午8:29:05
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer saveMailbox(Mailbox mailbox) throws IcException{
		try {
			 mailboxService.save(mailbox);
		} catch (Exception e) {
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
			throw se;
		}
		return 1;
	}
	
}