package com.jc.oa.machine.service;

import java.util.Date;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.oa.machine.domain.ToaRoomVisitMaintenance;
/**
 * @author mrb
 * @version 机房参观和入室维护表
*/
public interface IToaRoomVisitMaintenanceService extends IBaseService<ToaRoomVisitMaintenance>{

	public Integer saveByOperateType(ToaRoomVisitMaintenance toaRoomVisitMaintenance) throws CustomException;
	
	public Integer deleteByIds(ToaRoomVisitMaintenance toaRoomVisitMaintenance) throws CustomException;
	
	/** 接单  status(0-1)  申请状态 =》已接单状态
	 * 	@param Long id 工单ID
	 * 
	 * */
	public Integer jieDan(ToaRoomVisitMaintenance toaRoomVisitMaintenance)
			throws CustomException;
	
	/** 身份审核 status(1-2) 已接单状态=》审核通过状态
	 * 	@param Long id 工单ID
	 * 	@param String intPeopleCard 入室人员身份证号
	 * 	
	 * */
	public Integer shenFenShenHe(ToaRoomVisitMaintenance toaRoomVisitMaintenance)
			throws CustomException;
	
	/** 进行操作(机房参观、入室维护) status(2-3) 审核通过状态=》操作完成状态
	 * 	@param Long id 工单ID
	 * 	@param String realityType 实际操作内容
	 * 
	 * */
	public Integer operate(ToaRoomVisitMaintenance toaRoomVisitMaintenance)
			throws CustomException;
}
