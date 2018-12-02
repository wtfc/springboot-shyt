package com.jc.shjfgl.machine.web;

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
import com.jc.shjfgl.machine.domain.Machine;
import com.jc.shjfgl.machine.service.IMachineService;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.util.ActionLog;



@Controller
@RequestMapping(value="/machine/machine")
public class MachineController extends BaseController{
	
	@Autowired
	private IMachineService machineService;
	
	public MachineController(){};
	
	/**
	* @description 机房管理管理默认跳转方法
	* @param HttpServletRequest request
	* @return 
	* @throws Exception
	* @author 马如彪
	* @version  2015-07-23 
	*/
	@RequestMapping(value="zygl.action")
	@ActionLog(operateModelNm="机房管理",operateFuncNm="personalOffice",operateDescribe="机房管理默认跳转")
	public String personalOfficeSkip(HttpServletRequest request) throws Exception{
		return "sys/zygl"; 
	}
	
	/**
	* @description 机房管理管理默认跳转方法
	* @param HttpServletRequest request
	* @return 
	* @throws Exception
	* @author 马如彪
	* @version  2015-07-23 
	*/
	@RequestMapping(value="gdgl.action")
	@ActionLog(operateModelNm="机房管理",operateFuncNm="personalOffice",operateDescribe="机房管理默认跳转")
	public String zygl(HttpServletRequest request) throws Exception{
		return "sys/gdgl"; 
	}
	
	/**
	 * 保存方法
	 * @param TimeSet timeSet 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-12-08
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单表",operateFuncNm="save",operateDescribe="对工单表表进行发起操作")
	public Map<String,Object> save( Machine machine,BindingResult result,
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
			if(machine.getId()!=null){
				machine.setId(null);
			}
			machineService.save(machine);
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
	* @version  2014-12-08 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单表",operateFuncNm="update",operateDescribe="对机房工单表进行更新操作")
	public Map<String, Object> update(Machine machine, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = machineService.update(machine);
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
	 * @version  2014-12-08
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单表",operateFuncNm="get",operateDescribe="对机房工单表进行单条查询操作")
	public Machine get(Machine machine,HttpServletRequest request) throws Exception{
		return machineService.get(machine);
	}

	/**
	 * 分页查询方法
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-12-08 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单表",operateFuncNm="get",operateDescribe="分页查询操作")
	public PageManager manageList(Machine machine,PageManager page,HttpServletRequest request){
		
		//默认排序
		if(StringUtil.isEmpty(machine.getOrderBy())) {
			machine.addOrderByFieldDesc("t.CREATE_DATE");
		}
		PageManager page_ = machineService.query(machine, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单表进行跳转操作")
	public String manage(HttpServletRequest request) throws Exception{
		
		return "shjfgl/machine/machine"; 
	}
	
/**
	 * 删除方法
	 * @param TimeSet timeSet 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-12-08
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房进出工单表",operateFuncNm="deleteByIds",operateDescribe="对工单表进行删除")
	public  Map<String, Object> deleteByIds(Machine machine,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		machine.setPrimaryKeys(ids.split(","));	
		if(machineService.deleteByIds(machine) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
		}
		return resultMap;
	}
	
}
