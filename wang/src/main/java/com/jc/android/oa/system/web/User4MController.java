package com.jc.android.oa.system.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.android.oa.system.domain.User4M;
import com.jc.foundation.web.BaseController;
import com.jc.system.CustomException;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IDepartmentService;
import com.jc.system.security.service.IUserService;

/**
 * 
 * @title 移动端action处理类
 * @description 
 * @version  2014年9月11日
 */
@Controller
@RequestMapping(value = "/android/system/user/")
public class User4MController extends BaseController{

	@Autowired
	private IUserService userService;
	@Autowired
	private IDepartmentService deptService;
	
	/**
	 * 根据用户ID查询用户信息
	 * @param id 用户ID
	 * @param request request
	 * @return User4M 用户信息
	 * @throws Exception
	 */
	@RequestMapping(value = "getUserById4M.action")
	@ResponseBody
	public Map<String, Object> getUserById4M(String id, HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(id)){
			resultMap.put("state", "success");
			resultMap.put("data", userService.getUser4M(Long.parseLong(id)));
		} else {
			resultMap.put("state", "failure");
		}
		return resultMap;
	}
	
	/**
	 * 查询所有用户
	 * @param user4m 查询对象
	 * @param request request
	 * @return List<User4M> 查询结果集
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAllFor4M.action")
	@ResponseBody
	public Map<String, Object> queryAllFor4M(User4M user4m, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "success");
		resultMap.put("data", userService.queryAll4M(user4m));
		return resultMap;
	}
	
	
	/**
	 * 根据登录用户获取组织人员
	 * @param id	用户ID
	 * @param request
	 * @return
	 * @author 张继伟
	 * @version 1.0 2014年11月11日 下午2:20:09
	 * @throws CustomException 
	 */
	@RequestMapping(value = "queryDeptAndUserFor4M.action")
	@ResponseBody
	public Map<String, Object> queryDeptAndUserFor4M(String id, HttpServletRequest request) throws CustomException {
		User userInfo = userService.getUser(new Long(id));
		return deptService.queryDeptAndUserFor4M(userInfo);
	}
}
