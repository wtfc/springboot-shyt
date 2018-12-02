package com.jc.oa.product.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.product.domain.ToaProductCloudtest;
/**
 * @author mrb
 * @version 云主机测试表
*/
public interface IToaProductCloudtestService extends IBaseService<ToaProductCloudtest>{

	public Integer deleteByIds(ToaProductCloudtest toaProductCloudtest) throws CustomException;
}
