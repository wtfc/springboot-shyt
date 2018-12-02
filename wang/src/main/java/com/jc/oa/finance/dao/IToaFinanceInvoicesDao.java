package com.jc.oa.finance.dao;


import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.foundation.domain.PageManager;
import com.jc.oa.finance.domain.ToaFinanceInvoices;
/**
 * @author mrb
 * @version 月收入
*/
public interface IToaFinanceInvoicesDao extends IBaseDao<ToaFinanceInvoices> {
	
	public List<ToaFinanceInvoices> getMain(ToaFinanceInvoices toaFinanceInvoices);
	
	public Integer billState(ToaFinanceInvoices toaFinanceInvoices);
	
	public PageManager mainInvoices(ToaFinanceInvoices toaFinanceInvoices, PageManager page);
	//通过应收月份查询
	public List<ToaFinanceInvoices> queryInvoices(ToaFinanceInvoices toaFinanceInvoices);
	
	//通过应收月份和客户名称
	public List<ToaFinanceInvoices> queryInvoicesName(ToaFinanceInvoices toaFinanceInvoices);
	//修改状态
	public Integer billNewState(ToaFinanceInvoices toaFinanceInvoices);
}
