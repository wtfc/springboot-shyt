package com.jc.oa.ic.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.ic.domain.Out;
import com.jc.oa.ic.domain.validator.OutValidator;
import com.jc.oa.ic.service.IOutService;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.util.ActionLog;

/**
 * @title GOA V2.0
 * @description controller类
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 宋海涛
 * @version  2014年5月6日下午7:11:04
 */
@Controller
@RequestMapping(value="/ic/out")
public class OutController extends BaseController{
	
	@Autowired
	private IOutService outService;
	
	@org.springframework.web.bind.annotation.InitBinder("out")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new OutValidator()); 
        	binder.registerCustomEditor(Date.class,"statisticSendDate", new CustomDateEditor(new  SimpleDateFormat("yyyy-MM"),true));
    }
	
	public OutController() {
	}

	/**
	 * @description 分页查询方法
	 * @param Out out 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014-04-29 
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="manageList",operateDescribe="对短信进行查询")
	@RequestMapping(value="manageList.action")
	@ResponseBody
	public PageManager manageList(Out out,PageManager page,HttpServletRequest request){
		PageManager page_ = outService.query(out, page);
		return page_; 
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author 宋海涛
	* @version  2014-04-29 
	*/
	@ActionLog(operateModelNm="短信",operateFuncNm="manage",operateDescribe="对发信短信进行访问")
	@RequestMapping(value="manage.action")
	public String manage(Model model, HttpServletRequest request) throws Exception{
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DATE,-1);
		model.addAttribute("today",ca.getTime());
		String token = getToken(request);
		model.addAttribute(GlobalContext.SESSION_TOKEN, token);
		return "ic/out/outIc"; 
	}

	/**
	 * @description 删除方法
	 * @param Out out 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014-04-29
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="deleteByIds",operateDescribe="对发信箱进行删除")
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Map<String, Object> deleteByIds(Out out,String ids,HttpServletRequest request) throws Exception{
		out.setPrimaryKeys(ids.split(","));
		if(outService.delete(out) == 1){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
			return resultMap;
		}
		return null;
	}

	/**
	 * @description 保存方法
	 * @param Out out 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014-04-29
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="save",operateDescribe="对短信进行保存")
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid Out out,BindingResult result,
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
		if (outService.save(out) == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param Out out 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-04-29 
	*/
	@ActionLog(operateModelNm="短信",operateFuncNm="update",operateDescribe="对短信进行修改")
	@RequestMapping(value="update.action")
	@ResponseBody
	public Map<String, Object> update(Out out, BindingResult result,
			HttpServletRequest request) throws Exception{
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		if (outService.update(out) == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		return resultMap;
	}

	/**
	 * @description 获取单条记录方法
	 * @param Out out 实体类
	 * @return Out 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-29
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="get",operateDescribe="进行单个对象查询")
	@RequestMapping(value="get.action")
	@ResponseBody
	public Out get(Out out,HttpServletRequest request) throws Exception{
		out.setDeleteFlag(0);
		return outService.get(out);
	}
	
	/**
	 * 方法描述：发送保存短信方法
	 * @param out
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月7日上午8:50:40
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="sendAndSave",operateDescribe="发送短信")
	@RequestMapping(value="sendAndSave.action")
	@ResponseBody
	public Map<String,Object> sendAndSave(@Valid Out out,BindingResult result,
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
		//验证是否有可发短信
//		resultMap = outService.isHaveSendMes(out);
//		if (resultMap.size() > 0) {
//			return resultMap;
//		}
//		
		//保存成功返回1
		if ( outService.sendAndSave(out) == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_OA_IC_073"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/**
	 * 方法描述：发信箱分页查询
	 * @param out
	 * @param page
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月7日上午8:51:17
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="outList",operateDescribe="对发信箱进行查询")
	@RequestMapping(value="outList.action")
	@ResponseBody
	public PageManager outList(Out out,PageManager page,HttpServletRequest request) throws Exception{
		//默认排序
		if(StringUtils.isEmpty(out.getOrderBy())) {
			out.addOrderByFieldDesc("sentDate");
		}
		PageManager page_ = outService.queryOut(out, page);
		return page_; 
	}
	
	/**
	 * 方法描述：跳转方法
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月7日上午8:51:47
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="outManage",operateDescribe="对发信箱进行访问")
	@RequestMapping(value="outManage.action")
	public String outManage(HttpServletRequest request) throws Exception{
		return "ic/out/sendIc"; 
	}
	
	/**
	 * 方法描述:获得手机号
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月13日下午8:45:55
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="findMobileNum",operateDescribe="对电话号码进行查询")
	@RequestMapping(value="findMobileNum.action")
	@ResponseBody
	public String findMobileNum(HttpServletRequest request) throws Exception{
		String id = request.getParameter("userId");
		String mobile = outService.getMobile(id);
		return mobile;
	}
	
	/**
	 * 方法描述:是否存在手机号
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月13日下午8:45:55
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="isHaveMobile",operateDescribe="对是否存在手机号进行查询")
	@RequestMapping(value="isHaveMobile.action")
	@ResponseBody
	public Map<String,Object> isHaveMobile(Out out,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = outService.isHaveMobile(out);
		if(resultMap.size()<=0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "success");
		}
		return resultMap;
	}
	
	
	/**
	 * 方法描述：获取短信前缀
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年6月6日下午3:05:43
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="addmSgPrefix",operateDescribe="查询短信前缀")
	@RequestMapping(value="addmSgPrefix.action")
	@ResponseBody
	public Map<String,Object> addmSgPrefix(HttpServletRequest request) throws Exception{
		return outService.addmSgPrefix(); 
	}
	
	
	/**
	 * 方法描述：查看个人姓名长度
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年6月9日上午11:28:00
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="addName",operateDescribe="查询个人姓名长度")
	@RequestMapping(value="addName.action")
	@ResponseBody
	public Integer addName(HttpServletRequest request) throws Exception{
		return outService.addName(); 
	}
	
	/**
	 * 方法描述：判断短信功能是否开启
	 * @param out
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version 2014年6月9日上午11:28:00
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="mesFunctionIsOpen",operateDescribe="短信功能是否开启")
	@RequestMapping(value="mesFunctionIsOpen.action")
	@ResponseBody
	public Map<String,Object> mesFunctionIsOpen(Out out,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = outService.mesFunctionIsOpen();
		return resultMap;
	}
	
	/**
	 * 方法描述：校验短信发送
	 * @param out
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年10月9日上午10:44:25
	 * @see
	 */
	@ActionLog(operateModelNm="短信",operateFuncNm="sendValidate",operateDescribe="短信发送校验")
	@RequestMapping(value="sendValidate.action")
	@ResponseBody
	public Map<String,Object> sendValidate(Out out,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = outService.sendValidate(out);
		return resultMap;
	}
}