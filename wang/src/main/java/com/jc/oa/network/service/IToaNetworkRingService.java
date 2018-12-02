package com.jc.oa.network.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.network.domain.ToaNetworkRing;
/**
 * @author mrb
 * @version 骨干网链路带宽统计表
*/
public interface IToaNetworkRingService extends IBaseService<ToaNetworkRing>{

	public Integer deleteByIds(ToaNetworkRing toaNetworkRing) throws CustomException;
}
