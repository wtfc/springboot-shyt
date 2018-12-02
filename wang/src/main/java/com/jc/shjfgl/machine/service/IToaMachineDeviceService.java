package com.jc.shjfgl.machine.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.shjfgl.machine.domain.ToaMachineDevice;
/**
 * @author mrb
 * @version 工单设备关联表
*/
public interface IToaMachineDeviceService extends IBaseService<ToaMachineDevice>{

	public Integer deleteByIds(ToaMachineDevice toaMachineDevice) throws CustomException;
}
