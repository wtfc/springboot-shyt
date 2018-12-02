package com.jc.oa.machine.domain;

import com.jc.foundation.domain.BaseBean;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author mrb
 * @version 工单附件表
*/
public class ToaMachineAttach extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String resourcesName;//   文件相对路径
	private String error = "";
	private String deleteUrl = "";
	private String thumbnailUrl = "";
	private String thumbnailresourcesName = "";//缩略图物理地址
	private String fileUrl = "";
	private String url="";
	private String isPaged="0";//为0时查询单条记录，1时分页查询
	private String content_type ="";
	private String deleteUserPhotoUrl;
	private String userid = "";   /*上传人id*/
	private String username = "";   /*上传人名字*/
	private String originalUrl="";  /*原图地址*/
	private BigDecimal businessIndex;/*附件业务索引id*/
	private String  attachSize;   /*附件大小*/
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getDeleteUrl() {
		return deleteUrl;
	}
	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getThumbnailresourcesName() {
		return thumbnailresourcesName;
	}
	public void setThumbnailresourcesName(String thumbnailresourcesName) {
		this.thumbnailresourcesName = thumbnailresourcesName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIsPaged() {
		return isPaged;
	}
	public void setIsPaged(String isPaged) {
		this.isPaged = isPaged;
	}
	public String getContent_type() {
		return content_type;
	}
	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}
	public String getDeleteUserPhotoUrl() {
		return deleteUserPhotoUrl;
	}
	public void setDeleteUserPhotoUrl(String deleteUserPhotoUrl) {
		this.deleteUserPhotoUrl = deleteUserPhotoUrl;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	public BigDecimal getBusinessIndex() {
		return businessIndex;
	}
	public void setBusinessIndex(BigDecimal businessIndex) {
		this.businessIndex = businessIndex;
	}
	public String getAttachSize() {
		return attachSize;
	}
	public void setAttachSize(String attachSize) {
		this.attachSize = attachSize;
	}
	
}
