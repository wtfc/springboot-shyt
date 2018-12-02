package com.jc.shjfgl.machine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.shjfgl.machine.dao.IExchangeDao;
import com.jc.shjfgl.machine.domain.Exchange;
import com.jc.shjfgl.machine.service.IExchangeService;
import com.jc.system.CustomException;

@Service
public class ExchangeServiceImpl extends BaseServiceImpl<Exchange> implements IExchangeService{
	
	@Autowired
	public ExchangeServiceImpl(IExchangeDao exchangeDao){
		super(exchangeDao);
		this.exchangeDao = exchangeDao;
	}
	public ExchangeServiceImpl(){};
	
	private IExchangeDao exchangeDao;
	
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(Exchange exchange) throws CustomException {
		Integer result = -1;
		try{
			propertyService.fillProperties(exchange,true);
			result = exchangeDao.delete(exchange);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(exchange);
			throw ce;
		}
		return result;
	}
}
