package com.jc.oa.ic.domain;

import java.util.List;

import com.jc.system.common.domain.SecurityBean;

/**
 * 
 * @title GOA V2.0
 * @description 邮件文件夹 实体类 
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights
 * Reserved Company 长春嘉诚网络工程有限公司
 * @author zhanglg
 * @version 2014年5月7日 下午3:13:05
 */
public class MailFolder extends SecurityBean { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 5136371057987087297L;

	private String folderName;
	
	private List<Contacts> contactsList;

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public List<Contacts> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<Contacts> contactsList) {
		this.contactsList = contactsList;
	}

}
