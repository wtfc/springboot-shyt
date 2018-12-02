package com.jc.oa.project.service;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.project.domain.Monitors;
import com.jc.system.CustomException;

public interface IMonitorsService extends IBaseService<Monitors>{
	
	public Integer deleteByIds(Monitors monitor)throws CustomException;
	
	public Integer updateStatus(Monitors monitor)throws CustomException;

}
