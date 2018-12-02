package com.jc.oa.machine.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.machine.domain.ToaMachineNetworkfault;
/**
 * @author mrb
 * @version 网络故障
*/
public interface IToaMachineNetworkfaultService extends IBaseService<ToaMachineNetworkfault>{

	public Integer deleteByIds(ToaMachineNetworkfault toaMachineNetworkfault) throws CustomException;
}
