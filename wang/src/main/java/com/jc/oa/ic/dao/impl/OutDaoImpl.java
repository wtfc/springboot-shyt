package com.jc.oa.ic.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.ic.dao.IOutDao;
import com.jc.oa.ic.domain.Out;

/**
 * @title HR
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-29
 */
@Repository
public class OutDaoImpl extends BaseDaoImpl<Out> implements IOutDao{
	/**
	 * 查询可发送短信数量
	 */
	public static final String SQL_querySendCount= "querySendCount";

	public OutDaoImpl(){}
	
	/**
	 * 方法描述：查询某个月发送了多少条短信
	 * @param out
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月3日下午1:15:25
	 * @see
	 */
	public Integer querySendCount(Out o){
		Integer count = template.selectOne(getNameSpace(o) + "."+SQL_querySendCount, o);
		return count;
	}

}