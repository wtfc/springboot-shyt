package com.jc.foundation.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * 
 * @title GOA V2.0
 * @description 实体属性进行Json序列化时返回yyyy-MM-dd格式字符串
 * @version  2014年5月23日下午2:22:03
 */
public abstract  class CustomDateTypeSerializer extends JsonSerializer<Date> {

	
	/**
	  * 获取日期格式
	  * @return
	  * @version 1.0 2014年6月4日 上午8:18:56
	 */
	public abstract String getPattern();
	
	@Override
	public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat(getPattern());
		String formattedDate = formatter.format(value);
		jgen.writeString(formattedDate);
	}
}