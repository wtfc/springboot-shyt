package com.jc.system.security.web;

import java.util.HashMap;
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
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.domain.UserIp;
import com.jc.system.security.domain.validator.UserIpValidator;
import com.jc.system.security.service.IUserIpService;
import com.jc.system.security.util.ActionLog;


/**
 * @title GOA2.0
 * @description  controller类
 * @author 
 * @version  2014-04-30
 */
 
@Controller
@RequestMapping(value="/sys/userIp")
public class UserIpController extends BaseController{
	
	@Autowired
	private IUserIpService userIpService;
	
	@org.springframework.web.bind.annotation.InitBinder("userIp")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new UserIpValidator()); 
    }
	
	public UserIpController() {
	}
	
	private UserIpValidator uiv = new UserIpValidator();

	/**
	 * @description 分页查询方法
	 * @param UserIp userIp 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-30 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="IP绑定",operateFuncNm="manageList",operateDescribe="对IP绑定进行查询")
	public PageManager manageList(UserIp userIp,PageManager page, HttpServletRequest request){
		//默认排序
		if(StringUtils.isEmpty(userIp.getOrderBy())) {
			userIp.addOrderByFieldDesc("t.MODIFY_DATE");
		}
		PageManager page_ = userIpService.query(userIp, page);
		return page_; 
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-30 
	*/
	@RequestMapping(value="manage.action")
	public String manage() throws Exception{
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			if(userInfo.getIsAdmin() == 1 || userInfo.getIsSystemAdmin()){
				return "sys/userIp/userIp";
			} else {
				return "error/unauthorized";
			}
		}
		return "error/unauthorized";
	}
	
	@RequestMapping(value = "userIpEdit.action")
	public String userIpEdit(Model model, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "sys/userIp/userIpEdit";
	}

/**
	 * @description 删除方法
	 * @param UserIp userIp 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-04-30
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="IP绑定",operateFuncNm="deleteByIds",operateDescribe="对IP绑定进行删除") 
	public Integer deleteByIds(UserIp userIp,String ids,HttpServletRequest request) throws Exception{
		userIp.setPrimaryKeys(ids.split(","));
		userIp.setModifyDate(DateUtils.getSysDate());
		return userIpService.delete(userIp);
	}

	/**
	 * @description 保存方法
	 * @param UserIp userIp 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-04-30
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="IP绑定",operateFuncNm="save",operateDescribe="对IP绑定进行保存")
	public Map<String,Object> save(@Valid UserIp userIp,BindingResult result,HttpServletRequest request) throws Exception{
		
		uiv.validate(userIp, result);
		
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
			if(userIp.getUserStartIp() != null && userIp.getUserStartIp().split(",").length > 1)
				userIp.setUserStartIp(userIp.getUserStartIp().split(",")[1]);
			else 
				userIp.setUserStartIp(userIp.getUserStartIp().split(",")[0]);
			userIp.setCreateDate(DateUtils.getSysDate());
			userIp.setModifyDate(DateUtils.getSysDate());
			userIpService.save(userIp);
			resultMap.put("success", "true");
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param UserIp userIp 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-04-30 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="IP绑定",operateFuncNm="update",operateDescribe="对IP绑定进行修改")
	public Map<String, Object> update(UserIp userIp,BindingResult result,HttpServletRequest request) throws Exception{
		
		uiv.validate(userIp, result);
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		if(userIp.getUserStartIp() != null && userIp.getUserStartIp().split(",").length > 1){
			if(userIp.getBindType() == 1)
				userIp.setUserStartIp(userIp.getUserStartIp().split(",")[0]);
			else
				userIp.setUserStartIp(userIp.getUserStartIp().split(",")[1]);
		} else 
			userIp.setUserStartIp(userIp.getUserStartIp().split(",")[0]);
		userIp.setModifyDate(DateUtils.getSysDate());
		Integer flag = userIpService.update(userIp);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	 * @description 获取单条记录方法
	 * @param UserIp userIp 实体类
	 * @return UserIp 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-30
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public UserIp get(UserIp userIp,HttpServletRequest request) throws Exception{
		return userIpService.get(userIp);
	}
	
	/**
	 * @description 验证用户是否存在绑定ip
	 * @param User user 实体类
	 * @return String true不存在	false存在
	 * @throws Exception
	 * @author
	 * @version 2014-05-16
	 */
	@RequestMapping(value = "checkRepeatName.action")
	@ResponseBody
	public Map<String, Object> checkRepeatName(UserIp userIp, Long userId, Long userIpid) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(userIpid == null){
			if(userId != null){
				userIp.setDeleteFlag(0);
				userIp.setUserId(userId);
				if(userIpService.get(userIp) == null){
					resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				} else {
					resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
				}
			} else {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			}
		}else {
			if(userId != null){
				UserIp tempuserIp = new UserIp();
				tempuserIp.setDeleteFlag(0);
				tempuserIp.setUserId(userId);
				tempuserIp.setId(userIpid);
				
				userIp.setDeleteFlag(0);
				userIp.setUserId(userId);
				if(userIpService.get(tempuserIp) != null || userIpService.queryAll(userIp).size() == 0){
					resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				} else {
					resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
				}
			} else {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			}
		}
		return resultMap;
	}

}