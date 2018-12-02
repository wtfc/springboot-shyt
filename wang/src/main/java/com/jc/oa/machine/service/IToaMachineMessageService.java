package com.jc.oa.machine.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.machine.domain.ToaMachineMessage;
/**
 * @author mrb
 * @version 工单消息表
*/
public interface IToaMachineMessageService extends IBaseService<ToaMachineMessage>{

	public Integer deleteByIds(ToaMachineMessage toaMachineMessage) throws CustomException;
}
