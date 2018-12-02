package com.jc.oa.contract.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.contract.domain.ToaContractOutcontract;
/**
 * @author mrb
 * @version 付款方合同
*/
public interface IToaContractOutcontractService extends IBaseService<ToaContractOutcontract>{

	public Integer deleteByIds(ToaContractOutcontract toaContractOutcontract) throws CustomException;
}
