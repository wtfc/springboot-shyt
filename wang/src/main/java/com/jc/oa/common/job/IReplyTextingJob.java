package com.jc.oa.common.job;

import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;

import com.jc.oa.ic.domain.Mail;
import com.jc.oa.ic.domain.MailRecord;
import com.jc.oa.ic.service.IMailRecordService;
import com.jc.oa.ic.service.IMailService;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.job.CustomJob;
import com.jc.system.notice.NoticeMsgUtil;
import com.jc.system.notice.domain.NoticeMsg;

/**
 * 
 * @title GOA V2.0
 * @author 曹杨
 * @version 2014年6月23日 下午2:15:55
 */
public class IReplyTextingJob extends CustomJob {

	IMailService mailService;
	IMailRecordService recordService;

	public IReplyTextingJob() {
		this.mailService = SpringContextHolder.getBean(IMailService.class);
		this.recordService = SpringContextHolder.getBean(IMailRecordService.class);
	}

	@Override
	public void run(JobExecutionContext jobExecutionContext) {
		try {
			List<Mail> list = mailService.getReplyTexting();
			//当前时间
			long nowDate =  new Date().getTime()/1000;
			//发送时间
			long senderTime;
			//回复时间
			long replyTime;
			//提醒时间
			long remindTime;		
			if (list != null && list.size() > 0) {
				NoticeMsg noticeMsg = new NoticeMsg();
				for (Mail newmail : list) {
					if (newmail.getReplyTextingTime() != null) {
						 //时间转换将小时转换为秒
						 replyTime = Long.parseLong((newmail.getReplyTextingTime()).toString())*60*60;
						 //取得发送时间单位为秒
						 senderTime = (newmail.getSenderTime()).getTime()/1000;
						 //取得应该提醒的时间
						 remindTime = senderTime + replyTime;
						 //判断当前时间大于或等于提醒时间则进行提醒
						if (nowDate >= remindTime){
						List<MailRecord> rlist = newmail.getReceivers();
						for (MailRecord mailRecord : rlist) {
							//弹出窗口提醒
							noticeMsg.setId(null);
							noticeMsg.setTitle(newmail.getMailTitle());
							noticeMsg.setReceiveUser(mailRecord.getReceiveUserId());
							noticeMsg.setContent(newmail.getMailContent());
							noticeMsg.setSendUser(newmail.getSenderUserId());
							noticeMsg.setNoticeType("回复邮件提醒");
							noticeMsg.setBusinessFlag("tty_ic_mail");
							noticeMsg.setBusinessId(newmail.getId());
							noticeMsg.setUrl("/ic/mail/manageDetailContent.action?id="+ newmail.getId() + "&index=0&mrid="+ mailRecord.getId() + "&folderId=1");
							noticeMsg.setExtStr1("/ic/mail/manageUnread.action");
							NoticeMsgUtil.notice(noticeMsg);
						}
						recordService.setRemindFlag(rlist);
					  }
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
