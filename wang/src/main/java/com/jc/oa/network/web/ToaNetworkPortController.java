package com.jc.oa.network.web;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
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
import com.jc.oa.network.domain.ToaNetworkPort;
import com.jc.oa.network.service.IToaNetworkPortService;
/**
 * @author mrb
 * @version 端口使用情况统计表
*/
@Controller
@RequestMapping(value="/network/toaNetworkPort")
public class ToaNetworkPortController extends BaseController{
	
	@Autowired 
	private IToaNetworkPortService toaNetworkPortService; 
	
	public ToaNetworkPortController(){
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
	@ActionLog(operateModelNm="端口使用情况统计表",operateFuncNm="save",operateDescribe="对端口使用情况统计表进行发起操作")
	public Map<String,Object> save(ToaNetworkPort toaNetworkPort,BindingResult result,
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
			DecimalFormat    df   = new DecimalFormat("######0.00");  
			double mysel = Double.valueOf(toaNetworkPort.getMyselfNumber())/Double.valueOf(toaNetworkPort.getBandwidthNumber());
			double companysel = Double.valueOf(toaNetworkPort.getCompanyNumber())/Double.valueOf(toaNetworkPort.getBandwidthNumber());
			toaNetworkPort.setMyselfRate(String.valueOf(df.format(mysel)));
			toaNetworkPort.setCompanyRate(String.valueOf(df.format(companysel)));
			toaNetworkPortService.save(toaNetworkPort);
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
	@ActionLog(operateModelNm="端口使用情况统计表",operateFuncNm="update",operateDescribe="对端口使用情况统计表进行更新操作")
	public Map<String, Object> update(ToaNetworkPort toaNetworkPort, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		toaNetworkPort.setMyselfRate(String.valueOf(Double.valueOf(toaNetworkPort.getMyselfNumber())/Double.valueOf(toaNetworkPort.getBandwidthNumber())));
		toaNetworkPort.setCompanyRate(String.valueOf(Double.valueOf(toaNetworkPort.getCompanyNumber())/Double.valueOf(toaNetworkPort.getBandwidthNumber())));
		Integer flag = toaNetworkPortService.update(toaNetworkPort);
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
	@ActionLog(operateModelNm="端口使用情况统计表",operateFuncNm="get",operateDescribe="对端口使用情况统计表进行单条查询操作")
	public ToaNetworkPort get(ToaNetworkPort toaNetworkPort,HttpServletRequest request) throws Exception{
		return toaNetworkPortService.get(toaNetworkPort);
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
	@ActionLog(operateModelNm="端口使用情况统计表",operateFuncNm="get",operateDescribe="端口使用情况统计表分页查询")
	public PageManager manageList(ToaNetworkPort toaNetworkPort,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(toaNetworkPort.getOrderBy())) {
			toaNetworkPort.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = toaNetworkPortService.query(toaNetworkPort, page);
		return page_; 
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="端口使用情况统计表",operateFuncNm="manage",operateDescribe="对端口使用情况统计表进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "network/toaNetworkPort/toaNetworkPort";
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
	@ActionLog(operateModelNm="端口使用情况统计表表",operateFuncNm="deleteByIds",operateDescribe="对端口使用情况统计表表进行删除")
	public  Map<String, Object> deleteByIds(ToaNetworkPort toaNetworkPort,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaNetworkPort.setPrimaryKeys(ids.split(","));	
		if(toaNetworkPortService.deleteByIds(toaNetworkPort) != 0){
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
	@ActionLog(operateModelNm="端口使用情况统计表表",operateFuncNm="loadForm",operateDescribe="对端口使用情况统计表表跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			model.addAttribute("Id", id);
		}
		return "network/toaNetworkPort/toaNetworkPortForm";
	}

}
