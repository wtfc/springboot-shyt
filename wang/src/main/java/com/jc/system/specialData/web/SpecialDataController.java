package com.jc.system.specialData.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.common.util.CacheUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.portal.util.PortalUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.ActionLog;
import com.jc.system.specialData.domain.SpecialData;
import com.jc.system.specialData.domain.validator.SpecialDataValidator;
import com.jc.system.specialData.service.ISpecialDataService;

/**
 * @title 172.16.3.68
 * @description  controller类
 * @author 
 * @version  2014-12-02
 */
 
@Controller
@RequestMapping(value="/sys/specialData")
public class SpecialDataController extends BaseController{
	
	@Autowired
	private ISpecialDataService specialDataService;
	
	@Autowired
	private IUserService userService;
	
	@org.springframework.web.bind.annotation.InitBinder("specialData")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new SpecialDataValidator()); 
    }
	
	public SpecialDataController() {
	}

	/**
	 * 分页查询方法
	 * @param SpecialData specialData 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-12-02 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	public PageManager manageList(SpecialData specialData,PageManager page){
		//默认排序
		if(StringUtil.isEmpty(specialData.getOrderBy())) {
			specialData.addOrderByFieldDesc("t.CREATE_DATE");
		}
		PageManager page_ = specialDataService.query(specialData, page);
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
	@ActionLog(operateModelNm="假日提醒",operateFuncNm="manage",operateDescribe="对假日提醒进行查询操作")
	public String manage(HttpServletRequest request) throws Exception{
		return "sys/specialData/specialData"; 
	}

/**
	 * 删除方法
	 * @param SpecialData specialData 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-12-02
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="假日提醒",operateFuncNm="deleteByIds",operateDescribe="对假日提醒进行删除")
	public  Map<String, Object> deleteByIds(SpecialData specialData,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		specialData.setPrimaryKeys(ids.split(","));	
		if(specialDataService.deleteByIds(specialData) != 0){
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
	 * @param SpecialData specialData 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-12-02
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="假日提醒",operateFuncNm="save",operateDescribe="对假日提醒进行新增操作")
	public Map<String,Object> save(@Valid SpecialData specialData,BindingResult result,
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
			if(specialData.getUserId() != null && !specialData.getUserId().equals("")){
				User user = new User();
				user.setId(specialData.getUserId());
				user = userService.get(user);
				//User u = (User)CacheUtils.get("user_info_" + specialData.getUserId());
				specialData.setUserPicurl(user.getPhoto());
			}
			specialDataService.save(specialData);
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param SpecialData specialData 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-12-02 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="假日提醒",operateFuncNm="update",operateDescribe="对假日提醒进行更新操作")
	public Map<String, Object> update(SpecialData specialData, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = specialDataService.update(specialData);
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
	 * @param SpecialData specialData 实体类
	 * @return SpecialData 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-12-02
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="假日提醒",operateFuncNm="get",operateDescribe="对假日提醒进行单条查询操作")
	public SpecialData get(SpecialData specialData,HttpServletRequest request) throws Exception{
		return specialDataService.get(specialData);
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
		model.addAttribute("data", map);
		return "sys/specialData/specialDataEdit"; 
	}
	
	/**
	* @description 门户组件数据源
	* @return String 跳转的路径
	* @throws Exception
	* @author	池海洲
	* @version  2014-12-3 
	*/
	@RequestMapping(value="manageSpecialData.action")
	public String manageSpecialData(Model model,SpecialData specialData,HttpServletRequest request) throws Exception{
		//门户引用类固定代码---start---
		model = PortalUtils.getPortalParameter(model, request);
	    //门户引用类固定代码---end---
		String parameter = request.getParameter("parameter");//携带业务参数
		
		if(!StringUtils.isEmpty(parameter)){//栏目编号
			//info.setColumnId(Long.parseLong(parameter));
			model.addAttribute("columnId", Long.parseLong(parameter));
		}else 
			model.addAttribute("columnId", null);
		
		return "sys/specialData/specialDataPortal";
	}
	
	/**
	* @description 门户组件数据源
	* @return Map 数据集合
	* @throws Exception
	* @author	
	* @version  2014-09-02 
	*/
	@RequestMapping(value="getSpecialData.action")
	@ResponseBody
	public Map<String, Object> getSpecialData(Long columnId,String funViewType,int dataRows) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		User userInfo = SystemSecurityUtils.getUser();
		result = specialDataService.querySpecialForPortal(columnId, userInfo.getId(), userInfo.getDeptId(), userInfo.getOrgId());
		result.put("dataRows",dataRows);
		return result;
	}
	
	/**
	 * 生日祝福方法
	 * @param SpecialData specialData 实体类
	 * @return SpecialData 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-12-22
	 */
	@RequestMapping(value="birthdayBlessing.action")
	@ResponseBody
	public SpecialData birthdayBlessing(SpecialData specialData,HttpServletRequest request) throws Exception{
		User userInfo = SystemSecurityUtils.getUser();
		return specialDataService.getBirthdayBlessing(userInfo.getId());
	}

}