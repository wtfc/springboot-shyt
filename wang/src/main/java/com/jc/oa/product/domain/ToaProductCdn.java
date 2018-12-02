package com.jc.oa.product.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version CDN业务表
*/
public class ToaProductCdn extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private Integer customersId;//   客户编码
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
