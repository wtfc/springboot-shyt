package com.jc.oa.finance.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.finance.domain.ToaFinanceSplit;
import com.jc.oa.finance.dao.IToaFinanceSplitDao;
/**
 * @author mrb
 * @version 权责发生制
 */
@Repository
public class ToaFinanceSplitDaoImpl extends BaseDaoImpl<ToaFinanceSplit> implements IToaFinanceSplitDao{
	
	public ToaFinanceSplitDaoImpl(){};
}
