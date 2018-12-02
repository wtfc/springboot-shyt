package com.jc.oa.finance.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.finance.domain.ToaFinanceSplit;
/**
 * @author mrb
 * @version 权责发生制
*/
public interface IToaFinanceSplitService extends IBaseService<ToaFinanceSplit>{

	public Integer deleteByIds(ToaFinanceSplit toaFinanceSplit) throws CustomException;
}
