package com.jc.oa.ic.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.ic.domain.SmsStatistic;
import com.jc.oa.ic.domain.validator.SmsStatisticValidator;
import com.jc.oa.ic.service.ISmsStatisticService;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;


/**
 * @title GOA V2.0
 * @description 互动交流controller类
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 宋海涛
 * @version  2014年6月5日下午6:33:32
 */
@Controller
@RequestMapping(value="/ic/smsStatistic")
public class SmsStatisticController extends BaseController{
	
	@Autowired
	private ISmsStatisticService smsStatisticService;
	
	@org.springframework.web.bind.annotation.InitBinder("smsStatistic")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new SmsStatisticValidator()); 
        	binder.registerCustomEditor(Date.class,"statisticsMonth", new CustomDateEditor(new  SimpleDateFormat("yyyy-MM"),true));
    }
	
	public SmsStatisticController() {
	}

	/**
	 * 分页查询方法
	 * @param SmsStatistic smsStatistic 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author	宋海涛
	 * @version  2014-06-05 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="短信",operateFuncNm="manageList",operateDescribe="对短信统计进行查询操作")
	public PageManager manageList(SmsStatistic smsStatistic,PageManager page,HttpServletRequest request){
		smsStatistic.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
		PageManager page_ = smsStatisticService.query(smsStatistic, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author	宋海涛
	* @version  2014-06-05 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="短信",operateFuncNm="manage",operateDescribe="对进行跳转操作")
	public String manage(HttpServletRequest request) throws Exception{
		return "ic/smsStatistic/smsStatisticInteract"; 
	}

/**
	 * 删除方法
	 * @param SmsStatistic smsStatistic 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author	宋海涛
	 * @version  2014-06-05
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="短信",operateFuncNm="deleteByIds",operateDescribe="对短信统计进行删除")
	public Map<String, Object> deleteByIds(SmsStatistic smsStatistic,String ids,HttpServletRequest request) throws Exception{
		smsStatistic.setPrimaryKeys(ids.split(","));
		if(smsStatisticService.delete(smsStatistic) == 1){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
			return resultMap;
		}
		return null;
	}

	/**
	 * 保存方法
	 * @param SmsStatistic smsStatistic 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author	宋海涛
	 * @version  2014-06-05
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="短信",operateFuncNm="save",operateDescribe="对短信统计进行新增操作")
	public Map<String,Object> save(@Valid SmsStatistic smsStatistic,BindingResult result,
			HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		if(!"false".equals(resultMap.get("success"))){
			smsStatisticService.save(smsStatistic);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param SmsStatistic smsStatistic 实体类
	* @return Integer 更新的数目
	* @author	宋海涛
	* @version  2014-06-05 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="短信",operateFuncNm="update",operateDescribe="对短信统计进行更新操作")
	public Map<String, Object> update(SmsStatistic smsStatistic, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = smsStatisticService.update(smsStatistic);
		if (flag == 1) {
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param SmsStatistic smsStatistic 实体类
	 * @return SmsStatistic 查询结果
	 * @throws Exception
	 * @author  宋海涛
	 * @version  2014-06-05
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="短信",operateFuncNm="get",operateDescribe="对短信统计进行单条查询操作")
	public SmsStatistic get(SmsStatistic smsStatistic,HttpServletRequest request) throws Exception{
		return smsStatisticService.get(smsStatistic);
	}

}