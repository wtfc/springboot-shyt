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
import com.jc.oa.ic.domain.MailRecord;
import com.jc.oa.ic.domain.validator.MailRecordValidator;
import com.jc.oa.ic.service.IMailRecordService;


/**
 * @title 互动交流
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-17
 */
 
@Controller
@RequestMapping(value="/ic/mailRecord")
public class MailRecordController extends BaseController{
	
	@Autowired
	private IMailRecordService mailRecordService;
	
	@org.springframework.web.bind.annotation.InitBinder("mailRecord")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new MailRecordValidator()); 
    }
	
	public MailRecordController() {
	}

	/**
	 * @description 分页查询方法
	 * @param MailRecord mailRecord 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-17 
	 */
	@RequestMapping(value="manageList.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public PageManager manageList(MailRecord mailRecord,PageManager page,HttpServletRequest request){
		PageManager page_ = mailRecordService.query(mailRecord, page);
		return page_; 
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-17 
	*/
	@RequestMapping(value="manage.action")
	public String manage(HttpServletRequest request) throws Exception{
		return "ic/mailRecord/mailRecordInteract"; 
	}

/**
	 * @description 删除方法
	 * @param MailRecord mailRecord 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Integer deleteByIds(MailRecord mailRecord,String ids,HttpServletRequest request) throws Exception{
		mailRecord.setPrimaryKeys(ids.split(","));
		return mailRecordService.delete(mailRecord);//待完善
	}

	/**
	 * @description 保存方法
	 * @param MailRecord mailRecord 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid MailRecord mailRecord,BindingResult result,HttpServletRequest request) throws Exception{
		
		Map<String,Object> resultMap = validateBean(result);
		if(!"false".equals(resultMap.get("success"))){
			mailRecordService.save(mailRecord);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param MailRecord mailRecord 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-04-17 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	public Integer update(MailRecord mailRecord,HttpServletRequest request) throws Exception{
		Integer flag = mailRecordService.update(mailRecord);
		return flag;
	}

	/**
	 * @description 获取单条记录方法
	 * @param MailRecord mailRecord 实体类
	 * @return MailRecord 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public MailRecord get(MailRecord mailRecord,HttpServletRequest request) throws Exception{
		return mailRecordService.get(mailRecord);
	}

}