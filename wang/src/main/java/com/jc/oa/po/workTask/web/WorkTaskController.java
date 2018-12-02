package com.jc.oa.po.workTask.web;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.WebDataBinder;

import com.jc.oa.po.PoException;
import com.jc.oa.po.anno.domain.Anno;
import com.jc.oa.po.workTask.domain.WorkTask;
import com.jc.oa.po.workTask.domain.WorkTaskfinish;
import com.jc.oa.po.workTask.domain.validator.WorkTaskValidator;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.po.workTask.service.IWorkTaskService;
import com.jc.oa.po.workTask.service.IWorkTaskfinishService;
import com.jc.system.CustomException;
import com.jc.system.common.util.BeanUtil;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.ActionLog;
import com.jc.system.security.util.SettingUtils;


 /**
  * 
  * @title GOA V2.0 个人办公
  * @description 
  * @version  2014年5月6日下午7:20:53
  */
@Controller
@RequestMapping(value="/po/workTask")
public class WorkTaskController extends BaseController{
	
	@Autowired
	private IWorkTaskService workTaskService;
	@Autowired
	private IWorkTaskfinishService workTaskfinishService;
	@Autowired
	private IUserService userService;
	
	@org.springframework.web.bind.annotation.InitBinder("workTask")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new WorkTaskValidator()); 
    }
	
	public WorkTaskController() {
	}

	private WorkTaskValidator wv=new WorkTaskValidator();
	/**
	 * @description 分页查询方法
	 * @param WorkTask task 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-04-23 
	 */
	@RequestMapping(value="manageList.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="manageList",operateDescribe="对督办协办进行分页查询") 
	public PageManager manageList(WorkTask task,final PageManager page,HttpServletRequest request) throws Exception{
		if(StringUtils.isEmpty(task.getOrderBy())) {
			task.addOrderByFieldDesc("createDate");
		}
		task=workTaskService.setSession(task, request);
		PageManager page_ = workTaskService.queryByPage(task, page,Constants.QUERY_RELEVANCE_COUNT,Constants.QUERY_RELEVANCE);
//		用于返回操作 开始
//		StringBuffer url = new StringBuffer(request.getServletPath());
//		if (request.getQueryString() != null) {
//			url.append("?").append(request.getQueryString());
//		}
//		PageManager page_ =null;
//		List<WorkTask> list=null;
//		PageManager pageTemp=workTaskService.queryByPage(task, page,Constants.QUERY_RELEVANCE_COUNT,Constants.QUERY_RELEVANCE);
//		List listTemp=pageTemp.getData();
//		if (null !=listTemp && listTemp.size()>0) {
//			list=new ArrayList<WorkTask>();
//			for (int i = 0; i <listTemp.size(); i++) {
//				WorkTask workTask=(WorkTask)listTemp.get(i);
//				workTask.setReturnURL(url.toString());
//				list.add(workTask);
//			}
//		}
//		page_=pageTemp;
//		page_.setData(list);
		//用于返回操作 结束
		return page_;
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径(任务布置)
	* @throws Exception
	* @author 李洪宇
	* @version  2014-04-23 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="督办协办",operateFuncNm="manage",operateDescribe="督办协办中任务布置页面跳转") 
	public String manage(Model model,HttpServletRequest request) throws Exception{
		User userInfo = SystemSecurityUtils.getUser();
		request.setAttribute("userId",userInfo.getId());
		request.setAttribute("user",userInfo);
		model.addAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));//性能优化 页面获取token
		return "po/workTask/taskArrangement"; 
	}
	/**
	* @description 跳转方法
	* @return String 跳转的路径（任务查询）
	* @throws Exception
	* @author 李洪宇
	* @version  2014-04-23 
	*/
	@RequestMapping(value="queryTask.action")
	@ActionLog(operateModelNm="督办协办",operateFuncNm="queryTask",operateDescribe="督办协办中任务查询页面跳转") 
	public String queryTask(Model model,HttpServletRequest request) throws Exception{
		User userInfo = SystemSecurityUtils.getUser();
		request.setAttribute("userId", userInfo.getId());
		model.addAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));//性能优化 页面获取token
		//xuweiping 2014.12.08 添加首页传入的参数用于页面判断
		request.setAttribute("fromPortal",request.getParameter("fromPortal"));
		return "po/workTask/taskQuery"; 
	}
	
    /**
	 * @description 删除方法(逻辑删除)
	 * @param WorkTask task 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-04-30
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="deleteByIds",operateDescribe="督办协办中删除方法(逻辑删除)") 
	public Integer deleteByIds(WorkTask task,String ids,HttpServletRequest request) throws Exception{
		task.setPrimaryKeys(ids.split(","));
		return workTaskService.updateDeleteFlagByIds(task);
	}

	/**
	 * @description 保存方法
	 * @param WorkTask task 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-04-23
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="save",operateDescribe="督办协办中任务布置") 
	public Map<String,Object> save(@RequestBody Map<String, Object> taskMap,BindingResult result,
			HttpServletRequest request) throws Exception{
		WorkTask task=BeanUtil.map2Object(taskMap, WorkTask.class);
		wv.validate(task, result);
		// 验证bean
		Map<String,Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));//性能优化 页面获取token
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request, (String)getMapValue(taskMap, GlobalContext.SESSION_TOKEN));
		if (resultMap.size() > 0) {
			resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));//性能优化 页面获取token
			return resultMap;
		}
		if (workTaskService.save(task)==1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
		}else {
			resultMap.put(GlobalContext.RESULT_LABELERRORMESSAGE, MessageUtils.getMessage("JC_OA_PO_029"));
		}
		resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));//性能优化 页面获取token
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param WorkTask task 实体类
	* @return Map<String, Object> 
	* @author 李洪宇
	* @version  2014-04-30
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="update",operateDescribe="督办协办中任务修改")
	public Map<String, Object>  update(@RequestBody Map<String, Object> taskMap,BindingResult result,HttpServletRequest request) throws Exception{
		//后台校验
		WorkTask task=BeanUtil.map2Object(taskMap, WorkTask.class);
		wv.validate(task, result,"");
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));//性能优化 页面获取token
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request, (String)getMapValue(taskMap, GlobalContext.SESSION_TOKEN));
		if (resultMap.size() > 0) {
			resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));//性能优化 页面获取token
			return resultMap;
		}
		//修改
		if (workTaskService.update(task,0) == 1) {//修改，添加事件
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
		}
		resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));//性能优化 页面获取token
		return resultMap;
	}
	/**
	 * @description 获取单条记录方法
	 * @param WorkTask task 实体类
	 * @return Task 查询结果
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-04-23
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="get",operateDescribe="督办协办中获取单条记录")
	public WorkTask get(WorkTask task,HttpServletRequest request) throws Exception{
		task.setDeleteFlag(0);
		WorkTask workTask= workTaskService.get(task);
		if(null!=workTask){
			workTask.setTaskProc(workTaskService.queryTaskProc(workTask));
			workTask=workTaskService.converterWorkTask(workTask);
		}
		return workTask;
	}
	/**
	* @description 跳转方法
	* @return String 跳转的路径（查询统计）
	* @throws Exception
	* @author 李洪宇
	* @version  2014-05-04 
	*/
	@RequestMapping(value="queryStatistics.action")
	@ActionLog(operateModelNm="督办协办",operateFuncNm="queryStatistics",operateDescribe="督办协办中查询统计页面跳转")
	public String queryStatistics(HttpServletRequest request) throws Exception{
		User userInfo = SystemSecurityUtils.getUser();
		request.setAttribute("userId",userInfo.getId());
		return "po/workTask/taskStatistics";
	}
	/**
	 * @description 任务统计分页查询方法
	 * @param WorkTask task 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-05-04 
	 */
	@RequestMapping(value="manageListOfTask.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="manageListOfTask",operateDescribe="督办协办中任务统计分页查询")
	public PageManager manageListOfTask(WorkTask task,final PageManager page,
			HttpServletRequest request) throws PoException{
		PageManager page_ = workTaskService.queryTaskTatal(task, page);
		return page_; 
	}
	/**
	* @description 跳转方法
	* @return String 跳转的路径（下发任务）
	* @throws Exception
	* @author 李洪宇
	* @version  2014-05-05
	*/
	@RequestMapping(value="queryNextTask.action")
	@ActionLog(operateModelNm="督办协办",operateFuncNm="queryNextTask",operateDescribe="督办协办中下发任务")
	public String queryNextTask(Model model,HttpServletRequest request) throws Exception{
		User userInfo = SystemSecurityUtils.getUser();
		request.setAttribute("userId",userInfo.getId());
		request.setAttribute("user", userInfo);
		 Long id=new Long(request.getParameter("id"));
		 WorkTask workTask=new WorkTask();
		 workTask.setId(new Long(id));
		 workTask.setDeleteFlag(0);
		 WorkTask taskTemp=workTaskService.get(workTask);
		 taskTemp.setTaskProc(workTaskService.queryTaskProc(taskTemp));
		 WorkTask wrok=workTaskService.converterWorkTask(taskTemp);
		 request.setAttribute("taskTemp", wrok);
		 workTask=new WorkTask();
		 workTask.setParentTaskid(id);
		 workTask.setDeleteFlag(0);
		 //
		 String[] infoStatus={"0","1","2","3","4","6","7","8"};
		 workTask.setInfoStatus(infoStatus);
		 //
		 List<WorkTask> listTemp=workTaskService.converterTaskList(workTask);
		 request.setAttribute("taskListSize", listTemp==null?0:listTemp.size());
		 request.setAttribute("taskList", listTemp);
		 request.setAttribute("parentId", id);
		 model.addAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));//性能优化 页面获取token
		//获取系统设置的参数，督办协办拆分任务时，新建下级任务，上级信息是否回显  0 不回显   1回显
		request.setAttribute("taskParentToSub", SettingUtils.getSetting(SettingUtils.TASK_PARENT_TO_SUB));
		return "po/workTask/taskMissionIssued"; 
	}
	/**
	 * @description 分页查询方法(任务统计)
	 * @param WorkTask task 实体类
	 * @param PageManager page
	 * @return PageManager
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-05-09
	 */
	@RequestMapping(value="getWorkTaskList.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="getWorkTaskList",operateDescribe="督办协办中任务统计分页查询")
	public PageManager getWorkTaskList(WorkTask task,final PageManager page,HttpServletRequest request){
		PageManager page_ = workTaskService.query(task, page);
		return page_; 
	}
	/**
	* @description 根据任务名称查询（跳转）
	* @return String 
	* @throws Exception
	* @author 李洪宇
	* @version  2014-05-13
	*/
	@RequestMapping(value="getWorkTaskInfo.action")
	@ActionLog(operateModelNm="督办协办",operateFuncNm="getWorkTaskInfo",operateDescribe="督办协办中根据任务名称查询")
	public String getWorkTaskInfo(Model model,HttpServletRequest request) throws Exception{
		String id=request.getParameter("id");
		request.setAttribute("taskId", id);
		WorkTask work=null;
		if (null !=id && !"".equals(id)) {
			WorkTask workTask=new WorkTask();
			workTask.setId(new Long(id));
			work=workTaskService.getTask(workTask);//为了防止当任务完成时，原任务将被删除，从而无法查询
		    work=workTaskService.converterWorkTask(work);
			request.setAttribute("workTask",work);
		}
		//将当前登录用户ID传入页面
		User userInfo = SystemSecurityUtils.getUser();
		request.setAttribute("userId",userInfo.getId());
		request.setAttribute("displayName",userInfo.getDisplayName());
		//查询是否存在子任务 start
		List<WorkTask> workTasks=null;
		WorkTask workTask=null;
		if (null!=id && !"".equals(id)) {
			workTask=new WorkTask();
			workTask.setParentTaskid(new Long(id));
			workTask.setDeleteFlag(0);
			try {
				workTasks=workTaskService.queryAll(workTask);
			} catch (CustomException e) {
				PoException po=new PoException(e);
				po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
				throw po;
			}
		}
		request.setAttribute("childSize",workTasks==null?0:workTasks.size());
		//查询是否存在子任务 end
		//权限判断
		boolean isLeader=false;
		if (null!=work) {
			User user=new User();
			user.setId(work.getDirectorId());
			isLeader=userService.isLeader(user,userInfo);
			if(userInfo.getId().intValue()==work.getDirectorId().intValue()){//如果当前登录人为任务的负责人
				isLeader=true;
			}
		}
		if(null!=work && "8".equals(work.getStatus())){//暂存任务无批注
			isLeader=false;
		}
		request.setAttribute("isLeader",isLeader);
		model.addAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));//性能优化 页面获取token
		return "po/workTask/taskQueryByName";
	}
   /**
	 * @description 取消任务模板
	 * @param WorkTask task 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-05-15
	 */
	@RequestMapping(value="cancalTemplate.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="cancalTemplate",operateDescribe="督办协办中取消任务模板")
	public Integer cancalTemplate(WorkTask task,HttpServletRequest request) throws Exception{
		Integer reVal=0;
		if (null !=task && null !=task.getId() && null!=task.getIsTemplet()) {
			reVal=workTaskService.cancalTemplate(task);
		}
		return reVal;
	}
	/**
	 * @description 分页查询方法(子任务)
	 * @param WorkTask task 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-04-23 
	 */
	@RequestMapping(value="manageListOfsubTask.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="manageListOfsubTask",operateDescribe="督办协办中子任务分页查询")
	public PageManager manageListOfsubTask(WorkTask task,final PageManager page,HttpServletRequest request) throws Exception{
		PageManager page_ = workTaskService.queryByPage(task, page,Constants.QUERY_COUNT_TASK,Constants.QUERY_TASK);
		return page_;
	}
	/**
	 * @description 获取单条记录方法(点击任务名称查询)
	 * @param WorkTask task 实体类
	 * @return Task 查询结果
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-05-21
	 */
	@RequestMapping(value="getTask.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="getTask",operateDescribe="督办协办中获取单条记录方法")
	public WorkTask getTask(WorkTask task,HttpServletRequest request) throws Exception{
		task.setDeleteFlag(0);
		WorkTask workTask= workTaskService.getTask(task);
		workTask=workTaskService.converterWorkTask(workTask);
		return workTask;
	}
	/**
	 * 方法描述：查询批注
	 * @param anno
	 * @return
	 * @throws PoException
	 * @author 李洪宇
	 * @version  2014-05-27
	 * @see
	 */
	@RequestMapping(value="queryAnno.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="queryAnno",operateDescribe="督办协办中查询批注")
	public List<Anno> queryAnno(Anno anno,HttpServletRequest request) throws PoException{
		return workTaskService.queryAnnoByWorklog(anno);
	}
	/**
	 * 方法描述：保存批注回复
	 * @param anno
	 * @return
	 * @author 李洪宇
	 * @version  2014-05-27
	 * @see
	 */
	@RequestMapping(value="annoReply.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="annoReply",operateDescribe="督办协办中保存批注回复")
	public Map<String,Object> annoReply(Anno anno,HttpServletRequest request)throws PoException{	
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//权限判断 add by lihongyu at 2014-11-05 start
		boolean isLeader=false;
		//将当前登录用户ID传入页面
		User userInfo = SystemSecurityUtils.getUser();
		if (null!=anno) {
			User user=new User();
			user.setId(anno.getByAnnoUserId());
			isLeader=userService.isLeader(user,userInfo);
			if(userInfo.getId().intValue()==anno.getByAnnoUserId().intValue()){
				isLeader=true;
			}
		}
		if (isLeader==false) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, false);
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_PO_047"));
			return resultMap;
		}
		//权限判断 add by lihongyu at 2014-11-05 end
		if(workTaskService.saveAnnoReply(anno)==1){	
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_OA_PO_010"));
			return resultMap;
		}
		return  null;
	}
	/**
	 * 方法描述：取得子任务(对外)
	 * @param HttpServletRequest request
	 * @return List<WorkTask>
	 * @author 李洪宇
	 * @version  2014-05-27
	 * @see
	 */
	@RequestMapping(value="getChildList.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="getChildList",operateDescribe="督办协办中取得子任务(对外)")
	public List<WorkTask> getChildList(HttpServletRequest request) throws PoException{
		return workTaskService.getChildList(request);
	}
	/**
	 * 方法描述：取得子任务
	 * @param WorkTask task,final PageManager page,HttpServletRequest request
	 * @return PageManager
	 * @author 李洪宇
	 * @version  2014-06-18
	 * @see
	 */
	@RequestMapping(value="getChildListNew.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="getChildListNew",operateDescribe="督办协办中取得子任务")
	public PageManager getChildListNew(WorkTask task,final PageManager page,HttpServletRequest request) throws PoException{
		String parentTaskid=request.getParameter("id");
		if (null!=parentTaskid && !"".equals(parentTaskid)) {
			task=new WorkTask();
			task.setParentTaskid(new Long(parentTaskid));
			task.setDeleteFlag(0);
		}
		PageManager page_ = workTaskService.queryByPage(task, page,Constants.QUERY_COUNTUNION,Constants.QUERY_UNION); 
		return page_;
	}
	/**
	 * @description 分页查询方法(任务模板)
	 * @param WorkTask task 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-06-09 
	 */
	@RequestMapping(value="manageListOfModel.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="manageListOfModel",operateDescribe="督办协办中任务模板分页查询")
	public PageManager manageListOfModel(WorkTask task,final PageManager page,HttpServletRequest request) throws Exception{
		PageManager page_ = workTaskService.queryByPageAll(task, page,Constants.QUERY_COUNTUNION,Constants.QUERY_UNION); 
		return page_;
	}
	/**
	* @description 用于批注管理中查询详细批注信息（关于督办协办）
	* @return String 
	* @throws Exception
	* @author 李洪宇
	* @version  2014-06-11 
	*/
	@RequestMapping(value="queryAnnotationUrl.action")
	@ActionLog(operateModelNm="督办协办",operateFuncNm="queryAnnotationUrl",operateDescribe="督办协办中查询详细批注信息")
	public String queryAnnotationUrl(Long id,HttpServletRequest request) throws Exception{
		String urlTemp="";
		if (null==id || id==0) {
			return urlTemp;
		}
		WorkTask workTask=new WorkTask();
		workTask.setId(id);
		WorkTask work=workTaskService.getTask(workTask);
		if (null!=work && null!=work.getStatus()) {
			String status=work.getStatus();
			if ("3".equals(status)) {
				urlTemp="redirect:/po/taskfinish/getWorkTask.action?id="+id+"&time="+new Date();
			}else {
				urlTemp="redirect:/po/workTask/getWorkTask.action?id="+id+"&time="+new Date();
			}
		}else{
			return "po/workTask/taskAnnoNoData";//没有查询到数据时跳转到该页面
		}
		return urlTemp;
	}
	/**
	* @description 跳转方法
	* @return String 跳转的路径（任务监控）
	* @throws Exception
	* @author 李洪宇
	* @version  2014-06-17
	*/
	@RequestMapping(value="queryTaskAll.action")
	@ActionLog(operateModelNm="督办协办",operateFuncNm="queryTaskAll",operateDescribe="任务监控")
	public String queryTaskAll(Model model,HttpServletRequest request) throws Exception{
		User userInfo = SystemSecurityUtils.getUser();
		request.setAttribute("userId",userInfo.getId());
		//返回 开始
//		StringBuffer url = new StringBuffer(request.getServletPath());
//		if (request.getQueryString() != null) {
//			url.append("?").append(request.getQueryString());
//		}
//		request.setAttribute("returnURL",url.toString());
		//返回 结束
		model.addAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));//性能优化 页面获取token
		return "po/workTask/taskQueryAll"; 
	}
	/**
	* @description 根据任务名称查询（任务监控中查询跳转）
	* @return String 
	* @throws Exception
	* @author 李洪宇
	* @version  2014-06-17
	*/
	@RequestMapping(value="getOnlyWorkTaskInfo.action")
	@ActionLog(operateModelNm="督办协办",operateFuncNm="getOnlyWorkTaskInfo",operateDescribe="任务监控中查询跳转")
	public String getOnlyWorkTaskInfo(Model model,HttpServletRequest request) throws Exception{
		String id=request.getParameter("id");
		request.setAttribute("taskId", id);
		WorkTask work=null;
		if (null !=id && !"".equals(id)) {
			WorkTask workTask=new WorkTask();
			workTask.setId(new Long(id));
			work=workTaskService.get(workTask);
		    work=workTaskService.converterWorkTask(work);
			//返回 开始
//			work.setReturnURL(request.getParameter("url"));
			//返回 结束
			request.setAttribute("workTask",work);
		}
		//将当前登录用户ID传入页面
		User userInfo = SystemSecurityUtils.getUser();
		request.setAttribute("userId", userInfo.getId());
		request.setAttribute("displayName", userInfo.getDisplayName());
		//查询是否存在子任务 start
		List<WorkTask> workTasks=null;
		WorkTask workTask=null;
		if (null!=id && !"".equals(id)) {
			workTask=new WorkTask();
			workTask.setParentTaskid(new Long(id));
			workTask.setDeleteFlag(0);
			try {
				workTasks=workTaskService.queryAll(workTask);
			} catch (CustomException e) {
				PoException po=new PoException(e);
				po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
				throw po;
			}
		}
		request.setAttribute("childSize",workTasks==null?0:workTasks.size());
		//查询是否存在子任务 end
		//权限判断
		boolean isLeader=false;
		if (null!=work) {
			User user=new User();
			user.setId(work.getDirectorId());
			isLeader=userService.isLeader(user,userInfo);
			if(userInfo.getId().intValue()==work.getDirectorId().intValue()){//如果当前登录人为任务的负责人
				isLeader=true;
			}
		}
		request.setAttribute("isLeader",isLeader);
		//用于返回操作 开始
		request.setAttribute("url",request.getParameter("url"));
		//用于返回操作 结束
		model.addAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));//性能优化 页面获取token
		return "po/workTask/taskOnlyQueryByName";
	}
	/**
	* @description 跳转方法
	* @return String 跳转的路径（任务监控查询统计）
	* @throws Exception
	* @author 李洪宇
	* @version  2014-06-17
	*/
	@RequestMapping(value="queryStatisticsNew.action")
	@ActionLog(operateModelNm="督办协办",operateFuncNm="queryStatisticsNew",operateDescribe="任务监控查询统计")
	public String queryStatisticsNew(HttpServletRequest request) throws Exception{
		//将当前登录用户ID传入页面
	    request.setAttribute("userOrgId",SystemSecurityUtils.getUser().getOrgId());
		return "po/workTask/taskStatisticsNew";
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
		request.setAttribute("taskId", id);
		WorkTask work=null;
		if (null !=id && !"".equals(id)) {
			WorkTask workTask=new WorkTask();
			workTask.setId(new Long(id));
			work=workTaskService.get(workTask);
		    work=workTaskService.converterWorkTask(work);
			request.setAttribute("workTask",work);
		}
		//将当前登录用户ID传入页面
		User userInfo = SystemSecurityUtils.getUser();
		request.setAttribute("userId", userInfo.getId());
		request.setAttribute("displayName", userInfo.getDisplayName());
		//查询是否存在子任务 start
		List<WorkTask> workTasks=null;
		WorkTask workTask=null;
		if (null!=id && !"".equals(id)) {
			workTask=new WorkTask();
			workTask.setParentTaskid(new Long(id));
			workTask.setDeleteFlag(0);
			try {
				workTasks=workTaskService.queryAll(workTask);
			} catch (CustomException e) {
				PoException po=new PoException(e);
				po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
				throw po;
			}
		}
		request.setAttribute("childSize",workTasks==null?0:workTasks.size());
		//查询是否存在子任务 end
		//权限判断
		boolean isLeader=false;
		if (null!=work) {
			User user=new User();
			user.setId(work.getDirectorId());
			isLeader=userService.isLeader(user,userInfo);
			if(userInfo.getId().intValue()==work.getDirectorId().intValue()){//如果当前登录人为任务的负责人
				isLeader=true;
			}
		}
		request.setAttribute("isLeader",isLeader);
		return "po/workTask/taskQueryAnno";
	}
	/**
	 * @description 获取单条记录方法(任务模板提取任务)
	 * @param WorkTask task 实体类,HttpServletRequest request
	 * @return WorkTask 查询结果
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-07-11
	 */
	@RequestMapping(value="getAll.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="get",operateDescribe="督办协办中任务模板提取任务")
	public WorkTask getAll(WorkTask task,HttpServletRequest request) throws Exception{
		task.setDeleteFlag(0);
		WorkTask workTask=workTaskService.get(task);
		if(null==workTask){
			workTask=new WorkTask();
			WorkTaskfinish workTaskfinish=new WorkTaskfinish();
			workTaskfinish.setTaskId(task.getId());
			workTaskfinish.setDeleteFlag(0);
			WorkTaskfinish wtf=workTaskfinishService.get(workTaskfinish);
			if (null!=wtf) {
				BeanUtils.copyProperties(wtf, workTask);
				workTask.setTaskProc(workTaskService.queryTaskProc(workTask));
				workTask=workTaskService.converterWorkTask(workTask);
			}else{
				workTask=null;
			}
		}else {
			workTask.setTaskProc(workTaskService.queryTaskProc(workTask));
			workTask=workTaskService.converterWorkTask(workTask);
		}
		return workTask;
	}
   /**
	 * @description 任务操作校验
	 * @param String taskId
	 * @param String operateType
	 * @return Integer
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-07-28
	 */
	@RequestMapping(value="checkOperation.action")
	@ResponseBody
	@ActionLog(operateModelNm="任务操作校验",operateFuncNm="checkOperation",operateDescribe="督办协办中取消任务模板")
	public Integer checkOperation(String taskId,String operateType,HttpServletRequest request) throws Exception{
		return workTaskService.checkOperation(Long.valueOf(taskId),operateType);
	}
	/**
	 * @description 获取单条记录方法（修改使用）
	 * @param WorkTask task 实体类
	 * @return Task 查询结果
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-10-10
	 */
	@RequestMapping(value="queryInfoForUpdate.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="queryInfoForUpdate",operateDescribe="督办协办中获取单条记录修改")
	public WorkTask queryInfoForUpdate(WorkTask task,HttpServletRequest request) throws Exception{
		task.setDeleteFlag(0);
		WorkTask workTask= workTaskService.get(task);
		if(null!=workTask){
			workTask.setTaskProc(workTaskService.queryTaskProc(workTask));
			workTask=workTaskService.converterWorkTask(workTask);
		}
		workTask.setTokenTemp(super.getToken(request));//token性能优化
		return workTask;
	}
	/**
	 * 方法描述：取得子任务(对外)
	 * @param WorkTask task,final PageManager page,HttpServletRequest request
	 * @return PageManager
	 * @author 李洪宇
	 * @version  2014-10-29
	 * @see
	 */
	@RequestMapping(value="getChildListForPlan.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="getChildListForPlan",operateDescribe="督办协办中取得子任务(对外)")
	public PageManager getChildListForPlan(WorkTask task,final PageManager page,HttpServletRequest request) throws PoException{
		String parentTaskid=request.getParameter("id");
		if (null!=parentTaskid && !"".equals(parentTaskid)) {
			task=new WorkTask();
			task.setParentTaskid(new Long(parentTaskid));
			task.setDeleteFlag(0);
			String[] infoStatus={"1","2","6"};
			task.setInfoStatus(infoStatus);
		}
		PageManager page_ = workTaskService.queryByPage(task, page,Constants.QUERY_COUNTUNION,Constants.QUERY_UNION); 
		return page_;
	}
	/**
	 * 方法描述：取得子任务
	 * @param WorkTask task,final PageManager page,HttpServletRequest request
	 * @return PageManager
	 * @author 李洪宇
	 * @version  2014-06-18
	 * @see
	 */
	@RequestMapping(value="getChildListNewForZC.action")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="getChildListNewForZC",operateDescribe="督办协办中取得子任务(暂存)")
	public PageManager getChildListNewForZC(WorkTask task,final PageManager page,HttpServletRequest request) throws PoException{
		String parentTaskid=request.getParameter("id");
		if (null!=parentTaskid && !"".equals(parentTaskid)) {
			task=new WorkTask();
			task.setParentTaskid(new Long(parentTaskid));
			task.setDeleteFlag(0);
		}
		String[] infoStatus={"0","1","2","3","4","6","7","8"};
		task.setInfoStatus(infoStatus);
		PageManager page_ = workTaskService.queryByPage(task, page,Constants.QUERY_COUNTUNION,Constants.QUERY_UNION); 
		return page_;
	}
	/**
	 * @description 分页查询方法（暂存）
	 * @param WorkTask task 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014-11-25 
	 */
	@RequestMapping(value="manageListOfZC.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="督办协办",operateFuncNm="manageListOfZC",operateDescribe="对暂存任务进行分页查询")
	public PageManager manageListOfZC(WorkTask task,final PageManager page,HttpServletRequest request) throws Exception{
		if(StringUtils.isEmpty(task.getOrderBy())) {
			task.addOrderByFieldDesc("createDate");
		}
		User userInfo = SystemSecurityUtils.getUser();
		task.setSponsorId(userInfo.getId());
		PageManager page_ = workTaskService.queryByPage(task, page,Constants.QUERY_ZC_TASK_COUNT,Constants.QUERY_ZC_TASK);
		return page_;
	}
}