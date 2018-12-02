package com.jc.oa.po.workTask.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.jc.foundation.domain.BaseBean;


/**
 * @title 个人办公
 * @description 公共_个人办公_任务催办表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-23
 */

public class WorkRemaind extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long taskId;                 /*任务ID*/
	private Long remaindId;              /*催办人ID*/
	private Long remaindDeptid;          /*催办人部门ID*/
	private Long passiveRemaindId;       /*被催办人ID*/
	private Long passiveRemaindDeptid;   /*被催办人部门ID*/
	private String  remaindContent;      /*催办内容*/
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getRemaindId() {
		return remaindId;
	}
	public void setRemaindId(Long remaindId) {
		this.remaindId = remaindId;
	}
	public Long getRemaindDeptid() {
		return remaindDeptid;
	}
	public void setRemaindDeptid(Long remaindDeptid) {
		this.remaindDeptid = remaindDeptid;
	}
	public Long getPassiveRemaindId() {
		return passiveRemaindId;
	}
	public void setPassiveRemaindId(Long passiveRemaindId) {
		this.passiveRemaindId = passiveRemaindId;
	}
	public Long getPassiveRemaindDeptid() {
		return passiveRemaindDeptid;
	}
	public void setPassiveRemaindDeptid(Long passiveRemaindDeptid) {
		this.passiveRemaindDeptid = passiveRemaindDeptid;
	}
	public String getRemaindContent() {
		return remaindContent;
	}
	public void setRemaindContent(String remaindContent) {
		this.remaindContent = remaindContent;
	}
	
	
}