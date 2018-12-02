package com.jc.system.job;

/**
 * @title 系统任务
 * @description 常量类
 * @version  2014-05-08 17:08
 */
public class CycleType {
	
	//周期类型:"1"固定间隔."2"周期循环."3"一次性
	//固定间隔
	public static final String CYCLETYPE_INTERVAL = "1";
	
	//周期循环
	public static final String CYCLETYPE_CYCLELOOP = "2";
	
	//一次性
	public static final String CYCLETYPE_ONCE = "3";
	
	//选择的周期类型
	public static final String CYCLESELECT_DAY = "day";
	
	//选择的周期类型
	public static final String CYCLESELECT_MONTH = "month";
		
	//选择的周期类型
	public static final String CYCLESELECT_WEEK = "week";
		
	//选择的周期类型
	public static final String CYCLESELECT_YEAR = "year";
	
	//选择月循环
	public static final String CYCLESELECT_MONTHLYDAY = "monthlyDay";
	
	//选择月循环方式特殊日期
	public static final String CYCLESELECT_MONTHLYLASTDAY = "monthlyLastDay";
	
	//选择月循环方式星期
	public static final String CYCLESELECT_MONTHLYDAYWEEK = "monthlyDayWeek";
	
	//选择年循环方式月日
	public static final String CYCLESELECT_YEARLYMD = "yearlyMD";
	
	//选择年循环方式指定月份最后一日
	public static final String CYCLESELECT_YEARLYMONTHL = "yearlyMonthL";
	
	//选择年循环方式星期
	public static final String CYCLESELECT_YEARLYLW = "yearlyLW";
	
	//系统任务状态：运行
	public static final String SYS_JOB_STATE_RUN = "1";
	
	//系统任务状态：暂停 
	public static final String SYS_JOB_STATE_PAUSE = "0";
	
	//符号: * 号
	public static final String SYMBOL_ASTERISK = "*";
	
	//符号: ? 号
	public static final String SYMBOL_QUESTION_MARK = "?";
	
	//符号: , 号
	public static final String SYMBOL_COMMA = ",";
	
	//符号: / 左斜线
	public static final String SYMBOL_LEFT_DIAGONAL = "/";
	
	//符号: # 井字号
	public static final String SYMBOL_POUND_SIGN = "#";
	
	//符号:   空格
	public static final String SYMBOL_BLANK = " ";
	
	//符号: 0  零
	public static final String SYMBOL_ZERO = "0";
	
	//符号: L L
	public static final String SYMBOL_L = "L";
}
