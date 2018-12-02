package com.jc.android.oa.po.diary.domain;

public class DiaryByLeaderShare4M {
	private String leaderId;
	private String displayName;   /*日程所有人ID*/
	private String title;   /*日程标题*/
	private String content;   /*主要完成事项*/
	private String starttime;   /*日程开始时间*/
	private String endtime;   /*日程结束时间*/
	public String getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
}
