package com.jc.oa.ic.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.oa.ic.domain.Suggest;
import com.jc.system.common.util.MessageUtils;

/**
 * @title 互动交流
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-17
 */

public class SuggestValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-04-17
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Suggest.class.equals(arg0);
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
		Suggest v =  (Suggest)arg0;

		if (v.getSuggestTitle() == null || v.getSuggestTitle().equals("")) {
			arg1.reject("suggestTitle", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getSuggestTitle()!=null&&v.getSuggestTitle().length()>100){
			arg1.reject("suggestTitle", MessageUtils.getMessage("JC_SYS_011", new Object[]{"100"}));
		}
		if(v.getSuggestContent()!=null&&v.getSuggestContent().length()>300){
			arg1.reject("suggestContent", MessageUtils.getMessage("JC_SYS_011", new Object[]{"300"}));
		}
		if(v.getSuggestWay()!=null&&v.getSuggestWay().length()>1){
			arg1.reject("suggestWay", MessageUtils.getMessage("JC_SYS_011", new Object[]{"1"}));
		}
		if(v.getSingleShow()!=null&&v.getSingleShow().length()>2){
			arg1.reject("singleShow", MessageUtils.getMessage("JC_SYS_011", new Object[]{"2"}));
		}
		if(v.getRepStatus()!=null&&v.getRepStatus().length()>1){
			arg1.reject("repStatus", MessageUtils.getMessage("JC_SYS_011", new Object[]{"1"}));
		}
		if(v.getDisposeType()!=null&&v.getDisposeType().length()>1){
			arg1.reject("disposeType", MessageUtils.getMessage("JC_SYS_011", new Object[]{"1"}));
		}
		if(v.getSuggestTel()!=null&&v.getSuggestTel().length()>64){
			arg1.reject("suggestTel", MessageUtils.getMessage("JC_SYS_011", new Object[]{"64"}));
		}
		if(v.getSuggestMail()!=null&&v.getSuggestMail().length()>255){
			arg1.reject("suggestMail", MessageUtils.getMessage("JC_SYS_011", new Object[]{"255"}));
		}
	}
}
