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
public class TimerTask extends BaseBean{
	private static final long serialVersionUID = 1L;

	private String groupName; /* 任务类型 */
	private String description; /* 任务描述 */
	private String jobClassName; /* 任务处理类 */
	private Date startAt; /* 开始时间 */
	private Date endAt; /* 结束时间 */
	private String cycleType; /* 周期 */
	private String cycleDetail; /* 调度详情 */
	private String cronExpression; /* 定时表达式 */
	private String state; /* 状态 */
	private String intervalHours; /* 间隔小时 */
	private String intervalMinutes; /* 间隔分钟 */
	private String taskCounts; /* 执行次数 */
	private String cycleSelect; /* 提醒周期 */
	private String weekly; /* 每周 */
	private String monthly; /* 每月 */
	private String monthlyDay; /* 每月 */
	private String monthlyLast; /* 每月特殊选项 */
	private String monthlyLastWeek; /* 每月特殊选项星期 */
	private String yearly; /* 每年 */
	private String yearlyMonthForDay; /* 每年月 */
	private String yearlyMonthForLast; /* 每年指定月份最后一天*/
	private String yearlyDay; /* 每年月日 */
	private String yearlyMonthForWeek; /* 每年月 */
	private String yearlyLast; /* 每年特殊选项月 */
	private String yearlyLastWeek; /* 每年月星期 */
	private String perHours; /* 每小时 */
	private String perMinutes; /* 每分钟 */
	private String EXT_STR1; /* 执行次数 */

	public String getEXT_STR1() {
		return EXT_STR1;
	}

	public void setEXT_STR1(String eXT_STR1) {
		EXT_STR1 = eXT_STR1;
	}

	public String getYearlyMonthForLast() {
		return yearlyMonthForLast;
	}

	public void setYearlyMonthForLast(String yearlyMonthForLast) {
		this.yearlyMonthForLast = yearlyMonthForLast;
	}

	public String getMonthly() {
		return monthly;
	}

	public String getWeekly() {
		return weekly;
	}

	public void setWeekly(String weekly) {
		this.weekly = weekly;
	}

	public void setMonthly(String monthly) {
		this.monthly = monthly;
	}

	public String getMonthlyDay() {
		return monthlyDay;
	}

	public void setMonthlyDay(String monthlyDay) {
		this.monthlyDay = monthlyDay;
	}

	public String getMonthlyLast() {
		return monthlyLast;
	}

	public void setMonthlyLast(String monthlyLast) {
		this.monthlyLast = monthlyLast;
	}

	public String getMonthlyLastWeek() {
		return monthlyLastWeek;
	}

	public void setMonthlyLastWeek(String monthlyLastWeek) {
		this.monthlyLastWeek = monthlyLastWeek;
	}

	public String getYearly() {
		return yearly;
	}

	public void setYearly(String yearly) {
		this.yearly = yearly;
	}

	public String getYearlyMonthForDay() {
		return yearlyMonthForDay;
	}

	public void setYearlyMonthForDay(String yearlyMonthForDay) {
		this.yearlyMonthForDay = yearlyMonthForDay;
	}

	public String getYearlyDay() {
		return yearlyDay;
	}

	public void setYearlyDay(String yearlyDay) {
		this.yearlyDay = yearlyDay;
	}

	public String getYearlyMonthForWeek() {
		return yearlyMonthForWeek;
	}

	public void setYearlyMonthForWeek(String yearlyMonthForWeek) {
		this.yearlyMonthForWeek = yearlyMonthForWeek;
	}

	public String getYearlyLast() {
		return yearlyLast;
	}

	public void setYearlyLast(String yearlyLast) {
		this.yearlyLast = yearlyLast;
	}

	public String getYearlyLastWeek() {
		return yearlyLastWeek;
	}

	public void setYearlyLastWeek(String yearlyLastWeek) {
		this.yearlyLastWeek = yearlyLastWeek;
	}

	public String getPerHours() {
		return perHours;
	}

	public void setPerHours(String perHours) {
		this.perHours = perHours;
	}

	public String getPerMinutes() {
		return perMinutes;
	}

	public void setPerMinutes(String perMinutes) {
		this.perMinutes = perMinutes;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getIntervalHours() {
		return intervalHours;
	}

	public void setIntervalHours(String intervalHours) {
		this.intervalHours = intervalHours;
	}

	public String getIntervalMinutes() {
		return intervalMinutes;
	}

	public void setIntervalMinutes(String intervalMinutes) {
		this.intervalMinutes = intervalMinutes;
	}

	public String getTaskCounts() {
		return taskCounts;
	}

	public void setTaskCounts(String taskCounts) {
		this.taskCounts = taskCounts;
	}

	public String getCycleSelect() {
		return cycleSelect;
	}

	public void setCycleSelect(String cycleSelect) {
		this.cycleSelect = cycleSelect;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJobClassName() {
		return jobClassName;
	}

	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
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

	public String getCycleType() {
		return cycleType;
	}

	public void setCycleType(String cycleType) {
		this.cycleType = cycleType;
	}

	public String getCycleDetail() {
		return cycleDetail;
	}

	public void setCycleDetail(String cycleDetail) {
		this.cycleDetail = cycleDetail;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}