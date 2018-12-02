package com.jc.oa.network.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.CustomException;
import com.jc.system.SystemListener;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.portal.util.PortalUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;
import com.jc.oa.network.domain.ToaNetworkShift;
import com.jc.oa.network.service.IToaNetworkShiftService;
import com.jc.oa.shyt.domain.Visit;
/**
 * @author mrb
 * @version 交接班记录表
*/
@Controller
@RequestMapping(value="/network/toaNetworkShift")
public class ToaNetworkShiftController extends BaseController{
	
	@Autowired 
	private IToaNetworkShiftService toaNetworkShiftService; 
	
	public ToaNetworkShiftController(){
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
	@ActionLog(operateModelNm="交接班记录表",operateFuncNm="save",operateDescribe="对交接班记录表进行发起操作")
	public Map<String,Object> save(ToaNetworkShift toaNetworkShift,BindingResult result,
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
			toaNetworkShiftService.save(toaNetworkShift);
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
	@ActionLog(operateModelNm="交接班记录表",operateFuncNm="update",operateDescribe="对交接班记录表进行更新操作")
	public Map<String, Object> update(ToaNetworkShift toaNetworkShift, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaNetworkShiftService.update(toaNetworkShift);
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
	@ActionLog(operateModelNm="交接班记录表",operateFuncNm="get",operateDescribe="对交接班记录表进行单条查询操作")
	public ToaNetworkShift get(ToaNetworkShift toaNetworkShift,HttpServletRequest request) throws Exception{
		return toaNetworkShiftService.get(toaNetworkShift);
	}
	
	/**
	 * 分页查询方法
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @throws CustomException 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="交接班记录表",operateFuncNm="get",operateDescribe="交接班记录表分页查询")
	public PageManager manageList(ToaNetworkShift toaNetworkShift,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		
		//默认排序
		if(StringUtil.isEmpty(toaNetworkShift.getOrderBy())) {
			toaNetworkShift.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = toaNetworkShiftService.query(toaNetworkShift, page);
		//PageManager page_ = toaNetworkShiftService.queryForPermission(toaNetworkShift, page);
		return page_; 
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="交接班记录表",operateFuncNm="manage",operateDescribe="对交接班记录表进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "network/toaNetworkShift/toaNetworkShift";
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
	@ActionLog(operateModelNm="交接班记录表表",operateFuncNm="deleteByIds",operateDescribe="对交接班记录表表进行删除")
	public  Map<String, Object> deleteByIds(ToaNetworkShift toaNetworkShift,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaNetworkShift.setPrimaryKeys(ids.split(","));	
		if(toaNetworkShiftService.deleteByIds(toaNetworkShift) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
		}
		return resultMap;
	}

	/**
	 * @description 新增与修改方法跳转页面
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadForm.action")
	@ActionLog(operateModelNm="交接班记录表表",operateFuncNm="loadForm",operateDescribe="对交接班记录表表跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			model.addAttribute("Id", id);
		}else{
			String name = SystemSecurityUtils.getUser().getDisplayName();
			model.addAttribute("name", name);
		}
		return "network/toaNetworkShift/toaNetworkShiftForm";
	}

	/**
	 * 交接方法
	 * @param TimeSet timeSet 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="state.action")
	@ResponseBody
	@ActionLog(operateModelNm="交接班记录表表",operateFuncNm="state",operateDescribe="对交接班记录表表进行交接")
	public  Map<String, Object> state(ToaNetworkShift toaNetworkShift,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaNetworkShift.setPrimaryKeys(ids.split(","));	
		if(toaNetworkShiftService.state(toaNetworkShift) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_146"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_147"));
		}
		return resultMap;
	}
	
	/**
	 * @description 新增与修改方法跳转页面
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="manageState.action")
	@ActionLog(operateModelNm="交接班记录表表",operateFuncNm="loadForm",operateDescribe="对交接班记录表表跳转")
	public String manageState(Model model,HttpServletRequest request) throws Exception{
		String name = SystemSecurityUtils.getUser().getDisplayName();
		model.addAttribute("name", name);
		return "network/toaNetworkShift/stateShift";
	}
	
	/**
	* @description 门户便签组件数据
	* @return String 跳转的路径
	* @throws Exception
	* @author	池海洲
	* @version  2014-12-23 
	*/
	@RequestMapping(value="boxPortalData.action")
	public String boxPortalData(Model model,HttpServletRequest request) throws Exception{
		//门户引用类固定代码
		model = PortalUtils.getPortalParameter(model, request);
		return "network/toaNetworkShift/shiftPortal";
	}
	
	/**
	* @description 门户便签组件数据
	* @return Map 数据集合
	* @throws Exception
	* @version  2014-12-23 
	*/
	@RequestMapping(value="getBoxData.action")
	@ResponseBody
	public Map<String, Object> getBoxData(Long columnId,String funViewType,int dataRows) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		ToaNetworkShift note = new ToaNetworkShift();
		note.setDeleteFlag(0);
		note.addOrderByFieldDesc("t.MODIFY_DATE");
		List<ToaNetworkShift> noteList =  toaNetworkShiftService.queryAll(note);
		result.put("notelist", noteList);
		result.put("dataRows",dataRows);
		return result;
	}
}
