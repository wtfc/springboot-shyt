package com.jc.oa.po.diary.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.ic.facade.IInteractFacadeService;
import com.jc.oa.po.diary.domain.DiaryDelegation;
import com.jc.oa.po.diary.domain.validator.DiaryDelegationValidator;
import com.jc.oa.po.diary.service.IDiaryDelegationService;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.JsonUtil;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;
import com.jc.system.security.util.UserUtils;
/**
 * @title 个人办公
 * @description  controller类
 * @author 
 * @version  2014-05-13
 */
 
@Controller
@RequestMapping(value="/po/diaryDelegation")
public class DiaryDelegationController extends BaseController{
	
	@Autowired
	private IDiaryDelegationService diaryDelegationService;
	
	@Autowired
	private IInteractFacadeService interactFacadeService;
	
	@org.springframework.web.bind.annotation.InitBinder("diaryDelegation")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new DiaryDelegationValidator()); 
    }
	
	public DiaryDelegationController() {
	}

	/**
	 * 分页查询方法
	 * @param DiaryDelegation diaryDelegation 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-13 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_日程委托表",operateFuncNm="manageList",operateDescribe="对公共_个人办公_日程委托表进行查询操作")
	public PageManager manageList(DiaryDelegation diaryDelegation,PageManager page,HttpServletRequest request){
		PageManager page_ = diaryDelegationService.query(diaryDelegation, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-05-13 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="公共_个人办公_日程委托表",operateFuncNm="manage",operateDescribe="对公共_个人办公_日程委托表进行跳转操作")
	public String manage(HttpServletRequest request) throws Exception{
		return "po/diaryDelegation/diaryDelegation添加常用网址"; 
	}

/**
	 * 删除方法
	 * @param DiaryDelegation diaryDelegation 实体类
	 * @param String ids 删除的多个主键
	 * @return Map<String, Object> 
	 * @throws Exception
	 * @author
	 * @version  2014-05-13
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_日程委托表",operateFuncNm="deleteByIds",operateDescribe="对公共_个人办公_日程委托表进行删除")
	public Map<String, Object> deleteByIds(DiaryDelegation diaryDelegation,String ids,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		diaryDelegation.setPrimaryKeys(ids.split(","));
		if(diaryDelegationService.delete(diaryDelegation,false) == 1){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		}
		return resultMap;
	}
	
	/**
	 * 方法描述：组装共享提醒内容
	 * @param worklog
	 * @return String
	 * @author 金峰
	 * @version  2014年5月26日上午10:43:04
	 * @see
	 */
	private String getShareRemindContentMail(DiaryDelegation diaryDelegation){
		StringBuffer content = new StringBuffer();
		content.append(UserUtils.getUser(diaryDelegation.getCreateUser()).getDisplayName())
		.append("于")
		.append(DateUtils.getDate())
		.append("委托您代为填写日程，望知晓。");
		return Constants.getEmailContent("日程委托", content.toString());
		
	}
	
	/**
	 * 方法描述：组装取消共享提醒内容
	 * @param worklog
	 * @return String
	 * @author 金峰
	 * @version  2014年5月26日上午10:43:04
	 * @see
	 */
	private String getCancelShareRemindContentMail(DiaryDelegation diaryDelegation){
		StringBuffer content = new StringBuffer();
		content.append(UserUtils.getUser(diaryDelegation.getCreateUser()).getDisplayName())
		.append("于")
		.append(DateUtils.getDate())
		.append("取消委托您代为填写日程，望知晓。");
		return Constants.getEmailContent("日程委托", content.toString());
	}
	
	/**
	 * 方法描述：组装共享提醒内容
	 * @param worklog
	 * @return String
	 * @author 金峰
	 * @version  2014年5月26日上午10:43:04
	 * @see
	 */
	private String getShareRemindContentSms(DiaryDelegation diaryDelegation){
		StringBuffer content = new StringBuffer();
		content.append(UserUtils.getUser(diaryDelegation.getCreateUser()).getDisplayName())
		.append("于")
		.append(DateUtils.getDate())
		.append("委托您代为填写日程，望知晓。");
		return Constants.getSmsText("日程委托", content.toString());
		
	}
	
	/**
	 * 方法描述：组装取消共享提醒内容
	 * @param worklog
	 * @return String
	 * @author 金峰
	 * @version  2014年5月26日上午10:43:04
	 * @see
	 */
	private String getCancelShareRemindContentSms(DiaryDelegation diaryDelegation){
		StringBuffer content = new StringBuffer();
		content.append(UserUtils.getUser(diaryDelegation.getCreateUser()).getDisplayName())
		.append("于")
		.append(DateUtils.getDate())
		.append("取消委托您代为填写日程，望知晓。");
		return Constants.getSmsText("日程委托", content.toString());
	}

	/**
	 * 保存方法
	 * @param DiaryDelegation diaryDelegation 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-05-13
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_日程委托表",operateFuncNm="save",operateDescribe="对公共_个人办公_日程委托表进行新增操作")
	public Map<String,Object> save(@Valid DiaryDelegation diaryDelegation,BindingResult result,
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
		diaryDelegation.setMandatorId(SystemSecurityUtils.getUser().getId());
		
		if(diaryDelegationService.save(diaryDelegation)==1){
			//保存提醒
			String remindWay = request.getParameter("remindWay");
			if(!StringUtil.isEmpty(remindWay)){
				if(Constants.PO_DIARY_AGENT_MAIL.equals(remindWay)){//邮件提醒
					Map<String,String> paramMap = new HashMap<String,String>();
					paramMap.put(interactFacadeService.MAIL_SENDER_ID, diaryDelegation.getCreateUser().toString());
					paramMap.put(interactFacadeService.MAIL_SUBJECT, Constants.getEmailSubject("日程委托"));
					paramMap.put(interactFacadeService.MAIL_CONTENT, getShareRemindContentMail(diaryDelegation));
					paramMap.put(interactFacadeService.MAIL_RECEIVER_IDS, diaryDelegation.getMandataryId()+"");
					interactFacadeService.sendMail(paramMap);
				}else if(Constants.PO_DIARY_AGENT_MSG.equals(remindWay)){//短信提醒
					Map<String,String> paramMap = new HashMap<String,String>();
					paramMap.put(interactFacadeService.SMS_SEND_IDS, diaryDelegation.getMandataryId()+"");
					paramMap.put(interactFacadeService.SMS_TEXT, getShareRemindContentSms(diaryDelegation));
					paramMap.put(interactFacadeService.SMS_CEATEUSER, diaryDelegation.getCreateUser().toString());
					paramMap.put(interactFacadeService.SMS_SENDTYPE, "sendType_3");
					interactFacadeService.sendSms(paramMap);
				}
			}
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param DiaryDelegation diaryDelegation 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-05-13 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_日程委托表",operateFuncNm="update",operateDescribe="对公共_个人办公_日程委托表进行更新操作")
	public Map<String, Object> update(DiaryDelegation diaryDelegation, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		DiaryDelegation d=new DiaryDelegation();
		d.setId(diaryDelegation.getId());
		d=diaryDelegationService.get(d);
		d.setMandatorId(SystemSecurityUtils.getUser().getId());
		d.setModifyDate(diaryDelegation.getDdModifyDate());
		d.setMandataryId(diaryDelegation.getMandataryId());
		Integer flag = diaryDelegationService.update(d);
		if (flag == 1) {
			//保存提醒
			String remindWay = request.getParameter("remindWay");
			if(!StringUtil.isEmpty(remindWay)){
				if(Constants.PO_DIARY_AGENT_MAIL.equals(remindWay)){//邮件提醒
					Map<String,String> paramMap = new HashMap<String,String>();
					paramMap.put(interactFacadeService.MAIL_SENDER_ID, d.getCreateUser().toString());
					paramMap.put(interactFacadeService.MAIL_SUBJECT, Constants.getEmailSubject("日程委托"));
					paramMap.put(interactFacadeService.MAIL_CONTENT, getShareRemindContentMail(d));
					paramMap.put(interactFacadeService.MAIL_RECEIVER_IDS, d.getMandataryId()+"");
					interactFacadeService.sendMail(paramMap);
				}else if(Constants.PO_DIARY_AGENT_MSG.equals(remindWay)){//短信提醒
					Map<String,String> paramMap = new HashMap<String,String>();
					paramMap.put(interactFacadeService.SMS_SEND_IDS, d.getMandataryId()+"");
					paramMap.put(interactFacadeService.SMS_TEXT, getShareRemindContentSms(d));
					paramMap.put(interactFacadeService.SMS_CEATEUSER, d.getCreateUser().toString());
					paramMap.put(interactFacadeService.SMS_SENDTYPE, "sendType_3");
					interactFacadeService.sendSms(paramMap);
				}
			}
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param DiaryDelegation diaryDelegation 实体类
	 * @return DiaryDelegation 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-13
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_日程委托表",operateFuncNm="get",operateDescribe="对公共_个人办公_日程委托表进行单条查询操作")
	public DiaryDelegation get(DiaryDelegation diaryDelegation,HttpServletRequest request) throws Exception{
		return diaryDelegationService.get(diaryDelegation);
	}

	/** 方法描述：根据当前用户查询被委托人
	 * @param diaryDelegation
	 * @return DiaryDelegation
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月16日上午11:33:52
	 * @see
	 */
	@RequestMapping(value="getDelegationByMandatorId.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_日程委托表",operateFuncNm="getDelegationByMandatorId",operateDescribe="根据当前用户查询被委托人")
	public DiaryDelegation getDelegationByMandatorId(DiaryDelegation diaryDelegation,HttpServletRequest request) throws Exception {
		diaryDelegation.setMandatorId(SystemSecurityUtils.getUser().getId());
		List<DiaryDelegation> list=diaryDelegationService.queryAll(diaryDelegation);
		if(list.size()>0&&!list.isEmpty()){
			return list.get(0);
		}else{
			return diaryDelegation;
		}
	}
	
	/** 方法描述：根据当前用户查询委托人
	 * @param diaryDelegation
	 * @return DiaryDelegation
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月16日上午11:33:52
	 * @see
	 */
	@RequestMapping(value="getDelegationByMandataryId.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_日程委托表",operateFuncNm="getDelegationByMandataryId",operateDescribe="根据当前用户查询委托人")
	public DiaryDelegation getDelegationByMandataryId(DiaryDelegation diaryDelegation,HttpServletRequest request) throws Exception {
		diaryDelegation.setMandataryId(SystemSecurityUtils.getUser().getId());
		List<DiaryDelegation> list=diaryDelegationService.queryAll(diaryDelegation);
		DiaryDelegation d=new DiaryDelegation();
		if(list.size()>0&&!list.isEmpty()){
			String ids="";
			for(DiaryDelegation dd:list){
				ids+=dd.getMandatorId()+",";
			}
			ids=ids.substring(0, ids.length()-1);
			d.setMandatorIds(ids);
			return d;
//			return list.get(0);//单委托
		}else{
			return d;
//			return diaryDelegation;//单委托
		}
	}
	/** 方法描述：跳转方法 显示日程委托层
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年10月8日上午13:14:19
	 * @see
	 */
	@RequestMapping(value="showAgentSetDiv.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="showAgentSetDiv",operateDescribe="显示日程委托层 ")
	public String showAgentSetDiv(Long id,Model model,HttpServletRequest request) throws Exception{
		DiaryDelegation diaryDelegation=new DiaryDelegation();
		diaryDelegation.setMandatorId(SystemSecurityUtils.getUser().getId());
		List<DiaryDelegation> list=diaryDelegationService.queryAll(diaryDelegation);
		if(list.size()>0&&!list.isEmpty()){
			String diaryDelegationJson=JsonUtil.getJSON(list.get(0));
			diaryDelegationJson=diaryDelegationJson.replaceAll("'", "&acute;");
			model.addAttribute("diaryDelegationJson",diaryDelegationJson);
		}else{
			String diaryDelegationJson=JsonUtil.getJSON(diaryDelegation);
			diaryDelegationJson=diaryDelegationJson.replaceAll("'", "&acute;");
			model.addAttribute("diaryDelegationJson",diaryDelegationJson);
		}
		model.addAttribute("agentToken", super.getToken(request));
		return "po/diary/diaryDelegation"; 
	}
	
	/**
	 * 删除方法
	 * @param DiaryDelegation diaryDelegation 实体类
	 * @return Map<String, Object> 
	 * @throws Exception
	 * @author
	 * @version  2014-05-13
	 */
	@RequestMapping(value="deleteByMandatorIdAndMandataryId.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_日程委托表",operateFuncNm="deleteByMandatorIdAndMandataryId",operateDescribe="对公共_个人办公_日程委托表进行删除")
	public Map<String, Object> deleteByMandatorIdAndMandataryId(DiaryDelegation diaryDelegation,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<DiaryDelegation> list=diaryDelegationService.queryAll(diaryDelegation);
		if(list!=null&&!list.isEmpty()){
			String ids="";
			for(DiaryDelegation tmp:list){
				if(ids.equals("")){
					ids=tmp.getId().toString();
				}else{
					ids+=","+tmp.getId();
				}
			}
			DiaryDelegation dd=new DiaryDelegation();
			dd.setPrimaryKeys(ids.split(","));
			if(diaryDelegationService.delete(dd,false) == 1){
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
			}
		}else{
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
		}
		
		return resultMap;
	}
}