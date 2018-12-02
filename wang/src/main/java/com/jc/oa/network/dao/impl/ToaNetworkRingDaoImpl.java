package com.jc.oa.network.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.network.domain.ToaNetworkRing;
import com.jc.oa.network.dao.IToaNetworkRingDao;
/**
 * @author mrb
 * @version 骨干网链路带宽统计表
 */
@Repository
public class ToaNetworkRingDaoImpl extends BaseDaoImpl<ToaNetworkRing> implements IToaNetworkRingDao{
	
	public ToaNetworkRingDaoImpl(){};
}
