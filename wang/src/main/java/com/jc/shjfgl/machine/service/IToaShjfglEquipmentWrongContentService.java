package com.jc.shjfgl.machine.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentWrongContent;
/**
 * @author mrb
 * @version 故障处理过程表
*/
public interface IToaShjfglEquipmentWrongContentService extends IBaseService<ToaShjfglEquipmentWrongContent>{

	public Integer deleteByIds(ToaShjfglEquipmentWrongContent toaShjfglEquipmentWrongContent) throws CustomException;
}
