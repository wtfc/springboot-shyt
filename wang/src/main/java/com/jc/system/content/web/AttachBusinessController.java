package com.jc.system.content.web;

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
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.content.domain.AttachBusiness;
import com.jc.system.content.domain.validator.AttachBusinessValidator;
import com.jc.system.content.service.IAttachBusinessService;
import com.jc.system.security.util.ActionLog;




/**
 * @title GOA2.0
 * @description  controller类
 * @author 
 * @version  2014-05-21
 */
 
@Controller
@RequestMapping(value="/sys/attachBusiness")
public class AttachBusinessController extends BaseController{
	
	@Autowired
	private IAttachBusinessService attachBusinessService;
	
	@org.springframework.web.bind.annotation.InitBinder("attachBusiness")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new AttachBusinessValidator()); 
    }
	
	public AttachBusinessController() {
	}

	/**
	 * 分页查询方法
	 * @param AttachBusiness attachBusiness 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-21 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="附件业务关系表",operateFuncNm="manageList",operateDescribe="对附件业务关系表进行查询操作")
	public PageManager manageList(AttachBusiness attachBusiness,PageManager page){
		PageManager page_ = attachBusinessService.query(attachBusiness, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-05-21 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="附件业务关系表",operateFuncNm="manage",operateDescribe="对附件业务关系表进行跳转操作")
	public String manage() throws Exception{
		return "sys/attachBusiness/attachBusinessAaa"; 
	}

/**
	 * 删除方法
	 * @param AttachBusiness attachBusiness 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-05-21
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="附件业务关系表",operateFuncNm="deleteByIds",operateDescribe="对附件业务关系表进行删除")
	public Integer deleteByIds(AttachBusiness attachBusiness,String ids) throws Exception{
		attachBusiness.setPrimaryKeys(ids.split(","));
		return attachBusinessService.delete(attachBusiness);
	}

	/**
	 * 保存方法
	 * @param AttachBusiness attachBusiness 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-05-21
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="附件业务关系表",operateFuncNm="save",operateDescribe="对附件业务关系表进行新增操作")
	public Map<String,Object> save(@Valid AttachBusiness attachBusiness,BindingResult result,
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
			attachBusinessService.save(attachBusiness);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param AttachBusiness attachBusiness 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-05-21 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="附件业务关系表",operateFuncNm="update",operateDescribe="对附件业务关系表进行更新操作")
	public Map<String, Object> update(AttachBusiness attachBusiness, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = attachBusinessService.update(attachBusiness);
		if (flag == 1) {
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param AttachBusiness attachBusiness 实体类
	 * @return AttachBusiness 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-21
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="附件业务关系表",operateFuncNm="get",operateDescribe="对附件业务关系表进行单条查询操作")
	public AttachBusiness get(AttachBusiness attachBusiness) throws Exception{
		return attachBusinessService.get(attachBusiness);
	}

}