package com.jc.oa.finance.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.finance.domain.ToaFinanceIncome;
/**
 * @author mrb
 * @version 收入底表
*/
public interface IToaFinanceIncomeService extends IBaseService<ToaFinanceIncome>{

	public Integer deleteByIds(ToaFinanceIncome toaFinanceIncome) throws CustomException;
	
	public void updateFinanceIncome() throws CustomException; 
}
