package com.jc.system.portal.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title 172.16.3.68
 * @description  实体类
 * @author 
 * @version  2014-11-18
 */

public class PortalFriendlylink extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String linkName;   /*友情链接名称*/
	private String linkUrl;   /*友情链接地址*/
	private Long portalId;   /*门户id*/
	private Long portletId;   /*门户业务组件id*/
	private Integer sequence;   /*排序号*/
	//------临时字段------
	private String portalName; /*门户名称*/
	private String portletName; /*门户业务组件名称*/

	public String getLinkName(){
		return linkName;
	}
	
	public void setLinkName(String linkName){
		this.linkName = linkName;
	}
	
	public String getLinkUrl(){
		return linkUrl;
	}
	
	public void setLinkUrl(String linkUrl){
		this.linkUrl = linkUrl;
	}
	
	public Long getPortalId(){
		return portalId;
	}
	
	public void setPortalId(Long portalId){
		this.portalId = portalId;
	}
	
	public Long getPortletId(){
		return portletId;
	}
	
	public void setPortletId(Long portletId){
		this.portletId = portletId;
	}
	
	public Integer getSequence(){
		return sequence;
	}
	
	public void setSequence(Integer sequence){
		this.sequence = sequence;
	}

	public String getPortalName() {
		return portalName;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	public String getPortletName() {
		return portletName;
	}

	public void setPortletName(String portletName) {
		this.portletName = portletName;
	}
	
	
	
}