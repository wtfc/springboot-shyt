package com.jc.oa.archive.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.oa.archive.domain.Document;
import com.jc.oa.archive.domain.Version;
import com.jc.oa.archive.dao.IVersionDao;
import com.jc.foundation.dao.impl.BaseDaoImpl;

/**
 * @title  GOA2.0源代码
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-07-01
 */
@Repository
public class VersionDaoImpl extends BaseDaoImpl<Version> implements IVersionDao{

	public VersionDaoImpl(){}

	@Override
	public Integer getMaxVersion(Long backUpId){
		return template.selectOne(getNameSpace(new Version())+".getMaxVersion",backUpId);
	}
}