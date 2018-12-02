package com.jc.oa.shyt.web;

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
import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.service.impl.UserServiceImpl;
import com.jc.system.security.util.ActionLog;
import com.jc.oa.shyt.domain.CustomerPeople;
import com.jc.oa.shyt.domain.CustomerPeoples;
import com.jc.oa.shyt.domain.Customer;
import com.jc.oa.shyt.domain.Visit;
import com.jc.oa.shyt.service.ICustomerPeopleService;
import com.jc.oa.shyt.service.ICustomerService;
import com.jc.oa.shyt.service.IVisitService;
/**
 * @author mrb
 * @version 客户信息表
*/
@Controller
@RequestMapping(value="/shyt/toaShytCustomer")
public class ToaShytCustomerController extends BaseController{
	
	@Autowired 
	private ICustomerService toaShytCustomerService; 
	@Autowired 
	private IUserService userService;
	@Autowired
	private IVisitService visitService;
	@Autowired
	private ICustomerPeopleService customerPeopleService;
	public ToaShytCustomerController(){
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
	@ActionLog(operateModelNm="客户信息表",operateFuncNm="save",operateDescribe="对客户信息表进行发起操作")
	public Map<String,Object> save(Customer toaShytCustomer,CustomerPeoples customerPeople,BindingResult result,
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
			toaShytCustomerService.savePeople(toaShytCustomer, customerPeople);
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
	@ActionLog(operateModelNm="客户信息表",operateFuncNm="update",operateDescribe="对客户信息表进行更新操作")
	public Map<String, Object> update(Customer toaShytCustomer, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		if(toaShytCustomer.getEndIntel()!=null){
			toaShytCustomer.setExtStr1("1");
		}
		Integer flag = toaShytCustomerService.update(toaShytCustomer);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	* 修改方法(维护专员)
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="updateTrade.action")
	@ResponseBody
	@ActionLog(operateModelNm="客户信息表",operateFuncNm="updateTrade",operateDescribe="对客户信息表进行更新操作")
	public Map<String, Object> updateTrade(Customer toaShytCustomer, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		if(toaShytCustomer.getEndIntel()!=null){
			toaShytCustomer.setExtStr1("1");
		}
		Visit visit = new Visit();
		CustomerPeople customerPeople = new CustomerPeople();
		User user=new User();
		user.setId(Long.valueOf(toaShytCustomer.getCreateUser()));
		User newUser=userService.get(user);
		toaShytCustomer.setTradeUser(newUser.getDisplayName());
		
		Integer flag = toaShytCustomerService.update(toaShytCustomer);
		if (flag == 1) {
			//修改回访信息
			visit.setCreateUser(Long.valueOf(toaShytCustomer.getCreateUser()));
			visit.setCustomerId(Integer.valueOf(toaShytCustomer.getId().toString()));
			visitService.updateCustomer(visit);
			
			customerPeople.setCreateUser(Long.valueOf(toaShytCustomer.getCreateUser()));
			customerPeople.setCustomerId(Integer.valueOf(toaShytCustomer.getId().toString()));
			customerPeopleService.updatePeople(customerPeople);
			
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
	@ActionLog(operateModelNm="客户信息表",operateFuncNm="get",operateDescribe="对客户信息表进行单条查询操作")
	public Customer get(Customer toaShytCustomer,HttpServletRequest request) throws Exception{
		return toaShytCustomerService.get(toaShytCustomer);
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
	@ActionLog(operateModelNm="客户信息表",operateFuncNm="get",operateDescribe="客户信息表分页查询")
	public PageManager manageList(Customer toaShytCustomer,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		
		//默认排序
		if(StringUtil.isEmpty(toaShytCustomer.getOrderBy())) {
			toaShytCustomer.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = toaShytCustomerService.query(toaShytCustomer, page);
		return page_; 
	}
	
	/**
	 * 权限分页查询方法
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @throws CustomException 
	 */
	@RequestMapping(value="managePermissionList.action")
	@ResponseBody
	@ActionLog(operateModelNm="客户信息表",operateFuncNm="get",operateDescribe="客户信息表分页查询")
	public PageManager managePermissionList(Customer toaShytCustomer,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		
		//默认排序
		if(StringUtil.isEmpty(toaShytCustomer.getOrderBy())) {
			toaShytCustomer.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = toaShytCustomerService.queryForPermission(toaShytCustomer, page);
		return page_; 
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="客户信息表",operateFuncNm="manage",operateDescribe="对客户信息表进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "shyt/customer/toaShytCustomer";
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
	@ActionLog(operateModelNm="客户信息表表",operateFuncNm="deleteByIds",operateDescribe="对客户信息表表进行删除")
	public  Map<String, Object> deleteByIds(Customer toaShytCustomer,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaShytCustomer.setPrimaryKeys(ids.split(","));	
		if(toaShytCustomerService.deleteByIds(toaShytCustomer) != 0){
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
	@ActionLog(operateModelNm="客户信息表表",operateFuncNm="loadForm",operateDescribe="对客户信息表表跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			model.addAttribute("Id", id);
		}
		return "shyt/customer/toaShytCustomerForm";
	}
	
	/**
	* 跳转方法
	* @return String 跳转到查看所有信息
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="viewPage.action")
	@ActionLog(operateModelNm="客户信息表",operateFuncNm="manage",operateDescribe="对客户信息表进行跳转操作")
	public String viewPage(Model model,HttpServletRequest request) throws Exception{
		String id= request.getParameter("id");
		model.addAttribute("Id", id);
		String company = request.getParameter("company");
		model.addAttribute("company", company);
		return "shyt/customer/viewPage";
	}
	
	/**
	* 跳转方法
	* @return String 跳转综合查询客户信息list表
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="viewCustomer.action")
	@ActionLog(operateModelNm="客户信息表",operateFuncNm="manage",operateDescribe="对客户信息表进行跳转操作")
	public String viewCustomer(Model model,HttpServletRequest request) throws Exception{
		
		return "shyt/customer/viewCustomer";
	}
	
	/**
	* 终止客户跳转方法
	* @return String 跳转综合查询客户信息list表
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="zhongzhiCustomer.action")
	@ActionLog(operateModelNm="客户信息表",operateFuncNm="manage",operateDescribe="对终止客户信息表进行跳转操作")
	public String zhongzhiCustomer(Model model,HttpServletRequest request) throws Exception{
		
		return "shyt/customer/zhongzhiCustomer";
	}
	
	/**
	* @return String 跳转客户信息首页显示
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="top.action")
	@ActionLog(operateModelNm="客户信息表",operateFuncNm="manage",operateDescribe="对客户信息表进行跳转操作")
	public String top(Model model,HttpServletRequest request) throws Exception{
		
		return "sys/customerSystem";
	}
	
	/**
	 * @description 新增与修改方法跳转页面
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadForm2.action")
	@ActionLog(operateModelNm="客户信息表表",operateFuncNm="loadForm",operateDescribe="对客户信息表表跳转")
	public String loadForm2(Customer toaShytCustomer,Model model,HttpServletRequest request) throws Exception{
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			toaShytCustomer.setId(Long.valueOf(id));
		}
		Customer customer = toaShytCustomerService.get(toaShytCustomer);
		model.addAttribute("customer", customer);
		return "shyt/customer/viewCustomerFrom";
	}
	
	/**
	* @description 基本信息默认跳转方法
	* @param HttpServletRequest request
	* @return /WebRoot/WEB-INF/web/doc/template/official.jsp
	* @throws Exception
	* @author 吕桢卓
	* @version  2014-09-05 
	*/
	@RequestMapping(value="officialjump.action")
	@ActionLog(operateModelNm="基本信息",operateFuncNm="officialjump",operateDescribe="基本信息默认跳转")
	public String personalOfficeSkip(HttpServletRequest request) throws Exception{
		return "sys/official"; 
	}

}
