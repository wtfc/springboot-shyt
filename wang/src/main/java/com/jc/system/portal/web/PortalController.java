package com.jc.system.portal.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.portal.domain.Portal;
import com.jc.system.portal.domain.validator.PortalValidator;
import com.jc.system.portal.service.IPortalService;
import com.jc.system.security.util.ActionLog;


/**
 * @title GOA2.0
 * @description  controller类
 * @author 
 * @version  2014-06-13
 */
 
@Controller
@RequestMapping(value="/sys/portal")
public class PortalController extends BaseController{
	
	@Autowired
	private IPortalService portalService;
	
	@org.springframework.web.bind.annotation.InitBinder("portal")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new PortalValidator()); 
    }
	
	public PortalController() {
	}

	/**
	 * 分页查询方法
	 * @param Portal portal 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-13 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户管理",operateFuncNm="manageList",operateDescribe="对门户管理进行查询")
	public PageManager manageList(Portal portal,PageManager page, HttpServletRequest request){
		if(portal.getPortalType() != null && portal.getPortalType().equals("ptype_user_only")){
			portal.setPortalType("ptype_user");
			portal.setPortalStatus("portalstatus_1");
		}
		portal = portalService.spellToPortal(portal, portal.getPortalType());
		PageManager page_ = portalService.query(portal, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径(门户管理)
	* @throws Exception
	* @author
	* @version  2014-06-13 
	*/
	@RequestMapping(value="manage.action")
	public String manage() throws Exception{
		return "sys/portal/portal"; 
	}
	
	@RequestMapping(value = "portalEdit.action")
	public String portalEdit(Model model, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "sys/portal/portalEdit";
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径(门户设置)
	* @throws Exception
	* @author
	* @version  2014-06-13 
	*/
	@RequestMapping(value="manageSet.action")
	public String manageSet(Model model,String portalType,HttpServletRequest request) throws Exception{
		Map<String,Object> portletMap = new HashMap<String,Object>();
		portletMap = portalService.portletListForPortal(portalType,null);
		String token = getToken(request);
		portletMap.put(GlobalContext.SESSION_TOKEN, token);
		model.mergeAttributes(portletMap);	
		model.addAttribute("portalType", portalType);
		return "sys/portal/portalSet"; 
	}

/**
	 * 删除方法
	 * @param Portal portal 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-13
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户管理",operateFuncNm="deleteByIds",operateDescribe="对门户管理进行删除")
	public Integer deleteByIds(Portal portal,String ids,HttpServletRequest request) throws Exception{
		return portalService.deletePortalAndPortletByIds(ids);
	}

	/**
	 * 保存方法
	 * @param Portal portal 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-06-13
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户管理",operateFuncNm="save",operateDescribe="对门户管理进行新增操作")
	public Map<String,Object> save(@Valid Portal portal,BindingResult result,
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
			portalService.save(portal);
			resultMap.put("success", "true");
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	* 修改方法
	* @param Portal portal 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-06-13 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户管理",operateFuncNm="update",operateDescribe="对门户管理进行更新操作")
	public Map<String, Object> update(Portal portal, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = portalService.update(portal);
		if (flag == 1) {
			resultMap.put("success", "true");
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param Portal portal 实体类
	 * @return Portal 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-13
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public Portal get(Portal portal) throws Exception{
		return portalService.get(portal);
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径(门户主界面)
	* @throws Exception
	* @author
	* @version  2014-06-27 
	*/
	@RequestMapping(value="manageView.action")
	public String manageView(Model model,Long portalId,String portalType) throws Exception{
		Map<String,Object> portletMap = new HashMap<String,Object>();
		portletMap = portalService.portletListForPortal(portalType,portalId);
		portletMap.put("portalId", portalId);
		portletMap.put("portalType", portalType);
		model.mergeAttributes(portletMap);	
		return "sys/portal/portalView"; 
	}
	
	/**
	 * 验证功能名称重复
	 * @param PortalFunction portalFunction 实体类
	 * @return success 是否成功
	 * @throws Exception
	 * @author
	 * @version  2014-10-30
	 */
	@RequestMapping(value="valNameEcho.action")
	@ResponseBody
	public Map<String,Object> valNameEcho(@Valid Portal portal) 
			throws Exception{
		return portalService.valNameEcho(portal);
	}

}