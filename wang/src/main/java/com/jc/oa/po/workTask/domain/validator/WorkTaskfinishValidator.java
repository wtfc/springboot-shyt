package com.jc.oa.po.workTask.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.StringUtil;
import com.jc.oa.po.workTask.domain.WorkTaskfinish;

/**
 * @title 个人办公
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-24
 */

public class WorkTaskfinishValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author 李洪宇
	 * @version  2014-04-24
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return WorkTaskfinish.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author 李洪宇
	 * @version  2014-04-24
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		WorkTaskfinish v =  (WorkTaskfinish)arg0;
		if(v.getTaskName()!=null&&v.getTaskName().length()>255){
			arg1.reject("taskName", "TaskName is too long");
		}
		if(v.getTaskImpCode()!=null&&v.getTaskImpCode().length()>64){
			arg1.reject("taskImpCode", "TaskImpCode is too long");
		}
		if(v.getPrticipantsId()!=null&&v.getPrticipantsId().length()>65535){
			arg1.reject("prticipantsId", "PrticipantsId is too long");
		}
		if(v.getContent()!=null&&v.getContent().length()>2000){
			arg1.reject("content", "Content is too long");
		}
		if(v.getStandard()!=null&&v.getStandard().length()>2000){
			arg1.reject("standard", "Standard is too long");
		}
		if(v.getReportType()!=null&&v.getReportType().length()>1){
			arg1.reject("reportType", "ReportType is too long");
		}
		if(v.getStatus()!=null&&v.getStatus().length()>1){
			arg1.reject("status", "Status is too long");
		}
		if(v.getTaskOrigin()!=null&&v.getTaskOrigin().length()>1){
			arg1.reject("taskOrigin", "TaskOrigin is too long");
		}
		if(v.getExtStr1()!=null&&v.getExtStr1().length()>200){
			arg1.reject("extStr1", "ExtStr1 is too long");
		}
		if(v.getExtStr2()!=null&&v.getExtStr2().length()>200){
			arg1.reject("extStr2", "ExtStr2 is too long");
		}
		if(v.getExtStr3()!=null&&v.getExtStr3().length()>200){
			arg1.reject("extStr3", "ExtStr3 is too long");
		}
		if(v.getExtStr4()!=null&&v.getExtStr4().length()>200){
			arg1.reject("extStr4", "ExtStr4 is too long");
		}
		if(v.getExtStr5()!=null&&v.getExtStr5().length()>200){
			arg1.reject("extStr5", "ExtStr5 is too long");
		}
	}
}
