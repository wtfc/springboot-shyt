package com.jc.oa.po.personalBox.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.web.BaseController;
import com.jc.oa.po.anno.domain.validator.AnnoValidator;
import com.jc.oa.po.personalBox.domain.Note;
import com.jc.oa.po.personalBox.service.IPersonalBoxService;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.portal.util.PortalUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;


/**
 * @title 个人办公-工具箱
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-11-18
 */
 
@Controller
@RequestMapping(value="/po/personalBox")
public class personalBoxController extends BaseController{
	
	@Autowired
	private IPersonalBoxService personalBoxService;
	
	@org.springframework.web.bind.annotation.InitBinder("personalBox")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new AnnoValidator()); 
    }
	
	public personalBoxController() {
	}

	/**
	* @description 便签跳转方法
	* @param HttpServletRequest request
	* @return String 跳转的路径
	* @throws Exception
	* @author 
	* @version  2014-11-18 
	*/
	@RequestMapping(value="getNote.action")
	@ActionLog(operateModelNm="个人办公-工具箱",operateFuncNm="getNote",operateDescribe="跳转到便签")
	public String getNote(HttpServletRequest request) throws Exception{
		return "po/personalBox/note"; 
	}
	
	/**
	 * 加载所有便签方法
	 * @param Note note 实体类
	 * @return List
	 * @throws Exception
	 * @author 
	 * @version  2014-11-21
	 */
	@RequestMapping(value="noteManagerList.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_个人办公_工具箱",operateFuncNm="deleteNote",operateDescribe="加载所有的便签")
	public Map<String,Object> noteManagerList(Note note,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		note.setDeleteFlag(0);
		note.setCreateUser(SystemSecurityUtils.getUser().getId());
		List<Note> noteList =  personalBoxService.queryAll(note);
		if(noteList != null){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put("list", noteList);
			return resultMap;
		}else{
			return null;
		}

	}
	
	/**
	 * @description 保存便签方法
	 * @param Note note 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author  
	 * @version 2014-03-18
	 */
	@RequestMapping(value = "saveNote.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公-工具箱",operateFuncNm="saveNote.action",operateDescribe="保存便签") 
	public Map<String, Object> saveNote(@Valid Note note, BindingResult result,HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try{
			if(personalBoxService.save(note) == 1){
			   resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			   resultMap.put("note", note);
			} 
		}catch(Exception e){
		    e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * @description 修改便签方法
	 * @param Note note 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 刘锡来
	 * @version 2014-12-01
	 */
	@RequestMapping(value = "updateNote.action")
	@ResponseBody
	@ActionLog(operateModelNm="个人办公-工具箱",operateFuncNm="updateNote.action",operateDescribe="修改便签") 
	public Map<String, Object> updateNote(@Valid Note note, BindingResult result,HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try{
			note.setModifyDateNew(new Date());
			if(personalBoxService.update(note) == 1){
			   resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			   resultMap.put("note", note);
			} 
		}catch(Exception e){
		    e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * 删除便签方法
	 * @param Note note 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 
	 * @version  2014-11-21
	 */
	@RequestMapping(value="deleteNote.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_个人办公_工具箱",operateFuncNm="deleteNote",operateDescribe="对OA_个人办公_工具箱_便签进行删除")
	public Map<String,Object> deleteByIds(Note note,String ids,HttpServletRequest request) throws Exception{
		String[] id = {ids};
		note.setPrimaryKeys(id);
		if(personalBoxService.delete(note) == 1){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
			return resultMap;
		}
		return null;
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
		return "po/personalBox/boxPortal";
	}
	
	/**
	* @description 门户便签组件数据
	* @return Map 数据集合
	* @throws Exception
	* @author	池海洲
	* @version  2014-12-23 
	*/
	@RequestMapping(value="getBoxData.action")
	@ResponseBody
	public Map<String, Object> getBoxData(Long columnId,String funViewType,int dataRows) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		Note note = new Note();
		note.setDeleteFlag(0);
		note.addOrderByFieldDesc("t.MODIFY_DATE");
		note.setCreateUser(SystemSecurityUtils.getUser().getId());
		List<Note> noteList =  personalBoxService.queryAll(note);
		result.put("notelist", noteList);
		result.put("dataRows",dataRows);
		return result;
	}
	
	/**
	* @description 万年历跳转方法
	* @param HttpServletRequest request
	* @return String 跳转的路径
	* @throws Exception
	* @author 
	* @version  2014-11-18 
	*/
	@RequestMapping(value="getCalendar.action")
	@ActionLog(operateModelNm="个人办公-工具箱",operateFuncNm="getCalculator",operateDescribe="跳转到万年历")
	public String getCalendar(HttpServletRequest request) throws Exception{
		return "po/personalBox/calendar"; 
	}
	
	/**
	* @description 计算器跳转方法
	* @param HttpServletRequest request
	* @return String 跳转的路径
	* @throws Exception
	* @author 
	* @version  2014-11-18 
	*/
	@RequestMapping(value="getCalculator.action")
	@ActionLog(operateModelNm="个人办公-工具箱",operateFuncNm="getCalculator",operateDescribe="跳转到计算器")
	public String getCalculator(HttpServletRequest request) throws Exception{
		return "po/personalBox/calculator"; 
	}
	
	/**
	* @description 个税计算器跳转方法
	* @param HttpServletRequest request
	* @return String 跳转的路径
	* @throws Exception
	* @version  2014-11-18 
	*/
	@RequestMapping(value="getIncomeTaxCalculator.action")
	@ActionLog(operateModelNm="个人办公-工具箱",operateFuncNm="getIncomeTaxCalculator",operateDescribe="跳转到个税计算器")
	public String getIncomeTaxCalculator(HttpServletRequest request) throws Exception{
		return "po/personalBox/incomeTaxCalculator"; 
	}
	
	/**
	* @description 公积金计算器跳转方法
	* @param HttpServletRequest request
	* @return String 跳转的路径
	* @throws Exception
	* @version  2014-11-19
	*/
	@RequestMapping(value="accumulationFundSkip.action")
	@ActionLog(operateModelNm="个人办公-工具箱",operateFuncNm="accumulationFundSkip",operateDescribe="公积金计算器页面跳转")
	public String accumulationFundSkip(HttpServletRequest request) throws Exception{
		return "po/personalBox/accumulationFund"; 
	}

}