package com.jc.system.common.util;

import org.springframework.web.context.WebApplicationContext;

public class SpringUtil {
	public static WebApplicationContext ctx;

	/**
	 * @description 根据serviceStr获得spring里面的名字
	 */
	public static String getBeanName(String serviceStr) {
		String result = "";
		int position = serviceStr.lastIndexOf(".");
		result = serviceStr.substring(position + 1);
		result = StringUtil.firstLower(result);
		return result;
	}
}
