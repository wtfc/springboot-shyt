package com.jc.oa.machine.dao;


import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.foundation.domain.PageManager;
import com.jc.oa.machine.domain.ToaMachineRestart;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 重启操作
*/
public interface IToaMachineRestartDao extends IBaseDao<ToaMachineRestart> {
	
	public PageManager queryAll(ToaMachineRestart toaMachineRestart,
			PageManager page) throws CustomException;
	
	public  List<ToaMachineRestart> queryApp(ToaMachineRestart toaMachineRestart) throws CustomException;
}
