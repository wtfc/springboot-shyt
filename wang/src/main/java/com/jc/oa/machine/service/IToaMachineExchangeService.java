package com.jc.oa.machine.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.machine.domain.ToaMachineExchange;
/**
 * @author mrb
 * @version 工单附言表
*/
public interface IToaMachineExchangeService extends IBaseService<ToaMachineExchange>{

	public Integer deleteByIds(ToaMachineExchange toaMachineExchange) throws CustomException;
}
