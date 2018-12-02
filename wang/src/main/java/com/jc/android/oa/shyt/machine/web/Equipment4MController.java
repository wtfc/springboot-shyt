package com.jc.android.oa.shyt.machine.web;

import java.io.UnsupportedEncodingException;
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

import com.jc.android.oa.shyt.machine.domain.Equipment4M;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.machine.domain.ToaMachineRestart;
import com.jc.oa.machine.service.IToaMachineRestartService;
import com.jc.shjfgl.machine.domain.Equipment;
import com.jc.shjfgl.machine.service.IEquipmentService;
import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.ActionLog;


@Controller
@RequestMapping(value="/android/machine/equipment")
public class Equipment4MController extends BaseController{
	
	@Autowired
	private IEquipmentService equipmentService;
	
	@Autowired 
	private IToaMachineRestartService toaMachineRestartService;

    @Autowired
    private IUserService userService;
    
	public Equipment4MController(){};
	/**
	 * 保存方法
	 * @param TimeSet timeSet 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-12-08
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单表",operateFuncNm="save",operateDescribe="对工单表表进行发起操作")
	public Map<String,Object> save(Equipment equipment,BindingResult result,
			HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
//		resultMap = validateToken(request);
//		if (resultMap.size() > 0) {
//			return resultMap;
//		}
		if(!"false".equals(resultMap.get("success"))){
			if(equipment.getId()!=null){
				equipment.setId(null);
			}
			equipmentService.save(equipment);
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单表",operateFuncNm="update",operateDescribe="对机房工单表进行更新操作")
	public Map<String, Object> update(Equipment equipment, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = equipmentService.update(equipment);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param TimeSet timeSet 实体类
	 * @return TimeSet 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-12-08
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单表",operateFuncNm="get",operateDescribe="对机房工单表进行单条查询操作")
	public Equipment get(Equipment equipment,HttpServletRequest request) throws Exception{
		return equipmentService.get(equipment);
	}

	/**
	 * 根据登录用户ID对设备信息分页查询方法
	 * @param 	int 				page 		分页页码
	 * @param   HttpServletRequest 	request
	 * @return  Map<String, Object> resultMap 单条设备信息查询结果（是否成功，提示信息）
	 * @throws 	CustomException
	 * @author  wtf
	 * @version 2017-04-12
	 */
	@RequestMapping(value="manage4MList.action")
	@ResponseBody
	@ActionLog(operateModelNm="设备信息表",operateFuncNm="manage4MList",operateDescribe="根据登录用户ID对设备信息分页查询方法")
	public Map<String, Object> manageList(Equipment equipment,PageManager page,HttpServletRequest request) throws CustomException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//默认排序
		if(StringUtil.isEmpty(equipment.getOrderBy())) {
			equipment.addOrderByFieldDesc("t.CREATE_DATE");
		}
		String companyId = SystemSecurityUtils.getUser().getCode();	//登陆者ID
		String deptName = SystemSecurityUtils.getUser().getDeptName();//部门
		if(!StringUtil.isEmpty(companyId)){	//companyId不为空，鉴别为客户登录
			equipment.setCompanyId(Long.valueOf(companyId));
		}else if(!StringUtil.isEmpty(deptName)){//companyId为空,鉴别为机房人员
			equipment.setContact(deptName);
		}
		PageManager _page = equipmentService.query(equipment, page);
		if(_page !=null){
			// 成功状态位和数据
			resultMap.put("state", "success");
			resultMap.put("data", _page.getData());
		}else{
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap; 
	}
	
	/**
	 * 根据设备ID获取单条设备信息方法
	 * @param   Equipment 			equipment 	设备信息实体类
	 * @param   Long 	  			id      	设备信息id
	 * @param   HttpServletRequest 	request
	 * @param   BindingResult 		result 		校验结果
	 * @throws  Exception
	 * @return  Map<String, Object> resultMap 单条设备信息查询结果（是否成功，提示信息）
	 * @author  wtf
	 * @version 2017-04-12
	 */
	@RequestMapping(value="getById4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="设备信息表",operateFuncNm="getById4M",operateDescribe="对设备信息表进行单条查询操作")
	public Map<String, Object> getById4M(Equipment equipment,Long id,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		equipment.setId(id);
		
		Equipment equipmentTemp=equipmentService.get(equipment);
		if(equipmentTemp !=null){
				Equipment4M equipment4M = new Equipment4M();
				BeanUtils.copyProperties(equipmentTemp, equipment4M);// (源，目标)
				
				//获取设备工单记录
				ToaMachineRestart toaMachineRestart = new ToaMachineRestart();
				toaMachineRestart.setEquipmentId(id.toString());
				toaMachineRestart.addOrderByFieldDesc("t.CREATE_DATE");
				List<ToaMachineRestart> operateList = toaMachineRestartService.queryAll(toaMachineRestart);
				// 成功状态位和数据
				resultMap.put("state", "success");
				resultMap.put("data", equipment4M);
				resultMap.put("operateList", operateList);
		}else{
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap; 
	}
	
	/**
	 * 根据设备ID对设备进行重启
	 * @param 	TimeSet 			timeSet 设备信息实体类
	 * @param 	Long 	  			id      设备信息id
	 * @param 	String  			operate 操作类型（1、重启操作、...）
	 * @param 	BindingResult 		result 	校验结果
	 * @param 	HttpServletRequest 	request
	 * @throws 	Exception
	 * @return 	Map<String, Object> resultMap 重启操作结果（是否成功，提示信息）
	 * @author  wtf
	 * @version 2017-04-12
	 */
	@RequestMapping(value = "equipmentRestartById.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单表",operateFuncNm="equipmentRestartById",operateDescribe="对根据设备ID，工单表表进行发起操作")
	public Map<String,Object> equipmentRestartById(Equipment equipment,Long id,String operate,BindingResult result,
			HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = validateBean(result);
		//Equipment equipmentTemp=null;
		if (resultMap.size() > 0) {
			return resultMap;
		}
		//得到要操作数据的信息
		//equipmentTemp=equipmentService.get(equipment);
		
		if(!"false".equals(resultMap.get("success"))){
			
			//发起操作
			Integer flag=equipmentService.operate(equipment,operate);
			if(flag !=null && flag==1){
				//Equipment4M equipment4M = new Equipment4M();
				//BeanUtils.copyProperties(equipmentTemp, equipment4M);// (源，目标)
				// 成功状态位和数据
				resultMap.put("state", "success");
				//resultMap.put("data", equipment4M);
			}else{
				// 失败状态位和错误信息
				resultMap.put("state", "failure");
				resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
			}
		}
		return resultMap;
	}
}