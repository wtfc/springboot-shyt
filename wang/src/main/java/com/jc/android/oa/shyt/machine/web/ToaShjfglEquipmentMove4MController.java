package com.jc.android.oa.shyt.machine.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.android.oa.shyt.machine.domain.ToaMachineRestart4M;
import com.jc.android.oa.shyt.machine.domain.ToaShjfglEquipmentMove4M;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.machine.domain.ToaMachineAttach;
import com.jc.oa.machine.domain.ToaMachineRestart;
import com.jc.oa.machine.service.IToaMachineAttachService;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentMove;
import com.jc.shjfgl.machine.service.IToaShjfglEquipmentMoveService;
import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;
/**
 * @author mrb
 * @version 设备搬迁表
*/
@Controller
@RequestMapping(value="/android/machine/toaShjfglEquipmentMove")
public class ToaShjfglEquipmentMove4MController extends BaseController{
	 
 
	@Autowired
	private IToaMachineAttachService toaMachineAttachService;//附件工单表
	
	@Autowired
	private IToaShjfglEquipmentMoveService toaShjfglEquipmentMoveService;//设备搬迁表
	
	public ToaShjfglEquipmentMove4MController(){
	}
	
	/**
	 * APP设备搬迁-保存方法
	 * @param TimeSet 	timeSet 		实体类
	 * @param BindingResult result 校验结果
	 * @return Map<String,Object>  success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value = "save4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="设备搬迁过程表",operateFuncNm="save4M",operateDescribe="对设备搬迁过程表进行发起操作")
	public Map<String,Object> save4M(ToaShjfglEquipmentMove toaShjfglEquipmentMove,BindingResult result,HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = validateBean(result);
		Integer flag=toaShjfglEquipmentMoveService.saveEquipmentMove(toaShjfglEquipmentMove);
		String moveNewId=toaShjfglEquipmentMove.getId().toString();
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "保存完成");
			resultMap.put("moveNewId", moveNewId);
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
			resultMap.put("data", "保存失败");
		}
		resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		return resultMap;
	}

	/**
	 * APP设备搬迁-获取单条方法
	 * @param TimeSet 	timeSet 		实体类
	 * @param Long id 搬迁工单ID
	 * @param BindingResult result 校验结果
	 * @return Map<String,Object>  success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value = "getById4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="设备搬迁过程表",operateFuncNm="getById4M",operateDescribe="对设备搬迁过程表进行发起操作")
	public Map<String,Object> getById4M(ToaShjfglEquipmentMove toaShjfglEquipmentMove,Long id,HttpServletRequest request) throws Exception{
		Map<String,Object> resultMap=new HashMap<String,Object>();
		//默认排序
		if(StringUtil.isEmpty(toaShjfglEquipmentMove.getOrderBy())) {
			toaShjfglEquipmentMove.addOrderByFieldDesc("t.CREATE_DATE");
		}
		toaShjfglEquipmentMove.setId(id);
		ToaShjfglEquipmentMove toaEquipmentMove=toaShjfglEquipmentMoveService.get(toaShjfglEquipmentMove);
		
		if(toaEquipmentMove!=null){
			//附件工单表
			ToaMachineAttach toaMachineAttach=new ToaMachineAttach();
			toaMachineAttach.setBusinessId(id.intValue());
			toaMachineAttach.setBusinessSource("3");
			List<ToaMachineAttach> toaAttachList=toaMachineAttachService.queryAll(toaMachineAttach);
			
			ToaShjfglEquipmentMove4M toaEquipmentMoveTemp=new ToaShjfglEquipmentMove4M();
			BeanUtils.copyProperties(toaEquipmentMove, toaEquipmentMoveTemp);
			resultMap.put("state", "success");
			resultMap.put("fileName",toaAttachList);
			resultMap.put("data", toaEquipmentMoveTemp);
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap; 
	}
	
	/**
	 * APP设备搬迁-搬迁分页查询方法
	 * @param TimeSet 	timeSet 		实体类
	 * @param HttpServletRequest request
	 * @throws CustomException
	 * @return Map<String,Object> 查询结果
	 */
	@RequestMapping(value="manageList4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="设备搬迁过程表",operateFuncNm="manageList4M",operateDescribe="对设备搬迁过程表进行发起操作")
	public Map<String,Object> manageList4M(ToaShjfglEquipmentMove toaShjfglEquipmentMove,HttpServletRequest request) throws CustomException{
		
		Map<String,Object> resultMap=new HashMap<String,Object>();
		//默认排序
		if(StringUtil.isEmpty(toaShjfglEquipmentMove.getOrderBy())) {
			toaShjfglEquipmentMove.addOrderByFieldDesc("t.CREATE_DATE");
		}
		User user=SystemSecurityUtils.getUser();
		String companyId=user.getCode();
		if(!StringUtil.isEmpty(companyId)){	//companyId不为空，鉴别为客户登录
			toaShjfglEquipmentMove.setCompanyId(Integer.valueOf(companyId));
		}else{
			toaShjfglEquipmentMove.setCreateUserDept(user.getDeptId());
			toaShjfglEquipmentMove.setStatus(0);
			toaShjfglEquipmentMove.setCaozgcs(user.getId().toString());
		}
		List<ToaShjfglEquipmentMove> equipmentMoves=toaShjfglEquipmentMoveService.quertMove(toaShjfglEquipmentMove);
		List<ToaShjfglEquipmentMove4M> resultList = new ArrayList<ToaShjfglEquipmentMove4M>();
		if(equipmentMoves!=null){
			for(ToaShjfglEquipmentMove t:equipmentMoves){
				ToaShjfglEquipmentMove4M toaEquipmentMoveTemp=new ToaShjfglEquipmentMove4M();
				BeanUtils.copyProperties(t, toaEquipmentMoveTemp);
				resultList.add(toaEquipmentMoveTemp);
			}
			if(resultList!=null){
				resultMap.put("state", "success");
				resultMap.put("data", resultList);
			}
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap; 
	}
	
	/**
	 * APP设备搬迁-接单方法(0-1)
	 * @param ToaShjfglEquipmentMove ToaShjfglEquipmentMove 搬迁操作实体类
	 * @param id 迁移工单ID
	 * @param status 迁移工单状态
	 * @param HttpServletRequest request
	 * @return Map<String,Object> 查询结果
	 * @throws CustomException
	 */
	@RequestMapping(value="updateMove4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="设备搬迁过程表",operateFuncNm="updateMove4M",operateDescribe="对设备搬迁过程表进行发起操作")
	public Map<String, Object> updateMove4M(ToaShjfglEquipmentMove toaShjfglEquipmentMove,BindingResult result,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag=0;
		if(toaShjfglEquipmentMove.getStatus()==0){
			ToaShjfglEquipmentMove toaEquipmentMove=toaShjfglEquipmentMoveService.get(toaShjfglEquipmentMove);
			if(toaEquipmentMove != null ){
				flag = toaShjfglEquipmentMoveService.updateMove(toaShjfglEquipmentMove);
			}
		}
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "接单成功");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
			resultMap.put("data", "接单失败");
		}
		return resultMap;
	}	
	
	/**
	 * APP设备搬迁-到达设备现场方法(1-2)
	 * @param ToaShjfglEquipmentMove ToaShjfglEquipmentMove 重启操作实体类
	 * @param id 搬迁工单ID
	 * @param HttpServletRequest request
	 * @return Map<String,Object> 查询结果
	 * @throws CustomException
	 */
	@RequestMapping(value="updateMoveScan4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="设备搬迁过程表",operateFuncNm="updateMoveScan4M",operateDescribe="对设备搬迁过程表进行发起操作")
	public Map<String, Object> updateMoveScan4M(ToaShjfglEquipmentMove toaShjfglEquipmentMove,BindingResult result,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaShjfglEquipmentMoveService.updateMoveScan(toaShjfglEquipmentMove);
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "到达设备现场");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
			resultMap.put("data", "未到达设备现场");
		}
		return resultMap;
	}
	
	/**
	 * APP设备搬迁-开始操作(2-3)
	 * @param ToaShjfglEquipmentMove ToaShjfglEquipmentMove 重启操作实体类
	 * @param id 搬迁工单ID
	 * @param sn 搬迁sn
	 * @param HttpServletRequest request
	 * @return Map<String,Object> 查询结果
	 * @throws CustomException
	 */
	@RequestMapping(value="updateMoveHandle4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="设备搬迁过程表",operateFuncNm="updateMoveHandle4M",operateDescribe="对设备搬迁过程表进行发起操作")
	public Map<String, Object> updateMoveHandle4M(ToaShjfglEquipmentMove toaShjfglEquipmentMove,BindingResult result,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaShjfglEquipmentMoveService.updateMoveHandle(toaShjfglEquipmentMove);
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "设备正确");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
			resultMap.put("data", "设备不正确");
		}
		return resultMap;
	}
	
	/**
	 * APP设备搬迁-操作完成(3-4)
	 * @param ToaShjfglEquipmentMove ToaShjfglEquipmentMove 重启操作实体类
	 * @param id 搬迁工单ID
	 * @param HttpServletRequest request
	 * @return Map<String,Object> 查询结果
	 * @throws CustomException
	 */
	@RequestMapping(value="updateMoveShow4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="设备搬迁过程表",operateFuncNm="updateMoveShow4M",operateDescribe="对设备搬迁过程表进行发起操作")
	public Map<String, Object> updateMoveShow4M(ToaShjfglEquipmentMove toaShjfglEquipmentMove,BindingResult result,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaShjfglEquipmentMoveService.updateMoveShow(toaShjfglEquipmentMove);
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "操作完成");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
			resultMap.put("data", "操作失败");
		}
		return resultMap;
	}
	
	/**
	 * APP设备搬迁-到达上架设备现场方法(4-5)
	 * @param ToaShjfglEquipmentMove ToaShjfglEquipmentMove 重启操作实体类
	 * @param id 搬迁工单ID
	 * @param HttpServletRequest request
	 * @return Map<String,Object> 查询结果
	 * @throws CustomException
	 */
	@RequestMapping(value="headblockMove4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="设备搬迁过程表",operateFuncNm="headblockMove4M",operateDescribe="对设备搬迁过程表进行发起操作")
	public Map<String, Object> headblockMove4M(ToaShjfglEquipmentMove toaShjfglEquipmentMove,BindingResult result,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaShjfglEquipmentMoveService.headblockMove(toaShjfglEquipmentMove);
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "到达上架设备现场");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
			resultMap.put("data", "未到达上架设备现场");
		}
		return resultMap;
	}

	/**
	 * APP设备搬迁-上架开始操作(5-6)
	 * @param ToaShjfglEquipmentMove ToaShjfglEquipmentMove 重启操作实体类
	 * @param id 搬迁工单ID
	 * @param sn 搬迁sn
	 * @param HttpServletRequest request
	 * @return Map<String,Object> 查询结果
	 * @throws CustomException
	 */
	@RequestMapping(value="StoreOperatingMove4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="设备搬迁过程表",operateFuncNm="StoreOperatingMove4M",operateDescribe="对设备搬迁过程表进行发起操作")
	public Map<String, Object> StoreOperatingMove4M(ToaShjfglEquipmentMove toaShjfglEquipmentMove,BindingResult result,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaShjfglEquipmentMoveService.StoreOperatingMove(toaShjfglEquipmentMove);
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "设备正确");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
			resultMap.put("data", "设备不正确");
		}
		return resultMap;
	}
	
	/**
	 * APP设备搬迁-搬迁完成(6-7)
	 * @param ToaShjfglEquipmentMove ToaShjfglEquipmentMove 重启操作实体类
	 * @param id 搬迁工单ID
	 * @param HttpServletRequest request
	 * @return Map<String,Object> 查询结果
	 * @throws CustomException
	 */
	@RequestMapping(value="completeMove4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="设备搬迁过程表",operateFuncNm="completeMove4M",operateDescribe="对设备搬迁过程表进行发起操作")
	public Map<String, Object> completeMove4M(ToaShjfglEquipmentMove toaShjfglEquipmentMove,BindingResult result,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaShjfglEquipmentMoveService.completeMove(toaShjfglEquipmentMove);
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "操作完成");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
			resultMap.put("data", "操作失败");
		}
		return resultMap;
	}
}
