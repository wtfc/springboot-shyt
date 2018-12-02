package com.jc.oa.shyt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.shyt.dao.ICustomerPeopleDao;
import com.jc.oa.shyt.domain.CustomerPeople;
import com.jc.oa.shyt.service.ICustomerPeopleService;
import com.jc.system.CustomException;

@Service
public class CustomerPeopleServiceImpl extends BaseServiceImpl<CustomerPeople> implements ICustomerPeopleService{

	private ICustomerPeopleDao customerPeopleDao;
	
	public CustomerPeopleServiceImpl(){}
	
	@Autowired
	public CustomerPeopleServiceImpl(ICustomerPeopleDao customerPeopleDao){
		super(customerPeopleDao);
		this.customerPeopleDao = customerPeopleDao;
	}
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(CustomerPeople customerPeople) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(customerPeople,true);
			result = customerPeopleDao.delete(customerPeople);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(customerPeople);
			throw ce;
		}
		return result;
	}

	@Override
	public Integer updatePeople(CustomerPeople customerPeople)throws CustomException{
		Integer result = -1;
		try{
			result = customerPeopleDao.updatePeople(customerPeople);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(customerPeople);
			throw ce;
		}
		return result;
	}
}
