package com.jc.oa.network.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.network.domain.ToaNetworkProcure;
import com.jc.oa.network.dao.IToaNetworkProcureDao;
/**
 * @author mrb
 * @version 网络设备采购表
 */
@Repository
public class ToaNetworkProcureDaoImpl extends BaseDaoImpl<ToaNetworkProcure> implements IToaNetworkProcureDao{
	
	public ToaNetworkProcureDaoImpl(){};
}
