package com.jc.system.common.util;

import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

/**
 * 
 * @title GOA V2.0
 * @description
 * @version  2014年5月6日
 */
public class BeanUtil {

	private static Logger log = Logger.getLogger(BeanUtil.class);
	
	/**
	 * 
	  * @description map to object
	  * @param map
	  * @param obj
	  * @return obj
	  * @author 高研
	  * @version 1.0 2014年5月7日 上午10:58:28
	 */
	public static <T> T map2Object(Map<String, Object> map, Class<T> T){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);  
		objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.setDateFormat(new StdDateFormat());
		return objectMapper.convertValue(map, T);
	}
}
