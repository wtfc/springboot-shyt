package com.jc.oa.common.domain;

import java.util.Date;

import com.jc.foundation.domain.BaseBean;


/**
 * @title  GOA2.0源代码
 * @description  实体类
 * @author 
 * @version  2014-04-18
 */

public class FormSuggest extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String piId;   /*流程实例id*/
	private String nodeName;   /*当前节点名称*/
	private String taskId;   /*任务id*/
	private String message;   /*意见内容*/
	private String userName;   /*添加人姓名*/
	private String suggestId;   /*意见域id*/
	private String signContainerId;   /*签名容器id*/
	private String createUserDuty;   /*创建人职务*/
	private String createUserLevel;   /*创建人级别*/
	private Integer queue;   /*排序号
            存放创建人 序号*/
	private Integer subType;   /*子功能类型*/
	private Integer mainType;   /*主功能类型*/
	private String signInfo;   /*签名信息*/
	private Integer queueRole;   /*意见域排序规则
            1 按时间倒序
                	2 按填写人序号排序
                	3 按级别排序
                	4 按职务排序*/
	private Integer deleteUser;   /*删除人
            0 未删
            1 已删除*/
	private Date deleteDate;   /*
            删除时间*/
	private String extStr5;   /*预留*/
	private String extStr3;   /*预留*/
	private String extStr4;   /*预留*/
	private String extStr1;   /*预留*/
	private String extStr2;   /*预留*/
 
	public String getPiId(){
		return piId;
	}
	
	public void setPiId(String piId){
		this.piId = piId;
	}
	public String getNodeName(){
		return nodeName;
	}
	
	public void setNodeName(String nodeName){
		this.nodeName = nodeName;
	}
	public String getTaskId(){
		return taskId;
	}
	
	public void setTaskId(String taskId){
		this.taskId = taskId;
	}
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	public String getSuggestId(){
		return suggestId;
	}
	
	public void setSuggestId(String suggestId){
		this.suggestId = suggestId;
	}
	public String getSignContainerId(){
		return signContainerId;
	}
	
	public void setSignContainerId(String signContainerId){
		this.signContainerId = signContainerId;
	}
	public String getCreateUserDuty(){
		return createUserDuty;
	}
	
	public void setCreateUserDuty(String createUserDuty){
		this.createUserDuty = createUserDuty;
	}
	public String getCreateUserLevel(){
		return createUserLevel;
	}
	
	public void setCreateUserLevel(String createUserLevel){
		this.createUserLevel = createUserLevel;
	}
	public Integer getQueue(){
		return queue;
	}
	
	public void setQueue(Integer queue){
		this.queue = queue;
	}
	public Integer getSubType(){
		return subType;
	}
	
	public void setSubType(Integer subType){
		this.subType = subType;
	}
	public Integer getMainType(){
		return mainType;
	}
	
	public void setMainType(Integer mainType){
		this.mainType = mainType;
	}
	public String getSignInfo(){
		return signInfo;
	}
	
	public void setSignInfo(String signInfo){
		this.signInfo = signInfo;
	}
	public Integer getQueueRole(){
		return queueRole;
	}
	
	public void setQueueRole(Integer queueRole){
		this.queueRole = queueRole;
	}
	public Integer getDeleteUser(){
		return deleteUser;
	}
	
	public void setDeleteUser(Integer deleteUser){
		this.deleteUser = deleteUser;
	}
	public Date getDeleteDate(){
		return deleteDate;
	}
	
	public void setDeleteDate(Date deleteDate){
		this.deleteDate = deleteDate;
	}
	public String getExtStr5(){
		return extStr5;
	}
	
	public void setExtStr5(String extStr5){
		this.extStr5 = extStr5;
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
}