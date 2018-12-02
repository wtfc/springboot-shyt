package com.jc.android.oa.exception.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.android.oa.exception.dao.IExceptionDao;
import com.jc.android.oa.exception.domain.Exception4M;
import com.jc.android.oa.exception.service.IExceptionService;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.CustomException;

/**
 * @author 
 * @version  2014-12-10
 */
@Service
public class ExceptionServiceImpl extends BaseServiceImpl<Exception4M> implements IExceptionService{

	private IExceptionDao exceptionDao;
	
	public ExceptionServiceImpl(){}
	
	@Autowired
	public ExceptionServiceImpl(IExceptionDao exceptionDao){
		super(exceptionDao);
		this.exceptionDao = exceptionDao;
	}

	/**
	* @description 根据主键删除多条记录方法
	* @param Exception4M exception 实体类
	* @return Integer 处理结果
	* @author
	* @version  2014-12-10 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(Exception4M exception) throws CustomException{
		Integer result = -1;
		try{
			result = exceptionDao.delete(exception);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(exception);
			throw ce;
		}
		return result;
	}

}