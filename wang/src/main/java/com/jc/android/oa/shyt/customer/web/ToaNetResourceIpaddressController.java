package com.jc.android.oa.shyt.customer.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;
import com.jc.oa.network.domain.ToaNetworkIpaddress;
import com.jc.oa.network.service.IToaNetworkIpaddressService;
import com.jc.oa.product.domain.ToaProductCloud;
/**
 * @author wtf
 * @version 客户IP地址统计表
*/
@Controller
@RequestMapping(value="/customer/toaNetResourceIpaddress")
public class ToaNetResourceIpaddressController extends BaseController{
	
	@Autowired 
	private IToaNetworkIpaddressService toaNetworkIpaddressService; 
	
	public ToaNetResourceIpaddressController(){
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
	@ActionLog(operateModelNm="客户IP地址统计表",operateFuncNm="get",operateDescribe="客户IP地址统计表分页查询")
	public PageManager manageList(ToaNetworkIpaddress toaNetworkIpaddress,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(toaNetworkIpaddress.getOrderBy())) {
			toaNetworkIpaddress.addOrderByFieldDesc("t.CREATE_DATE");
		}
		String companyId = SystemSecurityUtils.getUser().getCode();
		if(!StringUtil.isEmpty(companyId)){
			toaNetworkIpaddress.setCompanyId(Integer.valueOf(companyId));
		}
		PageManager page_ = toaNetworkIpaddressService.query(toaNetworkIpaddress, page);
		return page_; 
	}
	
	/**
	* 客户服务部
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="客户IP地址统计表",operateFuncNm="manage",operateDescribe="对客户IP地址统计表进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "customer/netResource/toaNetResourceIpaddress/toaNetResourceIpaddress";
	}
	
	/**
	 * APP客户模块-客户IP地址统计表获取单条记录方法
	 * @param  ToaNetworkIpaddress 	toaNetworkIpaddress 实体类
	 * @param  Long 				id 					客户IP地址统计表ID
	 * @return Map<String, Object> 	resultMap 			云计算业务表查询结果（是否成功，提示信息）
	 * @throws Exception
	 * @author wtf
	 */
	@RequestMapping(value="get4MById.action")
	@ResponseBody
	@ActionLog(operateModelNm="客户IP地址统计表",operateFuncNm="get4MById",operateDescribe="对客户IP地址统计表进行单条查询操作")
	public Map<String, Object> get4MById(ToaNetworkIpaddress toaNetworkIpaddress,Long id,HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaNetworkIpaddress.setId(id);
		toaNetworkIpaddress=toaNetworkIpaddressService.get(toaNetworkIpaddress);
		if(toaNetworkIpaddress !=null){
			// 成功状态位和数据
			resultMap.put("state", "success");
			resultMap.put("data", toaNetworkIpaddress);
		}else{
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}
	
	/**
	 * APP客户模块-客户IP地址统计表分页查询方法
	 * @param  ToaNetworkIpaddress 	toaNetworkIpaddress 客户IP地址统计表实体类
	 * @param  PageManager 			page 				页码
	 * @return Map<String, Object> 	resultMap 			云计算业务表查询结果（是否成功，提示信息）
	 * @throws Exception
	 * @author wtf
	 */
	@RequestMapping(value="manage4MList.action")
	@ResponseBody
	@ActionLog(operateModelNm="客户IP地址统计表",operateFuncNm="manage4MList",operateDescribe="客户IP地址统计表分页查询")
	public Map<String, Object> manage4MList(ToaNetworkIpaddress toaNetworkIpaddress,PageManager page,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//默认排序
		if(StringUtil.isEmpty(toaNetworkIpaddress.getOrderBy())) {
			toaNetworkIpaddress.addOrderByFieldDesc("t.CREATE_DATE");
		}
		String companyId = SystemSecurityUtils.getUser().getCode();
		if(!StringUtil.isEmpty(companyId)){
			toaNetworkIpaddress.setCompanyId(Integer.valueOf(companyId));
			PageManager page_ = toaNetworkIpaddressService.query(toaNetworkIpaddress, page);
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
