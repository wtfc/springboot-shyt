package com.jc.android.oa.shyt.network.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.jc.android.oa.shyt.machine.domain.ToaShjfglEquipmentWrong4M;
import com.jc.android.oa.shyt.network.domain.ToaNetworkWrong4M;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.machine.domain.ToaMachineAttach;
import com.jc.oa.machine.service.IToaMachineAttachService;
import com.jc.oa.network.domain.ToaNetworkWrong;
import com.jc.oa.network.service.IToaNetworkWrongService;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentWrong;
import com.jc.shjfgl.machine.service.IToaShjfglEquipmentWrongContentService;
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
 * @version 网络故障处理表
*/
@Controller
@RequestMapping(value="/android/machine/toaNetworkWrong")
public class ToaNetworkWrong4MController extends BaseController{
	
	@Autowired 
	private IToaNetworkWrongService toaNetworkWrongService;
	@Autowired 
	private IToaShjfglEquipmentWrongContentService toaShjfglEquipmentWrongContentService;  
	@Autowired
	private IToaMachineAttachService toaMachineAttachService;//附件工单表
	
	public ToaNetworkWrong4MController(){
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
	@ActionLog(operateModelNm="网络故障处理表",operateFuncNm="save",operateDescribe="对网络故障处理表进行发起操作")
	public Map<String,Object> save(ToaNetworkWrong toaNetworkWrong,BindingResult result,
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
			toaNetworkWrongService.save(toaNetworkWrong);
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
	@ActionLog(operateModelNm="网络故障处理表",operateFuncNm="update",operateDescribe="对网络故障处理表进行更新操作")
	public Map<String, Object> update(ToaNetworkWrong toaNetworkWrong, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaNetworkWrongService.update(toaNetworkWrong);
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
	@ActionLog(operateModelNm="网络故障处理表",operateFuncNm="get",operateDescribe="对网络故障处理表进行单条查询操作")
	public ToaNetworkWrong get(ToaNetworkWrong toaNetworkWrong,HttpServletRequest request) throws Exception{
		return toaNetworkWrongService.get(toaNetworkWrong);
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
	@ActionLog(operateModelNm="网络故障处理表",operateFuncNm="get",operateDescribe="网络故障处理表分页查询")
	public PageManager manageList(ToaNetworkWrong toaNetworkWrong,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(toaNetworkWrong.getOrderBy())) {
			toaNetworkWrong.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = toaNetworkWrongService.query(toaNetworkWrong, page);
		return page_; 
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="网络故障处理表",operateFuncNm="manage",operateDescribe="对网络故障处理表进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "machine/toaNetworkWrong/toaNetworkWrong";
	}
	
	/**
	 * @description 新增与修改方法
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadForm.action")
	@ActionLog(operateModelNm="网络故障处理表表",operateFuncNm="loadForm",operateDescribe="对网络故障处理表表跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		//String id= request.getParameter("id");
		//if(!StringUtil.isEmpty(id)){
		//	model.addAttribute("Id", id);
		//}
		return "machine/toaNetworkWrong/module/toaNetworkWrongModule";
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
	@ActionLog(operateModelNm="网络故障处理表表",operateFuncNm="deleteByIds",operateDescribe="对网络故障处理表表进行删除")
	public  Map<String, Object> deleteByIds(ToaNetworkWrong toaNetworkWrong,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaNetworkWrong.setPrimaryKeys(ids.split(","));	
		if(toaNetworkWrongService.deleteByIds(toaNetworkWrong) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
		}
		return resultMap;
	}
	
	/**
	 * APP-网络故障-保存方法
	 * @param TimeSet timeSet 实体类
	 * @param BindingResult result 校验结果
	 * @return Map<String,Object>  state 是否成功， errormsg错误信息
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value = "save4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="网络故障处理表",operateFuncNm="save4M",operateDescribe="对网络故障处理表进行发起操作")
	public Map<String,Object> save4M(ToaNetworkWrong toaNetworkWrong,BindingResult result,
			HttpServletRequest request) throws Exception{
		Integer flag = -1;
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		 //验证token
//		resultMap = validateToken(request);
//		if (resultMap.size() > 0) {
//			return resultMap;
//		}
		//编号
		INumber number = new Number();
		String applyNum = number.getNumber("MachineNetworkfault", 1,2, null);
		applyNum = applyNum.substring(1, applyNum.length());
		
		String equipmentNumber=applyNum;

		
		toaNetworkWrong.setEquipmentNumber(equipmentNumber);//工单编号
		
		if(!"false".equals(resultMap.get("success"))){
			flag = toaNetworkWrongService.save4M(toaNetworkWrong);
		}
		
		if(flag !=null && flag==1){

			ToaNetworkWrong toaNetworkWrongTemp = new ToaNetworkWrong();
			toaNetworkWrongTemp.setEquipmentNumber(equipmentNumber);
			
			toaNetworkWrongTemp = toaNetworkWrongService.get(toaNetworkWrongTemp);
			if(toaNetworkWrongTemp!=null){

				ToaNetworkWrong4M toaNetworkWrong4M = new ToaNetworkWrong4M();
				BeanUtils.copyProperties(toaNetworkWrongTemp, toaNetworkWrong4M);// (源，目标)
				// 成功状态位和数据
				resultMap.put("state", "success");
				resultMap.put("data", toaNetworkWrong4M.getId());
			}
		}else{
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}
	
	/**
	* APP-网络故障处理-修改方法
	* @param TimeSet timeSet 实体类
	* @return Map<String,Object>  state 是否成功， errormsg错误信息
	* @author
	*/
	@RequestMapping(value="update4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="网络故障处理表",operateFuncNm="update4M",operateDescribe="对网络故障处理表进行更新操作")
	public Map<String, Object> update4M(ToaNetworkWrong toaNetworkWrong, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaNetworkWrongService.update(toaNetworkWrong);
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
	* APP-网络故障处理-接单  status(0-1)  申请状态 =》已接单状态
	* @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	* @param Long 	id 			工单ID
	* @param Date 	modifyDate 	上次修改时间
	* @return Map<String,Object>  state 是否成功， errormsg错误信息
	* @author wtf
	*/
	@RequestMapping(value="update4MByIdJieDan.action")
	@ResponseBody
	@ActionLog(operateModelNm="网络故障处理表",operateFuncNm="update4MByIdJieDan",operateDescribe="对网络故障处理表进行更新操作")
	public Map<String, Object> update4MByIdJieDan(ToaNetworkWrong toaNetworkWrong, Long id,Date modifyDate,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaNetworkWrongService.jieDan(toaNetworkWrong);
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
	* APP-网络故障处理-到达设备现场 status(1-2) 已接单状态=》审核通过状态
	* @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	* @param Long id 工单ID
	* @param Date 	modifyDate 	上次修改时间
	* @return Map<String,Object>  state 是否成功， errormsg错误信息
	* @author wtf
	*/
	@RequestMapping(value="update4MByIdDaoChang.action")
	@ResponseBody
	@ActionLog(operateModelNm="网络故障处理表",operateFuncNm="update4MByIdDaoChang",operateDescribe="对网络故障处理表进行更新操作")
	public Map<String, Object> update4MByIdDaoChang(ToaNetworkWrong toaNetworkWrong, Long id,Date modifyDate,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaNetworkWrongService.daoChang(toaNetworkWrong);
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
	* APP-网络故障处理-进行操作 status(2-3) 审核通过状态=》操作完成状态
	* @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	* @param Long id 工单ID
	* @param Date 	modifyDate 	上次修改时间
	* @return Map<String,Object>  state 是否成功， errormsg错误信息
	* @author wtf
	*/
	@RequestMapping(value="update4MByIdOperate.action")
	@ResponseBody
	@ActionLog(operateModelNm="网络故障处理表",operateFuncNm="update4MByIdOperate",operateDescribe="对网络故障处理表进行更新操作")
	public Map<String, Object> update4MByIdOperate(ToaNetworkWrong toaNetworkWrong, Long id,Date modifyDate,String realityType,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaNetworkWrongService.operate(toaNetworkWrong);
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
	* APP-网络故障处理-客户认可 status(3-4  / 3-2) 审核通过状态=》操作完成状态
	* @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	* @param Long id 工单ID
	* @param Date 	modifyDate 	上次修改时间
	* @param String 	isAgree 	客户是否认可
	* @return Map<String,Object>  state 是否成功， errormsg错误信息
	* @author wtf
	*/
	@RequestMapping(value="update4MByIdRenke.action")
	@ResponseBody
	@ActionLog(operateModelNm="网络故障处理表",operateFuncNm="update4MByIdRenke",operateDescribe="对网络故障处理表进行更新操作")
	public Map<String, Object> update4MByIdRenke(ToaNetworkWrong toaNetworkWrong, Long id,Date modifyDate,String isAgree,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		//toaNetworkWrong.setIsAgree(isAgree);
		Integer flag = toaNetworkWrongService.renke(toaNetworkWrong);
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
	* APP-网络故障处理-客户评价 status(4-5) 操作完成状态=》用户已评价状态
	* @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	* @param Long id 工单ID
	* @param Date 	modifyDate 	上次修改时间
	* @param String 	score 	客户评价
	* @param String 	remark 	备注
	* @return Map<String,Object>  state 是否成功， errormsg错误信息
	* @author wtf
	*/
	@RequestMapping(value="update4MByIdPingJia.action")
	@ResponseBody
	@ActionLog(operateModelNm="网络故障处理表",operateFuncNm="update4MByIdPingJia",operateDescribe="对网络故障处理表进行更新操作")
	public Map<String, Object> update4MByIdPingJia(ToaNetworkWrong toaNetworkWrong, Long id,Date modifyDate,String score,String remark,BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}

		Integer flag = toaNetworkWrongService.pingJia(toaNetworkWrong);
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "评价成功");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}
	
	/**
	 * APP-网络故障处理-分页查询方法
	 * @deprecated 得到工程师  本部门和状态为“待接单” 的工单
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return Map<String,Object>  state 是否成功， errormsg错误信息
	 * @throws Exception
	 * @author
	 * @throws CustomException 
	 */
	@RequestMapping(value="manage4MList.action")
	@ResponseBody
	@ActionLog(operateModelNm="网络故障处理表",operateFuncNm="manage4MList",operateDescribe="网络故障处理表分页查询")
	public Map<String, Object> manage4MList(ToaNetworkWrong toaNetworkWrong,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//默认排序
		if(StringUtil.isEmpty(toaNetworkWrong.getOrderBy())) {
			toaNetworkWrong.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		User user=SystemSecurityUtils.getUser();
		//toaNetworkWrong.setCreateUserDept(user.getDeptId());
		toaNetworkWrong.setStatus(0);
		toaNetworkWrong.setCaozgcs(user.getId().toString());
		
		List<ToaNetworkWrong> resultList = toaNetworkWrongService.queryApp(toaNetworkWrong);
		
		if (resultList != null) {

			List<ToaNetworkWrong4M> list4M =new ArrayList<ToaNetworkWrong4M>();
			for(int i = 0;i<resultList.size();i++){
				ToaNetworkWrong toaNetworkWrongTemp = resultList.get(i);
				ToaNetworkWrong4M toaNetworkWrong4MTemp = new ToaNetworkWrong4M();
				BeanUtils.copyProperties(toaNetworkWrongTemp, toaNetworkWrong4MTemp);
				list4M.add(toaNetworkWrong4MTemp);
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
	 * APP-网络故障处理-分页查询方法(客户APP使用)
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
	@ActionLog(operateModelNm="网络故障处理表",operateFuncNm="manage4MListForCustomer",operateDescribe="网络故障处理表分页查询")
	public Map<String, Object> manage4MListForCustomer(ToaNetworkWrong toaNetworkWrong,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ToaNetworkWrong> resultList = null;
		//默认排序
		if(StringUtil.isEmpty(toaNetworkWrong.getOrderBy())) {
			toaNetworkWrong.addOrderByFieldDesc("t.CREATE_DATE");
		}
		
		User user=SystemSecurityUtils.getUser();
		if(!StringUtils.isEmpty(user.getCode())){//得到公司ID
			toaNetworkWrong.setCompanyId(Integer.parseInt(user.getCode()));
			resultList = toaNetworkWrongService.queryAll(toaNetworkWrong);
		}
		
		if (resultList != null) {

			List<ToaNetworkWrong4M> list4M =new ArrayList<ToaNetworkWrong4M>();
			for(int i = 0;i<resultList.size();i++){
				ToaNetworkWrong toaNetworkWrongTemp = resultList.get(i);
				ToaNetworkWrong4M toaNetworkWrong4MTemp = new ToaNetworkWrong4M();
				BeanUtils.copyProperties(toaNetworkWrongTemp, toaNetworkWrong4MTemp);
				list4M.add(toaNetworkWrong4MTemp);
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
	 * APP-网络故障处理-获取单条记录方法
	 * @param TimeSet 	timeSet 实体类
	 * @param Long 		id 		故障工单ID
	 * @return Map<String,Object>  state 是否成功， errormsg错误信息
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="get4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="网络故障处理表",operateFuncNm="get4M",operateDescribe="对故障处理表进行单条查询操作")
	public Map<String, Object> get4M(ToaNetworkWrong toaNetworkWrong,Long id,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ToaNetworkWrong toaNetworkWrongTemp = new ToaNetworkWrong();
		toaNetworkWrongTemp.setId(id);
		toaNetworkWrongTemp = toaNetworkWrongService.get(toaNetworkWrong);
		if (toaNetworkWrongTemp != null) {
			//附件工单表
			ToaMachineAttach toaMachineAttach=new ToaMachineAttach();
			toaMachineAttach.setBusinessId(id.intValue());
			toaMachineAttach.setBusinessSource(toaNetworkWrongTemp.getOperationType());
			List<ToaMachineAttach> toaAttachList=toaMachineAttachService.queryAll(toaMachineAttach);
			
			ToaNetworkWrong4M toaNetworkWrongTemp4M = new ToaNetworkWrong4M();
			BeanUtils.copyProperties(toaNetworkWrongTemp, toaNetworkWrongTemp4M);
			
			resultMap.put("state", "success");
			resultMap.put("fileName",toaAttachList);
			resultMap.put("data", toaNetworkWrongTemp4M);
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}

}
