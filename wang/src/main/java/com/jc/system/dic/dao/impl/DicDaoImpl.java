package com.jc.system.dic.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.system.dic.dao.IDicDao;
import com.jc.system.dic.domain.Dic;

@Repository
public class DicDaoImpl extends BaseDaoImpl<Dic> implements IDicDao {

	public List<Dic> getDicByDuty(Dic dic) {
		return template.selectList(getNameSpace(dic)+".queryDuty", dic);
	}

	public Integer delForDicList(Dic dic) {
		return template.delete(getNameSpace(dic)+".deleteLogic", dic);
	}

	
}