package com.jc.oa.network.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.network.domain.ToaNetworkIpstop;
/**
 * @author mrb
 * @version IP封停记录表
*/
public interface IToaNetworkIpstopService extends IBaseService<ToaNetworkIpstop>{

	public Integer deleteByIds(ToaNetworkIpstop toaNetworkIpstop) throws CustomException;
}
