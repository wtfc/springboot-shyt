package com.jc.oa.shyt.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.oa.shyt.domain.CustomerPeople;

public interface ICustomerPeopleDao extends IBaseDao<CustomerPeople>{

	public Integer updatePeople(CustomerPeople customerPeople);
}
