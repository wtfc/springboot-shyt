package com.jc.oa.common.web;

import javax.validation.Valid;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.WebDataBinder;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.common.domain.ControlSide;
import com.jc.oa.common.domain.validator.ControlSideValidator;
import com.jc.oa.common.service.IControlSideService;


/**
 * @title 172.16.3.68
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * @author 
 * @version  2014-04-29
 */
 
@Controller
@RequestMapping(value="/sys/controlSide")
public class ControlSideController extends BaseController{
	
	@Autowired
	private IControlSideService controlSideService;
	
	@org.springframework.web.bind.annotation.InitBinder("controlSide")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new ControlSideValidator()); 
    }
	
	public ControlSideController() {
	}

	/**
	 * @description 保存方法
	 * @param ControlSide controlSide 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-04-29
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid ControlSide controlSide,BindingResult result) throws Exception{
		
		Map<String,Object> resultMap = validateBean(result);
		if(!"false".equals(resultMap.get("success"))){
			controlSideService.save(controlSide);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param ControlSide controlSide 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-04-29 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	public Integer update(ControlSide controlSide) throws Exception{
		Integer flag = controlSideService.update(controlSide);
		return flag;
	}

	/**
	 * @description 获取单条记录方法
	 * @param ControlSide controlSide 实体类
	 * @return ControlSide 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-29
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public ControlSide get(ControlSide controlSide) throws Exception{
		return controlSideService.get(controlSide);
	}

	/**
	 * @description 分页查询方法
	 * @param ControlSide controlSide 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-29 
	 */
	@RequestMapping(value="manageList.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public PageManager manageList(ControlSide controlSide,PageManager page){
		PageManager page_ = controlSideService.query(controlSide, page);
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
		return "sys/controlSide/controlSideCar"; 
	}

/**
	 * @description 删除方法
	 * @param ControlSide controlSide 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-04-29
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Integer deleteByIds(ControlSide controlSide,String ids) throws Exception{
		controlSide.setPrimaryKeys(ids.split(","));
		return controlSideService.delete(controlSide);
	}

}