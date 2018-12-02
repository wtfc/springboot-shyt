package com.jc.system.common.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @title GOA V2.0 级联表配置项目类
 * @description  用于描述级联关系的具体项目
 * @author zhangligang
 * @version  2014年7月22日 下午4:09:07
 * @see CascadeBusinessSetting
 */
@XmlRootElement (name="function")
public class CascadeSetting {

	public CascadeSetting() {
		
	}
	/**
	 * 自定义业务ID
	 */
	private String id;
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 列名
	 */
	private String columnName;
	/**
	 * 描述
	 */
	private String comment;
	
	private String piIdColumn;	//工作流程关联字段
	private String workflowVaildClass;	//流程删除验证类
	private String validFailLabel;
	/**
	 * 关联表
	 */
	private CascadeSetting[] refTable;
	
	@XmlAttribute (name="table-name")
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	@XmlAttribute (name="column-name")
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	@XmlAttribute (name="comment")
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@XmlElement(name="ref-table")
	public CascadeSetting[] getRefTable() {
		return refTable;
	}
	public void setRefTable(CascadeSetting[] refTable) {
		this.refTable = refTable;
	}
	
	@XmlAttribute(name="id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlAttribute (name="piIdColumn")
	public void setPiIdColumn(String piIdColumn) {
		this.piIdColumn = piIdColumn;
	}
	
	public String getPiIdColumn() {
		return piIdColumn;
	}
	
	@XmlAttribute (name="workflowVaildClass")
	public void setWorkflowVaildClass(String workflowVaildClass) {
		this.workflowVaildClass = workflowVaildClass;
	}
	
	public String getWorkflowVaildClass() {
		return workflowVaildClass;
	}
	
}
