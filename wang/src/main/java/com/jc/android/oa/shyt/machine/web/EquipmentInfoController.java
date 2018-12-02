package com.jc.android.oa.shyt.machine.web;

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
import org.springframework.web.servlet.View;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.machine.service.IToaMachineRestartService;
import com.jc.oa.shyt.service.ICustomerService;
import com.jc.shjfgl.machine.domain.Equipment;
import com.jc.shjfgl.machine.service.IEquipmentService;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.number.service.Number;
import com.jc.system.number.service.interfaces.INumber;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.ActionLog;


@Controller
@RequestMapping(value="/customer/equipmentInfo")
public class EquipmentInfoController extends BaseController{
	
	
	@Autowired
	private IEquipmentService equipmentService;

    @Autowired
    private IUserService userService;
    
    @Autowired
    private IToaMachineRestartService toaMachineRestartService;

	@Autowired 
	private ICustomerService toaShytCustomerService;
    
	public EquipmentInfoController(){};
	/**
	 * 保存方法
	 * @param TimeSet timeSet 实体类
	 * @param Long 	  id      机房信息id
	 * @param String  operate 操作类型
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-12-08
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单表",operateFuncNm="save",operateDescribe="对工单表表进行发起操作")
	public Map<String,Object> save(Equipment equipment,Long id,String operate,BindingResult result,
			HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = validateBean(result);
		Equipment equipmentTemp=null;
		if (resultMap.size() > 0) {
			return resultMap;
		}
		//得到要操作数据的信息
		equipmentTemp=equipmentService.get(equipment);
		
		if(!"false".equals(resultMap.get("success"))){
			
			//发起操作
			Integer flag=equipmentService.operate(equipmentTemp,operate);
			if(flag==-1){
				resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, "设备信息不足，无法完成操作");
				resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
			}else if(flag==1){
				resultMap.put("success", "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
				resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
			}else if(flag==2){
				resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_002"));
				resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
			}else{
				resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, "设备不存在");
				resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
			}
		}
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
	public Equipment get(Equipment equipment,HttpServletRequest request) throws Exception{
		return equipmentService.get(equipment);
	}

	/**
	 * 分页查询方法
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2017-03-21 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="设备表",operateFuncNm="get",operateDescribe="分页查询")
	public PageManager manageList(Equipment equipment,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(equipment.getOrderBy())) {
			equipment.addOrderByFieldDesc("t.CREATE_DATE");
		}
		String companyId = SystemSecurityUtils.getUser().getCode();
		if(!StringUtil.isEmpty(companyId)){
			equipment.setCompanyId(Long.valueOf(companyId));
		}
		if("".equals(equipment.getContact().trim())){
			equipment.setContact(null);
		}
		PageManager page_ = equipmentService.query(equipment, page);
		return page_; 
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2017-03-21 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="机房操作页面跳转",operateFuncNm="manage",operateDescribe="机房操作页面跳转")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "customer/equipmentInfo/roomView";
	}
	
	/**
	 * @description 弹出对话框方法
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadForm.action")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		//编号
		INumber number = new Number();
		String applyNum = number.getNumber("MachineRestart", 1,2, null);
		applyNum = applyNum.substring(1, applyNum.length());
		model.addAttribute("applyNum",applyNum);
		return "customer/equipmentInfo/module/roomViewModule"; 
	}
}
