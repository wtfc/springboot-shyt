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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.WebDataBinder;

import com.jc.oa.po.workTask.domain.WorkRemaind;
import com.jc.oa.po.workTask.domain.validator.WorkRemaindValidator;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.po.workTask.service.IWorkRemaindService;


/**
 * @title 个人办公
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-23
 */
 
@Controller
@RequestMapping(value="/po/workRemaind")
public class WorkRemaindController extends BaseController{
	
	@Autowired
	private IWorkRemaindService remaindService;
	
	@org.springframework.web.bind.annotation.InitBinder("remaind")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new WorkRemaindValidator()); 
    }
	
	public WorkRemaindController() {
	}

	/**
	 * @description 分页查询方法
	 * @param WorkRemaind remaind 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-23 
	 */
	@RequestMapping(value="manageList.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public PageManager manageList(WorkRemaind remaind,PageManager page){
		PageManager page_ = remaindService.query(remaind, page);
		return page_; 
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-23 
	*/
	@RequestMapping(value="manage.action")
	public String manage() throws Exception{
		return "po/remaind/remaind添加常用网址"; 
	}

/**
	 * @description 删除方法
	 * @param WorkRemaind remaind 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-04-23
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Integer deleteByIds(WorkRemaind remaind,String ids) throws Exception{
		remaind.setPrimaryKeys(ids.split(","));
		return remaindService.delete(remaind);
	}

	/**
	 * @description 保存方法
	 * @param WorkRemaind remaind 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-04-23
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid WorkRemaind remaind,BindingResult result) throws Exception{
		
		Map<String,Object> resultMap = validateBean(result);
		if(!"false".equals(resultMap.get("success"))){
			remaindService.save(remaind);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param WorkRemaind remaind 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-04-23 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	public Integer update(WorkRemaind remaind) throws Exception{
		Integer flag = remaindService.update(remaind);
		return flag;
	}

	/**
	 * @description 获取单条记录方法
	 * @param WorkRemaind remaind 实体类
	 * @return Remaind 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-23
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public WorkRemaind get(WorkRemaind remaind) throws Exception{
		return remaindService.get(remaind);
	}

}