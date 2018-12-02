package com.jc.oa.machine.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.machine.domain.ToaMachineAttach;
import com.jc.oa.machine.dao.IToaMachineAttachDao;
/**
 * @author mrb
 * @version 工单附件表
 */
@Repository
public class ToaMachineAttachDaoImpl extends BaseDaoImpl<ToaMachineAttach> implements IToaMachineAttachDao{
	
	public ToaMachineAttachDaoImpl(){};
}
