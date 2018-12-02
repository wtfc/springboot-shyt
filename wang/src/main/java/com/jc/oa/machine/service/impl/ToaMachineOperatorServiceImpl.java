package com.jc.oa.machine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.machine.domain.ToaMachineOperator;
import com.jc.oa.machine.dao.IToaMachineOperatorDao;
import com.jc.oa.machine.service.IToaMachineOperatorService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 机房操作
*/
@Service
public class  ToaMachineOperatorServiceImpl  extends BaseServiceImpl<ToaMachineOperator> implements IToaMachineOperatorService {

	public ToaMachineOperatorServiceImpl(){}	

    private IToaMachineOperatorDao toaMachineOperatorDao;

	@Autowired
	public ToaMachineOperatorServiceImpl(IToaMachineOperatorDao toaMachineOperatorDao){
		super(toaMachineOperatorDao);
		this.toaMachineOperatorDao = toaMachineOperatorDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaMachineOperator toaMachineOperator) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaMachineOperator,true);
			result = toaMachineOperatorDao.delete(toaMachineOperator);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaMachineOperator);
			throw ce;
		}
		return result;
	}

	

}
