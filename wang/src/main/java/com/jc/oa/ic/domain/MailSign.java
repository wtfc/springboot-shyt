package com.jc.oa.ic.domain;


import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA V2.0 互动交流
 * @description 邮件签名表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */

public class MailSign extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String signTitle;   /*签名名称*/
	private String signContent;   /*签名内容*/
	private Integer enable;   /*是否启用签名  0否 1是   由于在邮件表中已经存在了该字段，所以此处可以不考虑*/

	public String getSignTitle(){
		return signTitle;
	}
	
	public void setSignTitle(String signTitle){
		this.signTitle = signTitle;
	}
	public String getSignContent(){
		return signContent;
	}
	
	public void setSignContent(String signContent){
		this.signContent = signContent;
	}
	public Integer getEnable(){
		return enable;
	}
	
	public void setEnable(Integer enable){
		this.enable = enable;
	}
}