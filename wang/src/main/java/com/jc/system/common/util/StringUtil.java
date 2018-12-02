package com.jc.system.common.util;

import java.util.regex.Pattern;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-24
 * 
 */
public class StringUtil {

	/**
	 * @description 首字母小写
	 * @param str
	 *            转换的字符串
	 * @return String 转换的结果
	 * @author
	 * @version 2014-03-24
	 */
	public static String firstLower(String str) {
		if (trimIsEmpty(str))
			return "";
		StringBuffer resultBuffer = new StringBuffer();
		resultBuffer.append(str.substring(0, 1).toLowerCase());
		resultBuffer.append(str.substring(1));
		String result = resultBuffer.toString();
		return result;
	}

	/**
	 * @description 首字母大写
	 * @param str
	 *            转换的字符串
	 * @return String 转换的结果
	 * @author
	 * @version 2014-03-24
	 */
	public static String firstUpper(String str) {
		if (trimIsEmpty(str))
			return "";
		StringBuffer resultBuffer = new StringBuffer();
		resultBuffer.append(str.substring(0, 1).toUpperCase());
		resultBuffer.append(str.substring(1));
		String result = resultBuffer.toString();
		return result;
	}

	/**
	 * @description 判断去掉空格后是否为空字符串
	 * @param str
	 *            判断的字符串
	 * @return boolean 判断的结果
	 * @author
	 * @version 2014-03-24
	 */
	public static boolean trimIsEmpty(String str) {
		if (str == null) {
			return true;
		}
		String newStr = str.trim();
		return isEmpty(newStr);
	}

	/**
	 * @description 判断是否为空字符串
	 * @param str
	 *            判断的字符串
	 * @return boolean 判断的结果
	 * @author
	 * @version 2014-03-24
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * @description 获得jsp文件的文件名
	 * @param state
	 *            true 返回带.jsp的名字 否则返回去掉.jsp的名字
	 * @return String 返回jsp的文件名
	 * @author
	 * @version 2014-03-24
	 */
	public static String getJspName(String name, boolean state) {
		if (isEmpty(name)) {
			return name;
		}
		if (state) {
			if (name.indexOf(".jsp") != -1) {
				return name;
			} else {
				return name + ".jsp";
			}
		} else {
			if (name.indexOf(".jsp") != -1) {
				return name.substring(0, name.indexOf(".jsp"));
			} else {
				return name;
			}
		}
	}

	/**
	 * @description 判断是否是数字
	 * @param str
	 *            判断的字符串
	 * @return boolean 判断的结果
	 * @author
	 * @version 2014-03-24
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	
	/**
	 *  转义SQL语句、字段中的通配符。'_'转化为'\_','%'转化为'\%'
	 *  此方法不建议使用，系统全局控制
	  * @param str 原字符串
	  * @return 转义后的字符串
	  * @author 孙纪福
	  * @version 1.0 2014年6月4日 下午4:10:34
	  * @see StringUtil#escapeSQLSpecialChar(String)
	 */
	@Deprecated
	public static String escapeSQLWildcard(String str){
		if (isEmpty(str)) {
			return str;
		}
		return str.replace("_", "\\_");
//		return str;
	}
	
	
	/**
	  * 转义SQL特殊字符
	  * @param str 需要转投的字符串
	  * @return 处理后的字符串
	  * @author 孙纪福
	  * @version 1.0 2014年7月18日 上午9:12:29
	*/
	public static String escapeSQLSpecialChar(String str){
		if (isEmpty(str)) {
			return str;
		}
		return str.replace("'", "''").replace("%", "\\%");
	}
	
	/**
	 * 方法描述：过滤字符串的<style><style>标签
	 * @param inputString
	 * @return
	 * @author 王世元
	 * @version  2014年6月17日下午3:52:14
	 * @see
	 */
	public static String html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		try {
//			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script>]*?>[\s\S]*?<\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style>]*?>[\s\S]*?<\/style>
			// }
//			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

//			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
//			m_script = p_script.matcher(htmlStr);
//			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

//			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
//			m_html = p_html.matcher(htmlStr);
//			htmlStr = m_html.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;
	}
	
	/**
	 * 方法描述：过滤字符串的<html>标签
	 * @param inputString
	 * @return
	 * @author 王世元
	 * @version  2014年6月17日下午3:52:14
	 * @see
	 */
	public static String filterHtml(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script>]*?>[\s\S]*?<\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style>]*?>[\s\S]*?<\/style>
			// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			textStr = htmlStr;
		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;
	}
	
	/**
	 * 方法描述：截取字符串前12个字符
	 * @param inputString
	 * @return  截取后的字符串
	 * @author 王世元
	 * @version  2014年9月15日下午3:52:14
	 * @see
	 */
	public static String getTitle4M(String inputString){
		String outString = "";
		if (inputString.length() > 15){
			outString = inputString.substring(0,12) + "...";
		}else{
			outString = inputString;
		}
		
		return outString;
	}
	
	/**
	 * 替换&nbsp为空格字符
	 * @param inputString
	 * @return
	 */
	public static String replaceNBSP(String inputString){
		String outString = "";
		if (!StringUtil.isEmpty(inputString)){
			outString = inputString.replaceAll("&nbsp;", " ");
		}
		
		return outString;
	}
}
