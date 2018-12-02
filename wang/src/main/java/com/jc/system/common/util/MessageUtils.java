/**
 * 
 */
package com.jc.system.common.util;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.support.RequestContext;

/**
 * @title GOA V2.0
 * @description  提示信息工具类
 * @version  2014年5月4日
 */
public class MessageUtils {
	
	private static ResourceBundleMessageSource messageReSource = SpringContextHolder.getBean(ResourceBundleMessageSource.class);
	/**
	 * 
	 */
	public MessageUtils() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	  * 根据代码获取提示信息
	  * @param code 提示信息代码
	  * @return  返回对应的提示信息
	  * @author 孙纪福
	  * @version 1.0 2014年5月4日 下午5:43:43
	  * @see #getMessage(String, Object[])
	*/
	public static String getMessage(String code){
		return getMessage(code,(Object[])null);
	}
	
	
	/**
	  * 根据代码获取提示信息
	  * @param code 提示信息代码
	  * @param defaultValue 提示信息的默认值
	  * @return 返回代码对应的提示信息，如果对应的信息不存在，则返回传入的默认值
	  * @author 孙纪福
	  * @version 1.0 2014年5月4日 下午5:43:54
	  * @see #getMessage(String)
	*/
	public static String getMessage(String code,String defaultValue){
		return getMessage(code,new Object[]{},defaultValue);
	}
	
	/**
	  * 根据代码获取提示信息， 并根据传入参数填充对应的占位符
	  * @param code 提示信息代码
	  * @param params 对象数组
	  * @param defaultValue 提示信息的默认值
	  * @return 返回代码对应的提示信息，如果对应的信息不存在，则返回传入的默认值
	  * @see #getMessage(String, Object[])
	  * @version 1.0 2014年5月4日 下午5:43:59
	*/
	public static String getMessage(String code,Object[] params,String defaultValue){
		return messageReSource.getMessage(code,params ,defaultValue, Locale.getDefault());
	}
	
	
	
	/**
	  * 根据代码获取提示信息， 并根据传入参数填充对应的占位符
	  * @param code 提示信息代码
	  * @param params 对象数组
	  * @return  返回代码对应的提示信息
	  * @version 1.0 2014年5月4日 下午5:44:03
	  * @see #getMessage(String, Object[], String)
	*/
	public static String getMessage(String code,Object[] params){
		return messageReSource.getMessage(code, params, Locale.getDefault());
	}
	
	
}
