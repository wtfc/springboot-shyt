package com.jc.oa.po.readingstatus.web;

import java.util.List;
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
import com.jc.oa.po.readingstatus.domain.ReadingStatus;
import com.jc.oa.po.readingstatus.domain.validator.ReadingStatusValidator;
import com.jc.oa.po.readingstatus.service.IReadingStatusService;
import com.jc.system.CustomException;
import com.jc.system.security.util.ActionLog;


/**
 * @title 个人办公
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-05-22
 */
 
@Controller
@RequestMapping(value="/po/readingStatus")
public class ReadingStatusController extends BaseController{
	
	@Autowired
	private IReadingStatusService readingStatusService;
	
	@org.springframework.web.bind.annotation.InitBinder("readingStatus")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new ReadingStatusValidator()); 
    }
	
	public ReadingStatusController() {
	}

	/**
	 * 分页查询方法
	 * @param ReadingStatus readingStatus 实体类
	 * @param PageManager page 分页实体类
	 * @param HttpServletRequest request
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-22 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_阅读表",operateFuncNm="manageList",operateDescribe="对公共_个人办公_阅读表进行查询操作")
	public PageManager manageList(ReadingStatus readingStatus,PageManager page,HttpServletRequest request){
		PageManager page_ = readingStatusService.query(readingStatus, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @param HttpServletRequest request
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-05-22 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="公共_个人办公_阅读表",operateFuncNm="manage",operateDescribe="对公共_个人办公_阅读表进行跳转操作")
	public String manage(HttpServletRequest request) throws Exception{
		return "po/readingStatus/readingStatus添加常用网址"; 
	}
	
/**
	 * 删除方法
	 * @param ReadingStatus readingStatus 实体类
	 * @param String ids 删除的多个主键
	 * @param HttpServletRequest request
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-05-22
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_阅读表",operateFuncNm="deleteByIds",operateDescribe="对公共_个人办公_阅读表进行删除")
	public Integer deleteByIds(ReadingStatus readingStatus,String ids,HttpServletRequest request) throws Exception{
		readingStatus.setPrimaryKeys(ids.split(","));
		return readingStatusService.delete(readingStatus);
	}

	/**
	 * 保存方法
	 * @param ReadingStatus readingStatus 实体类
	 * @param BindingResult result 校验结果
	 * @param HttpServletRequest request
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-05-22
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_阅读表",operateFuncNm="save",operateDescribe="对公共_个人办公_阅读表进行新增操作")
	public Map<String,Object> save(@Valid ReadingStatus readingStatus,BindingResult result,
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
			readingStatusService.save(readingStatus);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param ReadingStatus readingStatus 实体类
	* @param HttpServletRequest request
	* @return Integer 更新的数目
	* @author
	* @version  2014-05-22 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_阅读表",operateFuncNm="update",operateDescribe="对公共_个人办公_阅读表进行更新操作")
	public Map<String, Object> update(ReadingStatus readingStatus, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = readingStatusService.update(readingStatus);
		if (flag == 1) {
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param ReadingStatus readingStatus 实体类
	 * @param HttpServletRequest request
	 * @return ReadingStatus 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-22
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_阅读表",operateFuncNm="get",operateDescribe="对公共_个人办公_阅读表进行单条查询操作")
	public ReadingStatus get(ReadingStatus readingStatus,HttpServletRequest request) throws Exception{
		return readingStatusService.get(readingStatus);
	}
	
	/**
	 * 获得所有
	 * @param ReadingStatus readingStatus 实体类
	 * @param PageManager page 分页实体类
	 * @param HttpServletRequest request
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-22 
	 * @throws CustomException 
	 */
	@RequestMapping(value="queryAll.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_阅读表",operateFuncNm="queryAll",operateDescribe="对公共_个人办公_阅读表进行查询操作")
	public List<ReadingStatus> queryAll(ReadingStatus readingStatus,HttpServletRequest request) throws CustomException{
		List<ReadingStatus> readingStatusListr = readingStatusService.queryAll(readingStatus);
		return readingStatusListr; 
	}

	/**
	 * 获得所有
	 * @param ReadingStatus readingStatus 实体类
	 * @param PageManager page 分页实体类
	 * @param HttpServletRequest request
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-22 
	 * @throws CustomException 
	 */
	@RequestMapping(value="queryAllByDataTable.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_阅读表",operateFuncNm="queryAll",operateDescribe="对公共_个人办公_阅读表进行查询操作")
	public PageManager queryAllByDataTable(PageManager pageManager,ReadingStatus readingStatus,HttpServletRequest request) throws CustomException{
		List<ReadingStatus> readingStatusListr = readingStatusService.queryAll(readingStatus);
		pageManager.setData(readingStatusListr);
		return pageManager; 
	}
}