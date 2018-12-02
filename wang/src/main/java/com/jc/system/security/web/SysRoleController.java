package com.jc.system.security.web;

import javax.validation.Valid;

import java.util.HashMap;
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
import com.jc.system.security.domain.validator.SysRoleValidator;
import com.jc.system.security.service.ISysRoleService;


/**
 * @title GOA2.0
 * @description 角色信息基本表 controller类
 * @author 
 * @version  2014-04-15
 *
 */
@Controller
@RequestMapping(value="/system/sysRole")
public class SysRoleController extends BaseController{
	
	@Autowired
	private ISysRoleService sysRoleService;
	
	@org.springframework.web.bind.annotation.InitBinder("sysRole")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new SysRoleValidator()); 
    }
	
	public SysRoleController() {
	}

	/**
	 *  分页查询方法
	 * @param SysRole sysRole 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-15 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	public PageManager manageList(SysRole sysRole,final PageManager page,
			HttpServletRequest request) {
		return sysRoleService.query(sysRole, page);
	}
	
	@RequestMapping(value="roleList.action")
	@ResponseBody
	public List<SysRole> roleList(SysRole sysRole,final PageManager page,
			HttpServletRequest request) {
		List<SysRole> list = sysRoleService.queryAll(sysRole);
		return list;
	}

	/**
	*  跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-15 
	*/
	@RequestMapping(value="manage.action")
	public String manage() throws Exception{
		return "sys/sysRole/sysRoleAaa"; 
	}

	/**
	 *  删除方法
	 * @param SysRole sysRole 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-04-15
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Integer deleteByIds(SysRole sysRole,String ids) throws Exception{
		sysRole.setPrimaryKeys(ids.split(","));
		sysRoleService.deleteByIds(sysRole);
		return 1;
	}

	/**
	 *  保存方法
	 * @param SysRole sysRole 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-04-15
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid SysRole sysRole,BindingResult result) throws Exception{
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
		sysRoleService.save(sysRole);
		resultMap.put("success", "true");
		return resultMap;
	}

	/**
	*  修改方法
	* @param SysRole sysRole 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-04-15 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	public Integer update(SysRole sysRole) throws Exception{
		Integer flag = sysRoleService.update(sysRole);
		return flag;
	}

	/**
	 *  获取单条记录方法
	 * @param SysRole sysRole 实体类
	 * @return SysRole 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-15
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public SysRole get(SysRole sysRole) throws Exception{
		return sysRoleService.get(sysRole);
	}

}