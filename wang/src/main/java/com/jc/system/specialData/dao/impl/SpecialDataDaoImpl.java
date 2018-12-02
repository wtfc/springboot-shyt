package com.jc.system.specialData.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.specialData.dao.ISpecialDataDao;
import com.jc.system.specialData.domain.SpecialData;


/**
 * @title 172.16.3.68
 * @description  dao实现类
 * @author 
 * @version  2014-12-02
 */
@Repository
public class SpecialDataDaoImpl extends BaseDaoImpl<SpecialData> implements ISpecialDataDao{

	public SpecialDataDaoImpl(){}

	public Integer updateSendStatus(SpecialData specialData) {
		return template.update(getNameSpace(specialData)+".updateSendStatus",specialData);
	}

}