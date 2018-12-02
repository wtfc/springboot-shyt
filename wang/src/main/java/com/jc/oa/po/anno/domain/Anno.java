package com.jc.oa.po.anno.domain;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.UserUtils;


/**
 * @title 个人办公
 * @description 公共_个人办公_批注信息表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李新桐
 * @version  2014-04-29
 */

public class Anno extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long businessId;       /*批注信息ID：存放业务ID，例如具体工作计划的ID，具体日志的ID，具体日程的ID*/
	private Long annoParentId;     /*批注信息父类ID*/
	private Long rootParentId;     /*批注信息根ID*/
	private String readingState;   /*阅读状态*/

	private String annoType;       /*批注信息类型[0-工作计划;1-工作日程;2-工作日志;3-督办协办]*/
	private String annoName;       /*批注文件名称*/
	private Long annoUserId;       /*批注人ID*/
	private Long annoUserDepid;    /*批注人部门ID*/
	private Long byAnnoUserId;	   /*被批注人ID*/
	private Long byAnnoUserDepid;  /*被批注人部门ID*/
	private Date annoDate;         /*批注时间*/
	private Date annoDateBegin;    /*批注时间开始*/
	private Date annoDateEnd;      /*批注时间结束*/
	private String content;        /*批注内容*/
	private String annoUserIdValue;      /*批注人ID*/
	private String annoUserDepidValue;   /*批注人部门ID*/
	private String byAnnoUserIdValue;      /*被批注人ID*/
	private String byAnnoUserDepidValue;   /*被批注人部门ID*/
	/*查询条件*/
	private String departmentName; /*批注人部门*/
	/*批注的回复*/
	private List<Anno> annoReplyList;
	/*回复批注的批注人*/
	private Long parentUserId;
	
	public String getReadingState() {
		return readingState;
	}
	public void setReadingState(String readingState) {
		this.readingState = readingState;
	}
	public String getParentUserName() {
		if(this.getParentUserId()!=null){
			return UserUtils.getUser(this.getParentUserId()).getDisplayName();
		}
		return "";
	}
	public String getAnnoTypeValue(){
		if(StringUtil.isEmpty(annoType)){
			return "";
		}
		int annoTypeTEMP = Integer.valueOf(annoType);
		switch (annoTypeTEMP) {
		case Constants.ANNOTYPE_PLAN:
			return "工作计划";
		case Constants.ANNOTYPE_DIARY:
			return "工作日程";
		case Constants.ANNOTYPE_WORKLOG:
			return "工作日志";
		case Constants.ANNOTYPE_WORKTASK:
			return "督办协办";
		default:
			return"";
		}
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getAnnoDateFormat(){
		return annoDate;
	}
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public Long getAnnoParentId() {
		return annoParentId;
	}
	public void setAnnoParentId(Long annoParentId) {
		this.annoParentId = annoParentId;
	}
	public Long getRootParentId() {
		return rootParentId;
	}
	public void setRootParentId(Long rootParentId) {
		this.rootParentId = rootParentId;
	}
	public String getAnnoType() {
		return annoType;
	}
	public void setAnnoType(String annoType) {
		this.annoType = annoType;
	}
	public String getAnnoName() {
		return annoName;
	}
	public void setAnnoName(String annoName) {
		this.annoName = annoName;
	}
	public Long getAnnoUserId() {
		return annoUserId;
	}
	public void setAnnoUserId(Long annoUserId) {
		this.annoUserId = annoUserId;
	}
	public Long getAnnoUserDepid() {
		return annoUserDepid;
	}
	public void setAnnoUserDepid(Long annoUserDepid) {
		this.annoUserDepid = annoUserDepid;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getAnnoDate() {
		return annoDate;
	}
	public void setAnnoDate(Date annoDate) {
		this.annoDate = annoDate;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getAnnoDateBegin() {
		return annoDateBegin;
	}
	public void setAnnoDateBegin(Date annoDateBegin) {
		this.annoDateBegin = annoDateBegin;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getAnnoDateEnd() {
		return annoDateEnd;
	}
	public void setAnnoDateEnd(Date annoDateEnd) {
		this.annoDateEnd = DateUtils.fillTime(annoDateEnd);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public List<Anno> getAnnoReplyList() {
		return annoReplyList;
	}
	public void setAnnoReplyList(List<Anno> annoReplyList) {
		this.annoReplyList = annoReplyList;
	}
	public Long getParentUserId() {
		return parentUserId;
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
	public String getAnnoUserDepidValue() {
		if(this.getAnnoUserId() != null ){
			Long userId = this.getAnnoUserId();
			
			User user = UserUtils.getUser(userId);
			if(user!=null){
			return user.getDeptName();
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	public void setAnnoUserDepidValue(String annoUserDepidValue) {
		this.annoUserDepidValue = annoUserDepidValue;
	}
	public Long getByAnnoUserId() {
		return byAnnoUserId;
	}
	public void setByAnnoUserId(Long byAnnoUserId) {
		this.byAnnoUserId = byAnnoUserId;
	}
	public Long getByAnnoUserDepid() {
		return byAnnoUserDepid;
	}
	public void setByAnnoUserDepid(Long byAnnoUserDepid) {
		this.byAnnoUserDepid = byAnnoUserDepid;
	}
	public String getByAnnoUserIdValue() {
		if(this.byAnnoUserId != null && this.byAnnoUserId > 0){
			//modify by lihongyu at 2015-03-17 start
//			return UserUtils.getUser(this.byAnnoUserId).getDisplayName();
			String reVal="";
			if (null!=UserUtils.getUser(this.byAnnoUserId)) {
				reVal=UserUtils.getUser(this.byAnnoUserId).getDisplayName();
			}
			return reVal;
			//modify by lihongyu at 2015-03-17 start
		}else{
			return "";
		}
	}
	public void setByAnnoUserIdValue(String byAnnoUserIdValue) {
		this.byAnnoUserIdValue = byAnnoUserIdValue;
	}
	public String getByAnnoUserDepidValue() {
		if(this.getByAnnoUserId() != null ){
			Long userId = this.getByAnnoUserId();
			
			User user = UserUtils.getUser(userId);
			if(user!=null){
			return user.getDeptName();
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	public void setByAnnoUserDepidValue(String byAnnoUserDepidValue) {
		this.byAnnoUserDepidValue = byAnnoUserDepidValue;
	}
}