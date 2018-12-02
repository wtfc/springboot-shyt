package com.jc.oa.ic.web;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.domain.Contacts;
import com.jc.oa.ic.domain.Mail;
import com.jc.oa.ic.domain.MailFolder;
import com.jc.oa.ic.domain.MailRecord;
import com.jc.oa.ic.domain.Mailbox;
import com.jc.oa.ic.domain.validator.MailValidator;
import com.jc.oa.ic.service.IContactsService;
import com.jc.oa.ic.service.IMailRecordService;
import com.jc.oa.ic.service.IMailService;
import com.jc.system.CustomException;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.content.domain.Attach;
import com.jc.system.portal.util.PortalUtils;
import com.jc.system.security.SystemAuthorizingRealm.Principal;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.Loginlog;
import com.jc.system.security.util.ActionLog;
import com.jc.system.security.util.SettingUtils;
import com.jc.system.security.util.UserUtils;

/**
 * @title 互动交流
 * @author
 * @version 2014-04-17
 */

@Controller
@RequestMapping(value = "/ic/mail")
public class MailController extends BaseController {

	@Autowired
	private IMailService mailService;

	@Autowired
	private IMailRecordService mailRecordService;
	@Autowired
	private IContactsService contactsService;

	@org.springframework.web.bind.annotation.InitBinder("mail")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new MailValidator());
	}

	public MailController() {
	}

	/**
	 * @description 分页查询方法
	 * @param Mail
	 *            mail 实体类
	 * @param PageManager
	 *            page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-04-17
	 * @throws Exception
	 */
	@RequestMapping(value = "manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageList", operateDescribe = "查询邮件列表")
	public PageManager manageList(Mail mail, PageManager page,
			HttpServletRequest request) throws Exception {
		if (mail.getSearchReceiveTimeEnd() != null) {
			mail.setSearchReceiveTimeEnd(DateUtils.fillTime(mail
					.getSearchReceiveTimeEnd()));
		}
		// 查询条件为""设置为null
		if ("".equals(mail.getMailTitle())) {
			mail.setMailTitle(null);
		}
		if ("".equals(mail.getSenderMail())) {
			mail.setSenderMail(null);
		}
		if("".equals(mail.getMailTitle())){
			mail.setMailEasyTitle(null);
		}
		if (mail.getReceivers().size() > 0
				&& "".equals(mail.getReceivers().get(0).getReceiveMailSearch())) {
			mail.getReceivers().get(0).setReceiveMailSearch(null);
		}
		if (mail.getReceivers().size() > 0
				&& "".equals(String.valueOf(mail.getReceivers().get(0)
						.getReceiveUserIdSearch()))) {
			mail.getReceivers().get(0).setReceiveUserIdSearch(null);
		}
		List<MailRecord> receivers = mail.getReceivers();
		if (Constants.IC_MAIL_MAILFOLDER_DRAFT.equals((receivers.get(0))
				.getFolderId())
				|| Constants.IC_MAIL_MAILFOLDER_SENDED
						.equals((receivers.get(0)).getFolderId())) {
			mail.addOrderByFieldDesc("t.SENDER_TIME");
		} else {
			mail.addOrderByFieldDesc("t.RECEIVE_TIME2");
		}
		if (mail.getMailboxId() == null) {
			if (Constants.IC_MAIL_MAILFOLDER_DRAFT.equals((receivers.get(0))
					.getFolderId())
					|| Constants.IC_MAIL_MAILFOLDER_SENDED.equals((receivers
							.get(0)).getFolderId())) {
				mail.addOrderByFieldDesc("t.SENDER_TIME");
			} else {
				mail.addOrderByFieldDesc("t.RECEIVE_TIME2");
			}
			Mailbox mailbox = new Mailbox();
			mailbox.setCreateUser(SystemSecurityUtils.getUser().getId());
			List<Mailbox> mailboxlist = mailService.getMailboxList(mailbox);
			Mailbox mbox = new Mailbox();
			mbox.setId(1L);
			mailboxlist.add(mbox);
			mail.setMailbox(mailboxlist);
		}
		PageManager page_ = mailService.query(mail, page);

		List<Principal> uList = SystemSecurityUtils.getOnlineUsers();

		List<Mail> mailList = (List<Mail>) page_.getData();
		MailRecord mailRecord = new MailRecord();
		mailRecord.setDeleteFlag(null);
		List<MailRecord> recordList = new ArrayList<MailRecord>();
		PageManager page1 = new PageManager();
		// 如果列表中只有一条数据，将index设置为-1，js中通过判断是-1来屏蔽掉上一页下一页的功能
		if (mailList.size() == 1 && page_.getTotalPages() == 1) {
			mailList.get(0).setIndex(0);
			mailList.get(0).setShowNext(false);
			mailList.get(0).setShowPre(false);
			if ("1".equals(mailList.get(0).getReceivers().get(0).getFolderId()
					.toString())) {
				boolean isOnlineUser = true;
				// 内部邮箱
				if (mail.getMailboxId() != null) {
					if (mail.getMailboxId() == 1) {
						// 如果当前在线人员不为空
						if (uList != null && uList.size() > 0) {
							for (int j = 0; j < uList.size(); j++) {
								// 判断当前发信人是否在线如果在线设置isOnline为”1“
								if (uList.get(j).getId().longValue() == mailList
										.get(0).getSenderUserId().longValue()) {
									mailList.get(0).setIsOnline("1");
									isOnlineUser = false;
									break;
								}
							}
							if (isOnlineUser) {
								mailList.get(0).setIsOnline("0");
							}
						}
					}
				}
			}
			if ("3".equals(mailList.get(0).getReceivers().get(0).getFolderId()
					.toString())) {
				mailRecord.setMailId(mailList.get(0).getId());
				// 根据当前记录主表id获得子表记录
				recordList = mailRecordService.queryAll(mailRecord);
				if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(mailList
						.get(0).getReceivers().get(0).getReceiveType())) {
					// 设置已读未读标识，方便追回使用
					int isRead = 1;
					for (MailRecord re : recordList) {
						if (!Constants.IC_MAIL_RECEIVETYPE_INNERSENDER
								.equals(re.getReceiveType())) {
							if (re.getReadDate() == null) {
								isRead = 0;
								break;
							}
						}
					}
					mailList.get(0).setIsRead(String.valueOf(isRead));
				} else {
					mailList.get(0).setIsRead("1");
				}

			}
			if ("2".equals(mailList.get(0).getReceivers().get(0).getFolderId()
					.toString())) {
				mailRecord.setMailId(mailList.get(0).getId());
				// 根据当前记录主表id获得子表记录
				page1.setPageRows(2);
				recordList = (List<MailRecord>) mailRecordService.query(
						mailRecord, page1).getData();
				// 如果子表记录数为1，说明该邮件无收件人
				if (recordList.size() == 1
						&& Constants.IC_MAIL_RECEIVETYPE_INNERSENDER
								.equals(recordList.get(0).getReceiveType())) {
					mailList.get(0).setIsHaveReceiveUser("0");
				}
			}

		} else {
			boolean isOnlineUser = true;
			for (int i = 0; i < mailList.size(); i++) {
				if ("1".equals(mailList.get(0).getReceivers().get(0)
						.getFolderId().toString())) {
					isOnlineUser = true;
					// 内部邮箱
					if (mail.getMailboxId() != null) {
						if (mail.getMailboxId() == 1) {
							// 如果当前在线人员不为空
							if (uList != null && uList.size() > 0) {
								for (int j = 0; j < uList.size(); j++) {
									// 判断当前发信人是否在线如果在线设置isOnline为”1“
									if (uList.get(j).getId().longValue() == mailList
											.get(i).getSenderUserId()
											.longValue()) {
										mailList.get(i).setIsOnline("1");
										isOnlineUser = false;
										break;
									}
								}
								if (isOnlineUser) {
									mailList.get(i).setIsOnline("0");
								}
							}
						}
					}
				}
				if ("3".equals(mailList.get(0).getReceivers().get(0)
						.getFolderId().toString())) {
					mailRecord.setMailId(mailList.get(i).getId());
					// 根据当前记录主表id获得子表记录
					recordList = mailRecordService.queryAll(mailRecord);

					if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER
							.equals(mailList.get(i).getReceivers().get(0)
									.getReceiveType())) {
						// 设置已读未读标识，方便追回使用
						int isRead = 1;
						for (MailRecord re : recordList) {
							if (!Constants.IC_MAIL_RECEIVETYPE_INNERSENDER
									.equals(re.getReceiveType())) {
								if (re.getReadDate() == null) {
									isRead = 0;
									break;
								}
								// if (re.getReadDate() != null) {
								// isRead = isRead | 1;
								// mailList.get(i).setIsRead(
								// String.valueOf(isRead));
								// } else {
								// isRead = isRead | 0;
								// mailList.get(i).setIsRead(
								// String.valueOf(isRead));
								// }
							}
						}
						mailList.get(i).setIsRead(String.valueOf(isRead));
					} else {
						mailList.get(i).setIsRead("1");
					}
				}
				if ("2".equals(mailList.get(0).getReceivers().get(0)
						.getFolderId().toString())) {
					mailRecord.setMailId(mailList.get(i).getId());
					// 根据当前记录主表id获得子表记录
					page1.setPageRows(2);
					recordList = (List<MailRecord>) mailRecordService.query(
							mailRecord, page1).getData();
					// recordList = mailRecordService.queryAll(mailRecord);
					// 如果子表记录数为1，说明该邮件无收件人
					if (recordList.size() == 1
							&& Constants.IC_MAIL_RECEIVETYPE_INNERSENDER
									.equals(recordList.get(0).getReceiveType())) {
						mailList.get(i).setIsHaveReceiveUser("0");
					}
				}
				if (i == 0 && page.getPage() == 0) {
					mailList.get(i).setShowPre(false);
				}
				if (i == mailList.size() - 1
						&& page_.getTotalPages() == page_.getPage()) {
					mailList.get(i).setShowNext(false);
				}
				mailList.get(i).setIndex(
						(page_.getPage() - 1) * page_.getPageRows() + i);
			}
		}
		return page_;
	}

	/**
	 * @description 分页查询方法
	 * @param Mail
	 *            mail 实体类
	 * @param PageManager
	 *            page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-04-17
	 * @throws Exception
	 */
	@RequestMapping(value = "manageSearch.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageSearch", operateDescribe = "搜索邮件列表")
	public PageManager manageSearchList(Mail mail, PageManager page,
			HttpServletRequest request) throws Exception {
		if (StringUtils.isEmpty(mail.getSenderMail())) {
			mail.setSenderMail(null);
		}
		if (StringUtils.isEmpty(mail.getMailTitle())) {
			mail.setMailTitle(null);
		}
		if (StringUtils.isEmpty(mail.getMailContent())) {
			mail.setMailContent(null);
		}
		if (mail.getSearchReceiveTimeEnd() != null) {
			mail.setSearchReceiveTimeEnd(DateUtils.fillTime(mail
					.getSearchReceiveTimeEnd()));
		}
		if (mail.getReceivers().size() > 0
				&& "".equals(mail.getReceivers().get(0).getReceiveMailSearch())) {
			mail.getReceivers().get(0).setReceiveMailSearch(null);
		}
		if (mail.getReceivers().size() > 0
				&& "".equals(String.valueOf(mail.getReceivers().get(0)
						.getReceiveUserIdSearch()))) {
			mail.getReceivers().get(0).setReceiveUserIdSearch(null);
		}
		mail.addOrderByFieldDesc("t.RECEIVE_TIME2");
		PageManager page_ = mailService.search(mail, page);

		List<Principal> uList = SystemSecurityUtils.getOnlineUsers();

		List<Mail> mailList = (List<Mail>) page_.getData();
		MailRecord mailRecord = new MailRecord();
		List<MailRecord> recordList = new ArrayList<MailRecord>();
		// 如果列表中只有一条数据，将index设置为-1，js中通过判断是-1来屏蔽掉上一页下一页的功能
		if (mailList.size() == 1 && page_.getTotalPages() == 1) {
			mailList.get(0).setIndex(0);
			mailList.get(0).setShowNext(false);
			mailList.get(0).setShowPre(false);

			mailRecord.setMailId(mailList.get(0).getId());
			// 根据当前记录主表id获得子表记录
			recordList = mailRecordService.queryAll(mailRecord);
			// 如果子表记录数为1，说明该邮件无收件人
			if (recordList.size() == 1
					&& Constants.IC_MAIL_RECEIVETYPE_INNERSENDER
							.equals(recordList.get(0).getReceiveType())) {
				mailList.get(0).setIsHaveReceiveUser("0");
			}
			boolean isOnlineUser = true;
			// 内部邮箱
			if (mail.getMailboxId() != null) {
				if (mail.getMailboxId() == 1) {
					// 如果当前在线人员不为空
					if (uList != null && uList.size() > 0) {
						for (int j = 0; j < uList.size(); j++) {
							// 判断当前发信人是否在线如果在线设置isOnline为”1“
							if (uList.get(j).getId().longValue() == mailList
									.get(0).getSenderUserId().longValue()) {
								mailList.get(0).setIsOnline("1");
								isOnlineUser = false;
								break;
							}
						}
						if (isOnlineUser) {
							mailList.get(0).setIsOnline("0");
						}
					}
				}
			}
			// 设置已读未读标识，方便追回使用
//modify by liuxl start 2015.03.10			
//			int isRead = 0;
			int isRead = 1;
			for (MailRecord re : recordList) {
				if (!Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(re.getReceiveType())) {
//					if (re.getReadDate() != null) {
//						isRead = isRead | 1;
//						mailList.get(0).setIsRead(String.valueOf(isRead));
//					} else {
//						isRead = isRead | 0;
//						mailList.get(0).setIsRead(String.valueOf(isRead));
//					}
					
					if (re.getReadDate() == null) {
						isRead = 0;
//						break;
					}
					mailList.get(0).setIsRead(String.valueOf(isRead));
				}
			}
//modify by liuxl end 2015.03.10			
		} else {
			boolean isOnlineUser = true;
			for (int i = 0; i < mailList.size(); i++) {
				isOnlineUser = true;
				// 内部邮箱
				if (mail.getMailboxId() != null) {
					if (mail.getMailboxId() == 1) {
						// 如果当前在线人员不为空
						if (uList != null && uList.size() > 0) {
							for (int j = 0; j < uList.size(); j++) {
								// 判断当前发信人是否在线如果在线设置isOnline为”1“
								if (uList.get(j).getId().longValue() == mailList
										.get(i).getSenderUserId().longValue()) {
									mailList.get(i).setIsOnline("1");
									isOnlineUser = false;
									break;
								}
							}
							if (isOnlineUser) {
								mailList.get(i).setIsOnline("0");
							}
						}
					}
				}
				mailRecord.setMailId(mailList.get(i).getId());
				// 根据当前记录主表id获得子表记录
				recordList = mailRecordService.queryAll(mailRecord);
				// 设置已读未读标识，方便追回使用
			//	int isRead = 0;
				for (MailRecord re : recordList) {
					if (!Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(re.getReceiveType())) {
						if (re.getReadDate() == null) {      
							
							mailList.get(i).setIsRead("0");
						} 
						
						/*if (re.getReadDate() != null) {
							isRead = isRead | 1;
							mailList.get(i).setIsRead(String.valueOf(isRead));
						} else {
							isRead = isRead & 0;
							mailList.get(i).setIsRead(String.valueOf(isRead));
						}*/
					}
				}
				if(	mailList.get(i).getIsRead()==null){
					mailList.get(i).setIsRead("1");

				}
				// 如果子表记录数为1，说明该邮件无收件人
				if (recordList.size() == 1
						&& Constants.IC_MAIL_RECEIVETYPE_INNERSENDER
								.equals(recordList.get(0).getReceiveType())) {
					mailList.get(i).setIsHaveReceiveUser("0");
				}
				if (i == 0 && page.getPage() == 0) {
					mailList.get(i).setShowPre(false);
				}
				if (i == mailList.size() - 1
						&& page_.getTotalPages() == page_.getPage()) {
					mailList.get(i).setShowNext(false);
				}
				mailList.get(i).setIndex(
						(page_.getPage() - 1) * page_.getPageRows() + i);
			}
		}
		return page_;
	}

	/**
	 * @description 跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-04-17
	 */
	@RequestMapping(value = "manage.action/{fid}")
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manage", operateDescribe = "进入邮件列表页")
	public String manage(@PathVariable Long fid, Model model,
			HttpServletRequest request) throws Exception {
		model.addAttribute("mailFolderList", this.initMailFolders(request));
		model.addAttribute("mailboxList", this.initMailboxs(request));
		model.addAttribute("mfid", fid);
		model.addAttribute("userId", SystemSecurityUtils.getUser().getId());
		StringBuffer url = new StringBuffer(request.getServletPath());
		if (request.getQueryString() != null) {
			url.append("?").append(request.getQueryString());
		}
		model.addAttribute("url", url.toString());

		Map paraMap = request.getParameterMap();
		Iterator<String> keys = paraMap.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			model.addAttribute(key, ((String[]) paraMap.get(key))[0]);
			if (key.equals("receivers[0].folderId")) {
				model.addAttribute("mfid", ((String[]) paraMap.get(key))[0]);
			}
			if (key.equals("receivers[0].readFlag")) {
				model.addAttribute("receiversReadFlag",
						((String[]) paraMap.get(key))[0]);
			}
			// 如果内部邮箱发件箱查询条件不为空
			if (key.equals("receivers[0].receiveUserIdSearch")) {
				model.addAttribute("senderUserId",
						((String[]) paraMap.get(key))[0]);
			}
			// 如果外部邮箱发件箱查询条件不为空
			if (key.equals("receivers[0].receiveMailSearch")) {
				model.addAttribute("senderMail",
						((String[]) paraMap.get(key))[0]);
			}
		}
		model.addAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));
		return "ic/mail/mailInteract";
	}

	/**
	 * @description 星标邮件跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-04-17
	 */
	@RequestMapping(value = "manageStar.action")
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageStar", operateDescribe = "查看星标邮件")
	public String manageStar(Model model, HttpServletRequest request)
			throws Exception {
		model.addAttribute("mailFolderList", this.initMailFolders(request));
		model.addAttribute("mailboxList", this.initMailboxs(request));
		model.addAttribute("starMail", Constants.IC_MAIL_STARMAIL_YES);
		model.addAttribute("userId", SystemSecurityUtils.getUser().getId());
		model.addAttribute("star", 6);
		StringBuffer url = new StringBuffer(request.getServletPath());
		if (request.getQueryString() != null) {
			url.append("?").append(request.getQueryString());
		}
		model.addAttribute("url", url.toString());
		Map paraMap = request.getParameterMap();
		Iterator<String> keys = paraMap.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			model.addAttribute(key, ((String[]) paraMap.get(key))[0]);
			if (key.equals("receivers[0].folderId")) {
				model.addAttribute("mfid", ((String[]) paraMap.get(key))[0]);
			}
			if (key.equals("receivers[0].readFlag")) {
				model.addAttribute("receiversReadFlag",
						((String[]) paraMap.get(key))[0]);
			}
		}
		model.addAttribute("moveShow", 1);// 星标邮件列表不显示移动至
		model.addAttribute("excludeFolderId", "4");
		return "ic/mail/mailStar";
	}

	/**
	 * 方法描述：阅读邮件
	 * 
	 * @return String 阅读邮件页面
	 * @author zhanglg
	 * @version 2014年5月7日下午1:05:42
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "manageDetail.action")
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageDetail", operateDescribe = "阅读邮件")
	public String manageDetail(Mail mailParam, String password,
			String folderId, Model model, HttpServletRequest request)
			throws Exception {
		Mail mail = new Mail();
		mail.setId(mailParam.getId());
		mail = mailService.get(mail);
		mailService.setReadStatus(mail, mailParam.getMrid(),
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
			if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(type)
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
		// 显示人员状态声明
		StringBuffer newTo = new StringBuffer();
		StringBuffer newCc = new StringBuffer();
		StringBuffer newSs = new StringBuffer();
		StringBuffer newBcc = new StringBuffer();

		for (int i = 0; i < receivers.size(); i++) {
			MailRecord record = receivers.get(i);
			if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
				if (mailParam.getMrid().equals(record.getId().toString())) {
					mail.setReceiver(record);
				}
				// 可以看到群发单显：群发单显收件人可以看到自己、发件人可以看到所有，
				if ((Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
						.getReceiveType()) && record.getReceiveUserId().equals(
						userId))
						|| isSender
						&& (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE)
								.equals(record.getReceiveType())) {
					if (record.getReadDate() != null) {
						newSs.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						ss.append(record.getReceiveUserName()).append(",");
					} else {
						newSs.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						ss.append(record.getReceiveUserName()).append(",");
					}

				} else if (Constants.IC_MAIL_RECEIVETYPE_TO.equals(record
						.getReceiveType())) {
					if (record.getReadDate() != null) {
						newTo.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						to.append(record.getReceiveUserName()).append(",");
					} else {
						newTo.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						to.append(record.getReceiveUserName()).append(",");
					}
				} else if (Constants.IC_MAIL_RECEIVETYPE_CC.equals(record
						.getReceiveType())) {
					if (record.getReadDate() != null) {
						newCc.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						cc.append(record.getReceiveUserName()).append(",");
					} else {
						newCc.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						cc.append(record.getReceiveUserName()).append(",");
					}
				}
				// 可以看到密送：收件人可以看到自己、发件人可以看到所有
				else if ((Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
						.getReceiveType()) && record.getReceiveUserId().equals(
						userId))
						|| isSender
						&& (Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
								.getReceiveType()))) {
					if (record.getReadDate() != null) {
						newBcc.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						bcc.append(record.getReceiveUserName()).append(",");
					} else {
						newBcc.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\"  onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						bcc.append(record.getReceiveUserName()).append(",");
					}
				}
			} else {
				if (mailParam.getMrid().equals(record.getId().toString())) {
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

		if (newTo.length() > 0 && newTo.charAt(newTo.length() - 1) == ',') {
			newTo.deleteCharAt(newTo.length() - 1);
		}
		if (newSs.length() > 0 && newSs.charAt(newSs.length() - 1) == ',') {
			newSs.deleteCharAt(newSs.length() - 1);
		}
		if (newCc.length() > 0 && newCc.charAt(newCc.length() - 1) == ',') {
			newCc.deleteCharAt(newCc.length() - 1);
		}
		if (newBcc.length() > 0 && newBcc.charAt(newBcc.length() - 1) == ',') {
			newBcc.deleteCharAt(newBcc.length() - 1);
		}

		mail.setTo(to.toString());
		mail.setCc(cc.toString());
		mail.setShowSingle(ss.toString());
		mail.setBcc(bcc.toString());
		// 回显收件人状态使用
		mail.setNewTo(newTo.toString());
		mail.setNewCc(newCc.toString());
		mail.setNewBcc(newBcc.toString());
		mail.setNewSs(newSs.toString());

		// 不再用下面这种逻辑显示收件人、抄送人了
		// 移除发件人记录和非本人的密送记录,and remove not me when show single
		// for (int i = receivers.size() - 1; i >= 0; i--) {
		// MailRecord record = receivers.get(i);
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE)) {
		// showSingle = showSingle | true;
		// }
		// // 内部邮件
		// if (mail.getMailboxId().equals(Constants.IC_MAIL_MAILBOX_INNER)) {
		// // 发送人不是当前用户
		// if (!mail.getSenderUserId().equals(
		// SystemSecurityUtils.getUser().getId())) {
		// // 如果是群发单显，并且接收人不是当前用户remove，接收人只显示自己
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE)
		// && !record.getReceiveUserId().equals(
		// SystemSecurityUtils.getUser().getId())) {
		// receivers.remove(i);
		// continue;
		// }
		// }
		// if (mailParam.getMrid().equals(record.getId().toString())) {
		// mail.setReceiver(record);
		// }
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
		// receivers.remove(i);
		// } else if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_BCC)) {
		// if(!record.getReceiveUserId().equals(SystemSecurityUtils.getUser().getId())){//如果密送人是当前登录人，不删除
		// if(!mail.getSenderUserId().equals(SystemSecurityUtils.getUser().getId())){//如果发送人是当前登录人，不删除
		// receivers.remove(i);
		// }
		// }
		// }
		//
		//
		// } else {// 外部
		// if (mailParam.getMrid().equals(record.getId().toString())) {
		// mail.setReceiver(record);
		// }
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
		// receivers.remove(i);
		// } else if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_BCC)) {
		// boolean remove = true;
		// for (Mailbox box : boxList) {
		// if (box.getAddress().equals(record.getReceiveMail())) {
		// remove = false;
		// }
		// }
		// if (remove) {
		// receivers.remove(i);
		// }
		// }
		// }
		// }
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

		model.addAttribute("mailFolderList", this.initMailFolders(request));
		model.addAttribute("mail", mail);
		model.addAttribute("showSignle", mail);
		if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			mailbox = new Mailbox();
			mailbox.setId(mail.getMailboxId());
			model.addAttribute("mailboxAddress", mailService
					.getMailbox(mailbox).getAddress());
		}

		model.addAttribute("currentUserId", SystemSecurityUtils.getUser()
				.getId());
		model.addAttribute("folderId", folderId);
		model.addAttribute("mfid", folderId);
		if (mailParam != null) {
			if (mailParam.getReturnURL() != null) {
				if(URLDecoder.decode(mailParam.getReturnURL(),"utf-8").indexOf("?") != -1){
					mail.setReturnURL(mailParam.getReturnURL() + "&returned=true");
					}
					else{
						mail.setReturnURL(mailParam.getReturnURL());
				}
			}
			mail.setIndex(mailParam.getIndex());
			mail.setShowNext(mailParam.isShowNext());
			mail.setShowPre(mailParam.isShowPre());
			if (mailParam.getMailboxId() != null
					&& mailParam.getMailboxId() == 0) {
				model.addAttribute("isAllMail", "true");
			} else {
				model.addAttribute("isAllMail", "false");
			}
			model.addAttribute("mailTitle", mailParam.getMailTitle());
			model.addAttribute("mailEasyTitle", mailParam.getMailEasyTitle());
			model.addAttribute("senderUserId", mailParam.getSenderUserId());
			model.addAttribute("senderMail", mailParam.getSenderMail());
			if (mailParam.getReceivers() != null
					&& mailParam.getReceivers().get(0) != null) {
				model.addAttribute("readFlag", mailParam.getReceivers().get(0)
						.getReadFlag());
			}
			if (mailParam.getSearchReceiveTimeBegin() != null) {
				model.addAttribute("searchReceiveTimeBegin", DateUtils
						.formatDate(mailParam.getSearchReceiveTimeBegin(),
								"yyyy-MM-dd"));
			}
			if (mailParam.getSearchReceiveTimeEnd() != null) {
				model.addAttribute("searchReceiveTimeEnd", DateUtils
						.formatDate(mailParam.getSearchReceiveTimeEnd(),
								"yyyy-MM-dd"));
			}
		}
		model.addAttribute("user", SystemSecurityUtils.getUser());
		return "ic/mail/mail";
	}

	/**
	 * 方法描述：通过提醒阅读邮件
	 * 
	 * @return String 阅读邮件页面
	 * @author zhanglg
	 * @version 2014年5月7日下午1:05:42
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "manageDetailContent.action")
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageDetailContent", operateDescribe = "通过提醒阅读邮件")
	public String manageDetailContent(Mail mailParam, String password,
			String folderId, Model model, HttpServletRequest request,boolean readOnly)
			throws Exception {
		Mail mail = new Mail();
		mail.setId(mailParam.getId());
		mail = mailService.get(mail);
		
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
			if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(type)
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
		// 显示人员状态声明
		StringBuffer newTo = new StringBuffer();
		StringBuffer newCc = new StringBuffer();
		StringBuffer newSs = new StringBuffer();
		StringBuffer newBcc = new StringBuffer();

		for (int i = 0; i < receivers.size(); i++) {
			MailRecord record = receivers.get(i);
			if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
				if (mailParam.getMrid().equals(record.getId().toString())) {
					mail.setReceiver(record);
				}
				// 可以看到群发单显：群发单显收件人可以看到自己、发件人可以看到所有，
				if ((Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
						.getReceiveType()) && record.getReceiveUserId().equals(
						userId))
						|| isSender
						&& (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE
								.equals(record.getReceiveType()))) {
					if (record.getReadDate() != null) {
						newSs.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						ss.append(record.getReceiveUserName()).append(",");
					} else {
						newSs.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						ss.append(record.getReceiveUserName()).append(",");
					}
				} else if (Constants.IC_MAIL_RECEIVETYPE_TO.equals(record
						.getReceiveType())) {
					if (record.getReadDate() != null) {
						newTo.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						to.append(record.getReceiveUserName()).append(",");
					} else {
						newTo.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						to.append(record.getReceiveUserName()).append(",");
					}
				} else if (Constants.IC_MAIL_RECEIVETYPE_CC.equals(record
						.getReceiveType())) {
					if (record.getReadDate() != null) {
						newCc.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						cc.append(record.getReceiveUserName()).append(",");
					} else {
						newCc.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						cc.append(record.getReceiveUserName()).append(",");
					}
				}
				// 可以看到密送：收件人可以看到自己、发件人可以看到所有
				else if ((Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
						.getReceiveType()) && record.getReceiveUserId().equals(
						userId))
						|| isSender
						&& (Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
								.getReceiveType()))) {
					if (record.getReadDate() != null) {
						newBcc.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						bcc.append(record.getReceiveUserName()).append(",");
					} else {
						newBcc.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\"  onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						bcc.append(record.getReceiveUserName()).append(",");
					}
				}
			} else {
				if (mailParam.getMrid().equals(record.getId().toString())) {
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
		if (newTo.length() > 0 && newTo.charAt(newTo.length() - 1) == ',') {
			newTo.deleteCharAt(newTo.length() - 1);
		}
		if (newSs.length() > 0 && newSs.charAt(newSs.length() - 1) == ',') {
			newSs.deleteCharAt(newSs.length() - 1);
		}
		if (newCc.length() > 0 && newCc.charAt(newCc.length() - 1) == ',') {
			newCc.deleteCharAt(newCc.length() - 1);
		}
		if (newBcc.length() > 0 && newBcc.charAt(newBcc.length() - 1) == ',') {
			newBcc.deleteCharAt(newBcc.length() - 1);
		}

		mail.setTo(to.toString());
		mail.setCc(cc.toString());
		mail.setShowSingle(ss.toString());
		mail.setBcc(bcc.toString());
		// 回显收件人状态使用
		mail.setNewTo(newTo.toString());
		mail.setNewCc(newCc.toString());
		mail.setNewBcc(newBcc.toString());
		mail.setNewSs(newSs.toString());
		// 不再用下面这种逻辑显示收件人、抄送人了
		// 移除发件人记录和非本人的密送记录,and remove not me when show single
		// for (int i = receivers.size() - 1; i >= 0; i--) {
		// MailRecord record = receivers.get(i);
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE)) {
		// showSingle = showSingle | true;
		// }
		// // 内部邮件
		// if (mail.getMailboxId().equals(Constants.IC_MAIL_MAILBOX_INNER)) {
		// // 发送人不是当前用户
		// if (!mail.getSenderUserId().equals(
		// SystemSecurityUtils.getUser().getId())) {
		// // 如果是群发单显，并且接收人不是当前用户remove，接收人只显示自己
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE)
		// && !record.getReceiveUserId().equals(
		// SystemSecurityUtils.getUser().getId())) {
		// receivers.remove(i);
		// continue;
		// }
		// }
		// if (mailParam.getMrid().equals(record.getId().toString())) {
		// mail.setReceiver(record);
		// }
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
		// receivers.remove(i);
		// } else if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_BCC)) {
		// if(!record.getReceiveUserId().equals(SystemSecurityUtils.getUser().getId())){//如果密送人是当前登录人，不删除
		// if(!mail.getSenderUserId().equals(SystemSecurityUtils.getUser().getId())){//如果发送人是当前登录人，不删除
		// receivers.remove(i);
		// }
		// }
		// }
		//
		//
		// } else {// 外部
		// if (mailParam.getMrid().equals(record.getId().toString())) {
		// mail.setReceiver(record);
		// }
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
		// receivers.remove(i);
		// } else if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_BCC)) {
		// boolean remove = true;
		// for (Mailbox box : boxList) {
		// if (box.getAddress().equals(record.getReceiveMail())) {
		// remove = false;
		// }
		// }
		// if (remove) {
		// receivers.remove(i);
		// }
		// }
		// }
		// }
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

		model.addAttribute("mailFolderList", this.initMailFolders(request));
		model.addAttribute("showSignle", mail);
		if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			mailbox = new Mailbox();
			mailbox.setId(mail.getMailboxId());
			model.addAttribute("mailboxAddress", mailService
					.getMailbox(mailbox).getAddress());
		}

		model.addAttribute("currentUserId", SystemSecurityUtils.getUser()
				.getId());
		model.addAttribute("folderId", folderId);
		model.addAttribute("mfid", folderId);
		if (mailParam != null) {
			if (mailParam.getReturnURL() != null) {
				if(URLDecoder.decode(mailParam.getReturnURL(),"utf-8").indexOf("?") != -1){
					mail.setReturnURL(mailParam.getReturnURL() + "&returned=true");
					}
					else{
						mail.setReturnURL(mailParam.getReturnURL());
				}
			}
			//mail.setIndex(mailParam.getIndex());
			if (mailParam.getMailboxId() != null
					&& mailParam.getMailboxId() == 0) {
				model.addAttribute("isAllMail", "true");
			} else {
				model.addAttribute("isAllMail", "false");
			}
			model.addAttribute("mailTitle", mailParam.getMailTitle());
			model.addAttribute("mailEasyTitle", mailParam.getMailEasyTitle());
			model.addAttribute("senderUserId", mailParam.getSenderUserId());
			model.addAttribute("senderMail", mailParam.getSenderMail());
			if (mailParam.getReceivers() != null
					&& mailParam.getReceivers().get(0) != null) {
				model.addAttribute("readFlag", mailParam.getReceivers().get(0)
						.getReadFlag());
			}
			if (mailParam.getSearchReceiveTimeBegin() != null) {
				model.addAttribute("searchReceiveTimeBegin", DateUtils
						.formatDate(mailParam.getSearchReceiveTimeBegin(),
								"yyyy-MM-dd"));
			}
			if (mailParam.getSearchReceiveTimeEnd() != null) {
				model.addAttribute("searchReceiveTimeEnd", DateUtils
						.formatDate(mailParam.getSearchReceiveTimeEnd(),
								"yyyy-MM-dd"));
			}
		}
		model.addAttribute("user", SystemSecurityUtils.getUser());
		
		MailRecord mailRecord = new MailRecord();
		mailRecord.setId(Long.parseLong(mailParam.getMrid()));
		mailRecord = mailRecordService.get(mailRecord);
		List<Mail> mailList  = new ArrayList<Mail>();
		int index = 0;
		if(Constants.IC_MAIL_MAILSTATUS_UNREAD.equals(mailRecord.getReadFlag())){
			mailParam.setMailboxId(1L);
			mailParam.setId(null);
			//mailParam.setReceivers(receivers);
			mailParam.addOrderByFieldDesc("receiveTime2");
			mailList= mailService.queryAllUnRead(mailParam);
			if(mailList.size()==1){
				mail.setShowNext(false);
				mail.setShowPre(false);
			}else{
				for (int i=0;i<mailList.size();i++) {
					if(mailParam.getMrid().equals(mailList.get(i).getMrid())){
						index = i;
						break;
					}
				}
				if(index==0){
					mail.setShowPre(false);
				}else if(index==mailList.size()){
					mail.setShowNext(false);
				}
				mail.setIndex(index);
			}
			mail.setReturnURL("/ic/mail/manageUnread.action");
			model.addAttribute("mail", mail);
			if(!readOnly){
				mailService.setReadStatus(mail, mailParam.getMrid(),
						Constants.IC_MAIL_MAILSTATUS_READED.toString());
			}
			return "ic/mail/mailRead";
		}else{
			mailParam.setMailboxId(1L);
			mailParam.setId(null);
			MailRecord mailRecord1 = new MailRecord();
			mailRecord1.setFolderId(mailRecord.getFolderId());
			List<MailRecord> recordList = new ArrayList<MailRecord>();
			recordList.add(mailRecord1);
			mailParam.setReceivers(recordList);
			mailParam.addOrderByFieldDesc("receiveTime2");
			mailList= mailService.queryAllDetail(mailParam);
			if(mailList.size()==0){
				mail.setShowNext(false);
				mail.setShowPre(false);
			}else{
				for (int i=0;i<mailList.size();i++) {
					if(mailParam.getMrid().equals(mailList.get(i).getMrid())){
						index = i;
						break;
					}
				}
				if(index==1){
					mail.setShowPre(false);
				}else if(index==mailList.size()){
					mail.setShowNext(false);
				}
				mail.setIndex(index);
			}
			mail.setReturnURL("/ic/mail/manage.action/1");
			model.addAttribute("mail", mail);
			return "ic/mail/mailReadDetail";
		}
		
	}

	/**
	 * 方法描述：跳转至写邮件界面
	 * 
	 * @param mail
	 * @return
	 * @author zhanglg
	 * @version 2014年5月8日上午9:52:57
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "manageSend.action")
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageSend", operateDescribe = "发送邮件")
	public String manageSend(Mail mail, Model model, HttpServletRequest request)
			throws CustomException {
		model.addAttribute("mail", mail);
		model.addAttribute("mailboxList", this.initMailboxs(request));
		model.addAttribute("user", SystemSecurityUtils.getUser());
		Integer interval = (Integer) SettingUtils
				.getSetting(SettingUtils.EMAIL_SAVETIME);
		interval *= (60 * 1000);
		model.addAttribute("autoSaveInterval", interval > 60000 ? interval
				: 60000);
		return "ic/mail/newMail";
	}

	/**
	 * 方法描述：从草稿箱跳转至写邮件界面
	 * 
	 * @param mail
	 * @return
	 * @author zhanglg
	 * @version 2014年5月8日上午9:52:57
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "manageWrite.action")
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageWrite", operateDescribe = "写邮件")
	public String manageWrite(Mail mail, String mrid, Model model,
			HttpServletRequest request) throws Exception {
		String returnURL = mail.getReturnURL();
		mail = mailService.get(mail);
		List<Attach> list = mail.getAttachs();
		String files = "";
		if (list.size() > 0) {
			for (Attach attach : list) {
				files += attach.getId() + ",";
			}
		}
		if (files.length() > 0) {
			files = files.substring(0, files.length() - 1);
		}
		mailService.setReadStatus(mail, mrid,
				Constants.IC_MAIL_MAILSTATUS_READED.toString());
		mail.setReturnURL(returnURL);
		// 收件人
		StringBuffer to = new StringBuffer();
		// 抄送
		StringBuffer cc = new StringBuffer();
		// 密送
		StringBuffer bcc = new StringBuffer();
		// 群发单显
		StringBuffer ss = new StringBuffer();
		if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			for (MailRecord record : mail.getReceivers()) {

				if (Constants.IC_MAIL_RECEIVETYPE_TO.equals(record
						.getReceiveType())) {
					Contacts contacts = new Contacts();
					contacts.setMail(record.getReceiveMail());
					contacts.setCreateUser(SystemSecurityUtils.getUser()
							.getId());
					contacts = contactsService.get(contacts);
					if (contacts != null) {
						to.append("{receiveMail:\"")
								.append(record.getReceiveMail()).append("\",");
						to.append("contactsId:\"").append(contacts.getId())
								.append("\",");
						to.append("userName:\"").append(contacts.getUserName())
								.append("\"}");
						to.append(",");
					} else {
						to.append("{receiveMail:\"")
								.append(record.getReceiveMail()).append("\"}");
						to.append(",");
					}
				}
				if (Constants.IC_MAIL_RECEIVETYPE_CC.equals(record
						.getReceiveType())) {
					Contacts contacts = new Contacts();
					contacts.setCreateUser(SystemSecurityUtils.getUser()
							.getId());
					contacts.setMail(record.getReceiveMail());
					contacts = contactsService.get(contacts);
					if (contacts != null) {
						cc.append("{receiveMail:\"")
								.append(record.getReceiveMail()).append("\",");
						cc.append("contactsId:\"").append(contacts.getId())
								.append("\",");
						cc.append("userName:\"").append(contacts.getUserName())
								.append("\"}");
						cc.append(",");
					} else {
						cc.append("{receiveMail:\"")
								.append(record.getReceiveMail()).append("\"}");
						cc.append(",");
					}
				}
				if (Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
						.getReceiveType())) {
					Contacts contacts = new Contacts();
					contacts.setCreateUser(SystemSecurityUtils.getUser()
							.getId());
					contacts.setMail(record.getReceiveMail());
					contacts = contactsService.get(contacts);
					if (contacts != null) {
						bcc.append("{receiveMail:\"")
								.append(record.getReceiveMail()).append("\",");
						bcc.append("contactsId:\"").append(contacts.getId())
								.append("\",");
						bcc.append("userName:\"")
								.append(contacts.getUserName()).append("\"}");
						bcc.append(",");
					} else {
						bcc.append("{receiveMail:\"")
								.append(record.getReceiveMail()).append("\"}");
						bcc.append(",");
					}
				}
				if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
						.getReceiveType())) {
					Contacts contacts = new Contacts();
					contacts.setCreateUser(SystemSecurityUtils.getUser()
							.getId());
					contacts.setMail(record.getReceiveMail());
					contacts = contactsService.get(contacts);
					if (contacts != null) {
						ss.append("{receiveMail:\"")
								.append(record.getReceiveMail()).append("\",");
						ss.append("contactsId:\"").append(contacts.getId())
								.append("\",");
						ss.append("userName:\"").append(contacts.getUserName())
								.append("\"}");
						ss.append(",");
					} else {
						ss.append("{receiveMail:\"")
								.append(record.getReceiveMail()).append("\"}");
						ss.append(",");
					}
				}

			}
			if (to.length() > 0 && to.charAt(to.length() - 1) == ',') {
				to.deleteCharAt(to.length() - 1);
				to.insert(0, "[");
				to.append("]");
				mail.setTo(to.toString());
			}
			if (cc.length() > 0 && cc.charAt(cc.length() - 1) == ',') {
				cc.deleteCharAt(cc.length() - 1);
				cc.insert(0, "[");
				cc.append("]");
				mail.setCc(cc.toString());
			}
			if (bcc.length() > 0 && bcc.charAt(bcc.length() - 1) == ',') {
				bcc.deleteCharAt(bcc.length() - 1);
				bcc.insert(0, "[");
				bcc.append("]");
				mail.setBcc(bcc.toString());
			}
			if (ss.length() > 0 && ss.charAt(ss.length() - 1) == ',') {
				ss.deleteCharAt(ss.length() - 1);
				ss.insert(0, "[");
				ss.append("]");
				mail.setShowSingle(ss.toString());
			}
		}
		model.addAttribute("mail", mail);
		model.addAttribute("mailboxList", this.initMailboxs(request));
		model.addAttribute("user", SystemSecurityUtils.getUser());
		model.addAttribute("files", files);
		model.addAttribute("folderId", mail.getReceivers().get(0).getFolderId());
		// 回复/全部回复/转发标识，草稿箱用它做附件判断
		model.addAttribute("isNew", "2");
		Integer interval = (Integer) SettingUtils
				.getSetting(SettingUtils.EMAIL_SAVETIME);
		interval *= (60 * 1000);
		model.addAttribute("autoSaveInterval", interval > 60000 ? interval
				: 60000);
		return "ic/mail/newMail";
	}

	/**
	 * 方法描述：跳转至回复邮件界面
	 * 
	 * @param mail
	 * @return
	 * @author zhanglg
	 * @version 2014年5月8日上午9:52:57
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "manageReplyMail.action")
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageReplyMail", operateDescribe = "回复邮件")
	public String manageReplyMail(Mail mail, String folderId, Model model,
			HttpServletRequest request) throws CustomException {
		if (mail.getId() == null) {
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_046"));
		}
		String returnURL = mail.getReturnURL();
		String mailContent = mail.getMailContent() == null ? "" : mail
				.getMailContent();
		// mailContent +=
		// "<br/><br/>------------------ 原始邮件内容 ------------------<br/><br/>";
		String replySign = "";
		if (mail.getMailboxId().intValue() == 1) {
			Mailbox box = new Mailbox();
			Mailbox mailBox = this.querySign(box, request);
			if (mailBox != null && mailBox.getReplySign() != null) {
				replySign = "<!--signature start --><br/><br/><br/><br/><br/>--------<br />"
						+ mailBox.getReplySign().getSignContent()
						+ "<!--signature end-->";
			}
		} else {
			Mailbox box = new Mailbox();
			box.setId(mail.getMailboxId());
			Mailbox mailBox = this.getSign(box, request);
			if (mailBox != null && mailBox.getReplySign() != null) {
				replySign = "<!--signature start --><br/><br/><br/><br/><br/>--------<br />"
						+ mailBox.getReplySign().getSignContent()
						+ "<!--signature end-->";
			}
		}
		mail.setMailContent(null);
		mail = mailService.getReplyInfo(mail, folderId);
		model.addAttribute("copyId", mail.getId());
		mail.setReturnURL(returnURL);
		mail.setMailContent(mailContent + replySign + mail.getMailContent());
		// 因加密邮件在回复之前已经过客户端解密，所以这里可以取出邮件原文。下面代码废除 Update by zhanglg 2014 07 24
		// if (Constants.IC_MAIL_ENCRYPTION_YES.equals(mail.getEncryption())) {
		// mail.setMailContent(mailContent
		// + "这是一封由<b> "
		// + (UserUtils.getUser(mail.getCreateUser()))
		// .getDisplayName() + " </b>发出的加密邮件，需要解密才能阅读。");
		// } else {
		// mail.setMailContent(mailContent + mail.getMailContent());
		// }
		model.addAttribute("mail", mail);
		model.addAttribute("mailReplyId", mail.getId());
		mail.setId(null);
		model.addAttribute("user", SystemSecurityUtils.getUser());
		model.addAttribute("open", true);
		Integer interval = (Integer) SettingUtils
				.getSetting(SettingUtils.EMAIL_SAVETIME);
		interval *= (60 * 1000);
		model.addAttribute("autoSaveInterval", interval > 60000 ? interval
				: 60000);
		model.addAttribute("isNew", "1");
		model.addAttribute("copy", "true");
		return "ic/mail/replyMail";
	}

	/**
	 * 方法描述：跳转至转发邮件界面
	 * 
	 * @param mail
	 *            邮件实体
	 * @param ids
	 *            Id列表
	 * @return
	 * @author zhanglg
	 * @version 2014年5月8日上午10:01:35
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "manageForwarding.action")
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageForwarding", operateDescribe = "转发邮件")
	public String manageForwarding(Mail mail, String ids, Model model,
			HttpServletRequest request) throws CustomException {
		if (mail.getId() == null && (ids == null || ids.length() == 0)) {
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_046"));
		}
		String returnURL = mail.getReturnURL();
		mail = mailService.getForwardingInfo(mail, ids);

		mail.setReturnURL(returnURL);
		String replySign = "";
		if (mail.getMailboxId().intValue() == 1) {
			Mailbox box = new Mailbox();
			Mailbox mailBox = this.querySign(box, request);
			if (mailBox != null && mailBox.getReplySign() != null) {
				replySign = "<!--signature start --><br/><br/><br/><br/><br/>--------<br />"
						+ mailBox.getReplySign().getSignContent()
						+ "<!--signature end-->";
			}
		} else {
			Mailbox box = new Mailbox();
			box.setId(mail.getMailboxId());
			Mailbox mailBox = this.getSign(box, request);
			if (mailBox != null && mailBox.getReplySign() != null) {
				replySign = "<!--signature start --><br/><br/><br/><br/><br/>--------<br />"
						+ mailBox.getReplySign().getSignContent()
						+ "<!--signature end-->";
			}
		}
		mail.setMailContent(replySign + mail.getMailContent());
		model.addAttribute("user", SystemSecurityUtils.getUser());
		model.addAttribute("mail", mail);

		model.addAttribute("open", true);
		Integer interval = (Integer) SettingUtils
				.getSetting(SettingUtils.EMAIL_SAVETIME);
		interval *= (60 * 1000);
		model.addAttribute("autoSaveInterval", interval > 60000 ? interval
				: 60000);
		model.addAttribute("isNew", "1");
		model.addAttribute("copy", "true");
		model.addAttribute("copyId", mail.getId());
		return "ic/mail/transMail";
	}

	/**
	 * 方法描述：邮件预览
	 * 
	 * @param mail
	 * @param model
	 * @return
	 * @author zhanglg
	 * @version 2014年5月14日下午3:07:21
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "managePreview.action")
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "managePreview", operateDescribe = "邮件预览")
	public String managePreview(Mail mail, Model model,
			HttpServletRequest request) throws CustomException {
		mail.setSenderTime(new Date());
		mail.setSenderUserName(SystemSecurityUtils.getUser().getDisplayName());
		if ("".equals(mail.getShowSingle())) {
			mail.setShowSingle(null);
		}
		if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			String innerTo = mail.getInnerTo();
			if (innerTo != null && innerTo.length() > 0) {
				StringBuffer sbTo = new StringBuffer();
				for (String to : innerTo.split(",")) {
					sbTo.append(
							UserUtils.getUser(new Long(to)).getDisplayName())
							.append(",");
				}
				if (sbTo.charAt(sbTo.length() - 1) == ',') {
					sbTo.deleteCharAt(sbTo.length() - 1);
				}
				mail.setTo(sbTo.toString());
			}

			String innerCc = mail.getInnerCc();
			if (innerCc != null && innerCc.length() > 0) {
				StringBuffer sbCC = new StringBuffer();
				for (String to : innerCc.split(",")) {
					sbCC.append(
							UserUtils.getUser(new Long(to)).getDisplayName())
							.append(",");
				}
				if (sbCC.charAt(sbCC.length() - 1) == ',') {
					sbCC.deleteCharAt(sbCC.length() - 1);
				}
				mail.setCc(sbCC.toString());
			}

			String innerBcc = mail.getInnerBcc();
			if (innerBcc != null && innerBcc.length() > 0) {
				StringBuffer sbBcc = new StringBuffer();
				for (String to : innerBcc.split(",")) {
					sbBcc.append(
							UserUtils.getUser(new Long(to)).getDisplayName())
							.append(",");
				}
				if (sbBcc.charAt(sbBcc.length() - 1) == ',') {
					sbBcc.deleteCharAt(sbBcc.length() - 1);
				}
				mail.setBcc(sbBcc.toString());
			}

			String innerSs = mail.getInnerSs();
			if (innerSs != null && innerSs.length() > 0) {
				StringBuffer sbSs = new StringBuffer();
				for (String to : innerSs.split(",")) {
					sbSs.append(
							UserUtils.getUser(new Long(to)).getDisplayName())
							.append(",");
				}
				if (sbSs.charAt(sbSs.length() - 1) == ',') {
					sbSs.deleteCharAt(sbSs.length() - 1);
				}
				mail.setShowSingle(sbSs.toString());
			}
		}

		mail.setAttachs(mailService.getUnsaveAttachs(mail));
		model.addAttribute("mail", mail);
		return "ic/mail/mailpreview";
	}

	/**
	 * 方法描述：往来邮件预览
	 * 
	 * @param mail
	 * @param model
	 * @return
	 * @author zhanglg
	 * @version 2014年5月14日下午3:07:21
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "manageView.action")
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageView", operateDescribe = "往来邮件预览")
	public String manageView(Mail mailParam, String password, Model model,
			HttpServletRequest request) throws Exception {
		Mail mail = new Mail();
		mail.setId(mailParam.getId());
		mail = mailService.get(mail);
		StringBuffer to = new StringBuffer();
		StringBuffer cc = new StringBuffer();
		StringBuffer bcc = new StringBuffer();
		StringBuffer ss = new StringBuffer();
		// 判断是否为内部邮箱
		boolean isInner = Constants.IC_MAIL_MAILBOX_INNER.equals(mail
				.getMailboxId());
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
			if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(type)
					&& (userId.equals(receiver.getReceiveUserId()) || (mailbox != null && mailbox
							.getAddress().equals(receiver.getReceiveMail())))) {
				isSender = true;
				break;
			}
		}
		for (int i = 0; i < receivers.size(); i++) {
			MailRecord record = receivers.get(i);
			if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
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
		// 去掉结尾逗号
		if (to.length() > 1) {
			if (to.charAt(to.length() - 1) == ',') {
				to.deleteCharAt(to.length() - 1);
			}
		}
		if (cc.length() > 1) {
			if (cc.charAt(cc.length() - 1) == ',') {
				cc.deleteCharAt(cc.length() - 1);
			}
		}
		if (bcc.length() > 1) {
			if (bcc.charAt(bcc.length() - 1) == ',') {
				bcc.deleteCharAt(bcc.length() - 1);
			}
		}
		if (ss.length() > 1) {
			if (ss.charAt(ss.length() - 1) == ',') {
				ss.deleteCharAt(ss.length() - 1);
			}
		}

		mail.setTo(to.toString());
		mail.setCc(cc.toString());
		mail.setBcc(bcc.toString());
		mail.setShowSingle(ss.toString());

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
		if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			mailbox = new Mailbox();
			mailbox.setId(mail.getMailboxId());
			model.addAttribute("mailboxAddress", mailService
					.getMailbox(mailbox).getAddress());
		}

		model.addAttribute("user", SystemSecurityUtils.getUser());
		model.addAttribute("mail", mail);
		model.addAttribute("isFromTo", true);
		return "ic/mail/mailview";
	}

	/**
	 * 方法描述：发送邮件
	 * 
	 * @param mail
	 * @param ids
	 * @return
	 * @author zhanglg
	 * @version 2014年5月8日上午10:07:18
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "sendMail.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "sendMail", operateDescribe = "发邮件")
	public Map<String, Object> sendMail(@Valid Mail mail, BindingResult result,
			HttpServletRequest request) throws CustomException {
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			mail.setTo(mail.getInnerTo());
			mail.setCc(mail.getInnerCc());
			mail.setBcc(mail.getInnerBcc());
			// 内部邮箱设置群发单显人员
			mail.setShowSingle(mail.getInnerSs());

			if (mail.getSenderUserId() == null) {
				mail.setSenderUserId(SystemSecurityUtils.getUser().getId());
			}
		}
		mailService.sendMail(mail, request);

		resultMap.put("success", "true");
		return resultMap;
	}

	/**
	 * 方法描述：转发邮件
	 * 
	 * @param mail
	 * @param ids
	 * @return
	 * @author zhanglg
	 * @version 2014年5月8日上午10:07:18
	 * @throws Exception
	 * @see
	 */
	@RequestMapping(value = "transMail.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "transMail", operateDescribe = "发邮件")
	public Map<String, Object> transMail(@Valid Mail mail,
			BindingResult result, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			mail.setTo(mail.getInnerTo());
			mail.setCc(mail.getInnerCc());
			mail.setBcc(mail.getInnerBcc());
			// 内部邮箱设置群发单显人员
			mail.setShowSingle(mail.getInnerSs());

			if (mail.getSenderUserId() == null) {
				mail.setSenderUserId(SystemSecurityUtils.getUser().getId());
			}
		}
		mailService.transMail(mail, request);

		resultMap.put("success", "true");
		return resultMap;
	}

	/**
	 * 方法描述：快速回复收件人
	 * 
	 * @param mail
	 * @param result
	 * @param request
	 * @return
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月19日上午11:03:09
	 * @see
	 */
	@RequestMapping(value = "fastReply.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "fastReply", operateDescribe = "快速回复")
	public Map<String, Object> fastReply(@Valid Mail mail, String folderId,
			BindingResult result, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String mailContent = mail.getMailContent();
		Long id = mail.getId();
		mail.setReply(0);
		mail.setMailContent(null);
		try {
			mail = mailService.getReplyInfo(mail, folderId);
		} catch (CustomException e) {
			e.printStackTrace();
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_IC_051"));
			return resultMap;
		}
		mail.setMailContent(mailContent + "<br/><br/><br/>"
				+ mail.getMailContent());
		// 因getReplyInfo时为了回显数据，邮件的收件人进行了人员选择组件的编码，
		// 这里必须对这个字符串重新解析
		String to = mail.getTo();
		// 表达式对象
		Pattern p = Pattern
				.compile("(?<=\\[\\{receiveMail:\\\").*(?=\\\"\\}\\])");

		// 创建 Matcher 对象
		Matcher m = p.matcher(to);

		// 是否找到匹配
		boolean found = m.find();

		if (found) {
			// 得到正确的邮箱地址
			String foundstring = m.group();
			mail.setTo(foundstring);
		}
		mail.setId(null);
		try {
			mailService.sendMail(mail, request);
		} catch (CustomException e) {
			e.printStackTrace();
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_IC_051"));
			return resultMap;
		}

		resultMap.put("success", "true");
		return resultMap;
	}

	/**
	 * 方法描述：收邮件
	 * 
	 * @param mail
	 * @return
	 * @author zhanglg
	 * @version 2014年5月9日下午1:16:09
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "receive.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "receive", operateDescribe = "收邮件")
	public Integer receive(Mail mail, HttpServletRequest request)
			throws CustomException {

		return mailService.receive(mail.getMailboxId());
	}

	/**
	 * 方法描述：邮件移动到...方法
	 * 
	 * @param mail
	 *            实体类
	 * @param ids
	 *            id列表
	 * @param folder
	 *            目标文件夹id
	 * @return
	 * @author zhanglg
	 * @version 2014年5月7日下午3:53:44
	 * @see
	 */
	@RequestMapping(value = "moveToByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "moveToByIds", operateDescribe = "移动邮件")
	public boolean moveToByIds(Mail mail, String ids, String mrids,
			Long fromFolder, Long toFolder, HttpServletRequest request) {
		mail.setPrimaryKeys(ids.indexOf(",") > 0 ? ids.split(",")
				: new String[] { ids });
		try {
			mailService.moveTo(mail, mrids, fromFolder, toFolder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 方法描述：设置邮件已读、未读
	 * 
	 * @param mail
	 *            邮件实体类
	 * @param ids
	 *            Id列表
	 * @return
	 * @author zhanglg
	 * @version 2014年5月7日下午4:39:15
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "setReadStatus.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "setReadStatus", operateDescribe = "设置已读未读")
	public boolean setReadStatus(Mail mail, String mrids, String status,
			String ids, HttpServletRequest request) throws Exception {
		if (status == null)
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_047"));
		mail.setPrimaryKeys(ids.split(","));

		mailService.setReadStatus(mail, mrids, status);
		return true;
	}

	/**
	 * 方法描述：设置邮件星标
	 * 
	 * @param mail
	 *            邮件实体类
	 * @param ids
	 *            Id列表
	 * @return
	 * @author zhanglg
	 * @version 2014年5月8日上午9:26:35
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "setStarByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "setStarByIds", operateDescribe = "设置星标")
	public boolean setStarByIds(Mail mail, String ids, String mrids,
			Long folder, HttpServletRequest request) throws CustomException {
		mail.setPrimaryKeys(ids.split(","));
		mailService.setStarFlag(mail, mrids, folder);
		return true;
	}

	/**
	 * 方法描述：初始化文件夹列表
	 * 
	 * @return 邮件文件夹对象
	 * @author zhanglg
	 * @version 2014年5月8日下午3:07:07
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "initMailFolders.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "initMailFolders", operateDescribe = "初始化文件夹列表")
	public List<MailFolder> initMailFolders(HttpServletRequest request)
			throws CustomException {
		return mailService.queryAllMailFolder(new MailFolder());
	}

	/**
	 * 方法描述：初始化邮箱列表
	 * 
	 * @return 邮箱列表对象
	 * @author zhanglg
	 * @version 2014年5月8日下午3:07:07
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "initMailboxs.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "initMailboxs", operateDescribe = "初始化邮箱列表")
	public List<Mailbox> initMailboxs(HttpServletRequest request)
			throws CustomException {
		return (List<Mailbox>) mailService.getMailboxList(new Mailbox());
	}

	/**
	 * @description 保存邮件文件夹方法
	 * @param MailFolder
	 *            folder 实体类
	 * @param BindingResult
	 *            result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author zhanglg
	 * @version 2014-05-08
	 */
	@RequestMapping(value = "saveFolder.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "saveFolder", operateDescribe = "保存自定义文件夹")
	public Map<String, Object> saveFolder(MailFolder folder,
			BindingResult result, HttpServletRequest request) throws Exception {
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request);
		if (resultMap.size() > 0) {
			return resultMap;
		}

		// 判断文件夹名是否存在
		MailFolder m = new MailFolder();
		m.setFolderName(folder.getFolderName());
		m.setCreateUser(SystemSecurityUtils.getUser().getId());
		m = mailService.getMailFolder(m);
		if (m != null) {
			if (m.getId().longValue() != folder.getId().longValue()) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
						MessageUtils.getMessage("JC_OA_IC_060"));
				resultMap.put(GlobalContext.SESSION_TOKEN,
						super.getToken(request));
				return resultMap;
			}
		}
		if (folder.getId() != 0) {
			// 保存文件夹
			if (mailService.updateMailFolder(folder) == 1) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
						MessageUtils.getMessage("JC_SYS_001"));
				resultMap.put(GlobalContext.SESSION_TOKEN,
						super.getToken(request));
			} else {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
						MessageUtils.getMessage("JC_SYS_002"));
			}
		} else {
			// 保存文件夹
			if (mailService.saveMailFolder(folder) == 1) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
						MessageUtils.getMessage("JC_SYS_001"));
				resultMap.put(GlobalContext.SESSION_TOKEN,
						super.getToken(request));
			} else {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
						MessageUtils.getMessage("JC_SYS_002"));
			}
		}
		return resultMap;
	}

	/**
	 * @description 上一封、下一封
	 * @param
	 * @return Mail 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-05-17
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "navigate.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "navigate", operateDescribe = "邮件导航")
	public Mail navigate(Mail mail, String folderId, HttpServletRequest request)
			throws Exception {
		if (mail.getSearchReceiveTimeEnd() != null) {
			mail.setSearchReceiveTimeEnd(DateUtils.fillTime(mail
					.getSearchReceiveTimeEnd()));
		}
		if ("".equals(mail.getSenderMail())) {
			mail.setSenderMail(null);
		}
		if ("".equals(mail.getMailTitle())) {
			mail.setMailTitle(null);
		}
		if ("true".equals(mail.getIsAllMail())) {
			mail.setMailboxId(null);
		}
		if("".equals(mail.getMailEasyTitle())){
			mail.setMailEasyTitle(null);
		}
		String returnURL = mail.getReturnURL();
		PageManager page = new PageManager();
		page.setPageRows(1);
		String readflag = "";
		if (mail.getReceivers() != null) {
			if (mail.getReceivers().get(0).getReadFlag() != null) {
				readflag = mail.getReceivers().get(0).getReadFlag().toString();
			}
		}
		if ("0".equals(readflag)) {
			if (mail.getForward() > 0) {
				mail.getIndex();
			} else {
				mail.setIndex(mail.getIndex() + mail.getForward());
			}
		} else {
			// 设置页数如果mail.getForward()>0向下翻页，否则像上翻页
			if (mail.getForward() == 1) {
				mail.setIndex(mail.getIndex() + 1);
			} else if (mail.getForward() == -1) {
				mail.setIndex(mail.getIndex() + mail.getForward());
			} else if (mail.getForward() == 2) {
				mail.setIndex(mail.getIndex());
			} else if (mail.getForward() == -2) {
				mail.setIndex(mail.getIndex() - 1);
			}
		}
		// 如果mailTitle为“”将值设为null不做查询
		if ("".equals(mail.getMailTitle())) {
			mail.setMailTitle(null);
		}
		page.setPage(mail.getIndex());
		List<MailRecord> receivers = mail.getReceivers();
		if (Constants.IC_MAIL_MAILFOLDER_DRAFT.equals((receivers.get(0))
				.getFolderId())
				|| Constants.IC_MAIL_MAILFOLDER_SENDED
						.equals((receivers.get(0)).getFolderId())) {
			mail.addOrderByFieldDesc("t.SENDER_TIME");
		} else {
			mail.addOrderByFieldDesc("t.RECEIVE_TIME2");
		}
		if (mail.getMailboxId() == null) {
			if (Constants.IC_MAIL_MAILFOLDER_DRAFT.equals((receivers.get(0))
					.getFolderId())
					|| Constants.IC_MAIL_MAILFOLDER_SENDED.equals((receivers
							.get(0)).getFolderId())) {
				mail.addOrderByFieldDesc("t.SENDER_TIME");
			} else {
				mail.addOrderByFieldDesc("t.RECEIVE_TIME2");
			}
			Mailbox mailbox = new Mailbox();
			mailbox.setCreateUser(SystemSecurityUtils.getUser().getId());
			List<Mailbox> mailboxlist = mailService.getMailboxList(mailbox);
			Mailbox mbox = new Mailbox();
			mbox.setId(1L);
			mailboxlist.add(mbox);
			mail.setMailbox(mailboxlist);
		}
		PageManager page_ = mailService.query(mail, page);

		List<Mail> mailList = (List<Mail>) page_.getData();
		if (mailList != null && mailList.size() > 0) {
			Long recordId = mailList.get(0).getReceivers().get(0).getId();
			Mail condMail = new Mail();
			condMail.setId(mailList.get(0).getId());
			mail = mailService.get(condMail);

			Mailbox mailbox = new Mailbox();
			mailbox.setId(mail.getMailboxId());
			mailbox = mailService.getMailbox(mailbox);

			// 新写的收件人、抄送人显示逻辑，把收件人、抄送人、群发单显人，分别放在Mail的to,cc,showSingle字段
			// Updated By 张立刚
			receivers = mail.getReceivers();
			Long userId = SystemSecurityUtils.getUser().getId();
			boolean isSender = false;
			for (int i = 0; i < receivers.size(); i++) {
				MailRecord receiver = receivers.get(i);
				Integer type = receiver.getReceiveType();
				// 如果发件人时当前人，设置isSender
				if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(type)
						&& (userId.equals(receiver.getReceiveUserId()) || mailbox
								.getAddress().equals(receiver.getReceiveMail()))) {
					isSender = true;
					break;
				}
			}
			StringBuffer to = new StringBuffer();
			StringBuffer cc = new StringBuffer();
			StringBuffer ss = new StringBuffer();
			StringBuffer bcc = new StringBuffer();

			// 显示人员状态声明
			StringBuffer newTo = new StringBuffer();
			StringBuffer newCc = new StringBuffer();
			StringBuffer newSs = new StringBuffer();
			StringBuffer newBcc = new StringBuffer();

			for (int i = 0; i < receivers.size(); i++) {
				MailRecord record = receivers.get(i);
				if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
					// 如果该邮件所有收发记录存在id值和当前页记录的子表id相等记录，则将该记录存方法mail的receiver属性中，用于传值
					if (record.getId().longValue() == recordId.longValue()) {
						mail.setReceiver(record);
					}
					// 可以看到群发单显：群发单显收件人可以看到自己、发件人可以看到所有，
					if ((Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
							.getReceiveType()) && record.getReceiveUserId()
							.equals(userId))
							|| isSender
							&& (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE
									.equals(record.getReceiveType()))) {
						if (record.getReadDate() != null) {
							newSs.append(
									"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
											+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "("
											+ DateFormatUtils.format(
													record.getReadDate(),
													"yyyy-MM-dd HH:mm:ss")
											+ ")</span></a>").append(",");
							ss.append(record.getReceiveUserName()).append(",");
						} else {
							newSs.append(
									"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "</a>").append(",");
							ss.append(record.getReceiveUserName()).append(",");
						}
					} else if (Constants.IC_MAIL_RECEIVETYPE_TO.equals(record
							.getReceiveType())) {
						if (record.getReadDate() != null) {
							newTo.append(
									"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
											+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "("
											+ DateFormatUtils.format(
													record.getReadDate(),
													"yyyy-MM-dd HH:mm:ss")
											+ ")</span></a>").append(",");
							to.append(record.getReceiveUserName()).append(",");
						} else {
							newTo.append(
									"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "</a>").append(",");
							to.append(record.getReceiveUserName()).append(",");
						}
					} else if (Constants.IC_MAIL_RECEIVETYPE_CC.equals(record
							.getReceiveType())) {
						if (record.getReadDate() != null) {
							newCc.append(
									"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
											+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "("
											+ DateFormatUtils.format(
													record.getReadDate(),
													"yyyy-MM-dd HH:mm:ss")
											+ ")</span></a>").append(",");
							cc.append(record.getReceiveUserName()).append(",");
						} else {
							newCc.append(
									"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "</a>").append(",");
							cc.append(record.getReceiveUserName()).append(",");
						}
					}
					// 可以看到密送：收件人可以看到自己、发件人可以看到所有
					else if ((Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
							.getReceiveType()) && record.getReceiveUserId()
							.equals(userId))
							|| isSender
							&& Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
									.getReceiveType())) {
						if (record.getReadDate() != null) {
							newBcc.append(
									"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
											+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "("
											+ DateFormatUtils.format(
													record.getReadDate(),
													"yyyy-MM-dd HH:mm:ss")
											+ ")</span></a>").append(",");
							bcc.append(record.getReceiveUserName()).append(",");
						} else {
							newBcc.append(
									"<a style = \"cursor :pointer\" class=\"email-receivers\"  onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "</a>").append(",");
							bcc.append(record.getReceiveUserName()).append(",");
						}
					}
				} else {
					// 如果该邮件所有收发记录存在id值和当前页记录的子表id相等记录，则将该记录存方法mail的receiver属性中，用于传值
					if (record.getId().longValue() == recordId.longValue()) {
						mail.setReceiver(record);
					}
					// 可以看到群发单显：群发单显收件人可以看到自己、发件人可以看到所有，
					if ((Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
							.getReceiveType()) && record.getReceiveMail()
							.equals(mailbox.getAddress()))
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

			if (newTo.length() > 0 && newTo.charAt(newTo.length() - 1) == ',') {
				newTo.deleteCharAt(newTo.length() - 1);
			}
			if (newSs.length() > 0 && newSs.charAt(newSs.length() - 1) == ',') {
				newSs.deleteCharAt(newSs.length() - 1);
			}
			if (newCc.length() > 0 && newCc.charAt(newCc.length() - 1) == ',') {
				newCc.deleteCharAt(newCc.length() - 1);
			}
			if (newBcc.length() > 0
					&& newBcc.charAt(newBcc.length() - 1) == ',') {
				newBcc.deleteCharAt(newBcc.length() - 1);
			}

			mail.setTo(to.toString());
			mail.setCc(cc.toString());
			mail.setShowSingle(ss.toString());
			mail.setBcc(bcc.toString());

			// 回显收件人状态使用
			mail.setNewTo(newTo.toString());
			mail.setNewCc(newCc.toString());
			mail.setNewBcc(newBcc.toString());
			mail.setNewSs(newSs.toString());

			// 不再用下面这种逻辑
			// 移除发件人记录和非本人的密送记录
			// for (int i = receivers.size() - 1; i >= 0; i--) {
			// MailRecord record = receivers.get(i);
			// // 内部邮件
			// if (mail.getMailboxId().equals(Constants.IC_MAIL_MAILBOX_INNER))
			// {
			//
			// // 如果该邮件所有收发记录存在id值和当前页记录的子表id相等记录，则将该记录存方法mail的receiver属性中，用于传值
			// if (record.getId().longValue() == recordId.longValue()) {
			// mail.setReceiver(record);
			// }
			// // 如果是发送记录
			// if (record.getReceiveType().equals(
			// Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
			// receivers.remove(i);
			// // 如果是密送人
			// } else if (record.getReceiveType().equals(
			// Constants.IC_MAIL_RECEIVETYPE_BCC)) {
			// receivers.remove(i);
			// }
			//
			// } else {// 外部
			// // 如果该邮件所有收发记录存在id值和当前页记录的子表id相等记录，则将该记录存方法mail的receiver属性中，用于传值
			// if (record.getId().longValue() == recordId.longValue()) {
			// mail.setReceiver(record);
			// }
			// if (record.getReceiveType().equals(
			// Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
			// receivers.remove(i);
			// } else if (record.getReceiveType().equals(
			// Constants.IC_MAIL_RECEIVETYPE_BCC)) {
			// boolean remove = true;
			// for (Mailbox box : boxList) {
			// if (box.getAddress()
			// .equals(record.getReceiveMail())) {
			// remove = false;
			// }
			// }
			// if (remove) {
			// receivers.remove(i);
			// }
			// }
			// }
			// }
		}
		if (Constants.IC_MAIL_ENCRYPTION_YES.equals(mail.getEncryption())) {
			mail.setMailContent(null);
		}

		mail.setReturnURL(returnURL);
		if (page_.getTotalPages() == page_.getPage()) {
			mail.setShowNext(false);
		}
		if (page_.getPage() - 1 == 0) {
			mail.setShowPre(false);
		}
		mail.setIndex(page_.getPage() - 1);
		// 如果下一封记录不为空，并且未读设置该条记录已读
		if (mail.getReceiver() != null) {
			if (!"1".equals(mail.getReceiver().getReadFlag().toString())) {
				// 当点击下一封是设置该邮件为已读
				mailService.setReadStatus(mail, mail.getReceiver().getId()
						.toString(),
						Constants.IC_MAIL_MAILSTATUS_READED.toString());
			}
		}
		mail.setWrongPassword(true);
		return mail;
	}

	/**
	 * @description 彻底删除方法，
	 * @param Mail
	 *            mail 实体类 mailbox属于不能空
	 * @param String
	 *            ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version 2014-04-17
	 */
	@RequestMapping(value = "deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "deleteByIds", operateDescribe = "删除邮件")
	public Integer deleteByIds(Mail mail, String ids, HttpServletRequest request)
			throws Exception {
		mail.setPrimaryKeys(ids.split(","));
		MailRecord record = new MailRecord();
		record.setPrimaryKeys(mail.getPrimaryKeys());
		return mailRecordService.deleteByMailIdsLogic(record);
	}

	/**
	 * @description 保存方法
	 * @param Mail
	 *            mail 实体类
	 * @param BindingResult
	 *            result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version 2014-04-17
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "save", operateDescribe = "保存草稿")
	public Map<String, Object> save(Mail mail, BindingResult result,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		MailRecord record = new MailRecord();
		record.setMailId(mail.getId());
		if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			record.setReceiveUserId(SystemSecurityUtils.getUser().getId());
		} else {
			record.setReceiveMail(mail.getSenderMail());
		}
		record.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_INNERSENDER);
		record.setFolderId(Constants.IC_MAIL_MAILFOLDER_DRAFT);
		record.setReadFlag(Constants.IC_MAIL_MAILSTATUS_READED);
		record.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
		if (mail.getReceivers() == null) {
			mail.setReceivers(new ArrayList<MailRecord>());
		}
		mail.getReceivers().add(record);

		if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			mail.setTo(mail.getInnerTo());
			mail.setCc(mail.getInnerCc());
			mail.setBcc(mail.getInnerBcc());
			// 内部邮箱设置群发单显人员
			mail.setShowSingle(mail.getInnerSs());
			if (mail.getSenderUserId() == null) {
				mail.setSenderUserId(SystemSecurityUtils.getUser().getId());
			}
		}
		mailService.saveDraft(mail);
		resultMap.put("success", "true");
		resultMap.put("id", mail.getId());

		return resultMap;
	}

	/**
	 * @description 修改方法 暂时不用
	 * @param Mail
	 *            mail 实体类
	 * @return Integer 更新的数目
	 * @author
	 * @version 2014-04-17
	 */
	@RequestMapping(value = "update.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "update", operateDescribe = "更新草稿")
	public Integer update(Mail mail, HttpServletRequest request)
			throws Exception {
		Integer flag = mailService.update(mail);
		return flag;
	}

	/**
	 * @description 获取单条记录方法 暂时不用
	 * @param Mail
	 *            mail 实体类
	 * @return Mail 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-04-17
	 */
	@RequestMapping(value = "get.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "get", operateDescribe = "读取邮件")
	public Mail get(Mail mail, HttpServletRequest request) throws Exception {
		mail = mailService.get(mail);

		Mailbox mailbox = new Mailbox();
		mailbox.setCreateUser(SystemSecurityUtils.getUser().getId());
		List<Mailbox> boxList = mailService.getMailboxList(mailbox);

		// 移除发件人记录和非本人的密送记录
		List<MailRecord> receivers = mail.getReceivers();
		for (int i = receivers.size() - 1; i >= 0; i--) {
			MailRecord record = receivers.get(i);
			// 内部邮件
			if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
				// 发送人不是当前用户
				if (!mail.getSenderUserId().equals(
						SystemSecurityUtils.getUser().getId())) {
					// 如果是群发单显，并且接收人不是当前用户remove，接收人只显示自己
					if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
							.getReceiveType())
							&& !record.getReceiveUserId().equals(
									SystemSecurityUtils.getUser().getId())) {
						receivers.remove(i);
						continue;
					}
				}
				if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(record
						.getReceiveType())) {
					receivers.remove(i);

				} else if (record.getReceiveUserId() != null
						&& record.getReceiveUserId().equals(
								SystemSecurityUtils.getUser().getId())) {
					mail.setReceiver(record);
				} else if (Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
						.getReceiveType())) {
					receivers.remove(i);
				}
			} else {// 外部
				if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(record
						.getReceiveType())) {
					receivers.remove(i);

				} else if (Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
						.getReceiveType())) {
					boolean remove = true;
					for (Mailbox box : boxList) {
						if (box.getAddress().equals(record.getReceiveMail())) {
							remove = false;
						}
					}
					if (remove) {
						receivers.remove(i);
					}
				}
			}
		}
		return mailService.get(mail);
	}

	/**
	 * 方法描述：获取收件人
	 * 
	 * @param mail
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version 2014年7月15日上午10:08:20
	 * @see
	 */
	@RequestMapping(value = "getReceiveUser.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "getReceiveUser", operateDescribe = "获取收件人")
	public Mail getReceiveUser(Mail mail, String folderId,
			HttpServletRequest request) throws Exception {
		mail = mailService.get(mail);

		Mailbox mailbox = new Mailbox();
		mailbox.setId(mail.getMailboxId());
		mailbox = mailService.getMailbox(mailbox);
		// 移除发件人记录和非本人的密送记录
		Long userId = SystemSecurityUtils.getUser().getId();
		List<MailRecord> receivers = mail.getReceivers();
		boolean isSender = false;
		for (int i = 0; i < receivers.size(); i++) {
			MailRecord receiver = receivers.get(i);
			Integer type = receiver.getReceiveType();
			// 如果发件人时当前人，设置isSender
			if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(type)
					&& (userId.equals(receiver.getReceiveUserId()) || (mailbox != null && mailbox
							.getAddress().equals(receiver.getReceiveMail())))) {
				isSender = true;
				break;
			}
		}

		for (int i = receivers.size() - 1; i >= 0; i--) {
			MailRecord record = receivers.get(i);
			// 内部邮件
			if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
				// 发送人不是当前用户
				if (!mail.getSenderUserId().equals(
						SystemSecurityUtils.getUser().getId())) {
					// 如果是群发单显，并且接收人不是当前用户remove，接收人只显示自己
					if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
							.getReceiveType())
							&& !record.getReceiveUserId().equals(
									SystemSecurityUtils.getUser().getId())) {
						receivers.remove(i);
						continue;
					}
				}
				if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(record
						.getReceiveType())) {
					receivers.remove(i);

				} else if (record.getReceiveUserId() != null
						&& record.getReceiveUserId().equals(
								SystemSecurityUtils.getUser().getId())) {
					mail.setReceiver(record);
				} else if (Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
						.getReceiveType()) && !isSender) {
					receivers.remove(i);
				}
			} else {// 外部
				if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(record
						.getReceiveType())) {
					receivers.remove(i);

				} else if (Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
						.getReceiveType()) && !isSender) {
					boolean remove = true;
					// for (Mailbox box : boxList) {
					if (mailbox.getAddress().equals(record.getReceiveMail())) {
						remove = false;
					}
					// }
					if (remove) {
						receivers.remove(i);
					}
				}
			}
		}
		return mail;
	}

	/**
	 * @description 删除文件夹方法
	 * @param Mail
	 *            mail 实体类
	 * @param String
	 *            id 删除数据的主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author xuweiping
	 * @version 2014-04-17
	 */
	@RequestMapping(value = "deleteFolder.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "deleteFolder", operateDescribe = "删除文件夹")
	public Map<String, Object> deleteFolder(MailFolder folder, String ids,
			HttpServletRequest request) throws Exception {
		folder.setPrimaryKeys(ids.split(","));
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Mail mail = new Mail();
		mail.setReceivers(new ArrayList<MailRecord>());
		for (String id : folder.getPrimaryKeys()) {
			MailRecord receiver = new MailRecord();
			receiver.setFolderId(new Long(id));
			receiver.setReceiveUserId(SystemSecurityUtils.getUser().getId());
			mail.getReceivers().add(receiver);
		}
		List<Mail> list = mailService.queryAllDetail(mail);
		if (list != null && list.size() > 0) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_IC_067"));
			return resultMap;
		}

		Integer count = mailService.deleteMailFolder(folder);

		if (count > 0) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_SYS_001"));
		}
		return resultMap;
	}

	/**
	 * @description 获取单条记录方法 暂时不用
	 * @param MailFolder
	 *            folder 实体类
	 * @return Mail 查询结果
	 * @throws Exception
	 * @author xuweiping
	 * @version 2014-04-17
	 */
	@RequestMapping(value = "getFolder.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "getFolder", operateDescribe = "读取文件夹")
	public MailFolder getFolder(MailFolder folder, HttpServletRequest request)
			throws Exception {
		return mailService.getMailFolder(folder);
	}

	/**
	 * 方法描述：获取邮箱签名方法
	 * 
	 * @param sign
	 * @return
	 * @author zhanglg
	 * @version 2014年5月26日下午3:21:06
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "getSign.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "getSign", operateDescribe = "读取签名")
	public Mailbox getSign(Mailbox box, HttpServletRequest request)
			throws CustomException {
		return mailService.getMailbox(box);
	}

	/**
	 * 方法描述：获取邮箱内部签名方法
	 * 
	 * @param sign
	 * @return
	 * @author weny
	 * @version 2014年9月24日
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "querySign.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "querySign", operateDescribe = "读取内部签名")
	public Mailbox querySign(Mailbox box, HttpServletRequest request)
			throws CustomException {
		return mailService.querySign(box);
	}

	/**
	 * 外部邮箱附件下载方法
	 * 
	 * @param filename
	 *            文件名
	 * @param mailId
	 *            邮件Id
	 * 
	 * @param request
	 *            文件路径 带扩展名
	 * @param response
	 * @throws IcException
	 */
	@RequestMapping("download.action")
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "download", operateDescribe = "下载附件")
	public void downloadFile(HttpServletRequest request, String fileName,
			Long mailId, HttpServletResponse response) throws IcException {
		mailService.downloadExtAttach(mailId, fileName, response, request);
	}

	/**
	 * @description 查看往来邮件
	 * @param Mail
	 *            mail 实体类
	 * @param String
	 *            id 发件人id
	 * @throws Exception
	 * @author 徐伟平
	 * @version 2014-6-13
	 */
	@RequestMapping(value = "viewToAndFrom.action")
	@ResponseBody
	@ActionLog(operateModelNm = "收件箱", operateFuncNm = "viewToAndFrom", operateDescribe = "查看往来邮件")
	public Map<String, Object> viewToAndFrom(Mail mail,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// 判断传回的发件人id是否为空，不为空放到对象中，在获取登录人id放到对象中
			mail.setCreateUser(SystemSecurityUtils.getUser().getId());
			resultMap = mailService.viewToAndFrom(mail);
		} catch (IcException e) {
			if (e.getLogMsg() == null) {
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
						MessageUtils.getMessage("JC_OA_IC_040"));
			} else {
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, e.getLogMsg());
			}
		}

		return resultMap;
	}

	/**
	 * 方法描述：删除未读邮件
	 * 
	 * @param mail
	 * @param ids
	 * @param mrids
	 * @param fromFolder
	 * @param toFolder
	 * @param request
	 * @return
	 * @author 宋海涛
	 * @version 2014年6月26日上午8:44:47
	 * @see
	 */
	@RequestMapping(value = "deleteUnRead.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "deleteUnRead", operateDescribe = "删除邮件")
	public boolean deleteUnRead(Mail mail, String ids, String mrids,
			Long fromFolder, Long toFolder, HttpServletRequest request) {
		mail.setPrimaryKeys(ids.indexOf(",") > 0 ? ids.split(",")
				: new String[] { ids });
		try {
			mailService.moveToUnReadMail(mail, mrids, fromFolder, toFolder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @description 未读邮件跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-04-17
	 */
	@RequestMapping(value = "manageUnread.action")
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageUnread", operateDescribe = "查询未读邮件")
	public String manageUnread(Model model, HttpServletRequest request)
			throws Exception {
		model.addAttribute("mailFolderList", this.initMailFolders(request));
		model.addAttribute("mailboxList", this.initMailboxs(request));
		model.addAttribute("readFlag", Constants.IC_MAIL_MAILSTATUS_UNREAD);
		model.addAttribute("userId", SystemSecurityUtils.getUser().getId());
		model.addAttribute("unread", 5);

		StringBuffer url = new StringBuffer(request.getServletPath());
		// 未读邮箱和星标邮箱未分离前，传参数用于辨别是未读还是星标
		// if (request.getQueryString() != null) {
		// url.append("?").append(request.getQueryString());
		// }
		model.addAttribute("url", url.toString());
		Map paraMap = request.getParameterMap();
		Iterator<String> keys = paraMap.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			model.addAttribute(key, ((String[]) paraMap.get(key))[0]);
			if (key.equals("receivers[0].folderId")) {
				model.addAttribute("mfid", ((String[]) paraMap.get(key))[0]);
			}
			if (key.equals("receivers[0].readFlag")) {
				model.addAttribute("receiversReadFlag",
						((String[]) paraMap.get(key))[0]);
			}
		}
		model.addAttribute("moveShow", 1);// 未读邮件列表不显示移动至

		return "ic/mail/mailUnRead";
	}

	/**
	 * 方法描述：分页查询未读邮件方法
	 * 
	 * @param mail
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version 2014年6月26日上午8:46:41
	 * @see
	 */
	@RequestMapping(value = "manageUnReadList.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageUnReadList", operateDescribe = "查询未读邮件")
	public PageManager manageUnReadList(Mail mail, PageManager page,
			HttpServletRequest request) throws Exception {
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
		List<MailRecord> receivers = mail.getReceivers();
		if (Constants.IC_MAIL_MAILFOLDER_DRAFT.equals((receivers.get(0))
				.getFolderId())
				|| Constants.IC_MAIL_MAILFOLDER_SENDED
						.equals((receivers.get(0)).getFolderId())) {
			mail.addOrderByFieldDesc("t.SENDER_TIME");
		} else {
			mail.addOrderByFieldDesc("receiveTime2");
		}
		if (mail.getMailboxId() == null) {
			if (Constants.IC_MAIL_MAILFOLDER_DRAFT.equals((receivers.get(0))
					.getFolderId())
					|| Constants.IC_MAIL_MAILFOLDER_SENDED.equals((receivers
							.get(0)).getFolderId())) {
				mail.addOrderByFieldDesc("t.SENDER_TIME");
			} else {
				mail.addOrderByFieldDesc("receiveTime2");
			}
			Mailbox mailbox = new Mailbox();
			mailbox.setCreateUser(SystemSecurityUtils.getUser().getId());
			List<Mailbox> mailboxlist = mailService.getMailboxList(mailbox);
			Mailbox mbox = new Mailbox();
			mbox.setId(1L);
			mailboxlist.add(mbox);
			mail.setMailbox(mailboxlist);
		}
		PageManager page_ = mailService.queryUnReadMail(mail, page);
		List<Principal> uList = SystemSecurityUtils.getOnlineUsers();
		List<Mail> mailList = (List<Mail>) page_.getData();
		MailRecord mailRecord = new MailRecord();
		List<MailRecord> recordList = new ArrayList<MailRecord>();
		// 如果列表中只有一条数据，将index设置为-1，js中通过判断是-1来屏蔽掉上一页下一页的功能
		if (mailList.size() == 1 && page_.getTotalPages() == 1) {
			mailList.get(0).setIndex(0);
			mailList.get(0).setShowNext(false);
			mailList.get(0).setShowPre(false);
			// mailRecord.setMailId(mailList.get(0).getId());
			// // 根据当前记录主表id获得子表记录
			// recordList = mailRecordService.queryAll(mailRecord);
			// // 如果子表记录数为1，说明该邮件无收件人
			// if (recordList.size() == 1
			// && Constants.IC_MAIL_RECEIVETYPE_INNERSENDER
			// .equals(recordList.get(0).getReceiveType())) {
			// mailList.get(0).setIsHaveReceiveUser("0");
			// }
			boolean isOnlineUser = true;
			// 内部邮箱
			if (mail.getMailboxId() != null) {
				if (mail.getMailboxId() == 1) {
					// 如果当前在线人员不为空
					if (uList != null && uList.size() > 0) {
						for (int j = 0; j < uList.size(); j++) {
							// 判断当前发信人是否在线如果在线设置isOnline为”1“
							if (uList.get(j).getId().longValue() == mailList
									.get(0).getSenderUserId().longValue()) {
								mailList.get(0).setIsOnline("1");
								isOnlineUser = false;
								break;
							}
						}
						if (isOnlineUser) {
							mailList.get(0).setIsOnline("0");
						}
					}
				}
			}
		} else {
			boolean isOnlineUser = true;
			for (int i = 0; i < mailList.size(); i++) {
				isOnlineUser = true;
				// 内部邮箱
				if (mail.getMailboxId() != null) {
					if (mail.getMailboxId() == 1) {
						// 如果当前在线人员不为空
						if (uList != null && uList.size() > 0) {
							for (int j = 0; j < uList.size(); j++) {
								// 判断当前发信人是否在线如果在线设置isOnline为”1“
								if (uList.get(j).getId().longValue() == mailList
										.get(i).getSenderUserId().longValue()) {
									mailList.get(i).setIsOnline("1");
									isOnlineUser = false;
									break;
								}
							}
							if (isOnlineUser) {
								mailList.get(i).setIsOnline("0");
							}
						}
					}
				}
				// mailRecord.setMailId(mailList.get(i).getId());
				// // 根据当前记录主表id获得子表记录
				// recordList = mailRecordService.queryAll(mailRecord);
				// // 如果子表记录数为1，说明该邮件无收件人
				// if (recordList.size() == 1
				// && recordList
				// .get(0)
				// .getReceiveType()
				// .equals(Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
				// mailList.get(i).setIsHaveReceiveUser("0");
				// }
				if (i == 0 && page.getPage() == 0) {
					mailList.get(i).setShowPre(false);
				}
				if (i == mailList.size() - 1
						&& page_.getTotalPages() == page_.getPage()) {
					mailList.get(i).setShowNext(false);
				}
				mailList.get(i).setIndex(
						(page_.getPage() - 1) * page_.getPageRows() + i);
			}
		}
		return page_;
	}

	/**
	 * 方法描述：未读邮件搜索查询
	 * 
	 * @param mail
	 * @param page
	 * @param request
	 * @return
	 * @author 宋海涛
	 * @version 2014年6月26日上午9:03:44
	 * @throws Exception
	 * @see
	 */
	@RequestMapping(value = "manageSearchUnRead.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageSearchUnRead", operateDescribe = "搜索未读邮件列表")
	public PageManager manageSearchUnRead(Mail mail, PageManager page,
			HttpServletRequest request) throws Exception {
		if (StringUtils.isEmpty(mail.getSenderMail())) {
			mail.setSenderMail(null);
		}
		if (StringUtils.isEmpty(mail.getMailTitle())) {
			mail.setMailTitle(null);
		}
		if (StringUtils.isEmpty(mail.getMailContent())) {
			mail.setMailContent(null);
		}
		if (mail.getSearchReceiveTimeEnd() != null) {
			mail.setSearchReceiveTimeEnd(DateUtils.fillTime(mail
					.getSearchReceiveTimeEnd()));
		}
		mail.addOrderByFieldDesc("receiveTime2");
		PageManager page_ = mailService.searchUnRead(mail, page);

		List<Mail> mailList = (List<Mail>) page_.getData();
		List<Principal> uList = SystemSecurityUtils.getOnlineUsers();
		MailRecord mailRecord = new MailRecord();
		List<MailRecord> recordList = new ArrayList<MailRecord>();
		// 如果列表中只有一条数据，将index设置为-1，js中通过判断是-1来屏蔽掉上一页下一页的功能
		if (mailList.size() == 1 && page_.getTotalPages() == 1) {
			mailList.get(0).setIndex(0);
			mailList.get(0).setShowNext(false);
			mailList.get(0).setShowPre(false);
			mailRecord.setMailId(mailList.get(0).getId());
			// 根据当前记录主表id获得子表记录
			recordList = mailRecordService.queryAll(mailRecord);
			// 如果子表记录数为1，说明该邮件无收件人
			if (recordList.size() == 1
					&& Constants.IC_MAIL_RECEIVETYPE_INNERSENDER
							.equals(recordList.get(0).getReceiveType())) {
				mailList.get(0).setIsHaveReceiveUser("0");
			}
			boolean isOnlineUser = true;
			// 内部邮箱
			if (mail.getMailboxId() != null) {
				if (mail.getMailboxId() == 1) {
					// 如果当前在线人员不为空
					if (uList != null && uList.size() > 0) {
						for (int j = 0; j < uList.size(); j++) {
							// 判断当前发信人是否在线如果在线设置isOnline为”1“
							if (uList.get(j).getId().longValue() == mailList
									.get(0).getSenderUserId().longValue()) {
								mailList.get(0).setIsOnline("1");
								isOnlineUser = false;
								break;
							}
						}
						if (isOnlineUser) {
							mailList.get(0).setIsOnline("0");
						}
					}
				}
			}
		} else {
			boolean isOnlineUser = true;
			for (int i = 0; i < mailList.size(); i++) {
				isOnlineUser = true;
				// 内部邮箱
				if (mail.getMailboxId() != null) {
					if (mail.getMailboxId() == 1) {
						// 如果当前在线人员不为空
						if (uList != null && uList.size() > 0) {
							for (int j = 0; j < uList.size(); j++) {
								// 判断当前发信人是否在线如果在线设置isOnline为”1“
								if (uList.get(j).getId().longValue() == mailList
										.get(i).getSenderUserId().longValue()) {
									mailList.get(i).setIsOnline("1");
									isOnlineUser = false;
									break;
								}
							}
							if (isOnlineUser) {
								mailList.get(i).setIsOnline("0");
							}
						}
					}
				}
				mailRecord.setMailId(mailList.get(i).getId());
				// 根据当前记录主表id获得子表记录
				recordList = mailRecordService.queryAll(mailRecord);
				// 如果子表记录数为1，说明该邮件无收件人
				if (recordList.size() == 1
						&& Constants.IC_MAIL_RECEIVETYPE_INNERSENDER
								.equals(recordList.get(0).getReceiveType())) {
					mailList.get(i).setIsHaveReceiveUser("0");
				}
				if (i == 0 && page.getPage() == 0) {
					mailList.get(i).setShowPre(false);
				}
				if (i == mailList.size() - 1
						&& page_.getTotalPages() == page_.getPage()) {
					mailList.get(i).setShowNext(false);
				}
				mailList.get(i).setIndex(
						(page_.getPage() - 1) * page_.getPageRows() + i);
			}
		}
		return page_;
	}

	/**
	 * 方法描述：阅读未读邮件
	 * 
	 * @param mailParam
	 * @param password
	 * @param folderId
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version 2014年6月27日上午8:22:02
	 * @see
	 */
	@RequestMapping(value = "manageUnReadDetail.action")
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageUnReadDetail", operateDescribe = "阅读未读邮件")
	public String manageUnReadDetail(Mail mailParam, String password,
			String folderId, Model model, HttpServletRequest request)
			throws Exception {

		Mail mail = new Mail();
		mail.setId(mailParam.getId());
		mail = mailService.get(mail);
		mailService.setReadStatus(mail, mailParam.getMrid(),
				Constants.IC_MAIL_MAILSTATUS_READED.toString());
		Mailbox mailbox = new Mailbox();
		mailbox.setId(mail.getMailboxId());
		mailbox = mailService.getMailbox(mailbox);
		boolean showSingle = false;
		// 新写的收件人、抄送人显示逻辑，把收件人、抄送人、群发单显人，分别放在Mail的to,cc,showSingle字段
		// Updated By 张立刚
		Long userId = SystemSecurityUtils.getUser().getId();
		List<MailRecord> receivers = mail.getReceivers();
		receivers = mail.getReceivers();
		boolean isSender = false;
		for (int i = 0; i < receivers.size(); i++) {
			MailRecord receiver = receivers.get(i);
			Integer type = receiver.getReceiveType();
			// 如果发件人时当前人，设置isSender
			if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(type)
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

		// 显示人员状态声明
		StringBuffer newTo = new StringBuffer();
		StringBuffer newCc = new StringBuffer();
		StringBuffer newSs = new StringBuffer();
		StringBuffer newBcc = new StringBuffer();
		for (int i = 0; i < receivers.size(); i++) {
			MailRecord record = receivers.get(i);
			if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
				// 如果该邮件所有收发记录存在id值和当前页记录的子表id相等记录，则将该记录存方法mail的receiver属性中，用于传值
				if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(record
						.getReceiveType())) {
					if ("3".equals(folderId)) {
						mail.setReceiver(record);
					}
				} else if (record.getReceiveUserId() != null
						&& record.getReceiveUserId().equals(
								SystemSecurityUtils.getUser().getId())
						&& !"3".equals(folderId)) {
					mail.setReceiver(record);
				}

				if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
						.getReceiveType())
						&& record.getReceiveUserId().equals(userId)
						|| isSender
						&& (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE
								.equals(record.getReceiveType()))) {
					if (record.getReadDate() != null) {
						newSs.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						ss.append(record.getReceiveUserName()).append(",");
					} else {
						newSs.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						ss.append(record.getReceiveUserName()).append(",");
					}
				} else if (Constants.IC_MAIL_RECEIVETYPE_TO.equals(record
						.getReceiveType())) {
					if (record.getReadDate() != null) {
						newTo.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						to.append(record.getReceiveUserName()).append(",");
					} else {
						newTo.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						to.append(record.getReceiveUserName()).append(",");
					}
				} else if (Constants.IC_MAIL_RECEIVETYPE_CC.equals(record
						.getReceiveType())) {
					if (record.getReadDate() != null) {
						newCc.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						cc.append(record.getReceiveUserName()).append(",");
					} else {
						newCc.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						cc.append(record.getReceiveUserName()).append(",");
					}
				}
				// 可以看到密送：收件人可以看到自己、发件人可以看到所有
				else if ((Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
						.getReceiveType()) && record.getReceiveUserId().equals(
						userId))
						|| isSender
						&& (Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
								.getReceiveType()))) {
					if (record.getReadDate() != null) {
						newBcc.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						bcc.append(record.getReceiveUserName()).append(",");
					} else {
						newBcc.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\"  onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						bcc.append(record.getReceiveUserName()).append(",");
					}
				}
			} else {
				// 如果该邮件所有收发记录存在id值和当前页记录的子表id相等记录，则将该记录存方法mail的receiver属性中，用于传值
				if (mailParam.getMrid().equals(record.getId().toString())) {
					mail.setReceiver(record);
				}
				if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
						.getReceiveType())
						&& record.getReceiveMail().equals(mailbox.getAddress())
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
		if (newTo.length() > 0 && newTo.charAt(newTo.length() - 1) == ',') {
			newTo.deleteCharAt(newTo.length() - 1);
		}
		if (newSs.length() > 0 && newSs.charAt(newSs.length() - 1) == ',') {
			newSs.deleteCharAt(newSs.length() - 1);
		}
		if (newCc.length() > 0 && newCc.charAt(newCc.length() - 1) == ',') {
			newCc.deleteCharAt(newCc.length() - 1);
		}
		if (newBcc.length() > 0 && newBcc.charAt(newBcc.length() - 1) == ',') {
			newBcc.deleteCharAt(newBcc.length() - 1);
		}

		mail.setTo(to.toString());
		mail.setCc(cc.toString());
		mail.setShowSingle(ss.toString());
		mail.setBcc(bcc.toString());

		// 回显收件人状态使用
		mail.setNewTo(newTo.toString());
		mail.setNewCc(newCc.toString());
		mail.setNewBcc(newBcc.toString());
		mail.setNewSs(newSs.toString());
		// 不再用下面这种逻辑
		// 移除发件人记录和非本人的密送记录,and remove not me when show single
		// List<MailRecord> receivers = mail.getReceivers();
		// for (int i = receivers.size() - 1; i >= 0; i--) {
		// MailRecord record = receivers.get(i);
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE)) {
		// showSingle = showSingle | true;
		// }
		// // 内部邮件
		// if (mail.getMailboxId().equals(Constants.IC_MAIL_MAILBOX_INNER)) {
		// // 发送人不是当前用户
		// if (!mail.getSenderUserId().equals(
		// SystemSecurityUtils.getUser().getId())) {
		// // 如果是群发单显，并且接收人不是当前用户remove，接收人只显示自己
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE)
		// && !record.getReceiveUserId().equals(
		// SystemSecurityUtils.getUser().getId())) {
		// receivers.remove(i);
		// continue;
		// }
		// }
		//
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
		// if ("3".equals(folderId)) {
		// mail.setReceiver(record);
		// }
		// receivers.remove(i);
		// } else if (record.getReceiveUserId() != null
		// && record.getReceiveUserId().equals(
		// SystemSecurityUtils.getUser().getId())
		// && !"3".equals(folderId)) {
		// mail.setReceiver(record);
		// } else if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_BCC)) {
		// receivers.remove(i);
		// }
		// } else {// 外部
		// if (mailParam.getMrid().equals(record.getId().toString())) {
		// mail.setReceiver(record);
		// }
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
		// receivers.remove(i);
		// } else if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_BCC)) {
		// boolean remove = true;
		// for (Mailbox box : boxList) {
		// if (box.getAddress().equals(record.getReceiveMail())) {
		// remove = false;
		// }
		// }
		// if (remove) {
		// receivers.remove(i);
		// }
		// }
		// }
		// }
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
		model.addAttribute("mailFolderList", this.initMailFolders(request));
		model.addAttribute("mail", mail);
		if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			mailbox = new Mailbox();
			mailbox.setId(mail.getMailboxId());
			model.addAttribute("mailboxAddress", mailService
					.getMailbox(mailbox).getAddress());
		}

		model.addAttribute("currentUserId", SystemSecurityUtils.getUser()
				.getId());
		model.addAttribute("showSingle", showSingle);
		model.addAttribute("folderId", folderId);
		if (mailParam != null) {
			if (mailParam.getReturnURL() != null) {
				if(URLDecoder.decode(mailParam.getReturnURL(),"utf-8").indexOf("?") != -1){
					mail.setReturnURL(mailParam.getReturnURL() + "&returned=true");
					}
					else{
						mail.setReturnURL(mailParam.getReturnURL());
				}
			}
			mail.setIndex(mailParam.getIndex());
			mail.setShowNext(mailParam.isShowNext());
			mail.setShowPre(mailParam.isShowPre());
			model.addAttribute("mailTitle", mailParam.getMailTitle());
			model.addAttribute("mailEasyTitle", mailParam.getMailEasyTitle());
			model.addAttribute("senderUserId", mailParam.getSenderUserId());
			model.addAttribute("senderMail", mailParam.getSenderMail());
			if (mailParam.getMailboxId() != null
					&& mailParam.getMailboxId() == 0) {
				model.addAttribute("isAllMail", "true");
			} else {
				model.addAttribute("isAllMail", "false");
			}
			if (mailParam.getSearchReceiveTimeBegin() != null) {
				model.addAttribute("searchReceiveTimeBegin", DateUtils
						.formatDate(mailParam.getSearchReceiveTimeBegin(),
								"yyyy-MM-dd"));
			}
			if (mailParam.getSearchReceiveTimeEnd() != null) {
				model.addAttribute("searchReceiveTimeEnd", DateUtils
						.formatDate(mailParam.getSearchReceiveTimeEnd(),
								"yyyy-MM-dd"));
			}
		}
		model.addAttribute("user", SystemSecurityUtils.getUser());
		return "ic/mail/mailUnReadDetail";
	}

	/**
	 * 方法描述：未读邮件上一封、下一封
	 * 
	 * @param mail
	 * @param folderId
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version 2014年6月26日上午11:38:09
	 * @see
	 */
	@RequestMapping(value = "navigateUnRead.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "navigateUnRead", operateDescribe = "未读邮件邮件导航")
	public Mail navigateUnRead(Mail mail, String folderId,
			HttpServletRequest request) throws Exception {
		if (mail.getSearchReceiveTimeEnd() != null) {
			mail.setSearchReceiveTimeEnd(DateUtils.fillTime(mail
					.getSearchReceiveTimeEnd()));
		}
		if ("".equals(mail.getSenderMail())) {
			mail.setSenderMail(null);
		}

		if ("".equals(mail.getMailTitle())) {
			mail.setMailTitle(null);
		}
		if ("true".equals(mail.getIsAllMail())) {
			mail.setMailboxId(null);
		}
		String returnURL = mail.getReturnURL();
		PageManager page = new PageManager();

		page.setPageRows(1);
		if (mail.getForward() > 0) {
			mail.getIndex();
		} else {
			mail.setIndex(mail.getIndex() + mail.getForward());
		}
		page.setPage(mail.getIndex());
		List<MailRecord> receivers = mail.getReceivers();
		mail.addOrderByFieldDesc("receiveTime2");

		if (mail.getMailboxId() == null) {
			Mailbox mailbox = new Mailbox();
			mailbox.setCreateUser(SystemSecurityUtils.getUser().getId());
			List<Mailbox> mailboxlist = mailService.getMailboxList(mailbox);
			Mailbox mbox = new Mailbox();
			mbox.setId(1L);
			mailboxlist.add(mbox);
			mail.setMailbox(mailboxlist);
		}
		PageManager page_ = mailService.queryUnReadMail(mail, page);

		List<Mail> mailList = (List<Mail>) page_.getData();
		if (mailList != null && mailList.size() > 0) {
			Long recordId = mailList.get(0).getReceivers().get(0).getId();
			Mail condMail = new Mail();
			condMail.setId(mailList.get(0).getId());
			mail = mailService.get(condMail);

			Mailbox mailbox = new Mailbox();
			mailbox.setId(mail.getMailboxId());
			mailbox = mailService.getMailbox(mailbox);

			// 新写的收件人、抄送人显示逻辑，把收件人、抄送人、群发单显人，分别放在Mail的to,cc,showSingle字段
			// Updated By 张立刚
			receivers = mail.getReceivers();
			Long userId = SystemSecurityUtils.getUser().getId();
			boolean isSender = false;
			for (int i = 0; i < receivers.size(); i++) {
				MailRecord receiver = receivers.get(i);
				Integer type = receiver.getReceiveType();
				// 如果发件人时当前人，设置isSender
				if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(type)
						&& (userId.equals(receiver.getReceiveUserId()) || mailbox
								.getAddress().equals(receiver.getReceiveMail()))) {
					isSender = true;
					break;
				}
			}
			StringBuffer to = new StringBuffer();
			StringBuffer cc = new StringBuffer();
			StringBuffer ss = new StringBuffer();
			StringBuffer bcc = new StringBuffer();
			// 显示人员状态声明
			StringBuffer newTo = new StringBuffer();
			StringBuffer newCc = new StringBuffer();
			StringBuffer newSs = new StringBuffer();
			StringBuffer newBcc = new StringBuffer();
			for (int i = 0; i < receivers.size(); i++) {
				MailRecord record = receivers.get(i);
				if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
					// //
					// 如果该邮件所有收发记录存在id值和当前页记录的子表id相等记录，则将该记录存方法mail的receiver属性中，用于传值
					// if (record.getReceiveType().equals(
					// Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
					// if ("3".equals(folderId)) {
					// mail.setReceiver(record);
					// }
					// } else if (record.getReceiveUserId() != null
					// && record.getReceiveUserId().equals(
					// SystemSecurityUtils.getUser().getId())
					// && !"3".equals(folderId)) {
					// mail.setReceiver(record);
					// }
					// 如果该邮件所有收发记录存在id值和当前页记录的子表id相等记录，则将该记录存方法mail的receiver属性中，用于传值
					if (record.getId().longValue() == recordId.longValue()) {
						mail.setReceiver(record);
					}
					if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
							.getReceiveType())
							&& record.getReceiveUserId().equals(
									SystemSecurityUtils.getUser().getId())
							|| isSender
							&& (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE
									.equals(record.getReceiveType()))) {

						if (record.getReadDate() != null) {
							newSs.append(
									"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
											+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "("
											+ DateFormatUtils.format(
													record.getReadDate(),
													"yyyy-MM-dd HH:mm:ss")
											+ ")</span></a>").append(",");
							ss.append(record.getReceiveUserName()).append(",");
						} else {
							newSs.append(
									"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "</a>").append(",");
							ss.append(record.getReceiveUserName()).append(",");
						}
					} else if (Constants.IC_MAIL_RECEIVETYPE_TO.equals(record
							.getReceiveType())) {

						if (record.getReadDate() != null) {
							newTo.append(
									"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
											+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "("
											+ DateFormatUtils.format(
													record.getReadDate(),
													"yyyy-MM-dd HH:mm:ss")
											+ ")</span></a>").append(",");
							to.append(record.getReceiveUserName()).append(",");
						} else {
							newTo.append(
									"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "</a>").append(",");
							to.append(record.getReceiveUserName()).append(",");
						}

					} else if (Constants.IC_MAIL_RECEIVETYPE_CC.equals(record
							.getReceiveType())) {
						if (record.getReadDate() != null) {
							newCc.append(
									"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
											+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "("
											+ DateFormatUtils.format(
													record.getReadDate(),
													"yyyy-MM-dd HH:mm:ss")
											+ ")</span></a>").append(",");
							cc.append(record.getReceiveUserName()).append(",");
						} else {
							newCc.append(
									"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "</a>").append(",");
							cc.append(record.getReceiveUserName()).append(",");
						}
					}
					// 可以看到密送：收件人可以看到自己、发件人可以看到所有
					else if ((Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
							.getReceiveType()) && record.getReceiveUserId()
							.equals(userId))
							|| isSender
							&& Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
									.getReceiveType())) {
						if (record.getReadDate() != null) {
							newBcc.append(
									"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
											+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "("
											+ DateFormatUtils.format(
													record.getReadDate(),
													"yyyy-MM-dd HH:mm:ss")
											+ ")</span></a>").append(",");
							bcc.append(record.getReceiveUserName()).append(",");
						} else {
							newBcc.append(
									"<a style = \"cursor :pointer\" class=\"email-receivers\"  onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "</a>").append(",");
							bcc.append(record.getReceiveUserName()).append(",");
						}
					}
				} else {
					// 如果该邮件所有收发记录存在id值和当前页记录的子表id相等记录，则将该记录存方法mail的receiver属性中，用于传值
					if (record.getId().longValue() == recordId.longValue()) {
						mail.setReceiver(record);
					}
					if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
							.getReceiveType())
							&& record.getReceiveMail().equals(
									mailbox.getAddress())
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
			if (newTo.length() > 0 && newTo.charAt(newTo.length() - 1) == ',') {
				newTo.deleteCharAt(newTo.length() - 1);
			}
			if (newSs.length() > 0 && newSs.charAt(newSs.length() - 1) == ',') {
				newSs.deleteCharAt(newSs.length() - 1);
			}
			if (newCc.length() > 0 && newCc.charAt(newCc.length() - 1) == ',') {
				newCc.deleteCharAt(newCc.length() - 1);
			}
			if (newBcc.length() > 0
					&& newBcc.charAt(newBcc.length() - 1) == ',') {
				newBcc.deleteCharAt(newBcc.length() - 1);
			}
			mail.setTo(to.toString());
			mail.setCc(cc.toString());
			mail.setShowSingle(ss.toString());
			mail.setBcc(bcc.toString());

			// 回显收件人状态使用
			mail.setNewTo(newTo.toString());
			mail.setNewCc(newCc.toString());
			mail.setNewBcc(newBcc.toString());
			mail.setNewSs(newSs.toString());
			// 不再用下面这种逻辑
			// 移除发件人记录和非本人的密送记录
			// receivers = mail.getReceivers();
			// for (int i = receivers.size() - 1; i >= 0; i--) {
			// MailRecord record = receivers.get(i);
			// // 内部邮件
			// if (mail.getMailboxId().equals(Constants.IC_MAIL_MAILBOX_INNER))
			// {
			//
			// if (record.getReceiveType().equals(
			// Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
			// if (record.getFolderId() == 3
			// && record.getReadFlag() == 0) {
			// mail.setReceiver(record);
			// }
			// receivers.remove(i);
			// } else if (record.getReceiveUserId() != null
			// && record.getReceiveUserId().equals(
			// SystemSecurityUtils.getUser().getId())
			// && !"3".equals(record.getFolderId().toString())
			// && record.getReadFlag() == 0) {
			// mail.setReceiver(record);
			// } else if (record.getReceiveType().equals(
			// Constants.IC_MAIL_RECEIVETYPE_BCC)) {
			// receivers.remove(i);
			// }
			//
			// } else {// 外部
			// if (record.getId().longValue() == recordId.longValue()) {
			// mail.setReceiver(record);
			// }
			// if (record.getReceiveType().equals(
			// Constants.IC_MAIL_RECEIVETYPE_BCC)) {
			// boolean remove = true;
			// for (Mailbox box : boxList) {
			// if (box.getAddress()
			// .equals(record.getReceiveMail())) {
			// remove = false;
			// }
			// }
			// if (remove) {
			// receivers.remove(i);
			// }
			// }
			// }
			// }
		}
		if (Constants.IC_MAIL_ENCRYPTION_YES.equals(mail.getEncryption())) {
			mail.setMailContent(null);
		}

		mail.setReturnURL(returnURL);
		if (page_.getTotalPages() == page_.getPage()) {
			mail.setShowNext(false);
		}
		if (page_.getPage() - 1 == 0) {
			mail.setShowPre(false);
		}
		mail.setIndex(page_.getPage() - 1);
		// 如果下一封记录不为空，并且未读设置该条记录已读
		if (mail.getReceiver() != null) {
			if (!"1".equals(mail.getReceiver().getReadFlag().toString())) {
				// 当点击下一封是设置该邮件为已读
				mailService.setReadStatus(mail, mail.getReceiver().getId()
						.toString(),
						Constants.IC_MAIL_MAILSTATUS_READED.toString());
			}
		}
		mail.setWrongPassword(true);
		return mail;
	}

	/**
	 * 方法描述：分页查询星标邮件方法
	 * 
	 * @param mail
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version 2014年6月26日上午8:46:41
	 * @see
	 */
	@RequestMapping(value = "manageStarList.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageStarList", operateDescribe = "查询星标邮件")
	public PageManager manageStarList(Mail mail, PageManager page,
			HttpServletRequest request) throws Exception {
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
		List<MailRecord> receivers = mail.getReceivers();
		if (Constants.IC_MAIL_MAILFOLDER_DRAFT.equals((receivers.get(0))
				.getFolderId())
				|| Constants.IC_MAIL_MAILFOLDER_SENDED
						.equals((receivers.get(0)).getFolderId())) {
			mail.addOrderByFieldDesc("t.SENDER_TIME");
		} else {
			mail.addOrderByFieldDesc("t.RECEIVE_TIME2");
		}
		if (mail.getMailboxId() == null) {
			if (Constants.IC_MAIL_MAILFOLDER_DRAFT.equals((receivers.get(0))
					.getFolderId())
					|| Constants.IC_MAIL_MAILFOLDER_SENDED.equals((receivers
							.get(0)).getFolderId())) {
				mail.addOrderByFieldDesc("t.SENDER_TIME");
			} else {
				mail.addOrderByFieldDesc("t.RECEIVE_TIME2");
			}
			Mailbox mailbox = new Mailbox();
			mailbox.setCreateUser(SystemSecurityUtils.getUser().getId());
			List<Mailbox> mailboxlist = mailService.getMailboxList(mailbox);
			Mailbox mbox = new Mailbox();
			mbox.setId(1L);
			mailboxlist.add(mbox);
			mail.setMailbox(mailboxlist);
		}
		PageManager page_ = mailService.queryStarMail(mail, page);

		List<Mail> mailList = (List<Mail>) page_.getData();
		MailRecord mailRecord = new MailRecord();
		List<MailRecord> recordList = new ArrayList<MailRecord>();
		// 如果列表中只有一条数据，将index设置为-1，js中通过判断是-1来屏蔽掉上一页下一页的功能
		if (mailList.size() == 1 && page_.getTotalPages() == 1) {
			mailList.get(0).setIndex(0);
			mailList.get(0).setShowNext(false);
			mailList.get(0).setShowPre(false);
			// mailRecord.setMailId(mailList.get(0).getId());
			// // 根据当前记录主表id获得子表记录
			// recordList = mailRecordService.queryAll(mailRecord);
			// // 如果子表记录数为1，说明该邮件无收件人
			// if (recordList.size() == 1
			// && Constants.IC_MAIL_RECEIVETYPE_INNERSENDER
			// .equals(recordList.get(0).getReceiveType())) {
			// mailList.get(0).setIsHaveReceiveUser("0");
			// }
		} else {
			for (int i = 0; i < mailList.size(); i++) {
				// mailRecord.setMailId(mailList.get(i).getId());
				// // 根据当前记录主表id获得子表记录
				// recordList = mailRecordService.queryAll(mailRecord);
				// // 如果子表记录数为1，说明该邮件无收件人
				// if (recordList.size() == 1
				// && recordList
				// .get(0)
				// .getReceiveType()
				// .equals(Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
				// mailList.get(i).setIsHaveReceiveUser("0");
				// }
				if (i == 0 && page.getPage() == 0) {
					mailList.get(i).setShowPre(false);
				}
				if (i == mailList.size() - 1
						&& page_.getTotalPages() == page_.getPage()) {
					mailList.get(i).setShowNext(false);
				}
				mailList.get(i).setIndex(
						(page_.getPage() - 1) * page_.getPageRows() + i);
			}
		}
		return page_;
	}

	/**
	 * 方法描述：阅读星标邮件
	 * 
	 * @param mailParam
	 * @param password
	 * @param folderId
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version 2014年6月30日上午9:40:48
	 * @see
	 */
	@RequestMapping(value = "manageStarDetail.action")
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageStarDetail", operateDescribe = "阅读星标邮件")
	public String manageStarDetail(Mail mailParam, String password,
			String folderId, Model model, HttpServletRequest request)
			throws Exception {

		Mail mail = new Mail();
		mail.setId(mailParam.getId());
		mail = mailService.get(mail);
		mailService.setReadStatus(mail, mailParam.getMrid(),
				Constants.IC_MAIL_MAILSTATUS_READED.toString());
		Mailbox mailbox = new Mailbox();
		mailbox.setId(mail.getMailboxId());
		mailbox = mailService.getMailbox(mailbox);
		boolean showSingle = false;
		// 新写的收件人、抄送人显示逻辑，把收件人、抄送人、群发单显人，分别放在Mail的to,cc,showSingle字段
		// Updated By 张立刚
		Long userId = SystemSecurityUtils.getUser().getId();
		List<MailRecord> receivers = mail.getReceivers();
		receivers = mail.getReceivers();
		boolean isSender = false;
		for (int i = 0; i < receivers.size(); i++) {
			MailRecord receiver = receivers.get(i);
			Integer type = receiver.getReceiveType();
			// 如果发件人时当前人，设置isSender
			if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(type)
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
		// 显示人员状态声明
		StringBuffer newTo = new StringBuffer();
		StringBuffer newCc = new StringBuffer();
		StringBuffer newSs = new StringBuffer();
		StringBuffer newBcc = new StringBuffer();
		for (int i = 0; i < receivers.size(); i++) {
			MailRecord record = receivers.get(i);
			if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
				// 如果该邮件所有收发记录存在id值和当前页记录的子表id相等记录，则将该记录存方法mail的receiver属性中，用于传值for
				// query
				if (mailParam.getMrid().equals(record.getId().toString())) {
					mail.setReceiver(record);
				}

				if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
						.getReceiveType())
						&& record.getReceiveUserId().equals(
								SystemSecurityUtils.getUser().getId())
						|| isSender
						&& (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE
								.equals(record.getReceiveType()))) {

					if (record.getReadDate() != null) {
						newSs.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						ss.append(record.getReceiveUserName()).append(",");
					} else {
						newSs.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						ss.append(record.getReceiveUserName()).append(",");
					}
				} else if (Constants.IC_MAIL_RECEIVETYPE_TO.equals(record
						.getReceiveType())) {
					if (record.getReadDate() != null) {
						newTo.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						to.append(record.getReceiveUserName()).append(",");
					} else {
						newTo.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						to.append(record.getReceiveUserName()).append(",");
					}
				} else if (Constants.IC_MAIL_RECEIVETYPE_CC.equals(record
						.getReceiveType())) {
					if (record.getReadDate() != null) {
						newCc.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						cc.append(record.getReceiveUserName()).append(",");
					} else {
						newCc.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						cc.append(record.getReceiveUserName()).append(",");
					}
				}
				// 可以看到密送：收件人可以看到自己、发件人可以看到所有
				else if ((Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
						.getReceiveType()) && record.getReceiveUserId().equals(
						userId))
						|| isSender
						&& (Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
								.getReceiveType()))) {
					if (record.getReadDate() != null) {
						newBcc.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						bcc.append(record.getReceiveUserName()).append(",");
					} else {
						newBcc.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\"  onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						bcc.append(record.getReceiveUserName()).append(",");
					}
				}
			} else {
				// 如果该邮件所有收发记录存在id值和当前页记录的子表id相等记录，则将该记录存方法mail的receiver属性中，用于传值
				if (mailParam.getMrid().equals(record.getId().toString())) {
					mail.setReceiver(record);
				}
				if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
						.getReceiveType())
						&& record.getReceiveMail().equals(mailbox.getAddress())
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
		if (newTo.length() > 0 && newTo.charAt(newTo.length() - 1) == ',') {
			newTo.deleteCharAt(newTo.length() - 1);
		}
		if (newSs.length() > 0 && newSs.charAt(newSs.length() - 1) == ',') {
			newSs.deleteCharAt(newSs.length() - 1);
		}
		if (newCc.length() > 0 && newCc.charAt(newCc.length() - 1) == ',') {
			newCc.deleteCharAt(newCc.length() - 1);
		}
		if (newBcc.length() > 0 && newBcc.charAt(newBcc.length() - 1) == ',') {
			newBcc.deleteCharAt(newBcc.length() - 1);
		}
		mail.setTo(to.toString());
		mail.setCc(cc.toString());
		mail.setShowSingle(ss.toString());
		mail.setBcc(bcc.toString());
		// 回显收件人状态使用
		mail.setNewTo(newTo.toString());
		mail.setNewCc(newCc.toString());
		mail.setNewBcc(newBcc.toString());
		mail.setNewSs(newSs.toString());
		// 不再用下面这种逻辑
		// 移除发件人记录和非本人的密送记录,and remove not me when show single
		// List<MailRecord> receivers = mail.getReceivers();
		// for (int i = receivers.size() - 1; i >= 0; i--) {
		// MailRecord record = receivers.get(i);
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE)) {
		// showSingle = showSingle | true;
		// }
		// // 内部邮件
		// if (mail.getMailboxId().equals(Constants.IC_MAIL_MAILBOX_INNER)) {
		// // 发送人不是当前用户
		// if (!mail.getSenderUserId().equals(
		// SystemSecurityUtils.getUser().getId())) {
		// // 如果是群发单显，并且接收人不是当前用户remove，接收人只显示自己
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE)
		// && !record.getReceiveUserId().equals(
		// SystemSecurityUtils.getUser().getId())) {
		// receivers.remove(i);
		// continue;
		// }
		// }
		// if (mailParam.getMrid().equals(record.getId().toString())) {
		// mail.setReceiver(record);
		// }
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
		// receivers.remove(i);
		// } else if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_BCC)) {
		// receivers.remove(i);
		// }
		// } else {// 外部
		// if (mailParam.getMrid().equals(record.getId().toString())) {
		// mail.setReceiver(record);
		// }
		// if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
		// receivers.remove(i);
		// } else if (record.getReceiveType().equals(
		// Constants.IC_MAIL_RECEIVETYPE_BCC)) {
		// boolean remove = true;
		// for (Mailbox box : boxList) {
		// if (box.getAddress().equals(record.getReceiveMail())) {
		// remove = false;
		// }
		// }
		// if (remove) {
		// receivers.remove(i);
		// }
		// }
		// }
		// }
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

		model.addAttribute("mailFolderList", this.initMailFolders(request));
		model.addAttribute("mail", mail);
		if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			mailbox = new Mailbox();
			mailbox.setId(mail.getMailboxId());
			model.addAttribute("mailboxAddress", mailService
					.getMailbox(mailbox).getAddress());
		}

		model.addAttribute("currentUserId", SystemSecurityUtils.getUser()
				.getId());
		model.addAttribute("showSingle", showSingle);
		model.addAttribute("folderId", folderId);
		if (mailParam != null) {
			if (mailParam.getReturnURL() != null) {
				if(URLDecoder.decode(mailParam.getReturnURL(),"utf-8").indexOf("?") != -1){
					mail.setReturnURL(mailParam.getReturnURL() + "&returned=true");
					}
					else{
						mail.setReturnURL(mailParam.getReturnURL());
				}
			}
			mail.setIndex(mailParam.getIndex());
			mail.setShowNext(mailParam.isShowNext());
			mail.setShowPre(mailParam.isShowPre());
			model.addAttribute("mailTitle", mailParam.getMailTitle());
			model.addAttribute("senderUserId", mailParam.getSenderUserId());
			model.addAttribute("mailEasyTitle", mailParam.getMailEasyTitle());
			model.addAttribute("senderMail", mailParam.getSenderMail());
			if (mailParam.getMailboxId() != null
					&& mailParam.getMailboxId() == 0) {
				model.addAttribute("isAllMail", "true");
			} else {
				model.addAttribute("isAllMail", "false");
			}
			if (mailParam.getReceivers() != null
					&& mailParam.getReceivers().get(0) != null) {
				model.addAttribute("readFlag", mailParam.getReceivers().get(0)
						.getReadFlag());
			}
			if (mailParam.getSearchReceiveTimeBegin() != null) {
				model.addAttribute("searchReceiveTimeBegin", DateUtils
						.formatDate(mailParam.getSearchReceiveTimeBegin(),
								"yyyy-MM-dd"));
			}
			if (mailParam.getSearchReceiveTimeEnd() != null) {
				model.addAttribute("searchReceiveTimeEnd", DateUtils
						.formatDate(mailParam.getSearchReceiveTimeEnd(),
								"yyyy-MM-dd"));
			}
		}
		model.addAttribute("user", SystemSecurityUtils.getUser());
		return "ic/mail/mailStarDetail";
	}

	/**
	 * 方法描述：星标邮件搜索查询
	 * 
	 * @param mail
	 * @param page
	 * @param request
	 * @return
	 * @author 宋海涛
	 * @version 2014年6月26日上午9:03:44
	 * @throws Exception
	 * @see
	 */
	@RequestMapping(value = "manageSearchStar.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "manageSearchStar", operateDescribe = "搜索星标邮件列表")
	public PageManager manageSearchStar(Mail mail, PageManager page,
			HttpServletRequest request) throws Exception {
		if (StringUtils.isEmpty(mail.getSenderMail())) {
			mail.setSenderMail(null);
		}
		if (StringUtils.isEmpty(mail.getMailTitle())) {
			mail.setMailTitle(null);
		}
		if (StringUtils.isEmpty(mail.getMailContent())) {
			mail.setMailContent(null);
		}
		if (mail.getSearchReceiveTimeEnd() != null) {
			mail.setSearchReceiveTimeEnd(DateUtils.fillTime(mail
					.getSearchReceiveTimeEnd()));
		}
		mail.addOrderByFieldDesc("t.RECEIVE_TIME2");
		PageManager page_ = mailService.searchStar(mail, page);

		List<Mail> mailList = (List<Mail>) page_.getData();
		MailRecord mailRecord = new MailRecord();
		List<MailRecord> recordList = new ArrayList<MailRecord>();
		// 如果列表中只有一条数据，将index设置为-1，js中通过判断是-1来屏蔽掉上一页下一页的功能
		if (mailList.size() == 1 && page_.getTotalPages() == 1) {
			mailList.get(0).setIndex(0);
			mailList.get(0).setShowNext(false);
			mailList.get(0).setShowPre(false);
			mailRecord.setMailId(mailList.get(0).getId());
			// 根据当前记录主表id获得子表记录
			recordList = mailRecordService.queryAll(mailRecord);
			// 如果子表记录数为1，说明该邮件无收件人
			if (recordList.size() == 1
					&& Constants.IC_MAIL_RECEIVETYPE_INNERSENDER
							.equals(recordList.get(0).getReceiveType())) {
				mailList.get(0).setIsHaveReceiveUser("0");
			}
		} else {
			for (int i = 0; i < mailList.size(); i++) {
				mailRecord.setMailId(mailList.get(i).getId());
				// 根据当前记录主表id获得子表记录
				recordList = mailRecordService.queryAll(mailRecord);
				// 如果子表记录数为1，说明该邮件无收件人
				if (recordList.size() == 1
						&& Constants.IC_MAIL_RECEIVETYPE_INNERSENDER
								.equals(recordList.get(0).getReceiveType())) {
					mailList.get(i).setIsHaveReceiveUser("0");
				}
				if (i == 0 && page.getPage() == 0) {
					mailList.get(i).setShowPre(false);
				}
				if (i == mailList.size() - 1
						&& page_.getTotalPages() == page_.getPage()) {
					mailList.get(i).setShowNext(false);
				}
				mailList.get(i).setIndex(
						(page_.getPage() - 1) * page_.getPageRows() + i);
			}
		}
		return page_;
	}

	/**
	 * 方法描述：星标邮件上一封、下一封
	 * 
	 * @param mail
	 * @param folderId
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version 2014年7月1日上午10:46:42
	 * @see
	 */
	@RequestMapping(value = "navigateStar.action")
	@ResponseBody
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "navigateStar", operateDescribe = "星标邮件邮件导航")
	public Mail navigateStar(Mail mail, String folderId,
			HttpServletRequest request) throws Exception {
		if (mail.getSearchReceiveTimeEnd() != null) {
			mail.setSearchReceiveTimeEnd(DateUtils.fillTime(mail
					.getSearchReceiveTimeEnd()));
		}
		if ("".equals(mail.getSenderMail())) {
			mail.setSenderMail(null);
		}

		if ("".equals(mail.getMailTitle())) {
			mail.setMailTitle(null);
		}
		if ("true".equals(mail.getIsAllMail())) {
			mail.setMailboxId(null);
		}
		String returnURL = mail.getReturnURL();
		PageManager page = new PageManager();
		// 设置条数
		page.setPageRows(1);
		String readflag = "";
		if (mail.getReceivers() != null) {
			if (mail.getReceivers().get(0).getReadFlag() != null) {
				readflag = mail.getReceivers().get(0).getReadFlag().toString();
			}
		}
		if ("0".equals(readflag)) {
			if (mail.getForward() > 0) {
				mail.getIndex();
			} else {
				mail.setIndex(mail.getIndex() + mail.getForward());
			}
		} else {
			// 设置页数如果mail.getForward()>0向下翻页，否则像上翻页
			if (mail.getForward() == 1) {
				mail.setIndex(mail.getIndex() + 1);
			} else if (mail.getForward() == -1) {
				mail.setIndex(mail.getIndex() + mail.getForward());
			} else if (mail.getForward() == 2) {
				mail.setIndex(mail.getIndex());
			} else if (mail.getForward() == -2) {
				mail.setIndex(mail.getIndex() - 1);
			}
		}

		// 如果mailTitle为“”将值设为null不做查询
		if ("".equals(mail.getMailTitle())) {
			mail.setMailTitle(null);
		}
		page.setPage(mail.getIndex());
		List<MailRecord> receivers = mail.getReceivers();
		mail.addOrderByFieldDesc("t.RECEIVE_TIME2");
		if (mail.getMailboxId() == null) {
			Mailbox mailbox = new Mailbox();
			mailbox.setCreateUser(SystemSecurityUtils.getUser().getId());
			List<Mailbox> mailboxlist = mailService.getMailboxList(mailbox);
			Mailbox mbox = new Mailbox();
			mbox.setId(1L);
			mailboxlist.add(mbox);
			mail.setMailbox(mailboxlist);
		}
		PageManager page_ = mailService.searchStar(mail, page);

		List<Mail> mailList = (List<Mail>) page_.getData();
		if (mailList != null && mailList.size() > 0) {
			Mail condMail = new Mail();
			condMail.setId(mailList.get(0).getId());
			mail = mailService.get(condMail);

			Mailbox mailbox = new Mailbox();
			mailbox.setId(mail.getMailboxId());
			mailbox = mailService.getMailbox(mailbox);

			// 新写的收件人、抄送人显示逻辑，把收件人、抄送人、群发单显人，分别放在Mail的to,cc,showSingle字段
			// Updated By 张立刚
			receivers = mail.getReceivers();
			// 得到当前用户
			Long userId = SystemSecurityUtils.getUser().getId();
			// 当前用户是否是发送人
			boolean isSender = false;
			for (int i = 0; i < receivers.size(); i++) {
				MailRecord receiver = receivers.get(i);
				Integer type = receiver.getReceiveType();
				// 如果发件人时当前人，设置isSender
				if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(type)
						&& (userId.equals(receiver.getReceiveUserId()) || mailbox
								.getAddress().equals(receiver.getReceiveMail()))) {
					isSender = true;
					break;
				}
			}
			StringBuffer to = new StringBuffer();
			StringBuffer cc = new StringBuffer();
			StringBuffer ss = new StringBuffer();
			StringBuffer bcc = new StringBuffer();
			// 显示人员状态声明
			StringBuffer newTo = new StringBuffer();
			StringBuffer newCc = new StringBuffer();
			StringBuffer newSs = new StringBuffer();
			StringBuffer newBcc = new StringBuffer();

			for (int i = 0; i < receivers.size(); i++) {
				MailRecord record = receivers.get(i);
				Long recordId = mailList.get(0).getReceivers().get(0).getId();
				if (Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
					// 如果该邮件所有收发记录存在id值和当前页记录的子表id相等记录，则将该记录存方法mail的receiver属性中，用于传值for
					// query
					if (record.getId().longValue() == recordId.longValue()) {
						mail.setReceiver(record);
					}

					// 如果是群发单显并且发送人时当前用户
					if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
							.getReceiveType())
							&& record.getReceiveUserId().equals(
									SystemSecurityUtils.getUser().getId())
							|| isSender
							&& (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE
									.equals(record.getReceiveType()))) {

						if (record.getReadDate() != null) {
							newSs.append(
									"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
											+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "("
											+ DateFormatUtils.format(
													record.getReadDate(),
													"yyyy-MM-dd HH:mm:ss")
											+ ")</span></a>").append(",");
							ss.append(record.getReceiveUserName()).append(",");
						} else {
							newSs.append(
									"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "</a>").append(",");
							ss.append(record.getReceiveUserName()).append(",");
						}

					} else if (record.getReceiveType().equals(
							Constants.IC_MAIL_RECEIVETYPE_TO)) {
						if (record.getReadDate() != null) {
							newTo.append(
									"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
											+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "("
											+ DateFormatUtils.format(
													record.getReadDate(),
													"yyyy-MM-dd HH:mm:ss")
											+ ")</span></a>").append(",");
							to.append(record.getReceiveUserName()).append(",");
						} else {
							newTo.append(
									"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "</a>").append(",");
							to.append(record.getReceiveUserName()).append(",");
						}

					} else if (record.getReceiveType().equals(
							Constants.IC_MAIL_RECEIVETYPE_CC)) {
						if (record.getReadDate() != null) {
							newCc.append(
									"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
											+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "("
											+ DateFormatUtils.format(
													record.getReadDate(),
													"yyyy-MM-dd HH:mm:ss")
											+ ")</span></a>").append(",");
							cc.append(record.getReceiveUserName()).append(",");
						} else {
							newCc.append(
									"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "</a>").append(",");
							cc.append(record.getReceiveUserName()).append(",");
						}
					}
					// 可以看到密送：收件人可以看到自己、发件人可以看到所有
					else if ((Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
							.getReceiveType()) && record.getReceiveUserId()
							.equals(userId))
							|| isSender
							&& Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
									.getReceiveType())) {
						if (record.getReadDate() != null) {
							newBcc.append(
									"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
											+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "("
											+ DateFormatUtils.format(
													record.getReadDate(),
													"yyyy-MM-dd HH:mm:ss")
											+ ")</span></a>").append(",");
							bcc.append(record.getReceiveUserName()).append(",");
						} else {
							newBcc.append(
									"<a style = \"cursor :pointer\" class=\"email-receivers\"  onclick=\"showOnlinePerson.showPersonInfo("
											+ record.getReceiveUserId()
											+ ");\">"
											+ record.getReceiveUserName()
											+ "</a>").append(",");
							bcc.append(record.getReceiveUserName()).append(",");
						}
					}
				} else {
					// 如果该邮件所有收发记录存在id值和当前页记录的子表id相等记录，则将该记录存方法mail的receiver属性中，用于传值
					if (record.getId().longValue() == recordId.longValue()) {
						mail.setReceiver(record);
					}
					if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
							.getReceiveType())
							&& record.getReceiveMail().equals(
									mailbox.getAddress())
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
			if (newTo.length() > 0 && newTo.charAt(newTo.length() - 1) == ',') {
				newTo.deleteCharAt(newTo.length() - 1);
			}
			if (newSs.length() > 0 && newSs.charAt(newSs.length() - 1) == ',') {
				newSs.deleteCharAt(newSs.length() - 1);
			}
			if (newCc.length() > 0 && newCc.charAt(newCc.length() - 1) == ',') {
				newCc.deleteCharAt(newCc.length() - 1);
			}
			if (newBcc.length() > 0
					&& newBcc.charAt(newBcc.length() - 1) == ',') {
				newBcc.deleteCharAt(newBcc.length() - 1);
			}

			mail.setTo(to.toString());
			mail.setCc(cc.toString());
			mail.setShowSingle(ss.toString());
			mail.setBcc(bcc.toString());
			// 回显收件人状态使用
			mail.setNewTo(newTo.toString());
			mail.setNewCc(newCc.toString());
			mail.setNewBcc(newBcc.toString());
			mail.setNewSs(newSs.toString());
			// 不再用下面这种逻辑
			// 移除发件人记录和非本人的密送记录
			// receivers = mail.getReceivers();
			// for (int i = receivers.size() - 1; i >= 0; i--) {
			// MailRecord record = receivers.get(i);
			// Long recordId = mailList.get(0).getReceivers().get(0).getId();
			// // 内部邮件
			// if (mail.getMailboxId().equals(Constants.IC_MAIL_MAILBOX_INNER))
			// {
			// // 如果该邮件所有收发记录存在id值和当前页记录的子表id相等记录，则将该记录存方法mail的receiver属性中，用于传值
			// if (record.getId().longValue() == recordId.longValue()) {
			// mail.setReceiver(record);
			// }
			// // 如果是发送记录
			// if (record.getReceiveType().equals(
			// Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
			// receivers.remove(i);
			// // 如果是密送人
			// } else if (record.getReceiveType().equals(
			// Constants.IC_MAIL_RECEIVETYPE_BCC)) {
			// receivers.remove(i);
			// }
			//
			// } else {// 外部
			// // 如果该邮件所有收发记录存在id值和当前页记录的子表id相等记录，则将该记录存方法mail的receiver属性中，用于传值
			// if (record.getId().longValue() == recordId.longValue()) {
			// mail.setReceiver(record);
			// }
			// if (record.getReceiveType().equals(
			// Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
			// receivers.remove(i);
			// } else if (record.getReceiveType().equals(
			// Constants.IC_MAIL_RECEIVETYPE_BCC)) {
			// boolean remove = true;
			// for (Mailbox box : boxList) {
			// if (box.getAddress()
			// .equals(record.getReceiveMail())) {
			// remove = false;
			// }
			// }
			// if (remove) {
			// receivers.remove(i);
			// }
			// }
			// }
			// }
		}
		// 邮件是否加密
		if (Constants.IC_MAIL_ENCRYPTION_YES.equals(mail.getEncryption())) {
			mail.setMailContent(null);
		}

		mail.setReturnURL(returnURL);
		if (page_.getTotalPages() == page_.getPage()) {
			mail.setShowNext(false);
		}
		if (page_.getPage() - 1 == 0) {
			mail.setShowPre(false);
		}
		mail.setIndex(page_.getPage() - 1);
		// 如果下一封记录不为空，并且未读设置该条记录已读
		if (mail.getReceiver() != null) {
			// 如果是未读邮件
			if (!"1".equals(mail.getReceiver().getReadFlag().toString())) {
				// 当点击下一封是设置该邮件为已读
				mailService.setReadStatus(mail, mail.getReceiver().getId()
						.toString(),
						Constants.IC_MAIL_MAILSTATUS_READED.toString());
			}
		}
		mail.setWrongPassword(true);
		return mail;
	}

	@RequestMapping(value = "validRemind.action")
	@ResponseBody
	@ActionLog(operateModelNm = "邮件", operateFuncNm = "validRemind", operateDescribe = "发送邮件短信提醒时被提醒人是否存在电话号")
	public Map<String, Object> validRemind(String userIds,
			HttpServletRequest request) throws Exception {

		return mailService.validRemind(userIds);
	}

	/**
	 * 方法描述：邮件追回
	 * 
	 * @param mail
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 曹杨
	 * @version 2014年7月22日下午2:04:16
	 * @see
	 */
	@ActionLog(operateModelNm = "邮件", operateFuncNm = "mailRecover", operateDescribe = "对邮件进行追回")
	@RequestMapping(value = "mailRecover.action")
	@ResponseBody
	public Map<String, Object> mailRecover(@Valid Mail mail,
			BindingResult result, HttpServletRequest request) throws Exception {
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 邮件召回
		Integer flag = mailService.mailRecover(mail);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_OA_IC_077"));
		} else if (flag == 3) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_IC_087"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
					MessageUtils.getMessage("JC_OA_IC_078"));
		}

		return resultMap;
	}

	/**
	 * 方法描述：邮件追回
	 * 
	 * @param mail
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 曹杨
	 * @version 2014年7月22日下午2:04:16
	 * @see
	 */
	@ActionLog(operateModelNm = "邮件", operateFuncNm = "getDiplayName", operateDescribe = "根据用户id获取用户姓名")
	@RequestMapping(value = "getDiplayName.action")
	@ResponseBody
	public Map<String, Object> getDiplayName(String ids,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String disPlayName = UserUtils.getUser(Long.parseLong(ids))
				.getDisplayName();
		resultMap.put("displayName", disPlayName);
		return resultMap;
	}

	/**
	 * 方法描述：未读邮件Portal方法
	 * 
	 * @param model
	 * @param loginlog
	 * @param request
	 * @return
	 * @throws Exception
	 * @author zhangligang
	 * @version 2014年7月23日下午1:31:22
	 * @see
	 */
	@RequestMapping(value = "portalUnread.action")
	@ActionLog(operateModelNm = "邮件", operateFuncNm = "portalUnread", operateDescribe = "未读邮件Portal")
	public String portalUnread(Model model, Loginlog loginlog,
			HttpServletRequest request) throws Exception {
		// 门户引用类固定代码---start---
		model = PortalUtils.getPortalParameter(model, request);
		// 门户引用类固定代码---end---
		return "ic/mail/mailPortletUnread";
	}

	/**
	 * 方法描述：未读邮件Portal获取数据方法
	 * 
	 * @return
	 * @throws Exception
	 * @author zhangligang
	 * @version 2014年7月23日下午1:31:22
	 * @see
	 */
	@RequestMapping(value = "getportalUnread.action")
	@ResponseBody
	public Map<String, Object> getportalUnread(String funViewType,
			int dataRows, HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		Mail mail = new Mail();
		mail.setReceivers(new ArrayList<MailRecord>());
		mail.getReceivers().add(new MailRecord());
		mail.setMailboxId(1L);
		PageManager page = new PageManager();
		// modify by lihongyu at 2015-03-16 start
		//page.setPageRows(dataRows);
		if(dataRows!=0){
			page.setPageRows(dataRows);
		}
		// modify by lihongyu at 2015-03-16 end
		PageManager page_ = this.manageUnReadList(mail, page, request);
		result.put("mailList", page_.getData());
		result.put("mailListSize", page_.getTotalCount());
		result.put("dataRows", dataRows);

		return result;
	}

	/**
	 * 方法描述：门户邮件预览
	 * 
	 * @param mail
	 * @param model
	 * @return
	 * @author zhanglg
	 * @version 2014年5月14日下午3:07:21
	 * @throws CustomException
	 * @see
	 */
	@RequestMapping(value = "managePortletView.action")
	@ActionLog(operateModelNm = "电子邮件", operateFuncNm = "managePortletView", operateDescribe = "门户邮件预览")
	public String managePortletView(Mail unreadmail, String password,
			Model model, HttpServletRequest request) throws Exception {
		Mail mail = new Mail();
		mail.setId(unreadmail.getId());
		mail = mailService.get(mail);
		mailService.setReadStatus(mail, unreadmail.getMrid(),
				Constants.IC_MAIL_MAILSTATUS_READED.toString());
		StringBuffer to = new StringBuffer();
		StringBuffer cc = new StringBuffer();
		StringBuffer bcc = new StringBuffer();
		StringBuffer ss = new StringBuffer();
		// 显示人员状态声明
		StringBuffer newTo = new StringBuffer();
		StringBuffer newCc = new StringBuffer();
		StringBuffer newSs = new StringBuffer();
		StringBuffer newBcc = new StringBuffer();
		// 判断是否为内部邮箱
		boolean isInner = Constants.IC_MAIL_MAILBOX_INNER.equals(mail
				.getMailboxId());
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
			if (Constants.IC_MAIL_RECEIVETYPE_INNERSENDER.equals(type)
					&& (userId.equals(receiver.getReceiveUserId()) || mailbox
							.getAddress().equals(receiver.getReceiveMail()))) {
				isSender = true;
				break;
			}
		}
		for (int i = 0; i < receivers.size(); i++) {
			MailRecord record = receivers.get(i);
			if (isInner) {
				if (unreadmail.getMrid().equals(record.getId().toString())) {
					mail.setReceiver(record);
				}
				if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
						.getReceiveType())
						&& record.getReceiveUserId().equals(userId)
						|| isSender
						&& (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE
								.equals(record.getReceiveType()))) {
					if (record.getReadDate() != null) {
						newSs.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						ss.append(record.getReceiveUserName()).append(",");
					} else {
						newSs.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						ss.append(record.getReceiveUserName()).append(",");
					}
				} else if (Constants.IC_MAIL_RECEIVETYPE_TO.equals(record
						.getReceiveType())) {
					if (record.getReadDate() != null) {
						newTo.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						to.append(record.getReceiveUserName()).append(",");
					} else {
						newTo.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						to.append(record.getReceiveUserName()).append(",");
					}
				} else if (Constants.IC_MAIL_RECEIVETYPE_CC.equals(record
						.getReceiveType())) {
					if (record.getReadDate() != null) {
						newCc.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						cc.append(record.getReceiveUserName()).append(",");
					} else {
						newCc.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						cc.append(record.getReceiveUserName()).append(",");
					}
				}
				// 可以看到密送：收件人可以看到自己、发件人可以看到所有
				else if ((Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
						.getReceiveType()) && record.getReceiveUserId().equals(
						userId))
						|| isSender
						&& (Constants.IC_MAIL_RECEIVETYPE_BCC.equals(record
								.getReceiveType()))) {
					if (record.getReadDate() != null) {
						newBcc.append(
								"<span style=\"font-weight:bolder\" class=\"email-f-c\">"
										+ "<a style = \" cursor :pointer;\" onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName()
										+ "("
										+ DateFormatUtils.format(
												record.getReadDate(),
												"yyyy-MM-dd HH:mm:ss")
										+ ")</span></a>").append(",");
						bcc.append(record.getReceiveUserName()).append(",");
					} else {
						newBcc.append(
								"<a style = \"cursor :pointer\" class=\"email-receivers\"  onclick=\"showOnlinePerson.showPersonInfo("
										+ record.getReceiveUserId()
										+ ");\">"
										+ record.getReceiveUserName() + "</a>")
								.append(",");
						bcc.append(record.getReceiveUserName()).append(",");
					}
				}

			} else {
				if (unreadmail.getMrid().equals(record.getId().toString())) {
					mail.setReceiver(record);
				}
				if (Constants.IC_MAIL_RECEIVETYPE_SHOWSINGLE.equals(record
						.getReceiveType())
						&& record.getReceiveMail().equals(mailbox.getAddress())
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
		// 去掉结尾逗号
		if (to.length() > 1) {
			if (to.charAt(to.length() - 1) == ',') {
				to.deleteCharAt(to.length() - 1);
			}
		}
		if (cc.length() > 1) {
			if (cc.charAt(cc.length() - 1) == ',') {
				cc.deleteCharAt(cc.length() - 1);
			}
		}
		if (bcc.length() > 1) {
			if (bcc.charAt(bcc.length() - 1) == ',') {
				bcc.deleteCharAt(bcc.length() - 1);
			}
		}
		if (ss.length() > 1) {
			if (ss.charAt(ss.length() - 1) == ',') {
				ss.deleteCharAt(ss.length() - 1);
			}
		}
		if (newTo.length() > 0 && newTo.charAt(newTo.length() - 1) == ',') {
			newTo.deleteCharAt(newTo.length() - 1);
		}
		if (newSs.length() > 0 && newSs.charAt(newSs.length() - 1) == ',') {
			newSs.deleteCharAt(newSs.length() - 1);
		}
		if (newCc.length() > 0 && newCc.charAt(newCc.length() - 1) == ',') {
			newCc.deleteCharAt(newCc.length() - 1);
		}
		if (newBcc.length() > 0 && newBcc.charAt(newBcc.length() - 1) == ',') {
			newBcc.deleteCharAt(newBcc.length() - 1);
		}
		mail.setTo(to.toString());
		mail.setCc(cc.toString());
		mail.setBcc(bcc.toString());
		mail.setShowSingle(ss.toString());
		// 回显收件人状态使用
		mail.setNewTo(newTo.toString());
		mail.setNewCc(newCc.toString());
		mail.setNewBcc(newBcc.toString());
		mail.setNewSs(newSs.toString());

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
		if (!Constants.IC_MAIL_MAILBOX_INNER.equals(mail.getMailboxId())) {
			mailbox = new Mailbox();
			mailbox.setId(mail.getMailboxId());
			model.addAttribute("mailboxAddress", mailService
					.getMailbox(mailbox).getAddress());
		}
		if (unreadmail != null) {
			mail.setIndex(unreadmail.getIndex());
			mail.setShowNext(unreadmail.isShowNext());
		}
		model.addAttribute("currentUserId", SystemSecurityUtils.getUser()
				.getId());
		model.addAttribute("user", SystemSecurityUtils.getUser());
		model.addAttribute("mail", mail);
		model.addAttribute("isFromTo", true);
		model.addAttribute("recId", unreadmail.getMrid());
		return "ic/mail/mailPortlet";
	}

	/**
	 * 方法描述：
	 * 
	 * @param id
	 *            根据附件Id获取邮件
	 * @param request
	 * @return
	 * @throws Exception
	 * @author zhangligang
	 * @version 2014年7月25日上午10:17:53
	 * @see
	 */
	@ActionLog(operateModelNm = "邮件", operateFuncNm = "getMailByAttach", operateDescribe = "根据附件id获取邮件")
	@RequestMapping(value = "getMailByAttach.action")
	@ResponseBody
	public Map<String, Object> getMailByAttach(String id,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Mail mail = mailService.getMailByAttach(id);
		resultMap.put("mail", mail);
		return resultMap;
	}

	/**
	 * 方法描述：
	 * 
	 * @param id
	 *            检查邮件密码是否正确
	 * @param request
	 * @return
	 * @throws Exception
	 * @author zhangligang
	 * @version 2014年7月25日上午10:18:00
	 * @see
	 */
	@ActionLog(operateModelNm = "邮件", operateFuncNm = "checkEncryptionPass", operateDescribe = "检查邮件密码")
	@RequestMapping(value = "checkEncryptionPass.action")
	@ResponseBody
	public Map<String, Object> checkEncryptionPass(String id, String password,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Mail mail = new Mail();
		mail.setId(new Long(id));
		mail = mailService.get(mail);
		resultMap.put("wrong",
				password.equals(mail.getEncryptionPass()) ? false : true);
		resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,
				MessageUtils.getMessage("JC_OA_IC_080"));
		return resultMap;
	}

	/**
	 * 方法描述：互动交流默认跳转
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 曹杨
	 * @version 2014年9月22日上午8:56:10
	 * @see
	 */
	@RequestMapping(value = "interaction.action")
	@ActionLog(operateModelNm = "互动交流", operateFuncNm = "interaction", operateDescribe = "互动交流默认跳转")
	public String interaction(HttpServletRequest request) throws Exception {
		return "ic/mail/interaction";
	}

	/**
	 * 方法描述：未读邮件Portal方法
	 * 
	 * @param model
	 * @param loginlog
	 * @param request
	 * @return
	 * @throws Exception
	 * @author zhangligang
	 * @version 2014年7月23日下午1:31:22
	 * @see
	 */
	@RequestMapping(value = "portalMailread.action")
	@ActionLog(operateModelNm = "邮件", operateFuncNm = "portalUnread", operateDescribe = "未读邮件Portal")
	public String portalMailread(Model model, Loginlog loginlog,
			HttpServletRequest request) throws Exception {
		// 门户引用类固定代码---start---
		model = PortalUtils.getPortalParameter(model, request);
		// 门户引用类固定代码---end---
		String parameter = request.getParameter("parameter");// 携带业务参数

		if (!StringUtils.isEmpty(parameter)) {// 栏目编号
			// info.setColumnId(Long.parseLong(parameter));
			model.addAttribute("foldId", Long.parseLong(parameter));
		} else
			model.addAttribute("foldId", null);

		return "ic/mail/mailPortletMailread";
	}

	/**
	 * 方法描述：未读邮件Portal获取数据方法
	 * 
	 * @return
	 * @throws Exception
	 * @author zhangligang
	 * @version 2014年7月23日下午1:31:22
	 * @see
	 */
	@RequestMapping(value = "getportalMailread.action")
	@ResponseBody
	public Map<String, Object> getportalMailread(String foldId,
			String funViewType, int dataRows, HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		Mail mail = new Mail();
		MailRecord mailRecord = new MailRecord();
		mail.setReceivers(new ArrayList<MailRecord>());
		mail.setMailboxId(1L);
		// mail.setMailFolderId(Long.valueOf(foldId));
		PageManager page = new PageManager();
		// modify by lihongyu at 2015-03-20 start
//		page.setPageRows(dataRows);
		if(dataRows!=0){
			page.setPageRows(dataRows);
		}
		// modify by lihongyu at 2015-03-20 end
		PageManager page_ = null;
		if ("555".equals(foldId)) {
			mailRecord.setStarMail(1);
			mail.getReceivers().add(mailRecord);
			page_ = this.manageStarList(mail, page, request);
		} else {
			mailRecord.setFolderId(Long.valueOf(foldId));
			mail.getReceivers().add(mailRecord);
			page_ = this.manageList(mail, page, request);
		}
		result.put("mailList", page_.getData());
		result.put("mailListSize", page_.getTotalCount());
		result.put("dataRows", dataRows);

		return result;
	}
	
	/**
	 * 方法描述：未读邮件Portal方法
	 * 
	 * @param model
	 * @param loginlog
	 * @param request
	 * @return manage.action/1
	 * @throws Exception
	 * @author zhangligang
	 * @version 2014年7月23日下午1:31:22
	 * @see
	 */
	@RequestMapping(value = "viewPage.action")
	@ActionLog(operateModelNm = "邮件", operateFuncNm = "portalUnread", operateDescribe = "未读邮件Portal")
	public String aaaaa(HttpServletRequest request) throws Exception {
		return "ao/viewPage";
	}
}