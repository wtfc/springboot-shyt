package com.jc.oa.po.worklog.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.oa.po.worklog.dao.IWorklogContentDao;
import com.jc.oa.po.worklog.domain.WorklogContent;
import com.jc.foundation.dao.impl.BaseDaoImpl;

/**
 * @title 个人办公
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李新桐
 * @version  2014-05-04
 */
@Repository
public class WorklogContentDaoImpl extends BaseDaoImpl<WorklogContent> implements IWorklogContentDao{

	public WorklogContentDaoImpl(){}
	
	@Override
	public int deleteByworklogId(WorklogContent worklogContent) {
		return template.delete(getNameSpace(worklogContent) + ".deleteByWorklogId", worklogContent);
	}

	@Override
	public int deleteByworklogIdLogic(WorklogContent worklogContent) {
		return template.update(getNameSpace(worklogContent) + ".deleteByWorklogIdLogic", worklogContent);
	}
	
	

}