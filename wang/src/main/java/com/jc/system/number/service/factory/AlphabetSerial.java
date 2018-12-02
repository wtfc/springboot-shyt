package com.jc.system.number.service.factory;

import org.apache.commons.lang.StringUtils;

import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.number.domain.NumberRule;
import com.jc.system.number.service.INumberRuleService;
import com.jc.system.number.util.Constants;
import com.jc.system.security.service.ISystemService;

public class AlphabetSerial extends SerialNumberFactory{
	
	private static INumberRuleService numberRuleService = SpringContextHolder.getBean(INumberRuleService.class);
	
	@Override
	public synchronized String getSerialNumber(NumberRule numberRule) throws Exception {
		if (StringUtils.isEmpty(numberRule.getNumberStart())) {
			return new String("");
		}
		// 流水号重置规则按日重置
		if (numberRule.getNumberResetRules() == Constants.NUMBER_RESET_RULES_DAY) {
//			if(numberState(numberRule.getNumberCeiling(), numberRule.getNumberStart()).equals(Constants.ERROR_CODE_NUMBER)) {
//				return Constants.ERROR_CODE_NUMBER;
//			} else {
//				return numberRule.getNumberStart().toUpperCase();
//			}
			return createNumber(numberRule);
		}
		// 流水号重置规则按月重置
		else if (numberRule.getNumberResetRules() == Constants.NUMBER_RESET_RULES_MONTH) {
			return createNumber(numberRule);
		}
		// 流水号重置规则按年重置
		else if (numberRule.getNumberResetRules() == Constants.NUMBER_RESET_RULES_YEAR) {
			return createNumber(numberRule);
		}
		// 流水号无重置规则
		else if (numberRule.getNumberResetRules() == Constants.NUMBER_RESET_RULES_NO) {
			return createNumber(numberRule);
		}
		
		return null;
	}

	/**
	 * @description 根据规则生成流水号
	 * @param nr 编号实体类
	 * @return String 生成的编号
	 * @author 高研
	 * @throws Exception 
	 * @throws NumberWaitingTimeout
	 */
	public static String createNumber(NumberRule nr) throws Exception {
//		NumberRule temp = new NumberRule();
//		temp.setRuleName(nr.getRuleName());
//		nr = numberRuleService.get(temp);
		// 流水号连续
		if (nr.getNumberState() == Constants.NUMBER_STATE_CONTINUOUS) {
			return continuousNumber(nr);
		}
		// 流水号非连续
		else if (nr.getNumberState() == Constants.NUMBER_STATE_DISCONTINUOUS) {
			return disContinuousNumber(nr);
		}
		return null;
	}

	/**
	 * 非连续的流水号
	 * 
	 * @param nr
	 * @return
	 * @throws Exception 
	 */
	private static String disContinuousNumber(NumberRule nr) throws Exception {
		String thisNumber = null;
		if (StringUtils.isEmpty(nr.getNumberValue())) {
			thisNumber = nr.getNumberStart();
		} else {
			//获取当前值
			if(nr.getType() == 1){
				thisNumber = nr.getNumberValue();
			}
			//获取下一个值
			else if(nr.getType() == 2){
				thisNumber = getNextValue(nr);
			}
		}
		if(numberState(nr.getNumberCeiling(), thisNumber).equals(Constants.ERROR_CODE_NUMBER)){
			return Constants.ERROR_CODE_NUMBER;
		} else {
			// 标识流水号已用
			NumberRule temp = new NumberRule();
			temp.setRuleName(nr.getRuleName());
			temp.setNumberValue(thisNumber);
			temp.setNumberStateValue(1);
			int state = numberRuleService.update(temp);
			//int state = dao.updateRuleValue(nr.getRuleName(), thisNumber, 1);
			if (state == 1) {
				return thisNumber.toUpperCase();
			} else {
				throw new Exception();
			}
		}
	}

	/**
	 * 连续的流水号
	 * 
	 * @param nr
	 * @return
	 * @throws Exception 
	 */
	private static String continuousNumber(NumberRule nr) throws Exception {
		// 当前流水号不可用
		if (nr.getNumberStateValue() == Constants.NUMBER_STATE_VALUE_USED) {
			String thisNumber = null;
			if (StringUtils.isEmpty(nr.getNumberValue())) {
				thisNumber = nr.getNumberStart();
			} else {
				//获取当前值
				if(nr.getType() == 1){
					thisNumber = nr.getNumberValue();
				}
				//获取下一个值
				else if(nr.getType() == 2){
					thisNumber = getNextValue(nr);
				}
			}
			if(numberState(nr.getNumberCeiling(), thisNumber).equals(Constants.ERROR_CODE_NUMBER)) {
				return Constants.ERROR_CODE_NUMBER;
			} else {
				// 标识流水号未用
				NumberRule temp = new NumberRule();
				temp.setRuleName(nr.getRuleName());
				temp.setNumberValue(thisNumber);
				temp.setNumberStateValue(2);
				int state = numberRuleService.update(temp);
				//int state = dao.updateRuleValue(nr.getRuleName(), thisNumber, 2);
				if (state == 1) {
					return thisNumber.toUpperCase();
				}
				else {
					throw new Exception();
				}
			}
		}
		// 当前流水号可用
		else if (nr.getNumberStateValue() == Constants.NUMBER_STATE_VALUE_UNUSED) {
			if (StringUtils.isEmpty(nr.getNumberValue())){
				if(numberState(nr.getNumberCeiling(), nr.getNumberStart()).equals(Constants.ERROR_CODE_NUMBER)){
					return Constants.ERROR_CODE_NUMBER;
				} else {
					NumberRule temp = new NumberRule();
					temp.setRuleName(nr.getRuleName());
					temp.setNumberValue(nr.getNumberStart());
					int state = numberRuleService.update(temp);
					if (state == 1)
						return nr.getNumberStart().toUpperCase();
					else
						throw new Exception();
					
				}
			}
			else {
				if(numberState(nr.getNumberCeiling(), nr.getNumberValue()).equals(Constants.ERROR_CODE_NUMBER)){
					return Constants.ERROR_CODE_NUMBER;
				} else {
					// 标识流水号未用
					NumberRule temp = new NumberRule();
					temp.setRuleName(nr.getRuleName());
					temp.setNumberStateValue(2);
					numberRuleService.update(temp);
					//dao.updateNumberStateValue(nr.getRuleName(), 2);
					return nr.getNumberValue().toUpperCase();
				}
			}
		}

		return null;
	}

	/**
	 * @description 获取下一个流水号
	 * @param String
	 *            s 当前流水号
	 * @return String 生成的下一个流水号
	 * @author 高研
	 */
	private static String getNextValue(NumberRule nr) throws Exception {
		byte b[] = nextValueForAlphabet(nr);
		return new String(b);
	}


	private static byte[] nextValueForAlphabet(NumberRule nr) throws Exception {
		byte numberValue[] = nr.getNumberValue().getBytes();
		byte result[] = numberValue;

		int step = 0;
		for (int i = numberValue.length - 1; i >= 0; i--) {
			if((int) numberValue[i] == 122) {
				continue;
			}
			int ascii = (int) (char) (int) numberValue[i] + nr.getNumberStep() - step;
			if (ascii > 122) {
				step = ascii - 122;
				ascii = 122;
				result[i] = (byte) ascii;
			} else {
				result[i] = (byte) ascii;
				break;
			}
		}
		return result;
	}

	/**
	 * @description 判断流水号状态
	 * @param NumberRule
	 *            nr,String number
	 * @return int 1正常 2流水号大于上限
	 * @author 高研
	 */
	public static String numberState(String numberCeiling, String number) throws Exception {
		if (StringUtils.isNotEmpty(number)) {
			// 没有上限返回成功
			if (StringUtils.isEmpty(numberCeiling)) {
				return number;
			}
			// 流水号大于上限值
			if (getAscii(number) > getAscii(numberCeiling)) {
				return Constants.ERROR_CODE_NUMBER;
			} else {
				return number;
			}
		}
		return Constants.ERROR_CODE_EXCEPTION;
	}
	
	private static int getAscii(String number) throws Exception {
		int value = 0;
		byte b[] = number.toLowerCase().getBytes();
		for(int i=0;i<b.length;i++){
			value += b[i];
		}
		return value;
	}
}
