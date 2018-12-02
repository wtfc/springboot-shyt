package com.jc.oa.po.diary.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.security.util.UserUtils;


/**
 * @title 个人办公
 * @description 公共_个人信息_工作日程信息表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 金峰
 * @version  2014-04-28
 */

public class Diary extends BaseBean{
	private static final long serialVersionUID = 1L;
	
	private Long possessorId;   /*日程所有人ID*/
	private String title;   /*日程标题*/
	private String content;   /*主要完成事项*/
	private Date starttime;   /*日程开始时间*/
	private Date starttimeBegin;   /*日程开始时间开始*/
	private Date starttimeEnd;   /*日程开始时间结束*/
	private Date endtime;   /*日程结束时间*/
	private Date endtimeBegin;   /*日程结束时间开始*/
	private Date endtimeEnd;   /*日程结束时间结束*/
	private String periodType;   /*周期类型(0-无定期;1-按天;2-按周;3-按月;4-按年)*/
	private String periodWay;    /*周期时间节点，辅助记录用，例如2:1,2,3,4,5,6,7表示每周日到周六  ;3:15表示每月15日 ;4:1:15表示每年1月15日*/
	private Date periodStartdate;   /*周期开始日期*/
	private Date periodStartdateBegin;   /*周期开始日期开始*/
	private Date periodStartdateEnd;   /*周期开始日期结束*/
	private Date periodEnddate;   /*周期结束日期*/
	private Date periodEnddateBegin;   /*周期结束日期开始*/
	private Date periodEnddateEnd;   /*周期结束日期结束*/
	private String moduleFlag;   /*模块来源标记[0-工作日程;1-日志导入;2-记事本导入;3-待办任务导入;4工作计划导入;5会务管理导入]*/
	private Long businessId; /*业务ID 来源模块主键ID*/
	private String diaryType;   /*日程类型[0-工作日程;1-万年历]*/
	private String isShare;   /*是否被共享(0-否;1-是)*/
	private String master;	/*0代表该日程是创建时的主日程 null表示非主日程*/
	private Long millisecond;/*存放createdate的毫秒数 用于判断同一周期日程*/ 
	/**以下是临时属性*/
	private String annoContent;//批注表内容
	private Long annoUserId;       /*批注人ID*/
	private String annoUserIdValue;      /*批注人ID*/
	private String possessorIdValue;
	private String shareScopeId;//共享范围ids
	private String shareScopeIdValue;//共享范围names
	private String agentId;
	//0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性 5拖拽拉伸（包含周期性编辑当前事件）
	private String modifyFlag;
	
	private Integer annoCount;//批注个数  非持久化字段，用于日志汇总中批注统计
	
	private String token;
	private String annoTokenAnno;//批注管理用
	
	private String loginUserId;//当前登录用户id
	
	
	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getAnnoTokenAnno() {
		return annoTokenAnno;
	}

	public void setAnnoTokenAnno(String annoTokenAnno) {
		this.annoTokenAnno = annoTokenAnno;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getAnnoCount() {
		return annoCount;
	}

	public void setAnnoCount(Integer annoCount) {
		this.annoCount = annoCount;
	}

	public Long getMillisecond() {
		return millisecond;
	}

	public void setMillisecond(Long millisecond) {
		this.millisecond = millisecond;
	}
	public Long getAnnoUserId() {
		return annoUserId;
	}

	public void setAnnoUserId(Long annoUserId) {
		this.annoUserId = annoUserId;
	}
	public String getAnnoUserIdValue() {
		if(this.annoUserId != null && this.annoUserId > 0){
			return UserUtils.getUser(this.annoUserId).getDisplayName();
		}else{
			return "";
		}
	}

	public void setAnnoUserIdValue(String annoUserIdValue) {
		this.annoUserIdValue = annoUserIdValue;
	}
	
	public String getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}
	private String starttimeEndtime;//导出Excel用
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}
	public String getStarttimeEndtime() {
		return starttimeEndtime;
	}

	public void setStarttimeEndtime(String starttimeEndtime) {
		this.starttimeEndtime = starttimeEndtime;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	private String agentIdValue;
	private String remind;
	
	public String getRemind() {
		return remind;
	}

	public void setRemind(String remind) {
		this.remind = remind;
	}

	@Override
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setShareScopeIdValue(String shareScopeIdValue) {
		this.shareScopeIdValue = shareScopeIdValue;
	}

	//根据人员ID，显示人员名称
	public String getShareScopeIdValue() {
		String strNames="";
		if(this.shareScopeId!=null&&!this.shareScopeId.equals("")){
			String[] ids=this.shareScopeId.split(",");
			for(String sid:ids){
				strNames+=UserUtils.getUser(Long.parseLong(sid)).getDisplayName()+",";
			}
			if(!strNames.equals("")){
				strNames=strNames.substring(0,strNames.length()-1);
			}
		}
		return strNames;
	}
	
	public String getShareScopeId() {
		return shareScopeId;
	}
	public void setShareScopeId(String shareScopeId) {
		this.shareScopeId = shareScopeId;
	}
	public void setPossessorIdValue(String possessorIdValue) {
		this.possessorIdValue = possessorIdValue;
	}
	//根据人员ID，显示人员名称
	public String getPossessorIdValue() {
		if(this.possessorId==null){
			return "";
		}
		return UserUtils.getUser(this.possessorId).getDisplayName();
	}
	public String getAnnoContent() {
		return annoContent;
	}
	public void setAnnoContent(String annoContent) {
		this.annoContent = annoContent;
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
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public String getPeriodWay() {
		return periodWay;
	}
	public void setPeriodWay(String periodWay) {
		this.periodWay = periodWay;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getPeriodStartdate() {
		return periodStartdate;
	}
	public void setPeriodStartdate(Date periodStartdate) {
		this.periodStartdate = periodStartdate;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getPeriodEnddate() {
		return periodEnddate;
	}
	public void setPeriodEnddate(Date periodEnddate) {
		this.periodEnddate = periodEnddate;
	}
	public String getModuleFlag() {
		return moduleFlag;
	}
	public void setModuleFlag(String moduleFlag) {
		this.moduleFlag = moduleFlag;
	}
	public String getDiaryType() {
		return diaryType;
	}
	public void setDiaryType(String diaryType) {
		this.diaryType = diaryType;
	}
	public String getIsShare() {
		return isShare;
	}
	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getStarttimeBegin() {
		return starttimeBegin;
	}
	public void setStarttimeBegin(Date starttimeBegin) {
		this.starttimeBegin = starttimeBegin;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getStarttimeEnd() {
		return starttimeEnd;
	}
	public void setStarttimeEnd(Date starttimeEnd) {
		this.starttimeEnd = starttimeEnd;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getEndtimeBegin() {
		return endtimeBegin;
	}
	public void setEndtimeBegin(Date endtimeBegin) {
		this.endtimeBegin = endtimeBegin;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getEndtimeEnd() {
		return endtimeEnd;
	}
	public void setEndtimeEnd(Date endtimeEnd) {
		this.endtimeEnd = endtimeEnd;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getPeriodStartdateBegin() {
		return periodStartdateBegin;
	}
	public void setPeriodStartdateBegin(Date periodStartdateBegin) {
		this.periodStartdateBegin = periodStartdateBegin;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getPeriodStartdateEnd() {
		return periodStartdateEnd;
	}
	public void setPeriodStartdateEnd(Date periodStartdateEnd) {
		this.periodStartdateEnd = periodStartdateEnd;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getPeriodEnddateBegin() {
		return periodEnddateBegin;
	}
	public void setPeriodEnddateBegin(Date periodEnddateBegin) {
		this.periodEnddateBegin = periodEnddateBegin;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getPeriodEnddateEnd() {
		return periodEnddateEnd;
	}
	public void setPeriodEnddateEnd(Date periodEnddateEnd) {
		this.periodEnddateEnd = periodEnddateEnd;
	}
	
}