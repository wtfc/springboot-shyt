package com.jc.oa.work.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 人员加班信息表
*/
public class ToaSysWorkOvertime extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String name;//   姓名
	public String getName() {
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	@JsonSerialize(using = CustomDatetimeSerializer.class)
