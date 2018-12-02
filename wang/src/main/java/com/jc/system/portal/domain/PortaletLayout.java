package com.jc.system.portal.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA2.0
 * @description  实体类
 * @author 
 * @version  2014-07-01
 */

public class PortaletLayout extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long userId;   /*用户ID*/
	private Long portalId;   /*门户ID*/
	private Integer sequence;   /*排序号*/
	private String layoutType;   /*布局样式类型  1:1:1栏式  1:2-二栏式 ....*/
	private String layoutSite;   /*布局坐标位置*/

	public Long getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	public Long getPortalId(){
		return portalId;
	}
	
	public void setPortalId(Long portalId){
		this.portalId = portalId;
	}
	
	public Integer getSequence(){
		return sequence;
	}
	
	public void setSequence(Integer sequence){
		this.sequence = sequence;
	}
	
	public String getLayoutType(){
		return layoutType;
	}
	
	public void setLayoutType(String layoutType){
		this.layoutType = layoutType;
	}
	
	public String getLayoutSite(){
		return layoutSite;
	}
	
	public void setLayoutSite(String layoutSite){
		this.layoutSite = layoutSite;
	}
	
}