package com.jc.system.security.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.Operlog;
import com.jc.system.security.domain.User;
import com.jc.system.security.domain.validator.OperlogValidator;
import com.jc.system.security.service.IOperlogService;
import com.jc.system.security.util.ActionLog;


/**
 * @title GOA2.0
 * @description  controller类
 * @author 
 * @version  2014-05-04
 */
 
@Controller
@RequestMapping(value="/sys/operlog")
public class OperlogController extends BaseController{
	
	@Autowired
	private IOperlogService operlogService;
	
	@org.springframework.web.bind.annotation.InitBinder("operlog")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new OperlogValidator()); 
    }
	
	public OperlogController() {
	}

	/**
	 * @description 分页查询方法
	 * @param Operlog operlog 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-04 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="操作日志",operateFuncNm="manageList",operateDescribe="对操作日志进行查询")
	public PageManager manageList(Operlog operlog,PageManager page,HttpServletRequest request){
		//默认排序
		if(StringUtils.isEmpty(operlog.getOrderBy())) {
			operlog.addOrderByFieldDesc("t.OPER_TIME");
		}
		PageManager page_ = operlogService.query(operlog, page);
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
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			if(userInfo.getIsAdmin() == 1 || userInfo.getIsSystemAdmin()){
				return "sys/operlog/operlog";
			} else {
				return "error/unauthorized";
			}
		}
		return "error/unauthorized";
	}

/**
	 * @description 删除方法
	 * @param Operlog operlog 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-05-04
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Integer deleteByIds(Operlog operlog,String ids) throws Exception{
		operlog.setPrimaryKeys(ids.split(","));
		return operlogService.delete(operlog);
	}

	/**
	 * @description 保存方法
	 * @param Operlog operlog 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-05-04
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid Operlog operlog,BindingResult result) throws Exception{
		
		Map<String,Object> resultMap = validateBean(result);
		if(!"false".equals(resultMap.get("success"))){
			operlogService.save(operlog);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param Operlog operlog 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-05-04 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	public Integer update(Operlog operlog) throws Exception{
		Integer flag = operlogService.update(operlog);
		return flag;
	}

	/**
	 * @description 获取单条记录方法
	 * @param Operlog operlog 实体类
	 * @return Operlog 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-05-04
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public Operlog get(Operlog operlog) throws Exception{
		return operlogService.get(operlog);
	}

}