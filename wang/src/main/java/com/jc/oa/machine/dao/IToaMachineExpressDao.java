package com.jc.oa.machine.dao;


import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.oa.machine.domain.ToaMachineExpress;
/**
 * @author mrb
 * @version 代收发快递
*/
public interface IToaMachineExpressDao extends IBaseDao<ToaMachineExpress> {
	//查询列表
	public List<ToaMachineExpress> queryExpress(ToaMachineExpress toaMachineExpress);
}
