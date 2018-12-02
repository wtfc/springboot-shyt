package com.jc.android.oa.exception.service;

import com.jc.android.oa.exception.domain.Exception4M;
import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;

/**
 * @author 
 * @version  2014-12-10
 */

public interface IExceptionService extends IBaseService<Exception4M>{
	/**
	* @description 根据主键删除多条记录方法
	* @param Exception4M exception 实体类
	* @return Integer 处理结果
	* @author
	* @version  2014-12-10 
	*/
	public Integer deleteByIds(Exception4M exception) throws CustomException;

	
}