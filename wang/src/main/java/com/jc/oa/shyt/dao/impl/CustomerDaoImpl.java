package com.jc.oa.shyt.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.shyt.domain.Customer;
import com.jc.oa.shyt.dao.ICustomerDao;
/**
 * @author mrb
 * @version 合同资源表
 */
@Repository
public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements ICustomerDao{
	
	public CustomerDaoImpl(){};
}
