package com.jc.android.oa.ic.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.android.oa.common.ClientMessageUtil;
import com.jc.android.oa.common.service.IUsermapService;
import com.jc.android.oa.ic.domain.Attach4M;
import com.jc.android.oa.ic.domain.Mail4M;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.ic.domain.Mail;
import com.jc.oa.ic.domain.MailRecord;
import com.jc.oa.ic.domain.Mailbox;
import com.jc.oa.ic.domain.validator.MailValidator;
import com.jc.oa.ic.service.IContactsService;
import com.jc.oa.ic.service.IMailRecordService;
import com.jc.oa.ic.service.IMailService;
import com.jc.system.CustomException;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.content.domain.Attach;
import com.jc.system.security.SystemSecurityUtils;

/**
 * @title 互动交流
 * @author
 * @version 2014-04-17
 */

@Controller
@RequestMapping(value = "/android/ic/mail")
public class Mail4MController extends BaseController {

	@Autowired
	private IMailService mailService;

	@Autowired
	private IMailRecordService mailRecordService;
	@Autowired
	private IContactsService contactsService;

	@Autowired
	private IUsermapService usermapService;

	private ClientMessageUtil clientMessageUtil = new ClientMessageUtil();

	@org.springframework.web.bind.annotation.InitBinder("mail")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new MailValidator());
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");

	public Mail4MController() {
	}

	/**
	 * 方法描述：移动端分页查询未读邮件方法
	 * 
	 * @param userId
	 * @param curPage
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 曹杨
	 * @version 2014年9月11日上午8:36:25
	 * @see
	 */
	@RequestMapping(value = "unReadList4M.action")
	@ResponseBody
	// @ActionLog(operateModelNm = "移动端查询未读邮件", operateFuncNm = "unReadList4M",
	// operateDescribe = "移动端查询未读邮件")
	public Map<String, Object> unReadList4M(String userId, String curPage,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String senderTime = "";
		String content = "";
		// 设置发布人为当前用户
		if (StringUtils.isNotEmpty(curPage) && userId != null) {
			// 查询内部邮箱
			Mail mail = new Mail();
			List<Mailbox> mailboxlist = new ArrayList<Mailbox>();
			Mailbox mbox = new Mailbox();
			mbox.setId(1L);
			mailboxlist.add(mbox);
			mail.setMailbox(mailboxlist);
			// 查询内部邮箱需要设置收件人userId
			mail.setReceivers(new ArrayList<MailRecord>());
			MailRecord receive;
			receive = new MailRecord();
			receive.setReceiveUserId(Long.parseLong(userId));
			mail.getReceivers().add(receive);

			if (mail.getSearchReceiveTimeEnd() != null) {
				mail.setSearchReceiveTimeEnd(DateUtils.fillTime(mail
						.getSearchReceiveTimeEnd()));
			}
			if ("".equals(mail.getMailTitle())) {
				mail.setMailTitle(null);
			}
			if ("".equals(mail.getSenderMail())) {
				mail.setSenderMail(null);
			}
			// 取得收件人
			List<MailRecord> receivers = mail.getReceivers();
			// 设置子表id
			StringBuffer mrid = new StringBuffer();
			for (MailRecord mailRecord : receivers) {
				mrid.append(mailRecord.getId()).append(",");
			}
			if (mrid.length() > 0 && mrid.charAt(mrid.length() - 1) == ',') {
				mrid.deleteCharAt(mrid.length() - 1);
			}
			mail.setMrid(mrid.toString());

			if (Constants.IC_MAIL_MAILFOLDER_DRAFT.equals((receivers.get(0))
					.getFolderId())
					|| Constants.IC_MAIL_MAILFOLDER_SENDED.equals((receivers
							.get(0)).getFolderId())) {
				mail.addOrderByFieldDesc("t.SENDER_TIME");
			} else {
				mail.addOrderByFieldDesc("receiveTime2");
			}
			if (mail.getMailboxId() == null) {
				if (Constants.IC_MAIL_MAILFOLDER_DRAFT
						.equals((receivers.get(0)).getFolderId())
						|| Constants.IC_MAIL_MAILFOLDER_SENDED
								.equals((receivers.get(0)).getFolderId())) {
					mail.addOrderByFieldDesc("t.SENDER_TIME");
				} else {
					mail.addOrderByFieldDesc("receiveTime2");
				}
			}
			PageManager pm = new PageManager();
			pm.setPage(Integer.parseInt(curPage));
			PageManager page_ = mailService.queryUnReadMail(mail, pm);
			List<Mail> list = (List<Mail>) page_.getData();
			List<Mail4M> alist = new ArrayList<Mail4M>();
			if (list != null) {
				for (Mail i : list) {
					Mail4M vo = new Mail4M();
					BeanUtils.copyProperties(i, vo);// (源，目标)
					content = StringUtil.filterHtml(i.getMailContent());
					content = StringUtil.replaceNBSP(content);
					content = StringUtil.getTitle4M(content);
					senderTime = dateFormat.format(i.getSenderTime());
					vo.setSenderTime(senderTime);
					vo.setMailContent(content);
					alist.add(vo);
				}
				if (alist != null) {
					// 成功状态位和数据
					resultMap.put("state", "success");
					resultMap.put("data", alist);

				}
			} else {
				// 失败状态位和错误信息
				resultMap.put("state", "failure");
				resultMap.put("errormsg",
						MessageUtils.getMessage("JC_OA_COMMON_016"));
			}
		} else {
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",
					MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}

	/**
	 * 方法描述：移动端查询未读邮件数量
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 曹杨
	 * @version 2014年9月11日下午8:46:41
	 * @see
	 */
	@RequestMapping(value = "unReadCount4M.action")
	@ResponseBody
	// @ActionLog(operateModelNm = "移动端查询未读邮件数量", operateFuncNm =
	// "unReadCount4M", operateDescribe = "移动端查询未读邮件数量")
	public Map<String, Object> unReadCount4M(String userId,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 设置发布人为当前用户
		if (userId != null) {
			// 查询内部邮箱
			Mail mail = new Mail();
			List<Mailbox> mailboxlist = new ArrayList<Mailbox>();
			Mailbox mbox = new Mailbox();
			mbox.setId(1L);
			mailboxlist.add(mbox);
			mail.setMailbox(mailboxlist);
			// 查询内部邮箱需要设置收件人userId
			mail.setReceivers(new ArrayList<MailRecord>());
			MailRecord receive;
			receive = new MailRecord();
			receive.setReceiveUserId(Long.parseLong(userId));
			mail.getReceivers().add(receive);

			if (mail.getSearchReceiveTimeEnd() != null) {
				mail.setSearchReceiveTimeEnd(DateUtils.fillTime(mail
						.getSearchReceiveTimeEnd()));
			}
			if ("".equals(mail.getMailTitle())) {
				mail.setMailTitle(null);
			}
			if ("".equals(mail.getSenderMail())) {
				mail.setSenderMail(null);
			}
			// 取得收件人
			List<MailRecord> receivers = mail.getReceivers();
			if (Constants.IC_MAIL_MAILFOLDER_DRAFT.equals((receivers.get(0))
					.getFolderId())
					|| Constants.IC_MAIL_MAILFOLDER_SENDED.equals((receivers
							.get(0)).getFolderId())) {
				mail.addOrderByFieldDesc("t.SENDER_TIME");
			} else {
				mail.addOrderByFieldDesc("receiveTime2");
			}
			if (mail.getMailboxId() == null) {
				if (Constants.IC_MAIL_MAILFOLDER_DRAFT
						.equals((receivers.get(0)).getFolderId())
						|| Constants.IC_MAIL_MAILFOLDER_SENDED
								.equals((receivers.get(0)).getFolderId())) {
					mail.addOrderByFieldDesc("t.SENDER_TIME");
				} else {
					mail.addOrderByFieldDesc("receiveTime2");
				}
			}
			PageManager pm = new PageManager();
			PageManager page_ = mailService.queryUnReadMail4M(mail, pm);
			List<Mail> list = (List<Mail>) page_.getData();
			if (list != null) {
				// 成功状态位和数据
				resultMap.put("state", "success");
				resultMap.put("data", page_.getTotalCount());
			} else {
				// 失败状态位和错误信息
				resultMap.put("state", "failure");
				resultMap.put("errormsg",
						MessageUtils.getMessage("JC_OA_COMMON_016"));
			}
		} else {
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",
					MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}

	/**
	 * 方法描述：移动端阅读邮件
	 * 
	 * @param mailId
	 * @param mrid
	 * @param password
	 * @param folderId
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 曹杨
	 * @version 2014年9月11日下午10:15:46
	 * @see
	 */
	@RequestMapping(value = "readMail4M.action")
	// @ActionLog(operateModelNm = "移动端阅读邮件", operateFuncNm = "readMail4M",
	// operateDescribe = "移动端阅读邮件")
	@ResponseBody
	public Map<String, Object> readMail4M(String mailId, String mrid,
			String password, String folderId, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String senderTime = "";
		String content = "";
		Mail mail = new Mail();
		mail.setId(Long.parseLong(mailId));
		mail = mailService.get(mail);
		// 附件列表
		List<Attach> attachListOA = mail.getAttachs();
		// 缩减附件信息--孙一石2014-09-17
		List attachList = new ArrayList();
		for (int i = 0; i < attachListOA.size(); i++) {
			Attach attach = attachListOA.get(i);
			Attach4M attach4m = new Attach4M();
			// web端bean转化为移动端bean，传输数据量更小
			BeanUtils.copyProperties(attach, attach4m);// (源，目标)
			attachList.add(attach4m);
		}

		mailService.setReadStatus(mail, mrid,
				Constants.IC_MAIL_MAILSTATUS_READED.toString());
		Mailbox mailbox = new Mailbox();
		mailbox.setId(mail.getMailboxId());
		mailbox = mailService.getMailbox(mailbox);
		Long userId = SystemSecurityUtils.getUser().getId();
		// 新写的收件人、抄送人显示逻辑，把收件人、抄送人、群发单显人，分别放在Mail的to,cc,showSingle字段
		// Updated By 张立刚

		List<MailRecord> receivers = mail.getReceivers();
		boolean isSender = false;
		for (int i = 0; i < receivers.size(); i++) {
			MailRecord receiver = receivers.get(i);
			Integer type = receiver.getReceiveType();
			// 如果发件人时当前人，设置isSender
			if (type.equals(Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)
					&& (userId.equals(receiver.getReceiveUserId()) || (mailbox != null && mailbox
							.getAddress().equals(receiver.getReceiveMail())))) {
				isSender = true;
				break;
			}
		}
		StringBuffer to = new StringBuffer();
		StringBuffer cc = new StringBuffer();
		StringBuffer ss = new StringBuffer();
		StringBuffer bcc = new StringBuffer();
		for (int i = 0; i < receivers.size(); i++) {
			MailRecord record = receivers.get(i);
			if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
				if (mailId.equals(record.getId().toString())) {
					mail.setReceiver(record);
				}
				// 可以看到群发单显：群发单显收件人可以看到自己、发件人可以看到所有，
				if ((Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
						.getReceiveType()) && record.getReceiveUserId().equals(
						userId))
						|| isSender
						&& (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE
								.equals(record.getReceiveType()))) {
					ss.append(record.getReceiveUserName()).append(",");
				} else if (Constants.IC_MAIL_RECEIVETYPE_TO.equals(record
						.getReceiveType())) {
					to.append(record.getReceiveUserName()).append(",");
				} else if (Constants.IC_MAIL_RECEIVETYPE_CC.equals(record
						.getReceiveType())) {
					cc.append(record.getReceiveUserName()).append(",");
				}
				// 可以看到密送：收件人可以看到自己、发件人可以看到所有
				else if ((Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
						.getReceiveType()) && record.getReceiveUserId().equals(
						userId))
						|| isSender
						&& (Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
								.getReceiveType()))) {
					bcc.append(record.getReceiveUserName()).append(",");
				}
			} else {
				if (mailId.equals(record.getId().toString())) {
					mail.setReceiver(record);
				}
				// 可以看到群发单显：群发单显收件人可以看到自己、发件人可以看到所有，
				if ((Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
						.getReceiveType()) && record.getReceiveMail().equals(
						mailbox.getAddress()))
						|| isSender
						&& Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE
								.equals(record.getReceiveType())) {
					ss.append(record.getReceiveMail()).append(",");
				} else if (Constants.IC_MAIL_RECEIVETYPE_TO.equals(record
						.getReceiveType())) {
					to.append(record.getReceiveMail()).append(",");
				} else if (Constants.IC_MAIL_RECEIVETYPE_CC.equals(record
						.getReceiveType())) {
					cc.append(record.getReceiveMail()).append(",");
				}
				// 可以看到密送：收件人可以看到自己、发件人可以看到所有
				else if ((Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
						.getReceiveType()) && mailbox.getAddress().equals(
						record.getReceiveMail()))
						|| isSender
						&& Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
								.getReceiveType())) {
					bcc.append(record.getReceiveMail()).append(",");
				}
			}
		}

		if (to.length() > 0 && to.charAt(to.length() - 1) == ',') {
			to.deleteCharAt(to.length() - 1);
		}
		if (cc.length() > 0 && cc.charAt(cc.length() - 1) == ',') {
			cc.deleteCharAt(cc.length() - 1);
		}
		if (ss.length() > 0 && ss.charAt(ss.length() - 1) == ',') {
			ss.deleteCharAt(ss.length() - 1);
		}
		if (bcc.length() > 0 && bcc.charAt(bcc.length() - 1) == ',') {
			bcc.deleteCharAt(bcc.length() - 1);
		}
		mail.setTo(to.toString());
		mail.setCc(cc.toString());
		mail.setShowSingle(ss.toString());
		mail.setBcc(bcc.toString());
		if (Constants.IC_MAIL_ENCRYPTION_YES.equals(mail.getEncryption())) {
			if (password == null) {
				mail.setWrongPassword(true);
				mail.setEncryptionPass(null);
			} else if (!mail.getEncryptionPass().equals(password)) {
				mail.setWrongPassword(true);
			} else {
				mail.setWrongPassword(false);
			}
		}
		List<Mail> list = new ArrayList<Mail>();
		list.add(mail);
		List<Mail4M> alist = new ArrayList<Mail4M>();
		for (Mail i : list) {
			Mail4M vo = new Mail4M();
			BeanUtils.copyProperties(i, vo);// (源，目标)
			// content = StringUtil.filterHtml(i.getMailContent());
			content = i.getMailContent();
			senderTime = dateFormat.format(i.getSenderTime());
			vo.setSenderTime(senderTime);
			vo.setMailContent(content);
			alist.add(vo);
		}
		if (alist != null) {
			// 成功状态位和数据
			resultMap.put("state", "success");
			resultMap.put("data", alist.get(0));
			resultMap.put("attachList", attachList);
		}
		return resultMap;
	}

	/**
	 * 方法描述：移动端发送邮件
	 * 
	 * @param userId
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param request
	 * @return
	 * @throws CustomException
	 * @author 曹杨
	 * @version 2014年9月11日下午1:19:12
	 * @see
	 */
	@RequestMapping(value = "sendMail4M.action")
	@ResponseBody
	// @ActionLog(operateModelNm = "移动端发送邮件", operateFuncNm = "sendMail4M",
	// operateDescribe = "移动端发送邮件")
	public Map<String, Object> sendMail4M(String userId, String to, String cc,
			String bcc, String title, String content, String attach,
			HttpServletRequest request) throws CustomException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Integer result = 0;
		final Mail mail = new Mail();
		List<Attach> attachs = new ArrayList<Attach>();
		Attach ach = new Attach();
		if (!StringUtil.isEmpty(attach)) {
			ach.setBusinessIdArray(attach.split(","));
			attachs.add(ach);
			mail.setAttachs(attachs);
			mail.setFileids(attach);
		}
		mail.setMailboxId(1L);
		mail.setTo(to);
		mail.setCc(cc);
		mail.setBcc(bcc);
		mail.setMailTitle(title);
		mail.setMailContent(content);
		mail.setSenderUserId(Long.parseLong(userId));

		result = mailService.sendMail(mail, request);
		if (result > 0) {
			// 成功状态
			resultMap.put("state", "success");
		} else {
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",
					MessageUtils.getMessage("JC_OA_COMMON_016"));
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				// 发送手机端通知--孙一石
				try {
					usermapService.pushMessage(mail.getReceivers(), "您有一封新邮件",
							mail.getMailTitle(),
							"com.jc.oa.mobile.ic.activity.MailInfoActivity?mail_id="
									+ mail.getId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();

		return resultMap;
	}

	/**
	 * 方法描述：移动端查询邮件列表
	 * 
	 * @param userId
	 * @param curPage
	 * @param folderId
	 *            1-已发送 3-发件箱
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 曹杨
	 * @version 2014年9月11日下午1:50:08
	 * @see
	 */
	@RequestMapping(value = "mailList4M.action")
	@ResponseBody
	// @ActionLog(operateModelNm = "移动端查询邮件列表", operateFuncNm = "mailList4M",
	// operateDescribe = "移动端查询邮件列表")
	public Map<String, Object> mailList4M(String userId, String curPage,
			String folderId, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String senderTime = "";
		String content = "";
		// 设置发布人为当前用户
		if (StringUtils.isNotEmpty(curPage) && userId != null) {
			// 查询内部邮箱
			Mail mail = new Mail();
			List<Mailbox> mailboxlist = new ArrayList<Mailbox>();
			Mailbox mbox = new Mailbox();
			mbox.setId(1L);
			mailboxlist.add(mbox);
			mail.setMailbox(mailboxlist);
			// 查询内部邮箱需要设置收件人userId
			mail.setReceivers(new ArrayList<MailRecord>());
			MailRecord receive;
			receive = new MailRecord();
			receive.setFolderId(Long.parseLong(folderId));
			receive.setReceiveUserId(Long.parseLong(userId));
			mail.getReceivers().add(receive);

			if (mail.getSearchReceiveTimeEnd() != null) {
				mail.setSearchReceiveTimeEnd(DateUtils.fillTime(mail
						.getSearchReceiveTimeEnd()));
			}
			if ("".equals(mail.getMailTitle())) {
				mail.setMailTitle(null);
			}
			if ("".equals(mail.getSenderMail())) {
				mail.setSenderMail(null);
			}
			// 取得收件人
			List<MailRecord> receivers = mail.getReceivers();
			if (Constants.IC_MAIL_MAILFOLDER_DRAFT.equals((receivers.get(0))
					.getFolderId())
					|| Constants.IC_MAIL_MAILFOLDER_SENDED.equals((receivers
							.get(0)).getFolderId())) {
				mail.addOrderByFieldDesc("t.SENDER_TIME");
			} else {
				mail.addOrderByFieldDesc("receiveTime2");
			}
			if (mail.getMailboxId() == null) {
				if (Constants.IC_MAIL_MAILFOLDER_DRAFT
						.equals((receivers.get(0)).getFolderId())
						|| Constants.IC_MAIL_MAILFOLDER_SENDED
								.equals((receivers.get(0)).getFolderId())) {
					mail.addOrderByFieldDesc("t.SENDER_TIME");
				} else {
					mail.addOrderByFieldDesc("receiveTime2");
				}
			}
			PageManager pm = new PageManager();
			pm.setPage(Integer.parseInt(curPage));
			PageManager page_ = mailService.query(mail, pm);
			List<Mail> list = (List<Mail>) page_.getData();
			List<Mail4M> alist = new ArrayList<Mail4M>();
			if (list != null) {
				for (Mail i : list) {
					Mail4M vo = new Mail4M();
					BeanUtils.copyProperties(i, vo);// (源，目标)

					content = StringUtil.filterHtml(i.getMailContent());
					content = StringUtil.replaceNBSP(content);
					content = StringUtil.getTitle4M(content);
					

					// 得到邮件的所有收件人
					List<MailRecord> reList = i.getReceivers();

					// 阅读状态：0未读1已读
					Integer readFlag = 0;

					for (MailRecord re : reList) {
						if (userId
								.equals(String.valueOf(re.getReceiveUserId()))) {
							readFlag = re.getReadFlag();
							break;
						}
					}

					senderTime = dateFormat.format(i.getSenderTime());
					vo.setSenderTime(senderTime);
					vo.setMailContent(content);
					// 已读状态
					vo.setIsRead(String.valueOf(readFlag));

					alist.add(vo);
				}
				if (alist != null) {
					// 成功状态位和数据
					resultMap.put("state", "success");
					resultMap.put("data", alist);
				}
			} else {
				// 失败状态位和错误信息
				resultMap.put("state", "failure");
				resultMap.put("errormsg",
						MessageUtils.getMessage("JC_OA_COMMON_016"));
			}
		} else {
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",
					MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}

	/**
	 * 方法描述：
	 * 
	 * @param userId
	 * @param folderId1
	 *            -已发送 2-发件箱
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 曹杨
	 * @version 2014年9月11日下午1:55:30
	 * @see
	 */
	@RequestMapping(value = "mailListCount4M.action")
	@ResponseBody
	// @ActionLog(operateModelNm = "移动端查询邮件数量", operateFuncNm =
	// "mailListCount4M", operateDescribe = "移动端查询邮件数量")
	public Map<String, Object> mailListCount4M(String userId, String folderId,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 设置发布人为当前用户
		if (userId != null) {
			// 查询内部邮箱
			Mail mail = new Mail();
			List<Mailbox> mailboxlist = new ArrayList<Mailbox>();
			Mailbox mbox = new Mailbox();
			mbox.setId(1L);
			mailboxlist.add(mbox);
			mail.setMailbox(mailboxlist);
			// 查询内部邮箱需要设置收件人userId
			mail.setReceivers(new ArrayList<MailRecord>());
			MailRecord receive;
			receive = new MailRecord();
			receive.setFolderId(Long.parseLong(folderId));
			receive.setReceiveUserId(Long.parseLong(userId));
			mail.getReceivers().add(receive);

			if (mail.getSearchReceiveTimeEnd() != null) {
				mail.setSearchReceiveTimeEnd(DateUtils.fillTime(mail
						.getSearchReceiveTimeEnd()));
			}
			if ("".equals(mail.getMailTitle())) {
				mail.setMailTitle(null);
			}
			if ("".equals(mail.getSenderMail())) {
				mail.setSenderMail(null);
			}
			// 取得收件人
			List<MailRecord> receivers = mail.getReceivers();
			if (Constants.IC_MAIL_MAILFOLDER_DRAFT.equals((receivers.get(0))
					.getFolderId())
					|| Constants.IC_MAIL_MAILFOLDER_SENDED.equals((receivers
							.get(0)).getFolderId())) {
				mail.addOrderByFieldDesc("t.SENDER_TIME");
			} else {
				mail.addOrderByFieldDesc("receiveTime2");
			}
			if (mail.getMailboxId() == null) {
				if (Constants.IC_MAIL_MAILFOLDER_DRAFT
						.equals((receivers.get(0)).getFolderId())
						|| Constants.IC_MAIL_MAILFOLDER_SENDED
								.equals((receivers.get(0)).getFolderId())) {
					mail.addOrderByFieldDesc("t.SENDER_TIME");
				} else {
					mail.addOrderByFieldDesc("receiveTime2");
				}
			}
			PageManager pm = new PageManager();
			PageManager page_ = mailService.query(mail, pm);
			List<Mail> list = (List<Mail>) page_.getData();
			if (list != null) {
				// 成功状态位和数据
				resultMap.put("state", "success");
				resultMap.put("data", page_.getTotalCount());
			} else {
				// 失败状态位和错误信息
				resultMap.put("state", "failure");
				resultMap.put("errormsg",
						MessageUtils.getMessage("JC_OA_COMMON_016"));
			}
		} else {
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",
					MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}
}