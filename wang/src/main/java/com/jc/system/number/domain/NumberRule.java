package com.jc.system.number.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA2.0
 * @author 
 * @version  2014-05-04
 */

public class NumberRule extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String ruleName;   /*规则名称*/
	private String dateFormat;   /*日期格式*/
	private Integer dateType;   /*日期类型(1默认选项 2手动)*/
	private String dateSplit;   /*日期分隔符*/
	private String numberStart;   /*流水号初始值*/
	private String numberCeiling;   /*流水号上限*/
	private Integer numberResetRules;   /*流水号重置规则(1按日 2按月 3按年)*/
	private String numberResetState;   /*流水号重置判断条件*/
	private Integer numberState;   /*判断流水号是否启用连续(1是 2否)*/
	private Integer numberStateValue;   /*流水号连续标识(1已用 2未用)*/
	private String parameter;   /*参数*/
	private String numberValue;   /*当前流水号*/
	private Integer dateSplitState;   /*分隔符标识(0 无 1普通 2汉字 3手输)*/
	private Integer numberDigit;   /*流水号位数*/
	private String numberPlaceholder;   /*流水号占位符*/
	private Integer numberType;   /*流水号类型*/
	private Integer numberStep;   /*流水号步长*/
	private Integer dateIndex;   /*日期格式位置*/
	private Integer type; //1当前值	2下一个值
	private String numberModule;
	private String ruleDisplayname;
	

	public String getRuleName(){
		return ruleName;
	}
	
	public void setRuleName(String ruleName){
		this.ruleName = ruleName;
	}
	public String getDateFormat(){
		return dateFormat;
	}

	public void setDateFormat(String dateFormat){
		this.dateFormat = dateFormat;
	}
	public Integer getDateType(){
		return dateType;
	}
	
	public void setDateType(Integer dateType){
		this.dateType = dateType;
	}
	public String getDateSplit(){
		return dateSplit;
	}
	
	public void setDateSplit(String dateSplit){
		this.dateSplit = dateSplit;
	}
	public String getNumberStart(){
		return numberStart;
	}
	
	public void setNumberStart(String numberStart){
		this.numberStart = numberStart;
	}
	public String getNumberCeiling(){
		return numberCeiling;
	}
	
	public void setNumberCeiling(String numberCeiling){
		this.numberCeiling = numberCeiling;
	}
	public Integer getNumberResetRules(){
		return numberResetRules;
	}
	
	public void setNumberResetRules(Integer numberResetRules){
		this.numberResetRules = numberResetRules;
	}
	public String getNumberResetState(){
		return numberResetState;
	}
	
	public void setNumberResetState(String numberResetState){
		this.numberResetState = numberResetState;
	}
	public Integer getNumberState(){
		return numberState;
	}
	
	public void setNumberState(Integer numberState){
		this.numberState = numberState;
	}
	public Integer getNumberStateValue(){
		return numberStateValue;
	}
	
	public void setNumberStateValue(Integer numberStateValue){
		this.numberStateValue = numberStateValue;
	}
	public String getParameter(){
		return parameter;
	}
	
	public void setParameter(String parameter){
		this.parameter = parameter;
	}
	public String getNumberValue(){
		return numberValue;
	}
	
	public void setNumberValue(String numberValue){
		this.numberValue = numberValue;
	}
	public Integer getDateSplitState(){
		return dateSplitState;
	}
	
	public void setDateSplitState(Integer dateSplitState){
		this.dateSplitState = dateSplitState;
	}
	public Integer getNumberDigit(){
		return numberDigit;
	}
	
	public void setNumberDigit(Integer numberDigit){
		this.numberDigit = numberDigit;
	}
	public String getNumberPlaceholder(){
		return numberPlaceholder;
	}
	
	public void setNumberPlaceholder(String numberPlaceholder){
		this.numberPlaceholder = numberPlaceholder;
	}
	public Integer getNumberType(){
		return numberType;
	}
	
	public void setNumberType(Integer numberType){
		this.numberType = numberType;
	}
	public Integer getNumberStep(){
		return numberStep;
	}
	
	public void setNumberStep(Integer numberStep){
		this.numberStep = numberStep;
	}
	public Integer getDateIndex(){
		return dateIndex;
	}
	
	public void setDateIndex(Integer dateIndex){
		this.dateIndex = dateIndex;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNumberModule() {
		return numberModule;
	}

	public void setNumberModule(String numberModule) {
		this.numberModule = numberModule;
	}

	public String getRuleDisplayname() {
		return ruleDisplayname;
	}

	public void setRuleDisplayname(String ruleDisplayname) {
		this.ruleDisplayname = ruleDisplayname;
	}
}