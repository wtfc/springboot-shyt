package com.jc.system.portal.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;


/**
 * @title GOA2.0
 * @description  实体类
 * @author 
 * @version  2014-06-13
 */

public class Portal extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String portalName;   /*门户名称*/
	private String portalStatus;   /*门户状态portalstatus_2-禁用portalstatus_1-启用*/
	private Long portalmenuId;   /**/
	private Integer sequence;   /*排序号*/
	private String portalType;	/*门户类型 ptype_org 机构 ptype_dept 部门  ptype_user 个人*/
	private Long roleId;   /*角色ID*/
	private Long deptId;   /*部门ID*/
	private Long userId;   /**/
	private Long organId;   /**/
	private String roleIds; /*角色IDS*/

	public String getPortalName(){
		return portalName;
	}
	
	public void setPortalName(String portalName){
		this.portalName = portalName;
	}
	
	public String getPortalStatus(){
		return portalStatus;
	}
	
	public void setPortalStatus(String portalStatus){
		this.portalStatus = portalStatus;
	}
	
	public String getPortalStatusValue(){
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("portal_status", portalStatus).getValue();
	}
	
	public Long getPortalmenuId(){
		return portalmenuId;
	}
	
	public void setPortalmenuId(Long portalmenuId){
		this.portalmenuId = portalmenuId;
	}
	
	public Integer getSequence(){
		return sequence;
	}
	
	public void setSequence(Integer sequence){
		this.sequence = sequence;
	}

	public String getPortalType() {
		return portalType;
	}

	public void setPortalType(String portalType) {
		this.portalType = portalType;
	}
	
	public String getPortalTypeValue(){
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("portal_type", portalType).getValue();
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
}