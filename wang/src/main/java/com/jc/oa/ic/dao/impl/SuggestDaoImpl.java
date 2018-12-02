package com.jc.oa.ic.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.ic.dao.ISuggestDao;
import com.jc.oa.ic.domain.Suggest;

/**
 * @title GOA V2.0 互动交流
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */
@Repository
public class SuggestDaoImpl extends BaseDaoImpl<Suggest> implements ISuggestDao{
	/**
	 * 条件查询SQL ID
	 */
	public static final String Suggest= "selectOne";
	/**
	 * @description 获取单条记录方法
	 * @param Suggest suggest 实体类
	 * @return SuggestType 查询结果
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17
	 */
	public Suggest get(Suggest suggest){
		return (Suggest) template.selectOne(getNameSpace(suggest) + "."+Suggest, suggest);
	}
}