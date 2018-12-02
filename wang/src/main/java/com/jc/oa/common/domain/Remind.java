package com.jc.oa.common.domain;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.jc.foundation.domain.BaseBean;


/**
 * @title  GOA2.0源代码
 * @description 系统提醒轮询时间公共表; InnoDB free: 11264 kB 实体类
 * @author 
 * @version  2014-04-17
 */

public class Remind extends BaseBean{
	private static final long serialVersionUID = 1L;
	//private long id;   /**/
	private long dataId;   /*功能数据id*/
	private Integer subType;   /*子功能标识*/
	private Integer mainType;   /*主功能标识*/
	private String tableName;
	

	private String remindType;   /*1 边界提醒 2固定间隔 3 周期日期 4 一次性*/
	private String cycle;   /*提醒周期
            1 天
            2 周
            3 月
            4 年*/
	private String viewStartTime;    
	private String viewEndTime;    
	private String viewStartTimeStr;   /*页面设置值回显使用*/
	private String viewEndTimeStr;   /*页面设置值回显使用*/
	private long endTime;   /*提醒结束时间*/
	private long nextTime;   /*下次提醒时间*/
	private Integer remindNum;   /*提醒次数*/
	private String cronExpression;   /*crin表达式*/
	private Integer intervalHour;   /*提醒的时间点*/
	private Integer intervalMinute;   /*提醒的时间点*/
	private String viewCycle;   /*存放周期提醒的页面设置用于回显*/
	private String title;   /*提醒标题*/
	private String content;   /*提醒内容*/
	private Integer startForwardTime;   /*开始前多长时间  用于回显select  */
	private Integer endForwardTime;   /*结束前多长时间 用于回显 select*/

	private Integer startForwardTimeValue;   /*开始前多长时间   */
	private Integer endForwardTimeValue;   /*结束前多长时间  */
	private String remindMode;   /*
	具体提醒类型
			0 不提醒
            1 内部邮件
            2 短信
            
             
            
             
            */
	private String receiveId;   /*接收提醒的地址,逗号分隔*/
	private String requireRemind;   /*0 非强制 1 强制*/
	private Integer remindInterval;   /*提醒间隔,存储间隔的分钟数*/

	
	private String isActive;
	private Integer remindedNum;
	
	private String logMsg;
	
	
	
	//使用范围
    private String controls;
	
 

	public String getControls() {
		return controls;
	}

	public void setControls(String controls) {
		this.controls = controls;
	}

	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}

	public String getIsActive() {
		return isActive;
	}

	public String getViewStartTime() {
		return viewStartTime;
	}

	public void setViewStartTime(String viewStartTime) {
		this.viewStartTime = viewStartTime;
	}

	public String getViewEndTime() {
		return viewEndTime;
	}

	public void setViewEndTime(String viewEndTime) {
		this.viewEndTime = viewEndTime;
	}
	
	

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Integer getRemindedNum() {
		return remindedNum;
	}

	public void setRemindedNum(Integer remindedNum) {
		this.remindedNum = remindedNum;
	}

	public Integer getStartForwardTimeValue() {
		return startForwardTimeValue;
	}
	public String getTableName() {
		return tableName;
	}

	public String getLogMsg() {
		return "";
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public void setStartForwardTimeValue(Integer startForwardTimeValue) {
		this.startForwardTimeValue = startForwardTimeValue;
	}
	
	public Integer getEndForwardTimeValue() {
		return endForwardTimeValue;
	}
	
	public void setEndForwardTimeValue(Integer endForwardTimeValue) {
		this.endForwardTimeValue = endForwardTimeValue;
	}
	 

	public String getViewStartTimeStr() {
		return this.viewStartTimeStr;
	}

	public void setViewStartTimeStr(String viewStartTimeStr) {
		this.viewStartTimeStr = viewStartTimeStr;
	}

	public String getViewEndTimeStr() {
		return this.viewEndTimeStr;
	}

	public void setViewEndTimeStr(String viewEndTimeStr) {
		this.viewEndTimeStr = viewEndTimeStr;
	}

	 
	 
/*	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}*/

	public long getDataId() {
		return dataId;
	}

	public void setDataId(long dataId) {
		this.dataId = dataId;
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
	public String getRemindType(){
		return remindType;
	}
	
	public void setRemindType(String remindType){
		this.remindType = remindType;
	}
	public String getCycle(){
		return cycle;
	}
	
	public void setCycle(String cycle){
		this.cycle = cycle;
	}
	 
 
	
	 
	public Integer getRemindNum(){
		return remindNum;
	}
	
	public void setRemindNum(Integer remindNum){
		this.remindNum = remindNum;
	}
	public String getCronExpression(){
		return cronExpression;
	}
	
	public void setCronExpression(String cronExpression){
		this.cronExpression = cronExpression;
	}
	public Integer getIntervalHour(){
		return intervalHour;
	}
	
	public void setIntervalHour(Integer intervalHour){
		this.intervalHour = intervalHour;
	}
	public Integer getIntervalMinute(){
		return intervalMinute;
	}
	
	public void setIntervalMinute(Integer intervalMinute){
		this.intervalMinute = intervalMinute;
	}
	public String getViewCycle(){
		return viewCycle;
	}
	
	public void setViewCycle(String viewCycle){
		this.viewCycle = viewCycle;
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
	public Integer getStartForwardTime(){
		return startForwardTime;
	}
	
	public void setStartForwardTime(Integer startForwardTime){
		this.startForwardTime = startForwardTime;
	}
	public Integer getEndForwardTime(){
		return endForwardTime;
	}
	
	public void setEndForwardTime(Integer endForwardTime){
		this.endForwardTime = endForwardTime;
	}
	public String getRemindMode(){
		return remindMode;
	}
	
	public void setRemindMode(String remindMode){
		this.remindMode = remindMode;
	}
	public String getReceiveId(){
		return receiveId;
	}
	
	public void setReceiveId(String receiveId){
		this.receiveId = receiveId;
	}
	public String getRequireRemind(){
		return requireRemind;
	}
	
	public void setRequireRemind(String requireRemind){
		this.requireRemind = requireRemind;
	}
	public Integer getRemindInterval(){
		return remindInterval;
	}
	
	public void setRemindInterval(Integer remindInterval){
		this.remindInterval = remindInterval;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getNextTime() {
		return nextTime;
	}

	public void setNextTime(long nextTime) {
		this.nextTime = nextTime;
	}
	
	public void setOrderBy(String orderBy) {
		
	}
}