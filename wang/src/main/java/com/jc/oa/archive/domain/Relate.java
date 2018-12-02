package com.jc.oa.archive.domain;

import com.jc.foundation.domain.BaseBean;


/**
 * @title  GOA2.0源代码
 * @description OA_文档管理_文档关联表 实体类
 * @author 
 * @version  2014-06-05
 */

public class Relate extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long documentId;   /*文档ID*/
	private Long dmRelateId;   /*关联文档ID*/
	private Long folderId;   /*关联文档所在文件夹ID*/
	private String referenceFlag;   /*是否单项/双项[0-单项;1-双项]*/
	private Integer weight;   /*安全系数*/
	
	private String dmName;   /*文档名称*/
	private String dmDir;   /*文档所在目录位置*/
	private String dmSuffix;/*文档后缀名*/
	private String dmId;/*文档后缀名*/
	
	public String getDmId() {
		return dmId;
	}

	public void setDmId(String dmId) {
		this.dmId = dmId;
	}

	public String getDmSuffix() {
		return dmSuffix;
	}

	public void setDmSuffix(String dmSuffix) {
		this.dmSuffix = dmSuffix;
	}

	public Long getDocumentId(){
		return documentId;
	}
	
	public void setDocumentId(Long documentId){
		this.documentId = documentId;
	}
	
	public Long getDmRelateId(){
		return dmRelateId;
	}
	
	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public void setDmRelateId(Long dmRelateId){
		this.dmRelateId = dmRelateId;
	}
	
	public String getReferenceFlag(){
		return referenceFlag;
	}
	
	public void setReferenceFlag(String referenceFlag){
		this.referenceFlag = referenceFlag;
	}
	
	public Integer getWeight(){
		return weight;
	}
	
	public void setWeight(Integer weight){
		this.weight = weight;
	}

	public String getDmName() {
		return dmName;
	}

	public void setDmName(String dmName) {
		this.dmName = dmName;
	}

	public String getDmDir() {
		return dmDir;
	}

	public void setDmDir(String dmDir) {
		this.dmDir = dmDir;
	}
	
}