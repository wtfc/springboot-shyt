package com.jc.oa.network.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.network.domain.ToaNetworkIpstop;
import com.jc.oa.network.dao.IToaNetworkIpstopDao;
/**
 * @author mrb
 * @version IP封停记录表
 */
@Repository
public class ToaNetworkIpstopDaoImpl extends BaseDaoImpl<ToaNetworkIpstop> implements IToaNetworkIpstopDao{
	
	public ToaNetworkIpstopDaoImpl(){};
}
