package com.jc.oa.ic.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jc.oa.ic.domain.Out;
import com.jc.system.common.util.MessageUtils;

/**
 * @title HR
 * @description  检验类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-29
 */

public class OutValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-04-29
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Out.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-04-29
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		Out v =  (Out)arg0;
		if(v.getOutLinkman()!=null&&!"".equals(v.getOutLinkman())){
			String[] code=v.getOutLinkman().split(",");
			String rex  ="^[1][3-8]+\\d{9}$";
			for(int i=0;i<code.length;i++){
				if(!code[i].matches(rex)){
					arg1.reject("outLinkman",  MessageUtils.getMessage("JC_SYS_022"));
				}
			}
		}
		if(v.getText()==null||"".equals(v.getText())){
			arg1.reject("text", MessageUtils.getMessage("JC_SYS_010"));
		}
		if(v.getText()!=null&&v.getText().length()>300){
			arg1.reject("text", MessageUtils.getMessage("JC_SYS_011", new Object[]{"300"}));
		}
		if(v.getSendType()!=null&&"".equals(v.getText())){
			arg1.reject("sendType",MessageUtils.getMessage("JC_SYS_010"));
		}
		
	}
}
