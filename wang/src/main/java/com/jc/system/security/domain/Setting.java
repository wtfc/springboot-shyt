package com.jc.system.security.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA2.0
 * @description 系统设置表 实体类
 * @author 
 * @version  2014-04-28
 */

public class Setting extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String isMsgService;   /*短信服务是否开启
            0 不开启
            1 开启*/
	private String msgPrefix;   /*短信前缀*/
	private Integer docSuggestionHistory;   /*意见域显示规则
            1 保留所有审批历史
            2 只保留最新意见*/
	private String suggestionType;   /*签批类型1 手写 2 签名*/
	private String showIdentifyingCode;   /*显示验证码 0 不显示 1 显示*/
	private String useIpBanding;   /*ip绑定 0 不启用  1 启用*/
	private Integer maxErrorCount;   /*最多错误登录次数*/
	private String loginType;   /*登录规则 0 不准许一个账号同时在不同客户端登录  1 准许*/
	private String netKey;   /*网络签章 0 不适用 1 适用*/
	private String filePath;   /*文件路径*/
	private String photoPatn;   /*图片路径*/
	private Integer queue;   /*排序*/
	private Integer emailSaveTime;	/*email自动保存时间*/
	private Integer lockTime;	/*锁定时间(分钟)*/
	private Integer worklogDay; /*日志编辑天数控制*/
	private String controlPrint;   /*是否控制公文正文章的颜色  0 不控制   1控制*/
	private String signType;   /*插件类型 0 点聚 1 金格*/
	private String taskParentToSub; /*督办协办拆分任务时，新建下级任务，上级信息是否回显 0 不回显 1回显*/

	public String getIsMsgService(){
		return isMsgService;
	}
	
	public void setIsMsgService(String isMsgService){
		this.isMsgService = isMsgService;
	}
	public String getMsgPrefix(){
		return msgPrefix;
	}
	
	public void setMsgPrefix(String msgPrefix){
		this.msgPrefix = msgPrefix;
	}
	public Integer getDocSuggestionHistory(){
		return docSuggestionHistory;
	}
	
	public void setDocSuggestionHistory(Integer docSuggestionHistory){
		this.docSuggestionHistory = docSuggestionHistory;
	}
	public String getSuggestionType(){
		return suggestionType;
	}
	
	public void setSuggestionType(String suggestionType){
		this.suggestionType = suggestionType;
	}
	public String getShowIdentifyingCode(){
		return showIdentifyingCode;
	}
	
	public void setShowIdentifyingCode(String showIdentifyingCode){
		this.showIdentifyingCode = showIdentifyingCode;
	}
	public String getUseIpBanding(){
		return useIpBanding;
	}
	
	public void setUseIpBanding(String useIpBanding){
		this.useIpBanding = useIpBanding;
	}
	public Integer getMaxErrorCount(){
		return maxErrorCount;
	}
	
	public void setMaxErrorCount(Integer maxErrorCount){
		this.maxErrorCount = maxErrorCount;
	}
	public String getLoginType(){
		return loginType;
	}
	
	public void setLoginType(String loginType){
		this.loginType = loginType;
	}
	public String getNetKey(){
		return netKey;
	}
	
	public void setNetKey(String netKey){
		this.netKey = netKey;
	}
	public String getFilePath(){
		return filePath;
	}
	
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}
	public String getPhotoPatn(){
		return photoPatn;
	}
	
	public void setPhotoPatn(String photoPatn){
		this.photoPatn = photoPatn;
	}
	public Integer getQueue(){
		return queue;
	}
	
	public void setQueue(Integer queue){
		this.queue = queue;
	}

	public Integer getEmailSaveTime() {
		return emailSaveTime;
	}

	public void setEmailSaveTime(Integer emailSaveTime) {
		this.emailSaveTime = emailSaveTime;
	}

	public Integer getLockTime() {
		return lockTime;
	}

	public void setLockTime(Integer lockTime) {
		this.lockTime = lockTime;
	}

	public Integer getWorklogDay() {
		return worklogDay;
	}

	public void setWorklogDay(Integer worklogDay) {
		this.worklogDay = worklogDay;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getControlPrint() {
		return controlPrint;
	}

	public void setControlPrint(String controlPrint) {
		this.controlPrint = controlPrint;
	}

	public String getTaskParentToSub(){
		return taskParentToSub;
	}
	
	public void setTaskParentToSub(String taskParentToSub){
		this.taskParentToSub = taskParentToSub;
	}
	
}