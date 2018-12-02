package com.jc.system.specialData.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.system.specialData.domain.SpecialdataShare;


/**
 * @title 172.16.3.68
 * @description  dao接口类
 * @author 
 * @version  2014-12-02
 */
 
public interface ISpecialdataShareDao extends IBaseDao<SpecialdataShare>{

	public Integer deleteForSpecialId(SpecialdataShare specialdataShare)  throws Exception; 
}
