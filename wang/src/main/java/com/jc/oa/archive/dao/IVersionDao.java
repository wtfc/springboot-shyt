package com.jc.oa.archive.dao;

import com.jc.oa.archive.domain.Version;
import com.jc.foundation.dao.IBaseDao;


/**
 * @title  GOA2.0源代码
 * @description  dao接口类
 * @author 
 * @version  2014-07-01
 */
 
public interface IVersionDao extends IBaseDao<Version>{

	/**
	 * 方法描述：获取文档最大版本号
	 * @param backUpId
	 * @return
	 * @author zhangligang
	 * @version  2014年7月3日下午3:03:04
	 * @see
	 */
	public Integer getMaxVersion(Long backUpId);

	
}
