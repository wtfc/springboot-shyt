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
import com.jc.system.portal.domain.RolePortal;
import com.jc.system.portal.domain.validator.RolePortalValidator;
import com.jc.system.portal.service.IRolePortalService;
import com.jc.system.security.domain.LeftRight;
import com.jc.system.security.service.IDepartmentService;
import com.jc.system.security.service.IRoleService;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.ActionLog;


/**
 * @title GOA2.0
 * @description  controller类
 * @author 
 * @version  2014-06-16
 */
 
@Controller
@RequestMapping(value="/sys/rolePortal")
public class RolePortalController extends BaseController{
	
	@Autowired
	private IRolePortalService rolePortalService;
	
	@Autowired
	private IDepartmentService departmentService; // 部门服务

	@Autowired
	private IUserService userService; // 用户服务

	@Autowired
	private IRoleService roleService;

	
	@org.springframework.web.bind.annotation.InitBinder("rolePortal")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new RolePortalValidator()); 
    }
	
	public RolePortalController() {
	}

	/**
	 * 分页查询方法
	 * @param RolePortal rolePortal 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-16 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户权限查询",operateFuncNm="manageList",operateDescribe="对门户权限进行查询操作")
	public PageManager manageList(RolePortal rolePortal,PageManager page,HttpServletRequest request){
		PageManager page_ = rolePortalService.query(rolePortal, page);
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
	@ActionLog(operateModelNm="门户权限",operateFuncNm="manage",operateDescribe="对门户权限进行跳转操作")
	public String manage(HttpServletRequest request) throws Exception{
		return "sys/rolePortal/rolePortalAaa"; 
	}
	
	@RequestMapping(value = "rolePortalEdit.action")
	public String rolePortalEdit(Model model, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("datas", map);
		return "sys/portal/rolePortalEdit";
	}
	
	@RequestMapping(value = "rolePortletsEdit.action")
	public String rolePortletsEdit(Model model, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("datas", map);
		return "sys/portal/rolePortletEdit";
	}

/**
	 * 删除方法
	 * @param RolePortal rolePortal 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-16
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户权限删除",operateFuncNm="deleteByIds",operateDescribe="对门户权限进行删除")
	public Integer deleteByIds(RolePortal rolePortal,String ids,HttpServletRequest request) throws Exception{
		rolePortal.setPrimaryKeys(ids.split(","));
		return rolePortalService.delete(rolePortal);
	}

	/**
	 * 保存方法
	 * @param RolePortal rolePortal 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 刘鑫峰
	 * @version  2014-06-16
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody 
	@ActionLog(operateModelNm="门户权限新增",operateFuncNm="save",operateDescribe="对门户权限进行新增操作")
	public Map<String,Object> save(@Valid RolePortal rolePortal,BindingResult result,
			HttpServletRequest request,String userids,String roleids,String deptids,String organids,String token) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		List<RolePortal> list = new ArrayList<RolePortal>();
		list = rolePortalService.parseToList(rolePortal,userids,roleids,deptids,organids);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request,token);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		if(!"false".equals(resultMap.get("success"))){
			Long count = rolePortalService.getCount(rolePortal);
			if(count.intValue()!=0)
				rolePortalService.deleteRolePortalItem(rolePortal);
			rolePortalService.saveList(list);
//			rolePortalService.save(rolePortal);
			resultMap.put("success", "true");
		}
		resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		return resultMap;
	}

	/**
	* 修改方法
	* @param RolePortal rolePortal 实体类
	* @return Integer 更新的数目
	* @author 刘鑫峰
	* @version  2014-06-16 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户权限更新",operateFuncNm="update",operateDescribe="对门户权限进行更新操作")
	public Map<String, Object> update(RolePortal rolePortal, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = rolePortalService.update(rolePortal);
		if (flag == 1) {
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param RolePortal rolePortal 实体类
	 * @return RolePortal 查询结果
	 * @throws Exception
	 * @author 刘鑫峰
	 * @version  2014-06-16
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="门户权限查询",operateFuncNm="get",operateDescribe="对门户权限进行单条查询操作")
	public RolePortal get(RolePortal rolePortal,HttpServletRequest request) throws Exception{
		return rolePortalService.get(rolePortal);
	}
	
	/**
	 * 获取控件数据方法
	 * @param  
	 * @return  
	 * @throws Exception
	 * @author 刘鑫峰
	 */
	@RequestMapping(value="getData.action")
	@ResponseBody 
	public List<LeftRight> getData(String type) throws Exception{
		List<LeftRight> result = new ArrayList<>();
		result = rolePortalService.getData(type);
		return result;
	}
	/**
	 * 获取控件分类Id方法
	 * @param  
	 * @return  
	 * @throws Exception
	 * @author 刘鑫峰
	 */
	@RequestMapping(value="getIds.action")
	@ResponseBody 
	public Map<String, String> getIds(RolePortal rolePortal,HttpServletRequest request) throws Exception{
		Map<String, String> result = rolePortalService.getIds(rolePortal);
		result.put(GlobalContext.SESSION_TOKEN, getToken(request));
		return result;
	}
}