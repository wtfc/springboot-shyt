package com.jc.system.portal.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.portal.domain.PortaletLayout;
import com.jc.system.portal.domain.validator.PortaletLayoutValidator;
import com.jc.system.portal.service.IPortaletLayoutService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;


/**
 * @title GOA2.0
 * @description  controller类
 * @author 
 * @version  2014-07-01
 */
 
@Controller
@RequestMapping(value="/sys/portaletLayout")
public class PortaletLayoutController extends BaseController{
	
	@Autowired
	private IPortaletLayoutService portaletLayoutService;
	
	@org.springframework.web.bind.annotation.InitBinder("portaletLayout")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new PortaletLayoutValidator()); 
    }
	
	public PortaletLayoutController() {
	}

	/**
	 * 分页查询方法
	 * @param PortaletLayout portaletLayout 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-07-01 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户布局",operateFuncNm="manageList",operateDescribe="对门户设置进行查询操作")
	public PageManager manageList(PortaletLayout portaletLayout,PageManager page,HttpServletRequest request){
		PageManager page_ = portaletLayoutService.query(portaletLayout, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-07-01 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="门户布局",operateFuncNm="manage",operateDescribe="对门户设置进行跳转操作")
	public String manage(HttpServletRequest request) throws Exception{
		return "sys/portaletLayout/portaletLayoutAaa"; 
	}

/**
	 * 删除方法
	 * @param PortaletLayout portaletLayout 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-07-01
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户布局删除",operateFuncNm="deleteByIds",operateDescribe="对门户设置进行删除")
	public Integer deleteByIds(PortaletLayout portaletLayout,String ids,
			HttpServletRequest request) throws Exception{
		portaletLayout.setPrimaryKeys(ids.split(","));
		return portaletLayoutService.delete(portaletLayout);
	}

	/**
	 * 保存方法
	 * @param PortaletLayout portaletLayout 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-07-01
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户布局新增",operateFuncNm="save",operateDescribe="对门户设置进行新增操作")
	public Map<String,Object> save(@Valid PortaletLayout portaletLayout,BindingResult result,
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
			//获取用户登录信息
			User user = SystemSecurityUtils.getUser();
			portaletLayout.setUserId(user.getId());
			portaletLayoutService.save(portaletLayout);
			resultMap.put("success", "true");
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	* 修改方法
	* @param PortaletLayout portaletLayout 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-07-01 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户布局修改",operateFuncNm="update",operateDescribe="对门户设置进行更新操作")
	public Map<String, Object> update(PortaletLayout portaletLayout, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		//获取用户登录信息
		User user = SystemSecurityUtils.getUser();
		portaletLayout.setUserId(user.getId());
		portaletLayout.setModifyDateNew(DateUtils.getSysDate());
		Integer flag = portaletLayoutService.update(portaletLayout);
		if (flag == 1) {
			resultMap.put("success", "true");
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param PortaletLayout portaletLayout 实体类
	 * @return PortaletLayout 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-07-01
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户设置",operateFuncNm="get",operateDescribe="对门户设置进行单条查询操作")
	public PortaletLayout get(PortaletLayout portaletLayout,HttpServletRequest request) throws Exception{
		return portaletLayoutService.get(portaletLayout);
	}

}