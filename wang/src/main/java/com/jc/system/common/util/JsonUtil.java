package com.jc.system.common.util;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-24
 * 
 */
public class JsonUtil {

	private static final Logger logger = Logger.getLogger(JsonUtil.class);

	/**
	 * @description 将对象转换成JSON字符串，并响应回前台
	 * @author 孙圣然
	 * @param ：object 转换的类
	 * @return：String 结果的json字符串
	 */
	public static String getJSON(Object object) {
		StringWriter sw = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(sw, object);
		} catch (JsonGenerationException e) {
			logger.error(e);
		} catch (JsonMappingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}

		if (sw != null) {
			try {
				sw.flush();
				sw.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return sw.toString();
	}
	
	/**
	 * @Description 将JSON字符串 转换成对象
	 * @author 盖旭
	 * @param ：@param json
	 * @param ：@param Class type
	 * @return：Object
	 */
	public static Object json2Java(String json, Class<?> type) {
		ObjectMapper mapper = new ObjectMapper();  
		Object jacksonToBean = null;
        try {  
            jacksonToBean = mapper.readValue(json, type);  
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
       return jacksonToBean;
	}
}
