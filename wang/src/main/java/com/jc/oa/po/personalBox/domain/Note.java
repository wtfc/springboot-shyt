package com.jc.oa.po.personalBox.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;


/**
 * @title 个人办公
 * @description 工具箱_便签表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-11-20
 */

public class Note extends BaseBean{
	private static final long serialVersionUID = 1L;
	
	private String content ;        /* 便签内容 */    
	
	private Long xPosition ;        /* 便签横坐标 */    
	
	private Long yPosition ;        /* 便签纵坐标 */    
	
	private String createDateValue ;        /* 便签填写时间 */    
	
	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Long getxPosition() {
		return xPosition;
	}


	public void setxPosition(Long xPosition) {
		this.xPosition = xPosition;
	}


	public Long getyPosition() {
		return yPosition;
	}


	public void setyPosition(Long yPosition) {
		this.yPosition = yPosition;
	}


	public String getCreateDateValue() {
		return createDateValue;
	}


	public void setCreateDateValue(String createDateValue) {
		this.createDateValue = createDateValue;
	}


	
	@Override
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getCreateDate() {
		return createDate;
	}
	
}