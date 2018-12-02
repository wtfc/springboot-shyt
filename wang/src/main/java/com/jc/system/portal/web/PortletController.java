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
import com.jc.system.portal.domain.Portlet;
import com.jc.system.portal.domain.validator.PortletValidator;
import com.jc.system.portal.service.IPortletService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;


/**
 * @title GOA2.0
 * @description  controller类
 * @author 
 * @version  2014-06-16
 */
 
@Controller
@RequestMapping(value="/sys/portlet")
public class PortletController extends BaseController{
	
	@Autowired
	private IPortletService portletService;
	
	@org.springframework.web.bind.annotation.InitBinder("portlet")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new PortletValidator()); 
    }
	
	public PortletController() {
	}

	/**
	 * 分页查询方法
	 * @param Portlet portlet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-16 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户业务组件",operateFuncNm="manageList",operateDescribe="对门户业务组件进行查询")
	public PageManager manageList(Portlet portlet,PageManager page, HttpServletRequest request){
		//获取用户登录信息
		User user = SystemSecurityUtils.getUser();
		portlet.setCreateUser(user.getId());
		PageManager page_ = portletService.query(portlet, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-06-16 
	*/
	@RequestMapping(value="manage.action")
	public String manage(Model model) throws Exception{
		Map<String,Object> portalMap = new HashMap<String,Object>();
		portalMap = portletService.getPortalAndFouction();
		model.mergeAttributes(portalMap);
		return "sys/portal/portlet"; 
	}
	
	@RequestMapping(value = "portletEdit.action")
	public String portletEdit(Model model, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map = portletService.getPortalAndFouction();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.mergeAttributes(map);
		return "sys/portal/portletEdit";
	}

/**
	 * 删除方法
	 * @param Portlet portlet 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-16
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户组件删除",operateFuncNm="deleteByIds",operateDescribe="对门户组件进行删除")
	public Integer deleteByIds(Portlet portlet,String ids,HttpServletRequest request) throws Exception{
		//portlet.setPrimaryKeys(ids.split(","));
		return portletService.reLayoutFordel(portlet,ids);
	}

	/**
	 * 保存方法
	 * @param Portlet portlet 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-06-16
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户组件添加",operateFuncNm="save",operateDescribe="对门户组件进行新增操作")
	public Map<String,Object> save(@Valid Portlet portlet,BindingResult result,
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
			portletService.save(portlet);
			resultMap.put("success", "true");
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	* 修改方法
	* @param Portlet portlet 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-06-16 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户组件修改",operateFuncNm="update",operateDescribe="对门户组件进行更新操作")
	public Map<String, Object> update(Portlet portlet, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = portletService.reLayout(portlet);
		if (flag == 1) {
			resultMap.put("success", "true");
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param Portlet portlet 实体类
	 * @return Portlet 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-16
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public Portlet get(Portlet portlet) throws Exception{
		return portletService.get(portlet);
	}
	
	/**
	 * 获取门户关联组件方法
	 * @param Long portalId 门户id
	 * @return PortletList 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-16
	 */
	@RequestMapping(value="getPortletList.action")
	public String getPortletList(Model model,Long portalId) throws Exception{
		Map<String,Object> portletMap = new HashMap<String,Object>();
		portletMap = portletService.getPortletForPortalId(portalId,"");
		model.mergeAttributes(portletMap);
		return "sys/portal/portletList";
	}
	
	/**
	 * 获取门户布局方法
	 * @param Long portalId 门户id
	 * @return PortletList 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-16
	 */
	@RequestMapping(value="getLayoutPortals.action")
	@ResponseBody
	public Map<String,Object> getLayoutPortals(Long portalId,String portalType) throws Exception{
		Map<String,Object> portletMap = new HashMap<String,Object>();
		portletMap = portletService.getPortletForPortalId(portalId,portalType);
		return portletMap;
	}
	
	/**
	 * 验证功能组件是否被使用
	 * @param String funtionids 功能组件串ids
	 * @return Map 查询结果 true不存在	false存在
	 * @throws Exception
	 * @author chz
	 * @version  2014-09-24
	 */
	@RequestMapping(value="valByUse.action")
	@ResponseBody
	public  Map<String, Object> valByUse(String funtionids)throws Exception{
		return portletService.queryByuseFuncionids(funtionids);
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
	public Map<String,Object> valNameEcho(@Valid Portlet portlet) 
			throws Exception{
		return portletService.valNameEcho(portlet);
	}

}