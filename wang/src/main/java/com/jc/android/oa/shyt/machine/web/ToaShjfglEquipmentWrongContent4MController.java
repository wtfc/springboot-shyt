package com.jc.android.oa.shyt.machine.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.android.oa.shyt.machine.domain.ToaShjfglEquipmentWrong4M;
import com.jc.android.oa.shyt.machine.domain.ToaShjfglEquipmentWrongContent4M;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentWrong;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentWrongContent;
import com.jc.shjfgl.machine.service.IToaShjfglEquipmentWrongContentService;
import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;
/**
 * @author mrb
 * @version 故障处理过程表
*/
@Controller
@RequestMapping(value="/android/machine/toaShjfglEquipmentWrongContent")
public class ToaShjfglEquipmentWrongContent4MController extends BaseController{
	
	@Autowired 
	private IToaShjfglEquipmentWrongContentService toaShjfglEquipmentWrongContentService; 
	
	public ToaShjfglEquipmentWrongContent4MController(){
	}
	
	/**
	 * 保存方法
	 * @param TimeSet 	timeSet 实体类
	 * @param Long 		id 		故障工单id
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="save",operateDescribe="对故障处理过程表进行发起操作")
	public Map<String,Object> save(ToaShjfglEquipmentWrongContent toaShjfglEquipmentWrongContent,Long id,BindingResult result,
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
			toaShjfglEquipmentWrongContent.setWrongId(id.intValue());
			toaShjfglEquipmentWrongContentService.save(toaShjfglEquipmentWrongContent);
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
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="update",operateDescribe="对故障处理过程表进行更新操作")
	public Map<String, Object> update(ToaShjfglEquipmentWrongContent toaShjfglEquipmentWrongContent, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaShjfglEquipmentWrongContentService.update(toaShjfglEquipmentWrongContent);
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
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="get",operateDescribe="对故障处理过程表进行单条查询操作")
	public ToaShjfglEquipmentWrongContent get(ToaShjfglEquipmentWrongContent toaShjfglEquipmentWrongContent,HttpServletRequest request) throws Exception{
		return toaShjfglEquipmentWrongContentService.get(toaShjfglEquipmentWrongContent);
	}
	
	/**
	 * 分页查询方法
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="get",operateDescribe="故障处理过程表分页查询")
	public PageManager manageList(ToaShjfglEquipmentWrongContent toaShjfglEquipmentWrongContent,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(toaShjfglEquipmentWrongContent.getOrderBy())) {
			toaShjfglEquipmentWrongContent.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = toaShjfglEquipmentWrongContentService.query(toaShjfglEquipmentWrongContent, page);
		return page_; 
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
	@ActionLog(operateModelNm="故障处理过程表表",operateFuncNm="deleteByIds",operateDescribe="对故障处理过程表表进行删除")
	public  Map<String, Object> deleteByIds(ToaShjfglEquipmentWrongContent toaShjfglEquipmentWrongContent,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaShjfglEquipmentWrongContent.setPrimaryKeys(ids.split(","));	
		if(toaShjfglEquipmentWrongContentService.deleteByIds(toaShjfglEquipmentWrongContent) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
		}
		return resultMap;
	}
	
	/**
	 * APP-故障处理内容-保存方法
	 * @param ToaShjfglEquipmentWrongContent toaShjfglEquipmentWrongContent 实体类
	 * @param Integer 	wrongId 		故障工单id
	 * @param String 	operationType 		故障类型（17、设备故障 18、网络故障）
	 * @param BindingResult result 校验结果
	 * @return Map<String,Object>  state 是否成功， errormsg错误信息
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value = "save4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="save4M",operateDescribe="对故障处理过程表进行发起操作")
	public Map<String,Object> save4M(ToaShjfglEquipmentWrongContent toaShjfglEquipmentWrongContent,Integer wrongId,String operationType,String operateAndResult,BindingResult result,
			HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = validateBean(result);
		Integer flag = -1;
		if (resultMap.size() > 0) {
			return resultMap;
		}
		if(!"false".equals(resultMap.get("success"))){
			//机房参观.陪同人	入室维护.机房接待人员
			User user =SystemSecurityUtils.getUser();
			String userCode=user.getId().toString();
			String userName=user.getDisplayName();
			toaShjfglEquipmentWrongContent.setCaozgcs(userCode);
			toaShjfglEquipmentWrongContent.setCaozgcsName(userName);
			toaShjfglEquipmentWrongContent.setWrongId(wrongId);
			toaShjfglEquipmentWrongContent.setOperationType(operationType);
			toaShjfglEquipmentWrongContent.setOperateAndResult(operateAndResult);
			flag = toaShjfglEquipmentWrongContentService.save(toaShjfglEquipmentWrongContent);
		}
		if (flag == 1) {	
			resultMap.put("state", "success");
			resultMap.put("data", "提交成功");
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}
	
	/**
	 * APP-故障处理内容-分页查询方法
	 * @param ToaShjfglEquipmentWrongContent toaShjfglEquipmentWrongContent 实体类
	 * @param Integer wrongId 设备或网络故障ID
	 * @param String wrongType 故障类型（17、设备故障 18、网络故障）
	 * @return Map<String,Object>  state 是否成功， errormsg错误信息
	 * @author	wtf
	 * @throws CustomException 
	 */
	@RequestMapping(value="manage4MList.action")
	@ResponseBody
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="manage4MList",operateDescribe="故障处理过程表分页查询")
	public Map<String, Object> manage4MList(ToaShjfglEquipmentWrongContent toaShjfglEquipmentWrongContent,Integer wrongId,String wrongType,PageManager page,HttpServletRequest request) throws CustomException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//默认排序
		if(StringUtil.isEmpty(toaShjfglEquipmentWrongContent.getOrderBy())) {
			toaShjfglEquipmentWrongContent.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		
		List<ToaShjfglEquipmentWrongContent> resultList = toaShjfglEquipmentWrongContentService.queryAll(toaShjfglEquipmentWrongContent);
		
		if (resultList != null) {

			List<ToaShjfglEquipmentWrongContent4M> list4M =new ArrayList<ToaShjfglEquipmentWrongContent4M>();
			for(int i = 0;i<resultList.size();i++){
				ToaShjfglEquipmentWrongContent toaShjfglEquipmentWrongContentTemp = resultList.get(i);
				ToaShjfglEquipmentWrongContent4M toaShjfglEquipmentWrongContent4MTemp = new ToaShjfglEquipmentWrongContent4M();
				BeanUtils.copyProperties(toaShjfglEquipmentWrongContentTemp, toaShjfglEquipmentWrongContent4MTemp);
				list4M.add(toaShjfglEquipmentWrongContent4MTemp);
			}
			resultMap.put("state", "success");
			resultMap.put("data", list4M);
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
			
		return resultMap; 
	}
	
	/**
	 * APP-故障处理内容-获取单条记录方法
	 * @param TimeSet 	timeSet 实体类
	 * @param Long 		id 		故障内容ID
	 * @return Map<String,Object>  state 是否成功， errormsg错误信息
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="get4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="故障处理过程表",operateFuncNm="get4M",operateDescribe="对故障处理过程表进行单条查询操作")
	public Map<String, Object> get4M(ToaShjfglEquipmentWrongContent toaShjfglEquipmentWrongContent,Long id,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ToaShjfglEquipmentWrongContent toaShjfglEquipmentWrongContentTemp = new ToaShjfglEquipmentWrongContent();
		toaShjfglEquipmentWrongContentTemp.setId(id);
		toaShjfglEquipmentWrongContentTemp = toaShjfglEquipmentWrongContentService.get(toaShjfglEquipmentWrongContent);
		if (toaShjfglEquipmentWrongContentTemp != null) {
			ToaShjfglEquipmentWrongContent4M toaShjfglEquipmentWrongContent4M = new ToaShjfglEquipmentWrongContent4M();
			BeanUtils.copyProperties(toaShjfglEquipmentWrongContentTemp, toaShjfglEquipmentWrongContent4M);
			
			resultMap.put("state", "success");
			resultMap.put("data", toaShjfglEquipmentWrongContent4M);
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap;
	}

}
