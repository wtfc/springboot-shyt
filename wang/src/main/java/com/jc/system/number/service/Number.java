package com.jc.system.number.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jc.system.CustomException;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.number.domain.NumberRule;
import com.jc.system.number.domain.Parameter;
import com.jc.system.number.service.factory.SerialNumberFactory;
import com.jc.system.number.service.interfaces.INumber;
import com.jc.system.number.util.Constants;

/**
 * 
 * @description: 编号service
 * @created: 2013-4-1 下午2:52:02
 */
public class Number implements INumber {
	
	private INumberRuleService numberRuleService = SpringContextHolder.getBean(INumberRuleService.class);

	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * @description 获取编号
	 * @param String ruleName 规则名称, String[] parameter 参数, type 1当前值	2下一个值
	 * @return String 编号
	 * @author 高研
	 */
	public synchronized String getNumber(String ruleName, int count, int type, String parameter []) {
		try {
			if (StringUtils.isNotEmpty(ruleName)) {
				StringBuffer sb = new StringBuffer();
				//查询规则信息
				NumberRule temp = new NumberRule();
				temp.setRuleName(ruleName);
				NumberRule numberRule = numberRuleService.get(temp);
				
				if (numberRule == null) {
					// 参数不正确
					return Constants.ERROR_CODE_PARAMETER;
				}
				//判断流水号是否连续 如果连续则不能生成多个编号
				if(numberRule.getNumberState() == Constants.NUMBER_STATE_CONTINUOUS && count > 1) {
					return Constants.ERROR_CODE_PARAMETER;
				}
				//设置获取流水号类型
				numberRule.setType(type);
				
				Parameter p = new Parameter(parameter);
				DefaultParameter parameterNumber = new DefaultParameter();
				//前缀
				String prefix = parameterNumber.getParameterFirstNumber(numberRule, p);
				//后缀
				String suffix = parameterNumber.getParameterEndNumber(numberRule, p);
				
				for(int i=0; i<count; i++) {
					//日期串
					String dateNumber = DateNumber.getDateNumber(numberRule, p);
					//流水串
					SerialNumberFactory serialNumberFactory = SerialNumberFactory.getInstance(numberRule.getNumberType());
					String serialNumber = serialNumberFactory.getSerialNumber(numberRule);
					
					if(serialNumber.equals(Constants.ERROR_CODE_NUMBER)) {
						sb.append(Constants.ERROR_CODE_NUMBER);
					} else {
						String number = SerialUtil.bindNumber(prefix, dateNumber, serialNumber, suffix, numberRule);
						log.info(number);
						// 第一位为标识 1成功  其他为错误
						sb.append(number);
						if(i != count-1) {
							sb.append(",");
						}
					}
				}
				return sb.toString();
			} else {
				// 参数不正确
				log.error(ruleName+"规则名不正确");
				return Constants.ERROR_CODE_PARAMETER;
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			log.error("规则名称"+ruleName+" 编号生成异常："+e.toString());
			return Constants.ERROR_CODE_EXCEPTION;
		}
	}

	/**
	 * @description 修改编号状态
	 * @param String ruleName 规则名称, int state 流水号连续标识
	 * @return int 操作结果
	 * @author 高研
	 */
	public synchronized int setNumberState(String ruleName, int state) {
		if (StringUtils.isNotEmpty(ruleName)) {
			NumberRule temp = new NumberRule();
			temp.setRuleName(ruleName);
			temp.setNumberStateValue(state);
			try {
				return numberRuleService.update(temp);
			} catch (CustomException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
