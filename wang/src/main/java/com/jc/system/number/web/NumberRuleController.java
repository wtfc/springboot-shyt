package com.jc.system.number.web;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.WebDataBinder;

import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.number.domain.NumberRule;
import com.jc.system.number.domain.validator.NumberRuleValidator;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.number.service.INumberRuleService;
import com.jc.system.security.domain.User;


/**
 * @title GOA2.0
 * @description  controller类
 * @author 
 * @version  2014-05-04
 */
 
@Controller
@RequestMapping(value="/number")
public class NumberRuleController extends BaseController{
	
	@Autowired
	private INumberRuleService numberRuleService;
	
	@org.springframework.web.bind.annotation.InitBinder("numberRule")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new NumberRuleValidator()); 
    }
	
	public NumberRuleController() {
	}

	/**
	 * @description 分页查询方法
	 * @param NumberRule numberRule 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-04 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	public List<NumberRule> manageList(NumberRule numberRule){
		try {
			return numberRuleService.queryAll(numberRule);
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-05-04 
	*/
	@RequestMapping(value="manage.action")
	public String manage() throws Exception{
		return "number/number"; 
	}

/**
	 * @description 删除方法
	 * @param NumberRule numberRule 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-05-04
	 */
	@RequestMapping(value="delete.action")
	@ResponseBody
	public Integer delete(NumberRule numberRule) throws Exception{
		return numberRuleService.delete(numberRule,false);
	}

	
	/**
	 * @description 验证用户名是否存在
	 * @param User user 实体类
	 * @return String true不存在	false存在
	 * @throws Exception
	 * @author
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "checkRuleName.action")
	@ResponseBody
	public boolean checkRuleName(NumberRule numberRule) throws Exception {
		if(numberRuleService.get(numberRule) == null){
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * @description 保存方法
	 * @param NumberRule numberRule 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-05-04
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid NumberRule numberRule,BindingResult result) throws Exception{
		
		Map<String,Object> resultMap = validateBean(result);
		// 验证bean
		if (resultMap.size() > 0) {
			return resultMap;
		}
		if(numberRuleService.save(numberRule) == 1){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param NumberRule numberRule 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-05-04 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	public Map<String,Object> update(NumberRule numberRule ,BindingResult result) throws Exception{
		Map<String,Object> resultMap = validateBean(result);
		// 验证bean
		if (resultMap.size() > 0) {
			return resultMap;
		}
		if(numberRuleService.update(numberRule) == 1){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		}
		return resultMap;
	}

	/**
	 * @description 获取单条记录方法
	 * @param NumberRule numberRule 实体类
	 * @return NumberRule 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-04
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public NumberRule get(NumberRule numberRule) throws Exception{
		return numberRuleService.get(numberRule);
	}

}