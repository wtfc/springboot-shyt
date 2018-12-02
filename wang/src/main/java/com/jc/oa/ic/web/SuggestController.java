package com.jc.oa.ic.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.domain.SugRep;
import com.jc.oa.ic.domain.Suggest;
import com.jc.oa.ic.domain.SuggestType;
import com.jc.oa.ic.domain.validator.SuggestValidator;
import com.jc.oa.ic.service.ISuggestService;
import com.jc.oa.ic.service.ISuggestTypeService;
import com.jc.system.CustomException;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.notice.NoticeMsgUtil;
import com.jc.system.notice.domain.NoticeMsg;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;


/**
 * @title GOA V2.0 互动交流
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version 2014-05-07
 */
 
@Controller
@RequestMapping(value="/ic/suggest")
public class SuggestController extends BaseController{
	
	@Autowired
	private ISuggestService suggestService;
	
	@Autowired
	private ISuggestTypeService suggestTypeService;
	
	@org.springframework.web.bind.annotation.InitBinder("suggest")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new SuggestValidator()); 
    }
	
	public SuggestController() {
	}

	/**
	 * @description 分页查询方法
	 * @param Suggest suggest 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author 徐伟平
	 * @version 2014-05-07 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="建议发起",operateFuncNm="manageList",operateDescribe="分页查询方法") 
	public PageManager manageList(Suggest suggest,PageManager page,HttpServletRequest request){
		
		User userInfo = SystemSecurityUtils.getUser();
		//默认排序
		if(StringUtils.isEmpty(suggest.getOrderBy())) {
			suggest.addOrderByFieldDesc("t.CREATE_DATE");
		}
		//将结束时间设置为选中日期的23点59分59秒
		if(suggest.getEndCreateDate() != null){
			suggest.setEndCreateDate(DateUtils.fillTime(suggest.getEndCreateDate()));
		}
		//发起方式,当发起方式都被选中时，查询条件中不做处理，查询全部数据
		if("0".equals(suggest.getSuggestWay_t()) && "1".equals(suggest.getSuggestWay_f())){
			suggest.setSuggestWay("");
		}else{
			if("0".equals(suggest.getSuggestWay_t())){
				suggest.setSuggestWay("0");
			}else if("1".equals(suggest.getSuggestWay_f())){
				suggest.setSuggestWay("1");
			}
		}
		if(!"".equals(String.valueOf(suggest.getCreateUser())) && suggest.getCreateUser() != null){
			if("1".equals(suggest.getSuggestWay())){
				suggest.setSuggestWay("2");
			}
			if("".equals(suggest.getSuggestWay()) || suggest.getSuggestWay() == null){
				suggest.setSuggestWay("0");
			}
		}
		//同时选择了发起和接收时，不做处理，查询全部数据
		if("1".equals(suggest.getDisposeType_s()) && "0".equals(suggest.getDisposeType_r())){
			suggest.setDisposeType("");
		}else{
			//选择了发起
			if("1".equals(suggest.getDisposeType_s())){
				suggest.setDisposeType("1");
				if(suggest.getCreateUser() == null || suggest.getCreateUser().longValue()==0){
					suggest.setCreateUser(userInfo.getId());
				}
			}
			//选择了接收
			if("0".equals(suggest.getDisposeType_r())){
				suggest.setDisposeType("0");
			}
		}
		//回复状态,当回复状态都被选中时，查询条件中不做处理，查询全部数据
		if("0".equals(suggest.getRepStatus_n()) && "1".equals(suggest.getRepStatus_y())){
		}else{
			if("0".equals(suggest.getRepStatus_n())){
				suggest.setRepStatus("0");
			}else if("1".equals(suggest.getRepStatus_y())){
				suggest.setRepStatus("1");
			}
		}
		
		request.getParameterMap();
		suggest.setDeleteFlag(0);
		suggest.setUserId(userInfo.getId());
		//处理查询条件中带有“_”的特殊字符转义
		if(!StringUtil.isEmpty(suggest.getSuggestTitle())){
			suggest.setSuggestTitle(StringUtil.escapeSQLWildcard(suggest.getSuggestTitle()));
		}
		//当选择了发起人作为排序条件时，由于发起人有匿名的情况，所以要先按照发起方式进行排序，解决匿名的数据排序不正确的情况
		if(!suggest.getOrderBy().isEmpty()&& suggest.getOrderBy().indexOf("displayName")!=-1){
			String[] fieldAndOrderType = suggest.getOrderBy().split("\\s+");//将排序字符串按照空格截取成数组\\s+ 允许中间有多个空格
			String field = fieldAndOrderType[1];
			suggest.setOrderBy("suggestWay "+field+","+suggest.getOrderBy());
		}
		//列表回显处理类型，由于数据库中disposeType字段未使用，因此需要通过使用创建人ID和当前登录用户ID做比对，来判断是什么处理类型
		PageManager page_ = suggestService.query(suggest, page);
		
		List<Suggest> list = (List<Suggest>) page_.getAaData();
		for(Suggest su : list){
			//将每条数据传入当前用户ID
			su.setUserId(userInfo.getId());
		}
		return page_;
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author 徐伟平
	* @version 2014-05-07 
	*/
	@RequestMapping(value="manage.action")
	public String manage(Model model, HttpServletRequest request) throws Exception{
		model.addAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));
		return "ic/suggest/suggestInteract"; 
	}

	/**
	 * @description 删除方法
	 * @param Suggest suggest 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 徐伟平
	 * @version 2014-05-07
	 */
	@RequiresPermissions("suggest:delete")
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="建议发起",operateFuncNm="deleteByIds",operateDescribe="逻辑删除意见建议") 
	public Map<String, Object> deleteByIds(Suggest suggest,String ids,
			HttpServletRequest request) throws Exception{
		suggest.setPrimaryKeys(ids.split(","));
		if(suggestService.delete(suggest) == 1){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
			return resultMap;
		}
		return null;
	}

	/**
	 * @description 保存方法
	 * @param Suggest suggest 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 徐伟平
	 * @version 2014-05-07
	 */
	@RequiresPermissions("suggest:save")
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="建议发起",operateFuncNm="save",operateDescribe="保存意见建议") 
	public Map<String, Object> save(@Valid Suggest suggest,
			BindingResult result, HttpServletRequest request) throws CustomException {
		Map<String, Object> resultMap = null;
		try {
			// 验证bean
			resultMap = validateBean(result);
			if (resultMap.size() > 0) {
				return resultMap;
			}
			SuggestType type = new SuggestType();
			type.setId(suggest.getSuggestTypeId());
			type.setDeleteFlag(0);
			type = suggestTypeService.get(type);
			if(type == null){
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_COMMON_014"));
				resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));
				return resultMap;
			}
			// 验证token
			resultMap = validateToken(request);
			if (resultMap.size() > 0) {
				return resultMap;
			}
			// 保存意见
			if (suggestService.save(suggest) == 1) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_WORKFLOW_001"));
				resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));
				NoticeMsg noticeMsg = new NoticeMsg();
				if(!"".equals(suggest.getRecipientIds())){
					String[] recipientIds = suggest.getRecipientIds().split(",");
					for (int i = 0; i < recipientIds.length; i++) {
						//发送弹出窗口提醒
						noticeMsg.setId(null);
						noticeMsg.setTitle(suggest.getSuggestTitle());
						noticeMsg.setReceiveUser(Long.parseLong(recipientIds[i]));
						noticeMsg.setContent(suggest.getSuggestContent());
						noticeMsg.setSendUser(suggest.getCreateUser());
						noticeMsg.setNoticeType("意见建议提醒");
						noticeMsg.setBusinessFlag("toa_ic_suggest");
						noticeMsg.setBusinessId(suggest.getId());
						noticeMsg.setUrl("/ic/suggest/manage.action?id="+ suggest.getId());
						noticeMsg.setExtStr1("/ic/suggest/manage.action");
						NoticeMsgUtil.notice(noticeMsg);
					}
				}
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
	 * @param Suggest suggest 实体类
	 * @return Integer 更新的数目
	 * @author 徐伟平
	 * @version 2014-05-07
	 */
	@RequestMapping(value = "update.action")
	@ResponseBody
	@ActionLog(operateModelNm="建议发起",operateFuncNm="update",operateDescribe="保存修改意见建议") 
	public Map<String, Object> update(Suggest suggest, BindingResult result,
			HttpServletRequest request) throws CustomException {
		Map<String, Object> resultMap = null;
		try {
			// 验证bean
			resultMap = validateBean(result);
			if (resultMap.size() > 0) {
				return resultMap;
			}
			Integer flag = suggestService.update(suggest);
			if (flag == 1) {
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
	 * @description 获取单条记录方法
	 * @param Suggest suggest 实体类
	 * @return Suggest 查询结果
	 * @throws Exception
	 * @author 徐伟平
	 * @version 2014-05-07
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="建议发起",operateFuncNm="get",operateDescribe="获取一条意见建议信息") 
	public Suggest get(Suggest suggest,HttpServletRequest request) throws Exception{
		suggest.setDeleteFlag(0);
		return suggestService.get(suggest);
	}
	
	/**
	* @description 设置建议类别的下拉数据
	* @return List<SuggestType>
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-21 
	*/
	@RequestMapping(value="getSuggestTypeList.action")
	@ResponseBody
	@ActionLog(operateModelNm="建议发起",operateFuncNm="getSuggestTypeList",operateDescribe="设置建议类别的下拉数据") 
	public List<SuggestType> getSuggestTypeList(SuggestType suggestType,final PageManager page,
			HttpServletRequest request) throws Exception{
		//默认排序
		if(StringUtils.isEmpty(suggestType.getOrderBy())) {
			suggestType.addOrderByFieldDesc("t.CREATE_DATE");
		}
		//加入组织机构权限
		suggestType.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
		return suggestService.getSuggestTypeList(suggestType);
	}
	/**
	* @description 带入默认的人员
	* @return List<SuggestType>
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-21 
	*/
	@RequestMapping(value="getUserNames.action")
	@ResponseBody
	@ActionLog(operateModelNm="建议发起",operateFuncNm="getUserNames",operateDescribe="获取接收人名称字符串") 
	public Map<String,String> getUserNames(String ids,HttpServletRequest request) throws Exception{
		return suggestService.getUserNames(ids);
	}
	
	/**
	 * @description 保存回复方法
	 * @param SugRep sugRep 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-05-12
	 */
	@RequiresPermissions("suggest:saveRep")
	@RequestMapping(value = "saveRep.action")
	@ResponseBody
	@ActionLog(operateModelNm="建议发起",operateFuncNm="saveRep",operateDescribe="保存回复信息") 
	public Map<String,Object> saveRep(@Valid SugRep sugRep,BindingResult result,
			HttpServletRequest request) throws IcException{
		Map<String, Object> resultMap = null;
		try {
			// 验证bean
			resultMap = validateBean(result);
			if (resultMap.size() > 0) {
				return resultMap;
			}
			Suggest t = new Suggest();
			t.setId(sugRep.getSuggestId());
			Suggest suggest = suggestService.get(t);
			if(suggest==null){
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_COMMON_014"));
				return resultMap;
			}
			// 保存回复信息
			if (suggestService.saveRep(sugRep) == 1) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_OA_DOC_005"));
			}
		} catch (IcException e) {
			if(e.getLogMsg() == null){
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_002"));
			}else{
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, e.getLogMsg());
			}
		} catch (CustomException e) {
			e.printStackTrace();
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, e.getLogMsg());
		}
		return resultMap;
	}
	
	/**
	 * 验证标题是否存在
	 * @param Suggest suggest 实体类
	 * @return String true不存在	false存在
	 * @throws Exception
	 * @author
	 * @version 2014-06-30
	 */
	@RequestMapping(value = "checkName.action")
	@ResponseBody
	public String checkName(Suggest suggest,String oldName,HttpServletRequest request) throws Exception {
		if(!StringUtils.isEmpty(suggest.getSuggestTitle()) && !suggest.getSuggestTitle().equals(oldName)){
			suggest.setDeleteFlag(0);
			if(suggestService.queryAll(suggest).size() > 0){
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
	* @version 2014-09-26 
	*/
	@RequestMapping(value="suggestDiv.action")
	public String suggestDiv(HttpServletRequest request) throws Exception{
		return "ic/suggest/suggestInteractDiv"; 
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author weny
	* @version 2014-09-26 
	*/
	@RequestMapping(value="suggestReplyDiv.action")
	public String suggestReplyDiv(HttpServletRequest request) throws Exception{
		return "ic/suggest/suggestInteractReplyDiv"; 
	}

}