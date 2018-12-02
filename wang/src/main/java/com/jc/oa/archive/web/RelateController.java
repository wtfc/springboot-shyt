package com.jc.oa.archive.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.archive.domain.Document;
import com.jc.oa.archive.domain.Relate;
import com.jc.oa.archive.domain.validator.RelateValidator;
import com.jc.oa.archive.service.IAudithisService;
import com.jc.oa.archive.service.IDocumentService;
import com.jc.oa.archive.service.IRelateService;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;


/**
 * @title  GOA2.0源代码
 * @description  controller类
 * @author 
 * @version  2014-06-05
 */
 
@Controller
@RequestMapping(value="/archive/relate")
public class RelateController extends BaseController{
	
	@Autowired
	private IRelateService relateService;

	@Autowired
	private IAudithisService audithisService;

	@Autowired
	private IDocumentService documentService;
	
	@org.springframework.web.bind.annotation.InitBinder("relate")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new RelateValidator()); 
    }
	
	public RelateController() {
	}

	/**
	 * 分页查询方法
	 * @param Relate relate 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_文档管理_文档关联表",operateFuncNm="manageList",operateDescribe="对OA_文档管理_文档关联表进行查询操作")
	public PageManager manageList(Relate relate,PageManager page,HttpServletRequest request){
		PageManager page_ = null;
		try{
			relate.setOrderBy("t.CREATE_DATE desc");
			page_ = relateService.query(relate, page);
		}catch(Exception e){
			System.out.print(e);
		}
		return page_;
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-06-05 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="OA_文档管理_文档关联表",operateFuncNm="manage",operateDescribe="对OA_文档管理_文档关联表进行跳转操作")
	public String manage(HttpServletRequest request) throws Exception{
		return "archive/relate/relate1"; 
	}

/**
	 * 删除方法
	 * @param Relate relate 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_文档管理_文档关联表",operateFuncNm="deleteByIds",operateDescribe="对OA_文档管理_文档关联表进行删除")
	public Map<String, Object> deleteByIds(Relate relate,String ids, HttpServletRequest request) throws Exception{

		Map<String, Object> resultMap = new HashMap<String, Object>();
		relate.setPrimaryKeys(ids.split(","));
		relate.setModifyDate(new Date());
		relate.setModifyUser(SystemSecurityUtils.getUser().getId());
		Relate rel = new Relate();
		rel.setId(Long.valueOf(ids));
		rel = relateService.get(rel);
		if(relateService.delete(relate) > 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		}
		audithisService.audithis(request, rel.getId(),
				rel.getDmName(),
				Constants.ARC_AUDITHIS_DATATYPE_DOC,
				Constants.ARC_AUDITHIS_RELATE, "删除关联文档");
		return resultMap;
	}

	/**
	 * 保存方法
	 * @param Relate relate 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_文档管理_文档关联表",operateFuncNm="save",operateDescribe="对OA_文档管理_文档关联表进行新增操作")
	public Map<String,Object> save(@Valid Relate relate,BindingResult result,
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
			relateService.save(relate);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param Relate relate 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-06-05 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_文档管理_文档关联表",operateFuncNm="update",operateDescribe="对OA_文档管理_文档关联表进行更新操作")
	public Map<String, Object> update(Relate relate, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = relateService.update(relate);
		if (flag == 1) {
			resultMap.put("success", "true");
		} else {
			resultMap.put("success", "false");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,
					MessageUtils.getMessage("JC_OA_COMMON_014"));
		}
		return resultMap;
	}

	/**
	 * 获取单条记录方法
	 * @param Relate relate 实体类
	 * @return Relate 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-06-05
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_文档管理_文档关联表",operateFuncNm="get",operateDescribe="对OA_文档管理_文档关联表进行单条查询操作")
	public Relate get(Relate relate,HttpServletRequest request) throws Exception{
		return relateService.get(relate);
	}
	
	/**
	 * 根据选择的文档批量保存关联关系方法
	 * @param Relate relate 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-07-01
	 */
	@RequestMapping(value="saveByDocumentIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="OA_文档管理_文档关联表",operateFuncNm="saveByDocumentIds",operateDescribe="根据选择的文档批量保存关联关系方法")
	public Map<String, Object> saveByDocumentIds(HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long documentId = Long.parseLong(request.getParameter("documentId"));//文档id
		String documentIds = request.getParameter("documentIds");//关联的文档id
		// 保存元素
		if (relateService.saveByDocumentIds(documentId,documentIds) > 0) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
		}
		Document doc = new Document();
		doc.setId(documentId);
		doc = documentService.get(doc);
		audithisService.audithis(request, documentId,
				doc.getDmName(),
				Constants.ARC_AUDITHIS_DATATYPE_DOC,
				Constants.ARC_AUDITHIS_RELATE, "添加关联文档");
		return resultMap;
	}
}