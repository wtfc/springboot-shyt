package com.jc.system.security.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.security.SystemAuthorizingRealm.Principal;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.Menu;
import com.jc.system.security.domain.User;
import com.jc.system.security.domain.validator.MenuValidator;
import com.jc.system.security.service.IMenuService;
import com.jc.system.security.util.ActionLog;


/**
 * @title GOA2.0
 * @description  controller类
 * @author 
 * @version  2014-04-17
 */
 
@Controller
@RequestMapping(value="/sys/menu")
public class MenuController extends BaseController{
	
	@Autowired
	private IMenuService menuService;
	
	@org.springframework.web.bind.annotation.InitBinder("menu")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new MenuValidator()); 
    }
	
	public MenuController() {
	}
	
	private MenuValidator mv = new MenuValidator();

	/**
	 * @description 分页查询方法
	 * @param Menu menu 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-17 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	public PageManager manageList(Menu menu,PageManager page){
		PageManager page_ = menuService.query(menu, page);
		return page_; 
	}
	
	/**
	* @description 跳转方法（导航栏）
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-23 
	*/
	@RequestMapping(value="managenAvigation.action")
	public String managenAvigation(Model model,Long id,String actionName) throws Exception{
		Map<String,Object> menuMap = new HashMap<String,Object>();
		if(id != null && id != 0 )
			menuMap = menuService.getNavigationMenu(id);
		else if(actionName != null && !actionName.equals("")){
			menuMap = menuService.getNavigationMenu(actionName);
		}
		model.mergeAttributes(menuMap);
		return "sys/menu/navigationMenu";
	}
	
	/**
	* @description 锚点应用
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-11-05 
	*/
	@RequestMapping(value="anchorAvigation.action")
	@ResponseBody
	public Map<String,Object> anchorAvigation(Long id,String actionName) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map = menuService.getNavigationMenu(actionName);
		map.put("success", "true");
		return map;
	}
	
	/**
	* @description 跳转方法（资源维护）
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-21 
	*/
	@RequestMapping(value="manageResource.action")
	public String manageResource() throws Exception{
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			if(userInfo.getIsAdmin() == 1 || userInfo.getIsSystemAdmin()){
				return "sys/menu/resurceTree";
			} else {
				return "error/unauthorized";
			}
		}
		return "error/unauthorized";
	}
	
	/**
	* @description 加载数据信息（资源维护）
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-21 
	*/
	@RequestMapping(value="manageLoadResource.action")
	public String manageLoadResource(Model model,Long id) throws Exception{
		//2014.7.17 chz 增加查询同级资源菜单列表 start----
		Menu menuVo = new Menu();
		menuVo.setId(id);
		List<Menu> menuList  = menuService.getResourceInfo(menuVo,"all");
		model.addAttribute("menuList", menuList);
		//2014.7.17 chz 增加查询同级资源菜单列表 end -----
		return "sys/menu/loadResource";
	}
	
	/**
	* @description 加载数据信息（资源树）
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-11-27 
	*/
	@RequestMapping(value="treeResource.action")
	@ResponseBody
	public Map<String,Object> treeResource(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Menu menuVo = new Menu();
		menuVo.setMenuType(0);
		List<Menu> menuList  = menuService.queryAll(menuVo);
		map.put("menuList", menuList);
		return map;
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-17 
	*/
	@RequestMapping(value="manage.action")
	public String manage(Model model) throws Exception{
		Map<String,Object> menuMap = new HashMap<String,Object>();
		Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
		//获取用户登录信息
		User user = SystemSecurityUtils.getUser();
		//2014.7.17 chz 增加查询所有一级菜单列表 start----
		Menu menuVo = new Menu();
		//menuVo.setParentId(-1L);//一级菜单标识
		if(user.getIsSystemAdmin())
			menuVo.setUserId(Long.valueOf(-99));
		else
			menuVo.setUserId(user.getId());//临时 测试 需要提取登录用户信息------------------------------替换
		menuMap = menuService.desktopMenuForQuery(menuVo);
		model.mergeAttributes(menuMap);
		model.addAttribute("ischildnode", 0);
		//model.addAttribute("menuList", menuList);
		//2014.7.17 chz 增加查询所有一级菜单列表 end -----
		if(StringUtils.isEmpty(principal.getTheme())){
			return "sys/menu/desktop";
		}
		if(principal.getTheme().equals("2")){
			return "sys/menu/desktopWhite"; 
		}
		return "sys/menu/desktop";
		//return "sys/menu/desktopWhite"; 
	}
	
	/**
	* @description 加载桌面左侧菜单
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-17 
	*/
	@RequestMapping(value="manageleft.action")
	public String manageleft(Model model,Long id,String ischildnode) throws Exception{
		//获取用户登录信息
		User user = SystemSecurityUtils.getUser();
		//2014.7.17 chz 增加查询所有一级菜单列表 start----
		Menu menuVo = new Menu();
		menuVo.setId(id);//一级菜单标识
		menuVo.setIsShow(0);// 菜单是否显示 0显示 1不显示
		if(user.getIsSystemAdmin())
			menuVo.setUserId(Long.valueOf(-99));
		else
			menuVo.setUserId(user.getId());//临时 测试 需要提取登录用户信息-------------------------------------替换
		List<Menu> leftmenuList = menuService.getResourceInfo(menuVo,"power");
		model.addAttribute("leftmenuList", leftmenuList);
		model.addAttribute("ischildnode", ischildnode);
		//2014.7.17 chz 增加查询所有一级菜单列表 end -----
		return "sys/menu/leftmenu"; 
	}

	/**
	 * @description 删除方法(物理删除)
	 * @param Menu menu 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Integer deleteByIds(Menu menu,String ids) throws Exception{
		menu.setPrimaryKeys(ids.split(","));
		return menuService.delete(menu);
	}
	
	/**
	 * @description 删除方法(逻辑删除)
	 * @param Menu menu 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value="delete.action")
	@ResponseBody
	@ActionLog(operateModelNm="资源设置",operateFuncNm="delete",operateDescribe="对资源设置进行菜单删除")
	public Integer delete(Menu menu,Long id,HttpServletRequest request) throws Exception{
		
		String ids = menuService.groupMenuId(id);
		menu.setPrimaryKeys(ids.split(","));
		menu.setModifyDate(DateUtils.getSysDate());
		return menuService.delete(menu);
	}
	

	/**
	 * @description 保存方法
	 * @param Menu menu 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="资源设置",operateFuncNm="save",operateDescribe="对资源设置进行菜单添加")
	public Map<String,Object> save(@Valid Menu menu,BindingResult result,HttpServletRequest request) throws Exception{
		
		mv.validate(menu, result);
		
		Map<String,Object> resultMap = validateBean(result);
		// 验证bean
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		if(!"false".equals(resultMap.get("success"))){
			menu.setDeleteFlag(0);
			menu.setCreateDate(DateUtils.getSysDate());
			menu.setModifyDate(DateUtils.getSysDate());
			menuService.save(menu);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param Menu menu 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-04-17 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="资源设置",operateFuncNm="update",operateDescribe="对资源设置进行菜单修改")
	public Map<String, Object> update(Menu menu,BindingResult result,HttpServletRequest request) throws Exception{
		
		mv.validate(menu, result);
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		menu.setModifyDate(DateUtils.getSysDate());
		Integer flag = menuService.update(menu);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		}
		return resultMap;
	}

	/**
	 * @description 获取单条记录方法
	 * @param Menu menu 实体类
	 * @return Menu 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public Menu get(Menu menu) throws Exception{
		return menuService.get(menu);
	}
	
	/**
	 * @description 加载数据信息（资源添加）
	 * @param int id 父菜单id
	 * @return Menu 查询结果
	 * @throws Exception
	 * @author chz
	 * @version  2014-04-22
	 */
	@RequestMapping(value="getaddpage.action")
	@ResponseBody
	public Map<String, Object> getaddpage(Long id, HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put("menuvos", menuService.getAddInfo(id));
		map.put(GlobalContext.SESSION_TOKEN, token);
		return map;
	}
	
	/**
	 * @description 加载数据信息（资源修改）
	 * @param int id 菜单id
	 * @return Menu 查询结果
	 * @throws Exception
	 * @author chz
	 * @version  2014-04-22
	 */
	@RequestMapping(value="geteditpage.action")
	@ResponseBody
	public Map<String, Object> geteditpage(Long id, HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put("menuvos", menuService.getEditInfo(id));
		map.put(GlobalContext.SESSION_TOKEN, token);
		return map;
	}
	
	/**
	 * @description 验证菜单数据是否存在子菜单（资源删除）
	 * @param int id 菜单id
	 * @return Map 查询结果 true不存在	false存在
	 * @throws Exception
	 * @author chz
	 * @version  2014-06-05
	 */
	@RequestMapping(value="valClickMenu.action")
	@ResponseBody
	public  Map<String, Object> valClickMenu(Long id)throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String ids = menuService.groupMenuId(id);
		if(ids.equals(String.valueOf(id))){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
		}
		return resultMap;
	}
	
	/**
	 * @description 验证菜单数据是否已被选中（资源删除）
	 * @param int id 菜单id
	 * @return Map 查询结果 true不存在	false存在
	 * @throws Exception
	 * @author chz
	 * @version  2014-06-05
	 */
	@RequestMapping(value="valMenuSel.action")
	@ResponseBody
	public  Map<String, Object> valMenuSel(Long id)throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(menuService.getRolesByMenu(id) == 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
		}
		return resultMap;
	}
	
	/**
	  * @description 获得菜单树信息
	  * @param menu 菜单实体bean
	  * @return List<Menu> 菜单树
	  * @throws Exception
	  * @author 孙鹏
	  * @version 1.0 2014年5月12日 下午2:17:30
	*/
	@RequestMapping(value = "menuTree.action")
	@ResponseBody
	public List<Menu> menuTree(Menu menu) throws Exception{
		menu.setDeleteFlag(0);
		menu.setMenuType(1);
		menu.setIsShow(0);
		menu.setUserId(SystemSecurityUtils.getUser().getId());
		List<Menu> menuList = new ArrayList<Menu>();
		if(SystemSecurityUtils.getUser().getIsSystemAdmin()){
			menu.setUserId(-99L);
			menuList = menuService.queryWithRole(menu);
		}else{
			menuList = menuService.queryWithRole(menu);
		}
		return menuList;
	}
	
	/**
	 * @description 验证当前用户是否已分配此菜单
	 * @param int id 菜单id
	 * @return Map 查询结果 true不存在	false存在
	 * @throws Exception
	 * @author chz
	 * @version  2014-06-15
	 */
	@RequestMapping(value="valUserMenu.action")
	@ResponseBody
	public  Map<String, Object> valUserMenu(Long id)throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//获取用户登录信息
		User user = SystemSecurityUtils.getUser();
		
		Menu menuVo = menuService.queryUserMenu(id,user.getId());
		
		if(menuVo == null){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		}
		resultMap.put("menuVo", menuVo);
		return resultMap;
	}
}