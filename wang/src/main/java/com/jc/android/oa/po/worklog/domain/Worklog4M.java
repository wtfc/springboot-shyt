package com.jc.android.oa.po.worklog.domain;


public class Worklog4M {
	private Long id;
	private String title;   /*日程标题*/
	private String worklogDate;   /*日志日期*/
	private String todayLogStr;   /*多条今日日志拼成字符串形式*/

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWorklogDate() {
		return worklogDate;
	}
	public void setWorklogDate(String worklogDate) {
		this.worklogDate = worklogDate;
	}
	public String getTodayLogStr() {
		return todayLogStr;
	}
	public void setTodayLogStr(String todayLogStr) {
		this.todayLogStr = todayLogStr;
	}
	
}
