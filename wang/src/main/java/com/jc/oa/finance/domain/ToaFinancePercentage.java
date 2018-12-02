package com.jc.oa.finance.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 绩效提成表
*/
public class ToaFinancePercentage extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String perNumber;//   编码
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
