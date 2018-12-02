package com.jc.oa.archive.dao;

import com.jc.oa.archive.domain.Relate;

import com.jc.foundation.dao.IBaseDao;


/**
 * @title  GOA2.0源代码
 * @description  dao接口类
 * @author 
 * @version  2014-06-05
 */
 
public interface IRelateDao extends IBaseDao<Relate>{
	void deleteRelateDM(Relate relate);
	
}
