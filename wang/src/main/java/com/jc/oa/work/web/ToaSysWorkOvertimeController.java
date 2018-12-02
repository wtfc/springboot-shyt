package com.jc.oa.work.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.util.ActionLog;
import com.jc.oa.work.domain.ToaSysWorkOvertime;
import com.jc.oa.work.service.IToaSysWorkOvertimeService;
/**
 * @author mrb
 * @version 人员加班信息表
*/
@Controller
@RequestMapping(value="/machine/toaSysWorkOvertime")
public class ToaSysWorkOvertimeController extends BaseController{
	
	@Autowired 
	private IToaSysWorkOvertimeService toaSysWorkOvertimeService; 
	
	public ToaSysWorkOvertimeController(){
	}
	
	/**
	 * 保存方法
	 * @param TimeSet timeSet 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="人员加班信息表",operateFuncNm="save",operateDescribe="对人员加班信息表进行发起操作")
	public Map<String,Object> save(ToaSysWorkOvertime toaSysWorkOvertime,BindingResult result,
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
			toaSysWorkOvertimeService.save(toaSysWorkOvertime);
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="人员加班信息表",operateFuncNm="update",operateDescribe="对人员加班信息表进行更新操作")
	public Map<String, Object> update(ToaSysWorkOvertime toaSysWorkOvertime, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaSysWorkOvertimeService.update(toaSysWorkOvertime);
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
	 * @param TimeSet timeSet 实体类
	 * @return TimeSet 查询结果
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="人员加班信息表",operateFuncNm="get",operateDescribe="对人员加班信息表进行单条查询操作")
	public ToaSysWorkOvertime get(ToaSysWorkOvertime toaSysWorkOvertime,HttpServletRequest request) throws Exception{
		return toaSysWorkOvertimeService.get(toaSysWorkOvertime);
	}
	
	/**
	 * 分页查询方法
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="人员加班信息表",operateFuncNm="get",operateDescribe="人员加班信息表分页查询")
	public PageManager manageList(ToaSysWorkOvertime toaSysWorkOvertime,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(toaSysWorkOvertime.getOrderBy())) {
			toaSysWorkOvertime.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = toaSysWorkOvertimeService.query(toaSysWorkOvertime, page);
		return page_; 
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="人员加班信息表",operateFuncNm="manage",operateDescribe="对人员加班信息表进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "machine/toaSysWorkOvertime/toaSysWorkOvertime";
	}
	
	/**
	 * @description 新增与修改方法
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadForm.action")
	@ActionLog(operateModelNm="人员加班信息表表",operateFuncNm="loadForm",operateDescribe="对人员加班信息表表跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		//String id= request.getParameter("id");
		//if(!StringUtil.isEmpty(id)){
		//	model.addAttribute("Id", id);
		//}
		return "machine/toaSysWorkOvertime/module/toaSysWorkOvertimeModule";
	}
	
	/**
	 * 删除方法
	 * @param TimeSet timeSet 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="人员加班信息表表",operateFuncNm="deleteByIds",operateDescribe="对人员加班信息表表进行删除")
	public  Map<String, Object> deleteByIds(ToaSysWorkOvertime toaSysWorkOvertime,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaSysWorkOvertime.setPrimaryKeys(ids.split(","));	
		if(toaSysWorkOvertimeService.deleteByIds(toaSysWorkOvertime) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
		}
		return resultMap;
	}

}
