package com.jc.system.content.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.system.content.UploadConstants;


/**
 * @title GOA2.0
 * @description  实体类
 * @author 
 * @version  2014-04-17
 */

public class Attach extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String resourcesName;   /*文件相对路径*/
	private String fileName;   /*文件名称*/
	private Long fileSize;   /*文件大小*/
	private Date uploadTime;   /*文件创建时间*/
	private Long businessId;   /*业务ID*/
	private String businessSource;   /*业务来源*/
	private String businessTable;   /*业务表*/
	private String error = "";
	private String deleteUrl = "";
	private String thumbnailUrl = "";
	private String thumbnailresourcesName = "";//缩略图物理地址
	private String fileUrl = "";
	private String url="";
	private String isPaged="0";//为0时查询单条记录，1时分页查询
	private String content_type ="";
	private String ext ="";
	private String category ="";
	private String deleteUserPhotoUrl;
	private String userid = "";   /*上传人id*/
	private String username = "";   /*上传人名字*/
	private String originalUrl="";  /*原图地址*/
	private BigDecimal businessIndex;/*附件业务索引id*/
	private String  attachSize;   /*附件大小*/
	
	public String getAttachSize() {
		return attachSize;
	}

	public void setAttachSize(String attachSize) {
		this.attachSize = attachSize;
	}

	private String businessIdArray[]; 
	
	
	public String[] getBusinessIdArray() {
		return businessIdArray;
	}

	public void setBusinessIdArray(String[] businessIdArray) {
		this.businessIdArray = businessIdArray;
	}

	public BigDecimal getBusinessIndex() {
		return businessIndex;
	}

	public void setBusinessIndex(BigDecimal businessIndex) {
		this.businessIndex = businessIndex;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
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
	
	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getThumbnailresourcesName() {
		return thumbnailresourcesName;
	}

	public void setThumbnailresourcesName(String thumbnailresourcesName) {
		this.thumbnailresourcesName = thumbnailresourcesName;
	}

	public String getBusinessTable() {
		return businessTable;
	}

	public void setBusinessTable(String businessTable) {
		this.businessTable = businessTable;
	}
	public String getIsPaged() {
		return isPaged;
	}



	public void setIsPaged(String isPaged) {
		this.isPaged = isPaged;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String getFileUrl() {
		return fileUrl;
	}



	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	private String deleteType = DELETE_TYPE;
	private static final String DELETE_TYPE = "DELETE";

	public String getError() {
		return error;
	}

	
	
	public String getDeleteUrl() {
		return deleteUrl;
	}



	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}



	public String getThumbnailUrl() {
//		String resourcesName = this.getResourcesName();
//		int index = oriName.lastIndexOf(".") ;
//		if(index ==-1){
//			fileExt = "";
//		}
//		else{
//			fileExt = oriName.substring(index+1)
//				.toLowerCase();
//		}
//		newNameBase + "-thumbnail.png";
		return thumbnailUrl;
	}



	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}



	public String getDeleteType() {
		return deleteType;
	}



	public void setDeleteType(String deleteType) {
		this.deleteType = deleteType;
	}



	public void setError(String error) {
		this.error = error;
	}

	

	public void setId(Long id) {
		super.setId(id);
		setDeleteUrl(UploadConstants.REQUEST_URI+"/delete/"+id);
		setThumbnailUrl(UploadConstants.REQUEST_URI+"/thumbnail/"+id);
		setOriginalUrl(UploadConstants.REQUEST_URI+"/original/"+id);
		setFileUrl(UploadConstants.REQUEST_URI+"/download/"+id);
		setUrl(UploadConstants.REQUEST_URI+"/download.action");
		setDeleteUserPhotoUrl(UploadConstants.REQUEST_URI+"/deleteUserPhoto");
	}

	public String getResourcesName(){
		return resourcesName;
	}
	
	public Long getSize(){
		return this.getFileSize();
	}
	
	public void setResourcesName(String resourcesName){
		this.resourcesName = resourcesName;
	}
	public String getFileName(){
		return fileName;
	}
	public String getName(){
		return this.getFileName();
	}
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	public Long getFileSize(){
		return fileSize;
	}
	
	public void setFileSize(Long fileSize){
		this.fileSize = fileSize;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getUploadTime(){
		return uploadTime;
	}
	
	public void setUploadTime(Date uploadTime){
		this.uploadTime = uploadTime;
	}
	public Long getBusinessId(){
		return businessId;
	}
	
	public void setBusinessId(Long businessId){
		this.businessId = businessId;
	}
	public String getBusinessSource(){
		return businessSource;
	}
	
	public void setBusinessSource(String businessSource){
		this.businessSource = businessSource;
	}

	public String getDeleteUserPhotoUrl() {
		return deleteUserPhotoUrl;
	}

	public void setDeleteUserPhotoUrl(String deleteUserPhotoUrl) {
		this.deleteUserPhotoUrl = deleteUserPhotoUrl;
	}
	
}