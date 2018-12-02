package com.jc.shjfgl.machine.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.shjfgl.machine.domain.Equipment;
import com.jc.shjfgl.machine.service.IEquipmentService;
import com.jc.system.CustomException;
import com.jc.system.common.util.ExcuteExcelUtil;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.number.service.Number;
import com.jc.system.number.service.interfaces.INumber;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.ActionLog;


@Controller
@RequestMapping(value="/machine/equipment")
public class EquipmentController extends BaseController{
	
	
	@Autowired
	private IEquipmentService equipmentService;

    @Autowired
    private IUserService userService;
    
	public EquipmentController(){};
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
	public Map<String,Object> save(Equipment equipment,BindingResult result,
			HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
//		resultMap = validateToken(request);
//		if (resultMap.size() > 0) {
//			return resultMap;
//		}
		if(!"false".equals(resultMap.get("success"))){
			//编号
			INumber number = new Number();
			String applyNum = number.getNumber("EquipmentNumber", 1,2, null);
			applyNum = applyNum.substring(1, applyNum.length());
			
			String equipmentNumber=applyNum;
			equipment.setEquipmentNumber(equipmentNumber);
			if(equipment.getId()!=null){
				equipment.setId(null);
			}
			equipmentService.save(equipment);
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
	public Map<String, Object> update(Equipment equipment, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = equipmentService.update(equipment);
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
	 * @version  2014-12-08 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房表",operateFuncNm="get",operateDescribe="分页查询")
	public PageManager manageList(Equipment equipment,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(equipment.getOrderBy())) {
			equipment.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = equipmentService.query(equipment, page);
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
	public String manage(Model model,HttpServletRequest request) throws Exception{
		String number = request.getParameter("number");
		String name = request.getParameter("name");
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")||deptName.equals("富丰桥机房")||deptName.equals("亦庄大族机房")){
			model.addAttribute("deptName", deptName);
		}else if(deptName.equals("小米代维组")){
			model.addAttribute("deptName", "鲁谷机房");
		}else{
			model.addAttribute("deptName", "");
		}
		return "shjfgl/equipment/equipment";
	}
	
	/**
	* 跳转方法(大族机房)
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageDaZu.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单表进行跳转操作")
	public String manageDaZu(Model model,HttpServletRequest request) throws Exception{
		String number = request.getParameter("number");
		String name = request.getParameter("name");
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")||deptName.equals("富丰桥机房")||deptName.equals("亦庄大族机房")){
			model.addAttribute("deptName", deptName);
		}else if(deptName.equals("小米代维组")){
			model.addAttribute("deptName", "鲁谷机房");
		}else{
			model.addAttribute("deptName", "");
		}
		return "shjfgl/equipment/equipmentDaZu";
	}
	
	/**
	* 跳转方法(比目鱼机房)
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageBiMuYu.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单表进行跳转操作")
	public String manageBiMuYu(Model model,HttpServletRequest request) throws Exception{
//		String number = request.getParameter("number");
//		String name = request.getParameter("name");
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")||deptName.equals("富丰桥机房")||deptName.equals("亦庄大族机房")||deptName.equals("比目鱼机房")){
			model.addAttribute("deptName", deptName);
		}else if(deptName.equals("小米代维组")){
			model.addAttribute("deptName", "鲁谷机房");
		}else{
			model.addAttribute("deptName", "");
		}
		return "shjfgl/equipment/equipmentBiMuYu";
	}
	
	/**
	* 跳转方法(廊坊机房)
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageLangFang.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单表进行跳转操作")
	public String manageLangFang(Model model,HttpServletRequest request) throws Exception{
		String number = request.getParameter("number");
		String name = request.getParameter("name");
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")||deptName.equals("富丰桥机房")||deptName.equals("亦庄大族机房")){
			model.addAttribute("deptName", deptName);
		}else if(deptName.equals("小米代维组")){
			model.addAttribute("deptName", "鲁谷机房");
		}else{
			model.addAttribute("deptName", "");
		}
		return "shjfgl/equipment/equipmentLangFang";
	}
	
	/**
	* 跳转方法(看丹机房)
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageKanDan.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单表进行跳转操作")
	public String manageKanDan(Model model,HttpServletRequest request) throws Exception{
		String number = request.getParameter("number");
		String name = request.getParameter("name");
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")||deptName.equals("富丰桥机房")||deptName.equals("亦庄大族机房")){
			model.addAttribute("deptName", deptName);
		}else if(deptName.equals("小米代维组")){
			model.addAttribute("deptName", "鲁谷机房");
		}else{
			model.addAttribute("deptName", "");
		}
		return "shjfgl/equipment/equipmentKanDan";
	}
	
	/**
	* 跳转方法(洋桥机房)
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageYangQiao.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单表进行跳转操作")
	public String manageYangqiao(Model model,HttpServletRequest request) throws Exception{
		String number = request.getParameter("number");
		String name = request.getParameter("name");
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")||deptName.equals("富丰桥机房")||deptName.equals("亦庄大族机房")){
			model.addAttribute("deptName", deptName);
		}else if(deptName.equals("小米代维组")){
			model.addAttribute("deptName", "鲁谷机房");
		}else{
			model.addAttribute("deptName", "");
		}
		return "shjfgl/equipment/equipmentYangQiao";
	}
	
	/**
	* 跳转方法(富丰机房)
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageFuFeng.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单表进行跳转操作")
	public String manageFuFeng(Model model,HttpServletRequest request) throws Exception{
		String number = request.getParameter("number");
		String name = request.getParameter("name");
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")||deptName.equals("富丰桥机房")||deptName.equals("亦庄大族机房")){
			model.addAttribute("deptName", deptName);
		}else if(deptName.equals("小米代维组")){
			model.addAttribute("deptName", "鲁谷机房");
		}else{
			model.addAttribute("deptName", "");
		}
		return "shjfgl/equipment/equipmentFuFeng";
	}
	
	/**
	* 跳转方法(鲁谷机房)
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageLuGu.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单表进行跳转操作")
	public String manageLuGu(Model model,HttpServletRequest request) throws Exception{
		String number = request.getParameter("number");
		String name = request.getParameter("name");
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")||deptName.equals("富丰桥机房")||deptName.equals("亦庄大族机房")){
			model.addAttribute("deptName", deptName);
		}else if(deptName.equals("小米代维组")){
			model.addAttribute("deptName", "鲁谷机房");
		}else{
			model.addAttribute("deptName", "");
		}
		return "shjfgl/equipment/equipmentLuGu";
	}
	
	/**
	* 跳转方法(沙河机房)
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageShaHe.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单表进行跳转操作")
	public String manageSheHe(Model model,HttpServletRequest request) throws Exception{
		String number = request.getParameter("number");
		String name = request.getParameter("name");
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")||deptName.equals("富丰桥机房")||deptName.equals("亦庄大族机房")){
			model.addAttribute("deptName", deptName);
		}else if(deptName.equals("小米代维组")){
			model.addAttribute("deptName", "鲁谷机房");
		}else{
			model.addAttribute("deptName", "");
		}
		return "shjfgl/equipment/equipmentShaHe";
	}
	
	/**
	* 跳转方法(兆维机房)
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageZhaoWei.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单表进行跳转操作")
	public String manageZhaoWei(Model model,HttpServletRequest request) throws Exception{
		String number = request.getParameter("number");
		String name = request.getParameter("name");
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")||deptName.equals("富丰桥机房")||deptName.equals("亦庄大族机房")){
			model.addAttribute("deptName", deptName);
		}else if(deptName.equals("小米代维组")){
			model.addAttribute("deptName", "鲁谷机房");
		}else{
			model.addAttribute("deptName", "");
		}
		return "shjfgl/equipment/equipmentZhaoWei";
	}
	
	/**
	* 跳转方法(清华园机房)
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageQingHuaYuan.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单表进行跳转操作")
	public String manageQingHuaYuan(Model model,HttpServletRequest request) throws Exception{
		String number = request.getParameter("number");
		String name = request.getParameter("name");
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")||deptName.equals("富丰桥机房")||deptName.equals("亦庄大族机房")){
			model.addAttribute("deptName", deptName);
		}else if(deptName.equals("小米代维组")){
			model.addAttribute("deptName", "鲁谷机房");
		}else{
			model.addAttribute("deptName", "");
		}
		return "shjfgl/equipment/equipmentQingHuaYuan";
	}
	
	/**
	* 跳转方法(小米代维机房)
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageXiaoMiDaiWei.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单表进行跳转操作")
	public String manageXiaoMiDaiWei(Model model,HttpServletRequest request) throws Exception{
		String number = request.getParameter("number");
		String name = request.getParameter("name");
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		/*if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")||deptName.equals("富丰桥机房")||deptName.equals("亦庄大族机房")){
			model.addAttribute("deptName", deptName);
		}else if(deptName.equals("小米代维组")){
			model.addAttribute("deptName", "鲁谷机房");
		}else{
			model.addAttribute("deptName", "");
		}*/
		return "shjfgl/equipment/equipmentXiaoMiDaiWei";
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
	public  Map<String, Object> deleteByIds(Equipment equipment,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		equipment.setPrimaryKeys(ids.split(","));	
		if(equipmentService.deleteByIds(equipment) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
		}
		return resultMap;
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
		return "shjfgl/equipment/module/equipmentFormModule"; 
	}
	
	/**
	 * @description 弹出对话框方法(大族机房)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadFormDaZu.action")
	public String loadFormDaZu(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "shjfgl/equipment/module/equipmentDaZuFormModule"; 
	}
	
	/**
	 * @description 弹出对话框方法(比目鱼机房)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadFormBiMuYu.action")
	public String loadFormBiMuYu(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "shjfgl/equipment/module/equipmentBiMuYuFormModule"; 
	}
	
	/**
	 * @description 弹出对话框方法(廊坊机房)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadFormLangFang.action")
	public String loadFormLangFang(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "shjfgl/equipment/module/equipmentLangFangFormModule"; 
	}
	/**
	 * @description 弹出对话框方法(看丹机房)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadFormKanDan.action")
	public String loadFormKanDan(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "shjfgl/equipment/module/equipmentKanDanFormModule"; 
	}
	
	/**
	 * @description 弹出对话框方法(洋桥机房)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadFormYangQiao.action")
	public String loadFormYangQiao(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "shjfgl/equipment/module/equipmentYangQiaoFormModule"; 
	}
	
	/**
	 * @description 弹出对话框方法(富丰机房)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadFormFuFeng.action")
	public String loadFormFuFeng(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "shjfgl/equipment/module/equipmentFuFengFormModule"; 
	}
	
	/**
	 * @description 弹出对话框方法(鲁谷机房)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadFormLuGu.action")
	public String loadFormLuGu(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "shjfgl/equipment/module/equipmentLuGuFormModule"; 
	}
	/**
	 * @description 弹出对话框方法(沙河机房)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadFormShaHe.action")
	public String loadFormShaHe(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "shjfgl/equipment/module/equipmentShaHeFormModule"; 
	}
	
	/**
	 * @description 弹出对话框方法(兆维机房)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadFormZhaoWei.action")
	public String loadFormZhaoWei(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "shjfgl/equipment/module/equipmentZhaoWeiFormModule"; 
	}
	
	/**
	 * @description 弹出对话框方法(清华园机房)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadFormQingHuaYuan.action")
	public String loadFormQingHuaYuan(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "shjfgl/equipment/module/equipmentQingHuaYuanFormModule"; 
	}
	/**
	 * @description 弹出对话框方法(小米代维机房)
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadFormXiaoMiDaiWei.action")
	public String loadFormXiaoMiDaiWei(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "shjfgl/equipment/module/equipmentXiaoMiDaiWeiFormModule"; 
	}
	
	
	//TODO 机房平面查询方法
	/**
	 * 分页查询方法
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-12-08 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="manageViewList.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房表",operateFuncNm="get",operateDescribe="分页查询")
	public PageManager manageViewList(Equipment equipment,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(equipment.getOrderBy())) {
			equipment.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = equipmentService.query(equipment, page);
		return page_; 
	}
	
	/**
	 * @description 查看详细方法
	 * @return 
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="equipmentView.action")
	public String equipmentView(Model model,HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		model.addAttribute("id", id);
		return "shjfgl/equipment/module/equipmentView";
	}
	
	/**
	 * @descriptio 客户服务部查看权限
	 * @return 
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="khfwManage.action")
	public String khfwManage(Model model,HttpServletRequest request) throws Exception{
		
		return "shjfgl/equipment/customerView";
	}
	
	/**
	 * @descriptio 客户服务部查看权限
	 * @return 
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="khfwPageManage.action")
	public String khfwPageManage(Model model,HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		Equipment equipment = new Equipment();
		if(!StringUtil.isEmpty(id)){
		equipment.setCompanyId(Long.valueOf(id));
		}
		equipment.setDeleteFlag(0);
		List<Equipment> listEquipment = equipmentService.queryAll(equipment);
		model.addAttribute("listEquipment", listEquipment);
		return "shjfgl/equipment/customerPageView";
	}
	
	 /**
     * @description 导出Excel
     * @param User user 实体类
     * @param PageManager page 分页实体类
     * @throws Exception
     * @author 都业广
     * @version 2014-05-08
     * @throws IOException 
     * @throws NoSuchFieldException 
	 * @throws CustomException 
     */
    @RequestMapping(value = "exportExcel.action")
    public void exportExcel(Equipment equipment,HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchFieldException, CustomException {
        
        //查询数据
        List<Equipment> page_=equipmentService.queryAll(equipment);
        //使用模板时情况
//        ExcuteExcelUtil.setTemplatePath("E://template.xls");  //模板位置,不进行设置的话,默认为C盘根目录
//        ExcuteExcelUtil.exportExcel(page_.getData(), response);  //调用导出功能
        
        //使用自定义标题的情况
        //定义list,添加标题内容
        List<String> lstHead = new ArrayList<String>();
        /*lstHead.add("标题1");
        lstHead.add("标题2");
        lstHead.add("标题3");
        lstHead.add("标题4");
        lstHead.add("标题5");*/
        lstHead.add("设备编号");
        lstHead.add("客户名称");
        lstHead.add("机房区域");
        lstHead.add("机柜编号");
        lstHead.add("机房名称");
        lstHead.add("机柜位置");
        lstHead.add("品牌型号");
        lstHead.add("ETH1 IP.Netmask");
        lstHead.add("交换机对应端口");
        lstHead.add("上架时间");
        lstHead.add("管理网IP");
        lstHead.add("电源（单.双电）");
        lstHead.add("设备功率");
        lstHead.add("U数");
        lstHead.add("设备功能");
        lstHead.add("上联端口");
        lstHead.add("A路电流");
        lstHead.add("B路电流");
        lstHead.add("操作系统");
        lstHead.add("设备配置");
        lstHead.add("资产编号");
        lstHead.add("SN");
        lstHead.add("备注");
        //指定输出类中的哪些属性
        //定义list,添加标题内容
        List<String> lstProp = new ArrayList<String>();
        /*lstProp.add("displayName");
        lstProp.add("loginName");
        lstProp.add("password");
        lstProp.add("entryDate");
        lstProp.add("sex");*/
        lstProp.add("equipmentNumber");
        lstProp.add("clientName");
        lstProp.add("machina");
        lstProp.add("machinaNumber");
        lstProp.add("contact");
        lstProp.add("address");
        lstProp.add("type");
        lstProp.add("netmaskOne");
        lstProp.add("interchangerOne");
        lstProp.add("onlineDate");
        lstProp.add("ip");
        lstProp.add("power");
        lstProp.add("plantPower");
        lstProp.add("uCount");
        lstProp.add("functionn");
        lstProp.add("port");
        lstProp.add("aCurrent");
        lstProp.add("bCurrent");
        lstProp.add("system");
        lstProp.add("device");
        lstProp.add("serialNumber");
        lstProp.add("sn");
        lstProp.add("remark");
        ExcuteExcelUtil.exportExcel(lstHead, lstProp, page_, response);  //调用导出功能
        
    }
}
