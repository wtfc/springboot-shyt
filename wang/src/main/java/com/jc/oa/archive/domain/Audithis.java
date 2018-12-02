package com.jc.oa.archive.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.common.util.DateUtils;


/**
 * @title  GOA2.0源代码
 * @description 文档审计历史表 实体类
 * @author 
 * @version  2014-06-05
 */

public class Audithis extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String auditType;   /*审计类型
            0-新建/上传
            1-浏览
            2-删除/移动
            3-版本更新
            4-下载
            5-复制*/
	private Date operTime;   /*操作时间*/
	private Date operTimeBegin;   /*操作时间开始*/
	private Date operTimeEnd;   /*操作时间结束*/
	private String ip;   /*IP*/
	private Long userId;   /*人员id*/
	private String operDesc;   /*事件描述*/
	private Integer weight;   /**/
	private Long dataId;    /*文档或文件夹id*/
	private String dataName;/*文档或文件夹名字*/
	private Integer dataType;   /*0文件夹  1 文档*/
	private String startDate;   /*操作时间开始*/
	private String endDate;   /*操作时间结束*/
	private String userName;   

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getAuditType(){
		return auditType;
	}
	
	public void setAuditType(String auditType){
		this.auditType = auditType;
	}
	
	@JsonSerialize(using=CustomDatetimeSerializer.class)
	public Date getOperTime(){
		return operTime;
	}
	
	public void setOperTime(Date operTime){
		this.operTime = operTime;
	}
	
	public Date getOperTimeBegin(){
		return operTime;
	}
	
	public void setOperTimeBegin(Date operTime){
		this.operTime = operTime;
	}
	
	public Date getOperTimeEnd(){
		return operTime;
	}
	
	public void setOperTimeEnd(Date operTime){
	   
		this.operTime = DateUtils.fillTime(operTime);
	}
	public String getIp(){
		return ip;
	}
	
	public void setIp(String ip){
		this.ip = ip;
	}
	
	public Long getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	public String getOperDesc(){
		return operDesc;
	}
	
	public void setOperDesc(String operDesc){
		this.operDesc = operDesc;
	}
	
	public Integer getWeight(){
		return weight;
	}
	
	public void setWeight(Integer weight){
		this.weight = weight;
	}
	
}