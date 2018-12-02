package com.jc.oa.machine.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.machine.domain.ToaMachineMessage;
import com.jc.oa.machine.dao.IToaMachineMessageDao;
/**
 * @author mrb
 * @version 工单消息表
 */
@Repository
public class ToaMachineMessageDaoImpl extends BaseDaoImpl<ToaMachineMessage> implements IToaMachineMessageDao{
	
	public ToaMachineMessageDaoImpl(){};
}
