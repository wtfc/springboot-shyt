package com.jc.oa.ic.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.ic.domain.In;
import com.jc.oa.ic.domain.validator.InValidator;
import com.jc.oa.ic.service.IInService;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.util.ActionLog;


/**
 * @title HR
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-29
 */
 
@Controller
@RequestMapping(value="/ic/in")
public class InController extends BaseController{
	
	@Autowired
	private IInService inService;
	
	@org.springframework.web.bind.annotation.InitBinder("in")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new InValidator()); 
    }
	
	public InController() {
	}

	/**
	 * 方法描述：分页查询方法
	 * @param in 实体类
	 * @param page 分页实体类
	 * @return
	 * @author 宋海涛
	 * @version  2014年5月13日下午8:06:18
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="manageList",operateDescribe="对收信箱进行查询")
	@RequestMapping(value="manageList.action")
	@ResponseBody
	public PageManager manageList(In in,PageManager page,HttpServletRequest request){
		if(in.getInDateEnd()!=null){
	        in.setInDateEnd(DateUtils.fillTime(in.getInDateEnd()));
		}
		//默认排序
		if(StringUtils.isEmpty(in.getOrderBy())) {
			in.addOrderByFieldDesc("t.RECEIVE_DATE");
		}
		PageManager page_ = inService.query(in, page);
		return page_; 
	}

	/**
	 * 方法描述：跳转方法
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月13日下午8:06:48
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="manage",operateDescribe="对收信箱进行访问")
	@RequestMapping(value="manage.action")
	public String manage(HttpServletRequest request) throws Exception{
		return "ic/in/inIc"; 
	}

	/**
	 * 方法描述： 删除方法
	 * @param in 实体类
	 * @param ids 删除的多个主键
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月13日下午8:07:05
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="deleteByIds",operateDescribe="对收信箱进行删除")
	@RequiresPermissions("in:delete")
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Map<String, Object> deleteByIds(In in,String ids,HttpServletRequest request) throws Exception{
		in.setPrimaryKeys(ids.split(","));
		if(inService.delete(in) >0){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
			return resultMap;
		}
		return null;
	}

	/**
	 * 方法描述：保存方法
	 * @param in 实体类
	 * @param result 校验结果
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月13日下午8:07:29
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="save",operateDescribe="对收信箱进行保存")
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid In in,BindingResult result,
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
		// 保存用户
		if (inService.save(in) == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
		}
		return resultMap;
	}
	
	/**
	 * 方法描述：修改方法
	 * @param in 实体类
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月13日下午8:07:50
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="update",operateDescribe="对收信箱进行修改")
	@RequestMapping(value="update.action")
	@ResponseBody
	public Map<String, Object> update(In in, BindingResult result,
			HttpServletRequest request) throws Exception{
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		if (inService.update(in) == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		return resultMap;
	}
	
	/**
	 * 方法描述： 获取单条记录方法
	 * @param in
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月13日下午8:08:07
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="get",operateDescribe="查询收信箱单个对象")
	@RequestMapping(value="get.action")
	@ResponseBody
	public In get(In in,HttpServletRequest request) throws Exception{
		in.setDeleteFlag(0);
		return inService.get(in);
	}

}