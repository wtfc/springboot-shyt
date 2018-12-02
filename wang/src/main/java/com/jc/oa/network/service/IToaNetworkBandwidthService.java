package com.jc.oa.network.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.network.domain.ToaNetworkBandwidth;
/**
 * @author mrb
 * @version 在网客户带宽统计表表
*/
public interface IToaNetworkBandwidthService extends IBaseService<ToaNetworkBandwidth>{

	public Integer deleteByIds(ToaNetworkBandwidth toaNetworkBandwidth) throws CustomException;
}
