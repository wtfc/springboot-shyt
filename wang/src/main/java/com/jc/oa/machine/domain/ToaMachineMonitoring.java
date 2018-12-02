package com.jc.oa.machine.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 监控报告通告
*/
public class ToaMachineMonitoring extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String faultNumber;//   工单编号
	
	public String getMachinaValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachina()) == null ? "" : dicManager.getDic("room", this.getMachina()).getValue();
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	@JsonSerialize(using = CustomDatetimeSerializer.class)
