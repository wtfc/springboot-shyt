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

import com.jc.oa.archive.domain.Favorite;
import com.jc.oa.archive.domain.validator.FavoriteValidator;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.util.ActionLog;
import com.jc.oa.archive.service.IFavoriteService;


/**
 * @title  GOA2.0源代码
 * @description  controller类
 * @author 
 * @version  2014-06-05
 */
 
@Controller
@RequestMapping(value="/archive/favorite")
public class FavoriteController extends BaseController{
	
	@Autowired
	private IFavoriteService favoriteService;
	
	@org.springframework.web.bind.annotation.InitBinder("favorite")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new FavoriteValidator()); 
    }
	
	public FavoriteController() {
	}

	/**
	 * 分页查询方法
	 * @param Favorite favorite 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_文档管理/知识管理_收藏信息表",operateFuncNm="manageList",operateDescribe="对OA_文档管理/知识管理_收藏信息表进行查询操作")
	public PageManager manageList(Favorite favorite,PageManager page,HttpServletRequest request){
		PageManager page_ = favoriteService.query(favorite, page);
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
	@ActionLog(operateModelNm="OA_文档管理/知识管理_收藏信息表",operateFuncNm="manage",operateDescribe="对OA_文档管理/知识管理_收藏信息表进行跳转操作")
	public String manage(HttpServletRequest request) throws Exception{
		return "archive/favorite/favorite1"; 
	}

/**
	 * 删除方法
	 * @param Favorite favorite 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_文档管理/知识管理_收藏信息表",operateFuncNm="deleteByIds",operateDescribe="对OA_文档管理/知识管理_收藏信息表进行删除")
	public Integer deleteByIds(Favorite favorite,String ids,HttpServletRequest request) throws Exception{
		favorite.setPrimaryKeys(ids.split(","));
		return favoriteService.delete(favorite);
	}

	/**
	 * 保存方法
	 * @param Favorite favorite 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_文档管理/知识管理_收藏信息表",operateFuncNm="save",operateDescribe="对OA_文档管理/知识管理_收藏信息表进行新增操作")
	public Map<String,Object> save(@Valid Favorite favorite,BindingResult result,
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
			favoriteService.save(favorite);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param Favorite favorite 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-06-05 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_文档管理/知识管理_收藏信息表",operateFuncNm="update",operateDescribe="对OA_文档管理/知识管理_收藏信息表进行更新操作")
	public Map<String, Object> update(Favorite favorite, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = favoriteService.update(favorite);
		if (flag == 1) {
			resultMap.put("success", "true");
		} else {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_OA_COMMON_014"));
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param Favorite favorite 实体类
	 * @return Favorite 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_文档管理/知识管理_收藏信息表",operateFuncNm="get",operateDescribe="对OA_文档管理/知识管理_收藏信息表进行单条查询操作")
	public Favorite get(Favorite favorite,HttpServletRequest request) throws Exception{
		return favoriteService.get(favorite);
	}

}