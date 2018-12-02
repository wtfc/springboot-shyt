package com.jc.oa.po.readingstatus.domain;

import java.util.Date;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.security.util.UserUtils;


/**
 * @title 个人办公
 * @description 公共_个人办公_阅读表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-05-22
 */

public class ReadingStatus extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long worklogId;        /*业务ID:包含日志ID、工作计划ID、工作日程ID*/
	private String businessType;   /*业务类型:0工作计划 1工作日程 2工作日志 */
	private Long readerId;         /*阅读人ID(或领导ID)*/
	private String readerName;     /*阅读人ID(或领导ID)*/
	
	private Long subordinateId;    /*具体业务人员ID：例如写日志的人 写计划的人 写日程的人*/
	private Integer weight;        /*安全系数*/
	
	public String getReaderName() {
		if(this.readerId != null && this.readerId > 0){
			return UserUtils.getUser(this.readerId).getDisplayName();
		}else{
			return "";
		}
	}

	public Long getWorklogId(){
		return worklogId;
	}
	
	public void setWorklogId(Long worklogId){
		this.worklogId = worklogId;
	}
	
	public String getBusinessType(){
		return businessType;
	}
	
	public void setBusinessType(String businessType){
		this.businessType = businessType;
	}
	
	public Long getReaderId(){
		return readerId;
	}
	
	public void setReaderId(Long readerId){
		this.readerId = readerId;
	}
	
	public Long getSubordinateId(){
		return subordinateId;
	}
	
	public void setSubordinateId(Long subordinateId){
		this.subordinateId = subordinateId;
	}
	
	public Integer getWeight(){
		return weight;
	}
	
	public void setWeight(Integer weight){
		this.weight = weight;
	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getReadingDate() {
		return this.getCreateDate();
	}
}