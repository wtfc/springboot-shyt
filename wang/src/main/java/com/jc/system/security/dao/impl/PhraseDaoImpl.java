package com.jc.system.security.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.foundation.domain.PageManager;
import com.jc.system.security.dao.IPhraseDao;
import com.jc.system.security.domain.Phrase;

/**
 * @title GOA2.0
 * @description  dao实现类
 * @author 
 * @version  2014-04-28
 */
@Repository
public class PhraseDaoImpl extends BaseDaoImpl<Phrase> implements IPhraseDao{

	public PhraseDaoImpl(){}

	public PageManager queryPhraseForUser(Phrase phrase,PageManager page) {
		return queryByPage(phrase,page,"queryCountForUser","queryForUser");
	}

}