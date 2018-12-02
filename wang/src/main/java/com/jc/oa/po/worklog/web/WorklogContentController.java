package com.jc.oa.po.worklog.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.po.diary.domain.Diary;
import com.jc.oa.po.worklog.domain.WorklogContent;
import com.jc.oa.po.worklog.domain.validator.WorklogContentValidator;
import com.jc.oa.po.worklog.service.IWorklogContentService;


/**
 * @title 个人办公
 * @description  controller类
 * @author 
 * @version  2014-05-04
 */
 
@Controller
@RequestMapping(value="/po/worklogContent")
public class WorklogContentController extends BaseController{
	
	@Autowired
	private IWorklogContentService worklogContentService;
	
	@org.springframework.web.bind.annotation.InitBinder("worklogContent")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new WorklogContentValidator()); 
    }
	
	public WorklogContentController() {
	}

	/**
	 * @description 分页查询方法
	 * @param WorklogContent worklogContent 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-04 
	 */
	@RequestMapping(value="manageList.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public PageManager manageList(WorklogContent worklogContent,PageManager page){
		PageManager page_ = worklogContentService.query(worklogContent, page);
		return page_; 
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
		return "po/worklogContent/worklogContent添加常用网址"; 
	}

/**
	 * @description 删除方法
	 * @param WorklogContent worklogContent 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-05-04
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Integer deleteByIds(WorklogContent worklogContent,String ids) throws Exception{
		worklogContent.setPrimaryKeys(ids.split(","));
		return worklogContentService.delete(worklogContent);
	}

	/**
	 * @description 保存方法
	 * @param WorklogContent worklogContent 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-05-04
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid WorklogContent worklogContent,BindingResult result) throws Exception{
		
		Map<String,Object> resultMap = validateBean(result);
		if(!"false".equals(resultMap.get("success"))){
			worklogContentService.save(worklogContent);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param WorklogContent worklogContent 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-05-04 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	public Integer update(WorklogContent worklogContent) throws Exception{
		Integer flag = worklogContentService.update(worklogContent);
		return flag;
	}

	/**
	 * @description 获取单条记录方法
	 * @param WorklogContent worklogContent 实体类
	 * @return WorklogContent 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-04
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public WorklogContent get(WorklogContent worklogContent) throws Exception{
		return worklogContentService.get(worklogContent);
	}
	

}