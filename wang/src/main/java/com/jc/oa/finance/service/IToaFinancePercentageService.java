package com.jc.oa.finance.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.finance.domain.ToaFinancePercentage;
/**
 * @author mrb
 * @version 绩效提成表
*/
public interface IToaFinancePercentageService extends IBaseService<ToaFinancePercentage>{

	public Integer deleteByIds(ToaFinancePercentage toaFinancePercentage) throws CustomException;
}
