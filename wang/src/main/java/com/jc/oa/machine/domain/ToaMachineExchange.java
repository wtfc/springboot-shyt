package com.jc.oa.machine.domain;

import com.jc.foundation.domain.BaseBean;
import java.util.Date;
/**
 * @author mrb
 * @version 工单附言表
*/
public class ToaMachineExchange extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String restartId;//   工单ID
	public Integer getServiceName() {
		return serviceName;
	}
	public void setServiceName(Integer serviceName) {
		this.serviceName = serviceName;
	}
	
