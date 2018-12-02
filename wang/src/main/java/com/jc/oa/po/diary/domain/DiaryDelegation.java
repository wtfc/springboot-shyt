package com.jc.oa.po.diary.domain;

import java.util.Date;

import com.jc.foundation.domain.BaseBean;
import com.jc.system.security.util.UserUtils;


/**
 * @title 个人办公
 * @description 公共_个人办公_日程委托表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 金峰
 * @version  2014-04-28
 */

public class DiaryDelegation extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long mandatorId;   /*委托人ID*/
	private Long mandataryId;   /*被委托人ID*/
	private Long diaryId;   /*日程ID*/
	private Integer weight;   /*安全系数*/
	
	private String remindWayValue;//提醒方式
	private String mandataryIdValue;
	
	private String mandatorIds;//委托人ID串
	
	private Date ddModifyDate;//修改时间 规避同一页面上多个modifyDate
	public Date getDdModifyDate() {
		return ddModifyDate;
	}
	public void setDdModifyDate(Date ddModifyDate) {
		this.ddModifyDate = ddModifyDate;
	}
	//根据人员ID，显示人员名称
	public String getMandataryIdValue() {
		if(this.mandataryId==null){
			return "";
		}
		return UserUtils.getUser(this.mandataryId).getDisplayName();
	}
	public String getMandatorIds() {
		return mandatorIds;
	}
	public void setMandatorIds(String mandatorIds) {
		this.mandatorIds = mandatorIds;
	}
	public void setMandataryIdValue(String mandataryIdValue) {
		this.mandataryIdValue = mandataryIdValue;
	}
	public String getRemindWayValue() {
		return remindWayValue;
	}
	public void setRemindWayValue(String remindWayValue) {
		this.remindWayValue = remindWayValue;
	}
	public Long getMandatorId() {
		return mandatorId;
	}
	public void setMandatorId(Long mandatorId) {
		this.mandatorId = mandatorId;
	}
	public Long getMandataryId() {
		return mandataryId;
	}
	public void setMandataryId(Long mandataryId) {
		this.mandataryId = mandataryId;
	}
	public Long getDiaryId() {
		return diaryId;
	}
	public void setDiaryId(Long diaryId) {
		this.diaryId = diaryId;
	}
	public Integer getWeight(){
		return weight;
	}
	public void setWeight(Integer weight){
		this.weight = weight;
	}
}