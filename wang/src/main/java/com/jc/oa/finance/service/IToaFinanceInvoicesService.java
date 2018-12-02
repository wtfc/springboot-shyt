package com.jc.oa.finance.service;


import java.util.List;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.finance.domain.ToaFinanceInvoices;
/**
 * @author mrb
 * @version 月收入
*/
public interface IToaFinanceInvoicesService extends IBaseService<ToaFinanceInvoices>{

	public Integer deleteByIds(ToaFinanceInvoices toaFinanceInvoices) throws CustomException;
	
	public Integer createBill(ToaFinanceInvoices toaFinanceInvoices,String ids) throws CustomException;
	
	public List<ToaFinanceInvoices> getMain(ToaFinanceInvoices toaFinanceInvoices)throws CustomException;
	
	public Integer update(ToaFinanceInvoices toaFinanceInvoices)throws CustomException ;
	
	public PageManager mainInvoices(ToaFinanceInvoices toaFinanceInvoices, PageManager page)throws CustomException;
	//应收月份
	public List<ToaFinanceInvoices> queryInvoices(ToaFinanceInvoices toaFinanceInvoices) throws CustomException;
	//应收月份和客户名称
	public List<ToaFinanceInvoices> queryInvoicesName(ToaFinanceInvoices toaFinanceInvoices) throws CustomException;
}
