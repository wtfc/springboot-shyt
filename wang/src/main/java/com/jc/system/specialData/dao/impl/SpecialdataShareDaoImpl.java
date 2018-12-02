package com.jc.system.specialData.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.specialData.dao.ISpecialdataShareDao;
import com.jc.system.specialData.domain.SpecialdataShare;


/**
 * @title 172.16.3.68
 * @description  dao实现类
 * @author 
 * @version  2014-12-02
 */
@Repository
public class SpecialdataShareDaoImpl extends BaseDaoImpl<SpecialdataShare> implements ISpecialdataShareDao{

	public SpecialdataShareDaoImpl(){}

	public Integer deleteForSpecialId(SpecialdataShare specialdataShare)
			throws Exception {
		return template.delete(getNameSpace(specialdataShare) +".deleteforspecialId", specialdataShare);
	}

}