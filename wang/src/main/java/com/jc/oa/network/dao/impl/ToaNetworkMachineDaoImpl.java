package com.jc.oa.network.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.network.domain.ToaNetworkMachine;
import com.jc.oa.network.dao.IToaNetworkMachineDao;
/**
 * @author mrb
 * @version 网络设备统计表
 */
@Repository
public class ToaNetworkMachineDaoImpl extends BaseDaoImpl<ToaNetworkMachine> implements IToaNetworkMachineDao{
	
	public ToaNetworkMachineDaoImpl(){};
}
