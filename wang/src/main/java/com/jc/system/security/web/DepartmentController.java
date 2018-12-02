package com.jc.system.security.web;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.CustomException;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.User;
import com.jc.system.security.domain.validator.DepartmentValidator;
import com.jc.system.security.service.IDepartmentService;
import com.jc.system.security.util.ActionLog;

/**
 * @title GOA2.0
 * @version 2014-03-18
 * 
 */
@Controller
@RequestMapping(value = "/department")
public class DepartmentController extends BaseController {

	@Autowired
	private IDepartmentService departmentService; // 部门服务

	@org.springframework.web.bind.annotation.InitBinder("department")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new DepartmentValidator());
	}

	public DepartmentController() {
	}

	/**
	 * 分页查询方法[单独查询部门]
	 * @param Department department 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="查询组织",operateFuncNm="manageList",operateDescribe="机构或部门查询操作")
	public PageManager manageList(Department department, final PageManager page, HttpServletRequest request) {
		PageManager page_ = departmentService.query(department, page);
		return page_;
	}

	/**
	 * 分页查询方法[用户查询]
	 * 
	 * @param User user 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "userManageList.action")
	@ResponseBody
	public PageManager userManageList(User user, final PageManager page) throws CustomException {
		if(StringUtils.isEmpty(user.getOrderBy())) {
			user.addOrderByField("t.ORDER_NO");
		}
		return departmentService.userManageList(user, page);
	}

	/**
	 * 用户查询
	 * @param user
	 * @return List<User>
	 * @author 张继伟
	 * @version 1.0 2014年4月28日 下午4:41:45
	 */
	@RequestMapping(value = "searchUserList.action")
	@ResponseBody
	public List<User> searchUserList() throws CustomException {
		return departmentService.searchUserList();
	}

	/**
	 * 查询分页查询方法[关联用户表查询]
	 * @param Department department 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "searchManageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="查询组织",operateFuncNm="searchManageList",operateDescribe="机构或部门查询操作")
	public PageManager searchManageList(Department department,
			final PageManager page, HttpServletRequest request) throws CustomException {
		department.setDeleteFlag(0);
		if(StringUtils.isEmpty(department.getOrderBy())) {
			department.addOrderByField("t.QUEUE");
			department.addOrderByField("t.ID");
		}
		PageManager page_ = departmentService.searchQuery(department, page);
		return page_;
	}

	@RequestMapping(value = "deptTree.action")
	@ResponseBody
	public List<Department> deptTree(Department department) throws CustomException {
		Department dept = new Department();
		dept.setDeleteFlag(0);
		if (department.getDeptType() != null)
			dept.setDeptType(department.getDeptType());
		List<Department> treeList = departmentService.query(dept);
		return treeList;
	}

	/**
	 * 跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "manage.action")
	public String manage(Model model) throws Exception {
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			if(userInfo.getIsAdmin() == 1 || userInfo.getIsSystemAdmin()){
				return "sys/department/departmentUser";
			} else {
				return "error/unauthorized";
			}
		}
		return "error/unauthorized";
	}
	
	/**
	 * 显示组织树-带人员名称
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年6月5日 上午11:44:10
	 */
	@RequestMapping(value = "queryDeptTree.action")
	@ResponseBody
	public List<Department> queryDeptTree() throws Exception {
		Department department = new Department();
		department.setDeleteFlag(0);
		return departmentService.queryTree(department);
	}

	/**
	 * 删除方法
	 * @param Department department 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="组织删除",operateFuncNm="deleteByIds",operateDescribe="机构或部门删除操作")
	public Integer deleteByIds(Department department, String ids, HttpServletRequest request) throws Exception {
		department.setPrimaryKeys(ids.split(","));
		departmentService.deleteByIds(department);
		return 1;
	}

	/**
	 * 逻辑删除部门-[删除关联关系adminSide]
	 * @param Department department 实体类
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version 2014-04-16
	 */
	@RequestMapping(value = "logicDeleteById.action")
	@ResponseBody
	@ActionLog(operateModelNm="组织删除",operateFuncNm="logicDeleteById",operateDescribe="机构或部门删除操作")
	public Map<String, Object> logicDeleteById(Department department, HttpServletRequest request) throws Exception {
		return departmentService.logicDeleteById(department);
	}

	/**
	 * 保存方法
	 * @param Department department 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="添加组织",operateFuncNm="save",operateDescribe="添加机构或部门操作")
	public Map<String, Object> save(@Valid Department department, BindingResult result, HttpServletRequest request) throws Exception {
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request, department.getDeptToken());
		if (resultMap.size() > 0) {
			return resultMap; 
		}
		User userInfo = SystemSecurityUtils.getUser();// 获取登录用户信息
		// 校验同一级部门或机构名称是否存在
		Department dept = new Department();
		dept.setName(department.getName());
		dept.setParentId(department.getParentId());
		if (departmentService.get(dept) != null) {
			resultMap.put("errorMessage", "同级组织已存在");
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
			return resultMap;
		}
		department.setDeleteFlag(0);
		department.setCreateDate(DateUtils.getSysDate());
		department.setCreateUser(userInfo.getCreateUser());
		department.setModifyUser(userInfo.getCreateUser());
		department.setModifyDate(DateUtils.getSysDate());
		if(department.getQueue() == null){
			department.setQueue(50);
		}
		if (departmentService.save(department) >= 1){
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
			Department department2 = departmentService.queryOne(department);
			resultMap.put("dept", department2);
		}else{
			resultMap.put("success", "false");
		}
		return resultMap;
	}

	/**
	 * 修改方法
	 * @param Department department 实体类
	 * @return Integer 更新的数目
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "update.action")
	@ResponseBody
	@ActionLog(operateModelNm="修改组织",operateFuncNm="update",operateDescribe="修改机构或部门操作")
	public Map<String, Object> update(Department department, BindingResult result, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		String name = departmentService.queryOne(department).getName();
		Department dept = new Department();
		dept.setName(department.getName());
		dept.setParentId(department.getParentId());
		Department d = departmentService.get(dept);
		if (d != null) {
			if(!name.equals(d.getName())){
				resultMap.put("errorMessage", "同级组织已存在");
				return resultMap;
			}
		}
		department.setModifyUser(SystemSecurityUtils.getUser().getModifyUser());
		department.setModifyDate(DateUtils.getSysDate());
		/*if(department.getQueue() == null){允许排序值为空
			department.setQueue(50);
		}*/
		if(department.getLeaderId() == null){//添加负责人修改为空
			department.setLeaderId(0L);
		}
		if (departmentService.update(department) >= 1){
			resultMap.put("success", "true");
			resultMap.put("dept", department);
			String token = getToken(request);
			resultMap.put(GlobalContext.SESSION_TOKEN, token);
		}else{
			resultMap.put("success", "false");
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param Department department 实体类
	 * @return Department 查询结果
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "get.action")
	@ResponseBody
	public Department get(Department department) throws Exception {
		return departmentService.get(department);
	}

	/**
	 * 获取部门单条记录方法(带父节点部门)
	 * @param department
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年4月16日 下午4:08:33
	 */
	@RequestMapping(value = "queryOne.action")
	@ResponseBody
	public Department queryOne(Department department) throws Exception {
		return departmentService.queryOne(department);
	}

	/**
	 * 获取全部部门及人员
	 * @return
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年5月15日 上午8:27:24
	 */
	@RequestMapping(value = "getAllDeptAndUser.action")
	@ResponseBody
	public void getAllDeptAndUser(HttpServletResponse response) throws Exception {
		String jsonArray = departmentService.getAllDeptAndUser();
		response.setContentType("application/javascript; charset=UTF-8");
		response.getWriter().print(jsonArray);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 获取全部部门及人员
	 * @param response
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年6月16日 上午10:07:39
	 */
	@RequestMapping(value = "getDeptAndUserByOnLine.action")
	@ResponseBody
	public void getDeptAndUserByOnLine(HttpServletResponse response) throws Exception {
		String jsonArray = departmentService.getDeptAndUserByOnLine();
		response.setContentType("application/javascript; charset=UTF-8");
		response.getWriter().print(jsonArray);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 获取职务人员
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年6月17日 上午11:25:07
	 */
	@RequestMapping(value = "getPostAndUser.action")
	@ResponseBody
	public void getPostAndUser(HttpServletResponse response) throws Exception {
		String jsonArray = departmentService.getPostAndUser();
		response.setContentType("application/javascript; charset=UTF-8");
		response.getWriter().print(jsonArray);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 获取个人组别
	 * @param response
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年7月29日 下午6:37:56
	 */
	@RequestMapping(value = "getPersonGroupAndUser.action")
	@ResponseBody
	public void getPersonGroupAndUser(HttpServletResponse response) throws Exception {
		String jsonArray = departmentService.getPersonGroupAndUser();
		response.setContentType("application/javascript; charset=UTF-8");
		response.getWriter().print(jsonArray);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 获取公共组别
	 * @param response
	 * @throws Exception
	 * @author 张继伟
	 * @version 1.0 2014年7月30日 上午9:40:50
	 */
	@RequestMapping(value = "getPublicGroupAndUser.action")
	@ResponseBody
	public void getPublicGroupAndUser(HttpServletResponse response) throws Exception {
		String jsonArray = departmentService.getPublicGroupAndUser();
		response.setContentType("application/javascript; charset=UTF-8");
		response.getWriter().print(jsonArray);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 查询机构树
	 * @throws Exception
	 * @author 高研
	 * @version 
	 */
	@RequestMapping(value = "orgTree.action")
	@ResponseBody
	public List<Department> orgTree() throws CustomException {
		Department department = new Department();
		department.setDeleteFlag(0);
		department.setDeptType(1);
		return departmentService.queryOrgTree(department);
	}
	
	/**
	 * 显示添加层
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-10-09
	 */
	@RequestMapping(value = "showDeptInsertHtml.action")
	public String showDeptInsertHtml(Model model, HttpServletRequest request) throws Exception {
		model.addAttribute("token", getToken(request));
		return "sys/department/departmentUserInsert";
	}
	
	/**
	 * 显示编辑层
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version 2014-10-10
	 */
	@RequestMapping(value = "showDeptEditHtml.action")
	public String showDeptEditHtml(Model model, HttpServletRequest request) throws Exception {
		model.addAttribute("token", getToken(request));
		return "sys/department/departmentUserEdit";
	}
	
	/**
	 * 根据登录人所在机构查询组织树
	 * @return
	 * @throws CustomException
	 * @author 张继伟
	 * @version 1.0 2014年11月6日 下午3:12:05
	 */
	@RequestMapping(value = "getOrgTree.action")
	@ResponseBody
	public List<Department> getOrgTree() throws CustomException {
		return departmentService.getOrgTree();
	}
	
	/**
	 * 查询整个机构组织树不包含部门
	 * @return
	 * @throws CustomException
	 * @author 张继伟
	 * @version 1.0 2014年11月6日 下午3:12:05
	 */
	@RequestMapping(value = "getAllOrgNoDeptTree.action")
	@ResponseBody
	public List<Department> getAllOrgNoDeptTree() throws CustomException {
		return departmentService.getAllOrgNoDeptTree();
	}
	
	/**
	 * 根据登录人所在机构查询组织树
	 * @return
	 * @throws CustomException
	 * @author 张继伟
	 * @version 1.0 2014年11月6日 下午3:12:05
	 */
	@RequestMapping(value = "getOrgAndPersonTree.action")
	@ResponseBody
	public List<Department> getOrgAndPersonTree() throws CustomException {
		return departmentService.getOrgAndPersonTree();
	}
	
}