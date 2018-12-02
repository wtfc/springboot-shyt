package com.jc.oa.common.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.oa.common.domain.Remind;

/**
 * @title  GOA2.0源代码
 * @description  检验类
 * @author 
 * @version  2014-04-17
 */

public class RemindValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-04-17
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Remind.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-04-17
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		Remind v =  (Remind)arg0;
		/*if(v.getRemindType()!=null&&v.getRemindType().length()>1){
			arg1.reject("remindType", "RemindType is too long");
		}
		if(v.getCycle()!=null&&v.getCycle().length()>1){
			arg1.reject("cycle", "Cycle is too long");
		}
		if(v.getCronExpression()!=null&&v.getCronExpression().length()>255){
			arg1.reject("cronExpression", "CronExpression is too long");
		}
		if(v.getViewCycle()!=null&&v.getViewCycle().length()>255){
			arg1.reject("viewCycle", "ViewCycle is too long");
		}
		if(v.getTitle()!=null&&v.getTitle().length()>255){
			arg1.reject("title", "Title is too long");
		}
		if(v.getContent()!=null&&v.getContent().length()>255){
			arg1.reject("content", "Content is too long");
		}
		if(v.getReceiveId()!=null&&v.getReceiveId().length()>4000){
			arg1.reject("receiveId", "ReceiveId is too long");
		}
		if(v.getRequireRemind()!=null&&v.getRequireRemind().length()>1){
			arg1.reject("requireRemind", "RequireRemind is too long");
		}*/
	}
}
