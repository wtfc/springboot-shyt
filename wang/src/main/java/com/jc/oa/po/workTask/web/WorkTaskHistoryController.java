package com.jc.oa.po.workTask.web;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.WebDataBinder;

import com.jc.oa.po.workTask.domain.WorkTask;
import com.jc.oa.po.workTask.domain.WorkTaskHistory;
import com.jc.oa.po.workTask.domain.validator.WorkTaskHistoryValidator;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.po.workTask.service.IWorkTaskHistoryService;
import com.jc.oa.po.workTask.service.IWorkTaskService;
import com.jc.system.common.util.BeanUtil;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.util.ActionLog;


/**
 * @title 个人办公
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-23
 */
 
@Controller
@RequestMapping(value="/po/workTaskHistory")
public class WorkTaskHistoryController extends BaseController{
	
	@Autowired
	private IWorkTaskHistoryService taskHistoryService;
	@org.springframework.web.bind.annotation.InitBinder("taskHistory")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new WorkTaskHistoryValidator()); 
    }
	
	public WorkTaskHistoryController() {
	}
	
	private WorkTaskHistoryValidator wv=new WorkTaskHistoryValidator();
	/**
	 * @description 分页查询方法
	 * @param WorkTaskHistory taskHistory 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-04-23 
	 */
	@RequestMapping(value="manageList.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办事件",operateFuncNm="manageList",operateDescribe="分页查询")
	public PageManager manageList(WorkTaskHistory taskHistory,PageManager page,HttpServletRequest request){
		PageManager page_ = taskHistoryService.query(taskHistory, page);
		return page_; 
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author 李洪宇
	* @version  2014-04-23 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="督办协办事件",operateFuncNm="manage",operateDescribe="跳转方法")
	public String manage(HttpServletRequest request) throws Exception{
		return "po/taskHistory/taskHistory添加常用网址"; 
	}

/**
	 * @description 删除方法
	 * @param WorkTaskHistory taskHistory 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-04-23
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办事件",operateFuncNm="deleteByIds",operateDescribe="删除")
	public Integer deleteByIds(WorkTaskHistory taskHistory,String ids,HttpServletRequest request) throws Exception{
		taskHistory.setPrimaryKeys(ids.split(","));
		return taskHistoryService.delete(taskHistory);
	}

	/**
	 * @description 保存方法
	 * @param WorkTaskHistory taskHistory 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-04-23
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办事件",operateFuncNm="save",operateDescribe="保存")
	public Map<String, Object>  save(@RequestBody Map<String, Object> taskMap,BindingResult result,
			HttpServletRequest request) throws Exception{
		WorkTaskHistory taskHistory=BeanUtil.map2Object(taskMap,WorkTaskHistory.class);
		wv.validate(taskHistory, result);
		// 验证bean
		Map<String,Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request, (String)getMapValue(taskMap, GlobalContext.SESSION_TOKEN));
		if (resultMap.size() > 0) {
			return resultMap;
		}
		if(taskHistoryService.saveWorkTask(taskHistory)==1){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
		}
		return resultMap;
	}
	/**
	* @description 修改方法
	* @param WorkTaskHistory taskHistory 实体类
	* @return Integer 更新的数目
	* @author 李洪宇
	* @version  2014-04-23 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办事件",operateFuncNm="update",operateDescribe="修改")
	public Integer update(WorkTaskHistory taskHistory,HttpServletRequest request) throws Exception{
		Integer flag = taskHistoryService.update(taskHistory);
		return flag;
	}

	/**
	 * @description 获取单条记录方法
	 * @param WorkTaskHistory taskHistory 实体类
	 * @return TaskHistory 查询结果
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-04-23
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办事件",operateFuncNm="get",operateDescribe="获取单条记录")
	public WorkTaskHistory get(WorkTaskHistory taskHistory,HttpServletRequest request) throws Exception{
		return taskHistoryService.get(taskHistory);
	}
	/**
	 * @description 汇报详细列表
	 * @param HttpServletRequest request
	 * @return List<WorkTaskHistory>
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-05-13
	 */
	@RequestMapping(value="getTaskHistListByTaskId.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办事件",operateFuncNm="getTaskHistListByTaskId",operateDescribe="汇报详细列表")
	public PageManager getTaskHistListByTaskId(WorkTaskHistory workTaskHistory,final PageManager page,HttpServletRequest request) throws Exception{
			return taskHistoryService.getTaskHistListByTaskId(workTaskHistory,page);
	}
}