package com.jc.shjfgl.machine.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
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
import com.jc.shjfgl.machine.domain.Equipment;
import com.jc.shjfgl.machine.domain.EquipmentInOut;
import com.jc.shjfgl.machine.domain.Exchange;
import com.jc.shjfgl.machine.service.IEquipmentInOutService;
import com.jc.shjfgl.machine.service.IExchangeService;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.content.domain.AttachBusiness;
import com.jc.system.content.service.IAttachBusinessService;
import com.jc.system.content.service.IUploadService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;

@Controller
@RequestMapping(value="/machine/equipmentInOut")
public class EquipmentInOutController extends BaseController{

	@Autowired
	private IEquipmentInOutService equipmentInOutService;
	
	@Autowired
	private IExchangeService exchangeService;
	
	@Autowired
	private IAttachBusinessService attachBusinessService;
	
	@Autowired
	private IUploadService uploadService;
	
	public EquipmentInOutController(){};
	
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
	public Map<String,Object> save(EquipmentInOut equipmentInOut,BindingResult result,
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
			if(equipmentInOut.getId()!=null){
				equipmentInOut.setId(null);
			}
			equipmentInOutService.save(equipmentInOut);
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_WORKFLOW_001"));
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
	public Map<String, Object> update(EquipmentInOut equipmentInOut, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = equipmentInOutService.update(equipmentInOut);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_WORKFLOW_001"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	/**
	* 修改方法
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="updateGdcl.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单表",operateFuncNm="update",operateDescribe="对机房工单处理进行更新操作")
	public Map<String, Object> updateGdcl(EquipmentInOut equipmentInOut, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		equipmentInOut.setStatus(2);
		Integer flag = equipmentInOutService.update(equipmentInOut);
		//删除页面中上传中删除的附件
		if(!StringUtil.isEmpty(equipmentInOut.getDelattachIds())){
			uploadService.deleteFileByIds(equipmentInOut.getDelattachIds());
		}
		//保存附件
		List<Long> fileIds = equipmentInOut.getFileids();
		if(fileIds != null && fileIds.size() >0){
			//查询所需修改的图片信息
			AttachBusiness attachBusines = new AttachBusiness();
			attachBusines.setBusinessId(equipmentInOut.getId());
			attachBusines.setBusinessTable("toa_shjfgl_equipment_inout");
			List<AttachBusiness> list = attachBusinessService.queryAll(attachBusines);
			//删除所需修改的图片信息及图片文件
			AttachBusiness delAttachBusiness = new AttachBusiness();
			String[] keys = new String[list.size()];
			if(list != null && list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					keys[i] = list.get(i).getId().toString();
				}
				delAttachBusiness.setPrimaryKeys(keys);
				attachBusinessService.delete(delAttachBusiness, false);
			}
			for (int i = 0; i < fileIds.size(); i++) {
				AttachBusiness attachBusiness = new AttachBusiness();
				attachBusiness.setAttachId(fileIds.get(i));
				attachBusiness.setBusinessId(equipmentInOut.getId());
				attachBusiness.setBusinessTable("toa_shjfgl_equipment_inout");
				attachBusiness.setBusinessSource("0");
				attachBusinessService.save(attachBusiness);
			}
		}
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_WORKFLOW_001"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	/**
	* 修改方法
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="updateKfhf.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房工单表",operateFuncNm="update",operateDescribe="对机房工单客服回访进行更新操作")
	public Map<String, Object> updateKfhf(EquipmentInOut equipmentInOut, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		equipmentInOut.setStatus(4);
		Integer flag = equipmentInOutService.update(equipmentInOut);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_WORKFLOW_001"));
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
	public EquipmentInOut get(EquipmentInOut equipmentInOut,HttpServletRequest request) throws Exception{
		return equipmentInOutService.get(equipmentInOut);
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
	public PageManager manageList(EquipmentInOut equipmentInOut,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		//默认排序
		if(StringUtil.isEmpty(equipmentInOut.getOrderBy())) {
			equipmentInOut.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = equipmentInOutService.query(equipmentInOut, page);
		return page_;
	}
	/**
	 * 工单作废方法
	 * @param TimeSet timeSet 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-12-08
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房进出工单表",operateFuncNm="deleteByIds",operateDescribe="工单作废方法")
	public  Map<String, Object> deleteByIds(EquipmentInOut equipmentInOut,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		equipmentInOut.setPrimaryKeys(ids.split(","));	
		if(equipmentInOutService.deleteByIds(equipmentInOut) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_130"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_131"));
		}
		return resultMap;
	}
	/**
	 * 接单方法
	 * @param TimeSet timeSet 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-12-08
	 */
	@RequestMapping(value="deleteStatus.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房进出工单表",operateFuncNm="deleteByIds",operateDescribe="接单方法")
	public  Map<String, Object> deleteStatus(EquipmentInOut equipmentInOut,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		equipmentInOut.setPrimaryKeys(ids.split(","));	
		if(equipmentInOutService.deleteStatus(equipmentInOut) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_132"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_133"));
		}
		return resultMap;
	}
	/**
	 * 操作方法
	 * @param TimeSet timeSet 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-12-08
	 */
	@RequestMapping(value="operate.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房进出工单表",operateFuncNm="deleteByIds",operateDescribe="接单方法")
	public  Map<String, Object> operate(EquipmentInOut equipmentInOut,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		equipmentInOut.setPrimaryKeys(ids.split(","));	
		if(equipmentInOutService.operate(equipmentInOut) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_WORKFLOW_001"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_WORKFLOW_002"));
		}
		return resultMap;
	}
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单详细表进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		String id=request.getParameter("id");
		String status=request.getParameter("status");
		String name = SystemSecurityUtils.getUser().getDisplayName();
		
		if(!StringUtil.isEmpty(id)){
			Exchange exchange = new Exchange();
			exchange.setEquipmentId(id);
			exchange.setExtStr1("toa_shjfgl_equipment_inout");
			List<Exchange> exchangeList = exchangeService.queryAll(exchange);
			model.addAttribute("id", id);
			model.addAttribute("idd", id);
			model.addAttribute("status", status);
			model.addAttribute("exchangeList",exchangeList);
			model.addAttribute("talkName",name);
			model.addAttribute("userName",name);
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			String token = getToken(request);
			map.put(GlobalContext.SESSION_TOKEN, token);
			model.addAttribute("data", map);
			model.addAttribute("userName",name);
			
		}
		return "shjfgl/equipmentInOut/equipmentInOut";
	}
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageRead.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单查询表进行跳转操作")
	public String manageRead(Model model,HttpServletRequest request) throws Exception{
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")){
			model.addAttribute("deptName", deptName);
		}
		return "shjfgl/equipmentInOut/equipmentInOutRead";
	}
	/**
	* 跳转方法
	* @return String 跳转的路径技术反馈
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="managejsfk.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单待接单表进行跳转操作")
	public String managejsfk(Model model,HttpServletRequest request) throws Exception{
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")){
			model.addAttribute("deptName", deptName);
		}
		return "shjfgl/equipmentInOut/equipmentInOut_jsfk";
	}
	/**
	* 跳转方法
	* @return String 跳转的客服回单
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="managekfhd.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单已接单表进行跳转操作")
	public String managekfhd(Model model,HttpServletRequest request) throws Exception{
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")){
			model.addAttribute("deptName", deptName);
		}
		return "shjfgl/equipmentInOut/equipmentInOut_kfhd";
	}
	
	/**
	* 跳转方法
	* @return String 跳转的客服回单
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageczz.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单已接单表进行跳转操作")
	public String manageccz(Model model,HttpServletRequest request) throws Exception{
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")){
			model.addAttribute("deptName", deptName);
		}
		return "shjfgl/equipmentInOut/equipmentInOut_czz";
	}
	/**
	* 跳转方法
	* @return String 跳转的客服回单
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="managehd.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对工单已接单表进行跳转操作")
	public String managehd(Model model,HttpServletRequest request) throws Exception{
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")){
			model.addAttribute("deptName", deptName);
		}
		return "shjfgl/equipmentInOut/equipmentInOuthd";
	}


	//TODO***************************人员进出机房Start*****************************/
	/**
	* 人员入室申请表跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="managePeople.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对人员进出工单下单表进行跳转操作")
	public String managePeople(Model model,HttpServletRequest request) throws Exception{
		String id=request.getParameter("id");
		String status=request.getParameter("status");
		String name = SystemSecurityUtils.getUser().getDisplayName();
		
		if(!StringUtil.isEmpty(id)){
			Exchange exchange = new Exchange();
			exchange.setEquipmentId(id);
			exchange.setExtStr1("toa_shjfgl_equipment_inout");
			List<Exchange> exchangeList = exchangeService.queryAll(exchange);
			model.addAttribute("id", id);
			model.addAttribute("idd", id);
			model.addAttribute("status", status);
			model.addAttribute("exchangeList",exchangeList);
			model.addAttribute("talkName",name);
			model.addAttribute("userName",name);
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			String token = getToken(request);
			map.put(GlobalContext.SESSION_TOKEN, token);
			model.addAttribute("data", map);
			model.addAttribute("userName",name);
			
		}
		return "shjfgl/equipmentInOutPeople/peopleInOut";
	}
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageReadPeople.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对人员进出工单工单查询表进行跳转操作")
	public String manageReadPeople(Model model,HttpServletRequest request) throws Exception{
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")){
			model.addAttribute("deptName", deptName);
		}
		return "shjfgl/equipmentInOutPeople/peopleInOutRead";
	}
	/**
	* 跳转方法
	* @return String 跳转的路径技术反馈
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="managejsfkPeople.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对人员人员工单待接单表进行跳转操作")
	public String managejsfkPeople(Model model,HttpServletRequest request) throws Exception{
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")){
			model.addAttribute("deptName", deptName);
		}
		return "shjfgl/equipmentInOutPeople/peopleInOut_jsfk";
	}
	/**
	* 跳转方法
	* @return String 跳转的客服回单
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="managekfhdPeople.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对人员工单已接单进行跳转操作")
	public String managekfhdPeople(Model model,HttpServletRequest request) throws Exception{
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")){
			model.addAttribute("deptName", deptName);
		}
		return "shjfgl/equipmentInOutPeople/peopleInOut_kfhd";
	}
	/**
	* 跳转方法
	* @return String 跳转的客服回单
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="managePeoplehd.action")
	@ActionLog(operateModelNm="进出工单表",operateFuncNm="manage",operateDescribe="对人员工单客服回单进行跳转操作")
	public String managePeoplehd(Model model,HttpServletRequest request) throws Exception{
		String deptName = SystemSecurityUtils.getUser().getDeptName();
		if(deptName.equals("鲁谷机房")||deptName.equals("看丹桥机房")||deptName.equals("兆维机房")||deptName.equals("廊坊机房")||deptName.equals("清华园机房")||deptName.equals("沙河机房")||deptName.equals("洋桥机房")){
			model.addAttribute("deptName", deptName);
		}
		return "shjfgl/equipmentInOutPeople/peopleInOuthd";
	}
}
