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
import com.jc.oa.ic.domain.ConGroupR;
import com.jc.oa.ic.domain.validator.ConGroupRValidator;
import com.jc.oa.ic.service.IConGroupRService;
import com.jc.system.security.util.ActionLog;


/**
 * @title 互动交流
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-05-19
 */
 
@Controller
@RequestMapping(value="/ic/conGroupR")
public class ConGroupRController extends BaseController{
	
	@Autowired
	private IConGroupRService conGroupRService;
	
	@org.springframework.web.bind.annotation.InitBinder("conGroupR")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new ConGroupRValidator()); 
    }
	
	public ConGroupRController() {
	}

	/**
	 * 分页查询方法
	 * @param ConGroupR conGroupR 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-19 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="联系人与分组中间表",operateFuncNm="manageList",operateDescribe="对联系人与分组中间表进行查询操作")
	public PageManager manageList(ConGroupR conGroupR,PageManager page,HttpServletRequest request){
		PageManager page_ = conGroupRService.query(conGroupR, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-05-19 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="联系人与分组中间表",operateFuncNm="manage",operateDescribe="对联系人与分组中间表进行跳转操作")
	public String manage(HttpServletRequest request) throws Exception{
		return "ic/conGroupR/conGroupRInteract"; 
	}

/**
	 * 删除方法
	 * @param ConGroupR conGroupR 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-05-19
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="联系人与分组中间表",operateFuncNm="deleteByIds",operateDescribe="对联系人与分组中间表进行删除")
	public Integer deleteByIds(ConGroupR conGroupR,String ids,HttpServletRequest request) throws Exception{
		conGroupR.setPrimaryKeys(ids.split(","));
		return conGroupRService.delete(conGroupR);
	}

	/**
	 * 保存方法
	 * @param ConGroupR conGroupR 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-05-19
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="联系人与分组中间表",operateFuncNm="save",operateDescribe="对联系人与分组中间表进行新增操作")
	public Map<String,Object> save(@Valid ConGroupR conGroupR,BindingResult result,
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
			conGroupRService.save(conGroupR);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param ConGroupR conGroupR 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-05-19 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="联系人与分组中间表",operateFuncNm="update",operateDescribe="对联系人与分组中间表进行更新操作")
	public Map<String, Object> update(ConGroupR conGroupR, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = conGroupRService.update(conGroupR);
		if (flag == 1) {
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param ConGroupR conGroupR 实体类
	 * @return ConGroupR 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-19
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="联系人与分组中间表",operateFuncNm="get",operateDescribe="对联系人与分组中间表进行单条查询操作")
	public ConGroupR get(ConGroupR conGroupR,HttpServletRequest request) throws Exception{
		return conGroupRService.get(conGroupR);
	}

}