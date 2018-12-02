package com.jc.oa.common.domain;


import com.jc.foundation.domain.BaseBean;


/**
 * @title 172.16.3.68
 * @description 系统中各种授权信息共用表 实体类
 * @author 
 * @version  2014-04-29
 */

public class ControlSide extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long dataId;   /*业务数据id*/
	private Long userId;   /*关联用户id*/
	private Long deptId;   /*用户所在部门*/
	private String controlType;   /*1 维护范围
            2 使用范围
            3 查看范围
            …..
            */
	private String permissionType;   /*访问权限类型
            1 本人
            2 所在部门
            3 所在机构
            4 自定义
            
            当类型为4时需要访问表T_DATA_SIDE获取数据范围*/
	private String tableName;   /*表名*/
	
	
	private String ids;

	private String[] userIds;
	
	
	public String[] getUserIds() {
		return userIds;
	}

	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getControlType(){
		return controlType;
	}
	
	public void setControlType(String controlType){
		this.controlType = controlType;
	}
	public String getPermissionType(){
		return permissionType;
	}
	
	public void setPermissionType(String permissionType){
		this.permissionType = permissionType;
	}
	public String getTableName(){
		return tableName;
	}
	
	public void setTableName(String tableName){
		this.tableName = tableName;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
}