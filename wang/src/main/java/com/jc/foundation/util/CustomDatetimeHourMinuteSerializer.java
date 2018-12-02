package com.jc.foundation.util;


/**
 * 
 * @title GOA V2.0
 * @description 实体属性进行Json序列化时返回yyyy-MM-dd格式字符串
 * @version  2014年5月23日下午2:22:03
 */
public class CustomDatetimeHourMinuteSerializer extends CustomDateTypeSerializer{

	@Override
	public String getPattern() {
		// TODO Auto-generated method stub
		return "yyyy-MM-dd HH:mm";
	}
}