package com.jc.system.common.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jc.foundation.web.BaseController;
import com.jc.system.CustomException;
import com.jc.system.common.service.ICommonService;
import com.jc.system.common.service.IRegService;
import com.jc.system.common.service.impl.RegServiceImpl;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.security.beans.UserBean;
import com.jc.system.security.domain.AdminSide;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.Role;
import com.jc.system.security.domain.User;

/**
 * @description:系统controller类
 */
@Controller
@RequestMapping(value = "/system")
public class CommonController extends BaseController {

	@Autowired
	ICommonService commonService;
	//注册表文件service
	IRegService regService = new RegServiceImpl();
	
	/**
	 * @description 跳转到500页面
	 * @return String 500页面的路径
	 * @author
	 */
	@RequestMapping(value = "500.action")
	public String goto500() throws Exception {
		return "error/500";
	}

	/**
	 * @description 跳转到500页面
	 * @return String 500页面的路径
	 * @author
	 */
	@RequestMapping(value = "system.action")
	public String system() throws Exception {
		return "sys/system";
	}
	
	/**
	 * @description 生成token方法
	 * @return Object token
	 * @author
	 */
	@RequestMapping(value = "token.action")
	@ResponseBody
	public Object token(HttpServletRequest request) throws Exception {
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		request.getSession().setAttribute(GlobalContext.SESSION_TOKEN, token);
		Map<String, String> map = new HashMap<String, String>();
		map.put("token", token);
		return map;
	}
	
	/**
	  * 读取国际化配置文件
	  * @param fileName 文件名
	  * @param response 
	  * @throws Exception
	  * @author 孙圣然
	  * @version 1.0 2014年5月9日 下午1:43:10
	*/
	@RequestMapping(value = "/i18n/{fileName}")
	public void getProperties(@PathVariable String fileName,HttpServletResponse response) throws Exception {
		String propertiesFileName = GlobalContext.basePath+"/"+fileName+".properties";
		sendFile(propertiesFileName, response,"text/html");
	}
	
	/**
	* @description 显示部门树
	* @author 
	*/
	@RequestMapping(value="structureView.action")
	public String structureView() throws Exception{
		return "sys/structure/structure";
	}
	
	/**
	* @description 显示部门树
	* @author 
	*/
	@RequestMapping(value="getStructureAndUser.action")
	@ResponseBody
	public List<Department> getStructureAndUser() throws Exception{
		return commonService.getDeptsAndUser();
	}
	
	/**
	* @description 显示管理员机构树
	* @author 
	*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "managerDeptTree.action")
	@ResponseBody
	public List<AdminSide> managerDeptTree() {
		return commonService.queryManagerDeptRree();
	}
	
	/**
	 * 查询机构树
	 * @throws Exception
	 * @author 高研
	 * @version 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "managerOrgTree.action")
	@ResponseBody
	public List<Department> orgTree() throws CustomException {
		return commonService.queryManagerOrgRree();
	}
	
	/**
	  * @description 用户管理角色列表
	  * @param request
	  * @return
	  * @throws Exception
	  * @author 高研
	  * @version 1.0 2014年5月13日 下午2:50:33
	*/
	@RequestMapping(value = "getRolesForUser.action")
	@ResponseBody
	public  Map<String, Object> getRolesForUser(HttpServletRequest request) throws Exception  {
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		List<Role> roleList = commonService.getRolesForUser();
		map.put(GlobalContext.SESSION_TOKEN, token);
		map.put("roleList", roleList);
		return map;
	}
	
	/**
	* @description 获取在线用户登录名
	* @author 
	*/
	@RequestMapping(value = "getOnlineUsers.action")
	@ResponseBody
	public List<UserBean> getOnlineUsers() {
		return commonService.getOnlineUserBean();
	}
	
	/**
	* @description 获取在线用户数
	* @author 
	*/
	@RequestMapping(value = "getOnlineUserCount.action")
	@ResponseBody
	public int getOnlineUserCount() {
		return commonService.getOnlineUserCount();
	}
	
	/**
	 * 根据用户ID获取用户信息
	 * @param id
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年6月24日 下午1:54:06
	 */
	@RequestMapping(value = "getUserById.action")
	@ResponseBody
	public User getUserById(Long id)	{
		return commonService.getUserById(id);
	}
	
	/**
	 * 获得注册表文件
	 * @author 孙圣然
	 * @version 1.0 2014年6月24日 下午1:54:06
	 */
	@RequestMapping(value = "getRegFile.action")
	@ResponseBody
	public void getRegFile(HttpServletRequest request,HttpServletResponse response){
		sendFile(GlobalContext.basePath+"../../install/setup.zip", response);
	}
}