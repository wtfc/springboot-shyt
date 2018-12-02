package com.jc.shjfgl.machine.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.shjfgl.machine.domain.ToaEquipmentUpDown;
import com.jc.shjfgl.machine.dao.IToaEquipmentUpDownDao;
/**
 * @author mrb
 * @version 设备上下架和迁移
 */
@Repository
public class ToaEquipmentUpDownDaoImpl extends BaseDaoImpl<ToaEquipmentUpDown> implements IToaEquipmentUpDownDao{
	
	public ToaEquipmentUpDownDaoImpl(){};
}
