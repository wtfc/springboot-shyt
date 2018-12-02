package com.jc.oa.common.web;

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

import com.jc.oa.common.domain.FormSuggest;
import com.jc.oa.common.domain.validator.FormSuggestValidator;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.common.service.IFormSuggestService;


/**
 * @title  GOA2.0源代码
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * @author 
 * @version  2014-04-18
 */
 
@Controller
@RequestMapping(value="/common/suggest")
public class FormSuggestController extends BaseController{
	
	@Autowired
	private IFormSuggestService suggestService;
	
	@org.springframework.web.bind.annotation.InitBinder("suggest")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new FormSuggestValidator()); 
    }
	
	public FormSuggestController() {
	}

	/**
	 * @description 分页查询方法
	 * @param FormSuggest suggest 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-18 
	 */
	@RequestMapping(value="manageList.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public PageManager manageList(FormSuggest suggest,PageManager page){
		PageManager page_ = suggestService.query(suggest, page);
		return page_; 
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-18 
	*/
	@RequestMapping(value="manage.action")
	public String manage() throws Exception{
		return "common/suggest/suggest1"; 
	}

/**
	 * @description 删除方法
	 * @param FormSuggest suggest 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-04-18
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Integer deleteByIds(FormSuggest suggest,String ids) throws Exception{
		suggest.setPrimaryKeys(ids.split(","));
		return suggestService.delete(suggest);
	}

	/**
	 * @description 保存方法
	 * @param FormSuggest suggest 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-04-18
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid FormSuggest suggest,BindingResult result) throws Exception{
		
		Map<String,Object> resultMap = validateBean(result);
		if(!"false".equals(resultMap.get("success"))){
			suggestService.save(suggest);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param FormSuggest suggest 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-04-18 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	public Integer update(FormSuggest suggest) throws Exception{
		Integer flag = suggestService.update(suggest);
		return flag;
	}

	/**
	 * @description 获取单条记录方法
	 * @param FormSuggest suggest 实体类
	 * @return Suggest 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-18
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public FormSuggest get(FormSuggest suggest) throws Exception{
		return suggestService.get(suggest);
	}

}