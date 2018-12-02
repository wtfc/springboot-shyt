package com.jc.system.portal.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.jc.system.portal.domain.PortalFriendlylink;
import com.jc.system.portal.domain.PortalFunction;
import com.jc.system.portal.domain.Portlet;
import com.jc.system.portal.domain.RolePortal;
import com.jc.system.portal.domain.validator.PortalFunctionValidator;
import com.jc.system.portal.service.IPortalFriendlylinkService;
import com.jc.system.portal.service.IPortalFunctionService;
import com.jc.system.portal.service.IPortletService;
import com.jc.system.portal.service.IRolePortalService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;


/**
 * @title GOA2.0
 * @description  controller类
 * @author 
 * @version  2014-06-11
 */
 
@Controller
@RequestMapping(value="/sys/portalFunction")
public class PortalFunctionController extends BaseController{
	
	@Autowired
	private IPortalFunctionService portalFunctionService;
	
	@Autowired
	private IRolePortalService rolePortalService;
	
	@Autowired
	private IPortletService portletService;
	
	@Autowired
	private IPortalFriendlylinkService portalFriendlylinkService;
	
	@org.springframework.web.bind.annotation.InitBinder("portalFunction")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new PortalFunctionValidator()); 
    }
	
	public PortalFunctionController() {
	}

	/**
	 * 分页查询方法
	 * @param PortalFunction portalFunction 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-11 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户功能组件",operateFuncNm="manageList",operateDescribe="对门户功能组件进行查询")
	public PageManager manageList(PortalFunction portalFunction,PageManager page, HttpServletRequest request){
		//获取用户登录信息
		User user = SystemSecurityUtils.getUser();
		portalFunction.setCreateUser(user.getId());
		PageManager page_ = portalFunctionService.query(portalFunction, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-06-11 
	*/
	@RequestMapping(value="manage.action")
	public String manage() throws Exception{
		return "sys/portal/portalFunction"; 
	}
	
	@RequestMapping(value = "portalFunctionEdit.action")
	public String portalFunctionEdit(Model model, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "sys/portal/portalFunctionEdit";
	}

	/**
	 * 删除方法
	 * @param PortalFunction portalFunction 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-11
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户功能组件",operateFuncNm="deleteByIds",operateDescribe="对门户功能组件进行删除")
	public Integer deleteByIds(PortalFunction portalFunction,String ids,HttpServletRequest request) throws Exception{
		portalFunction.setPrimaryKeys(ids.split(","));
		return portalFunctionService.delete(portalFunction);
	}
	/**
	 * 验证功能名称重复
	 * @param PortalFunction portalFunction 实体类
	 * @return success 是否成功
	 * @throws Exception
	 * @author
	 * @version  2014-06-11
	 */
	@RequestMapping(value="valNameEcho.action")
	@ResponseBody
	public Map<String,Object> valNameEcho(@Valid PortalFunction portalFunction) 
			throws Exception{
		return portalFunctionService.valNameEcho(portalFunction);
	}

	/**
	 * 保存方法
	 * @param PortalFunction portalFunction 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-06-11
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户功能组件",operateFuncNm="save",operateDescribe="对门户功能组件进行新增操作")
	public Map<String,Object> save(@Valid PortalFunction portalFunction,BindingResult result,
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
			portalFunctionService.save(portalFunction);
			resultMap.put("success", "true");
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	* 修改方法
	* @param PortalFunction portalFunction 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-06-11 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户功能组件",operateFuncNm="update",operateDescribe="对门户功能组件进行更新操作")
	public Map<String, Object> update(PortalFunction portalFunction, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = portalFunctionService.update(portalFunction);
		if (flag == 1) {
			resultMap.put("success", "true");
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param PortalFunction portalFunction 实体类
	 * @return PortalFunction 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-11
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户功能组件",operateFuncNm="get",operateDescribe="对门户功能组件进行单条查询操作")
	public PortalFunction get(PortalFunction portalFunction,HttpServletRequest request) throws Exception{
		return portalFunctionService.get(portalFunction);
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-07-03 
	*/
	@RequestMapping(value="getFunctionForPortlet.action")
	public String getFunctionForPortlet(Model model,String functionId,String viewType,Long portletId,String tabsize,String layoutStatus) throws Exception{
		
		List<PortalFunction> refunctions = new ArrayList<PortalFunction>();
		if(viewType.equals("onetable") || viewType.equals("shortcut")){
			PortalFunction portalFunction = new PortalFunction();
			portalFunction.setId(Long.valueOf(functionId));
			portalFunction = portalFunctionService.get(portalFunction);
			refunctions.add(portalFunction);
			
			Portlet portlet = new Portlet();
			portlet.setId(portletId);
			portlet = portletService.get(portlet);
			model.addAttribute("portlet", portlet);
			
		} else if(viewType.equals("moretable")){
			String[] ids = functionId.split(",");
			for(int i=0;i<ids.length;i++){
				PortalFunction portalFunction = new PortalFunction();
				portalFunction.setId(Long.valueOf(ids[i]));
				portalFunction = portalFunctionService.get(portalFunction);
				refunctions.add(portalFunction);
			}
		} else if(viewType.equals("friendlyLink")){
			PortalFunction portalFunction = new PortalFunction();
			portalFunction.setViewType("friendlyLink");
			refunctions.add(portalFunction);
			
			PortalFriendlylink portalFriendlylink = new PortalFriendlylink();
			portalFriendlylink.setPortletId(portletId);
			//判断是否为个人门户
			String portaltype = portletService.queryForPortalType(portletId);
			if("ptype_user".equals(portaltype)){
				User user = SystemSecurityUtils.getUser();
				portalFriendlylink.setCreateUser(user.getId());
			}
			List<PortalFriendlylink> friendlinks = portalFriendlylinkService.queryAll(portalFriendlylink);
			model.addAttribute("friendlinks", friendlinks);
			if(friendlinks.size() > 7){
				model.addAttribute("friendlinksize", "yes");
			}else 
				model.addAttribute("friendlinksize", "no");
		}
		
		RolePortal rolePortal = new RolePortal();
		rolePortal.setPortaletId(portletId);
		Map<String,String> rolePortalMap= rolePortalService.getIds(rolePortal);
		model.addAttribute("userids","\""+rolePortalMap.get("userids")+"\"");
		model.addAttribute("roleids","\""+rolePortalMap.get("roleids")+"\"");
		model.addAttribute("deptids","\""+rolePortalMap.get("deptids")+"\"");
		model.addAttribute("organids","\""+rolePortalMap.get("organids")+"\"");
		model.addAttribute("refunctions", refunctions);
		model.addAttribute("viewType", viewType);
		model.addAttribute("portletId", portletId);
		model.addAttribute("tabsize", tabsize);
		model.addAttribute("layoutStatus", layoutStatus);
		return "sys/portal/functionForPortlet"; 
	}
}