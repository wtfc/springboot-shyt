package com.jc.oa.shyt.service;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.shyt.domain.Visit;
import com.jc.system.CustomException;

public interface IVisitService extends IBaseService<Visit>{

	public Integer deleteByIds(Visit visit) throws CustomException;
	public Integer saveResource(Visit visit)throws CustomException ;
	public Integer updateResource(Visit visit)throws CustomException;
	
	public Integer updateCustomer(Visit visit)throws CustomException;
}
