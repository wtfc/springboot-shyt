package com.jc.oa.contract.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.contract.domain.ToaContractResource;
import com.jc.oa.contract.dao.IToaContractResourceDao;
/**
 * @author mrb
 * @version 合同资源表
 */
@Repository
public class ToaContractResourceDaoImpl extends BaseDaoImpl<ToaContractResource> implements IToaContractResourceDao{
	
	public ToaContractResourceDaoImpl(){};
}
