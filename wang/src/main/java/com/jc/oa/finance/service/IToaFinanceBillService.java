package com.jc.oa.finance.service;

import java.util.List;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.finance.domain.ToaFinanceBill;
import com.jc.oa.finance.domain.ToaFinanceInvoices;
/**
 * @author mrb
 * @version 账单信息
*/
public interface IToaFinanceBillService extends IBaseService<ToaFinanceBill>{

	public Integer deleteByIds(ToaFinanceBill toaFinanceBill) throws CustomException;
	//根据月账单id 查询资源信息 应收金额
	public List<ToaFinanceInvoices> involvesList(ToaFinanceInvoices financeInvoices)throws CustomException;

	//账单审批状态
	public Integer state(ToaFinanceBill toaFinanceBill) throws CustomException;
	
	public Integer invoicesBill() throws CustomException;
	
	//取一年中  每月总实际回款金额、应收金额、欠费金额
	public  List<ToaFinanceBill> queryByMonth(ToaFinanceBill toaFinanceBill) throws CustomException;
	//取一年金额
	public List<ToaFinanceBill> queryByYear(ToaFinanceBill toaFinanceBill) throws CustomException;
}
