package com.jc.shjfgl.machine.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentMove;
import com.jc.shjfgl.machine.dao.IToaShjfglEquipmentMoveDao;
/**
 * @author mrb
 * @version 设备搬迁表
 */
@Repository
public class ToaShjfglEquipmentMoveDaoImpl extends BaseDaoImpl<ToaShjfglEquipmentMove> implements IToaShjfglEquipmentMoveDao{
	
	public ToaShjfglEquipmentMoveDaoImpl(){}

	//查询列表
	@Override
	public List<ToaShjfglEquipmentMove> queryMove(ToaShjfglEquipmentMove toaShjfglEquipmentMove) {
		// TODO Auto-generated method stub
		return template.selectList(getNameSpace(toaShjfglEquipmentMove)+"."+"queryMove", toaShjfglEquipmentMove);
	};
}
