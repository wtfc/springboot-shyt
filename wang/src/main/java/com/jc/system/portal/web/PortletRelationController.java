package com.jc.system.portal.web;

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
import com.jc.system.portal.domain.PortletRelation;
import com.jc.system.portal.domain.validator.PortletRelationValidator;
import com.jc.system.portal.service.IPortletRelationService;
import com.jc.system.security.util.ActionLog;


/**
 * @title GOA2.0
 * @description  controller类
 * @author 
 * @version  2014-06-16
 */
 
@Controller
@RequestMapping(value="/sys/portletRelation")
public class PortletRelationController extends BaseController{
	
	@Autowired
	private IPortletRelationService portletRelationService;
	
	@org.springframework.web.bind.annotation.InitBinder("portletRelation")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new PortletRelationValidator()); 
    }
	
	public PortletRelationController() {
	}

	/**
	 * 分页查询方法
	 * @param PortletRelation portletRelation 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-16 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="",operateFuncNm="manageList",operateDescribe="对进行查询操作")
	public PageManager manageList(PortletRelation portletRelation,PageManager page){
		PageManager page_ = portletRelationService.query(portletRelation, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-06-16 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="",operateFuncNm="manage",operateDescribe="对进行跳转操作")
	public String manage() throws Exception{
		return "sys/portletRelation/portletRelationAaa"; 
	}

/**
	 * 删除方法
	 * @param PortletRelation portletRelation 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-16
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="",operateFuncNm="deleteByIds",operateDescribe="对进行删除")
	public Integer deleteByIds(PortletRelation portletRelation,String ids) throws Exception{
		portletRelation.setPrimaryKeys(ids.split(","));
		return portletRelationService.delete(portletRelation);
	}

	/**
	 * 保存方法
	 * @param PortletRelation portletRelation 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-06-16
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="",operateFuncNm="save",operateDescribe="对进行新增操作")
	public Map<String,Object> save(@Valid PortletRelation portletRelation,BindingResult result,
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
			portletRelationService.save(portletRelation);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param PortletRelation portletRelation 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-06-16 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="",operateFuncNm="update",operateDescribe="对进行更新操作")
	public Map<String, Object> update(PortletRelation portletRelation, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = portletRelationService.update(portletRelation);
		if (flag == 1) {
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param PortletRelation portletRelation 实体类
	 * @return PortletRelation 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-16
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="",operateFuncNm="get",operateDescribe="对注解关系进行单条查询操作")
	public PortletRelation get(PortletRelation portletRelation) throws Exception{
		return portletRelationService.get(portletRelation);
	}

}