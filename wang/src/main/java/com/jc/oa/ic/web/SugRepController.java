package com.jc.oa.ic.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.ic.domain.SugRep;
import com.jc.oa.ic.domain.validator.SugRepValidator;
import com.jc.oa.ic.service.ISugRepService;


/**
 * @title 互动交流
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */
 
@Controller
@RequestMapping(value="/ic/sugRep")
public class SugRepController extends BaseController{
	
	@Autowired
	private ISugRepService sugRepService;
	
	@org.springframework.web.bind.annotation.InitBinder("sugRep")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new SugRepValidator()); 
    }
	
	public SugRepController() {
	}

	/**
	 * @description 分页查询方法
	 * @param SugRep sugRep 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-17 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	public PageManager manageList(SugRep sugRep,PageManager page,HttpServletRequest request){
		PageManager page_ = sugRepService.query(sugRep, page);
		return page_; 
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-17 
	*/
	@RequestMapping(value="manage.action")
	public String manage(HttpServletRequest request) throws Exception{
		return "ic/sugRep/sugRepInteract"; 
	}

/**
	 * @description 删除方法
	 * @param SugRep sugRep 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Integer deleteByIds(SugRep sugRep,String ids,HttpServletRequest request) throws Exception{
		sugRep.setPrimaryKeys(ids.split(","));
		return sugRepService.delete(sugRep);//待完善
	}

	/**
	 * @description 保存方法
	 * @param SugRep sugRep 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid SugRep sugRep,BindingResult result,
			HttpServletRequest request) throws Exception{
		
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 保存回复信息
		if (sugRepService.save(sugRep) == 1) {
			resultMap.put("success", "true");
		} else {
			resultMap.put("success", "false");
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param SugRep sugRep 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-04-17 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	public Integer update(SugRep sugRep,HttpServletRequest request) throws Exception{
		Integer flag = sugRepService.update(sugRep);
		return flag;
	}

	/**
	 * @description 获取单条记录方法
	 * @param SugRep sugRep 实体类
	 * @return SugRep 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public SugRep get(SugRep sugRep,HttpServletRequest request) throws Exception{
		return sugRepService.get(sugRep);
	}

}