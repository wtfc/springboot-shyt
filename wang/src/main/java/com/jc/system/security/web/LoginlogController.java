package com.jc.system.security.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.jc.system.portal.util.PortalUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.Loginlog;
import com.jc.system.security.domain.User;
import com.jc.system.security.domain.validator.LoginlogValidator;
import com.jc.system.security.service.ILoginlogService;
import com.jc.system.security.util.ActionLog;


/**
 * @title GOA2.0
 * @description  controller类
 * @author 
 * @version  2014-05-04
 */
 
@Controller
@RequestMapping(value="/sys/loginlog")
public class LoginlogController extends BaseController{
	
	@Autowired
	private ILoginlogService loginlogService;
	
	@org.springframework.web.bind.annotation.InitBinder("loginlog")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new LoginlogValidator()); 
    }
	
	public LoginlogController() {
	}

	/**
	 * @description 分页查询方法
	 * @param Loginlog loginlog 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-04 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="登录日志",operateFuncNm="manageList",operateDescribe="对登录日志进行查询")
	public PageManager manageList(Loginlog loginlog,PageManager page,HttpServletRequest request){
		//默认排序
		if(StringUtils.isEmpty(loginlog.getOrderBy())) {
			loginlog.addOrderByFieldDesc("(case when t.LOGIN_TIME is null then t.LOGOUT_TIME else t.LOGIN_TIME end)");
		}
		PageManager page_ = loginlogService.query(loginlog, page);
		return page_; 
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-05-04 
	*/
	@RequestMapping(value="manage.action")
	public String manage() throws Exception{
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			if(userInfo.getIsAdmin() == 1 || userInfo.getIsSystemAdmin()){
				return "sys/loginlog/loginlog";
			} else {
				return "error/unauthorized";
			}
		}
		return "error/unauthorized";
	}

/**
	 * @description 删除方法
	 * @param Loginlog loginlog 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-05-04
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Integer deleteByIds(Loginlog loginlog,String ids) throws Exception{
		loginlog.setPrimaryKeys(ids.split(","));
		return loginlogService.delete(loginlog);
	}

	/**
	 * @description 保存方法
	 * @param Loginlog loginlog 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-05-04
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid Loginlog loginlog,BindingResult result) throws Exception{
		
		Map<String,Object> resultMap = validateBean(result);
		if(!"false".equals(resultMap.get("success"))){
			loginlogService.save(loginlog);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param Loginlog loginlog 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-05-04 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	public Integer update(Loginlog loginlog) throws Exception{
		Integer flag = loginlogService.update(loginlog);
		return flag;
	}

	/**
	 * @description 获取单条记录方法
	 * @param Loginlog loginlog 实体类
	 * @return Loginlog 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-04
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public Loginlog get(Loginlog loginlog) throws Exception{
		return loginlogService.get(loginlog);
	}
	
	/**
	* @description 门户组件数据源（测试）
	* @return String 跳转的路径
	* @throws Exception
	* @author	chz
	* @version  2014-07-10 
	*/
	@RequestMapping(value="manageloginlog.action")
	public String manageloginlog(Model model,Loginlog loginlog,HttpServletRequest request) throws Exception{
		//门户引用类固定代码---start---
		model = PortalUtils.getPortalParameter(model, request);
	    //门户引用类固定代码---end---
		request.getParameter("parameter");//携带业务参数
	    //门户数据权限应用---产品用--start---
		//Map<String, String> data = new HashMap<String, String>();
		//data = PortalUtils.getPortalParameter(request);
	    /*data.get("userids");
	    data.get("roleids");
	    data.get("deptids");
	    data.get("organids");*/
	    //门户数据权限应用---产品用--end---
		
		if(StringUtils.isEmpty(loginlog.getOrderBy())) {
			loginlog.addOrderByFieldDesc("(case when t.LOGIN_TIME is null then t.LOGOUT_TIME else t.LOGIN_TIME end)");
		}
		List<Loginlog> returnlist = loginlogService.queryAll(loginlog);
		model.addAttribute("loginlogList", returnlist);
		model.addAttribute("loginlogListSize", returnlist.size());
		return "sys/loginlog/loginlogPortlet"; 
	}

}