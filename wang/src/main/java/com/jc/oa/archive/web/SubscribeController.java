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

import com.jc.oa.archive.domain.Subscribe;
import com.jc.oa.archive.domain.validator.SubscribeValidator;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.security.util.ActionLog;

import com.jc.oa.archive.service.ISubscribeService;


/**
 * @title  GOA2.0源代码
 * @description  controller类
 * @author 
 * @version  2014-06-05
 */
 
@Controller
@RequestMapping(value="/archive/subscribe")
public class SubscribeController extends BaseController{
	
	@Autowired
	private ISubscribeService subscribeService;
	
	@org.springframework.web.bind.annotation.InitBinder("subscribe")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new SubscribeValidator()); 
    }
	
	public SubscribeController() {
	}

	/**
	 * 分页查询方法
	 * @param Subscribe subscribe 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_知识管理_知识订阅表",operateFuncNm="manageList",operateDescribe="对OA_知识管理_知识订阅表进行查询操作")
	public PageManager manageList(Subscribe subscribe,PageManager page){
		PageManager page_ = subscribeService.query(subscribe, page);
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
	@ActionLog(operateModelNm="OA_知识管理_知识订阅表",operateFuncNm="manage",operateDescribe="对OA_知识管理_知识订阅表进行跳转操作")
	public String manage() throws Exception{
		return "archive/subscribe/subscribe1"; 
	}

/**
	 * 删除方法
	 * @param Subscribe subscribe 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_知识管理_知识订阅表",operateFuncNm="deleteByIds",operateDescribe="对OA_知识管理_知识订阅表进行删除")
	public Integer deleteByIds(Subscribe subscribe,String ids) throws Exception{
		subscribe.setPrimaryKeys(ids.split(","));
		return subscribeService.delete(subscribe);
	}

	/**
	 * 保存方法
	 * @param Subscribe subscribe 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_知识管理_知识订阅表",operateFuncNm="save",operateDescribe="对OA_知识管理_知识订阅表进行新增操作")
	public Map<String,Object> save(@Valid Subscribe subscribe,BindingResult result,
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
			subscribeService.save(subscribe);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param Subscribe subscribe 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-06-05 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_知识管理_知识订阅表",operateFuncNm="update",operateDescribe="对OA_知识管理_知识订阅表进行更新操作")
	public Map<String, Object> update(Subscribe subscribe, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = subscribeService.update(subscribe);
		if (flag == 1) {
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param Subscribe subscribe 实体类
	 * @return Subscribe 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_知识管理_知识订阅表",operateFuncNm="get",operateDescribe="对OA_知识管理_知识订阅表进行单条查询操作")
	public Subscribe get(Subscribe subscribe) throws Exception{
		return subscribeService.get(subscribe);
	}

}