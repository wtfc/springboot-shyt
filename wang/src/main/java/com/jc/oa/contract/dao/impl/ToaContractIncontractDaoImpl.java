package com.jc.oa.contract.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.contract.domain.ToaContractIncontract;
import com.jc.oa.contract.dao.IToaContractIncontractDao;
/**
 * @author mrb
 * @version 收款方合同
 */
@Repository
public class ToaContractIncontractDaoImpl extends BaseDaoImpl<ToaContractIncontract> implements IToaContractIncontractDao{
	
	public ToaContractIncontractDaoImpl(){};
}
