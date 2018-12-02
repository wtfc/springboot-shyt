package com.jc.android.oa.exception.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.android.oa.exception.dao.IExceptionDao;
import com.jc.android.oa.exception.domain.Exception4M;
import com.jc.foundation.dao.impl.BaseDaoImpl;


/**
 * @author 
 * @version  2014-12-10
 */
@Repository
public class ExceptionDaoImpl extends BaseDaoImpl<Exception4M> implements IExceptionDao{
	public ExceptionDaoImpl(){}
}