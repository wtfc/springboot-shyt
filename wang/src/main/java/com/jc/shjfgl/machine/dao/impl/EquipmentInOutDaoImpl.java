package com.jc.shjfgl.machine.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.shjfgl.machine.dao.IEquipmentInOutDao;
import com.jc.shjfgl.machine.domain.EquipmentInOut;

@Repository
public class EquipmentInOutDaoImpl extends BaseDaoImpl<EquipmentInOut> implements IEquipmentInOutDao{

	public EquipmentInOutDaoImpl(){}

	@Override
	public Integer updateStatus(EquipmentInOut equipmentInOut) {
		return template.update(getNameSpace(equipmentInOut)+"."+"deletePid", equipmentInOut);
	};
	@Override
	public Integer operate(EquipmentInOut equipmentInOut) {
		return template.update(getNameSpace(equipmentInOut)+"."+"operate", equipmentInOut);
	};
}
