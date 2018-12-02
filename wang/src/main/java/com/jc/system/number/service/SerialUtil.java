package com.jc.system.number.service;

import org.apache.log4j.Logger;

import com.jc.system.number.domain.NumberRule;
import com.jc.system.number.util.Constants;


public class SerialUtil {

	private static Logger log = Logger.getLogger(SerialUtil.class);

	/**
	 * @description 拼装编号
	 * @param prefix 前缀
	 * @param dateNumber 日期字符
	 * @param serialNumber 流水
	 * @param suffix 后缀
	 * @param numberRule 规则
	 * @return 拼装后的编号
	 * @author 高研
	 */
	public static String bindNumber(String prefix, String dateNumber,
			String serialNumber, String suffix, NumberRule numberRule) {
		switch (numberRule.getDateIndex()) {
		case 1:
			return Constants.SUCCESS + dateNumber + prefix + serialNumber + suffix;
		case 2:
			return Constants.SUCCESS + prefix + dateNumber + serialNumber + suffix;
		case 3:
			return Constants.SUCCESS + prefix + serialNumber + dateNumber + suffix;
		case 4:
			return Constants.SUCCESS + prefix + serialNumber + suffix + dateNumber;
		}
		return null;
	}

	
}
