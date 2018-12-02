package com.jc.android.oa.shyt.customer.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.shyt.domain.Customer;
import com.jc.oa.shyt.domain.CustomerPeople;
import com.jc.oa.shyt.service.ICustomerPeopleService;
import com.jc.oa.shyt.service.ICustomerService;
import com.jc.shjfgl.machine.service.IEquipmentService;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.ActionLog;


@Controller
@RequestMapping(value="/customer/customerInfo")
public class CustomerResourceController extends BaseController{
	
	
	@Autowired
	private IEquipmentService equipmentService;

	@Autowired 
	private ICustomerService toaShytCustomerService; 
	
	@Autowired
	private ICustomerPeopleService customerPeopleService;
	
    @Autowired
    private IUserService userService;
    
	public CustomerResourceController(){};
	
	/**
	 * @description 客户模块欢迎页面跳转
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="客户信息表表",operateFuncNm="loadForm",operateDescribe="对客户信息表表跳转")
	public String manage(HttpServletRequest request) throws Exception{

		return "sys/customerModel";
	}
	
	/**
	 * @description 客户信息显示页面跳转
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadForm.action")
	@ActionLog(operateModelNm="客户信息表表",operateFuncNm="loadForm",operateDescribe="对客户信息表表跳转")
	public String loadForm(HttpServletRequest request) throws Exception{

		return "customer/customerInfo/customerInfo";
	}
	
	/**
	 * 获取单条记录方法
	 * @param TimeSet timeSet 实体类
	 * @return TimeSet 查询结果
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="getCustomer.action")
	@ResponseBody
	@ActionLog(operateModelNm="客户信息表",operateFuncNm="get",operateDescribe="对客户信息表进行单条查询操作")
	public Customer getCustomer(Customer toaShytCustomer,HttpServletRequest request) throws Exception{
		String companyId = SystemSecurityUtils.getUser().getCode();
		if(!StringUtil.isEmpty(companyId)){
			toaShytCustomer.setId(Long.valueOf(companyId));
		}
		return toaShytCustomerService.get(toaShytCustomer);
	}
	
	/**
	 * 获取单条记录方法
	 * @param TimeSet timeSet 实体类
	 * @return TimeSet 查询结果
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="getCustomerPeople.action")
	@ResponseBody
	@ActionLog(operateModelNm="客户联系信息表",operateFuncNm="get",operateDescribe="对客户联系信息表进行单条查询操作")
	public CustomerPeople getCustomerPeople(CustomerPeople customerPeople,HttpServletRequest request) throws Exception{
		String companyId = SystemSecurityUtils.getUser().getCode();
		if(!StringUtil.isEmpty(companyId)){
			customerPeople.setCustomerId(Integer.valueOf(companyId));
		}
		return customerPeopleService.get(customerPeople);
	}
	
	/**
	 * APP客户模块-客户信息表获取单条记录方法
	 * @param  Customer 			toaShytCustomer 客户信息实体类
	 * @return Map<String, Object> 	resultMap 		单条客户信息表查询结果（是否成功，提示信息）
	 * @throws Exception
	 * @author wtf
	 */
	@RequestMapping(value="get4MCustomer.action")
	@ResponseBody
	@ActionLog(operateModelNm="客户信息表",operateFuncNm="get4MCustomer",operateDescribe="对客户信息表进行单条查询操作")
	public Map<String, Object> get4MCustomer(Customer toaShytCustomer,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String companyId = SystemSecurityUtils.getUser().getCode();
		if(!StringUtil.isEmpty(companyId)){
			toaShytCustomer.setId(Long.valueOf(companyId));
			toaShytCustomer = toaShytCustomerService.get(toaShytCustomer);
			
			CustomerPeople customerPeople = new CustomerPeople();
			customerPeople.setCustomerId(Integer.valueOf(companyId));
			//customerPeople = customerPeopleService.get(customerPeople);
			List<CustomerPeople> page_=customerPeopleService.queryAll(customerPeople);
			if(toaShytCustomer !=null){
				// 成功状态位和数据
				resultMap.put("state", "success");
				resultMap.put("data", toaShytCustomer);
				resultMap.put("peoples", page_);
			}else{
				// 失败状态位和错误信息
				resultMap.put("state", "failure");
				resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
			}
		}else{
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		
		return resultMap;
	}
	
	/**
	 * APP客户模块-客户联系信息表list获取方法
	 * @param  CustomerPeople 		customerPeople 	客户联系信息表实体类
	 * @param  PageManager 			page 			页码
	 * @return Map<String, Object> 	resultMap 		客户联系信息表查询结果（是否成功，提示信息）
	 * @throws Exception
	 * @author wtf
	 */
	@RequestMapping(value="get4MCustomerPeople.action")
	@ResponseBody
	@ActionLog(operateModelNm="客户联系信息表",operateFuncNm="get4MCustomerPeople",operateDescribe="对客户联系信息表进行单条查询操作")
	public Map<String, Object> get4MCustomerPeople(CustomerPeople customerPeople,PageManager page,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String companyId = SystemSecurityUtils.getUser().getCode();
		if(!StringUtil.isEmpty(companyId)){
			customerPeople.setCustomerId(Integer.valueOf(companyId));
			//customerPeople = customerPeopleService.get(customerPeople);
			PageManager page_=customerPeopleService.query(customerPeople, page);
			if(page_ !=null){
				// 成功状态位和数据
				resultMap.put("state", "success");
				resultMap.put("data", page_.getData());
			}else{
				// 失败状态位和错误信息
				resultMap.put("state", "failure");
				resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
			}
		}else{
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		
		return resultMap;
	}
}
