package com.jc.oa.machine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.machine.domain.ToaMachineMonitoring;
import com.jc.oa.machine.dao.IToaMachineMonitoringDao;
import com.jc.oa.machine.service.IToaMachineMonitoringService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 监控报告通告
*/
@Service
public class  ToaMachineMonitoringServiceImpl  extends BaseServiceImpl<ToaMachineMonitoring> implements IToaMachineMonitoringService {

	public ToaMachineMonitoringServiceImpl(){}	

    private IToaMachineMonitoringDao toaMachineMonitoringDao;

	@Autowired
	public ToaMachineMonitoringServiceImpl(IToaMachineMonitoringDao toaMachineMonitoringDao){
		super(toaMachineMonitoringDao);
		this.toaMachineMonitoringDao = toaMachineMonitoringDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaMachineMonitoring toaMachineMonitoring) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaMachineMonitoring,true);
			result = toaMachineMonitoringDao.delete(toaMachineMonitoring);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaMachineMonitoring);
			throw ce;
		}
		return result;
	}

	

}
