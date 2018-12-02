package com.jc.system.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-24
 * 
 */
public class PropertiesUtil {
	private static final Logger logger = Logger.getLogger(GlobalContext.class);

	/**
	 * @description 获得路径下的properties文件
	 * @param pathName
	 *            properties文件路径
	 * @param fileName
	 *            properties文件名
	 * @return Map 属性文件结果
	 * @author
	 * @version 2014-03-24
	 */
	public static Map<String, String> getProperties(String pathName,
			String fileName) {
		Properties property = new Properties();
		Map<String, String> paraMap = new HashMap<String, String>();
		try {

			property.load(new FileInputStream(pathName + fileName));
			Set<Entry<Object, Object>> set = property.entrySet();
			Iterator<Entry<Object, Object>> itor = set.iterator();

			while (itor.hasNext()) {
				Entry<Object, Object> entry = itor.next();
				paraMap.put(entry.getKey().toString(), entry.getValue()
						.toString());
			}
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return paraMap;
	}

	/**
	 * @description 获得根目录下的properties内容
	 * @param fileName
	 *            properties文件名
	 * @return Map 属性文件结果
	 * @author
	 * @version 2014-03-24
	 */
	public static Map<String, String> getProperties(String fileName) {
		String basePath = GlobalContext.basePath;
		return getProperties(basePath, fileName);
	}
}
