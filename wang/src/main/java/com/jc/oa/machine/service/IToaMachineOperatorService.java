package com.jc.oa.machine.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.machine.domain.ToaMachineOperator;
/**
 * @author mrb
 * @version 机房操作
*/
public interface IToaMachineOperatorService extends IBaseService<ToaMachineOperator>{

	public Integer deleteByIds(ToaMachineOperator toaMachineOperator) throws CustomException;
}
