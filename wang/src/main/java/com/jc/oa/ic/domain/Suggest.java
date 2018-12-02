package com.jc.oa.ic.domain;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.system.content.domain.Attach;


/**
 * @title GOA V2.0 互动交流
 * @description 意见建议表 实体类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */

public class Suggest extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long suggestTypeId;   /*建议类别ID*/
	private String suggestTitle;   /*标题*/
	private String suggestContent;   /*意见内容*/
	private String suggestWay;   /*发起方式：0实名，1匿名*/
	private String singleShow;   /*多人回复是否互相可见 0否 1是*/
	private String repStatus;   /*回复状态 0未回复，1已回复*/
	private String disposeType;   /*处理类型：0接收，1发起*/
	private Long suggestUserId;   /*提议人ID*/
	private String suggestTel;   /*提议人电话*/
	private String suggestMail;   /*提议人邮箱*/
	
	
	/**非数据库字段，查询显示使用**/
	private String recipientIds;//意见接收人ID组成的字符串
	private String suggestTypeName;//建议类别名称
	private Long userId;//用户ID
	private String userName;//用户名称
	private String displayName;//创建人名称
	private String recipientNames;//接收人名称
	private  List<SugRep> sugRepList;/*一对多表list（回复信息list）*/
	private  List<Attach> attachList;/*附件list*/
	private Date startCreateDate;//发起时间起
	private Date endCreateDate;//发起时间止
	/*附件Id*/
	private List<Long> fileids;
	private String delattachIds;//删除附件Id
	
	private String isRep;//判断当前登录人是否已经回复
	private String createUserRep;//判断发起人是否已经回复

	private String suggestWay_t;
	private String suggestWay_f;
	private String disposeType_s;
	private String disposeType_r;
	private String repStatus_n;
	private String repStatus_y;
	
	public String getSuggestWay_t() {
		return suggestWay_t;
	}

	public void setSuggestWay_t(String suggestWay_t) {
		this.suggestWay_t = suggestWay_t;
	}

	public String getSuggestWay_f() {
		return suggestWay_f;
	}

	public void setSuggestWay_f(String suggestWay_f) {
		this.suggestWay_f = suggestWay_f;
	}

	public String getDisposeType_s() {
		return disposeType_s;
	}

	public void setDisposeType_s(String disposeType_s) {
		this.disposeType_s = disposeType_s;
	}

	public String getDisposeType_r() {
		return disposeType_r;
	}

	public void setDisposeType_r(String disposeType_r) {
		this.disposeType_r = disposeType_r;
	}

	public String getRepStatus_n() {
		return repStatus_n;
	}

	public void setRepStatus_n(String repStatus_n) {
		this.repStatus_n = repStatus_n;
	}

	public String getRepStatus_y() {
		return repStatus_y;
	}

	public void setRepStatus_y(String repStatus_y) {
		this.repStatus_y = repStatus_y;
	}

	public String getCreateUserRep() {
		return createUserRep;
	}

	public void setCreateUserRep(String createUserRep) {
		this.createUserRep = createUserRep;
	}

	public String getIsRep() {
		return isRep;
	}

	public void setIsRep(String isRep) {
		this.isRep = isRep;
	}

	public List<Attach> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<Attach> attachList) {
		this.attachList = attachList;
	}


	public Date getStartCreateDate() {
		return startCreateDate;
	}

	public List<Long> getFileids() {
		return fileids;
	}

	public void setFileids(List<Long> fileids) {
		this.fileids = fileids;
	}

	public String getDelattachIds() {
		return delattachIds;
	}

	public void setDelattachIds(String delattachIds) {
		this.delattachIds = delattachIds;
	}

	public void setStartCreateDate(Date startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public List<SugRep> getSugRepList() {
		return sugRepList;
	}

	public void setSugRepList(List<SugRep> sugRepList) {
		this.sugRepList = sugRepList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRecipientNames() {
		return recipientNames;
	}

	public void setRecipientNames(String recipientNames) {
		this.recipientNames = recipientNames;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	//重写getDate方法序列化日期格式
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateDate() {
		return createDate;
	}
	public String getSuggestTypeName() {
		return suggestTypeName;
	}

	public void setSuggestTypeName(String suggestTypeName) {
		this.suggestTypeName = suggestTypeName;
	}

	public String getRecipientIds() {
		return recipientIds;
	}

	public void setRecipientIds(String recipientIds) {
		this.recipientIds = recipientIds;
	}

	public Long getSuggestTypeId(){
		return suggestTypeId;
	}
	
	public void setSuggestTypeId(Long suggestTypeId){
		this.suggestTypeId = suggestTypeId;
	}
	public String getSuggestTitle(){
		return suggestTitle;
	}
	
	public void setSuggestTitle(String suggestTitle){
		this.suggestTitle = suggestTitle;
	}
	public String getSuggestContent(){
		return suggestContent;
	}
	
	public void setSuggestContent(String suggestContent){
		this.suggestContent = suggestContent;
	}
	public String getSuggestWay(){
		return suggestWay;
	}
	
	public void setSuggestWay(String suggestWay){
		this.suggestWay = suggestWay;
	}
	public String getSingleShow(){
		return singleShow;
	}
	
	public void setSingleShow(String singleShow){
		this.singleShow = singleShow;
	}
	public String getRepStatus(){
		return repStatus;
	}
	
	public void setRepStatus(String repStatus){
		this.repStatus = repStatus;
	}
	public String getDisposeType(){
		return disposeType;
	}
	
	public void setDisposeType(String disposeType){
		this.disposeType = disposeType;
	}
	public Long getSuggestUserId(){
		return suggestUserId;
	}
	
	public void setSuggestUserId(Long suggestUserId){
		this.suggestUserId = suggestUserId;
	}
	public String getSuggestTel(){
		return suggestTel;
	}
	
	public void setSuggestTel(String suggestTel){
		this.suggestTel = suggestTel;
	}
	public String getSuggestMail(){
		return suggestMail;
	}
	
	public void setSuggestMail(String suggestMail){
		this.suggestMail = suggestMail;
	}
}