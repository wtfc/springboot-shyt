package com.jc.system.specialData.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.util.ActionLog;
import com.jc.system.specialData.domain.SpecialdataShare;
import com.jc.system.specialData.domain.validator.SpecialdataShareValidator;
import com.jc.system.specialData.service.ISpecialdataShareService;

/**
 * @title 172.16.3.68
 * @description  controller类
 * @author 
 * @version  2014-12-02
 */
 
@Controller
@RequestMapping(value="/sys/specialdataShare")
public class SpecialdataShareController extends BaseController{
	
	@Autowired
	private ISpecialdataShareService specialdataShareService;
	
	@org.springframework.web.bind.annotation.InitBinder("specialdataShare")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new SpecialdataShareValidator()); 
    }
	
	public SpecialdataShareController() {
	}

	/**
	 * 分页查询方法
	 * @param SpecialdataShare specialdataShare 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-12-02 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	public PageManager manageList(SpecialdataShare specialdataShare,PageManager page){
		//默认排序
		if(StringUtil.isEmpty(specialdataShare.getOrderBy())) {
			specialdataShare.addOrderByFieldDesc("t.CREATE_DATE");
		}
		PageManager page_ = specialdataShareService.query(specialdataShare, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-02 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="",operateFuncNm="manage",operateDescribe="对进行跳转操作")
	public String manage() throws Exception{
		return "sys/specialdataShare/specialdataShareAaa"; 
	}

/**
	 * 删除方法
	 * @param SpecialdataShare specialdataShare 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-12-02
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="",operateFuncNm="deleteByIds",operateDescribe="对进行删除")
	public  Map<String, Object> deleteByIds(SpecialdataShare specialdataShare,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		specialdataShare.setPrimaryKeys(ids.split(","));	
		if(specialdataShareService.deleteByIds(specialdataShare) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
		}
		return resultMap;
	}

	/**
	 * 保存方法
	 * @param SpecialdataShare specialdataShare 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-12-02
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="节日生日共享范围",operateFuncNm="save",operateDescribe="对节日生日共享范围进行新增操作")
	public Map<String,Object> save(@Valid SpecialdataShare specialdataShare,BindingResult result,
			HttpServletRequest request,String userids,String deptids,String organids,String token) throws Exception{
		
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
			specialdataShareService.delAndsave(specialdataShare,userids,deptids,organids);
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param SpecialdataShare specialdataShare 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-12-02 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="",operateFuncNm="update",operateDescribe="对进行更新操作")
	public Map<String, Object> update(SpecialdataShare specialdataShare, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = specialdataShareService.update(specialdataShare);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param SpecialdataShare specialdataShare 实体类
	 * @return SpecialdataShare 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-12-02
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="",operateFuncNm="get",operateDescribe="对进行单条查询操作")
	public SpecialdataShare get(SpecialdataShare specialdataShare) throws Exception{
		return specialdataShareService.get(specialdataShare);
	}

	/**
	 * @description 弹出对话框方法
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2014-12-02
	 */
	@RequestMapping(value="loadForm.action")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("datas", map);
		return "sys/specialData/specialdataShareEdit"; 
	}
	
	/**
	 * 获取控件分类Id方法
	 * @param  
	 * @return  
	 * @throws Exception
	 * @author 刘鑫峰
	 */
	@RequestMapping(value="getIds.action")
	@ResponseBody 
	public Map<String, String> getIds(SpecialdataShare specialdataShare,HttpServletRequest request) throws Exception{
		Map<String, String> result = specialdataShareService.getIds(specialdataShare);
		result.put(GlobalContext.SESSION_TOKEN, getToken(request));
		return result;
	}

}