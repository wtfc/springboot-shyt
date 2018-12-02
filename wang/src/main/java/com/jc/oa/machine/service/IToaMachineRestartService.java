package com.jc.oa.machine.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.machine.domain.ToaMachineRestart;
/**
 * @author mrb
 * @version 重启操作
*/
public interface IToaMachineRestartService extends IBaseService<ToaMachineRestart>{

	public Integer deleteByIds(ToaMachineRestart toaMachineRestart) throws CustomException;

	//保存方法
	public Integer saveRestart(ToaMachineRestart toaMachineRestart) throws CustomException; 
	
	//修改方法
	//接单
	public Integer updateRoom(ToaMachineRestart toaMachineRestart) throws CustomException;
	//扫描
	public Integer updateRoomScan(ToaMachineRestart toaMachineRestart) throws CustomException;
	//2-3
	public Integer updateRoomHandle(ToaMachineRestart toaMachineRestart) throws CustomException;
	//2-4
	public Integer updateRoomShow(ToaMachineRestart toaMachineRestart) throws CustomException;
	//3-7
	public Integer updateRoomDirect(ToaMachineRestart toaMachineRestart) throws CustomException;
	//4-7
	public Integer updateRoomReveal(ToaMachineRestart toaMachineRestart) throws CustomException;
	//4-5
	public Integer updateRoomWrong(ToaMachineRestart toaMachineRestart) throws CustomException;
	//5-6
	public Integer updateRoomSeed(ToaMachineRestart toaMachineRestart) throws CustomException;
	//6-7
	public Integer updateRoomAssist(ToaMachineRestart toaMachineRestart) throws CustomException;
	
	//主管评分
	public Integer updateEngine(ToaMachineRestart toaMachineRestart) throws CustomException;
	
	//主管评分
	public PageManager queryAll(ToaMachineRestart toaMachineRestart, PageManager page) throws CustomException;
	
	//客户模块重启工单
	public Integer operate(ToaMachineRestart toaMachineRestart,HttpServletRequest request)throws CustomException;
	
	//app重启工单
	public List<ToaMachineRestart> queryApp(ToaMachineRestart toaMachineRestart) throws CustomException;
	
	//app保存方法
	public Integer saveMachine(ToaMachineRestart toaMachineRestart) throws CustomException;
}
