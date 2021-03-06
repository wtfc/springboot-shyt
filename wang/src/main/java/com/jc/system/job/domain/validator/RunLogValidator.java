package com.jc.system.job.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.MessageUtils;
import com.jc.system.job.domain.RunLog;

/**
 * @title 系统任务
 * @description 检验类 
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 都业广
 * @version 2014-03-20 17:08
 * 
 */
public class RunLogValidator implements Validator {

	/**
	 * @description 检验类能够校验的类
	 * @param arg0 校验的类型
	 * @return 是否支持校验
	 * @author 都业广
	 * @version 2014-03-20
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return RunLog.class.equals(arg0);
	}

	/**
	 * @description 检验具体实现方法
	 * @param arg0 当前的实体类
	 * @param arg1 错误的信息
	 * @author 都业广
	 * @version 2014-03-20
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		RunLog v = (RunLog) arg0;
		if (v.getGroupName() != null && v.getGroupName().length() > 255) {
			arg1.reject("message", MessageUtils.getMessage("JC_SYS_011", new Object[]{"255"}));
		}
		if (v.getJobData() != null && v.getJobData().length() > 65535) {
			arg1.reject("message", MessageUtils.getMessage("JC_SYS_011", new Object[]{"65535"}));
		}
	}
}
