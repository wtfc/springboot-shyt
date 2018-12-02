package com.jc.system.content.service;

import java.util.List;

import com.jc.foundation.service.IBaseService;
import com.jc.system.content.domain.Attach;

/**
 * @title GOA2.0
 * @description  业务接口类
 * @author 
 * @version  2014-04-17
 */

public interface IAttachService extends IBaseService<Attach>{
	/**
	 * 使用业务id拼接的字符串查询所有的附件
	 * 
	 * @param attach
	 * @throws Exception
	 */
	public List<Attach> queryAttachByBusinessIds(Attach attach) throws Exception;
}