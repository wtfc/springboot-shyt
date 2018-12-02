package com.jc.oa.machine.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.machine.domain.ToaMachineFault;
/**
 * @author mrb
 * @version 机房故障
*/
public interface IToaMachineFaultService extends IBaseService<ToaMachineFault>{

	public Integer deleteByIds(ToaMachineFault toaMachineFault) throws CustomException;
}
