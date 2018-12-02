package com.jc.oa.network.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.network.domain.ToaNetworkTenancies;
/**
 * @author mrb
 * @version 退租客户记录表
*/
public interface IToaNetworkTenanciesService extends IBaseService<ToaNetworkTenancies>{

	public Integer deleteByIds(ToaNetworkTenancies toaNetworkTenancies) throws CustomException;
}
