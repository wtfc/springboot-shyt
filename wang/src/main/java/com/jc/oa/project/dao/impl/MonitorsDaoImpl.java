package com.jc.oa.project.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.project.dao.IMonitorsDao;
import com.jc.oa.project.domain.Monitors;

@Repository
public class MonitorsDaoImpl extends BaseDaoImpl<Monitors> implements IMonitorsDao{

	public MonitorsDaoImpl(){};
	
	@Override
	public Integer updateStatus(Monitors monitor) {
		return template.update(getNameSpace(monitor)+"."+"statusUpdate", monitor);
	};
}
