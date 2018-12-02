package com.jc.system.content.dao;

import java.util.List;

import com.jc.system.content.domain.Attach;
import com.jc.foundation.dao.IBaseDao;


/**
 * @title GOA2.0
 * @description  dao接口类
 * @author 
 * @version  2014-04-17
 */
 
public interface IAttachDao extends IBaseDao<Attach>{

	/**
	 * 使用业务id拼接的字符串查询所有的附件
	 * 
	 * @param attach
	 * @throws Exception
	 */
	public List<Attach> queryAttachByBusinessIds(Attach attach) throws Exception;
}
