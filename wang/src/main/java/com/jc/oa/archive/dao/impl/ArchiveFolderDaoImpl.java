package com.jc.oa.archive.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.oa.archive.domain.Folder;
import com.jc.oa.archive.dao.IArchiveFolderDao;
import com.jc.foundation.dao.impl.BaseDaoImpl;

/**
 * @title  GOA2.0源代码
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-06-05
 */
@Repository
public class ArchiveFolderDaoImpl extends BaseDaoImpl<Folder> implements IArchiveFolderDao{

	public ArchiveFolderDaoImpl(){}

	@Override
	public int deleteDirToRecycle(Folder folder) {
		return template.update(getNameSpace(folder)+".deleteDirToRecycle", folder);
	}

	/**
	 * @description 权限过滤文件夹
	 * @param Folder folder 实体类
	 * @return List<Folder>
	 * @throws Exception 
	 * @author weny
	 * @version  2014-07-09
	 */
	public List<Folder> getFolderPermission(Folder folder) {
		return template.selectList(getNameSpace(folder) + ".getFolderPermission", folder);
	}

	@Override
	public int updateAllChildPath(Folder folder) {
		return template.update(getNameSpace(folder) + ".updateAllChildPath", folder);
	}

}