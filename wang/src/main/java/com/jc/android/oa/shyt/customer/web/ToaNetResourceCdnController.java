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
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;
import com.jc.oa.product.domain.ToaProductCdn;
import com.jc.oa.product.service.IToaProductCdnService;
/**
 * @author wtf
 * @version CDN业务表
*/
@Controller
@RequestMapping(value="/customer/ToaNetResourceCdn")
public class ToaNetResourceCdnController extends BaseController{
	
	@Autowired 
	private IToaProductCdnService toaProductCdnService; 
	
	public ToaNetResourceCdnController(){
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
	@ActionLog(operateModelNm="CDN业务表",operateFuncNm="get",operateDescribe="对CDN业务表进行单条查询操作")
	public ToaProductCdn get(ToaProductCdn toaProductCdn,HttpServletRequest request) throws Exception{
		return toaProductCdnService.get(toaProductCdn);
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
	@ActionLog(operateModelNm="CDN业务表",operateFuncNm="get",operateDescribe="CDN业务表分页查询")
	public PageManager manageList(ToaProductCdn toaProductCdn,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(toaProductCdn.getOrderBy())) {
			toaProductCdn.addOrderByFieldDesc("t.CREATE_DATE");
		}
		String companyId = SystemSecurityUtils.getUser().getCode();
		if(!StringUtil.isEmpty(companyId)){
			toaProductCdn.setCustomersId(Integer.valueOf(companyId));
		}
		PageManager page_ = toaProductCdnService.query(toaProductCdn, page);
		return page_; 
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="CDN业务表",operateFuncNm="manage",operateDescribe="对CDN业务表进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "/customer/netResource/toaNetResourceCdn/toaNetResourceCdn";
	}
	
	/**
	 * @description 新增与修改方法跳转页面
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadForm.action")
	@ActionLog(operateModelNm="CDN业务表表",operateFuncNm="loadForm",operateDescribe="对CDN业务表表跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			model.addAttribute("Id", id);
		}
		return "/customer/netResource/toaNetResourceCdn/toaNetResourceCdnForm";
	}
	
	/**
	 * APP客户模块-获取单条CDN记录方法
	 * @param  ToaProductCdn 		toaProductCdn 	CDN业务表实体类
	 * @param  Long 		 		id 		   		CDN业务表ID
	 * @return Map<String, Object> 	resultMap 		CDN业务表查询结果（是否成功，提示信息）
	 * @throws Exception
	 * @author wtf
	 */
	@RequestMapping(value="get4MById.action")
	@ResponseBody
	@ActionLog(operateModelNm="CDN业务表",operateFuncNm="get4MById",operateDescribe="对CDN业务表进行单条查询操作")
	public Map<String, Object> get4MById(ToaProductCdn toaProductCdn,Long id,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaProductCdn.setId(id);
		toaProductCdn=toaProductCdnService.get(toaProductCdn);
		if(toaProductCdn !=null){
			// 成功状态位和数据
			resultMap.put("state", "success");
			resultMap.put("data", toaProductCdn);
		}else{
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}
	
	/**
	 * APP客户模块-获取CDN分页查询方法
	 * @param ToaProductCdn 	   	toaProductCdn	CDN业务表实体类
	 * @param  PageManager         	page 			页码
	 * @return Map<String, Object>	resultMap  		CDN业务表查询结果（是否成功，提示信息）
	 * @throws Exception
	 * @author wtf
	 */
	@RequestMapping(value="manage4MList.action")
	@ResponseBody
	@ActionLog(operateModelNm="CDN业务表",operateFuncNm="manage4MList",operateDescribe="CDN业务表分页查询")
	public Map<String, Object> manage4MList(ToaProductCdn toaProductCdn,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//默认排序
		if(StringUtil.isEmpty(toaProductCdn.getOrderBy())) {
			toaProductCdn.addOrderByFieldDesc("t.CREATE_DATE");
		}
		String companyId = SystemSecurityUtils.getUser().getCode();
		if(!StringUtil.isEmpty(companyId)){
			toaProductCdn.setCustomersId(Integer.valueOf(companyId));
			PageManager page_ = toaProductCdnService.query(toaProductCdn, page);
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
