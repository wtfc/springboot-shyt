package com.jc.android.oa.shyt.machine.web;

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
import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;
import com.jc.oa.machine.domain.ToaMachineExpress;
import com.jc.oa.machine.domain.ToaMachineMessage;
import com.jc.oa.machine.service.IToaMachineMessageService;
/**
 * @author mrb
 * @version 工单消息表
*/
@Controller
@RequestMapping(value="/android/machine/message")
public class ToaMachineMessage4MController extends BaseController{
	
	@Autowired 
	private IToaMachineMessageService toaMachineMessageService; 
	
	public ToaMachineMessage4MController(){
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
	@RequestMapping(value="manageList4M.action")
	@ResponseBody
	@ActionLog(operateModelNm="工单消息表",operateFuncNm="get",operateDescribe="工单消息表分页查询")
	public Map<String,Object> manageList(ToaMachineMessage toaMachineMessage,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		
		Map<String,Object> resultMap=new HashMap<String,Object>();
		//默认排序
		if(StringUtil.isEmpty(toaMachineMessage.getOrderBy())) {
			toaMachineMessage.addOrderByFieldDesc("t.CREATE_DATE");
		}
		User user=SystemSecurityUtils.getUser();
		toaMachineMessage.setReceivedDeptId(user.getDeptId().toString());
		List<ToaMachineMessage> messageList=toaMachineMessageService.queryAll(toaMachineMessage);
		if(messageList != null){
			resultMap.put("state", "success");
			resultMap.put("data", messageList);
		}else{
			resultMap.put("state", "failure");
			resultMap.put("errormsg", MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap; 
	}
}	
