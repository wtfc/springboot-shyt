package com.jc.oa.po.workTask.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.oa.po.workTask.domain.WorkTask;

/**
 * @title 个人办公
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-23
 */

public class WorkTaskValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author 李洪宇
	 * @version  2014-04-23
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return WorkTask.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法(任务布置)
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author 李洪宇
	 * @version  2014-04-23
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		WorkTask v =  (WorkTask)arg0;
		//判断不能为空
		if(v.getTaskName()==null || v.getTaskName().equals("")){
			arg1.reject("taskName", MessageUtils.getMessage("JC_SYS_010"));
		}
//		if(v.getTaskWorkType()==null || v.getTaskWorkType().equals("")){
//			arg1.reject("taskWorkType", MessageUtils.getMessage("JC_SYS_010"));
//		}
//		if(v.getTaskOrigin()==null || v.getTaskOrigin().equals("")){
//			arg1.reject("taskOrigin", MessageUtils.getMessage("JC_SYS_010"));
//		}
		if (v.getTaskImpCode()!=null && v.getTaskImpCode().equals("")) {
			arg1.reject("taskImpCode", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getSponsorId()==null){
			arg1.reject("showSponsorTree", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getDirectorId()==null){
			arg1.reject("showDirectorIdTree", MessageUtils.getMessage("JC_SYS_010"));
		}
//		if(v.getPrticipantsId()==null || v.getPrticipantsId().equals("")){
//			arg1.reject("prticipantsTree", MessageUtils.getMessage("JC_SYS_010"));
//		}
		if(v.getContent()==null || v.getContent().equals("")){
			arg1.reject("taskContent", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getStartTime()==null){
			arg1.reject("startTime", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getEndTime()==null){
			arg1.reject("endTime", MessageUtils.getMessage("JC_SYS_010"));
		}
		//判断长度
		if(v.getTaskName()!=null && v.getTaskName().length()>60){
			arg1.reject("taskName", MessageUtils.getMessage("JC_SYS_011",new Object[]{60}));
		}
		if(v.getContent()!=null && v.getContent().length()>175){
			arg1.reject("content", MessageUtils.getMessage("JC_SYS_011",new Object[]{150}));
		}
		if(v.getStandard()!=null && v.getStandard().length()>175){
			arg1.reject("standard", MessageUtils.getMessage("JC_SYS_011",new Object[]{150}));
		}
	}
	/**
	 * @description 检验具体实现方法(任务修改)
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author 李洪宇
	 * @version  2014-04-23
	 */
	public void validate(Object arg0, Errors arg1,String vType) {
		WorkTask v =  (WorkTask)arg0;
		//判断不能为空
		if(v.getTaskName()==null || v.getTaskName().equals("")){
			arg1.reject("modifyTaskName", MessageUtils.getMessage("JC_SYS_010"));
		}
//		if(v.getTaskWorkType()==null || v.getTaskWorkType().equals("")){
//			arg1.reject("taskWorkType", MessageUtils.getMessage("JC_SYS_010"));
//		}
//		if(v.getTaskOrigin()==null || v.getTaskOrigin().equals("")){
//			arg1.reject("taskOrigin", MessageUtils.getMessage("JC_SYS_010"));
//		}
//		if(v.getSponsorId()==null){
//			arg1.reject("showSpTree", MessageUtils.getMessage("JC_SYS_010"));
//		}
		if(v.getDirectorId()==null){
			arg1.reject("showDiTree", MessageUtils.getMessage("JC_SYS_010"));
		}
//		if(v.getPrticipantsId()==null || v.getPrticipantsId().equals("")){
//			arg1.reject("prtTree", MessageUtils.getMessage("JC_SYS_010"));
//		}
		if(v.getContent()==null || v.getContent().equals("")){
			arg1.reject("taskContent", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getStartTime()==null){
			arg1.reject("updateStartTime", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getEndTime()==null){
			arg1.reject("updateEndTime", MessageUtils.getMessage("JC_SYS_010"));
		}
		//判断长度 
		if(v.getTaskName()!=null && v.getTaskName().length()>60){
			arg1.reject("modifyTaskName", MessageUtils.getMessage("JC_SYS_011",new Object[]{60}));
		}
		if(v.getContent()!=null && v.getContent().length()>175){
			arg1.reject("taskContent", MessageUtils.getMessage("JC_SYS_011",new Object[]{150}));
		}
		if(v.getStandard()!=null && v.getStandard().length()>175){
			arg1.reject("standard", MessageUtils.getMessage("JC_SYS_011",new Object[]{150}));
		}
	}
}
