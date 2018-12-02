package com.jc.oa.product.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.product.domain.ToaProductIdc;
/**
 * @author mrb
 * @version IDC业务表
*/
public interface IToaProductIdcService extends IBaseService<ToaProductIdc>{

	public Integer deleteByIds(ToaProductIdc toaProductIdc) throws CustomException;
}
