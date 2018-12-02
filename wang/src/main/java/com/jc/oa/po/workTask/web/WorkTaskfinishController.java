package com.jc.oa.po.workTask.web;
import javax.validation.Valid;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.WebDataBinder;

import com.jc.oa.po.PoException;
import com.jc.oa.po.workTask.domain.WorkTask;
import com.jc.oa.po.workTask.domain.WorkTaskfinish;
import com.jc.oa.po.workTask.domain.validator.WorkTaskfinishValidator;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.po.workTask.service.IWorkTaskService;
import com.jc.oa.po.workTask.service.IWorkTaskfinishService;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.ActionLog;


/**
 * @title 个人办公
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-24
 */
 
@Controller
@RequestMapping(value="/po/taskfinish")
public class WorkTaskfinishController extends BaseController{
	
	@Autowired
	private IWorkTaskfinishService taskfinishService;
	
	@Autowired
	private IWorkTaskService workTaskService;
	
	@Autowired
	private IUserService userService;
	
	@org.springframework.web.bind.annotation.InitBinder("taskfinish")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new WorkTaskfinishValidator()); 
    }
	
	public WorkTaskfinishController() {
	}

	/**
	 * @description 分页查询方法
	 * @param WorkTaskfinish taskfinish 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-24 
	 */
	@RequestMapping(value="manageList.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办(完成)",operateFuncNm="manageList",operateDescribe="分页查询")
	public PageManager manageList(WorkTaskfinish taskfinish,PageManager page,HttpServletRequest request){
		PageManager page_ = taskfinishService.query(taskfinish, page);
		return page_; 
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-24 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="督办协办(完成)",operateFuncNm="manage",operateDescribe="跳转")
	public String manage(HttpServletRequest request) throws Exception{
		return "po/taskfinish/taskfinish添加常用网址"; 
	}

/**
	 * @description 删除方法
	 * @param WorkTaskfinish taskfinish 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-04-24
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办(完成)",operateFuncNm="deleteByIds",operateDescribe="删除")
	public Integer deleteByIds(WorkTaskfinish taskfinish,String ids,HttpServletRequest request) throws Exception{
		taskfinish.setPrimaryKeys(ids.split(","));
		return taskfinishService.delete(taskfinish);
	}

	/**
	 * @description 保存方法
	 * @param WorkTaskfinish taskfinish 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-04-24
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办(完成)",operateFuncNm="save",operateDescribe="保存")
	public Map<String,Object> save(@Valid WorkTaskfinish taskfinish,BindingResult result,HttpServletRequest request) throws Exception{	
		Map<String,Object> resultMap = validateBean(result);
		if(!"false".equals(resultMap.get("success"))){
			taskfinishService.save(taskfinish);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param WorkTaskfinish taskfinish 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-04-24 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办(完成)",operateFuncNm="save",operateDescribe="修改")
	public Integer update(WorkTaskfinish taskfinish,HttpServletRequest request) throws Exception{
		Integer flag = taskfinishService.update(taskfinish);
		return flag;
	}

	/**
	 * @description 获取单条记录方法
	 * @param WorkTaskfinish taskfinish 实体类
	 * @return Taskfinish 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-24
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办(完成)",operateFuncNm="get",operateDescribe="获取单条记录")
	public WorkTaskfinish get(WorkTaskfinish taskfinish,HttpServletRequest request) throws Exception{
		return taskfinishService.get(taskfinish);
	}
	/**
	* @description 根据任务名称查询（跳转）
	* @return String 
	* @throws Exception
	* @author 李洪宇
	* @version  2014-05-21
	*/
	@RequestMapping(value="getWorkTaskInfo.action")
	@ActionLog(operateModelNm="督办协办(完成)",operateFuncNm="getWorkTaskInfo",operateDescribe="根据任务名称查询")
	public String getWorkTaskInfo(Model model,HttpServletRequest request) throws Exception{
		String id=request.getParameter("id");
		WorkTask workTask=null;
		if (null !=id && !"".equals(id)) {
			WorkTaskfinish workTaskfinish=new WorkTaskfinish();
			workTaskfinish.setId(new Long(id));
			WorkTaskfinish workfinish=taskfinishService.get(workTaskfinish);
			workTask=new WorkTask();
			BeanUtils.copyProperties(workfinish, workTask);
			workTask.setId(workfinish.getTaskId());
			WorkTask work=workTaskService.converterWorkTask(workTask);
			request.setAttribute("workTask",work);
			request.setAttribute("taskId", workfinish.getTaskId());
		}
		//将当前登录用户ID传入页面
		User userInfo = SystemSecurityUtils.getUser();
		request.setAttribute("userId", userInfo.getId());
		request.setAttribute("displayName", userInfo.getDisplayName());
		//权限判断
		boolean isLeader=false;
		if (null!=workTask) {
			User user=new User();
			user.setId(workTask.getDirectorId());
			isLeader=userService.isLeader(user,userInfo);
			if(userInfo.getId().intValue()==workTask.getDirectorId().intValue()){//如果当前登录人为任务的负责人
				isLeader=true;
			}
		}
		request.setAttribute("isLeader",isLeader);
		model.addAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));//性能优化 页面获取token
		return "po/workTask/taskQueryByName";
	}
	/**
	 * @description 分页查询方法(任务统计中完成任务)
	 * @param WorkTaskfinish taskfinish 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-22
	 */
	@RequestMapping(value="manageListOfFinish.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办(完成)",operateFuncNm="manageListOfFinish",operateDescribe="任务统计中完成任务分页查询")
	public PageManager manageListOfFinish(WorkTaskfinish taskfinish,PageManager page,HttpServletRequest request){
		PageManager page_ = taskfinishService.getFinWorkTaskList(taskfinish, page);
		return page_; 
	}
	/**
	 * 方法描述：取得子任务
	 * @param WorkTaskfinish taskfinish,PageManager page,HttpServletRequest request
	 * @return PageManager
	 * @author 李洪宇
	 * @version  2014-06-18
	 * @see
	 */
	@RequestMapping(value="getChildList.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办(完成)",operateFuncNm="getChildList",operateDescribe="取得子任务")
	public PageManager getChildList(WorkTaskfinish taskfinish,PageManager page,HttpServletRequest request)throws PoException{
		String parentTaskid=request.getParameter("id");
		if (null!=parentTaskid && !"".equals(parentTaskid)) {
			taskfinish=new WorkTaskfinish();
			taskfinish.setParentTaskid(new Long(parentTaskid));
			taskfinish.setDeleteFlag(0);
		}
		WorkTask workTask=new WorkTask();
		workTask.setParentTaskid(new Long(parentTaskid));
		workTask.setDeleteFlag(0);
		PageManager page_;
		try {
			page_ = workTaskService.queryByPage(workTask, page,Constants.QUERY_COUNTUNION,Constants.QUERY_UNION);
		} catch (PoException e) {
			PoException po=new PoException(e);
			po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
			throw po;
		} 
		return page_; 
	}
	/**
	* @description 根据任务名称查询（任务监控中查询跳转）
	* @return String 
	* @throws Exception
	* @author 李洪宇
	* @version  2014-06-17
	*/
	@RequestMapping(value="getOnlyWorkTaskInfo.action")
	@ActionLog(operateModelNm="督办协办(完成)",operateFuncNm="getOnlyWorkTaskInfo",operateDescribe="任务监控中查询跳转")
	public String getOnlyWorkTaskInfo(Model model,HttpServletRequest request) throws Exception{
		String id=request.getParameter("id");
		WorkTask workTask=null;
		if (null !=id && !"".equals(id)) {
			WorkTaskfinish workTaskfinish=new WorkTaskfinish();
			workTaskfinish.setId(new Long(id));
			WorkTaskfinish workfinish=taskfinishService.get(workTaskfinish);
			workTask=new WorkTask();
			BeanUtils.copyProperties(workfinish, workTask);
			workTask.setId(workfinish.getTaskId());
			WorkTask work=workTaskService.converterWorkTask(workTask);
			request.setAttribute("workTask",work);
			request.setAttribute("taskId", workfinish.getTaskId());
		}
		//将当前登录用户ID传入页面
		User userInfo = SystemSecurityUtils.getUser();
		request.setAttribute("userId", userInfo.getId());
		request.setAttribute("displayName", userInfo.getDisplayName());
		//权限判断
		boolean isLeader=false;
		if (null!=workTask) {
			User user=new User();
			user.setId(workTask.getDirectorId());
			isLeader=userService.isLeader(user,userInfo);
			if(userInfo.getId().intValue()==workTask.getDirectorId().intValue()){//如果当前登录人为任务的负责人
				isLeader=true;
			}
		}
		request.setAttribute("isLeader",isLeader);
		model.addAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));//性能优化 页面获取token
		return "po/workTask/taskOnlyQueryByName";
	}
	/**
	* @description 查询任务（对外调用：批注管理）
	* @return String 
	* @throws Exception
	* @author 李洪宇
	* @version  2014-07-01
	*/
	@RequestMapping(value="getWorkTask.action")
	@ActionLog(operateModelNm="批注管理",operateFuncNm="getWorkTask",operateDescribe="批注管理查看")
	public String getWorkTask(HttpServletRequest request) throws Exception{
		String id=request.getParameter("id");
		WorkTask workTask=null;
		if (null !=id && !"".equals(id)) {
			WorkTaskfinish workTaskfinish=new WorkTaskfinish();
			workTaskfinish.setTaskId(new Long(id));
			WorkTaskfinish workfinish=taskfinishService.get(workTaskfinish);
			workTask=new WorkTask();
			BeanUtils.copyProperties(workfinish, workTask);
			workTask.setId(workfinish.getTaskId());
			workTask=workTaskService.converterWorkTask(workTask);
			request.setAttribute("workTask",workTask);
			request.setAttribute("taskId", workfinish.getTaskId());
		}
		//将当前登录用户ID传入页面
		User userInfo = SystemSecurityUtils.getUser();
		request.setAttribute("userId", userInfo.getId());
		request.setAttribute("displayName", userInfo.getDisplayName());
		//权限判断
		boolean isLeader=false;
		if (null!=workTask) {
			User user=new User();
			user.setId(workTask.getDirectorId());
			isLeader=userService.isLeader(user,userInfo);
			if(userInfo.getId().intValue()==workTask.getDirectorId().intValue()){//如果当前登录人为任务的负责人
				isLeader=true;
			}
		}
		request.setAttribute("isLeader",isLeader);
		return "po/workTask/taskQueryAnno";
	}
	/**
	 * 方法描述：取得子任务(暂存)
	 * @param WorkTaskfinish taskfinish,PageManager page,HttpServletRequest request
	 * @return PageManager
	 * @author 李洪宇
	 * @version  2014-06-18
	 * @see
	 */
	@RequestMapping(value="getChildListForZC.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办(完成-暂存)",operateFuncNm="getChildListForZC",operateDescribe="取得子任务")
	public PageManager getChildListForZC(WorkTaskfinish taskfinish,PageManager page,HttpServletRequest request)throws PoException{
		String parentTaskid=request.getParameter("id");
		if (null!=parentTaskid && !"".equals(parentTaskid)) {
			taskfinish=new WorkTaskfinish();
			taskfinish.setParentTaskid(new Long(parentTaskid));
			taskfinish.setDeleteFlag(0);
		}
		WorkTask workTask=new WorkTask();
		workTask.setParentTaskid(new Long(parentTaskid));
		workTask.setDeleteFlag(0);
		String[] infoStatus={"0","1","2","3","4","6","7","8"};
		workTask.setInfoStatus(infoStatus);
		PageManager page_;
		try {
			page_ = workTaskService.queryByPage(workTask, page,Constants.QUERY_COUNTUNION,Constants.QUERY_UNION);
		} catch (PoException e) {
			PoException po=new PoException(e);
			po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
			throw po;
		} 
		return page_; 
	}
}