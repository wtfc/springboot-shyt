package com.jc.shjfgl.machine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.shjfgl.machine.domain.ToaMachineDevice;
import com.jc.shjfgl.machine.dao.IToaMachineDeviceDao;
import com.jc.shjfgl.machine.service.IToaMachineDeviceService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 工单设备关联表
*/
@Service
public class  ToaMachineDeviceServiceImpl  extends BaseServiceImpl<ToaMachineDevice> implements IToaMachineDeviceService {

	public ToaMachineDeviceServiceImpl(){}	

    private IToaMachineDeviceDao toaMachineDeviceDao;

	@Autowired
	public ToaMachineDeviceServiceImpl(IToaMachineDeviceDao toaMachineDeviceDao){
		super(toaMachineDeviceDao);
		this.toaMachineDeviceDao = toaMachineDeviceDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaMachineDevice toaMachineDevice) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaMachineDevice,true);
			result = toaMachineDeviceDao.delete(toaMachineDevice);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaMachineDevice);
			throw ce;
		}
		return result;
	}

	

}
