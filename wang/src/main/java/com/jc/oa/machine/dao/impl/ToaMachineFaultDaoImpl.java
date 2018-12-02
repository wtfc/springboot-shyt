package com.jc.oa.machine.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.machine.domain.ToaMachineFault;
import com.jc.oa.machine.dao.IToaMachineFaultDao;
/**
 * @author mrb
 * @version 机房故障
 */
@Repository
public class ToaMachineFaultDaoImpl extends BaseDaoImpl<ToaMachineFault> implements IToaMachineFaultDao{
	
	public ToaMachineFaultDaoImpl(){};
}
