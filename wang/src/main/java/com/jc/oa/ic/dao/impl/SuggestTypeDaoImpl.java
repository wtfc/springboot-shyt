package com.jc.oa.ic.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.ic.dao.ISuggestTypeDao;
import com.jc.oa.ic.domain.SuggestType;

/**
 * @title GOA V2.0 互动交流
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */
@Repository
public class SuggestTypeDaoImpl extends BaseDaoImpl<SuggestType> implements ISuggestTypeDao{
	/**
	 * 条件查询SQL ID
	 */
	public static final String SuggestType= "selectOne";
	/**
	 * @description 获取单条记录方法
	 * @param SuggestType suggestType 实体类
	 * @return SuggestType 查询结果
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17
	 */
	public SuggestType get(SuggestType suggestType){
		return (SuggestType) template.selectOne(getNameSpace(suggestType) + "."+SuggestType, suggestType);
	}
}