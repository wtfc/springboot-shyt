package com.jc.oa.finance.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.finance.domain.ToaFinancePercentage;
import com.jc.oa.finance.dao.IToaFinancePercentageDao;
/**
 * @author mrb
 * @version 绩效提成表
 */
@Repository
public class ToaFinancePercentageDaoImpl extends BaseDaoImpl<ToaFinancePercentage> implements IToaFinancePercentageDao{
	
	public ToaFinancePercentageDaoImpl(){};
}
