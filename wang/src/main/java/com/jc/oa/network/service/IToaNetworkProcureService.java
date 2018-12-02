package com.jc.oa.network.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.network.domain.ToaNetworkProcure;
/**
 * @author mrb
 * @version 网络设备采购表
*/
public interface IToaNetworkProcureService extends IBaseService<ToaNetworkProcure>{

	public Integer deleteByIds(ToaNetworkProcure toaNetworkProcure) throws CustomException;
}
