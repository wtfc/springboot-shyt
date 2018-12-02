package com.jc.oa.ic.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.oa.ic.domain.SuggestType;


/**
 * @title GOA V2.0 互动交流
 * @description  dao接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */
 
public interface ISuggestTypeDao extends IBaseDao<SuggestType>{
	/**
	 * @description 获取单条记录方法
	 * @param SuggestType suggestType 实体类
	 * @return SuggestType 查询结果
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17
	 */
	public SuggestType get(SuggestType suggestType);
}
