package com.jc.system.number.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jc.system.number.domain.NumberRule;
import com.jc.system.number.domain.Parameter;
import com.jc.system.number.util.Common;
import com.jc.system.number.util.Constants;

/**
 * 
 * @description: 生成时间字符串
 * @created: 2013-4-1 上午10:56:22
 */
public class DateNumber {

	private static Logger log = Logger.getLogger(DateNumber.class);

	/**
	 * 
	 * @param nr
	 *            实体类
	 * @param par
	 *            参数对象
	 * @return
	 * @throws ParameterParsingException
	 */
	public synchronized static String getDateNumber(NumberRule nr, Parameter par)
			throws Exception {
		// 日期类型默认
		if (nr.getDateType() == Constants.DATE_TYPE_DEFAULT) {
			return defaultDateType(nr, par);
		}
		// 日期类型手动
		else if (nr.getDateType() == Constants.DATE_TYPE_MANUAL) {
			return manualDateType(nr, par);
		}
		return null;
	}

	/**
	 * 手动配置时间格式
	 * 
	 * @param nr
	 * @param par
	 * @return
	 * @throws ParameterParsingException
	 */
	private static String manualDateType(NumberRule nr, Parameter par)
			throws Exception {
		// 有日期格式
		if (StringUtils.isNotEmpty(nr.getDateFormat())) {
			DefaultParameter parameterNumber = new DefaultParameter();
			String dateFormat[] = nr.getDateFormat().split("-");
			// 获取传递的参数
			String p[] = par.getParameter(1, 7);
			// 有日期分隔符
			if (StringUtils.isNotEmpty(nr.getDateSplit())) {
				// 分隔符为特殊格式：年月日……
				if (nr.getDateSplitState() == Constants.DATE_SPLIT_STATE_H) {
					String split[] = nr.getDateSplit().split("-");
					// 获取默认的参数
					String dp[] = parameterNumber.getParameter(nr, 1, 7);
					return bindDateNumberForManual(dateFormat, p, dp, split);
				}
				// 分隔符为普通分隔符
				else {
					// 获取默认的参数
					String dp[] = parameterNumber.getParameter(nr, 1, 7);
					return bindDateNumberForManual(dateFormat, p, dp,
							nr.getDateSplit());
				}
			}
			// 无日期分隔符
			else {
				// 获取默认的参数
				String dp[] = parameterNumber.getParameter(nr, 1, 7);
				return bindDateNumberForManual(dateFormat, p, dp, null);
			}
		}
		// 没有日期格式
		else {
			return new String("");
		}
	}

	/**
	 * 
	 * @param dateFormat
	 *            日期格式
	 * @param p
	 *            传递的参数
	 * @param dp
	 *            默认的参数
	 * @param split
	 *            分隔符
	 * @return 拼成的日期字符
	 */
	private static String bindDateNumberForManual(String dateFormat[],
			String p[], String dp[], Object split) throws Exception {
		StringBuffer date = new StringBuffer();
		for (int i = 0; i < dateFormat.length; i++) {
			// 格式化日期
			date.append(Common.getDateStr(dateFormat[i]));
			// 添加传递的参数
			if (StringUtils.isNotEmpty(p[i])) {
				date.append(p[i]);
			}
			// 添加默认的参数
			else {
				date.append(dp[i]);
			}
			// 添加分隔符
			if (split != null) {
				if (split instanceof Object[]) {
					String s[] = (String[]) split;
					date.append(s[i]);
				} else if (split instanceof String) {
					date.append((String) split);
				}
			}
		}
		return date.toString();
	}

	/**
	 * 默认时间格式
	 * 
	 * @param nr
	 * @param par
	 * @return
	 */
	private static String defaultDateType(NumberRule nr, Parameter par) throws Exception {
		// 有日期格式
		if (StringUtils.isNotEmpty(nr.getDateFormat())) {
			String dateFormat[] = nr.getDateFormat().split("-");
			// 有日期分隔符
			if (StringUtils.isNotEmpty(nr.getDateSplit())) {
				// 分隔符为特殊格式：年月日
				if (nr.getDateSplitState() == Constants.DATE_SPLIT_STATE_H) {
					String split[] = nr.getDateSplit().split("-");
					return bindDateNumberForDefault(dateFormat, split, nr, par);
				}
				// 普通分隔符
				else {
					return bindDateNumberForDefault(dateFormat,
							nr.getDateSplit(), nr, par);
				}
			}
			// 无日期分隔符
			else {
				return bindDateNumberForDefault(dateFormat, null, nr, par);
			}
		} else {
			return new String("");
		}
	}

	/**
	 * 
	 * @param dateFormat
	 *            日期格式
	 * @param split
	 *            分隔符
	 * @param nr
	 * @param par
	 * @return
	 */
	private static String bindDateNumberForDefault(String dateFormat[],
			Object split, NumberRule nr, Parameter par) throws Exception {
		StringBuffer date = new StringBuffer();
		for (int i = 0; i < dateFormat.length; i++) {
			// 添加日期格式
			date.append(dateFormat[i]);
			// 添加分隔符
			if (split != null) {
				if (split instanceof Object[]) {
					String s[] = (String[]) split;
					date.append(s[i]);
				} else if (split instanceof String) {
					date.append((String) split);
				}
			}
		}
		// 统一格式化日期并添加传递的参数
		String dateNumber = Common.getDateStr(date.toString());
		DefaultParameter pn = new DefaultParameter();
		return dateNumber.concat(pn.getParameterMiddleNumber(nr, par));
	}

}
