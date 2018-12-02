package com.jc.oa.archive.web;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.WebDataBinder;

import com.jc.oa.archive.domain.History;
import com.jc.oa.archive.domain.validator.HistoryValidator;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.util.ActionLog;
import com.jc.oa.archive.service.IHistoryService;


/**
 * @title  GOA2.0源代码
 * @description  controller类
 * @author 
 * @version  2014-06-05
 */
 
@Controller
@RequestMapping(value="/archive/history")
public class HistoryController extends BaseController{
	
	@Autowired
	private IHistoryService historyService;
	
	@org.springframework.web.bind.annotation.InitBinder("history")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new HistoryValidator()); 
    }
	
	public HistoryController() {
	}

	/**
	 * 分页查询方法
	 * @param History history 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_文档管理/知识管理_文档历史表",operateFuncNm="manageList",operateDescribe="对OA_文档管理/知识管理_文档历史表进行查询操作")
	public PageManager manageList(History history,PageManager page,HttpServletRequest request){
		PageManager page_ = historyService.query(history, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-06-05 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="OA_文档管理/知识管理_文档历史表",operateFuncNm="manage",operateDescribe="对OA_文档管理/知识管理_文档历史表进行跳转操作")
	public String manage(HttpServletRequest request) throws Exception{
		return "archive/history/history1"; 
	}

/**
	 * 删除方法
	 * @param History history 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_文档管理/知识管理_文档历史表",operateFuncNm="deleteByIds",operateDescribe="对OA_文档管理/知识管理_文档历史表进行删除")
	public Integer deleteByIds(History history,String ids,HttpServletRequest request) throws Exception{
		history.setPrimaryKeys(ids.split(","));
		return historyService.delete(history);
	}

	/**
	 * 保存方法
	 * @param History history 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_文档管理/知识管理_文档历史表",operateFuncNm="save",operateDescribe="对OA_文档管理/知识管理_文档历史表进行新增操作")
	public Map<String,Object> save(@Valid History history,BindingResult result,
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
		
		if(!"false".equals(resultMap.get("success"))){
			historyService.save(history);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param History history 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-06-05 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_文档管理/知识管理_文档历史表",operateFuncNm="update",operateDescribe="对OA_文档管理/知识管理_文档历史表进行更新操作")
	public Map<String, Object> update(History history, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = historyService.update(history);
		if (flag == 1) {
			resultMap.put("success", "true");
		} else {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_OA_COMMON_014"));
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param History history 实体类
	 * @return History 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_文档管理/知识管理_文档历史表",operateFuncNm="get",operateDescribe="对OA_文档管理/知识管理_文档历史表进行单条查询操作")
	public History get(History history,HttpServletRequest request) throws Exception{
		return historyService.get(history);
	}

}