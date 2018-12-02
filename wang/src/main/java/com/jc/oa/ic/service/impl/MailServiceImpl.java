package com.jc.oa.ic.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.android.oa.common.service.IUsermapService;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.dao.IMailDao;
import com.jc.oa.ic.dao.IMailboxDao;
import com.jc.oa.ic.dao.impl.MailboxDaoImpl;
import com.jc.oa.ic.domain.Contacts;
import com.jc.oa.ic.domain.Mail;
import com.jc.oa.ic.domain.MailFolder;
import com.jc.oa.ic.domain.MailRecord;
import com.jc.oa.ic.domain.Mailbox;
import com.jc.oa.ic.domain.Out;
import com.jc.oa.ic.facade.IInteractFacadeService;
import com.jc.oa.ic.service.IContactsService;
import com.jc.oa.ic.service.IFolderService;
import com.jc.oa.ic.service.IMailRecordService;
import com.jc.oa.ic.service.IMailService;
import com.jc.oa.ic.service.IMailboxService;
import com.jc.oa.ic.service.IOutService;
import com.jc.oa.ic.util.MailReceiver;
import com.jc.oa.ic.util.MailSender;
import com.jc.system.CustomException;
import com.jc.system.common.util.CharConventUtils;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.content.domain.Attach;
import com.jc.system.content.domain.AttachBusiness;
import com.jc.system.content.service.IAttachBusinessService;
import com.jc.system.content.service.IAttachService;
import com.jc.system.content.service.IUploadService;
import com.jc.system.notice.NoticeMsgUtil;
import com.jc.system.notice.domain.NoticeMsg;
import com.jc.system.notice.service.INoticeMsgService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.SettingUtils;
import com.jc.system.security.util.UserUtils;

/**
 * @title 互动交流
 * @description 业务服务类 Copyright (c) 2014 Jiachengnet.com Inc. All Rights
 *              Reserved Company 长春嘉诚网络工程有限公司
 * @author
 * @version 2014-04-17
 */
@Service
public class MailServiceImpl extends BaseServiceImpl<Mail> implements
		IMailService {
	private MailSender sender;
	private MailReceiver receiver;
	private IMailDao mailDao;
	private IMailRecordService mailRecordService;
	private IMailboxService mailboxService;
	private IFolderService folderService;
	private IOutService smsOutService;
	private IAttachBusinessService attachBusinessService;
	private IAttachService attachService;
	private IContactsService contactsService;
	private final String TABLE_NAME = "TTY_IC_MAIL";

	private final String FORWARD_SPLIT = "<br /><br /><br /><br /><br />-------- 转发邮件信息 --------<br/><br/><br/>";

	// 未读邮件列表查询语句
	public static final String SQL_QUERYUNREADMAIL = "queryUnReadMail";
	// 未读邮件条数查询语句
	public static final String SQL_QUERYUNREADMAILCOUNT = "queryUnReadMailCount";
	// 星标邮件列表查询语句
	public static final String SQL_QUERYSTARMAIL = "queryStarMail";
	// 星标邮件条数查询语句
	public static final String SQL_QUERYSTARAMAILCOUNT = "queryStarMailCount";

	// 星标邮件列表查询语句
	public static final String SQL_QUERYMAIL = "queryMail";
	// 星标邮件条数查询语句
	public static final String SQL_QUERYMAILCOUNT = "queryMailCount";

	@Autowired
	private IInteractFacadeService interactFacadeService;

	@Autowired
	private IUploadService uploadService;
	@Autowired	
	private IUsermapService usermapService;
	@Autowired	
	private INoticeMsgService noticeService;
	@Autowired
	public MailServiceImpl(IMailDao mailDao,
			IMailRecordService mailRecordService,
			IMailboxService mailboxService, IFolderService folderService,
			IOutService smsOutService,
			IAttachBusinessService attachBusinessService,
			IAttachService attachService, IContactsService contactsService) {
		super(mailDao);
		this.mailDao = mailDao;
		this.mailRecordService = mailRecordService;
		this.mailboxService = mailboxService;
		this.sender = new MailSender();
		this.receiver = new MailReceiver();
		this.folderService = folderService;
		this.smsOutService = smsOutService;
		this.attachBusinessService = attachBusinessService;
		this.attachService = attachService;
		this.contactsService = contactsService;
	}

	@Autowired
	private IMailboxDao mailboxDao;
	
	public MailServiceImpl() {

	}

	/**
	 * 方法描述：查询条件预处理，查询功能内部使用，
	 * 
	 * @param mail
	 * @return
	 * @author zhanglg
	 * @version 2014年5月28日上午10:13:35
	 * @see
	 */
	private Mail queryPreProcess(Mail mail) {
		// 如果查询条件有邮箱ID，根据邮箱设置来决定具体查询条件应加到哪个字段上
		if (mail.getMailboxId() != null) {
			// 根据邮箱ID取邮箱做查询条件
			Mailbox box = new Mailbox();
			box.setId(mail.getMailboxId());

			MailRecord record = null;
			// 如果收件记录列表不为空，列表的第0个元素既为界面传过来的查询条件，需要处理的就是这个条件
			if (mail.getReceivers() != null) {
				if (mail.getReceivers().size() > 0) {
					record = mail.getReceivers().get(0);
				}
			} else {
				mail.setReceivers(new ArrayList<MailRecord>());
			}

			if (record == null) {
				record = new MailRecord();
				if (mail.getReceivers().size() == 0) {
					mail.getReceivers().add(record);
				}
			}
			// 根据查询到的MailBox配置，来决定查询条件设置哪个字段
			try {
				box = mailboxService.get(box);
				if(box!=null){
					if (Constants.IC_MAIL_MAILBOX_INNER.equals(box.getId())) {
						record.setReceiveUserId(SystemSecurityUtils.getUser()
								.getId());
					} else {
						record.setReceiveMail(box.getAddress());
					}
				}
			} catch (CustomException e) {
				e.printStackTrace();
			}
		} else {
			Mailbox box = new Mailbox();
			box.setCreateUser(SystemSecurityUtils.getUser().getId());

			MailRecord record = null;
			// 如果收件记录列表不为空，列表的第0个元素为界面传过来的查询条件
			if (mail.getReceivers() != null) {
				if (mail.getReceivers().size() > 0) {
					record = mail.getReceivers().get(0);
				}
			} else {
				mail.setReceivers(new ArrayList<MailRecord>());
			}

			if (record == null) {
				record = new MailRecord();
				if (mail.getReceivers().size() == 0) {
					mail.getReceivers().add(record);
				}
			}

			record.setReceiveUserId(SystemSecurityUtils.getUser().getId());
			try {

				List<Mailbox> mailboxs = mailboxService.queryAll(box);
				MailRecord receiver = null;
				for (Mailbox mailbox : mailboxs) {
					receiver = new MailRecord();
					receiver.setReceiveMail(mailbox.getAddress());
					receiver.setFolderId(record.getFolderId());
					receiver.setStarMail(record.getStarMail());
					receiver.setReadFlag(record.getReadFlag());
					receiver.setRemindFlag(record.getRemindFlag());
					mail.getReceivers().add(receiver);
				}

			} catch (CustomException e) {
				e.printStackTrace();
			}
		}
		return mail;
	}

	/**
	 * 方法描述：分页条件查询
	 * 
	 * @param mail
	 * @param page
	 * @return
	 * @author zhanglg
	 * @version 2014年5月12日下午8:57:49
	 * @see
	 */
	@Override
	public PageManager query(Mail mail, PageManager page) {
		return mailDao.queryByPage(this.queryPreProcess(mail), page,
				SQL_QUERYMAILCOUNT, SQL_QUERYMAIL);
	}

	/**
	 * 方法描述：分页条件查询，使用like查询
	 * 
	 * @param mail
	 * @param page
	 * @return
	 * @author zhanglg
	 * @version 2014年5月12日下午8:57:49
	 * @see
	 */
	@Override
	public PageManager search(Mail mail, PageManager page) {
		return mailDao.search(this.queryPreProcess(mail), page);
	}

	/**
	 * 方法描述：未读邮件分页条件查询，使用like查询
	 * 
	 * @param mail
	 * @param page
	 * @return
	 * @author 宋海涛
	 * @version 2014年6月26日上午9:05:52
	 * @see
	 */
	@Override
	public PageManager searchUnRead(Mail mail, PageManager page) {
		return mailDao.searchUnRead(this.queryPreProcess(mail), page);
	}

	/**
	 * 向系统外邮件服务器发送邮件
	 * 
	 * @param request
	 * 
	 * @param mailinfo
	 *            待发送的邮件信息
	 * @return
	 * @throws IcException
	 * @throws CustomException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean sendExtMail(Mailbox Mailbox, Mail mail,
			HttpServletRequest request) throws IcException {
		try {
			if (mail.getFileids() != null) {
				List<Attach> attachs = new ArrayList<Attach>();
				String fileIds[] = mail.getFileids().split(",");
				String abPathPre=request.getRealPath(File.separator)
						+ (String) SettingUtils
						.getSetting(SettingUtils.FILE_PATH)
				+ File.separator ;
				// 处理附件
				if (fileIds != null && fileIds.length > 0
						&& !"".equals(fileIds[0])) {
					Attach attach = null;
					for (int i = 0; i < fileIds.length; i++) {
						attach = new Attach();
						attach.setId(new Long(fileIds[i]));
						// attach.setIsPaged("0");
						// PageManager page = new PageManager();
						// page.setPageRows(100);
						// PageManager page_ = attachService.query(attach,
						// page);
						// if (page_ != null) {
						// attachs.addAll((List<Attach>) page_.getData());
						// }
						// get Attach object from DB
						attach = attachService.get(attach);
						if(attach.getResourcesName().startsWith(abPathPre)==false){
							// 转换文件相对路径变成绝对路径
							attach.setResourcesName(abPathPre + attach.getResourcesName());
						}
						// add Attach to list
						attachs.add(attach);
					}
				}
				mail.setAttachs(attachs);
			}
			sender.sendMail(Mailbox, mail);
			return true;
		} catch (AddressException ae) {
			ae.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_024"));
		} catch (MessagingException ex) {
			ex.printStackTrace();
			if (ex.getMessage().contains("Connection timed out")) {
				throw new IcException(MessageUtils.getMessage("JC_OA_IC_025"),
						ex);
			} else if (ex.getMessage().contains("Invalid Addresses")) {
				throw new IcException(MessageUtils.getMessage("JC_OA_IC_076"),
						ex);
			} else {
				throw new IcException(MessageUtils.getMessage("JC_OA_IC_026"),
						ex);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_027"), e);
		} catch (IcException e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_028"), e);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_029"), e);
		} catch (CustomException e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_028"), e);
		}
	}
	
	
	/**
	 * 向系统外邮件服务器发送邮件
	 * 
	 * @param request
	 * 
	 * @param mailinfo
	 *            待发送的邮件信息
	 * @return
	 * @throws IcException
	 * @throws CustomException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean transExtMail(Mailbox Mailbox, Mail mail,
			HttpServletRequest request) throws IcException {
		try {
			if (mail.getFileids() != null) {
				List<Attach> attachs = new ArrayList<Attach>();
				String fileIds[] = mail.getFileids().split(",");
				// 处理附件
				if (fileIds != null && fileIds.length > 0
						&& !"".equals(fileIds[0])) {
					Attach attach = null;
					for (int i = 0; i < fileIds.length; i++) {
						attach = new Attach();
						attach.setId(new Long(fileIds[i]));
						// attach.setIsPaged("0");
						// PageManager page = new PageManager();
						// page.setPageRows(100);
						// PageManager page_ = attachService.query(attach,
						// page);
						// if (page_ != null) {
						// attachs.addAll((List<Attach>) page_.getData());
						// }
						// get Attach object from DB
						attach = attachService.get(attach);
						// 转换文件相对路径变成绝对路径
						attach.setResourcesName(request
								.getRealPath(File.separator)
								+ (String) SettingUtils
										.getSetting(SettingUtils.FILE_PATH)
								+ File.separator + attach.getResourcesName());
						// add Attach to list
						attachs.add(attach);
					}
				}
				mail.setAttachs(attachs);
			}
			sender.forwardingmail(Mailbox, mail);
			return true;
		} catch (AddressException ae) {
			ae.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_024"));
		} catch (MessagingException ex) {
			ex.printStackTrace();
			if (ex.getMessage().contains("Connection timed out")) {
				throw new IcException(MessageUtils.getMessage("JC_OA_IC_025"),
						ex);
			} else if (ex.getMessage().contains("Invalid Addresses")) {
				throw new IcException(MessageUtils.getMessage("JC_OA_IC_076"),
						ex);
			} else {
				throw new IcException(MessageUtils.getMessage("JC_OA_IC_026"),
						ex);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_027"), e);
		} catch (IcException e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_028"), e);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_029"), e);
		} catch (CustomException e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_028"), e);
		}
	}

	/**
	 * 接收邮件，保存到数据库
	 * 
	 * @param mailboxId
	 *            邮箱配置Id，如果为空，查询当前用户所有邮箱配置
	 * @return 新邮件数量
	 * @throws IcException
	 * @throws CustomException
	 */
	@Override
	public Integer receive(Long mailboxId) throws CustomException {
		// 取到当前用户
		User user = SystemSecurityUtils.getUser();
		// 取到当前用户的MailBox
		Mailbox box = new Mailbox();
		if (mailboxId != null) {
			box.setId(mailboxId);
		}
		box.setCreateUser(user.getId());
		box.setDeleteFlag(0);
		List<Mailbox> boxes = mailboxService.queryAll(box);
		// 收取每个MailBox的邮件
		List<Mail> mails = new ArrayList<Mail>();
		if (boxes != null) {
			for (Mailbox mailbox : boxes) {
				try {
					List<Mail> tmpMailList = receiveAllExtMail(mailbox);
					if (tmpMailList != null) {
						mails.addAll(tmpMailList);
					}
				} catch (IcException e) {
					throw e;
				} catch (Exception e) {
					e.printStackTrace();
					throw new IcException(
							MessageUtils.getMessage("JC_OA_IC_030"), e);
				}
			}
		}
		List<Mail> removeList = new ArrayList<Mail>();
		// 保存邮件，不做数据库事务处理
		Mail m = null;
		AttachBusiness attachBusiness = null;
		for (Mail mail : mails) {
			m = new Mail();
			m.setMessageId(mail.getMessageId());
			m.setMailboxId(mail.getMailboxId());
			List<Mail> mails2=mailDao.queryAll(m);
			if (mail.getMessageId() != null && (mails2 == null||mails2.size()==0)) {
//			if (mail.getMessageId() != null && mailDao.get(m) == null) {
				// 如果存在附件设置isfile字段为1
				if (mail.getAttachs() != null && mail.getAttachs().size() > 0) {
					mail.setIsfile(1);
				}
				mail.setMailFolderId(1L);
				Integer count = this.save(mail);

				// 接收邮件保存附件信息
				if (count > 0 && mail.getAttachs() != null) {
					for (Attach attach : mail.getAttachs()) {
						attachService.save(attach);
						attachBusiness = new AttachBusiness();
						attachBusiness.setAttachId(attach.getId());
						attachBusiness.setBusinessId(mail.getId());
						attachBusiness.setBusinessTable(TABLE_NAME);
						attachBusinessService.save(attachBusiness);
					}
				}
			} else {
				removeList.add(mail);
			}
		}
		//mailbox邮件最后更新时间
		mails.removeAll(removeList);
		if (boxes != null) {
			Date date = new Date();
			Mailbox oldMailbox = null;
			for (Mailbox mailbox : boxes) {
				mailbox.setLastReceived(date);
			    oldMailbox = new Mailbox();
				oldMailbox.setId(mailbox.getId());
				oldMailbox = mailboxService.get(oldMailbox);
				mailbox.setModifyDate(oldMailbox.getModifyDate());
				mailboxService.update(mailbox);
			}
		}
		return mails.size();
	}

	/**
	 * 接收系统外邮件服务器的所有邮件
	 * 
	 * @param receiverInfo
	 * @throws Exception
	 */
	@Override
	public List<Mail> receiveAllExtMail(Mailbox Mailbox) throws IcException {
		try {
			return receiver.receive(Mailbox);
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
			if (e.getMessage().contains("Connection timed out")) {
				throw new IcException(MessageUtils.getMessage("JC_OA_IC_025"),
						e);
			}
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_031"), e);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_029"), e);
		}
	}

	/**
	 * 接收系统外邮件服务器的指定邮件
	 * 
	 * @param mailbox
	 * @param messageId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Mail getExtMail(Mailbox mailbox, String messageId)
			throws IcException {
		try {
			return receiver.getMail(mailbox, messageId);
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
			if (e.getMessage().contains("Connection timed out")) {
				throw new IcException(MessageUtils.getMessage("JC_OA_IC_025"),
						e);
			}
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_031"), e);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_029"), e);
		}
	}

	/**
	 * 设置邮件已读
	 * 
	 * @param mailbox
	 *            邮箱设置
	 * @param messageId
	 *            RFC822消息ID
	 * @throws MessagingException
	 */
	@Override
	public void setExtMailSeen(Mailbox mailbox, String messageId)
			throws IcException {
		try {
			receiver.setMessageSeen(mailbox, messageId);
		} catch (MessagingException e) {
			e.printStackTrace();
			if (e.getMessage().contains("Connection timed out")) {
				throw new IcException(MessageUtils.getMessage("JC_OA_IC_025"),
						e);
			}
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_032"), e);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_029"), e);
		}
	}

	/**
	 * 回复邮件
	 * 
	 * @param mailbox
	 *            邮箱设置
	 * @param mail
	 *            邮件实例，必须包括原邮件RFC822消息ID
	 * @param reply
	 *            回复标志（true：回复给发送着；false：回复给所有人）
	 * @throws Exception
	 */
	@Override
	public void replyExtMail(Mailbox mailbox, Mail mail, boolean reply)
			throws IcException {
		try {
			sender.replymail(mailbox, mail, reply);
		} catch (AddressException ae) {
			ae.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_024"));
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
			if (e.getMessage().contains("Connection timed out")) {
				throw new IcException(MessageUtils.getMessage("JC_OA_IC_025"),
						e);
			}
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_033"), e);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_029"), e);
		}
	}

	/**
	 * 方法描述：get infomation from original mail
	 * 
	 * @param mail
	 * @return
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月14日上午9:51:53
	 * @see
	 */
	@Override
	public Mail getReplyInfo(Mail mail, String folderId) throws CustomException {
		Mail originalMail = this.get(mail);
		
		if (originalMail == null) {
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_034"));
		}
		String ss = "";
		String toSs="";
		// 如果原始邮件为群发单显，即使是回复全部也相当于回复发件人
		for (MailRecord record : originalMail.getReceivers()) {
			if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(
					record.getReceiveType())) {
				mail.setReply(0);
				break;
			}
		}
		//发件人
		String senderMailEcho ="";
		// 邮箱Id不等于1为外部邮件
		if (originalMail.getMailboxId().longValue() > Constants.IC_MAIL_MAILBOX_INNER
				.longValue()) {
			mail.setMessageId(originalMail.getMessageId());
			StringBuffer to = new StringBuffer();
			Contacts contacts = new Contacts();
			contacts.setMail(originalMail.getSenderMail());
			contacts.setCreateUser(SystemSecurityUtils.getUser().getId());
			contacts = contactsService.get(contacts);
			if (contacts != null) {
				to.append("{receiveMail:\"")
						.append(originalMail.getSenderMail()).append("\",");
				to.append("contactsId:\"").append(contacts.getId())
						.append("\",");
				to.append("userName:\"").append(contacts.getUserName())
						.append("\"}");
				to.append(",");
			} else {
				to.append("{receiveMail:\"")
						.append(originalMail.getSenderMail()).append("\"}");
				to.append(",");
			}
			Mailbox box = new Mailbox();
			box.setId(originalMail.getMailboxId());
			box = mailboxService.get(box);
			// 回复所有
			if (mail.getReply().intValue() == 1) {
				boolean remove = true;

				List<MailRecord> receivers = originalMail.getReceivers();
				// 得到邮件子记录
				for (int i = receivers.size() - 1; i >= 0; i--) {
					remove = true;
					MailRecord record = receivers.get(i);
					// 如果收信箱为1
					if ("1".equals(folderId)) {
						// 如果是发信人
						if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(
								record.getReceiveType())) {
							receivers.remove(i);
							continue;
						}
					}
					// 如果查询邮箱地址和当前邮箱地址相等删除
					if (box.getAddress().equals(record.getReceiveMail())) {
						remove = false;
					}
					if (!remove) {
						receivers.remove(i);
						continue;
					}
					// 如果是草稿箱
					if (record.getReceiveType().intValue() != 2) {
						Contacts contacts1 = new Contacts();
						contacts1.setMail(record.getReceiveMail());
						contacts1.setCreateUser(SystemSecurityUtils.getUser()
								.getId());
						contacts1 = contactsService.get(contacts1);
						// to.append(record.getReceiveMail()).append(",");
						if (contacts1 != null) {
							to.append("{receiveMail:\"")
									.append(record.getReceiveMail())
									.append("\",");
							to.append("contactsId:\"")
									.append(contacts1.getId()).append("\",");
							to.append("userName:\"")
									.append(contacts1.getUserName())
									.append("\"}");
							to.append(",");
						} else {
							to.append("{receiveMail:\"")
									.append(record.getReceiveMail())
									.append("\"}");
							to.append(",");
						}
					}
				}
			}
			if (to.length() > 0 && to.charAt(to.length() - 1) == ',') {
				to.deleteCharAt(to.length() - 1);
				to.insert(0, "[");
				to.append("]");
				mail.setTo(to.toString());
			}
			senderMailEcho = originalMail.getSenderMail();
			mail.setAttachs(originalMail.getAttachs());
			mail.setSenderMail(box.getAddress());
			mail.setMailboxId(box.getId());
		} else {
			for (MailRecord record : originalMail.getReceivers()) {
				if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(
						record.getReceiveType())
						&& record.getReceiveUserId().longValue() == SystemSecurityUtils
								.getUser().getId().longValue()) {
					toSs = "ss";
				}
			}
			for (MailRecord record : originalMail.getReceivers()) {
				if("ss".equals(toSs)){
					if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(
							record.getReceiveType())) {
						ss += UserUtils.getUser(record.getReceiveUserId()).getDisplayName()+"&nbsp;&nbsp;";
					}
				} else {
					if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(
							record.getReceiveType())
							&& record.getReceiveUserId().longValue() == SystemSecurityUtils
							.getUser().getId().longValue()) {
						ss += UserUtils.getUser(record.getReceiveUserId()).getDisplayName()+"&nbsp;&nbsp;";
					}
				}
			}
			// 回复内部邮件信息
			StringBuffer to = new StringBuffer();
			StringBuffer echoTo = new StringBuffer();

			// 回复发送方
			to.append(originalMail.getSenderUserId()).append(",");

			echoTo.append("{id:").append(originalMail.getSenderUserId())
					.append(",");
			echoTo.append("text:\"")
					.append(UserUtils.getUser(originalMail.getSenderUserId())
							.getDisplayName()).append("\"}");
			echoTo.append(",");
			// 回复所有
			if (mail.getReply().intValue() == 1) {
				List<MailRecord> receivers = originalMail.getReceivers();
				for (int i = receivers.size() - 1; i >= 0; i--) {
					MailRecord record = receivers.get(i);
					// 如果是发信箱folder为“1”，过滤发件人

					if (record.getReceiveType().intValue() != 2) {
						if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(
								record.getReceiveType())) {
							receivers.remove(i);
							continue;
						}
						if (!originalMail.getSenderUserId().equals(
								SystemSecurityUtils.getUser().getId())
								&& record.getReceiveUserId().equals(
										SystemSecurityUtils.getUser().getId())) {
							receivers.remove(i);
							continue;
						}
					}

				}
				for (int i = 0; i <receivers.size(); i++) {
					MailRecord record = receivers.get(i);
					if (record.getReceiveType().intValue() != 2) {
						echoTo.append("{id:").append(record.getReceiveUserId())
								.append(",");
						echoTo.append("text:\"")
								.append(UserUtils.getUser(
										record.getReceiveUserId())
										.getDisplayName()).append("\"}");
						echoTo.append(",");

						to.append(record.getReceiveUserId()).append(",");
					}

				}
			}
			if (echoTo.length() > 0
					&& echoTo.charAt(echoTo.length() - 1) == ',') {
				echoTo.deleteCharAt(echoTo.length() - 1);
				echoTo.insert(0, "[");
				echoTo.append("]");
				mail.setInnerTo(echoTo.toString());
			}

			if (to.charAt(to.length() - 1) == ',') {
				to.deleteCharAt(to.length() - 1);
			}
			senderMailEcho = originalMail.getSenderUserName();
			mail.setTo(to.toString());
			mail.setSenderUserId(SystemSecurityUtils.getUser().getId());
			mail.setMailboxId(Constants.IC_MAIL_MAILBOX_INNER);
		}
		String[] namecc = originalMail.getCc().split(",");
		String[] name = originalMail.getTo().split(",");
		
		String toName="";
		String ccName="";
		String names = "";
		if (originalMail.getMailboxId() == Constants.IC_MAIL_MAILBOX_INNER) {
			if(name[0] != null && !"".equals(name[0])){
				for(int i=0;i<name.length;i++){
						names += UserUtils.getUser(Long.valueOf(name[i])).getDisplayName()+"&nbsp;&nbsp;";
				}
			} else {
				names = (ss==null?"":ss);
			}
			for(int i=0;i<namecc.length;i++){
				if(!"".equals(namecc[i])){
					ccName += UserUtils.getUser(Long.valueOf(namecc[i])).getDisplayName()+"&nbsp;&nbsp;";
				}
			}
			toName = "<br/>"
					+ "<b>收件人:</b>&nbsp;"
					+ names
					+ "<br/>";
			if(!"".equals(ccName)){
				toName +=  "<b>抄送人:</b>&nbsp;" + ccName+ "<br/>";
			}
			toName += "<b>时间:</b>&nbsp;" + DateUtils.formatDate(originalMail.getCreateDate(),"yyyy-MM-dd HH:mm:ss");
		} else {
			if(name[0] != null && !"".equals(name[0])){
				names = originalMail.getTo().substring(0, originalMail.getTo().length()-1);
			} else {
				names = (mail.getToSs()==null?"无收件人":mail.getToSs());
			}
			if(!"".equals(namecc[0])){
				ccName = originalMail.getCc().substring(0, originalMail.getCc().length()-1);
			}
			toName = "<br/>"
					+ "<b>收件人:</b>&nbsp;"
					+ names
					+ "<br/>";
			if(!"".equals(ccName)){
				toName +=  "<b>抄送人:</b>&nbsp;" + ccName+ "<br/>";
			}
			toName += "<b>时间:</b>&nbsp;" + DateUtils.formatDate(originalMail.getCreateDate(),"yyyy-MM-dd HH:mm:ss");
		}
		mail.setMailReplyId(originalMail.getId());
		mail.setCreateUser(originalMail.getCreateUser());
		mail.setMailContent("<br/><br/><br/><br/> ------------------原始邮件内容------------------ <br/><br/><br/><div style='FONT-SIZE:"
				+ "12px;background:#efefef;word-break: break-all;word-wrap: break-word;' class='mail-padding-left'><b>发件人:</b>&nbsp;"
				+ senderMailEcho
				+ toName
				+ "<br/><b>主题:</b>&nbsp;"
				+ originalMail.getMailTitle()
				+ "<br/> <b>内容:</b> <br/>"
				+ originalMail.getMailContent()
				+ "</div><br/><br/><br/><br/>");
		mail.setMailTitle("回复：" + originalMail.getMailTitle());
		return mail;
	}

	/**
	 * 方法描述：获取转发邮件信息
	 * 
	 * @param mail
	 * @return
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月14日上午9:51:53
	 * @see
	 */
	@Override
	public Mail getForwardingInfo(Mail mail, String ids) throws CustomException {
		String senderMailEcho ="";
		if (ids == null) {
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_034"));
		}
		
		// 单邮件转发
		if (ids.split(",").length == 1) {
			mail.setId(new Long(ids.split(",")[0]));
			Mail originalMail = this.get(mail);

			// 如果原始邮件为空，转发失败
			if (originalMail == null) {
				throw new IcException(MessageUtils.getMessage("JC_OA_IC_034"));
			}

			String ss = "";
			String toSs="";
			// 邮箱Id不等于1为外部邮件
			if (originalMail.getMailboxId() > Constants.IC_MAIL_MAILBOX_INNER) {
				// 设置转发的原始邮件MessageId
				mail.setMessageId(originalMail.getMessageId());
				// 根据原始邮件的收件邮箱设置发件箱和邮箱配置
				Mailbox box = new Mailbox();
				box.setId(originalMail.getMailboxId());
				box = mailboxService.get(box);
				mail.setSenderMail(box.getAddress());
				mail.setMailboxId(box.getId());
				senderMailEcho = originalMail.getSenderMail();
			} else {
				for (MailRecord record : originalMail.getReceivers()) {
					if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(
							record.getReceiveType())
							&& record.getReceiveUserId().longValue() == SystemSecurityUtils
									.getUser().getId().longValue()) {
						toSs = "ss";
					}
				}
				for (MailRecord record : originalMail.getReceivers()) {
					if("ss".equals(toSs)){
						if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(
								record.getReceiveType())) {
							ss += UserUtils.getUser(record.getReceiveUserId()).getDisplayName()+"&nbsp;&nbsp;";
						}
					} else {
						if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(
								record.getReceiveType())
								&& record.getReceiveUserId().longValue() == SystemSecurityUtils
								.getUser().getId().longValue()) {
							ss += UserUtils.getUser(record.getReceiveUserId()).getDisplayName()+"&nbsp;&nbsp;";
						}
					}
				}
				
				// 内部邮件转发信息
				mail.setSenderUserId(SystemSecurityUtils.getUser().getId());
				mail.setMailboxId(Constants.IC_MAIL_MAILBOX_INNER);
				senderMailEcho = originalMail.getSenderUserName();
			}
			mail.setMailTitle("转发：" + originalMail.getMailTitle());
			// 转发内容
			// 转发加密邮件时，已经过客户端解密，所以这里可以取出邮件原文。下面代码废除 Update by zhanglg 2014 07 24

			String[] namecc = originalMail.getCc().split(",");
			String[] name = originalMail.getTo().split(",");
			String toName="";
			String ccName="";
			String names = "";
			if (originalMail.getMailboxId() == Constants.IC_MAIL_MAILBOX_INNER) {
				if(name[0] != null && !"".equals(name[0])){
					for(int i=0;i<name.length;i++){
							names += UserUtils.getUser(Long.valueOf(name[i])).getDisplayName()+"&nbsp;&nbsp;";
					}
				} else {
					names = (ss==null?"":ss);
				}
				for(int i=0;i<namecc.length;i++){
					if(!"".equals(namecc[i])){
						ccName += UserUtils.getUser(Long.valueOf(namecc[i])).getDisplayName()+"&nbsp;&nbsp;";
					}
				}
				toName = "<br/>"
						+ "<b>收件人:</b>&nbsp;"
						+ names
						+ "<br/>";
				if(!"".equals(ccName)){
					toName +=  "<b>抄送人:</b>&nbsp;" + ccName+ "<br/>";
				}
				toName += "<b>时间:</b>&nbsp;" + DateUtils.formatDate(originalMail.getCreateDate(),"yyyy-MM-dd HH:mm:ss");
			} else {
				if(name[0] != null && !"".equals(name[0])){
					names = originalMail.getTo().substring(0, originalMail.getTo().length()-1);
				} else {
					names = (mail.getToSs()==null?"无收件人":mail.getToSs());
				}
				if(!"".equals(namecc[0])){
					ccName = originalMail.getCc().substring(0, originalMail.getCc().length()-1);
				}
				toName = "<br/>"
						+ "<b>收件人:</b>&nbsp;"
						+ names
						+ "<br/>";
				if(!"".equals(ccName)){
					toName +=  "<b>抄送人:</b>&nbsp;" + ccName+ "<br/>";
				}
				toName += "<b>时间:</b>&nbsp;" + DateUtils.formatDate(originalMail.getCreateDate(),"yyyy-MM-dd HH:mm:ss");
			}

			mail.setMailContent(FORWARD_SPLIT + originalMail.getMailContent());
			mail.setMailContent(FORWARD_SPLIT+"<div style='FONT-SIZE:"
					+ "12px;background:#efefef;padding:8px;word-break: break-all;word-wrap: break-word;'><b>发件人:</b>&nbsp;"
					+ senderMailEcho
					+ toName
					+ "<br/><b>主题:</b>&nbsp;"
					+ originalMail.getMailTitle()
					+ "<br/> <b>内容:</b> <br/>"
					+ originalMail.getMailContent()
					+ "</div><br/><br/><br/><br/>");
			// if (originalMail.getEncryption().equals(
			// Constants.IC_MAIL_ENCRYPTION_YES)) {
			// mail.setMailContent(FORWARD_SPLIT
			// + "这是一封由<b> "
			// + (UserUtils.getUser(originalMail.getSenderUserId()))
			// .getDisplayName() + " </b>发出的加密邮件，需要解密才能阅读。");
			// } else {
			// mail.setMailContent(FORWARD_SPLIT
			// + originalMail.getMailContent());
			// }
			mail.setIsfile(originalMail.getIsfile());
			mail.setAttachs(originalMail.getAttachs());
		} else {
			// 多邮件转发
			String emailIds[] = ids.split(",");
			ArrayList<Attach> attachs = new ArrayList<Attach>();
			Mailbox box = null;
			Attach attach = null;
			for (int i = 0; i < emailIds.length; i++) {
				mail = new Mail();
				mail.setId(new Long(emailIds[i]));
				Mail originalMail = this.get(mail);
				if (originalMail == null) {
					throw new IcException(
							MessageUtils.getMessage("JC_OA_IC_034"));
				}

				box = new Mailbox();
				box.setId(originalMail.getMailboxId());
				box = mailboxService.get(box);
				mail.setSenderMail(box.getAddress());
				mail.setMailboxId(box.getId());
				try {
					File file = receiver.getMailAsFile(box,
							originalMail.getMessageId());
					attach = new Attach();
					attach.setFileName(file.getName());
					attach.setFileUrl(file.getPath());
					attachs.add(attach);
				} catch (MessagingException | IOException e) {
					e.printStackTrace();
					throw new IcException(
							MessageUtils.getMessage("JC_OA_IC_035"), e);
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new IcException(
							MessageUtils.getMessage("JC_OA_IC_029"), e);
				}

			}
			mail.setMailTitle("转发邮件");
			// 转发内容
			mail.setIsfile(Constants.IC_MAIL_ISFILE_YES);
			// TODO 外网邮件转发附件问题
			// mail.setAttachs(attachs);
		}
		return mail;
	}

	/**
	 * 更新已读、未读标志
	 * 
	 * @throws CustomException
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setReadStatus(Mail mail, String mrids, String status)
			throws Exception {
		if (mail.getPrimaryKeys() == null) {
			mail.setPrimaryKeys(new String[] { mail.getId().toString() });
		}
		String[] primaryKeys = mail.getPrimaryKeys();
		MailRecord record = null;
		Mailbox box = null;
		for (int i = 0; i < primaryKeys.length; i++) {
			
		/*	//设置提醒的状态为已读
			//update by 杨佶
			NoticeMsg noticeMsg = new NoticeMsg();
			noticeMsg.setBusinessId(Long.parseLong(primaryKeys[i]));
			noticeMsg.setReadFlag(1);
			noticeMsg.setBusinessFlag("tty_ic_mail");
			noticeMsg.setModifyDate(mail.getModifyDate());
			noticeService.update(noticeMsg);*/
			
			
			mail = new Mail();
			mail.setId(new Long(primaryKeys[i]));
			mail = mailDao.get(mail);

			record = new MailRecord();
			record.setMailId(mail.getId());
			record.setDeleteFlag(0);
			mail.setReceivers(mailRecordService.queryAll(record));
			String[] mridsArr = mrids.split(",");
			// 内部邮件
			if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
				Long mrid = new Long(mridsArr[i]);
				for (MailRecord mr : mail.getReceivers()) {
					// if (mr.getReceiveUserId().longValue() ==
					// SystemSecurityUtils
					// .getUser().getId()) {
					if (mr.getId().equals(mrid)) {
						mr.setReadFlag(new Integer(status));
						if (mr.getReadDate() == null
								&& Constants.IC_MAIL_MAILSTATUS_READED.equals(
										mr.getReadFlag())) {
							mr.setReadDate(new Date());
						}
						mailRecordService.update(mr);
					}
				}
			} else {// 外部邮件
				box = new Mailbox();
				box.setId(mail.getMailboxId());
				box = mailboxService.get(box);
				// 设置邮件为已读状态
				for (MailRecord mr : mail.getReceivers()) {
					Long mrid1 = new Long(mridsArr[i]);
					// if (mr.getReceiveMail().equals(box.getAddress())) {
					if (mr.getId().equals(mrid1)) {
						mr.setReadFlag(new Integer(status));
						if (mr.getReadDate() == null&& Constants.IC_MAIL_MAILSTATUS_READED.equals(mr.getReadFlag())) {
							mr.setReadDate(new Date());
						}
						mailRecordService.update(mr);
						break;
					}
				}
			}
		}
	}

	/**
	 * 方法描述：更新星标
	 * 
	 * @param mail
	 *            邮件实例
	 * @return
	 * @author zhanglg
	 * @version 2014年5月8日下午4:27:12
	 * @throws CustomException
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer setStarFlag(Mail mail, String mrids, Long folderId)
			throws CustomException {
		if (mail.getPrimaryKeys() == null) {
			mail.setPrimaryKeys(new String[] { mail.getId().toString() });
		}
		String[] primaryKeys = mail.getPrimaryKeys();
		String[] mridsArr = mrids.split(",");
		List<MailRecord> list = null;
		MailRecord receiver = null;
		MailRecord record =null;
		Mailbox box = null;
		Long mrid =null;
		Long mrid1 =null;
		for (int i = 0; i < primaryKeys.length; i++) {
			// for (String id : mail.getPrimaryKeys()) {
			mail.setId(new Long(primaryKeys[i]));
			list = new ArrayList<MailRecord>();
			receiver = new MailRecord();
			receiver.setFolderId(folderId);
			list.add(receiver);
			mail.setReceivers(list);
			mail = mailDao.get(mail);

			record = new MailRecord();
			record.setMailId(mail.getId());
			record.setDeleteFlag(0);
			mail.setReceivers(mailRecordService.queryAll(record));

			if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
				 mrid = new Long(mridsArr[i]);
				for (MailRecord mr : mail.getReceivers()) {
					// if (mr.getReceiveUserId().longValue() ==
					// SystemSecurityUtils
					// .getUser().getId()
					// && mr.getFolderId().equals(folderId)) {
					if (mr.getId().equals(mrid)) {
						if (mr.getStarMail().intValue() == Constants.IC_MAIL_STARMAIL_YES) {
							mr.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
						} else {
							mr.setStarMail(Constants.IC_MAIL_STARMAIL_YES);
						}
						mailRecordService.update(mr);
						break;
					}
				}
			} else {
				box = new Mailbox();
				box.setId(mail.getMailboxId());
				box = mailboxService.get(box);
				mrid1 = new Long(mridsArr[i]);
				// 设置邮件为已读状态
				for (MailRecord mr : mail.getReceivers()) {
					// if (mr.getReceiveMail().equals(box.getAddress())) {
					if (mr.getId().equals(mrid1)) {
						if (mr.getStarMail().intValue() == Constants.IC_MAIL_STARMAIL_YES) {
							mr.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
						} else {
							mr.setStarMail(Constants.IC_MAIL_STARMAIL_YES);
						}
						mailRecordService.update(mr);
						break;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 方法描述：移动到···文件夹
	 * 
	 * @param mail
	 *            邮件实例
	 * @return
	 * @author zhanglg
	 * @version 2014年5月8日下午4:27:12
	 * @throws CustomException
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void moveTo(Mail mail, String mrids, Long fromFolder, Long toFolder)
			throws Exception {

		if (mail.getPrimaryKeys() == null) {
			mail.setPrimaryKeys(new String[] { mail.getId().toString() });
		}
		String[] primaryKeys = mail.getPrimaryKeys();
		String[] mridsArr = mrids.split(",");
		List<MailRecord> list =null;
		MailRecord receiver = null;
		Mailbox box = null;
		Long mrid = null;
		for (int i = 0; i < primaryKeys.length; i++) {
			mail = new Mail();
			mail.setId(new Long(primaryKeys[i]));
			list = new ArrayList<MailRecord>();
			receiver = new MailRecord();
			receiver.setFolderId(fromFolder);
			list.add(receiver);
			mail.setReceivers(list);

			mail = mailDao.get(mail);
			mrid = new Long(mridsArr[i]);
			if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {

				for (MailRecord mr : mail.getReceivers()) {
					if (mr.getId().equals(mrid)) {
						// if (mr.getReceiveUserId().longValue() ==
						// SystemSecurityUtils
						// .getUser().getId()) {
						mr.setFolderId(toFolder);
						mr.setId(new Long(mridsArr[i]));
						mailRecordService.update(mr);
						break;
						// }
					}
				}
			} else {
				box = new Mailbox();
				box.setId(mail.getMailboxId());
				box = mailboxService.get(box);
				// 设置邮件为已读状态
				for (MailRecord mr : mail.getReceivers()) {
					// if (mr.getReceiveMail().equals(box.getAddress())) {
					if (mr.getId().equals(mrid)) {
						mr.setFolderId(toFolder);
						mr.setId(new Long(mrid));
						mailRecordService.update(mr);
						break;
					}
				}
			}
		}
	}

	/**
	 * 方法描述：取得邮件
	 * 
	 * @param mail
	 *            邮件实例
	 * @return
	 * @author zhanglg
	 * @version 2014年5月12日下午2:37:04
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Mail get(Mail mail) throws CustomException {
		String consigneeStatus = mail.getConsigneeStatus();
		mail = super.get(mail);
		if(mail.getMailContent()!=null){
		mail.setMailContent(mail.getMailContent().replace("\n", "<br>"));
		}
		StringBuffer to = new StringBuffer();
		StringBuffer cc = new StringBuffer();
		StringBuffer bcc = new StringBuffer();
		StringBuffer ss = new StringBuffer();
		if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			StringBuffer echoTo = new StringBuffer();
			StringBuffer echoCc = new StringBuffer();
			StringBuffer echoBcc = new StringBuffer();
			StringBuffer echoSs = new StringBuffer();
			for (MailRecord record : mail.getReceivers()) {
				if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(
						record.getReceiveType())) {
					continue;
				}

				if (Constants.IC_MAIL_RECEIVETYPE_TO.equals(record.getReceiveType()
						)) {
					to.append(record.getReceiveUserId()).append(",");
					echoTo.append("{id:").append(record.getReceiveUserId())
							.append(",");
					echoTo.append("text:\"")
							.append(UserUtils
									.getUser(record.getReceiveUserId())
									.getDisplayName()).append("\"}");
					echoTo.append(",");
				} else if (Constants.IC_MAIL_RECEIVETYPE_CC.equals(
						record.getReceiveType())) {
					cc.append(record.getReceiveUserId()).append(",");

					echoCc.append("{id:").append(record.getReceiveUserId())
							.append(",");
					echoCc.append("text:\"")
							.append(UserUtils
									.getUser(record.getReceiveUserId())
									.getDisplayName()).append("\"}");
					echoCc.append(",");
				} else if (Constants.IC_MAIL_RECEIVETYPE_BCC.equals(
						record.getReceiveType())) {
					bcc.append(record.getReceiveUserId()).append(",");
					echoBcc.append("{id:").append(record.getReceiveUserId())
							.append(",");
					echoBcc.append("text:\"")
							.append(UserUtils
									.getUser(record.getReceiveUserId())
									.getDisplayName()).append("\"}");
					echoBcc.append(",");
				} else if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(
						record.getReceiveType())) {
					ss.append(record.getReceiveUserId()).append(",");
					echoSs.append("{id:").append(record.getReceiveUserId())
							.append(",");
					echoSs.append("text:\"")
							.append(UserUtils
									.getUser(record.getReceiveUserId())
									.getDisplayName()).append("\"}");
					echoSs.append(",");
				}

			}

			if (echoTo.length() > 0
					&& echoTo.charAt(echoTo.length() - 1) == ',') {
				echoTo.deleteCharAt(echoTo.length() - 1);
				echoTo.insert(0, "[");
				echoTo.append("]");
				mail.setInnerTo(echoTo.toString());
			}

			if (echoCc.length() > 0
					&& echoCc.charAt(echoCc.length() - 1) == ',') {
				echoCc.deleteCharAt(echoCc.length() - 1);
				echoCc.insert(0, "[");
				echoCc.append("]");
				mail.setInnerCc(echoCc.toString());
			}

			if (echoBcc.length() > 0
					&& echoBcc.charAt(echoBcc.length() - 1) == ',') {
				echoBcc.deleteCharAt(echoBcc.length() - 1);
				echoBcc.insert(0, "[");
				echoBcc.append("]");
				mail.setInnerBcc(echoBcc.toString());
			}

			if (echoSs.length() > 0
					&& echoSs.charAt(echoSs.length() - 1) == ',') {
				echoSs.deleteCharAt(echoSs.length() - 1);
				echoSs.insert(0, "[");
				echoSs.append("]");
				mail.setInnerSs(echoSs.toString());
			}

		} else {
			for (MailRecord record : mail.getReceivers()) {
				if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(
						record.getReceiveType())) {
					continue;
				}

				if (Constants.IC_MAIL_RECEIVETYPE_TO.equals(
						record.getReceiveType())) {
					to.append(record.getReceiveMail()).append(",");
				} else if (Constants.IC_MAIL_RECEIVETYPE_CC.equals(
						record.getReceiveType())) {
					cc.append(record.getReceiveMail()).append(",");
				} else if (Constants.IC_MAIL_RECEIVETYPE_BCC.equals(
						record.getReceiveType())) {
					bcc.append(record.getReceiveMail()).append(",");
				} else if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(
						record.getReceiveType())) {
					ss.append(record.getReceiveMail()).append(",");
				}

				mail.setInnerTo(to.toString());
				mail.setInnerCc(cc.toString());
				mail.setInnerBcc(bcc.toString());
			}
		}

		mail.setTo(to.toString());
		mail.setCc(cc.toString());
		mail.setBcc(bcc.toString());

		Attach attach = new Attach();
		attach.setBusinessId(Long.valueOf(mail.getId()));
		attach.setBusinessTable(TABLE_NAME);
		attach.setIsPaged("1");
		PageManager page = new PageManager();
		page.setPageRows(100);
		PageManager page_ = attachService.query(attach, page);
		if (page_ != null) {
			mail.setAttachs((List<Attach>) page_.getData());
		}

		mail.setPrimaryKeys(new String[] { mail.getId().toString() });
		if (StringUtil.isEmpty(consigneeStatus)) {
			// TODO
			// this.setReadStatus(mail,
			// Constants.IC_MAIL_MAILSTATUS_READED.toString());
		}
		return mail;
	}

	/**
	 * 方法描述：导航查询方法，根据当前邮件Id和导航方向查询上/下一封邮件
	 * 
	 * @param mail
	 *            邮件实例
	 * @return
	 * @author zhanglg
	 * @version 2014年5月12日下午2:37:04
	 * @throws CustomException
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Mail navigate(Mail mail) throws CustomException {

		mail = mailDao.navigate(mail);
		MailRecord record = new MailRecord();
		record.setMailId(mail.getId());
		record.setDeleteFlag(0);
		mail.setReceivers(mailRecordService.queryAll(record));

		// 设置邮件为已读状态
		if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			for (MailRecord mr : mail.getReceivers()) {
				if (mr.getReceiveUserId().longValue() == SystemSecurityUtils
						.getUser().getId()
						&& Constants.IC_MAIL_MAILSTATUS_UNREAD.equals(
								mr.getReadFlag())) {
					mr.setReadFlag(Constants.IC_MAIL_MAILSTATUS_READED);
					mailRecordService.update(mr);
				}
			}
		} else {
			Mailbox box = new Mailbox();
			box.setId(mail.getMailboxId());
			box = mailboxService.get(box);
			// 设置邮件为已读状态
			for (MailRecord mr : mail.getReceivers()) {
				if (mr.getReceiveMail().equals(box.getAddress())
						&&Constants.IC_MAIL_MAILSTATUS_UNREAD.equals(
								 mr.getReadFlag())) {
					mr.setReadFlag(Constants.IC_MAIL_MAILSTATUS_READED);
					mailRecordService.update(mr);
				}
			}
		}
		return mail;
	}

	@Override
	public List<Mailbox> getMailboxList(Mailbox mailbox) throws CustomException {
		mailbox.setCreateUser(SystemSecurityUtils.getUser().getId());
		return mailboxService.queryAll(mailbox);
	}

	/**
	 * 方法描述：发送邮件，并保存到数据库
	 * 
	 * @param mail
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月13日下午2:48:15
	 * @param request
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer sendMail(Mail mail, HttpServletRequest request)
			throws CustomException {
		Integer result = 0;
		if (mail.getReceivers() == null) {
			mail.setReceivers(new ArrayList<MailRecord>());
		}

		// 如果是新邮件，保存邮件记录
		if (mail.getId() == null) {

			MailRecord record = new MailRecord();
			record.setMailId(mail.getId());
			if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
				record.setReceiveMail(mail.getSenderMail());
			} else {
				record.setReceiveUserId(mail.getSenderUserId() == null ? SystemSecurityUtils.getUser().getId() : mail.getSenderUserId());
			}
			record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_INNERSENDER);
			record.setFolderId(Constants.IC_MAIL_MAILFOLDER_SENDED);
			record.setReadFlag(Constants.IC_MAIL_MAILSTATUS_READED);
			record.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
			record.setRemindFlag(Constants.IC_MAIL_UNREMIND_FLAG);
			record.setReplyFlag(Constants.IC_MAIL_UNREPLY_FLAG);
			if (mail.getReceivers() == null) {
				mail.setReceivers(new ArrayList<MailRecord>());
			}
			mail.getReceivers().add(record);

		} else {// 如果是草稿箱中的邮件，更新邮件记录到已发送

			MailRecord record = new MailRecord();
			record.setMailId(mail.getId());
			mailRecordService.deleteByMailId(record);

			record = new MailRecord();
			record.setMailId(mail.getId());
			if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
				record.setReceiveMail(mail.getSenderMail());
			}
			record.setReceiveUserId(SystemSecurityUtils.getUser().getId());
			record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_INNERSENDER);
			record.setFolderId(Constants.IC_MAIL_MAILFOLDER_SENDED);
			record.setReadFlag(Constants.IC_MAIL_MAILSTATUS_READED);
			record.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
			record.setRemindFlag(Constants.IC_MAIL_UNREMIND_FLAG);
			record.setReplyFlag(Constants.IC_MAIL_UNREPLY_FLAG);
			if (mail.getReceivers() == null) {
				mail.setReceivers(new ArrayList<MailRecord>());
			}
			mail.getReceivers().add(record);
		}
		this.preprocesseMail(mail);
		// 外部邮件，准备发射！！
		if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			Mailbox box = new Mailbox();
			box.setId(mail.getMailboxId());
			box = mailboxService.get(box);

			boolean isShowSingle = false;
			for (MailRecord record : mail.getReceivers()) {
				if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(
						record.getReceiveType())) {
					isShowSingle = true;
				}
			}
			if (isShowSingle) {
				List<MailRecord> list = mail.getReceivers();
				// 群发单显
				for (MailRecord record : list) {
					mail.setReceivers(new ArrayList<MailRecord>());
					if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(
							record.getReceiveType())) {
						record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_TO);
						mail.getReceivers().add(record);
						this.sendExtMail(box, mail, request);
						record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE);
					}
				}
				mail.setReceivers(list);
			} else {
				this.sendExtMail(box, mail, request);
			}
		}

		// 特殊处理一下外部邮件收件人是自己的情况
		if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			// this.update(mail);
			for (MailRecord record : mail.getReceivers()) {
				if (record.getReceiveMail().equals(mail.getSenderMail())
						&& !Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(
								record.getReceiveType())) {
					mail.setReceiveTime2(new Date());
				}
			}
		}

		// 回复邮件提醒
		// 内部邮箱
//		if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
//			if (mail.getMailReplyId() != null) {
//				MailRecord mailrecord = new MailRecord();
//				mailrecord.setMailId(mail.getMailReplyId());
//				mailrecord.setReceiveUserId(mail.getSenderUserId());
//				// 因收件人、抄送、密送、发件人都可能是自己，此处查询结果应是列表
//				List<MailRecord> receivers = mailRecordService
//						.queryAll(mailrecord);
//				if (receivers != null) {
//					for (MailRecord mailRecord2 : receivers) {
//						// 对类型不是发件人的记录设置回复标识
//						if (!Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(
//								mailRecord2.getReceiveType())) {
//							// 设置回复标识
//							mailrecord
//									.setReplyFlag(Constants.IC_MAIL_REPLY_FLAG);
//							mailRecordService.setReplyFlag(mailrecord);
//						}
//					}
//				}
//			}
//		}

		// 短信提醒
		if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())
				&& Constants.IC_MAIL_SMSALERT_YES.equals(mail.getSmsAlert())) {
			Out out = new Out();
			// StringBuffer sb = new StringBuffer();
			// for (MailRecord record : mail.getReceivers()) {
			// sb.append(record.getReceiveUserId()).append(",");
			// }
			// if (sb.charAt(sb.length() - 1) == ',') {
			// sb.deleteCharAt(sb.length() - 1);
			// }
			// 用户id用“，”分割
			out.setUserId(mail.getSmsReceiver());
			// 短信内容
			out.setText(Constants.IC_MAIL_SEND_MESSAGE);
			// 短信类型
			out.setSendType("sendType_3");
			// 发送人
			out.setCreateUser(mail.getSenderUserId());
			try {
				smsOutService.sendAndSave(out);
			} catch (Exception e) {
				throw new IcException(MessageUtils.getMessage("JC_OA_IC_004"),
						e);
			}
		}
		result = this.save(mail);
		
		//向移动端发送消息，通知有新邮件
//		try {
//			usermapService.pushMessage(mail.getReceivers(),mail.getMailTitle(),"你有一封新邮件","");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//邮件提醒
		if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			NoticeMsg noticeMsg = new NoticeMsg();
			List<MailRecord> rlist = mail.getReceivers();
			for (MailRecord mailRecord : rlist) {
				if(!Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(mailRecord.getReceiveType())){
					//弹出窗口提醒
					noticeMsg.setId(null);
					noticeMsg.setTitle(mail.getMailTitle());
					noticeMsg.setReceiveUser(mailRecord.getReceiveUserId());
					noticeMsg.setContent(mail.getMailContent());
					noticeMsg.setSendUser(mail.getSenderUserId());
					noticeMsg.setNoticeType("邮件提醒");
					noticeMsg.setBusinessFlag("tty_ic_mail");
					noticeMsg.setBusinessId(mail.getId());
					noticeMsg.setUrl("/ic/mail/manageDetailContent.action?id="+ mail.getId() + "&index=0&mrid="+ mailRecord.getId()+ "&folderId=1");
					noticeMsg.setExtStr1("/ic/mail/manageUnread.action");
					NoticeMsgUtil.notice(noticeMsg);
				}
			}
		}
		return result;
	}

	/**
	 * 方法描述：发送邮件，并保存到数据库
	 * 
	 * @param mail
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月13日下午2:48:15
	 * @param request
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer transMail(Mail mail, HttpServletRequest request)
			throws Exception {
		Integer result = 0;
		if (mail.getReceivers() == null) {
			mail.setReceivers(new ArrayList<MailRecord>());
		}

		// 如果是新邮件，保存邮件记录
		if (mail.getId() == null) {

			MailRecord record = new MailRecord();
			record.setMailId(mail.getId());
			if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
				record.setReceiveMail(mail.getSenderMail());
			} else {
				record.setReceiveUserId(mail.getSenderUserId() == null ? SystemSecurityUtils
						.getUser().getId() : mail.getSenderUserId());
			}
			record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_INNERSENDER);
			record.setFolderId(Constants.IC_MAIL_MAILFOLDER_SENDED);
			record.setReadFlag(Constants.IC_MAIL_MAILSTATUS_READED);
			record.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
			record.setRemindFlag(Constants.IC_MAIL_UNREMIND_FLAG);
			record.setReplyFlag(Constants.IC_MAIL_UNREPLY_FLAG);
			if (mail.getReceivers() == null) {
				mail.setReceivers(new ArrayList<MailRecord>());
			}
			mail.getReceivers().add(record);

		} else {// 如果是草稿箱中的邮件，更新邮件记录到已发送

			MailRecord record = new MailRecord();
			record.setMailId(mail.getId());
			mailRecordService.deleteByMailId(record);

			record = new MailRecord();
			record.setMailId(mail.getId());
			if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
				record.setReceiveMail(mail.getSenderMail());
			}
			record.setReceiveUserId(SystemSecurityUtils.getUser().getId());
			record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_INNERSENDER);
			record.setFolderId(Constants.IC_MAIL_MAILFOLDER_SENDED);
			record.setReadFlag(Constants.IC_MAIL_MAILSTATUS_READED);
			record.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
			record.setRemindFlag(Constants.IC_MAIL_UNREMIND_FLAG);
			record.setReplyFlag(Constants.IC_MAIL_UNREPLY_FLAG);
			if (mail.getReceivers() == null) {
				mail.setReceivers(new ArrayList<MailRecord>());
			}
			mail.getReceivers().add(record);
		}
		this.preprocesseMail(mail);
		// 外部邮件，准备发射！！
		if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			Mailbox box = new Mailbox();
			box.setId(mail.getMailboxId());
			box = mailboxService.get(box);

			boolean isShowSingle = false;
			for (MailRecord record : mail.getReceivers()) {
				if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(
						record.getReceiveType())) {
					isShowSingle = true;
				}
			}
			if (isShowSingle) {
				List<MailRecord> list = mail.getReceivers();
				// 群发单显
				for (MailRecord record : list) {
					mail.setReceivers(new ArrayList<MailRecord>());
					if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(
							record.getReceiveType())) {
						record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_TO);
						mail.getReceivers().add(record);
						this.transExtMail(box, mail, request);
						record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE);
					}
				}
				mail.setReceivers(list);
			} else {
				this.transExtMail(box, mail, request);
			}
		}

		// 特殊处理一下外部邮件收件人是自己的情况
		if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			// this.update(mail);
			for (MailRecord record : mail.getReceivers()) {
				if (record.getReceiveMail().equals(mail.getSenderMail())
						&& !Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(
								record.getReceiveType())) {
					mail.setReceiveTime2(new Date());
				}
			}
		}

		// 回复邮件提醒
		// 内部邮箱
//		if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
//			if (mail.getMailReplyId() != null) {
//				MailRecord mailrecord = new MailRecord();
//				mailrecord.setMailId(mail.getMailReplyId());
//				mailrecord.setReceiveUserId(mail.getSenderUserId());
//				// 因收件人、抄送、密送、发件人都可能是自己，此处查询结果应是列表
//				List<MailRecord> receivers = mailRecordService
//						.queryAll(mailrecord);
//				if (receivers != null) {
//					for (MailRecord mailRecord2 : receivers) {
//						// 对类型不是发件人的记录设置回复标识
//						if (!Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(
//								mailRecord2.getReceiveType())) {
//							// 设置回复标识
//							mailrecord
//									.setReplyFlag(Constants.IC_MAIL_REPLY_FLAG);
//							mailRecordService.setReplyFlag(mailrecord);
//						}
//					}
//				}
//			}
//		}
		// 短信提醒
		if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())
				&&Constants.IC_MAIL_SMSALERT_YES.equals(mail.getSmsAlert())) {
			Out out = new Out();
			// 用户id用“，”分割
			out.setUserId(mail.getSmsReceiver());
			// 短信内容
			out.setText(Constants.IC_MAIL_SEND_MESSAGE);
			// 短信类型
			out.setSendType("sendType_1");
			// 发送人
			out.setCreateUser(mail.getSenderUserId());
			try {
				smsOutService.sendAndSave(out);
			} catch (Exception e) {
				throw new IcException(MessageUtils.getMessage("JC_OA_IC_004"),
						e);
			}
		}
		result = this.save(mail);
		
		//邮件提醒
		if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			NoticeMsg noticeMsg = new NoticeMsg();
			List<MailRecord> rlist = mail.getReceivers();
			for (MailRecord mailRecord : rlist) {
				if(!Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(mailRecord.getReceiveType())){
					//弹出窗口提醒
					noticeMsg.setId(null);
					noticeMsg.setTitle(mail.getMailTitle());
					noticeMsg.setReceiveUser(mailRecord.getReceiveUserId());
					noticeMsg.setContent(mail.getMailContent());
					noticeMsg.setSendUser(mail.getSenderUserId());
					noticeMsg.setNoticeType("邮件提醒");
					noticeMsg.setBusinessFlag("tty_ic_mail");
					noticeMsg.setBusinessId(mail.getId());
					noticeMsg.setUrl("/ic/mail/manageDetailContent.action?id="+ mail.getId() + "&index=0&mrid="+ mailRecord.getId()+ "&folderId=1");
					noticeMsg.setExtStr1("/ic/mail/manageUnread.action");
					NoticeMsgUtil.notice(noticeMsg);
				}
			}
		}
		
		return result;
	}

	
	/**
	 * 方法描述：发送邮件，并保存到数据库，用于外模块的门面调用 外模块调用不会发外部邮件
	 * 
	 * @param mail
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月13日下午2:48:15
	 * @param request
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer sendMail(Mail mail) throws CustomException {
		Integer result = 0;
		if (mail.getReceivers() == null) {
			mail.setReceivers(new ArrayList<MailRecord>());
		}

		// 如果是新邮件，保存邮件记录
		if (mail.getId() == null) {

			MailRecord record = new MailRecord();
			record.setMailId(mail.getId());
			if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
				record.setReceiveMail(mail.getSenderMail());
			} else {
				record.setReceiveUserId(mail.getSenderUserId() == null ? SystemSecurityUtils
						.getUser().getId() : mail.getSenderUserId());
			}
			record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_INNERSENDER);
			record.setFolderId(Constants.IC_MAIL_MAILFOLDER_SENDED);
			record.setReadFlag(Constants.IC_MAIL_MAILSTATUS_READED);
			record.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
			record.setRemindFlag(Constants.IC_MAIL_UNREMIND_FLAG);
			record.setReplyFlag(Constants.IC_MAIL_UNREPLY_FLAG);
			if (mail.getReceivers() == null) {
				mail.setReceivers(new ArrayList<MailRecord>());
			}
			mail.getReceivers().add(record);

		} else {// 如果是草稿箱中的邮件，更新邮件记录到已发送

			MailRecord record = new MailRecord();
			record.setMailId(mail.getId());
			mailRecordService.deleteByMailId(record);

			record = new MailRecord();
			record.setMailId(mail.getId());
			if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
				record.setReceiveMail(mail.getSenderMail());
			}
			record.setReceiveUserId(SystemSecurityUtils.getUser().getId());
			record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_INNERSENDER);
			record.setFolderId(Constants.IC_MAIL_MAILFOLDER_SENDED);
			record.setReadFlag(Constants.IC_MAIL_MAILSTATUS_READED);
			record.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
			record.setRemindFlag(Constants.IC_MAIL_UNREMIND_FLAG);
			record.setReplyFlag(Constants.IC_MAIL_UNREPLY_FLAG);
			if (mail.getReceivers() == null) {
				mail.setReceivers(new ArrayList<MailRecord>());
			}
			mail.getReceivers().add(record);
		}
		this.preprocesseMail(mail);
		// 外部邮件，准备发射！！
		// if (!mail.getMailboxId().equals(Constants.IC_MAIL_MAILBOX_INNER)) {
		// Mailbox box = new Mailbox();
		// box.setId(mail.getMailboxId());
		// box = mailboxService.get(box);
		//
		// boolean isShowSingle = false;
		// for (MailRecord record : mail.getReceivers()) {
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE)) {
		// isShowSingle = true;
		// }
		// }
		// if (isShowSingle) {
		// List<MailRecord> list = mail.getReceivers();
		// // 群发单显
		// for (MailRecord record : list) {
		// mail.setReceivers(new ArrayList<MailRecord>());
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE)) {
		// record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_TO);
		// mail.getReceivers().add(record);
		// // this.sendExtMail(box, mail,request);
		// record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE);
		// }
		// }
		// mail.setReceivers(list);
		// } else {
		// // this.sendExtMail(box, mail,request);
		// }
		// }

		// 特殊处理一下外部邮件收件人是自己的情况
		// if (!mail.getMailboxId().equals(Constants.IC_MAIL_MAILBOX_INNER)) {
		// // this.update(mail);
		// for (MailRecord record : mail.getReceivers()) {
		// if (record.getReceiveMail().equals(mail.getSenderMail())
		// && !record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
		// mail.setReceiveTime2(new Date());
		// }
		// }
		// }

		// 短信提醒
		if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())
				&& Constants.IC_MAIL_SMSALERT_YES.equals(mail.getSmsAlert())) {
			Out out = new Out();
			// StringBuffer sb = new StringBuffer();
			// for (MailRecord record : mail.getReceivers()) {
			// sb.append(record.getReceiveUserId()).append(",");
			// }
			// if (sb.charAt(sb.length() - 1) == ',') {
			// sb.deleteCharAt(sb.length() - 1);
			// }
			// 用户id用“，”分割
			out.setUserId(mail.getSmsReceiver());
			// 短信内容
			out.setText(Constants.IC_MAIL_SEND_MESSAGE);
			// 短信类型
			out.setSendType("sendType_1");
			// 发送人
			out.setCreateUser(mail.getSenderUserId());
			try {
				smsOutService.sendAndSave(out);
			} catch (Exception e) {
				throw new IcException(MessageUtils.getMessage("JC_OA_IC_004"),
						e);
			}
		}
		result = this.save(mail);
		
		//邮件提醒
		if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			NoticeMsg noticeMsg = new NoticeMsg();
			List<MailRecord> rlist = mail.getReceivers();
			for (MailRecord mailRecord : rlist) {
				if(!Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(mailRecord.getReceiveType())){
					//弹出窗口提醒
					noticeMsg.setId(null);
					noticeMsg.setTitle(mail.getMailTitle());
					noticeMsg.setReceiveUser(mailRecord.getReceiveUserId());
					noticeMsg.setContent(mail.getMailContent());
					noticeMsg.setSendUser(mail.getSenderUserId());
					noticeMsg.setNoticeType("邮件提醒");
					noticeMsg.setBusinessFlag("tty_ic_mail");
					noticeMsg.setBusinessId(mail.getId());
					noticeMsg.setUrl("/ic/mail/manageDetailContent.action?id="+ mail.getId() + "&index=0&mrid="+ mailRecord.getId()+ "&folderId=1");
					noticeMsg.setExtStr1("/ic/mail/manageUnread.action");
					NoticeMsgUtil.notice(noticeMsg);
				}
			}
		}
		
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer save(Mail mail) throws CustomException {

		Integer counts = null;

		this.preprocesseMail(mail);

		if (mail.getId() == null) {
			counts = super.save(mail);
		} else {
			counts = super.update(mail);
			MailRecord record = new MailRecord();
			record.setMailId(mail.getId());
			mailRecordService.deleteByMailId(record);
		}
		
		Mailbox mailbox =  new Mailbox();
		mailbox.setId(mail.getMailboxId());
		mailbox = mailboxService.get(mailbox);
		
		for (MailRecord record : mail.getReceivers()) {
			if(mailbox!=null&&mailbox.getId()>1){
				if(mailbox.getAddress().equals(record.getReceiveMail())){
					record.setMailId(mail.getId());
				}else{
					record.setMailId(mail.getId());
					record.setFolderId(-1l);
				}
			}else{
				record.setMailId(mail.getId());
			}
			mailRecordService.save(record);
		}

		// 删除页面中上传中删除的附件
		if (!StringUtil.isEmpty(mail.getDelattachIds())) {
			uploadService.deleteFileByIds(mail.getDelattachIds());
		}
		if (mail.getFileids() != null && !"".equals(mail.getFileids())) {
			String fileIds[] = mail.getFileids().split(",");
			// 保存附件
			if (fileIds != null && fileIds.length > 0 && !"".equals(fileIds[0])) {
				// 查询所需修改的图片信息
				AttachBusiness attachBusines = new AttachBusiness();
				attachBusines.setBusinessId(mail.getId());
				attachBusines.setBusinessTable(TABLE_NAME);
				List<AttachBusiness> list = attachBusinessService
						.queryAll(attachBusines);
				// 删除所需修改的图片信息及图片文件
				AttachBusiness delAttachBusiness = new AttachBusiness();
				String[] keys = new String[list.size()];
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						keys[i] = list.get(i).getId().toString();
					}
					delAttachBusiness.setPrimaryKeys(keys);
					attachBusinessService.delete(delAttachBusiness, false);
				}
				// 保存新的图片信息
				AttachBusiness attachBusiness = null;
				for (int i = 0; i < fileIds.length; i++) {
					attachBusiness = new AttachBusiness();
					attachBusiness.setAttachId(new Long(fileIds[i]));
					attachBusiness.setBusinessId(mail.getId());
					attachBusiness.setBusinessTable(TABLE_NAME);
					attachBusinessService.save(attachBusiness);
				}
			}
		}
		return counts;
	}

	private void preprocesseMail(Mail mail) {

		mail.setSenderTime(new Date());
		if (mail.getReceivers() == null) {
			mail.setReceivers(new ArrayList<MailRecord>());
		}

		// 群发单显
		if (mail.getShowSingle() != null && mail.getShowSingle().length() > 0) {
			String[] ss = mail.getShowSingle().split(",");
			MailRecord record = null;
			for (String showSingle : ss) {
				record = new MailRecord();
				if (!Constants.IC_MAIL_MAILBOX_INNER
						.equals(mail.getMailboxId())) {
					record.setReceiveMail(showSingle);
				} else {
					record.setReceiveUserId(new Long(showSingle));
				}
				record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE);
				record.setFolderId(Constants.IC_MAIL_MAILFOLDER_INBOX);
				record.setReadFlag(Constants.IC_MAIL_MAILSTATUS_UNREAD);
				record.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
				record.setRemindFlag(Constants.IC_MAIL_UNREMIND_FLAG);
				record.setReplyFlag(Constants.IC_MAIL_UNREPLY_FLAG);
				mail.getReceivers().add(record);
			}
			mail.setShowSingle(null);
		} else {
			// 收件人
			if (mail.getTo() != null && mail.getTo().length() > 0) {
				String[] tos = mail.getTo().split(",");
				MailRecord record = null;
				for (String to : tos) {
					record = new MailRecord();
					if (!Constants.IC_MAIL_MAILBOX_INNER.equals(
							mail.getMailboxId())) {
						record.setReceiveMail(to);
					} else {
						record.setReceiveUserId(new Long(to));
					}
					record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_TO);
					record.setFolderId(Constants.IC_MAIL_MAILFOLDER_INBOX);
					record.setReadFlag(Constants.IC_MAIL_MAILSTATUS_UNREAD);
					record.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
					record.setRemindFlag(Constants.IC_MAIL_UNREMIND_FLAG);
					record.setReplyFlag(Constants.IC_MAIL_UNREPLY_FLAG);
					mail.getReceivers().add(record);
				}
				mail.setTo(null);
			}
			// 抄送
			if (mail.getCc() != null && mail.getCc().length() > 0) {
				String[] ccs = mail.getCc().split(",");
				MailRecord record = null;
				for (String cc : ccs) {
					record = new MailRecord();
					if (!Constants.IC_MAIL_MAILBOX_INNER.equals(
							mail.getMailboxId())) {
						record.setReceiveMail(cc);
					} else {
						record.setReceiveUserId(new Long(cc));
					}
					record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_CC);
					record.setFolderId(Constants.IC_MAIL_MAILFOLDER_INBOX);
					record.setReadFlag(Constants.IC_MAIL_MAILSTATUS_UNREAD);
					record.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
					record.setRemindFlag(Constants.IC_MAIL_UNREMIND_FLAG);
					record.setReplyFlag(Constants.IC_MAIL_UNREPLY_FLAG);
					mail.getReceivers().add(record);
				}
				mail.setCc(null);
			}
			// 密送
			if (mail.getBcc() != null && mail.getBcc().length() > 0) {
				String[] bccs = mail.getBcc().split(",");
				MailRecord record = null;
				for (String bcc : bccs) {
					record = new MailRecord();
					if (!Constants.IC_MAIL_MAILBOX_INNER.equals(
							mail.getMailboxId())) {
						record.setReceiveMail(bcc);
					} else {
						record.setReceiveUserId(new Long(bcc));
					}
					record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_BCC);
					record.setFolderId(Constants.IC_MAIL_MAILFOLDER_INBOX);
					record.setReadFlag(Constants.IC_MAIL_MAILSTATUS_UNREAD);
					record.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
					record.setRemindFlag(Constants.IC_MAIL_UNREMIND_FLAG);
					record.setReplyFlag(Constants.IC_MAIL_UNREPLY_FLAG);
					mail.getReceivers().add(record);
				}
				mail.setBcc(null);
			}
		}
		// 保存草稿时，如果fileids为空或空串，没有附件。收外部邮件时，attachs附件信息为空，没有附件
		if ((mail.getFileids() == null || mail.getFileids().length() == 0)
				&& (mail.getAttachs() == null || mail.getAttachs().size() == 0)) {
			mail.setIsfile(Constants.IC_MAIL_ISFILE_NO);
		} else {
			mail.setIsfile(Constants.IC_MAIL_ISFILE_YES);
		}
		// 外部邮件
		if (mail.getMailboxId().longValue() != Constants.IC_MAIL_MAILBOX_INNER) {

			if (mail.getMailTitle() == null
					|| mail.getMailTitle().length() == 0) {
				mail.setMailTitle("( 无主题  )");
			}
			if (mail.getMailContent() == null) {
				mail.setMailContent("");
			}
			if (mail.getSenderMail() != null) {
				mail.setSenderMail(mail.getSenderMail().replaceAll("\"", ""));
			}
		} else {// 内部邮件
			if (mail.getMailTitle() == null
					|| mail.getMailTitle().length() == 0) {
				mail.setMailTitle("( 无主题  )");
			}
			mail.setReceiveTime2(new Date());
		}

		if (!Constants.IC_MAIL_SMSALERT_YES.equals(mail.getSmsAlert())) {
			mail.setSmsAlert(Constants.IC_MAIL_SMSALERT_NO);
		}
		if (!Constants.IC_MAIL_SIGNATURE_YES.equals(mail.getSignature())) {
			mail.setSignature(Constants.IC_MAIL_SIGNATURE_NO);
		}
		if (!Constants.IC_MAIL_REPLYTEXTING_YES.equals(mail.getReplyTexting())) {
			mail.setReplyTexting(Constants.IC_MAIL_REPLYTEXTING_NO);
		}
		if (!Constants.IC_MAIL_PRESSING_YES.equals(mail.getPressing())) {
			mail.setPressing(Constants.IC_MAIL_PRESSING_NO);
		}
		if (!Constants.IC_MAIL_ENCRYPTION_YES.equals(mail.getEncryption())) {
			mail.setEncryption(Constants.IC_MAIL_ENCRYPTION_NO);
		}

		mail.setMailStatus(1);
		// 替换外部邮件回复时自带的样式<style></style>形式出现的内容
		String newContent = StringUtil.html2Text(mail.getMailContent());
		mail.setMailContent(newContent);

	}

	/**
	 * 方法描述：根据主表信息保存mailrecord表
	 * 
	 * @param mail
	 * @author Administrator
	 * @version 2014年6月30日下午5:34:44
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	private void preprocesseMailDraft(Mail mail) {

		mail.setSenderTime(new Date());
		if (mail.getReceivers() == null) {
			mail.setReceivers(new ArrayList<MailRecord>());
		}

		// 群发单显
		if (mail.getShowSingle() != null && mail.getShowSingle().length() > 0) {
			String[] ss = mail.getShowSingle().split(",");
			MailRecord record = null;
			for (String showSingle : ss) {
				record = new MailRecord();
				if (!Constants.IC_MAIL_MAILBOX_INNER
						.equals(mail.getMailboxId())) {
					record.setReceiveMail(showSingle);
				} else {
					record.setReceiveUserId(new Long(showSingle));
				}
				record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE);
				record.setFolderId(Constants.IC_MAIL_MAILFOLDERID_DRAFT);
				record.setReadFlag(Constants.IC_MAIL_MAILSTATUS_UNREAD);
				record.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
				mail.getReceivers().add(record);
			}
			mail.setShowSingle(null);
		} else {
			// 收件人
			if (mail.getTo() != null && mail.getTo().length() > 0) {
				String[] tos = mail.getTo().split(",");
				MailRecord record = null;
				for (String to : tos) {
					record = new MailRecord();
					if (!Constants.IC_MAIL_MAILBOX_INNER.equals(
							mail.getMailboxId())) {
						record.setReceiveMail(to);
					} else {
						record.setReceiveUserId(new Long(to));
					}
					record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_TO);
					record.setFolderId(Constants.IC_MAIL_MAILFOLDERID_DRAFT);
					record.setReadFlag(Constants.IC_MAIL_MAILSTATUS_UNREAD);
					record.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
					mail.getReceivers().add(record);
				}
				mail.setTo(null);
			}
			// 抄送
			if (mail.getCc() != null && mail.getCc().length() > 0) {
				String[] ccs = mail.getCc().split(",");
				MailRecord record = null;
				for (String cc : ccs) {
					record = new MailRecord();
					if (!Constants.IC_MAIL_MAILBOX_INNER.equals(
							mail.getMailboxId())) {
						record.setReceiveMail(cc);
					} else {
						record.setReceiveUserId(new Long(cc));
					}
					record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_CC);
					record.setFolderId(Constants.IC_MAIL_MAILFOLDERID_DRAFT);
					record.setReadFlag(Constants.IC_MAIL_MAILSTATUS_UNREAD);
					record.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
					record.setRemindFlag(Constants.IC_MAIL_UNREMIND_FLAG);
					record.setReplyFlag(Constants.IC_MAIL_UNREPLY_FLAG);
					mail.getReceivers().add(record);
				}
				mail.setCc(null);
			}
			// 密送
			if (mail.getBcc() != null && mail.getBcc().length() > 0) {
				String[] bccs = mail.getBcc().split(",");
				MailRecord record = null;
				for (String bcc : bccs) {
					record = new MailRecord();
					if (!Constants.IC_MAIL_MAILBOX_INNER.equals(
							mail.getMailboxId())) {
						record.setReceiveMail(bcc);
					} else {
						record.setReceiveUserId(new Long(bcc));
					}
					record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_BCC);
					record.setFolderId(Constants.IC_MAIL_MAILFOLDERID_DRAFT);
					record.setReadFlag(Constants.IC_MAIL_MAILSTATUS_UNREAD);
					record.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
					record.setRemindFlag(Constants.IC_MAIL_UNREMIND_FLAG);
					record.setReplyFlag(Constants.IC_MAIL_UNREPLY_FLAG);
					mail.getReceivers().add(record);
				}
				mail.setBcc(null);
			}
		}

		if ("0".equals(mail.getIsHaveAttach())||mail.getFileids() == null || mail.getFileids().length() == 0) {
			mail.setIsfile(Constants.IC_MAIL_ISFILE_NO);
		} else {
			mail.setIsfile(Constants.IC_MAIL_ISFILE_YES);
		}
		// 外部邮件
		if (mail.getMailboxId().longValue() != Constants.IC_MAIL_MAILBOX_INNER) {

			// if (mail.getMailTitle() == null
			// || mail.getMailTitle().length() == 0) {
			// mail.setMailTitle("(无标题)");
			// }
			if (mail.getMailContent() == null) {
				mail.setMailContent("");
			}
			if (mail.getSenderMail() != null) {
				mail.setSenderMail(mail.getSenderMail().replaceAll("\"", ""));
			}
		} else {// 内部邮件
			// if (mail.getMailTitle() == null
			// || mail.getMailTitle().length() == 0) {
			// mail.setMailTitle("(无标题)");
			// }
			mail.setReceiveTime2(new Date());
		}

		if (!Constants.IC_MAIL_SMSALERT_YES.equals(mail.getSmsAlert())) {
			mail.setSmsAlert(Constants.IC_MAIL_SMSALERT_NO);
		}
		if (!Constants.IC_MAIL_SIGNATURE_YES.equals(mail.getSignature())) {
			mail.setSignature(Constants.IC_MAIL_SIGNATURE_NO);
		}
		if (!Constants.IC_MAIL_REPLYTEXTING_YES.equals(mail.getReplyTexting())) {
			mail.setReplyTexting(Constants.IC_MAIL_REPLYTEXTING_NO);
		}
		if (!Constants.IC_MAIL_PRESSING_YES.equals(mail.getPressing())) {
			mail.setPressing(Constants.IC_MAIL_PRESSING_NO);
		}
		if (!Constants.IC_MAIL_ENCRYPTION_YES.equals(mail.getEncryption())) {
			mail.setEncryption(Constants.IC_MAIL_ENCRYPTION_NO);
		}

		mail.setMailStatus(1);
		// 替换外部邮件回复时自带的样式<style></style>形式出现的内容
		String newContent = StringUtil.html2Text(mail.getMailContent());
		mail.setMailContent(newContent);

	}

	/**
	 * 方法描述：查询邮件文件夹
	 * 
	 * @param mailFolder
	 * @return
	 * @author zhanglg
	 * @version 2014年5月20日下午7:40:25
	 * @throws CustomException
	 * @see
	 */
	@Override
	public List<MailFolder> queryAllMailFolder(MailFolder mailFolder)
			throws CustomException {

		return (List<MailFolder>) folderService.queryAll(new MailFolder());
	}

	/**
	 * 方法描述：查询指定邮件文件夹
	 * 
	 * @param m
	 * @return
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月23日下午3:01:54
	 * @see
	 */
	@Override
	public MailFolder getMailFolder(MailFolder m) throws CustomException {

		return folderService.get(m);
	}

	/**
	 * 方法描述：更新邮件文件夹
	 * 
	 * @param folder
	 * @return
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月23日下午3:02:03
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public Integer updateMailFolder(MailFolder folder) throws CustomException {

		return folderService.update(folder);
	}

	/**
	 * 方法描述：保存邮件文件夹
	 * 
	 * @param folder
	 * @return
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月23日下午3:02:08
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public Integer saveMailFolder(MailFolder folder) throws CustomException {

		return folderService.save(folder);
	}

	/**
	 * 方法描述：删除邮件文件夹
	 * 
	 * @param folder
	 * @return
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月23日下午3:02:12
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public Integer deleteMailFolder(MailFolder folder) throws CustomException {

		return folderService.delete(folder);
	}

	/**
	 * 方法描述：接收所有用户外部邮箱的方法
	 * 
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月23日下午2:59:53
	 * @see
	 */
	@Override
	public void autoReceiveAllExtMail() {
		Mailbox box = new Mailbox();
		box.setDeleteFlag(0);
		List<Mailbox> boxList;
		try {
			boxList = mailboxService.queryAll(new Mailbox());

			for (Mailbox mailbox : boxList) {
				if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mailbox.getId())) {
					this.receiveAllExtMail(mailbox);
				}
			}
		} catch (CustomException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 方法描述：取得用户指定邮箱配置
	 * 
	 * @param box
	 * @return
	 * @author zhanglg
	 * @version 2014年5月26日下午3:55:26
	 * @throws CustomException
	 * @see
	 */
	@Override
	public Mailbox getMailbox(Mailbox box) throws CustomException {
		if (box.getId() == null || box.getId() > 1) {
			box.setCreateUser(SystemSecurityUtils.getUser().getId());
		} 
		return mailboxService.get(box);
	}

	/**
	 * 方法描述：查询内部邮箱签名
	 * 
	 * @param Mailbox
	 * @return
	 * @author weny
	 * @version 2014年9月24日
	 * @throws CustomException
	 * @see
	 */
	@Override
	public Mailbox querySign(Mailbox box) throws CustomException {
		Mailbox mailbox = new Mailbox();
		if(box.getId() == null || box.getId().intValue() == 1){
			mailbox.setAddress(String.valueOf(SystemSecurityUtils.getUser().getId()));
		}
		return mailboxDao.querySign(mailbox);
	}

	/**
	 * 方法描述：下载外网邮箱附件方法
	 * 
	 * @param id
	 *            邮件id
	 * @param fileName
	 *            文件名
	 * @param response
	 * @param request
	 * @author zhanglg
	 * @version 2014年5月29日上午11:11:29
	 * @throws IcException
	 * @see
	 */
	@Override
	public void downloadExtAttach(Long id, String fileName,
			HttpServletResponse response, HttpServletRequest request)
			throws IcException {
		String filename = "";
		filename = CharConventUtils.encodingFileName(fileName);
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=\""
				+ filename + "\"");
		try {

			Mail mail = new Mail();
			mail.setId(id);
			mail = this.get(mail);

			Mailbox mailbox = new Mailbox();
			mailbox.setId(mail.getMailboxId());
			mailbox = this.getMailbox(mailbox);

			receiver.downloadMailAttach(mailbox, mail.getMessageId(), fileName,
					response);

		} catch (FileNotFoundException e) {
			log.error(MessageUtils.getMessage("JC_OA_IC_036"));
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_036"), e);
		} catch (IOException e) {
			log.error(MessageUtils.getMessage("JC_OA_IC_037") + e);
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_037"), e);
		} catch (CustomException e) {
			log.error(MessageUtils.getMessage("JC_OA_IC_038"));
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_038"), e);
		} catch (MessagingException e) {
			log.error(MessageUtils.getMessage("JC_OA_IC_039"));
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_039"), e);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_029"), e);
		}
	}

	/**
	 * 方法描述：查询邮件未保存状态下的附件信息
	 * 
	 * @param mail
	 * @return
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年6月4日上午11:02:56
	 * @see
	 */
	@Override
	public List<Attach> getUnsaveAttachs(Mail mail) throws CustomException {
		List<Attach> attachs = new ArrayList<Attach>();
		Attach attach = new Attach();
		Attach attachNew = null;
		String fileIds[] = mail.getFileids().split(",");
		// 保存附件
		if (fileIds != null && fileIds.length > 0 && !"".equals(fileIds[0])) {
			for (int i = 0; i < fileIds.length; i++) {
				attach.setId(new Long(fileIds[i]));
				attachNew = attachService.get(attach);
				if(attachNew != null ){
					attachs.add(attachNew);
				}
			}
		}

		return attachs;
	}

	/**
	 * 方法描述：查看往来邮件
	 * 
	 * @param mail
	 * @return
	 * @throws IcException
	 * @author 徐伟平
	 * @version 2014年6月13日
	 */
	@Override
	public Map<String, Object> viewToAndFrom(Mail mail) throws IcException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// 查询往来邮件list
			List<Mail> mailList = null;
			mailList = mailDao.viewToAndFrom(mail);
			Mail maildelete = new Mail();
			maildelete.setSenderUserId(mail.getCreateUser());
			List<Mail> list =mailDao.mailDelete(maildelete);
			for(int i=list.size()-1;i>=0;i--){
				Mail mList = list.get(i);
				for(int j=mailList.size()-1;j>=0;j--){
					Mail mdelete = mailList.get(j);
					if(mList.getId().longValue()==mdelete.getId().longValue()){
						mailList.remove(j);
					}
				}
			}
			// 根据businessids 查询往来邮件的附件
			String businessIds = "";
			for (Mail m : mailList) {
				businessIds += m.getId() + ",";
			}
			Attach attach = new Attach();
			attach.setBusinessIdArray(businessIds.split(","));
			// attach.setBusinessSource("0");
			attach.setBusinessTable("TTY_IC_MAIL");
			List<Attach> attachList = attachService
					.queryAttachByBusinessIds(attach);
			// 将查询到是数据放入map中带入js中处理
			resultMap.put("mailList", mailList);
			resultMap.put("mailCount", mailList.size());
			resultMap.put("attachList", attachList);
			resultMap.put("attachCount", attachList.size());
			return resultMap;
		} catch (CustomException e) {
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_OA_IC_040"));
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 方法描述：查询未读邮件
	 * 
	 * @param mail
	 * @param page
	 * @return
	 * @author 宋海涛
	 * @version 2014年6月26日上午8:43:53
	 * @see
	 */
	@Override
	public PageManager queryUnReadMail(Mail mail, PageManager page) {
		return mailDao.queryByPage(this.queryPreProcess(mail), page,
				SQL_QUERYUNREADMAILCOUNT, SQL_QUERYUNREADMAIL);
	}

	/**
	 * 方法描述：移动端查询未读邮件
	 * 
	 * @param mail
	 * @param page
	 * @return
	 * @author 宋海涛
	 * @version 2014年6月26日上午8:43:53
	 * @see
	 */
	@Override
	public PageManager queryUnReadMail4M(Mail mail, PageManager page) {
		return mailDao.queryByPage(mail, page,
				SQL_QUERYUNREADMAILCOUNT, SQL_QUERYUNREADMAIL);
	}
	/**
	 * 方法描述：删除未读邮件
	 * 
	 * @param mail
	 * @param mrids
	 * @param fromFolder
	 * @param toFolder
	 * @throws Exception
	 * @author 宋海涛
	 * @version 2014年6月26日上午8:43:33
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void moveToUnReadMail(Mail mail, String mrids, Long fromFolder,
			Long toFolder) throws Exception {

		if (mail.getPrimaryKeys() == null) {
			mail.setPrimaryKeys(new String[] { mail.getId().toString() });
		}
		String[] primaryKeys = mail.getPrimaryKeys();
		String[] mridsArr = mrids.split(",");
		List<MailRecord> list = null;
		MailRecord receiver = null;
		for (int i = 0; i < primaryKeys.length; i++) {
			mail = new Mail();
			mail.setId(new Long(primaryKeys[i]));
			list = new ArrayList<MailRecord>();
			receiver = new MailRecord();
			receiver.setFolderId(fromFolder);
			list.add(receiver);
			mail.setReceivers(list);

			mail = mailDao.get(mail);
			Long mrid = new Long(mridsArr[i]);
			if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {

				for (MailRecord mr : mail.getReceivers()) {
					if (mr.getId().equals(mrid)) {
						// if (mr.getReceiveUserId().longValue() ==
						// SystemSecurityUtils
						// .getUser().getId()) {
						mr.setFolderId(toFolder);
						mr.setId(new Long(mridsArr[i]));
						mailRecordService.update(mr);
						break;
						// }
					}
				}
			} else {
				Mailbox box = new Mailbox();
				box.setId(mail.getMailboxId());
				box = mailboxService.get(box);
				// 设置邮件为已读状态
				for (MailRecord mr : mail.getReceivers()) {
					// if (mr.getReceiveMail().equals(box.getAddress())) {
					if (mr.getId().equals(mrid)) {
						mr.setFolderId(toFolder);
						mr.setId(new Long(mridsArr[i]));
						mailRecordService.update(mr);
						break;
					}
				}
			}
		}
	}

	/**
	 * 方法描述： 保存草稿
	 * 
	 * @param mail
	 * @return
	 * @throws IcException
	 * @author 曹杨
	 * @version 2014年6月30日下午5:31:36
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer saveDraft(Mail mail) throws CustomException {
		Integer counts = null;

		this.preprocesseMailDraft(mail);

		if (mail.getId() == null) {
			counts = super.save(mail);
		} else {
			counts = super.update(mail);
			MailRecord record = new MailRecord();
			record.setMailId(mail.getId());
			mailRecordService.deleteByMailId(record);
		}

		for (MailRecord record : mail.getReceivers()) {
			record.setMailId(mail.getId());
			mailRecordService.save(record);
		}

		// 删除页面中上传中删除的附件
		if (!StringUtil.isEmpty(mail.getDelattachIds())) {
			uploadService.deleteFileByIds(mail.getDelattachIds());
		}

		// if (mail.getFileids() != null) {
		// String fileIds[] = mail.getFileids().split(",");
		// // 保存附件
		// if (fileIds != null && fileIds.length > 0 && !"".equals(fileIds[0]))
		// {
		// AttachBusiness attachBusiness = new AttachBusiness();
		// for (int i = 0; i < fileIds.length; i++) {
		// attachBusiness.setAttachId(new Long(fileIds[i]));
		// attachBusiness.setBusinessId(mail.getId());
		// attachBusiness.setBusinessTable(TABLE_NAME);
		// attachBusinessService.save(attachBusiness);
		// }
		// }
		// }
		String fileIds[] = mail.getFileids().split(",");
		// 保存附件
		AttachBusiness attachBusines = null;
		AttachBusiness delAttachBusiness = null;
		AttachBusiness attachBusiness = null;
		if (fileIds != null && fileIds.length > 0 && !"".equals(fileIds[0])) {
			// 查询所需修改的图片信息
			attachBusines = new AttachBusiness();
			attachBusines.setBusinessId(mail.getId());
			attachBusines.setBusinessTable(TABLE_NAME);
			List<AttachBusiness> list = attachBusinessService
					.queryAll(attachBusines);
			// 删除所需修改的图片信息及图片文件
			delAttachBusiness = new AttachBusiness();
			String[] keys = new String[list.size()];
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					keys[i] = list.get(i).getId().toString();
				}
				delAttachBusiness.setPrimaryKeys(keys);
				attachBusinessService.delete(delAttachBusiness, false);
			}
			// 保存新的图片信息
			for (int i = 0; i < fileIds.length; i++) {
				attachBusiness = new AttachBusiness();
				attachBusiness.setAttachId(new Long(fileIds[i]));
				attachBusiness.setBusinessId(mail.getId());
				attachBusiness.setBusinessTable(TABLE_NAME);
				attachBusinessService.save(attachBusiness);
			}
		}
		return counts;
	}

	/**
	 * 方法描述：分页查询星标邮件方法
	 * 
	 * @param mail
	 * @param page
	 * @return
	 * @author 宋海涛
	 * @version 2014年6月30日上午11:24:13
	 * @see
	 */
	@Override
	public PageManager queryStarMail(Mail mail, PageManager page) {
		return mailDao.queryByPage(this.queryPreProcess(mail), page,
				SQL_QUERYSTARAMAILCOUNT, SQL_QUERYSTARMAIL);
	}

	/**
	 * 方法描述：星标寻呼查询
	 * 
	 * @param mail
	 * @param page
	 * @return
	 * @author 宋海涛
	 * @version 2014年6月30日下午3:35:02
	 * @see
	 */
	@Override
	public PageManager searchStar(Mail mail, PageManager page) {

		return mailDao.searchStar(this.queryPreProcess(mail), page);
	}

	/**
	 * 方法描述：回复提醒查询
	 * 
	 * @param mail
	 * @return
	 * @author 曹杨
	 * @version 2014年7月1日下午4:46:12
	 * @see
	 */
	@Override
	public List<Mail> getReplyTexting() {
		Mail mail = new Mail();
		mail.setDeleteFlag(0);
		return mailDao.getReplyTexting(mail);
	}

	/**
	 * 方法描述：短信验证
	 * 
	 * @param userIds
	 * @return
	 * @throws IcException
	 * @author 宋海涛
	 * @version 2014年7月17日上午10:03:03
	 * @see
	 */
	@Override
	public Map<String, Object> validRemind(String userIds) throws IcException {
		try {
			return interactFacadeService.sendSmsValidate(userIds);
		} catch (IcException ice) {
			IcException ic = new IcException(ice);
			ic.setLogMsg(ice.getLogMsg());
			throw ic;
		}
	}

	/**
	 * 方法描述：邮件追回
	 * 
	 * @param mail
	 * @return
	 * @throws IcException
	 * @author 曹杨
	 * @version 2014年7月22日下午1:30:42
	 * @see
	 */

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer mailRecover(Mail mail) throws CustomException {
		Integer result = 0;
		Date now = new Date();
		boolean canRecover = false;
		MailRecord record = new MailRecord();
		record.setMailId(mail.getId());
		List<MailRecord> mailRecord = mailRecordService.queryAll(record);
		for (MailRecord mr : mailRecord) {
			if (mr.getReadDate()==null
					&& mr.getReceiveType().intValue() != Constants.IC_MAIL_RECEIVETYPE_INNERSENDER) {
				canRecover = true;
				break;
			}
		}
		if(canRecover){
			Mail newMail = new Mail();
			boolean isInsertMail = false;
			for (MailRecord mr : mailRecord) {
				
				Long mrid =  mr.getId();
				Long mrMailId = mr.getMailId();
				Date mrReadDate = mr.getReadDate();
				if(!isInsertMail){
					newMail = this.get(mail);
					newMail.setId(null);
					mailDao.save(newMail);
					List<Attach> attachList = newMail.getAttachs();
					AttachBusiness attachBusiness = null;
					for (Attach attach : attachList) {
						attach.setId(null);
						attachService.save(attach);
						attachBusiness = new AttachBusiness();
						attachBusiness.setAttachId(attach.getId());
						attachBusiness.setBusinessId(newMail.getId());
						attachBusiness.setBusinessTable(TABLE_NAME);
						attachBusinessService.save(attachBusiness);
					}
					isInsertMail = true; 
				}
				if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(mr
						.getReceiveType())) {
					mr.setReadFlag(Constants.IC_MAIL_MAILSTATUS_READED);
					mr.setFolderId(Constants.IC_MAIL_MAILFOLDER_DRAFT);
				}else{
					mr.setReadFlag(Constants.IC_MAIL_MAILSTATUS_UNREAD);
					mr.setFolderId(0L);
				}
				mr.setReadDate(null);
				mr.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
				mr.setId(null);
				mr.setMailId(newMail.getId());
				propertyService.fillProperties(mr, false);
				result = mailRecordService.save(mr);
				
				if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(mr
						.getReceiveType())||mrReadDate==null) {
					mr.setFolderId(-1L);
					mr.setId(mrid);
					mr.setMailId(mrMailId);
					mr.setRecoverFlag(Constants.IC_MAIL_RECOVER_FLAG);
					mr.setRecoverDate(now);
					propertyService.fillProperties(mr, false);
					result = mailRecordService.update(mr);
				} 
			}
		}else{
			return 3;
		}
//		if(canRecover){
//			Mail newMail = new Mail();
//			boolean isInsertMail = false;
//			MailRecord senderMr = new MailRecord();
//			for (MailRecord mr : mailRecord) {
//				if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(mr
//						.getReceiveType())) {
//					mr.setFolderId(Constants.IC_MAIL_MAILFOLDER_DRAFT);
//					senderMr = mr;
//				//如果该邮件是已读邮件则重新储存一条子表记录
//				} else {
//					Long mrid =  mr.getId();
//					Long mrMailId = mr.getMailId();
//					if(mr.getReadDate()!=null){
//						if(!isInsertMail){
//							newMail = this.get(mail);
//							newMail.setId(null);
//							mailDao.save(newMail);
//							//MailRecord newMailRecord = mr;
//							
//							//List<Attach> attachList = uploadService.copyAttachList(request, mail.getAttachs());
//							List<Attach> attachList = newMail.getAttachs();
//							AttachBusiness attachBusiness = null;
//							for (Attach attach : attachList) {
//								attach.setId(null);
//								attachService.save(attach);
//								attachBusiness = new AttachBusiness();
//								attachBusiness.setAttachId(attach.getId());
//								attachBusiness.setBusinessId(newMail.getId());
//								attachBusiness.setBusinessTable(TABLE_NAME);
//								attachBusinessService.save(attachBusiness);
//							}
//							senderMr.setId(null);
//							senderMr.setMailId(newMail.getId());
//							//folderId为10是为了插入发件人信息，往来邮件查看详细使用，不能在列表中显示
//							senderMr.setFolderId(10L);
//							mailRecordService.save(senderMr);
//							isInsertMail = true;
//						}
//						mr.setId(null);
//						mr.setMailId(newMail.getId());
//						mailRecordService.save(mr);
//				    }
//					mr.setId(mrid);
//					mr.setMailId(mrMailId);
//					mr.setFolderId(Constants.IC_MAIL_MAILFOLDERID_DRAFT);
//				}
//				mr.setRecoverFlag(Constants.IC_MAIL_RECOVER_FLAG);
//				mr.setRecoverDate(now);
//				propertyService.fillProperties(mr, false);
//				result = mailRecordService.update(mr);
//			}
//		}else{
//			return 3;
//		}
		return result;
	}

	@Override
	public Mail getMailByAttach(String id) throws IcException {
		AttachBusiness attach = new AttachBusiness();
		attach.setAttachId(new Long(id));
		try {
			attach = attachBusinessService.get(attach);
		} catch (CustomException e) {
			IcException ic = new IcException(e);
			ic.setLogMsg(e.getLogMsg());
			throw ic;
		}
		Mail mail = new Mail();
		mail.setId(attach.getBusinessId());
		mail = mailDao.get(mail);
		return mail;
	}

	@Override
	public List<Mail> queryAllUnRead(Mail mailParam) {
		return mailDao.queryAll(this.queryPreProcess(mailParam));
	}

	@Override
	public List<Mail> queryAllDetail(Mail mailParam) {
		// TODO Auto-generated method stub
		return mailDao.queryAllDetail(this.queryPreProcess(mailParam));
	}
}