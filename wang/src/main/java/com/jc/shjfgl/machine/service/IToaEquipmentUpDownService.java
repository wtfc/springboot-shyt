package com.jc.shjfgl.machine.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.shjfgl.machine.domain.ToaEquipmentUpDown;
/**
 * @author mrb
 * @version 设备上下架和迁移
*/
public interface IToaEquipmentUpDownService extends IBaseService<ToaEquipmentUpDown>{

	public Integer deleteByIds(ToaEquipmentUpDown toaEquipmentUpDown) throws CustomException;
}
