package com.jc.oa.po.worklog.domain;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.UserUtils;


/**
 * @title 个人办公
 * @description 公共_个人办公_工作日志信息表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李新桐
 * @version  2014-05-04
 */

public class Worklog extends BaseBean{
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "tty_po_worklog";
	private String title;   /*日程标题*/
	private Date worklogDate;   /*日志日期*/
	private Date worklogDateBegin;   /*日志日期开始*/
	private Date worklogDateEnd;   /*日志日期结束*/
	
	private String sentimentRemark;   /*感悟及备注*/
	private String isShare;   /*是否被共享(0-否;1-是)*/
	private String status;   /*0:有批注未阅读的日志;1:有批注已阅读的日志 ;null:没有批注*/
	private String remindType;   /*提醒方式(0:不提醒 1:邮件 2:短信)*/
	private String remindMan;   /*提醒人*/
	private String remindTitle;   /*提醒标题（邮件标题）*/
	private String remindContent;   /*提醒内容*/
	
	private String content;//内容
	
	/*非数据库字段查询显示*/
	private String startDate;
	private String endDate;
	private String shareWorklogUserId;//共享给我日志的人员id
	private String shareWorklogUserName;//共享给我日志的人员name
	
	/*今日日志*/
	private List<WorklogContent> todayLogs;
	private String todayLogStr;//多条今日日志拼成字符串形式
	/*明日提醒*/
	private List<WorklogContent> tomorrowReminds;
	/*共享范围*/
	private String shareUserIds;//共享范围ids
	private String shareUserNames;//共享范围names
	
	/*某天的日志数*/
	private Integer counts;
	/*附件Id*/
	private List<Long> fileids;
	private String delattachIds;//删除附件Id
	
	private String byAnnoUserId;/*被批注人ID*/	
	
	private Integer annoCount;//批注个数  非持久化字段，用于日志汇总中批注统计  李洪宇 2014-9-18
	
	private String tokenTemp;//非持久化字段，用于token临时使用  李洪宇 2014-10-14
	
	private String beforeRemindMan;//非持久化字段，用于日志管理中修改提醒时，赋值  李洪宇 2014-10-17
	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getTodayLogStr() {
		return todayLogStr;
	}

	public void setTodayLogStr(String todayLogStr) {
		this.todayLogStr = todayLogStr;
	}

	public String getDelattachIds() {
		return delattachIds;
	}

	public void setDelattachIds(String delattachIds) {
		this.delattachIds = delattachIds;
	}

	public List<Long> getFileids() {
		return fileids;
	}

	public void setFileids(List<Long> fileids) {
		this.fileids = fileids;
	}

	

	public String getShareWorklogUserName() {
		if(StringUtils.isNotEmpty(this.getShareWorklogUserId())){
			User user = UserUtils.getUser(Long.valueOf(this.getShareWorklogUserId()));
			if(user==null)return "";
			return user.getDisplayName();
		}
		return "";
	}

	public void setShareWorklogUserName(String shareWorklogUserName) {
		this.shareWorklogUserName = shareWorklogUserName;
	}

	public String getTableName(){
		return this.TABLE_NAME;
	}
	
	public String getShareUserNames() {
		return this.shareUserNames;
	}
	
	public void setShareUserNames(String shareUserNames) {
		this.shareUserNames = shareUserNames;
	}
	
	public String getShareWorklogUserId() {
		return shareWorklogUserId;
	}
	public void setShareWorklogUserId(String shareWorklogUserId) {
		this.shareWorklogUserId = shareWorklogUserId;
	}
	public Date getWorklogDateBegin() {
		return worklogDateBegin;
	}
	public void setWorklogDateBegin(Date worklogDateBegin) {
		this.worklogDateBegin = worklogDateBegin;
	}
	public Date getWorklogDateEnd() {
		return worklogDateEnd;
	}
	public void setWorklogDateEnd(Date worklogDateEnd) {
		this.worklogDateEnd = worklogDateEnd;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCounts() {
		return counts;
	}
	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	public String getShareUserIds() {
		return shareUserIds;
	}
	public void setShareUserIds(String shareUserIds) {
		this.shareUserIds = shareUserIds;
	}
	public List<WorklogContent> getTodayLogs() {
		return todayLogs;
	}
	public void setTodayLogs(List<WorklogContent> todayLogs) {
		this.todayLogs = todayLogs;
	}
	public List<WorklogContent> getTomorrowReminds() {
		return tomorrowReminds;
	}
	public void setTomorrowReminds(List<WorklogContent> tomorrowReminds) {
		this.tomorrowReminds = tomorrowReminds;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getWorklogDate() {
		return worklogDate;
	}
	public void setWorklogDate(Date worklogDate) {
		this.worklogDate = worklogDate;
	}
	public String getSentimentRemark() {
		return sentimentRemark;
	}
	public void setSentimentRemark(String sentimentRemark) {
		this.sentimentRemark = sentimentRemark;
	}
	public String getIsShare() {
		return isShare;
	}
	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}

	public String getRemindType() {
		return remindType;
	}

	public void setRemindType(String remindType) {
		this.remindType = remindType;
	}

	public String getRemindMan() {
		return remindMan;
	}

	public void setRemindMan(String remindMan) {
		this.remindMan = remindMan;
	}

	public String getRemindContent() {
		return remindContent;
	}

	public void setRemindContent(String remindContent) {
		this.remindContent = remindContent;
	}

	public String getRemindTitle() {
		return remindTitle;
	}

	public void setRemindTitle(String remindTitle) {
		this.remindTitle = remindTitle;
	}

	public String getByAnnoUserId() {
		return byAnnoUserId;
	}

	public void setByAnnoUserId(String byAnnoUserId) {
		this.byAnnoUserId = byAnnoUserId;
	}

	public Integer getAnnoCount() {
		return annoCount;
	}

	public void setAnnoCount(Integer annoCount) {
		this.annoCount = annoCount;
	}

	public String getTokenTemp() {
		return tokenTemp;
	}

	public void setTokenTemp(String tokenTemp) {
		this.tokenTemp = tokenTemp;
	}

	public String getBeforeRemindMan() {
		return beforeRemindMan;
	}

	public void setBeforeRemindMan(String beforeRemindMan) {
		this.beforeRemindMan = beforeRemindMan;
	}
	
}