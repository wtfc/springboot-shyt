package com.jc.android.oa.shyt.customer.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.android.oa.shyt.customer.domain.ToaRoomVisitMaintenance4M;
import com.jc.android.oa.shyt.machine.domain.ToaMachineRestart4M;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.machine.domain.ToaMachineAttach;
import com.jc.oa.machine.domain.ToaMachineRestart;
import com.jc.oa.machine.domain.ToaRoomVisitMaintenance;
import com.jc.oa.machine.service.IToaMachineAttachService;
import com.jc.oa.machine.service.IToaRoomVisitMaintenanceService;
import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.number.service.Number;
import com.jc.system.number.service.interfaces.INumber;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;
/**
 * @author mrb
 * @version 机房参观和入室维护表
*/
@Controller
@RequestMapping(value="/machine/toaRoomVisitMaintenance")
public class ToaRoomVisitMaintenanceController extends BaseController{
	
	@Autowired 
	private IToaRoomVisitMaintenanceService toaRoomVisitMaintenanceService; 
	@Autowired
	private IToaMachineAttachService toaMachineAttachService;//附件工单表
	
	public ToaRoomVisitMaintenanceController(){
	}
	
	/**
	 * 保存方法
	 * @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房参观和入室维护表",operateFuncNm="save",operateDescribe="对机房参观和入室维护表进行发起操作")
	public Map<String,Object> save(ToaRoomVisitMaintenance toaRoomVisitMaintenance,BindingResult result,
			HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		if(!"false".equals(resultMap.get("success"))){
			toaRoomVisitMaintenance.setStatus(0);
			toaRoomVisitMaintenanceService.save(toaRoomVisitMaintenance);
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}
		return resultMap;
	}

	/**
	 * 根据工单类型保存方法
	 * @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	 * @param Integer 	operateType		工单类型（19、机房参观；20、入室维护）
	 * @param String 	imgName			工单类型图标名称
	 * @param String 	machina			机房
	 * @param String 	contact			联系人
	 * @param String 	tel				联系人联系方式
	 * @param String 	intPeople		参观/入室人员
	 * @param String 	intPeopleCard	入室人员身份证号
	 * @param Date 		intDate			预计时间
	 * @param String 	visitZone		参观区域
	 * @param String 	type			操作类型
	 * @param String 	realityType		实际操作内容
	 * @param String 	preOperate		有无前置操作
	 * @param String 	preOperateUrl	前置操作链接
	 * @param BindingResult result 校验结果
	 * @return Map<String, Object> 执行结果（是否成功，提示信息）
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value = "saveByOperateType.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房参观和入室维护表",operateFuncNm="saveByOperateType",operateDescribe="对机房参观和入室维护表进行发起操作")
	public Map<String,Object> saveByOperateType(ToaRoomVisitMaintenance toaRoomVisitMaintenance,Date intDate,BindingResult result,
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
		//编号
		INumber number = new Number();
		String applyNum = number.getNumber("MachineInout", 1,2, null);
		applyNum = applyNum.substring(1, applyNum.length());
		String equipmentNumber=applyNum;
		toaRoomVisitMaintenance.setEquipmentNumber(equipmentNumber);//工单编号
		if(!"false".equals(resultMap.get("success"))){
			toaRoomVisitMaintenance.setStatus(0);
			Integer flag = toaRoomVisitMaintenanceService.saveByOperateType(toaRoomVisitMaintenance);
			if (flag == 1) {
				ToaRoomVisitMaintenance toaRoomVisitMaintenanceTemp = new ToaRoomVisitMaintenance();
				toaRoomVisitMaintenanceTemp.setEquipmentNumber(equipmentNumber);
				toaRoomVisitMaintenanceTemp = toaRoomVisitMaintenanceService.get(toaRoomVisitMaintenanceTemp);
				
				resultMap.put("state", "success");
				resultMap.put("data", toaRoomVisitMaintenanceTemp.getId());
			}else{
				resultMap.put("state", "failure");
				resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
			}
		}
		return resultMap;
	}
	
	/**
	* 修改方法
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房参观和入室维护表",operateFuncNm="update",operateDescribe="对机房参观和入室维护表进行更新操作")
	public Map<String, Object> update(ToaRoomVisitMaintenance toaRoomVisitMaintenance, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaRoomVisitMaintenanceService.update(toaRoomVisitMaintenance);
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
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房参观和入室维护表",operateFuncNm="get",operateDescribe="对机房参观和入室维护表进行单条查询操作")
	public ToaRoomVisitMaintenance get(ToaRoomVisitMaintenance toaRoomVisitMaintenance,HttpServletRequest request) throws Exception{
		return toaRoomVisitMaintenanceService.get(toaRoomVisitMaintenance);
	}
	
	/**
	 * 分页查询方法
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房参观和入室维护表",operateFuncNm="get",operateDescribe="机房参观和入室维护表分页查询")
	public PageManager manageList(ToaRoomVisitMaintenance toaRoomVisitMaintenance,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(toaRoomVisitMaintenance.getOrderBy())) {
			toaRoomVisitMaintenance.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = toaRoomVisitMaintenanceService.query(toaRoomVisitMaintenance, page);
		return page_; 
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="机房参观和入室维护表",operateFuncNm="manage",operateDescribe="对机房参观和入室维护表进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "machine/toaRoomVisitMaintenance/toaRoomVisitMaintenance";
	}
	
	/**
	 * @description 新增与修改方法
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadForm.action")
	@ActionLog(operateModelNm="机房参观和入室维护表表",operateFuncNm="loadForm",operateDescribe="对机房参观和入室维护表表跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		//String id= request.getParameter("id");
		//if(!StringUtil.isEmpty(id)){
		//	model.addAttribute("Id", id);
		//}
		return "machine/toaRoomVisitMaintenance/module/toaRoomVisitMaintenanceModule";
	}
	
	/**
	 * 删除方法
	 * @param TimeSet timeSet 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房参观和入室维护表表",operateFuncNm="deleteByIds",operateDescribe="对机房参观和入室维护表表进行删除")
	public  Map<String, Object> deleteByIds(ToaRoomVisitMaintenance toaRoomVisitMaintenance,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaRoomVisitMaintenance.setPrimaryKeys(ids.split(","));	
		if(toaRoomVisitMaintenanceService.deleteByIds(toaRoomVisitMaintenance) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
		}
		return resultMap;
	}
	
	/**
	* APP-入室操作-接单  status(0-1)  申请状态 =》已接单状态
	* @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	* @param Long id 工单ID
	* @return Map<String, Object> 执行结果（是否成功，提示信息）
	* @author wtf
	*/
	@RequestMapping(value="update4MByIdJieDan.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房参观和入室维护表",operateFuncNm="update4MByIdJieDan",operateDescribe="对机房参观和入室维护表进行更新操作")
	public Map<String, Object> update4MByIdJieDan(ToaRoomVisitMaintenance toaRoomVisitMaintenance, Long id,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaRoomVisitMaintenanceService.jieDan(toaRoomVisitMaintenance);
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "接单成功");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}
	
	/**
	* APP-入室操作-身份审核 status(1-2) 已接单状态=》审核通过状态
	* @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	* @param Long id 工单ID
	* @return Map<String, Object> 执行结果（是否成功，提示信息）
	* @author wtf
	*/
	@RequestMapping(value="update4MByIdShenHe.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房参观和入室维护表",operateFuncNm="update4MByIdShenHe",operateDescribe="对机房参观和入室维护表进行更新操作")
	public Map<String, Object> update4MByIdShenHe(ToaRoomVisitMaintenance toaRoomVisitMaintenance, Long id,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaRoomVisitMaintenanceService.shenFenShenHe(toaRoomVisitMaintenance);
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "已审核");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}
	
	/**
	* APP-入室操作-进行操作(机房参观、入室维护) status(2-3) 审核通过状态=》操作完成状态
	* @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	* @param Long id 工单ID
	* @param String realityType 实际操作内容
	* @return Map<String, Object> 执行结果（是否成功，提示信息）
	* @author wtf
	*/
	@RequestMapping(value="update4MByIdOperate.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房参观和入室维护表",operateFuncNm="update4MByIdOperate",operateDescribe="对机房参观和入室维护表进行更新操作")
	public Map<String, Object> update4MByIdOperate(ToaRoomVisitMaintenance toaRoomVisitMaintenance, Long id,String realityType,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaRoomVisitMaintenanceService.operate(toaRoomVisitMaintenance);
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "操作完成");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}
	/**
	 * APP 入室操作-获取单条记录方法
	 * @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	 * @param Long id 工单ID
	 * @return Map<String, Object> 查询结果（是否成功，提示信息）
	 * @throws Exception
	 * @author wtf
	 */
	@RequestMapping(value="get4MById.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房参观和入室维护表",operateFuncNm="get4MById",operateDescribe="对机房参观和入室维护表进行单条查询操作")
	public Map<String, Object> get4MById(ToaRoomVisitMaintenance toaRoomVisitMaintenance, Long id,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		toaRoomVisitMaintenance.setId(id);
		toaRoomVisitMaintenance=toaRoomVisitMaintenanceService.get(toaRoomVisitMaintenance);

		if (toaRoomVisitMaintenance != null) {

			//附件工单表
			ToaMachineAttach toaMachineAttach=new ToaMachineAttach();
			toaMachineAttach.setBusinessId(id.intValue());
			toaMachineAttach.setBusinessSource(toaRoomVisitMaintenance.getOperateType().toString());
			List<ToaMachineAttach> toaAttachList=toaMachineAttachService.queryAll(toaMachineAttach);
			ToaRoomVisitMaintenance4M toaRoomVisitMaintenance4MTemp=new ToaRoomVisitMaintenance4M();
			BeanUtils.copyProperties(toaRoomVisitMaintenance, toaRoomVisitMaintenance4MTemp);
			
			resultMap.put("state", "success");
			resultMap.put("data", toaRoomVisitMaintenance4MTemp);
			resultMap.put("fileName",toaAttachList);
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
		
	}
	
	/**
	 * APP 入室操作-分页查询方法
	 * @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	 * @param PageManager page 分页实体类
	 * @return Map<String, Object> 查询结果（是否成功，提示信息）
	 * @throws Exception
	 * @author wtf
	 * @throws CustomException 
	 */
	@RequestMapping(value="manage4MList.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房参观和入室维护表",operateFuncNm="manage4MList",operateDescribe="机房参观和入室维护表分页查询")
	public Map<String, Object> manage4MList(ToaRoomVisitMaintenance toaRoomVisitMaintenance,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ToaRoomVisitMaintenance4M> resultListTemp = new ArrayList<ToaRoomVisitMaintenance4M>();
		//默认排序
		if(StringUtil.isEmpty(toaRoomVisitMaintenance.getOrderBy())) {
			toaRoomVisitMaintenance.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}

		User user=SystemSecurityUtils.getUser();
		toaRoomVisitMaintenance.setCreateUserDept(user.getDeptId());
		toaRoomVisitMaintenance.setStatus(0);
		toaRoomVisitMaintenance.setCaozgcs(user.getId().toString());
		List<ToaRoomVisitMaintenance> resultList = toaRoomVisitMaintenanceService.queryAll(toaRoomVisitMaintenance);
		if(resultList !=null){
			// 成功状态位和数据
			for(ToaRoomVisitMaintenance t:resultList){
				ToaRoomVisitMaintenance4M toaRoomVisitMaintenance4MTemp=new ToaRoomVisitMaintenance4M();
				BeanUtils.copyProperties(t, toaRoomVisitMaintenance4MTemp);
				resultListTemp.add(toaRoomVisitMaintenance4MTemp);
			}
			if(resultListTemp!=null){
				resultMap.put("state", "success");
				resultMap.put("data", resultListTemp);
			}
		}else{
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap; 
	}
	
	/**
	 * APP 入室操作-分页查询方法(客户APP使用)
	 * @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	 * @param PageManager page 分页实体类
	 * @return Map<String, Object> 查询结果（是否成功，提示信息）
	 * @throws Exception
	 * @author wtf
	 * @throws CustomException 
	 */
	@RequestMapping(value="manage4MListForCustomer.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房参观和入室维护表",operateFuncNm="manage4MListForCustomer",operateDescribe="机房参观和入室维护表分页查询")
	public Map<String, Object> manage4MListForCustomer(ToaRoomVisitMaintenance toaRoomVisitMaintenance,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ToaRoomVisitMaintenance> resultList = new ArrayList<ToaRoomVisitMaintenance>();
		List<ToaRoomVisitMaintenance4M> resultListTemp = new ArrayList<ToaRoomVisitMaintenance4M>();
		//默认排序
		if(StringUtil.isEmpty(toaRoomVisitMaintenance.getOrderBy())) {
			toaRoomVisitMaintenance.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}

		User user=SystemSecurityUtils.getUser();
		if(!StringUtils.isEmpty(user.getCode())){
			toaRoomVisitMaintenance.setCompanyId(Integer.parseInt(user.getCode()));
			resultList = toaRoomVisitMaintenanceService.queryAll(toaRoomVisitMaintenance);
		}
		if(resultList !=null){
			// 成功状态位和数据
			for(ToaRoomVisitMaintenance t:resultList){
				ToaRoomVisitMaintenance4M toaRoomVisitMaintenance4MTemp=new ToaRoomVisitMaintenance4M();
				BeanUtils.copyProperties(t, toaRoomVisitMaintenance4MTemp);
				resultListTemp.add(toaRoomVisitMaintenance4MTemp);
			}
			if(resultListTemp!=null){
				resultMap.put("state", "success");
				resultMap.put("data", resultListTemp);
			}
		}else{
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap; 
	}

}
