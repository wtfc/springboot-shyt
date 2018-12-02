package com.jc.system.notice.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.UserUtils;


/**
 * @title GOA系统管理
 * @description 通知消息表 实体类
 * @author 
 * @version  2014-06-05
 */

public class NoticeMsg extends BaseBean implements Cloneable{
	private static final long serialVersionUID = 1L;
	private Long sendUser;   /*消息发送人*/
	private Long receiveUser;	/*消息接收人*/
	private String noticeType;	/*消息类型*/
	private String title;   /*通知标题*/
	private String content;   /*通知内容*/
	private String url;   /*通知跳转路径*/
	private Integer showFlag;		/*弹出标志*/
	private Integer readFlag;		/*已读标志*/
	private Long businessId;   /*业务id*/
	private String businessFlag;   /*业务标示*/
	
	private Integer showFlagForUpdate;		/*弹出标志*/
	
	public Long getSendUser(){
		return sendUser;
	}
	
	/**
	  * 获得发送人员名称
	  * @return 发送人员名称
	  * @author 孙圣然
	  * @version 1.0 2014年6月9日 上午9:45:04
	*/
	public String getSendUserStr(){
		String result = "";
		User user = UserUtils.getUser(getSendUser());
		if(user !=null){
			result = user.getDisplayName();
		}
		return result;
	}
	
	public void setSendUser(Long sendUser){
		this.sendUser = sendUser;
	}
	
	public void setReceiveUser(Long receiveUser) {
		this.receiveUser = receiveUser;
	}
	
	public Long getReceiveUser() {
		return receiveUser;
	}
	
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	
	public String getNoticeType() {
		return noticeType;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public Integer getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(Integer showFlag) {
		this.showFlag = showFlag;
	}

	public Integer getReadFlag() {
		return readFlag;
	}
	
	/**
	  * 消息状态（中文标示）
	  * @return 消息状态（中文标示）
	  * @author 孙圣然
	  * @version 1.0 2014年6月9日 上午9:47:49
	*/
	public String getReadFlagMsg() {
		String result = "";
		if(readFlag == 0){
			result = "未读";
		}else if(readFlag == 1){
			result = "已读";
		}
		return result;
	}
	
	public void setReadFlag(Integer readFlag) {
		this.readFlag = readFlag;
	}

	public Long getBusinessId(){
		return businessId;
	}
	
	public void setBusinessId(Long businessId){
		this.businessId = businessId;
	}
	
	public String getBusinessFlag(){
		return businessFlag;
	}
	
	public void setBusinessFlag(String businessFlag){
		this.businessFlag = businessFlag;
	}
	
	
	public void setShowFlagForUpdate(Integer showFlagForUpdate) {
		this.showFlagForUpdate = showFlagForUpdate;
	}
	public Integer getShowFlagForUpdate() {
		return showFlagForUpdate;
	}
	
	@Override
	public NoticeMsg clone(){
		try {
			return (NoticeMsg) super.clone();
		} catch (CloneNotSupportedException e) {
			log.error(e);
			return new NoticeMsg();
		}
	}
}