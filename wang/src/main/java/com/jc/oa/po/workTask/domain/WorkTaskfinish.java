package com.jc.oa.po.workTask.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;


/**
 * @title 个人办公
 * @description 公共_个人办公_督办协办完成表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-24
 */

public class WorkTaskfinish extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long    taskId;        /*任务主键ID*/
	private String  taskName;      /*任务名称*/
	private Long    parentTaskid;  /*上级任务ID*/
	private String  taskImpCode;   /*任务重要程度字典值[2-不重要不紧急;4-不重要紧急;6-重要不紧急;8-重要紧急]*/
	private Long    sponsorId;     /*任务发起人ID*/
	private Long    directorId;    /*负责人ID*/
	private String  prticipantsId; /*任务参与人ID*/
	private String  content;       /*主要完成事项*/
	private String  standard;      /*完成标准*/
	private Date    startTime;     /*计划开始时间*/
	private Date    endTime;       /*计划完成时间*/
	private Date    actStartTime;  /*任务实际开始时间*/
	private Date    actEndTime;    /*任务实际完成时间*/
	private Integer taskProc;      /*任务进度*/
	private String  reportType;    /*汇报时限类型(0-天数;1-日期;)*/
	private Integer reportDay;     /*汇报时限天数*/
	private Date    reportTime;    /*汇报时限日期*/
	private String  status;        /*任务状态[3-完成]*/
	private String  taskOrigin;    /*任务来源:0表单新建 1工作计划导入 2会议纪要导入*/
	private Integer isTemplet;      /*是否为模板，默认0：是；1：否*/
	private String  remindType;     /*超期提醒类型，默认0：不提醒；1：短信提醒；2：邮件提醒*/
	private String  remindPerId;    /*超期提醒 收件人ID*/
	private String  remindContent;  /*超期提醒 内容*/
	private String  isImportDiary; /*是否导入日程[0-不导入;1-导入]*/
	private String  delayStatus;    /*延期状态[0-延期申请;1-审批通过;2-审批未通过;3-取消申请]*/
	private String  taskWorkType;   /*任务类型[0-领导交办;1-横向协调；2-工作请示]*/  //add by lihongyu at 2014-11-17 2.3新需求
	/*非持久化字段，用于页面显示  began*/
	private String sponsor;        /*任务发起人*/
	private String director;       /*负责人*/
	private String prticipants;    /*任务参与人*/
	private String[] finTaskIds;   /*多个任务ID,用逗号分隔,用于已完成任务*/
	private String parentTaskName; /*上级任务名称*/
	private String parentTaskImpCode;/*上级任务重要程度*/
	private Integer totalCount;    /*计数*/
	private String[] infoStatus;   /*信息状态，用于任务查询时复选框*/
	private String taskImpCode2;   /*任务重要程度字典值[2-不重要不紧急]*/
	private String taskImpCode4;   /*任务重要程度字典值[4-不重要紧急]*/
	private String taskImpCode6;   /*任务重要程度字典值[6-重要不紧急]*/
	private String taskImpCode8;   /*任务重要程度字典值[8-重要紧急]*/
	private Long   taskPersonId;    /*任务操作人ID*/
	private String taskType;        /*任务关系类型[0-发起人;1-负责人]*/
	private String taskNameTemp;    /*任务名称,用于任务模板查重*/
	private Integer normalToCount;  /*计数*/
	private String taskTypeName;    /*任务类型名称*/
	private Integer taskZCCount;    /*暂存状态计数*/
	private Integer taskNowCount;	/*进行中任务计数*/
	/*非持久化字段，用于页面显示  end*/
	
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
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
	public Integer getIsTemplet() {
		return isTemplet;
	}
	public void setIsTemplet(Integer isTemplet) {
		this.isTemplet = isTemplet;
	}
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
	public String[] getFinTaskIds() {
		return finTaskIds;
	}
	public void setFinTaskIds(String[] finTaskIds) {
		this.finTaskIds = finTaskIds;
	}
	public String getRemindType() {
		return remindType;
	}
	public void setRemindType(String remindType) {
		this.remindType = remindType;
	}
	public String getParentTaskName() {
		return parentTaskName;
	}
	public void setParentTaskName(String parentTaskName) {
		this.parentTaskName = parentTaskName;
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
	public String getIsImportDiary() {
		return isImportDiary;
	}
	public void setIsImportDiary(String isImportDiary) {
		this.isImportDiary = isImportDiary;
	}
	public String getParentTaskImpCode() {
		return parentTaskImpCode;
	}
	public void setParentTaskImpCode(String parentTaskImpCode) {
		this.parentTaskImpCode = parentTaskImpCode;
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
	public String getDelayStatus() {
		return delayStatus;
	}
	public void setDelayStatus(String delayStatus) {
		this.delayStatus = delayStatus;
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