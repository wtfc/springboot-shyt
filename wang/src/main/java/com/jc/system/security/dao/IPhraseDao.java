package com.jc.system.security.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.foundation.domain.PageManager;
import com.jc.system.security.domain.Phrase;


/**
 * @title GOA2.0
 * @description  dao接口类
 * @author 
 * @version  2014-04-28
 */
 
public interface IPhraseDao extends IBaseDao<Phrase>{

	/**
	 * 查询所有常用词
	 * @param Phrase phrase
	 * @return PageManager
	 */
	public PageManager queryPhraseForUser(Phrase phrase,PageManager page);
	
}
