package com.jc.system.portal.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA2.0
 * @description  实体类
 * @author 
 * @version  2014-06-16
 */

public class Portlet extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long portalId;   /*门户ID*/
	private String letTitle;   /*门户标题*/
	private String letTextarea;   /*门户文本域*/
	private String letFile;   /*门户单图片*/
	private String functionName;   /*功能名称*/
	private String viewType;   /*组件视图类型*/
	private Integer letNum;   /*条数*/
	private String columnShowName;   /*显示字段名*/
	private String columnName;   /*字段英文名*/
	private String letStyle;   /*元素样式*/
	private String letIcon;   /*元素图标*/
	private Integer letHeight;   /*高度*/
	private Integer topMargin;   /*距上高度*/
	private Integer bottomMargin;   /*距下高度*/
	private Integer letfMargin;   /*距左高度*/
	private Integer rightMargin;   /*距右高度*/
	
	//--2014.6.17 增加临时门户名称字段---start---
	private String portalName;
	//--2014.6.17 增加临时门户名称字段---end---
	//--2014.6.18 增加功能组件id字段---start----
	private String functionId;
	//--2014.6.18 增加功能组件id字段---end----
	
	//--2014.7.1 增加临时布局坐标字段---start---
	private String siteX;
	private String siteY;
	//--2014.7.1 增加临时布局坐标字段---end---
	
	//--2014.7.4 增加临时布局容器标识---start---
	private String layoutPackId;
	//--2014.7.4 增加临时布局容器标识---end---
	
	//--2014.9.4 增加临时门户id数组串---start---
	private String portalIds;
	//--2014.9.4 增加临时门户id数组串---end---

	public Long getPortalId(){
		return portalId;
	}
	
	public void setPortalId(Long portalId){
		this.portalId = portalId;
	}
	
	public String getLetTitle(){
		return letTitle;
	}
	
	public void setLetTitle(String letTitle){
		this.letTitle = letTitle;
	}
	
	public String getFunctionName(){
		return functionName;
	}
	
	public void setFunctionName(String functionName){
		this.functionName = functionName;
	}
	
	public String getViewType(){
		return viewType;
	}
	
	public void setViewType(String viewType){
		this.viewType = viewType;
	}
	
	public Integer getLetNum(){
		return letNum;
	}
	
	public void setLetNum(Integer letNum){
		this.letNum = letNum;
	}
	
	public String getColumnShowName(){
		return columnShowName;
	}
	
	public void setColumnShowName(String columnShowName){
		this.columnShowName = columnShowName;
	}
	
	public String getColumnName(){
		return columnName;
	}
	
	public void setColumnName(String columnName){
		this.columnName = columnName;
	}
	
	public String getLetStyle(){
		return letStyle;
	}
	
	public void setLetStyle(String letStyle){
		this.letStyle = letStyle;
	}
	
	public String getLetIcon(){
		return letIcon;
	}
	
	public void setLetIcon(String letIcon){
		this.letIcon = letIcon;
	}
	
	public Integer getLetHeight(){
		return letHeight;
	}
	
	public void setLetHeight(Integer letHeight){
		this.letHeight = letHeight;
	}
	
	public Integer getTopMargin(){
		return topMargin;
	}
	
	public void setTopMargin(Integer topMargin){
		this.topMargin = topMargin;
	}
	
	public Integer getBottomMargin(){
		return bottomMargin;
	}
	
	public void setBottomMargin(Integer bottomMargin){
		this.bottomMargin = bottomMargin;
	}
	
	public Integer getLetfMargin(){
		return letfMargin;
	}
	
	public void setLetfMargin(Integer letfMargin){
		this.letfMargin = letfMargin;
	}
	
	public Integer getRightMargin(){
		return rightMargin;
	}
	
	public void setRightMargin(Integer rightMargin){
		this.rightMargin = rightMargin;
	}

	public String getPortalName() {
		return portalName;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getSiteX() {
		return siteX;
	}

	public void setSiteX(String siteX) {
		this.siteX = siteX;
	}

	public String getSiteY() {
		return siteY;
	}

	public void setSiteY(String siteY) {
		this.siteY = siteY;
	}

	public String getLayoutPackId() {
		return layoutPackId;
	}

	public void setLayoutPackId(String layoutPackId) {
		this.layoutPackId = layoutPackId;
	}
	
	public String getLetTextarea(){
		return letTextarea;
	}
	
	public void setLetTextarea(String letTextarea){
		this.letTextarea = letTextarea;
	}
	
	public String getLetFile(){
		return letFile;
	}
	
	public void setLetFile(String letFile){
		this.letFile = letFile;
	}

	public String getPortalIds() {
		return portalIds;
	}

	public void setPortalIds(String portalIds) {
		this.portalIds = portalIds;
	}
	
}