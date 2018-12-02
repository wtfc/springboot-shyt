package com.jc.oa.machine.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.machine.domain.ToaMachineAttach;
/**
 * @author mrb
 * @version 工单附件表
*/
public interface IToaMachineAttachService extends IBaseService<ToaMachineAttach>{

	public Integer deleteByIds(ToaMachineAttach toaMachineAttach) throws CustomException;
}
