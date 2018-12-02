package com.jc.oa.ic.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.AndTerm;
import javax.mail.search.IntegerComparisonTerm;
import javax.mail.search.MessageIDTerm;
import javax.mail.search.SizeTerm;

import org.apache.commons.lang.StringUtils;

import com.jc.oa.ic.IcException;
import com.jc.oa.ic.domain.Mail;
import com.jc.oa.ic.domain.MailRecord;
import com.jc.oa.ic.domain.Mailbox;
import com.jc.oa.ic.vo.IdentityAuthenticator;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.content.domain.Attach;
import com.jc.system.security.util.SettingUtils;
import com.sun.mail.util.MailSSLSocketFactory;

/**
 * 
 * @title 互动交流
 * @description 系统外邮件发送工具类 Copyright (c) 2014 Jiachengnet.com Inc. All Rights
 *              Reserved Company 长春嘉诚网络工程有限公司
 * @author zhanglg
 * @version 2014年5月5日
 */
public class MailSender {
	/**
	 * 发送邮件方法
	 * 
	 * @param mailbox
	 *            邮箱配置
	 * @param mail
	 *            邮件实体
	 * @throws MessagingException
	 * @throws IcException
	 * @throws UnsupportedEncodingException
	 * @throws GeneralSecurityException
	 * @throws Exception
	 */
	public void sendMail(Mailbox mailbox, Mail mail) throws AddressException,
			MessagingException, UnsupportedEncodingException, IcException,
			GeneralSecurityException {

		Session session = this.createSession(mailbox);
		// 由 Session 对象获得 Transport 对象
		Transport transport = session.getTransport();
		// 发送用户名、密码连接到指定的 smtp 服务器
		transport.connect(mailbox.getSenderService(), mailbox.getUsername(),
				mailbox.getMailPassword());

		Message msg = this.createMessage(session, mailbox, mail);
		transport.sendMessage(msg, msg.getAllRecipients());
		String messageId = msg.getHeader("Message-ID")[0].replace("<", "")
				.replace(">", "");
		mail.setMessageId(messageId);
		transport.close();
	}

	/**
	 * 回复邮件
	 * 
	 * @param mailbox
	 *            邮箱配置
	 * @param mail
	 *            邮件实体，一定要有原邮件的RFC822消息ID
	 * @param reply
	 *            回复标志（true：回复给发送着；false：回复给所有人）
	 * @throws IcException
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @throws GeneralSecurityException
	 * @throws Exception
	 */
	public void replymail(Mailbox mailbox, Mail mail, boolean reply)
			throws IcException, AddressException, MessagingException,
			UnsupportedEncodingException, GeneralSecurityException {
		// 解密邮箱密码
		CryptUtil ctypt;
		String mailPassword = null;
		try {
			ctypt = new CryptUtil();
			mailPassword = ctypt.decrypt(mailbox.getMailPassword());
			if (mailPassword != null && !"".equals(mailPassword)) {
				if (!mailPassword.equals(mailbox.getMailPassword())) {
					mailbox.setMailPassword(mailPassword);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (mail.getMessageId() == null
				|| StringUtils.isEmpty(mail.getMessageId())) {
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_043"));
		}
		Session session = createSession(mailbox);
		Store store = session.getStore("imap");
		store.connect(mailbox.getReceiveService(), mailbox.getUsername(),
				mailbox.getMailPassword());

		Folder[] fs = store.getDefaultFolder().list();
		MessageIDTerm term = new MessageIDTerm(mail.getMessageId());
		// 设置Size查询条件，腾讯邮箱必须用
		SizeTerm sizeTerm = new SizeTerm(IntegerComparisonTerm.GE, 1024);
		AndTerm andTerm = new AndTerm(term, sizeTerm);
		boolean found = false;
		for (Folder folder : fs) {
			// 不收包含以下关键字的文件夹
			if (folder == null || folder.exists() == false
					|| folder.getFullName().indexOf("删除") > -1
					|| folder.getFullName().indexOf("发送") > -1
					|| folder.getFullName().indexOf("草稿") > -1
					|| folder.getFullName().indexOf("发件") > -1
					|| folder.getFullName().indexOf("垃圾") > -1) {
				continue;
			}
			
			Message[] messages = new Message[0];
			// 特殊处理：如果是QQ邮箱，使用Size查询条件
			if (mailbox.getReceiveService().toLowerCase().indexOf("qq.com") > 0) {
				folder.open(Folder.READ_ONLY);
				messages = folder.search(andTerm);
			} else {
				folder.open(Folder.READ_WRITE);
				messages = folder.search(term);
			}
			if (messages != null) {
				for (Message message : messages) {
					Message replyMessage = message.reply(reply);
					if (Constants.IC_MAIL_PRESSING_YES.equals(mail
							.getPressing())) {
						replyMessage.addHeader("X-Priority", "1");
					}
					replyMessage.setFrom(new InternetAddress(mailbox
							.getAddress(), mailbox.getUsername()));
					if (mail.getMailTitle() != null) {
						replyMessage.setSubject(mail.getMailTitle());
					}
					// 设置邮件消息发送的时间
					replyMessage.setSentDate(new Date());

					// 创建一个"related"型的组合 MimeMultipart 对象
					MimeMultipart allPart = new MimeMultipart("related");
					// 创建邮件的各个 MimeBodyPart 部分
					MimeBodyPart content = createContent(mail.getMailContent(),
							"text/html; charset=utf-8");
					content.setDisposition(Part.INLINE);
					allPart.addBodyPart(content);
					// TODO 转发邮件带附件问题
					// if (mail.getAttachs() != null) {
					// for (Attach attach : mail.getAttachs()) {
					// MimeBodyPart attachmentPart = createAttachment(attach);
					// attachmentPart.setDescription(Part.ATTACHMENT);
					// allPart.addBodyPart(attachmentPart);
					// }
					// }

					// 将上面混合型的 MimeMultipart 对象作为邮件内容并保存
					replyMessage.setContent(allPart);
					replyMessage.saveChanges();

					// 由 Session 对象获得 Transport 对象
					Transport transport = session.getTransport("smtp");
					// 发送用户名、密码连接到指定的 smtp 服务器
					transport.connect(mailbox.getSenderService(),
							mailbox.getUsername(), mailbox.getMailPassword());
					transport.send(replyMessage);
					String messageId = replyMessage.getHeader("Message-ID")[0]
							.replace("<", "").replace(">", "");
					mail.setMessageId(messageId);
					transport.close();

					found = true;
					break;
				}
			}
			folder.close(false);
			if (found) {
				break;
			}
		}
		store.close();
	}

	/**
	 * 转发邮件
	 * 
	 * @param mailbox
	 *            邮箱配置
	 * @param mail
	 *            邮件实体，一定要有原邮件的RFC822消息ID
	 * @throws IcException
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @throws GeneralSecurityException
	 * @throws Exception
	 */
	public void forwardingmail(Mailbox mailbox, Mail mail) throws IcException,
			AddressException, MessagingException, UnsupportedEncodingException,
			GeneralSecurityException {
		// 解密邮箱密码
		CryptUtil ctypt;
		String mailPassword = null;
		try {
			ctypt = new CryptUtil();
			mailPassword = ctypt.decrypt(mailbox.getMailPassword());
			if (mailPassword != null && !"".equals(mailPassword)) {
				if (!mailPassword.equals(mailbox.getMailPassword())) {
					mailbox.setMailPassword(mailPassword);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (mail.getMessageId() == null
				|| StringUtils.isEmpty(mail.getMessageId())) {
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_043"));
		}
		Session session = createSession(mailbox);
		Store store = session.getStore("imap");
		store.connect(mailbox.getReceiveService(), mailbox.getUsername(),
				mailbox.getMailPassword());

		Folder[] fs = store.getDefaultFolder().list();
		MessageIDTerm term = new MessageIDTerm(mail.getMessageId());
		// 设置Size查询条件，腾讯邮箱必须用
		SizeTerm sizeTerm = new SizeTerm(IntegerComparisonTerm.GE, 1024);
		AndTerm andTerm = new AndTerm(term, sizeTerm);
		boolean found = false;
		for (Folder folder : fs) {
			// 不收包含以下关键字的文件夹
			if (folder == null || folder.exists() == false
					|| folder.getFullName().indexOf("删除") > -1
					|| folder.getFullName().indexOf("发送") > -1
					|| folder.getFullName().indexOf("草稿") > -1
					|| folder.getFullName().indexOf("发件") > -1
					|| folder.getFullName().indexOf("垃圾") > -1) {
				continue;
			}
			Message[] messages = new Message[0];
			// 特殊处理：如果是QQ邮箱，使用Size查询条件
			if (mailbox.getReceiveService().toLowerCase().indexOf("qq.com") > 0) {
				folder.open(Folder.READ_ONLY);
				messages = folder.search(andTerm);
			} else {
				folder.open(Folder.READ_WRITE);
				messages = folder.search(term);
			}
			if (messages != null) {
				for (Message message : messages) {

					Message replyMessage = new MimeMessage(session);
					if (Constants.IC_MAIL_PRESSING_YES.equals(mail
							.getPressing())) {
						replyMessage.addHeader("X-Priority", "1");
					}
					if (mail.getMailTitle() != null) {
						replyMessage.setSubject(mail.getMailTitle());
					}
					replyMessage.setSubject(mail.getMailTitle());
					replyMessage.setFrom(new InternetAddress(mailbox
							.getAddress(), mailbox.getUsername()));

					ArrayList<InternetAddress> toReceivers = new ArrayList<InternetAddress>();
					ArrayList<InternetAddress> ccReceivers = new ArrayList<InternetAddress>();
					ArrayList<InternetAddress> bccReceivers = new ArrayList<InternetAddress>();
					ArrayList<InternetAddress> tosReceivers = new ArrayList<InternetAddress>();
					if (mail.getReceivers() == null
							|| mail.getReceivers().size() == 0) {
						throw new IcException(
								MessageUtils.getMessage("JC_OA_IC_044"));
					}
					if (mail.getMailContent() == null) {
						throw new IcException(
								MessageUtils.getMessage("JC_OA_IC_045"));
					}
					for (MailRecord receiver : mail.getReceivers()) {
						switch (receiver.getReceiveType()) {
						case 0:
							toReceivers.add(new InternetAddress(receiver
									.getReceiveMail()));
							break;
						case 1:
							ccReceivers.add(new InternetAddress(receiver
									.getReceiveMail()));
							break;
						case 2:
							bccReceivers.add(new InternetAddress(receiver
									.getReceiveMail()));
							break;
						case 4:
							tosReceivers.add(new InternetAddress(receiver
									.getReceiveMail()));
							break;
						default:
							toReceivers.add(new InternetAddress(receiver
									.getReceiveMail()));
							break;
						}
					}

					InternetAddress[] toArray = new InternetAddress[toReceivers
							.size()];
					replyMessage.setRecipients(RecipientType.TO,
							toReceivers.toArray(toArray));
					InternetAddress[] ccArray = new InternetAddress[ccReceivers
							.size()];
					replyMessage.setRecipients(RecipientType.CC,
							ccReceivers.toArray(ccArray));
					InternetAddress[] bccArray = new InternetAddress[bccReceivers
							.size()];
					replyMessage.setRecipients(RecipientType.BCC,
							bccReceivers.toArray(bccArray));

					// 设置邮件消息发送的时间
					replyMessage.setSentDate(new Date());

					// 创建一个"related"型的组合 MimeMultipart 对象
					MimeMultipart allPart = new MimeMultipart("related");
					// 创建邮件的各个 MimeBodyPart 部分
					MimeBodyPart content = createContent(mail.getMailContent(),
							"text/html; charset=utf-8");
					content.setDisposition(Part.INLINE);
					allPart.addBodyPart(content);
					try {
						// 转发邮件带附件问题，先去读取远程服务器上的附件（既原始邮件的附件），如果未读到，再读取本地服务器上的附件
						if (mail.getAttachs() != null) {
							for (Attach attach : mail.getAttachs()) {
								DataHandler handler = null;

								Object body = message.getContent();
								if (body instanceof Multipart) {
									handler = processMultipartLookupAttach(
											(Multipart) body, mail,
											attach.getFileName());
								} else {
									handler = getAttachInputStream(message,
											mail, attach.getFileName());
								}
								if (handler != null) {
									MimeBodyPart attachmentPart = createAttachment(
											handler, attach);
									// attachmentPart.setDataHandler(handler);
									allPart.addBodyPart(attachmentPart);
								} else {
									MimeBodyPart attachmentPart = createAttachment(attach);
									attachmentPart
											.setDescription(Part.ATTACHMENT);
									allPart.addBodyPart(attachmentPart);
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
						throw new IcException(e);
					}
					// 将上面混合型的 MimeMultipart 对象作为邮件内容并保存
					replyMessage.setContent(allPart);
					// replyMessage.saveChanges();

					// 由 Session 对象获得 Transport 对象
					Transport transport = session.getTransport("smtp");
					// 发送用户名、密码连接到指定的 smtp 服务器
					transport.connect(mailbox.getSenderService(),
							mailbox.getUsername(), mailbox.getMailPassword());
					transport.send(replyMessage);
					String messageId = replyMessage.getHeader("Message-ID")[0]
							.replace("<", "").replace(">", "");
					mail.setMessageId(messageId);
					transport.close();

					found = true;
					break;
				}
			}
			folder.close(false);
			if (found) {
				break;
			}
		}
		store.close();
	}

	/**
	 * 创建会话
	 * 
	 * @param mailbox
	 *            邮箱配置
	 * @return 会话实例
	 * @throws GeneralSecurityException
	 */
	private Session createSession(Mailbox mailbox)
			throws GeneralSecurityException {
		// 解密邮箱密码
		CryptUtil ctypt;
		String mailPassword = null;
		try {
			ctypt = new CryptUtil();
			mailPassword = ctypt.decrypt(mailbox.getMailPassword());
			if (mailPassword != null && !"".equals(mailPassword)) {
				if (!mailPassword.equals(mailbox.getMailPassword())) {
					mailbox.setMailPassword(mailPassword);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Properties property = new Properties();
		property.setProperty("mail.transport.protocol", "smtp");
		property.setProperty("mail.smtp.auth", "true");
		property.setProperty("mail.smtp.host", mailbox.getSenderService());
		if (mailbox.getSenderPort() != null) {
			property.setProperty("mail.smtp.port", mailbox.getSenderPort()
					.toString());
		}
		if (mailbox.getSenderPort() != null) {
			property.setProperty("mail.imap.port", mailbox.getReceivePort()
					.toString());
		}
		property.setProperty("mail.smtp.user", mailbox.getUsername());
		property.setProperty("mail.smtp.pass", mailbox.getMailPassword());

		if ("1".equals(mailbox.getSenderSSL())) {
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			property.put("mail.smtp.ssl.enable", "true");
			property.put("mail.smtp.ssl.socketFactory", sf);
		}
		// 设置会话参数：根据配置文件内容，设置SSL通信
		if ("1".equals(mailbox.getReceiveSSL())) {
			MailSSLSocketFactory msf = new MailSSLSocketFactory();
			msf.setTrustAllHosts(true);
			property.put("mail.imap.ssl.enable", "true");
			property.put("mail.imap.ssl.socketFactory", msf);
		}
		Session session = Session.getInstance(
				property,
				new IdentityAuthenticator(mailbox.getUsername(), mailbox
						.getMailPassword()));

		// 启动JavaMail调试功能，可以返回与SMTP服务器交互的命令信息
		// 可以从控制台中看一下服务器的响应信息
		session.setDebug(true);
		return session;
	}

	/**
	 * 根据传入的 Session 对象创建混合型的 MIME消息
	 * 
	 * @param session
	 *            会话实例
	 * @param mailbox
	 *            邮箱配置
	 * @param mail
	 *            邮件实体
	 * @return MIME消息实体
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @throws IcException
	 * @throws Exception
	 */
	private MimeMessage createMessage(Session session, Mailbox mailbox,
			Mail mail) throws MessagingException, UnsupportedEncodingException,
			IcException {
		MimeMessage msg = new MimeMessage(session);
		if (Constants.IC_MAIL_PRESSING_YES.equals(mail.getPressing())) {
			msg.addHeader("X-Priority", "1");
		}

		msg.setSubject(mail.getMailTitle());
		msg.setFrom(new InternetAddress(mailbox.getAddress(), mailbox
				.getUsername()));

		ArrayList<InternetAddress> toReceivers = new ArrayList<InternetAddress>();
		ArrayList<InternetAddress> ccReceivers = new ArrayList<InternetAddress>();
		ArrayList<InternetAddress> bccReceivers = new ArrayList<InternetAddress>();
		ArrayList<InternetAddress> tosReceivers = new ArrayList<InternetAddress>();
		if (mail.getReceivers() == null || mail.getReceivers().size() == 0) {
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_044"));
		}
		if (mail.getMailContent() == null) {
			throw new IcException(MessageUtils.getMessage("JC_OA_IC_045"));
		}
		for (MailRecord receiver : mail.getReceivers()) {
			switch (receiver.getReceiveType()) {
			case 0:
				toReceivers.add(new InternetAddress(receiver.getReceiveMail()));
				break;
			case 1:
				ccReceivers.add(new InternetAddress(receiver.getReceiveMail()));
				break;
			case 2:
				bccReceivers
						.add(new InternetAddress(receiver.getReceiveMail()));
				break;
			case 4:
				tosReceivers
						.add(new InternetAddress(receiver.getReceiveMail()));
				break;
			default:
				toReceivers.add(new InternetAddress(receiver.getReceiveMail()));
				break;
			}
		}

		InternetAddress[] toArray = new InternetAddress[toReceivers.size()];
		msg.setRecipients(RecipientType.TO, toReceivers.toArray(toArray));
		InternetAddress[] ccArray = new InternetAddress[ccReceivers.size()];
		msg.setRecipients(RecipientType.CC, ccReceivers.toArray(ccArray));
		InternetAddress[] bccArray = new InternetAddress[bccReceivers.size()];
		msg.setRecipients(RecipientType.BCC, bccReceivers.toArray(bccArray));

		// 设置邮件消息发送的时间
		msg.setSentDate(new Date());

		// 创建一个"related"型的组合 MimeMultipart 对象
		MimeMultipart allPart = new MimeMultipart("related");
		// 创建邮件的各个 MimeBodyPart 部分
		MimeBodyPart content = createContent(mail.getMailContent(),
				"text/html; charset=utf-8");
		content.setDisposition(Part.INLINE);
		allPart.addBodyPart(content);
		// 发送附件
		if (mail.getAttachs() != null && mail.getAttachs().size() > 0) {
			for (Attach attach : mail.getAttachs()) {
				MimeBodyPart attachmentPart = createAttachment(attach);
				attachmentPart.setDescription(Part.ATTACHMENT);
				allPart.addBodyPart(attachmentPart);
			}
		}

		// 将上面混合型的 MimeMultipart 对象作为邮件内容并保存
		msg.setContent(allPart);
		// msg.saveChanges();
		return msg;
	}

	/**
	 * 创建正文部分
	 * 
	 * @param body
	 *            正文内容
	 * @param mimeType
	 *            MIME类型
	 * @return 正文部分实体
	 * @throws MessagingException
	 */
	private MimeBodyPart createContent(String body, String mineType)
			throws MessagingException {
		// 用于保存最终正文部分
		MimeBodyPart contentBody = new MimeBodyPart();
		contentBody.setContent(body, mineType);
		return contentBody;
	}

	/**
	 * 根据传入的邮件正文body和文件路径创建图文并茂的正文部分
	 * 
	 * @param body
	 * @param fileName
	 * @return
	 * @throws MessagingException
	 * @throws Exception
	 */
	private MimeBodyPart createMultiMediaContent(String body, String fileName)
			throws MessagingException {
		// 用于保存最终正文部分
		MimeBodyPart contentBody = new MimeBodyPart();
		// 用于组合文本和图片，"related"型的MimeMultipart对象
		MimeMultipart contentMulti = new MimeMultipart("related");

		// 正文的文本部分
		MimeBodyPart textBody = new MimeBodyPart();
		textBody.setContent(body, "text/html;charset=utf8");
		contentMulti.addBodyPart(textBody);

		// 正文的图片部分
		MimeBodyPart jpgBody = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(fileName);
		jpgBody.setDataHandler(new DataHandler(fds));
		jpgBody.setContentID("logo_jpg");
		contentMulti.addBodyPart(jpgBody);

		// 将上面"related"型的 MimeMultipart 对象作为邮件的正文
		contentBody.setContent(contentMulti);
		return contentBody;
	}

	/**
	 * 根据传入的文件路径创建附件并返回
	 * 
	 * @param attach
	 *            附件文件实体
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	private MimeBodyPart createAttachment(Attach attach)
			throws MessagingException, UnsupportedEncodingException {
		MimeBodyPart attachmentPart = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(attach.getResourcesName());
		attachmentPart.setDataHandler(new DataHandler(fds));
		attachmentPart.setFileName(MimeUtility.encodeWord(attach.getName(),
				"utf8", null));
		attachmentPart.setDisposition(Part.ATTACHMENT);
		return attachmentPart;
	}

	/**
	 * 根据传入的文件路径创建附件并返回
	 * 
	 * @param attach
	 *            附件文件实体
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 * @throws Exception
	 */
	private MimeBodyPart createAttachment(DataHandler handler, Attach attach)
			throws MessagingException, IOException {
		MimeBodyPart attachmentPart = new MimeBodyPart();
		DataSource ds = handler.getDataSource();
		DataHandler newHandler = new DataHandler(ds);
		attachmentPart.setDataHandler(newHandler);
		attachmentPart.setFileName(MimeUtility.encodeWord(attach.getName(),
				"utf8", null));
		attachmentPart.setDisposition(Part.ATTACHMENT);
		return attachmentPart;
	}

	/**
	 * 方法描述：SMTP邮箱连接验证身份验证
	 * 
	 * @param mailbox
	 * @return
	 */
	public boolean identitySmtp(Mailbox mailbox) {
		Transport transport = null;
		try {
			Session session = this.createSession(mailbox);
			// 由 Session 对象获得 Transport 对象
			transport = session.getTransport();
			// 发送用户名、密码连接到指定的 smtp 服务器
			transport.connect(mailbox.getSenderService(),
					mailbox.getUsername(), mailbox.getMailPassword());

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 方法描述：在MultiPart邮件体中找到指定附件
	 * 
	 * @param mp
	 * @param mail
	 * @param attachName
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 * @author zhangligang
	 * @version 2014年7月5日下午2:00:02
	 * @see
	 */
	private DataHandler processMultipartLookupAttach(Multipart mp, Mail mail,
			String attachName) throws MessagingException, IOException {
		DataHandler handler = null;
		for (int i = 0; i < mp.getCount(); i++) {
			handler = getAttachInputStream(mp.getBodyPart(i), mail, attachName);
			if (handler != null) {
				break;
			}
		}
		return handler;
	}

	/**
	 * 方法描述：取得附件文件的handler
	 * 
	 * @param part
	 * @param mail
	 * @param attachName
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 * @author zhangligang
	 * @version 2014年7月5日下午2:01:01
	 * @see
	 */
	private DataHandler getAttachInputStream(Part part, Mail mail,
			String attachName) throws MessagingException, IOException {
		DataHandler handler = null;
		String fileName = part.getFileName();
		String disposition = part.getDisposition();
		String contentType = part.getContentType();

		if (fileName != null) {
			fileName = MimeUtility.decodeText(fileName);
		}
		// 如果当前Part还是Multipart，递归处理
		if (contentType.toLowerCase().startsWith("multipart/")) {
			handler = processMultipartLookupAttach(
					(Multipart) part.getContent(), mail, attachName);
		} else {
			// 如果Part中有附件，或正文中包含文件，从Part中取得文件名
			if (fileName == null
					&& (Part.ATTACHMENT.equalsIgnoreCase(disposition) || Part.INLINE
							.equalsIgnoreCase(disposition))) {
				fileName = getFileNameByMimeType(contentType);
			}
			// 如果Part中的文件名与参数一致，取得输入流
			if (fileName != null && fileName.equals(attachName)) {
				handler = part.getDataHandler();
			}
		}
		return handler;
	}

	/**
	 * 根据MIME类型生成文件名
	 * 
	 * @param contentType
	 * @return
	 * @throws IOException
	 */
	private String getFileNameByMimeType(String contentType) throws IOException {
		switch (contentType) {
		case "image/gif":
			return File.createTempFile("attachment", ".gif").getName();
		default:
			break;
		}
		return null;
	}
}
