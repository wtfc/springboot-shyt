package com.jc.system.security.web;

import java.util.HashMap;
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
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.Setting;
import com.jc.system.security.domain.User;
import com.jc.system.security.domain.validator.SettingValidator;
import com.jc.system.security.service.ISettingService;
import com.jc.system.security.util.ActionLog;


/**
 * @title GOA2.0
 * @description  controller类
 * @author 
 * @version  2014-04-28
 */
 
@Controller
@RequestMapping(value="/sys/setting")
public class SettingController extends BaseController{
	
	@Autowired
	private ISettingService settingService;
	
	@org.springframework.web.bind.annotation.InitBinder("setting")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new SettingValidator()); 
    }
	
	public SettingController() {
	}
	
	private SettingValidator sv = new SettingValidator();

	/**
	 * @description 分页查询方法
	 * @param Setting setting 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-28 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	public PageManager manageList(Setting setting,PageManager page){
		PageManager page_ = settingService.query(setting, page);
		return page_; 
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-28 
	*/
	@RequestMapping(value="manage.action")
	public String manage() throws Exception{
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			if(userInfo.getIsAdmin() == 1 || userInfo.getIsSystemAdmin()){
				return "sys/setting/setting";
			} else {
				return "error/unauthorized";
			}
		}
		return "error/unauthorized";
	}

/**
	 * @description 删除方法
	 * @param Setting setting 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-04-28
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	public Integer deleteByIds(Setting setting,String ids) throws Exception{
		setting.setPrimaryKeys(ids.split(","));
		return settingService.delete(setting);
	}

	/**
	 * @description 保存方法
	 * @param Setting setting 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-04-28
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="参数设置",operateFuncNm="save",operateDescribe="对参数设置进行添加") 
	public Map<String,Object> save(@Valid Setting setting,BindingResult result,HttpServletRequest request) throws Exception{
		
		sv.validate(setting, result);
		
		Map<String,Object> resultMap = validateBean(result);
		// 验证bean
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		if(!"false".equals(resultMap.get("success"))){
			setting.setCreateDate(DateUtils.getSysDate());
			setting.setModifyDate(DateUtils.getSysDate());
			settingService.save(setting);
			resultMap.put("success", "true");
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param Setting setting 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-04-28 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="参数设置",operateFuncNm="update",operateDescribe="对参数设置进行修改") 
	public Map<String, Object> update(Setting setting,BindingResult result,HttpServletRequest request) throws Exception{
		sv.validate(setting, result);
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		setting.setModifyDate(DateUtils.getSysDate());
		Integer flag = settingService.update(setting);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	 * @description 获取单条记录方法
	 * @param Setting setting 实体类
	 * @return Setting 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-28
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public Setting get(Setting setting) throws Exception{
		return settingService.get(setting);
	}
	
	/**
	 * @description 获取单条记录方法(系统参数配置为统一的一个)
	 * @param Setting setting 实体类
	 * @return Setting 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-28
	 */
	@RequestMapping(value="getOne.action")
	@ResponseBody
	public Map<String, Object> getOne(Setting setting, HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put("settingvos", settingService.getOne(setting));
		map.put(GlobalContext.SESSION_TOKEN, token);
		return map;
	}

}