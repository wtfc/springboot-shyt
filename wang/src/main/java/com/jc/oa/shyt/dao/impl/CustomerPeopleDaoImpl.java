package com.jc.oa.shyt.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.shyt.dao.ICustomerPeopleDao;
import com.jc.oa.shyt.domain.CustomerPeople;

@Repository
public class CustomerPeopleDaoImpl extends BaseDaoImpl<CustomerPeople> implements ICustomerPeopleDao{

	public CustomerPeopleDaoImpl(){}

	@Override
	public Integer updatePeople(CustomerPeople customerPeople) {
		// TODO Auto-generated method stub
		return template.update(getNameSpace(customerPeople)+"."+"updatePeople",customerPeople);
	};
}
