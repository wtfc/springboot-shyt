package com.jc.shjfgl.machine.service;

import com.jc.foundation.service.IBaseService;
import com.jc.shjfgl.machine.domain.EquipmentInOut;
import com.jc.system.CustomException;

public interface IEquipmentInOutService extends IBaseService<EquipmentInOut>{

	public Integer deleteByIds(EquipmentInOut equipmentInOut)throws CustomException;
	public Integer deleteStatus(EquipmentInOut equipmentInOut)throws CustomException;
	public Integer operate(EquipmentInOut equipmentInOut)throws CustomException;
}
