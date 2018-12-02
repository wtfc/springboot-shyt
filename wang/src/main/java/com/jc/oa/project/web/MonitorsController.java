package com.jc.oa.project.web;

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
import com.jc.oa.project.domain.Monitors;
import com.jc.oa.project.service.IMonitorsService;
import com.jc.shjfgl.machine.domain.Exchange;
import com.jc.shjfgl.machine.service.IExchangeService;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;

@Controller
@RequestMapping(value="/project/monitor")
public class MonitorsController extends BaseController{

	@Autowired
	private IMonitorsService monitorService;
	
	@Autowired
	private IExchangeService exchangeService;
	
	public MonitorsController(){};
	
	/**
	 * 保存方法
	 * @param TimeSet timeSet 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="项目管理表",operateFuncNm="save",operateDescribe="对项目管理表进行发起操作")
	public Map<String,Object> save(Monitors monitor,BindingResult result,
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
			if(monitor.getId()!=null){
				monitor.setId(null);
				monitor.setStatus(0);
			}
			monitorService.save(monitor);
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
	@ActionLog(operateModelNm="项目管理表",operateFuncNm="update",operateDescribe="对项目管理表进行更新操作")
	public Map<String, Object> update(Monitors monitor, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = monitorService.update(monitor);
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
	@ActionLog(operateModelNm="项目管理表",operateFuncNm="get",operateDescribe="对项目管理表进行单条查询操作")
	public Monitors get(Monitors monitor,HttpServletRequest request) throws Exception{
		return monitorService.get(monitor);
	}
	
	
	/**
	 * 分页查询方法
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="项目管理表",operateFuncNm="get",operateDescribe="项目管理表分页查询")
	public PageManager manageList(Monitors monitor,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(monitor.getOrderBy())) {
			monitor.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = monitorService.query(monitor, page);
		return page_;
	}

	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manageForm.action")
	@ActionLog(operateModelNm="项目管理表",operateFuncNm="manage",operateDescribe="对项目管理表进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		String name = SystemSecurityUtils.getUser().getDisplayName();
		String dept = SystemSecurityUtils.getUser().getDeptName();
		Exchange exchange = new Exchange();
		exchange.setEquipmentId(id);
		exchange.setExtStr1("toa_project_monitor");
		List<Exchange> exchangeList = exchangeService.queryAll(exchange);
		if(!StringUtil.isEmpty(id)){
			model.addAttribute("id", id);
			model.addAttribute("status", status);
			model.addAttribute("idd", id);
			model.addAttribute("status", status);
			model.addAttribute("exchangeList",exchangeList);
			model.addAttribute("talkName",name);
		}else{
			model.addAttribute("name", name);
			model.addAttribute("dept", dept);
		}
		
		return "project/monitor/monitorFrom";
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="项目管理表",operateFuncNm="manage",operateDescribe="对客户管理首页进行跳转操作")
	public String khgl(HttpServletRequest request) throws Exception{
		return "project/monitor/monitor";
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manageSj.action")
	@ActionLog(operateModelNm="项目管理表",operateFuncNm="manage",operateDescribe="对客户管理首页进行跳转操作")
	public String manageSj(Model model,HttpServletRequest request) throws Exception{
		return "project/monitor/monitorSj";
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manageFinish.action")
	@ActionLog(operateModelNm="项目管理表",operateFuncNm="manage",operateDescribe="对客户管理首页进行跳转操作")
	public String manageFinish(HttpServletRequest request) throws Exception{
		return "project/monitor/monitorFinish";
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
	@ActionLog(operateModelNm="项目管理表",operateFuncNm="deleteByIds",operateDescribe="对项目管理表进行删除")
	public  Map<String, Object> deleteByIds(Monitors monitor,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		monitor.setPrimaryKeys(ids.split(","));	
		if(monitorService.deleteByIds(monitor) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
		}
		return resultMap;
	}
	
	/**
	 * 删除方法
	 * @param TimeSet timeSet 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="updateStatus.action")
	@ResponseBody
	@ActionLog(operateModelNm="项目管理表",operateFuncNm="updateStatus",operateDescribe="对项目管理表进行删除")
	public  Map<String, Object> updateStatus(Monitors monitor,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		monitor.setPrimaryKeys(ids.split(","));
		if(monitorService.updateStatus(monitor) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_WORKFLOW_001"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_WORKFLOW_002"));
		}
		return resultMap;
	}
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="khgl.action")
	@ActionLog(operateModelNm="客户管理表",operateFuncNm="manage",operateDescribe="对客户管理首页进行跳转操作")
	public String yemian(HttpServletRequest request) throws Exception{
		return "sys/khgl";
	}
}
