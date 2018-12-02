package com.jc.shjfgl.machine.service;

import java.util.List;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.shjfgl.machine.domain.Equipment;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentWrong;
/**
 * @author mrb
 * @version 故障处理表
*/
public interface IToaShjfglEquipmentWrongService extends IBaseService<ToaShjfglEquipmentWrong>{

	public Integer deleteByIds(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong) throws CustomException;

	public Integer saveByEquipment(Equipment equipment, ToaShjfglEquipmentWrong toaShjfglEquipmentWrong) throws CustomException;

	public Integer jieDan(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong) throws CustomException;

	public Integer daoChang(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong) throws CustomException;

	public Integer operate(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong) throws CustomException;
	
	public List<ToaShjfglEquipmentWrong> queryApp(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong) throws CustomException;

	public Integer renke(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong) throws CustomException;
}
