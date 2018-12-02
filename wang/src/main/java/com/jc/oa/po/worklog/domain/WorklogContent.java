package com.jc.oa.po.worklog.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.jc.foundation.domain.BaseBean;


/**
 * @title 个人办公
 * @description  实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李新桐
 * @version  2014-05-04
 */

public class WorklogContent extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long worklogId;   /*日志ID*/
	private String contentType;   /*内容类型(0-今日日志;1-明日提醒)*/
	private Integer sortNum;   /*排序序号*/
	private String content;   /*主要完成事项*/
	private Date extDate1;   /*预留时间字段1*/
	private Date extDate2;   /*预留时间字段2*/
	private BigDecimal extNum1;   /*预留数字字段1*/
	private BigDecimal extNum2;   /*预留数字字段2*/
	private BigDecimal extNum3;   /*预留数字字段3*/
	private String extStr1;   /*预留字符字段1*/
	private String extStr2;   /*预留字符字段2*/
	private String extStr3;   /*预留字符字段3*/
	private String extStr4;   /*预留字符字段4*/
	private String extStr5;   /*预留字符字段5*/

	public Long getWorklogId(){
		return worklogId;
	}
	
	public void setWorklogId(Long worklogId){
		this.worklogId = worklogId;
	}
	public String getContentType(){
		return contentType;
	}
	
	public void setContentType(String contentType){
		this.contentType = contentType;
	}
	public Integer getSortNum(){
		return sortNum;
	}
	
	public void setSortNum(Integer sortNum){
		this.sortNum = sortNum;
	}
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	public Date getExtDate1(){
		return extDate1;
	}
	
	public void setExtDate1(Date extDate1){
		this.extDate1 = extDate1;
	}
	public Date getExtDate2(){
		return extDate2;
	}
	
	public void setExtDate2(Date extDate2){
		this.extDate2 = extDate2;
	}
	
	public BigDecimal getExtNum1() {
		return extNum1;
	}

	public void setExtNum1(BigDecimal extNum1) {
		this.extNum1 = extNum1;
	}

	public BigDecimal getExtNum2() {
		return extNum2;
	}

	public void setExtNum2(BigDecimal extNum2) {
		this.extNum2 = extNum2;
	}

	public BigDecimal getExtNum3() {
		return extNum3;
	}

	public void setExtNum3(BigDecimal extNum3) {
		this.extNum3 = extNum3;
	}

	public String getExtStr1(){
		return extStr1;
	}
	
	public void setExtStr1(String extStr1){
		this.extStr1 = extStr1;
	}
	public String getExtStr2(){
		return extStr2;
	}
	
	public void setExtStr2(String extStr2){
		this.extStr2 = extStr2;
	}
	public String getExtStr3(){
		return extStr3;
	}
	
	public void setExtStr3(String extStr3){
		this.extStr3 = extStr3;
	}
	public String getExtStr4(){
		return extStr4;
	}
	
	public void setExtStr4(String extStr4){
		this.extStr4 = extStr4;
	}
	public String getExtStr5(){
		return extStr5;
	}
	
	public void setExtStr5(String extStr5){
		this.extStr5 = extStr5;
	}
}