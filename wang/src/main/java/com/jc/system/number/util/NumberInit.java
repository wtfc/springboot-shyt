package com.jc.system.number.util;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;

import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.job.CustomJob;
import com.jc.system.number.domain.NumberRule;
import com.jc.system.number.service.INumberRuleService;

/**
 * 
 * @description: 定时初始化编号初始值 
 * @created: 2013-5-1 上午2:05:31 
 * @version：$Id: NumberInit.java 55637 2014-07-25 13:12:21Z gaoy $ 
 */
public class NumberInit extends CustomJob {
	
	private INumberRuleService numberRuleService = SpringContextHolder.getBean(INumberRuleService.class);
	
	private Logger log = Logger.getLogger(this.getClass());
	
	public NumberInit(){
	}

	@Override
	public void run(JobExecutionContext jobExecutionContext) {
		try{
			List<NumberRule> list = numberRuleService.queryAll(new NumberRule());
			if(list != null){
				for(NumberRule nr:list){
					if (nr.getNumberResetRules() == Constants.NUMBER_RESET_RULES_MONTH) {
						Calendar calendar = Calendar.getInstance();
						int day = calendar.get(Calendar.DAY_OF_MONTH);
						int month = calendar.get(Calendar.MONTH) + 1;
						boolean flag = true;
						if(StringUtils.isNotEmpty(nr.getNumberResetState())){
							if(month == Integer.parseInt(nr.getNumberResetState())){
								flag = false;
							}
						}
						if (day == 1 && flag) {
							NumberRule temp = new NumberRule();
							temp.setRuleName(nr.getRuleName());
							temp.setNumberValue("");
							temp.setNumberResetState(String.valueOf(month));
							temp.setNumberStateValue(2);
							int state = numberRuleService.update(temp);
							if (state != 1)
							{
								log.error(nr.getRuleName()+"初始化流水号失败!");
							} else {
								log.error(nr.getRuleName()+"初始化流水号成功!");
							}
						} 
					}
					else if (nr.getNumberResetRules() == Constants.NUMBER_RESET_RULES_YEAR) {
						Calendar calendar = Calendar.getInstance();
						int month = calendar.get(Calendar.MONTH) + 1;
						int day = calendar.get(Calendar.DAY_OF_MONTH);
						int year = calendar.get(Calendar.YEAR);
						boolean flag = true;
						if(StringUtils.isNotEmpty(nr.getNumberResetState())){
							if(year == Integer.parseInt(nr.getNumberResetState())){
								flag = false;
							}
						}
						if (month == 1 && day == 1 && flag) {
							NumberRule temp = new NumberRule();
							temp.setRuleName(nr.getRuleName());
							temp.setNumberValue("");
							temp.setNumberResetState(String.valueOf(year));
							temp.setNumberStateValue(2);
							int state = numberRuleService.update(temp);
							if (state != 1)
							{
								log.error(nr.getRuleName()+"初始化流水号失败!");
							} else {
								log.error(nr.getRuleName()+"初始化流水号成功!");
							}
						} 
					}
					else if(nr.getNumberResetRules() == Constants.NUMBER_RESET_RULES_DAY){
						NumberRule temp = new NumberRule();
						temp.setRuleName(nr.getRuleName());
						temp.setNumberValue("");
						temp.setNumberStateValue(2);
						int state = numberRuleService.update(temp);
						if (state != 1)
						{
							log.error(nr.getRuleName()+"初始化流水号失败!");
						} else {
							log.error(nr.getRuleName()+"初始化流水号成功!");
						}
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			log.error(e.toString());
		}
	}
}
