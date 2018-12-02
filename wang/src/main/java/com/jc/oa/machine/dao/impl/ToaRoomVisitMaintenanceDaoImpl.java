package com.jc.oa.machine.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.machine.domain.ToaRoomVisitMaintenance;
import com.jc.oa.machine.dao.IToaRoomVisitMaintenanceDao;
/**
 * @author mrb
 * @version 机房参观和入室维护表
 */
@Repository
public class ToaRoomVisitMaintenanceDaoImpl extends BaseDaoImpl<ToaRoomVisitMaintenance> implements IToaRoomVisitMaintenanceDao{
	
	public ToaRoomVisitMaintenanceDaoImpl(){};
}
