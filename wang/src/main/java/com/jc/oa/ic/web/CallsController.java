package com.jc.oa.ic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.ic.domain.Calls;
import com.jc.oa.ic.domain.validator.CallsValidator;
import com.jc.oa.ic.service.ICallsService;


/**
 * @title HR
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-29
 */
 
@Controller
@RequestMapping(value="/ic/calls")
public class CallsController extends BaseController{
	
	@Autowired
	private ICallsService callsService;
	
	@org.springframework.web.bind.annotation.InitBinder("calls")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new CallsValidator()); 
    }
	
	public CallsController() {
	}

	/**
	 * @description 分页查询方法
	 * @param Calls calls 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-29 
	 */
	@RequestMapping(value="manageList.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public PageManager manageList(Calls calls,PageManager page){
		PageManager page_ = callsService.query(calls, page);
		return page_; 
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-29 
	*/
	@RequestMapping(value="manage.action")
	public String manage() throws Exception{
		return "smsserver/calls/callsIc"; 
	}

/**
	 * @description 删除方法
	 * @param Calls calls 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-04-29
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Integer deleteByIds(Calls calls,String ids) throws Exception{
		calls.setPrimaryKeys(ids.split(","));
		return callsService.delete(calls);
	}

	/**
	 * @description 保存方法
	 * @param Calls calls 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-04-29
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid Calls calls,BindingResult result) throws Exception{
		
		Map<String,Object> resultMap = validateBean(result);
		if(!"false".equals(resultMap.get("success"))){
			callsService.save(calls);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param Calls calls 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-04-29 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	public Integer update(Calls calls) throws Exception{
		Integer flag = callsService.update(calls);
		return flag;
	}

	/**
	 * @description 获取单条记录方法
	 * @param Calls calls 实体类
	 * @return Calls 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-29
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public Calls get(Calls calls) throws Exception{
		return callsService.get(calls);
	}

}