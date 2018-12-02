package com.jc.android.oa.ic.domain;

public class Attach4M {
	
	
	private Long id;/*附件ID*/
	private String fileName;   /*文件名称*/
	private Long fileSize;   /*文件大小*/
	private String resourcesName;   /*文件相对路径*/
	
	
	public String getResourcesName() {
		return resourcesName;
	}
	public void setResourcesName(String resourcesName) {
		this.resourcesName = resourcesName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	
	

}
