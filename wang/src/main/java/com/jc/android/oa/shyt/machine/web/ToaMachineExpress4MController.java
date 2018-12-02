package com.jc.android.oa.shyt.machine.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.machine.domain.ToaMachineAttach;
import com.jc.oa.machine.domain.ToaMachineExpress;
import com.jc.oa.machine.service.IToaMachineAttachService;
import com.jc.oa.machine.service.IToaMachineExpressService;
import com.jc.system.CustomException;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;


@Controller
@RequestMapping(value="/android/machine/express")
public class ToaMachineExpress4MController extends BaseController{
	
    @Autowired
    private IToaMachineExpressService toaMachineExpressService;
    @Autowired
    private IToaMachineAttachService toaMachineAttachService;
    
	public ToaMachineExpress4MController(){};
	
	/**
	 * APP代收发快递-保存方法
	 * @param TimeSet timeSet 实体类
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  
	 */
	@RequestMapping(value = "save4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="代收发快递表",operateFuncNm="save",operateDescribe="对代收发快递表表进行发起操作")
	public Map<String,Object> save4M(ToaMachineExpress toaMachineExpress,BindingResult result,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		Integer flag=toaMachineExpressService.saveExpress(toaMachineExpress);
		if(flag==1){
			resultMap.put("state", "success");
			resultMap.put("data", MessageUtils.getMessage("JC_SYS_001"));
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_002"));
		}
		return resultMap;
	}
	
	/**
	 * APP代收发快递-接单方法(状态0-1)
	 * @param TimeSet timeSet 实体类
	 * @param Long id 快递id
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 */
	@RequestMapping(value = "updateExpress4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="代收发快递表",operateFuncNm="update",operateDescribe="对代收发快递表表进行发起操作")
	public Map<String,Object> updateExpress4M(ToaMachineExpress toaMachineExpress,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ToaMachineExpress toaExpress=new ToaMachineExpress();
		toaExpress.setId(toaMachineExpress.getId());
		toaMachineExpress=toaMachineExpressService.get(toaExpress);
		if(toaMachineExpress!=null){
			Integer flag=toaMachineExpressService.updateExpress(toaMachineExpress);
			if (flag == 1) {
				resultMap.put("state", "success");
				resultMap.put("data", "接单成功");
			}else{
				resultMap.put("state", "failure");
				resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
				resultMap.put("data", "接单失败");
			}
		}else{
			resultMap.put("state", "failure");
			resultMap.put("data", "没有此快递");
		}
		return resultMap;
	}
	
	/**
	 * APP代收发快递-完成方法(状态1-2)
	 * @param TimeSet timeSet 实体类
	 * @param Long id 快递id
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 */
	@RequestMapping(value = "updateComplete4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="代收发快递表",operateFuncNm="update",operateDescribe="对代收发快递表表进行发起操作")
	public Map<String,Object> updateComplete4M(ToaMachineExpress toaMachineExpress,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Integer flag=toaMachineExpressService.updateComplete(toaMachineExpress);
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
	 * APP代收发快递-拒收方法(状态1-3)
	 * @param TimeSet timeSet 实体类
	 * @param Long id 快递id
	 * @param String checkAccepttance 验收
	* @param String  signReceiver 签收
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 */
	@RequestMapping(value = "updateRejection4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="代收发快递表",operateFuncNm="update",operateDescribe="对代收发快递表表进行发起操作")
	public Map<String,Object> updateRejection4M(ToaMachineExpress toaMachineExpress,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Integer flag=toaMachineExpressService.updateRejection(toaMachineExpress);
		if (flag == 1) {
			resultMap.put("state", "success");
			resultMap.put("data", "拒绝签收");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
			resultMap.put("data", "签收失败");
		}
		return resultMap;
	}
	
	/**
	 * APP代收发快递-分页查询方法
	 * @param ToaMachineExpress toaMachineExpress 实体类
	 * @param HttpServletRequest request
	 * @throws CustomException
	 * @return Map<String,Object> 查询结果
	 */
	@RequestMapping(value="manageList4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单查询-分页查询方法",operateFuncNm="manageList4M",operateDescribe="机房工单查询-分页查询方法手机端接口")
	public Map<String,Object> manageList4M(ToaMachineExpress toaMachineExpress,PageManager page,HttpServletRequest request) throws CustomException{
		
		Map<String,Object> resultMap=new HashMap<String,Object>();
		//默认排序
		if(StringUtil.isEmpty(toaMachineExpress.getOrderBy())) {
			toaMachineExpress.addOrderByFieldDesc("t.CREATE_DATE");
		}
		User user=SystemSecurityUtils.getUser();
		toaMachineExpress.setCreateUserDept(user.getDeptId());
		toaMachineExpress.setStatus(0);
		toaMachineExpress.setCaozgcs(user.getId().toString());
		List<ToaMachineExpress> expressList=toaMachineExpressService.quertExpress(toaMachineExpress);
		if(expressList != null){
			resultMap.put("state", "success");
			resultMap.put("data", expressList);
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap; 
	}
	
	/**
	 * APP代收发快递-分页查询方法(客户查询接口)
	 * @param ToaMachineExpress toaMachineExpress 实体类
	 * @param HttpServletRequest request
	 * @throws CustomException
	 * @return Map<String,Object> 查询结果
	 */
	@RequestMapping(value="manageCustomerList4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单查询-分页查询方法",operateFuncNm="manageList4M",operateDescribe="机房工单查询-分页查询方法手机端接口")
	public Map<String,Object> manageCustomerList4M(ToaMachineExpress toaMachineExpress,PageManager page,HttpServletRequest request) throws CustomException{
		
		Map<String,Object> resultMap=new HashMap<String,Object>();
		//默认排序
		if(StringUtil.isEmpty(toaMachineExpress.getOrderBy())) {
			toaMachineExpress.addOrderByFieldDesc("t.CREATE_DATE");
		}
		List<ToaMachineExpress> expressList=null;
		String user=SystemSecurityUtils.getUser().getCode();
		if(!StringUtil.isEmpty(user)){
			toaMachineExpress.setCompanyId(Integer.valueOf(user));
			expressList=toaMachineExpressService.queryAll(toaMachineExpress);
		}
		if(expressList != null){
			resultMap.put("state", "success");
			resultMap.put("data", expressList);
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap; 
	}

	/**
	 * APP代收发快递-单条查询方法
	 * @param ToaMachineExpress toaMachineExpress 实体类
	 * @param Long id 重启工单ID
	 * @param HttpServletRequest request
	 * @return Map<String,Object> 查询结果
	 * @throws CustomException
	 * @author wangtf
	 */
	@RequestMapping(value="getById4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单查询-单条查询方法",operateFuncNm="getById4M",operateDescribe="重启操作查询单条数据手机端接口")
	public Map<String,Object> getById4M(ToaMachineExpress toaMachineExpress,Long id,HttpServletRequest request) throws CustomException{
		
		Map<String,Object> resultMap=new HashMap<String,Object>();
		//默认排序
		if(StringUtil.isEmpty(toaMachineExpress.getOrderBy())) {
			toaMachineExpress.addOrderByFieldDesc("t.CREATE_DATE");
		}
		toaMachineExpress.setId(id);
		ToaMachineExpress express = toaMachineExpressService.get(toaMachineExpress);
		//附件表
		ToaMachineAttach toaMachineAttach=new ToaMachineAttach();
		toaMachineAttach.setBusinessId(id.intValue());
		toaMachineAttach.setBusinessSource("2");
		List<ToaMachineAttach> toaAttachList=toaMachineAttachService.queryAll(toaMachineAttach);
		if(express!=null){
			resultMap.put("state", "success");
			resultMap.put("fileName", toaAttachList);
			resultMap.put("data", express);
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap; 
	}
}