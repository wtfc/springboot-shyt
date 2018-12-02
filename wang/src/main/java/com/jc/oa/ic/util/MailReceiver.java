package com.jc.oa.ic.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeUtility;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.IntegerComparisonTerm;
import javax.mail.search.MessageIDTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SizeTerm;
import javax.mail.util.SharedByteArrayInputStream;
import javax.servlet.http.HttpServletResponse;

import com.jc.oa.ic.domain.Mail;
import com.jc.oa.ic.domain.MailRecord;
import com.jc.oa.ic.domain.Mailbox;
import com.jc.oa.ic.vo.IdentityAuthenticator;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.content.domain.Attach;
import com.jc.system.security.util.SettingUtils;
import com.sun.mail.util.MailSSLSocketFactory;

/**
 * @title 邮件接收
 * @description 邮件接收器，目前支持pop3协议。能够接收文本、HTML和带有附件的邮件 Copyright (c) 2014
 *              Jiachengnet.com Inc. All Rights Reserved Company 长春嘉诚网络工程有限公司
 * @author
 * @version 2014-04-17
 */

public class MailReceiver {
	public MailReceiver() {

	}

	/**
	 * 接收所有邮件方法
	 * 
	 * @param mailbox
	 *            邮箱配置
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 * @throws GeneralSecurityException
	 * @throws Exception
	 */
	public List<Mail> receive(Mailbox mailbox) throws MessagingException,
			IOException, GeneralSecurityException {
		Session session = this.createSession(mailbox);

		List<Mail> mailList = this.getAllImapMessages(session, mailbox);
		return mailList;
	}

	/**
	 * 方法描述：根据邮箱配置和邮件MessageId取得指定邮件
	 * 
	 * @param mailbox
	 * @param messageId
	 * @return mail Mail实例
	 * @throws MessagingException
	 * @throws IOException
	 * @author zhanglg
	 * @version 2014年5月19日下午1:45:17
	 * @throws GeneralSecurityException
	 * @see
	 */
	public Mail getMail(Mailbox mailbox, String messageId)
			throws MessagingException, IOException, GeneralSecurityException {
		Mail mail = null;
		// 创建邮件通信会话
		Session session = this.createSession(mailbox);
		// 设置通信协议为Imap
		Store store = session.getStore("imap");
		// 连接
		store.connect(mailbox.getReceiveService(), mailbox.getUsername(),
				mailbox.getMailPassword());
		// 读取邮箱中的文件夹列表
		Folder[] fs = store.getDefaultFolder().list();
		// 设置搜索条件为邮件Message-Id
		MessageIDTerm term = new MessageIDTerm(messageId);
		boolean found = false;
		for (Folder folder : fs) {
			// 以读写方式打开文件夹，才能执行搜索操作
			folder.open(Folder.READ_WRITE);
			Message[] messages = folder.search(term);
			if (messages != null) {
				mail = new Mail();
				// 理论上使用Message-Id进行搜索只会返回一封邮件
				Message message = messages[0];
				// 处理邮件头信息
				this.processMailHeader(message, mail, mailbox);
				// 获取邮件主体，并处理。
				Object body = message.getContent();
				if (body instanceof Multipart) {
					processMultipart((Multipart) body, mail);
				} else {
					processPart(message, mail);
				}
				// 设置标记，已经找到
				found = true;
			}
			folder.close(false);
			// 如果已经找到，不再循环打开其它文件夹
			if (found) {
				break;
			}
		}
		// 关闭通信
		store.close();
		return mail;
	}

	/**
	 * 方法描述：根据邮件ID，文件名，下载指定附件
	 * 
	 * @param mailbox
	 * @param messageId
	 * @param attachName
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 * @author zhanglg
	 * @version 2014年5月29日上午10:16:07
	 * @param response
	 * @throws GeneralSecurityException
	 * @see
	 */
	public void downloadMailAttach(Mailbox mailbox, String messageId,
			String attachName, HttpServletResponse response)
			throws MessagingException, IOException, GeneralSecurityException {
		InputStream is = null;
		Mail mail = null;
		// 创建邮件通信会话
		Session session = this.createSession(mailbox);
		// 设置通信协议为Imap
		Store store = session.getStore("imap");
		// 连接
		store.connect(mailbox.getReceiveService(), mailbox.getUsername(),
				mailbox.getMailPassword());
		// 读取服务器上文件夹列表
		Folder[] fs = store.getDefaultFolder().list();
		// 设置搜索条件为邮件Message-Id
		MessageIDTerm term = new MessageIDTerm(messageId);
		boolean found = false;
		for (Folder folder : fs) {
			// 以读写模式打开，并执行搜索
			folder.open(Folder.READ_WRITE);
			Message[] messages = folder.search(term);
			if (messages != null) {
				mail = new Mail();
				for (Message message : messages) {
					String msg_ID = message.getHeader("Message-ID")[0].replace(
							"<", "").replace(">", "");
					// 判断搜索结果，如果与参数中的MessageId相同，既为目标邮件，解析邮件内容，取得附件的输入流
					if (messageId.equals(msg_ID)) {
						Object body = message.getContent();
						if (body instanceof Multipart) {
							is = processMultipartLookupAttach((Multipart) body,
									mail, attachName);
						} else {
							is = getAttachInputStream(message, mail, attachName);
						}
						found = true;
						break;
					}

				}
			}
			// 通过输出流发送到客户端
			if(is != null){
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[Integer.parseInt(GlobalContext
					.getProperty("STREAM_SLICE"))];
			int length;
			while ((length = is.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.close();
			is.close();
			folder.close(false);
			if (found) {
				break;
			}
		  }
		}
		store.close();
	}

	/**
	 * 方法描述：根据邮箱配置和邮件MessageId取得指定邮件，返回eml格式文件
	 * 
	 * @param mailbox
	 * @param messageId
	 * @return eml文件实例
	 * @author zhanglg
	 * @version 2014年5月19日下午1:49:28
	 * @throws MessagingException
	 * @throws IOException
	 * @throws GeneralSecurityException
	 * @see
	 */
	public File getMailAsFile(Mailbox mailbox, String messageId)
			throws MessagingException, IOException, GeneralSecurityException {
		File emlFile = null;
		// 创建会话，设置Imap协议，并连接
		Session session = this.createSession(mailbox);
		Store store = session.getStore("imap");
		store.connect(mailbox.getReceiveService(), mailbox.getUsername(),
				mailbox.getMailPassword());
		// 列举服务器上文件夹列表，并通过MessageId查询
		Folder[] fs = store.getDefaultFolder().list();
		MessageIDTerm term = new MessageIDTerm(messageId);
		boolean found = false;
		for (Folder folder : fs) {
			folder.open(Folder.READ_WRITE);
			Message[] messages = folder.search(term);
			if (messages != null) {
				// 循环处理搜索结果
				for (Message message : messages) {
					// 根据邮件主题创建Eml文件
					String subject = MimeUtility.decodeText(message
							.getSubject());
					String filename = String.valueOf(SettingUtils
							.getSetting(SettingUtils.FILE_PATH))
							+ subject
							+ ".eml";
					// mail=saveFile(filename, message.getInputStream());
					emlFile = new File(filename);
					emlFile.createNewFile();
					// 邮件主体写入到Eml文件
					message.writeTo(new FileOutputStream(emlFile));
					break;
				}
			}
			folder.close(false);
			if (found) {
				break;
			}
		}
		store.close();
		return emlFile;
	}

	/**
	 * 设置邮件已读状态
	 * 
	 * @param mailbox
	 *            邮箱配置
	 * @param messageId
	 *            RFC822 消息ID
	 * @throws MessagingException
	 * @throws GeneralSecurityException
	 */
	public void setMessageSeen(Mailbox mailbox, String messageId)
			throws MessagingException, GeneralSecurityException {
		setMessageFlag(mailbox, messageId, Flag.SEEN);
	}

	/**
	 * 设置邮件状态
	 * 
	 * @param mailbox
	 *            邮箱配置
	 * @param messageId
	 *            RFC822 消息ID
	 * @param flag
	 *            邮件状态 @see Flags.flag
	 * @throws MessagingException
	 * @throws GeneralSecurityException
	 */
	public void setMessageFlag(Mailbox mailbox, String messageId, Flag flag)
			throws MessagingException, GeneralSecurityException {
		// 创建会话，设置Imap协议，连接
		Session session = this.createSession(mailbox);
		Store store = session.getStore("imap");
		store.connect(mailbox.getReceiveService(), mailbox.getUsername(),
				mailbox.getMailPassword());
		// 循环服务器上文件夹目录，根据MessageId进行搜索
		Folder[] fs = store.getDefaultFolder().list();
		MessageIDTerm term = new MessageIDTerm(messageId);
		boolean found = false;
		for (Folder folder : fs) {
			folder.open(Folder.READ_WRITE);
			Message[] messages = folder.search(term);
			if (messages != null) {
				// 循环处理搜索结果，设置邮件状态
				for (Message message : messages) {
					message.setFlag(flag, true);
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
	 * @return
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
		// 设置会话参数：服务器、端口、
		Properties property = new Properties();

		property.setProperty("mail.imap.host", mailbox.getReceiveService());
		if (mailbox.getSenderPort() != null) {
			property.setProperty("mail.imap.port", mailbox.getReceivePort()
					.toString());
		}
		// 设置会话参数：根据配置文件内容，设置SSL通信
		if ("1".equals(mailbox.getReceiveSSL())) {
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			property.put("mail.imap.ssl.enable", "true");
			property.put("mail.imap.ssl.socketFactory", sf);
		}
		//Sohu邮件，需要设置这个参数为False。经测试对网易邮箱无影响
		property.put("mail.imap.partialfetch", false);

		Session session = Session.getInstance(
				property,
				new IdentityAuthenticator(mailbox.getUsername(), mailbox
						.getMailPassword()));
		session.setDebug(true);
		return session;
	}

	/**
	 * 收取IMAP服务器所有邮件
	 * 
	 * @param session
	 * @param mailbox
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 * @throws Exception
	 */
	private List<Mail> getAllImapMessages(Session session, Mailbox mailbox)
			throws MessagingException, IOException {
		List<Mail> mailList = new ArrayList<Mail>();
		// 设置以Imap协议通信
		Store store = session.getStore("imap");
		store.connect(mailbox.getUsername(), mailbox.getMailPassword());

		Folder[] fs = store.getDefaultFolder().list();
		// 设置时间查询条件，防止把邮箱中所有历史邮件全部收取回来
		Calendar calendar = Calendar.getInstance();
		if (mailbox.getLastReceived() == null) {
			calendar.setTime(mailbox.getCreateDate());
		} else {
			calendar.setTime(mailbox.getLastReceived());
		}

		ReceivedDateTerm receivedTerm = new ReceivedDateTerm(ComparisonTerm.GE,
				calendar.getTime());
		// 设置Size查询条件，腾讯邮箱必须用
		SizeTerm sizeTerm = new SizeTerm(IntegerComparisonTerm.GE, 1024);
		AndTerm andTerm = new AndTerm(receivedTerm, sizeTerm);

		for (Folder folder : fs) {
			Folder inbox = folder;
			// 不收包含以下关键字的文件夹
			if (inbox == null || inbox.exists() == false
					|| inbox.getFullName().indexOf("删除") > -1
					|| inbox.getFullName().indexOf("发送") > -1
					|| inbox.getFullName().indexOf("草稿") > -1
					|| inbox.getFullName().indexOf("发件") > -1
					|| inbox.getFullName().indexOf("垃圾") > -1) {
				continue;
			}
			inbox.open(Folder.READ_ONLY);
			Message[] messages = new Message[0];
			// 特殊处理：如果是QQ邮箱，使用Size查询条件
			if (mailbox.getReceiveService().toLowerCase().indexOf("qq.com") > 0) {
				messages = inbox.search(andTerm);
			} else {
				messages = inbox.search(receivedTerm);
			}
			// 如果得到的邮件列表不空，收取邮件，包装成Mail对象
			if (messages != null && messages.length > 0) {
				for (Message mess : messages) {
					// Message mess = messages[messages.length - 1];
					Mail mail = new Mail();
					mail.setMailboxId(mailbox.getId());
					// 处理邮件头
					this.processMailHeader(mess, mail, mailbox);
					// 处理邮件体	
					Object body = null;
					try {
						body = mess.getContent();
					} catch (MessagingException messEx) {
						if (messEx.getMessage() != null
								&& messEx.getMessage().toLowerCase()
										.contains("unable to load bodystructure")) {
							MimeMessage msg = (MimeMessage) mess;

							// Copy the message by writing into an byte array and
							// creating a new MimeMessage object based on the contents
							// of the byte array:
							ByteArrayOutputStream bos = new ByteArrayOutputStream();
							msg.writeTo(bos);
							bos.close();
							SharedByteArrayInputStream bis = new SharedByteArrayInputStream(
									bos.toByteArray());
							MimeMessage cmsg = new MimeMessage(session, bis);
							bis.close();
						} else {

							throw messEx;
						}
					}

					if (body != null) {
						if (body instanceof Multipart) {
							processMultipart((Multipart) body, mail);
						} else {
							processPart(mess, mail);
						}
					}

					mailList.add(mail);
				}
			}
			inbox.close(false);
		}

		store.close();
		return mailList;
	}

	/**
	 * 收取POP邮箱 暂不使用
	 * 
	 * @param session
	 * @param Mailbox
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 * @throws Exception
	 */
	private Message[] getPopMessages(Session session, Mailbox Mailbox)
			throws MessagingException, IOException {
		Store store = session.getStore("pop3");
		store.connect(Mailbox.getReceiveService(), Mailbox.getUsername(),
				Mailbox.getMailPassword());

		Folder inbox = store.getFolder("INBOX");
		if (inbox == null) {
			return null;
		}
		inbox.open(Folder.READ_ONLY);

		Message[] messages = inbox.getMessages();
		for (int i = 0; i < 2; i++) {
			Message mess = messages[i];
			Mail mail = new Mail();
			System.out.println("------------message " + i + " -------------");

			Enumeration<Header> headers = mess.getAllHeaders();
			while (headers.hasMoreElements()) {
				Header h = headers.nextElement();
				System.out.println(h.getName() + ":" + h.getValue());
			}
			System.out.println();

			Object body = mess.getContent();
			if (body instanceof Multipart) {
				processMultipart((Multipart) body, mail);
			} else {
				processPart(mess, mail);
			}
			System.out.println();
		}

		inbox.close(false);
		store.close();
		return messages;
	}

	/**
	 * 处理邮件消息头，会用消息实例中的值填充邮件实例中相关值
	 * 
	 * @param msg
	 *            消息实例
	 * @param mail
	 *            邮件实例
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	private void processMailHeader(Message msg, Mail mail, Mailbox mailbox)
			throws UnsupportedEncodingException, MessagingException {
		// 解析主题
		if (msg.getSubject() != null) {
			mail.setMailTitle(MimeUtility.decodeText(msg.getSubject()));
		}
		// 解析发件人
		Address[] from = msg.getFrom();
		StringBuffer sbfrom = new StringBuffer();
		if (from.length > 0) {
			for (Address address : from) {
				sbfrom.append(((InternetAddress) from[0]).getAddress()).append(
						",");
			}
			// 去除字符串结尾的逗号
			if (sbfrom.charAt(sbfrom.length() - 1) == ',') {
				sbfrom.deleteCharAt(sbfrom.length() - 1);
			}
			mail.setSenderMail(sbfrom.toString());
		}
		// 解析收件人
		List<MailRecord> receivers = new ArrayList<MailRecord>();
		receivers
				.addAll(parseMailRecord(msg.getRecipients(RecipientType.TO), 0));
		receivers
				.addAll(parseMailRecord(msg.getRecipients(RecipientType.CC), 1));
		// 本来是要在这里用BCC常量取密送接收人，实践中发现，BCC的接收人根本取不到，只能通过下面的方法特殊处理了
		// receivers.addAll(parseMailRecord(msg.getRecipients(RecipientType.BCC),
		// 2));
		// 密收邮件的处理:当用户通过用户名、密码登陆邮箱收到了这封邮件时，就证明用户一定是收件人之一。如果To和Cc都没有，就是Bcc
		boolean isBcc = true;
		for (MailRecord record : receivers) {
			if (record.getReceiveMail().indexOf(mailbox.getAddress()) > -1) {
				isBcc = false;
			}
		}
		if (isBcc) {
			MailRecord receiver = new MailRecord();
			receiver.setReceiveMail(mailbox.getAddress());
			receiver.setReceiveType(Constants.IC_MAIL_RECEIVETYPE_BCC);
			receiver.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
			receiver.setFolderId(Constants.IC_MAIL_MAILFOLDER_INBOX);
			receiver.setReadFlag(Constants.IC_MAIL_MAILSTATUS_UNREAD);
			receivers.add(receiver);
		}
		mail.setReceivers(receivers);

		// 解析发件时间、收件时间
		mail.setSenderTime(msg.getSentDate());
		mail.setReceiveTime2(msg.getReceivedDate());
		mail.setMailStatus(1);
		// 解析MessageId
		if (msg.getHeader("Message-ID") != null
				&& msg.getHeader("Message-ID")[0] != null) {
			String messageId = msg.getHeader("Message-ID")[0].replace("<", "")
					.replace(">", "");
			mail.setMessageId(messageId);
		}
		mail.setIsfile(new Integer(Constants.IC_MAIL_ISFILE_NO));
		mail.setStarMail(new Integer(Constants.IC_MAIL_STARMAIL_NO));
		mail.setMailStatus(new Integer(Constants.IC_MAIL_MAILSTATUS_UNREAD));
		mail.setMailboxId(mail.getMailboxId());
		// 解析邮件优先级
		String[] priority = msg.getHeader("X-Priority");
		if (priority != null && priority.length > 0 && "1".equals(priority[0])) {
			mail.setPressing(Constants.IC_MAIL_PRESSING_YES);
		}
	}

	/**
	 * 处理收件人地址
	 * 
	 * @param addresses
	 *            地址数组
	 * @param receiveType
	 *            收件人类型
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private List<MailRecord> parseMailRecord(Address[] addresses,
			Integer receiveType) throws UnsupportedEncodingException {
		List<MailRecord> list = new ArrayList<MailRecord>();
		if (addresses != null) {
			for (Address address : addresses) {
				String addressStr = MimeUtility
						.decodeText(((InternetAddress) address).getAddress());

				MailRecord receiver = new MailRecord();
				receiver.setReceiveMail(addressStr);
				receiver.setReceiveType(receiveType);
				receiver.setStarMail(Constants.IC_MAIL_STARMAIL_NO);
				receiver.setFolderId(Constants.IC_MAIL_MAILFOLDER_INBOX);
				receiver.setReadFlag(Constants.IC_MAIL_MAILSTATUS_UNREAD);
				list.add(receiver);
			}
		}
		return list;
	}

	/**
	 * 处理邮件MultiPart，用MultiPart数据填充邮件实例中相关值
	 * 
	 * @param mp
	 *            复合部分实例
	 * @param mail
	 *            邮件实例
	 * @throws IOException
	 * @throws MessagingException
	 * @throws Exception
	 */
	private void processMultipart(Multipart mp, Mail mail)
			throws MessagingException, IOException {
		for (int i = 0; i < mp.getCount(); i++) {
			processPart(mp.getBodyPart(i), mail);
		}
	}

	/**
	 * 处理邮件Part，用Part中数据填充邮件实例
	 * 
	 * @param part
	 * @param mail
	 * @throws MessagingException
	 * @throws IOException
	 * @throws Exception
	 */
	private void processPart(Part part, Mail mail) throws MessagingException,
			IOException {
		String fileName = part.getFileName();
		String disposition = part.getDisposition();
		String contentType = part.getContentType();
		// 如果文件名不为空，重新解码文件名
		if (fileName != null) {
			fileName = MimeUtility.decodeText(fileName);
		}
		// 如果邮件主体为MultiPart，递归处理第一部分
		if (contentType.toLowerCase().startsWith("multipart/")) {
			processMultipart((Multipart) part.getContent(), mail);
		} else {
			// 如果文件名不为空，根据MimeType生成文件名
			if (fileName == null
					&& (Part.ATTACHMENT.equalsIgnoreCase(disposition) || Part.INLINE
							.equalsIgnoreCase(disposition))) {
				fileName = getFileNameByMimeType(contentType);
			}
			// 文件名为空，按无附件处理
			if (fileName == null) {
				Object content = part.getContent();
				if (content instanceof String)
					mail.setMailContent((String) content);
			} else {
				mail.setIsfile(new Integer(Constants.IC_MAIL_ISFILE_YES));
				// TODO 附件暂不保存文件，只存文件信息
				// fileName = attachmentDir + fileName;
				// saveFile(fileName, part.getInputStream());

				Attach attach = new Attach();
				attach.setResourcesName("");
				;
				attach.setFileName(fileName);
				attach.setFileSize(new Long(part.getSize()));

				if (mail.getAttachs() == null) {
					mail.setAttachs(new ArrayList<Attach>());
				}

				mail.getAttachs().add(attach);
			}
		}
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
	private InputStream processMultipartLookupAttach(Multipart mp, Mail mail,
			String attachName) throws MessagingException, IOException {
		InputStream is = null;
		for (int i = 0; i < mp.getCount(); i++) {
			is = getAttachInputStream(mp.getBodyPart(i), mail, attachName);
			if (is != null) {
				break;
			}
		}
		return is;
	}

	/**
	 * 方法描述：取得附件文件的输入流
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
	private InputStream getAttachInputStream(Part part, Mail mail,
			String attachName) throws MessagingException, IOException {
		InputStream is = null;
		String fileName = part.getFileName();
		String disposition = part.getDisposition();
		String contentType = part.getContentType();

		if (fileName != null) {
			fileName = MimeUtility.decodeText(fileName);
		}
		// 如果当前Part还是Multipart，递归处理
		if (contentType.toLowerCase().startsWith("multipart/")) {
			is = processMultipartLookupAttach((Multipart) part.getContent(),
					mail, attachName);
		} else {
			// 如果Part中有附件，或正文中包含文件，从Part中取得文件名
			if (fileName == null
					&& (Part.ATTACHMENT.equalsIgnoreCase(disposition) || Part.INLINE
							.equalsIgnoreCase(disposition))) {
				fileName = getFileNameByMimeType(contentType);
			}
			// 如果Part中的文件名与参数一致，取得输入流
			if (fileName != null && fileName.equals(attachName)) {
				is = part.getInputStream();
				// TODO 附件暂不保存
				// fileName = attachmentDir + fileName;
				// saveFile(fileName, part.getInputStream());
			}
		}
		return is;
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

	/**
	 * 保存文件内容
	 * 
	 * @param fileName
	 *            文件名
	 * @param input
	 *            输入流
	 * @return
	 * @throws IOException
	 */
	private File saveFile(String fileName, InputStream input)
			throws IOException {

		// 为了防止文件名重名，在重名的文件名后面加上数字
		File file = new File(fileName);
		// 先取得文件名的后缀
		int lastDot = fileName.lastIndexOf(".");
		String extension = fileName.substring(lastDot);
		fileName = fileName.substring(0, lastDot);
		for (int i = 0; file.exists(); i++) {
			// 　如果文件重名，则添加i
			file = new File(fileName + i + extension);
		}
		// 从输入流中读取数据，写入文件输出流
		OutputStream out = new BufferedOutputStream(new FileOutputStream(file));

		InputStream in = new BufferedInputStream(input);
		int b;
		while ((b = in.read()) != -1)
			out.write(b);
		out.flush();
		out.close();
		in.close();
		return file;
	}

	/**
	 * 方法描述：Imap邮箱连接验证身份验证
	 * 
	 * @param mailbox
	 * @return
	 */
	public boolean identityImap(Mailbox mailbox) {
		Transport transport = null;
		Store store = null;
		try {
			Session session = this.createSession(mailbox);
			// 由 Session 对象获得 Store对象
			store = session.getStore("imap");
			// 发送用户名、密码连接到指定的 imtp 服务器
			store.connect(mailbox.getReceiveService(), mailbox.getUsername(),
					mailbox.getMailPassword());

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
}