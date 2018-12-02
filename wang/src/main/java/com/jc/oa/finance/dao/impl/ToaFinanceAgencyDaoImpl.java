package com.jc.oa.finance.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.finance.domain.ToaFinanceAgency;
import com.jc.oa.finance.dao.IToaFinanceAgencyDao;
/**
 * @author mrb
 * @version 代理费
 */
@Repository
public class ToaFinanceAgencyDaoImpl extends BaseDaoImpl<ToaFinanceAgency> implements IToaFinanceAgencyDao{
	
	public ToaFinanceAgencyDaoImpl(){};
}
