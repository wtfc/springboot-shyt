package com.jc.oa.ic.web;

import java.util.HashMap;
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
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.domain.MailSign;
import com.jc.oa.ic.domain.Mailbox;
import com.jc.oa.ic.domain.Suggest;
import com.jc.oa.ic.domain.validator.MailSignValidator;
import com.jc.oa.ic.service.IMailSignService;
import com.jc.system.CustomException;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
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
@RequestMapping(value="/ic/mailSign")
public class MailSignController extends BaseController{
	
	@Autowired
	private IMailSignService mailSignService;
	
	@org.springframework.web.bind.annotation.InitBinder("mailSign")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new MailSignValidator()); 
    }
	
	public MailSignController() {
	}

	/**
	 * @description 分页查询方法
	 * @param MailSign mailSign 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="邮件签名设置",operateFuncNm="manageList",operateDescribe="查询签名列表")
	public PageManager manageList(MailSign mailSign,PageManager page,
			HttpServletRequest request){
		PageManager page_ = mailSignService.query(mailSign, page);
		return page_; 
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
		return "ic/mailSign/mailSignInteract"; 
	}

/**
	 * @description 删除方法
	 * @param MailSign mailSign 实体类
	 * @param String id 被删除数据主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17
	 */
	@RequestMapping(value="delete.action")
	@ResponseBody
	@ActionLog(operateModelNm="邮件签名设置",operateFuncNm="delete",operateDescribe="逻辑删除签名")
	public Map<String, Object> delete(MailSign mailSign,String id,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if(mailSignService.delete(mailSign,id) == 1){
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
			}
		} catch (IcException e) {
			if(e.getLogMsg() == null){
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
			}else{
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, e.getLogMsg());
			}
		}
		return resultMap;
	}

	/**
	 * @description 保存方法
	 * @param MailSign mailSign 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 徐伟平 Ray
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="邮件签名设置",operateFuncNm="save",operateDescribe="保存签名设置")
	public Map<String, Object> save(@Valid MailSign mailSign, BindingResult result,
			HttpServletRequest request) throws CustomException {
		Map<String, Object> resultMap = null;
		try{
			// 验证bean
			resultMap= validateBean(result);
			if (resultMap.size() > 0) {
				return resultMap;
			}
			// 验证token
			resultMap = validateToken(request);
			if (resultMap.size() > 0) {
				return resultMap;
			}
			// 判断签名名称是否存在
			MailSign m = new MailSign();
			m.setSignTitle(mailSign.getSignTitle());
			m.setDeleteFlag(0);
			m.setCreateUser(SystemSecurityUtils.getUser().getId());
			if (mailSignService.get(m) != null) {
				IcException ce = new IcException();
				ce.setLogMsg(MessageUtils.getMessage("JC_SYS_036",new String[]{"签名名称"}));
				throw ce;
			}
			// 保存签名
			if (mailSignService.save(mailSign) == 1) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
				resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));
			} 
		}catch(IcException e){
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
	 * @param MailSign mailSign 实体类
	 * @return Integer 更新的数目
	 * @author 徐伟平
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "update.action")
	@ResponseBody
	@ActionLog(operateModelNm="邮件签名设置",operateFuncNm="update",operateDescribe="保存修改签名设置")
	public Map<String, Object> update(MailSign mailSign, BindingResult result,
			HttpServletRequest request) throws CustomException {
		Map<String, Object> resultMap = null;
		try{
			// 验证bean
			resultMap= validateBean(result);
			if (resultMap.size() > 0) {
				return resultMap;
			}
			Integer flag = mailSignService.update(mailSign);
			if (flag == 1) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
				resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));
			} 
		}catch(IcException e){
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_002"));
		}
		return resultMap;
	}

	/**
	 * @description 获取单条记录方法
	 * @param MailSign mailSign 实体类
	 * @return MailSign 查询结果
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="邮件签名设置",operateFuncNm="get",operateDescribe="获取一条签名信息")
	public MailSign get(MailSign mailSign,HttpServletRequest request) throws Exception{
		return mailSignService.get(mailSign);
	}
	
	/**
	* @description 加载所有签名数据
	* @return String 跳转的路径
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-21 
	*/
	@RequestMapping(value="manageLoadSign.action")
	@ActionLog(operateModelNm="邮件签名设置",operateFuncNm="manageLoadSign",operateDescribe="加载所有签名数据")
	public String manageLoadResource(Model model,HttpServletRequest request) throws Exception{
		MailSign mailSign = new MailSign();
		mailSign.setCreateUser(SystemSecurityUtils.getUser().getId());//只能查询到自己的签名
		List<MailSign> signList = mailSignService.queryAll(mailSign);
		model.addAttribute("signList", signList);
		return "ic/mailSign/loadSign";
	}
	
	/**
	* @description 设置邮箱默认签名时的下拉数据
	* @return List<Mailbox>
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-21 
	*/
	@RequestMapping(value="mailboxList.action")
	@ResponseBody
	@ActionLog(operateModelNm="邮件签名设置",operateFuncNm="mailboxList",operateDescribe="设置邮箱默认签名时的下拉数据")
	public List<Mailbox> mailboxList(Mailbox mailbox,final PageManager page,
			HttpServletRequest request) throws Exception{
		mailbox.setCreateUser(SystemSecurityUtils.getUser().getId());//只能查询到自己的邮箱
		return mailSignService.mailboxList(mailbox);
	}
	/**
	* @description 加载设置邮箱默认签名的两个下拉的数据
	* @return List<MailSign>
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-21 
	*/
	@RequestMapping(value="mailSignList.action")
	@ResponseBody
	@ActionLog(operateModelNm="邮件签名设置",operateFuncNm="mailSignList",operateDescribe="加载设置邮箱默认签名的两个下拉的数据")
	public List<MailSign> mailSignList(MailSign mailsign,final PageManager page,
			HttpServletRequest request) throws Exception{
		mailsign.setCreateUser(SystemSecurityUtils.getUser().getId());//只能查询到自己的签名
		return mailSignService.queryAll(mailsign);
	}
	/**
	 * @description 保存邮箱默认签名方法
	 * @param Mailbox mailbox 实体类
	 * @return Integer 更新成功
	 * @author 徐伟平
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "updateMailbox.action")
	@ResponseBody
	@ActionLog(operateModelNm="邮件签名设置",operateFuncNm="updateMailbox",operateDescribe="保存邮箱默认签名方法")
	public Map<String, Object> updateMailbox(Mailbox mailbox, BindingResult result,
			HttpServletRequest request) throws CustomException {
		Map<String, Object> resultMap = null;
		try{
			// 验证bean
			resultMap = validateBean(result);
			if (resultMap.size() > 0) {
				return resultMap;
			}
			mailbox.setId(new Long(request.getParameter("mailboxId")));
			if("".equals(request.getParameter("signId"))){
				mailbox.setSignId(new Long(0));
			}
			if("".equals(request.getParameter("replySignId"))){
				mailbox.setReplySignId(new Long(0));
			}
			mailbox.setModifyDate(DateUtils.parseDate(request.getParameter("mailBoxModifyDate")));
			mailbox.setUpdateFlag("1");//说明是设置默认签名的修改，不需要进行邮箱验证
			Integer flag = mailSignService.updateMailbox(mailbox);
			if (flag == 1) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			}
		}catch(IcException e){
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_002"));
		}
		return resultMap;
	}
	
	/**
	 * 方法描述：保存内部邮箱默认签名方法
	 * @param mailbox
	 * @param result
	 * @param request
	 * @return
	 * @throws CustomException
	 * @author 宋海涛
	 * @version  2014年9月24日上午8:29:05
	 * @see
	 */
	@RequestMapping(value = "saveMailbox.action")
	@ResponseBody
	@ActionLog(operateModelNm="邮件签名设置",operateFuncNm="saveMailbox",operateDescribe="保存邮箱默认签名方法")
	public Map<String, Object> saveMailbox(Mailbox mailbox, BindingResult result,
			HttpServletRequest request) throws CustomException {
		Map<String, Object> resultMap = null;
		try{
			// 验证bean
			resultMap = validateBean(result);
			if (resultMap.size() > 0) {
				return resultMap;
			}
			//mailbox.setId(new Long(request.getParameter("mailboxId")));
//			if("".equals(request.getParameter("signId"))){
//				mailbox.setSignId(new Long(0));
//			}
//			if("".equals(request.getParameter("replySignId"))){
//				mailbox.setReplySignId(new Long(0));
//			}
			mailbox.setModifyDate(DateUtils.parseDate(request.getParameter("mailBoxModifyDate")));
			mailbox.setUpdateFlag("1");//说明是设置默认签名的修改，不需要进行邮箱验证
			mailbox.setAddress(SystemSecurityUtils.getUser().getId().toString());
			mailbox.setReceiveService("1");
			mailbox.setSenderService("1");
			mailbox.setUsername(SystemSecurityUtils.getUser().getDisplayName());
			mailbox.setMailPassword(SystemSecurityUtils.getUser().getId().toString());
			Integer flag = mailSignService.saveMailbox(mailbox);
			if (flag == 1) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			}
		}catch(IcException e){
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_002"));
		}
		return resultMap;
	}
	
	/**
	 * 验证签名名称是否存在
	 * @param MailSign mailSign 实体类
	 * @return String true不存在	false存在
	 * @throws Exception
	 * @author
	 * @version 2014-06-30
	 */
	@RequestMapping(value = "checkName.action")
	@ResponseBody
	public String checkName(MailSign mailSign,String oldName,HttpServletRequest request) throws Exception {
		if(!StringUtils.isEmpty(mailSign.getSignTitle()) && !mailSign.getSignTitle().equals(oldName)){
			mailSign.setDeleteFlag(0);
			mailSign.setCreateUser(SystemSecurityUtils.getUser().getId());
			if(mailSignService.get(mailSign) != null){
				return "false";
			} else {
				return "true";
			}
		} else {
			return "true";
		}
	}
}