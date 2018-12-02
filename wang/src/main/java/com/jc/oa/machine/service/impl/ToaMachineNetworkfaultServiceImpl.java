package com.jc.oa.machine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.machine.domain.ToaMachineNetworkfault;
import com.jc.oa.machine.dao.IToaMachineNetworkfaultDao;
import com.jc.oa.machine.service.IToaMachineNetworkfaultService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 网络故障
*/
@Service
public class  ToaMachineNetworkfaultServiceImpl  extends BaseServiceImpl<ToaMachineNetworkfault> implements IToaMachineNetworkfaultService {

	public ToaMachineNetworkfaultServiceImpl(){}	

    private IToaMachineNetworkfaultDao toaMachineNetworkfaultDao;

	@Autowired
	public ToaMachineNetworkfaultServiceImpl(IToaMachineNetworkfaultDao toaMachineNetworkfaultDao){
		super(toaMachineNetworkfaultDao);
		this.toaMachineNetworkfaultDao = toaMachineNetworkfaultDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaMachineNetworkfault toaMachineNetworkfault) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaMachineNetworkfault,true);
			result = toaMachineNetworkfaultDao.delete(toaMachineNetworkfault);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaMachineNetworkfault);
			throw ce;
		}
		return result;
	}

	

}
