package com.jc.oa.network.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.network.domain.ToaNetworkPort;
import com.jc.oa.network.dao.IToaNetworkPortDao;
/**
 * @author mrb
 * @version 端口使用情况统计表
 */
@Repository
public class ToaNetworkPortDaoImpl extends BaseDaoImpl<ToaNetworkPort> implements IToaNetworkPortDao{
	
	public ToaNetworkPortDaoImpl(){};
}
