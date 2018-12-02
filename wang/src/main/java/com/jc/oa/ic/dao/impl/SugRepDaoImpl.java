package com.jc.oa.ic.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.oa.ic.domain.SugRecipient;
import com.jc.oa.ic.domain.SugRep;
import com.jc.oa.ic.dao.ISugRepDao;
import com.jc.foundation.dao.impl.BaseDaoImpl;

/**
 * @title 互动交流
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-17
 */
@Repository
public class SugRepDaoImpl extends BaseDaoImpl<SugRep> implements ISugRepDao{
	/**
	* @description 修改删除标记
	* @param SugRep sugRep 实体类
	* @return Integer 操作结果
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-10 
	*/
	@Override
	public Integer updateDeleteFlagByIds(SugRep sugRep) {
		return template.update(getNameSpace(sugRep) + ".updateDeleteFlagByIds" , sugRep);
	}

}