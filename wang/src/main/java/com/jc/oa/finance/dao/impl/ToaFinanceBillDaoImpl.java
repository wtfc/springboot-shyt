package com.jc.oa.finance.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.finance.domain.ToaFinanceBill;
import com.jc.oa.finance.dao.IToaFinanceBillDao;
/**
 * @author mrb
 * @version 账单信息
 */
@Repository
public class ToaFinanceBillDaoImpl extends BaseDaoImpl<ToaFinanceBill> implements IToaFinanceBillDao{
	
	private String SQL_QUERY_BY_MONTH = "queryByMonth";
	public ToaFinanceBillDaoImpl(){}

	@Override
	public Integer state(ToaFinanceBill toaFinanceBill) {
		
		return template.update(getNameSpace(toaFinanceBill)+"."+"state",toaFinanceBill);
	}


	//取一年中  每月总实际回款金额、应收金额、欠费金额
	@Override
	public List<ToaFinanceBill> queryByMonth(ToaFinanceBill toaFinanceBill) {
		
		return template.selectList(getNameSpace(toaFinanceBill) + "." + SQL_QUERY_BY_MONTH, toaFinanceBill);
	}

	/**
	 * 取得一年的金额
	 * */
	@Override
	public List<ToaFinanceBill> queryByYear(ToaFinanceBill toaFinanceBill) {
		// TODO Auto-generated method stub
		return template.selectList(getNameSpace(toaFinanceBill)+"."+"queryByYear",toaFinanceBill);
	};
}
