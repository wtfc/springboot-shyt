package com.jc.system.number.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.system.number.dao.INumberRuleDao;
import com.jc.system.number.domain.NumberRule;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.number.service.INumberRuleService;

/**
 * @title GOA2.0
 * @description  业务服务类
 * @author 
 * @version  2014-05-04
 */
@Service
public class NumberRuleServiceImpl extends BaseServiceImpl<NumberRule> implements INumberRuleService{

	private INumberRuleDao numberRuleDao;
	
	public NumberRuleServiceImpl(){}
	
	@Autowired
	public NumberRuleServiceImpl(INumberRuleDao numberRuleDao){
		super(numberRuleDao);
		this.numberRuleDao = numberRuleDao;
	}

}