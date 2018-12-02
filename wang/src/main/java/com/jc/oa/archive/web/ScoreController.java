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

import com.jc.oa.archive.domain.Score;
import com.jc.oa.archive.domain.validator.ScoreValidator;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.security.util.ActionLog;

import com.jc.oa.archive.service.IScoreService;


/**
 * @title  GOA2.0源代码
 * @description  controller类
 * @author 
 * @version  2014-06-05
 */
 
@Controller
@RequestMapping(value="/archive/score")
public class ScoreController extends BaseController{
	
	@Autowired
	private IScoreService scoreService;
	
	@org.springframework.web.bind.annotation.InitBinder("score")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new ScoreValidator()); 
    }
	
	public ScoreController() {
	}

	/**
	 * 分页查询方法
	 * @param Score score 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_知识管理_知识评分",operateFuncNm="manageList",operateDescribe="对OA_知识管理_知识评分进行查询操作")
	public PageManager manageList(Score score,PageManager page){
		PageManager page_ = scoreService.query(score, page);
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
	@ActionLog(operateModelNm="OA_知识管理_知识评分",operateFuncNm="manage",operateDescribe="对OA_知识管理_知识评分进行跳转操作")
	public String manage() throws Exception{
		return "archive/score/score1"; 
	}

/**
	 * 删除方法
	 * @param Score score 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_知识管理_知识评分",operateFuncNm="deleteByIds",operateDescribe="对OA_知识管理_知识评分进行删除")
	public Integer deleteByIds(Score score,String ids) throws Exception{
		score.setPrimaryKeys(ids.split(","));
		return scoreService.delete(score);
	}

	/**
	 * 保存方法
	 * @param Score score 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_知识管理_知识评分",operateFuncNm="save",operateDescribe="对OA_知识管理_知识评分进行新增操作")
	public Map<String,Object> save(@Valid Score score,BindingResult result,
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
			scoreService.save(score);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param Score score 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-06-05 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_知识管理_知识评分",operateFuncNm="update",operateDescribe="对OA_知识管理_知识评分进行更新操作")
	public Map<String, Object> update(Score score, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = scoreService.update(score);
		if (flag == 1) {
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param Score score 实体类
	 * @return Score 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_知识管理_知识评分",operateFuncNm="get",operateDescribe="对OA_知识管理_知识评分进行单条查询操作")
	public Score get(Score score) throws Exception{
		return scoreService.get(score);
	}

}