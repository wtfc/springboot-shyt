package com.jc.oa.po.workTask.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;


/**
 * @title 个人办公
 * @description 公共_个人办公_督办协办任务历史表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-23
 */

public class WorkTaskHistory extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long    taskId;         /*任务ID*/
	private String  taskEventType;  /*任务事件类型(0-布置;1-修改;2-删除;3-接收;4-不接收;5-汇报;6-催办;7-延期审批通过;8-取消;9-完成;10-批注;11-超期;12-延期申请;13-延期审批未通过;14-延期取消;15-暂存)*/
	private String  taskEventTitle; /*任务事件标题*/
	private String  content;        /*主要完成事项*/
	private String  reportContent;  /*汇报内容*/
	private Integer reportProc;     /*汇报进度*/
	private String  delayReason;    /*原因*/
	private Date    delayEnddate;   /*延期结束时间*/
	private Date    createDate;     /*创建时间*/
	/*非持久化字段，仅用于查询使用  began*/
	private Long    directorId;     /*负责人ID*/
	private Date    startTime;      /*开始时间*/
	private Date    endTime;        /*完成时间*/
	private Integer delayDays;		/*延期天数*/
	private Integer rowNum;         /*序号*/
	private String  userName;       /*操作用户名称*/
	private Date    actStartTime;   /*任务实际开始时间*/
	private String  remindType;     /*超期提醒类型，默认0：不提醒；1：短信提醒；2：邮件提醒*/
	/*非持久化字段，仅用于查询使用  end*/
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getTaskEventType(){
		return taskEventType;
	}
	
	public void setTaskEventType(String taskEventType){
		this.taskEventType = taskEventType;
	}
	public String getTaskEventTitle(){
		return taskEventTitle;
	}
	
	public void setTaskEventTitle(String taskEventTitle){
		this.taskEventTitle = taskEventTitle;
	}
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	public String getReportContent(){
		return reportContent;
	}
	
	public void setReportContent(String reportContent){
		this.reportContent = reportContent;
	}
	public Integer getReportProc(){
		return reportProc;
	}
	
	public void setReportProc(Integer reportProc){
		this.reportProc = reportProc;
	}
	public String getDelayReason(){
		return delayReason;
	}
	
	public void setDelayReason(String delayReason){
		this.delayReason = delayReason;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getDelayEnddate(){
		return delayEnddate;
	}
	
	public void setDelayEnddate(Date delayEnddate){
		this.delayEnddate = delayEnddate;
	}

	public Long getDirectorId() {
		return directorId;
	}

	public void setDirectorId(Long directorId) {
		this.directorId = directorId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getDelayDays() {
		return delayDays;
	}

	public void setDelayDays(Integer delayDays) {
		this.delayDays = delayDays;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getActStartTime() {
		return actStartTime;
	}

	public void setActStartTime(Date actStartTime) {
		this.actStartTime = actStartTime;
	}

	public String getRemindType() {
		return remindType;
	}

	public void setRemindType(String remindType) {
		this.remindType = remindType;
	}
	
}