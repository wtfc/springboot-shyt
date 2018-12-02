package com.jc.oa.machine.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 机房故障
*/
public class ToaMachineFault extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String faultNumber;//   工单编号
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getIntDate() {
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	@JsonSerialize(using = CustomDatetimeSerializer.class)
