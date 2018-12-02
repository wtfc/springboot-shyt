package com.jc.oa.network.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.network.domain.ToaNetworkIpaddress;
/**
 * @author mrb
 * @version 客户IP地址统计表
*/
public interface IToaNetworkIpaddressService extends IBaseService<ToaNetworkIpaddress>{

	public Integer deleteByIds(ToaNetworkIpaddress toaNetworkIpaddress) throws CustomException;
}
