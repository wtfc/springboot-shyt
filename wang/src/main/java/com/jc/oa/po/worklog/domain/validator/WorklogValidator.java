package com.jc.oa.po.worklog.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.oa.po.worklog.domain.Worklog;

/**
 * @title 个人办公
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李新桐
 * @version  2014-05-04
 */

public class WorklogValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-05-04
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Worklog.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-05-04
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		Worklog v =  (Worklog)arg0;
		if(v.getTitle()!=null&&v.getTitle().length()>255){
			arg1.reject("title", MessageUtils.getMessage("JC_SYS_011", new Object[]{"255"}));
		}
		if(v.getTitle()!=null&&"".equals(v.getTitle())){
			arg1.reject("title",  MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getSentimentRemark()==null&&v.getSentimentRemark().length()>2000){
			arg1.reject("title", MessageUtils.getMessage("JC_SYS_011", new Object[]{"2000"}));
		}
	}
}
