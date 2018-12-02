package com.jc.system.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-24
 * 
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static String[] parsePatterns = { "yyyy-MM-dd",
			"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
			"yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm","yyyy-MM" };

	// public static final long DAY_

	// public static final int FIRST_DAY_OF_WEEK = 7;

/*	public static Date nowDate() {
		return new Date();
	}

	public static long nowLong() {
		return System.currentTimeMillis();
	}*/

	public static Timestamp nowTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Date getSysDate() {

		return new Date();
	}

	/**
	 *  得到当前日期字符串 格式（yyyy-MM-dd）
	 * @return 转换后的字符串
	 * @author
	 * @version 2014-03-24
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 *  得到当前日期字符串 格式（yyyy-MM-dd）
	 * @param pattern
	 *            格式化字符串 可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 * @return 转换后的字符串
	 * @author
	 * @version 2014-03-24
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 *  得到日期字符串 默认格式（yyyy-MM-dd）
	 * @param pattern
	 *            格式化字符串 可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 * @return 转换后的字符串
	 * @author
	 * @version 2014-03-24
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 *  得到当前时间字符串 格式（HH:mm:ss）
	 * @return 转换后的字符串
	 * @author
	 * @version 2014-03-24
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 *  得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 * @return 转换后的字符串
	 * @author
	 * @version 2014-03-24
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 *  得到当前年份字符串 格式（yyyy）
	 * @return 转换后的字符串
	 * @author
	 * @version 2014-03-24
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 *  得到当前月份字符串 格式（MM）
	 * @return 转换后的字符串
	 * @author
	 * @version 2014-03-24
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 *  得到当天字符串 格式（dd）
	 * @return 转换后的字符串
	 * @author
	 * @version 2014-03-24
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}
	
	/** 方法描述：得到当天数值 （一个月中的某天）
	 * @param date 日期
	 * @return 转换后的字符串
	 * @author 金峰
	 * @version  2014年5月9日下午1:21:33
	 * @see
	 */
	public static int getDay(Date date) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 *  得到当前星期字符串 格式（E）星期几
	 * @return 转换后的字符串
	 * @author
	 * @version 2014-03-24
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 *  日期型字符串转化为日期 格式
	 * @param str
	 *            时间字符串 格式可以为{ "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss",
	 *            "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss",
	 *            "yyyy/MM/dd HH:mm" }
	 * @return 转换后的时间
	 * @author
	 * @version 2014-03-24
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 *  获取过去的天数
	 * @param date
	 *            过去的时间
	 * @return 已经过去的天数
	 * @author
	 * @version 2014-03-24
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24L * 60 * 60 * 1000);
	}
	
	
	/** 方法描述：获取时间的差值（天数）
	 * @param date1  被减数 
	 * @param date2  减数
	 * @return 差值天数
	 * @author 金峰
	 * @version  2014年5月9日下午1:20:15
	 * @see
	 */
	public static long subtractionDays(Date date1,Date date2) {
		long t = date1.getTime() - date2.getTime();
		return t / (24L * 60 * 60 * 1000);
	}
	
	/** 方法描述：获取时间的差值（分钟数）
	 * @param date1  被减数 
	 * @param date2  减数
	 * @return 差值分钟数
	 * @author 
	 * @version  2014年5月9日下午1:20:15
	 * @see
	 */
	public static long subtractionMinute(Date date1,Date date2) {
		Calendar c1 = Calendar.getInstance();
        c1.clear();
        Calendar c2 = Calendar.getInstance();
        c2.clear();
        c1.setTime(date1);
        c2.setTime(date2);

        long time1 = c1.getTimeInMillis();
        long time2 = c2.getTimeInMillis();
        long diff = time2 - time1;
        
       return diff / (60 * 1000);
	}
	
	/** 方法描述：获取时间加n（天数）后的时间 
	 * @param date 原时间
	 * @param n 需要加的天数 （n可以为负值，做减法用）
	 * @return 加n天之后的时间
	 * @author 金峰
	 * @version  2014年5月9日下午1:18:45
	 * @see
	 */
	public static Date addOrSubtractDaysReturnDate(Date date,int n) {
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(date);   
		calendar.add(Calendar.DATE,n);   
		return calendar.getTime();   
	}
	
	/** 方法描述：获取时间加n（天数）后的时间
	 * @param date 原时间
	 * @param n 需要加的天数 （n可以为负值，做减法用）
	 * @return 加n天之后的时间 字符串类型
	 * @author 金峰
	 * @version  2014年5月9日下午1:18:45
	 * @see
	 */
	public static String addOrSubtractDaysReturnString(Date date,int n) {
		Date newDate=addOrSubtractDaysReturnDate(date,n);   
		return DateUtils.formatDate(newDate);   
	}
	
	/** 方法描述：根据传入日期获取是当月的哪一天
	 * @param date
	 * @return
	 * @author 金峰
	 * @version  2014年7月7日下午4:38:58
	 * @see
	 */
	public static int getDayByDate(Date date){
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(date);   
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/** 方法描述：根据传入日期获取是哪个月
	 * @param date
	 * @return
	 * @author 金峰
	 * @version  2014年7月7日下午4:38:58
	 * @see
	 */
	public static int getMonthByDate(Date date){
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(date);   
		return calendar.get(Calendar.MONTH);
	}
	
	/**
	 *  获取当前毫秒
	 * @return 获取毫秒数
	 * @author
	 * @version 2014-03-24
	 */
	public static String currentTimeMillis() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * 获取指定年份月份的全部日期及每天是周几
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @author 孙纪福
	 * @version 1.0 2014年4月25日 下午1:36:28
	 */
	public static Integer[] getWeekdays(int year, int month) {
		int lastDay = getMonthDays(year, month);
		Integer[] weekdays = new Integer[lastDay];
		int firstWeekday = getWeekday(year, month);
		for (int i = 0; i < weekdays.length; i++) {
			int temp = (firstWeekday + i) % 7;
			if (temp == 0) {
				temp = 7;
			}
			weekdays[i] = temp;
		}
		return weekdays;
	}

	/**
	 * 获取指定年份、月份条件下月份总天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @see #getMonthDays(Date)
	 * @see #getMonthDays(long)
	 * @author 孙纪福
	 * @version 1.0 2014年4月25日 下午1:36:28
	 */
	public static int getMonthDays(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return getMonthDays(calendar.getTimeInMillis());
	}

	/**
	 * 获取指定日期对象戳的所在月份的总天数
	 * 
	 * @param time
	 * @return
	 * @see #getMonthDays(long)
	 * @author 孙纪福
	 * @version 1.0 2014年4月25日 下午1:36:28
	 */
	public static int getMonthDays(Date date) {
		return getMonthDays(date.getTime());
	}

	/**
	 * 获取指定时间戳的所在月份的总天数
	 * 
	 * @param time
	 * @return
	 * @author 孙纪福
	 * @version 1.0 2014年4月25日 下午1:36:28
	 */
	public static int getMonthDays(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return lastDay;
	}

	/**
	 * 获取指定日期是星期几
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 * @see #getWeekday(long)
	 * @see #getWeekday(Date)
	 * @see #getWeekday(int, int)
	 * @author 孙纪福
	 * @version 1.0 2014年4月25日 下午1:36:28
	 */
	public static int getWeekday(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取指定年月的第一天是星期几
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 * @see #getWeekday(long)
	 * @see #getWeekday(Date)
	 * @see #getWeekday(int, int, int)
	 * @author 孙纪福
	 * @version 1.0 2014年4月25日 下午1:36:28
	 */
	public static int getWeekday(int year, int month) {
		return getWeekday(year, month, 1);
	}

	/**
	 * 获取指定日期是星期几
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 * @see #getWeekday(long)
	 * @see #getWeekday(int, int)
	 * @see #getWeekday(int, int, int)
	 * @author 孙纪福
	 * @version 1.0 2014年4月25日 下午1:36:28
	 */
	public static int getWeekday(Date date) {
		return getWeekday(date.getTime());
	}

	/**
	 *  获取指定时间戳是星期几
	 * @param time
	 * @return
	 * @see #getWeekday(Date)
	 * @see #getWeekday(int, int)
	 * @see #getWeekday(int, int, int)
	 * @author 孙纪福
	 * @version 1.0 2014年4月25日 下午1:36:28
	 */
	public static int getWeekday(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取指定年月显示当月各周是第几周，该处若有周跨月需全部显示出来。例如：2014年1月共计五周，分别是14年的第一周至第五周
	 *              。
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return 星期数组
	 * @author 孙纪福
	 * @version 1.0 2014年4月25日 下午1:34:15
	 */
	public static Integer[] getWeeks(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int startWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		calendar.set(year, month, 0);
		int endWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		if(endWeek<startWeek){
			int max = calendar.getActualMaximum(Calendar.WEEK_OF_YEAR);
			endWeek = max+endWeek;
		}
		Integer[] weeks = new Integer[endWeek - startWeek + 1];
		for (int i = 0; i < weeks.length; i++) {
			weeks[i] = startWeek + i;
		}
		return weeks;
	}

	/**
	 * 获取指定年月显示当月各周是第几周，该处若有周跨月需全部显示出来。例如：2014年1月共计五周，分别是14年的第一周至第五周。
	 * 产品部门提出修改，业务确定每年只取前52周
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return 星期数组 星期数最大为52周
	 * @author 孙纪福
	 * @version 1.0 2014年4月25日 下午1:34:15
	 */
	public static Integer[] getWeeks52(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int startWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		calendar.set(year, month, 0);
		int endWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		if(endWeek<startWeek){
			int max = calendar.getActualMaximum(Calendar.WEEK_OF_YEAR);
			endWeek = max+endWeek;
		}
		Integer[] weeks = new Integer[endWeek - startWeek + 1];
		for (int i = 0; i < weeks.length; i++) {
			int temp = startWeek + i;
			if(temp<=52){
				weeks[i] = temp;
			}
		}
		return weeks;
	}
	
	/**
	 *  根据日期获取该日期所在周的第一天和最后一天
	 * @param date
	 * @return
	 * @author 孙纪福
	 * @version 1.0 2014年4月25日 下午1:31:37
	 */
	public static Date[] getWeekStartAndEndDate(Date date) {
		int weekday = getWeekday(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1 * weekday + 1);
		Date startDate = calendar.getTime();
		calendar.add(Calendar.DATE, 6);
		Date endDate = calendar.getTime();
		return new Date[] { startDate, endDate };
	}

	/**
	 * 
	 *  根据日期获取该日期所在周的工作日第一天和最后一天（周一，周五）
	 * @param date
	 * @return 每周工作日的起止日期
	 * @author 孙纪福
	 * @version 1.0 2014年4月25日 下午1:30:28
	 */
	public static Date[] getWeekStartAndEndWorkDate(Date date) {
		int weekday = getWeekday(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 2 - weekday);
		Date startDate = calendar.getTime();
		calendar.add(Calendar.DATE, 4);
		Date endDate = calendar.getTime();
		return new Date[] { startDate, endDate };
	}

	/**
	 *  根据日期获取该日期所在周的工作日第一天和最后一天（周一，周五）
	 * @return
	 * @author 孙纪福
	 * @version 1.0 2014年4月25日 下午1:32:42
	 */
	public static Date[] getWeekStartAndEndWorkDate() {
		return getWeekStartAndEndWorkDate(new Date());
	}

	/**
	 *  获取当前日期所在周的第一天和最后一天
	 * @return
	 * @author 孙纪福
	 * @version 1.0 2014年4月25日 下午1:32:55
	 */
	public static Date[] getWeekStartAndEndDate() {
		return getWeekStartAndEndDate(new Date());
	}

	/**
	 *  根据日期获取该日期所在周是全年的第几周
	 * @param date 日期对象
	 * @return 全年的第几周序号
	 * @see #getWeekOfYear()
	 * @author 孙纪福
	 * @version 1.0 2014年4月25日 下午1:33:18
	 */
	public static int getWeekOfYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 *  获取当前日期所在周是全年的第几周
	 * @return 全年的第几周序号
	 * @author 孙纪福
     * @see #getWeekOfYear(Date)
	 * @version 1.0 2014年4月25日 下午1:33:53
	 */
	public static int getWeekOfYear() {
		return getWeekOfYear(new Date());
	}

	/**
	 * 当日期的时分秒为0:0:0时，填充为23:59:59
	 * @param date 需要填充的日期
	 * @return 返回处理后的日期
	 * @author 孙纪福
	 * @version 1.0 2014年5月4日 上午9:52:20
	 */
	public static Date fillTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hour = calendar.get(Calendar.HOUR);
		int min = calendar.get(Calendar.MINUTE);
		int sec = calendar.get(Calendar.SECOND);
		if (hour == 0 && min == 0 && sec == 0) {
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			return calendar.getTime();
		} else {
			return date;
		}

	}

	/**
	 * 获取某年的第几周是在第几月
	 * @param year 指定年
	 * @param weeks 星期数
	 * @return 返回某年的第几周是在第几月,如果传入的周大于全年实际星期数直接返回12月，对于周跨月情况，按前半段所在月返回
	 */
	public static int getMonth(int year,int week){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		int maxWeek = calendar.getActualMaximum(Calendar.WEEK_OF_YEAR);
		if(week>maxWeek) {
//			throw new IllegalArgumentException("参数week值["+week+"]大于"+year+"年最大星期数"+maxWeek);
			return 12;
		}
		else{
			calendar.set(Calendar.WEEK_OF_YEAR, week);
			return calendar.get(Calendar.MONTH)+1;
		}
	}
	private static void printArray(Object[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + "  ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		;
		System.out.println(getMonth(2014, 10));
		printArray(getWeeks(2013, 12));
		printArray(getWeekStartAndEndDate());
		printArray(getWeekStartAndEndWorkDate());
	}
}
