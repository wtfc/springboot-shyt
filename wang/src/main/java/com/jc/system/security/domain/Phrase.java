package com.jc.system.security.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA2.0
 * @description 常用词表 实体类
 * @author 
 * @version  2014-04-28
 */

public class Phrase extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String content;   /*常用词内容*/
	private String phraseType;   /*’0‘--公共 '1'--个人*/
	//2014.5.12 chz 增加用户中文名  临时字段 解决列表显示 查询问题---start---
	private String userName;
	//2014.4.12 chz 增加用户中文名  临时字段 解决列表显示 查询问题---end---

	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	public String getPhraseType(){
		return phraseType;
	}
	
	public void setPhraseType(String phraseType){
		this.phraseType = phraseType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}