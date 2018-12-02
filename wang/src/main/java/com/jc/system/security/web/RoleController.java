package com.jc.system.security.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.CustomException;
import com.jc.system.common.util.BeanUtil;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.Role;
import com.jc.system.security.domain.RoleBlocks;
import com.jc.system.security.domain.RoleExts;
import com.jc.system.security.domain.RoleMenus;
import com.jc.system.security.domain.User;
import com.jc.system.security.domain.validator.RoleValidator;
import com.jc.system.security.service.IRoleService;
import com.jc.system.security.util.ActionLog;

@Controller
@RequestMapping(value = "/sys/role")
public class RoleController extends BaseController {

	@Autowired
	private IRoleService roleService;

	public RoleController() {

	}
	
	@org.springframework.web.bind.annotation.InitBinder("role")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new RoleValidator());
	}

	/**
	 * @description 跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-05-09
	 */
	@RequestMapping(value = "manage.action")
	public String manageList(Model model) {
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			if(userInfo.getIsAdmin() == 1 || userInfo.getIsSystemAdmin()){
				return "sys/role/role";
			} else {
				return "error/unauthorized";
			}
		}
		return "error/unauthorized";
	}
	
	/**
	 * @description 分页查询方法
	 * @param User
	 *            user 实体类
	 * @param PageManager
	 *            page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 * @throws CustomException 
	 */
	@RequestMapping(value = "manageList.action")
	@ResponseBody
	public PageManager manageList(Role role,final PageManager page,
			HttpServletRequest request) throws CustomException {
		//默认排序
		if(StringUtils.isEmpty(role.getOrderBy())) {
			role.addOrderByFieldDesc("t.CREATE_DATE");
		}
		
		role.setDeleteFlag(0);
		return roleService.query(role, page);
	}

	/**
	 * 添加角色
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="角色设置",operateFuncNm="save",operateDescribe="对角色设置进行添加")
	public  Map<String, Object> save(@Valid Role role, BindingResult result,HttpServletRequest request) throws Exception  {
		Map<String, Object> resultMap = validateBean(result);
		
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Role rBean = new Role();
		rBean.setName(role.getName());
		rBean.setDeptIds(role.getDeptId().toString());
		if (roleService.get(rBean) != null) {
			throw new CustomException("角色名称已存在");
		}
		
		if (roleService.save(role) == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		}
		
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	 * 验证角色名称
	 */
	@ResponseBody
	@RequestMapping(value = "checkName")
	public String checkName(String oldName, Role role) {
		return "false";
	}

	/**
	 * @description 修改方法
	 * @param User
	 *            user 实体类
	 * @return Integer 更新的数目
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "update.action")
	@ResponseBody
	@ActionLog(operateModelNm="角色设置",operateFuncNm="update",operateDescribe="对角色设置进行修改")
	public Map<String, Object> update(@Valid Role role, BindingResult result,HttpServletRequest request) throws Exception {
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		role.setModifyDateNew(DateUtils.getSysDate());
		if (roleService.update(role) == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		}
		
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	 * @description 删除方法
	 * @param User
	 *            user 实体类
	 * @param String
	 *            ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="角色设置",operateFuncNm="deleteByIds",operateDescribe="对角色设置进行删除")
	public Integer deleteByIds(Role role, String ids) throws Exception {
		role.setPrimaryKeys(ids.split(","));
		return roleService.delete(role);
	}
	
	/**
	 * @description 获取单条记录方法
	 * @param User
	 *            user 实体类
	 * @return User 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "get.action")
	@ResponseBody
	public Role get(Role role) throws Exception {
		role.setDeleteFlag(0);
		return roleService.get(role);
	}
	
	@RequestMapping(value = "roleEdit.action")
	public String roleEdit(Model model, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "sys/role/roleEdit";
	}
	
	@RequestMapping(value = "roleAuthorize.action")
	public String roleAuthorize(Model model, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "sys/role/roleAuthorize";
	}
	
	/**
	  * @description  保存角色-菜单关联数据
	  * @param roleMap
	  * @param result
	  * @param request
	  * @return
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月13日 下午2:51:29
	*/
	@RequestMapping(value = "saveRoleMenu.action")
	@ResponseBody
	@ActionLog(operateModelNm="角色设置",operateFuncNm="saveRoleMenu",operateDescribe="对角色设置进行授权")
	public  Map<String, Object> saveRoleMenu(@RequestBody Map<String, Object> roleMap, BindingResult result,HttpServletRequest request) throws Exception  {
		Role role = BeanUtil.map2Object(roleMap, Role.class);
		
		Map<String, Object> resultMap = validateBean(result);
		
		roleService.saveRoleMenu(role);
		
		resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/**
	  * @description 根据角色获得已选的菜单
	  * @param roleMenus
	  * @param result
	  * @param request
	  * @return
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月13日 下午2:50:33
	*/
	@RequestMapping(value = "getMenusByRole.action")
	@ResponseBody
	public  List<RoleMenus> getMenusByRole(RoleMenus roleMenus, BindingResult result,HttpServletRequest request) throws Exception  {
		return roleService.getMenusByRole(roleMenus);
	}
	
	/**
	  * @description 根据角色获得已选的菜单
	  * @param roleMenus
	  * @param result
	  * @param request
	  * @return
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月13日 下午2:50:33
	*/
	@RequestMapping(value = "getExtsByRole.action")
	@ResponseBody
	public  List<RoleExts> getExtsByRole(RoleExts roleExts, BindingResult result,HttpServletRequest request) throws Exception  {
		return roleService.getExtsByRole(roleExts);
	}
	
	/**
	  * @description 根据角色获得已选的部门
	  * @param roleMenus
	  * @param result
	  * @param request
	  * @return
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月13日 下午2:50:33
	*/
	@RequestMapping(value = "getBlcoksByRole.action")
	@ResponseBody
	public  List<RoleBlocks> getBlcoksByRole(RoleBlocks roleBlocks, BindingResult result,HttpServletRequest request) throws Exception  {
		return roleService.getBlocksByRole(roleBlocks);
	}
	
	/**
	  * @description 获得部门及部门下所有角色集合
	  * @param response
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月23日 下午1:50:32
	*/
	@RequestMapping(value = "getAllDeptAndRole.action")
	public void getAllDeptAndRole(HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(roleService.getAllDeptAndRole().toString());
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	
	/**
	  * @description  验证同一部门下角色名称是否存在
	  * @param role
	  * @return
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年7月7日 下午2:24:41
	*/
	@RequestMapping(value = "checkRoleName.action")
	@ResponseBody
	public String checkRoleName(Role role) throws Exception {
 		if(role.getNameOld().equals(role.getName())){
 			return "true";
		}else{
			if(roleService.get(role) == null ){
				return "true";
			}else{
				return "false";
			}
		}
	}
	
}