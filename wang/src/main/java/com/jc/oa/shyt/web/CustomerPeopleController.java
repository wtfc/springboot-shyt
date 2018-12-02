package com.jc.oa.shyt.web;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
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
import com.jc.oa.shyt.domain.Complain;
import com.jc.oa.shyt.domain.CustomerPeople;
import com.jc.oa.shyt.service.ICustomerPeopleService;
import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.portal.util.PortalUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;

@Controller
@RequestMapping(value="/shyt/customerPeople")
public class CustomerPeopleController extends BaseController{

	@Autowired
	private ICustomerPeopleService customerPeopleService;
	
	public CustomerPeopleController(){
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
	@ActionLog(operateModelNm="客户联系信息表",operateFuncNm="save",operateDescribe="对客户联系信息表进行发起操作")
	public Map<String,Object> save(CustomerPeople customerPeople,BindingResult result,
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
			if(!StringUtil.isEmpty(customerPeople.getIdCard())){
				String birthday = customerPeople.getIdCard().substring(10, 12);
				customerPeople.setExtStr2(birthday);
				customerPeople.setExtStr3("0");
			}
			customerPeopleService.save(customerPeople);
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
	@ActionLog(operateModelNm="客户联系信息表",operateFuncNm="update",operateDescribe="对客户联系信息表进行更新操作")
	public Map<String, Object> update(CustomerPeople customerPeople, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		if(!StringUtil.isEmpty(customerPeople.getIdCard())){
			String birthday = customerPeople.getIdCard().substring(10, 12);
			customerPeople.setExtStr2(birthday);
			customerPeople.setIdCard(customerPeople.getIdCard());
			customerPeople.setExtStr3("0");
		}
		Integer flag = customerPeopleService.update(customerPeople);
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
	@ActionLog(operateModelNm="客户联系信息表",operateFuncNm="get",operateDescribe="对客户联系信息表进行单条查询操作")
	public CustomerPeople get(CustomerPeople customerPeople,HttpServletRequest request) throws Exception{
		return customerPeopleService.get(customerPeople);
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
	@ActionLog(operateModelNm="客户联系信息表",operateFuncNm="get",operateDescribe="客户联系信息表分页查询")
	public PageManager manageList(CustomerPeople customerPeople,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		
		//默认排序
		if(StringUtil.isEmpty(customerPeople.getOrderBy())) {
			customerPeople.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = customerPeopleService.queryForPermission(customerPeople, page);
		return page_; 
	}

	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="客户联系信息表",operateFuncNm="manage",operateDescribe="对客户联系信息表进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "shyt/customerPeople/customerPeople";
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author 离职之前人员信息
	* @version  2014-12-08 
	*/
	@RequestMapping(value="leavePeople.action")
	@ActionLog(operateModelNm="客户联系信息表",operateFuncNm="manage",operateDescribe="对客户联系信息表进行跳转操作")
	public String leavePeople(Model model,HttpServletRequest request) throws Exception{
		
		return "shyt/customerPeople/NocustomerPeople";
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage2.action")
	@ActionLog(operateModelNm="客户联系信息表",operateFuncNm="manage",operateDescribe="对客户联系信息表进行跳转操作")
	public String manage2(Model model,HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		CustomerPeople customer = new CustomerPeople();
		customer.setCustomerId(Integer.valueOf(id));
		customer.setExtStr1("0");
		customer.setDeleteFlag(0);
		List<CustomerPeople> customers = customerPeopleService.queryAll(customer);
		model.addAttribute("customers", customers);
		model.addAttribute("oldId", id);
		return "shyt/customerPeople/customerPeopleView";
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
	@ActionLog(operateModelNm="客户联系信息表",operateFuncNm="deleteByIds",operateDescribe="对客户联系信息表进行删除")
	public  Map<String, Object> deleteByIds(CustomerPeople customerPeople,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		customerPeople.setPrimaryKeys(ids.split(","));	
		if(customerPeopleService.deleteByIds(customerPeople) != 0){
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
	@ActionLog(operateModelNm="客户联系信息表",operateFuncNm="loadForm",operateDescribe="对客户联系信息表跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			model.addAttribute("oldId", id);
		}else{
			String token = getToken(request);
			map.put(GlobalContext.SESSION_TOKEN, token);
			model.addAttribute("data", map);
		}
		return "shyt/customerPeople/customerPeopleFrom";
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="shouye.action")
	@ActionLog(operateModelNm="客户联系信息表",operateFuncNm="manage",operateDescribe="对客户联系信息表进行跳转操作")
	public String hsouye(Model model,HttpServletRequest request) throws Exception{
		return "sys/ywxx";
	}
	
	/**
	* 生日提醒跳转
	* @return String 生日提醒跳转
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="birthday.action")
	@ActionLog(operateModelNm="客户联系信息表",operateFuncNm="manage",operateDescribe="对客户联系信息表进行跳转操作")
	public String birthday(Model model,HttpServletRequest request) throws Exception{
		Calendar calendar=Calendar.getInstance();
		//获得当前时间的月份，月份从0开始所以结果要加1
		int month=calendar.get(Calendar.MONTH)+1;
		String months=null;
		if(month<10){
			months="0"+month;
		}else{
			months = month+"";
		}
		model.addAttribute("month", months);
		return "shyt/customerPeople/customerBirthday";
	}
	
	/**
	 * @description 新增与修改方法跳转页面
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="BirthdayForm.action")
	@ActionLog(operateModelNm="客户联系信息表",operateFuncNm="loadForm",operateDescribe="对客户联系信息表跳转")
	public String BirthdayForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			model.addAttribute("oldId", id);
		}else{
			String token = getToken(request);
			map.put(GlobalContext.SESSION_TOKEN, token);
			model.addAttribute("data", map);
		}
		return "shyt/customerPeople/customerBirthdayFrom";
	}
	
	/**
	* @description 门户便签组件数据
	* @return String 跳转的路径
	* @throws Exception
	* @version  2014-12-23 
	*/
	@RequestMapping(value="boxPortalData.action")
	public String boxPortalData(Model model,HttpServletRequest request) throws Exception{
		//门户引用类固定代码
		model = PortalUtils.getPortalParameter(model, request);
		return "shyt/customerPeople/customerBirthdayPortal";
	}
	
	/**
	* @description 门户便签组件数据
	* @return Map 数据集合
	* @throws Exception
	* @version  2014-12-23 
	*/
	@RequestMapping(value="getBoxData.action")
	@ResponseBody
	public Map<String, Object> getBoxData(Long columnId,String funViewType,int dataRows) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		CustomerPeople note = new CustomerPeople();
		note.setDeleteFlag(0);
		note.addOrderByFieldDesc("t.MODIFY_DATE");
		note.setCreateUser(SystemSecurityUtils.getUser().getId());
		
		Calendar calendar=Calendar.getInstance();
		//获得当前时间的月份，月份从0开始所以结果要加1
		int month=calendar.get(Calendar.MONTH)+1;
		String months=null;
		if(month<10){
			months="0"+month;
		}
		note.setExtStr2(months);
		note.setExtStr1("0");
		List<CustomerPeople> noteList =  customerPeopleService.queryAll(note);
		result.put("notelist", noteList);
		result.put("dataRows",dataRows);
		return result;
	}
}
