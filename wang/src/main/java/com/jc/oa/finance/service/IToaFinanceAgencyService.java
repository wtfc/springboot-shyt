package com.jc.oa.finance.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.finance.domain.ToaFinanceAgency;
/**
 * @author mrb
 * @version 代理费
*/
public interface IToaFinanceAgencyService extends IBaseService<ToaFinanceAgency>{

	public Integer deleteByIds(ToaFinanceAgency toaFinanceAgency) throws CustomException;
}
