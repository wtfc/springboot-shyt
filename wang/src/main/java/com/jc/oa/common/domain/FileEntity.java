package com.jc.oa.common.domain;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.jc.foundation.domain.BaseBean;


/**
 * @title  GOA2.0源代码
 * @description 系统提醒轮询时间公共表; InnoDB free: 11264 kB 实体类
 * @author 
 * @version  2014-04-17
 */

public class FileEntity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3250968578647582834L;
	
	private String fileName;
	
	private String fileType;
	
	private String fileSize;
	
	/**
	 *  文件base64格式数据
	 */
	private String base64;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}
	
	
	
}