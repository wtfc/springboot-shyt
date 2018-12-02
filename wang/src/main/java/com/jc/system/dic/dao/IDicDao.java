package com.jc.system.dic.dao;

import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.system.dic.domain.Dic;

public interface IDicDao extends IBaseDao<Dic> {

	public List<Dic> getDicByDuty(Dic dic);
	
	public Integer delForDicList(Dic dic);
}
