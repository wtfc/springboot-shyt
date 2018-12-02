package com.jc.oa.ic.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.ic.dao.IFolderDao;
import com.jc.oa.ic.domain.MailFolder;

@Repository
public class FolderDaoImpl extends BaseDaoImpl<MailFolder> implements
		IFolderDao {

	@Override
	public List<MailFolder> queryAll(MailFolder folder) {
		return template.selectList(getNameSpace(folder) + ".query", folder);
	}

}
