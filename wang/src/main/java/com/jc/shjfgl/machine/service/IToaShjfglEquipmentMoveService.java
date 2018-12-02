package com.jc.shjfgl.machine.service;

import java.util.List;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentMove;
/**
 * @author mrb
 * @version 设备搬迁表
*/
public interface IToaShjfglEquipmentMoveService extends IBaseService<ToaShjfglEquipmentMove>{

	public Integer deleteByIds(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException;
	
	//手机接口保存方法
	public Integer saveEquipmentMove(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException;
	//查询列表
	public List<ToaShjfglEquipmentMove> quertMove(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException;
	//接单(0-1)
	public Integer updateMove(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException;
	//到达设备现场(1-2)
	public Integer updateMoveScan(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException;
	//开始操作(2-3)
	public Integer updateMoveHandle(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException; 
	//操作完成(3-4)
	public Integer updateMoveShow(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException;
	//到达设备上架现场(4-5)
	public Integer headblockMove(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException;
	//开始操作(5-6)
	public Integer StoreOperatingMove(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException;
	//搬迁完成(6-7)
	public Integer  completeMove(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException;
}
