package com.jc.oa.contract.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
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
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.util.ActionLog;
import com.jc.oa.contract.domain.ToaContractOutcontract;
import com.jc.oa.contract.service.IToaContractOutcontractService;
/**
 * @author mrb
 * @version 付款方合同
*/
@Controller
@RequestMapping(value="/contract/toaContractOutcontract")
public class ToaContractOutcontractController extends BaseController{
	
	@Autowired 
	private IToaContractOutcontractService toaContractOutcontractService; 
	
	public ToaContractOutcontractController(){
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
	@ActionLog(operateModelNm="付款方合同",operateFuncNm="save",operateDescribe="对付款方合同进行发起操作")
	public Map<String,Object> save(ToaContractOutcontract toaContractOutcontract,BindingResult result,
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
			toaContractOutcontractService.save(toaContractOutcontract);
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
	@ActionLog(operateModelNm="付款方合同",operateFuncNm="update",operateDescribe="对付款方合同进行更新操作")
	public Map<String, Object> update(ToaContractOutcontract toaContractOutcontract, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaContractOutcontractService.update(toaContractOutcontract);
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
	@ActionLog(operateModelNm="付款方合同",operateFuncNm="get",operateDescribe="对付款方合同进行单条查询操作")
	public ToaContractOutcontract get(ToaContractOutcontract toaContractOutcontract,HttpServletRequest request) throws Exception{
		return toaContractOutcontractService.get(toaContractOutcontract);
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
	@ActionLog(operateModelNm="付款方合同",operateFuncNm="get",operateDescribe="付款方合同分页查询")
	public PageManager manageList(ToaContractOutcontract toaContractOutcontract,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(toaContractOutcontract.getOrderBy())) {
			toaContractOutcontract.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = toaContractOutcontractService.query(toaContractOutcontract, page);
		return page_; 
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="付款方合同",operateFuncNm="manage",operateDescribe="对付款方合同进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "contract/toaContractOutcontract/toaContractOutcontract";
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
	@ActionLog(operateModelNm="付款方合同表",operateFuncNm="deleteByIds",operateDescribe="对付款方合同表进行删除")
	public  Map<String, Object> deleteByIds(ToaContractOutcontract toaContractOutcontract,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaContractOutcontract.setPrimaryKeys(ids.split(","));	
		if(toaContractOutcontractService.deleteByIds(toaContractOutcontract) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
		}
		return resultMap;
	}

	/**
	 * @description 新增与修改方法跳转页面
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadForm.action")
	@ActionLog(operateModelNm="付款方合同表",operateFuncNm="loadForm",operateDescribe="对付款方合同表跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			model.addAttribute("Id", id);
		}
		return "contract/toaContractOutcontract/toaContractOutcontractForm";
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manageLead.action")
	@ActionLog(operateModelNm="收款合同表",operateFuncNm="manage",operateDescribe="收款合同表进行跳转操作")
	public String manageLead(Model model,HttpServletRequest request) throws Exception{
		return "sys/gdgl2";
	}
}
