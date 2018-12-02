package com.jc.android.oa.exception.web;

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

import com.jc.android.oa.exception.domain.Exception4M;
import com.jc.android.oa.exception.domain.validator.ExceptionValidator;
import com.jc.android.oa.exception.service.IExceptionService;
import com.jc.foundation.web.BaseController;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.util.ActionLog;

/**
 * @author 
 * @version  2014-12-10
 */
 
@Controller
@RequestMapping(value="/android/exception")
public class ExceptionController extends BaseController{
	
	@Autowired
	private IExceptionService exceptionService;
	
	@org.springframework.web.bind.annotation.InitBinder("exception")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new ExceptionValidator()); 
    }
	
	public ExceptionController() {
	}

	/**
	 * 保存方法
	 * @param Exception4M exception 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-12-10
	 */
	@RequestMapping(value = "save2.action")
	@ResponseBody
	@ActionLog(operateModelNm="",operateFuncNm="save",operateDescribe="对进行新增操作")
	public Map<String,Object> save(@Valid Exception4M exception,BindingResult result,
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
			exceptionService.save(exception);
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}
		return resultMap;
	}
	
	/**
	 * 保存方法
	 * @param Exception4M exception 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-12-10
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="",operateFuncNm="save",operateDescribe="对进行新增操作")
	public Map<String,Object> save(String userId,String exceTime,String exceDetail) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Exception4M exception = new Exception4M();
		exception.setUserId(Long.valueOf(userId));
		exception.setExceTime(DateUtils.parseDate(exceTime));
		exception.setExceDetail(exceDetail);
		Integer result = exceptionService.save(exception);
		
		if (result > 0){
			//成功状态位
			resultMap.put("state", "success");
		}else{
			//失败状态位
			resultMap.put("state", "failure");
			resultMap.put("errormsg",
					MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		
		return resultMap;
	}
	

	

}