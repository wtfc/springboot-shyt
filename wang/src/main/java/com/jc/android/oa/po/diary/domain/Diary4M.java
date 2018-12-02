package com.jc.android.oa.po.diary.domain;


public class Diary4M {
	private Long id;
	private Long possessorId;   /*日程所有人ID*/
	private String title;   /*日程标题*/
	private String content;   /*主要完成事项*/
	private String starttime;   /*日程开始时间*/
	private String endtime;   /*日程结束时间*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPossessorId() {
		return possessorId;
	}
	public void setPossessorId(Long possessorId) {
		this.possessorId = possessorId;
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
