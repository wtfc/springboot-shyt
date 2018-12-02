package com.jc.oa.archive.domain;

import java.util.Date;
import java.math.BigDecimal;
import com.jc.system.common.util.DateUtils;
import com.jc.foundation.domain.BaseBean;


/**
 * @title  GOA2.0源代码
 * @description  实体类
 * @author 
 * @version  2014-07-09
 */

public class Filing extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String fileName;   /**/
	private String filePath;   /**/
	private Long documentId;   /**/
	private String fizeSize;   /**/
	private Integer fileType;/*文件类型：0表单 1正文 2附件*/
	private String formContent;/*表单内容*/
	private String formType;/*表单内容*/

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getFileName(){
		return fileName;
	}
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public String getFilePath(){
		return filePath;
	}
	
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}
	
	public Long getDocumentId(){
		return documentId;
	}
	
	public void setDocumentId(Long documentId){
		this.documentId = documentId;
	}
	
	public String getFizeSize(){
		return fizeSize;
	}
	
	public void setFizeSize(String fizeSize){
		this.fizeSize = fizeSize;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public String getFormContent() {
		return formContent;
	}

	public void setFormContent(String formContent) {
		this.formContent = formContent;
	}
	
}