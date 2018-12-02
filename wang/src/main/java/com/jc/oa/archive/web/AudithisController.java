package com.jc.oa.archive.web;

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

import com.jc.oa.archive.domain.Audithis;
import com.jc.oa.archive.domain.validator.AudithisValidator;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;
import com.jc.system.security.util.UserUtils;
import com.jc.oa.archive.service.IAudithisService;
import com.jc.oa.archive.util.PermissionUtil;



/**
 * @title  GOA2.0源代码
 * @description  controller类
 * @author 
 * @version  2014-06-05
 */
 
@Controller
@RequestMapping(value="/archive/audithis")
public class AudithisController extends BaseController{
	
	@Autowired
	private IAudithisService audithisService;
	
	@org.springframework.web.bind.annotation.InitBinder("audithis")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new AudithisValidator()); 
    }
	
	public AudithisController() {
	}

	/**
	 * 分页查询方法
	 * @param Audithis audithis 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="文档审计历史表",operateFuncNm="manageList",operateDescribe="对文档审计历史表进行查询操作")
	public PageManager manageList(HttpServletRequest request,Audithis audithis,PageManager page){
		//默认排序
		if(StringUtils.isEmpty(audithis.getOrderBy())) {
			audithis.addOrderByFieldDesc("t.CREATE_DATE");
		}
		if(audithis.getEndDate() !=null){
			audithis.setEndDate(audithis.getEndDate()+" 23:59:59");
		}
		PageManager page_ = null;
		if(audithis.getDataId() == null) {
			//文档审计菜单调用
			page_ = audithisService.queryByPermission(audithis, page);
		} else {
			// 公共文档中文档审计调用
			page_ = audithisService.query(audithis, page);
		}
		if(page_ ==null || page_.getAaData() == null || page_.getAaData().size()<1){
			return page;
		}
		for(int i=0;i<page_.getAaData().size();i++){
			Audithis rec = (Audithis) page_.getAaData().get(i);
			rec.setUserName(UserUtils.getUser(rec.getUserId()).getDisplayName());
			rec.setAuditType(PermissionUtil.permissionType(rec.getAuditType()));
		}
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
	@ActionLog(operateModelNm="文档审计历史表",operateFuncNm="manage",operateDescribe="对文档审计历史表进行跳转操作")
	public String manage(HttpServletRequest request) throws Exception{
		return "archive/document/audithis"; 
	}
	/**
	 * 跳转方法
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author
	 * @version  2014-06-05 
	 */
	@RequestMapping(value="manageTest.action")
	@ActionLog(operateModelNm="文档审计历史表",operateFuncNm="manage",operateDescribe="对文档审计历史表进行跳转操作")
	public String manageTest(HttpServletRequest request) throws Exception{
		return "archive/document/MyJsp"; 
	}

/**
	 * 删除方法
	 * @param Audithis audithis 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="文档审计历史表",operateFuncNm="deleteByIds",operateDescribe="对文档审计历史表进行删除")
	public Integer deleteByIds(HttpServletRequest request,Audithis audithis,String ids) throws Exception{
		audithis.setPrimaryKeys(ids.split(","));
		return audithisService.delete(audithis);
	}

	/**
	 * 保存方法
	 * @param Audithis audithis 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="文档审计历史表",operateFuncNm="save",operateDescribe="对文档审计历史表进行新增操作")
	public Map<String,Object> save(@Valid Audithis audithis,BindingResult result,
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
			audithisService.save(audithis);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param Audithis audithis 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-06-05 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="文档审计历史表",operateFuncNm="update",operateDescribe="对文档审计历史表进行更新操作")
	public Map<String, Object> update(Audithis audithis, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = audithisService.update(audithis);
		if (flag == 1) {
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param Audithis audithis 实体类
	 * @return Audithis 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="文档审计历史表",operateFuncNm="get",operateDescribe="对文档审计历史表进行单条查询操作")
	public Audithis get(HttpServletRequest request,Audithis audithis) throws Exception{
		return audithisService.get(audithis);
	}

	/**
	 * 分页查询方法
	 * @param Audithis audithis 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05 
	 */
	@RequestMapping(value="myOperationList.action")
	@ResponseBody
	@ActionLog(operateModelNm="我的操作历史表",operateFuncNm="myOperationList",operateDescribe="对我的操作历史表进行查询操作")
	public PageManager myOperationList(HttpServletRequest request,Audithis audithis,PageManager page){
		//默认排序
		if(StringUtils.isEmpty(audithis.getOrderBy())) {
			audithis.addOrderByFieldDesc("t.CREATE_DATE");
		}
		audithis.setUserId(SystemSecurityUtils.getUser().getId());
		if(audithis.getEndDate() !=null){
			audithis.setEndDate(audithis.getEndDate()+" 23:59:59");
		}
		PageManager page_ = audithisService.query(audithis, page);
		if(page_ ==null || page_.getAaData() == null || page_.getAaData().size()<1){
			return page;
		}
		for(int i=0;i<page_.getAaData().size();i++){
			Audithis rec = (Audithis) page_.getAaData().get(i);
			rec.setUserName(UserUtils.getUser(rec.getUserId()).getDisplayName());
			rec.setAuditType(PermissionUtil.permissionType(rec.getAuditType()));
		}
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-06-05 
	*/
	@RequestMapping(value="myOperation.action")
	@ActionLog(operateModelNm="我的操作历史表",operateFuncNm="myOperation",operateDescribe="对我的操作历史表进行跳转操作")
	public String myOperation(HttpServletRequest request) throws Exception{
		return "archive/document/myOperation"; 
	}

}