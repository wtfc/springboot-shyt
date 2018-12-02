package com.jc.oa.product.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.product.domain.ToaProductCdn;
/**
 * @author mrb
 * @version CDN业务表
*/
public interface IToaProductCdnService extends IBaseService<ToaProductCdn>{

	public Integer deleteByIds(ToaProductCdn toaProductCdn) throws CustomException;
}
