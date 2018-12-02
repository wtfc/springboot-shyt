package com.jc.oa.shyt.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.shyt.dao.IVisitDao;
import com.jc.oa.shyt.domain.Visit;

@Repository
public class VisitDaoImpl extends BaseDaoImpl<Visit> implements IVisitDao{
	
	public VisitDaoImpl(){}

	
	@Override
	public Integer updateCustomer(Visit visit) {
		// TODO Auto-generated method stub
		return template.update(getNameSpace(visit)+"."+"updateCustomer",visit);
	};

}
