package com.jc.oa.network.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.network.domain.ToaNetworkPort;
/**
 * @author mrb
 * @version 端口使用情况统计表
*/
public interface IToaNetworkPortService extends IBaseService<ToaNetworkPort>{

	public Integer deleteByIds(ToaNetworkPort toaNetworkPort) throws CustomException;
}
