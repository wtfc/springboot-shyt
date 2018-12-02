package com.jc.oa.contract.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.contract.domain.ToaContractIncontract;
/**
 * @author mrb
 * @version 收款方合同
*/
public interface IToaContractIncontractService extends IBaseService<ToaContractIncontract>{

	public Integer deleteByIds(ToaContractIncontract toaContractIncontract) throws CustomException;
	public Integer saveResource(ToaContractIncontract toaContractIncontract)throws CustomException ;
	public Integer updateResource(ToaContractIncontract toaContractIncontract)throws CustomException;
}
