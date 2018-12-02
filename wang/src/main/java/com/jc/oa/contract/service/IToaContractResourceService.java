package com.jc.oa.contract.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.contract.domain.ToaContractResource;
/**
 * @author mrb
 * @version 合同资源表
*/
public interface IToaContractResourceService extends IBaseService<ToaContractResource>{

	public Integer deleteByIds(ToaContractResource toaContractResource) throws CustomException;
}
