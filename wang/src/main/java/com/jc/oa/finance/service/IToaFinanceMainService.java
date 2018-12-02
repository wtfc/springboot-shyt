package com.jc.oa.finance.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.finance.domain.Invoices;
import com.jc.oa.finance.domain.ToaFinanceMain;
/**
 * @author mrb
 * @version 收入主体表
*/
public interface IToaFinanceMainService extends IBaseService<ToaFinanceMain>{

	public Integer deleteByIds(ToaFinanceMain toaFinanceMain) throws CustomException;
	
	public Integer saveMainInvoices(ToaFinanceMain toaFinanceMain,Invoices invoices)throws CustomException;
	
	public Integer updateMainInvoices(ToaFinanceMain toaFinanceMain,Invoices invoices)throws CustomException;
}
