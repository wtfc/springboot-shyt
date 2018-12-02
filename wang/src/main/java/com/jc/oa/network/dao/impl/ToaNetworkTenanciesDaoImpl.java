package com.jc.oa.network.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.network.domain.ToaNetworkTenancies;
import com.jc.oa.network.dao.IToaNetworkTenanciesDao;
/**
 * @author mrb
 * @version 退租客户记录表
 */
@Repository
public class ToaNetworkTenanciesDaoImpl extends BaseDaoImpl<ToaNetworkTenancies> implements IToaNetworkTenanciesDao{
	
	public ToaNetworkTenanciesDaoImpl(){};
}
