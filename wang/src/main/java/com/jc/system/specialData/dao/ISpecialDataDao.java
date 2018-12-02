package com.jc.system.specialData.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.system.specialData.domain.SpecialData;


/**
 * @title 172.16.3.68
 * @description  dao接口类
 * @author 
 * @version  2014-12-02
 */
 
public interface ISpecialDataDao extends IBaseDao<SpecialData>{

	public Integer updateSendStatus(SpecialData specialData);
}
