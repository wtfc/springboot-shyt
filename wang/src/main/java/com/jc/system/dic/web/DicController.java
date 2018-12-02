package com.jc.system.dic.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.web.BaseController;
import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.domain.DicType;
import com.jc.system.dic.impl.DicManagerImpl;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;

@Controller
@RequestMapping(value = "/dic")
public class DicController extends BaseController {

	private IDicManager dicManager = new DicManagerImpl();

	public DicController() {
	}

	/**
	 * @description 跳转方法
	 * @param
	 * @return String 跳转的路径
	 * @author
	 */
	@RequestMapping(value = "manage.action")
	public String manage(Model model,HttpServletRequest request) throws Exception {
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			if(userInfo.getIsAdmin() == 1 || userInfo.getIsSystemAdmin()){
				dicManager.getDicsByTypeCode("test", "parent");
				Map<String, Object> map = new HashMap<String, Object>();
				String token = getToken(request);
				map.put(GlobalContext.SESSION_TOKEN, token);
				model.addAttribute("data", map);
				return "dic/manage";
			} else {
				return "error/unauthorized";
			}
		}
		return "error/unauthorized";
	}

	@RequestMapping(value = "getDics.action")
	@ResponseBody
	public List<Dic> getDics(String typeCode) {
		return dicManager.getDicsByTypeCode(typeCode);
	}

	@RequestMapping(value = "getDicsAll.action")
	@ResponseBody
	public List<Dic> getDicsAll(String typeCode) {
		return dicManager.getDicsByTypeCode(typeCode, false);
	}

	@RequestMapping(value = "getDicValue.action")
	@ResponseBody
	public Dic getDicValue(String typeCode, String dicCode) {
		return dicManager.getDic(typeCode, dicCode);
	}

	@RequestMapping(value = "getDicTypes.action")
	@ResponseBody
	public List<DicType> getDicTypes() {
		return dicManager.getDicTypes();
	}
	
	/**
	* @description 跳转方法（数据字典左侧树）
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-05-06 
	*/
	@RequestMapping(value="getDicTreeTypes.action")
	public String getDicTreeTypes(Model model,String typeCode) throws Exception{
		model.addAttribute("dicTreeTypeList", dicManager.getDicTypes());
		return "dic/dicTypeList";
	}
	
	/**
	* @description 跳转方法（数据字典右侧节点列表）
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-05-06 
	*/
	@RequestMapping(value="getDicTreeInfo.action")
	public String getDicTreeInfo(Model model,String typeCode) throws Exception{
		List<Dic> diclist =  dicManager.getDicsByTypeCode(typeCode, false);
		model.addAttribute("dicNodeInfo", diclist);
		model.addAttribute("dicNodeInfoSize", diclist.size());
		return "dic/dicNodeList";
	}
	
	/**
	* @description 数据字典多级联动查询方法
	* @return JSON 字典数据
	* @throws Exception
	* @author	chz
	* @version  2014-05-26 
	*/
	@RequestMapping(value="getDicJSONInfo.action")
	public void getDicJSONInfo(String typeCode,HttpServletResponse response) throws Exception{
		List<Dic> diclist =  dicManager.getDicsByTypeCode(typeCode, true);
		response.setContentType("text/html; charset=UTF-8");
		JSONArray json = new JSONArray();
		json = JSONArray.fromObject(diclist);
		response.getWriter().print(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}

	@RequestMapping(value = "addNewDic.action")
	@ResponseBody
	public Dic addNewType(Dic dic) throws CustomException {
		return dicManager.addNewDic(dic);
	}
	
	@RequestMapping(value = "addNewDicList.action")
	@ResponseBody
	@ActionLog(operateModelNm="字典设置",operateFuncNm="addNewDicList",operateDescribe="对字典设置进行批量保存")
	public Map<String,Object> addNewDicList(String parentid,HttpServletRequest request) throws CustomException {
		// 验证token
		Map<String,Object> resultMap = validateToken(request);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		if(!"false".equals(resultMap.get("success"))){
			String jsonStr = request.getParameter("mydata");
			JSONArray jsonarray = JSONArray.fromObject(jsonStr); 
			List<Dic> dicList = (List<Dic>)JSONArray.toCollection(jsonarray, Dic.class); 
			dicManager.addNewDicList(dicList);
			resultMap.put("success", "true");
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	@RequestMapping(value = "addNewType.action")
	@ResponseBody
	public DicType addNewType(DicType type) throws CustomException {
		return dicManager.addNewDicType(type);
	}

	@RequestMapping(value = "deleteDic.action")
	@ResponseBody
	public Dic deleteDic(Dic dic) throws CustomException {
		return dicManager.removeDic(dic);
	}

	@RequestMapping(value = "deleteType.action")
	@ResponseBody
	public DicType deleteType(DicType type) throws CustomException{
		return dicManager.removeDicType(type);
	}

	@RequestMapping(value = "updateDic.action")
	@ResponseBody
	public Dic updateDic(Dic dic) throws CustomException{
		return dicManager.updateDic(dic);
	}

	@RequestMapping(value = "updateDicType.action")
	@ResponseBody
	public DicType updateDicType(DicType type) throws CustomException{
		return dicManager.updateType(type);
	}

	@RequestMapping(value = "changeDicToType.action")
	@ResponseBody
	public Dic changeDicToType(Dic dic) throws CustomException{
		return dicManager.changeDicToType(dic);
	}

	@RequestMapping(value = "updateOrderFlag.action")
	@ResponseBody
	public Dic updateOrderFlag(Dic dic) throws CustomException{
		return dicManager.updateDic(dic);
	}
	
	/**
	 * @description 跳转方法
	 * @param
	 * @return String 跳转的路径
	 * @author
	 */
	@RequestMapping(value = "manageInfo.action")
	public String manageInfo(Model model,HttpServletRequest request) throws Exception {
		User userInfo = SystemSecurityUtils.getUser();
		if(userInfo != null){
			if(userInfo.getIsAdmin() == 1 || userInfo.getIsSystemAdmin()){
				dicManager.getDicsByTypeCode("test", "parent");
				Map<String, Object> map = new HashMap<String, Object>();
				String token = getToken(request);
				map.put(GlobalContext.SESSION_TOKEN, token);
				model.addAttribute("data", map);
				return "dic/dicTypeManage";
			} else {
				return "error/unauthorized";
			}
		}
		return "error/unauthorized";
	}
	
	/**
	* @description 跳转方法（数据字典类型左侧树）
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-05-06 
	*/
	@RequestMapping(value="getDicInfoTreeTypes.action")
	public String getDicInfoTreeTypes(Model model,String typeCode) throws Exception{
		model.addAttribute("dicTreeTypeList", dicManager.getDicTypes());
		return "dic/dicInfoTypeList";
	}
	
	/**
	* @description 跳转方法（数据字典右侧表单）
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-11-26 
	*/
	@RequestMapping(value="getDicInfo.action")
	public String getDicInfo(Model model,Long dicId,HttpServletRequest request) throws Exception{
		Dic dic = dicManager.getDic(dicId);
		String token = getToken(request);
		model.addAttribute(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("dicInfo", dic);
		return "dic/dicInfo";
	}

}