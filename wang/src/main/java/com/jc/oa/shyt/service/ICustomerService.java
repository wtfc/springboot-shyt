package com.jc.oa.shyt.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.shyt.domain.CustomerPeoples;
import com.jc.oa.shyt.domain.Customer;
/**
 * @author mrb
 * @version 合同资源表
*/
public interface ICustomerService extends IBaseService<Customer>{

	public Integer deleteByIds(Customer toaShytCustomer) throws CustomException;
	public Integer savePeople(Customer toaShytCustomer,CustomerPeoples customerPeople)throws CustomException;
}
