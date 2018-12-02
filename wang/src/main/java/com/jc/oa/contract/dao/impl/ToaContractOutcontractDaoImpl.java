package com.jc.oa.contract.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.contract.domain.ToaContractOutcontract;
import com.jc.oa.contract.dao.IToaContractOutcontractDao;
/**
 * @author mrb
 * @version 付款方合同
 */
@Repository
public class ToaContractOutcontractDaoImpl extends BaseDaoImpl<ToaContractOutcontract> implements IToaContractOutcontractDao{
	
	public ToaContractOutcontractDaoImpl(){};
}
