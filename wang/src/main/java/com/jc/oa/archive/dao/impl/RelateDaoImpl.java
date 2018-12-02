package com.jc.oa.archive.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.oa.archive.domain.Relate;
import com.jc.oa.archive.dao.IRelateDao;
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
public class RelateDaoImpl extends BaseDaoImpl<Relate> implements IRelateDao{

	public RelateDaoImpl(){}

	public void deleteRelateDM(Relate relate) {
		template.delete(getNameSpace(relate) + ".deleteRelate", relate);
	}
}