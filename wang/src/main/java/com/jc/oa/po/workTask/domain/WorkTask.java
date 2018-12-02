package com.jc.oa.po.workTask.domain;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;



/**
 * @title 个人办公
 * @description 公共_个人办公_督办协办表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-23
 */

public class WorkTask extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String  taskName;       /*任务名称*/
	private Long    parentTaskid;   /*上级任务ID*/
	private String  taskImpCode;    /*任务重要程度字典值[2-不重要不紧急;4-不重要紧急;6-重要不紧急;8-重要紧急]*/
	private Long    sponsorId;      /*任务发起人ID*/
	private Long    directorId;     /*负责人ID*/
	private String  prticipantsId;  /*任务参与人ID*/
	private String  content;        /*主要完成事项*/
	private String  standard;       /*完成标准*/
	private Date    startTime;      /*计划开始时间*/
	private Date    endTime;        /*计划完成时间*/
	private Date    actStartTime;   /*任务实际开始时间*/
	private Date    actEndTime;     /*任务实际完成时间*/
	private Integer taskProc;       /*任务进度*/
	private String  reportType;     /*短信提醒：0：不提醒；1：提醒*/
	private Integer reportDay;      /*汇报时限天数*/
	private Date    reportTime;     /*汇报时限日期*/
	private String  status;         /*任务状态[0-未接收;1-进行中;2-延期;3-完成;4-取消;5-模板;6-超期;7-拒接收；8-暂存]*/
	private String  taskOrigin;		/*任务来源(0-新建表单;1-工作计划导入;2-会议纪要导入)*/
	private Integer isTemplet;      /*是否为模板，默认0：是；1：否*/
	private String  remindType;     /*超期提醒类型，默认0：不提醒；1：短信提醒；2：邮件提醒*/
	private String  remindPerId;    /*超期提醒 收件人ID*/
	private String  remindContent;  /*超期提醒 内容*/
	private String  isImportDiary;  /*是否导入日程[0-不导入;1-导入]*/
	private String  delayStatus;    /*延期状态[0-延期申请;1-审批通过;2-审批未通过;3-取消申请]*/
	private String  taskWorkType;   /*任务类型[0-领导交办;1-横向协调；2-工作请示]*/  //add by lihongyu at 2014-11-17 2.3新需求
	/*非持久化字段，用于页面显示  began*/
	private String notReceiveTask; /*待接收任务,用于统计*/
	private String receiveTask;    /*进行中任务,用于统计*/
	private String remindersTask;  /*催办任务,用于统计*/
	private String extensionTask;  /*延期任务,用于统计*/
	private String extendedTask;   /*超期任务,用于统计*/
	private String finishedTask;   /*已完成任务,用于统计*/
	private String parentTaskName; /*上级任务名称*/	
	private String parentTaskImpCode;/*上级任务重要程度*/
	private String parentTaskImpName;/*上级任务重要程度名称*/
	private String taskImpName;      /*任务重要程度名称*/
	private String startDate;      /*开始日期*/
	private String sponsor;        /*任务发起人*/
	private String director;       /*负责人*/
	private String prticipants;    /*任务参与人*/
	private String prtiForUpdate;  /*任务参与人,用于拼串*/
	private String report;         /*汇报时限*/
	private String statusArr;      /*任务状态,用于查询存在多个状态位*/
	private Long   userId;         /*当前登录用户ID*/
	private String[] taskIds;	   /*多个任务ID,用逗号分隔,用于催办任务*/
	private String[] finTaskIds;   /*多个任务ID,用逗号分隔,用于已完成任务*/
	private String[] fileid;       /*附件Id*/
	private String delattachIds;   /*删除附件Id*/
	private List  fileids;		   /*附件Id*/	
	private String remind;         /*提醒设置*/
	private Long   taskId;		   /*任务Id*/	
	private Integer totalCount;    /*计数*/
	private String[] infoStatus;   /*信息状态，用于任务查询时复选框*/
	private String taskOriName;    /*任务来源名称*/
	private String taskImpCode2;   /*任务重要程度字典值[2-不重要不紧急]*/
	private String taskImpCode4;   /*任务重要程度字典值[4-不重要紧急]*/
	private String taskImpCode6;   /*任务重要程度字典值[6-重要不紧急]*/
	private String taskImpCode8;   /*任务重要程度字典值[8-重要紧急]*/
	private Double taskPercent;    /*任务比重,用于查询任务进度*/
	private Integer isSubTask;     /*是否存在下一级子任务 0：不存在；1：存在*/
	private String  taskEventType;  /*任务事件类型(0-布置;1-修改;2-删除;3-接收;4-不接收;5-汇报;6-催办;7-延期审批通过;8-取消;9-完成;10-批注;11-超期;12-延期申请;13-延期审批未通过;14-延期取消;15-暂存)*/
	private Long   taskPersonId;    /*任务操作人ID*/
	private String taskType;        /*任务关系类型[0-发起人;1-负责人]*/
	private String taskNameTemp;    /*任务名称,用于任务模板查重*/
	private Integer normalToCount;  /*计数*/
	private String returnURL;       /*返回路径*/
	private String remindTemp;      /*提醒设置*/
	private String tokenTemp;		/*token 临时*/
	private String taskTypeName;    /*任务类型名称*/
	private Integer taskZCCount;    /*暂存状态计数*/
	private Integer taskNowCount;	/*进行中任务计数*/
	/*非持久化字段，用于页面显示  end*/
	
	public String getSponsor() {
		return sponsor;
	}
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getPrticipants() {
		return prticipants;
	}
	public void setPrticipants(String prticipants) {
		this.prticipants = prticipants;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Long getParentTaskid() {
		return parentTaskid;
	}
	public void setParentTaskid(Long parentTaskid) {
		this.parentTaskid = parentTaskid;
	}
	public String getTaskImpCode() {
		return taskImpCode;
	}
	public void setTaskImpCode(String taskImpCode) {
		this.taskImpCode = taskImpCode;
	}
	public Long getSponsorId() {
		return sponsorId;
	}
	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}
	public Long getDirectorId() {
		return directorId;
	}
	public void setDirectorId(Long directorId) {
		this.directorId = directorId;
	}
	public String getPrticipantsId() {
		return prticipantsId;
	}
	public void setPrticipantsId(String prticipantsId) {
		this.prticipantsId = prticipantsId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getActStartTime() {
		return actStartTime;
	}
	public void setActStartTime(Date actStartTime) {
		this.actStartTime = actStartTime;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getActEndTime() {
		return actEndTime;
	}
	public void setActEndTime(Date actEndTime) {
		this.actEndTime = actEndTime;
	}
	public Integer getTaskProc() {
		return taskProc;
	}
	public void setTaskProc(Integer taskProc) {
		this.taskProc = taskProc;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public Integer getReportDay() {
		return reportDay;
	}
	public void setReportDay(Integer reportDay) {
		this.reportDay = reportDay;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getReportTime() {
		return reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTaskOrigin() {
		return taskOrigin;
	}
	public void setTaskOrigin(String taskOrigin) {
		this.taskOrigin = taskOrigin;
	}
	public String getNotReceiveTask() {
		return notReceiveTask;
	}
	public void setNotReceiveTask(String notReceiveTask) {
		this.notReceiveTask = notReceiveTask;
	}
	public String getReceiveTask() {
		return receiveTask;
	}
	public void setReceiveTask(String receiveTask) {
		this.receiveTask = receiveTask;
	}
	public String getRemindersTask() {
		return remindersTask;
	}
	public void setRemindersTask(String remindersTask) {
		this.remindersTask = remindersTask;
	}
	public String getExtensionTask() {
		return extensionTask;
	}
	public void setExtensionTask(String extensionTask) {
		this.extensionTask = extensionTask;
	}
	public String getExtendedTask() {
		return extendedTask;
	}
	public void setExtendedTask(String extendedTask) {
		this.extendedTask = extendedTask;
	}
	public String getFinishedTask() {
		return finishedTask;
	}
	public void setFinishedTask(String finishedTask) {
		this.finishedTask = finishedTask;
	}
	public String getParentTaskName() {
		return parentTaskName;
	}
	public void setParentTaskName(String parentTaskName) {
		this.parentTaskName = parentTaskName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getStatusArr() {
		return statusArr;
	}
	public void setStatusArr(String statusArr) {
		this.statusArr = statusArr;
	}
	public Integer getIsTemplet() {
		return isTemplet;
	}
	public void setIsTemplet(Integer isTemplet) {
		this.isTemplet = isTemplet;
	}
	public String getIsImportDiary() {
		return isImportDiary;
	}
	public void setIsImportDiary(String isImportDiary) {
		this.isImportDiary = isImportDiary;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String[] getTaskIds() {
		return taskIds;
	}
	public void setTaskIds(String[] taskIds) {
		this.taskIds = taskIds;
	}
	public String[] getFinTaskIds() {
		return finTaskIds;
	}
	public void setFinTaskIds(String[] finTaskIds) {
		this.finTaskIds = finTaskIds;
	}
	public String getPrtiForUpdate() {
		return prtiForUpdate;
	}
	public void setPrtiForUpdate(String prtiForUpdate) {
		this.prtiForUpdate = prtiForUpdate;
	}
	public String[] getFileid() {
		return fileid;
	}
	public void setFileid(String[] fileid) {
		this.fileid = fileid;
	}
	public String getRemind() {
		return remind;
	}
	public void setRemind(String remind) {
		this.remind = remind;
	}
	public String getRemindType() {
		return remindType;
	}
	public void setRemindType(String remindType) {
		this.remindType = remindType;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public String[] getInfoStatus() {
		return infoStatus;
	}
	public void setInfoStatus(String[] infoStatus) {
		this.infoStatus = infoStatus;
	}
	public String getTaskOriName() {
		return taskOriName;
	}
	public void setTaskOriName(String taskOriName) {
		this.taskOriName = taskOriName;
	}
	public String getParentTaskImpCode() {
		return parentTaskImpCode;
	}
	public void setParentTaskImpCode(String parentTaskImpCode) {
		this.parentTaskImpCode = parentTaskImpCode;
	}
	public String getParentTaskImpName() {
		return parentTaskImpName;
	}
	public void setParentTaskImpName(String parentTaskImpName) {
		this.parentTaskImpName = parentTaskImpName;
	}
	public String getTaskImpName() {
		return taskImpName;
	}
	public void setTaskImpName(String taskImpName) {
		this.taskImpName = taskImpName;
	}
	public String getTaskImpCode2() {
		return taskImpCode2;
	}
	public void setTaskImpCode2(String taskImpCode2) {
		this.taskImpCode2 = taskImpCode2;
	}
	public String getTaskImpCode4() {
		return taskImpCode4;
	}
	public void setTaskImpCode4(String taskImpCode4) {
		this.taskImpCode4 = taskImpCode4;
	}
	public String getTaskImpCode6() {
		return taskImpCode6;
	}
	public void setTaskImpCode6(String taskImpCode6) {
		this.taskImpCode6 = taskImpCode6;
	}
	public String getTaskImpCode8() {
		return taskImpCode8;
	}
	public void setTaskImpCode8(String taskImpCode8) {
		this.taskImpCode8 = taskImpCode8;
	}
	public String getRemindPerId() {
		return remindPerId;
	}
	public void setRemindPerId(String remindPerId) {
		this.remindPerId = remindPerId;
	}
	public String getRemindContent() {
		return remindContent;
	}
	public void setRemindContent(String remindContent) {
		this.remindContent = remindContent;
	}
	public String getDelayStatus() {
		return delayStatus;
	}
	public void setDelayStatus(String delayStatus) {
		this.delayStatus = delayStatus;
	}
	public Double getTaskPercent() {
		return taskPercent;
	}
	public void setTaskPercent(Double taskPercent) {
		this.taskPercent = taskPercent;
	}
	public Integer getIsSubTask() {
		return isSubTask;
	}
	public void setIsSubTask(Integer isSubTask) {
		this.isSubTask = isSubTask;
	}
	public String getDelattachIds() {
		return delattachIds;
	}
	public void setDelattachIds(String delattachIds) {
		this.delattachIds = delattachIds;
	}
	public List getFileids() {
		return fileids;
	}
	public void setFileids(List fileids) {
		this.fileids = fileids;
	}
	public String getTaskEventType() {
		return taskEventType;
	}
	public void setTaskEventType(String taskEventType) {
		this.taskEventType = taskEventType;
	}
	public Long getTaskPersonId() {
		return taskPersonId;
	}
	public void setTaskPersonId(Long taskPersonId) {
		this.taskPersonId = taskPersonId;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getTaskNameTemp() {
		return taskNameTemp;
	}
	public void setTaskNameTemp(String taskNameTemp) {
		this.taskNameTemp = taskNameTemp;
	}
	public Integer getNormalToCount() {
		return normalToCount;
	}
	public void setNormalToCount(Integer normalToCount) {
		this.normalToCount = normalToCount;
	}
	public String getReturnURL() {
		return returnURL;
	}
	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}
	public String getRemindTemp() {
		return remindTemp;
	}
	public void setRemindTemp(String remindTemp) {
		this.remindTemp = remindTemp;
	}
	public String getTokenTemp() {
		return tokenTemp;
	}
	public void setTokenTemp(String tokenTemp) {
		this.tokenTemp = tokenTemp;
	}
	public String getTaskWorkType() {
		return taskWorkType;
	}
	public void setTaskWorkType(String taskWorkType) {
		this.taskWorkType = taskWorkType;
	}
	public String getTaskTypeName() {
		return taskTypeName;
	}
	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}
	public Integer getTaskZCCount() {
		return taskZCCount;
	}
	public void setTaskZCCount(Integer taskZCCount) {
		this.taskZCCount = taskZCCount;
	}
	public Integer getTaskNowCount() {
		return taskNowCount;
	}
	public void setTaskNowCount(Integer taskNowCount) {
		this.taskNowCount = taskNowCount;
	}
	
}