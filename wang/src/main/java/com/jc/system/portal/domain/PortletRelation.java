package com.jc.system.portal.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA2.0
 * @description  实体类
 * @author 
 * @version  2014-06-16
 */

public class PortletRelation extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long funtionId;   /*门户ID*/
	private Long portletId;   /*功能组件ID*/
	private Long portalId;   /*门户ID*/
	private String letTextarea;   /*门户文本域*/
	private String letFile;   /*门户单图片*/
	private Long userId;	/*业务组件信息归属用户id*/

	public Long getFuntionId(){
		return funtionId;
	}
	
	public void setFuntionId(Long funtionId){
		this.funtionId = funtionId;
	}
	
	public Long getPortletId(){
		return portletId;
	}
	
	public void setPortletId(Long portletId){
		this.portletId = portletId;
	}

	public Long getPortalId() {
		return portalId;
	}

	public void setPortalId(Long portalId) {
		this.portalId = portalId;
	}

	public String getLetTextarea() {
		return letTextarea;
	}

	public void setLetTextarea(String letTextarea) {
		this.letTextarea = letTextarea;
	}

	public String getLetFile() {
		return letFile;
	}

	public void setLetFile(String letFile) {
		this.letFile = letFile;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}