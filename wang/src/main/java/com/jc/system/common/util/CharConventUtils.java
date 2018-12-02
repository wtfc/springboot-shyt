package com.jc.system.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-24
 * 
 */
public class CharConventUtils {
	/**
	 * @description 文件下载中文名编码转换
	 * @param fileName
	 *            要转换的中文名
	 * @return 转换后的中文名
	 * @author
	 * @version 2014-03-24
	 */
	public static String encodingFileName(String fileName) {
		String returnFileName = "";
		try {
			returnFileName = URLEncoder.encode(fileName, "UTF-8");
			returnFileName = StringUtils.replace(returnFileName, "+", "%20");
			if (returnFileName.length() > 150) {
				returnFileName = new String(fileName.getBytes("GB2312"),
						"ISO8859-1");
				returnFileName = StringUtils
						.replace(returnFileName, " ", "%20");
			}
		} catch (UnsupportedEncodingException e) {

		}
		return returnFileName;
	}
}
