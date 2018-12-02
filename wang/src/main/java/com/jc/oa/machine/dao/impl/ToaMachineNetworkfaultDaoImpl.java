package com.jc.oa.machine.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.machine.domain.ToaMachineNetworkfault;
import com.jc.oa.machine.dao.IToaMachineNetworkfaultDao;
/**
 * @author mrb
 * @version 网络故障
 */
@Repository
public class ToaMachineNetworkfaultDaoImpl extends BaseDaoImpl<ToaMachineNetworkfault> implements IToaMachineNetworkfaultDao{
	
	public ToaMachineNetworkfaultDaoImpl(){};
}
