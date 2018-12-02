package com.jc.oa.network.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.network.domain.ToaNetworkMachine;
/**
 * @author mrb
 * @version 网络设备统计表
*/
public interface IToaNetworkMachineService extends IBaseService<ToaNetworkMachine>{

	public Integer deleteByIds(ToaNetworkMachine toaNetworkMachine) throws CustomException;
}
