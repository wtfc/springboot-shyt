package com.jc.oa.network.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.network.domain.ToaNetworkMachinewidth;
/**
 * @author mrb
 * @version 在网客户机房带宽统计表
*/
public interface IToaNetworkMachinewidthService extends IBaseService<ToaNetworkMachinewidth>{

	public Integer deleteByIds(ToaNetworkMachinewidth toaNetworkMachinewidth) throws CustomException;
}
