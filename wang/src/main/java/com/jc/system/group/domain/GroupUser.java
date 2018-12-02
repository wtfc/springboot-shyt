package com.jc.system.group.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA2.0使用的个人组别和公共组别应用
 * @description  实体类
 * @author 
 * @version  2014-07-24
 */

public class GroupUser extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long groupId;   /*组ID*/
	private Long userId;   /*用户ID*/
	private String displayName;   /*用户姓名*/
	private Integer orderNo; /*排序*/

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Long getGroupId(){
		return groupId;
	}
	
	public void setGroupId(Long groupId){
		this.groupId = groupId;
	}
	
	public Long getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
}