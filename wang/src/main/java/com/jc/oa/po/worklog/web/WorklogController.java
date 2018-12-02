package com.jc.oa.po.worklog.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.constants.OrderType;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.po.PoException;
import com.jc.oa.po.anno.domain.Anno;
import com.jc.oa.po.diary.domain.Diary;
import com.jc.oa.po.diary.service.IDiaryService;
import com.jc.oa.po.readingstatus.domain.ReadingStatus;
import com.jc.oa.po.workTask.domain.WorkTask;
import com.jc.oa.po.workTask.domain.WorkTaskHistory;
import com.jc.oa.po.workTask.service.IWorkTaskService;
import com.jc.oa.po.worklog.domain.Worklog;
import com.jc.oa.po.worklog.domain.WorklogContent;
import com.jc.oa.po.worklog.domain.validator.WorklogContentValidator;
import com.jc.oa.po.worklog.domain.validator.WorklogValidator;
import com.jc.oa.po.worklog.service.IWorklogService;
import com.jc.system.CustomException;
import com.jc.system.common.util.BeanUtil;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.ExcuteExcelUtil;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.LeftRight;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.ActionLog;
import com.jc.system.security.util.SettingUtils;
import com.jc.system.security.util.UserUtils;


/**
 * @title 个人办公
 * @description  controller类
 * @version  2014-05-04
 */
 
@Controller
@RequestMapping(value="/po/worklog")
public class WorklogController extends BaseController{
	
	private Logger log = Logger.getLogger(WorklogController.class);
	
	@Autowired
	private IWorklogService worklogService;
	
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IDiaryService diaryService;
	
	@org.springframework.web.bind.annotation.InitBinder("worklog")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new WorklogValidator()); 
    }
	
	public WorklogController() {
	}
	
	private WorklogValidator wv = new WorklogValidator();
	private WorklogContentValidator wcv = new WorklogContentValidator();
	
	/** 方法描述：跳转方法 进入下属日日志无信息页
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014年5月14日上午9:31:19
	 * @see
	 */
	@RequestMapping(value="worklogNoUnderling.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日志",operateFuncNm="worklogNoUnderling",operateDescribe="进入下属日志无信息页")
	public String diaryNoUnderling(HttpServletRequest request) throws Exception{
		return "po/worklog/worklogNoUnderling"; 
	}
	
	/**
	 * @description 我的日志查询方法
	 * @param Worklog worklog 实体类
	 * @return List 查询结果
	 * @throws PoException
	 * @author 李新桐
	 * @version  2014-05-04 
	 * @throws PoException 
	 */
	@RequestMapping(value="getMyworklogList.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="manageList",operateDescribe="我的日志查询方法") 
	public List<Worklog> manageList(Worklog worklog,PageManager page,HttpServletRequest request) throws PoException{
		try {
			if(worklog.getCreateUser()==null){
				worklog.setCreateUser(SystemSecurityUtils.getUser().getId());
			}
			return worklogService.queryAll(worklog);
		} catch (CustomException e) {
			PoException po = new PoException(e);
			throw po;
		}
	}
	
	/**
	 * @description 日志汇总查询方法
	 * @param Worklog worklog 实体类
	 * @return PageManager 查询结果
	 * @author 李新桐
	 * @version  2014-05-04 
	 * @throws PoException 
	 * @throws Exception 
	 */
	@RequestMapping(value="getMyworklogCollect.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="getMyworklogCollect",operateDescribe="日志汇总查询方法") 
	public PageManager getMyworklogCollect(PageManager page,Worklog worklog,HttpServletRequest request) throws Exception{
		if(worklog.getCreateUser()==null){
			worklog.setCreateUser(SystemSecurityUtils.getUser().getId());
		}
		worklog.addOrderByField("worklogDate", OrderType.DESC);
		
		PageManager pageManager = worklogService.queryMyworklogCollect(worklog, page);
		return pageManager;
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author 李新桐 
	* @version  2014-05-04 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="manage",operateDescribe="跳转方法") 
	public String manage(HttpServletRequest request) throws PoException{
		return "po/worklog/worklog"; 
	}
	
	/**
	 * @description 工作日志领导批注跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 李新桐 
	 * @version  2014-05-04 
	 */
	@RequestMapping(value="worklogLeaderAnno.action")
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="worklogLeaderAnno",operateDescribe="工作日志领导批注跳转方法") 
	public String worklogLeaderAnno(Model model,HttpServletRequest request) throws PoException{
		model.addAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));//性能优化 页面获取token
		return "po/worklog/worklogLeaderAnno"; 
	}
	
	/**
	 * @description "我的日志-日历形式"跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014-05-04 
	 */
	@RequestMapping(value="myWorklogCalendarSkip.action")
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="myWorklogCalendarSkip",operateDescribe="我的日志-日历形式 跳转方法") 
	public String myWorklogCalendarSkip(Model model,HttpServletRequest request) throws PoException{
		model.addAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));
		//将当前登录用户ID传入页面
		User userInfo = SystemSecurityUtils.getUser();
		request.setAttribute("currentLoginUser",userInfo.getId());
		//获取系统设置的参数，判断所选日期的操作权限  xuweiping 20141126
		request.setAttribute("workLogDay", SettingUtils.getSetting(SettingUtils.WORKLOG_DAY));
		return "po/worklog/myWorklogCalendar"; 
	}
	
	/**
	 * @description 获取系统参数WORKLOG_DAY的值
	 * @return Map
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-11-28
	 */
	@RequestMapping(value="getWorkLogDay.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="getWorkLogDay",operateDescribe="获取系统参数WORKLOG_DAY的值") 
	public Map<String, Object> getWorkLogDay(Worklog worklog,HttpServletRequest request) throws PoException{
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("workLogDay", SettingUtils.getSetting(SettingUtils.WORKLOG_DAY));
			return resultMap;
	}
	
	/**
	 * @description "我的日志-列表形式"跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014-05-04 
	 */
	@RequestMapping(value="myWorklogListSkip.action")
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="myWorklogListSkip",operateDescribe="我的日志-列表形式 跳转方法") 
	public String myWorklogListSkip(Model model,HttpServletRequest request) throws PoException{
		model.addAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));
		return "po/worklog/myWorklogList"; 
	}
	
	/**
	 * @description "我的日志-列表形式"跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014-05-04 
	 */
	@RequestMapping(value="myWorklogDetailSkip.action")
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="myWorklogDetailSkip",operateDescribe="我的日志-列表形式 跳转方法") 
	public String myWorklogDetailSkip(HttpServletRequest request) throws PoException{
		return "po/worklog/myWorklogDetail"; 
	}
	
	/**
	 * @description "日志管理"跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014-05-04 
	 */
	@RequestMapping(value="worklogManageSkip.action")
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="worklogManageSkip",operateDescribe="日志管理 跳转方法") 
	public String worklogManageSkip(Model model,HttpServletRequest request) throws PoException{
		model.addAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));
		return "po/worklog/worklogManage"; 
	}
	
	/**
	 * @description "共享日志"跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014-05-04 
	 */
	@RequestMapping(value="worklogShareSkip.action")
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="worklogShareSkip",operateDescribe="共享日志 跳转方法") 
	public String worklogShareSkip(HttpServletRequest request) throws PoException{
		//将当前登录用户ID传入页面
		User userInfo = SystemSecurityUtils.getUser();
		request.setAttribute("userId",userInfo.getId());
		request.setAttribute("displayName",userInfo.getDisplayName());
		return "po/worklog/worklogShare"; 
	}
	
	/**
	 * @description "共享日志-列表形式"跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014-05-04 
	 */
	@RequestMapping(value="worklogShareListSkip.action")
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="worklogShareListSkip",operateDescribe="共享日志-列表形式 跳转方法") 
	public String worklogShareListSkip(HttpServletRequest request) throws PoException{
		//将当前登录用户ID传入页面
		User userInfo = SystemSecurityUtils.getUser();
		request.setAttribute("userId",userInfo.getId());
		request.setAttribute("displayName",userInfo.getDisplayName());
		return "po/worklog/worklogShareList"; 
	}

/**
	 * @description 删除方法
	 * @param Worklog worklog 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014-05-04
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="deleteByIds",operateDescribe="删除方法") 
	public Map<String, Object> deleteByIds(Worklog worklog,String ids,HttpServletRequest request) throws PoException{
		worklog.setPrimaryKeys(ids.split(","));
		if(worklogService.deleteByworklogIdLogic(worklog) == 1){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
			return resultMap;
		}
		return null;
	}

	/**
	 * @description 保存方法
	 * @param Worklog worklog 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014-05-04
	 * @throws CustomException 
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="save",operateDescribe="保存方法") 
	public Map<String,Object> save(@RequestBody Map<String, Object> worklogMap, BindingResult result,
			HttpServletRequest request) throws CustomException{
		// 验证bean
		Worklog worklog = BeanUtil.map2Object(worklogMap, Worklog.class);
		wv.validate(worklog, result);	
		List<WorklogContent> todayLogList = worklog.getTodayLogs();
		for (WorklogContent wc : todayLogList) {
			wcv.validate(wc, result);
		}
		List<WorklogContent> tomorrowRemindList = worklog.getTomorrowReminds();
		for (WorklogContent wc : tomorrowRemindList) {
			wcv.validate(wc, result);
		}
		
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		//验证token
		resultMap = validateToken(request, (String)getMapValue(worklogMap, GlobalContext.SESSION_TOKEN));
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		// 保存日志
		if (worklogService.save(worklog) == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param Worklog worklog 实体类
	* @return Integer 更新的数目
	* @author 李新桐
	* @version  2014-05-04 
	 * @throws CustomException 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="update",operateDescribe="修改方法")
	public Map<String, Object> update(@RequestBody Map<String, Object> worklogMap, BindingResult result,
			HttpServletRequest request) throws CustomException{
		
		// 验证bean
		Worklog worklog = BeanUtil.map2Object(worklogMap, Worklog.class);
		wv.validate(worklog, result);	
		List<WorklogContent> todayLogList = worklog.getTodayLogs();
		for (WorklogContent wc : todayLogList) {
			wcv.validate(wc, result);
		}
		List<WorklogContent> tomorrowRemindList = worklog.getTomorrowReminds();
		for (WorklogContent wc : tomorrowRemindList) {
			wcv.validate(wc, result);
		}
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		//验证token
//		resultMap = validateToken(request);
		resultMap = validateToken(request, (String)getMapValue(worklogMap, GlobalContext.SESSION_TOKEN));
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = worklogService.update(worklog);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		String token = super.getToken(request);//获取token
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	 * @description 获取单条记录方法
	 * @param Worklog worklog 实体类
	 * @return Worklog 查询结果
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014-05-04
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="get",operateDescribe="获取单条记录方法")
	public Worklog get(Worklog worklog,HttpServletRequest request) throws Exception{
		worklog.setDeleteFlag(0);
		if(worklog.getCreateUser()==null&&worklog.getId()==null){
			worklog.setCreateUser(SystemSecurityUtils.getUser().getId());
		}
		Worklog worklogTemp=worklogService.getWorklog(worklog);
		if (null!=worklogTemp) {
			String tokenTemp=super.getToken(request);
			worklogTemp.setTokenTemp(tokenTemp);//获取token
		}
//		return worklogService.getWorklog(worklog);
		return worklogTemp;
	}
	
	/**
	 * 方法描述：获得昨日提醒
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月28日下午2:14:31
	 * @throws CustomException 
	 * @throws PoException 
	 * @see
	 */
	@RequestMapping(value="getYesterdayRemind.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="getYesterdayRemind",operateDescribe="获得昨日提醒")
	public Map<String, Object> getYesterdayRemind(Worklog worklog,HttpServletRequest request) throws PoException, CustomException{
		worklog.setCreateUser(SystemSecurityUtils.getUser().getId());
		String yesterday = DateUtils.addOrSubtractDaysReturnString(worklog.getWorklogDate(),-1);
		worklog.setWorklogDate(null);
		worklog.setWorklogDateBegin(DateUtils.parseDate(yesterday+" 00:00:00"));
		worklog.setWorklogDateEnd(DateUtils.parseDate(yesterday+" 23:59:59"));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		worklog=worklogService.getWorklog(worklog);
		if(worklog== null){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, "昨日还没有日志" );
			return resultMap;
		}
		WorklogContent worklogContent = new WorklogContent();
		worklogContent.setWorklogId(worklog.getId());
		worklogContent.setContentType("1");
		List<WorklogContent> worklogContents = worklogService.getYesterdayRemind(worklogContent);
		resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		resultMap.put("worklogContents", worklogContents);
		return resultMap;
	}
	
	/**
	 * 方法描述：某天的过去五天的日志数
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月13日下午12:57:16
	 * @see
	 */
	@RequestMapping(value="queryMyWorklogPastFiveDays.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="queryMyWorklogPastFiveDays",operateDescribe="某天的过去五天的日志数")
	public List<Worklog> queryMyWorklogPastFiveDays(Worklog worklog,HttpServletRequest request){
		worklog.setCreateUser(SystemSecurityUtils.getUser().getId());
		return worklogService.queryMyWorklogPastFiveDays(worklog);
	}
	
	/**
	 * 方法描述：待办任务插入日程
	 * @param diary
	 * @return
	 * @author 李新桐
	 * @version  2014年5月12日上午10:08:38
	 * @throws PoException 
	 * @see
	 */
	@RequestMapping(value="taskToDiary.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="taskToDiary",operateDescribe="待办任务插入日程")
	public  Map<String, Object> taskToDiary(Diary diary,HttpServletRequest request) throws PoException{
		if(worklogService.taskToDiary(diary)==1){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			return resultMap;
		}
		return  null;
		//return worklogService.taskToDiary(diary);
	}
	
	/**
	 * 方法描述：根据日期查询日程
	 * @param diary
	 * @return
	 * @author 李新桐
	 * @version  2014年5月12日上午10:08:38
	 * @throws PoException 
	 * @see
	 */
	@RequestMapping(value="queryDiary.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="queryDiary",operateDescribe="根据日期查询日程")
	public List<Diary> queryDiary(Diary diary,HttpServletRequest request) throws PoException{
		if(diary.getPossessorId()==null){
			diary.setPossessorId(SystemSecurityUtils.getUser().getId());
		}
		return worklogService.queryDiary(diary);
	}
	
	/** 方法描述：共享查询tree
	 * @return List<Department>
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014年5月16日下午9:32:15
	 * @see
	 */
	@RequestMapping(value="queryForShareTree.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="queryForShareTree",operateDescribe="共享查询tree")
	public List<Department> queryForShareTree(HttpServletRequest request) throws Exception{
		return worklogService.queryForShareTree(); 
	}
	
	/** 方法描述：共享查询左右人员选择树数据
	 * @return List<User>
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014年5月16日下午9:32:15
	 * @see
	 */
	@RequestMapping(value="queryForShareLeftRight.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="queryForShareLeftRight",operateDescribe="共享查询左右人员选择树数据")
	public List<LeftRight> queryForShareLeftRight(HttpServletRequest request) throws Exception{
		List<User> users =  worklogService.queryForShareLRTree(); 
		List<LeftRight> result = new ArrayList<>();
		for (User user : users) {
			LeftRight item = new LeftRight();
			Long id = user.getId();
			item.setCode(id.toString());
			item.setText(user.getDisplayName());
			result.add(item);
		}
		
		return result;
	}
	
	/**
	 * 方法描述：查询所有共享给我的日志
	 * @return
	 * @author 李新桐
	 * @version  2014年5月16日上午10:38:25
	 * @see
	 */
	@RequestMapping(value="getShareWorklogList.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="getShareWorklogList",operateDescribe="查询所有共享给我的日志")
	public List<Worklog> getShareWorklogList(Worklog worklog,HttpServletRequest request){
		return worklogService.getShareWorklogList(worklog);
	}
	
	/**
	 * 方法描述：查询所有共享给我的日志
	 * @return
	 * @author 李新桐
	 * @version  2014年5月16日上午10:38:25
	 * @see
	 */
	@RequestMapping(value="getShareWorklogPage.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="getShareWorklogPage",operateDescribe="查询所有共享给我的日志")
	public PageManager getShareWorklogPage(PageManager pageManager,Worklog worklog,HttpServletRequest request){
		if(StringUtils.isEmpty(worklog.getOrderBy())) {
			worklog.addOrderByFieldDesc("t.WORKLOG_DATE");
		}
		return worklogService.getShareWorklogPage(worklog,pageManager);
	}
	
	@Autowired
	private IWorkTaskService workTaskService;
	/**
	 * 方法描述：查询待办列表
	 * @return
	 * @author 李新桐
	 * @version  2014年5月12日下午2:53:27
	 * @see
	 */
	@RequestMapping(value="queryWorkTask.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="queryTask",operateDescribe="查询待办列表")
	public PageManager queryTask(PageManager pageManager,WorkTask workTask,HttpServletRequest request) throws PoException{
		if(workTask.getDirectorId()==null){
			workTask.setDirectorId(SystemSecurityUtils.getUser().getId());
		}
//		PageManager pageManager1 = workTaskService.queryWorkTaskPage(workTask, pageManager);
		pageManager.setData(worklogService.queryWorkTask(workTask));
//		pageManager.setPage(1);
//		pageManager.setTotalPages(1);
//		pageManager.setTotalCount(1);
//		pageManager.setPageRows(-1);
//		pageManager.setsEcho(1);
//		pageManager.
		return pageManager;
	}
	
	/**
	 * 方法描述：查询批注
	 * @param anno
	 * @return
	 * @throws PoException
	 * @author 李新桐
	 * @version  2014年5月14日上午9:24:54
	 * @see
	 */
	@RequestMapping(value="queryAnno.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="queryAnno",operateDescribe="查询批注")
	public List<Anno> queryAnno(Anno anno,HttpServletRequest request) throws PoException{
		return worklogService.queryAnnoByWorklog(anno);
	}
	/**
	 * 方法描述：保存批注回复
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年5月14日下午1:21:04
	 * @see
	 */
	@RequestMapping(value="annoReply.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="annoReply",operateDescribe="保存批注回复")
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
		if(worklogService.saveAnnoReply(anno)==1){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_OA_PO_010"));
			return resultMap;
		}
		return  null;
	}
	
	
	/**
	 * 方法描述：判断某天时候已经存在日志
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月14日下午3:14:28
	 * @see
	 */
	@RequestMapping(value="worklogAllowInsert.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="worklogAllowInsert",operateDescribe="判断某天时候已经存在日志")
	public Map<String,Object> worklogAllowInsert(Worklog worklog,Model model,HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String token = super.getToken(request);//获取token
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		worklog.setCreateUser(SystemSecurityUtils.getUser().getId());
		if(worklogService.worklogAllowInsert(worklog)==0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, true);
			return resultMap;
		}else{
			resultMap.put(GlobalContext.RESULT_SUCCESS, false);
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, "日志已经存在不能重复创建!");
			return resultMap;
		}
	}
	
	/**
	 * 方法描述：待办任务汇报
	 * @param wth
	 * @return
	 * @author 李新桐
	 * @version  2014年5月19日上午10:19:43
	 * @see
	 */
	@RequestMapping(value="taskReport.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="taskReport",operateDescribe="待办任务汇报")
	public Map<String,Object> taskReport(@RequestBody Map<String, Object> workTaskHistoryMap, BindingResult result,
			HttpServletRequest request) throws CustomException{
		
		WorkTaskHistory workTaskHistory = BeanUtil.map2Object(workTaskHistoryMap, WorkTaskHistory.class);
		Map<String, Object> resultMap  = new HashMap<String,Object>();
		// 验证token
		resultMap = validateToken(request, (String)getMapValue(workTaskHistoryMap, GlobalContext.SESSION_TOKEN));
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		if(worklogService.taskReport(workTaskHistory)==1){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
		}
		resultMap.put(GlobalContext.SESSION_TOKEN, super.getToken(request));//性能优化 页面获取token
		return resultMap;
	}
	
	/**
	 * 方法描述：保存阅读情况
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月22日上午9:49:21
	 * @throws CustomException 
	 * @see
	 */
	@RequestMapping(value="savaReadingStatus.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="savaReadingStatus",operateDescribe="保存阅读情况")
	public Map<String,Object> savaReadingStatus(Worklog worklog,HttpServletRequest request) throws CustomException{
		ReadingStatus readingStatus = new ReadingStatus();
		readingStatus.setWorklogId(worklog.getId());
		readingStatus.setBusinessType("2");
		readingStatus.setSubordinateId(worklog.getCreateUser());
		readingStatus.setReaderId(SystemSecurityUtils.getUser().getId());;
		worklogService.savaReadingStatus(readingStatus);
		return null;
	}
	/**
	 * 方法描述：更新共享范围
	 * @return
	 * @author 李新桐
	 * @version  2014年5月22日下午4:22:30
	 * @throws CustomException 
	 * @see
	 */
	@RequestMapping(value="updateShareUser.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="updateShareUser",operateDescribe="更新共享范围")
	public Map<String,Object> updateShareUser(Worklog worklog,HttpServletRequest request) throws CustomException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//查询当前日志是否存在 add by lihongyu at 2014-11-5 start
		Worklog myLog=new Worklog();
		myLog.setId(worklog.getId());
		myLog.setDeleteFlag(0);
		Worklog wLog=worklogService.get(myLog);
		if (null==wLog) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_COMMON_015"));
			return resultMap;
		}
		//查询当前日志是否存在 add by lihongyu at 2014-11-5 end
		int n=worklogService.updateShareUser(worklog);
		resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
		return resultMap;
	}
	
	/**
	 * 方法描述：校验将日志共享短信提醒时被提醒人是否存在电话号
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年6月3日下午2:05:44
	 * @throws PoException 
	 * @see
	 */
	@RequestMapping(value="validRemind.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="validRemind",operateDescribe="校验将日志共享短信提醒时被提醒人是否存在电话号")
	public Map<String,Object> validRemind(Worklog worklog,HttpServletRequest request) throws PoException{
		String userIds = worklog.getShareUserIds();
		return worklogService.validRemind(userIds);
	}
	
	/**
	 * 方法描述：保存领导批注
	 * @param anno
	 * @param result
	 * @param request
	 * @return
	 * @throws CustomException
	 * @author 李新桐
	 * @version  2014年6月4日上午11:06:34
	 * @see
	 */
	@RequestMapping(value = "saveAnno.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="saveAnno",operateDescribe="保存领导批注")
	public Map<String,Object> saveAnno(@Valid Anno anno, BindingResult result,
			HttpServletRequest request) throws CustomException{
		Map<String,Object> resultMap = validateBean(result);
		if(!"false".equals(resultMap.get("success"))){
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
			worklogService.saveAnno(anno);
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_OA_PO_008"));
		}
		return resultMap;
	}
	
	/**
	 * 方法描述：更新批注的阅读情况
	 * @return
	 * @author 李新桐
	 * @version  2014年5月28日上午8:42:51
	 * @throws CustomException 
	 * @see
	 */
	@RequestMapping(value="annoReading.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="annoReading",operateDescribe="更新批注的阅读情况")
	public Map<String,Object> annoReading(Anno anno,String status,HttpServletRequest request) throws CustomException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Worklog worklog = new Worklog();
		worklog.setId(anno.getBusinessId());
		worklog = worklogService.get(worklog);
		if(worklog!=null && worklog.getCreateUser().longValue()==SystemSecurityUtils.getUser().getId().longValue()){
			if(worklogService.annoReading(anno,status)!=0){
				resultMap.put("success", "true");
			}
		}
		return resultMap;
	}
	
	/**
	 * 方法描述：根据开始和结束时间查询日志
	 * @param dates
	 * @return
	 * @throws CustomException
	 * @author 李新桐
	 * @version  2014年6月4日下午7:59:34
	 * @see
	 */
	@RequestMapping(value="showWorklogStatus.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="showWorklogStatus",operateDescribe="根据开始和结束时间查询日志")
	public Map<String,Object> showWorklogStatus(String dates,Long createUser,HttpServletRequest request) throws CustomException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String[] startAndEnd = dates.split(",");
		Worklog worklog = new Worklog();
		if(startAndEnd.length==2){
			String startDate = startAndEnd[0];
			String endDate = startAndEnd[1];
			worklog.setWorklogDateBegin(DateUtils.parseDate(startDate));
			worklog.setWorklogDateEnd(DateUtils.parseDate(endDate));
			Long userId = createUser==null?SystemSecurityUtils.getUser().getId():createUser;
			worklog.setCreateUser(userId);
			List<Worklog> worklogList = worklogService.queryAll(worklog);
			resultMap.put("success", "true");
			resultMap.put("worklogList", worklogList);
		}
		return resultMap;
	}
	
	/**
	 * 方法描述：获得上一篇和下一篇日志
	 * @param worklog
	 * @param isNext
	 * @return
	 * @author 李新桐
	 * @version  2014年6月6日上午9:23:12
	 * @throws CustomException 
	 * @see
	 */
	@RequestMapping(value="getPrefAndNext.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="getPrefAndNext",operateDescribe="获得上一篇和下一篇日志")
	public Map<String,Object> getPrefAndNext(Worklog worklog,HttpServletRequest request) throws CustomException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(worklog.getWorklogDateBegin()!=null){
			worklog.addOrderByField("WorklogDate",OrderType.ASC);
		}
		if(worklog.getWorklogDateEnd()!=null){
			worklog.addOrderByField("WorklogDate",OrderType.DESC);
		}
		if(worklog.getCreateUser()==null){
			worklog.setCreateUser(SystemSecurityUtils.getUser().getId());;
		}
		
		List<Worklog> worklogList = worklogService.queryAll(worklog);
		if(worklogList.size()==0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, "没了");
			return resultMap;
		}else{
			Worklog worklogTemp = worklogList.get(0);
			worklogTemp = worklogService.getWorklog(worklogTemp);
			worklogList.set(0, worklogTemp);
		}
		resultMap.put("success", "true");
		resultMap.put("worklogList", worklogList);
		return resultMap;
	} 
	
	/**
	 * 方法描述：根据提醒人ids返回左右选择树形式的数据
	 * @return
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014年6月17日下午2:18:25
	 * @see
	 */
	@RequestMapping(value = "getRemaidManData.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="getRemaidManData",operateDescribe="根据提醒人ids返回左右选择树形式的数据")
	public List<LeftRight> getRemaidManData(String ids,HttpServletRequest request) throws Exception {
		
		List<LeftRight> result = new ArrayList<>();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			LeftRight item = new LeftRight();
			String code = id[i];
			item.setCode(code);
			item.setText(UserUtils.getUser(Long.parseLong(code)).getDisplayName());
			result.add(item);
		}
		
		return result;
	}
	
	/**
	 * 方法描述：日志汇总导出Excel
	 * @param pageManager
	 * @param diary
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014年7月16日上午8:42:17
	 * @see
	 */
	@RequestMapping(value="exportExcel.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="exportExcel",operateDescribe="日志汇总导出数据")
	public void exportExcel(Worklog worklog,HttpServletRequest request,HttpServletResponse response) throws Exception{
		if(worklog.getCreateUser()==null){
			worklog.setCreateUser(SystemSecurityUtils.getUser().getId());
		}
		worklog.addOrderByField("worklogDate", OrderType.DESC);
		
		List<Worklog> list= worklogService.getMyworklogCollect(worklog);
		for(Worklog w:list){
			List<WorklogContent> todayLogs = w.getTodayLogs();
			StringBuffer worklogContent = new StringBuffer();
			for(int j=0;j<todayLogs.size();j++){
				worklogContent.append((j+1)+"."+todayLogs.get(j).getContent()+";\r\n");
			}
			w.setTodayLogStr(worklogContent.toString());
		}
		List<String> head = new ArrayList<String>();
		head.add("日志日期");
		head.add("内容");
		head.add("感悟及备注");
		List<String> column = new ArrayList<String>();
		column.add("worklogDate");
		column.add("todayLogStr");
		column.add("sentimentRemark");
		ExcuteExcelUtil.setDateFormat("yyyy-MM-dd");
		ExcuteExcelUtil.exportExcel(head,column,list,response);
	}
	
	/**
	 * @description 工作日程快捷方式日志批注数量统计
	 * @return Map<String, Integer>
	 * @throws Exception
	 * @author 刘锡来
	 * @version  2014年7月26日
	 */
	@RequestMapping(value = "workLogAnnoCountForDiary.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="workLogAnnoCountForDiary",operateDescribe="日志批注汇总")
	public Map<String, Integer> workLogAnnoCountForDiary(HttpServletRequest request) throws Exception {
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		Worklog worklog = new Worklog();
		worklog.setByAnnoUserId(SystemSecurityUtils.getUser().getId().toString());//设置被批注人为当前登录人
		worklog.setCreateUser(SystemSecurityUtils.getUser().getId());//设置日志所有人为当前登录人
		Integer result = worklogService.worklogAnnoCount(worklog);
		resultMap.put("annoCount", result);
		return resultMap;
	}
	/**
	 * @description 判断当前登录用户与被查看日志所属人员是否为领导关系
	 * @return Integer
	 * @throws 
	 * @author 李洪宇
	 * @version  2014年8月18日
	 */
	@RequestMapping(value = "getIsLeader.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="getIsLeader",operateDescribe="判断当前登录用户与被查看日志所属人员是否为领导关系")
	public Integer getIsLeader(Long id,Long createUserId,HttpServletRequest request){
		Integer reVal=0;
		if (null!=id) {
			User userInfo = SystemSecurityUtils.getUser();
			User user=new User();
			user.setId(id);
			boolean isLeader=false;
			if (userInfo.getId().intValue()==createUserId.intValue()) {//当前登录人与日志创建人是否为同一个人
				isLeader=true;
			}else {
				isLeader=userService.isLeader(user,userInfo);
			}
			if (isLeader) {
				reVal=1;
			}
		}
		return reVal;
	}
	/** 方法描述：跳转方法 进入共享日志无信息页
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 李洪宇
	 * @version  2014年8月28日上午11:37:19
	 * @see
	 */
	@RequestMapping(value="diaryNoShareInfo.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日志",operateFuncNm="diaryNoShareInfo",operateDescribe="进入共享日志无信息页")
	public String diaryNoShareInfo(HttpServletRequest request) throws Exception{
		return "po/worklog/worklogNoShareInfo"; 
	}
	/**
	 * 方法描述：校验将日志共享短信提醒时被提醒人是否存在电话号
	 * @param Worklog worklog,HttpServletRequest request
	 * @return Map
	 * @author 李洪宇
	 * @version  2014年9月3日 上午09:02:10
	 * @throws PoException 
	 * @see
	 */
	@RequestMapping(value="validRemindNew.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公_工作日志",operateFuncNm="validRemindNew",operateDescribe="校验将日志共享短信提醒时被提醒人是否存在电话号以及日志创建人是否可以发送短信")
	public Map<String,Object> validRemindNew(Worklog worklog,HttpServletRequest request) throws PoException{
		String userIds = worklog.getShareUserIds();
		Long createUser=worklog.getCreateUser();
		String createUsers="";
		if (null!=createUser) {
			createUsers=createUser.toString();
		}
		return worklogService.validRemind(userIds,createUsers);
	}
	
	/**
	* @description 下属计划人员选择树
	* @param HttpServletRequest request
	* @return List<User>
	* @throws Exception
	* @author 李新桐
	* @version  2014-10-10 
	*/
	@RequestMapping(value="subWorkLogQueryTree.action")
	@ResponseBody
	@ActionLog(operateModelNm="下属日志人员选择树",operateFuncNm="subWorkLogQueryTree",operateDescribe="下属日志人员选择树加载")
	public List<Department> subPlanQueryTree(HttpServletRequest request) throws Exception{
		return  diaryService.queryForUnderlingTree(); 
	}
}