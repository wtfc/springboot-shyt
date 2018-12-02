package com.jc.shjfgl.machine.service;

import com.jc.foundation.service.IBaseService;
import com.jc.shjfgl.machine.domain.Equipment;
import com.jc.system.CustomException;

public interface IEquipmentService extends IBaseService<Equipment>{

	public Integer deleteByIds(Equipment equipment)throws CustomException;
	public Integer operate(Equipment equipment,String operate)throws CustomException;
	public void updateEquipmentNumber() throws CustomException;
}
