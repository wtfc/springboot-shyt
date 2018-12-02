package com.jc.oa.machine.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.machine.domain.ToaMachineInout;
import com.jc.oa.machine.dao.IToaMachineInoutDao;
/**
 * @author mrb
 * @version 机房进出
 */
@Repository
public class ToaMachineInoutDaoImpl extends BaseDaoImpl<ToaMachineInout> implements IToaMachineInoutDao{
	
	public ToaMachineInoutDaoImpl(){};
}
