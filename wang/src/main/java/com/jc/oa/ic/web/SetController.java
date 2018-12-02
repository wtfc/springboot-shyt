package com.jc.oa.ic.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.ic.domain.SetSms;
import com.jc.oa.ic.domain.validator.SetValidator;
import com.jc.oa.ic.service.ISetService;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.Role;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;

 /**
  * 
  * @title GOA V2.0
  * @description  controller类
  * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
  * Company 长春嘉诚网络工程有限公司
  * @author 宋海涛
  * @version  2014年5月12日上午8:39:57
  */
@Controller
@RequestMapping(value="/ic/set")
public class SetController extends BaseController{
	
	@Autowired
	private ISetService setService;
	
	@org.springframework.web.bind.annotation.InitBinder("setSms")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new SetValidator()); 
    }
	
	public SetController() {
	}

	/**
	 * 方法描述：分页查询方法
	 * @param set
	 * @param page
	 * @return
	 * @author 宋海涛
	 * @version  2014年5月14日上午8:29:04
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="manageList",operateDescribe="对短信设置进行查询")
	@RequestMapping(value="manageList.action")
	@ResponseBody
	public PageManager manageList(SetSms set,PageManager page,HttpServletRequest request){
		if(set.getSetDateEnd()!=null){
	        set.setSetDateEnd(DateUtils.fillTime(set.getSetDateEnd()));
		}
		if(StringUtils.isEmpty(set.getOrderBy())) {
			set.addOrderByFieldDesc("t.START_DATE");
		}
		if("-1".equals(set.getSetType())){
			set.setSetType(null);
		}
		//获取当前人所在机构
		set.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
		PageManager page_ = setService.query(set, page);
		return page_; 
	}
	
	/**
	 * 方法描述：跳转方法
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月14日上午8:29:16
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="manage",operateDescribe="对短信设置进行访问")
	@RequestMapping(value="manage.action")
	public String manage(HttpServletRequest request) throws Exception{
		return "ic/set/setIc"; 
	}
	
	/**
	 * 方法描述：删除方法
	 * @param set
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月14日上午8:29:29
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="deleteByIds",operateDescribe="对短信设置进行删除")
	@RequiresPermissions("smsSet:delete")
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Map<String, Object> deleteByIds(SetSms set,String ids,HttpServletRequest request) throws Exception{
		set.setPrimaryKeys(ids.split(","));
		if(setService.delete(set) >0){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
			return resultMap;
		}
		return null;
	}

	/**
	 * @description 保存方法
	 * @param Set set 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author	宋海涛
	 * @version  2014-04-29
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="save",operateDescribe="对短信设置进行保存")
	@RequiresPermissions("smsSet:add")
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid SetSms set,BindingResult result,
			HttpServletRequest request) throws Exception{
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
		if(set.getRankIdSelect()!=null&&!"".equals(set.getRankIdSelect())){
			set.setRankId(set.getRankIdSelect());
		}
		// 保存设置
		if (setService.save(set) == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param Set set 实体类
	* @return Integer 更新的数目
	* @author	宋海涛
	* @version  2014-04-29 
	*/
	@ActionLog(operateModelNm="短信",operateFuncNm="update",operateDescribe="对短信设置进行修改")
	@RequiresPermissions("smsSet:update")
	@RequestMapping(value="update.action")
	@ResponseBody
	public Map<String, Object> update(@Valid SetSms set,BindingResult result,
			HttpServletRequest request) throws Exception{
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		//去掉rankId中的“，”
		if(set.getRankIdSelect()!=null&&!"".equals(set.getRankIdSelect())){
			set.setRankId(set.getRankIdSelect());
		}
		Integer flag = setService.update(set);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	 * @description 获取单条记录方法
	 * @param Set set 实体类
	 * @return Set 查询结果
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014-04-29
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public SetSms get(SetSms set,HttpServletRequest request) throws Exception{
		return setService.get(set);
	}
	
	/**
	 * 方法描述：获取当前登陆人id和显示名
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月14日上午8:29:44
	 * @see
	 */
	@RequestMapping(value="getUser.action")
	@ResponseBody
	public Map<String, Object> getUser(HttpServletRequest request) throws Exception{
		User userInfo = SystemSecurityUtils.getUser();
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("userId", userInfo.getId());
		userMap.put("displayName",userInfo.getDisplayName());
		return userMap;
	}
	
	
	/**
	 * 方法描述:名称是否重复
	 * @param set
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月13日下午8:45:55
	 * @see
	 */
	@RequestMapping(value="checkName.action")
	@ResponseBody
	public String checkName(SetSms set,HttpServletRequest request) throws Exception{
		
		set.setDeleteFlag(0);
		set.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
		if(setService.get(set) == null){
			return "true";
		} else {
			return "false";
		}
	}
	
	/**
	 * 方法描述：弹出新增
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年10月9日下午2:44:11
	 * @see
	 */
	@RequestMapping(value="setSmsEdit.action")
	public String setSmsEdit(Model model,HttpServletRequest request) throws Exception{
		model.addAttribute("userName", SystemSecurityUtils.getUser().getDisplayName());
		model.addAttribute("effectTime",DateFormatUtils.format(new Date(),"yyyy-MM-dd hh:mm:ss"));
		String token = getToken(request);
		model.addAttribute(GlobalContext.SESSION_TOKEN, token);
		return "ic/set/setSmsEdit";
	}
	/**
	 * 方法描述:获取当前时间
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 杨佶
	 * @version  2015年3月25日下午2:44:11
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value="getFormatDate.action")
	public String getFormatDate(){
		String FormatDate= DateFormatUtils.format(new Date(),"yyyy-MM-dd hh:mm:ss");

		return FormatDate;
	}
}