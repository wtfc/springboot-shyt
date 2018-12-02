package com.jc.system.job.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;

/**
 * @title 系统任务
 * @description 实体类 
 * @version 2014-03-20 17:08
 * 
 */
public class RunLog extends BaseBean{
	private static final long serialVersionUID = 1L;

	private Integer timerTaskId; /* 任务ID */
	private String groupName; /* 任务组 */
	private Date startAt; /* 执行开始时间 */
	private Date endAt; /* 执行结束时间 */
	private String jobData; /* 数据 */

	public Integer getTimerTaskId() {
		return timerTaskId;
	}

	public void setTimerTaskId(Integer timerTaskId) {
		this.timerTaskId = timerTaskId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public String getJobData() {
		return jobData;
	}

	public void setJobData(String jobData) {
		this.jobData = jobData;
	}
}