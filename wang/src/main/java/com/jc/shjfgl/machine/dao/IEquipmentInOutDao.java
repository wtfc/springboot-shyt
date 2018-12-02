package com.jc.shjfgl.machine.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.shjfgl.machine.domain.EquipmentInOut;

public interface IEquipmentInOutDao extends IBaseDao<EquipmentInOut>{
	
	public Integer updateStatus(EquipmentInOut equipmentInOut);
	
	public Integer operate(EquipmentInOut equipmentInOut);

}
