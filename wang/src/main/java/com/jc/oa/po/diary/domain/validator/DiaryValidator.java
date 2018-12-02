package com.jc.oa.po.diary.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.oa.po.diary.domain.Diary;

/**
 * @title 个人办公
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-05-13
 */

public class DiaryValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-05-13
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Diary.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-05-13
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		Diary v =  (Diary)arg0;
			if(v.getTitle()!=null&&v.getTitle().length()>255){
				arg1.reject("title", "MessageUtils.getMessage(\"JC_SYS_011\", new Object[]{\"255\"})");
			}
			if(v.getContent()!=null&&v.getContent().length()>2000){
				arg1.reject("content", "MessageUtils.getMessage(\"JC_SYS_011\", new Object[]{\"2,000\"})");
			}
//			if(v.getPeriodType()!=null&&v.getPeriodType().length()>1){
//				arg1.reject("periodType", "MessageUtils.getMessage(\"JC_SYS_011\", new Object[]{\"1\"})");
//			}
//			if(v.getPeriodWay()!=null&&v.getPeriodWay().length()>64){
//				arg1.reject("periodWay", "MessageUtils.getMessage(\"JC_SYS_011\", new Object[]{\"64\"})");
//			}
//			if(v.getModuleFlag()!=null&&v.getModuleFlag().length()>1){
//				arg1.reject("moduleFlag", "MessageUtils.getMessage(\"JC_SYS_011\", new Object[]{\"1\"})");
//			}
//			if(v.getDiaryType()!=null&&v.getDiaryType().length()>1){
//				arg1.reject("diaryType", "MessageUtils.getMessage(\"JC_SYS_011\", new Object[]{\"1\"})");
//			}
//			if(v.getIsShare()!=null&&v.getIsShare().length()>1){
//				arg1.reject("isShare", "MessageUtils.getMessage(\"JC_SYS_011\", new Object[]{\"1\"})");
//			}
//			if(v.getExtStr1()!=null&&v.getExtStr1().length()>200){
//				arg1.reject("extStr1", "MessageUtils.getMessage(\"JC_SYS_011\", new Object[]{\"200\"})");
//			}
//			if(v.getExtStr2()!=null&&v.getExtStr2().length()>200){
//				arg1.reject("extStr2", "MessageUtils.getMessage(\"JC_SYS_011\", new Object[]{\"200\"})");
//			}
//			if(v.getExtStr3()!=null&&v.getExtStr3().length()>200){
//				arg1.reject("extStr3", "MessageUtils.getMessage(\"JC_SYS_011\", new Object[]{\"200\"})");
//			}
//			if(v.getExtStr4()!=null&&v.getExtStr4().length()>200){
//				arg1.reject("extStr4", "MessageUtils.getMessage(\"JC_SYS_011\", new Object[]{\"200\"})");
//			}
//			if(v.getExtStr5()!=null&&v.getExtStr5().length()>200){
//				arg1.reject("extStr5", "MessageUtils.getMessage(\"JC_SYS_011\", new Object[]{\"200\"})");
//			}
	}
}
