package com.jc.system.number.service;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jc.system.CustomException;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.number.domain.NumberRule;
import com.jc.system.number.util.Constants;

/**
 * 
 * @description:编号生成类
 * @created: 2013-4-1 下午3:16:00
 */
public class SerialNumber {
	
	private static INumberRuleService numberRuleService = SpringContextHolder.getBean(INumberRuleService.class);

	private static Logger log = Logger.getLogger(SerialNumber.class);

	// private static int waitCount = 1;

	/**
	 * @description 判断流水重置规则
	 * @param NumberRule nr 编号实体类
	 * @return String 生成的编号
	 * @author 高研
	 * @throws NumberWaitingTimeout
	 */
	public synchronized static String getSerialNumber(NumberRule nr){
		if (StringUtils.isEmpty(nr.getNumberStart())) {
			return new String("");
		}
		// 流水号重置规则按日重置
		if (nr.getNumberResetRules() == Constants.NUMBER_RESET_RULES_DAY) {
			return getNumberFlag(nr, nr.getNumberStart()).concat(addPlaceholder(nr,nr.getNumberStart()));
		}
		// 流水号重置规则按月重置
		else if (nr.getNumberResetRules() == Constants.NUMBER_RESET_RULES_MONTH) {
			return createNumber(nr);
		}
		// 流水号重置规则按年重置
		else if (nr.getNumberResetRules() == Constants.NUMBER_RESET_RULES_YEAR) {
			return createNumber(nr);
		}
		// 流水号无重置规则
		else if (nr.getNumberResetRules() == Constants.NUMBER_RESET_RULES_NO) {
			return createNumber(nr);
		}
		return null;
	}

	/**
	 * @description 根据规则生成流水号
	 * @param NumberRule nr 编号实体类
	 * @return String 生成的编号
	 * @author 高研
	 * @throws NumberWaitingTimeout
	 */
	private static String createNumber(NumberRule nr){
		try {
			NumberRule temp = new NumberRule();
			temp.setRuleName(nr.getRuleName());
			nr = numberRuleService.get(temp);
			//nr = dao.selectRuleByRuleName(nr.getRuleName());
			// 流水号连续
			if (nr.getNumberState() == Constants.NUMBER_STATE_CONTINUOUS) {
				return numberContinuous(nr);
			}
			// 流水号非连续
			else if (nr.getNumberState() == Constants.NUMBER_STATE_DISCONTINUOUS) { 
				return numberDisContinuous(nr);
			}
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 非连续的流水号
	 * 
	 * @param nr
	 * @return
	 */
	private static String numberDisContinuous(NumberRule nr) {
		String thisNumber = null;
		if (StringUtils.isEmpty(nr.getNumberValue())){
			thisNumber = nr.getNumberStart();
		} else {
			thisNumber = getNextValue(nr.getNumberValue());
		}
		//标识流水号已用
		NumberRule temp = new NumberRule();
		temp.setRuleName(nr.getRuleName());
		temp.setNumberValue(thisNumber);
		temp.setNumberStateValue(1);
		int state = 0;
		try {
			state = numberRuleService.update(temp);
		} catch (CustomException e) {
			e.printStackTrace();
		}
		//int state = dao.updateRuleValue(nr.getRuleName(), thisNumber, 1);
		if (state == 1){
			return getNumberFlag(nr, thisNumber).concat(addPlaceholder(nr,thisNumber));
		}
		else {
			return null;
		}
	}

	/**
	 * 连续的流水号
	 * 
	 * @param nr
	 * @return
	 */
	private static String numberContinuous(NumberRule nr) {
		// 当前流水号不可用
		if (nr.getNumberStateValue() == Constants.NUMBER_STATE_VALUE_USED) {
			String thisNumber = null;
			if (StringUtils.isEmpty(nr.getNumberValue())) {
				thisNumber = nr.getNumberStart();
			} else {
				thisNumber = getNextValue(nr.getNumberValue());
			}
			//标识流水号未用
			NumberRule temp = new NumberRule();
			temp.setRuleName(nr.getRuleName());
			temp.setNumberValue(thisNumber);
			temp.setNumberStateValue(2);
			int state = 0;
			try {
				state = numberRuleService.update(temp);
			} catch (CustomException e) {
				e.printStackTrace();
				return null;
			}
			//int state = dao.updateRuleValue(nr.getRuleName(), thisNumber, 2);
			if (state == 1)
				return getNumberFlag(nr, thisNumber).concat(addPlaceholder(nr,thisNumber));
			else
				return null;
		}
		// 当前流水号可用
		else if (nr.getNumberStateValue() == Constants.NUMBER_STATE_VALUE_UNUSED) {
			if (StringUtils.isEmpty(nr.getNumberValue()))
				return getNumberFlag(nr, nr.getNumberStart()).concat(addPlaceholder(nr,nr.getNumberStart()));
			else {
				//标识流水号未用
				NumberRule temp = new NumberRule();
				temp.setRuleName(nr.getRuleName());
				temp.setNumberStateValue(2);
				try {
					numberRuleService.update(temp);
				} catch (CustomException e) {
					e.printStackTrace();
					return null;
				}
				//dao.updateNumberStateValue(nr.getRuleName(), 2);
				return getNumberFlag(nr, nr.getNumberValue()).concat(addPlaceholder(nr,nr.getNumberValue()));
			}
		}
		/**
		else if (nr.getNumberStateValue() == Constants.NUMBER_STATE_VALUE_WAIT) {
			if (waitCount >= 5) {
				waitCount = 0;
				throw new NumberWaitingTimeout("Waiting for the timeout");
			}
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return null;
			}
			waitCount++;
			NumberRule nr1 = dao.selectRuleByRuleName(nr.getRuleName());
			return createNumber(nr1);
		}
		**/
		return null;
	}
	
	/**
	 * 添加流水号占位符
	 * @param nr
	 * @param number
	 * @return
	 */
	private static String addPlaceholder(NumberRule nr, String number) {
		try {
			if (StringUtils.isNotEmpty(number)) {
				if(number.length() < nr.getNumberDigit()) {
					String placeholder = "0";
					String newNumber = number;
					if(StringUtils.isNotEmpty(nr.getNumberPlaceholder())) {
						placeholder = nr.getNumberPlaceholder();
					}
					for(int i=0;i<nr.getNumberDigit()-number.length();i++) {
						newNumber = placeholder.concat(newNumber);
					}
					return newNumber;
				} else {
					return number;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			return null;
		}
		return null;
	}

	/**
	 * @description 获取下一个流水号
	 * @param String s 当前流水号
	 * @return String 生成的下一个流水号
	 * @author 高研
	 */
	private static String getNextValue(String s) {
		try {
			StringBuffer number = new StringBuffer("1").append(s);
			long l = Long.parseLong(number.toString());
			l++;
			String str = String.valueOf(l);
			str = str.substring(1, str.length());
			if (Long.parseLong(str) == 0){
				return new String("1").concat(str);
			} else {
				return str;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @description 判断流水号状态
	 * @param NumberRule nr,String number
	 * @return int 1正常 2流水号大于上限
	 * @author 高研
	 */
	private static String getNumberFlag(NumberRule nr, String number) {
		if (StringUtils.isNotEmpty(number)) {
			//没有上限返回成功
			if (StringUtils.isEmpty(nr.getNumberCeiling())){
				return Constants.SUCCESS;
			}
			//流水号大于上限值
			if (Long.parseLong(number) >= Long.parseLong(nr.getNumberCeiling())){
				return Constants.ERROR_CODE_NUMBER;
			} else {
				return Constants.SUCCESS;
			}
		}
		return Constants.ERROR_CODE_EXCEPTION;
	}
}
