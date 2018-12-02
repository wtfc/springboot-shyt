package com.jc.oa.machine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.machine.domain.ToaMachineExchange;
import com.jc.oa.machine.dao.IToaMachineExchangeDao;
import com.jc.oa.machine.service.IToaMachineExchangeService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 工单附言表
*/
@Service
public class  ToaMachineExchangeServiceImpl  extends BaseServiceImpl<ToaMachineExchange> implements IToaMachineExchangeService {

	public ToaMachineExchangeServiceImpl(){}	

    private IToaMachineExchangeDao toaMachineExchangeDao;

	@Autowired
	public ToaMachineExchangeServiceImpl(IToaMachineExchangeDao toaMachineExchangeDao){
		super(toaMachineExchangeDao);
		this.toaMachineExchangeDao = toaMachineExchangeDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaMachineExchange toaMachineExchange) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaMachineExchange,true);
			result = toaMachineExchangeDao.delete(toaMachineExchange);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaMachineExchange);
			throw ce;
		}
		return result;
	}

	

}
