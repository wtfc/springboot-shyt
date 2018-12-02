package com.jc.oa.network.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.network.domain.ToaNetworkIpresource;
/**
 * @author mrb
 * @version IP总资源表
*/
public interface IToaNetworkIpresourceService extends IBaseService<ToaNetworkIpresource>{

	public Integer deleteByIds(ToaNetworkIpresource toaNetworkIpresource) throws CustomException;
}
