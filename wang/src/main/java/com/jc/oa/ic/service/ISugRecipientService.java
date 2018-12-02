package com.jc.oa.ic.service;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.ic.domain.SugRecipient;

/**
 * @title GOA V2.0 互动交流
 * @description  业务接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */

public interface ISugRecipientService extends IBaseService<SugRecipient>{
	/**
	* @description 修改删除标记
	* @param SugRecipient sugRecipient 实体类
	* @return Integer 操作结果
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-10 
	*/
	public Integer updateDeleteFlagByIds(SugRecipient sugRecipient) ;
}