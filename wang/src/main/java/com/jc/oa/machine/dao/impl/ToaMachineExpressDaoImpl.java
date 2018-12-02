package com.jc.oa.machine.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.machine.domain.ToaMachineExpress;
import com.jc.oa.machine.dao.IToaMachineExpressDao;
/**
 * @author mrb
 * @version 代收发快递
 */
@Repository
public class ToaMachineExpressDaoImpl extends BaseDaoImpl<ToaMachineExpress> implements IToaMachineExpressDao{
	
	public ToaMachineExpressDaoImpl(){}

	@Override
	public List<ToaMachineExpress> queryExpress(ToaMachineExpress toaMachineExpress) {
		// TODO Auto-generated method stub
		return template.selectList(getNameSpace(toaMachineExpress)+"."+"queryExpress", toaMachineExpress);
	};
}
