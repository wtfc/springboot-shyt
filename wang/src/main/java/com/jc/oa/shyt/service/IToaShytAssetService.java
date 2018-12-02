package com.jc.oa.shyt.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.shyt.domain.ToaShytAsset;
/**
 * @author mrb
 * @version 资产信息表
*/
public interface IToaShytAssetService extends IBaseService<ToaShytAsset>{

	public Integer deleteByIds(ToaShytAsset toaShytAsset) throws CustomException;
}
