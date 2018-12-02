package com.jc.oa.product.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.product.domain.ToaProductCloud;
/**
 * @author mrb
 * @version 云计算业务表
*/
public interface IToaProductCloudService extends IBaseService<ToaProductCloud>{

	public Integer deleteByIds(ToaProductCloud toaProductCloud) throws CustomException;
}
