package com.jc.oa.machine.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.machine.domain.ToaMachineInout;
/**
 * @author mrb
 * @version 机房进出
*/
public interface IToaMachineInoutService extends IBaseService<ToaMachineInout>{

	public Integer deleteByIds(ToaMachineInout toaMachineInout) throws CustomException;
}
