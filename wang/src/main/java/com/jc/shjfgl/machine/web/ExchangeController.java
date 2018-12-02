package com.jc.shjfgl.machine.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
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
import com.jc.shjfgl.machine.domain.Exchange;
import com.jc.shjfgl.machine.domain.View;
import com.jc.shjfgl.machine.service.IExchangeService;
import com.jc.shjfgl.machine.service.IViewService;
import com.jc.system.CustomException;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;

@Controller
@RequestMapping(value="/machine/exchange")
public class ExchangeController extends BaseController{

	
	@Autowired
	private IExchangeService exchangeService;
	
	public ExchangeController(){};
	
	/**
	 * 保存方法
	 * @param TimeSet timeSet 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-12-08
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房平面表",operateFuncNm="save",operateDescribe="对机房平面表进行发起操作")
	public Map<String,Object> save(Exchange exchange,BindingResult result,
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
			if(exchange.getId()!=null){
				exchange.setId(null);
			}
			exchange.setStartDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			exchangeService.save(exchange);
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_135"));
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房平面表",operateFuncNm="update",operateDescribe="对机房平面表进行更新操作")
	public Map<String, Object> update(Exchange exchange, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = exchangeService.update(exchange);
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
	 * @version  2014-12-08
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房平面表",operateFuncNm="get",operateDescribe="对机房平面表进行单条查询操作")
	public Exchange get(Exchange exchange,HttpServletRequest request) throws Exception{
		return exchangeService.get(exchange);
	}

	/**
	 * 分页查询方法
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-12-08 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房平面表",operateFuncNm="get",operateDescribe="机房平面表分页查询")
	public PageManager manageList(Exchange exchange,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		//默认排序
		if(StringUtil.isEmpty(exchange.getOrderBy())) {
			exchange.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			View.setContact(deptName);
//		}
		PageManager page_ = exchangeService.query(exchange, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="机房平面表",operateFuncNm="manage",operateDescribe="对机房平面表进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")){
			model.addAttribute("deptName", deptName);
		}
		return "shjfgl/exchange/view";
	}
	
/**
	 * 删除方法
	 * @param TimeSet timeSet 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-12-08
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房平面表",operateFuncNm="deleteByIds",operateDescribe="对机房平面表进行删除")
	public  Map<String, Object> deleteByIds(Exchange exchange,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		exchange.setPrimaryKeys(ids.split(","));	
		if(exchangeService.deleteByIds(exchange) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
		}
		return resultMap;
	}

	/**
	 * @description 弹出对话框方法
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadForm.action")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "shjfgl/view/module/viewFormModule"; 
	}
	
	@RequestMapping(value="exchange.action")
	public String view(Model model,Exchange exchange,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(exchange.getOrderBy())) {
			exchange.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			View.setContact(deptName);
//		}
		List<Exchange> page_ = exchangeService.queryAll(exchange);
		model.addAttribute("dList", page_);
		return "shjfgl/exchange/equipmentView"; 
	}
	@RequestMapping(value="exchangeTalk.action")
	public String exchangeTalk(Model model,Exchange exchange,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(exchange.getOrderBy())) {
			exchange.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			View.setContact(deptName);
//		}
		List<Exchange> page_ = exchangeService.queryAll(exchange);
		model.addAttribute("dList", page_);
		return "shjfgl/view/equipmentViewTalk"; 
	}
	/**
	 * 分页查询方法
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-12-08 
	 * @throws UnsupportedEncodingException 
	 * @throws CustomException 
	 */
	@RequestMapping(value="manageViewList.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房平面表",operateFuncNm="get",operateDescribe="机房平面表分页查询")
	public List<Exchange> manageViewList(Exchange exchange,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		//默认排序
		if(StringUtil.isEmpty(exchange.getOrderBy())) {
			exchange.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			View.setContact(deptName);
//		}
		List<Exchange> page_ = exchangeService.queryAll(exchange);
		return page_; 
	}
}
