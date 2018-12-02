package com.jc.system.security.web;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.WebDataBinder;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.security.domain.SysRole;
import com.jc.system.security.domain.SysUserRole;
import com.jc.system.security.domain.validator.SysUserRoleValidator;
import com.jc.system.security.service.ISysUserRoleService;


/**
 * @title GOA2.0
 * @description  controller类
 * @version  2014-04-15
 *
 */
@Controller
@RequestMapping(value="/sysUserRole")
public class SysUserRoleController extends BaseController{
	
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	
	@org.springframework.web.bind.annotation.InitBinder("sysUserRole")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new SysUserRoleValidator()); 
    }
	
	public SysUserRoleController() {
	}

	/**
	 * @description 分页查询方法
	 * @param SysUserRole sysUserRole 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-15 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	public PageManager manageList(SysUserRole sysUserRole,final PageManager page,
			HttpServletRequest request) {
		return sysUserRoleService.query(sysUserRole, page);
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-15 
	*/
	@RequestMapping(value="manage.action")
	public String manage() throws Exception{
		return "sys/sysUserRole/sysUserRoleAaa"; 
	}

	/**
	 * @description 删除方法
	 * @param SysUserRole sysUserRole 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-04-15
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Integer deleteByIds(SysUserRole sysUserRole,String ids) throws Exception{
		sysUserRole.setPrimaryKeys(ids.split(","));
		sysUserRoleService.delete(sysUserRole);
		return 1;
	}

	/**
	 * @description 保存方法
	 * @param SysUserRole sysUserRole 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-04-15
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid SysUserRole sysUserRole,BindingResult result) throws Exception{
		Map<String,Object> resultMap = new HashMap<String, Object>();
		if (result.hasErrors()) {
			String errorStr ="";
			List<ObjectError> errorList=result.getAllErrors();
			for(ObjectError error:errorList){
				errorStr+=error.getDefaultMessage()+",";
			}
			resultMap.put("errorMessage", errorStr);
			resultMap.put("success", "false");
			return resultMap;
		}
		sysUserRoleService.save(sysUserRole);
		resultMap.put("success", "true");
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param SysUserRole sysUserRole 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-04-15 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	public Integer update(SysUserRole sysUserRole) throws Exception{
		Integer flag = sysUserRoleService.update(sysUserRole);
		return flag;
	}

	/**
	 * @description 获取单条记录方法
	 * @param SysUserRole sysUserRole 实体类
	 * @return SysUserRole 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-15
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public SysUserRole get(SysUserRole sysUserRole) throws Exception{
		return sysUserRoleService.get(sysUserRole);
	}
	
	/**
	 * @description 根据角色ID获得所有用户id集合
	 * @param SysUserRole sysUserRole 实体类
	 * @return SysUserRole 查询结果
	 * @throws Exception
	 */
	@RequestMapping(value="getUserIdByRoleId.action")
	@ResponseBody
	public List<SysUserRole> getUserIdByRoleId(SysUserRole sysUserRole) throws Exception{
		return sysUserRoleService.queryAll(sysUserRole);
	}

}