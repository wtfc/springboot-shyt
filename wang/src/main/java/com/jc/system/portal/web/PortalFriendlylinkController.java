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
import com.jc.system.common.util.MessageUtils;
import com.jc.system.portal.domain.PortalFriendlylink;
import com.jc.system.portal.domain.validator.PortalFriendlylinkValidator;
import com.jc.system.portal.service.IPortalFriendlylinkService;
import com.jc.system.portal.service.IPortalFriendlylinksService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;

/**
 * @title 172.16.3.68
 * @description  controller类
 * @author 
 * @version  2014-11-18
 */
 
@Controller
@RequestMapping(value="/sys/portalFriendlylink")
public class PortalFriendlylinkController extends BaseController{
	
	@Autowired
	private IPortalFriendlylinkService portalFriendlylinkService;
	
	@Autowired
	private IPortalFriendlylinksService portalFriendlylinksService;
	
	@org.springframework.web.bind.annotation.InitBinder("portalFriendlylink")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new PortalFriendlylinkValidator()); 
    }
	
	public PortalFriendlylinkController() {
	}

	/**
	 * 保存方法
	 * @param PortalFriendlylink portalFriendlylink 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-11-18
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="友情链接",operateFuncNm="save",operateDescribe="对友情链接进行批量新增操作")
	public Map<String,Object> save(String linkName,String linkUrl,String sequence,String portletid,
			Long portalid,HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = portalFriendlylinkService.instertFriendlyLinks(linkName,linkUrl,sequence,portletid,portalid);
		return resultMap;
	}
	
	/**
	 * 保存方法
	 * @param PortalFriendlylink portalFriendlylink 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-11-18
	 */
	@RequestMapping(value = "saveone.action")
	@ResponseBody
	@ActionLog(operateModelNm="友情链接",operateFuncNm="saveone",operateDescribe="对友情链接进行新增操作")
	public Map<String,Object> saveone(@Valid PortalFriendlylink portalFriendlylink,BindingResult result,
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
			portalFriendlylinkService.save(portalFriendlylink);
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param PortalFriendlylink portalFriendlylink 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-11-18 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="友情链接",operateFuncNm="update",operateDescribe="对友情链接进行更新操作")
	public Map<String, Object> update(PortalFriendlylink portalFriendlylink, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = portalFriendlylinkService.update(portalFriendlylink);
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
	 * @param PortalFriendlylink portalFriendlylink 实体类
	 * @return PortalFriendlylink 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-11-18
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public PortalFriendlylink get(PortalFriendlylink portalFriendlylink) throws Exception{
		return portalFriendlylinkService.get(portalFriendlylink);
	}

	/**
	 * 分页查询方法
	 * @param PortalFriendlylink portalFriendlylink 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-11-18 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	public PageManager manageList(PortalFriendlylink portalFriendlylink,PageManager page){
		User userInfo = SystemSecurityUtils.getUser();
		portalFriendlylink.setCreateUserOrg(userInfo.getOrgId());
		PageManager page_ = portalFriendlylinkService.queryFriendlyLinkAndPortal(portalFriendlylink, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-11-18 
	*/
	@RequestMapping(value="manage.action")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map = portalFriendlylinksService.queryFriendlyLinks();
		model.addAttribute("seldata", map);
		return "sys/portal/portalFriendlylink"; 
	}

/**
	 * 删除方法
	 * @param PortalFriendlylink portalFriendlylink 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-11-18
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="友情链接",operateFuncNm="deleteByIds",operateDescribe="对友情链接进行删除")
	public  Map<String, Object> deleteByIds(PortalFriendlylink portalFriendlylink,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		portalFriendlylink.setPrimaryKeys(ids.split(","));	
		if(portalFriendlylinkService.delete(portalFriendlylink) != 0){
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
	 * @version  2014-11-18
	 */
	@RequestMapping(value="loadForm.action")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map = portalFriendlylinksService.queryFriendlyLinks();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "sys/portal/portalFriendlylinkedit"; 
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
	public Map<String,Object> valNameEcho(@Valid PortalFriendlylink portalFriendlylink) 
			throws Exception{
		return portalFriendlylinkService.valNameEcho(portalFriendlylink);
	}

}