package com.jc.android.oa.shyt.allBill.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.web.BaseController;
import com.jc.oa.machine.domain.ToaMachineExpress;
import com.jc.oa.machine.domain.ToaMachineRestart;
import com.jc.oa.machine.domain.ToaRoomVisitMaintenance;
import com.jc.oa.machine.service.IToaMachineExpressService;
import com.jc.oa.machine.service.IToaMachineRestartService;
import com.jc.oa.machine.service.IToaRoomVisitMaintenanceService;
import com.jc.oa.network.domain.ToaNetworkWrong;
import com.jc.oa.network.service.IToaNetworkWrongService;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentMove;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentWrong;
import com.jc.shjfgl.machine.service.IToaShjfglEquipmentMoveService;
import com.jc.shjfgl.machine.service.IToaShjfglEquipmentWrongService;
import com.jc.system.CustomException;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;


@Controller
@RequestMapping(value="/android/allBill/billCount")
public class BillCount4MController extends BaseController{

	@Autowired 
	private IToaNetworkWrongService toaNetworkWrongService;
	
	@Autowired 
	private IToaShjfglEquipmentWrongService toaShjfglEquipmentWrongService; 
	
	@Autowired 
	private IToaMachineRestartService toaMachineRestartService;
	
	@Autowired 
	private IToaRoomVisitMaintenanceService toaRoomVisitMaintenanceService; 
	
	@Autowired 
	private  IToaShjfglEquipmentMoveService toaShjfglEquipmentMoveService;
	
	@Autowired 
	private  IToaMachineExpressService toaMachineExpressService;
    
	public BillCount4MController(){};

	/**
	 * 根据登录用户ID对设备信息分页查询方法
	 * @return  Map<String, Object> resultMap 单条设备信息查询结果（是否成功，提示信息）
	 * @throws 	CustomException
	 * @author  wtf
	 * @version 2017-04-12
	 */
	@RequestMapping(value="manage4MList.action")
	@ResponseBody
	@ActionLog(operateModelNm="设备信息表",operateFuncNm="manage4MList",operateDescribe="根据登录用户ID对设备信息分页查询方法")
	public Map<String, Object> manage4MList(HttpServletRequest request) throws CustomException{
		Map<String, Object> resultMap=new HashMap<String, Object>();
		User user=SystemSecurityUtils.getUser();
		long deptId=user.getDeptId();
		int status=0;
		//网络故障
		ToaNetworkWrong toaNetworkWrong = new ToaNetworkWrong();
		//toaNetworkWrong.setCreateUserDept(deptId);
		toaNetworkWrong.setStatus(status);
		List<ToaNetworkWrong> toaNetworkWrongList = toaNetworkWrongService.queryAll(toaNetworkWrong);
		//设备故障
		ToaShjfglEquipmentWrong toaShjfglEquipmentWrong = new ToaShjfglEquipmentWrong();
		toaShjfglEquipmentWrong.setCreateUserDept(deptId);
		toaShjfglEquipmentWrong.setStatus(status);
		List<ToaShjfglEquipmentWrong> toaShjfglEquipmentWrongList = toaShjfglEquipmentWrongService.queryAll(toaShjfglEquipmentWrong);
		
		//设备操作
		ToaMachineRestart toaMachineRestart = new ToaMachineRestart();
		toaMachineRestart.setCreateUserDept(deptId);
		toaMachineRestart.setStatus(status);
		List<ToaMachineRestart> toaMachineRestartList = toaMachineRestartService.queryAll(toaMachineRestart);
		
		//人员进出
		ToaRoomVisitMaintenance toaRoomVisitMaintenance = new ToaRoomVisitMaintenance();
		toaRoomVisitMaintenance.setCreateUserDept(deptId);
		toaRoomVisitMaintenance.setStatus(status);
		List<ToaRoomVisitMaintenance> toaRoomVisitMaintenanceList = toaRoomVisitMaintenanceService.queryAll(toaRoomVisitMaintenance);
		
		//收发快递
		ToaMachineExpress toaMachineExpress=new ToaMachineExpress();
		toaMachineExpress.setCreateUserDept(deptId);
		toaMachineExpress.setStatus(status);
		List<ToaMachineExpress> toaExpressList=toaMachineExpressService.queryAll(toaMachineExpress);
		
		//设备搬迁
		ToaShjfglEquipmentMove toaShjfglEquipmentMove=new ToaShjfglEquipmentMove();
		toaShjfglEquipmentMove.setCreateUserDept(deptId);
		toaShjfglEquipmentMove.setStatus(status);
		List<ToaShjfglEquipmentMove> toaMoveList=toaShjfglEquipmentMoveService.queryAll(toaShjfglEquipmentMove);
		
		
		Map<String, Object> countMap = new HashMap<String, Object>();
		if(toaNetworkWrongList !=null){
			countMap.put("net", toaNetworkWrongList.size());
		}
		if(toaShjfglEquipmentWrongList !=null){
			countMap.put("equipment", toaShjfglEquipmentWrongList.size());
		}
		if(toaMachineRestartList !=null){
			countMap.put("operate", toaMachineRestartList.size());
		}
		if(toaRoomVisitMaintenanceList !=null){
			countMap.put("inout", toaRoomVisitMaintenanceList.size());
		}
		if(toaExpressList !=null){
			countMap.put("delivery", toaExpressList.size());
		}
		if(toaMoveList !=null){
			countMap.put("move", toaMoveList.size());
		}
		
		if(!countMap.isEmpty()){
			// 成功状态位和数据
			resultMap.put("state", "success");
			resultMap.put("data", countMap);
		}else{
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap; 
	}
}