package com.jc.oa.machine.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.machine.domain.ToaMachineOperator;
import com.jc.oa.machine.dao.IToaMachineOperatorDao;
/**
 * @author mrb
 * @version 机房操作
 */
@Repository
public class ToaMachineOperatorDaoImpl extends BaseDaoImpl<ToaMachineOperator> implements IToaMachineOperatorDao{
	
	public ToaMachineOperatorDaoImpl(){};
}
