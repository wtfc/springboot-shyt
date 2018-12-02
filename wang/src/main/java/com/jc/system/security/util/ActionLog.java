package com.jc.system.security.util;

import java.lang.annotation.Documented;  
import java.lang.annotation.ElementType;  
import java.lang.annotation.Inherited;  
import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;  
import java.lang.annotation.Target;  

/** 
 * 类的方法描述注解 
 * @author CHZ
 */  
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
@Inherited  
public @interface ActionLog {  

	/** 
	 * 方法描述 注入日志
	 * @return 
	 */  
	String operateModelNm();  
	String operateFuncNm();  
	String operateDescribe(); 



}
