package com.jc.oa.po.workTask.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.oa.po.workTask.domain.WorkTaskHistory;

/**
 * @title 个人办公
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author  李洪宇
 * @version  2014-04-23
 */

public class WorkTaskHistoryValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author 李洪宇
	 * @version  2014-04-23
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return WorkTaskHistory.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author 李洪宇
	 * @version  2014-04-23
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		WorkTaskHistory v =  (WorkTaskHistory)arg0;
		if (null!=v && null!=v.getTaskEventType() && !"".equals(v.getTaskEventType())) {
			if ("3".equals(v.getTaskEventType())) {//接收
				if (v.getActStartTime()==null) {
					arg1.reject("actStartTime",MessageUtils.getMessage("JC_SYS_010"));
				}
			}
			if ("4".equals(v.getTaskEventType())) {//不接收
				if(v.getDelayReason()==null || v.getDelayReason().equals("")){
					arg1.reject("noReTaskReason",MessageUtils.getMessage("JC_SYS_010"));
				}
				if((v.getDelayReason()!=null && !"".equals(v.getDelayReason())) && v.getDelayReason().length()>100){
					arg1.reject("noReTaskReason",MessageUtils.getMessage("JC_SYS_011",new Object[]{100}));
				}
			}
			if ("5".equals(v.getTaskEventType())) {//汇报
				if (v.getReportProc()==null) {
					arg1.reject("reportProc",MessageUtils.getMessage("JC_SYS_010"));
				}
				if (v.getReportProc()!=null && (v.getReportProc() <=0 || v.getReportProc() >100) ) {
					arg1.reject("reportProc",MessageUtils.getMessage("JC_SYS_053",new Object[]{0,100}));
				}
				if (v.getReportContent()==null || v.getReportContent().equals("")) {
					arg1.reject("reportContent",MessageUtils.getMessage("JC_SYS_010"));
				}
				if ((v.getReportContent()!=null && !"".equals(v.getReportContent())) && v.getReportContent().length()>100) {
					arg1.reject("reportContent",MessageUtils.getMessage("JC_SYS_011",new Object[]{100}));
				}
			}
			if ("6".equals(v.getTaskEventType())) {//催办
				if(v.getDelayReason()==null || v.getDelayReason().equals("")){
					arg1.reject("remindersCo",MessageUtils.getMessage("JC_SYS_010"));
				}
				if((v.getDelayReason()!=null && !"".equals(v.getDelayReason())) && v.getDelayReason().length()>100){
					arg1.reject("remindersCo",MessageUtils.getMessage("JC_SYS_011",new Object[]{100}));
				}
			}
			if ("8".equals(v.getTaskEventType())) {//取消
				if(v.getDelayReason()==null || v.getDelayReason().equals("")){
					arg1.reject("cancelReason",MessageUtils.getMessage("JC_SYS_010"));
				}
				if((v.getDelayReason()!=null && !"".equals(v.getDelayReason())) && v.getDelayReason().length()>100){
					arg1.reject("cancelReason",MessageUtils.getMessage("JC_SYS_011",new Object[]{100}));
				}
			}
			if ("12".equals(v.getTaskEventType())) {//延期申请
				if (v.getDelayDays()!=null && v.getDelayDays() <=0) {
					arg1.reject("delayDays",MessageUtils.getMessage("JC_SYS_051",new Object[]{0}));
				}
				if (v.getDelayEnddate()==null) {
					arg1.reject("delayDays",MessageUtils.getMessage("JC_SYS_010"));
				}
				if(v.getDelayReason()==null || v.getDelayReason().equals("")){
					arg1.reject("delayReasons",MessageUtils.getMessage("JC_SYS_010"));
				}
				if((v.getDelayReason()!=null && !"".equals(v.getDelayReason())) && v.getDelayReason().length()>100){
					arg1.reject("delayReasons",MessageUtils.getMessage("JC_SYS_011",new Object[]{100}));
				}
			}
		}
	}
}
