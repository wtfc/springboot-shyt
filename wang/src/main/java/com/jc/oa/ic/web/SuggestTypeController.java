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
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.domain.SuggestType;
import com.jc.oa.ic.domain.validator.SuggestTypeValidator;
import com.jc.oa.ic.service.ISuggestTypeService;
import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;


/**
 * @title GOA V2.0 互动交流
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */
 
@Controller
@RequestMapping(value="/ic/suggestType")
public class SuggestTypeController extends BaseController{
	
	@Autowired
	private ISuggestTypeService suggestTypeService;
	
	@org.springframework.web.bind.annotation.InitBinder("suggestType")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new SuggestTypeValidator()); 
    }
	
	public SuggestTypeController() {
	}

	/**
	 * @description 分页查询方法
	 * @param SuggestType suggestType 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="建议类型设置",operateFuncNm="manageList",operateDescribe="分页查询建议类型") 
	public PageManager manageList(SuggestType suggestType,PageManager page,HttpServletRequest request){
		//默认排序
		if(StringUtils.isEmpty(suggestType.getOrderBy())) {
			suggestType.addOrderByFieldDesc("t.CREATE_DATE");
		}
		//接收人固定状态,当固定状态都被选中时，查询条件中不做处理，查询全部数据
		if("1".equals(request.getParameter("isFixed_t")) && "0".equals(request.getParameter("isFixed_f"))){
			suggestType.setIsFixed("");
		}else{
			if("1".equals(request.getParameter("isFixed_t"))){
				suggestType.setIsFixed("1");
			}else if("0".equals(request.getParameter("isFixed_f"))){
				suggestType.setIsFixed("0");
			}
		}
		request.getParameterMap();
		suggestType.setDeleteFlag(0);
		//处理查询条件中带有“_”的特殊字符转义
		if(!StringUtil.isEmpty(suggestType.getTypeName())){
			suggestType.setTypeName(StringUtil.escapeSQLWildcard(suggestType.getTypeName()));
		}
		//加入组织机构权限
		suggestType.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
		return suggestTypeService.query(suggestType, page);
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-17 
	*/
	@RequestMapping(value="manage.action")
	public String manage(HttpServletRequest request) throws Exception{
		request.setAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));
		return "ic/suggestType/suggestTypeInteract"; 
	}

	/**
	 * @description 删除方法
	 * @param SuggestType suggestType 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17
	 */
	@RequiresPermissions("suggestType:delete")
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="建议类型设置",operateFuncNm="deleteByIds",operateDescribe="逻辑删除建议类型") 
	public Map<String, Object> deleteByIds(SuggestType suggestType,String ids,
			HttpServletRequest request) throws Exception{
		suggestType.setPrimaryKeys(ids.split(","));
		if(suggestTypeService.delete(suggestType) >= 1){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
			return resultMap;
		}
		return null;
	}

	/**
	 * @description 保存方法
	 * @param SuggestType suggestType 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 徐伟平
	 * @version 2014-03-18
	 */
	@RequiresPermissions("suggestType:save")
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="建议类型设置",operateFuncNm="save",operateDescribe="保存建议类型") 
	public Map<String, Object> save(@Valid SuggestType suggestType,
			BindingResult result, HttpServletRequest request) throws CustomException {
		Map<String, Object> resultMap = null;
		try {
			// 验证bean
			resultMap = validateBean(result);
			if (resultMap.size() > 0) {
				return resultMap;
			}
			// 判断建议类别名称是否存在
			SuggestType m = new SuggestType();
			m.setTypeName(suggestType.getTypeName());
			m.setDeleteFlag(0);
			m.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
			if (suggestTypeService.get(m) != null) {
				IcException ce = new IcException();
				ce.setLogMsg(MessageUtils.getMessage("JC_SYS_036",new String[]{"类型名称"}));
				throw ce;
			}
			// 验证token
			resultMap = validateToken(request);
			if (resultMap.size() > 0) {
				return resultMap;
			}

			// 保存建议类别
			if (suggestTypeService.save(suggestType) == 1) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
				resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));
			}
		} catch (IcException e) {
			if(e.getLogMsg() == null){
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_002"));
			}else{
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, e.getLogMsg());
			}
		}
		return resultMap;
	}

	/**
	 * @description 修改方法
	 * @param SuggestType suggestType 实体类
	 * @return Integer 更新的数目
	 * @author 徐伟平
	 * @version 2014-03-18
	 */
	@RequiresPermissions("suggestType:update")
	@RequestMapping(value = "update.action")
	@ResponseBody
	@ActionLog(operateModelNm="建议类型设置",operateFuncNm="update",operateDescribe="保存修改建议类型") 
	public Map<String, Object> update(SuggestType suggestType, BindingResult result,
			HttpServletRequest request) throws CustomException {
		Map<String, Object> resultMap = null;
		try {
			// 验证bean
			resultMap = validateBean(result);
			if (resultMap.size() > 0) {
				return resultMap;
			}
			Integer flag = suggestTypeService.update(suggestType);
			if (flag == 1) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
				resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));
			}
		} catch (IcException e) {
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_002"));
		}
		return resultMap;
	}

	/**
	 * @description 获取单条记录方法
	 * @param SuggestType suggestType 实体类
	 * @return SuggestType 查询结果
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="建议类型设置",operateFuncNm="get",operateDescribe="获取一条建议类型") 
	public SuggestType get(SuggestType suggestType,
			HttpServletRequest request) throws Exception{
		return suggestTypeService.get(suggestType);
	}
	/**
	 * 验证类型名称是否存在
	 * @param SuggestType suggestType 实体类
	 * @return String true不存在	false存在
	 * @throws Exception
	 * @author
	 * @version 2014-06-30
	 */
	@RequestMapping(value = "checkName.action")
	@ResponseBody
	public String checkName(SuggestType suggestType,String oldName,HttpServletRequest request) throws Exception {
		if(!StringUtils.isEmpty(suggestType.getTypeName()) && !suggestType.getTypeName().equals(oldName)){
			suggestType.setDeleteFlag(0);
			suggestType.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
			if(suggestTypeService.get(suggestType) != null){
				return "false";
			} else {
				return "true";
			}
		} else {
			return "true";
		}
	}
	
	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author weny
	* @version  2014-09-28 
	*/
	@RequestMapping(value="suggestTypeDiv.action")
	public String suggestTypeDiv(HttpServletRequest request) throws Exception{
		return "ic/suggestType/suggestTypeInteractDiv"; 
	}

}