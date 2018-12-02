package com.jc.oa.ic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.dao.IFolderDao;
import com.jc.oa.ic.domain.MailFolder;
import com.jc.oa.ic.service.IFolderService;
import com.jc.system.CustomException;
import com.jc.system.security.SystemSecurityUtils;

/**
 * 
 * @title GOA V2.0
 * @description  邮件文件夹管理
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author zhanglg
 * @version  2014年5月27日 下午8:10:37
 */
@Service
public class FolderServiceImpl extends BaseServiceImpl<MailFolder> implements
		IFolderService {
	private IFolderDao folderDao;

	@Autowired
	public FolderServiceImpl(IFolderDao folderDao) {
		super(folderDao);
		this.folderDao = folderDao;
	}

	public FolderServiceImpl() {
	}

	/**
	 * 方法描述：获取所有邮件文件夹列表
	 * 
	 * @param folder
	 * @return
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月14日上午9:51:53
	 * @see
	 */
	@Override
	public List<MailFolder> queryAll(MailFolder folder) throws CustomException {
		folder.setCreateUser(SystemSecurityUtils.getUser().getId());
		return folderDao.queryAll(folder);
	}

}
