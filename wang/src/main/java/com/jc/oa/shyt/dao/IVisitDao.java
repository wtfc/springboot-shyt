package com.jc.oa.shyt.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.oa.shyt.domain.Visit;

public interface IVisitDao extends IBaseDao<Visit>{
	
	public Integer updateCustomer(Visit visit);

}
