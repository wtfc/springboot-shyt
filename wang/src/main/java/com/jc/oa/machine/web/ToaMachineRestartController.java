package com.jc.oa.machine.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.shjfgl.machine.domain.Equipment;
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
import com.jc.oa.machine.domain.ToaMachineExchange;
import com.jc.oa.machine.domain.ToaMachineRestart;
import com.jc.oa.machine.service.IToaMachineExchangeService;
import com.jc.oa.machine.service.IToaMachineRestartService;
/**
 * @author mrb
 * @version 重启操作
*/
@Controller
@RequestMapping(value="/machine/toaMachineRestart")
public class ToaMachineRestartController extends BaseController{
	
	@Autowired 
	private IToaMachineRestartService toaMachineRestartService; 
	@Autowired
	private IEquipmentService equipmentService;
	@Autowired
	private IToaMachineExchangeService toaMachineExchangeService;
	
	public ToaMachineRestartController(){
	}
	
	/**
	 * 保存方法
	 * @param TimeSet timeSet 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="save",operateDescribe="对重启操作进行发起操作")
	public Map<String,Object> save(ToaMachineRestart toaMachineRestart,BindingResult result,
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
			
			Integer flag=toaMachineRestartService.saveRestart(toaMachineRestart);
			if(flag==1){
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
				//resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
			}
		}
		return resultMap;
	}

	/**
	* 修改方法(机房   接单)
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="updateRoom.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="updateRoom",operateDescribe="对重启操作进行更新操作")
	public Map<String, Object> updateRoom(ToaMachineRestart toaMachineRestart, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaMachineRestartService.updateRoom(toaMachineRestart);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}else{
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_004"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/**
	* 修改方法(机房  扫描)
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="updateRoomScan.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="updateRoom",operateDescribe="对重启操作进行更新操作")
	public Map<String, Object> updateRoomScan(ToaMachineRestart toaMachineRestart, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaMachineRestartService.updateRoomScan(toaMachineRestart);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}else{
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_004"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/**
	* 修改方法(机房  2-3)
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="updateRoomHandle.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="updateRoom",operateDescribe="对重启操作进行更新操作")
	public Map<String, Object> updateRoomHandle(ToaMachineRestart toaMachineRestart, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaMachineRestartService.updateRoomHandle(toaMachineRestart);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}else{
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_004"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/**
	* 修改方法(机房  2-4)
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="updateRoomShow.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="updateRoom",operateDescribe="对重启操作进行更新操作")
	public Map<String, Object> updateRoomShow(ToaMachineRestart toaMachineRestart, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaMachineRestartService.updateRoomShow(toaMachineRestart);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}else{
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_004"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/**
	* 修改方法(机房  3-4)
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="updateRoomDirect.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="updateRoom",operateDescribe="对重启操作进行更新操作")
	public Map<String, Object> updateRoomDirect(ToaMachineRestart toaMachineRestart, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaMachineRestartService.updateRoomDirect(toaMachineRestart);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}else{
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_004"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/**
	* 修改方法(机房  4-7)
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="updateRoomReveal.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="updateRoom",operateDescribe="对重启操作进行更新操作")
	public Map<String, Object> updateRoomReveal(ToaMachineRestart toaMachineRestart, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaMachineRestartService.updateRoomReveal(toaMachineRestart);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}else{
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_004"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/**
	* 修改方法(机房  4-5)
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="updateRoomWrong.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="updateRoom",operateDescribe="对重启操作进行更新操作")
	public Map<String, Object> updateRoomWrong(ToaMachineRestart toaMachineRestart, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaMachineRestartService.updateRoomWrong(toaMachineRestart);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/**
	* 修改方法(机房  5-6)
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="updateRoomSeed.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="updateRoom",operateDescribe="对重启操作进行更新操作")
	public Map<String, Object> updateRoomSeed(ToaMachineRestart toaMachineRestart, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = toaMachineRestartService.updateRoomSeed(toaMachineRestart);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/**
	* 修改方法(机房  6-7)
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="updateRoomAssist.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="updateRoom",operateDescribe="对重启操作进行更新操作")
	public Map<String, Object> updateRoomAssist(ToaMachineRestart toaMachineRestart, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaMachineRestartService.updateRoomAssist(toaMachineRestart);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	
	
	
	/**
	* 修改方法(机房主管)
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="updateEngine.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="updateEngine",operateDescribe="对重启操作进行更新操作")
	public Map<String, Object> updateEngine(ToaMachineRestart toaMachineRestart, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = toaMachineRestartService.updateEngine(toaMachineRestart);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/**
	* 修改方法(区域主管)
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="updateDirector.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="updateDirector",operateDescribe="对重启操作进行更新操作")
	public Map<String, Object> updateDirector(ToaMachineRestart toaMachineRestart, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		Long id=toaMachineRestart.getId();
		ToaMachineRestart toaRestart=new ToaMachineRestart();
		toaRestart.setId(id);
		ToaMachineRestart toaMachine=toaMachineRestartService.get(toaRestart);
		if(toaMachineRestart.getRegionExamine().equals("1")){
			toaMachineRestart.setStatus(3);
			if(toaMachine.getCreateUser().equals(user.getId())){
				toaMachineRestart.setStatus(4);
			}
		}else{
			toaMachineRestart.setStatus(2);
		}
		Integer flag = toaMachineRestartService.update(toaMachineRestart);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/**
	* 修改方法(呼叫中心)
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="updateShout.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="updateShout",operateDescribe="对重启操作进行更新操作")
	public Map<String, Object> updateShout(ToaMachineRestart toaMachineRestart, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		Long id=toaMachineRestart.getId();
		ToaMachineRestart toaRestart=new ToaMachineRestart();
		toaRestart.setId(id);
		ToaMachineRestart toaMachine=toaMachineRestartService.get(toaRestart);
		if(toaMachine.getCreateUser().equals(user.getId())){
			toaMachineRestart.setStatus(4);
		}
		Integer flag = toaMachineRestartService.update(toaMachineRestart);
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
	@ActionLog(operateModelNm="重启操作",operateFuncNm="get",operateDescribe="重启操作分页查询")
	public PageManager manageList(ToaMachineRestart toaMachineRestart,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		
		//默认排序
		if(StringUtil.isEmpty(toaMachineRestart.getOrderBy())) {
			toaMachineRestart.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}

		User user=SystemSecurityUtils.getUser();
		toaMachineRestart.setCreateUserDept(user.getDeptId());
		toaMachineRestart.setStatus(0);
		toaMachineRestart.setCaozgcs(user.getId().toString());
		PageManager page_ = toaMachineRestartService.queryForPermission(toaMachineRestart, page);
		return page_; 
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
	@RequestMapping(value="manageDirectors.action")
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="manageDirector",operateDescribe="重启操作分页查询")
	public PageManager manageDirectors(ToaMachineRestart toaMachineRestart,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		
		//默认排序
		if(StringUtil.isEmpty(toaMachineRestart.getOrderBy())) {
			toaMachineRestart.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		
		PageManager page_ = toaMachineRestartService.query(toaMachineRestart, page);
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
		
		return "machine/toaMachineRestart/toaMachineRestart";
	}
	
	/**
	* 跳转方法(机房)
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manageRoom.action")
	@ActionLog(operateModelNm="重启操作",operateFuncNm="manageRoom",operateDescribe="对重启操作进行跳转操作")
	public String manageRoom(Model model,HttpServletRequest request) throws Exception{
		String userName=SystemSecurityUtils.getUser().getDisplayName();
		model.addAttribute("userName", userName);
		return "machine/toaMachineRestart/toaMachineRestartRoom";
	}
	
	/**
	* 跳转方法(机房主管)
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manageEngine.action")
	@ActionLog(operateModelNm="重启操作",operateFuncNm="manageEngine",operateDescribe="对重启操作进行跳转操作")
	public String manageEngine(Model model,HttpServletRequest request) throws Exception{
		
		return "machine/toaMachineRestart/toaMachineRestartEngine";
	}
	
	/**
	* 跳转方法(区域主管)
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manageDirector.action")
	@ActionLog(operateModelNm="重启操作",operateFuncNm="manageDirector",operateDescribe="对重启操作进行跳转操作")
	public String manageDirector(Model model,HttpServletRequest request) throws Exception{
		User user= SystemSecurityUtils.getUser();
		Long deptId=user.getDeptId();
		model.addAttribute("deptId", deptId);
		return "machine/toaMachineRestart/toaMachineRestartDirector";
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
	@ActionLog(operateModelNm="重启操作表",operateFuncNm="deleteByIds",operateDescribe="对重启操作表进行删除")
	public  Map<String, Object> deleteByIds(ToaMachineRestart toaMachineRestart,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaMachineRestart.setPrimaryKeys(ids.split(","));	
		if(toaMachineRestartService.deleteByIds(toaMachineRestart) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
		}
		return resultMap;
	}

	/**
	 * @description 新增(呼叫中心)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadForm.action")
	@ActionLog(operateModelNm="重启操作表",operateFuncNm="loadForm",operateDescribe="对重启操作表跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		//String id= request.getParameter("id");
		//String name=SystemSecurityUtils.getUser().getDisplayName();
		
		/*if(!StringUtil.isEmpty(id)){
			ToaMachineExchange toaMachineExchange=new ToaMachineExchange();
			toaMachineExchange.setRestartId(id);
			toaMachineExchange.setServiceName(0);
			List<ToaMachineExchange> exchangeList=toaMachineExchangeService.queryAll(toaMachineExchange);
			model.addAttribute("Id", id);
			model.addAttribute("idd",id);
			model.addAttribute("exchangeList", exchangeList);
			model.addAttribute("talkName", name);
		}else{}*/
			//编号
			INumber number = new Number();
			String applyNum = number.getNumber("MachineRestart", 1,2, null);
			applyNum = applyNum.substring(1, applyNum.length());
			model.addAttribute("applyNum",applyNum);
		return "machine/toaMachineRestart/toaMachineRestartForm";
	}
	
	/**
	 * @description 审批(呼叫中心)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadShout.action")
	@ActionLog(operateModelNm="重启操作表",operateFuncNm="loadShout",operateDescribe="对重启操作表跳转")
	public String loadShout(Model model,HttpServletRequest request) throws Exception{
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
		return "machine/toaMachineRestart/module/toaMachineRestartShoutForm";
	}
	
	/**
	 * @description 修改方法跳转页面(机房   接单 0-1)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadRoom.action")
	@ActionLog(operateModelNm="重启操作表",operateFuncNm="loadRoom",operateDescribe="对重启操作表跳转")
	public String loadRoom(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		String name=SystemSecurityUtils.getUser().getDisplayName();
		model.addAttribute("userName", name);
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
		return "machine/toaMachineRestart/module/toaMachineRestartRoomForm";
	}
	
	/**
	 * @description 修改方法跳转页面(机房   扫描 1-2)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadRoomScan.action")
	@ActionLog(operateModelNm="重启操作表",operateFuncNm="loadRoom",operateDescribe="对重启操作表跳转")
	public String loadRoomScan(Model model,HttpServletRequest request) throws Exception{
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
		return "machine/toaMachineRestart/module/toaMachineRestartRoomScanForm";
	}
	
	/**
	 * @description 修改方法跳转页面(机房   系统判断跳转 2-3)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadRoomJudge.action")
	@ActionLog(operateModelNm="重启操作表",operateFuncNm="loadRoom",operateDescribe="对重启操作表跳转")
	public String loadRoomJudge(Model model,HttpServletRequest request) throws Exception{
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
		ToaMachineRestart toaMachineRestart=new ToaMachineRestart();
		toaMachineRestart.setId(Long.valueOf(id));
		//存放（通过id找到机房,客户名称,IP,sn）
		ToaMachineRestart toaRestart=toaMachineRestartService.get(toaMachineRestart);
		toaMachineRestart.setId(null);
		toaMachineRestart.setCompanyName(toaRestart.getCompanyName());
		toaMachineRestart.setMachina(toaRestart.getMachina());
		toaMachineRestart.setSn(toaRestart.getSn());
		//是否存在重启设备
		//List<ToaMachineRestart> toaMachineRestartList=toaMachineRestartService.queryAll(toaMachineRestart);
		//if(toaMachineRestartList.size()==0){
			//直接操作(状态2-3)
			return "machine/toaMachineRestart/module/toaMachineRestartRoomHandleForm";
		/*}else{
			int count=0;
			for(int i=0;i<toaMachineRestartList.size();i++){
				ToaMachineRestart toaRestartList=toaMachineRestartList.get(i);
				String extStr=toaRestartList.getFirstRestart();
				count+=Integer.valueOf(extStr);
			}
			if(count>0){
				//显示器(状态2-4)
				return "machine/toaMachineRestart/module/toaMachineRestartRoomShowForm";
			}else{
				//直接操作(状态2-3)
				return "machine/toaMachineRestart/module/toaMachineRestartRoomHandleForm";
			}
		}*/
	}
	
	/**
	 * @description 修改方法跳转页面(机房   3-4)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadRoomDirect.action")
	@ActionLog(operateModelNm="重启操作表",operateFuncNm="loadRoom",operateDescribe="对重启操作表跳转")
	public String loadRoomDirect(Model model,HttpServletRequest request) throws Exception{
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
		ToaMachineRestart toaMachineRestart=new ToaMachineRestart();
		toaMachineRestart.setId(Long.valueOf(id));
		//存放（通过id找到机房,客户名称,IP,sn）
		ToaMachineRestart toaRestart=toaMachineRestartService.get(toaMachineRestart);
		toaMachineRestart.setId(null);
		//使用公司ID  add-wangtf
		toaMachineRestart.setCompanyId(toaRestart.getCompanyId());
		
		//toaMachineRestart.setCompanyName(toaRestart.getCompanyName());
		toaMachineRestart.setMachina(toaRestart.getMachina());
		
		//toaMachineRestart.setSn(toaRestart.getSn());
		//是否存在重启设备
		List<ToaMachineRestart> toaMachineRestartList=toaMachineRestartService.queryAll(toaMachineRestart);
		if(toaMachineRestartList.size()==0){
			//直接操作(状态3-4)
			return "machine/toaMachineRestart/module/toaMachineRestartRoomDirectForm";
		}else{
			int count=0;
			for(int i=0;i<toaMachineRestartList.size();i++){
				ToaMachineRestart toaRestartList=toaMachineRestartList.get(i);
				String extStr=toaRestartList.getFirstRestart();
				count+=Integer.valueOf(extStr);
			}
			if(count>0){
				//显示器(状态3-4)
				return "machine/toaMachineRestart/module/toaMachineRestartRoomShowForm";
			}else{
				//直接操作(状态3-4)
				return "machine/toaMachineRestart/module/toaMachineRestartRoomDirectForm";
			}
		}		
		//return "machine/toaMachineRestart/module/toaMachineRestartRoomDirectForm";
	}
	
	/**
	 * @description 修改方法跳转页面(机房   4-7 4-5)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadRoomReveal.action")
	@ActionLog(operateModelNm="重启操作表",operateFuncNm="loadRoom",operateDescribe="对重启操作表跳转")
	public String loadRoomReveal(Model model,HttpServletRequest request) throws Exception{
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
		ToaMachineRestart toaMachineRestart=new ToaMachineRestart();
		toaMachineRestart.setId(Long.valueOf(id));
		ToaMachineRestart toaRestart=toaMachineRestartService.get(toaMachineRestart);
		if(toaRestart.getIsWrong().equals("0")){
			//(状态4-7)
			return  "machine/toaMachineRestart/module/toaMachineRestartRoomRevealForm";
		}else{
			//(状态4-5)
			return "machine/toaMachineRestart/module/toaMachineRestartRoomWrongForm";
		}
	}
	
	/**
	 * @description 修改方法跳转页面(机房   5-6)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadRoomSeed.action")
	@ActionLog(operateModelNm="重启操作表",operateFuncNm="loadRoom",operateDescribe="对重启操作表跳转")
	public String loadRoomSeed(Model model,HttpServletRequest request) throws Exception{
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
		return "machine/toaMachineRestart/module/toaMachineRestartRoomSeedForm";
	}
	
	/**
	 * @description 修改方法跳转页面(机房  6-7)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadRoomAssist.action")
	@ActionLog(operateModelNm="重启操作表",operateFuncNm="loadRoom",operateDescribe="对重启操作表跳转")
	public String loadRoomAssist(Model model,HttpServletRequest request) throws Exception{
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
		return "machine/toaMachineRestart/module/toaMachineRestartRoomAssistForm";
	}
	
	
	
	
	/**
	 * @description 修改方法跳转页面(机房主管)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadEngine.action")
	@ActionLog(operateModelNm="重启操作表",operateFuncNm="loadEngine",operateDescribe="对重启操作表跳转")
	public String loadEngine(Model model,HttpServletRequest request) throws Exception{
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
		return "machine/toaMachineRestart/module/toaMachineRestartEngineForm";
	}
	
	/**
	 * @description 修改方法跳转页面(区域主管)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadDirector.action")
	@ActionLog(operateModelNm="重启操作表",operateFuncNm="loadDirector",operateDescribe="对重启操作表跳转")
	public String loadDirector(Model model,HttpServletRequest request) throws Exception{
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
		return "machine/toaMachineRestart/module/toaMachineRestartDirectorForm";
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
		return "machine/toaMachineRestart/toaMachineRestartSee";
	}
}
