package com.jc.oa.ic.dao;

import com.jc.oa.ic.domain.SugRep;
import com.jc.foundation.dao.IBaseDao;


/**
 * @title 互动交流
 * @description  dao接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-17
 */
 
public interface ISugRepDao extends IBaseDao<SugRep>{
	/**
	* @description 修改删除标记
	* @param SugRep sugRep 实体类
	* @return Integer 操作结果
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-10 
	*/
	public Integer updateDeleteFlagByIds(SugRep sugRep) ;
}
