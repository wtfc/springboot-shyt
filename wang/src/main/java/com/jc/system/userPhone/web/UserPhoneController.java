package com.jc.system.userPhone.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.CustomException;
import com.jc.system.common.util.BeanUtil;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.util.ActionLog;
import com.jc.system.userPhone.domain.UserPhone;
import com.jc.system.userPhone.domain.validator.UserPhoneValidator;
import com.jc.system.userPhone.service.IUserPhoneService;

/**
 * @title GOA2.0使用的个人组别和公共组别应用
 * @description  controller类
 * @author 
 * @version  2014-11-21
 */
 
@Controller
@RequestMapping(value="/sys/userPhone")
public class UserPhoneController extends BaseController{
	
	@Autowired
	private IUserPhoneService userPhoneService;
	
	@org.springframework.web.bind.annotation.InitBinder("userPhone")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new UserPhoneValidator()); 
    }
	
	public UserPhoneController() {
	}

	/**
	 * 保存方法
	 * @param UserPhone userPhone 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-11-21
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA移动用户绑定表",operateFuncNm="save",operateDescribe="对OA移动用户绑定表进行新增操作")
	public Map<String,Object> save(@Valid UserPhone userPhone,BindingResult result,
			HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(!"false".equals(resultMap.get("success"))){
			userPhoneService.save(userPhone);
			resultMap.put("success", "true");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "changeStatus.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA移动用户绑定表",operateFuncNm="save",operateDescribe="对OA移动用户绑定表进行新增操作")
	public Map<String,Object> changeStatus(@Valid UserPhone userPhone,BindingResult result,
			HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		
		if(!"false".equals(resultMap.get("success"))){
			UserPhone userP = new UserPhone();
			userP.setId(userPhone.getId());
			userP = userPhoneService.get(userP);
			userP.setStatus(userPhone.getStatus());
			userPhoneService.update(userP);
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param UserPhone userPhone 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-11-21 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA移动用户绑定表",operateFuncNm="update",operateDescribe="对OA移动用户绑定表进行更新操作")
	public Map<String, Object> update(UserPhone userPhone, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = userPhoneService.update(userPhone);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	@RequestMapping(value="queryAll.action")
	@ResponseBody
	public List<UserPhone> queryAll(UserPhone userPhone) throws Exception{
		return userPhoneService.queryAll(userPhone);
	}

	/**
	 * 获取单条记录方法
	 * @param UserPhone userPhone 实体类
	 * @return UserPhone 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-11-21
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA移动用户绑定表",operateFuncNm="get",operateDescribe="对OA移动用户绑定表进行单条查询操作")
	public UserPhone get(UserPhone userPhone) throws Exception{
		return userPhoneService.get(userPhone);
	}

	/**
	 * 分页查询方法
	 * @param UserPhone userPhone 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-11-21 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA移动用户绑定表",operateFuncNm="manageList",operateDescribe="对OA移动用户绑定表进行查询操作")
	public PageManager manageList(UserPhone userPhone,PageManager page){
		if(StringUtils.isEmpty(userPhone.getOrderBy())) {
			userPhone.addOrderByFieldDesc("t.CREATE_DATE");
		}
		PageManager page_ = userPhoneService.query(userPhone, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-11-21 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="OA移动用户绑定表",operateFuncNm="manage",operateDescribe="对OA移动用户绑定表进行跳转操作")
	public String manage() throws Exception{
		return "sys/userPhone/userPhoneList"; 
	}

/**
	 * 删除方法
	 * @param UserPhone userPhone 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-11-21
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA移动用户绑定表",operateFuncNm="deleteByIds",operateDescribe="对OA移动用户绑定表进行删除")
	public  Map<String, Object> deleteByIds(UserPhone userPhone,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		userPhone.setPrimaryKeys(ids.split(","));	
		userPhoneService.delete(userPhone,false);
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		return resultMap;
	}

	/**
	 * @description 弹出对话框方法
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2014-11-21
	 */
	@RequestMapping(value="loadForm.action")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "sys/userPhone/module/userPhoneListFormModule"; 
	}
	
	@RequestMapping(value = "saveUserPhone.action")
	@ResponseBody
	public Map<String, Object> saveUserPhone(@RequestBody Map<String, Object> userPhoneMap, BindingResult result) throws CustomException {
		UserPhone userPhoneBean = BeanUtil.map2Object(userPhoneMap, UserPhone.class);
		Map<String, Object> resultMap = validateBean(result);
		userPhoneService.saveUserPhone(userPhoneBean);
		resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
		return resultMap;
	}

}