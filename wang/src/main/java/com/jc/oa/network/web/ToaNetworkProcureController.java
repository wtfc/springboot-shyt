package com.jc.oa.network.web;

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
import com.jc.oa.network.domain.ToaNetworkProcure;
import com.jc.oa.network.service.IToaNetworkProcureService;
/**
 * @author mrb
 * @version 网络设备采购表
*/
@Controller
@RequestMapping(value="/network/toaNetworkProcure")
public class ToaNetworkProcureController extends BaseController{
	
	@Autowired 
	private IToaNetworkProcureService toaNetworkProcureService; 
	
	public ToaNetworkProcureController(){
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
	@ActionLog(operateModelNm="网络设备采购表",operateFuncNm="save",operateDescribe="对网络设备采购表进行发起操作")
	public Map<String,Object> save(ToaNetworkProcure toaNetworkProcure,BindingResult result,
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
			toaNetworkProcureService.save(toaNetworkProcure);
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
	@ActionLog(operateModelNm="网络设备采购表",operateFuncNm="update",operateDescribe="对网络设备采购表进行更新操作")
	public Map<String, Object> update(ToaNetworkProcure toaNetworkProcure, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaNetworkProcureService.update(toaNetworkProcure);
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
	@ActionLog(operateModelNm="网络设备采购表",operateFuncNm="get",operateDescribe="对网络设备采购表进行单条查询操作")
	public ToaNetworkProcure get(ToaNetworkProcure toaNetworkProcure,HttpServletRequest request) throws Exception{
		return toaNetworkProcureService.get(toaNetworkProcure);
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
	@ActionLog(operateModelNm="网络设备采购表",operateFuncNm="get",operateDescribe="网络设备采购表分页查询")
	public PageManager manageList(ToaNetworkProcure toaNetworkProcure,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(toaNetworkProcure.getOrderBy())) {
			toaNetworkProcure.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = toaNetworkProcureService.query(toaNetworkProcure, page);
		return page_; 
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="网络设备采购表",operateFuncNm="manage",operateDescribe="对网络设备采购表进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "network/toaNetworkProcure/toaNetworkProcure";
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
	@ActionLog(operateModelNm="网络设备采购表表",operateFuncNm="deleteByIds",operateDescribe="对网络设备采购表表进行删除")
	public  Map<String, Object> deleteByIds(ToaNetworkProcure toaNetworkProcure,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaNetworkProcure.setPrimaryKeys(ids.split(","));	
		if(toaNetworkProcureService.deleteByIds(toaNetworkProcure) != 0){
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
	@ActionLog(operateModelNm="网络设备采购表表",operateFuncNm="loadForm",operateDescribe="对网络设备采购表表跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			model.addAttribute("Id", id);
		}
		return "network/toaNetworkProcure/toaNetworkProcureForm";
	}

}
