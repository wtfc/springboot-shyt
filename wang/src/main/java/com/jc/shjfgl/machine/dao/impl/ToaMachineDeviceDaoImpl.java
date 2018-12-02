package com.jc.shjfgl.machine.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.shjfgl.machine.domain.ToaMachineDevice;
import com.jc.shjfgl.machine.dao.IToaMachineDeviceDao;
/**
 * @author mrb
 * @version 工单设备关联表
 */
@Repository
public class ToaMachineDeviceDaoImpl extends BaseDaoImpl<ToaMachineDevice> implements IToaMachineDeviceDao{
	
	public ToaMachineDeviceDaoImpl(){};
}
