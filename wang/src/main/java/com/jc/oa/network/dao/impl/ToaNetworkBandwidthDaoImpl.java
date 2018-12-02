package com.jc.oa.network.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.network.domain.ToaNetworkBandwidth;
import com.jc.oa.network.dao.IToaNetworkBandwidthDao;
/**
 * @author mrb
 * @version 在网客户带宽统计表表
 */
@Repository
public class ToaNetworkBandwidthDaoImpl extends BaseDaoImpl<ToaNetworkBandwidth> implements IToaNetworkBandwidthDao{
	
	public ToaNetworkBandwidthDaoImpl(){};
}
