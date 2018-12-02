 package com.jc.android.oa.shyt.machine.web;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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

import com.jc.android.oa.shyt.machine.domain.ToaMachineRestart4M;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.machine.domain.ToaMachineAttach;
import com.jc.oa.machine.domain.ToaMachineExchange;
import com.jc.oa.machine.domain.ToaMachineRestart;
import com.jc.oa.machine.domain.ToaRoomVisitMaintenance;
import com.jc.oa.machine.service.IToaMachineAttachService;
import com.jc.oa.machine.service.IToaMachineExchangeService;
import com.jc.oa.machine.service.IToaMachineRestartService;
import com.jc.shjfgl.machine.service.IEquipmentService;
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
 * @version 重启操作
*/
@Controller
@RequestMapping(value="/customer/toaMachineRestartInfo")
public class ToaMachineRestartInfoController extends BaseController{
	
	@Autowired 
	private IToaMachineRestartService toaMachineRestartService;
	
	@Autowired
	private IEquipmentService equipmentService;
	@Autowired
	private IToaMachineExchangeService toaMachineExchangeService;
	@Autowired
	private IToaMachineAttachService toaMachineAttachService;//附件工单表
	
	public ToaMachineRestartInfoController(){
	}
	
	/**
	 * 保存方法
	 * @param TimeSet timeSet 实体类
	 * @param Long 	  id      机房信息id
	 * @param String  operate 操作类型
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-12-08
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单表",operateFuncNm="save",operateDescribe="对工单表表进行发起操作")
	public Map<String,Object> save(Model model,ToaMachineRestart toaMachineRestart,Long id,String operate,BindingResult result,
			HttpServletRequest request) throws Exception{
		ToaMachineRestart toaMachineRestartTemp=new ToaMachineRestart();
		Map<String, Object> resultMap = validateBean(result);
		toaMachineRestartTemp=toaMachineRestartService.get(toaMachineRestart);
		Integer flag=toaMachineRestartService.operate(toaMachineRestartTemp, request);
		if(flag==-1){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, "设备信息不足，无法完成操作");
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}else if(flag==1){
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}else if(flag==2){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_002"));
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}else{
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, "设备不存在");
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}
		
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
	@ActionLog(operateModelNm="重启操作",operateFuncNm="get",operateDescribe="对重启操作进行单条查询操作")
	public ToaMachineRestart get(ToaMachineRestart toaMachineRestart,HttpServletRequest request) throws Exception{
		return toaMachineRestartService.get(toaMachineRestart);
	}
	
	/**
	 * 分页查询方法
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @throws CustomException 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="manageList",operateDescribe="重启操作分页查询")
	public PageManager manageList(ToaMachineRestart toaMachineRestart,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		//默认排序
		if(StringUtil.isEmpty(toaMachineRestart.getOrderBy())) {
			toaMachineRestart.addOrderByFieldDesc("t.CREATE_DATE");
		}
		PageManager page_ = toaMachineRestartService.queryAll(toaMachineRestart, page);
		return page_; 
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="重启操作",operateFuncNm="manage",operateDescribe="对重启操作进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		String companyId = SystemSecurityUtils.getUser().getCode();
		model.addAttribute("companyId", companyId);
		return "customer/toaMachineRestartInfo/toaMachineRestartInfo";
	}
	
	/**
	 * @description 新增与修改方法跳转页面
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadSee.action")
	@ActionLog(operateModelNm="重启操作表",operateFuncNm="loadSee",operateDescribe="对重启操作表跳转")
	public String loadSee(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		String name=SystemSecurityUtils.getUser().getDisplayName();
		if(!StringUtil.isEmpty(id)){
			ToaMachineExchange toaMachineExchange=new ToaMachineExchange();
			toaMachineExchange.setRestartId(id);
			toaMachineExchange.setServiceName(0);
			List<ToaMachineExchange> exchangeList=toaMachineExchangeService.queryAll(toaMachineExchange);
			model.addAttribute("Id", id);
			model.addAttribute("idd",id);
			model.addAttribute("exchangeList", exchangeList);
			model.addAttribute("talkName", name);
		}
		return "customer/toaMachineRestartInfo/toaMachineRestartInfoSee";
	}
	
	/**
	 * APP设备操作-机房工单查询-接单方法(0-1)
	 * @param ToaMachineRestart4M toaMachineRestart 重启操作实体类
	 * @param id 重启工单ID
	 * @param status 重启工单状态
	 * @param HttpServletRequest request
	 * @return Map<String,Object> 查询结果
	 * @throws CustomException
	 */
	@RequestMapping(value="updateRoom4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="updateRoom4M",operateDescribe="重启操作接单手机端接口")
	public Map<String, Object> updateRoom4M(ToaMachineRestart toaMachineRestart,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag=0;
		if(toaMachineRestart.getStatus()==0){
			ToaMachineRestart toaRestart=toaMachineRestartService.get(toaMachineRestart);
			if(toaRestart != null ){
				flag = toaMachineRestartService.updateRoom(toaMachineRestart);
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
		//String token = getToken(request);
		//resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}	
	
	/**
	 * APP设备操作-机房工单查询-到达设备现场方法(1-2)
	 * @param ToaMachineRestart4M toaMachineRestart 重启操作实体类
	 * @param id 重启工单ID
	 * @param HttpServletRequest request
	 * @return Map<String,Object> 查询结果
	 * @throws CustomException
	 */
	@RequestMapping(value="updateRoomScan4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="updateRoom",operateDescribe="对重启操作进行更新操作")
	public Map<String, Object> updateRoomScan4M(ToaMachineRestart toaMachineRestart,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaMachineRestartService.updateRoomScan(toaMachineRestart);
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
	 * APP设备操作-机房工单查询-操作完成方法(3-4)
	 * @param ToaMachineRestart4M toaMachineRestart 重启操作实体类
	 * @param id 重启工单ID
	 * @param HttpServletRequest request
	 * @return Map<String,Object> 查询结果
	 * @throws CustomException
	 */
	@RequestMapping(value="updateRoomDirect4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="updateRoom",operateDescribe="对重启操作进行更新操作")
	public Map<String, Object> updateRoomDirect4M(ToaMachineRestart toaMachineRestart,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaMachineRestartService.updateRoomDirect(toaMachineRestart);
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "操作完成");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
			resultMap.put("data", "操作失败");
		}
//		String token = getToken(request);
//		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/**
	* APP设备操作-机房工单查询-设备审批提交操作(工单状态  2-3)
	* @param ToaMachineRestart toaMachineRestart 重启工单实体类
	* @param Long machineRestartId 重启工单ID
	* @param String sn 重启工单sn
	* @throws Exception
	* @return Map<String, Object> resultMap 更新状态（是否成功，提示信息）
	* @author wangtf
	* @version  2017-04-10
	*/
	@RequestMapping(value="update4MRoomHandle.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作-设备审批提交",operateFuncNm="update4MRoomHandle",operateDescribe="对重启操作-设备审批阶段进行提交操作")
	public Map<String, Object> update4MRoomHandle(ToaMachineRestart toaMachineRestart,Long machineRestartId, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		toaMachineRestart.setId(machineRestartId);
		Integer flag = toaMachineRestartService.updateRoomHandle(toaMachineRestart);
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "设备正确");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
			resultMap.put("data", "设备不正确");
		}
//		String token = getToken(request);
//		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/**
	* APP设备操作-机房工单查询-重复重启接显示器提交操作(工单状态  3-4)
	* @param ToaMachineRestart toaMachineRestart 重启工单实体类
	* @param Long 		id 				重启工单ID
	* @param String		ExtStr3 		附件url
	* @param String 	isWrong 		是否报错（是，否）
	* @param String 	regionExamine 	有无后续操作（是，否）
	* @param String 	assist 			是否协助处理（是，否）
	* @param List<Long> Fileids 		附件Id
	* @param String 	DelattachIds 	删除附件Id
	* @return Map<String, Object> resultMap 更新状态（是否成功，提示信息）
	* @author wangtf
	* @version  2017-04-10
	*/
	@RequestMapping(value="update4MRoomShow.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单查询-重复重启",operateFuncNm="update4MRoomShow",operateDescribe="机房工单查询-重复重启接显示器提交操作")
	public Map<String, Object> update4MRoomShow(ToaMachineRestart toaMachineRestart,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		ToaMachineRestart toaMachine=new ToaMachineRestart();
		toaMachine.setId(toaMachineRestart.getId());
		//存放（通过id找到机房,客户名称,IP）
		ToaMachineRestart toaRestart=toaMachineRestartService.get(toaMachine);
		toaMachine.setId(null);
		//使用公司ID  add-wangtf
		toaMachine.setCompanyId(toaRestart.getCompanyId());
		toaMachine.setMachina(toaRestart.getMachina());
		//是否存在重启设备
		List<ToaMachineRestart> toaMachineRestartList=toaMachineRestartService.queryAll(toaMachine);
		Integer flag=0;
		if(toaMachineRestartList.size()==0){
			//直接操作(状态3-4)
			flag = toaMachineRestartService.updateRoomDirect(toaMachineRestart);
		}else{
			int count=0;
			for(int i=0;i<toaMachineRestartList.size();i++){
				ToaMachineRestart toaRestartList=toaMachineRestartList.get(i);
				String extStr=toaRestartList.getFirstRestart();
				count+=Integer.valueOf(extStr);
			}
			if(count>0){
				//显示器(状态3-4)
				flag = toaMachineRestartService.updateRoomShow(toaMachineRestart);
			}else{
				//直接操作(状态3-4)
				flag = toaMachineRestartService.updateRoomDirect(toaMachineRestart);
			}
		}
		//Integer flag = toaMachineRestartService.updateRoomShow(toaMachineRestart);
		if (flag == 1) {
			resultMap.put("state", "success");
//			resultMap.put("data", flag);
			resultMap.put("data", "操作完成");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
			resultMap.put("data", "操作失败");
		}
//		String token = getToken(request);
//		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/**
	 * APP设备操作-保存方法
	 * @param ToaMachineRestart toaMachineRestart 重启工单实体类
	 * @param BindingResult result 校验结果
	 * @throws Exception
	 * @return Map<String, Object> resultMap 更新状态（是否成功，提示信息）
	 */
	@RequestMapping(value = "save4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启工单表",operateFuncNm="save4M",operateDescribe="对已存在的重启工单重新发起操作")
	public Map<String,Object> save4M(Model model,ToaMachineRestart toaMachineRestart,BindingResult result,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
//		//编号
//		INumber number = new Number();
//		String applyNum = number.getNumber("MachineRestart", 1,2, null);
//		applyNum = applyNum.substring(1, applyNum.length());
//		String equipmentNumber=applyNum;
//		toaMachineRestart.setEquipmentNumber(equipmentNumber);
		Integer flag=toaMachineRestartService.saveMachine(toaMachineRestart);
//		ToaMachineRestart toaRestart=new ToaMachineRestart();
//		toaRestart.setEquipmentNumber(applyNum);
//		toaRestart=toaMachineRestartService.get(toaRestart);
		String machineId=toaMachineRestart.getId().toString();
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "保存完成");
			//resultMap.put("machineId", toaRestart.getId().toString());
			resultMap.put("machineId", machineId);
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
			resultMap.put("data", "保存失败");
		}
		resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		return resultMap;
	}
	
	/**
	 * APP设备操作-机房工单查询-分页查询方法
	 * @param ToaMachineRestart4M toaMachineRestart 重启操作实体类
	 * @param Integer companyId 公司ID
	 * @param HttpServletRequest request
	 * @throws CustomException
	 * @return Map<String,Object> 查询结果
	 * @author wangtf
	 */
	@RequestMapping(value="manage4MList.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单查询-分页查询方法",operateFuncNm="manage4MList",operateDescribe="机房工单查询-分页查询方法手机端接口")
	public Map<String,Object> manage4MList(ToaMachineRestart toaMachineRestart,Integer companyId,HttpServletRequest request) throws CustomException{
		
		Map<String,Object> resultMap=new HashMap<String,Object>();
		//默认排序
		if(StringUtil.isEmpty(toaMachineRestart.getOrderBy())) {
			toaMachineRestart.addOrderByFieldDesc("t.CREATE_DATE");
		}
		toaMachineRestart.setCompanyId(companyId);
		List<ToaMachineRestart> machineList = toaMachineRestartService.queryAll(toaMachineRestart);
		List<ToaMachineRestart4M> resultList = new ArrayList<ToaMachineRestart4M>();
		if(machineList!=null){
			for(ToaMachineRestart t:machineList){
				ToaMachineRestart4M toaMachineRestartTemp=new ToaMachineRestart4M();
				BeanUtils.copyProperties(t, toaMachineRestartTemp);
				resultList.add(toaMachineRestartTemp);
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
	 * APP设备操作-机房工单查询-分页查询方法
	 * @param ToaMachineRestart4M toaMachineRestart 重启操作实体类
	 * @param HttpServletRequest request
	 * @throws CustomException
	 * @return Map<String,Object> 查询结果
	 */
	@RequestMapping(value="manageList4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单查询-分页查询方法",operateFuncNm="manageList4M",operateDescribe="机房工单查询-分页查询方法手机端接口")
	public Map<String,Object> manageList4M(ToaMachineRestart toaMachineRestart,PageManager page,HttpServletRequest request) throws CustomException{
		
		Map<String,Object> resultMap=new HashMap<String,Object>();
		//默认排序
		if(StringUtil.isEmpty(toaMachineRestart.getOrderBy())) {
			toaMachineRestart.addOrderByFieldDesc("t.CREATE_DATE");
		}
		User user=SystemSecurityUtils.getUser();
		toaMachineRestart.setCreateUserDept(user.getDeptId());
		toaMachineRestart.setStatus(0);
		toaMachineRestart.setCaozgcs(user.getId().toString());
		List<ToaMachineRestart> machineList = toaMachineRestartService.queryApp(toaMachineRestart);
		List<ToaMachineRestart4M> resultList = new ArrayList<ToaMachineRestart4M>();
//		PageManager page_=toaMachineRestartService.queryApp(toaMachineRestart, page);
//		if(page_!=null){
//			resultMap.put("state", "success");
//     		resultMap.put("data", page_.getAaData());
//		}else{
//			resultMap.put("state", "failure");
//			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
//		}
		if(machineList!=null){
			for(ToaMachineRestart t:machineList){
				ToaMachineRestart4M toaMachineRestartTemp=new ToaMachineRestart4M();
				BeanUtils.copyProperties(t, toaMachineRestartTemp);
				resultList.add(toaMachineRestartTemp);
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
	 * APP设备操作-机房工单查询-分页查询方法(客户APP使用)
	 * @param ToaMachineRestart4M toaMachineRestart 重启操作实体类
	 * @param HttpServletRequest request
	 * @throws CustomException
	 * @return Map<String,Object> 查询结果
	 */
	@RequestMapping(value="manageList4MForCustomer.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单查询-分页查询方法",operateFuncNm="manageList4MForCustomer",operateDescribe="机房工单查询-分页查询方法手机端接口")
	public Map<String,Object> manageList4MForCustomer(ToaMachineRestart toaMachineRestart,PageManager page,HttpServletRequest request) throws CustomException{
		
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<ToaMachineRestart> machineList = new ArrayList<ToaMachineRestart>();
		//默认排序
		if(StringUtil.isEmpty(toaMachineRestart.getOrderBy())) {
			toaMachineRestart.addOrderByFieldDesc("t.CREATE_DATE");
		}
		User user=SystemSecurityUtils.getUser();
		if(!StringUtils.isEmpty(user.getCode())){
			toaMachineRestart.setCompanyId(Integer.parseInt(user.getCode()));
			machineList = toaMachineRestartService.queryApp(toaMachineRestart);
		}
		List<ToaMachineRestart4M> resultList = new ArrayList<ToaMachineRestart4M>();
//		PageManager page_=toaMachineRestartService.queryApp(toaMachineRestart, page);
//		if(page_!=null){
//			resultMap.put("state", "success");
//     		resultMap.put("data", page_.getAaData());
//		}else{
//			resultMap.put("state", "failure");
//			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
//		}
		if(machineList!=null){
			for(ToaMachineRestart t:machineList){
				ToaMachineRestart4M toaMachineRestartTemp=new ToaMachineRestart4M();
				BeanUtils.copyProperties(t, toaMachineRestartTemp);
				resultList.add(toaMachineRestartTemp);
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
	 * APP设备操作-机房工单查询-单条查询方法
	 * @param ToaMachineRestart4M toaMachineRestart 重启操作实体类
	 * @param Long id 重启工单ID
	 * @param HttpServletRequest request
	 * @return Map<String,Object> 查询结果
	 * @throws CustomException
	 * @author wangtf
	 */
	@RequestMapping(value="getById4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单查询-单条查询方法",operateFuncNm="getById4M",operateDescribe="重启操作查询单条数据手机端接口")
	public Map<String,Object> getById4M(ToaMachineRestart toaMachineRestart,Long id,HttpServletRequest request) throws CustomException{
		
		Map<String,Object> resultMap=new HashMap<String,Object>();
		//默认排序
		if(StringUtil.isEmpty(toaMachineRestart.getOrderBy())) {
			toaMachineRestart.addOrderByFieldDesc("t.CREATE_DATE");
		}
		toaMachineRestart.setId(id);
		ToaMachineRestart machine = toaMachineRestartService.get(toaMachineRestart);
//		List<String> toaList=new ArrayList<String>();
//		if(toaAttachList.size()>0){
//			for(int i=0;i<toaAttachList.size();i++){
//				toaList.add(toaAttachList.get(i).getResourcesName());
//			}
//		}
		if(machine!=null){
			//附件工单表
			ToaMachineAttach toaMachineAttach=new ToaMachineAttach();
			toaMachineAttach.setBusinessId(id.intValue());
			toaMachineAttach.setBusinessSource(machine.getOperationType());
			List<ToaMachineAttach> toaAttachList=toaMachineAttachService.queryAll(toaMachineAttach);
			
			ToaMachineRestart4M toaMachineRestartTemp=new ToaMachineRestart4M();
			BeanUtils.copyProperties(machine, toaMachineRestartTemp);
			resultMap.put("state", "success");
			resultMap.put("fileName",toaAttachList);
			resultMap.put("data", toaMachineRestartTemp);
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap; 
	}
	
}
