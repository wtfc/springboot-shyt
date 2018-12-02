package com.jc.oa.ic.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;


/**
 * @title 互动交流
 * @description 建议回复表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-17
 */

public class SugRep extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long suggestId;   /*建议类别ID*/
	private String repContent;   /*回复内容*/
	private Date repTime;   /*回复时间*/
	
	/**非数据库字段用于查询显示**/
	private String replyerName;//回复人名称
	
	
	public String getReplyerName() {
		return replyerName;
	}

	public void setReplyerName(String replyerName) {
		this.replyerName = replyerName;
	}

	public Long getSuggestId(){
		return suggestId;
	}
	
	public void setSuggestId(Long suggestId){
		this.suggestId = suggestId;
	}
	public String getRepContent(){
		return repContent;
	}
	
	public void setRepContent(String repContent){
		this.repContent = repContent;
	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getRepTime(){
		return repTime;
	}
	
	public void setRepTime(Date repTime){
		this.repTime = repTime;
	}
}