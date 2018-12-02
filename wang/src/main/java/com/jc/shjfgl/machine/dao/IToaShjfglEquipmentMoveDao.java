package com.jc.shjfgl.machine.dao;


import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentMove;
/**
 * @author mrb
 * @version 设备搬迁表
*/
public interface IToaShjfglEquipmentMoveDao extends IBaseDao<ToaShjfglEquipmentMove> {
	
	public List<ToaShjfglEquipmentMove> queryMove(ToaShjfglEquipmentMove toaShjfglEquipmentMove);
}
