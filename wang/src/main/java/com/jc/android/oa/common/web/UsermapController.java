package com.jc.android.oa.common.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.android.oa.common.domain.Usermap;
import com.jc.android.oa.common.domain.validator.UsermapValidator;
import com.jc.android.oa.common.service.IUsermapService;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.security.util.ActionLog;



/**
 * @title 172.16.3.68
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * @author 
 * @version  2014-09-23
 */
 
@Controller
@RequestMapping(value="/android/usermap")
public class UsermapController extends BaseController{
	
	@Autowired
	private IUsermapService usermapService;
	
	@org.springframework.web.bind.annotation.InitBinder("usermap")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new UsermapValidator()); 
    }
	
	public UsermapController() {
	}

	/**
	 * 保存方法
	 * @param Usermap usermap 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-09-23
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="",operateFuncNm="save",operateDescribe="对进行新增操作")
	public void save(Long userId,String username,String client) throws Exception{
		usermapService.saveUsermapClient(userId, username, client);
		
	}

	/**
	* 修改方法
	* @param Usermap usermap 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-09-23 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="",operateFuncNm="update",operateDescribe="对进行更新操作")
	public Map<String, Object> update(Usermap usermap, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = usermapService.update(usermap);
		if (flag == 1) {
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param Usermap usermap 实体类
	 * @return Usermap 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-09-23
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="",operateFuncNm="get",operateDescribe="对进行单条查询操作")
	public Usermap get(Usermap usermap) throws Exception{
		return usermapService.get(usermap);
	}

	/**
	 * 分页查询方法
	 * @param Usermap usermap 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-09-23 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="",operateFuncNm="manageList",operateDescribe="对进行查询操作")
	public PageManager manageList(Usermap usermap,PageManager page){
		PageManager page_ = usermapService.query(usermap, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-09-23 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="",operateFuncNm="manage",operateDescribe="对进行跳转操作")
	public String manage() throws Exception{
		return "android/usermap/usermapCar"; 
	}

/**
	 * 删除方法
	 * @param Usermap usermap 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-09-23
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="",operateFuncNm="deleteByIds",operateDescribe="对进行删除")
	public Integer deleteByIds(Usermap usermap,String ids) throws Exception{
		usermap.setPrimaryKeys(ids.split(","));
		return usermapService.delete(usermap);
	}

}