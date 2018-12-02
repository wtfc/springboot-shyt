package com.jc.oa.shyt.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.shyt.dao.IComplainDao;
import com.jc.oa.shyt.domain.Complain;

@Repository
public class ComplainDaoImpl extends BaseDaoImpl<Complain> implements IComplainDao{

	public ComplainDaoImpl(){};
}
