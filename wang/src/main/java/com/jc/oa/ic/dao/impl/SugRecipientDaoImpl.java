package com.jc.oa.ic.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.ic.dao.ISugRecipientDao;
import com.jc.oa.ic.domain.SugRecipient;

/**
 * @title GOA V2.0 互动交流
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */
@Repository
public class SugRecipientDaoImpl extends BaseDaoImpl<SugRecipient> implements ISugRecipientDao{
	/**
	* @description 修改删除标记
	* @param SugRecipient sugRecipient 实体类
	* @return Integer 操作结果
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-10 
	*/
	@Override
	public Integer updateDeleteFlagByIds(SugRecipient sugRecipient) {
		return template.update(getNameSpace(sugRecipient) + ".updateDeleteFlagByIds" , sugRecipient);
	}
}