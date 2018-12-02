package com.jc.oa.shyt.web;

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
import com.jc.oa.po.personalBox.domain.Note;
import com.jc.oa.shyt.domain.Visit;
import com.jc.oa.shyt.service.IVisitService;
import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.portal.util.PortalUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;

@Controller
@RequestMapping(value="/shyt/visit")
public class VisitController extends BaseController{
	
	@Autowired
	private IVisitService visitService;
	
	public VisitController(){}
	
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
	@ActionLog(operateModelNm="客户回访信息表",operateFuncNm="save",operateDescribe="对客户回访信息表进行发起操作")
	public Map<String,Object> save(Visit visit,BindingResult result,
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
			visitService.saveResource(visit);
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
	@ActionLog(operateModelNm="客户回访信息表",operateFuncNm="update",operateDescribe="对客户回访信息表进行更新操作")
	public Map<String, Object> update(Visit visit, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = visitService.updateResource(visit);
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
	@ActionLog(operateModelNm="客户回访信息表",operateFuncNm="get",operateDescribe="对客户回访信息表进行单条查询操作")
	public Visit get(Visit visit,HttpServletRequest request) throws Exception{
		return visitService.get(visit);
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
	@ActionLog(operateModelNm="客户回访信息表",operateFuncNm="get",operateDescribe="客户回访信息表分页查询")
	public PageManager manageList(Visit visit,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		
		//默认排序
		if(StringUtil.isEmpty(visit.getOrderBy())) {
			visit.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = visitService.queryForPermission(visit, page);
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
	@ActionLog(operateModelNm="客户回访信息表",operateFuncNm="manage",operateDescribe="对客户回访信息表进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "shyt/visit/visit";
	}
	
	/**
	* 跳转方法 未解决问题跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="nomanage.action")
	@ActionLog(operateModelNm="客户回访信息表",operateFuncNm="nomanage",operateDescribe="对客户回访信息表未解决问题跳转方法进行跳转操作")
	public String nomanage(Model model,HttpServletRequest request) throws Exception{
		
		return "shyt/visit/novisit";
	}
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manage2.action")
	@ActionLog(operateModelNm="客户回访信息表",operateFuncNm="manage",operateDescribe="客户回访信息操作")
	public String manage2(Model model,HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		Visit visit = new Visit();
		visit.setCustomerId(Integer.valueOf(id));
		visit.setDeleteFlag(0);
		List<Visit> visits = visitService.queryAll(visit);
		model.addAttribute("visits", visits);
		return "shyt/visit/visitView";
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
	@ActionLog(operateModelNm="客户回访信息表",operateFuncNm="deleteByIds",operateDescribe="对客户回访信息表进行删除")
	public  Map<String, Object> deleteByIds(Visit visit,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		visit.setPrimaryKeys(ids.split(","));	
		if(visitService.deleteByIds(visit) != 0){
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
	@ActionLog(operateModelNm="客户回访信息表",operateFuncNm="loadForm",operateDescribe="对客户回访信息表跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			model.addAttribute("oldId", id);
		}else{
			String token = getToken(request);
			map.put(GlobalContext.SESSION_TOKEN, token);
			model.addAttribute("data", map);
		}
		return "shyt/visit/visitFrom";
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
		return "shyt/visit/visitPortal";
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
		Visit note = new Visit();
		note.setDeleteFlag(0);
		note.addOrderByFieldDesc("t.MODIFY_DATE");
		note.setCreateUser(SystemSecurityUtils.getUser().getId());
		note.setVisitIsReturn("否");
		List<Visit> noteList =  visitService.queryAll(note);
		result.put("notelist", noteList);
		result.put("dataRows",dataRows);
		return result;
	}

}
