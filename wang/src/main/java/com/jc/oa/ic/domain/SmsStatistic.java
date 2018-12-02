package com.jc.oa.ic.domain;

import java.util.Date;

import com.jc.foundation.domain.BaseBean;
import com.jc.system.common.util.DateUtils;
import com.jc.system.security.util.UserUtils;


/**
 * @title 互动交流
 * @description  实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-06-05
 */

public class SmsStatistic extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long userId;   /*用户主键*/
	private Date statisticsMonth;   /*统计月份*/
	private Date statisticsMonthBegin;   /*统计月份开始*/
	private Date statisticsMonthEnd;   /*统计月份结束*/
	private Integer sendNum;   /*发送数量*/
	private Integer residueNum;   /*剩余数量*/
	private String userLevel;/*用户级别*/
	
	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	private String userName;/*用户名称*/
	
	public Long getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	public Date getStatisticsMonth(){
		return statisticsMonth;
	}
	
	public void setStatisticsMonth(Date statisticsMonth){
		this.statisticsMonth = statisticsMonth;
	}
	
	public Date getStatisticsMonthBegin() {
		return statisticsMonthBegin;
	}

	public void setStatisticsMonthBegin(Date statisticsMonthBegin) {
		this.statisticsMonthBegin = statisticsMonthBegin;
	}

	public Date getStatisticsMonthEnd(){
		return statisticsMonth;
	}
	
	public void setStatisticsMonthEnd(Date statisticsMonth){
	   
		this.statisticsMonth = DateUtils.fillTime(statisticsMonth);
	}
	public Integer getSendNum(){
		return sendNum;
	}
	
	public void setSendNum(Integer sendNum){
		this.sendNum = sendNum;
	}
	
	public Integer getResidueNum(){
		return residueNum;
	}
	
	public void setResidueNum(Integer residueNum){
		this.residueNum = residueNum;
	}

	public String getUserName() {
		String userName = "";
		if(this.getUserId()>0&&UserUtils.getUser(this.getUserId())!=null){
			userName = UserUtils.getUser(this.getUserId()).getDisplayName();
		}
		return userName;
	}
	
}