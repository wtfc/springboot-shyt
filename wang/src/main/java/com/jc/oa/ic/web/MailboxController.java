package com.jc.oa.ic.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.domain.Mailbox;
import com.jc.oa.ic.domain.validator.MailboxValidator;
import com.jc.oa.ic.service.IMailboxService;
import com.jc.oa.ic.util.CryptUtil;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;


/**
 * @title GOA V2.0 互动交流
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * @version  2014-04-17
 */
 
@Controller
@RequestMapping(value="/ic/mailbox")
public class MailboxController extends BaseController{
	@Autowired
	private IMailboxService mailboxService;
	
	@org.springframework.web.bind.annotation.InitBinder("mailbox")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new MailboxValidator()); 
        	binder.registerCustomEditor(Date.class,"modifyDate", new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"),true));
    }
	
	public MailboxController() {
	}

	/**
	 * @description 分页查询方法
	 * @param Mailbox mailbox 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="我的Internet邮箱",operateFuncNm="manageList",operateDescribe="查询邮箱配置列表")
	public PageManager manageList(Mailbox mailbox,PageManager page,HttpServletRequest request){
		//默认排序
		if(StringUtils.isEmpty(mailbox.getOrderBy())) {
			mailbox.addOrderByFieldDesc("t.CREATE_DATE");
		}
		request.getParameterMap();
		mailbox.setDeleteFlag(0);
		//查询属于自己的邮箱
		mailbox.setCreateUser(SystemSecurityUtils.getUser().getId());
		//查询条件中输入的字符如果带有“_”需要手动转义
		if(!StringUtil.isEmpty(mailbox.getAddress())){
			mailbox.setAddress(StringUtil.escapeSQLWildcard(mailbox.getAddress()));
		}
		return mailboxService.query(mailbox, page);
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
		return "ic/mailbox/mailboxInteract"; 
	}

/**
	 * @description 删除方法
	 * @param Mailbox mailbox 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17
	 */
	@RequestMapping(value="delete.action")
	@ResponseBody
	@ActionLog(operateModelNm="我的Internet邮箱",operateFuncNm="delete",operateDescribe="逻辑删除邮箱配置信息") 
	public Map<String, Object> deleteByIds(Mailbox mailbox,String ids,
			HttpServletRequest request) throws Exception{
		mailbox.setPrimaryKeys(ids.split(","));
		if(mailboxService.delete(mailbox,true).intValue() >0){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
			return resultMap;
		}
		return null;
	}

	/**
	 * @description 保存方法
	 * @param Mailbox
	 *            mailbox 实体类
	 * @param BindingResult
	 *            result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 徐伟平 
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="我的Internet邮箱",operateFuncNm="save",operateDescribe="保存邮箱配置信息") 
	public Map<String, Object> save(@Valid Mailbox mailbox, BindingResult result,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = null;
	//	try {
			// 验证bean
			resultMap = validateBean(result);
			if (resultMap.size() > 0) {
				return resultMap;
			}
			// 验证token
			resultMap = validateToken(request);
			if (resultMap.size() > 0) {
				return resultMap;
			}
			if("".equals(mailbox.getSenderSSL()) || mailbox.getSenderSSL() == null){
				mailbox.setSenderSSL("0");
			}
			if("".equals(mailbox.getReceiveSSL()) || mailbox.getReceiveSSL() == null){
				mailbox.setReceiveSSL("0");
			}
			// 保存邮箱
			if (mailboxService.save(mailbox) == 1) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
				resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));
			} 
//		} catch (IcException e) {
//			if(e.getLogMsg() == null){
//				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_002"));
//			}else{
//				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, e.getLogMsg());
//			}
//			//resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));
//		}
		return resultMap;
	}

	/**
	 * @description 修改方法
	 * @param Mailbox mailbox 实体类
	 * @return Integer 更新的数目
	 * @author 徐伟平
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "update.action")
	@ResponseBody
	@ActionLog(operateModelNm="我的Internet邮箱",operateFuncNm="update",operateDescribe="保存修改邮箱配置信息") 
	public Map<String, Object> update(Mailbox mailbox, BindingResult result,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = null;
		//try {
			// 验证bean
		resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = mailboxService.update(mailbox);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));
		}
//		} catch (IcException e) {
//			if(e.getLogMsg() == null){
//				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_002"));
//			}else{
//				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, e.getLogMsg());
//			}
//		}
		return resultMap;
	}
	

	/**
	 * @description 获取单条记录方法
	 * @param Mailbox mailbox 实体类
	 * @return Mailbox 查询结果
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="我的Internet邮箱",operateFuncNm="get",operateDescribe="获取一条邮箱配置信息") 
	public Mailbox get(Mailbox mailbox,HttpServletRequest request) throws Exception{
		Mailbox mb = new Mailbox();
		mb = mailboxService.get(mailbox);
		CryptUtil cryptUtil = new CryptUtil();
		mb.setMailPassword(cryptUtil.decrypt(mb.getMailPassword()));//对密码进行解密转换
		return mb;
	}
	/**
	 * 验证名称是否存在
	 * @param Mailbox mailbox 实体类
	 * @return String true不存在	false存在
	 * @throws Exception
	 * @author
	 * @version 2014-06-30
	 */
	@RequestMapping(value = "checkName.action")
	@ResponseBody
	public String checkName(Mailbox mailbox,String oldName,HttpServletRequest request) throws Exception {
		if(!StringUtils.isEmpty(mailbox.getUsername()) && !mailbox.getUsername().equals(oldName)){
			mailbox.setDeleteFlag(0);
			if(mailboxService.get(mailbox) != null){
				return "false";
			} else {
				return "true";
			}
		} else {
			return "true";
		}
	}

}