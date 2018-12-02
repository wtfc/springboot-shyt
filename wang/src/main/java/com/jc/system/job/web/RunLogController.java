package com.jc.system.job.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;

import com.jc.foundation.web.BaseController;
import com.jc.system.job.domain.RunLog;
import com.jc.system.job.domain.validator.RunLogValidator;
import com.jc.system.job.service.IRunLogService;

/**
 * @title 系统任务
 * @description 控制层类
 * @version 2014-05-08 17:08
 */
@Controller
@RequestMapping(value = "/runLog")
public class RunLogController extends BaseController {

	@Autowired
	private IRunLogService runLogService;

	@org.springframework.web.bind.annotation.InitBinder("runLog")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new RunLogValidator());
	}

	public RunLogController() {
	}

	/**
	 * @description 保存方法
	 * @param RunLog runLog 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 都业广
	 * @version 2014-03-20
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String, Object> save(@Valid RunLog runLog, BindingResult result)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (result.hasErrors()) {
			String errorStr = "";
			List<ObjectError> errorList = result.getAllErrors();
			for (ObjectError error : errorList) {
				errorStr += error.getDefaultMessage() + ",";
			}
			resultMap.put("errorMessage", errorStr);
			resultMap.put("success", "false");
			return resultMap;
		}
		runLogService.save(runLog);
		resultMap.put("success", "true");
		return resultMap;
	}

	/**
	 * @description 修改方法
	 * @param RunLog runLog 实体类
	 * @return Integer 更新的数目
	 * @author 都业广
	 * @version 2014-03-20
	 */
	@RequestMapping(value = "update.action")
	@ResponseBody
	public Integer update(RunLog runLog) throws Exception {
		Integer flag = runLogService.update(runLog);
		return flag;
	}

	/**
	 * @description 获取单条记录方法
	 * @param RunLog runLog 实体类
	 * @return RunLog 查询结果
	 * @throws Exception
	 * @author 都业广
	 * @version 2014-03-20
	 */
	@RequestMapping(value = "get.action")
	@ResponseBody
	public RunLog get(RunLog runLog) throws Exception {
		return runLogService.get(runLog);
	}

	/**
	 * @description 分页查询方法
	 * @param RunLog runLog 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author 都业广
	 * @version 2014-03-20
	 */
	@RequestMapping(value = "manageList.action")
	@ResponseBody
	public PageManager manageList(RunLog runLog,final PageManager page,
			HttpServletRequest request) {
		PageManager page_ = runLogService.query(runLog, page);
		
		return page_;
	}

	/**
	 * @description 跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 都业广
	 * @version 2014-03-20
	 */
	@RequestMapping(value = "manage.action")
	public String manage() throws Exception {
		return "sys/runLog/runLogManage";
	}

	/**
	 * @description 删除方法
	 * @param RunLog runLog 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 都业广
	 * @version 2014-03-20
	 */
	@RequestMapping(value = "deleteByIds.action")
	@ResponseBody
	public Integer deleteByIds(RunLog runLog, String ids) throws Exception {
		runLog.setPrimaryKeys(ids.split(","));
		runLogService.delete(runLog);
		return 1;
	}

}