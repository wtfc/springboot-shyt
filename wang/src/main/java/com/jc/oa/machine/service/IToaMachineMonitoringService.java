package com.jc.oa.machine.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.machine.domain.ToaMachineMonitoring;
/**
 * @author mrb
 * @version 监控报告通告
*/
public interface IToaMachineMonitoringService extends IBaseService<ToaMachineMonitoring>{

	public Integer deleteByIds(ToaMachineMonitoring toaMachineMonitoring) throws CustomException;
}
