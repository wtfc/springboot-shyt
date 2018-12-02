package com.jc.oa.archive.web;

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

import com.jc.oa.archive.domain.Recommend;
import com.jc.oa.archive.domain.validator.RecommendValidator;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.security.util.ActionLog;

import com.jc.oa.archive.service.IRecommendService;


/**
 * @title  GOA2.0源代码
 * @description  controller类
 * @author 
 * @version  2014-06-05
 */
 
@Controller
@RequestMapping(value="/archive/recommend")
public class RecommendController extends BaseController{
	
	@Autowired
	private IRecommendService recommendService;
	
	@org.springframework.web.bind.annotation.InitBinder("recommend")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new RecommendValidator()); 
    }
	
	public RecommendController() {
	}

	/**
	 * 分页查询方法
	 * @param Recommend recommend 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_知识管理_知识推荐表",operateFuncNm="manageList",operateDescribe="对OA_知识管理_知识推荐表进行查询操作")
	public PageManager manageList(Recommend recommend,PageManager page,HttpServletRequest request){
		PageManager page_ = recommendService.query(recommend, page);
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
	@ActionLog(operateModelNm="OA_知识管理_知识推荐表",operateFuncNm="manage",operateDescribe="对OA_知识管理_知识推荐表进行跳转操作")
	public String manage(HttpServletRequest request) throws Exception{
		return "archive/recommend/recommend1"; 
	}

/**
	 * 删除方法
	 * @param Recommend recommend 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_知识管理_知识推荐表",operateFuncNm="deleteByIds",operateDescribe="对OA_知识管理_知识推荐表进行删除")
	public Integer deleteByIds(Recommend recommend,String ids,HttpServletRequest request) throws Exception{
		recommend.setPrimaryKeys(ids.split(","));
		return recommendService.delete(recommend);
	}

	/**
	 * 保存方法
	 * @param Recommend recommend 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_知识管理_知识推荐表",operateFuncNm="save",operateDescribe="对OA_知识管理_知识推荐表进行新增操作")
	public Map<String,Object> save(@Valid Recommend recommend,BindingResult result,
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
			recommendService.save(recommend);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param Recommend recommend 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-06-05 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_知识管理_知识推荐表",operateFuncNm="update",operateDescribe="对OA_知识管理_知识推荐表进行更新操作")
	public Map<String, Object> update(Recommend recommend, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = recommendService.update(recommend);
		if (flag == 1) {
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param Recommend recommend 实体类
	 * @return Recommend 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_知识管理_知识推荐表",operateFuncNm="get",operateDescribe="对OA_知识管理_知识推荐表进行单条查询操作")
	public Recommend get(Recommend recommend,HttpServletRequest request) throws Exception{
		return recommendService.get(recommend);
	}

}