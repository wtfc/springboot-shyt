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
	private String resourcesName;//   文件相对路径	private String fileName;//   文件名称	private Long fileSize;//   文件大小	private Date uploadTime;//   文件创建时间	private Integer businessId;//   业务ID--》工单id	private String businessSource;//   业务来源-->工单类型	private String contentType;//   响应类型	private String ext;//   文件后缀	private String category;//   文件分类	private String userId;//   上传人员id	private String userName;//   上传人员姓名
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
	private String  attachSize;   /*附件大小*/		public String getResourcesName() {	    return resourcesName;	}	public void setResourcesName(String resourcesName) {	    this.resourcesName=resourcesName;	}	public String getFileName() {	    return fileName;	}	public void setFileName(String fileName) {	    this.fileName=fileName;	}	public Long getFileSize() {	    return fileSize;	}	public void setFileSize(Long fileSize) {	    this.fileSize=fileSize;	}	public Date getUploadTime() {	    return uploadTime;	}	public void setUploadTime(Date uploadTime) {	    this.uploadTime=uploadTime;	}	public Integer getBusinessId() {	    return businessId;	}	public void setBusinessId(Integer businessId) {	    this.businessId=businessId;	}	public String getBusinessSource() {	    return businessSource;	}	public void setBusinessSource(String businessSource) {	    this.businessSource=businessSource;	}	public String getContentType() {	    return contentType;	}	public void setContentType(String contentType) {	    this.contentType=contentType;	}	public String getExt() {	    return ext;	}	public void setExt(String ext) {	    this.ext=ext;	}	public String getCategory() {	    return category;	}	public void setCategory(String category) {	    this.category=category;	}	public String getUserId() {	    return userId;	}	public void setUserId(String userId) {	    this.userId=userId;	}	public String getUserName() {	    return userName;	}	public void setUserName(String userName) {	    this.userName=userName;	}
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

