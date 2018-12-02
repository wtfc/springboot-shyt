package com.jc.system.notice;

/**
 * @title GOA V2.0
 * @description 消息实体接口
 * @version  2014年6月5日
 */
public interface INoticeMsg {
	
	Long getSendUser();
	
	void setSendUser(Long sendUser);
	
	String getTitle();
	
	void setTitle(String title);
	
	String getContent();
	
	void setContent(String content);
	
	String getUrl();
	
	void setUrl(String url);
	
	Long getBusinessId();
	
	void setBusinessId(Long businessId);
	
	String getBusinessFlag();
	
	void setBusinessFlag(String businessFlag);
}
