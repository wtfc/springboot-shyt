package com.jc.oa.finance.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.finance.domain.ToaFinanceMain;
import com.jc.oa.finance.dao.IToaFinanceMainDao;
/**
 * @author mrb
 * @version 收入主体表
 */
@Repository
public class ToaFinanceMainDaoImpl extends BaseDaoImpl<ToaFinanceMain> implements IToaFinanceMainDao{
	
	public ToaFinanceMainDaoImpl(){};
}
