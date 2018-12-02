package com.jc.shjfgl.machine.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.shjfgl.machine.dao.IEquipmentDao;
import com.jc.shjfgl.machine.domain.Equipment;

@Repository
public class EquipmentDaoImpl extends BaseDaoImpl<Equipment> implements IEquipmentDao{

	public EquipmentDaoImpl(){};
}
