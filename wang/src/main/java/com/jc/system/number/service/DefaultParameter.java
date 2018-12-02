package com.jc.system.number.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jc.system.number.domain.NumberRule;
import com.jc.system.number.domain.Parameter;
import com.jc.system.number.util.Constants;

/**
 * 
 * @description: 处理默认的参数
 */
public class DefaultParameter {

	private Logger log = Logger.getLogger(this.getClass());
	private final String splitStr = "@@##"; 

	/**
	 * 拆分前缀
	 * @param nr 编号实体类
	 * @param p 参数实体类
	 * @return
	 */
	public String getParameterFirstNumber(NumberRule nr, Parameter p)
			throws Exception {
		if (StringUtils.isNotEmpty(p.getParameter()[0])) {
			return p.getParameter()[0].trim();
		} else {
			if (StringUtils.isNotEmpty(nr.getParameter())) {
				return nr.getParameter().split(",",-1)[0].replace(splitStr, "");
			} else
				return new String("");
		}
	}

	/**
	 * 根据数字下标获取默认参数数字
	 * @param nr 编号实体类
	 * @param startIndex 开始坐标
	 * @param endIndex 结束坐标
	 * @return
	 */
	public String[] getParameter(NumberRule nr, int startIndex, int endIndex)
			throws Exception {

		if (StringUtils.isNotEmpty(nr.getParameter())) {
			String str[] = nr.getParameter().split(",",-1);
			String s[] = new String[7];
			int j = 0;
			for (int i = startIndex; i <= endIndex; i++) {
				String temp = str[i].replace(splitStr, ""); 
				s[j] = temp;
				j++;
			}
			return s;
		} else {
			return new String[] { "", "", "", "", "", "", "" };
		}

	}

	/**
	 * 默认日期类型获取中间参数
	 * @param nr 编号实体类
	 * @param p 参数实体类
	 * @return
	 */
	public String getParameterMiddleNumber(NumberRule nr, Parameter p)
			throws Exception {
		String str[] = p.getParameter();
		if (StringUtils.isNotEmpty(str[1])) {
			return str[1];
		} else {
			String par[] = nr.getParameter().split(",",-1);
			if (StringUtils.isNotEmpty(par[1])) {
				return par[1].replace(splitStr, "");
			} else {
				return new String("");
			}
		}
	}

	/**
	 * 获取后缀
	 * @param nr 编号实体类
	 * @param p 参数实体类
	 * @return
	 */
	public String getParameterEndNumber(NumberRule nr, Parameter p)
			throws Exception {
		String str[] = p.getParameter();
		String par[] = nr.getParameter().split(",",-1);
		// 默认日期格式
		if (nr.getDateType() == Constants.DATE_TYPE_DEFAULT) {
			// 传参不为空返回参数
			if (StringUtils.isNotEmpty(str[2])) {
				return str[2];
			} else if (StringUtils.isNotEmpty(par[2])) {
				return par[2].replace(splitStr, "");
			} else {
				return new String("");
			}
		} else {
			if (StringUtils.isNotEmpty(str[str.length - 1])) {
				return str[str.length - 1];
			}
			if (StringUtils.isNotEmpty(par[par.length - 1])) {
				return par[par.length - 1].replace(splitStr, "");
			} else {
				return new String("");
			}
		}
	}
}
