package com.jc.oa.finance.dao;


import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.oa.finance.domain.ToaFinanceBill;
/**
 * @author mrb
 * @version 账单信息
*/
public interface IToaFinanceBillDao extends IBaseDao<ToaFinanceBill> {
	
	public Integer state(ToaFinanceBill toaFinanceBill);
	
	//取一年中  每月总实际回款金额、应收金额、欠费金额
	public  List<ToaFinanceBill> queryByMonth(ToaFinanceBill toaFinanceBill);
	
	//取一年总金额
	public List<ToaFinanceBill> queryByYear(ToaFinanceBill toaFinanceBill);
}
