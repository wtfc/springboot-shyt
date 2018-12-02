package com.jc.shjfgl.machine.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.machine.domain.ToaMachineRestart;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentWrong;
import com.jc.shjfgl.machine.dao.IToaShjfglEquipmentWrongDao;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 故障处理表
 */
@Repository
public class ToaShjfglEquipmentWrongDaoImpl extends BaseDaoImpl<ToaShjfglEquipmentWrong> implements IToaShjfglEquipmentWrongDao{
	
	public ToaShjfglEquipmentWrongDaoImpl(){};
	
	@Override
	public List<ToaShjfglEquipmentWrong> queryApp(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong) throws CustomException {
		return template.selectList(getNameSpace(toaShjfglEquipmentWrong)+"."+"queryApp", toaShjfglEquipmentWrong);
	}
}
