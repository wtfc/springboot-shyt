package com.jc.oa.finance.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.finance.domain.ToaFinanceIncome;
import com.jc.oa.finance.dao.IToaFinanceIncomeDao;
/**
 * @author mrb
 * @version 收入底表
 */
@Repository
public class ToaFinanceIncomeDaoImpl extends BaseDaoImpl<ToaFinanceIncome> implements IToaFinanceIncomeDao{
	
	public ToaFinanceIncomeDaoImpl(){};
}
