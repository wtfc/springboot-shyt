package com.jc.oa.network.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.network.domain.ToaNetworkMachine;
import com.jc.oa.network.dao.IToaNetworkMachineDao;
import com.jc.oa.network.service.IToaNetworkMachineService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 网络设备统计表
*/
@Service
public class  ToaNetworkMachineServiceImpl  extends BaseServiceImpl<ToaNetworkMachine> implements IToaNetworkMachineService {

	public ToaNetworkMachineServiceImpl(){}	

    private IToaNetworkMachineDao toaNetworkMachineDao;

	@Autowired
	public ToaNetworkMachineServiceImpl(IToaNetworkMachineDao toaNetworkMachineDao){
		super(toaNetworkMachineDao);
		this.toaNetworkMachineDao = toaNetworkMachineDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaNetworkMachine toaNetworkMachine) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaNetworkMachine,true);
			result = toaNetworkMachineDao.delete(toaNetworkMachine);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkMachine);
			throw ce;
		}
		return result;
	}

	

}
