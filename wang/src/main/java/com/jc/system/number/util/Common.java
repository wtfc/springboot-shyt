package com.jc.system.number.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @description: 公用方法类 
 * @created: 2013-4-1 下午3:28:52 
 * @version：$Id: Common.java 43813 2014-05-05 11:05:29Z gaoy $ 
 */
public class Common {
	
	/**
	 * 日期格式去掉-
	 * @param dateStr
	 * @return
	 */
	public static String formatDateStr(String dateStr){
		return dateStr.replaceAll("-", "");
	}

	/**
	 * 根据指定格式格式化时间
	 * @param formatStr
	 * @return
	 */
	public static String getDateStr(String formatStr){
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		return df.format(d);
	}

	/**
	 * 字符串转换成日期
	 * @param str
	 * @return date
	 */
	public static java.util.Date StrToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * AJAX中文转码
	 * @param str
	 * @return
	 */
	public static String getUTFString(String str) {
		try {
			return java.net.URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return str;
	}

}
