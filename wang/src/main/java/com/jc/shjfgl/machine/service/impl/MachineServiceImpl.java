package com.jc.shjfgl.machine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.shjfgl.machine.dao.IMachineDao;
import com.jc.shjfgl.machine.domain.Machine;
import com.jc.shjfgl.machine.service.IMachineService;
import com.jc.system.CustomException;

@Service
public class MachineServiceImpl extends BaseServiceImpl<Machine> implements IMachineService{

	@Autowired
	public MachineServiceImpl(IMachineDao machineDao){
		super(machineDao);
		this.machineDao = machineDao;
	}
	
	private IMachineDao machineDao;
	
	public MachineServiceImpl(){};
	
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(Machine machine) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(machine,true);
			result = machineDao.delete(machine);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(machine);
			throw ce;
		}
		return result;
	}
	
	
	
	
}
