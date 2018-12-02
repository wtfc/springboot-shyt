package com.jc.system.job.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.job.CycleType;
import com.jc.system.job.domain.TimerTask;
import com.jc.system.job.domain.validator.TimerTaskValidator;
import com.jc.system.job.service.ITimerTaskMonitor;
import com.jc.system.job.service.ITimerTaskService;
import com.jc.system.job.util.TimerTaskUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.Role;
import com.jc.system.security.domain.User;

/**
 * @title 系统任务
 * @description 控制层类
 * @version  2014-05-08 17:08
 */
@Controller
@RequestMapping(value = "/sys/job")
public class TimerTaskController extends BaseController {

	@Autowired
	private ITimerTaskService timerTaskService;
	@Autowired
	private ITimerTaskMonitor timerTaskMonitor;

	@org.springframework.web.bind.annotation.InitBinder("timerTask")
	public void initBinder(WebDataBinder binder) {

		binder.setValidator(new TimerTaskValidator());
	}

	public TimerTaskController() {
	}
	
	private TimerTaskValidator ttv = new TimerTaskValidator();

	/**
	 * @description 保存方法
	 * @param TimerTask timerTask 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 都业广
	 * @version 2014-05-12
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String, Object> save(TimerTask timerTask, BindingResult result,
			HttpServletRequest request) throws Exception {
		//验证bean的内容
		ttv.validate(timerTask, result);
		
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		// 验证token
		resultMap = validateToken(request,request.getParameter(GlobalContext.SESSION_TOKEN));
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		TimerTask t = new TimerTask();
		t.setGroupName(timerTask.getGroupName());
		if(timerTaskService.queryByGroupName(t) != null){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, "任务类型已经存在");
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
			return resultMap;
		}
		
		// 生成任务详情
		timerTask = TimerTaskUtils.generateCycleDetail(timerTask);
		
		// 生成CronExpression表达式
		timerTask = TimerTaskUtils.generateCronExpression(timerTask);
		
		//设置系统任务的运行状态 （quartz处理的相关功能）
		timerTask.setState(CycleType.SYS_JOB_STATE_RUN);  //默认运行状态
		
		//设置系统任务的运行次数
		timerTask.setEXT_STR1(CycleType.SYMBOL_ZERO);  //追加时执行任务次数为0
		
		// 保存系统任务
		if (timerTaskMonitor.add(timerTask) == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,MessageUtils.getMessage("JC_SYS_001"));
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}
		return resultMap;
	}

	/**
	 * @description 修改方法
	 * @param TimerTask timerTask 实体类
	 * @param HttpServletRequest request
	 * @return Integer 更新的数目
	 * @author 都业广
	 * @version 2014-05-12
	 */
	@RequestMapping(value = "update.action")
	@ResponseBody
	public Map<String, Object> update(TimerTask timerTask, BindingResult result, 
			HttpServletRequest request) throws Exception {

		// 验证bean的内容
		ttv.validate(timerTask, result);

		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}

		// 验证token
		resultMap = validateToken(request,
				request.getParameter(GlobalContext.SESSION_TOKEN));
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		// 生成任务详情
		timerTask = TimerTaskUtils.generateCycleDetail(timerTask);
		
		// 生成CronExpression表达式
		timerTask = TimerTaskUtils.generateCronExpression(timerTask);

		//设置系统任务的运行状态 （quartz处理的相关功能）
		timerTask.setState(CycleType.SYS_JOB_STATE_RUN); // 默认运行状态
		
		//设置系统任务的运行次数
		timerTask.setEXT_STR1(CycleType.SYMBOL_ZERO);  //修改时执行任务次数为0

		//更新系统任务
		if(timerTaskMonitor.update(timerTask) == 1){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,MessageUtils.getMessage("JC_SYS_001"));
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}
		
		return resultMap;
	}

	/**
	 * @description 获取单条记录方法
	 * @param TimerTask timerTask 实体类
	 * @return TimerTask 查询结果
	 * @throws Exception
	 * @author 都业广
	 * @version 2014-05-12
	 */
	@RequestMapping(value = "get.action")
	@ResponseBody
	public TimerTask get(TimerTask timerTask) throws Exception {
		
		timerTask = timerTaskService.get(timerTask);
		
		//解析循环详情
		Map<String, String> hashmap = TimerTaskUtils.jsonToHashMap(timerTask.getCycleDetail());
		
		//将对应的数据保存到bean中
		timerTask = TimerTaskUtils.hashMapToBean(timerTask, hashmap);
		return timerTask;
	}

	/**
	 * @description 分页查询方法
	 * @param TimerTask timerTask 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author 都业广
	 * @version 2014-05-08
	 */
	@RequestMapping(value = "manageList.action")
	@ResponseBody
	public PageManager manageList(TimerTask timerTask,
			final PageManager page,
			HttpServletRequest request) {
		
		//设置组织id	去掉机构限制
		//timerTask.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
		PageManager page_= timerTaskService.query(timerTask, page);
		
		return page_;
	}

	/**
	 * @description 跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 都业广
	 * @version 2014-05-08
	 */
	@RequestMapping(value = "manage.action")
	public String manage() throws Exception {
		
		//访问权限
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			if(userInfo.getIsAdmin() == 1 || userInfo.getIsSystemAdmin()){
				return "sys/job/timerTaskManage";
			} else {
				return "error/unauthorized";
			}
		}
		return "error/unauthorized";
	}

	@RequestMapping(value = "timerTaskEdit.action")
	public String timerTaskEdit(Model model, HttpServletRequest request) throws Exception {
		model.addAttribute("token", getToken(request));
		return "sys/job/timerTaskEdit";
	}
	
	/**
	 * @description 删除方法
	 * @param TimerTask timerTask 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 都业广
	 * @version 2014-05-09
	 */
	@RequestMapping(value = "deleteByIds.action")
	@ResponseBody
	public Integer deleteByIds(TimerTask timerTask, String ids)
			throws Exception {
		timerTask.setPrimaryKeys(ids.split(","));
		
		//删除系统任务
		timerTaskMonitor.remove(timerTask);
		
		return 1;
	}

	/**
	 * @description 暂停系统任务方法
	 * @param TimerTask timerTask 实体类
	 * @param String id 主键
	 * @return Integer 成功暂停
	 * @throws Exception
	 * @author 都业广
	 * @version 2014-05-12
	 */
	@RequestMapping(value = "pause.action")
	@ResponseBody
	public Integer pause(TimerTask timerTask) throws Exception {
		
		//暂停时状态值为0
		timerTask.setState(CycleType.SYS_JOB_STATE_PAUSE);
		
		//暂停系统任务运行
		Integer flag = timerTaskMonitor.pause(timerTask);
		
		return flag;
	}

	/**
	 * @description 运行系统任务方法
	 * @param TimerTask timerTask 实体类
	 * @param String id 主键
	 * @return Integer 成功运行
	 * @throws Exception
	 * @author 都业广
	 * @version 2014-05-12
	 */
	@RequestMapping(value = "resume.action")
	@ResponseBody
	public Integer resume(TimerTask timerTask) throws Exception {
		
		//运行时状态值为1
		timerTask.setState(CycleType.SYS_JOB_STATE_RUN);
		
		//恢复系统任务运行状态
		Integer flag = timerTaskMonitor.resume(timerTask);

		return flag;
	}
}