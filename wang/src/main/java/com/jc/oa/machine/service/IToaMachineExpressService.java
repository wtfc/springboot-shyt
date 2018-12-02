package com.jc.oa.machine.service;

import java.util.List;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.machine.domain.ToaMachineExpress;
/**
 * @author mrb
 * @version 代收发快递
*/
public interface IToaMachineExpressService extends IBaseService<ToaMachineExpress>{

	public Integer deleteByIds(ToaMachineExpress toaMachineExpress) throws CustomException;
	
	//保存(状态为0)
	public Integer saveExpress(ToaMachineExpress toaMachineExpress) throws CustomException;
	//接单(状态0-1)
	public Integer updateExpress(ToaMachineExpress toaMachineExpress) throws CustomException;
	//完成(状态1-2)
	public Integer updateComplete(ToaMachineExpress toaMachineExpress)throws CustomException;
	//拒收快递(状态1-3)
	public Integer updateRejection(ToaMachineExpress toaMachineExpress) throws CustomException;
	//查询
	public List<ToaMachineExpress> quertExpress(ToaMachineExpress toaMachineExpress)throws CustomException;

}
