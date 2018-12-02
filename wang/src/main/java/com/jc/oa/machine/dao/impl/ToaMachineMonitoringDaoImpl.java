package com.jc.oa.machine.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.machine.domain.ToaMachineMonitoring;
import com.jc.oa.machine.dao.IToaMachineMonitoringDao;
/**
 * @author mrb
 * @version 监控报告通告
 */
@Repository
public class ToaMachineMonitoringDaoImpl extends BaseDaoImpl<ToaMachineMonitoring> implements IToaMachineMonitoringDao{
	
	public ToaMachineMonitoringDaoImpl(){};
}
