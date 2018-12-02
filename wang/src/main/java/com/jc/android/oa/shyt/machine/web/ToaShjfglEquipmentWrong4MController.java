package com.jc.android.oa.shyt.machine.web;

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
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.android.oa.shyt.machine.domain.ToaShjfglEquipmentWrong4M;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.machine.domain.ToaMachineAttach;
import com.jc.oa.machine.service.IToaMachineAttachService;
import com.jc.shjfgl.machine.domain.Equipment;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentWrong;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentWrongContent;
import com.jc.shjfgl.machine.service.IToaShjfglEquipmentWrongContentService;
import com.jc.shjfgl.machine.service.IToaShjfglEquipmentWrongService;
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
 * @version 故障处理过程表
*/
@Controller
@RequestMapping(value="/android/machine/toaShjfglEquipmentWrong")
public class ToaShjfglEquipmentWrong4MController extends BaseController{
	
	@Autowired 
	private IToaShjfglEquipmentWrongService toaShjfglEquipmentWrongService; 
	@Autowired 
	private IToaShjfglEquipmentWrongContentService toaShjfglEquipmentWrongContentService; 
	@Autowired
	private IToaMachineAttachService toaMachineAttachService;//附件工单表
	
	public ToaShjfglEquipmentWrong4MController(){
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
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="save",operateDescribe="对故障处理过程表进行发起操作")
	public Map<String,Object> save(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong,BindingResult result,
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
			toaShjfglEquipmentWrongService.save(toaShjfglEquipmentWrong);
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
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="update",operateDescribe="对故障处理过程表进行更新操作")
	public Map<String, Object> update(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaShjfglEquipmentWrongService.update(toaShjfglEquipmentWrong);
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
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="get",operateDescribe="对故障处理过程表进行单条查询操作")
	public ToaShjfglEquipmentWrong get(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong,HttpServletRequest request) throws Exception{
		return toaShjfglEquipmentWrongService.get(toaShjfglEquipmentWrong);
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
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="get",operateDescribe="故障处理过程表分页查询")
	public PageManager manageList(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(toaShjfglEquipmentWrong.getOrderBy())) {
			toaShjfglEquipmentWrong.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = toaShjfglEquipmentWrongService.query(toaShjfglEquipmentWrong, page);
		return page_; 
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
	@ActionLog(operateModelNm="故障处理过程表表",operateFuncNm="deleteByIds",operateDescribe="对故障处理过程表表进行删除")
	public  Map<String, Object> deleteByIds(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaShjfglEquipmentWrong.setPrimaryKeys(ids.split(","));	
		if(toaShjfglEquipmentWrongService.deleteByIds(toaShjfglEquipmentWrong) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
		}
		return resultMap;
	}
	
	/**
	 * APP-设备故障处理-保存方法
	 * @param TimeSet 	timeSet 		实体类
	 * @param Long 		equipmentId 	设备ID
	 * @param String 	contact 		客户联系人
	 * @param String 	tel 			客户联系方式
	 * @param String 	operationType 	工单类型（17.设备故障 18.网络故障）
	 * @param BindingResult result 校验结果
	 * @return Map<String,Object>  state 是否成功， errormsg错误信息
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value = "save4MByEquipmentId.action")
	@ResponseBody
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="save4MByEquipmentId",operateDescribe="对故障处理过程表进行发起操作")
	public Map<String,Object> save4MByEquipmentId(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong,Long equipmentId,String contact,String tel,BindingResult result,
			HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = validateBean(result);
		Integer flag = -1;
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
//		resultMap = validateToken(request);
//		if (resultMap.size() > 0) {
//			return resultMap;
//		}
		//工单编号
		INumber number = new Number();
		String applyNum = null;
		applyNum = number.getNumber("MachineFault", 1,2, null);
		
		applyNum = applyNum.substring(1, applyNum.length());
		String equipmentNumber=applyNum;
		if(!"false".equals(resultMap.get("success"))){
			toaShjfglEquipmentWrong.setEquipmentNumber(equipmentNumber);//工单编号
			toaShjfglEquipmentWrong.setOperationType("17");//设备报障
			Equipment equipment = new Equipment();
			equipment.setId(equipmentId);
			flag = toaShjfglEquipmentWrongService.saveByEquipment(equipment,toaShjfglEquipmentWrong);
			
		}
		if (flag == 1) {
			ToaShjfglEquipmentWrong toaShjfglEquipmentWrongTemp = new ToaShjfglEquipmentWrong();
			ToaShjfglEquipmentWrong4M toaShjfglEquipmentWrong4M = new ToaShjfglEquipmentWrong4M();
			
			toaShjfglEquipmentWrongTemp.setEquipmentNumber(equipmentNumber);
			toaShjfglEquipmentWrongTemp = toaShjfglEquipmentWrongService.get(toaShjfglEquipmentWrongTemp);
			BeanUtils.copyProperties(toaShjfglEquipmentWrongTemp, toaShjfglEquipmentWrong4M);// (源，目标)
			
			resultMap.put("state", "success");
			resultMap.put("data", toaShjfglEquipmentWrong4M.getId());//上传附件使用
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}

	/**
	* APP-设备故障处理-修改方法
	* @param TimeSet timeSet 实体类
	* @return Map<String,Object>  state 是否成功， errormsg错误信息
	* @author
	*/
	@RequestMapping(value="update4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="update4M",operateDescribe="对故障处理过程表进行更新操作")
	public Map<String, Object> update4M(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong,Long id,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaShjfglEquipmentWrongService.update(toaShjfglEquipmentWrong);
		if (flag == 1) {	
			resultMap.put("state", "success");
			resultMap.put("data", "提交成功");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}

	/**
	* APP-设备故障处理-接单  status(0-1)  申请状态 =》已接单状态
	* @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	* @param Long 	id 			工单ID
	* @param Date 	modifyDate 	上次修改时间
	* @return Map<String,Object>  state 是否成功， errormsg错误信息
	* @author wtf
	*/
	@RequestMapping(value="update4MByIdJieDan.action")
	@ResponseBody
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="update4MByIdJieDan",operateDescribe="对故障处理过程表进行更新操作")
	public Map<String, Object> update4MByIdJieDan(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong, Long id,Date modifyDate,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaShjfglEquipmentWrongService.jieDan(toaShjfglEquipmentWrong);
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
	* APP-设备故障处理-到达设备现场 status(1-2) 已接单状态=》审核通过状态
	* @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	* @param Long id 工单ID
	* @param Date 	modifyDate 	上次修改时间
	* @return Map<String,Object>  state 是否成功， errormsg错误信息
	* @author wtf
	*/
	@RequestMapping(value="update4MByIdDaoChang.action")
	@ResponseBody
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="update4MByIdDaoChang",operateDescribe="对故障处理过程表进行更新操作")
	public Map<String, Object> update4MByIdDaoChang(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong, Long id,Date modifyDate,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaShjfglEquipmentWrongService.daoChang(toaShjfglEquipmentWrong);
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
	* APP-设备故障处理-进行操作 status(2-3) 审核通过状态=》操作完成状态
	* @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	* @param Long id 工单ID
	* @param Date 	modifyDate 	上次修改时间
	* @return Map<String,Object>  state 是否成功， errormsg错误信息
	* @author wtf
	*/
	@RequestMapping(value="update4MByIdOperate.action")
	@ResponseBody
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="update4MByIdOperate",operateDescribe="对故障处理过程表进行更新操作")
	public Map<String, Object> update4MByIdOperate(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong, Long id,Date modifyDate,String realityType,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaShjfglEquipmentWrongService.operate(toaShjfglEquipmentWrong);
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "进行操作");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}
	
	/**
	* APP-设备故障处理-客户认可 status(3-4  / 3-2) 审核通过状态=》操作完成状态
	* @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	* @param Long id 工单ID
	* @param Date 	modifyDate 	上次修改时间
	* @param String 	isAgree 	客户是否认可
	* @return Map<String,Object>  state 是否成功， errormsg错误信息
	* @author wtf
	*/
	@RequestMapping(value="update4MByIdRenke.action")
	@ResponseBody
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="update4MByIdRenke",operateDescribe="对故障处理过程表进行更新操作")
	public Map<String, Object> update4MByIdRenke(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong, Long id,Date modifyDate,String isAgree,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = toaShjfglEquipmentWrongService.renke(toaShjfglEquipmentWrong);
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
	 * APP-设备故障处理-分页查询方法
	 * @deprecated 得到工程师  本部门和状态为“待接单” 的工单
	 * @param TimeSet timeSet 实体类
	 * @return Map<String,Object>  state 是否成功， errormsg错误信息
	 * @throws Exception
	 * @author
	 * @throws CustomException 
	 */
	@RequestMapping(value="manage4MList.action")
	@ResponseBody
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="manage4MList",operateDescribe="故障处理过程表分页查询")
	public Map<String, Object> manage4MList(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//默认排序
		if(StringUtil.isEmpty(toaShjfglEquipmentWrong.getOrderBy())) {
			toaShjfglEquipmentWrong.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		User user=SystemSecurityUtils.getUser();
		toaShjfglEquipmentWrong.setCreateUserDept(user.getDeptId());
		toaShjfglEquipmentWrong.setStatus(0);
		toaShjfglEquipmentWrong.setCaozgcs(user.getId().toString());
		
		List<ToaShjfglEquipmentWrong> resultList = toaShjfglEquipmentWrongService.queryApp(toaShjfglEquipmentWrong);
		
		if (resultList != null) {

			List<ToaShjfglEquipmentWrong4M> list4M =new ArrayList<ToaShjfglEquipmentWrong4M>();
			for(int i = 0;i<resultList.size();i++){
				ToaShjfglEquipmentWrong toaShjfglEquipmentWrongTemp = resultList.get(i);
				ToaShjfglEquipmentWrong4M toaShjfglEquipmentWrong4MTemp = new ToaShjfglEquipmentWrong4M();
				BeanUtils.copyProperties(toaShjfglEquipmentWrongTemp, toaShjfglEquipmentWrong4MTemp);
				list4M.add(toaShjfglEquipmentWrong4MTemp);
			}
			resultMap.put("state", "success");
			resultMap.put("data", list4M);
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap; 
	}
	
	/**
	 * APP-设备故障处理-分页查询方法(客户APP使用)
	 * @deprecated 得到本公司的工单
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return Map<String,Object>  state 是否成功， errormsg错误信息
	 * @throws Exception
	 * @author
	 * @throws CustomException 
	 */
	@RequestMapping(value="manage4MListForCustomer.action")
	@ResponseBody
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="manage4MListForCustomer",operateDescribe="故障处理过程表分页查询")
	public Map<String, Object> manage4MListForCustomer(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ToaShjfglEquipmentWrong> resultList = null;
		//默认排序
		if(StringUtil.isEmpty(toaShjfglEquipmentWrong.getOrderBy())) {
			toaShjfglEquipmentWrong.addOrderByFieldDesc("t.CREATE_DATE");
		}
		
		User user=SystemSecurityUtils.getUser();
		if(!StringUtils.isEmpty(user.getCode())){//得到公司ID
			toaShjfglEquipmentWrong.setCompanyId(Integer.parseInt(user.getCode()));
			resultList = toaShjfglEquipmentWrongService.queryAll(toaShjfglEquipmentWrong);
		}
		
		if (resultList != null) {

			List<ToaShjfglEquipmentWrong4M> list4M =new ArrayList<ToaShjfglEquipmentWrong4M>();
			for(int i = 0;i<resultList.size();i++){
				ToaShjfglEquipmentWrong toaShjfglEquipmentWrongTemp = resultList.get(i);
				ToaShjfglEquipmentWrong4M toaShjfglEquipmentWrong4MTemp = new ToaShjfglEquipmentWrong4M();
				BeanUtils.copyProperties(toaShjfglEquipmentWrongTemp, toaShjfglEquipmentWrong4MTemp);
				list4M.add(toaShjfglEquipmentWrong4MTemp);
			}
			resultMap.put("state", "success");
			resultMap.put("data", list4M);
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap; 
	}
	
	/**
	 * APP-设备故障处理-获取单条记录方法
	 * @param TimeSet 	timeSet 实体类
	 * @param Long 		id 		故障工单ID
	 * @return Map<String,Object>  state 是否成功， errormsg错误信息
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="get4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="get4M",operateDescribe="对故障处理过程表进行单条查询操作")
	public Map<String, Object> get4M(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong,Long id,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ToaShjfglEquipmentWrong toaShjfglEquipmentWrongTemp = new ToaShjfglEquipmentWrong();
		toaShjfglEquipmentWrongTemp.setId(id);
		toaShjfglEquipmentWrongTemp = toaShjfglEquipmentWrongService.get(toaShjfglEquipmentWrongTemp);
		if (toaShjfglEquipmentWrongTemp != null) {
			//附件工单表
			ToaMachineAttach toaMachineAttach=new ToaMachineAttach();
			toaMachineAttach.setBusinessId(id.intValue());
			toaMachineAttach.setBusinessSource(toaShjfglEquipmentWrongTemp.getOperationType());
			List<ToaMachineAttach> toaAttachList=toaMachineAttachService.queryAll(toaMachineAttach);
			
			ToaShjfglEquipmentWrong4M toaShjfglEquipmentWrong4M = new ToaShjfglEquipmentWrong4M();
			BeanUtils.copyProperties(toaShjfglEquipmentWrongTemp, toaShjfglEquipmentWrong4M);
			
			resultMap.put("state", "success");
			resultMap.put("fileName",toaAttachList);
			resultMap.put("data", toaShjfglEquipmentWrong4M);
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}

}
