package com.jc.oa.po.anno.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.po.anno.domain.Anno;
import com.jc.oa.po.anno.domain.validator.AnnoValidator;
import com.jc.oa.po.anno.service.IAnnoService;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.ActionLog;
import com.jc.system.security.util.UserUtils;


/**
 * @title 个人办公
 * @description  controller类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 刘锡来
 * @version  2014-04-29
 */
 
@Controller
@RequestMapping(value="/po/anno")
public class AnnoController extends BaseController{
	
	@Autowired
	private IAnnoService annoService;
	
	@Autowired
	private IUserService userService;
	
	@org.springframework.web.bind.annotation.InitBinder("anno")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new AnnoValidator()); 
    }
	
	public AnnoController() {
	}

	/**
	 * @description 分页查询方法
	 * @param Anno anno 实体类
	 * @param PageManager page 分页实体类
	 * @param HttpServletRequest request
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author 刘锡来
	 * @version  2014-04-29 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="批注管理",operateFuncNm="manageList",operateDescribe="批注查询") 
	public PageManager manageList(Anno anno,PageManager page,HttpServletRequest request){
		anno.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
		if(StringUtils.isEmpty(anno.getOrderBy())) {
			anno.addOrderByFieldDesc("t.ANNO_DATE");
		}
		PageManager page_ = annoService.query(anno, page);
		return page_; 
	}
	
	/**
	* @description 跳转方法
	* @param HttpServletRequest request
	* @return String 跳转的路径
	* @throws Exception
	* @author 刘锡来
	* @version  2014-04-29 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="批注管理",operateFuncNm="manage",operateDescribe="批注跳转")
	public String manage(HttpServletRequest request) throws Exception{
		return "po/anno/anno"; 
	}

/**
	 * @description 删除方法
	 * @param Anno anno 实体类
	 * @param String ids 删除的多个主键
	 * @param HttpServletRequest request
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 刘锡来
	 * @version  2014-04-29
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="批注管理",operateFuncNm="deleteByIds",operateDescribe="批注删除")
	public Integer deleteByIds(Anno anno,String ids,HttpServletRequest request) throws Exception{
		anno.setPrimaryKeys(ids.split(","));
		return annoService.delete(anno);
	}

	/**
	 * @description 保存方法
	 * @param Anno anno 实体类
	 * @param BindingResult result 校验结果
	 * @param HttpServletRequest request
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 刘锡来
	 * @version  2014-04-29
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="批注管理",operateFuncNm="save",operateDescribe="批注保存")
	public Map<String,Object> save(@Valid Anno anno,BindingResult result,HttpServletRequest request) throws Exception{
		Map<String,Object> resultMap = validateBean(result);
		//验证token
		resultMap = validateToken(request);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		String token = super.getToken(request);//获取token
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		if(!"false".equals(resultMap.get("success"))){
			
			//权限判断 add by lihongyu at 2014-11-06 start
			boolean isLeader=false;
			//将当前登录用户ID传入页面
			User userInfo = SystemSecurityUtils.getUser();
			if (null!=anno) {
				User user=new User();
				user.setId(anno.getByAnnoUserId());
				isLeader=userService.isLeader(user,userInfo);
				if(userInfo.getId().intValue()==anno.getByAnnoUserId().intValue()){
					isLeader=true;
				}
			}
			if (isLeader==false) {
				resultMap.put(GlobalContext.RESULT_SUCCESS, false);
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_PO_047"));
				return resultMap;
			}
			//权限判断 add by lihongyu at 2014-11-06 end
			anno.setAnnoUserId(SystemSecurityUtils.getUser().getId());
			anno.setAnnoUserDepid(SystemSecurityUtils.getUser().getDeptId());
			anno.setAnnoDate(DateUtils.getSysDate());
			anno.setByAnnoUserDepid(UserUtils.getUser(anno.getByAnnoUserId()).getDeptId());
			anno.setReadingState(Constants.PO_ANNO_IS_NOT_READ);
			annoService.save(anno);
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_OA_PO_008"));
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param Anno anno 实体类
	* @param HttpServletRequest request
	* @return Integer 更新的数目
	* @author 刘锡来
	* @version  2014-04-29 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="批注管理",operateFuncNm="update",operateDescribe="批注修改")
	public Integer update(Anno anno,HttpServletRequest request) throws Exception{
		Integer flag = annoService.update(anno);
		return flag;
	}

	/**
	 * @description 获取单条记录方法
	 * @param Anno anno 实体类
	 * @return Anno 查询结果
	 * @throws Exception
	 * @author 刘锡来
	 * @version  2014-04-29
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="批注管理",operateFuncNm="get",operateDescribe="获取单条批注信息")
	public Anno get(Anno anno,HttpServletRequest request) throws Exception{
		return annoService.get(anno);
	}

	
	/**
	 * @description：更新批注的阅读情况
	 * @param Anno anno 实体类
	 * @param HttpServletRequest request
	 * @return Map<String,Object>
	 * @author 李新桐
	 * @version  2014-5-28
	 */
	@RequestMapping(value="annoReading.action")
	@ResponseBody
	@ActionLog(operateModelNm="批注管理",operateFuncNm="annoReading",operateDescribe="更新批注的阅读情况")
	public Map<String,Object> annoReading(Anno anno,HttpServletRequest request){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(annoService.annoReading(anno)!=0){
			resultMap.put("success", "true");
		}
		return resultMap;
	}
	
	/**
	* @description 个人办公默认跳转方法
	* @param HttpServletRequest request
	* @return /WebRoot/WEB-INF/web/po/plan/personalOffice.jsp
	* @throws Exception
	* @author 刘锡来
	* @version  2014-09-05 
	*/
	@RequestMapping(value="personalOffice.action")
	@ActionLog(operateModelNm="我的计划",operateFuncNm="personalOffice",operateDescribe="个人办公默认跳转")
	public String personalOfficeSkip(HttpServletRequest request) throws Exception{
		return "po/plan/personalOffice"; 
	}
}