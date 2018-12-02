package com.jc.oa.ic.service;

import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.domain.Mail;
import com.jc.oa.ic.domain.MailFolder;
import com.jc.oa.ic.domain.Mailbox;
import com.jc.system.CustomException;
import com.jc.system.content.domain.Attach;

/**
 * @title 互动交流
 * @description 系统外部邮件服务业务接口类 Copyright (c) 2014 Jiachengnet.com Inc. All Rights
 *              Reserved Company 长春嘉诚网络工程有限公司
 * @author
 * @version 2014-04-17
 */
public interface IMailService extends IBaseService<Mail> {
	/**
	 * 向系统外邮件服务器发送邮件
	 * 
	 * @param mailinfo
	 *            待发送的邮件信息
	 * @return
	 * @throws CustomException
	 */
	public boolean sendExtMail(Mailbox mailinfo, Mail mail, HttpServletRequest request) throws Exception;

	/**
	 * 接收系统外邮件服务器的所有邮件
	 * 
	 * @param receiverInfo
	 * @throws Exception
	 */
	public List<Mail> receiveAllExtMail(Mailbox mailinfo) throws Exception;

	/**
	 * 设置邮件已读
	 * 
	 * @param mailbox
	 *            邮箱设置
	 * @param messageId
	 *            RFC822消息ID
	 * @throws MessagingException
	 * @throws IcException
	 */
	public void setExtMailSeen(Mailbox mailbox, String messageId)
			throws IcException;

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
	public void replyExtMail(Mailbox mailbox, Mail mail, boolean reply)
			throws IcException;

	/**
	 * 接收系统外邮件服务器的指定邮件
	 * 
	 * @param mailbox
	 * @param messageId
	 * @return
	 * @throws Exception
	 */
	public Mail getExtMail(Mailbox mailbox, String messageId)
			throws IcException;

	/**
	 * 方法描述： 更新已读、未读标志
	 * 
	 * @param mail
	 *            邮件实例
	 * @param status
	 *            状态
	 * @return
	 * @author zhanglg
	 * @version 2014年5月8日下午4:16:06
	 * @param status
	 * @throws CustomException
	 * @see
	 */
	public void setReadStatus(Mail mail,String mrids,String status) throws Exception;

	/**
	 * 方法描述：更新星标
	 * 
	 * @param mail
	 *            邮件实例
	 * @return
	 * @author zhanglg
	 * @version 2014年5月8日下午4:27:12
	 * @param folder 
	 * @throws CustomException
	 * @see
	 */
	public Integer setStarFlag(Mail mail, String mrids,Long folder) throws CustomException;

	/**
	 * 方法描述：移动到···文件夹
	 * 
	 * @param mail
	 *            邮件实例
	 * @return
	 * @author zhanglg
	 * @version 2014年5月8日下午4:27:12
	 * @param folder
	 * @param toFolder 
	 * @throws CustomException
	 * @see
	 */
	public void moveTo(Mail mail,String mrids, Long fromFolder, Long toFolder) throws Exception;

	/**
	 * 接收邮件，保存到数据库
	 * 
	 * @param mailboxId
	 * 
	 * @return 新邮件数量
	 * @throws IcException
	 * @throws CustomException
	 */
	public Integer receive(Long mailboxId) throws CustomException;

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
	public Mail navigate(Mail mail) throws CustomException;

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
	public PageManager search(Mail mail, PageManager page);

	/**
	 * 方法描述：获取当前用户邮箱列表
	 * 
	 * @param mailbox
	 * @return
	 * @author zhanglg
	 * @version 2014年5月13日上午10:06:46
	 * @throws CustomException
	 * @see
	 */
	public List<Mailbox> getMailboxList(Mailbox mailbox) throws CustomException;

	/**
	 * 方法描述：发送邮件，并保存到数据库
	 * 
	 * @param mail
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月13日下午2:48:15
	 * @return
	 * @see
	 */
	public Integer sendMail(Mail mail, HttpServletRequest request) throws CustomException;
	
	/**
	 * 方法描述：转发邮件，并保存到数据库
	 * @param mail
	 * @param request
	 * @return
	 * @throws IcException
	 * @author 宋海涛
	 * @version  2014年8月4日上午11:44:51
	 * @see
	 */
	public Integer transMail(Mail mail, HttpServletRequest request) throws Exception;
	
	/**
	 * 方法描述：发送邮件，并保存到数据库，用于外模块的门面调用
	 * 
	 * @param mail
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月13日下午2:48:15
	 * @return
	 * @see
	 */
	public Integer sendMail(Mail mail) throws CustomException;
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
	public Mail getReplyInfo(Mail mail,String folderId) throws CustomException;

	/**
	 * 方法描述：获取转发邮件信息
	 * 
	 * @param mail
	 * @return
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月14日下午2:49:36
	 * @see
	 */
	public Mail getForwardingInfo(Mail mail, String ids) throws CustomException;

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
	public List<MailFolder> queryAllMailFolder(MailFolder mailFolder)
			throws CustomException;

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
	public MailFolder getMailFolder(MailFolder m) throws CustomException;

	/**
	 * 方法描述：更新邮件文件夹
	 * @param folder
	 * @return
	 * @throws CustomException
	 * @author zhanglg
	 * @version  2014年5月23日下午3:02:03
	 * @see
	 */
	public Integer updateMailFolder(MailFolder folder) throws CustomException;
	/**
	 * 方法描述：保存邮件文件夹
	 * @param folder
	 * @return
	 * @throws CustomException
	 * @author zhanglg
	 * @version  2014年5月23日下午3:02:08
	 * @see
	 */
	public Integer saveMailFolder(MailFolder folder) throws CustomException;

	/**
	 * 方法描述：删除邮件文件夹
	 * @param folder
	 * @return
	 * @throws CustomException
	 * @author zhanglg
	 * @version  2014年5月23日下午3:02:12
	 * @see
	 */
	public Integer deleteMailFolder(MailFolder folder) throws CustomException;

	/**
	 * 方法描述：接收所有用户外部邮箱的方法
	 * 
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月23日下午2:59:53
	 * @see
	 */
	public void autoReceiveAllExtMail() throws CustomException;
	
	/**
	 * 方法描述：取得用户指定邮箱配置
	 * @param box
	 * @return
	 * @author zhanglg
	 * @version  2014年5月26日下午3:55:26
	 * @throws CustomException 
	 * @see
	 */
	public Mailbox getMailbox(Mailbox box) throws CustomException;
	
	/**
	 * 方法描述：下载外网邮箱附件方法
	 * @param id 邮件id
	 * @param fileName 文件名
	 * @param response 
	 * @param request
	 * @author zhanglg
	 * @version  2014年5月29日上午11:11:29
	 * @throws IcException 
	 * @see
	 */
	public void downloadExtAttach(Long id, String fileName,
			HttpServletResponse response, HttpServletRequest request) throws IcException;

	/**
	 * 方法描述：查询邮件未保存状态下的附件信息
	 * @param mail
	 * @return
	 * @throws CustomException
	 * @author zhanglg
	 * @version  2014年6月4日上午11:02:56
	 * @see
	 */
	public List<Attach> getUnsaveAttachs(Mail mail) throws CustomException;
	/**
	 * 查看往来邮件
	 * 
	 * @param mail
	 * @throws Exception
	 * @author 徐伟平
	 */
	public Map<String, Object> viewToAndFrom(Mail mail) throws IcException;
	
	/**
	 * 方法描述：查询未读邮件
	 * @param mail
	 * @param page
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月26日上午8:42:07
	 * @see
	 */
	public PageManager queryUnReadMail(Mail mail, PageManager page);
	/**
	 * 方法描述：移动端查询未读邮件
	 * @param mail
	 * @param page
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月26日上午8:42:07
	 * @see
	 */
	public PageManager queryUnReadMail4M(Mail mail, PageManager page);
	
	/**
	 * 方法描述：删除未读邮件
	 * @param mail
	 * @param mrids
	 * @param fromFolder
	 * @param toFolder
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年6月26日上午8:42:28
	 * @see
	 */
	public void moveToUnReadMail(Mail mail, String mrids, Long fromFolder,
			Long toFolder)throws Exception;
	
	/**
	 * 方法描述：	未读邮件搜索
	 * @param mail
	 * @param page
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月26日上午9:04:43
	 * @see
	 */
	public PageManager searchUnRead(Mail mail, PageManager page);
	
	/**
	 * 方法描述： 保存草稿
	 * @param mail
	 * @return
	 * @throws IcException
	 * @author 曹杨
	 * @version  2014年6月30日下午5:31:36
	 * @see
	 */
	public Integer saveDraft(Mail mail)  throws CustomException;

	/**
	 * 方法描述：分页查询星标邮件方法
	 * @param mail
	 * @param page
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月30日上午11:24:13
	 * @see
	 */
	PageManager queryStarMail(Mail mail, PageManager page);
	
	/**
	 * 方法描述：星标寻呼查询
	 * @param mail
	 * @param page
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月30日下午3:35:02
	 * @see
	 */
	PageManager searchStar(Mail mail, PageManager page);
	/**
	 * 方法描述：回复提醒查询
	 * @param mail
	 * @return
	 * @author 曹杨
	 * @version  2014年7月1日下午4:46:12
	 * @see
	 */
	public List<Mail> getReplyTexting();
    /**
     * 方法描述：短信验证
     * @param userIds
     * @return
     * @throws IcException
     * @author 宋海涛
     * @version  2014年7月17日上午10:03:03
     * @see
     */
	public Map<String, Object> validRemind(String userIds) throws IcException;
	/**
	 * 方法描述：邮件追回
	 * @param mail
	 * @return
	 * @throws IcException
	 * @author 曹杨
	 * @version  2014年7月22日下午1:30:42
	 * @see
	 */
	public Integer mailRecover(Mail mail) throws CustomException;

	/**
	 * 方法描述：根据附件Id获取邮件
	 * @param id
	 * @return
	 * @author zhangligang
	 * @version  2014年7月25日上午10:19:58
	 * @throws IcException 
	 * @see
	 */
	public Mail getMailByAttach(String id) throws IcException;

	boolean transExtMail(Mailbox Mailbox, Mail mail, HttpServletRequest request)
			throws IcException;
	
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
	public Mailbox querySign(Mailbox box) throws CustomException;

	public List<Mail> queryAllUnRead(Mail mailParam);

	public List<Mail> queryAllDetail(Mail mailParam);
}