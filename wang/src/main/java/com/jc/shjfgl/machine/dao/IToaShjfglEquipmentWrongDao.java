package com.jc.shjfgl.machine.dao;


import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentWrong;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 故障处理表
*/
public interface IToaShjfglEquipmentWrongDao extends IBaseDao<ToaShjfglEquipmentWrong> {
	
	public List<ToaShjfglEquipmentWrong> queryApp(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong) throws CustomException;
}
