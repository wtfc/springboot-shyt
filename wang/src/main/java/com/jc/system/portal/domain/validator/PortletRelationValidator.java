package com.jc.system.portal.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.portal.domain.PortletRelation;

/**
 * @title GOA2.0
 * @description  检验类
 * @author 
 * @version  2014-06-16
 */

public class PortletRelationValidator implements Validator{
	
	/**
	 * @description 检验类能够校验的类
	 * @param arg0  校验的类型
	 * @return 是否支持校验
	 * @author
	 * @version  2014-06-16
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return PortletRelation.class.equals(arg0);
	}
	
	/**
	 * @description 检验具体实现方法
	 * @param arg0  当前的实体类
	 * @param arg1  错误的信息
	 * @author
	 * @version  2014-06-16
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
		PortletRelation v =  (PortletRelation)arg0;
		if(v.getLetTextarea()!=null&&v.getLetTextarea().length()>65535){
			arg1.reject("letTextarea", MessageUtils.getMessage("JC_SYS_011", new Object[]{"65,535"}));
		}
		if(v.getLetFile()!=null&&v.getLetFile().length()>100){
			arg1.reject("letFile", MessageUtils.getMessage("JC_SYS_011", new Object[]{"100"}));
		}
	}
}
