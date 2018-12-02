package com.jc.system.specialData.domain;

import java.util.Date;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;


/**
 * @title 172.16.3.68
 * @description  实体类
 * @author 
 * @version  2014-12-02
 */

public class SpecialData extends BaseBean{
	
	protected transient final Logger log = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = 1L;
	private String infoName;   /*信息名称*/
	private String infoType;   /*信息类型 0-节日1-生日*/
	private String userSex;   /*用户性别 1-男 0-女*/
	private String userPicurl;   /*用户头像图片链接*/
	private Long userId;   /*用户id*/
	private Date specialData;   /*提示日期*/
	private Date specialDataBegin;   /*提示日期开始*/
	private Date specialDataEnd;   /*提示日期结束*/
	private Integer beforeDay;   /*提前提示天数*/
	private String showType;   /*展现方式 当showtype存储userid值时关联用户表;无值时采用默认样式*/
	private String infoCirculate;   /*信息是否循环使用 1-是 2-否*/
	private String openLevel;   /*公开程度 1-完全公开 2-部分公开*/
	private String solarorlunar;   /*日期计算方式 1-阳历2-阴历*/
	private String summaryContent;   /**/
	private String sendmailStatus;   /*是否完成邮件推送1-是2-否*/
	private String sendpictureStatus;   /*是否完成发送系统贺卡1-是2-否*/

	public String getInfoName(){
		return infoName;
	}
	
	public void setInfoName(String infoName){
		this.infoName = infoName;
	}
	
	public String getInfoType(){
		return infoType;
	}
	
	public void setInfoType(String infoType){
		this.infoType = infoType;
	}
	
	public String getUserSex(){
		return userSex;
	}
	
	public void setUserSex(String userSex){
		this.userSex = userSex;
	}
	
	public String getUserPicurl(){
		return userPicurl;
	}
	
	public void setUserPicurl(String userPicurl){
		this.userPicurl = userPicurl;
	}
	
	public Long getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getSpecialData(){
		return specialData;
	}
	
	public void setSpecialData(Date specialData){
		this.specialData = specialData;
	}
	
	public Date getSpecialDataBegin(){
		return specialDataBegin;
	}
	
	public void setSpecialDataBegin(Date specialDataBegin){
		this.specialDataBegin = specialDataBegin;
	}
	
	public Date getSpecialDataEnd(){
		return specialDataEnd;
	}
	
	public void setSpecialDataEnd(Date specialDataEnd){
	   
		this.specialDataEnd = specialDataEnd;
	}
	public Integer getBeforeDay(){
		return beforeDay;
	}
	
	public void setBeforeDay(Integer beforeDay){
		this.beforeDay = beforeDay;
	}
	
	public String getShowType(){
		return showType;
	}
	
	public void setShowType(String showType){
		this.showType = showType;
	}
	
	public String getInfoCirculate(){
		return infoCirculate;
	}
	
	public void setInfoCirculate(String infoCirculate){
		this.infoCirculate = infoCirculate;
	}
	
	public String getOpenLevel(){
		return openLevel;
	}
	
	public void setOpenLevel(String openLevel){
		this.openLevel = openLevel;
	}
	
	public String getSolarorlunar(){
		return solarorlunar;
	}
	
	public void setSolarorlunar(String solarorlunar){
		this.solarorlunar = solarorlunar;
	}
	
	public String getSummaryContent(){
		return summaryContent;
	}
	
	public void setSummaryContent(String summaryContent){
		this.summaryContent = summaryContent;
	}
	
	public String getSendmailStatus(){
		return sendmailStatus;
	}
	
	public void setSendmailStatus(String sendmailStatus){
		this.sendmailStatus = sendmailStatus;
	}
	
	public String getSendpictureStatus(){
		return sendpictureStatus;
	}
	
	public void setSendpictureStatus(String sendpictureStatus){
		this.sendpictureStatus = sendpictureStatus;
	}
	
}