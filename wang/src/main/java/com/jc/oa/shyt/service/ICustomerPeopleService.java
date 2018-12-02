package com.jc.oa.shyt.service;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.shyt.domain.CustomerPeople;
import com.jc.system.CustomException;

public interface ICustomerPeopleService extends IBaseService<CustomerPeople>{
	
	public Integer deleteByIds(CustomerPeople customerPeople) throws CustomException;
	
	public Integer updatePeople(CustomerPeople customerPeople)throws CustomException;

}
