package com.jc.oa.ic.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.oa.ic.domain.Out;


/**
 * @title HR
 * @description  dao接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-29
 */
 
public interface IOutDao extends IBaseDao<Out>{
	
	/**
	 * 方法描述：查询某个月发送了多少条短信
	 * @param out
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月3日下午1:15:25
	 * @see
	 */
	Integer querySendCount(Out out);

	
}
