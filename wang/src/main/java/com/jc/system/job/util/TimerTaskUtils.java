package com.jc.system.job.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.jc.system.job.CycleType;
import com.jc.system.job.domain.TimerTask;

/**
 * @title 系统任务
 * @description 业务共通类
 * @version 2014-05-08 17:08
 */
public class TimerTaskUtils {

	/**
	 * @description 根据对应的数据生成系统任务需要的trigger
	 * @param TimerTask timerTask 实体bean
	 * @return Trigger 
	 * @version 2014-05-12 15:50
	 */
	public static Trigger getTrigger(TimerTask timerTask) {
		
		TriggerBuilder<Trigger> builder = TriggerBuilder
				.newTrigger()
				.withIdentity(String.valueOf(timerTask.getId()),
						Scheduler.DEFAULT_GROUP)
				.withDescription(timerTask.getDescription());
		
		//固定间隔时的处理
		if(CycleType.CYCLETYPE_INTERVAL.equals(timerTask.getCycleType())){
			
			//指定次数间隔处理
			if(timerTask.getTaskCounts() != null && 
					!timerTask.getTaskCounts().equals("")){
				builder.withSchedule(SimpleScheduleBuilder.simpleSchedule()
						//间隔小时数
						.withIntervalInHours(Integer.parseInt(timerTask.getIntervalHours()))
						//间隔分钟数
						.withIntervalInMinutes(Integer.parseInt(timerTask.getIntervalMinutes()))
						//一共需要执行的次数
						.withRepeatCount(Integer.parseInt(timerTask.getTaskCounts())-1));
			//无限次循环
			}else{
				builder.withSchedule(SimpleScheduleBuilder.simpleSchedule()
						//间隔小时数
						.withIntervalInHours(Integer.parseInt(timerTask.getIntervalHours()))
						//间隔分钟数
						.withIntervalInMinutes(Integer.parseInt(timerTask.getIntervalMinutes()))
						//无限制循环
						.repeatForever());
			}
			
		//一次性时的处理
		}else if(CycleType.CYCLETYPE_ONCE.equals(timerTask.getCycleType())){
			//一次性时的处理
			builder.withSchedule(SimpleScheduleBuilder.simpleSchedule());
		//循环周期
		}else{
			//系统任务运行方式为CronExpression表达式的方式
			builder.withSchedule(CronScheduleBuilder.cronSchedule(timerTask
					.getCronExpression()));
		}
		
		//存在开始时间的情况
		if (timerTask.getStartAt() != null) {
			builder.startAt(timerTask.getStartAt());
		}
		
		//存在结束时间的情况
		if (timerTask.getEndAt() != null) {
			builder.endAt(timerTask.getEndAt());
		}
		return builder.build();
	}

	/**
	 * @description 根据相关数据生成系统任务需要的job
	 * @param TimerTask timerTask 实体bean
	 * @version 2014-05-12 15:50
	 */
	@SuppressWarnings("unchecked")
	public static JobDetail getJobDetail(TimerTask timerTask) throws Exception {

		//取得需要执行定时任务的类名称
		String className = timerTask.getJobClassName();

		//返回根据类名称生成的系统任务类
		return JobBuilder.newJob((Class<Job>) Class.forName(className)).build();

	}

	/**
	 * @description 获得triggerkey
	 * @param TimerTask timerTask 实体bean
	 * @return TriggerKey(值与数据库中的id相同) 
	 * @version 2014-05-12 15:50
	 */
	public static TriggerKey getTriggerKey(TimerTask timerTask) {

		return getTriggerKey(timerTask.getId());

	}

	/**
	 * @description 获得triggerkey
	 * @param timerTaskId timerTask 实体bean中的id
	 * @return TriggerKey(值与数据库中的id相同) 
	 * @version 2014-05-12 15:50
	 */
	public static TriggerKey getTriggerKey(Long timerTaskId) {

		return TriggerKey.triggerKey(timerTaskId.toString(),
				Scheduler.DEFAULT_GROUP);

	}
	
	/**
	 * @description 生成任务详情
	 * @param TimerTask timerTask 实体bean
	 * @return TimerTask
	 * @version 2014-05-12 15:50
	 */
	public static TimerTask generateCycleDetail(TimerTask timerTask){
		
		//循环详情
		String cycleDetail ="";
		Map<String, String> hashmap = new HashMap<String, String>();
		
		//固定间隔的情况
		if(CycleType.CYCLETYPE_INTERVAL.equals(timerTask.getCycleType())){
			hashmap.put("intervalHours", timerTask.getIntervalHours());
			hashmap.put("intervalMinutes", timerTask.getIntervalMinutes());
			hashmap.put("taskCounts", timerTask.getTaskCounts());
			cycleDetail = hashMapToJson(hashmap);
			
		//周期循环的情况
		}else if(CycleType.CYCLETYPE_CYCLELOOP.equals(timerTask.getCycleType())){
			
			//选中的循环类型
			String cycleSelect = timerTask.getCycleSelect();
			hashmap.put("cycleSelect", cycleSelect);
			
			//以天循环
			if(CycleType.CYCLESELECT_DAY.equals(cycleSelect)){
				
			//以周循环
			}else if(CycleType.CYCLESELECT_WEEK.equals(cycleSelect)){
				hashmap.put("weekly", timerTask.getWeekly().replaceAll(",", ";"));
				
			//以月循环
			}else if(CycleType.CYCLESELECT_MONTH.equals(cycleSelect)){
				String monthly = timerTask.getMonthly();
				hashmap.put("monthly", monthly);
				
				if(CycleType.CYCLESELECT_MONTHLYDAY.equals(monthly)){
					
					hashmap.put("monthlyDay", timerTask.getMonthlyDay());
					
				}else if(CycleType.CYCLESELECT_MONTHLYDAYWEEK.equals(monthly)){
					
					hashmap.put("monthlyLast", timerTask.getMonthlyLast());
					hashmap.put("monthlyLastWeek", timerTask.getMonthlyLastWeek());
					
				}
			
			//以年循环
			}else if(CycleType.CYCLESELECT_YEAR.equals(cycleSelect)){
				String yearly = timerTask.getYearly();
				hashmap.put("yearly", yearly);
				
				if(CycleType.CYCLESELECT_YEARLYMD.equals(yearly)){
					
					hashmap.put("yearlyMonthForDay", timerTask.getYearlyMonthForDay());
					hashmap.put("yearlyDay", timerTask.getYearlyDay());
					
				}else if(CycleType.CYCLESELECT_YEARLYMONTHL.equals(yearly)){
					
					hashmap.put("yearlyMonthForLast", timerTask.getYearlyMonthForLast());
					
				}else if(CycleType.CYCLESELECT_YEARLYLW.equals(yearly)){
					
					hashmap.put("yearlyLast", timerTask.getYearlyLast());
					hashmap.put("yearlyLastWeek", timerTask.getYearlyLastWeek());
					
				}
			}

			//每天时
			hashmap.put("perHours", timerTask.getPerHours());
			//每天分
			hashmap.put("perMinutes", timerTask.getPerMinutes());
			
			//根据数据换换成json串
			cycleDetail = hashMapToJson(hashmap);
		}
		
		timerTask.setCycleDetail(cycleDetail);
		return timerTask;
	}

	/**
	 * @description 生成CronExpression表达式
	 * @param TimerTask timerTask 实体bean
	 * @return TimerTask
	 * @version 2014-05-12 15:50
	 */
	public static TimerTask generateCronExpression(TimerTask timerTask){
		
		//间隔任务时不做此处理
		if(CycleType.CYCLETYPE_INTERVAL.equals(timerTask.getCycleType())
				|| CycleType.CYCLETYPE_ONCE.equals(timerTask.getCycleType())){
			timerTask.setCronExpression("");
			return timerTask;
		}

		//缓冲流的形式拼接字符串
		StringBuffer cronExpression = new StringBuffer();

		// 每天秒为0
		cronExpression.append(CycleType.SYMBOL_ZERO);

		//秒分之间追加空格
		cronExpression.append(CycleType.SYMBOL_BLANK);
		
		// 每天分不能为空
		if (timerTask.getPerMinutes() != null) {
			cronExpression.append(timerTask.getPerMinutes());
		}
		
		//分时之间追加空格
		cronExpression.append(CycleType.SYMBOL_BLANK);
		
		// 每天时不能为空
		if(timerTask.getPerHours() != null){
			cronExpression.append(timerTask.getPerHours());
		}
		
		//时日之间追加空格
		cronExpression.append(CycleType.SYMBOL_BLANK);
		
		// 以天循环
		if (CycleType.CYCLESELECT_DAY.equals(timerTask.getCycleSelect())) {

			//每日
			cronExpression.append(CycleType.SYMBOL_ASTERISK);
			
			//分时之间追加空格
			cronExpression.append(CycleType.SYMBOL_BLANK);
			
			//每月
			cronExpression.append(CycleType.SYMBOL_ASTERISK);
			
			//分时之间追加空格
			cronExpression.append(CycleType.SYMBOL_BLANK);
			
			//周为不指定
			cronExpression.append(CycleType.SYMBOL_QUESTION_MARK);
			
		// 以周循环
		}else if (CycleType.CYCLESELECT_WEEK.equals(timerTask.getCycleSelect())) {
			//日为不关注
			cronExpression.append(CycleType.SYMBOL_QUESTION_MARK);
			
			//日月之间追加空格
			cronExpression.append(CycleType.SYMBOL_BLANK);
			
			//月为每个月
			cronExpression.append(CycleType.SYMBOL_ASTERISK);
			
			//月周之间追加空格
			cronExpression.append(CycleType.SYMBOL_BLANK);
			
			//周为对应数据
			cronExpression.append(timerTask.getWeekly());
			
		// 以月循环
		}else if(CycleType.CYCLESELECT_MONTH.equals(timerTask.getCycleSelect())) {
			
			String monthly = timerTask.getMonthly();
			
			//每月第几日
			if(CycleType.CYCLESELECT_MONTHLYDAY.equals(monthly)){
				
				//日为对应数据
				cronExpression.append(timerTask.getMonthlyDay());
				
				//日月之间追加空格
				cronExpression.append(CycleType.SYMBOL_BLANK);
				
				//月为每个月
				cronExpression.append(CycleType.SYMBOL_ASTERISK);
				
				//月周之间追加空格
				cronExpression.append(CycleType.SYMBOL_BLANK);
				
				//周为不关注
				cronExpression.append(CycleType.SYMBOL_QUESTION_MARK);
			
			//每月最后一天
			}else if(CycleType.CYCLESELECT_MONTHLYLASTDAY.equals(monthly)){
				
				//日为L,表示最后一天
				cronExpression.append(CycleType.SYMBOL_L);
				
				//日月之间追加空格
				cronExpression.append(CycleType.SYMBOL_BLANK);
				
				//月为每个月
				cronExpression.append(CycleType.SYMBOL_ASTERISK);
				
				//月周之间追加空格
				cronExpression.append(CycleType.SYMBOL_BLANK);
				
				//周为不关注
				cronExpression.append(CycleType.SYMBOL_QUESTION_MARK);
				
			}else if(CycleType.CYCLESELECT_MONTHLYDAYWEEK.equals(monthly)){
				
				//最后一周的情况
				if(CycleType.SYMBOL_L.equals((timerTask.getMonthlyLast()))){
					
					//日为不关注
					cronExpression.append(CycleType.SYMBOL_QUESTION_MARK);
					
					//日月之间追加空格
					cronExpression.append(CycleType.SYMBOL_BLANK);
					
					//月为每个月
					cronExpression.append(CycleType.SYMBOL_ASTERISK);
					
					//月周之间追加空格
					cronExpression.append(CycleType.SYMBOL_BLANK);
					
					//最后一周的星期几
					cronExpression.append(timerTask.getMonthlyLastWeek());
					
					//周为不关注
					cronExpression.append(CycleType.SYMBOL_L);
				
				//每月第一天,第二天,第三天的情况
				}else{
					
					//日为不关注
					cronExpression.append(CycleType.SYMBOL_QUESTION_MARK);
					
					//日月之间追加空格
					cronExpression.append(CycleType.SYMBOL_BLANK);
					
					//月为每个月
					cronExpression.append(CycleType.SYMBOL_ASTERISK);
					
					//月周之间追加空格
					cronExpression.append(CycleType.SYMBOL_BLANK);
					
					//周为指定日期
					//星期
					cronExpression.append(timerTask.getMonthlyLastWeek());
					
					//井号
					cronExpression.append(CycleType.SYMBOL_POUND_SIGN);

					//第几个
					cronExpression.append(timerTask.getMonthlyLast());
					
				}
			}
		
		//以年循环
		}else if(CycleType.CYCLESELECT_YEAR.equals(timerTask.getCycleSelect())){
			
			String yearly = timerTask.getYearly();
			
			if(CycleType.CYCLESELECT_YEARLYMD.equals(yearly)){
				
				//日为指定日期
				cronExpression.append(timerTask.getYearlyDay());
				
				//日月之间追加空格
				cronExpression.append(CycleType.SYMBOL_BLANK);
				
				//月为每个月
				cronExpression.append(timerTask.getYearlyMonthForDay());
				
				//月周之间追加空格
				cronExpression.append(CycleType.SYMBOL_BLANK);
				
				//周为不考虑
				cronExpression.append(CycleType.SYMBOL_QUESTION_MARK);
				
			}else if(CycleType.CYCLESELECT_YEARLYMONTHL.equals(yearly)){
				
				//日为最后一日
				cronExpression.append(CycleType.SYMBOL_L);
				
				//日月之间追加空格
				cronExpression.append(CycleType.SYMBOL_BLANK);
				
				//月为每个月
				cronExpression.append(timerTask.getYearlyMonthForLast());
				
				//月周之间追加空格
				cronExpression.append(CycleType.SYMBOL_BLANK);
				
				//周为不考虑
				cronExpression.append(CycleType.SYMBOL_QUESTION_MARK);
				
			}else if(CycleType.CYCLESELECT_YEARLYLW.equals(yearly)){
				
				//每年月最后一周
				if(CycleType.SYMBOL_L.equals(timerTask.getYearlyLast())){
					
					//日为不考虑
					cronExpression.append(CycleType.SYMBOL_QUESTION_MARK);
					
					//日月之间追加空格
					cronExpression.append(CycleType.SYMBOL_BLANK);
					
					//月指定月份
					cronExpression.append(timerTask.getYearlyMonthForWeek());
					
					//月周之间追加空格
					cronExpression.append(CycleType.SYMBOL_BLANK);
					
					//最后一个星期几
					cronExpression.append(timerTask.getYearlyLastWeek());
					
					//周为最后一周
					cronExpression.append(CycleType.SYMBOL_L);
					
				}else{
					
					//日为不考虑
					cronExpression.append(CycleType.SYMBOL_QUESTION_MARK);
					
					//日月之间追加空格
					cronExpression.append(CycleType.SYMBOL_BLANK);
					
					//月指定月份
					cronExpression.append(timerTask.getYearlyMonthForWeek());
					
					//月周之间追加空格
					cronExpression.append(CycleType.SYMBOL_BLANK);
					
					//周为指定星期
					//星期几
					cronExpression.append(timerTask.getYearlyLastWeek());
					
					//井号
					cronExpression.append(CycleType.SYMBOL_POUND_SIGN);
					
					//第几个
					cronExpression.append(timerTask.getYearlyLast());
					
				}
			}
			
			//周年之间追加空格
			cronExpression.append(CycleType.SYMBOL_BLANK);
			
			//年为每一年
			cronExpression.append(CycleType.SYMBOL_ASTERISK);
		}
		
		timerTask.setCronExpression(cronExpression.toString());
		return timerTask;
	}
	
	/**
	 * @description 根据hashmap生成自用json格式
	 * @param HashMap
	 * @return json格式数据{xxx:xxx,xxx:xxx}
	 * @version 2014-05-12 15:50
	 */
	public static String hashMapToJson(Map<String,String> map) {  
        String json = "{";
        
        //迭代map
        for (Iterator<Entry<String, String>> it = map.entrySet().iterator(); it.hasNext();) {  
            Entry<String,String> e = (Entry<String, String>) it.next();
            
            //值为空的数据不转换字符串
            if(e.getValue() == null || e.getValue().equals("")){
            	continue;
            }
            
            //拼接字符串name:value形式
            json += e.getKey() + ":";  
            json += e.getValue() + ",";
        }
        
        //去掉最后面的逗号
        json = json.substring(0, json.lastIndexOf(","));  
        json += "}";  
        return json;  
    }

	/**
	 * @description 根据json字符串生成hashmap
	 * @param json字符串
	 * @return Map
	 * @version 2014-05-12 15:50
	 */
	public static Map<String,String> jsonToHashMap(String json) {
		Map<String, String> hashmap = new HashMap<String, String>();
		
		//判断是否有json的情况
		if(json.length()>1){
			
			//去掉大括号{}
			json = json.substring(1, json.length()-1);
			
			//根据逗号分组
			String group[]=json.split(",");
			
			//遍历分组
			for(String temp : group){
				//根据冒号:区分name和value
				String sub[] = temp.split(":");
				
				//将name和value保存到hashmap中.
				//由于特殊字段的数据是使用逗号分隔的,例如1,2,3.因此在保存时将其替换成了分号;.
				//因此在转换回元数据时,需要将分号;全部替换成逗号,
				hashmap.put(sub[0], sub[1].replaceAll(";", ","));
			}
		}
        return hashmap;  
    }

	/**
	 * @description 根据hashmap向bean中保存数据
	 * @param timerTask
	 * @return Map
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @version 2014-05-12 15:50
	 */
	public static TimerTask hashMapToBean(TimerTask timerTask, Map<String,String> hashMap) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		//循环遍历hashmap,将其中对应的key和value保存到bean中.
		Iterator<Entry<String, String>> entryKeyIterator = hashMap.entrySet().iterator();

		while (entryKeyIterator.hasNext()) {

			Entry<String, String> e = entryKeyIterator.next();

			//取得map中的key
			String key = e.getKey();
			
			//取得map中的value
			String value = e.getValue();

			//遍历实体Bean timerTask
			Class<? extends TimerTask> timerTaskClass = timerTask.getClass();
			
			//根据key值,并且将首字母大写,拼装set字符串,取得timerTask中的set方法
			Method setMethod = timerTaskClass.getDeclaredMethod("set"
					+ toUpperCaseFirstOne(key), String.class);
			
			//将对应数据保存在bean中
			setMethod.invoke(timerTask, value);
		}
		
        return timerTask;  
    }
	
	/**
	 * @description 首字母转大写
	 * @param String key
	 * @return Map
	 * @version 2014-05-12 15:50
	 */
    public static String toUpperCaseFirstOne(String key)
    {
    	//如果首字母已经是大写的情况
        if(Character.isUpperCase(key.charAt(0))){
            return key;
            
        //否则进行转换
        }else{
            return (new StringBuilder()).append(Character.toUpperCase(key.charAt(0))).append(key.substring(1)).toString();
        }
    }
}

