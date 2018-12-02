package com.jc.system.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.foundation.domain.BaseBean;
import com.jc.system.CustomException;
import com.jc.system.common.dao.ICommonDao;

@Repository
public class CommonDaoImpl extends BaseDaoImpl<BaseBean> implements ICommonDao {

	@Override
	public String getDBSysdate() throws CustomException {
		return template.selectOne("com.jc.system.getSysdate");
	}

}
