package com.jc.system.common.dao;

import com.jc.foundation.dao.IBaseDao;
import com.jc.foundation.domain.BaseBean;
import com.jc.system.CustomException;

public interface ICommonDao extends IBaseDao<BaseBean> {

	public String getDBSysdate() throws CustomException;
}
