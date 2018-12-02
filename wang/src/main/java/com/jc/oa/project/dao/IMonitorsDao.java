package com.jc.oa.project.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.oa.project.domain.Monitors;

public interface IMonitorsDao extends IBaseDao<Monitors>{

	public Integer updateStatus(Monitors monitor);
}
