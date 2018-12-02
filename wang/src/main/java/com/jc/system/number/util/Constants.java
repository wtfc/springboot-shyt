package com.jc.system.number.util;

/**
 * 
 * @description: 全局常量类 
 * @created: 2013-4-1 下午3:29:22 
 * @version：$Id: Constants.java 43813 2014-05-05 11:05:29Z gaoy $ 
 */
public class Constants {

	/**
	 * 流水重置规则：按日重置
	 */
	public static final int NUMBER_RESET_RULES_DAY = 1;
	/**
	 * 流水重置规则：按月重置
	 */
	public static final int NUMBER_RESET_RULES_MONTH = 2;
	/**
	 * 流水重置规则：按年重置
	 */
	public static final int NUMBER_RESET_RULES_YEAR = 3;
	/**
	 * 流水重置规则：无
	 */
	public static final int NUMBER_RESET_RULES_NO = 0;
	/**
	 * 流水号连续标识：连续
	 */
	public static final int NUMBER_STATE_CONTINUOUS = 1;
	/**
	 * 流水号连续标识：非连续
	 */
	public static final int NUMBER_STATE_DISCONTINUOUS = 2;
	/**
	 * 当前流水号状态为等待
	 */
	public static final int NUMBER_STATE_VALUE_WAIT = 0;
	/**
	 * 当前流水号不可用
	 */
	public static final int NUMBER_STATE_VALUE_USED = 1;
	/**
	 * 当前流水号可用
	 */
	public static final int NUMBER_STATE_VALUE_UNUSED = 2;
	/**
	 * 日期类型:默认选项 
	 */
	public static final int DATE_TYPE_DEFAULT = 1;
	/**
	 * 日期类型:手动
	 */
	public static final int DATE_TYPE_MANUAL = 2;
	/**
	 * 日期默认分隔符
	 */
	public static final String DATE_SPLIT_DEFAULT = "年月日";
	/**
	 * 日期手动分隔符
	 */
	public static final String DATE_SPLIT_MANUAL = "年月日小时分钟秒毫秒";
	/**
	 * 日期:普通分隔符
	 */
	public static final int DATE_SPLIT_STATE_P = 1;
	/**
	 * 日期:特殊分隔符(年月日……)
	 */
	public static final int DATE_SPLIT_STATE_H = 2;
	/**
	 * 日期:特殊分隔符手动收入
	 */
	public static final int DATE_SPLIT_STATE_S = 3;
	/**
	 * 返回值错误码：参数错误
	 */
	public static final String ERROR_CODE_PARAMETER = "6";
	/**
	 * 返回值错误码：程序异常
	 */
	public static final String ERROR_CODE_EXCEPTION = "7";
	/**
	 * 成功
	 */
	public static final String SUCCESS = "1";
	/**
	 * 返回值错误码：流水号大于上限
	 */
	public static final String ERROR_CODE_NUMBER = "-2";
	
}
